package android.os;

import android.annotation.SystemApi;
import android.os.Parcelable;
import android.provider.Telephony;
import android.text.format.DateFormat;
import android.util.SparseArray;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: classes3.dex */
public final class UserHandle implements Parcelable {
    public static final int AID_APP_END = 19999;
    public static final int AID_APP_START = 10000;
    public static final int AID_CACHE_GID_START = 20000;
    public static final int AID_ROOT = 0;
    public static final int AID_SHARED_GID_START = 50000;
    public static final Parcelable.Creator<UserHandle> CREATOR;
    public static final int ERR_GID = -1;
    public static final int MAX_EXTRA_USER_HANDLE_CACHE_SIZE = 32;
    public static final int MAX_SECONDARY_USER_ID = 21473;
    public static final int MIN_SECONDARY_USER_ID = 10;
    public static final boolean MU_ENABLED = true;
    private static final int NUM_CACHED_USERS = 8;
    public static final int PER_USER_RANGE = 100000;
    public static final UserHandle SEM_ALL;
    public static final UserHandle SEM_CURRENT;
    public static final UserHandle SEM_OWNER;
    public static final int SEM_USER_CURRENT = -2;
    public static final int SEM_USER_NULL = -10000;
    public static final int SEM_USER_OWNER = 0;
    public static final int USER_ALL = -1;
    public static final int USER_CURRENT = -2;
    public static final int USER_CURRENT_OR_SELF = -3;
    public static final int USER_NULL = -10000;

    @Deprecated
    public static final int USER_OWNER = 0;
    public static final int USER_SERIAL_SYSTEM = 0;
    public static final int USER_SYSTEM = 0;
    final int mHandle;

    @SystemApi
    public static final UserHandle ALL = new UserHandle(-1);

    @SystemApi
    public static final UserHandle CURRENT = new UserHandle(-2);
    public static final UserHandle CURRENT_OR_SELF = new UserHandle(-3);
    private static final UserHandle NULL = new UserHandle(-10000);

    @Deprecated
    public static final UserHandle OWNER = new UserHandle(0);

    @SystemApi
    public static final UserHandle SYSTEM = new UserHandle(0);
    private static final UserHandle[] CACHED_USER_HANDLES = new UserHandle[8];
    public static final SparseArray<UserHandle> sExtraUserHandleCache = new SparseArray<>(0);

    public static int getSharedAppGid(int i, int i2) {
        if (i2 >= 10000 && i2 <= 19999) {
            return i2 - (-40000);
        }
        if (i2 < 0 || i2 > 10000) {
            return -1;
        }
        return i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        int i = 0;
        while (true) {
            UserHandle[] userHandleArr = CACHED_USER_HANDLES;
            if (i < userHandleArr.length) {
                userHandleArr[i] = new UserHandle(i + 10);
                i++;
            } else {
                CREATOR = new Parcelable.Creator<UserHandle>() { // from class: android.os.UserHandle.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public UserHandle createFromParcel(Parcel parcel) {
                        return UserHandle.of(parcel.readInt());
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public UserHandle[] newArray(int i2) {
                        return new UserHandle[i2];
                    }
                };
                SEM_ALL = ALL;
                SEM_OWNER = OWNER;
                SEM_CURRENT = CURRENT;
                return;
            }
        }
    }

    public static boolean isSameUser(int i, int i2) {
        return getUserId(i) == getUserId(i2);
    }

    public static boolean isSameApp(int i, int i2) {
        return getAppId(i) == getAppId(i2);
    }

    public static boolean isIsolated(int i) {
        if (i > 0) {
            return Process.isIsolated(i);
        }
        return false;
    }

    public static boolean isApp(int i) {
        int appId;
        return i > 0 && (appId = getAppId(i)) >= 10000 && appId <= 19999;
    }

    public static boolean isCore(int i) {
        return i >= 0 && getAppId(i) < 10000;
    }

    public static boolean isSharedAppGid(int i) {
        return getAppIdFromSharedAppGid(i) != -1;
    }

    public static UserHandle getUserHandleForUid(int i) {
        return of(getUserId(i));
    }

    public static int getUserId(int i) {
        return i / 100000;
    }

    public static final int semGetUserId(int i) {
        return getUserId(i);
    }

    public static int getCallingUserId() {
        return getUserId(Binder.getCallingUid());
    }

    public static final int semGetCallingUserId() {
        return getCallingUserId();
    }

    public static int getCallingAppId() {
        return getAppId(Binder.getCallingUid());
    }

