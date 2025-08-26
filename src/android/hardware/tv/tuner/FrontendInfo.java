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
        int iDataPosition = parcel.dataPosition();
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.minFrequency = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.maxFrequency = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.minSymbolRate = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.maxSymbolRate = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.acquireRange = parcel.readLong();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.exclusiveGroupId = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.statusCaps = parcel.createIntArray();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.frontendCaps = (FrontendCapabilities) parcel.readTypedObject(FrontendCapabilities.CREATOR);
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
        return describeContents(this.frontendCaps);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
