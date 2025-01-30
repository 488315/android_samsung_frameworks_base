package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
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

  public GroupItemCollection(String magic, ArrayList<GroupItem> item) {
    setMagicString(magic);
    this.mGroupItems = item;
  }

  public GroupItem getGroupItem(int index) {
    return this.mGroupItems.get(index);
  }

  public int getItemsNum() {
    return this.mGroupItems.size();
  }

  public void addGroupItemCollection(int groupIndex, String name, String description) {
    this.mGroupItems.add(new GroupItem(groupIndex, name, description));
  }

  public void addAttrToGroupItem(int groupIndex, int type, int len, byte[] attribute) {
    Iterator<GroupItem> it = this.mGroupItems.iterator();
    while (it.hasNext()) {
      GroupItem item = it.next();
      if (item.getIndex() == groupIndex) {
        item.pushAttribute(type, len, attribute);
      }
    }
  }

  public GroupItem getGroupItemByIndex(int index) {
    Iterator<GroupItem> it = this.mGroupItems.iterator();
    while (it.hasNext()) {
      GroupItem item = it.next();
      if (item.getIndex() == index) {
        return item;
      }
    }
    return null;
  }
}
