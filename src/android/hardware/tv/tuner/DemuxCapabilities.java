package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxCapabilities implements Parcelable {
    public static final Parcelable.Creator<DemuxCapabilities> CREATOR = new Parcelable.Creator<DemuxCapabilities>() { // from class: android.hardware.tv.tuner.DemuxCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxCapabilities createFromParcel(Parcel parcel) {
            DemuxCapabilities demuxCapabilities = new DemuxCapabilities();
            demuxCapabilities.readFromParcel(parcel);
            return demuxCapabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxCapabilities[] newArray(int i) {
            return new DemuxCapabilities[i];
        }
    };
    public int[] linkCaps;
    public int numDemux = 0;
    public int numRecord = 0;
    public int numPlayback = 0;
    public int numTsFilter = 0;
    public int numSectionFilter = 0;
    public int numAudioFilter = 0;
    public int numVideoFilter = 0;
    public int numPesFilter = 0;
    public int numPcrFilter = 0;
    public long numBytesInSectionFilter = 0;
    public int filterCaps = 0;
    public boolean bTimeFilter = false;

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
        parcel.writeInt(this.numDemux);
        parcel.writeInt(this.numRecord);
        parcel.writeInt(this.numPlayback);
        parcel.writeInt(this.numTsFilter);
        parcel.writeInt(this.numSectionFilter);
        parcel.writeInt(this.numAudioFilter);
        parcel.writeInt(this.numVideoFilter);
        parcel.writeInt(this.numPesFilter);
        parcel.writeInt(this.numPcrFilter);
        parcel.writeLong(this.numBytesInSectionFilter);
        parcel.writeInt(this.filterCaps);
        parcel.writeIntArray(this.linkCaps);
        parcel.writeBoolean(this.bTimeFilter);
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
                this.numDemux = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.numRecord = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.numPlayback = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.numTsFilter = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.numSectionFilter = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.numAudioFilter = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.numVideoFilter = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.numPesFilter = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.numPcrFilter = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.numBytesInSectionFilter = parcel.readLong();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.filterCaps = parcel.readInt();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.linkCaps = parcel.createIntArray();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                this.bTimeFilter = parcel.readBoolean();
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
