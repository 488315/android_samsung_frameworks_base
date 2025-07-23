package android.security.metrics;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class KeystoreAtomPayload implements Parcelable {
    public static final Parcelable.Creator<KeystoreAtomPayload> CREATOR = new Parcelable.Creator<KeystoreAtomPayload>() { // from class: android.security.metrics.KeystoreAtomPayload.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeystoreAtomPayload createFromParcel(Parcel parcel) {
            return new KeystoreAtomPayload(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeystoreAtomPayload[] newArray(int i) {
            return new KeystoreAtomPayload[i];
        }
    };
    public static final int crashStats = 8;
    public static final int keyCreationWithAuthInfo = 2;
    public static final int keyCreationWithGeneralInfo = 1;
    public static final int keyCreationWithPurposeAndModesInfo = 3;
    public static final int keyOperationWithGeneralInfo = 6;
    public static final int keyOperationWithPurposeAndModesInfo = 5;
    public static final int keystore2AtomWithOverflow = 4;
    public static final int rkpErrorStats = 7;
    public static final int storageStats = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int crashStats = 8;
        public static final int keyCreationWithAuthInfo = 2;
        public static final int keyCreationWithGeneralInfo = 1;
        public static final int keyCreationWithPurposeAndModesInfo = 3;
        public static final int keyOperationWithGeneralInfo = 6;
        public static final int keyOperationWithPurposeAndModesInfo = 5;
        public static final int keystore2AtomWithOverflow = 4;
        public static final int rkpErrorStats = 7;
        public static final int storageStats = 0;
    }

    public KeystoreAtomPayload() {
        this._tag = 0;
        this._value = null;
    }

    private KeystoreAtomPayload(Parcel parcel) {
        readFromParcel(parcel);
    }

    private KeystoreAtomPayload(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static KeystoreAtomPayload storageStats(StorageStats storageStats2) {
        return new KeystoreAtomPayload(0, storageStats2);
    }

    public StorageStats getStorageStats() {
        _assertTag(0);
        return (StorageStats) this._value;
    }

    public void setStorageStats(StorageStats storageStats2) {
        _set(0, storageStats2);
    }

    public static KeystoreAtomPayload keyCreationWithGeneralInfo(KeyCreationWithGeneralInfo keyCreationWithGeneralInfo2) {
        return new KeystoreAtomPayload(1, keyCreationWithGeneralInfo2);
    }

    public KeyCreationWithGeneralInfo getKeyCreationWithGeneralInfo() {
        _assertTag(1);
        return (KeyCreationWithGeneralInfo) this._value;
    }

    public void setKeyCreationWithGeneralInfo(KeyCreationWithGeneralInfo keyCreationWithGeneralInfo2) {
        _set(1, keyCreationWithGeneralInfo2);
    }

    public static KeystoreAtomPayload keyCreationWithAuthInfo(KeyCreationWithAuthInfo keyCreationWithAuthInfo2) {
        return new KeystoreAtomPayload(2, keyCreationWithAuthInfo2);
    }

    public KeyCreationWithAuthInfo getKeyCreationWithAuthInfo() {
        _assertTag(2);
        return (KeyCreationWithAuthInfo) this._value;
    }

    public void setKeyCreationWithAuthInfo(KeyCreationWithAuthInfo keyCreationWithAuthInfo2) {
        _set(2, keyCreationWithAuthInfo2);
    }

    public static KeystoreAtomPayload keyCreationWithPurposeAndModesInfo(KeyCreationWithPurposeAndModesInfo keyCreationWithPurposeAndModesInfo2) {
        return new KeystoreAtomPayload(3, keyCreationWithPurposeAndModesInfo2);
    }

    public KeyCreationWithPurposeAndModesInfo getKeyCreationWithPurposeAndModesInfo() {
        _assertTag(3);
        return (KeyCreationWithPurposeAndModesInfo) this._value;
    }

    public void setKeyCreationWithPurposeAndModesInfo(KeyCreationWithPurposeAndModesInfo keyCreationWithPurposeAndModesInfo2) {
        _set(3, keyCreationWithPurposeAndModesInfo2);
    }

    public static KeystoreAtomPayload keystore2AtomWithOverflow(Keystore2AtomWithOverflow keystore2AtomWithOverflow2) {
        return new KeystoreAtomPayload(4, keystore2AtomWithOverflow2);
    }

    public Keystore2AtomWithOverflow getKeystore2AtomWithOverflow() {
        _assertTag(4);
        return (Keystore2AtomWithOverflow) this._value;
    }

    public void setKeystore2AtomWithOverflow(Keystore2AtomWithOverflow keystore2AtomWithOverflow2) {
        _set(4, keystore2AtomWithOverflow2);
    }

    public static KeystoreAtomPayload keyOperationWithPurposeAndModesInfo(KeyOperationWithPurposeAndModesInfo keyOperationWithPurposeAndModesInfo2) {
        return new KeystoreAtomPayload(5, keyOperationWithPurposeAndModesInfo2);
    }

    public KeyOperationWithPurposeAndModesInfo getKeyOperationWithPurposeAndModesInfo() {
        _assertTag(5);
        return (KeyOperationWithPurposeAndModesInfo) this._value;
    }

    public void setKeyOperationWithPurposeAndModesInfo(KeyOperationWithPurposeAndModesInfo keyOperationWithPurposeAndModesInfo2) {
        _set(5, keyOperationWithPurposeAndModesInfo2);
    }

    public static KeystoreAtomPayload keyOperationWithGeneralInfo(KeyOperationWithGeneralInfo keyOperationWithGeneralInfo2) {
        return new KeystoreAtomPayload(6, keyOperationWithGeneralInfo2);
    }

    public KeyOperationWithGeneralInfo getKeyOperationWithGeneralInfo() {
        _assertTag(6);
        return (KeyOperationWithGeneralInfo) this._value;
    }

    public void setKeyOperationWithGeneralInfo(KeyOperationWithGeneralInfo keyOperationWithGeneralInfo2) {
        _set(6, keyOperationWithGeneralInfo2);
    }

    public static KeystoreAtomPayload rkpErrorStats(RkpErrorStats rkpErrorStats2) {
        return new KeystoreAtomPayload(7, rkpErrorStats2);
    }

    public RkpErrorStats getRkpErrorStats() {
        _assertTag(7);
        return (RkpErrorStats) this._value;
    }

    public void setRkpErrorStats(RkpErrorStats rkpErrorStats2) {
        _set(7, rkpErrorStats2);
    }

    public static KeystoreAtomPayload crashStats(CrashStats crashStats2) {
        return new KeystoreAtomPayload(8, crashStats2);
    }

    public CrashStats getCrashStats() {
        _assertTag(8);
        return (CrashStats) this._value;
    }

    public void setCrashStats(CrashStats crashStats2) {
        _set(8, crashStats2);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeTypedObject(getStorageStats(), i);
                break;
            case 1:
                parcel.writeTypedObject(getKeyCreationWithGeneralInfo(), i);
                break;
            case 2:
                parcel.writeTypedObject(getKeyCreationWithAuthInfo(), i);
                break;
            case 3:
                parcel.writeTypedObject(getKeyCreationWithPurposeAndModesInfo(), i);
                break;
            case 4:
                parcel.writeTypedObject(getKeystore2AtomWithOverflow(), i);
                break;
            case 5:
                parcel.writeTypedObject(getKeyOperationWithPurposeAndModesInfo(), i);
                break;
            case 6:
                parcel.writeTypedObject(getKeyOperationWithGeneralInfo(), i);
                break;
            case 7:
                parcel.writeTypedObject(getRkpErrorStats(), i);
                break;
            case 8:
                parcel.writeTypedObject(getCrashStats(), i);
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (StorageStats) parcel.readTypedObject(StorageStats.CREATOR));
                return;
            case 1:
                _set(readInt, (KeyCreationWithGeneralInfo) parcel.readTypedObject(KeyCreationWithGeneralInfo.CREATOR));
                return;
            case 2:
                _set(readInt, (KeyCreationWithAuthInfo) parcel.readTypedObject(KeyCreationWithAuthInfo.CREATOR));
                return;
            case 3:
                _set(readInt, (KeyCreationWithPurposeAndModesInfo) parcel.readTypedObject(KeyCreationWithPurposeAndModesInfo.CREATOR));
                return;
            case 4:
                _set(readInt, (Keystore2AtomWithOverflow) parcel.readTypedObject(Keystore2AtomWithOverflow.CREATOR));
                return;
            case 5:
                _set(readInt, (KeyOperationWithPurposeAndModesInfo) parcel.readTypedObject(KeyOperationWithPurposeAndModesInfo.CREATOR));
                return;
            case 6:
                _set(readInt, (KeyOperationWithGeneralInfo) parcel.readTypedObject(KeyOperationWithGeneralInfo.CREATOR));
                return;
            case 7:
                _set(readInt, (RkpErrorStats) parcel.readTypedObject(RkpErrorStats.CREATOR));
                return;
            case 8:
                _set(readInt, (CrashStats) parcel.readTypedObject(CrashStats.CREATOR));
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return describeContents(getStorageStats());
            case 1:
                return describeContents(getKeyCreationWithGeneralInfo());
            case 2:
                return describeContents(getKeyCreationWithAuthInfo());
            case 3:
                return describeContents(getKeyCreationWithPurposeAndModesInfo());
            case 4:
                return describeContents(getKeystore2AtomWithOverflow());
            case 5:
                return describeContents(getKeyOperationWithPurposeAndModesInfo());
            case 6:
                return describeContents(getKeyOperationWithGeneralInfo());
            case 7:
                return describeContents(getRkpErrorStats());
            case 8:
                return describeContents(getCrashStats());
            default:
                return 0;
        }
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        switch (i) {
            case 0:
                return "storageStats";
            case 1:
                return "keyCreationWithGeneralInfo";
            case 2:
                return "keyCreationWithAuthInfo";
            case 3:
                return "keyCreationWithPurposeAndModesInfo";
            case 4:
                return "keystore2AtomWithOverflow";
            case 5:
                return "keyOperationWithPurposeAndModesInfo";
            case 6:
                return "keyOperationWithGeneralInfo";
            case 7:
                return "rkpErrorStats";
            case 8:
                return "crashStats";
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
