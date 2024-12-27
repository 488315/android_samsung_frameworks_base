package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class CommonItemCollection extends InfoCollection {
    private ArrayList<CommonItem> mCommonItem;

    @Override // com.samsung.android.service.EngineeringMode.token.InfoCollection
    public /* bridge */ /* synthetic */ String getMagicString() {
        return super.getMagicString();
    }

    @Override // com.samsung.android.service.EngineeringMode.token.InfoCollection
    public /* bridge */ /* synthetic */ void setMagicString(String str) {
        super.setMagicString(str);
    }

    public CommonItemCollection(String magic, ArrayList<CommonItem> item) {
        setMagicString(magic);
        this.mCommonItem = item;
    }

    public CommonItem getCommonItem(int index) {
        return this.mCommonItem.get(index);
    }

    public int getItemsNum() {
        return this.mCommonItem.size();
    }

    public void addCommonItem(int type, int len, byte[] content) {
        this.mCommonItem.add(new CommonItem(type, len, content));
    }
}
