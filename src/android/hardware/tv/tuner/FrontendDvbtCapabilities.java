package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendDvbtCapabilities implements Parcelable {
    public static final Parcelable.Creator<FrontendDvbtCapabilities> CREATOR = new Parcelable.Creator<FrontendDvbtCapabilities>() { // from class: android.hardware.tv.tuner.FrontendDvbtCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbtCapabilities createFromParcel(Parcel parcel) {
            FrontendDvbtCapabilities frontendDvbtCapabilities = new FrontendDvbtCapabilities();
            frontendDvbtCapabilities.readFromParcel(parcel);
            return frontendDvbtCapabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbtCapabilities[] newArray(int i) {
            return new FrontendDvbtCapabilities[i];
        }
    };
    public int transmissionModeCap = 0;
    public int bandwidthCap = 0;
    public int constellationCap = 0;
    public int coderateCap = 0;
    public int hierarchyCap = 0;
    public int guardIntervalCap = 0;
    public boolean isT2Supported = false;
    public boolean isMisoSupported = false;

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
        parcel.writeInt(this.transmissionModeCap);
        parcel.writeInt(this.bandwidthCap);
        parcel.writeInt(this.constellationCap);
        parcel.writeInt(this.coderateCap);
        parcel.writeInt(this.hierarchyCap);
        parcel.writeInt(this.guardIntervalCap);
        parcel.writeBoolean(this.isT2Supported);
        parcel.writeBoolean(this.isMisoSupported);
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
                this.transmissionModeCap = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.bandwidthCap = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.constellationCap = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.coderateCap = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.hierarchyCap = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.guardIntervalCap = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.isT2Supported = parcel.readBoolean();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.isMisoSupported = parcel.readBoolean();
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
