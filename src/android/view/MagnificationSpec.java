package android.view;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class MagnificationSpec implements Parcelable {
    public static final Parcelable.Creator<MagnificationSpec> CREATOR = new Parcelable.Creator<MagnificationSpec>() { // from class: android.view.MagnificationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MagnificationSpec[] newArray(int i) {
            return new MagnificationSpec[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MagnificationSpec createFromParcel(Parcel parcel) {
            MagnificationSpec magnificationSpec = new MagnificationSpec();
            magnificationSpec.initFromParcel(parcel);
            return magnificationSpec;
        }
    };
    public float offsetX;
    public float offsetY;
    public float scale = 1.0f;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void initialize(float f, float f2, float f3) {
        this.scale = f;
        this.offsetX = f2;
        this.offsetY = f3;
    }

    public boolean isNop() {
        return this.scale == 1.0f && this.offsetX == 0.0f && this.offsetY == 0.0f;
    }

    public void clear() {
        this.scale = 1.0f;
        this.offsetX = 0.0f;
        this.offsetY = 0.0f;
    }

    public void setTo(MagnificationSpec magnificationSpec) {
        this.scale = magnificationSpec.scale;
        this.offsetX = magnificationSpec.offsetX;
        this.offsetY = magnificationSpec.offsetY;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.scale);
        parcel.writeFloat(this.offsetX);
        parcel.writeFloat(this.offsetY);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            MagnificationSpec magnificationSpec = (MagnificationSpec) obj;
            if (this.scale == magnificationSpec.scale && this.offsetX == magnificationSpec.offsetX && this.offsetY == magnificationSpec.offsetY) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        float f = this.scale;
        int floatToIntBits = (f != 0.0f ? Float.floatToIntBits(f) : 0) * 31;
        float f2 = this.offsetX;
        int floatToIntBits2 = (floatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
        float f3 = this.offsetY;
        return floatToIntBits2 + (f3 != 0.0f ? Float.floatToIntBits(f3) : 0);
    }

    public String toString() {
        return "<scale:" + Float.toString(this.scale) + ",offsetX:" + Float.toString(this.offsetX) + ",offsetY:" + Float.toString(this.offsetY) + ">";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(Parcel parcel) {
        this.scale = parcel.readFloat();
        this.offsetX = parcel.readFloat();
        this.offsetY = parcel.readFloat();
    }
}
