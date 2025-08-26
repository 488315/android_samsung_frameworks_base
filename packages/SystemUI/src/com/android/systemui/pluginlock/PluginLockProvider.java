package com.android.systemui.pluginlock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.util.LogUtil;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager;

/* loaded from: classes2.dex */
public class PluginLockProvider extends ContentProvider {
    public static final String AUTHORITY = "com.android.systemui.pluginlock.provider";
    private static final String FILL_WALLPAPER_DATA = "fill_wallpaper_data";
    private static final String GET_DLS_DATA = "get_dls_data";
    private static final String GET_LOCKSTAR_TASK_SHORTCUT_STATE = "get_lockstar_task_shortcut_state";
    private static final String GET_NOTIFICATION_NUMBERS = "get_notification_numbers";
    private static final String GET_WALLPAPER_INDEX = "get_wallpaper_index";
    public static final String KEY_ACTION = "action";
    public static final String KEY_ARG = "arg";
    public static final String KEY_EXTRAS = "extras";
    public static final String KEY_WALLPAPER_INDEX = "wallpaper_index";
    private static final String REMOVE_LOCKSTAR_TASK_SHORTCUT_LISTENER = "remove_lockstar_task_shortcut_listener";
    private static final String SCREEN = "screen";
    private static final String SOURCE = "source";
    private static final String SOURCE_TYPE = "source_type";
    private static final String TAG = "PluginLockProvider";
    private static final String UPDATE_LOCKSTAR_TASK_SHORTCUT_STATE = "update_lockstar_task_shortcut_state";
    private static final String WALLPAPER_TYPE = "wallpaper_type";

    private void onEventReceived(PluginLockManager pluginLockManager, Bundle bundle) {
        if (pluginLockManager != null) {
            PluginLock pluginLock = pluginLockManager.getPluginLock();
            LogUtil.d(TAG, "onEventReceived, pluginLock:" + pluginLock, new Object[0]);
            if (pluginLock != null) {
                PluginLockBasicManager basicManager = pluginLock.getBasicManager();
                LogUtil.d(TAG, "onEventReceived, basicManager:" + basicManager, new Object[0]);
                if (basicManager != null) {
                    LogUtil.d(TAG, "call => onEventReceived", new Object[0]);
                    basicManager.onEventReceived(bundle);
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0038  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Bundle call(String str, String str2, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        PluginLockManager pluginLockManager = (PluginLockManager) Dependency.sDependency.getDependencyInner(PluginLockManager.class);
        MediaSessions$H$$ExternalSyntheticOutline0.m("call: method ", str, ", arg:", str2, TAG);
        if (pluginLockManager != null) {
            str.getClass();
            switch (str) {
                case "get_wallpaper_index":
                    bundle2.putInt(KEY_WALLPAPER_INDEX, (LsRune.SUBSCREEN_WATCHFACE && !TextUtils.isEmpty(str2) && Integer.parseInt(str2) == 1) ? ((PluginWallpaperManager) Dependency.sDependency.getDependencyInner(PluginWallpaperManager.class)).getWallpaperIndex(1, bundle) : ((PluginWallpaperManager) Dependency.sDependency.getDependencyInner(PluginWallpaperManager.class)).getWallpaperIndex());
                    break;
                case "fill_wallpaper_data":
                    ((PluginWallpaperManager) Dependency.sDependency.getDependencyInner(PluginWallpaperManager.class)).fillWallpaperData(bundle.getInt("screen"), bundle.getInt(WALLPAPER_TYPE), bundle.getInt(SOURCE_TYPE), bundle.getString(SOURCE));
                    return bundle2;
                case "remove_lockstar_task_shortcut_listener":
                    LogUtil.d(TAG, "call method:".concat(str), new Object[0]);
                    pluginLockManager.removeShortcutTaskListener();
                    return bundle2;
                case "update_lockstar_task_shortcut_state":
                    pluginLockManager.updateShortcutTaskState(str2);
                    return bundle2;
                case "get_lockstar_task_shortcut_state":
                    boolean shortcutTaskState = pluginLockManager.getShortcutTaskState(str2);
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("isEnable", shortcutTaskState);
                    bundle2.putString("action", str);
                    bundle2.putString("arg", str2);
                    bundle2.putBundle("extras", bundle3);
                    onEventReceived(pluginLockManager, bundle2);
                    return bundle2;
                case "get_dls_data":
                    Log.d(TAG, "call: GET_DLS_DATA");
                    bundle2.putString("dynamicLockData", ((PluginLockManager) Dependency.sDependency.getDependencyInner(PluginLockManager.class)).getDynamicLockData());
                    return bundle2;
                default:
                    bundle2.putString("action", str);
                    bundle2.putString("arg", str2);
                    bundle2.putBundle("extras", bundle);
                    LogUtil.d(TAG, "call bundle:" + bundle2.toString(), new Object[0]);
                    LogUtil.d(TAG, "call method:".concat(str), new Object[0]);
                    LogUtil.d(TAG, "call arg:" + str2, new Object[0]);
                    onEventReceived(pluginLockManager, bundle2);
                    return bundle2;
            }
        }
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
