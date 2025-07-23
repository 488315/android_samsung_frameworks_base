package android.graphics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.FastMath;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class RectF implements Parcelable {
    public static final Parcelable.Creator<RectF> CREATOR = new Parcelable.Creator<RectF>() { // from class: android.graphics.RectF.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RectF createFromParcel(Parcel parcel) {
            RectF rectF = new RectF();
            rectF.readFromParcel(parcel);
            return rectF;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RectF[] newArray(int i) {
            return new RectF[i];
        }
    };
    public float bottom;
    public float left;
    public float right;
    public float top;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RectF() {
    }

    public RectF(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public RectF(RectF rectF) {
        if (rectF == null) {
            this.bottom = 0.0f;
            this.right = 0.0f;
            this.top = 0.0f;
            this.left = 0.0f;
            return;
        }
        this.left = rectF.left;
        this.top = rectF.top;
        this.right = rectF.right;
        this.bottom = rectF.bottom;
    }

    public RectF(Rect rect) {
        if (rect == null) {
            this.bottom = 0.0f;
            this.right = 0.0f;
            this.top = 0.0f;
            this.left = 0.0f;
            return;
        }
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            RectF rectF = (RectF) obj;
            if (this.left == rectF.left && this.top == rectF.top && this.right == rectF.right && this.bottom == rectF.bottom) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        float f = this.left;
        int floatToIntBits = (f != 0.0f ? Float.floatToIntBits(f) : 0) * 31;
        float f2 = this.top;
        int floatToIntBits2 = (floatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
        float f3 = this.right;
        int floatToIntBits3 = (floatToIntBits2 + (f3 != 0.0f ? Float.floatToIntBits(f3) : 0)) * 31;
        float f4 = this.bottom;
        return floatToIntBits3 + (f4 != 0.0f ? Float.floatToIntBits(f4) : 0);
    }

    public String toString() {
        return "RectF(" + this.left + ", " + this.top + ", " + this.right + ", " + this.bottom + NavigationBarInflaterView.KEY_CODE_END;
    }

    public String toShortString() {
        return toShortString(new StringBuilder(32));
    }

    public String toShortString(StringBuilder sb) {
        sb.setLength(0);
        sb.append('[');
        sb.append(this.left);
        sb.append(',');
        sb.append(this.top);
        sb.append("][");
        sb.append(this.right);
        sb.append(',');
        sb.append(this.bottom);
        sb.append(']');
        return sb.toString();
    }

    public void printShortString(PrintWriter printWriter) {
        printWriter.print('[');
        printWriter.print(this.left);
        printWriter.print(',');
        printWriter.print(this.top);
        printWriter.print("][");
        printWriter.print(this.right);
        printWriter.print(',');
        printWriter.print(this.bottom);
        printWriter.print(']');
    }

    public final boolean isEmpty() {
        return this.left >= this.right || this.top >= this.bottom;
    }

    public final float width() {
        return this.right - this.left;
    }

    public final float height() {
        return this.bottom - this.top;
    }

    public final float centerX() {
        return (this.left + this.right) * 0.5f;
    }

    public final float centerY() {
        return (this.top + this.bottom) * 0.5f;
    }

    public void setEmpty() {
        this.bottom = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.left = 0.0f;
    }

    public void set(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public void set(RectF rectF) {
        this.left = rectF.left;
        this.top = rectF.top;
        this.right = rectF.right;
        this.bottom = rectF.bottom;
    }

    public void set(Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public void offset(float f, float f2) {
        this.left += f;
        this.top += f2;
        this.right += f;
        this.bottom += f2;
    }

    public void offsetTo(float f, float f2) {
        this.right += f - this.left;
        this.bottom += f2 - this.top;
        this.left = f;
        this.top = f2;
    }

    public void inset(float f, float f2) {
        this.left += f;
        this.top += f2;
        this.right -= f;
        this.bottom -= f2;
    }

    public boolean contains(float f, float f2) {
        float f3 = this.left;
        float f4 = this.right;
        if (f3 >= f4) {
            return false;
        }
        float f5 = this.top;
        float f6 = this.bottom;
        return f5 < f6 && f >= f3 && f < f4 && f2 >= f5 && f2 < f6;
    }

    public boolean contains(float f, float f2, float f3, float f4) {
        float f5 = this.left;
        float f6 = this.right;
        if (f5 >= f6) {
            return false;
        }
        float f7 = this.top;
        float f8 = this.bottom;
        return f7 < f8 && f5 <= f && f7 <= f2 && f6 >= f3 && f8 >= f4;
    }

    public boolean contains(RectF rectF) {
        float f = this.left;
        float f2 = this.right;
        if (f >= f2) {
            return false;
        }
        float f3 = this.top;
        float f4 = this.bottom;
        return f3 < f4 && f <= rectF.left && f3 <= rectF.top && f2 >= rectF.right && f4 >= rectF.bottom;
    }

    public boolean intersect(float f, float f2, float f3, float f4) {
        float f5 = this.left;
        if (f5 >= f3) {
            return false;
        }
        float f6 = this.right;
        if (f >= f6) {
            return false;
        }
        float f7 = this.top;
        if (f7 >= f4) {
            return false;
        }
        float f8 = this.bottom;
        if (f2 >= f8) {
            return false;
        }
        if (f5 < f) {
            this.left = f;
        }
        if (f7 < f2) {
            this.top = f2;
        }
        if (f6 > f3) {
            this.right = f3;
        }
        if (f8 <= f4) {
            return true;
        }
        this.bottom = f4;
        return true;
    }

    public boolean intersect(RectF rectF) {
        return intersect(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    public boolean setIntersect(RectF rectF, RectF rectF2) {
        float f = rectF.left;
        if (f >= rectF2.right) {
            return false;
        }
        float f2 = rectF2.left;
        if (f2 >= rectF.right || rectF.top >= rectF2.bottom || rectF2.top >= rectF.bottom) {
            return false;
        }
        this.left = Math.max(f, f2);
        this.top = Math.max(rectF.top, rectF2.top);
        this.right = Math.min(rectF.right, rectF2.right);
        this.bottom = Math.min(rectF.bottom, rectF2.bottom);
        return true;
    }

    public boolean intersects(float f, float f2, float f3, float f4) {
        return this.left < f3 && f < this.right && this.top < f4 && f2 < this.bottom;
    }

    public static boolean intersects(RectF rectF, RectF rectF2) {
        return rectF.left < rectF2.right && rectF2.left < rectF.right && rectF.top < rectF2.bottom && rectF2.top < rectF.bottom;
    }

    public void round(Rect rect) {
        rect.set(FastMath.round(this.left), FastMath.round(this.top), FastMath.round(this.right), FastMath.round(this.bottom));
    }

    public void roundOut(Rect rect) {
        rect.set((int) Math.floor(this.left), (int) Math.floor(this.top), (int) Math.ceil(this.right), (int) Math.ceil(this.bottom));
    }

    public void union(float f, float f2, float f3, float f4) {
        if (f >= f3 || f2 >= f4) {
            return;
        }
        float f5 = this.left;
        float f6 = this.right;
        if (f5 < f6) {
            float f7 = this.top;
            float f8 = this.bottom;
            if (f7 < f8) {
                if (f5 > f) {
                    this.left = f;
                }
                if (f7 > f2) {
                    this.top = f2;
                }
                if (f6 < f3) {
                    this.right = f3;
                }
                if (f8 < f4) {
                    this.bottom = f4;
                    return;
                }
                return;
            }
        }
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    public void union(RectF rectF) {
        union(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    public void union(float f, float f2) {
        if (f < this.left) {
            this.left = f;
        } else if (f > this.right) {
            this.right = f;
        }
        if (f2 < this.top) {
            this.top = f2;
        } else if (f2 > this.bottom) {
            this.bottom = f2;
        }
    }

    public void sort() {
        float f = this.left;
        float f2 = this.right;
        if (f > f2) {
            this.left = f2;
            this.right = f;
        }
        float f3 = this.top;
        float f4 = this.bottom;
        if (f3 > f4) {
            this.top = f4;
            this.bottom = f3;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.left);
        parcel.writeFloat(this.top);
        parcel.writeFloat(this.right);
        parcel.writeFloat(this.bottom);
    }

    public void readFromParcel(Parcel parcel) {
        this.left = parcel.readFloat();
        this.top = parcel.readFloat();
        this.right = parcel.readFloat();
        this.bottom = parcel.readFloat();
    }

    public void scale(float f) {
        if (f != 1.0f) {
            this.left *= f;
            this.top *= f;
            this.right *= f;
            this.bottom *= f;
        }
    }
}
