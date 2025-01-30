package com.samsung.android.fontutil;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class SemTypeface {
  private String mName = null;
  private String mFontPackageName = null;
  private String mTypefaceFilename = null;
  public final List<TypefaceFile> mSansFonts = new ArrayList();
  public final List<TypefaceFile> mSerifFonts = new ArrayList();
  public final List<TypefaceFile> mMonospaceFonts = new ArrayList();

  public String getFontPackageName() {
    return this.mFontPackageName;
  }

  public void setFontPackageName(String name) {
    this.mFontPackageName = name;
  }

  public String getName() {
    return this.mName;
  }

  public void setName(String name) {
    this.mName = name;
  }

  public String getTypefaceFilename() {
    return this.mTypefaceFilename;
  }

  public void setTypefaceFilename(String typefaceFilename) {
    this.mTypefaceFilename = typefaceFilename;
  }

  public String getSansName() {
    if (this.mSansFonts.isEmpty()) {
      return null;
    }
    return this.mName;
  }
}
