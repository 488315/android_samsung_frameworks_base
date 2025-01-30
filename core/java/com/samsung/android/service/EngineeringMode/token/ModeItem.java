package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ModeItem {
  private String mDescription;
  private int mGroupIndex;
  private int mIndex;
  private ArrayList<AttributeInfo> mModeAttribute = new ArrayList<>();
  private String mName;

  public ModeItem(int mIndex, String nName, String mDescription, int mGroupIndex) {
    this.mIndex = mIndex;
    this.mName = nName;
    this.mDescription = mDescription;
    this.mGroupIndex = mGroupIndex;
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

  public void pushAttribute(int type, int len, byte[] attribute) {
    this.mModeAttribute.add(new AttributeInfo(type, len, attribute));
  }

  public AttributeInfo getAttribute(int index) {
    return this.mModeAttribute.get(index);
  }

  public int getAttributeInfoNum() {
    return this.mModeAttribute.size();
  }
}
