/**
 * Autogenerated by Thrift Compiler (0.9.0-dev)
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerProfile implements org.apache.thrift.TBase<PlayerProfile, PlayerProfile._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PlayerProfile");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ROM_PROFILES_FIELD_DESC = new org.apache.thrift.protocol.TField("romProfiles", org.apache.thrift.protocol.TType.MAP, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PlayerProfileStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PlayerProfileTupleSchemeFactory());
  }

  public String id; // required
  public Map<String,PlayerRomProfile> romProfiles; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    ROM_PROFILES((short)2, "romProfiles");

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
        case 1: // ID
          return ID;
        case 2: // ROM_PROFILES
          return ROM_PROFILES;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ROM_PROFILES, new org.apache.thrift.meta_data.FieldMetaData("romProfiles", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PlayerRomProfile.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PlayerProfile.class, metaDataMap);
  }

  public PlayerProfile() {
    this.romProfiles = new HashMap<String,PlayerRomProfile>();

  }

  public PlayerProfile(
    String id,
    Map<String,PlayerRomProfile> romProfiles)
  {
    this();
    this.id = id;
    this.romProfiles = romProfiles;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PlayerProfile(PlayerProfile other) {
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetRomProfiles()) {
      Map<String,PlayerRomProfile> __this__romProfiles = new HashMap<String,PlayerRomProfile>();
      for (Map.Entry<String, PlayerRomProfile> other_element : other.romProfiles.entrySet()) {

        String other_element_key = other_element.getKey();
        PlayerRomProfile other_element_value = other_element.getValue();

        String __this__romProfiles_copy_key = other_element_key;

        PlayerRomProfile __this__romProfiles_copy_value = new PlayerRomProfile(other_element_value);

        __this__romProfiles.put(__this__romProfiles_copy_key, __this__romProfiles_copy_value);
      }
      this.romProfiles = __this__romProfiles;
    }
  }

  public PlayerProfile deepCopy() {
    return new PlayerProfile(this);
  }

  @Override
  public void clear() {
    this.id = null;
    this.romProfiles = new HashMap<String,PlayerRomProfile>();

  }

  public String getId() {
    return this.id;
  }

  public PlayerProfile setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
    }
  }

  public int getRomProfilesSize() {
    return (this.romProfiles == null) ? 0 : this.romProfiles.size();
  }

  public void putToRomProfiles(String key, PlayerRomProfile val) {
    if (this.romProfiles == null) {
      this.romProfiles = new HashMap<String,PlayerRomProfile>();
    }
    this.romProfiles.put(key, val);
  }

  public Map<String,PlayerRomProfile> getRomProfiles() {
    return this.romProfiles;
  }

  public PlayerProfile setRomProfiles(Map<String,PlayerRomProfile> romProfiles) {
    this.romProfiles = romProfiles;
    return this;
  }

  public void unsetRomProfiles() {
    this.romProfiles = null;
  }

  /** Returns true if field romProfiles is set (has been assigned a value) and false otherwise */
  public boolean isSetRomProfiles() {
    return this.romProfiles != null;
  }

  public void setRomProfilesIsSet(boolean value) {
    if (!value) {
      this.romProfiles = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    case ROM_PROFILES:
      if (value == null) {
        unsetRomProfiles();
      } else {
        setRomProfiles((Map<String,PlayerRomProfile>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case ROM_PROFILES:
      return getRomProfiles();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case ROM_PROFILES:
      return isSetRomProfiles();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PlayerProfile)
      return this.equals((PlayerProfile)that);
    return false;
  }

  public boolean equals(PlayerProfile that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
        return false;
    }

    boolean this_present_romProfiles = true && this.isSetRomProfiles();
    boolean that_present_romProfiles = true && that.isSetRomProfiles();
    if (this_present_romProfiles || that_present_romProfiles) {
      if (!(this_present_romProfiles && that_present_romProfiles))
        return false;
      if (!this.romProfiles.equals(that.romProfiles))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(PlayerProfile other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    PlayerProfile typedOther = (PlayerProfile)other;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(typedOther.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, typedOther.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRomProfiles()).compareTo(typedOther.isSetRomProfiles());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRomProfiles()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.romProfiles, typedOther.romProfiles);
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
    StringBuilder sb = new StringBuilder("PlayerProfile(");
    boolean first = true;

    sb.append("id:");
    if (this.id == null) {
      sb.append("null");
    } else {
      sb.append(this.id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("romProfiles:");
    if (this.romProfiles == null) {
      sb.append("null");
    } else {
      sb.append(this.romProfiles);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PlayerProfileStandardSchemeFactory implements SchemeFactory {
    public PlayerProfileStandardScheme getScheme() {
      return new PlayerProfileStandardScheme();
    }
  }

  private static class PlayerProfileStandardScheme extends StandardScheme<PlayerProfile> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PlayerProfile struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.id = iprot.readString();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ROM_PROFILES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map18 = iprot.readMapBegin();
                struct.romProfiles = new HashMap<String,PlayerRomProfile>(2*_map18.size);
                for (int _i19 = 0; _i19 < _map18.size; ++_i19)
                {
                  String _key20; // required
                  PlayerRomProfile _val21; // required
                  _key20 = iprot.readString();
                  _val21 = new PlayerRomProfile();
                  _val21.read(iprot);
                  struct.romProfiles.put(_key20, _val21);
                }
                iprot.readMapEnd();
              }
              struct.setRomProfilesIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PlayerProfile struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.id != null) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeString(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.romProfiles != null) {
        oprot.writeFieldBegin(ROM_PROFILES_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.romProfiles.size()));
          for (Map.Entry<String, PlayerRomProfile> _iter22 : struct.romProfiles.entrySet())
          {
            oprot.writeString(_iter22.getKey());
            _iter22.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PlayerProfileTupleSchemeFactory implements SchemeFactory {
    public PlayerProfileTupleScheme getScheme() {
      return new PlayerProfileTupleScheme();
    }
  }

  private static class PlayerProfileTupleScheme extends TupleScheme<PlayerProfile> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PlayerProfile struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetRomProfiles()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetId()) {
        oprot.writeString(struct.id);
      }
      if (struct.isSetRomProfiles()) {
        {
          oprot.writeI32(struct.romProfiles.size());
          for (Map.Entry<String, PlayerRomProfile> _iter23 : struct.romProfiles.entrySet())
          {
            oprot.writeString(_iter23.getKey());
            _iter23.getValue().write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PlayerProfile struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.id = iprot.readString();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TMap _map24 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.romProfiles = new HashMap<String,PlayerRomProfile>(2*_map24.size);
          for (int _i25 = 0; _i25 < _map24.size; ++_i25)
          {
            String _key26; // required
            PlayerRomProfile _val27; // required
            _key26 = iprot.readString();
            _val27 = new PlayerRomProfile();
            _val27.read(iprot);
            struct.romProfiles.put(_key26, _val27);
          }
        }
        struct.setRomProfilesIsSet(true);
      }
    }
  }

}

