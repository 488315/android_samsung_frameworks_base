package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendInfo implements Parcelable {
    public static final Parcelable.Creator<FrontendInfo> CREATOR = new Parcelable.Creator<FrontendInfo>() { // from class: android.hardware.tv.tuner.FrontendInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendInfo createFromParcel(Parcel parcel) {
            FrontendInfo frontendInfo = new FrontendInfo();
            frontendInfo.readFromParcel(parcel);
            return frontendInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendInfo[] newArray(int i) {
            return new FrontendInfo[i];
        }
    };
    public FrontendCapabilities frontendCaps;
    public int[] statusCaps;
    public int type = 0;
    public long minFrequency = 0;
    public long maxFrequency = 0;
    public int minSymbolRate = 0;
    public int maxSymbolRate = 0;
    public long acquireRange = 0;
    public int exclusiveGroupId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeLong(this.minFrequency);
        parcel.writeLong(this.maxFrequency);
        parcel.writeInt(this.minSymbolRate);
        parcel.writeInt(this.maxSymbolRate);
        parcel.writeLong(this.acquireRange);
        parcel.writeInt(this.exclusiveGroupId);
        parcel.writeIntArray(this.statusCaps);
        parcel.writeTypedObject(this.frontendCaps, i);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.minFrequency = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.maxFrequency = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.minSymbolRate = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.maxSymbolRate = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.acquireRange = parcel.readLong();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.exclusiveGroupId = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.statusCaps = parcel.createIntArray();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.frontendCaps = (FrontendCapabilities) parcel.readTypedObject(FrontendCapabilities.CREATOR);
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
        return describeContents(this.frontendCaps);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
