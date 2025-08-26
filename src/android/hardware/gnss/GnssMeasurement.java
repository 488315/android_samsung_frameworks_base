package android.hardware.gnss;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GnssMeasurement implements Parcelable {
    public static final int ADR_STATE_CYCLE_SLIP = 4;
    public static final int ADR_STATE_HALF_CYCLE_RESOLVED = 8;
    public static final int ADR_STATE_RESET = 2;
    public static final int ADR_STATE_UNKNOWN = 0;
    public static final int ADR_STATE_VALID = 1;
    public static final Parcelable.Creator<GnssMeasurement> CREATOR = new Parcelable.Creator<GnssMeasurement>() { // from class: android.hardware.gnss.GnssMeasurement.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssMeasurement createFromParcel(Parcel parcel) {
            GnssMeasurement gnssMeasurement = new GnssMeasurement();
            gnssMeasurement.readFromParcel(parcel);
            return gnssMeasurement;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GnssMeasurement[] newArray(int i) {
            return new GnssMeasurement[i];
        }
    };
    public static final int HAS_AUTOMATIC_GAIN_CONTROL = 8192;
    public static final int HAS_CARRIER_CYCLES = 1024;
    public static final int HAS_CARRIER_FREQUENCY = 512;
    public static final int HAS_CARRIER_PHASE = 2048;
    public static final int HAS_CARRIER_PHASE_UNCERTAINTY = 4096;
    public static final int HAS_CORRELATION_VECTOR = 2097152;
    public static final int HAS_FULL_ISB = 65536;
    public static final int HAS_FULL_ISB_UNCERTAINTY = 131072;
    public static final int HAS_SATELLITE_ISB = 262144;
    public static final int HAS_SATELLITE_ISB_UNCERTAINTY = 524288;
    public static final int HAS_SATELLITE_PVT = 1048576;
    public static final int HAS_SNR = 1;
    public static final int STATE_2ND_CODE_LOCK = 65536;
    public static final int STATE_BDS_D2_BIT_SYNC = 256;
    public static final int STATE_BDS_D2_SUBFRAME_SYNC = 512;
    public static final int STATE_BIT_SYNC = 2;
    public static final int STATE_CODE_LOCK = 1;
    public static final int STATE_GAL_E1BC_CODE_LOCK = 1024;
    public static final int STATE_GAL_E1B_PAGE_SYNC = 4096;
    public static final int STATE_GAL_E1C_2ND_CODE_LOCK = 2048;
    public static final int STATE_GLO_STRING_SYNC = 64;
    public static final int STATE_GLO_TOD_DECODED = 128;
    public static final int STATE_GLO_TOD_KNOWN = 32768;
    public static final int STATE_MSEC_AMBIGUOUS = 16;
    public static final int STATE_SBAS_SYNC = 8192;
    public static final int STATE_SUBFRAME_SYNC = 4;
    public static final int STATE_SYMBOL_SYNC = 32;
    public static final int STATE_TOW_DECODED = 8;
    public static final int STATE_TOW_KNOWN = 16384;
    public static final int STATE_UNKNOWN = 0;
    public CorrelationVector[] correlationVectors;
    public SatellitePvt satellitePvt;
    public GnssSignalType signalType;
    public int flags = 0;
    public int svid = 0;
    public double timeOffsetNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public int state = 0;
    public long receivedSvTimeInNs = 0;
    public long receivedSvTimeUncertaintyInNs = 0;
    public double antennaCN0DbHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double basebandCN0DbHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double pseudorangeRateMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double pseudorangeRateUncertaintyMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public int accumulatedDeltaRangeState = 0;
    public double accumulatedDeltaRangeM = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double accumulatedDeltaRangeUncertaintyM = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public long carrierCycles = 0;
    public double carrierPhase = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double carrierPhaseUncertainty = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public int multipathIndicator = 0;
    public double snrDb = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double agcLevelDb = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double fullInterSignalBiasNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double fullInterSignalBiasUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double satelliteInterSignalBiasNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public double satelliteInterSignalBiasUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.svid);
        parcel.writeTypedObject(this.signalType, i);
        parcel.writeDouble(this.timeOffsetNs);
        parcel.writeInt(this.state);
        parcel.writeLong(this.receivedSvTimeInNs);
        parcel.writeLong(this.receivedSvTimeUncertaintyInNs);
        parcel.writeDouble(this.antennaCN0DbHz);
        parcel.writeDouble(this.basebandCN0DbHz);
        parcel.writeDouble(this.pseudorangeRateMps);
        parcel.writeDouble(this.pseudorangeRateUncertaintyMps);
        parcel.writeInt(this.accumulatedDeltaRangeState);
        parcel.writeDouble(this.accumulatedDeltaRangeM);
        parcel.writeDouble(this.accumulatedDeltaRangeUncertaintyM);
        parcel.writeLong(this.carrierCycles);
        parcel.writeDouble(this.carrierPhase);
        parcel.writeDouble(this.carrierPhaseUncertainty);
        parcel.writeInt(this.multipathIndicator);
        parcel.writeDouble(this.snrDb);
        parcel.writeDouble(this.agcLevelDb);
        parcel.writeDouble(this.fullInterSignalBiasNs);
        parcel.writeDouble(this.fullInterSignalBiasUncertaintyNs);
        parcel.writeDouble(this.satelliteInterSignalBiasNs);
        parcel.writeDouble(this.satelliteInterSignalBiasUncertaintyNs);
        parcel.writeTypedObject(this.satellitePvt, i);
        parcel.writeTypedArray(this.correlationVectors, i);
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
                this.flags = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.svid = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.signalType = (GnssSignalType) parcel.readTypedObject(GnssSignalType.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.timeOffsetNs = parcel.readDouble();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.state = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.receivedSvTimeInNs = parcel.readLong();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.receivedSvTimeUncertaintyInNs = parcel.readLong();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.antennaCN0DbHz = parcel.readDouble();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.basebandCN0DbHz = parcel.readDouble();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.pseudorangeRateMps = parcel.readDouble();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.pseudorangeRateUncertaintyMps = parcel.readDouble();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.accumulatedDeltaRangeState = parcel.readInt();
                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                this.accumulatedDeltaRangeM = parcel.readDouble();
                                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                                    this.accumulatedDeltaRangeUncertaintyM = parcel.readDouble();
                                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                                        this.carrierCycles = parcel.readLong();
                                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                                            this.carrierPhase = parcel.readDouble();
                                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                                this.carrierPhaseUncertainty = parcel.readDouble();
                                                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                                                    this.multipathIndicator = parcel.readInt();
                                                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                                                        this.snrDb = parcel.readDouble();
                                                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                                                            this.agcLevelDb = parcel.readDouble();
                                                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                                                this.fullInterSignalBiasNs = parcel.readDouble();
                                                                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                                                                    this.fullInterSignalBiasUncertaintyNs = parcel.readDouble();
                                                                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                                                                        this.satelliteInterSignalBiasNs = parcel.readDouble();
                                                                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                                                                            this.satelliteInterSignalBiasUncertaintyNs = parcel.readDouble();
                                                                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                                                                this.satellitePvt = (SatellitePvt) parcel.readTypedObject(SatellitePvt.CREATOR);
                                                                                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                                                                                    this.correlationVectors = (CorrelationVector[]) parcel.createTypedArray(CorrelationVector.CREATOR);
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
        return describeContents(this.correlationVectors) | describeContents(this.signalType) | describeContents(this.satellitePvt);
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
