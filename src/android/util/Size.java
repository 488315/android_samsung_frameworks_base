package android.util;

import com.android.internal.util.Preconditions;

/* loaded from: classes4.dex */
public final class Size {
    private final int mHeight;
    private final int mWidth;

    public Size(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Size) {
            Size size = (Size) obj;
            if (this.mWidth == size.mWidth && this.mHeight == size.mHeight) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return this.mWidth + "x" + this.mHeight;
    }

    private static NumberFormatException invalidSize(String str) {
        throw new NumberFormatException("Invalid Size: \"" + str + "\"");
    }

    public static Size parseSize(String str) throws NumberFormatException {
        Preconditions.checkNotNull(str, "string must not be null");
        int indexOf = str.indexOf(42);
        if (indexOf < 0) {
            indexOf = str.indexOf(120);
        }
        if (indexOf < 0) {
            throw invalidSize(str);
        }
        try {
            return new Size(Integer.parseInt(str.substring(0, indexOf)), Integer.parseInt(str.substring(indexOf + 1)));
        } catch (NumberFormatException unused) {
            throw invalidSize(str);
        }
    }

    public int hashCode() {
        int i = this.mHeight;
        int i2 = this.mWidth;
        return ((i2 >>> 16) | (i2 << 16)) ^ i;
    }
}
