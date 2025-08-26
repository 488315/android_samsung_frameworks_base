package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendDvbcSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendDvbcSettings> CREATOR = new Parcelable.Creator<FrontendDvbcSettings>() { // from class: android.hardware.tv.tuner.FrontendDvbcSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbcSettings createFromParcel(Parcel parcel) {
            FrontendDvbcSettings frontendDvbcSettings = new FrontendDvbcSettings();
            frontendDvbcSettings.readFromParcel(parcel);
            return frontendDvbcSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbcSettings[] newArray(int i) {
            return new FrontendDvbcSettings[i];
        }
    };
    public long frequency = 0;
    public long endFrequency = 0;
    public int modulation = 0;
    public long fec = 0;
    public int symbolRate = 0;
    public int outerFec = 0;
    public byte annex = 0;
    public int inversion = 0;
    public int interleaveMode = 0;
    public int bandwidth = 0;

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
        parcel.writeInt(this.modulation);
        parcel.writeLong(this.fec);
        parcel.writeInt(this.symbolRate);
        parcel.writeInt(this.outerFec);
        parcel.writeByte(this.annex);
        parcel.writeInt(this.inversion);
        parcel.writeInt(this.interleaveMode);
        parcel.writeInt(this.bandwidth);
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
                        this.modulation = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.fec = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.symbolRate = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.outerFec = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.annex = parcel.readByte();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.inversion = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.interleaveMode = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.bandwidth = parcel.readInt();
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
