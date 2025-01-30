package com.samsung.android.sume.core.types;

/* loaded from: classes4.dex */
public enum SplitType implements NumericEnum {
  NONE(0),
  CHANNELS(1),
  PLANES(2),
  ALPHA(3),
  TILE(4);

  private final int value;

  SplitType(int value) {
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
