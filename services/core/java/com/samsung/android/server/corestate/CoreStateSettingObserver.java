package com.samsung.android.server.corestate;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.server.display.DisplayPowerController2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class CoreStateSettingObserver extends ContentObserver {
    public static final String TAG = CoreStateSettingObserver.class.getSimpleName();
    public final Context mContext;
    public CoreStateObserverController mController;
    public final Map mGlobalSettingToTypeMap;
    public final Map mIntegerDefaultKeyMap;
    public final Map mSecureSettingToTypeMap;
    public final Map mSecureSettingToTypeMapForUser;
    public final Map mSystemSettingToTypeMap;
    public final Map mSystemSettingToTypeMapForUser;

    public CoreStateSettingObserver(Context context, Handler handler, CoreStateObserverController coreStateObserverController) {
        super(handler);
        this.mSecureSettingToTypeMap = new HashMap();
        this.mSystemSettingToTypeMap = new HashMap();
        this.mGlobalSettingToTypeMap = new HashMap();
        this.mSecureSettingToTypeMapForUser = new HashMap();
        this.mSystemSettingToTypeMapForUser = new HashMap();
        this.mIntegerDefaultKeyMap = new HashMap();
        this.mController = coreStateObserverController;
        this.mContext = context;
        registerObservingItems();
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri, int i) {
        Slog.d(TAG, "onChange(u" + i + ") : uri=" + uri);
        this.mController.onCoreStateChanged(i);
    }

    public void beginObserveCoreStateSettings() {
        Slog.d(TAG, "beginObserveCoreStateSettings");
        Iterator it = this.mSecureSettingToTypeMap.keySet().iterator();
        while (it.hasNext()) {
            Uri uriFor = Settings.Secure.getUriFor((String) it.next());
            Slog.d(TAG, "beginObserveCoreStateSettings : " + uriFor);
            registerContentObserver(uriFor, false, this, 0);
        }
        Iterator it2 = this.mSystemSettingToTypeMap.keySet().iterator();
        while (it2.hasNext()) {
            Uri uriFor2 = Settings.System.getUriFor((String) it2.next());
            Slog.d(TAG, "beginObserveCoreStateSettings : " + uriFor2);
            registerContentObserver(uriFor2, false, this, 0);
        }
        Iterator it3 = this.mGlobalSettingToTypeMap.keySet().iterator();
        while (it3.hasNext()) {
            Uri uriFor3 = Settings.Global.getUriFor((String) it3.next());
            Slog.d(TAG, "beginObserveCoreStateSettings : " + uriFor3);
            registerContentObserver(uriFor3, false, this, 0);
        }
        beginObserveCoreStateSettingsForSingleUser(0);
    }

    public final void registerContentObserver(final Uri uri, final boolean z, final ContentObserver contentObserver, final int i) {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.samsung.android.server.corestate.CoreStateSettingObserver$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CoreStateSettingObserver.this.lambda$registerContentObserver$0(uri, z, contentObserver, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerContentObserver$0(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        this.mContext.getContentResolver().registerContentObserver(uri, z, contentObserver, i);
    }

    public void beginObserveCoreStateSettingsForSingleUser(int i) {
        Slog.d(TAG, "beginObserveCoreStateSettingsForSingleUser(u" + i + ")");
        Iterator it = this.mSecureSettingToTypeMapForUser.keySet().iterator();
        while (it.hasNext()) {
            Uri uriFor = Settings.Secure.getUriFor((String) it.next());
            Slog.d(TAG, "beginObserveCoreStateSettings : " + uriFor);
            registerContentObserver(uriFor, false, this, i);
        }
        Iterator it2 = this.mSystemSettingToTypeMapForUser.keySet().iterator();
        while (it2.hasNext()) {
            Uri uriFor2 = Settings.System.getUriFor((String) it2.next());
            Slog.d(TAG, "beginObserveCoreStateSettings : " + uriFor2);
            registerContentObserver(uriFor2, false, this, i);
        }
    }

    public void endObserveCoreStateSettingsForSingleUser(ArraySet arraySet, int i) {
        Slog.d(TAG, "endObserveCoreStateSettingsForSingleUser(u" + i + ")");
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.samsung.android.server.corestate.CoreStateSettingObserver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CoreStateSettingObserver.this.lambda$endObserveCoreStateSettingsForSingleUser$1();
            }
        });
        beginObserveCoreStateSettings();
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (intValue != 0 && intValue != i) {
                beginObserveCoreStateSettingsForSingleUser(intValue);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$endObserveCoreStateSettingsForSingleUser$1() {
        this.mContext.getContentResolver().unregisterContentObserver(this);
    }

    public void registerObservingItems() {
        this.mGlobalSettingToTypeMap.put("open_in_pop_up_view", Integer.TYPE);
    }

    public int populateState(Bundle bundle, int i) {
        return (populate(bundle, this.mSecureSettingToTypeMap, i) ? 1 : 0) | 0 | (populate(bundle, this.mSystemSettingToTypeMap, i) ? 1 : 0) | (populate(bundle, this.mGlobalSettingToTypeMap, i) ? 1 : 0) | (populate(bundle, this.mSecureSettingToTypeMapForUser, i) ? 2 : 0) | (populate(bundle, this.mSystemSettingToTypeMapForUser, i) ? 2 : 0);
    }

    public final boolean populate(Bundle bundle, Map map, int i) {
        String stringForUser;
        int intForUser;
        boolean z = false;
        for (Map.Entry entry : map.entrySet()) {
            String str = (String) entry.getKey();
            Class cls = (Class) entry.getValue();
            if (cls == String.class) {
                if (map == this.mSecureSettingToTypeMap) {
                    stringForUser = Settings.Secure.getString(this.mContext.getContentResolver(), str);
                } else if (map == this.mSystemSettingToTypeMap) {
                    stringForUser = Settings.System.getString(this.mContext.getContentResolver(), str);
                } else if (map == this.mGlobalSettingToTypeMap) {
                    stringForUser = Settings.Global.getString(this.mContext.getContentResolver(), str);
                } else if (map == this.mSecureSettingToTypeMapForUser) {
                    stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, i);
                } else {
                    stringForUser = map == this.mSystemSettingToTypeMapForUser ? Settings.System.getStringForUser(this.mContext.getContentResolver(), str, i) : "";
                }
                if (stringForUser != null && !stringForUser.equals(bundle.getString(str))) {
                    bundle.putString(str, stringForUser);
                    z = true;
                }
            } else if (cls == Integer.TYPE) {
                Integer num = (Integer) this.mIntegerDefaultKeyMap.get(str);
                int intValue = num != null ? num.intValue() : 0;
                if (map == this.mSecureSettingToTypeMap) {
                    intForUser = Settings.Secure.getInt(this.mContext.getContentResolver(), str, 0);
                } else if (map == this.mSystemSettingToTypeMap) {
                    intForUser = Settings.System.getInt(this.mContext.getContentResolver(), str, 0);
                } else if (map == this.mGlobalSettingToTypeMap) {
                    intForUser = Settings.Global.getInt(this.mContext.getContentResolver(), str, 0);
                } else if (map == this.mSecureSettingToTypeMapForUser) {
                    intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), str, 0, i);
                } else {
                    intForUser = map == this.mSystemSettingToTypeMapForUser ? Settings.System.getIntForUser(this.mContext.getContentResolver(), str, 0, i) : 0;
                }
                if (intForUser != bundle.getInt(str, intValue)) {
                    bundle.putInt(str, intForUser);
                    z = true;
                }
            } else if (cls == Float.TYPE) {
                Map map2 = this.mSecureSettingToTypeMap;
                float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                if (map == map2) {
                    f = Settings.Secure.getFloat(this.mContext.getContentResolver(), str, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                } else if (map == this.mSystemSettingToTypeMap) {
                    f = Settings.System.getFloat(this.mContext.getContentResolver(), str, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                } else if (map == this.mGlobalSettingToTypeMap) {
                    f = Settings.Global.getFloat(this.mContext.getContentResolver(), str, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                } else if (map == this.mSecureSettingToTypeMapForUser) {
                    f = Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), str, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, i);
                } else if (map == this.mSystemSettingToTypeMapForUser) {
                    f = Settings.System.getFloatForUser(this.mContext.getContentResolver(), str, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, i);
                }
                if (f != bundle.getFloat(str)) {
                    bundle.putFloat(str, f);
                    z = true;
                }
            } else if (cls == Long.TYPE) {
                long j = 0;
                if (map == this.mSecureSettingToTypeMap) {
                    j = Settings.Secure.getLong(this.mContext.getContentResolver(), str, 0L);
                } else if (map == this.mSystemSettingToTypeMap) {
                    j = Settings.System.getLong(this.mContext.getContentResolver(), str, 0L);
                } else if (map == this.mGlobalSettingToTypeMap) {
                    j = Settings.Global.getLong(this.mContext.getContentResolver(), str, 0L);
                } else if (map == this.mSecureSettingToTypeMapForUser) {
                    j = Settings.Secure.getLongForUser(this.mContext.getContentResolver(), str, 0L, i);
                } else if (map == this.mSystemSettingToTypeMapForUser) {
                    j = Settings.System.getLongForUser(this.mContext.getContentResolver(), str, 0L, i);
                }
                if (j != bundle.getLong(str)) {
                    bundle.putLong(str, j);
                    z = true;
                }
            }
        }
        return z;
    }
}
