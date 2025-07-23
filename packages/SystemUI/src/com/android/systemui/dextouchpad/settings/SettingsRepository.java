package com.android.systemui.dextouchpad.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.android.systemui.dextouchpad.util.Features;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SettingsRepository {
    public static final Object sInstanceLock = new Object();
    public static SettingsRepository sSettingsRepository;
    public final Object mLock;
    public final SharedPreferences mPrefs;

    private SettingsRepository(Context context) {
        String str;
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
                        if (settings$Key != null) {
                            Class cls = settings$Key.mType;
                            if (cls == Integer.class) {
                                str = Integer.toString(((Integer) entry.getValue()).intValue());
                            } else if (cls == String.class) {
                                str = (String) entry.getValue();
                            }
                            Log.d("DexTouchpadSettingsRepository", "init prefs key=" + key + " value=" + str);
                        }
                        str = null;
                        Log.d("DexTouchpadSettingsRepository", "init prefs key=" + key + " value=" + str);
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
