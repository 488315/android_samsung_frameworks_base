package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class GroupItem {
    private String mDescription;
    private ArrayList<AttributeInfo> mGroupAttribute = new ArrayList<>();
    private int mIndex;
    private String mName;

    public GroupItem(int i, String str, String str2) {
        this.mIndex = i;
        this.mName = str;
        this.mDescription = str2;
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

    public void pushAttribute(int i, int i2, byte[] bArr) {
        this.mGroupAttribute.add(new AttributeInfo(i, i2, bArr));
    }

    public AttributeInfo getAttribute(int i) {
        return this.mGroupAttribute.get(i);
    }

    public int getAttributeInfoNum() {
        return this.mGroupAttribute.size();
    }
}
