package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class GroupItemCollection extends InfoCollection {
    private ArrayList<GroupItem> mGroupItems;

    @Override // com.samsung.android.service.EngineeringMode.token.InfoCollection
    public /* bridge */ /* synthetic */ String getMagicString() {
        return super.getMagicString();
    }

    @Override // com.samsung.android.service.EngineeringMode.token.InfoCollection
    public /* bridge */ /* synthetic */ void setMagicString(String str) {
        super.setMagicString(str);
    }

    public GroupItemCollection(String str, ArrayList<GroupItem> arrayList) {
        setMagicString(str);
        this.mGroupItems = arrayList;
    }

    public GroupItem getGroupItem(int i) {
        return this.mGroupItems.get(i);
    }

    public int getItemsNum() {
        return this.mGroupItems.size();
    }

    public void addGroupItemCollection(int i, String str, String str2) {
        this.mGroupItems.add(new GroupItem(i, str, str2));
    }

    public void addAttrToGroupItem(int i, int i2, int i3, byte[] bArr) {
        Iterator<GroupItem> it = this.mGroupItems.iterator();
        while (it.hasNext()) {
            GroupItem next = it.next();
            if (next.getIndex() == i) {
                next.pushAttribute(i2, i3, bArr);
            }
        }
    }

    public GroupItem getGroupItemByIndex(int i) {
        Iterator<GroupItem> it = this.mGroupItems.iterator();
        while (it.hasNext()) {
            GroupItem next = it.next();
            if (next.getIndex() == i) {
                return next;
            }
        }
        return null;
    }
}
