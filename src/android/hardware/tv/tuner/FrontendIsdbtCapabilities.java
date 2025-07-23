package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendIsdbtCapabilities implements Parcelable {
    public static final Parcelable.Creator<FrontendIsdbtCapabilities> CREATOR = new Parcelable.Creator<FrontendIsdbtCapabilities>() { // from class: android.hardware.tv.tuner.FrontendIsdbtCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIsdbtCapabilities createFromParcel(Parcel parcel) {
            FrontendIsdbtCapabilities frontendIsdbtCapabilities = new FrontendIsdbtCapabilities();
            frontendIsdbtCapabilities.readFromParcel(parcel);
            return frontendIsdbtCapabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIsdbtCapabilities[] newArray(int i) {
            return new FrontendIsdbtCapabilities[i];
        }
    };
    public int modeCap = 0;
    public int bandwidthCap = 0;
    public int modulationCap = 0;
    public int coderateCap = 0;
    public int guardIntervalCap = 0;
    public int timeInterleaveCap = 0;
    public boolean isSegmentAuto = false;
    public boolean isFullSegment = false;

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
        parcel.writeInt(this.modeCap);
        parcel.writeInt(this.bandwidthCap);
        parcel.writeInt(this.modulationCap);
        parcel.writeInt(this.coderateCap);
        parcel.writeInt(this.guardIntervalCap);
        parcel.writeInt(this.timeInterleaveCap);
        parcel.writeBoolean(this.isSegmentAuto);
        parcel.writeBoolean(this.isFullSegment);
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
                this.modeCap = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.bandwidthCap = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.modulationCap = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.coderateCap = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.guardIntervalCap = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.timeInterleaveCap = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.isSegmentAuto = parcel.readBoolean();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.isFullSegment = parcel.readBoolean();
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
