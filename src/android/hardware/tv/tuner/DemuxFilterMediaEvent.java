package android.hardware.tv.tuner;

import android.hardware.common.NativeHandle;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class DemuxFilterMediaEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterMediaEvent> CREATOR = new Parcelable.Creator<DemuxFilterMediaEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterMediaEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterMediaEvent createFromParcel(Parcel parcel) {
            DemuxFilterMediaEvent demuxFilterMediaEvent = new DemuxFilterMediaEvent();
            demuxFilterMediaEvent.readFromParcel(parcel);
            return demuxFilterMediaEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterMediaEvent[] newArray(int i) {
            return new DemuxFilterMediaEvent[i];
        }
    };
    public NativeHandle avMemory;
    public DemuxFilterMediaEventExtraMetaData extraMetaData;
    public DemuxFilterScIndexMask scIndexMask;
    public int streamId = 0;
    public boolean isPtsPresent = false;
    public long pts = 0;
    public boolean isDtsPresent = false;
    public long dts = 0;
    public long dataLength = 0;
    public long offset = 0;
    public boolean isSecureMemory = false;
    public long avDataId = 0;
    public int mpuSequenceNumber = 0;
    public boolean isPesPrivateData = false;
    public int numDataPieces = 0;
    public int indexInDataGroup = 0;
    public int dataGroupId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.streamId);
        parcel.writeBoolean(this.isPtsPresent);
        parcel.writeLong(this.pts);
        parcel.writeBoolean(this.isDtsPresent);
        parcel.writeLong(this.dts);
        parcel.writeLong(this.dataLength);
        parcel.writeLong(this.offset);
        parcel.writeTypedObject(this.avMemory, i);
        parcel.writeBoolean(this.isSecureMemory);
        parcel.writeLong(this.avDataId);
        parcel.writeInt(this.mpuSequenceNumber);
        parcel.writeBoolean(this.isPesPrivateData);
        parcel.writeTypedObject(this.extraMetaData, i);
        parcel.writeTypedObject(this.scIndexMask, i);
        parcel.writeInt(this.numDataPieces);
        parcel.writeInt(this.indexInDataGroup);
        parcel.writeInt(this.dataGroupId);
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
                this.streamId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.isPtsPresent = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.pts = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isDtsPresent = parcel.readBoolean();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.dts = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.dataLength = parcel.readLong();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.offset = parcel.readLong();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.avMemory = (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR);
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.isSecureMemory = parcel.readBoolean();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.avDataId = parcel.readLong();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.mpuSequenceNumber = parcel.readInt();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.isPesPrivateData = parcel.readBoolean();
                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                this.extraMetaData = (DemuxFilterMediaEventExtraMetaData) parcel.readTypedObject(DemuxFilterMediaEventExtraMetaData.CREATOR);
                                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                                    this.scIndexMask = (DemuxFilterScIndexMask) parcel.readTypedObject(DemuxFilterScIndexMask.CREATOR);
                                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                                        this.numDataPieces = parcel.readInt();
                                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                                            this.indexInDataGroup = parcel.readInt();
                                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                                this.dataGroupId = parcel.readInt();
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
        return describeContents(this.scIndexMask) | describeContents(this.avMemory) | describeContents(this.extraMetaData);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
