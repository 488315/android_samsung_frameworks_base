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

public final class E2eeContactKeysManager {
    private static final int ARRAY_IS_NULL = -1;
    public static final String AUTHORITY = "com.android.contactkeys.contactkeysprovider";
    public static final Uri AUTHORITY_URI =
            Uri.parse("content://com.android.contactkeys.contactkeysprovider");
    private static final int MAX_KEY_SIZE_BYTES = 5000;
    public static final int VERIFICATION_STATE_UNVERIFIED = 0;
    public static final int VERIFICATION_STATE_VERIFICATION_FAILED = 1;
    public static final int VERIFICATION_STATE_VERIFIED = 2;
    private final ContentResolver mContentResolver;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VerificationState {}

    public E2eeContactKeysManager(Context context) {
        Objects.requireNonNull(context);
        this.mContentResolver = context.getContentResolver();
    }

    public void updateOrInsertE2eeContactKey(
            String lookupKey, String deviceId, String accountId, byte[] keyValue) {
        validateKeyLength(keyValue);
        Bundle extras = new Bundle();
        extras.putString("lookup", (String) Objects.requireNonNull(lookupKey));
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        extras.putByteArray(E2eeContactKeys.KEY_VALUE, (byte[]) Objects.requireNonNull(keyValue));
        nullSafeCall(
                this.mContentResolver, E2eeContactKeys.UPDATE_OR_INSERT_CONTACT_KEY_METHOD, extras);
    }

    public E2eeContactKey getE2eeContactKey(String lookupKey, String deviceId, String accountId) {
        Bundle extras = new Bundle();
        extras.putString("lookup", (String) Objects.requireNonNull(lookupKey));
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        Bundle response =
                nullSafeCall(this.mContentResolver, E2eeContactKeys.GET_CONTACT_KEY_METHOD, extras);
        if (response == null) {
            return null;
        }
        return (E2eeContactKey)
                response.getParcelable(E2eeContactKeys.KEY_CONTACT_KEY, E2eeContactKey.class);
    }

    public List<E2eeContactKey> getAllE2eeContactKeys(String lookupKey) {
        Bundle extras = new Bundle();
        extras.putString("lookup", (String) Objects.requireNonNull(lookupKey));
        Bundle response =
                nullSafeCall(
                        this.mContentResolver, E2eeContactKeys.GET_ALL_CONTACT_KEYS_METHOD, extras);
        if (response == null) {
            return new ArrayList();
        }
        List<E2eeContactKey> value =
                response.getParcelableArrayList(
                        E2eeContactKeys.KEY_CONTACT_KEYS, E2eeContactKey.class);
        if (value == null) {
            return new ArrayList();
        }
        return value;
    }

    public List<E2eeContactKey> getOwnerE2eeContactKeys(String lookupKey) {
        Bundle extras = new Bundle();
        extras.putString("lookup", (String) Objects.requireNonNull(lookupKey));
        Bundle response =
                nullSafeCall(
                        this.mContentResolver,
                        E2eeContactKeys.GET_OWNER_CONTACT_KEYS_METHOD,
                        extras);
        if (response == null) {
            return new ArrayList();
        }
        List<E2eeContactKey> value =
                response.getParcelableArrayList(
                        E2eeContactKeys.KEY_CONTACT_KEYS, E2eeContactKey.class);
        if (value == null) {
            return new ArrayList();
        }
        return value;
    }

