package android.hardware.tv.tuner;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class FrontendScanMessage implements Parcelable {
    public static final Parcelable.Creator<FrontendScanMessage> CREATOR = new Parcelable.Creator<FrontendScanMessage>() { // from class: android.hardware.tv.tuner.FrontendScanMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendScanMessage createFromParcel(Parcel parcel) {
            return new FrontendScanMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendScanMessage[] newArray(int i) {
            return new FrontendScanMessage[i];
        }
    };
    public static final int analogType = 6;
    public static final int annex = 13;
    public static final int atsc3PlpInfos = 11;
    public static final int dvbtCellIds = 15;
    public static final int frequencies = 3;
    public static final int groupIds = 8;
    public static final int hierarchy = 5;
    public static final int inputStreamIds = 9;
    public static final int isEnd = 1;
    public static final int isHighPriority = 14;
    public static final int isLocked = 0;
    public static final int modulation = 12;
    public static final int plpIds = 7;
    public static final int progressPercent = 2;
    public static final int std = 10;
    public static final int symbolRates = 4;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int analogType = 6;
        public static final int annex = 13;
        public static final int atsc3PlpInfos = 11;
        public static final int dvbtCellIds = 15;
        public static final int frequencies = 3;
        public static final int groupIds = 8;
        public static final int hierarchy = 5;
        public static final int inputStreamIds = 9;
        public static final int isEnd = 1;
        public static final int isHighPriority = 14;
        public static final int isLocked = 0;
        public static final int modulation = 12;
        public static final int plpIds = 7;
        public static final int progressPercent = 2;
        public static final int std = 10;
        public static final int symbolRates = 4;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public FrontendScanMessage() {
        this._tag = 0;
        this._value = false;
    }

    private FrontendScanMessage(Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendScanMessage(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static FrontendScanMessage isLocked(boolean z) {
        return new FrontendScanMessage(0, Boolean.valueOf(z));
    }

    public boolean getIsLocked() {
        _assertTag(0);
        return ((Boolean) this._value).booleanValue();
    }

    public void setIsLocked(boolean z) {
        _set(0, Boolean.valueOf(z));
    }

    public static FrontendScanMessage isEnd(boolean z) {
        return new FrontendScanMessage(1, Boolean.valueOf(z));
    }

    public boolean getIsEnd() {
        _assertTag(1);
        return ((Boolean) this._value).booleanValue();
    }

    public void setIsEnd(boolean z) {
        _set(1, Boolean.valueOf(z));
    }

    public static FrontendScanMessage progressPercent(int i) {
        return new FrontendScanMessage(2, Integer.valueOf(i));
    }

    public int getProgressPercent() {
        _assertTag(2);
        return ((Integer) this._value).intValue();
    }

    public void setProgressPercent(int i) {
        _set(2, Integer.valueOf(i));
    }

    public static FrontendScanMessage frequencies(long[] jArr) {
        return new FrontendScanMessage(3, jArr);
    }

    public long[] getFrequencies() {
        _assertTag(3);
        return (long[]) this._value;
    }

    public void setFrequencies(long[] jArr) {
        _set(3, jArr);
    }

    public static FrontendScanMessage symbolRates(int[] iArr) {
        return new FrontendScanMessage(4, iArr);
    }

    public int[] getSymbolRates() {
        _assertTag(4);
        return (int[]) this._value;
    }

    public void setSymbolRates(int[] iArr) {
        _set(4, iArr);
    }

    public static FrontendScanMessage hierarchy(int i) {
        return new FrontendScanMessage(5, Integer.valueOf(i));
    }

    public int getHierarchy() {
        _assertTag(5);
        return ((Integer) this._value).intValue();
    }

    public void setHierarchy(int i) {
        _set(5, Integer.valueOf(i));
    }

    public static FrontendScanMessage analogType(int i) {
        return new FrontendScanMessage(6, Integer.valueOf(i));
    }

    public int getAnalogType() {
        _assertTag(6);
        return ((Integer) this._value).intValue();
    }

    public void setAnalogType(int i) {
        _set(6, Integer.valueOf(i));
    }

    public static FrontendScanMessage plpIds(int[] iArr) {
        return new FrontendScanMessage(7, iArr);
    }

    public int[] getPlpIds() {
        _assertTag(7);
        return (int[]) this._value;
    }

    public void setPlpIds(int[] iArr) {
        _set(7, iArr);
    }

    public static FrontendScanMessage groupIds(int[] iArr) {
        return new FrontendScanMessage(8, iArr);
    }

    public int[] getGroupIds() {
        _assertTag(8);
        return (int[]) this._value;
    }

    public void setGroupIds(int[] iArr) {
        _set(8, iArr);
    }

    public static FrontendScanMessage inputStreamIds(int[] iArr) {
        return new FrontendScanMessage(9, iArr);
    }

    public int[] getInputStreamIds() {
        _assertTag(9);
        return (int[]) this._value;
    }

    public void setInputStreamIds(int[] iArr) {
        _set(9, iArr);
    }

    public static FrontendScanMessage std(FrontendScanMessageStandard frontendScanMessageStandard) {
        return new FrontendScanMessage(10, frontendScanMessageStandard);
    }

    public FrontendScanMessageStandard getStd() {
        _assertTag(10);
        return (FrontendScanMessageStandard) this._value;
    }

    public void setStd(FrontendScanMessageStandard frontendScanMessageStandard) {
        _set(10, frontendScanMessageStandard);
    }

    public static FrontendScanMessage atsc3PlpInfos(FrontendScanAtsc3PlpInfo[] frontendScanAtsc3PlpInfoArr) {
        return new FrontendScanMessage(11, frontendScanAtsc3PlpInfoArr);
    }

    public FrontendScanAtsc3PlpInfo[] getAtsc3PlpInfos() {
        _assertTag(11);
        return (FrontendScanAtsc3PlpInfo[]) this._value;
    }

    public void setAtsc3PlpInfos(FrontendScanAtsc3PlpInfo[] frontendScanAtsc3PlpInfoArr) {
        _set(11, frontendScanAtsc3PlpInfoArr);
    }

    public static FrontendScanMessage modulation(FrontendModulation frontendModulation) {
        return new FrontendScanMessage(12, frontendModulation);
    }

    public FrontendModulation getModulation() {
        _assertTag(12);
        return (FrontendModulation) this._value;
    }

    public void setModulation(FrontendModulation frontendModulation) {
        _set(12, frontendModulation);
    }

    public static FrontendScanMessage annex(byte b) {
        return new FrontendScanMessage(13, Byte.valueOf(b));
    }

    public byte getAnnex() {
        _assertTag(13);
        return ((Byte) this._value).byteValue();
    }

    public void setAnnex(byte b) {
        _set(13, Byte.valueOf(b));
    }

    public static FrontendScanMessage isHighPriority(boolean z) {
        return new FrontendScanMessage(14, Boolean.valueOf(z));
    }

    public boolean getIsHighPriority() {
        _assertTag(14);
        return ((Boolean) this._value).booleanValue();
    }

    public void setIsHighPriority(boolean z) {
        _set(14, Boolean.valueOf(z));
    }

    public static FrontendScanMessage dvbtCellIds(int[] iArr) {
        return new FrontendScanMessage(15, iArr);
    }

    public int[] getDvbtCellIds() {
        _assertTag(15);
        return (int[]) this._value;
    }

    public void setDvbtCellIds(int[] iArr) {
        _set(15, iArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeBoolean(getIsLocked());
                break;
            case 1:
                parcel.writeBoolean(getIsEnd());
                break;
            case 2:
                parcel.writeInt(getProgressPercent());
                break;
            case 3:
                parcel.writeLongArray(getFrequencies());
                break;
            case 4:
                parcel.writeIntArray(getSymbolRates());
                break;
            case 5:
                parcel.writeInt(getHierarchy());
                break;
            case 6:
                parcel.writeInt(getAnalogType());
                break;
            case 7:
                parcel.writeIntArray(getPlpIds());
                break;
            case 8:
                parcel.writeIntArray(getGroupIds());
                break;
            case 9:
                parcel.writeIntArray(getInputStreamIds());
                break;
            case 10:
                parcel.writeTypedObject(getStd(), i);
                break;
            case 11:
                parcel.writeTypedArray(getAtsc3PlpInfos(), i);
                break;
            case 12:
                parcel.writeTypedObject(getModulation(), i);
                break;
            case 13:
                parcel.writeByte(getAnnex());
                break;
            case 14:
                parcel.writeBoolean(getIsHighPriority());
                break;
            case 15:
                parcel.writeIntArray(getDvbtCellIds());
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, Boolean.valueOf(parcel.readBoolean()));
                return;
            case 1:
                _set(readInt, Boolean.valueOf(parcel.readBoolean()));
                return;
            case 2:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 3:
                _set(readInt, parcel.createLongArray());
                return;
            case 4:
                _set(readInt, parcel.createIntArray());
                return;
            case 5:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 6:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            case 7:
                _set(readInt, parcel.createIntArray());
                return;
            case 8:
                _set(readInt, parcel.createIntArray());
                return;
            case 9:
                _set(readInt, parcel.createIntArray());
                return;
            case 10:
                _set(readInt, (FrontendScanMessageStandard) parcel.readTypedObject(FrontendScanMessageStandard.CREATOR));
                return;
            case 11:
                _set(readInt, (FrontendScanAtsc3PlpInfo[]) parcel.createTypedArray(FrontendScanAtsc3PlpInfo.CREATOR));
                return;
            case 12:
                _set(readInt, (FrontendModulation) parcel.readTypedObject(FrontendModulation.CREATOR));
                return;
            case 13:
                _set(readInt, Byte.valueOf(parcel.readByte()));
                return;
            case 14:
                _set(readInt, Boolean.valueOf(parcel.readBoolean()));
                return;
            case 15:
                _set(readInt, parcel.createIntArray());
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 10:
                return describeContents(getStd());
            case 11:
                return describeContents(getAtsc3PlpInfos());
            case 12:
                return describeContents(getModulation());
            default:
                return 0;
        }
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
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
                return "isLocked";
            case 1:
                return "isEnd";
            case 2:
                return "progressPercent";
            case 3:
                return "frequencies";
            case 4:
                return "symbolRates";
            case 5:
                return "hierarchy";
            case 6:
                return "analogType";
            case 7:
                return "plpIds";
            case 8:
                return "groupIds";
            case 9:
                return "inputStreamIds";
            case 10:
                return "std";
            case 11:
                return "atsc3PlpInfos";
            case 12:
                return "modulation";
            case 13:
                return "annex";
            case 14:
                return "isHighPriority";
            case 15:
                return "dvbtCellIds";
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
