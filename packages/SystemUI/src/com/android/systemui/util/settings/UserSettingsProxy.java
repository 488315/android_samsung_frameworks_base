package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Trace;
import android.provider.Settings;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface UserSettingsProxy extends SettingsProxy {
    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean getBool(String str, boolean z) {
        return getBoolForUser(str, z, getUserId());
    }

    default boolean getBoolForUser(String str, boolean z, int i) {
        return getIntForUser(str, z ? 1 : 0, i) != 0;
    }

    default float getFloatForUser(String str, float f, int i) {
        return SettingsProxy.Companion.parseFloat(getStringForUser(str, i), f);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default int getInt(String str, int i) {
        return getIntForUser(str, i, getUserId());
    }

    default int getIntForUser(String str, int i, int i2) {
        try {
            return Integer.parseInt(getStringForUser(str, i2));
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    default long getLongForUser(String str, long j, int i) {
        return SettingsProxy.Companion.parseLongOrUseDefault(getStringForUser(str, i), j);
    }

    default int getRealUserHandle(int i) {
        return i != -2 ? i : ((UserTrackerImpl) getUserTracker()).getUserId();
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default String getString(String str) {
        return getStringForUser(str, getUserId());
    }

    String getStringForUser(String str, int i);

    default int getUserId() {
        return getContentResolver().getUserId();
    }

    UserTracker getUserTracker();

    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean putBool(String str, boolean z) {
        return putBoolForUser(str, z, getUserId());
    }

    default boolean putBoolForUser(String str, boolean z, int i) {
        return putIntForUser(str, z ? 1 : 0, i);
    }

    default boolean putFloatForUser(String str, float f, int i) {
        return putStringForUser(str, String.valueOf(f), i);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean putInt(String str, int i) {
        return putIntForUser(str, i, getUserId());
    }

    default boolean putIntForUser(String str, int i, int i2) {
        return putStringForUser(str, String.valueOf(i), i2);
    }

    default boolean putLongForUser(String str, long j, int i) {
        return putStringForUser(str, String.valueOf(j), i);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean putString(String str, String str2) {
        return putStringForUser(str, str2, getUserId());
    }

    boolean putString(String str, String str2, boolean z);

    boolean putStringForUser(String str, String str2, int i);

    boolean putStringForUser(String str, String str2, String str3, boolean z, int i, boolean z2);

    default void registerContentObserverForUserSync(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("USP#registerObserver#[" + uri + "]");
        }
        try {
            getContentResolver().registerContentObserver(uri, z, contentObserver, getRealUserHandle(i));
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default void registerContentObserverSync(Uri uri, ContentObserver contentObserver) {
        registerContentObserverForUserSync(uri, contentObserver, getUserId());
    }

    default void setUserId(int i) {
        throw new UnsupportedOperationException("userId cannot be set in interface, use setter from an implementation instead.");
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean getBool(String str) throws Settings.SettingNotFoundException {
        return getBoolForUser(str, getUserId());
    }

    default boolean getBoolForUser(String str, int i) throws Settings.SettingNotFoundException {
        return getIntForUser(str, i) != 0;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default int getInt(String str) throws Settings.SettingNotFoundException {
        return getIntForUser(str, getUserId());
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default void registerContentObserverSync(Uri uri, boolean z, ContentObserver contentObserver) {
        registerContentObserverForUserSync(uri, z, contentObserver, getUserId());
    }

    default float getFloatForUser(String str, int i) throws Settings.SettingNotFoundException {
        return SettingsProxy.Companion.parseFloatOrThrow(str, getStringForUser(str, i));
    }

    default int getIntForUser(String str, int i) throws Settings.SettingNotFoundException {
        try {
            return Integer.parseInt(getStringForUser(str, i));
        } catch (NumberFormatException unused) {
            throw new Settings.SettingNotFoundException(str);
        }
    }

    default long getLongForUser(String str, int i) throws Settings.SettingNotFoundException {
        return SettingsProxy.Companion.parseLongOrThrow(str, getStringForUser(str, i));
    }

    default void registerContentObserverForUserSync(String str, ContentObserver contentObserver, int i) {
        registerContentObserverForUserSync(getUriFor(str), contentObserver, i);
    }

    default void registerContentObserverForUserSync(Uri uri, ContentObserver contentObserver, int i) {
        registerContentObserverForUserSync(uri, false, contentObserver, i);
    }

    default void registerContentObserverForUserSync(String str, boolean z, ContentObserver contentObserver, int i) {
        registerContentObserverForUserSync(getUriFor(str), z, contentObserver, i);
    }
}
