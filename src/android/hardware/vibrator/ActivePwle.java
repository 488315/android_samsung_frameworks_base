package android.hardware.vibrator;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ActivePwle implements Parcelable {
    public static final Parcelable.Creator<ActivePwle> CREATOR = new Parcelable.Creator<ActivePwle>() { // from class: android.hardware.vibrator.ActivePwle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivePwle createFromParcel(Parcel parcel) {
            ActivePwle activePwle = new ActivePwle();
            activePwle.readFromParcel(parcel);
            return activePwle;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivePwle[] newArray(int i) {
            return new ActivePwle[i];
        }
    };
    public float startAmplitude = 0.0f;
    public float startFrequency = 0.0f;
    public float endAmplitude = 0.0f;
    public float endFrequency = 0.0f;
    public int duration = 0;

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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeFloat(this.startAmplitude);
        parcel.writeFloat(this.startFrequency);
        parcel.writeFloat(this.endAmplitude);
        parcel.writeFloat(this.endFrequency);
        parcel.writeInt(this.duration);
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
                this.startAmplitude = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.startFrequency = parcel.readFloat();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.endAmplitude = parcel.readFloat();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.endFrequency = parcel.readFloat();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.duration = parcel.readInt();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
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
}
