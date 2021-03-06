/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.mamehub.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (1.0.0-dev)", date = "2013-12-10")
public class Message implements org.apache.thrift.TBase<Message, Message._Fields>, java.io.Serializable, Cloneable, Comparable<Message> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Message");

  private static final org.apache.thrift.protocol.TField TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("timestamp", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SOURCE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sourceId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CHAT_FIELD_DESC = new org.apache.thrift.protocol.TField("chat", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PLAYER_CHANGED_FIELD_DESC = new org.apache.thrift.protocol.TField("playerChanged", org.apache.thrift.protocol.TType.STRUCT, (short)7);
  private static final org.apache.thrift.protocol.TField GAME_CHANGED_FIELD_DESC = new org.apache.thrift.protocol.TField("gameChanged", org.apache.thrift.protocol.TType.STRUCT, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MessageStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MessageTupleSchemeFactory());
  }

  public long timestamp; // required
  public String sourceId; // required
  public String chat; // required
  public Player playerChanged; // required
  public Game gameChanged; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TIMESTAMP((short)1, "timestamp"),
    SOURCE_ID((short)2, "sourceId"),
    CHAT((short)3, "chat"),
    PLAYER_CHANGED((short)7, "playerChanged"),
    GAME_CHANGED((short)8, "gameChanged");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TIMESTAMP
          return TIMESTAMP;
        case 2: // SOURCE_ID
          return SOURCE_ID;
        case 3: // CHAT
          return CHAT;
        case 7: // PLAYER_CHANGED
          return PLAYER_CHANGED;
        case 8: // GAME_CHANGED
          return GAME_CHANGED;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TIMESTAMP_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("timestamp", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SOURCE_ID, new org.apache.thrift.meta_data.FieldMetaData("sourceId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CHAT, new org.apache.thrift.meta_data.FieldMetaData("chat", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PLAYER_CHANGED, new org.apache.thrift.meta_data.FieldMetaData("playerChanged", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Player.class)));
    tmpMap.put(_Fields.GAME_CHANGED, new org.apache.thrift.meta_data.FieldMetaData("gameChanged", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Game.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Message.class, metaDataMap);
  }

  public Message() {
  }

  public Message(
    long timestamp,
    String sourceId,
    String chat,
    Player playerChanged,
    Game gameChanged)
  {
    this();
    this.timestamp = timestamp;
    setTimestampIsSet(true);
    this.sourceId = sourceId;
    this.chat = chat;
    this.playerChanged = playerChanged;
    this.gameChanged = gameChanged;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Message(Message other) {
    __isset_bitfield = other.__isset_bitfield;
    this.timestamp = other.timestamp;
    if (other.isSetSourceId()) {
      this.sourceId = other.sourceId;
    }
    if (other.isSetChat()) {
      this.chat = other.chat;
    }
    if (other.isSetPlayerChanged()) {
      this.playerChanged = new Player(other.playerChanged);
    }
    if (other.isSetGameChanged()) {
      this.gameChanged = new Game(other.gameChanged);
    }
  }

  public Message deepCopy() {
    return new Message(this);
  }

  @Override
  public void clear() {
    setTimestampIsSet(false);
    this.timestamp = 0;
    this.sourceId = null;
    this.chat = null;
    this.playerChanged = null;
    this.gameChanged = null;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public Message setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    setTimestampIsSet(true);
    return this;
  }

  public void unsetTimestamp() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TIMESTAMP_ISSET_ID);
  }

  /** Returns true if field timestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetTimestamp() {
    return EncodingUtils.testBit(__isset_bitfield, __TIMESTAMP_ISSET_ID);
  }

  public void setTimestampIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TIMESTAMP_ISSET_ID, value);
  }

  public String getSourceId() {
    return this.sourceId;
  }

  public Message setSourceId(String sourceId) {
    this.sourceId = sourceId;
    return this;
  }

  public void unsetSourceId() {
    this.sourceId = null;
  }

  /** Returns true if field sourceId is set (has been assigned a value) and false otherwise */
  public boolean isSetSourceId() {
    return this.sourceId != null;
  }

  public void setSourceIdIsSet(boolean value) {
    if (!value) {
      this.sourceId = null;
    }
  }

  public String getChat() {
    return this.chat;
  }

  public Message setChat(String chat) {
    this.chat = chat;
    return this;
  }

  public void unsetChat() {
    this.chat = null;
  }

  /** Returns true if field chat is set (has been assigned a value) and false otherwise */
  public boolean isSetChat() {
    return this.chat != null;
  }

  public void setChatIsSet(boolean value) {
    if (!value) {
      this.chat = null;
    }
  }

  public Player getPlayerChanged() {
    return this.playerChanged;
  }

  public Message setPlayerChanged(Player playerChanged) {
    this.playerChanged = playerChanged;
    return this;
  }

  public void unsetPlayerChanged() {
    this.playerChanged = null;
  }

  /** Returns true if field playerChanged is set (has been assigned a value) and false otherwise */
  public boolean isSetPlayerChanged() {
    return this.playerChanged != null;
  }

  public void setPlayerChangedIsSet(boolean value) {
    if (!value) {
      this.playerChanged = null;
    }
  }

  public Game getGameChanged() {
    return this.gameChanged;
  }

  public Message setGameChanged(Game gameChanged) {
    this.gameChanged = gameChanged;
    return this;
  }

  public void unsetGameChanged() {
    this.gameChanged = null;
  }

  /** Returns true if field gameChanged is set (has been assigned a value) and false otherwise */
  public boolean isSetGameChanged() {
    return this.gameChanged != null;
  }

  public void setGameChangedIsSet(boolean value) {
    if (!value) {
      this.gameChanged = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TIMESTAMP:
      if (value == null) {
        unsetTimestamp();
      } else {
        setTimestamp((Long)value);
      }
      break;

    case SOURCE_ID:
      if (value == null) {
        unsetSourceId();
      } else {
        setSourceId((String)value);
      }
      break;

    case CHAT:
      if (value == null) {
        unsetChat();
      } else {
        setChat((String)value);
      }
      break;

    case PLAYER_CHANGED:
      if (value == null) {
        unsetPlayerChanged();
      } else {
        setPlayerChanged((Player)value);
      }
      break;

    case GAME_CHANGED:
      if (value == null) {
        unsetGameChanged();
      } else {
        setGameChanged((Game)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TIMESTAMP:
      return Long.valueOf(getTimestamp());

    case SOURCE_ID:
      return getSourceId();

    case CHAT:
      return getChat();

    case PLAYER_CHANGED:
      return getPlayerChanged();

    case GAME_CHANGED:
      return getGameChanged();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TIMESTAMP:
      return isSetTimestamp();
    case SOURCE_ID:
      return isSetSourceId();
    case CHAT:
      return isSetChat();
    case PLAYER_CHANGED:
      return isSetPlayerChanged();
    case GAME_CHANGED:
      return isSetGameChanged();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Message)
      return this.equals((Message)that);
    return false;
  }

  public boolean equals(Message that) {
    if (that == null)
      return false;

    boolean this_present_timestamp = true;
    boolean that_present_timestamp = true;
    if (this_present_timestamp || that_present_timestamp) {
      if (!(this_present_timestamp && that_present_timestamp))
        return false;
      if (this.timestamp != that.timestamp)
        return false;
    }

    boolean this_present_sourceId = true && this.isSetSourceId();
    boolean that_present_sourceId = true && that.isSetSourceId();
    if (this_present_sourceId || that_present_sourceId) {
      if (!(this_present_sourceId && that_present_sourceId))
        return false;
      if (!this.sourceId.equals(that.sourceId))
        return false;
    }

    boolean this_present_chat = true && this.isSetChat();
    boolean that_present_chat = true && that.isSetChat();
    if (this_present_chat || that_present_chat) {
      if (!(this_present_chat && that_present_chat))
        return false;
      if (!this.chat.equals(that.chat))
        return false;
    }

    boolean this_present_playerChanged = true && this.isSetPlayerChanged();
    boolean that_present_playerChanged = true && that.isSetPlayerChanged();
    if (this_present_playerChanged || that_present_playerChanged) {
      if (!(this_present_playerChanged && that_present_playerChanged))
        return false;
      if (!this.playerChanged.equals(that.playerChanged))
        return false;
    }

    boolean this_present_gameChanged = true && this.isSetGameChanged();
    boolean that_present_gameChanged = true && that.isSetGameChanged();
    if (this_present_gameChanged || that_present_gameChanged) {
      if (!(this_present_gameChanged && that_present_gameChanged))
        return false;
      if (!this.gameChanged.equals(that.gameChanged))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(Message other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTimestamp()).compareTo(other.isSetTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.timestamp, other.timestamp);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSourceId()).compareTo(other.isSetSourceId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSourceId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sourceId, other.sourceId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChat()).compareTo(other.isSetChat());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChat()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.chat, other.chat);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlayerChanged()).compareTo(other.isSetPlayerChanged());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlayerChanged()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.playerChanged, other.playerChanged);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGameChanged()).compareTo(other.isSetGameChanged());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGameChanged()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.gameChanged, other.gameChanged);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Message(");
    boolean first = true;

    sb.append("timestamp:");
    sb.append(this.timestamp);
    first = false;
    if (!first) sb.append(", ");
    sb.append("sourceId:");
    if (this.sourceId == null) {
      sb.append("null");
    } else {
      sb.append(this.sourceId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("chat:");
    if (this.chat == null) {
      sb.append("null");
    } else {
      sb.append(this.chat);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("playerChanged:");
    if (this.playerChanged == null) {
      sb.append("null");
    } else {
      sb.append(this.playerChanged);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("gameChanged:");
    if (this.gameChanged == null) {
      sb.append("null");
    } else {
      sb.append(this.gameChanged);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (playerChanged != null) {
      playerChanged.validate();
    }
    if (gameChanged != null) {
      gameChanged.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MessageStandardSchemeFactory implements SchemeFactory {
    public MessageStandardScheme getScheme() {
      return new MessageStandardScheme();
    }
  }

  private static class MessageStandardScheme extends StandardScheme<Message> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Message struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.timestamp = iprot.readI64();
              struct.setTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SOURCE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sourceId = iprot.readString();
              struct.setSourceIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CHAT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.chat = iprot.readString();
              struct.setChatIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // PLAYER_CHANGED
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.playerChanged = new Player();
              struct.playerChanged.read(iprot);
              struct.setPlayerChangedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // GAME_CHANGED
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.gameChanged = new Game();
              struct.gameChanged.read(iprot);
              struct.setGameChangedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Message struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(TIMESTAMP_FIELD_DESC);
      oprot.writeI64(struct.timestamp);
      oprot.writeFieldEnd();
      if (struct.sourceId != null) {
        oprot.writeFieldBegin(SOURCE_ID_FIELD_DESC);
        oprot.writeString(struct.sourceId);
        oprot.writeFieldEnd();
      }
      if (struct.chat != null) {
        oprot.writeFieldBegin(CHAT_FIELD_DESC);
        oprot.writeString(struct.chat);
        oprot.writeFieldEnd();
      }
      if (struct.playerChanged != null) {
        oprot.writeFieldBegin(PLAYER_CHANGED_FIELD_DESC);
        struct.playerChanged.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.gameChanged != null) {
        oprot.writeFieldBegin(GAME_CHANGED_FIELD_DESC);
        struct.gameChanged.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MessageTupleSchemeFactory implements SchemeFactory {
    public MessageTupleScheme getScheme() {
      return new MessageTupleScheme();
    }
  }

  private static class MessageTupleScheme extends TupleScheme<Message> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Message struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTimestamp()) {
        optionals.set(0);
      }
      if (struct.isSetSourceId()) {
        optionals.set(1);
      }
      if (struct.isSetChat()) {
        optionals.set(2);
      }
      if (struct.isSetPlayerChanged()) {
        optionals.set(3);
      }
      if (struct.isSetGameChanged()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetTimestamp()) {
        oprot.writeI64(struct.timestamp);
      }
      if (struct.isSetSourceId()) {
        oprot.writeString(struct.sourceId);
      }
      if (struct.isSetChat()) {
        oprot.writeString(struct.chat);
      }
      if (struct.isSetPlayerChanged()) {
        struct.playerChanged.write(oprot);
      }
      if (struct.isSetGameChanged()) {
        struct.gameChanged.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Message struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.timestamp = iprot.readI64();
        struct.setTimestampIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sourceId = iprot.readString();
        struct.setSourceIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.chat = iprot.readString();
        struct.setChatIsSet(true);
      }
      if (incoming.get(3)) {
        struct.playerChanged = new Player();
        struct.playerChanged.read(iprot);
        struct.setPlayerChangedIsSet(true);
      }
      if (incoming.get(4)) {
        struct.gameChanged = new Game();
        struct.gameChanged.read(iprot);
        struct.setGameChangedIsSet(true);
      }
    }
  }

}

