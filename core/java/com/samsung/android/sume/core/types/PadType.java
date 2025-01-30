package com.samsung.android.sume.core.types;

/* loaded from: classes4.dex */
public enum PadType implements NumericEnum {
  NONE(0),
  OVERLAP(1);

  private final int value;

  PadType(int value) {
    this.value = value;
  }

  @Override // com.samsung.android.sume.core.types.NumericEnum
  public int getValue() {
    return this.value;
  }

  @Override // com.samsung.android.sume.core.types.NumericEnum
  public String stringfy() {
    return name() + ":" + this.value;
  }
}
