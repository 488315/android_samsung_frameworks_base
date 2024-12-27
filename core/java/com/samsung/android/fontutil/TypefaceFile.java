package com.samsung.android.fontutil;

public class TypefaceFile {
    private String mFileName = null;
    private String mDroidName = null;

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
