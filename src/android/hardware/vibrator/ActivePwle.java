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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeFloat(this.startAmplitude);
        parcel.writeFloat(this.startFrequency);
        parcel.writeFloat(this.endAmplitude);
        parcel.writeFloat(this.endFrequency);
        parcel.writeInt(this.duration);
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
                this.startAmplitude = parcel.readFloat();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.startFrequency = parcel.readFloat();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.endAmplitude = parcel.readFloat();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.endFrequency = parcel.readFloat();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.duration = parcel.readInt();
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
