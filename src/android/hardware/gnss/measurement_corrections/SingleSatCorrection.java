package android.hardware.gnss.measurement_corrections;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SingleSatCorrection implements Parcelable {
    public static final Parcelable.Creator<SingleSatCorrection> CREATOR = new Parcelable.Creator<SingleSatCorrection>() { // from class: android.hardware.gnss.measurement_corrections.SingleSatCorrection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SingleSatCorrection createFromParcel(Parcel parcel) {
            SingleSatCorrection singleSatCorrection = new SingleSatCorrection();
            singleSatCorrection.readFromParcel(parcel);
            return singleSatCorrection;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SingleSatCorrection[] newArray(int i) {
            return new SingleSatCorrection[i];
        }
    };
    public static final int SINGLE_SAT_CORRECTION_HAS_COMBINED_ATTENUATION = 16;
    public static final int SINGLE_SAT_CORRECTION_HAS_COMBINED_EXCESS_PATH_LENGTH = 2;
    public static final int SINGLE_SAT_CORRECTION_HAS_COMBINED_EXCESS_PATH_LENGTH_UNC = 4;
    public static final int SINGLE_SAT_CORRECTION_HAS_SAT_IS_LOS_PROBABILITY = 1;
    public int constellation;
    public ExcessPathInfo[] excessPathInfos;
    public int singleSatCorrectionFlags = 0;
    public int svid = 0;
    public long carrierFrequencyHz = 0;
    public float probSatIsLos = 0.0f;
    public float combinedExcessPathLengthMeters = 0.0f;
    public float combinedExcessPathLengthUncertaintyMeters = 0.0f;
    public float combinedAttenuationDb = 0.0f;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.singleSatCorrectionFlags);
        parcel.writeInt(this.constellation);
        parcel.writeInt(this.svid);
        parcel.writeLong(this.carrierFrequencyHz);
        parcel.writeFloat(this.probSatIsLos);
        parcel.writeFloat(this.combinedExcessPathLengthMeters);
        parcel.writeFloat(this.combinedExcessPathLengthUncertaintyMeters);
        parcel.writeFloat(this.combinedAttenuationDb);
        parcel.writeTypedArray(this.excessPathInfos, i);
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
                this.singleSatCorrectionFlags = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.constellation = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.svid = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.carrierFrequencyHz = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.probSatIsLos = parcel.readFloat();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.combinedExcessPathLengthMeters = parcel.readFloat();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.combinedExcessPathLengthUncertaintyMeters = parcel.readFloat();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.combinedAttenuationDb = parcel.readFloat();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.excessPathInfos = (ExcessPathInfo[]) parcel.createTypedArray(ExcessPathInfo.CREATOR);
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
        return describeContents(this.excessPathInfos);
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

    public static class ExcessPathInfo implements Parcelable {
        public static final Parcelable.Creator<ExcessPathInfo> CREATOR = new Parcelable.Creator<ExcessPathInfo>() { // from class: android.hardware.gnss.measurement_corrections.SingleSatCorrection.ExcessPathInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ExcessPathInfo createFromParcel(Parcel parcel) {
                ExcessPathInfo excessPathInfo = new ExcessPathInfo();
                excessPathInfo.readFromParcel(parcel);
                return excessPathInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ExcessPathInfo[] newArray(int i) {
                return new ExcessPathInfo[i];
            }
        };
        public static final int EXCESS_PATH_INFO_HAS_ATTENUATION = 8;
        public static final int EXCESS_PATH_INFO_HAS_EXCESS_PATH_LENGTH = 1;
        public static final int EXCESS_PATH_INFO_HAS_EXCESS_PATH_LENGTH_UNC = 2;
        public static final int EXCESS_PATH_INFO_HAS_REFLECTING_PLANE = 4;
        public ReflectingPlane reflectingPlane;
        public int excessPathInfoFlags = 0;
        public float excessPathLengthMeters = 0.0f;
        public float excessPathLengthUncertaintyMeters = 0.0f;
        public float attenuationDb = 0.0f;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.excessPathInfoFlags);
            parcel.writeFloat(this.excessPathLengthMeters);
            parcel.writeFloat(this.excessPathLengthUncertaintyMeters);
            parcel.writeTypedObject(this.reflectingPlane, i);
            parcel.writeFloat(this.attenuationDb);
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
                    this.excessPathInfoFlags = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.excessPathLengthMeters = parcel.readFloat();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.excessPathLengthUncertaintyMeters = parcel.readFloat();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.reflectingPlane = (ReflectingPlane) parcel.readTypedObject(ReflectingPlane.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.attenuationDb = parcel.readFloat();
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.reflectingPlane);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
