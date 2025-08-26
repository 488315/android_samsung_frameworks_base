package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendIsdbtSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendIsdbtSettings> CREATOR = new Parcelable.Creator<FrontendIsdbtSettings>() { // from class: android.hardware.tv.tuner.FrontendIsdbtSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIsdbtSettings createFromParcel(Parcel parcel) {
            FrontendIsdbtSettings frontendIsdbtSettings = new FrontendIsdbtSettings();
            frontendIsdbtSettings.readFromParcel(parcel);
            return frontendIsdbtSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIsdbtSettings[] newArray(int i) {
            return new FrontendIsdbtSettings[i];
        }
    };
    public FrontendIsdbtLayerSettings[] layerSettings;
    public long frequency = 0;
    public long endFrequency = 0;
    public int inversion = 0;
    public int bandwidth = 0;
    public int mode = 0;
    public int guardInterval = 0;
    public int serviceAreaId = 0;
    public int partialReceptionFlag = 0;

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
        parcel.writeInt(this.bandwidth);
        parcel.writeInt(this.mode);
        parcel.writeInt(this.guardInterval);
        parcel.writeInt(this.serviceAreaId);
        parcel.writeInt(this.partialReceptionFlag);
        parcel.writeTypedArray(this.layerSettings, i);
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
                            this.bandwidth = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.mode = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.guardInterval = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.serviceAreaId = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.partialReceptionFlag = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.layerSettings = (FrontendIsdbtLayerSettings[]) parcel.createTypedArray(FrontendIsdbtLayerSettings.CREATOR);
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
        return describeContents(this.layerSettings);
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