    public boolean updateE2eeContactKeyLocalVerificationState(
            String lookupKey, String deviceId, String accountId, int localVerificationState) {
        validateVerificationState(localVerificationState);
        Bundle extras = new Bundle();
        extras.putString("lookup", (String) Objects.requireNonNull(lookupKey));
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        extras.putInt(E2eeContactKeys.LOCAL_VERIFICATION_STATE, localVerificationState);
        Bundle response =
                nullSafeCall(
                        this.mContentResolver,
                        E2eeContactKeys.UPDATE_CONTACT_KEY_LOCAL_VERIFICATION_STATE_METHOD,
                        extras);
        return response != null && response.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    @SystemApi
    public boolean updateE2eeContactKeyLocalVerificationState(
            String lookupKey,
            String deviceId,
            String accountId,
            String ownerPackageName,
            int localVerificationState) {
        validateVerificationState(localVerificationState);
        Bundle extras = new Bundle();
        extras.putString("lookup", (String) Objects.requireNonNull(lookupKey));
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        extras.putString(
                E2eeContactKeys.OWNER_PACKAGE_NAME,
                (String) Objects.requireNonNull(ownerPackageName));
        extras.putInt(E2eeContactKeys.LOCAL_VERIFICATION_STATE, localVerificationState);
        Bundle response =
                nullSafeCall(
                        this.mContentResolver,
                        E2eeContactKeys.UPDATE_CONTACT_KEY_LOCAL_VERIFICATION_STATE_METHOD,
                        extras);
        return response != null && response.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    public boolean updateE2eeContactKeyRemoteVerificationState(
            String lookupKey, String deviceId, String accountId, int remoteVerificationState) {
        validateVerificationState(remoteVerificationState);
        Bundle extras = new Bundle();
        extras.putString("lookup", (String) Objects.requireNonNull(lookupKey));
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        extras.putInt(E2eeContactKeys.REMOTE_VERIFICATION_STATE, remoteVerificationState);
        Bundle response =
                nullSafeCall(
                        this.mContentResolver,
                        E2eeContactKeys.UPDATE_CONTACT_KEY_REMOTE_VERIFICATION_STATE_METHOD,
                        extras);
        return response != null && response.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    @SystemApi
    public boolean updateE2eeContactKeyRemoteVerificationState(
            String lookupKey,
            String deviceId,
            String accountId,
            String ownerPackageName,
            int remoteVerificationState) {
        validateVerificationState(remoteVerificationState);
        Bundle extras = new Bundle();
        extras.putString("lookup", (String) Objects.requireNonNull(lookupKey));
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        extras.putString(
                E2eeContactKeys.OWNER_PACKAGE_NAME,
                (String) Objects.requireNonNull(ownerPackageName));
        extras.putInt(E2eeContactKeys.REMOTE_VERIFICATION_STATE, remoteVerificationState);
        Bundle response =
                nullSafeCall(
                        this.mContentResolver,
                        E2eeContactKeys.UPDATE_CONTACT_KEY_REMOTE_VERIFICATION_STATE_METHOD,
                        extras);
        return response != null && response.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    private static void validateVerificationState(int verificationState) {
        if (verificationState != 0 && verificationState != 1 && verificationState != 2) {
            throw new IllegalArgumentException(
                    "Verification state value " + verificationState + " is not supported");
        }
    }

    public boolean removeE2eeContactKey(String lookupKey, String deviceId, String accountId) {
        Bundle extras = new Bundle();
        extras.putString("lookup", (String) Objects.requireNonNull(lookupKey));
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        Bundle response =
                nullSafeCall(
                        this.mContentResolver, E2eeContactKeys.REMOVE_CONTACT_KEY_METHOD, extras);
        return response != null && response.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    public boolean updateOrInsertE2eeSelfKey(String deviceId, String accountId, byte[] keyValue) {
        validateKeyLength(keyValue);
        Bundle extras = new Bundle();
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        extras.putByteArray(E2eeContactKeys.KEY_VALUE, (byte[]) Objects.requireNonNull(keyValue));
        Bundle response =
                nullSafeCall(
                        this.mContentResolver,
                        E2eeContactKeys.UPDATE_OR_INSERT_SELF_KEY_METHOD,
                        extras);
        return response != null && response.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    private static void validateKeyLength(byte[] keyValue) {
        Objects.requireNonNull(keyValue);
        if (keyValue.length == 0 || keyValue.length > getMaxKeySizeBytes()) {
            throw new IllegalArgumentException(
                    "Key value length is "
                            + keyValue.length
                            + ". Should be more than 0 and less than "
                            + getMaxKeySizeBytes());
        }
    }

    public boolean updateE2eeSelfKeyRemoteVerificationState(
            String deviceId, String accountId, int remoteVerificationState) {
        validateVerificationState(remoteVerificationState);
        Bundle extras = new Bundle();
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        extras.putInt(E2eeContactKeys.REMOTE_VERIFICATION_STATE, remoteVerificationState);
        Bundle response =
                nullSafeCall(
                        this.mContentResolver,
                        E2eeContactKeys.UPDATE_SELF_KEY_REMOTE_VERIFICATION_STATE_METHOD,
                        extras);
        return response != null && response.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    @SystemApi
    public boolean updateE2eeSelfKeyRemoteVerificationState(
            String deviceId,
            String accountId,
            String ownerPackageName,
            int remoteVerificationState) {
        validateVerificationState(remoteVerificationState);
        Bundle extras = new Bundle();
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        extras.putString(
                E2eeContactKeys.OWNER_PACKAGE_NAME,
                (String) Objects.requireNonNull(ownerPackageName));
        extras.putInt(E2eeContactKeys.REMOTE_VERIFICATION_STATE, remoteVerificationState);
        Bundle response =
                nullSafeCall(
                        this.mContentResolver,
                        E2eeContactKeys.UPDATE_SELF_KEY_REMOTE_VERIFICATION_STATE_METHOD,
                        extras);
        return response != null && response.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    public static int getMaxKeySizeBytes() {
        return 5000;
    }

    public E2eeSelfKey getE2eeSelfKey(String deviceId, String accountId) {
        Bundle extras = new Bundle();
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        Bundle response =
                nullSafeCall(this.mContentResolver, E2eeContactKeys.GET_SELF_KEY_METHOD, extras);
        if (response == null) {
            return null;
        }
        return (E2eeSelfKey)
                response.getParcelable(E2eeContactKeys.KEY_CONTACT_KEY, E2eeSelfKey.class);
    }

    public List<E2eeSelfKey> getAllE2eeSelfKeys() {
        Bundle extras = new Bundle();
        Bundle response =
                nullSafeCall(
                        this.mContentResolver, E2eeContactKeys.GET_ALL_SELF_KEYS_METHOD, extras);
        if (response == null) {
            return new ArrayList();
        }
        List<E2eeSelfKey> value =
                response.getParcelableArrayList(
                        E2eeContactKeys.KEY_CONTACT_KEYS, E2eeSelfKey.class);
        if (value == null) {
            return new ArrayList();
        }
        return value;
    }

    public List<E2eeSelfKey> getOwnerE2eeSelfKeys() {
        Bundle extras = new Bundle();
        Bundle response =
                nullSafeCall(
                        this.mContentResolver, E2eeContactKeys.GET_OWNER_SELF_KEYS_METHOD, extras);
        if (response == null) {
            return new ArrayList();
        }
        List<E2eeSelfKey> value =
                response.getParcelableArrayList(
                        E2eeContactKeys.KEY_CONTACT_KEYS, E2eeSelfKey.class);
        if (value == null) {
            return new ArrayList();
        }
        return value;
    }

    public boolean removeE2eeSelfKey(String deviceId, String accountId) {
        Bundle extras = new Bundle();
        extras.putString(E2eeContactKeys.DEVICE_ID, (String) Objects.requireNonNull(deviceId));
        extras.putString("account_id", (String) Objects.requireNonNull(accountId));
        Bundle response =
                nullSafeCall(this.mContentResolver, E2eeContactKeys.REMOVE_SELF_KEY_METHOD, extras);
        return response != null && response.getBoolean(E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    private Bundle nullSafeCall(ContentResolver resolver, String method, Bundle extras) {
        try {
            ContentProviderClient client = resolver.acquireContentProviderClient(AUTHORITY_URI);
            try {
                Bundle call = client.call(method, null, extras);
                if (client != null) {
                    client.close();
                }
                return call;
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
        public static final String UPDATE_CONTACT_KEY_LOCAL_VERIFICATION_STATE_METHOD =
                "updateContactKeyLocalVerificationState";
        public static final String UPDATE_CONTACT_KEY_REMOTE_VERIFICATION_STATE_METHOD =
                "updateContactKeyRemoteVerificationState";
        public static final String UPDATE_OR_INSERT_CONTACT_KEY_METHOD = "updateOrInsertContactKey";
        public static final String UPDATE_OR_INSERT_SELF_KEY_METHOD = "updateOrInsertSelfKey";
        public static final String UPDATE_SELF_KEY_REMOTE_VERIFICATION_STATE_METHOD =
                "updateSelfKeyRemoteVerificationState";

        private E2eeContactKeys() {}
    }

    public static final class E2eeContactKey extends E2eeBaseKey implements Parcelable {
        public static final Parcelable.Creator<E2eeContactKey> CREATOR =
                new Parcelable.Creator<
                        E2eeContactKey>() { // from class:
                                            // android.provider.E2eeContactKeysManager.E2eeContactKey.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public E2eeContactKey createFromParcel(Parcel source) {
                        byte[] keyValue;
                        String deviceId = source.readString8();
                        String accountId = source.readString8();
                        String ownerPackageName = source.readString8();
                        long timeUpdated = source.readLong();
                        int keyValueLength = source.readInt();
                        if (keyValueLength > 0) {
                            byte[] keyValue2 = new byte[keyValueLength];
                            source.readByteArray(keyValue2);
                            keyValue = keyValue2;
                        } else {
                            keyValue = null;
                        }
                        int localVerificationState = source.readInt();
                        int remoteVerificationState = source.readInt();
                        String displayName = source.readString8();
                        String number = source.readString8();
                        String address = source.readString8();
                        return new E2eeContactKey(
                                deviceId,
                                accountId,
                                ownerPackageName,
                                timeUpdated,
                                keyValue,
                                localVerificationState,
                                remoteVerificationState,
                                displayName,
                                number,
                                address);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public E2eeContactKey[] newArray(int size) {
                        return new E2eeContactKey[size];
                    }
                };
        private final String mDisplayName;
        private final String mEmailAddress;
        private final int mLocalVerificationState;
        private final String mPhoneNumber;

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

        public E2eeContactKey(
                String deviceId,
                String accountId,
                String ownerPackageName,
                long timeUpdated,
                byte[] keyValue,
                int localVerificationState,
                int remoteVerificationState,
                String displayName,
                String phoneNumber,
                String emailAddress) {
            super(
                    deviceId,
                    accountId,
                    ownerPackageName,
                    timeUpdated,
                    keyValue,
                    remoteVerificationState);
            this.mLocalVerificationState = localVerificationState;
            this.mDisplayName = displayName;
            this.mPhoneNumber = phoneNumber;
            this.mEmailAddress = emailAddress;
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
            return Objects.hash(
                    this.mDeviceId,
                    this.mAccountId,
                    this.mOwnerPackageName,
                    Long.valueOf(this.mTimeUpdated),
                    Integer.valueOf(Arrays.hashCode(this.mKeyValue)),
                    Integer.valueOf(this.mLocalVerificationState),
                    Integer.valueOf(this.mRemoteVerificationState),
                    this.mDisplayName,
                    this.mPhoneNumber,
                    this.mEmailAddress);
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof E2eeContactKey)) {
                return false;
            }
            E2eeContactKey toCompare = (E2eeContactKey) obj;
            if (!Objects.equals(this.mDeviceId, toCompare.mDeviceId)
                    || !Objects.equals(this.mAccountId, toCompare.mAccountId)
                    || !Objects.equals(this.mOwnerPackageName, toCompare.mOwnerPackageName)
                    || this.mTimeUpdated != toCompare.mTimeUpdated
                    || !Arrays.equals(this.mKeyValue, toCompare.mKeyValue)
                    || this.mLocalVerificationState != toCompare.mLocalVerificationState
                    || this.mRemoteVerificationState != toCompare.mRemoteVerificationState
                    || !Objects.equals(this.mDisplayName, toCompare.mDisplayName)
                    || !Objects.equals(this.mPhoneNumber, toCompare.mPhoneNumber)
                    || !Objects.equals(this.mEmailAddress, toCompare.mEmailAddress)) {
                return false;
            }
            return true;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString8(this.mDeviceId);
            dest.writeString8(this.mAccountId);
            dest.writeString8(this.mOwnerPackageName);
            dest.writeLong(this.mTimeUpdated);
            dest.writeInt(this.mKeyValue != null ? this.mKeyValue.length : -1);
            if (this.mKeyValue != null) {
                dest.writeByteArray(this.mKeyValue);
            }
            dest.writeInt(this.mLocalVerificationState);
            dest.writeInt(this.mRemoteVerificationState);
            dest.writeString8(this.mDisplayName);
            dest.writeString8(this.mPhoneNumber);
            dest.writeString8(this.mEmailAddress);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static final class E2eeSelfKey extends E2eeBaseKey implements Parcelable {
        public static final Parcelable.Creator<E2eeSelfKey> CREATOR =
                new Parcelable.Creator<
                        E2eeSelfKey>() { // from class:
                                         // android.provider.E2eeContactKeysManager.E2eeSelfKey.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public E2eeSelfKey createFromParcel(Parcel source) {
                        byte[] keyValue;
                        String deviceId = source.readString8();
                        String accountId = source.readString8();
                        String ownerPackageName = source.readString8();
                        long timeUpdated = source.readLong();
                        int keyValueLength = source.readInt();
                        if (keyValueLength > 0) {
                            byte[] keyValue2 = new byte[keyValueLength];
                            source.readByteArray(keyValue2);
                            keyValue = keyValue2;
                        } else {
                            keyValue = null;
                        }
                        int remoteVerificationState = source.readInt();
                        return new E2eeSelfKey(
                                deviceId,
                                accountId,
                                ownerPackageName,
                                timeUpdated,
                                keyValue,
                                remoteVerificationState);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public E2eeSelfKey[] newArray(int size) {
                        return new E2eeSelfKey[size];
                    }
                };

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

        public E2eeSelfKey(
                String deviceId,
                String accountId,
                String ownerPackageName,
                long timeUpdated,
                byte[] keyValue,
                int remoteVerificationState) {
            super(
                    deviceId,
                    accountId,
                    ownerPackageName,
                    timeUpdated,
                    keyValue,
                    remoteVerificationState);
        }

        public int hashCode() {
            return Objects.hash(
                    this.mDeviceId,
                    this.mAccountId,
                    this.mOwnerPackageName,
                    Long.valueOf(this.mTimeUpdated),
                    Integer.valueOf(Arrays.hashCode(this.mKeyValue)),
                    Integer.valueOf(this.mRemoteVerificationState));
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof E2eeSelfKey)) {
                return false;
            }
            E2eeSelfKey toCompare = (E2eeSelfKey) obj;
            if (!Objects.equals(this.mDeviceId, toCompare.mDeviceId)
                    || !Objects.equals(this.mAccountId, toCompare.mAccountId)
                    || !Objects.equals(this.mOwnerPackageName, toCompare.mOwnerPackageName)
                    || this.mTimeUpdated != toCompare.mTimeUpdated
                    || !Arrays.equals(this.mKeyValue, toCompare.mKeyValue)
                    || this.mRemoteVerificationState != toCompare.mRemoteVerificationState) {
                return false;
            }
            return true;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString8(this.mDeviceId);
            dest.writeString8(this.mAccountId);
            dest.writeString8(this.mOwnerPackageName);
            dest.writeLong(this.mTimeUpdated);
            dest.writeInt(this.mKeyValue != null ? this.mKeyValue.length : -1);
            if (this.mKeyValue != null) {
                dest.writeByteArray(this.mKeyValue);
            }
            dest.writeInt(this.mRemoteVerificationState);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    abstract static class E2eeBaseKey {
        protected final String mAccountId;
        protected final String mDeviceId;
        protected final byte[] mKeyValue;
        protected final String mOwnerPackageName;
        protected final int mRemoteVerificationState;
        protected final long mTimeUpdated;

        protected E2eeBaseKey(
                String deviceId,
                String accountId,
                String ownerPackageName,
                long timeUpdated,
                byte[] keyValue,
                int remoteVerificationState) {
            this.mDeviceId = deviceId;
            this.mAccountId = accountId;
            this.mOwnerPackageName = ownerPackageName;
            this.mTimeUpdated = timeUpdated;
            this.mKeyValue = keyValue == null ? null : Arrays.copyOf(keyValue, keyValue.length);
            this.mRemoteVerificationState = remoteVerificationState;
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
            if (this.mKeyValue == null) {
                return null;
            }
            return Arrays.copyOf(this.mKeyValue, this.mKeyValue.length);
        }

        public int getRemoteVerificationState() {
            return this.mRemoteVerificationState;
        }
    }
}
