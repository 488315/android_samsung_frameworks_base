package android.graphics;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.WireTypeMismatchException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class Rect implements Parcelable {
    public static final Parcelable.Creator<Rect> CREATOR = new Parcelable.Creator<Rect>() { // from class: android.graphics.Rect.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Rect createFromParcel(Parcel parcel) {
            Rect rect = new Rect();
            rect.readFromParcel(parcel);
            return rect;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Rect[] newArray(int i) {
            return new Rect[i];
        }
    };
    public int bottom;
    public int left;
    public int right;
    public int top;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static final class UnflattenHelper {
        private static final Pattern FLATTENED_PATTERN = Pattern.compile("(-?\\d+) (-?\\d+) (-?\\d+) (-?\\d+)");

        private UnflattenHelper() {
        }

        static Matcher getMatcher(String str) {
            return FLATTENED_PATTERN.matcher(str);
        }
    }

    public Rect() {
    }

    public Rect(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
    }

    public Rect(Rect rect) {
        if (rect == null) {
            this.bottom = 0;
            this.right = 0;
            this.top = 0;
            this.left = 0;
            return;
        }
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public Rect(Insets insets) {
        if (insets == null) {
            this.bottom = 0;
            this.right = 0;
            this.top = 0;
            this.left = 0;
            return;
        }
        this.left = insets.left;
        this.top = insets.top;
        this.right = insets.right;
        this.bottom = insets.bottom;
    }

    public static Rect copyOrNull(Rect rect) {
        if (rect == null) {
            return null;
        }
        return new Rect(rect);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Rect rect = (Rect) obj;
            if (this.left == rect.left && this.top == rect.top && this.right == rect.right && this.bottom == rect.bottom) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((((this.left * 31) + this.top) * 31) + this.right) * 31) + this.bottom;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("Rect(");
        sb.append(this.left);
        sb.append(", ");
        sb.append(this.top);
        sb.append(" - ");
        sb.append(this.right);
        sb.append(", ");
        sb.append(this.bottom);
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
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

    public String flattenToString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.left);
        sb.append(' ');
        sb.append(this.top);
        sb.append(' ');
        sb.append(this.right);
        sb.append(' ');
        sb.append(this.bottom);
        return sb.toString();
    }

    public static Rect unflattenFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Matcher matcher = UnflattenHelper.getMatcher(str);
        if (matcher.matches()) {
            return new Rect(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
        }
        return null;
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

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.left);
        protoOutputStream.write(1120986464258L, this.top);
        protoOutputStream.write(1120986464259L, this.right);
        protoOutputStream.write(1120986464260L, this.bottom);
        protoOutputStream.end(start);
    }

    public void readFromProto(ProtoInputStream protoInputStream, long j) throws IOException, WireTypeMismatchException {
        long start = protoInputStream.start(j);
        while (protoInputStream.nextField() != -1) {
            try {
                int fieldNumber = protoInputStream.getFieldNumber();
                if (fieldNumber == 1) {
                    this.left = protoInputStream.readInt(1120986464257L);
                } else if (fieldNumber == 2) {
                    this.top = protoInputStream.readInt(1120986464258L);
                } else if (fieldNumber == 3) {
                    this.right = protoInputStream.readInt(1120986464259L);
                } else if (fieldNumber == 4) {
                    this.bottom = protoInputStream.readInt(1120986464260L);
                }
            } finally {
                protoInputStream.end(start);
            }
        }
    }

    public final boolean isEmpty() {
        return this.left >= this.right || this.top >= this.bottom;
    }

    public boolean isValid() {
        return this.left <= this.right && this.top <= this.bottom;
    }

    public final int width() {
        return this.right - this.left;
    }

    public final int height() {
        return this.bottom - this.top;
    }

    public final int centerX() {
        return (this.left + this.right) >> 1;
    }

    public final int centerY() {
        return (this.top + this.bottom) >> 1;
    }

    public final float exactCenterX() {
        return (this.left + this.right) * 0.5f;
    }

    public final float exactCenterY() {
        return (this.top + this.bottom) * 0.5f;
    }

    public void setEmpty() {
        this.bottom = 0;
        this.top = 0;
        this.right = 0;
        this.left = 0;
    }

    public void set(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
    }

    public void set(Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public void offset(int i, int i2) {
        this.left += i;
        this.top += i2;
        this.right += i;
        this.bottom += i2;
    }

    public void offsetTo(int i, int i2) {
        this.right += i - this.left;
        this.bottom += i2 - this.top;
        this.left = i;
        this.top = i2;
    }

    public void inset(int i, int i2) {
        this.left += i;
        this.top += i2;
        this.right -= i;
        this.bottom -= i2;
    }

    public void inset(Rect rect) {
        this.left += rect.left;
        this.top += rect.top;
        this.right -= rect.right;
        this.bottom -= rect.bottom;
    }

    public void inset(Insets insets) {
        this.left += insets.left;
        this.top += insets.top;
        this.right -= insets.right;
        this.bottom -= insets.bottom;
    }

    public void inset(int i, int i2, int i3, int i4) {
        this.left += i;
        this.top += i2;
        this.right -= i3;
        this.bottom -= i4;
    }

    public boolean contains(int i, int i2) {
        int i3;
        int i4;
        int i5 = this.left;
        int i6 = this.right;
        return i5 < i6 && (i3 = this.top) < (i4 = this.bottom) && i >= i5 && i < i6 && i2 >= i3 && i2 < i4;
    }

    public boolean contains(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = this.left;
        int i8 = this.right;
        return i7 < i8 && (i5 = this.top) < (i6 = this.bottom) && i7 <= i && i5 <= i2 && i8 >= i3 && i6 >= i4;
    }

    public boolean contains(Rect rect) {
        int i;
        int i2;
        int i3 = this.left;
        int i4 = this.right;
        return i3 < i4 && (i = this.top) < (i2 = this.bottom) && i3 <= rect.left && i <= rect.top && i4 >= rect.right && i2 >= rect.bottom;
    }

    public boolean intersect(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8 = this.left;
        if (i8 >= i3 || i >= (i5 = this.right) || (i6 = this.top) >= i4 || i2 >= (i7 = this.bottom)) {
            return false;
        }
        if (i8 < i) {
            this.left = i;
        }
        if (i6 < i2) {
            this.top = i2;
        }
        if (i5 > i3) {
            this.right = i3;
        }
        if (i7 <= i4) {
            return true;
        }
        this.bottom = i4;
        return true;
    }

    public boolean intersect(Rect rect) {
        return intersect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void intersectUnchecked(Rect rect) {
        this.left = Math.max(this.left, rect.left);
        this.top = Math.max(this.top, rect.top);
        this.right = Math.min(this.right, rect.right);
        this.bottom = Math.min(this.bottom, rect.bottom);
    }

    public boolean setIntersect(Rect rect, Rect rect2) {
        int i;
        int i2 = rect.left;
        if (i2 >= rect2.right || (i = rect2.left) >= rect.right || rect.top >= rect2.bottom || rect2.top >= rect.bottom) {
            return false;
        }
        this.left = Math.max(i2, i);
        this.top = Math.max(rect.top, rect2.top);
        this.right = Math.min(rect.right, rect2.right);
        this.bottom = Math.min(rect.bottom, rect2.bottom);
        return true;
    }

    public boolean intersects(int i, int i2, int i3, int i4) {
        return this.left < i3 && i < this.right && this.top < i4 && i2 < this.bottom;
    }

    public static boolean intersects(Rect rect, Rect rect2) {
        return rect.left < rect2.right && rect2.left < rect.right && rect.top < rect2.bottom && rect2.top < rect.bottom;
    }

    public void union(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (i >= i3 || i2 >= i4) {
            return;
        }
        int i7 = this.left;
        int i8 = this.right;
        if (i7 >= i8 || (i5 = this.top) >= (i6 = this.bottom)) {
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
            return;
        }
        if (i7 > i) {
            this.left = i;
        }
        if (i5 > i2) {
            this.top = i2;
        }
        if (i8 < i3) {
            this.right = i3;
        }
        if (i6 < i4) {
            this.bottom = i4;
        }
    }

    public void union(Rect rect) {
        union(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void union(int i, int i2) {
        if (i < this.left) {
            this.left = i;
        } else if (i > this.right) {
            this.right = i;
        }
        if (i2 < this.top) {
            this.top = i2;
        } else if (i2 > this.bottom) {
            this.bottom = i2;
        }
    }

    public void sort() {
        int i = this.left;
        int i2 = this.right;
        if (i > i2) {
            this.left = i2;
            this.right = i;
        }
        int i3 = this.top;
        int i4 = this.bottom;
        if (i3 > i4) {
            this.top = i4;
            this.bottom = i3;
        }
    }

    public void splitVertically(Rect... rectArr) {
        int length = rectArr.length;
        int width = width() / length;
        for (int i = 0; i < length; i++) {
            Rect rect = rectArr[i];
            int i2 = this.left + (width * i);
            rect.left = i2;
            rect.top = this.top;
            rect.right = i2 + width;
            rect.bottom = this.bottom;
        }
    }

    public void splitHorizontally(Rect... rectArr) {
        int length = rectArr.length;
        int height = height() / length;
        for (int i = 0; i < length; i++) {
            Rect rect = rectArr[i];
            rect.left = this.left;
            int i2 = this.top + (height * i);
            rect.top = i2;
            rect.right = this.right;
            rect.bottom = i2 + height;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.left);
        parcel.writeInt(this.top);
        parcel.writeInt(this.right);
        parcel.writeInt(this.bottom);
    }

    public void readFromParcel(Parcel parcel) {
        this.left = parcel.readInt();
        this.top = parcel.readInt();
        this.right = parcel.readInt();
        this.bottom = parcel.readInt();
    }

    public void scale(float f) {
        if (f != 1.0f) {
            this.left = (int) ((this.left * f) + 0.5f);
            this.top = (int) ((this.top * f) + 0.5f);
            this.right = (int) ((this.right * f) + 0.5f);
            this.bottom = (int) ((this.bottom * f) + 0.5f);
        }
    }
}
