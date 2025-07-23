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

    public CommonItemCollection(String str, ArrayList<CommonItem> arrayList) {
        setMagicString(str);
        this.mCommonItem = arrayList;
    }

    public CommonItem getCommonItem(int i) {
        return this.mCommonItem.get(i);
    }

    public int getItemsNum() {
        return this.mCommonItem.size();
    }

    public void addCommonItem(int i, int i2, byte[] bArr) {
        this.mCommonItem.add(new CommonItem(i, i2, bArr));
    }
}
