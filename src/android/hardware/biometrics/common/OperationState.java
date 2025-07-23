package android.hardware.biometrics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableHolder;

/* loaded from: classes2.dex */
public final class OperationState implements Parcelable {
    public static final Parcelable.Creator<OperationState> CREATOR = new Parcelable.Creator<OperationState>() { // from class: android.hardware.biometrics.common.OperationState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperationState createFromParcel(Parcel parcel) {
            return new OperationState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperationState[] newArray(int i) {
            return new OperationState[i];
        }
    };
    public static final int faceOperationState = 1;
    public static final int fingerprintOperationState = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int faceOperationState = 1;
        public static final int fingerprintOperationState = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public OperationState() {
        this._tag = 0;
        this._value = null;
    }

    private OperationState(Parcel parcel) {
        readFromParcel(parcel);
    }

    private OperationState(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static OperationState fingerprintOperationState(FingerprintOperationState fingerprintOperationState2) {
        return new OperationState(0, fingerprintOperationState2);
    }

    public FingerprintOperationState getFingerprintOperationState() {
        _assertTag(0);
        return (FingerprintOperationState) this._value;
    }

    public void setFingerprintOperationState(FingerprintOperationState fingerprintOperationState2) {
        _set(0, fingerprintOperationState2);
    }

    public static OperationState faceOperationState(FaceOperationState faceOperationState2) {
        return new OperationState(1, faceOperationState2);
    }

    public FaceOperationState getFaceOperationState() {
        _assertTag(1);
        return (FaceOperationState) this._value;
    }

    public void setFaceOperationState(FaceOperationState faceOperationState2) {
        _set(1, faceOperationState2);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getFingerprintOperationState(), i);
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeTypedObject(getFaceOperationState(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            _set(readInt, (FingerprintOperationState) parcel.readTypedObject(FingerprintOperationState.CREATOR));
        } else if (readInt == 1) {
            _set(readInt, (FaceOperationState) parcel.readTypedObject(FaceOperationState.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 0) {
            return describeContents(getFingerprintOperationState());
        }
        if (tag != 1) {
            return 0;
        }
        return describeContents(getFaceOperationState());
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
        if (i == 0) {
            return "fingerprintOperationState";
        }
        if (i == 1) {
            return "faceOperationState";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public static class FingerprintOperationState implements Parcelable {
        public static final Parcelable.Creator<FingerprintOperationState> CREATOR = new Parcelable.Creator<FingerprintOperationState>() { // from class: android.hardware.biometrics.common.OperationState.FingerprintOperationState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FingerprintOperationState createFromParcel(Parcel parcel) {
                FingerprintOperationState fingerprintOperationState = new FingerprintOperationState();
                fingerprintOperationState.readFromParcel(parcel);
                return fingerprintOperationState;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FingerprintOperationState[] newArray(int i) {
                return new FingerprintOperationState[i];
            }
        };
        public final ParcelableHolder extension = new ParcelableHolder(1);
        public boolean isHardwareIgnoringTouches = false;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.extension, 0);
            parcel.writeBoolean(this.isHardwareIgnoringTouches);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    if (parcel.readInt() != 0) {
                        this.extension.readFromParcel(parcel);
                    }
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isHardwareIgnoringTouches = parcel.readBoolean();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.extension);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }

    public static class FaceOperationState implements Parcelable {
        public static final Parcelable.Creator<FaceOperationState> CREATOR = new Parcelable.Creator<FaceOperationState>() { // from class: android.hardware.biometrics.common.OperationState.FaceOperationState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FaceOperationState createFromParcel(Parcel parcel) {
                FaceOperationState faceOperationState = new FaceOperationState();
                faceOperationState.readFromParcel(parcel);
                return faceOperationState;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FaceOperationState[] newArray(int i) {
                return new FaceOperationState[i];
            }
        };
        public final ParcelableHolder extension = new ParcelableHolder(1);

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.extension, 0);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    if (parcel.readInt() != 0) {
                        this.extension.readFromParcel(parcel);
                    }
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.extension);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
