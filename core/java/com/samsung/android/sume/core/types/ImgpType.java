package com.samsung.android.sume.core.types;

/* loaded from: classes4.dex */
public enum ImgpType implements NumericEnum {
  ANY(0),
  RESIZE(1),
  CVT_COLOR(2),
  CVT_DATA(3),
  CVT_GAMUT(4),
  CVT_HDR2SDR(5),
  ROTATE(6),
  CROP(7),
  SPLIT(8),
  MERGE(9),
  QUALITY_MEASURE(10),
  DECODE(11),
  ENCODE(12),
  ENCODE_HDR(13),
  FLIP(14),
  CREATE_GAINMAP(15);

  private final int value;

  ImgpType(int value) {
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
