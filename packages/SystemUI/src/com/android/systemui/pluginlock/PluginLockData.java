package com.android.systemui.pluginlock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PluginLockData {
    public static final int TYPE_FACE_WIDGET = 1;
    public static final int TYPE_HELP_TEXT = 5;
    public static final int TYPE_LOCK_ICON = 7;
    public static final int TYPE_MUSIC = 2;
    public static final int TYPE_N_CARD = 4;
    public static final int TYPE_N_IO = 3;
    public static final int TYPE_SHORTCUT = 6;

    int getBottom(int i);

    int getCount(int i);

    int getGravity(int i);

    int getPaddingBottom(int i);

    int getPaddingEnd(int i);

    int getPaddingStart(int i);

    int getTop(int i);

    int getVisibility(int i);

    boolean isAvailable();

    boolean isAvailable(int i);
}
