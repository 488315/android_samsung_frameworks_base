package com.android.server.wm;

import android.graphics.Rect;
import com.android.modules.utils.TypedXmlSerializer;

/* loaded from: classes3.dex */
public class NewDexPersistBoundsParams {
  public final Rect mNewDexNextGenBounds = new Rect();
  public int mNewDexWindowingMode;

  public void restore(String str, String str2) {
    Rect unflattenFromString;
    str.hashCode();
    if (str.equals("new_dex_windowing_mode")) {
      this.mNewDexWindowingMode = Integer.parseInt(str2);
    } else if (str.equals("new_dex_next_gen_bounds")
        && (unflattenFromString = Rect.unflattenFromString(str2)) != null) {
      this.mNewDexNextGenBounds.set(unflattenFromString);
    }
  }

  public void saveToXml(TypedXmlSerializer typedXmlSerializer) {
    typedXmlSerializer.attribute(
        (String) null, "new_dex_windowing_mode", Integer.toString(this.mNewDexWindowingMode));
    typedXmlSerializer.attribute(
        (String) null, "new_dex_next_gen_bounds", this.mNewDexNextGenBounds.flattenToString());
  }

  public void set(NewDexPersistBoundsParams newDexPersistBoundsParams) {
    this.mNewDexWindowingMode = newDexPersistBoundsParams.mNewDexWindowingMode;
    this.mNewDexNextGenBounds.set(newDexPersistBoundsParams.mNewDexNextGenBounds);
  }

  public void reset() {
    this.mNewDexWindowingMode = 0;
    this.mNewDexNextGenBounds.setEmpty();
  }

  public boolean isValid() {
    return (this.mNewDexNextGenBounds.isEmpty() || this.mNewDexWindowingMode == 0) ? false : true;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(" newDexNextGenBounds=" + this.mNewDexNextGenBounds);
    sb.append(" newDexWindowingMode=" + this.mNewDexWindowingMode);
    return sb.toString();
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj != null && getClass() == obj.getClass()) {
      NewDexPersistBoundsParams newDexPersistBoundsParams = (NewDexPersistBoundsParams) obj;
      if (this.mNewDexWindowingMode == newDexPersistBoundsParams.mNewDexWindowingMode
          && this.mNewDexNextGenBounds.equals(newDexPersistBoundsParams.mNewDexNextGenBounds)) {
        return true;
      }
    }
    return false;
  }

  public int hashCode() {
    return (this.mNewDexNextGenBounds.hashCode() * 31) + this.mNewDexWindowingMode;
  }
}
