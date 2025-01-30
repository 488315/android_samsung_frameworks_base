package com.samsung.android.sume.core.types;

import android.graphics.Bitmap;
import android.security.keystore.KeyProperties;
import com.samsung.android.sume.core.Def;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'BT601_LR' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes4.dex */
public final class ColorSpace implements NumericEnum {
  private static final /* synthetic */ ColorSpace[] $VALUES;
  public static final ColorSpace BT2020;
  public static final ColorSpace BT2020_FR;
  public static final ColorSpace BT2020_LR;
  public static final ColorSpace BT601;
  public static final ColorSpace BT601_FR;
  public static final ColorSpace BT601_LR;
  public static final ColorSpace BT709;
  public static final ColorSpace BT709_FR;
  public static final ColorSpace BT709_LR;
  static final int CS_RANGE_SHIFT = 4;
  static final int CS_SET_MASK = 15;
  private static final int CS_SET_MAX = 16;
  public static final ColorSpace DISPLAY_P3;
  public static final ColorSpace NONE = new ColorSpace(KeyProperties.DIGEST_NONE, 0, 0);
  public static final ColorSpace SRGB;
  private final int value;

  private static /* synthetic */ ColorSpace[] $values() {
    return new ColorSpace[] {
      NONE,
      BT601,
      BT709,
      BT2020,
      SRGB,
      DISPLAY_P3,
      BT601_LR,
      BT601_FR,
      BT709_LR,
      BT709_FR,
      BT2020_LR,
      BT2020_FR
    };
  }

  public static ColorSpace valueOf(String name) {
    return (ColorSpace) Enum.valueOf(ColorSpace.class, name);
  }

  public static ColorSpace[] values() {
    return (ColorSpace[]) $VALUES.clone();
  }

  static {
    ColorSpace colorSpace = new ColorSpace("BT601", 1, 1);
    BT601 = colorSpace;
    ColorSpace colorSpace2 = new ColorSpace("BT709", 2, 2);
    BT709 = colorSpace2;
    ColorSpace colorSpace3 = new ColorSpace("BT2020", 3, 3);
    BT2020 = colorSpace3;
    SRGB = new ColorSpace("SRGB", 4, 4);
    DISPLAY_P3 = new ColorSpace("DISPLAY_P3", 5, 5);
    BT601_LR = new ColorSpace("BT601_LR", 6, makeColorSpace(colorSpace, ColorSpaceRange.LIMITED));
    BT601_FR = new ColorSpace("BT601_FR", 7, makeColorSpace(colorSpace, ColorSpaceRange.FULL));
    BT709_LR = new ColorSpace("BT709_LR", 8, makeColorSpace(colorSpace2, ColorSpaceRange.LIMITED));
    BT709_FR = new ColorSpace("BT709_FR", 9, makeColorSpace(colorSpace2, ColorSpaceRange.FULL));
    BT2020_LR =
        new ColorSpace("BT2020_LR", 10, makeColorSpace(colorSpace3, ColorSpaceRange.LIMITED));
    BT2020_FR = new ColorSpace("BT2020_FR", 11, makeColorSpace(colorSpace3, ColorSpaceRange.FULL));
    $VALUES = $values();
  }

  private ColorSpace(String str, int i, int value) {
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

  private static int makeColorSpace(ColorSpace colorSpace, ColorSpaceRange range) {
    Def.require(
        (colorSpace == NONE || range == ColorSpaceRange.NONE) ? false : true,
        "color-space is none",
        new Object[0]);
    return (colorSpace.getValue() & 15) + (range.getValue() << 4);
  }

  public boolean isLimitedRange() {
    return (this.value >> 4) == ColorSpaceRange.LIMITED.value;
  }

  public boolean isFullRange() {
    return (this.value >> 4) == ColorSpaceRange.FULL.value;
  }

  public enum ColorSpaceRange implements NumericEnum {
    NONE(0),
    LIMITED(1),
    FULL(2);

    private final int value;

    ColorSpaceRange(int value) {
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

  /* renamed from: of */
  public static ColorSpace m466of(Bitmap bitmap) {
    android.graphics.ColorSpace colorSpace = bitmap.getColorSpace();
    if (colorSpace == null) {
      return NONE;
    }
    switch (colorSpace.getId()) {
      case 0:
        break;
      case 4:
        break;
      case 5:
        break;
      case 7:
        break;
    }
    return NONE;
  }
}
