package com.samsung.android.fontutil;

/* loaded from: classes5.dex */
public class TypefaceFile {
    private String mDroidName;
    private String mFileName;

    public TypefaceFile() {
        this.mFileName = null;
        this.mDroidName = null;
    }

    public TypefaceFile(String fileName, String droidName) {
        this.mFileName = null;
        this.mDroidName = null;
        this.mFileName = fileName;
        this.mDroidName = droidName;
    }

    public String getFileName() {
        return this.mFileName;
    }

    public void setFileName(String fileName) {
        this.mFileName = fileName;
    }

    public String getDroidName() {
        return this.mDroidName;
    }

    public void setDroidName(String droidName) {
        this.mDroidName = droidName;
    }

    public String toString() {
        return "Filename : " + this.mFileName + " / Droidname : " + this.mDroidName;
    }
}
