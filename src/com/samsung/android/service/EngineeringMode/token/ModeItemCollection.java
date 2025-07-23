package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
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

    public ModeItemCollection(String str, ArrayList<ModeItem> arrayList) {
        setMagicString(str);
        this.mModeItems = arrayList;
    }

    public ModeItem getModeItem(int i) {
        return this.mModeItems.get(i);
    }

    public int getItemsNum() {
        return this.mModeItems.size();
    }

    public void addModeItemCollection(int i, String str, String str2, int i2) {
        this.mModeItems.add(new ModeItem(i, str, str2, i2));
    }

    public void addAttrToModeItem(int i, int i2, int i3, byte[] bArr) {
        Iterator<ModeItem> it = this.mModeItems.iterator();
        while (it.hasNext()) {
            ModeItem next = it.next();
            if (next.getIndex() == i) {
                next.pushAttribute(i2, i3, bArr);
            }
        }
    }

    public ModeItem getModeItemByIndex(int i) {
        Iterator<ModeItem> it = this.mModeItems.iterator();
        while (it.hasNext()) {
            ModeItem next = it.next();
            if (next.getIndex() == i) {
                return next;
            }
        }
        return null;
    }
}
