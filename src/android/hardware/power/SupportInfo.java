package android.hardware.power;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SupportInfo implements Parcelable {
    public static final Parcelable.Creator<SupportInfo> CREATOR = new Parcelable.Creator<SupportInfo>() { // from class: android.hardware.power.SupportInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SupportInfo createFromParcel(Parcel parcel) {
            SupportInfo supportInfo = new SupportInfo();
            supportInfo.readFromParcel(parcel);
            return supportInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SupportInfo[] newArray(int i) {
            return new SupportInfo[i];
        }
    };
    public CompositionDataSupportInfo compositionData;
    public HeadroomSupportInfo headroom;
    public boolean usesSessions = false;
    public long boosts = 0;
    public long modes = 0;
    public long sessionHints = 0;
    public long sessionModes = 0;
    public long sessionTags = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.usesSessions);
        parcel.writeLong(this.boosts);
        parcel.writeLong(this.modes);
        parcel.writeLong(this.sessionHints);
        parcel.writeLong(this.sessionModes);
        parcel.writeLong(this.sessionTags);
        parcel.writeTypedObject(this.compositionData, i);
        parcel.writeTypedObject(this.headroom, i);
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
                this.usesSessions = parcel.readBoolean();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.boosts = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.modes = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.sessionHints = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.sessionModes = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.sessionTags = parcel.readLong();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.compositionData = (CompositionDataSupportInfo) parcel.readTypedObject(CompositionDataSupportInfo.CREATOR);
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.headroom = (HeadroomSupportInfo) parcel.readTypedObject(HeadroomSupportInfo.CREATOR);
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
        return describeContents(this.headroom) | describeContents(this.compositionData);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public static class CompositionDataSupportInfo implements Parcelable {
        public static final Parcelable.Creator<CompositionDataSupportInfo> CREATOR = new Parcelable.Creator<CompositionDataSupportInfo>() { // from class: android.hardware.power.SupportInfo.CompositionDataSupportInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CompositionDataSupportInfo createFromParcel(Parcel parcel) {
                CompositionDataSupportInfo compositionDataSupportInfo = new CompositionDataSupportInfo();
                compositionDataSupportInfo.readFromParcel(parcel);
                return compositionDataSupportInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CompositionDataSupportInfo[] newArray(int i) {
                return new CompositionDataSupportInfo[i];
            }
        };
        public boolean isSupported = false;
        public boolean disableGpuFences = false;
        public int maxBatchSize = 0;
        public boolean alwaysBatch = false;

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
            parcel.writeBoolean(this.isSupported);
            parcel.writeBoolean(this.disableGpuFences);
            parcel.writeInt(this.maxBatchSize);
            parcel.writeBoolean(this.alwaysBatch);
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
                    this.isSupported = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.disableGpuFences = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.maxBatchSize = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.alwaysBatch = parcel.readBoolean();
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

    public static class HeadroomSupportInfo implements Parcelable {
        public static final Parcelable.Creator<HeadroomSupportInfo> CREATOR = new Parcelable.Creator<HeadroomSupportInfo>() { // from class: android.hardware.power.SupportInfo.HeadroomSupportInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HeadroomSupportInfo createFromParcel(Parcel parcel) {
                HeadroomSupportInfo headroomSupportInfo = new HeadroomSupportInfo();
                headroomSupportInfo.readFromParcel(parcel);
                return headroomSupportInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HeadroomSupportInfo[] newArray(int i) {
                return new HeadroomSupportInfo[i];
            }
        };
        public boolean isCpuSupported = false;
        public boolean isGpuSupported = false;
        public int cpuMinIntervalMillis = 0;
        public int gpuMinIntervalMillis = 0;
        public int cpuMinCalculationWindowMillis = 50;
        public int cpuMaxCalculationWindowMillis = 10000;
        public int gpuMinCalculationWindowMillis = 50;
        public int gpuMaxCalculationWindowMillis = 10000;
        public int cpuMaxTidCount = 5;

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
            parcel.writeBoolean(this.isCpuSupported);
            parcel.writeBoolean(this.isGpuSupported);
            parcel.writeInt(this.cpuMinIntervalMillis);
            parcel.writeInt(this.gpuMinIntervalMillis);
            parcel.writeInt(this.cpuMinCalculationWindowMillis);
            parcel.writeInt(this.cpuMaxCalculationWindowMillis);
            parcel.writeInt(this.gpuMinCalculationWindowMillis);
            parcel.writeInt(this.gpuMaxCalculationWindowMillis);
            parcel.writeInt(this.cpuMaxTidCount);
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
                    this.isCpuSupported = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.isGpuSupported = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.cpuMinIntervalMillis = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.gpuMinIntervalMillis = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.cpuMinCalculationWindowMillis = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.cpuMaxCalculationWindowMillis = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.gpuMinCalculationWindowMillis = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.gpuMaxCalculationWindowMillis = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.cpuMaxTidCount = parcel.readInt();
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
    }
}
