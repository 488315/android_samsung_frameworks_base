package com.samsung.android.fontutil;

/* loaded from: classes6.dex */
public class TypefaceFile {
    private String mFileName = null;
    private String mDroidName = null;

    public String getFileName() {
        return this.mFileName;
    }

    public void setFileName(String str) {
        this.mFileName = str;
    }

    public String getDroidName() {
        return this.mDroidName;
    }

    public void setDroidName(String str) {
        this.mDroidName = str;
    }

    public String toString() {
        return "Filename : " + this.mFileName + " / Droidname : " + this.mDroidName;
    }
}
