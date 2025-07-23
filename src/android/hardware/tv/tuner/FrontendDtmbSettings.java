package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendDtmbSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendDtmbSettings> CREATOR = new Parcelable.Creator<FrontendDtmbSettings>() { // from class: android.hardware.tv.tuner.FrontendDtmbSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDtmbSettings createFromParcel(Parcel parcel) {
            FrontendDtmbSettings frontendDtmbSettings = new FrontendDtmbSettings();
            frontendDtmbSettings.readFromParcel(parcel);
            return frontendDtmbSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDtmbSettings[] newArray(int i) {
            return new FrontendDtmbSettings[i];
        }
    };
    public long frequency = 0;
    public long endFrequency = 0;
    public int inversion = 0;
    public int transmissionMode = 0;
    public int bandwidth = 0;
    public int modulation = 0;
    public int codeRate = 0;
    public int guardInterval = 0;
    public int interleaveMode = 0;

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
        parcel.writeLong(this.frequency);
        parcel.writeLong(this.endFrequency);
        parcel.writeInt(this.inversion);
        parcel.writeInt(this.transmissionMode);
        parcel.writeInt(this.bandwidth);
        parcel.writeInt(this.modulation);
        parcel.writeInt(this.codeRate);
        parcel.writeInt(this.guardInterval);
        parcel.writeInt(this.interleaveMode);
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
                this.frequency = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.endFrequency = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.inversion = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.transmissionMode = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.bandwidth = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.modulation = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.codeRate = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.guardInterval = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.interleaveMode = parcel.readInt();
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
