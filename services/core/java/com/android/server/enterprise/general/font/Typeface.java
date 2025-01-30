package com.android.server.enterprise.general.font;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class Typeface {
    public String mName = null;
    public String mFontPackageName = null;
    public String mTypefaceFilename = null;
    public final List mSansFonts = new ArrayList();
    public final List mSerifFonts = new ArrayList();
    public final List mMonospaceFonts = new ArrayList();

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
