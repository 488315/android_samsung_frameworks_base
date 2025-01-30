package com.samsung.systemui.splugins.slimindicator;

import android.graphics.drawable.Drawable;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface SPluginSlimIndicatorBoxCallback {
    public static final String ICON_BLACKLIST_SETTING = "icon_blacklist";
    public static final int VERSION = 7005;
    public static final HashMap<String, Drawable> mIconDrawableList = null;
    public static final HashMap<String, Boolean> mIconEnableList = null;
    public static final boolean mIsShowHomeCarrier = true;
    public static final boolean mIsShowLockCarrier = true;

    boolean getIsLockCarrier();

    boolean getIsShowCarrier();
}
