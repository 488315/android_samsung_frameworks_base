package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GnssData implements Parcelable {
    public static final Parcelable.Creator<GnssData> CREATOR = new Parcelable.Creator<GnssData>() { // from class: android.hardware.gnss.GnssData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssData createFromParcel(Parcel parcel) {
            GnssData gnssData = new GnssData();
            gnssData.readFromParcel(parcel);
            return gnssData;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssData[] newArray(int i) {
            return new GnssData[i];
        }
    };
    public GnssClock clock;
    public ElapsedRealtime elapsedRealtime;
    public GnssAgc[] gnssAgcs = new GnssAgc[0];
    public GnssMeasurement[] measurements;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.measurements, i);
        parcel.writeTypedObject(this.clock, i);
        parcel.writeTypedObject(this.elapsedRealtime, i);
        parcel.writeTypedArray(this.gnssAgcs, i);
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
                this.measurements = (GnssMeasurement[]) parcel.createTypedArray(GnssMeasurement.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.clock = (GnssClock) parcel.readTypedObject(GnssClock.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.elapsedRealtime = (ElapsedRealtime) parcel.readTypedObject(ElapsedRealtime.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.gnssAgcs = (GnssAgc[]) parcel.createTypedArray(GnssAgc.CREATOR);
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.gnssAgcs) | describeContents(this.measurements) | describeContents(this.clock) | describeContents(this.elapsedRealtime);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public static class GnssAgc implements Parcelable {
        public static final Parcelable.Creator<GnssAgc> CREATOR = new Parcelable.Creator<GnssAgc>() { // from class: android.hardware.gnss.GnssData.GnssAgc.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssAgc createFromParcel(Parcel parcel) {
                GnssAgc gnssAgc = new GnssAgc();
                gnssAgc.readFromParcel(parcel);
                return gnssAgc;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssAgc[] newArray(int i) {
                return new GnssAgc[i];
            }
        };
        public double agcLevelDb = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public int constellation = 0;
        public long carrierFrequencyHz = 0;

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
            parcel.writeDouble(this.agcLevelDb);
            parcel.writeInt(this.constellation);
            parcel.writeLong(this.carrierFrequencyHz);
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
                    this.agcLevelDb = parcel.readDouble();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.constellation = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.carrierFrequencyHz = parcel.readLong();
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
}
