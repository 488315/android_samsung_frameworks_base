package com.samsung.systemui.splugins.slimindicator;

import android.graphics.drawable.Drawable;
import java.util.HashMap;

/* loaded from: classes4.dex */
public interface SPluginSlimIndicatorBoxCallback {
    public static final String ICON_BLACKLIST_SETTING = "icon_blacklist";
    public static final int VERSION = 9000;
    public static final HashMap<String, Drawable> mIconDrawableList = null;
    public static final HashMap<String, Boolean> mIconEnableList = null;
    public static final boolean mIsShowHomeCarrier = true;
    public static final boolean mIsShowLockCarrier = true;

    boolean getIsLockCarrier();

    boolean getIsShowCarrier();
}
