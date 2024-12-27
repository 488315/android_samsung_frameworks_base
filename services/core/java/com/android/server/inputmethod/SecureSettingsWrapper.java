package com.android.server.inputmethod;

import android.app.ActivityManagerInternal;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;

import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;

public abstract class SecureSettingsWrapper {
    public static final ArraySet CLONE_TO_MANAGED_PROFILE;
    public static final AnonymousClass1 NOOP;
    public static volatile ContentResolver sContentResolver;
    public static final SparseArray sUserMap;

    /* renamed from: com.android.server.inputmethod.SecureSettingsWrapper$1, reason: invalid class name */
    public final class AnonymousClass1 implements ReaderWriter {
        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public final int getInt(int i, String str) {
            return i;
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public final String getString(String str, String str2) {
            return str2;
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public final void putInt(int i, String str) {}

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public final void putString(String str, String str2) {}
    }

    public final class LockedUserImpl extends UnlockedUserImpl {
        public final ArrayMap mNonPersistentKeyValues;

        public LockedUserImpl(int i, ContentResolver contentResolver) {
            super(i, contentResolver);
            this.mNonPersistentKeyValues = new ArrayMap();
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl,
                  // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public final int getInt(int i, String str) {
            synchronized (this.mNonPersistentKeyValues) {
                try {
                    if (!this.mNonPersistentKeyValues.containsKey(str)) {
                        return super.getInt(i, str);
                    }
                    String str2 = (String) this.mNonPersistentKeyValues.get(str);
                    if (str2 != null) {
                        i = Integer.parseInt(str2);
                    }
                    return i;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl,
                  // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public final String getString(String str, String str2) {
            synchronized (this.mNonPersistentKeyValues) {
                try {
                    if (!this.mNonPersistentKeyValues.containsKey(str)) {
                        return super.getString(str, str2);
                    }
                    String str3 = (String) this.mNonPersistentKeyValues.get(str);
                    if (str3 != null) {
                        str2 = str3;
                    }
                    return str2;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl,
                  // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public final void putInt(int i, String str) {
            synchronized (this.mNonPersistentKeyValues) {
                this.mNonPersistentKeyValues.put(str, String.valueOf(i));
            }
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.UnlockedUserImpl,
                  // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public final void putString(String str, String str2) {
            synchronized (this.mNonPersistentKeyValues) {
                this.mNonPersistentKeyValues.put(str, str2);
            }
        }
    }

    public interface ReaderWriter {
        int getInt(int i, String str);

        String getString(String str, String str2);

        void putInt(int i, String str);

        void putString(String str, String str2);
    }

    public class UnlockedUserImpl implements ReaderWriter {
        public final ContentResolver mContentResolver;
        public final int mUserId;

        public UnlockedUserImpl(int i, ContentResolver contentResolver) {
            this.mUserId = i;
            this.mContentResolver = contentResolver;
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public int getInt(int i, String str) {
            return Settings.Secure.getIntForUser(this.mContentResolver, str, i, this.mUserId);
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public String getString(String str, String str2) {
            String stringForUser =
                    Settings.Secure.getStringForUser(this.mContentResolver, str, this.mUserId);
            return stringForUser != null ? stringForUser : str2;
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public void putInt(int i, String str) {
            boolean contains = SecureSettingsWrapper.CLONE_TO_MANAGED_PROFILE.contains(str);
            int i2 = this.mUserId;
            if (contains) {
                i2 =
                        ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                                .getProfileParentId(i2);
            }
            Settings.Secure.putIntForUser(this.mContentResolver, str, i, i2);
        }

        @Override // com.android.server.inputmethod.SecureSettingsWrapper.ReaderWriter
        public void putString(String str, String str2) {
            boolean contains = SecureSettingsWrapper.CLONE_TO_MANAGED_PROFILE.contains(str);
            int i = this.mUserId;
            if (contains) {
                i =
                        ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class))
                                .getProfileParentId(i);
            }
            Settings.Secure.putStringForUser(this.mContentResolver, str, str2, i);
        }
    }

    static {
        ArraySet arraySet = new ArraySet();
        CLONE_TO_MANAGED_PROFILE = arraySet;
        Settings.Secure.getCloneToManagedProfileSettings(arraySet);
        sUserMap = new SparseArray();
        NOOP = new AnonymousClass1();
    }

    public static UnlockedUserImpl createImpl(UserManagerInternal userManagerInternal, int i) {
        return userManagerInternal.isUserUnlockingOrUnlocked(i)
                ? new UnlockedUserImpl(i, sContentResolver)
                : new LockedUserImpl(i, sContentResolver);
    }

    public static ReaderWriter get(int i) {
        SparseArray sparseArray = sUserMap;
        synchronized (sparseArray) {
            try {
                ReaderWriter readerWriter = (ReaderWriter) sparseArray.get(i);
                if (readerWriter != null) {
                    return readerWriter;
                }
                UserManagerInternal userManagerInternal =
                        (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
                return !userManagerInternal.exists(i)
                        ? NOOP
                        : putOrGet(i, createImpl(userManagerInternal, i));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static int getInt(int i, int i2, String str) {
        return get(i2).getInt(i, str);
    }

    public static String getString(String str, String str2, int i) {
        return get(i).getString(str, str2);
    }

    public static void onStart(Context context) {
        sContentResolver = context.getContentResolver();
        final int currentUserId =
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class))
                        .getCurrentUserId();
        UserManagerInternal userManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        putOrGet(currentUserId, createImpl(userManagerInternal, currentUserId));
        userManagerInternal.addUserLifecycleListener(
                new UserManagerInternal
                        .UserLifecycleListener() { // from class:
                                                   // com.android.server.inputmethod.SecureSettingsWrapper.2
                    @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
                    public final void onUserRemoved(UserInfo userInfo) {
                        SparseArray sparseArray = SecureSettingsWrapper.sUserMap;
                        synchronized (sparseArray) {
                            sparseArray.remove(currentUserId);
                        }
                    }
                });
    }

    public static ReaderWriter putOrGet(int i, UnlockedUserImpl unlockedUserImpl) {
        SparseArray sparseArray = sUserMap;
        synchronized (sparseArray) {
            try {
                ReaderWriter readerWriter = (ReaderWriter) sparseArray.get(i);
                if (readerWriter == null) {
                    sparseArray.put(i, unlockedUserImpl);
                    return unlockedUserImpl;
                }
                if (!(readerWriter instanceof LockedUserImpl)) {
                    return readerWriter;
                }
                sparseArray.put(i, unlockedUserImpl);
                return unlockedUserImpl;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