    public static int[] fromUserHandles(List<UserHandle> list) {
        int size = list.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = list.get(i).getIdentifier();
        }
        return iArr;
    }

    public static List<UserHandle> toUserHandles(int[] iArr) {
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(of(i));
        }
        return arrayList;
    }

    @SystemApi
    public static UserHandle of(int i) {
        if (i == 0) {
            return SYSTEM;
        }
        if (i == -3) {
            return CURRENT_OR_SELF;
        }
        if (i == -2) {
            return CURRENT;
        }
        if (i == -1) {
            return ALL;
        }
        if (i >= 10) {
            UserHandle[] userHandleArr = CACHED_USER_HANDLES;
            if (i < userHandleArr.length + 10) {
                return userHandleArr[i - 10];
            }
        }
        if (i == -10000) {
            return NULL;
        }
        return getUserHandleFromExtraCache(i);
    }

    public static UserHandle getUserHandleFromExtraCache(int i) {
        SparseArray<UserHandle> sparseArray = sExtraUserHandleCache;
        synchronized (sparseArray) {
            UserHandle userHandle = sparseArray.get(i);
            if (userHandle != null) {
                return userHandle;
            }
            if (sparseArray.size() >= 32) {
                sparseArray.removeAt(new Random().nextInt(32));
            }
            UserHandle userHandle2 = new UserHandle(i);
            sparseArray.put(i, userHandle2);
            return userHandle2;
        }
    }

    public static int getUid(int i, int i2) {
        return i2 >= 0 ? (i * 100000) + (i2 % 100000) : i2;
    }

    @SystemApi
    public int getUid(int i) {
        return getUid(getIdentifier(), i);
    }

    @SystemApi
    public static int getAppId(int i) {
        return i % 100000;
    }

    public static int getUserGid(int i) {
        return getUid(i, Process.SHARED_USER_GID);
    }

    @SystemApi
    public static int getSharedAppGid(int i) {
        return getSharedAppGid(getUserId(i), getAppId(i));
    }

    public static int getAppIdFromSharedAppGid(int i) {
        int appId = getAppId(i) - 40000;
        if (appId < 0 || appId >= 50000) {
            return -1;
        }
        return appId;
    }

    public static int getCacheAppGid(int i) {
        return getCacheAppGid(getUserId(i), getAppId(i));
    }

    public static int getCacheAppGid(int i, int i2) {
        if (i2 < 10000 || i2 > 19999) {
            return -1;
        }
        return getUid(i, i2 + 10000);
    }

    public static void formatUid(StringBuilder sb, int i) {
        if (i < 10000) {
            sb.append(i);
            return;
        }
        sb.append('u');
        sb.append(getUserId(i));
        int appId = getAppId(i);
        if (isIsolated(appId)) {
            if (appId > 99000) {
                sb.append('i');
                sb.append(appId - Process.FIRST_ISOLATED_UID);
                return;
            } else {
                sb.append("ai");
                sb.append(appId - 90000);
                return;
            }
        }
        if (appId >= 10000) {
            sb.append(DateFormat.AM_PM);
            sb.append(appId - 10000);
        } else {
            sb.append('s');
            sb.append(appId);
        }
    }

    @SystemApi
    public static String formatUid(int i) {
        StringBuilder sb = new StringBuilder();
        formatUid(sb, i);
        return sb.toString();
    }

    public static void formatUid(PrintWriter printWriter, int i) {
        if (i < 10000) {
            printWriter.print(i);
            return;
        }
        printWriter.print('u');
        printWriter.print(getUserId(i));
        int appId = getAppId(i);
        if (isIsolated(appId)) {
            if (appId > 99000) {
                printWriter.print('i');
                printWriter.print(appId - Process.FIRST_ISOLATED_UID);
                return;
            } else {
                printWriter.print("ai");
                printWriter.print(appId - 90000);
                return;
            }
        }
        if (appId >= 10000) {
            printWriter.print(DateFormat.AM_PM);
            printWriter.print(appId - 10000);
        } else {
            printWriter.print('s');
            printWriter.print(appId);
        }
    }

    public static int parseUserArg(String str) {
        if ("all".equals(str)) {
            return -1;
        }
        if (Telephony.Carriers.CURRENT.equals(str) || "cur".equals(str)) {
            return -2;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            throw new IllegalArgumentException("Bad user number: " + str);
        }
    }

    @SystemApi
    public static int myUserId() {
        return getUserId(Process.myUid());
    }

    private static final int hidden_myUserId() {
        return myUserId();
    }

    public static final int semGetMyUserId() {
        return myUserId();
    }

    @SystemApi
    @Deprecated
    public boolean isOwner() {
        return equals(OWNER);
    }

    @SystemApi
    public boolean isSystem() {
        return equals(SYSTEM);
    }

    public UserHandle(int i) {
        this.mHandle = i;
    }

    @SystemApi
    public int getIdentifier() {
        return this.mHandle;
    }

    public int semGetIdentifier() {
        return getIdentifier();
    }

    public String toString() {
        return "UserHandle{" + this.mHandle + "}";
    }

    public boolean equals(Object obj) {
        return (obj instanceof UserHandle) && this.mHandle == ((UserHandle) obj).mHandle;
    }

    public int hashCode() {
        return this.mHandle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mHandle);
    }

    public static void writeToParcel(UserHandle userHandle, Parcel parcel) {
        if (userHandle != null) {
            userHandle.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(-10000);
        }
    }

    public static UserHandle readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt != -10000) {
            return new UserHandle(readInt);
        }
        return null;
    }

    public UserHandle(Parcel parcel) {
        this.mHandle = parcel.readInt();
    }

    public static UserHandle semOf(int i) {
        return of(i);
    }
}
