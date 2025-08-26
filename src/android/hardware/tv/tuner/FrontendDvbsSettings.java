package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendDvbsSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendDvbsSettings> CREATOR = new Parcelable.Creator<FrontendDvbsSettings>() { // from class: android.hardware.tv.tuner.FrontendDvbsSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbsSettings createFromParcel(Parcel parcel) {
            FrontendDvbsSettings frontendDvbsSettings = new FrontendDvbsSettings();
            frontendDvbsSettings.readFromParcel(parcel);
            return frontendDvbsSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbsSettings[] newArray(int i) {
            return new FrontendDvbsSettings[i];
        }
    };
    public FrontendDvbsCodeRate coderate;
    public long frequency = 0;
    public long endFrequency = 0;
    public int inversion = 0;
    public int modulation = 0;
    public int symbolRate = 0;
    public int rolloff = 0;
    public int pilot = 0;
    public int inputStreamId = 0;
    public byte standard = 0;
    public int vcmMode = 0;
    public int scanType = 0;
    public boolean isDiseqcRxMessage = false;

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
        parcel.writeInt(this.inversion);
        parcel.writeInt(this.modulation);
        parcel.writeTypedObject(this.coderate, i);
        parcel.writeInt(this.symbolRate);
        parcel.writeInt(this.rolloff);
        parcel.writeInt(this.pilot);
        parcel.writeInt(this.inputStreamId);
        parcel.writeByte(this.standard);
        parcel.writeInt(this.vcmMode);
        parcel.writeInt(this.scanType);
        parcel.writeBoolean(this.isDiseqcRxMessage);
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
                        this.inversion = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.modulation = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.coderate = (FrontendDvbsCodeRate) parcel.readTypedObject(FrontendDvbsCodeRate.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.symbolRate = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.rolloff = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.pilot = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.inputStreamId = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.standard = parcel.readByte();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.vcmMode = parcel.readInt();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.scanType = parcel.readInt();
                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                this.isDiseqcRxMessage = parcel.readBoolean();
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
        return describeContents(this.coderate);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
