package com.android.systemui.dextouchpad.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.android.systemui.dextouchpad.util.Features;
import java.util.Map;

/* loaded from: classes2.dex */
public class SettingsRepository {
    public static final Object sInstanceLock = new Object();
    public static SettingsRepository sSettingsRepository;
    public final Object mLock;
    public final SharedPreferences mPrefs;

    /* JADX WARN: Removed duplicated region for block: B:19:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private SettingsRepository(Context context) {
        String string;
        Object obj = new Object();
        this.mLock = obj;
        synchronized (obj) {
            try {
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                this.mPrefs = defaultSharedPreferences;
                if (Features.DEBUG) {
                    for (Map.Entry<String, ?> entry : defaultSharedPreferences.getAll().entrySet()) {
                        String key = entry.getKey();
                        Settings$Key settings$Key = (Settings$Key) SettingsKeys.STRING_TO_KEY.get(key);
                        if (settings$Key == null) {
                            string = null;
                        } else {
                            Class cls = settings$Key.mType;
                            if (cls == Integer.class) {
                                string = Integer.toString(((Integer) entry.getValue()).intValue());
                            } else if (cls == String.class) {
                                string = (String) entry.getValue();
                            }
                        }
                        Log.d("DexTouchpadSettingsRepository", "init prefs key=" + key + " value=" + string);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static SettingsRepository getInstance(Context context) {
        SettingsRepository settingsRepository;
        synchronized (sInstanceLock) {
            try {
                if (sSettingsRepository == null) {
                    sSettingsRepository = new SettingsRepository(context);
                }
                settingsRepository = sSettingsRepository;
            } catch (Throwable th) {
                throw th;
            }
        }
        return settingsRepository;
    }

    public final int getInt(Settings$Key settings$Key) {
        int i;
        synchronized (this.mLock) {
            i = this.mPrefs.getInt(settings$Key.mName, Integer.parseInt(settings$Key.mDefValue));
        }
        return i;
    }

    public final void putInt(Settings$Key settings$Key, int i) {
        synchronized (this.mLock) {
            this.mPrefs.edit().putInt(settings$Key.mName, i).apply();
        }
    }
}
