package android.graphics;

import android.graphics.ColorSpace;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class ParcelableColorSpace implements Parcelable {
    public static final Parcelable.Creator<ParcelableColorSpace> CREATOR = new Parcelable.Creator<ParcelableColorSpace>() { // from class: android.graphics.ParcelableColorSpace.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableColorSpace createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == -1) {
                return new ParcelableColorSpace(new ColorSpace.Rgb(parcel.readString(), parcel.createFloatArray(), parcel.createFloatArray(), new ColorSpace.Rgb.TransferParameters(parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble(), parcel.readDouble())));
            }
            return new ParcelableColorSpace(ColorSpace.get(i));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableColorSpace[] newArray(int i) {
            return new ParcelableColorSpace[i];
        }
    };
    private final ColorSpace mColorSpace;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static boolean isParcelable(ColorSpace colorSpace) {
        if (colorSpace.getId() == -1) {
            return (colorSpace instanceof ColorSpace.Rgb) && ((ColorSpace.Rgb) colorSpace).getTransferParameters() != null;
        }
        return true;
    }

    public ParcelableColorSpace(ColorSpace colorSpace) {
        this.mColorSpace = colorSpace;
        if (colorSpace.getId() == -1) {
            if (!(colorSpace instanceof ColorSpace.Rgb)) {
                throw new IllegalArgumentException("Unable to parcel unknown ColorSpaces that are not ColorSpace.Rgb");
            }
            if (((ColorSpace.Rgb) colorSpace).getTransferParameters() == null) {
                throw new IllegalArgumentException("ColorSpace must use an ICC parametric transfer function to be parcelable");
            }
        }
    }

    public ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int id = this.mColorSpace.getId();
        parcel.writeInt(id);
        if (id == -1) {
            ColorSpace.Rgb rgb = (ColorSpace.Rgb) this.mColorSpace;
            parcel.writeString(rgb.getName());
            parcel.writeFloatArray(rgb.getPrimaries());
            parcel.writeFloatArray(rgb.getWhitePoint());
            ColorSpace.Rgb.TransferParameters transferParameters = rgb.getTransferParameters();
            parcel.writeDouble(transferParameters.a);
            parcel.writeDouble(transferParameters.b);
            parcel.writeDouble(transferParameters.c);
            parcel.writeDouble(transferParameters.d);
            parcel.writeDouble(transferParameters.e);
            parcel.writeDouble(transferParameters.f);
            parcel.writeDouble(transferParameters.g);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mColorSpace.equals(((ParcelableColorSpace) obj).mColorSpace);
    }

    public int hashCode() {
        return this.mColorSpace.hashCode();
    }
}
