package com.android.systemui.pluginlock;

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
