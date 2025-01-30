package com.samsung.systemui.splugins.navigationbar;

import android.util.ArrayMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
