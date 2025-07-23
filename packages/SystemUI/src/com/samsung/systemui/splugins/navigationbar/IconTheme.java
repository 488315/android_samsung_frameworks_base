package com.samsung.systemui.splugins.navigationbar;

import android.util.ArrayMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class IconTheme implements IconThemeBase {
    Map<IconType, IconResource> mData = new ArrayMap();
    IconThemeType mThemeType;

    public IconTheme(IconThemeType iconThemeType) {
        this.mThemeType = iconThemeType;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.IconThemeBase
    public void addData(IconType iconType, IconResource iconResource) {
        this.mData.put(iconType, iconResource);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.IconThemeBase
    public IconResource getData(IconType iconType) {
        return this.mData.get(iconType);
    }
}
