package android.hardware.graphics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class PlaneLayout implements Parcelable {
    public static final Parcelable.Creator<PlaneLayout> CREATOR = new Parcelable.Creator<PlaneLayout>() { // from class: android.hardware.graphics.common.PlaneLayout.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaneLayout createFromParcel(Parcel parcel) {
            PlaneLayout planeLayout = new PlaneLayout();
            planeLayout.readFromParcel(parcel);
            return planeLayout;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaneLayout[] newArray(int i) {
            return new PlaneLayout[i];
        }
    };
    public PlaneLayoutComponent[] components;
    public long offsetInBytes = 0;
    public long sampleIncrementInBits = 0;
    public long strideInBytes = 0;
    public long widthInSamples = 0;
    public long heightInSamples = 0;
    public long totalSizeInBytes = 0;
    public long horizontalSubsampling = 0;
    public long verticalSubsampling = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.components, i);
        parcel.writeLong(this.offsetInBytes);
        parcel.writeLong(this.sampleIncrementInBits);
        parcel.writeLong(this.strideInBytes);
        parcel.writeLong(this.widthInSamples);
        parcel.writeLong(this.heightInSamples);
        parcel.writeLong(this.totalSizeInBytes);
        parcel.writeLong(this.horizontalSubsampling);
        parcel.writeLong(this.verticalSubsampling);
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
                this.components = (PlaneLayoutComponent[]) parcel.createTypedArray(PlaneLayoutComponent.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.offsetInBytes = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.sampleIncrementInBits = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.strideInBytes = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.widthInSamples = parcel.readLong();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.heightInSamples = parcel.readLong();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.totalSizeInBytes = parcel.readLong();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.horizontalSubsampling = parcel.readLong();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.verticalSubsampling = parcel.readLong();
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
        return describeContents(this.components);
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
