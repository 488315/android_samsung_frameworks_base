package com.android.framework.protobuf;

import java.io.IOException;

@CheckReturnValue
/* loaded from: classes4.dex */
abstract class UnknownFieldSchema<T, B> {
  abstract void addFixed32(B b, int i, int i2);

  abstract void addFixed64(B b, int i, long j);

  abstract void addGroup(B b, int i, T t);

  abstract void addLengthDelimited(B b, int i, ByteString byteString);

  abstract void addVarint(B b, int i, long j);

  abstract B getBuilderFromMessage(Object obj);

  abstract T getFromMessage(Object obj);

  abstract int getSerializedSize(T t);

  abstract int getSerializedSizeAsMessageSet(T t);

  abstract void makeImmutable(Object obj);

  abstract T merge(T t, T t2);

  abstract B newBuilder();

  abstract void setBuilderToMessage(Object obj, B b);

  abstract void setToMessage(Object obj, T t);

  abstract boolean shouldDiscardUnknownFields(Reader reader);

  abstract T toImmutable(B b);

  abstract void writeAsMessageSetTo(T t, Writer writer) throws IOException;

  abstract void writeTo(T t, Writer writer) throws IOException;

  UnknownFieldSchema() {}

  final boolean mergeOneFieldFrom(B unknownFields, Reader reader) throws IOException {
    int tag = reader.getTag();
    int fieldNumber = WireFormat.getTagFieldNumber(tag);
    switch (WireFormat.getTagWireType(tag)) {
      case 0:
        addVarint(unknownFields, fieldNumber, reader.readInt64());
        return true;
      case 1:
        addFixed64(unknownFields, fieldNumber, reader.readFixed64());
        return true;
      case 2:
        addLengthDelimited(unknownFields, fieldNumber, reader.readBytes());
        return true;
      case 3:
        B subFields = newBuilder();
        int endGroupTag = WireFormat.makeTag(fieldNumber, 4);
        mergeFrom(subFields, reader);
        if (endGroupTag != reader.getTag()) {
          throw InvalidProtocolBufferException.invalidEndTag();
        }
        addGroup(unknownFields, fieldNumber, toImmutable(subFields));
        return true;
      case 4:
        return false;
      case 5:
        addFixed32(unknownFields, fieldNumber, reader.readFixed32());
        return true;
      default:
        throw InvalidProtocolBufferException.invalidWireType();
    }
  }

  final void mergeFrom(B unknownFields, Reader reader) throws IOException {
    while (reader.getFieldNumber() != Integer.MAX_VALUE
        && mergeOneFieldFrom(unknownFields, reader)) {}
  }
}
