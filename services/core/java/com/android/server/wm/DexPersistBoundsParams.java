package com.android.server.wm;

import android.graphics.Rect;
import com.android.modules.utils.TypedXmlSerializer;

/* loaded from: classes3.dex */
public class DexPersistBoundsParams {
  public final Rect mDexDualBounds = new Rect();
  public final Rect mDexStandAloneBounds = new Rect();
  public int mDexWindowingMode;

  public void restore(String str, String str2) {
    str.hashCode();
    switch (str) {
      case "dex_windowing_mode":
        this.mDexWindowingMode = Integer.parseInt(str2);
        break;
      case "dex_dual_bounds":
        Rect unflattenFromString = Rect.unflattenFromString(str2);
        if (unflattenFromString != null) {
          this.mDexDualBounds.set(unflattenFromString);
          break;
        }
        break;
      case "dex_standalone_bounds":
        Rect unflattenFromString2 = Rect.unflattenFromString(str2);
        if (unflattenFromString2 != null) {
          this.mDexStandAloneBounds.set(unflattenFromString2);
          break;
        }
        break;
    }
  }

  public void saveToXml(TypedXmlSerializer typedXmlSerializer) {
    typedXmlSerializer.attribute(
        (String) null, "dex_windowing_mode", Integer.toString(this.mDexWindowingMode));
    typedXmlSerializer.attribute(
        (String) null, "dex_dual_bounds", this.mDexDualBounds.flattenToString());
    typedXmlSerializer.attribute(
        (String) null, "dex_standalone_bounds", this.mDexStandAloneBounds.flattenToString());
  }

  public void set(DexPersistBoundsParams dexPersistBoundsParams) {
    this.mDexWindowingMode = dexPersistBoundsParams.mDexWindowingMode;
    this.mDexDualBounds.set(dexPersistBoundsParams.mDexDualBounds);
    this.mDexStandAloneBounds.set(dexPersistBoundsParams.mDexStandAloneBounds);
  }

  public void reset() {
    this.mDexWindowingMode = 0;
    this.mDexDualBounds.setEmpty();
    this.mDexStandAloneBounds.setEmpty();
  }

  public boolean isValid() {
    return ((this.mDexDualBounds.isEmpty() && this.mDexStandAloneBounds.isEmpty())
            || this.mDexWindowingMode == 0)
        ? false
        : true;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" dexDualBounds=" + this.mDexDualBounds);
    sb.append(" dexStandAloneBounds=" + this.mDexStandAloneBounds);
    sb.append(" dexWindowingMode=" + this.mDexWindowingMode);
    return sb.toString();
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj != null && getClass() == obj.getClass()) {
      DexPersistBoundsParams dexPersistBoundsParams = (DexPersistBoundsParams) obj;
      if (this.mDexWindowingMode == dexPersistBoundsParams.mDexWindowingMode
          && this.mDexStandAloneBounds.equals(dexPersistBoundsParams.mDexStandAloneBounds)
          && this.mDexDualBounds.equals(dexPersistBoundsParams.mDexDualBounds)) {
        return true;
      }
    }
    return false;
  }

  public int hashCode() {
    return (((this.mDexStandAloneBounds.hashCode() * 31) + this.mDexDualBounds.hashCode()) * 31)
        + this.mDexWindowingMode;
  }
}
