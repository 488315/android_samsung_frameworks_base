package com.android.systemui.pluginlock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
