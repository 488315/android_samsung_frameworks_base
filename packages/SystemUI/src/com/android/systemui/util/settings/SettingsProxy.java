package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Trace;
import android.provider.Settings;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SettingsProxy {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final float parseFloat(String str, float f) {
            if (str == null) {
                return f;
            }
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException unused) {
                return f;
            }
        }

        public final float parseFloatOrThrow(String str, String str2) throws Settings.SettingNotFoundException {
            if (str2 == null) {
                throw new Settings.SettingNotFoundException(str);
            }
            try {
                return Float.parseFloat(str2);
            } catch (NumberFormatException unused) {
                throw new Settings.SettingNotFoundException(str);
            }
        }

        public final long parseLongOrThrow(String str, String str2) throws Settings.SettingNotFoundException {
            if (str2 == null) {
                throw new Settings.SettingNotFoundException(str);
            }
            try {
                return Long.parseLong(str2);
            } catch (NumberFormatException unused) {
                throw new Settings.SettingNotFoundException(str);
            }
        }

        public final long parseLongOrUseDefault(String str, long j) {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException unused) {
                return j;
            }
        }
    }

    static float parseFloat(String str, float f) {
        return Companion.parseFloat(str, f);
    }

    static float parseFloatOrThrow(String str, String str2) throws Settings.SettingNotFoundException {
        return Companion.parseFloatOrThrow(str, str2);
    }

    static long parseLongOrThrow(String str, String str2) throws Settings.SettingNotFoundException {
        return Companion.parseLongOrThrow(str, str2);
    }

    static long parseLongOrUseDefault(String str, long j) {
        return Companion.parseLongOrUseDefault(str, j);
    }

    default boolean getBool(String str, boolean z) {
        return getInt(str, z ? 1 : 0) != 0;
    }

    ContentResolver getContentResolver();

    default float getFloat(String str, float f) {
        return Companion.parseFloat(getString(str), f);
    }

    default int getInt(String str, int i) {
        try {
            return Integer.parseInt(getString(str));
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    default long getLong(String str, long j) {
        return Companion.parseLongOrUseDefault(getString(str), j);
    }

    String getString(String str);

    Uri getUriFor(String str);

    default boolean putBool(String str, boolean z) {
        return putInt(str, z ? 1 : 0);
    }

    default boolean putFloat(String str, float f) {
        return putString(str, String.valueOf(f));
    }

    default boolean putInt(String str, int i) {
        return putString(str, String.valueOf(i));
    }

    default boolean putLong(String str, long j) {
        return putString(str, String.valueOf(j));
    }

    boolean putString(String str, String str2);

    boolean putString(String str, String str2, String str3, boolean z);

    default void registerContentObserverSync(Uri uri, boolean z, ContentObserver contentObserver) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("SP#registerObserver#[" + uri + "]");
        }
        try {
            getContentResolver().registerContentObserver(uri, z, contentObserver);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    default void unregisterContentObserverSync(ContentObserver contentObserver) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("SP#unregisterObserver");
        }
        try {
            getContentResolver().unregisterContentObserver(contentObserver);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    default boolean getBool(String str) throws Settings.SettingNotFoundException {
        return getInt(str) != 0;
    }

    default float getFloat(String str) throws Settings.SettingNotFoundException {
        return Companion.parseFloatOrThrow(str, getString(str));
    }

    default int getInt(String str) throws Settings.SettingNotFoundException {
        try {
            return Integer.parseInt(getString(str));
        } catch (NumberFormatException unused) {
            throw new Settings.SettingNotFoundException(str);
        }
    }

    default long getLong(String str) throws Settings.SettingNotFoundException {
        return Companion.parseLongOrThrow(str, getString(str));
    }

    default void registerContentObserverSync(String str, ContentObserver contentObserver) {
        registerContentObserverSync(getUriFor(str), contentObserver);
    }

    default void registerContentObserverSync(Uri uri, ContentObserver contentObserver) {
        registerContentObserverSync(uri, false, contentObserver);
    }

    default void registerContentObserverSync(String str, boolean z, ContentObserver contentObserver) {
        registerContentObserverSync(getUriFor(str), z, contentObserver);
    }
}
