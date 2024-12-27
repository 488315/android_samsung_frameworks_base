package android.graphics;

import android.os.Parcel;
import android.os.Parcelable;

public final class ParcelableColorSpace implements Parcelable {
    public static final Parcelable.Creator<ParcelableColorSpace> CREATOR =
            new Parcelable.Creator<
                    ParcelableColorSpace>() { // from class: android.graphics.ParcelableColorSpace.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParcelableColorSpace createFromParcel(Parcel in) {
                    int id = in.readInt();
                    if (id == -1) {
                        String name = in.readString();
                        float[] primaries = in.createFloatArray();
                        float[] whitePoint = in.createFloatArray();
                        double a = in.readDouble();
                        double b = in.readDouble();
                        double c = in.readDouble();
                        double d = in.readDouble();
                        double e = in.readDouble();
                        double f = in.readDouble();
                        double g = in.readDouble();
                        ColorSpace.Rgb.TransferParameters function =
                                new ColorSpace.Rgb.TransferParameters(a, b, c, d, e, f, g);
                        return new ParcelableColorSpace(
                                new ColorSpace.Rgb(name, primaries, whitePoint, function));
                    }
                    return new ParcelableColorSpace(ColorSpace.get(id));
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ParcelableColorSpace[] newArray(int size) {
                    return new ParcelableColorSpace[size];
                }
            };
    private final ColorSpace mColorSpace;

    public static boolean isParcelable(ColorSpace colorSpace) {
        if (colorSpace.getId() == -1) {
            if (!(colorSpace instanceof ColorSpace.Rgb)) {
                return false;
            }
            ColorSpace.Rgb rgb = (ColorSpace.Rgb) colorSpace;
            return rgb.getTransferParameters() != null;
        }
        return true;
    }

    public ParcelableColorSpace(ColorSpace colorSpace) {
        this.mColorSpace = colorSpace;
        if (this.mColorSpace.getId() == -1) {
            if (!(this.mColorSpace instanceof ColorSpace.Rgb)) {
                throw new IllegalArgumentException(
                        "Unable to parcel unknown ColorSpaces that are not ColorSpace.Rgb");
            }
            ColorSpace.Rgb rgb = (ColorSpace.Rgb) this.mColorSpace;
            if (rgb.getTransferParameters() == null) {
                throw new IllegalArgumentException(
                        "ColorSpace must use an ICC parametric transfer function to be parcelable");
            }
        }
    }

    public ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        int id = this.mColorSpace.getId();
        dest.writeInt(id);
        if (id == -1) {
            ColorSpace.Rgb rgb = (ColorSpace.Rgb) this.mColorSpace;
            dest.writeString(rgb.getName());
            dest.writeFloatArray(rgb.getPrimaries());
            dest.writeFloatArray(rgb.getWhitePoint());
            ColorSpace.Rgb.TransferParameters transferParameters = rgb.getTransferParameters();
            dest.writeDouble(transferParameters.a);
            dest.writeDouble(transferParameters.b);
            dest.writeDouble(transferParameters.c);
            dest.writeDouble(transferParameters.d);
            dest.writeDouble(transferParameters.e);
            dest.writeDouble(transferParameters.f);
            dest.writeDouble(transferParameters.g);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParcelableColorSpace other = (ParcelableColorSpace) o;
        return this.mColorSpace.equals(other.mColorSpace);
    }

    public int hashCode() {
        return this.mColorSpace.hashCode();
    }
}
