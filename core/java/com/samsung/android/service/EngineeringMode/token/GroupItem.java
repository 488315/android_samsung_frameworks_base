package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class GroupItem {
    private String mDescription;
    private ArrayList<AttributeInfo> mGroupAttribute = new ArrayList<>();
    private int mIndex;
    private String mName;

    public GroupItem(int mIndex, String nName, String mDescription) {
        this.mIndex = mIndex;
        this.mName = nName;
        this.mDescription = mDescription;
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

    public void pushAttribute(int type, int len, byte[] attribute) {
        this.mGroupAttribute.add(new AttributeInfo(type, len, attribute));
    }

    public AttributeInfo getAttribute(int index) {
        return this.mGroupAttribute.get(index);
    }

    public int getAttributeInfoNum() {
        return this.mGroupAttribute.size();
    }
}
