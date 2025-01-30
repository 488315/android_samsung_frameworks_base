package com.samsung.android.sume.core.descriptor;

import java.io.InputStream;

/* loaded from: classes4.dex */
public interface MFDescriptorParser {

  public enum Type {
    JSON
  }

  MFDescriptor parse(InputStream inputStream);

  /* renamed from: com.samsung.android.sume.core.descriptor.MFDescriptorParser$1 */
  static /* synthetic */ class C52651 {

    /* renamed from: $SwitchMap$com$samsung$android$sume$core$descriptor$MFDescriptorParser$Type */
    static final /* synthetic */ int[] f3051x37447d1f;

    static {
      int[] iArr = new int[Type.values().length];
      f3051x37447d1f = iArr;
      try {
        iArr[Type.JSON.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
    }
  }

  /* renamed from: of */
  static MFDescriptorParser m357of(Type type) {
    switch (C52651.f3051x37447d1f[type.ordinal()]) {
      case 1:
        return new MFDescriptorJsonParser();
      default:
        throw new UnsupportedOperationException("unknown type");
    }
  }
}
