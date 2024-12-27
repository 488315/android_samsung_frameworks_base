package com.android.systemui.edgelighting.backup;

public final class CocktailBarSettingValue {
    public String mName;
    public String mType;
    public String mValue;

    public CocktailBarSettingValue() {
    }

    public CocktailBarSettingValue(String str, String str2, String str3) {
        this.mName = str;
        this.mValue = str2;
        this.mType = str3;
    }
}
