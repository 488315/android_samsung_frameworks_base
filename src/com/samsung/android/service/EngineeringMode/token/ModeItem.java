package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class ModeItem {
    private String mDescription;
    private int mGroupIndex;
    private int mIndex;
    private ArrayList<AttributeInfo> mModeAttribute = new ArrayList<>();
    private String mName;

    public ModeItem(int i, String str, String str2, int i2) {
        this.mIndex = i;
        this.mName = str;
        this.mDescription = str2;
        this.mGroupIndex = i2;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public String getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int getGroupIndex() {
        return this.mGroupIndex;
    }

    public void pushAttribute(int i, int i2, byte[] bArr) {
        this.mModeAttribute.add(new AttributeInfo(i, i2, bArr));
    }

    public AttributeInfo getAttribute(int i) {
        return this.mModeAttribute.get(i);
    }

    public int getAttributeInfoNum() {
        return this.mModeAttribute.size();
    }
}
