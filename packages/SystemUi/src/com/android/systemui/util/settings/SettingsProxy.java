package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SettingsProxy {
    default boolean getBoolForUser(int i, String str, boolean z) {
        return getIntForUser(z ? 1 : 0, i, str) != 0;
    }

    ContentResolver getContentResolver();

    default float getFloatForUser(String str, int i, float f) {
        String stringForUser = getStringForUser(i, str);
        if (stringForUser == null) {
            return f;
        }
        try {
            return Float.parseFloat(stringForUser);
        } catch (NumberFormatException unused) {
            return f;
        }
    }

    default int getInt(String str, int i) {
        return getIntForUser(i, getUserId(), str);
    }

    default int getIntForUser(int i, int i2, String str) {
        String stringForUser = getStringForUser(i2, str);
        if (stringForUser == null) {
            return i;
        }
        try {
            return Integer.parseInt(stringForUser);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    default int getRealUserHandle(int i) {
        return i != -2 ? i : ((UserTrackerImpl) getUserTracker()).getUserId();
    }

    String getStringForUser(int i, String str);

    Uri getUriFor(String str);

    default int getUserId() {
        return getContentResolver().getUserId();
    }

    UserTracker getUserTracker();

    default boolean putIntForUser(int i, int i2, String str) {
        return putStringForUser(i2, str, Integer.toString(i));
    }

    boolean putStringForUser(int i, String str, String str2);

    default void registerContentObserverForUser(String str, ContentObserver contentObserver, int i) {
        registerContentObserverForUser(getUriFor(str), false, contentObserver, i);
    }

    default void unregisterContentObserver(ContentObserver contentObserver) {
        getContentResolver().unregisterContentObserver(contentObserver);
    }

    default void registerContentObserverForUser(String str, boolean z, ContentObserver contentObserver, int i) {
        registerContentObserverForUser(getUriFor(str), z, contentObserver, i);
    }

    default void registerContentObserverForUser(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        getContentResolver().registerContentObserver(uri, z, contentObserver, getRealUserHandle(i));
    }
}
