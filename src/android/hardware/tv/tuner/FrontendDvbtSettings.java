package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendDvbtSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendDvbtSettings> CREATOR = new Parcelable.Creator<FrontendDvbtSettings>() { // from class: android.hardware.tv.tuner.FrontendDvbtSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbtSettings createFromParcel(Parcel parcel) {
            FrontendDvbtSettings frontendDvbtSettings = new FrontendDvbtSettings();
            frontendDvbtSettings.readFromParcel(parcel);
            return frontendDvbtSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendDvbtSettings[] newArray(int i) {
            return new FrontendDvbtSettings[i];
        }
    };
    public long frequency = 0;
    public long endFrequency = 0;
    public int inversion = 0;
    public int transmissionMode = 0;
    public int bandwidth = 0;
    public int constellation = 0;
    public int hierarchy = 0;
    public int hpCoderate = 0;
    public int lpCoderate = 0;
    public int guardInterval = 0;
    public boolean isHighPriority = false;
    public byte standard = 0;
    public boolean isMiso = false;
    public int plpMode = 0;
    public int plpId = 0;
    public int plpGroupId = 0;

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
        parcel.writeInt(this.constellation);
        parcel.writeInt(this.hierarchy);
        parcel.writeInt(this.hpCoderate);
        parcel.writeInt(this.lpCoderate);
        parcel.writeInt(this.guardInterval);
        parcel.writeBoolean(this.isHighPriority);
        parcel.writeByte(this.standard);
        parcel.writeBoolean(this.isMiso);
        parcel.writeInt(this.plpMode);
        parcel.writeInt(this.plpId);
        parcel.writeInt(this.plpGroupId);
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
                                    this.constellation = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.hierarchy = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.hpCoderate = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.lpCoderate = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.guardInterval = parcel.readInt();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.isHighPriority = parcel.readBoolean();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.standard = parcel.readByte();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                this.isMiso = parcel.readBoolean();
                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                    this.plpMode = parcel.readInt();
                                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                                        this.plpId = parcel.readInt();
                                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                                            this.plpGroupId = parcel.readInt();
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
