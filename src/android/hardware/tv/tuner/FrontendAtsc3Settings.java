package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendAtsc3Settings implements Parcelable {
    public static final Parcelable.Creator<FrontendAtsc3Settings> CREATOR = new Parcelable.Creator<FrontendAtsc3Settings>() { // from class: android.hardware.tv.tuner.FrontendAtsc3Settings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAtsc3Settings createFromParcel(Parcel parcel) {
            FrontendAtsc3Settings frontendAtsc3Settings = new FrontendAtsc3Settings();
            frontendAtsc3Settings.readFromParcel(parcel);
            return frontendAtsc3Settings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendAtsc3Settings[] newArray(int i) {
            return new FrontendAtsc3Settings[i];
        }
    };
    public FrontendAtsc3PlpSettings[] plpSettings;
    public long frequency = 0;
    public long endFrequency = 0;
    public int bandwidth = 0;
    public int inversion = 0;
    public byte demodOutputFormat = 0;

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
        parcel.writeInt(this.bandwidth);
        parcel.writeInt(this.inversion);
        parcel.writeByte(this.demodOutputFormat);
        parcel.writeTypedArray(this.plpSettings, i);
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
                        this.bandwidth = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.inversion = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.demodOutputFormat = parcel.readByte();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.plpSettings = (FrontendAtsc3PlpSettings[]) parcel.createTypedArray(FrontendAtsc3PlpSettings.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.plpSettings);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
