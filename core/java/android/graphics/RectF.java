package android.graphics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.FastMath;

import java.io.PrintWriter;

public class RectF implements Parcelable {
    public static final Parcelable.Creator<RectF> CREATOR =
            new Parcelable.Creator<RectF>() { // from class: android.graphics.RectF.1
                @Override // android.os.Parcelable.Creator
                public RectF createFromParcel(Parcel in) {
                    RectF r = new RectF();
                    r.readFromParcel(in);
                    return r;
                }

                @Override // android.os.Parcelable.Creator
                public RectF[] newArray(int size) {
                    return new RectF[size];
                }
            };
    public float bottom;
    public float left;
    public float right;
    public float top;

    public RectF() {}

    public RectF(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public RectF(RectF r) {
        if (r == null) {
            this.bottom = 0.0f;
            this.right = 0.0f;
            this.top = 0.0f;
            this.left = 0.0f;
            return;
        }
        this.left = r.left;
        this.top = r.top;
        this.right = r.right;
        this.bottom = r.bottom;
    }

    public RectF(Rect r) {
        if (r == null) {
            this.bottom = 0.0f;
            this.right = 0.0f;
            this.top = 0.0f;
            this.left = 0.0f;
            return;
        }
        this.left = r.left;
        this.top = r.top;
        this.right = r.right;
        this.bottom = r.bottom;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RectF r = (RectF) o;
        if (this.left == r.left
                && this.top == r.top
                && this.right == r.right
                && this.bottom == r.bottom) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.left != 0.0f ? Float.floatToIntBits(this.left) : 0;
        return (((((result * 31) + (this.top != 0.0f ? Float.floatToIntBits(this.top) : 0)) * 31)
                                + (this.right != 0.0f ? Float.floatToIntBits(this.right) : 0))
                        * 31)
                + (this.bottom != 0.0f ? Float.floatToIntBits(this.bottom) : 0);
    }

    public String toString() {
        return "RectF("
                + this.left
                + ", "
                + this.top
                + ", "
                + this.right
                + ", "
                + this.bottom
                + NavigationBarInflaterView.KEY_CODE_END;
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

    public void printShortString(PrintWriter pw) {
        pw.print('[');
        pw.print(this.left);
        pw.print(',');
        pw.print(this.top);
        pw.print("][");
        pw.print(this.right);
        pw.print(',');
        pw.print(this.bottom);
        pw.print(']');
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

    public void set(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void set(RectF src) {
        this.left = src.left;
        this.top = src.top;
        this.right = src.right;
        this.bottom = src.bottom;
    }

    public void set(Rect src) {
        this.left = src.left;
        this.top = src.top;
        this.right = src.right;
        this.bottom = src.bottom;
    }

    public void offset(float dx, float dy) {
        this.left += dx;
        this.top += dy;
        this.right += dx;
        this.bottom += dy;
    }

    public void offsetTo(float newLeft, float newTop) {
        this.right += newLeft - this.left;
        this.bottom += newTop - this.top;
        this.left = newLeft;
        this.top = newTop;
    }

    public void inset(float dx, float dy) {
        this.left += dx;
        this.top += dy;
        this.right -= dx;
        this.bottom -= dy;
    }

    public boolean contains(float x, float y) {
        return this.left < this.right
                && this.top < this.bottom
                && x >= this.left
                && x < this.right
                && y >= this.top
                && y < this.bottom;
    }

    public boolean contains(float left, float top, float right, float bottom) {
        return this.left < this.right
                && this.top < this.bottom
                && this.left <= left
                && this.top <= top
                && this.right >= right
                && this.bottom >= bottom;
    }

    public boolean contains(RectF r) {
        return this.left < this.right
                && this.top < this.bottom
                && this.left <= r.left
                && this.top <= r.top
                && this.right >= r.right
                && this.bottom >= r.bottom;
    }

    public boolean intersect(float left, float top, float right, float bottom) {
        if (this.left >= right || left >= this.right || this.top >= bottom || top >= this.bottom) {
            return false;
        }
        if (this.left < left) {
            this.left = left;
        }
        if (this.top < top) {
            this.top = top;
        }
        if (this.right > right) {
            this.right = right;
        }
        if (this.bottom > bottom) {
            this.bottom = bottom;
            return true;
        }
        return true;
    }

    public boolean intersect(RectF r) {
        return intersect(r.left, r.top, r.right, r.bottom);
    }

    public boolean setIntersect(RectF a, RectF b) {
        if (a.left < b.right && b.left < a.right && a.top < b.bottom && b.top < a.bottom) {
            this.left = Math.max(a.left, b.left);
            this.top = Math.max(a.top, b.top);
            this.right = Math.min(a.right, b.right);
            this.bottom = Math.min(a.bottom, b.bottom);
            return true;
        }
        return false;
    }

    public boolean intersects(float left, float top, float right, float bottom) {
        return this.left < right && left < this.right && this.top < bottom && top < this.bottom;
    }

    public static boolean intersects(RectF a, RectF b) {
        return a.left < b.right && b.left < a.right && a.top < b.bottom && b.top < a.bottom;
    }

    public void round(Rect dst) {
        dst.set(
                FastMath.round(this.left),
                FastMath.round(this.top),
                FastMath.round(this.right),
                FastMath.round(this.bottom));
    }

    public void roundOut(Rect dst) {
        dst.set(
                (int) Math.floor(this.left),
                (int) Math.floor(this.top),
                (int) Math.ceil(this.right),
                (int) Math.ceil(this.bottom));
    }

    public void union(float left, float top, float right, float bottom) {
        if (left >= right || top >= bottom) {
            return;
        }
        if (this.left >= this.right || this.top >= this.bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            return;
        }
        if (this.left > left) {
            this.left = left;
        }
        if (this.top > top) {
            this.top = top;
        }
        if (this.right < right) {
            this.right = right;
        }
        if (this.bottom < bottom) {
            this.bottom = bottom;
        }
    }

    public void union(RectF r) {
        union(r.left, r.top, r.right, r.bottom);
    }

    public void union(float x, float y) {
        if (x < this.left) {
            this.left = x;
        } else if (x > this.right) {
            this.right = x;
        }
        if (y < this.top) {
            this.top = y;
        } else if (y > this.bottom) {
            this.bottom = y;
        }
    }

    public void sort() {
        if (this.left > this.right) {
            float temp = this.left;
            this.left = this.right;
            this.right = temp;
        }
        float temp2 = this.top;
        if (temp2 > this.bottom) {
            float temp3 = this.top;
            this.top = this.bottom;
            this.bottom = temp3;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(this.left);
        out.writeFloat(this.top);
        out.writeFloat(this.right);
        out.writeFloat(this.bottom);
    }

    public void readFromParcel(Parcel in) {
        this.left = in.readFloat();
        this.top = in.readFloat();
        this.right = in.readFloat();
        this.bottom = in.readFloat();
    }

    public void scale(float scale) {
        if (scale != 1.0f) {
            this.left *= scale;
            this.top *= scale;
            this.right *= scale;
            this.bottom *= scale;
        }
    }
}
