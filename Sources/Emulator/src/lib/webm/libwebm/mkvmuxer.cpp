// Copyright (c) 2011 The WebM project authors. All Rights Reserved.
//
// Use of this source code is governed by a BSD-style license
// that can be found in the LICENSE file in the root of the source
// tree. An additional intellectual property rights grant can be found
// in the file PATENTS.  All contributing project authors may
// be found in the AUTHORS file in the root of the source tree.

#include "mkvmuxer.hpp"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include <cassert>
#include <new>
#include <iostream>

#include "mkvmuxerutil.hpp"
#include "webmids.hpp"

using namespace std;

namespace mkvmuxer {

///////////////////////////////////////////////////////////////
//
// IMkvWriter Class

IMkvWriter::IMkvWriter() {
}

IMkvWriter::~IMkvWriter() {
}

bool WriteEbmlHeader(IMkvWriter* writer) {
  // Level 0
  uint64 size = EbmlElementSize(kMkvEBMLVersion, 1ULL, false);
  size += EbmlElementSize(kMkvEBMLReadVersion, 1ULL, false);
  size += EbmlElementSize(kMkvEBMLMaxIDLength, 4ULL, false);
  size += EbmlElementSize(kMkvEBMLMaxSizeLength, 8ULL, false);
  size += EbmlElementSize(kMkvDocType, "webm", false);
  size += EbmlElementSize(kMkvDocTypeVersion, 2ULL, false);
  size += EbmlElementSize(kMkvDocTypeReadVersion, 2ULL, false);

  if (!WriteEbmlMasterElement(writer, kMkvEBML, size))
    return false;

  if (!WriteEbmlElement(writer, kMkvEBMLVersion, 1ULL))
    return false;
  if (!WriteEbmlElement(writer, kMkvEBMLReadVersion, 1ULL))
    return false;
  if (!WriteEbmlElement(writer, kMkvEBMLMaxIDLength, 4ULL))
    return false;
  if (!WriteEbmlElement(writer, kMkvEBMLMaxSizeLength, 8ULL))
    return false;
  if (!WriteEbmlElement(writer, kMkvDocType, "webm"))
    return false;
  if (!WriteEbmlElement(writer, kMkvDocTypeVersion, 2ULL))
    return false;
  if (!WriteEbmlElement(writer, kMkvDocTypeReadVersion, 2ULL))
    return false;

  return true;
}

///////////////////////////////////////////////////////////////
//
// Frame Class

Frame::Frame()
    : frame_(NULL),
      length_(0),
      track_number_(0),
      timestamp_(0),
      is_key_(false) {
}

Frame::~Frame() {
  delete [] frame_;
}

bool Frame::Init(const uint8* frame, uint64 length) {
  uint8* const data = new (std::nothrow) uint8[static_cast<size_t>(length)];
  if (!data)
    return false;

  delete [] frame_;
  frame_ = data;
  length_ = length;

  memcpy(frame_, frame, static_cast<size_t>(length_));
  return true;
}

///////////////////////////////////////////////////////////////
//
// CuePoint Class

CuePoint::CuePoint()
    : time_(0),
      track_(0),
      cluster_pos_(0),
      block_number_(1),
      output_block_number_(true) {
}

CuePoint::~CuePoint() {
}

bool CuePoint::Write(IMkvWriter* writer) const {
  assert(writer);
  assert(track_ > 0);
  assert(cluster_pos_ > 0);

  uint64 size = EbmlElementSize(kMkvCueClusterPosition, cluster_pos_, false);
  size += EbmlElementSize(kMkvCueTrack, track_, false);
  if (output_block_number_ && block_number_ > 1)
    size += EbmlElementSize(kMkvCueBlockNumber, block_number_, false);
  const uint64 track_pos_size = EbmlElementSize(kMkvCueTrackPositions,
                                                size,
                                                true) + size;
  const uint64 payload_size = EbmlElementSize(kMkvCueTime, time_, false) +
                              track_pos_size;

  if (!WriteEbmlMasterElement(writer, kMkvCuePoint, payload_size))
    return false;

  const int64 payload_position = writer->Position();
  if (payload_position < 0)
    return false;

  if (!WriteEbmlElement(writer, kMkvCueTime, time_))
    return false;

  if (!WriteEbmlMasterElement(writer, kMkvCueTrackPositions, size))
    return false;
  if (!WriteEbmlElement(writer, kMkvCueTrack, track_))
    return false;
  if (!WriteEbmlElement(writer, kMkvCueClusterPosition, cluster_pos_))
    return false;
  if (output_block_number_ && block_number_ > 1)
    if (!WriteEbmlElement(writer, kMkvCueBlockNumber, block_number_))
      return false;

  const int64 stop_position = writer->Position();
  if (stop_position < 0)
    return false;
  assert(stop_position - payload_position == static_cast<int64>(payload_size));

  return true;
}

uint64 CuePoint::PayloadSize() const {
  uint64 size = EbmlElementSize(kMkvCueClusterPosition, cluster_pos_, false);
  size += EbmlElementSize(kMkvCueTrack, track_, false);
  if (output_block_number_ && block_number_ > 1)
    size += EbmlElementSize(kMkvCueBlockNumber, block_number_, false);
  const uint64 track_pos_size = EbmlElementSize(kMkvCueTrackPositions,
                                                size,
                                                true) + size;
  const uint64 payload_size = EbmlElementSize(kMkvCueTime, time_, false) +
                              track_pos_size;

  return payload_size;
}

uint64 CuePoint::Size() const {
  const uint64 payload_size = PayloadSize();
  return EbmlElementSize(kMkvCuePoint, payload_size, true) + payload_size;
}

///////////////////////////////////////////////////////////////
//
// Cues Class

Cues::Cues()
    : cue_entries_capacity_(0),
      cue_entries_size_(0),
      cue_entries_(NULL),
      output_block_number_(true) {
}

Cues::~Cues() {
  if (cue_entries_) {
    for (int32 i = 0; i < cue_entries_size_; ++i) {
      CuePoint* const cue = cue_entries_[i];
      delete cue;
    }
    delete [] cue_entries_;
  }
}

bool Cues::AddCue(CuePoint* cue) {
  assert(cue);

  if ((cue_entries_size_ + 1) > cue_entries_capacity_) {
    // Add more CuePoints.
    const int32 new_capacity =
        (!cue_entries_capacity_) ? 2 : cue_entries_capacity_ * 2;

    assert(new_capacity > 0);
    CuePoint** const cues = new (std::nothrow) CuePoint*[new_capacity];
    if (!cues)
      return false;

    for (int32 i = 0; i < cue_entries_size_; ++i) {
      cues[i] = cue_entries_[i];
    }

    delete [] cue_entries_;

    cue_entries_ = cues;
    cue_entries_capacity_ = new_capacity;
  }

  cue->set_output_block_number(output_block_number_);
  cue_entries_[cue_entries_size_++] = cue;
  return true;
}

const CuePoint* Cues::GetCueByIndex(int32 index) const {
  if (cue_entries_ == NULL)
    return NULL;

  if (index >= cue_entries_size_)
    return NULL;

  return cue_entries_[index];
}

bool Cues::Write(IMkvWriter* writer) const {
  assert(writer);

  uint64 size = 0;
  for (int32 i = 0; i < cue_entries_size_; ++i) {
    const CuePoint* const cue = GetCueByIndex(i);
    assert(cue);
    size += cue->Size();
  }

  if (!WriteEbmlMasterElement(writer, kMkvCues, size))
    return false;

  const int64 payload_position = writer->Position();
  if (payload_position < 0)
    return false;

  for (int32 i = 0; i < cue_entries_size_; ++i) {
    const CuePoint* const cue = GetCueByIndex(i);
    assert(cue);
    if (!cue->Write(writer))
      return false;
  }

  const int64 stop_position = writer->Position();
  if (stop_position < 0)
    return false;
  assert(stop_position - payload_position == static_cast<int64>(size));

  return true;
}

///////////////////////////////////////////////////////////////
//
// Track Class

Track::Track()
    : codec_id_(NULL),
      codec_private_(NULL),
      language_(NULL),
      name_(NULL),
      number_(0),
      type_(0),
      uid_(MakeUID()),
      codec_private_length_(0) {
}

Track::~Track() {
  delete [] codec_id_;
  delete [] codec_private_;
}

uint64 Track::PayloadSize() const {
  uint64 size = EbmlElementSize(kMkvTrackNumber, number_, false);
  size += EbmlElementSize(kMkvTrackUID, uid_, false);
  size += EbmlElementSize(kMkvTrackType, type_, false);
  if (codec_id_)
    size += EbmlElementSize(kMkvCodecID, codec_id_, false);
  if (codec_private_)
    size += EbmlElementSize(kMkvCodecPrivate,
                            codec_private_,
                            codec_private_length_,
                            false);
  if (language_)
    size += EbmlElementSize(kMkvLanguage, language_, false);
  if (name_)
    size += EbmlElementSize(kMkvName, name_, false);

  return size;
}

uint64 Track::Size() const {
  uint64 size = Track::PayloadSize();
  size += EbmlElementSize(kMkvTrackEntry, size, true);

  return size;
}

bool Track::Write(IMkvWriter* writer) const {
  assert(writer);

  // |size| may be bigger than what is written out in this function because
  // derived classes may write out more data in the Track element.
  const uint64 payload_size = PayloadSize();

  if (!WriteEbmlMasterElement(writer, kMkvTrackEntry, payload_size))
    return false;

  uint64 size = EbmlElementSize(kMkvTrackNumber, number_, false);
  size += EbmlElementSize(kMkvTrackUID, uid_, false);
  size += EbmlElementSize(kMkvTrackType, type_, false);
  if (codec_id_)
    size += EbmlElementSize(kMkvCodecID, codec_id_, false);
  if (codec_private_)
    size += EbmlElementSize(kMkvCodecPrivate,
                            codec_private_,
                            codec_private_length_,
                            false);
  if (language_)
    size += EbmlElementSize(kMkvLanguage, language_, false);
  if (name_)
    size += EbmlElementSize(kMkvName, name_, false);

  const int64 payload_position = writer->Position();
  if (payload_position < 0)
    return false;

  if (!WriteEbmlElement(writer, kMkvTrackNumber, number_))
    return false;
  if (!WriteEbmlElement(writer, kMkvTrackUID, uid_))
    return false;
  if (!WriteEbmlElement(writer, kMkvTrackType, type_))
    return false;
  if (codec_id_) {
    if (!WriteEbmlElement(writer, kMkvCodecID, codec_id_))
      return false;
  }
  if (codec_private_) {
    if (!WriteEbmlElement(writer,
                          kMkvCodecPrivate,
                          codec_private_,
                          codec_private_length_))
      return false;
  }
  if (language_) {
    if (!WriteEbmlElement(writer, kMkvLanguage, language_))
      return false;
  }
  if (name_) {
    if (!WriteEbmlElement(writer, kMkvName, name_))
      return false;
  }

  const int64 stop_position = writer->Position();
  if (stop_position < 0)
    return false;
  assert(stop_position - payload_position == static_cast<int64>(size));

  return true;
}

bool Track::SetCodecPrivate(const uint8* codec_private, uint64 length) {
  assert(codec_private);
  assert(length > 0);

  delete [] codec_private_;

  codec_private_ =
      new (std::nothrow) uint8[static_cast<size_t>(length)];
  if (!codec_private_)
    return false;

  memcpy(codec_private_, codec_private, static_cast<size_t>(length));
  codec_private_length_ = length;

  return true;
}

void Track::set_codec_id(const char* codec_id) {
  assert(codec_id);

  delete [] codec_id_;

  const size_t length = strlen(codec_id) + 1;
  codec_id_ = new (std::nothrow) char[length];
  if (codec_id_) {
#ifdef _MSC_VER
    strcpy_s(codec_id_, length, codec_id);
#else
    strcpy(codec_id_, codec_id);
#endif
  }
}

// TODO(fgalligan): Vet the language parameter.
void Track::set_language(const char* language) {
  assert(language);

  delete [] language_;

  const size_t length = strlen(language) + 1;
  language_ = new (std::nothrow) char[length];
  if (language_) {
#ifdef _MSC_VER
    strcpy_s(language_, length, language);
#else
    strcpy(language_, language);
#endif
  }
}

void Track::set_name(const char* name) {
  assert(name);

  delete [] name_;

  const size_t length = strlen(name) + 1;
  name_ = new (std::nothrow) char[length];
  if (name_) {
#ifdef _MSC_VER
    strcpy_s(name_, length, name);
#else
    strcpy(name_, name);
#endif
  }
}

bool Track::is_seeded_ = false;

uint64 Track::MakeUID() {
  if (!is_seeded_) {
    srand(static_cast<uint32>(time(NULL)));
    is_seeded_ = true;
  }

  uint64 track_uid = 0;
  for (int32 i = 0; i < 7; ++i) {  // avoid problems with 8-byte values
    track_uid <<= 8;

    const int32 nn = rand();
    const int32 n = 0xFF & (nn >> 4);  // throw away low-order bits

    track_uid |= n;
  }

  return track_uid;
}

///////////////////////////////////////////////////////////////
//
// VideoTrack Class

VideoTrack::VideoTrack()
    : display_height_(0),
      display_width_(0),
      frame_rate_(0.0),
      height_(0),
      stereo_mode_(0),
      width_(0) {
}

VideoTrack::~VideoTrack() {
}

bool VideoTrack::SetStereoMode(uint64 stereo_mode) {
  if (stereo_mode != kMono &&
      stereo_mode != kSideBySideLeftIsFirst &&
      stereo_mode != kTopBottomRightIsFirst &&
      stereo_mode != kTopBottomLeftIsFirst &&
      stereo_mode != kSideBySideRightIsFirst)
    return false;

  stereo_mode_ = stereo_mode;
  return true;
}

uint64 VideoTrack::PayloadSize() const {
  const uint64 parent_size = Track::PayloadSize();

  uint64 size = VideoPayloadSize();
  size += EbmlElementSize(kMkvVideo, size, true);

  return parent_size + size;
}

uint64 VideoTrack::Size() const {
  const uint64 parent_size = Track::Size();

  uint64 size = VideoPayloadSize();
  size += EbmlElementSize(kMkvVideo, size, true);

  return parent_size + size;
}

bool VideoTrack::Write(IMkvWriter* writer) const {
  assert(writer);

  if (!Track::Write(writer))
    return false;

  const uint64 size = VideoPayloadSize();

  if (!WriteEbmlMasterElement(writer, kMkvVideo, size))
    return false;

  const int64 payload_position = writer->Position();
  if (payload_position < 0)
    return false;

  if (!WriteEbmlElement(writer, kMkvPixelWidth, width_))
    return false;
  if (!WriteEbmlElement(writer, kMkvPixelHeight, height_))
    return false;
  if (display_width_ > 0)
    if (!WriteEbmlElement(writer, kMkvDisplayWidth, display_width_))
      return false;
  if (display_height_ > 0)
    if (!WriteEbmlElement(writer, kMkvDisplayHeight, display_height_))
      return false;
  if (stereo_mode_ > kMono)
    if (!WriteEbmlElement(writer, kMkvStereoMode, stereo_mode_))
      return false;
  if (frame_rate_ > 0.0)
    if (!WriteEbmlElement(writer,
                          kMkvFrameRate,
                          static_cast<float>(frame_rate_)))
      return false;

  const int64 stop_position = writer->Position();
  if (stop_position < 0)
    return false;
  assert(stop_position - payload_position == static_cast<int64>(size));

  return true;
}

uint64 VideoTrack::VideoPayloadSize() const {
  uint64 size = EbmlElementSize(kMkvPixelWidth, width_, false);
  size += EbmlElementSize(kMkvPixelHeight, height_, false);
  if (display_width_ > 0)
    size += EbmlElementSize(kMkvDisplayWidth, display_width_, false);
  if (display_height_ > 0)
    size += EbmlElementSize(kMkvDisplayHeight, display_height_, false);
  if (stereo_mode_ > kMono)
    size += EbmlElementSize(kMkvStereoMode, stereo_mode_, false);
  if (frame_rate_ > 0.0)
    size += EbmlElementSize(kMkvFrameRate,
                            static_cast<float>(frame_rate_),
                            false);

  return size;
}

///////////////////////////////////////////////////////////////
//
// AudioTrack Class

AudioTrack::AudioTrack()
    : bit_depth_(0),
      channels_(1),
      sample_rate_(0.0) {
}

AudioTrack::~AudioTrack() {
}

uint64 AudioTrack::PayloadSize() const {
  const uint64 parent_size = Track::PayloadSize();

  uint64 size = EbmlElementSize(kMkvSamplingFrequency,
                                static_cast<float>(sample_rate_),
                                false);
  size += EbmlElementSize(kMkvChannels, channels_, false);
  if (bit_depth_ > 0)
    size += EbmlElementSize(kMkvBitDepth, bit_depth_, false);
  size += EbmlElementSize(kMkvAudio, size, true);

  return parent_size + size;
}

uint64 AudioTrack::Size() const {
  const uint64 parent_size = Track::Size();

  uint64 size = EbmlElementSize(kMkvSamplingFrequency,
                                static_cast<float>(sample_rate_),
                                false);
  size += EbmlElementSize(kMkvChannels, channels_, false);
  if (bit_depth_ > 0)
    size += EbmlElementSize(kMkvBitDepth, bit_depth_, false);
  size += EbmlElementSize(kMkvAudio, size, true);

  return parent_size + size;
}

bool AudioTrack::Write(IMkvWriter* writer) const {
  assert(writer);

  if (!Track::Write(writer))
    return false;

  // Calculate AudioSettings size.
  uint64 size = EbmlElementSize(kMkvSamplingFrequency,
                                static_cast<float>(sample_rate_),
                                false);
  size += EbmlElementSize(kMkvChannels, channels_, false);
  if (bit_depth_ > 0)
    size += EbmlElementSize(kMkvBitDepth, bit_depth_, false);

  if (!WriteEbmlMasterElement(writer, kMkvAudio, size))
    return false;

  const int64 payload_position = writer->Position();
  if (payload_position < 0)
    return false;

  if (!WriteEbmlElement(writer,
                        kMkvSamplingFrequency,
                        static_cast<float>(sample_rate_)))
    return false;
  if (!WriteEbmlElement(writer, kMkvChannels, channels_))
    return false;
  if (bit_depth_ > 0)
    if (!WriteEbmlElement(writer, kMkvBitDepth, bit_depth_))
      return false;

  const int64 stop_position = writer->Position();
  if (stop_position < 0)
    return false;
  assert(stop_position - payload_position == static_cast<int64>(size));

  return true;
}

///////////////////////////////////////////////////////////////
//
// Tracks Class

const char* const Tracks::kVp8CodecId = "V_VP8";
const char* const Tracks::kVorbisCodecId = "A_VORBIS";

Tracks::Tracks()
    : track_entries_(NULL),
      track_entries_size_(0) {
}

Tracks::~Tracks() {
  if (track_entries_) {
    for (uint32 i = 0; i < track_entries_size_; ++i) {
      Track* const track = track_entries_[i];
      delete track;
    }
    delete [] track_entries_;
  }
}

bool Tracks::AddTrack(Track* track, int32 number) {
  if (number < 0)
    return false;

  int32 track_num = number;

  // Check to make sure a track does not already have |track_num|.
  for (uint32 i = 0; i < track_entries_size_; ++i) {
    if (track_entries_[i]->number() == track_num)
      return false;
  }

  const uint32 count = track_entries_size_ + 1;

  Track** const track_entries = new (std::nothrow) Track*[count];
  if (!track_entries)
    return false;

  for (uint32 i = 0; i < track_entries_size_; ++i) {
    track_entries[i] = track_entries_[i];
  }

  delete [] track_entries_;

  // Find the lowest availible track number > 0.
  if (track_num == 0) {
    track_num = count;

    // Check to make sure a track does not already have |track_num|.
    bool exit = false;
    do {
      exit = true;
      for (uint32 i = 0; i < track_entries_size_; ++i) {
        if (track_entries[i]->number() == track_num) {
          track_num++;
          exit = false;
          break;
        }
      }
    } while (!exit);
  }
  track->set_number(track_num);

  track_entries_ = track_entries;
  track_entries_[track_entries_size_] = track;
  track_entries_size_ = count;
  return true;
}

const Track* Tracks::GetTrackByIndex(uint32 index) const {
  if (track_entries_ == NULL)
    return NULL;

  if (index >= track_entries_size_)
    return NULL;

  return track_entries_[index];
}

Track* Tracks::GetTrackByNumber(uint64 track_number) const {
  const int32 count = track_entries_size();
  for (int32 i = 0; i < count; ++i) {
    if (track_entries_[i]->number() == track_number)
      return track_entries_[i];
  }

  return NULL;
}

bool Tracks::TrackIsAudio(uint64 track_number) const {
  const Track* const track = GetTrackByNumber(track_number);

  if (track->type() == kAudio)
    return true;

  return false;
}

bool Tracks::TrackIsVideo(uint64 track_number) const {
  const Track* const track = GetTrackByNumber(track_number);

  if (track->type() == kVideo)
    return true;

  return false;
}

bool Tracks::Write(IMkvWriter* writer) const {
  assert(writer);

  uint64 size = 0;
  const int32 count = track_entries_size();
  for (int32 i = 0; i < count; ++i) {
    const Track* const track = GetTrackByIndex(i);
    assert(track);
    size += track->Size();
  }

  if (!WriteEbmlMasterElement(writer, kMkvTracks, size))
    return false;

  const int64 payload_position = writer->Position();
  if (payload_position < 0)
    return false;

  for (int32 i = 0; i < count; ++i) {
    const Track* const track = GetTrackByIndex(i);
    if (!track->Write(writer))
      return false;
  }

  const int64 stop_position = writer->Position();
  if (stop_position < 0)
    return false;
  assert(stop_position - payload_position == static_cast<int64>(size));

  return true;
}

///////////////////////////////////////////////////////////////
//
// Cluster Class

Cluster::Cluster(uint64 timecode, IMkvWriter* writer)
    : blocks_added_(0),
      finalized_(false),
      header_written_(false),
      payload_size_(0),
      position_for_cues_(-1),
      size_position_(-1),
      timecode_(timecode),
      writer_(writer) {
  // TODO(fgalligan): Create an Init function.
  assert(writer_);
  position_for_cues_ = writer_->Position();
}

Cluster::~Cluster() {
}

bool Cluster::AddFrame(const uint8* frame,
                       uint64 length,
                       uint64 track_number,
                       short timecode,
                       bool is_key) {
  if (finalized_)
    return false;

  if (!header_written_)
    if (!WriteClusterHeader())
      return false;

  const uint64 element_size = WriteSimpleBlock(writer_,
                                               frame,
                                               length,
                                               static_cast<char>(track_number),
                                               timecode,
                                               is_key);
  if (!element_size)
    return false;

  AddPayloadSize(element_size);
  blocks_added_++;

  return true;
}

void Cluster::AddPayloadSize(uint64 size) {
  payload_size_ += size;
}

bool Cluster::Finalize() {
  if (finalized_)
    return false;

  assert(size_position_ != -1);

  if (writer_->Seekable()) {
    const int64 pos = writer_->Position();

    if (writer_->Position(size_position_))
      return false;

    if (WriteUIntSize(writer_, payload_size(), 8))
      return false;

    if (writer_->Position(pos))
      return false;
  }

  finalized_ = true;

  return true;
}

bool Cluster::WriteClusterHeader() {
  assert(!finalized_);

  if (SerializeInt(writer_, kMkvCluster, 4))
    return false;

  // Save for later.
  size_position_ = writer_->Position();

  // Write "unknown" (EBML coded -1) as cluster size value. We need to write 8
  // bytes because we do not know how big our cluster will be.
  if (SerializeInt(writer_, kEbmlUnknownValue, 8))
    return false;

  if (!WriteEbmlElement(writer_, kMkvTimecode, timecode()))
    return false;
  AddPayloadSize(EbmlElementSize(kMkvTimecode, timecode(), false));
  header_written_ = true;

  return true;
}

///////////////////////////////////////////////////////////////
//
// SeekHead Class

SeekHead::SeekHead() : start_pos_(0ULL) {
  for (int32 i = 0; i < kSeekEntryCount; ++i) {
    seek_entry_id_[i] = 0;
    seek_entry_pos_[i] = 0;
  }
}

SeekHead::~SeekHead() {
}

bool SeekHead::Finalize(IMkvWriter* writer) const {
  if (writer->Seekable()) {
    assert(start_pos_ != -1);

    uint64 payload_size = 0;
    uint64 entry_size[kSeekEntryCount];

    for (int32 i = 0; i < kSeekEntryCount; ++i) {
      if (seek_entry_id_[i] != 0) {
        entry_size[i] = EbmlElementSize(
            kMkvSeekID,
            static_cast<uint64>(seek_entry_id_[i]),
            false);
        entry_size[i] += EbmlElementSize(kMkvSeekPosition,
                                         seek_entry_pos_[i],
                                         false);

        payload_size += EbmlElementSize(kMkvSeek, entry_size[i], true) +
                        entry_size[i];
      }
    }

    // No SeekHead elements
    if (payload_size == 0)
      return true;

    const int64 pos = writer->Position();
    if (writer->Position(start_pos_))
      return false;

    if (!WriteEbmlMasterElement(writer, kMkvSeekHead, payload_size))
      return false;

    for (int32 i = 0; i < kSeekEntryCount; ++i) {
      if (seek_entry_id_[i] != 0) {
        if (!WriteEbmlMasterElement(writer, kMkvSeek, entry_size[i]))
          return false;

        if (!WriteEbmlElement(writer,
                              kMkvSeekID,
                              static_cast<uint64>(seek_entry_id_[i])))
          return false;

        if (!WriteEbmlElement(writer, kMkvSeekPosition, seek_entry_pos_[i]))
          return false;
      }
    }

    const uint64 total_entry_size = kSeekEntryCount * MaxEntrySize();
    const uint64 total_size = EbmlElementSize(kMkvSeekHead,
                                              total_entry_size,
                                              true) + total_entry_size;
    const int64 size_left = total_size - (writer->Position() - start_pos_);

    const uint64 bytes_written = WriteVoidElement(writer, size_left);
    if (!bytes_written)
      return false;

    if (writer->Position(pos))
      return false;
  }

  return true;
}

bool SeekHead::Write(IMkvWriter* writer) {
  const uint64 entry_size = kSeekEntryCount * MaxEntrySize();
  const uint64 size = EbmlElementSize(kMkvSeekHead, entry_size, true);

  start_pos_ = writer->Position();

  const uint64 bytes_written = WriteVoidElement(writer, size + entry_size);
  if (!bytes_written)
    return false;

  return true;
}

bool SeekHead::AddSeekEntry(uint32 id, uint64 pos) {
  for (int32 i = 0; i < kSeekEntryCount; ++i) {
    if (seek_entry_id_[i] == 0) {
      seek_entry_id_[i] = id;
      seek_entry_pos_[i] = pos;
      return true;
    }
  }
  return false;
}

uint64 SeekHead::MaxEntrySize() const {
  const uint64 max_entry_payload_size =
      EbmlElementSize(kMkvSeekID, 0xffffffffULL, false) +
      EbmlElementSize(kMkvSeekPosition, 0xffffffffffffffffULL, false);
  const uint64 max_entry_size =
      EbmlElementSize(kMkvSeek, max_entry_payload_size, true) +
      max_entry_payload_size;

  return max_entry_size;
}

///////////////////////////////////////////////////////////////
//
// SegmentInfo Class

SegmentInfo::SegmentInfo()
    : duration_(-1.0),
      muxing_app_(NULL),
      timecode_scale_(1000000ULL),
      writing_app_(NULL),
      duration_pos_(-1) {
}

SegmentInfo::~SegmentInfo() {
  delete [] muxing_app_;
  delete [] writing_app_;
}

bool SegmentInfo::Init() {
  int32 major;
  int32 minor;
  int32 build;
  int32 revision;
  GetVersion(major, minor, build, revision);
  char temp[256];
#ifdef _MSC_VER
  sprintf_s(temp,
            sizeof(temp)/sizeof(temp[0]),
            "libwebm-%d.%d.%d.%d",
            major,
            minor,
            build,
            revision);
#else
  snprintf(temp,
           sizeof(temp)/sizeof(temp[0]),
           "libwebm-%d.%d.%d.%d",
           major,
           minor,
           build,
           revision);
#endif

  const size_t app_len = strlen(temp) + 1;

  delete [] muxing_app_;

  muxing_app_ = new (std::nothrow) char[app_len];
  if (!muxing_app_)
    return false;

#ifdef _MSC_VER
  strcpy_s(muxing_app_, app_len, temp);
#else
  strcpy(muxing_app_, temp);
#endif

  set_writing_app(temp);
  if (!writing_app_)
    return false;
  return true;
}

bool SegmentInfo::Finalize(IMkvWriter* writer) const {
  assert(writer);

  if (duration_ > 0.0) {
    if (writer->Seekable()) {
      assert(duration_pos_ != -1);

      const int64 pos = writer->Position();

      if (writer->Position(duration_pos_))
        return false;

      if (!WriteEbmlElement(writer,
                            kMkvDuration,
                            static_cast<float>(duration_)))
        return false;

      if (writer->Position(pos))
        return false;
    }
  }

  return true;
}

bool SegmentInfo::Write(IMkvWriter* writer) {
  assert(writer);

  if (!muxing_app_ || !writing_app_)
    return false;

  uint64 size = EbmlElementSize(kMkvTimecodeScale, timecode_scale_, false);
  if (duration_ > 0.0)
    size += EbmlElementSize(kMkvDuration,
                            static_cast<float>(duration_),
                            false);
  size += EbmlElementSize(kMkvMuxingApp, muxing_app_, false);
  size += EbmlElementSize(kMkvWritingApp, writing_app_, false);

  if (!WriteEbmlMasterElement(writer, kMkvInfo, size))
    return false;

  const int64 payload_position = writer->Position();
  if (payload_position < 0)
    return false;

  if (!WriteEbmlElement(writer, kMkvTimecodeScale, timecode_scale_))
    return false;

  if (duration_ > 0.0) {
    // Save for later
    duration_pos_ = writer->Position();

    if (!WriteEbmlElement(writer, kMkvDuration, static_cast<float>(duration_)))
      return false;
  }

  if (!WriteEbmlElement(writer, kMkvMuxingApp, muxing_app_))
    return false;
  if (!WriteEbmlElement(writer, kMkvWritingApp, writing_app_))
    return false;

  const int64 stop_position = writer->Position();
  if (stop_position < 0)
    return false;
  assert(stop_position - payload_position == static_cast<int64>(size));

  return true;
}

void SegmentInfo::set_writing_app(const char* app) {
  assert(app);

  delete [] writing_app_;

  const size_t length = strlen(app) + 1;
  writing_app_ = new (std::nothrow) char[length];
  if (writing_app_) {
#ifdef _MSC_VER
    strcpy_s(writing_app_, length, app);
#else
    strcpy(writing_app_, app);
#endif
  }
}

///////////////////////////////////////////////////////////////
//
// Segment Class

Segment::Segment(IMkvWriter* writer)
    : cluster_list_(NULL),
      cluster_list_capacity_(0),
      cluster_list_size_(0),
      cues_track_(0),
      frames_(NULL),
      frames_capacity_(0),
      frames_size_(0),
      has_video_(false),
      header_written_(false),
      last_timestamp_(0),
      max_cluster_duration_(kDefaultMaxClusterDuration),
      max_cluster_size_(0),
      mode_(kFile),
      new_cluster_(true),
      new_cuepoint_(false),
      output_cues_(true),
      payload_pos_(0),
      size_position_(0),
      writer_(writer) {
  assert(writer_);

  // TODO(fgalligan): Create an Init function for Segment.
  segment_info_.Init();
}

Segment::~Segment() {
  if (cluster_list_) {
    for (int32 i = 0; i < cluster_list_size_; ++i) {
      Cluster* const cluster = cluster_list_[i];
      delete cluster;
    }
    delete [] cluster_list_;
  }

  if (frames_) {
    for (int32 i = 0; i < frames_size_; ++i) {
      Frame* const frame = frames_[i];
      delete frame;
    }
    delete [] frames_;
  }
}

bool Segment::Finalize() {
  if (!WriteFramesAll())
    return false;

  if (mode_ == kFile) {
    if (cluster_list_size_ > 0) {
      // Update last cluster's size
      Cluster* const old_cluster = cluster_list_[cluster_list_size_-1];
      assert(old_cluster);

      if (!old_cluster->Finalize())
        return false;
    }

    const double duration =
        static_cast<double>(last_timestamp_) / segment_info_.timecode_scale();
    segment_info_.set_duration(duration);
    if (!segment_info_.Finalize(writer_))
      return false;

    // TODO(fgalligan): Add support for putting the Cues at the front.
    if (!seek_head_.AddSeekEntry(kMkvCues, writer_->Position() - payload_pos_))
      return false;

    if (!cues_.Write(writer_))
      return false;

    if (!seek_head_.Finalize(writer_))
      return false;

    if (writer_->Seekable()) {
      assert(size_position_ != -1);

      const int64 pos = writer_->Position();

      // -8 for the size of the segment size
      const int64 segment_size = pos - size_position_ - 8;
      assert(segment_size > 0);

      if (writer_->Position(size_position_))
        return false;

      if (WriteUIntSize(writer_, segment_size, 8))
        return false;

      if (writer_->Position(pos))
        return false;
    }
  }

  return true;
}

uint64 Segment::AddVideoTrack(int32 width, int32 height) {
  return AddVideoTrack(width, height, 0);
}

uint64 Segment::AddVideoTrack(int32 width, int32 height, int32 number) {
  VideoTrack* const vid_track = new (std::nothrow) VideoTrack();
  if (!vid_track)
    return 0;

  vid_track->set_type(Tracks::kVideo);
  vid_track->set_codec_id(Tracks::kVp8CodecId);
  vid_track->set_width(width);
  vid_track->set_height(height);

  tracks_.AddTrack(vid_track, number);
  has_video_ = true;

  return vid_track->number();
}

uint64 Segment::AddAudioTrack(int32 sample_rate, int32 channels) {
  return AddAudioTrack(sample_rate, channels, 0);
}

uint64 Segment::AddAudioTrack(int32 sample_rate,
                              int32 channels,
                              int32 number) {
  AudioTrack* const aud_track = new (std::nothrow) AudioTrack();
  if (!aud_track)
    return 0;

  aud_track->set_type(Tracks::kAudio);
  aud_track->set_codec_id(Tracks::kVorbisCodecId);
  aud_track->set_sample_rate(sample_rate);
  aud_track->set_channels(channels);

  tracks_.AddTrack(aud_track, number);

  return aud_track->number();
}

bool Segment::AddFrame(uint8* frame,
                       uint64 length,
                       uint64 track_number,
                       uint64 timestamp,
                       bool is_key) {
  assert(frame);

  if (!CheckHeaderInfo())
    return false;

  // If the segment has a video track hold onto audio frames to make sure the
  // audio that is associated with the start time of a video key-frame is
  // muxed into the same cluster.
  if (has_video_ && tracks_.TrackIsAudio(track_number)) {
    Frame* const new_frame = new Frame();
    if (!new_frame->Init(frame, length))
      return false;
    new_frame->set_track_number(track_number);
    new_frame->set_timestamp(timestamp);
    new_frame->set_is_key(is_key);

    if (!QueueFrame(new_frame))
      return false;

    return true;
  }

  // Check to see if the muxer needs to start a new cluster.
  if (is_key && tracks_.TrackIsVideo(track_number)) {
    new_cluster_ = true;
  } else if (cluster_list_size_ > 0) {
    const Cluster* const cluster = cluster_list_[cluster_list_size_-1];
    assert(cluster);
    const uint64 cluster_ts =
        cluster->timecode() * segment_info_.timecode_scale();

    if (max_cluster_duration_ > 0 &&
        (timestamp - cluster_ts) >= max_cluster_duration_) {
      new_cluster_ = true;
    } else if (max_cluster_size_ > 0 && cluster_list_size_ > 0) {
      if (cluster->payload_size() >= max_cluster_size_) {
        new_cluster_ = true;
      }
    }
  }

  if (new_cluster_) {
    const int32 new_size = cluster_list_size_ + 1;

    if (new_size > cluster_list_capacity_) {
      // Add more clusters.
      const int32 new_capacity =
          (!cluster_list_capacity_) ? 2 : cluster_list_capacity_ * 2;

      assert(new_capacity > 0);
      Cluster** const clusters = new (std::nothrow) Cluster*[new_capacity];
      if (!clusters)
        return false;

      for (int32 i = 0; i < cluster_list_size_; ++i) {
        clusters[i] = cluster_list_[i];
      }

      delete [] cluster_list_;

      cluster_list_ = clusters;
      cluster_list_capacity_ = new_capacity;
    }

    if (!WriteFramesLessThan(timestamp))
      return false;

    uint64 audio_timecode = 0;
    uint64 timecode = timestamp / segment_info_.timecode_scale();
    if (frames_size_ > 0) {
      audio_timecode =
          frames_[0]->timestamp() / segment_info_.timecode_scale();

      // Update the cluster's timecode to match the first audio frame.
      if (audio_timecode < timecode)
        timecode = audio_timecode;
    }

    // TODO(fgalligan): Add checks here to make sure the timestamps passed in
    // are valid.

    cluster_list_[cluster_list_size_] = new (std::nothrow) Cluster(timecode,
                                                                   writer_);
    if (!cluster_list_[cluster_list_size_])
      return false;
    cluster_list_size_ = new_size;

    if (mode_ == kFile) {
      if (cluster_list_size_ > 1) {
        // Update old cluster's size
        Cluster* const old_cluster = cluster_list_[cluster_list_size_-2];
        assert(old_cluster);

        if (!old_cluster->Finalize())
          return false;
      }

      if (output_cues_)
        new_cuepoint_ = true;
    }

    new_cluster_ = false;
  }

  // Write any audio frames left.
  if (!WriteFramesAll())
    return false;

  assert(cluster_list_size_ > 0);
  Cluster* const cluster = cluster_list_[cluster_list_size_-1];
  assert(cluster);

  int64 block_timecode = timestamp / segment_info_.timecode_scale();
  block_timecode -= static_cast<int64>(cluster->timecode());
  assert(block_timecode >= 0);

  if (new_cuepoint_ && cues_track_ == track_number) {
    if (!AddCuePoint(timestamp))
      return false;
  }

  if (!cluster->AddFrame(frame,
                         length,
                         track_number,
                         static_cast<short>(block_timecode),
                         is_key))
    return false;

  if (timestamp > last_timestamp_)
    last_timestamp_ = timestamp;

  return true;
}

void Segment::OutputCues(bool output_cues) {
  output_cues_ = output_cues;
}

bool Segment::CuesTrack(uint64 track_number) {
  const Track* const track = GetTrackByNumber(track_number);
  if (!track)
    return false;

  cues_track_ = track_number;
  return true;
}

Track* Segment::GetTrackByNumber(uint64 track_number) const {
  return tracks_.GetTrackByNumber(track_number);
}

bool Segment::WriteSegmentHeader() {
  // TODO(fgalligan): Support more than one segment.
  if (!WriteEbmlHeader(writer_))
    return false;

  // Write "unknown" (-1) as segment size value. If mode is kFile, Segment
  // will write over duration when the file is finalized.
  if (SerializeInt(writer_, kMkvSegment, 4))
    return false;

  // Save for later.
  size_position_ = writer_->Position();

  // Write "unknown" (EBML coded -1) as segment size value. We need to write 8
  // bytes because if we are going to overwrite the segment size later we do
  // not know how big our segment will be.
  if (SerializeInt(writer_, kEbmlUnknownValue, 8))
    return false;

  payload_pos_ =  writer_->Position();

  if (mode_ == kFile && writer_->Seekable()) {
    // Set the duration > 0.0 so SegmentInfo will write out the duration. When
    // the muxer is done writing we will set the correct duration and have
    // SegmentInfo upadte it.
    segment_info_.set_duration(1.0);

    if (!seek_head_.Write(writer_))
      return false;
  }

  if (!seek_head_.AddSeekEntry(kMkvInfo, writer_->Position() - payload_pos_))
    return false;
  if (!segment_info_.Write(writer_))
    return false;

  if (!seek_head_.AddSeekEntry(kMkvTracks, writer_->Position() - payload_pos_))
    return false;
  if (!tracks_.Write(writer_))
    return false;
  header_written_ = true;

  return true;
}

bool Segment::CheckHeaderInfo() {
  if (!header_written_) {
    if (!WriteSegmentHeader())
      return false;

    if (!seek_head_.AddSeekEntry(kMkvCluster,
                                 writer_->Position() - payload_pos_))
      return false;

    if (output_cues_ && cues_track_ == 0) {
      // Check for a video track
      for (uint32 i = 0; i < tracks_.track_entries_size(); ++i) {
        const Track* const track = tracks_.GetTrackByIndex(i);
        assert(track);

        if (tracks_.TrackIsVideo(track->number())) {
          cues_track_ = track->number();
          break;
        }
      }

      // Set first track found
      if (cues_track_ == 0) {
        const Track* const track = tracks_.GetTrackByIndex(0);
        assert(track);
        cues_track_ = track->number();
      }
    }
  }
  return true;
}

bool Segment::AddCuePoint(uint64 timestamp) {
  assert(cluster_list_size_ > 0);
  const Cluster* const cluster = cluster_list_[cluster_list_size_-1];
  assert(cluster);

  CuePoint* const cue = new (std::nothrow) CuePoint();
  if (!cue)
    return false;

  cue->set_time(timestamp / segment_info_.timecode_scale());
  cue->set_block_number(cluster->blocks_added() + 1);
  cue->set_cluster_pos(cluster->position_for_cues() - payload_pos_);
  cue->set_track(cues_track_);
  if (!cues_.AddCue(cue))
    return false;

  new_cuepoint_ = false;
  return true;
}

bool Segment::QueueFrame(Frame* frame) {
  const int32 new_size = frames_size_ + 1;

  if (new_size > frames_capacity_) {
    // Add more frames.
    const int32 new_capacity = (!frames_capacity_) ? 2 : frames_capacity_ * 2;
    assert(new_capacity > 0);

    Frame** const frames = new (std::nothrow) Frame*[new_capacity];
    if (!frames)
      return false;

    for (int32 i = 0; i < frames_size_; ++i) {
      frames[i] = frames_[i];
    }

    delete [] frames_;
    frames_ = frames;
    frames_capacity_ = new_capacity;
  }

  frames_[frames_size_++] = frame;

  return true;
}

bool Segment::WriteFramesAll() {
  if (frames_) {
    assert(cluster_list_size_ > 0);
    Cluster* const cluster = cluster_list_[cluster_list_size_-1];
    assert(cluster);

    for (int32 i = 0; i < frames_size_; ++i) {
      Frame* const frame = frames_[i];

      int64 block_timecode =
          frame->timestamp() / segment_info_.timecode_scale();
      block_timecode -= static_cast<int64>(cluster->timecode());
      assert(block_timecode >= 0);

      if (new_cuepoint_ && cues_track_ == frame->track_number()) {
        if (!AddCuePoint(frame->timestamp()))
          return false;
      }

      if (!cluster->AddFrame(frame->frame(),
                             frame->length(),
                             frame->track_number(),
                             static_cast<short>(block_timecode),
                             frame->is_key()))
        return false;

      if (frame->timestamp() > last_timestamp_)
        last_timestamp_ = frame->timestamp();

      delete frame;
    }

    frames_size_ = 0;
  }

  return true;
}

bool Segment::WriteFramesLessThan(uint64 timestamp) {
  // Check |cluster_list_size_| to see if this is the first cluster. If it is
  // the first cluster the audio frames that are less than the first video
  // timesatmp will be written in a later step.
  if (frames_size_ > 0 && cluster_list_size_ > 0) {
    assert(frames_);
    Cluster* const cluster = cluster_list_[cluster_list_size_-1];
    assert(cluster);

    int32 shift_left = 0;

    // TODO(fgalligan): Change this to use the durations of frames instead of
    // the next frame's start time if the duration is accurate.
    for (int32 i = 1; i < frames_size_; ++i) {
      const Frame* const frame_curr = frames_[i];

      if (frame_curr->timestamp() > timestamp)
        break;

      const Frame* const frame_prev = frames_[i-1];

      int64 block_timecode =
          frame_prev->timestamp() / segment_info_.timecode_scale();
      block_timecode -= static_cast<int64>(cluster->timecode());
      assert(block_timecode >= 0);

      if (new_cuepoint_ && cues_track_ == frame_prev->track_number()) {
        if (!AddCuePoint(frame_prev->timestamp()))
          return false;
      }

      if (!cluster->AddFrame(frame_prev->frame(),
                             frame_prev->length(),
                             frame_prev->track_number(),
                             static_cast<short>(block_timecode),
                             frame_prev->is_key()))
        return false;

      ++shift_left;
      if (frame_prev->timestamp() > last_timestamp_)
        last_timestamp_ = frame_prev->timestamp();

      delete frame_prev;
    }

    if (shift_left > 0) {
      assert(shift_left < frames_size_);

      const int32 new_frames_size = frames_size_ - shift_left;
      for (int32 i = 0; i < new_frames_size; ++i) {
        frames_[i] = frames_[i+shift_left];
      }

      frames_size_ = new_frames_size;
    }
  }

  return true;
}

}  // namespace mkvmuxer
