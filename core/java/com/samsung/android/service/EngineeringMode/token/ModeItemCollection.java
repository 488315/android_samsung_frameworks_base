package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class ModeItemCollection extends InfoCollection {
    private ArrayList<ModeItem> mModeItems;

    @Override // com.samsung.android.service.EngineeringMode.token.InfoCollection
    public /* bridge */ /* synthetic */ String getMagicString() {
        return super.getMagicString();
    }

    @Override // com.samsung.android.service.EngineeringMode.token.InfoCollection
    public /* bridge */ /* synthetic */ void setMagicString(String str) {
        super.setMagicString(str);
    }

    public ModeItemCollection(String magic, ArrayList<ModeItem> item) {
        setMagicString(magic);
        this.mModeItems = item;
    }

    public ModeItem getModeItem(int index) {
        return this.mModeItems.get(index);
    }

    public int getItemsNum() {
        return this.mModeItems.size();
    }

    public void addModeItemCollection(int modeIndex, String name, String description, int groupIndex) {
        this.mModeItems.add(new ModeItem(modeIndex, name, description, groupIndex));
    }

    public void addAttrToModeItem(int modeIndex, int type, int len, byte[] attribute) {
        Iterator<ModeItem> it = this.mModeItems.iterator();
        while (it.hasNext()) {
            ModeItem item = it.next();
            if (item.getIndex() == modeIndex) {
                item.pushAttribute(type, len, attribute);
            }
        }
    }

    public ModeItem getModeItemByIndex(int index) {
        Iterator<ModeItem> it = this.mModeItems.iterator();
        while (it.hasNext()) {
            ModeItem item = it.next();
            if (item.getIndex() == index) {
                return item;
            }
        }
        return null;
    }
}
