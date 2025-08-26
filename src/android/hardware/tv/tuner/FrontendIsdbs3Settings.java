package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendIsdbs3Settings implements Parcelable {
    public static final Parcelable.Creator<FrontendIsdbs3Settings> CREATOR = new Parcelable.Creator<FrontendIsdbs3Settings>() { // from class: android.hardware.tv.tuner.FrontendIsdbs3Settings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIsdbs3Settings createFromParcel(Parcel parcel) {
            FrontendIsdbs3Settings frontendIsdbs3Settings = new FrontendIsdbs3Settings();
            frontendIsdbs3Settings.readFromParcel(parcel);
            return frontendIsdbs3Settings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIsdbs3Settings[] newArray(int i) {
            return new FrontendIsdbs3Settings[i];
        }
    };
    public long frequency = 0;
    public long endFrequency = 0;
    public int streamId = 0;
    public int streamIdType = 2;
    public int modulation = 0;
    public int coderate = 0;
    public int symbolRate = 0;
    public int rolloff = 0;

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
        parcel.writeLong(this.frequency);
        parcel.writeLong(this.endFrequency);
        parcel.writeInt(this.streamId);
        parcel.writeInt(this.streamIdType);
        parcel.writeInt(this.modulation);
        parcel.writeInt(this.coderate);
        parcel.writeInt(this.symbolRate);
        parcel.writeInt(this.rolloff);
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
                this.frequency = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.endFrequency = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.streamId = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.streamIdType = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.modulation = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.coderate = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.symbolRate = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.rolloff = parcel.readInt();
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
