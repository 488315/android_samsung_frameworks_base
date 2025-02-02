package com.android.framework.protobuf;


/* loaded from: classes4.dex */
public abstract class ExtensionLite<ContainingType extends MessageLite, Type> {
  public abstract Type getDefaultValue();

  public abstract WireFormat.FieldType getLiteType();

  public abstract MessageLite getMessageDefaultInstance();

  public abstract int getNumber();

  public abstract boolean isRepeated();

  boolean isLite() {
    return true;
  }
}
