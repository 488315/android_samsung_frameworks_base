package com.samsung.android.content.smartclip;

/* loaded from: classes5.dex */
public class SemSmartClipMetaTag {
    private String mType;
    private String mValue;

    public SemSmartClipMetaTag(String tagType, String value) {
        this.mType = null;
        this.mValue = null;
        this.mType = tagType;
        this.mValue = value;
    }

    public String getType() {
        return this.mType;
    }

    public String getValue() {
        return this.mValue;
    }

    public void setType(String tagType) {
        this.mType = tagType;
    }

    public void setValue(String value) {
        this.mValue = value;
    }
}
