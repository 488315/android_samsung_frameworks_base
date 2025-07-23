package android.view;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayUtils;
import android.util.PathParser;
import android.util.RotationUtils;
import com.android.internal.R;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class DisplayShape implements Parcelable {
    private final int mDisplayHeight;
    public final String mDisplayShapeSpec;
    private final int mDisplayWidth;
    private final int mOffsetX;
    private final int mOffsetY;
    private final float mPhysicalPixelDisplaySizeRatio;
    private final int mRotation;
    private final float mScale;
    public static final DisplayShape NONE = new DisplayShape("", 0, 0, 0.0f, 0);
    public static final Parcelable.Creator<DisplayShape> CREATOR = new Parcelable.Creator<DisplayShape>() { // from class: android.view.DisplayShape.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayShape createFromParcel(Parcel parcel) {
            return new DisplayShape(parcel.readString8(), parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readFloat());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayShape[] newArray(int i) {
            return new DisplayShape[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DisplayShape(String str, int i, int i2, float f, int i3) {
        this(str, i, i2, f, i3, 0, 0, 1.0f);
    }

    private DisplayShape(String str, int i, int i2, float f, int i3, int i4, int i5, float f2) {
        this.mDisplayShapeSpec = str;
        this.mDisplayWidth = i;
        this.mDisplayHeight = i2;
        this.mPhysicalPixelDisplaySizeRatio = f;
        this.mRotation = i3;
        this.mOffsetX = i4;
        this.mOffsetY = i5;
        this.mScale = f2;
    }

    public static DisplayShape fromResources(Resources resources, String str, int i, int i2, int i3, int i4) {
        boolean builtInDisplayIsRound = RoundedCorners.getBuiltInDisplayIsRound(resources, str);
        String specString = getSpecString(resources, str);
        if (specString == null || specString.isEmpty()) {
            return createDefaultDisplayShape(i3, i4, builtInDisplayIsRound);
        }
        return fromSpecString(specString, DisplayUtils.getPhysicalPixelDisplaySizeRatio(i, i2, i3, i4), i3, i4);
    }

    public static DisplayShape createDefaultDisplayShape(int i, int i2, boolean z) {
        return fromSpecString(createDefaultSpecString(i, i2, z), 1.0f, i, i2);
    }

    public static DisplayShape fromSpecString(String str, float f, int i, int i2) {
        return Cache.getDisplayShape(str, f, i, i2);
    }

    private static String createDefaultSpecString(int i, int i2, boolean z) {
        if (z) {
            float f = i / 2.0f;
            float f2 = i2 / 2.0f;
            return "M0," + f2 + " A" + f + "," + f2 + " 0 1,1 " + i + "," + f2 + " A" + f + "," + f2 + " 0 1,1 0," + f2 + " Z";
        }
        return "M0,0 L" + i + ",0 L" + i + "," + i2 + " L0," + i2 + " Z";
    }

    public static String getSpecString(Resources resources, String str) {
        String string;
        int displayUniqueIdConfigIndex = DisplayUtils.getDisplayUniqueIdConfigIndex(resources, str);
        TypedArray obtainTypedArray = resources.obtainTypedArray(R.array.config_displayShapeArray);
        if (displayUniqueIdConfigIndex >= 0 && displayUniqueIdConfigIndex < obtainTypedArray.length()) {
            string = obtainTypedArray.getString(displayUniqueIdConfigIndex);
        } else {
            string = resources.getString(R.string.config_mainDisplayShape);
        }
        obtainTypedArray.recycle();
        return string;
    }

    public DisplayShape setRotation(int i) {
        return new DisplayShape(this.mDisplayShapeSpec, this.mDisplayWidth, this.mDisplayHeight, this.mPhysicalPixelDisplaySizeRatio, i, this.mOffsetX, this.mOffsetY, this.mScale);
    }

    public DisplayShape setOffset(int i, int i2) {
        return new DisplayShape(this.mDisplayShapeSpec, this.mDisplayWidth, this.mDisplayHeight, this.mPhysicalPixelDisplaySizeRatio, this.mRotation, i, i2, this.mScale);
    }

    public DisplayShape setScale(float f) {
        return new DisplayShape(this.mDisplayShapeSpec, this.mDisplayWidth, this.mDisplayHeight, this.mPhysicalPixelDisplaySizeRatio, this.mRotation, this.mOffsetX, this.mOffsetY, f);
    }

    public int hashCode() {
        return Objects.hash(this.mDisplayShapeSpec, Integer.valueOf(this.mDisplayWidth), Integer.valueOf(this.mDisplayHeight), Float.valueOf(this.mPhysicalPixelDisplaySizeRatio), Integer.valueOf(this.mRotation), Integer.valueOf(this.mOffsetX), Integer.valueOf(this.mOffsetY), Float.valueOf(this.mScale));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof DisplayShape) {
            DisplayShape displayShape = (DisplayShape) obj;
            if (Objects.equals(this.mDisplayShapeSpec, displayShape.mDisplayShapeSpec) && this.mDisplayWidth == displayShape.mDisplayWidth && this.mDisplayHeight == displayShape.mDisplayHeight && this.mPhysicalPixelDisplaySizeRatio == displayShape.mPhysicalPixelDisplaySizeRatio && this.mRotation == displayShape.mRotation && this.mOffsetX == displayShape.mOffsetX && this.mOffsetY == displayShape.mOffsetY && this.mScale == displayShape.mScale) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "DisplayShape{ spec=" + this.mDisplayShapeSpec.hashCode() + " displayWidth=" + this.mDisplayWidth + " displayHeight=" + this.mDisplayHeight + " physicalPixelDisplaySizeRatio=" + this.mPhysicalPixelDisplaySizeRatio + " rotation=" + this.mRotation + " offsetX=" + this.mOffsetX + " offsetY=" + this.mOffsetY + " scale=" + this.mScale + "}";
    }

    public Path getPath() {
        return Cache.getPath(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mDisplayShapeSpec);
        parcel.writeInt(this.mDisplayWidth);
        parcel.writeInt(this.mDisplayHeight);
        parcel.writeFloat(this.mPhysicalPixelDisplaySizeRatio);
        parcel.writeInt(this.mRotation);
        parcel.writeInt(this.mOffsetX);
        parcel.writeInt(this.mOffsetY);
        parcel.writeFloat(this.mScale);
    }

    private static final class Cache {
        private static final Object CACHE_LOCK = new Object();
        private static DisplayShape sCacheForPath;
        private static int sCachedDisplayHeight;
        private static DisplayShape sCachedDisplayShape;
        private static int sCachedDisplayWidth;
        private static Path sCachedPath;
        private static float sCachedPhysicalPixelDisplaySizeRatio;
        private static String sCachedSpec;

        private Cache() {
        }

        static DisplayShape getDisplayShape(String str, float f, int i, int i2) {
            Object obj = CACHE_LOCK;
            synchronized (obj) {
                if (str.equals(sCachedSpec) && sCachedDisplayWidth == i && sCachedDisplayHeight == i2 && sCachedPhysicalPixelDisplaySizeRatio == f) {
                    return sCachedDisplayShape;
                }
                DisplayShape displayShape = new DisplayShape(str, i, i2, f, 0);
                synchronized (obj) {
                    sCachedSpec = str;
                    sCachedDisplayWidth = i;
                    sCachedDisplayHeight = i2;
                    sCachedPhysicalPixelDisplaySizeRatio = f;
                    sCachedDisplayShape = displayShape;
                }
                return displayShape;
            }
        }

        static Path getPath(DisplayShape displayShape) {
            Object obj = CACHE_LOCK;
            synchronized (obj) {
                if (displayShape.equals(sCacheForPath)) {
                    return sCachedPath;
                }
                Path createPathFromPathData = PathParser.createPathFromPathData(displayShape.mDisplayShapeSpec);
                if (!createPathFromPathData.isEmpty()) {
                    Matrix matrix = new Matrix();
                    if (displayShape.mRotation != 0) {
                        RotationUtils.transformPhysicalToLogicalCoordinates(displayShape.mRotation, displayShape.mDisplayWidth, displayShape.mDisplayHeight, matrix);
                    }
                    if (displayShape.mPhysicalPixelDisplaySizeRatio != 1.0f) {
                        matrix.preScale(displayShape.mPhysicalPixelDisplaySizeRatio, displayShape.mPhysicalPixelDisplaySizeRatio);
                    }
                    if (displayShape.mOffsetX != 0 || displayShape.mOffsetY != 0) {
                        matrix.postTranslate(displayShape.mOffsetX, displayShape.mOffsetY);
                    }
                    if (displayShape.mScale != 1.0f) {
                        matrix.postScale(displayShape.mScale, displayShape.mScale);
                    }
                    createPathFromPathData.transform(matrix);
                }
                synchronized (obj) {
                    sCacheForPath = displayShape;
                    sCachedPath = createPathFromPathData;
                }
                return createPathFromPathData;
            }
        }
    }
}
