package android.hardware.camera2.extension;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Surface;

/* loaded from: classes2.dex */
public class OutputSurface implements Parcelable {
    public static final Parcelable.Creator<OutputSurface> CREATOR = new Parcelable.Creator<OutputSurface>() { // from class: android.hardware.camera2.extension.OutputSurface.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OutputSurface createFromParcel(Parcel parcel) {
            OutputSurface outputSurface = new OutputSurface();
            outputSurface.readFromParcel(parcel);
            return outputSurface;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OutputSurface[] newArray(int i) {
            return new OutputSurface[i];
        }
    };
    public Size size;
    public Surface surface;
    public int imageFormat = 0;
    public long dynamicRangeProfile = 0;
    public int colorSpace = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.surface, i);
        parcel.writeTypedObject(this.size, i);
        parcel.writeInt(this.imageFormat);
        parcel.writeLong(this.dynamicRangeProfile);
        parcel.writeInt(this.colorSpace);
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
                this.surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.size = (Size) parcel.readTypedObject(Size.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.imageFormat = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.dynamicRangeProfile = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.colorSpace = parcel.readInt();
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
        return describeContents(this.size) | describeContents(this.surface);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
