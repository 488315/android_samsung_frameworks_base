package android.provider;

import android.annotation.SystemApi;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class E2eeContactKeysManager {
    private static final int ARRAY_IS_NULL = -1;
    public static final String AUTHORITY = "com.android.contactkeys.contactkeysprovider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://com.android.contactkeys.contactkeysprovider");
    private static final int MAX_KEY_SIZE_BYTES = 5000;
    public static final int VERIFICATION_STATE_UNVERIFIED = 0;
    public static final int VERIFICATION_STATE_VERIFICATION_FAILED = 1;
    public static final int VERIFICATION_STATE_VERIFIED = 2;
    private final ContentResolver mContentResolver;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VerificationState {
    }

    public static int getMaxKeySizeBytes() {
        return 5000;
    }

    public E2eeContactKeysManager(Context context) {
        Objects.requireNonNull(context);
        this.mContentResolver = context.getContentResolver();
    }

    public void updateOrInsertE2eeContactKey(String str, String str2, String str3, byte[] bArr) {
        validateKeyLength(bArr);
        Bundle bundle = new Bundle();
        bundle.putString("lookup", (String) Objects.requireNonNull(str));
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str2));
        bundle.putString("account_id", (String) Objects.requireNonNull(str3));
        bundle.putByteArray(E2eeContactKeys.KEY_VALUE, (byte[]) Objects.requireNonNull(bArr));
        nullSafeCall(this.mContentResolver, E2eeContactKeys.UPDATE_OR_INSERT_CONTACT_KEY_METHOD, bundle);
    }

    public E2eeContactKey getE2eeContactKey(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("lookup", (String) Objects.requireNonNull(str));
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str2));
        bundle.putString("account_id", (String) Objects.requireNonNull(str3));
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.GET_CONTACT_KEY_METHOD, bundle);
        if (bundleNullSafeCall == null) {
            return null;
        }
        return (E2eeContactKey) bundleNullSafeCall.getParcelable(E2eeContactKeys.KEY_CONTACT_KEY, E2eeContactKey.class);
    }

    public List<E2eeContactKey> getAllE2eeContactKeys(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("lookup", (String) Objects.requireNonNull(str));
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.GET_ALL_CONTACT_KEYS_METHOD, bundle);
        if (bundleNullSafeCall == null) {
            return new ArrayList();
        }
        ArrayList parcelableArrayList = bundleNullSafeCall.getParcelableArrayList(E2eeContactKeys.KEY_CONTACT_KEYS, E2eeContactKey.class);
        return parcelableArrayList == null ? new ArrayList() : parcelableArrayList;
    }

    public List<E2eeContactKey> getOwnerE2eeContactKeys(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("lookup", (String) Objects.requireNonNull(str));
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.GET_OWNER_CONTACT_KEYS_METHOD, bundle);
        if (bundleNullSafeCall == null) {
            return new ArrayList();
        }
        ArrayList parcelableArrayList = bundleNullSafeCall.getParcelableArrayList(E2eeContactKeys.KEY_CONTACT_KEYS, E2eeContactKey.class);
        return parcelableArrayList == null ? new ArrayList() : parcelableArrayList;
    }

    public boolean updateE2eeContactKeyLocalVerificationState(String str, String str2, String str3, int i) {
        validateVerificationState(i);
        Bundle bundle = new Bundle();
        bundle.putString("lookup", (String) Objects.requireNonNull(str));
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str2));
        bundle.putString("account_id", (String) Objects.requireNonNull(str3));
        bundle.putInt(E2eeContactKeys.LOCAL_VERIFICATION_STATE, i);
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.UPDATE_CONTACT_KEY_LOCAL_VERIFICATION_STATE_METHOD, bundle);
        return bundleNullSafeCall != null && bundleNullSafeCall.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    @SystemApi
    public boolean updateE2eeContactKeyLocalVerificationState(String str, String str2, String str3, String str4, int i) {
        validateVerificationState(i);
        Bundle bundle = new Bundle();
        bundle.putString("lookup", (String) Objects.requireNonNull(str));
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str2));
        bundle.putString("account_id", (String) Objects.requireNonNull(str3));
        bundle.putString(E2eeContactKeys.OWNER_PACKAGE_NAME, (String) Objects.requireNonNull(str4));
        bundle.putInt(E2eeContactKeys.LOCAL_VERIFICATION_STATE, i);
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.UPDATE_CONTACT_KEY_LOCAL_VERIFICATION_STATE_METHOD, bundle);
        return bundleNullSafeCall != null && bundleNullSafeCall.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    public boolean updateE2eeContactKeyRemoteVerificationState(String str, String str2, String str3, int i) {
        validateVerificationState(i);
        Bundle bundle = new Bundle();
        bundle.putString("lookup", (String) Objects.requireNonNull(str));
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str2));
        bundle.putString("account_id", (String) Objects.requireNonNull(str3));
        bundle.putInt(E2eeContactKeys.REMOTE_VERIFICATION_STATE, i);
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.UPDATE_CONTACT_KEY_REMOTE_VERIFICATION_STATE_METHOD, bundle);
        return bundleNullSafeCall != null && bundleNullSafeCall.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    @SystemApi
    public boolean updateE2eeContactKeyRemoteVerificationState(String str, String str2, String str3, String str4, int i) {
        validateVerificationState(i);
        Bundle bundle = new Bundle();
        bundle.putString("lookup", (String) Objects.requireNonNull(str));
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str2));
        bundle.putString("account_id", (String) Objects.requireNonNull(str3));
        bundle.putString(E2eeContactKeys.OWNER_PACKAGE_NAME, (String) Objects.requireNonNull(str4));
        bundle.putInt(E2eeContactKeys.REMOTE_VERIFICATION_STATE, i);
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.UPDATE_CONTACT_KEY_REMOTE_VERIFICATION_STATE_METHOD, bundle);
        return bundleNullSafeCall != null && bundleNullSafeCall.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    private static void validateVerificationState(int i) {
        if (i == 0 || i == 1 || i == 2) {
            return;
        }
        throw new IllegalArgumentException("Verification state value " + i + " is not supported");
    }

    public boolean removeE2eeContactKey(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("lookup", (String) Objects.requireNonNull(str));
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str2));
        bundle.putString("account_id", (String) Objects.requireNonNull(str3));
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.REMOVE_CONTACT_KEY_METHOD, bundle);
        return bundleNullSafeCall != null && bundleNullSafeCall.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    public boolean updateOrInsertE2eeSelfKey(String str, String str2, byte[] bArr) {
        validateKeyLength(bArr);
        Bundle bundle = new Bundle();
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str));
        bundle.putString("account_id", (String) Objects.requireNonNull(str2));
        bundle.putByteArray(E2eeContactKeys.KEY_VALUE, (byte[]) Objects.requireNonNull(bArr));
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.UPDATE_OR_INSERT_SELF_KEY_METHOD, bundle);
        return bundleNullSafeCall != null && bundleNullSafeCall.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    private static void validateKeyLength(byte[] bArr) {
        Objects.requireNonNull(bArr);
        if (bArr.length == 0 || bArr.length > getMaxKeySizeBytes()) {
            throw new IllegalArgumentException("Key value length is " + bArr.length + ". Should be more than 0 and less than " + getMaxKeySizeBytes());
        }
    }

    public boolean updateE2eeSelfKeyRemoteVerificationState(String str, String str2, int i) {
        validateVerificationState(i);
        Bundle bundle = new Bundle();
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str));
        bundle.putString("account_id", (String) Objects.requireNonNull(str2));
        bundle.putInt(E2eeContactKeys.REMOTE_VERIFICATION_STATE, i);
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.UPDATE_SELF_KEY_REMOTE_VERIFICATION_STATE_METHOD, bundle);
        return bundleNullSafeCall != null && bundleNullSafeCall.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    @SystemApi
    public boolean updateE2eeSelfKeyRemoteVerificationState(String str, String str2, String str3, int i) {
        validateVerificationState(i);
        Bundle bundle = new Bundle();
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str));
        bundle.putString("account_id", (String) Objects.requireNonNull(str2));
        bundle.putString(E2eeContactKeys.OWNER_PACKAGE_NAME, (String) Objects.requireNonNull(str3));
        bundle.putInt(E2eeContactKeys.REMOTE_VERIFICATION_STATE, i);
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.UPDATE_SELF_KEY_REMOTE_VERIFICATION_STATE_METHOD, bundle);
        return bundleNullSafeCall != null && bundleNullSafeCall.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    public E2eeSelfKey getE2eeSelfKey(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str));
        bundle.putString("account_id", (String) Objects.requireNonNull(str2));
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.GET_SELF_KEY_METHOD, bundle);
        if (bundleNullSafeCall == null) {
            return null;
        }
        return (E2eeSelfKey) bundleNullSafeCall.getParcelable(E2eeContactKeys.KEY_CONTACT_KEY, E2eeSelfKey.class);
    }

    public List<E2eeSelfKey> getAllE2eeSelfKeys() {
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.GET_ALL_SELF_KEYS_METHOD, new Bundle());
        if (bundleNullSafeCall == null) {
            return new ArrayList();
        }
        ArrayList parcelableArrayList = bundleNullSafeCall.getParcelableArrayList(E2eeContactKeys.KEY_CONTACT_KEYS, E2eeSelfKey.class);
        return parcelableArrayList == null ? new ArrayList() : parcelableArrayList;
    }

    public List<E2eeSelfKey> getOwnerE2eeSelfKeys() {
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.GET_OWNER_SELF_KEYS_METHOD, new Bundle());
        if (bundleNullSafeCall == null) {
            return new ArrayList();
        }
        ArrayList parcelableArrayList = bundleNullSafeCall.getParcelableArrayList(E2eeContactKeys.KEY_CONTACT_KEYS, E2eeSelfKey.class);
        return parcelableArrayList == null ? new ArrayList() : parcelableArrayList;
    }

    public boolean removeE2eeSelfKey(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(str));
        bundle.putString("account_id", (String) Objects.requireNonNull(str2));
        Bundle bundleNullSafeCall = nullSafeCall(this.mContentResolver, E2eeContactKeys.REMOVE_SELF_KEY_METHOD, bundle);
        return bundleNullSafeCall != null && bundleNullSafeCall.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    private Bundle nullSafeCall(ContentResolver contentResolver, String str, Bundle bundle) {
        try {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = contentResolver.acquireContentProviderClient(AUTHORITY_URI);
            try {
                Bundle bundleCall = contentProviderClientAcquireContentProviderClient.call(str, null, bundle);
                if (contentProviderClientAcquireContentProviderClient != null) {
                    contentProviderClientAcquireContentProviderClient.close();
                }
                return bundleCall;
            } finally {
            }
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public static final class E2eeContactKeys {
        public static final String ACCOUNT_ID = "account_id";
        public static final String DEVICE_ID = "device_id";
        public static final String DISPLAY_NAME = "display_name";
        public static final String EMAIL_ADDRESS = "address";
        public static final String GET_ALL_CONTACT_KEYS_METHOD = "getAllContactKeys";
        public static final String GET_ALL_SELF_KEYS_METHOD = "getAllSelfKeys";
        public static final String GET_CONTACT_KEY_METHOD = "getContactKey";
        public static final String GET_OWNER_CONTACT_KEYS_METHOD = "getOwnerContactKeys";
        public static final String GET_OWNER_SELF_KEYS_METHOD = "getOwnerSelfKeys";
        public static final String GET_SELF_KEY_METHOD = "getSelfKey";
        public static final String KEY_CONTACT_KEY = "key_contact_key";
        public static final String KEY_CONTACT_KEYS = "key_contact_keys";
        public static final String KEY_UPDATED_ROWS = "key_updated_rows";
        public static final String KEY_VALUE = "key_value";
        public static final String LOCAL_VERIFICATION_STATE = "local_verification_state";
        public static final String LOOKUP_KEY = "lookup";
        public static final String OWNER_PACKAGE_NAME = "owner_package_name";
        public static final String PHONE_NUMBER = "number";
        public static final String REMOTE_VERIFICATION_STATE = "remote_verification_state";
        public static final String REMOVE_CONTACT_KEY_METHOD = "removeContactKey";
        public static final String REMOVE_SELF_KEY_METHOD = "removeSelfKey";
        public static final String TIME_UPDATED = "time_updated";
        public static final String UPDATE_CONTACT_KEY_LOCAL_VERIFICATION_STATE_METHOD = "updateContactKeyLocalVerificationState";
        public static final String UPDATE_CONTACT_KEY_REMOTE_VERIFICATION_STATE_METHOD = "updateContactKeyRemoteVerificationState";
        public static final String UPDATE_OR_INSERT_CONTACT_KEY_METHOD = "updateOrInsertContactKey";
        public static final String UPDATE_OR_INSERT_SELF_KEY_METHOD = "updateOrInsertSelfKey";
        public static final String UPDATE_SELF_KEY_REMOTE_VERIFICATION_STATE_METHOD = "updateSelfKeyRemoteVerificationState";

        private E2eeContactKeys() {
        }
    }

    public static final class E2eeContactKey extends E2eeBaseKey implements Parcelable {
        public static final Parcelable.Creator<E2eeContactKey> CREATOR = new Parcelable.Creator<E2eeContactKey>() { // from class: android.provider.E2eeContactKeysManager.E2eeContactKey.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public E2eeContactKey createFromParcel(Parcel parcel) {
                byte[] bArr;
                String string8 = parcel.readString8();
                String string82 = parcel.readString8();
                String string83 = parcel.readString8();
                long j = parcel.readLong();
                int i = parcel.readInt();
                if (i > 0) {
                    bArr = new byte[i];
                    parcel.readByteArray(bArr);
                } else {
                    bArr = null;
                }
                return new E2eeContactKey(string8, string82, string83, j, bArr, parcel.readInt(), parcel.readInt(), parcel.readString8(), parcel.readString8(), parcel.readString8());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public E2eeContactKey[] newArray(int i) {
                return new E2eeContactKey[i];
            }
        };
        private final String mDisplayName;
        private final String mEmailAddress;
        private final int mLocalVerificationState;
        private final String mPhoneNumber;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ String getAccountId() {
            return super.getAccountId();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ String getDeviceId() {
            return super.getDeviceId();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ byte[] getKeyValue() {
            return super.getKeyValue();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ String getOwnerPackageName() {
            return super.getOwnerPackageName();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ int getRemoteVerificationState() {
            return super.getRemoteVerificationState();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ long getTimeUpdated() {
            return super.getTimeUpdated();
        }

        public E2eeContactKey(String str, String str2, String str3, long j, byte[] bArr, int i, int i2, String str4, String str5, String str6) {
            super(str, str2, str3, j, bArr, i2);
            this.mLocalVerificationState = i;
            this.mDisplayName = str4;
            this.mPhoneNumber = str5;
            this.mEmailAddress = str6;
        }

        public int getLocalVerificationState() {
            return this.mLocalVerificationState;
        }

        public String getDisplayName() {
            return this.mDisplayName;
        }

        public String getPhoneNumber() {
            return this.mPhoneNumber;
        }

        public String getEmailAddress() {
            return this.mEmailAddress;
        }

        public int hashCode() {
            return Objects.hash(this.mDeviceId, this.mAccountId, this.mOwnerPackageName, Long.valueOf(this.mTimeUpdated), Integer.valueOf(Arrays.hashCode(this.mKeyValue)), Integer.valueOf(this.mLocalVerificationState), Integer.valueOf(this.mRemoteVerificationState), this.mDisplayName, this.mPhoneNumber, this.mEmailAddress);
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj instanceof E2eeContactKey) {
                E2eeContactKey e2eeContactKey = (E2eeContactKey) obj;
                if (Objects.equals(this.mDeviceId, e2eeContactKey.mDeviceId) && Objects.equals(this.mAccountId, e2eeContactKey.mAccountId) && Objects.equals(this.mOwnerPackageName, e2eeContactKey.mOwnerPackageName) && this.mTimeUpdated == e2eeContactKey.mTimeUpdated && Arrays.equals(this.mKeyValue, e2eeContactKey.mKeyValue) && this.mLocalVerificationState == e2eeContactKey.mLocalVerificationState && this.mRemoteVerificationState == e2eeContactKey.mRemoteVerificationState && Objects.equals(this.mDisplayName, e2eeContactKey.mDisplayName) && Objects.equals(this.mPhoneNumber, e2eeContactKey.mPhoneNumber) && Objects.equals(this.mEmailAddress, e2eeContactKey.mEmailAddress)) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.mDeviceId);
            parcel.writeString8(this.mAccountId);
            parcel.writeString8(this.mOwnerPackageName);
            parcel.writeLong(this.mTimeUpdated);
            parcel.writeInt(this.mKeyValue != null ? this.mKeyValue.length : -1);
            if (this.mKeyValue != null) {
                parcel.writeByteArray(this.mKeyValue);
            }
            parcel.writeInt(this.mLocalVerificationState);
            parcel.writeInt(this.mRemoteVerificationState);
            parcel.writeString8(this.mDisplayName);
            parcel.writeString8(this.mPhoneNumber);
            parcel.writeString8(this.mEmailAddress);
        }
    }

    public static final class E2eeSelfKey extends E2eeBaseKey implements Parcelable {
        public static final Parcelable.Creator<E2eeSelfKey> CREATOR = new Parcelable.Creator<E2eeSelfKey>() { // from class: android.provider.E2eeContactKeysManager.E2eeSelfKey.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public E2eeSelfKey createFromParcel(Parcel parcel) {
                byte[] bArr;
                String string8 = parcel.readString8();
                String string82 = parcel.readString8();
                String string83 = parcel.readString8();
                long j = parcel.readLong();
                int i = parcel.readInt();
                if (i > 0) {
                    bArr = new byte[i];
                    parcel.readByteArray(bArr);
                } else {
                    bArr = null;
                }
                return new E2eeSelfKey(string8, string82, string83, j, bArr, parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public E2eeSelfKey[] newArray(int i) {
                return new E2eeSelfKey[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ String getAccountId() {
            return super.getAccountId();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ String getDeviceId() {
            return super.getDeviceId();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ byte[] getKeyValue() {
            return super.getKeyValue();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ String getOwnerPackageName() {
            return super.getOwnerPackageName();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ int getRemoteVerificationState() {
            return super.getRemoteVerificationState();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ long getTimeUpdated() {
            return super.getTimeUpdated();
        }

        public E2eeSelfKey(String str, String str2, String str3, long j, byte[] bArr, int i) {
            super(str, str2, str3, j, bArr, i);
        }

        public int hashCode() {
            return Objects.hash(this.mDeviceId, this.mAccountId, this.mOwnerPackageName, Long.valueOf(this.mTimeUpdated), Integer.valueOf(Arrays.hashCode(this.mKeyValue)), Integer.valueOf(this.mRemoteVerificationState));
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj instanceof E2eeSelfKey) {
                E2eeSelfKey e2eeSelfKey = (E2eeSelfKey) obj;
                if (Objects.equals(this.mDeviceId, e2eeSelfKey.mDeviceId) && Objects.equals(this.mAccountId, e2eeSelfKey.mAccountId) && Objects.equals(this.mOwnerPackageName, e2eeSelfKey.mOwnerPackageName) && this.mTimeUpdated == e2eeSelfKey.mTimeUpdated && Arrays.equals(this.mKeyValue, e2eeSelfKey.mKeyValue) && this.mRemoteVerificationState == e2eeSelfKey.mRemoteVerificationState) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.mDeviceId);
            parcel.writeString8(this.mAccountId);
            parcel.writeString8(this.mOwnerPackageName);
            parcel.writeLong(this.mTimeUpdated);
            parcel.writeInt(this.mKeyValue != null ? this.mKeyValue.length : -1);
            if (this.mKeyValue != null) {
                parcel.writeByteArray(this.mKeyValue);
            }
            parcel.writeInt(this.mRemoteVerificationState);
        }
    }

    static abstract class E2eeBaseKey {
        protected final String mAccountId;
        protected final String mDeviceId;
        protected final byte[] mKeyValue;
        protected final String mOwnerPackageName;
        protected final int mRemoteVerificationState;
        protected final long mTimeUpdated;

        protected E2eeBaseKey(String str, String str2, String str3, long j, byte[] bArr, int i) {
            this.mDeviceId = str;
            this.mAccountId = str2;
            this.mOwnerPackageName = str3;
            this.mTimeUpdated = j;
            this.mKeyValue = bArr == null ? null : Arrays.copyOf(bArr, bArr.length);
            this.mRemoteVerificationState = i;
        }

        public String getDeviceId() {
            return this.mDeviceId;
        }

        public String getAccountId() {
            return this.mAccountId;
        }

        public String getOwnerPackageName() {
            return this.mOwnerPackageName;
        }

        public long getTimeUpdated() {
            return this.mTimeUpdated;
        }

        public byte[] getKeyValue() {
            byte[] bArr = this.mKeyValue;
            if (bArr == null) {
                return null;
            }
            return Arrays.copyOf(bArr, bArr.length);
        }

        public int getRemoteVerificationState() {
            return this.mRemoteVerificationState;
        }
    }
}
