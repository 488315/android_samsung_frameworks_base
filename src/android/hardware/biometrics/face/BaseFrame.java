package android.hardware.biometrics.face;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class BaseFrame implements Parcelable {
    public static final Parcelable.Creator<BaseFrame> CREATOR = new Parcelable.Creator<BaseFrame>() { // from class: android.hardware.biometrics.face.BaseFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseFrame createFromParcel(Parcel parcel) {
            BaseFrame baseFrame = new BaseFrame();
            baseFrame.readFromParcel(parcel);
            return baseFrame;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BaseFrame[] newArray(int i) {
            return new BaseFrame[i];
        }
    };
    public byte acquiredInfo = 0;
    public int vendorCode = 0;
    public float pan = 0.0f;
    public float tilt = 0.0f;
    public float distance = 0.0f;
    public boolean isCancellable = false;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.acquiredInfo);
        parcel.writeInt(this.vendorCode);
        parcel.writeFloat(this.pan);
        parcel.writeFloat(this.tilt);
        parcel.writeFloat(this.distance);
        parcel.writeBoolean(this.isCancellable);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.acquiredInfo = parcel.readByte();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.vendorCode = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.pan = parcel.readFloat();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.tilt = parcel.readFloat();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.distance = parcel.readFloat();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.isCancellable = parcel.readBoolean();
                                    if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
