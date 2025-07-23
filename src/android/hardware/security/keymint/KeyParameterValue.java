package android.hardware.security.keymint;

import android.media.TtmlUtils;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator;

/* loaded from: classes2.dex */
public final class KeyParameterValue implements Parcelable {
    public static final Parcelable.Creator<KeyParameterValue> CREATOR = new Parcelable.Creator<KeyParameterValue>() { // from class: android.hardware.security.keymint.KeyParameterValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyParameterValue createFromParcel(Parcel parcel) {
            return new KeyParameterValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyParameterValue[] newArray(int i) {
            return new KeyParameterValue[i];
        }
    };
    public static final int algorithm = 1;
    public static final int blob = 14;
    public static final int blockMode = 2;
    public static final int boolValue = 10;
    public static final int dateTime = 13;
    public static final int digest = 4;
    public static final int ecCurve = 5;
    public static final int hardwareAuthenticatorType = 8;
    public static final int integer = 11;
    public static final int invalid = 0;
    public static final int keyPurpose = 7;
    public static final int longInteger = 12;
    public static final int origin = 6;
    public static final int paddingMode = 3;
    public static final int securityLevel = 9;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int algorithm = 1;
        public static final int blob = 14;
        public static final int blockMode = 2;
        public static final int boolValue = 10;
        public static final int dateTime = 13;
        public static final int digest = 4;
        public static final int ecCurve = 5;
        public static final int hardwareAuthenticatorType = 8;
        public static final int integer = 11;
        public static final int invalid = 0;
        public static final int keyPurpose = 7;
        public static final int longInteger = 12;
        public static final int origin = 6;
        public static final int paddingMode = 3;
        public static final int securityLevel = 9;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public KeyParameterValue() {
        this._tag = 0;
        this._value = 0;
    }

    private KeyParameterValue(Parcel parcel) {
        readFromParcel(parcel);
    }

