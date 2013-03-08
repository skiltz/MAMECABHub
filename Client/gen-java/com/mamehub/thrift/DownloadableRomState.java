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

public class DownloadableRomState implements org.apache.thrift.TBase<DownloadableRomState, DownloadableRomState._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DownloadableRomState");

  private static final org.apache.thrift.protocol.TField ROMS_FIELD_DESC = new org.apache.thrift.protocol.TField("roms", org.apache.thrift.protocol.TType.MAP, (short)1);
  private static final org.apache.thrift.protocol.TField STALE_FIELD_DESC = new org.apache.thrift.protocol.TField("stale", org.apache.thrift.protocol.TType.BOOL, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DownloadableRomStateStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DownloadableRomStateTupleSchemeFactory());
  }

  public Map<String,Set<String>> roms; // required
  public boolean stale; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ROMS((short)1, "roms"),
    STALE((short)2, "stale");

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
        case 1: // ROMS
          return ROMS;
        case 2: // STALE
          return STALE;
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
  private static final int __STALE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ROMS, new org.apache.thrift.meta_data.FieldMetaData("roms", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)))));
    tmpMap.put(_Fields.STALE, new org.apache.thrift.meta_data.FieldMetaData("stale", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DownloadableRomState.class, metaDataMap);
  }

  public DownloadableRomState() {
    this.roms = new HashMap<String,Set<String>>();

    this.stale = false;

  }

  public DownloadableRomState(
    Map<String,Set<String>> roms,
    boolean stale)
  {
    this();
    this.roms = roms;
    this.stale = stale;
    setStaleIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DownloadableRomState(DownloadableRomState other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetRoms()) {
      Map<String,Set<String>> __this__roms = new HashMap<String,Set<String>>();
      for (Map.Entry<String, Set<String>> other_element : other.roms.entrySet()) {

        String other_element_key = other_element.getKey();
        Set<String> other_element_value = other_element.getValue();

        String __this__roms_copy_key = other_element_key;

        Set<String> __this__roms_copy_value = new HashSet<String>();
        for (String other_element_value_element : other_element_value) {
          __this__roms_copy_value.add(other_element_value_element);
        }

        __this__roms.put(__this__roms_copy_key, __this__roms_copy_value);
      }
      this.roms = __this__roms;
    }
    this.stale = other.stale;
  }

  public DownloadableRomState deepCopy() {
    return new DownloadableRomState(this);
  }

  @Override
  public void clear() {
    this.roms = new HashMap<String,Set<String>>();

    this.stale = false;

  }

  public int getRomsSize() {
    return (this.roms == null) ? 0 : this.roms.size();
  }

  public void putToRoms(String key, Set<String> val) {
    if (this.roms == null) {
      this.roms = new HashMap<String,Set<String>>();
    }
    this.roms.put(key, val);
  }

  public Map<String,Set<String>> getRoms() {
    return this.roms;
  }

  public DownloadableRomState setRoms(Map<String,Set<String>> roms) {
    this.roms = roms;
    return this;
  }

  public void unsetRoms() {
    this.roms = null;
  }

  /** Returns true if field roms is set (has been assigned a value) and false otherwise */
  public boolean isSetRoms() {
    return this.roms != null;
  }

  public void setRomsIsSet(boolean value) {
    if (!value) {
      this.roms = null;
    }
  }

  public boolean isStale() {
    return this.stale;
  }

  public DownloadableRomState setStale(boolean stale) {
    this.stale = stale;
    setStaleIsSet(true);
    return this;
  }

  public void unsetStale() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STALE_ISSET_ID);
  }

  /** Returns true if field stale is set (has been assigned a value) and false otherwise */
  public boolean isSetStale() {
    return EncodingUtils.testBit(__isset_bitfield, __STALE_ISSET_ID);
  }

  public void setStaleIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STALE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ROMS:
      if (value == null) {
        unsetRoms();
      } else {
        setRoms((Map<String,Set<String>>)value);
      }
      break;

    case STALE:
      if (value == null) {
        unsetStale();
      } else {
        setStale((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ROMS:
      return getRoms();

    case STALE:
      return Boolean.valueOf(isStale());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ROMS:
      return isSetRoms();
    case STALE:
      return isSetStale();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DownloadableRomState)
      return this.equals((DownloadableRomState)that);
    return false;
  }

  public boolean equals(DownloadableRomState that) {
    if (that == null)
      return false;

    boolean this_present_roms = true && this.isSetRoms();
    boolean that_present_roms = true && that.isSetRoms();
    if (this_present_roms || that_present_roms) {
      if (!(this_present_roms && that_present_roms))
        return false;
      if (!this.roms.equals(that.roms))
        return false;
    }

    boolean this_present_stale = true;
    boolean that_present_stale = true;
    if (this_present_stale || that_present_stale) {
      if (!(this_present_stale && that_present_stale))
        return false;
      if (this.stale != that.stale)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(DownloadableRomState other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    DownloadableRomState typedOther = (DownloadableRomState)other;

    lastComparison = Boolean.valueOf(isSetRoms()).compareTo(typedOther.isSetRoms());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRoms()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.roms, typedOther.roms);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStale()).compareTo(typedOther.isSetStale());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStale()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.stale, typedOther.stale);
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
    StringBuilder sb = new StringBuilder("DownloadableRomState(");
    boolean first = true;

    sb.append("roms:");
    if (this.roms == null) {
      sb.append("null");
    } else {
      sb.append(this.roms);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("stale:");
    sb.append(this.stale);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class DownloadableRomStateStandardSchemeFactory implements SchemeFactory {
    public DownloadableRomStateStandardScheme getScheme() {
      return new DownloadableRomStateStandardScheme();
    }
  }

  private static class DownloadableRomStateStandardScheme extends StandardScheme<DownloadableRomState> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DownloadableRomState struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ROMS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map84 = iprot.readMapBegin();
                struct.roms = new HashMap<String,Set<String>>(2*_map84.size);
                for (int _i85 = 0; _i85 < _map84.size; ++_i85)
                {
                  String _key86; // required
                  Set<String> _val87; // required
                  _key86 = iprot.readString();
                  {
                    org.apache.thrift.protocol.TSet _set88 = iprot.readSetBegin();
                    _val87 = new HashSet<String>(2*_set88.size);
                    for (int _i89 = 0; _i89 < _set88.size; ++_i89)
                    {
                      String _elem90; // optional
                      _elem90 = iprot.readString();
                      _val87.add(_elem90);
                    }
                    iprot.readSetEnd();
                  }
                  struct.roms.put(_key86, _val87);
                }
                iprot.readMapEnd();
              }
              struct.setRomsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // STALE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.stale = iprot.readBool();
              struct.setStaleIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DownloadableRomState struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.roms != null) {
        oprot.writeFieldBegin(ROMS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.SET, struct.roms.size()));
          for (Map.Entry<String, Set<String>> _iter91 : struct.roms.entrySet())
          {
            oprot.writeString(_iter91.getKey());
            {
              oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, _iter91.getValue().size()));
              for (String _iter92 : _iter91.getValue())
              {
                oprot.writeString(_iter92);
              }
              oprot.writeSetEnd();
            }
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STALE_FIELD_DESC);
      oprot.writeBool(struct.stale);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DownloadableRomStateTupleSchemeFactory implements SchemeFactory {
    public DownloadableRomStateTupleScheme getScheme() {
      return new DownloadableRomStateTupleScheme();
    }
  }

  private static class DownloadableRomStateTupleScheme extends TupleScheme<DownloadableRomState> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DownloadableRomState struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetRoms()) {
        optionals.set(0);
      }
      if (struct.isSetStale()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetRoms()) {
        {
          oprot.writeI32(struct.roms.size());
          for (Map.Entry<String, Set<String>> _iter93 : struct.roms.entrySet())
          {
            oprot.writeString(_iter93.getKey());
            {
              oprot.writeI32(_iter93.getValue().size());
              for (String _iter94 : _iter93.getValue())
              {
                oprot.writeString(_iter94);
              }
            }
          }
        }
      }
      if (struct.isSetStale()) {
        oprot.writeBool(struct.stale);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DownloadableRomState struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TMap _map95 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.SET, iprot.readI32());
          struct.roms = new HashMap<String,Set<String>>(2*_map95.size);
          for (int _i96 = 0; _i96 < _map95.size; ++_i96)
          {
            String _key97; // required
            Set<String> _val98; // required
            _key97 = iprot.readString();
            {
              org.apache.thrift.protocol.TSet _set99 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
              _val98 = new HashSet<String>(2*_set99.size);
              for (int _i100 = 0; _i100 < _set99.size; ++_i100)
              {
                String _elem101; // optional
                _elem101 = iprot.readString();
                _val98.add(_elem101);
              }
            }
            struct.roms.put(_key97, _val98);
          }
        }
        struct.setRomsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.stale = iprot.readBool();
        struct.setStaleIsSet(true);
      }
    }
  }

}

