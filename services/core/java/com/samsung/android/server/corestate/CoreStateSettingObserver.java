package com.samsung.android.server.corestate;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class CoreStateSettingObserver extends ContentObserver {
    public final Context mContext;
    public final CoreStateObserverController mController;
    public final Map mGlobalSettingToTypeMap;
    public final Map mIntegerDefaultKeyMap;
    public final Map mSecureSettingToTypeMap;
    public final Map mSecureSettingToTypeMapForUser;
    public final Map mSystemSettingToTypeMap;
    public final Map mSystemSettingToTypeMapForUser;

    public CoreStateSettingObserver(
            Context context,
            Handler handler,
            CoreStateObserverController coreStateObserverController) {
        super(handler);
        this.mSecureSettingToTypeMap = new HashMap();
        this.mSystemSettingToTypeMap = new HashMap();
        HashMap hashMap = new HashMap();
        this.mGlobalSettingToTypeMap = hashMap;
        this.mSecureSettingToTypeMapForUser = new HashMap();
        this.mSystemSettingToTypeMapForUser = new HashMap();
        this.mIntegerDefaultKeyMap = new HashMap();
        this.mController = coreStateObserverController;
        this.mContext = context;
        hashMap.put("open_in_pop_up_view", Integer.TYPE);
    }

    public final void beginObserveCoreStateSettings() {
        Slog.d("CoreStateSettingObserver", "beginObserveCoreStateSettings");
        Iterator it = ((HashMap) this.mSecureSettingToTypeMap).keySet().iterator();
        while (it.hasNext()) {
            Uri uriFor = Settings.Secure.getUriFor((String) it.next());
            Slog.d("CoreStateSettingObserver", "beginObserveCoreStateSettings : " + uriFor);
            registerContentObserver(uriFor, this, 0);
        }
        Iterator it2 = ((HashMap) this.mSystemSettingToTypeMap).keySet().iterator();
        while (it2.hasNext()) {
            Uri uriFor2 = Settings.System.getUriFor((String) it2.next());
            Slog.d("CoreStateSettingObserver", "beginObserveCoreStateSettings : " + uriFor2);
            registerContentObserver(uriFor2, this, 0);
        }
        Iterator it3 = ((HashMap) this.mGlobalSettingToTypeMap).keySet().iterator();
        while (it3.hasNext()) {
            Uri uriFor3 = Settings.Global.getUriFor((String) it3.next());
            Slog.d("CoreStateSettingObserver", "beginObserveCoreStateSettings : " + uriFor3);
            registerContentObserver(uriFor3, this, 0);
        }
        beginObserveCoreStateSettingsForSingleUser(0);
    }

    public final void beginObserveCoreStateSettingsForSingleUser(int i) {
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                i, "beginObserveCoreStateSettingsForSingleUser(u", ")", "CoreStateSettingObserver");
        Iterator it = ((HashMap) this.mSecureSettingToTypeMapForUser).keySet().iterator();
        while (it.hasNext()) {
            Uri uriFor = Settings.Secure.getUriFor((String) it.next());
            Slog.d("CoreStateSettingObserver", "beginObserveCoreStateSettings : " + uriFor);
            registerContentObserver(uriFor, this, i);
        }
        Iterator it2 = ((HashMap) this.mSystemSettingToTypeMapForUser).keySet().iterator();
        while (it2.hasNext()) {
            Uri uriFor2 = Settings.System.getUriFor((String) it2.next());
            Slog.d("CoreStateSettingObserver", "beginObserveCoreStateSettings : " + uriFor2);
            registerContentObserver(uriFor2, this, i);
        }
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri, int i) {
        Slog.d("CoreStateSettingObserver", "onChange(u" + i + ") : uri=" + uri);
        this.mController.sendCoreState(false, i, null);
    }

    public final boolean populate(Bundle bundle, Map map, int i) {
        HashMap hashMap = (HashMap) map;
        boolean z = false;
        for (Map.Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            Class cls = (Class) entry.getValue();
            if (cls == String.class) {
                String string =
                        hashMap == this.mSecureSettingToTypeMap
                                ? Settings.Secure.getString(this.mContext.getContentResolver(), str)
                                : hashMap == this.mSystemSettingToTypeMap
                                        ? Settings.System.getString(
                                                this.mContext.getContentResolver(), str)
                                        : hashMap == this.mGlobalSettingToTypeMap
                                                ? Settings.Global.getString(
                                                        this.mContext.getContentResolver(), str)
                                                : hashMap == this.mSecureSettingToTypeMapForUser
                                                        ? Settings.Secure.getStringForUser(
                                                                this.mContext.getContentResolver(),
                                                                str,
                                                                i)
                                                        : hashMap
                                                                        == this
                                                                                .mSystemSettingToTypeMapForUser
                                                                ? Settings.System.getStringForUser(
                                                                        this.mContext
                                                                                .getContentResolver(),
                                                                        str,
                                                                        i)
                                                                : "";
                if (string != null && !string.equals(bundle.getString(str))) {
                    bundle.putString(str, string);
                    z = true;
                }
            } else if (cls == Integer.TYPE) {
                Integer num = (Integer) ((HashMap) this.mIntegerDefaultKeyMap).get(str);
                int intValue = num != null ? num.intValue() : 0;
                int i2 =
                        hashMap == this.mSecureSettingToTypeMap
                                ? Settings.Secure.getInt(this.mContext.getContentResolver(), str, 0)
                                : hashMap == this.mSystemSettingToTypeMap
                                        ? Settings.System.getInt(
                                                this.mContext.getContentResolver(), str, 0)
                                        : hashMap == this.mGlobalSettingToTypeMap
                                                ? Settings.Global.getInt(
                                                        this.mContext.getContentResolver(), str, 0)
                                                : hashMap == this.mSecureSettingToTypeMapForUser
                                                        ? Settings.Secure.getIntForUser(
                                                                this.mContext.getContentResolver(),
                                                                str,
                                                                0,
                                                                i)
                                                        : hashMap
                                                                        == this
                                                                                .mSystemSettingToTypeMapForUser
                                                                ? Settings.System.getIntForUser(
                                                                        this.mContext
                                                                                .getContentResolver(),
                                                                        str,
                                                                        0,
                                                                        i)
                                                                : 0;
                if (i2 != bundle.getInt(str, intValue)) {
                    bundle.putInt(str, i2);
                    z = true;
                }
            } else if (cls == Float.TYPE) {
                Map map2 = this.mSecureSettingToTypeMap;
                float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                if (hashMap == map2) {
                    f =
                            Settings.Secure.getFloat(
                                    this.mContext.getContentResolver(),
                                    str,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE);
                } else if (hashMap == this.mSystemSettingToTypeMap) {
                    f =
                            Settings.System.getFloat(
                                    this.mContext.getContentResolver(),
                                    str,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE);
                } else if (hashMap == this.mGlobalSettingToTypeMap) {
                    f =
                            Settings.Global.getFloat(
                                    this.mContext.getContentResolver(),
                                    str,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE);
                } else if (hashMap == this.mSecureSettingToTypeMapForUser) {
                    f =
                            Settings.Secure.getFloatForUser(
                                    this.mContext.getContentResolver(),
                                    str,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    i);
                } else if (hashMap == this.mSystemSettingToTypeMapForUser) {
                    f =
                            Settings.System.getFloatForUser(
                                    this.mContext.getContentResolver(),
                                    str,
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    i);
                }
                if (f != bundle.getFloat(str)) {
                    bundle.putFloat(str, f);
                    z = true;
                }
            } else if (cls == Long.TYPE) {
                long j = 0;
                if (hashMap == this.mSecureSettingToTypeMap) {
                    j = Settings.Secure.getLong(this.mContext.getContentResolver(), str, 0L);
                } else if (hashMap == this.mSystemSettingToTypeMap) {
                    j = Settings.System.getLong(this.mContext.getContentResolver(), str, 0L);
                } else if (hashMap == this.mGlobalSettingToTypeMap) {
                    j = Settings.Global.getLong(this.mContext.getContentResolver(), str, 0L);
                } else if (hashMap == this.mSecureSettingToTypeMapForUser) {
                    j =
                            Settings.Secure.getLongForUser(
                                    this.mContext.getContentResolver(), str, 0L, i);
                } else if (hashMap == this.mSystemSettingToTypeMapForUser) {
                    j =
                            Settings.System.getLongForUser(
                                    this.mContext.getContentResolver(), str, 0L, i);
                }
                if (j != bundle.getLong(str)) {
                    bundle.putLong(str, j);
                    z = true;
                }
            }
        }
        return z;
    }

    public final void registerContentObserver(
            final Uri uri, final ContentObserver contentObserver, final int i) {
        BackgroundThread.getHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.samsung.android.server.corestate.CoreStateSettingObserver$$ExternalSyntheticLambda1
                            public final /* synthetic */ boolean f$2 = false;

                            @Override // java.lang.Runnable
                            public final void run() {
                                CoreStateSettingObserver coreStateSettingObserver =
                                        CoreStateSettingObserver.this;
                                coreStateSettingObserver
                                        .mContext
                                        .getContentResolver()
                                        .registerContentObserver(uri, this.f$2, contentObserver, i);
                            }
                        });
    }
}