    private KeyParameterValue(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static KeyParameterValue invalid(int i) {
        return new KeyParameterValue(0, Integer.valueOf(i));
    }

    public int getInvalid() {
        _assertTag(0);
        return ((Integer) this._value).intValue();
    }

    public void setInvalid(int i) {
        _set(0, Integer.valueOf(i));
    }

    public static KeyParameterValue algorithm(int i) {
        return new KeyParameterValue(1, Integer.valueOf(i));
    }

    public int getAlgorithm() {
        _assertTag(1);
        return ((Integer) this._value).intValue();
    }

    public void setAlgorithm(int i) {
        _set(1, Integer.valueOf(i));
    }

    public static KeyParameterValue blockMode(int i) {
        return new KeyParameterValue(2, Integer.valueOf(i));
    }

    public int getBlockMode() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setBlockMode(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static KeyParameterValue paddingMode(int i) {
        return new KeyParameterValue(3, Integer.valueOf(i));
    }

    public int getPaddingMode() {
        _assertTag(3);
        return ((Integer) this._value).intValue();
    }

    public void setPaddingMode(int i) {
        _set(3, Integer.valueOf(i));
    }

    public static KeyParameterValue digest(int i) {
        return new KeyParameterValue(4, Integer.valueOf(i));
    }

    public int getDigest() {
        _assertTag(4);
        return ((Integer) this._value).intValue();
    }

    public void setDigest(int i) {
        _set(4, Integer.valueOf(i));
    }

    public static KeyParameterValue ecCurve(int i) {
        return new KeyParameterValue(5, Integer.valueOf(i));
    }

    public int getEcCurve() {
        _assertTag(5);
        return ((Integer) this._value).intValue();
    }

    public void setEcCurve(int i) {
        _set(5, Integer.valueOf(i));
    }

    public static KeyParameterValue origin(int i) {
        return new KeyParameterValue(6, Integer.valueOf(i));
    }

    public int getOrigin() {
        _assertTag(6);
        return ((Integer) this._value).intValue();
    }

    public void setOrigin(int i) {
        _set(6, Integer.valueOf(i));
    }

    public static KeyParameterValue keyPurpose(int i) {
        return new KeyParameterValue(7, Integer.valueOf(i));
    }

    public int getKeyPurpose() {
        _assertTag(7);
        return ((Integer) this._value).intValue();
    }

    public void setKeyPurpose(int i) {
        _set(7, Integer.valueOf(i));
    }

    public static KeyParameterValue hardwareAuthenticatorType(int i) {
        return new KeyParameterValue(8, Integer.valueOf(i));
    }

    public int getHardwareAuthenticatorType() {
        _assertTag(8);
        return ((Integer) this._value).intValue();
    }

    public void setHardwareAuthenticatorType(int i) {
        _set(8, Integer.valueOf(i));
    }

    public static KeyParameterValue securityLevel(int i) {
        return new KeyParameterValue(9, Integer.valueOf(i));
    }

    public int getSecurityLevel() {
        _assertTag(9);
        return ((Integer) this._value).intValue();
    }

    public void setSecurityLevel(int i) {
        _set(9, Integer.valueOf(i));
    }

    public static KeyParameterValue boolValue(boolean z) {
        return new KeyParameterValue(10, Boolean.valueOf(z));
    }

    public boolean getBoolValue() {
        _assertTag(10);
        return ((Boolean) this._value).booleanValue();
    }

    public void setBoolValue(boolean z) {
        _set(10, Boolean.valueOf(z));
    }

    public static KeyParameterValue integer(int i) {
        return new KeyParameterValue(11, Integer.valueOf(i));
    }

    public int getInteger() {
        _assertTag(11);
        return ((Integer) this._value).intValue();
    }

    public void setInteger(int i) {
        _set(11, Integer.valueOf(i));
    }

    public static KeyParameterValue longInteger(long j) {
        return new KeyParameterValue(12, Long.valueOf(j));
    }

    public long getLongInteger() {
        _assertTag(12);
        return ((Long) this._value).longValue();
    }

    public void setLongInteger(long j) {
        _set(12, Long.valueOf(j));
    }

    public static KeyParameterValue dateTime(long j) {
        return new KeyParameterValue(13, Long.valueOf(j));
    }

    public long getDateTime() {
        _assertTag(13);
        return ((Long) this._value).longValue();
    }

    public void setDateTime(long j) {
        _set(13, Long.valueOf(j));
    }

    public static KeyParameterValue blob(byte[] bArr) {
        return new KeyParameterValue(14, bArr);
    }

    public byte[] getBlob() {
        _assertTag(14);
        return (byte[]) this._value;
    }

    public void setBlob(byte[] bArr) {
        _set(14, bArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeInt(getInvalid());
                break;
            case 1:
                parcel.writeInt(getAlgorithm());
                break;
            case 2:
                parcel.writeInt(getBlockMode());
                break;
            case 3:
                parcel.writeInt(getPaddingMode());
                break;
            case 4:
                parcel.writeInt(getDigest());
                break;
            case 5:
                parcel.writeInt(getEcCurve());
                break;
            case 6:
                parcel.writeInt(getOrigin());
                break;
            case 7:
                parcel.writeInt(getKeyPurpose());
                break;
            case 8:
                parcel.writeInt(getHardwareAuthenticatorType());
                break;
            case 9:
                parcel.writeInt(getSecurityLevel());
                break;
            case 10:
                parcel.writeBoolean(getBoolValue());
                break;
            case 11:
                parcel.writeInt(getInteger());
                break;
            case 12:
                parcel.writeLong(getLongInteger());
                break;
            case 13:
                parcel.writeLong(getDateTime());
                break;
            case 14:
                parcel.writeByteArray(getBlob());
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 1:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 2:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 3:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 4:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 5:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 6:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 7:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 8:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 9:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 10:
                _set(readInt, Boolean.valueOf(parcel.readBoolean()));
                return;
            case 11:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 12:
                _set(readInt, Long.valueOf(parcel.readLong()));
                return;
            case 13:
                _set(readInt, Long.valueOf(parcel.readLong()));
                return;
            case 14:
                _set(readInt, parcel.createByteArray());
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
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
                return "invalid";
            case 1:
                return "algorithm";
            case 2:
                return "blockMode";
            case 3:
                return "paddingMode";
            case 4:
                return CMSAttributeTableGenerator.DIGEST;
            case 5:
                return "ecCurve";
            case 6:
                return TtmlUtils.ATTR_ORIGIN;
            case 7:
                return "keyPurpose";
            case 8:
                return "hardwareAuthenticatorType";
            case 9:
                return "securityLevel";
            case 10:
                return "boolValue";
            case 11:
                return "integer";
            case 12:
                return "longInteger";
            case 13:
                return "dateTime";
            case 14:
                return "blob";
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
