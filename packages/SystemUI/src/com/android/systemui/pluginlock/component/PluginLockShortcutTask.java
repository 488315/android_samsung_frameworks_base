package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.graphics.drawable.Drawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
