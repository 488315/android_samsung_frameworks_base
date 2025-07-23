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
        int dataPosition = parcel.dataPosition();
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
                this.components = (PlaneLayoutComponent[]) parcel.createTypedArray(PlaneLayoutComponent.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.offsetInBytes = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.sampleIncrementInBits = parcel.readLong();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.strideInBytes = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.widthInSamples = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.heightInSamples = parcel.readLong();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.totalSizeInBytes = parcel.readLong();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.horizontalSubsampling = parcel.readLong();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.verticalSubsampling = parcel.readLong();
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
        return describeContents(this.components);
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
}
