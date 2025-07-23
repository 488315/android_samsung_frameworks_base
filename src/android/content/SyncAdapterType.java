package android.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes.dex */
public class SyncAdapterType implements Parcelable {
    public static final Parcelable.Creator<SyncAdapterType> CREATOR = new Parcelable.Creator<SyncAdapterType>() { // from class: android.content.SyncAdapterType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncAdapterType createFromParcel(Parcel parcel) {
            return new SyncAdapterType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncAdapterType[] newArray(int i) {
            return new SyncAdapterType[i];
        }
    };
    public final String accountType;
    private final boolean allowParallelSyncs;
    public final String authority;
    private final boolean isAlwaysSyncable;
    public final boolean isKey;
    private final String packageName;
    private final String settingsActivity;
    private final boolean supportsUploading;
    private final boolean userVisible;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SyncAdapterType(String str, String str2, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the authority must not be empty: " + str);
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("the accountType must not be empty: " + str2);
        }
        this.authority = str;
        this.accountType = str2;
        this.userVisible = z;
        this.supportsUploading = z2;
        this.isAlwaysSyncable = false;
        this.allowParallelSyncs = false;
        this.settingsActivity = null;
        this.isKey = false;
        this.packageName = null;
    }

    public SyncAdapterType(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, String str3, String str4) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the authority must not be empty: " + str);
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("the accountType must not be empty: " + str2);
        }
        this.authority = str;
        this.accountType = str2;
        this.userVisible = z;
        this.supportsUploading = z2;
        this.isAlwaysSyncable = z3;
        this.allowParallelSyncs = z4;
        this.settingsActivity = str3;
        this.isKey = false;
        this.packageName = str4;
    }

    private SyncAdapterType(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the authority must not be empty: " + str);
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("the accountType must not be empty: " + str2);
        }
        this.authority = str;
        this.accountType = str2;
        this.userVisible = true;
        this.supportsUploading = true;
        this.isAlwaysSyncable = false;
        this.allowParallelSyncs = false;
        this.settingsActivity = null;
        this.isKey = true;
        this.packageName = null;
    }

    public boolean supportsUploading() {
        if (this.isKey) {
            throw new IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.supportsUploading;
    }

    public boolean isUserVisible() {
        if (this.isKey) {
            throw new IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.userVisible;
    }

    public boolean allowParallelSyncs() {
        if (this.isKey) {
            throw new IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.allowParallelSyncs;
    }

    public boolean isAlwaysSyncable() {
        if (this.isKey) {
            throw new IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.isAlwaysSyncable;
    }

    public String getSettingsActivity() {
        if (this.isKey) {
            throw new IllegalStateException("this method is not allowed to be called when this is a key");
        }
        return this.settingsActivity;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public static SyncAdapterType newKey(String str, String str2) {
        return new SyncAdapterType(str, str2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SyncAdapterType)) {
            return false;
        }
        SyncAdapterType syncAdapterType = (SyncAdapterType) obj;
        return this.authority.equals(syncAdapterType.authority) && this.accountType.equals(syncAdapterType.accountType);
    }

    public int hashCode() {
        return ((527 + this.authority.hashCode()) * 31) + this.accountType.hashCode();
    }

    public String toString() {
        if (this.isKey) {
            return "SyncAdapterType Key {name=" + this.authority + ", type=" + this.accountType + "}";
        }
        return "SyncAdapterType {name=" + this.authority + ", type=" + this.accountType + ", userVisible=" + this.userVisible + ", supportsUploading=" + this.supportsUploading + ", isAlwaysSyncable=" + this.isAlwaysSyncable + ", allowParallelSyncs=" + this.allowParallelSyncs + ", settingsActivity=" + this.settingsActivity + ", packageName=" + this.packageName + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.isKey) {
            throw new IllegalStateException("keys aren't parcelable");
        }
        parcel.writeString(this.authority);
        parcel.writeString(this.accountType);
        parcel.writeInt(this.userVisible ? 1 : 0);
        parcel.writeInt(this.supportsUploading ? 1 : 0);
        parcel.writeInt(this.isAlwaysSyncable ? 1 : 0);
        parcel.writeInt(this.allowParallelSyncs ? 1 : 0);
        parcel.writeString(this.settingsActivity);
        parcel.writeString(this.packageName);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SyncAdapterType(android.os.Parcel r10) {
        /*
            r9 = this;
            java.lang.String r1 = r10.readString()
            java.lang.String r2 = r10.readString()
            int r0 = r10.readInt()
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L12
            r0 = r3
            goto L14
        L12:
            r0 = r3
            r3 = r4
        L14:
            int r5 = r10.readInt()
            if (r5 == 0) goto L1d
            r5 = r4
            r4 = r0
            goto L1e
        L1d:
            r5 = r4
        L1e:
            int r6 = r10.readInt()
            if (r6 == 0) goto L27
            r6 = r5
            r5 = r0
            goto L28
        L27:
            r6 = r5
        L28:
            int r7 = r10.readInt()
            if (r7 == 0) goto L2f
            r6 = r0
        L2f:
            java.lang.String r7 = r10.readString()
            java.lang.String r8 = r10.readString()
            r0 = r9
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.SyncAdapterType.<init>(android.os.Parcel):void");
    }
}
