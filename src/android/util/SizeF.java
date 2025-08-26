package android.util;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes4.dex */
public final class SizeF implements Parcelable {
    public static final Parcelable.Creator<SizeF> CREATOR = new Parcelable.Creator<SizeF>() { // from class: android.util.SizeF.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SizeF createFromParcel(Parcel parcel) {
            return new SizeF(parcel.readFloat(), parcel.readFloat());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SizeF[] newArray(int i) {
            return new SizeF[i];
        }
    };
    private final float mHeight;
    private final float mWidth;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SizeF(float f, float f2) {
        this.mWidth = Preconditions.checkArgumentFinite(f, "width");
        this.mHeight = Preconditions.checkArgumentFinite(f2, "height");
    }

    public float getWidth() {
        return this.mWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof SizeF) {
            SizeF sizeF = (SizeF) obj;
            if (this.mWidth == sizeF.mWidth && this.mHeight == sizeF.mHeight) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return this.mWidth + "x" + this.mHeight;
    }

    private static NumberFormatException invalidSizeF(String str) {
        throw new NumberFormatException("Invalid SizeF: \"" + str + "\"");
    }

    public static SizeF parseSizeF(String str) throws NumberFormatException {
        Preconditions.checkNotNull(str, "string must not be null");
        int iIndexOf = str.indexOf(42);
        if (iIndexOf < 0) {
            iIndexOf = str.indexOf(120);
        }
        if (iIndexOf < 0) {
            throw invalidSizeF(str);
        }
        try {
            return new SizeF(Float.parseFloat(str.substring(0, iIndexOf)), Float.parseFloat(str.substring(iIndexOf + 1)));
        } catch (NumberFormatException unused) {
            throw invalidSizeF(str);
        } catch (IllegalArgumentException unused2) {
            throw invalidSizeF(str);
        }
    }

    public int hashCode() {
        return Float.floatToIntBits(this.mHeight) ^ Float.floatToIntBits(this.mWidth);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mWidth);
        parcel.writeFloat(this.mHeight);
    }
}
