package com.samsung.android.fontutil;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
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

    public void setFontPackageName(String str) {
        this.mFontPackageName = str;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public String getTypefaceFilename() {
        return this.mTypefaceFilename;
    }

    public void setTypefaceFilename(String str) {
        this.mTypefaceFilename = str;
    }

    public String getSansName() {
        if (this.mSansFonts.isEmpty()) {
            return null;
        }
        return this.mName;
    }
}
