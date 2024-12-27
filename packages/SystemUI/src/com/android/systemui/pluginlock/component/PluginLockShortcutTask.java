package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class PluginLockShortcutTask {
    public static final String DO_NOT_DISTURB_TASK = "Dnd";
    public static final String FLASH_LIGHT_TASK = "Flashlight";
    public static final String GET_LOCKSTAR_TASK_SHORTCUT_STATE = "get_lockstar_task_shortcut_state";
    public static final String KEY_ACTION = "action";
    public static final String KEY_ARG = "arg";
    public static final String KEY_EXTRAS = "extras";
    final Context mContext;

    public PluginLockShortcutTask(Context context) {
        this.mContext = context;
    }

    public abstract void excute();

    public abstract String getAppLabel();

    public abstract Drawable getDrawble();

    public abstract boolean isEnabled();

    public abstract void removeListener();

    public abstract void setState(boolean z);
}
