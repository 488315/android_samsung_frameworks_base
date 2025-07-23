package android.preference;

import java.util.Set;

@Deprecated
/* loaded from: classes3.dex */
public interface PreferenceDataStore {
    default boolean getBoolean(String str, boolean z) {
        return z;
    }

    default float getFloat(String str, float f) {
        return f;
    }

    default int getInt(String str, int i) {
        return i;
    }

    default long getLong(String str, long j) {
        return j;
    }

    default String getString(String str, String str2) {
        return str2;
    }

    default Set<String> getStringSet(String str, Set<String> set) {
        return set;
    }

    default void putString(String str, String str2) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default void putStringSet(String str, Set<String> set) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default void putInt(String str, int i) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default void putLong(String str, long j) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default void putFloat(String str, float f) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default void putBoolean(String str, boolean z) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }
}
