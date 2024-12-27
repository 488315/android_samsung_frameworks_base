package android.window;

import android.content.ComponentName;
import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

import com.android.window.flags.Flags;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TaskSnapshot implements Parcelable {
    public static final Parcelable.Creator<TaskSnapshot> CREATOR =
            new Parcelable.Creator<TaskSnapshot>() { // from class: android.window.TaskSnapshot.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TaskSnapshot createFromParcel(Parcel source) {
                    return new TaskSnapshot(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TaskSnapshot[] newArray(int size) {
                    return new TaskSnapshot[size];
                }
            };
    public static final int REFERENCE_BROADCAST = 1;
    public static final int REFERENCE_CACHE = 2;
    public static final int REFERENCE_PERSIST = 4;
    private final int mAppearance;
    private final long mCaptureTime;
    private final ColorSpace mColorSpace;
    private boolean mContainsSecureLayers;
    private final Rect mContentInsets;
    private final Rect mCutoutInsets;
    private final boolean mHasImeSurface;
    private final long mId;
    private int mInternalReferences;
    private boolean mIsFolded;
    private final boolean mIsLowResolution;
    private final boolean mIsRealSnapshot;
    private final boolean mIsTranslucent;
    private final Rect mLetterboxInsets;
    private final int mOrientation;
    private final int mRotation;
    private final HardwareBuffer mSnapshot;
    private final Point mTaskSize;
    private final ComponentName mTopActivityComponent;
    private final int mWindowingMode;

    @Retention(RetentionPolicy.SOURCE)
    @interface ReferenceFlags {}

    public TaskSnapshot(
            long id,
            long captureTime,
            ComponentName topActivityComponent,
            HardwareBuffer snapshot,
            ColorSpace colorSpace,
            int orientation,
            int rotation,
            Point taskSize,
            Rect contentInsets,
            Rect letterboxInsets,
            boolean isLowResolution,
            boolean isRealSnapshot,
            int windowingMode,
            int appearance,
            boolean isTranslucent,
            boolean hasImeSurface) {
        this(
                id,
                captureTime,
                topActivityComponent,
                snapshot,
                colorSpace,
                orientation,
                rotation,
                taskSize,
                contentInsets,
                letterboxInsets,
                isLowResolution,
                isRealSnapshot,
                windowingMode,
                appearance,
                isTranslucent,
                hasImeSurface,
                null);
    }

    public TaskSnapshot(
            long id,
            long captureTime,
            ComponentName topActivityComponent,
            HardwareBuffer snapshot,
            ColorSpace colorSpace,
            int orientation,
            int rotation,
            Point taskSize,
            Rect contentInsets,
            Rect letterboxInsets,
            boolean isLowResolution,
            boolean isRealSnapshot,
            int windowingMode,
            int appearance,
            boolean isTranslucent,
            boolean hasImeSurface,
            Rect cutoutInsets) {
        this(
                id,
                captureTime,
                topActivityComponent,
                snapshot,
                colorSpace,
                orientation,
                rotation,
                taskSize,
                contentInsets,
                letterboxInsets,
                isLowResolution,
                isRealSnapshot,
                windowingMode,
                appearance,
                isTranslucent,
                hasImeSurface,
                null,
                false);
    }

    public TaskSnapshot(
            long id,
            long captureTime,
            ComponentName topActivityComponent,
            HardwareBuffer snapshot,
            ColorSpace colorSpace,
            int orientation,
            int rotation,
            Point taskSize,
            Rect contentInsets,
            Rect letterboxInsets,
            boolean isLowResolution,
            boolean isRealSnapshot,
            int windowingMode,
            int appearance,
            boolean isTranslucent,
            boolean hasImeSurface,
            Rect cutoutInsets,
            boolean containsSecureLayers) {
        this(
                id,
                captureTime,
                topActivityComponent,
                snapshot,
                colorSpace,
                orientation,
                rotation,
                taskSize,
                contentInsets,
                letterboxInsets,
                isLowResolution,
                isRealSnapshot,
                windowingMode,
                appearance,
                isTranslucent,
                hasImeSurface,
                null,
                containsSecureLayers,
                false);
    }

    public TaskSnapshot(
            long id,
            long captureTime,
            ComponentName topActivityComponent,
            HardwareBuffer snapshot,
            ColorSpace colorSpace,
            int orientation,
            int rotation,
            Point taskSize,
            Rect contentInsets,
            Rect letterboxInsets,
            boolean isLowResolution,
            boolean isRealSnapshot,
            int windowingMode,
            int appearance,
            boolean isTranslucent,
            boolean hasImeSurface,
            Rect cutoutInsets,
            boolean containsSecureLayers,
            boolean isFolded) {
        this.mId = id;
        this.mCaptureTime = captureTime;
        this.mTopActivityComponent = topActivityComponent;
        this.mSnapshot = snapshot;
        this.mColorSpace =
                colorSpace.getId() < 0 ? ColorSpace.get(ColorSpace.Named.SRGB) : colorSpace;
        this.mOrientation = orientation;
        this.mRotation = rotation;
        this.mTaskSize = new Point(taskSize);
        this.mContentInsets = new Rect(contentInsets);
        this.mLetterboxInsets = new Rect(letterboxInsets);
        this.mIsLowResolution = isLowResolution;
        this.mIsRealSnapshot = isRealSnapshot;
        this.mWindowingMode = windowingMode;
        this.mAppearance = appearance;
        this.mIsTranslucent = isTranslucent;
        this.mHasImeSurface = hasImeSurface;
        this.mCutoutInsets = new Rect(cutoutInsets);
        this.mContainsSecureLayers = containsSecureLayers;
    }

    private TaskSnapshot(Parcel source) {
        ColorSpace colorSpace;
        this.mId = source.readLong();
        this.mCaptureTime = SystemClock.elapsedRealtimeNanos();
        this.mTopActivityComponent = ComponentName.readFromParcel(source);
        this.mSnapshot = (HardwareBuffer) source.readTypedObject(HardwareBuffer.CREATOR);
        int colorSpaceId = source.readInt();
        if (colorSpaceId >= 0 && colorSpaceId < ColorSpace.Named.values().length) {
            colorSpace = ColorSpace.get(ColorSpace.Named.values()[colorSpaceId]);
        } else {
            colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        }
        this.mColorSpace = colorSpace;
        this.mOrientation = source.readInt();
        this.mRotation = source.readInt();
        this.mTaskSize = (Point) source.readTypedObject(Point.CREATOR);
        this.mContentInsets = (Rect) source.readTypedObject(Rect.CREATOR);
        this.mLetterboxInsets = (Rect) source.readTypedObject(Rect.CREATOR);
        this.mIsLowResolution = source.readBoolean();
        this.mIsRealSnapshot = source.readBoolean();
        this.mWindowingMode = source.readInt();
        this.mAppearance = source.readInt();
        this.mIsTranslucent = source.readBoolean();
        this.mHasImeSurface = source.readBoolean();
        this.mCutoutInsets = (Rect) source.readTypedObject(Rect.CREATOR);
        this.mContainsSecureLayers = source.readBoolean();
    }

    public long getId() {
        return this.mId;
    }

    public long getCaptureTime() {
        return this.mCaptureTime;
    }

    public ComponentName getTopActivityComponent() {
        return this.mTopActivityComponent;
    }

    public GraphicBuffer getSnapshot() {
        return GraphicBuffer.createFromHardwareBuffer(this.mSnapshot);
    }

    public HardwareBuffer getHardwareBuffer() {
        return this.mSnapshot;
    }

    public ColorSpace getColorSpace() {
        return this.mColorSpace;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getRotation() {
        return this.mRotation;
    }

    public Point getTaskSize() {
        return this.mTaskSize;
    }

    public Rect getContentInsets() {
        return this.mContentInsets;
    }

    public Rect getLetterboxInsets() {
        return this.mLetterboxInsets;
    }

    public boolean isLowResolution() {
        return this.mIsLowResolution;
    }

    public boolean isRealSnapshot() {
        return this.mIsRealSnapshot;
    }

    public boolean isTranslucent() {
        return this.mIsTranslucent;
    }

    public boolean hasImeSurface() {
        return this.mHasImeSurface;
    }

    public int getWindowingMode() {
        return this.mWindowingMode;
    }

    public Rect getCutoutInsets() {
        return this.mCutoutInsets;
    }

    public boolean containsSecureLayers() {
        return this.mContainsSecureLayers;
    }

    public boolean isFolded() {
        return this.mIsFolded;
    }

    public int getAppearance() {
        return this.mAppearance;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        ComponentName.writeToParcel(this.mTopActivityComponent, dest);
        dest.writeTypedObject(
                (this.mSnapshot == null || this.mSnapshot.isClosed()) ? null : this.mSnapshot, 0);
        dest.writeInt(this.mColorSpace.getId());
        dest.writeInt(this.mOrientation);
        dest.writeInt(this.mRotation);
        dest.writeTypedObject(this.mTaskSize, 0);
        dest.writeTypedObject(this.mContentInsets, 0);
        dest.writeTypedObject(this.mLetterboxInsets, 0);
        dest.writeBoolean(this.mIsLowResolution);
        dest.writeBoolean(this.mIsRealSnapshot);
        dest.writeInt(this.mWindowingMode);
        dest.writeInt(this.mAppearance);
        dest.writeBoolean(this.mIsTranslucent);
        dest.writeBoolean(this.mHasImeSurface);
        dest.writeTypedObject(this.mCutoutInsets, 0);
        dest.writeBoolean(this.mContainsSecureLayers);
    }

    public String toString() {
        int width = this.mSnapshot != null ? this.mSnapshot.getWidth() : 0;
        int height = this.mSnapshot != null ? this.mSnapshot.getHeight() : 0;
        return "TaskSnapshot{ mId="
                + this.mId
                + " mCaptureTime="
                + this.mCaptureTime
                + " mTopActivityComponent="
                + this.mTopActivityComponent.flattenToShortString()
                + " mSnapshot="
                + this.mSnapshot
                + " ("
                + width
                + "x"
                + height
                + ") mColorSpace="
                + this.mColorSpace.toString()
                + " mOrientation="
                + this.mOrientation
                + " mRotation="
                + this.mRotation
                + " mTaskSize="
                + this.mTaskSize.toString()
                + " mContentInsets="
                + this.mContentInsets.toShortString()
                + " mLetterboxInsets="
                + this.mLetterboxInsets.toShortString()
                + " mIsLowResolution="
                + this.mIsLowResolution
                + " mIsRealSnapshot="
                + this.mIsRealSnapshot
                + " mWindowingMode="
                + this.mWindowingMode
                + " mAppearance="
                + this.mAppearance
                + " mIsTranslucent="
                + this.mIsTranslucent
                + " mHasImeSurface="
                + this.mHasImeSurface
                + " mInternalReferences="
                + this.mInternalReferences;
    }

    public synchronized void addReference(int usage) {
        this.mInternalReferences |= usage;
    }

    public synchronized void removeReference(int usage) {
        this.mInternalReferences &= ~usage;
        if (Flags.releaseSnapshotAggressively()
                && this.mInternalReferences == 0
                && this.mSnapshot != null
                && !this.mSnapshot.isClosed()) {
            this.mSnapshot.close();
        }
    }

    public static final class Builder {
        private int mAppearance;
        private long mCaptureTime;
        private ColorSpace mColorSpace;
        private boolean mContainsSecureLayers;
        private Rect mContentInsets;
        private Rect mCutoutInsets;
        private boolean mHasImeSurface;
        private long mId;
        private boolean mIsFolded;
        private boolean mIsRealSnapshot;
        private boolean mIsTranslucent;
        private Rect mLetterboxInsets;
        private int mOrientation;
        private int mPixelFormat;
        private int mRotation;
        private HardwareBuffer mSnapshot;
        private Point mTaskSize;
        private ComponentName mTopActivity;
        private int mWindowingMode;

        public Builder setId(long id) {
            this.mId = id;
            return this;
        }

        public Builder setCaptureTime(long captureTime) {
            this.mCaptureTime = captureTime;
            return this;
        }

        public Builder setTopActivityComponent(ComponentName name) {
            this.mTopActivity = name;
            return this;
        }

        public Builder setSnapshot(HardwareBuffer buffer) {
            this.mSnapshot = buffer;
            return this;
        }

        public Builder setColorSpace(ColorSpace colorSpace) {
            this.mColorSpace = colorSpace;
            return this;
        }

        public Builder setOrientation(int orientation) {
            this.mOrientation = orientation;
            return this;
        }

        public Builder setRotation(int rotation) {
            this.mRotation = rotation;
            return this;
        }

        public Builder setTaskSize(Point size) {
            this.mTaskSize = size;
            return this;
        }

        public Builder setContentInsets(Rect contentInsets) {
            this.mContentInsets = contentInsets;
            return this;
        }

        public Builder setLetterboxInsets(Rect letterboxInsets) {
            this.mLetterboxInsets = letterboxInsets;
            return this;
        }

        public Builder setIsRealSnapshot(boolean realSnapshot) {
            this.mIsRealSnapshot = realSnapshot;
            return this;
        }

        public Builder setWindowingMode(int windowingMode) {
            this.mWindowingMode = windowingMode;
            return this;
        }

        public Builder setAppearance(int appearance) {
            this.mAppearance = appearance;
            return this;
        }

        public Builder setIsTranslucent(boolean isTranslucent) {
            this.mIsTranslucent = isTranslucent;
            return this;
        }

        public Builder setHasImeSurface(boolean hasImeSurface) {
            this.mHasImeSurface = hasImeSurface;
            return this;
        }

        public int getPixelFormat() {
            return this.mPixelFormat;
        }

        public Builder setPixelFormat(int pixelFormat) {
            this.mPixelFormat = pixelFormat;
            return this;
        }

        public Builder setCutoutInsets(Rect cutoutInsets) {
            this.mCutoutInsets = cutoutInsets;
            return this;
        }

        public Builder setContainsSecureLayers(boolean containsSecureLayers) {
            this.mContainsSecureLayers = containsSecureLayers;
            return this;
        }

        public Builder setFolded(boolean folded) {
            this.mIsFolded = folded;
            return this;
        }

        public TaskSnapshot build() {
            return new TaskSnapshot(
                    this.mId,
                    this.mCaptureTime,
                    this.mTopActivity,
                    this.mSnapshot,
                    this.mColorSpace,
                    this.mOrientation,
                    this.mRotation,
                    this.mTaskSize,
                    this.mContentInsets,
                    this.mLetterboxInsets,
                    false,
                    this.mIsRealSnapshot,
                    this.mWindowingMode,
                    this.mAppearance,
                    this.mIsTranslucent,
                    this.mHasImeSurface,
                    this.mCutoutInsets,
                    this.mContainsSecureLayers,
                    this.mIsFolded);
        }
    }
}
