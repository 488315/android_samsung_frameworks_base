package android.hardware.camera2.params;

import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.util.Size;
import com.android.internal.util.Preconditions;

/* loaded from: classes2.dex */
public final class MeteringRectangle {
    public static final int METERING_WEIGHT_DONT_CARE = 0;
    public static final int METERING_WEIGHT_MAX = 1000;
    public static final int METERING_WEIGHT_MIN = 0;
    private final int mHeight;
    private final int mWeight;
    private final int mWidth;
    private final int mX;
    private final int mY;

    public MeteringRectangle(int i, int i2, int i3, int i4, int i5) {
        this.mX = Preconditions.checkArgumentNonnegative(i, "x must be nonnegative");
        this.mY = Preconditions.checkArgumentNonnegative(i2, "y must be nonnegative");
        this.mWidth = Preconditions.checkArgumentNonnegative(i3, "width must be nonnegative");
        this.mHeight = Preconditions.checkArgumentNonnegative(i4, "height must be nonnegative");
        this.mWeight = Preconditions.checkArgumentInRange(i5, 0, 1000, "meteringWeight");
    }

    public MeteringRectangle(Point point, Size size, int i) {
        Preconditions.checkNotNull(point, "xy must not be null");
        Preconditions.checkNotNull(size, "dimensions must not be null");
        this.mX = Preconditions.checkArgumentNonnegative(point.x, "x must be nonnegative");
        this.mY = Preconditions.checkArgumentNonnegative(point.y, "y must be nonnegative");
        this.mWidth = Preconditions.checkArgumentNonnegative(size.getWidth(), "width must be nonnegative");
        this.mHeight = Preconditions.checkArgumentNonnegative(size.getHeight(), "height must be nonnegative");
        this.mWeight = Preconditions.checkArgumentNonnegative(i, "meteringWeight must be nonnegative");
    }

    public MeteringRectangle(Rect rect, int i) {
        Preconditions.checkNotNull(rect, "rect must not be null");
        this.mX = Preconditions.checkArgumentNonnegative(rect.left, "rect.left must be nonnegative");
        this.mY = Preconditions.checkArgumentNonnegative(rect.top, "rect.top must be nonnegative");
        this.mWidth = Preconditions.checkArgumentNonnegative(rect.width(), "rect.width must be nonnegative");
        this.mHeight = Preconditions.checkArgumentNonnegative(rect.height(), "rect.height must be nonnegative");
        this.mWeight = Preconditions.checkArgumentNonnegative(i, "meteringWeight must be nonnegative");
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getMeteringWeight() {
        return this.mWeight;
    }

    public Point getUpperLeftPoint() {
        return new Point(this.mX, this.mY);
    }

    public Size getSize() {
        return new Size(this.mWidth, this.mHeight);
    }

    public Rect getRect() {
        int i = this.mX;
        int i2 = this.mY;
        return new Rect(i, i2, this.mWidth + i, this.mHeight + i2);
    }

    public boolean equals(Object obj) {
        return (obj instanceof MeteringRectangle) && equals((MeteringRectangle) obj);
    }

    public boolean equals(MeteringRectangle meteringRectangle) {
        return meteringRectangle != null && this.mX == meteringRectangle.mX && this.mY == meteringRectangle.mY && this.mWidth == meteringRectangle.mWidth && this.mHeight == meteringRectangle.mHeight && this.mWeight == meteringRectangle.mWeight;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(this.mX, this.mY, this.mWidth, this.mHeight, this.mWeight);
    }

    public String toString() {
        return String.format("(x:%d, y:%d, w:%d, h:%d, wt:%d)", Integer.valueOf(this.mX), Integer.valueOf(this.mY), Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mWeight));
    }
}
