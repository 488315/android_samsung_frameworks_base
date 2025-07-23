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
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class TaskSnapshot implements Parcelable {
    public static final Parcelable.Creator<TaskSnapshot> CREATOR = new Parcelable.Creator<TaskSnapshot>() { // from class: android.window.TaskSnapshot.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskSnapshot createFromParcel(Parcel parcel) {
            return new TaskSnapshot(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaskSnapshot[] newArray(int i) {
            return new TaskSnapshot[i];
        }
    };
    public static final int REFERENCE_BROADCAST = 1;
    public static final int REFERENCE_CACHE = 2;
    public static final int REFERENCE_CONTENT_SUGGESTION = 8;
    public static final int REFERENCE_NONE = 0;
    public static final int REFERENCE_PERSIST = 4;
    public static final int REFERENCE_WRITE_TO_PARCEL = 16;
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
    private Consumer<HardwareBuffer> mSafeSnapshotReleaser;
    private final HardwareBuffer mSnapshot;
    private final Point mTaskSize;
    private final ComponentName mTopActivityComponent;
    private final int mUiMode;
    private final int mWindowingMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ReferenceFlags {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TaskSnapshot(long j, long j2, ComponentName componentName, HardwareBuffer hardwareBuffer, ColorSpace colorSpace, int i, int i2, Point point, Rect rect, Rect rect2, boolean z, boolean z2, int i3, int i4, boolean z3, boolean z4, int i5) {
        this(j, j2, componentName, hardwareBuffer, colorSpace, i, i2, point, rect, rect2, z, z2, i3, i4, z3, z4, i5, null);
    }

    public TaskSnapshot(long j, long j2, ComponentName componentName, HardwareBuffer hardwareBuffer, ColorSpace colorSpace, int i, int i2, Point point, Rect rect, Rect rect2, boolean z, boolean z2, int i3, int i4, boolean z3, boolean z4, int i5, Rect rect3) {
        this(j, j2, componentName, hardwareBuffer, colorSpace, i, i2, point, rect, rect2, z, z2, i3, i4, z3, z4, i5, rect3, false);
    }

    public TaskSnapshot(long j, long j2, ComponentName componentName, HardwareBuffer hardwareBuffer, ColorSpace colorSpace, int i, int i2, Point point, Rect rect, Rect rect2, boolean z, boolean z2, int i3, int i4, boolean z3, boolean z4, int i5, Rect rect3, boolean z5) {
        this(j, j2, componentName, hardwareBuffer, colorSpace, i, i2, point, rect, rect2, z, z2, i3, i4, z3, z4, i5, rect3, z5, false);
    }

    public TaskSnapshot(long j, long j2, ComponentName componentName, HardwareBuffer hardwareBuffer, ColorSpace colorSpace, int i, int i2, Point point, Rect rect, Rect rect2, boolean z, boolean z2, int i3, int i4, boolean z3, boolean z4, int i5, Rect rect3, boolean z5, boolean z6) {
        this.mId = j;
        this.mCaptureTime = j2;
        this.mTopActivityComponent = componentName;
        this.mSnapshot = hardwareBuffer;
        this.mColorSpace = colorSpace.getId() < 0 ? ColorSpace.get(ColorSpace.Named.SRGB) : colorSpace;
        this.mOrientation = i;
        this.mRotation = i2;
        this.mTaskSize = new Point(point);
        this.mContentInsets = new Rect(rect);
        this.mLetterboxInsets = new Rect(rect2);
        this.mIsLowResolution = z;
        this.mIsRealSnapshot = z2;
        this.mWindowingMode = i3;
        this.mAppearance = i4;
        this.mIsTranslucent = z3;
        this.mHasImeSurface = z4;
        this.mUiMode = i5;
        this.mCutoutInsets = new Rect(rect3);
        this.mContainsSecureLayers = z5;
    }

    private TaskSnapshot(Parcel parcel) {
        ColorSpace colorSpace;
        this.mId = parcel.readLong();
        this.mCaptureTime = SystemClock.elapsedRealtimeNanos();
        this.mTopActivityComponent = ComponentName.readFromParcel(parcel);
        this.mSnapshot = (HardwareBuffer) parcel.readTypedObject(HardwareBuffer.CREATOR);
        int readInt = parcel.readInt();
        if (readInt >= 0 && readInt < ColorSpace.Named.values().length) {
            colorSpace = ColorSpace.get(ColorSpace.Named.values()[readInt]);
        } else {
            colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        }
        this.mColorSpace = colorSpace;
        this.mOrientation = parcel.readInt();
        this.mRotation = parcel.readInt();
        this.mTaskSize = (Point) parcel.readTypedObject(Point.CREATOR);
        this.mContentInsets = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.mLetterboxInsets = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.mIsLowResolution = parcel.readBoolean();
        this.mIsRealSnapshot = parcel.readBoolean();
        this.mWindowingMode = parcel.readInt();
        this.mAppearance = parcel.readInt();
        this.mIsTranslucent = parcel.readBoolean();
        this.mHasImeSurface = parcel.readBoolean();
        this.mUiMode = parcel.readInt();
        this.mCutoutInsets = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.mContainsSecureLayers = parcel.readBoolean();
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

    public int getUiMode() {
        return this.mUiMode;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mId);
        ComponentName.writeToParcel(this.mTopActivityComponent, parcel);
        HardwareBuffer hardwareBuffer = this.mSnapshot;
        parcel.writeTypedObject((hardwareBuffer == null || hardwareBuffer.isClosed()) ? null : this.mSnapshot, 0);
        parcel.writeInt(this.mColorSpace.getId());
        parcel.writeInt(this.mOrientation);
        parcel.writeInt(this.mRotation);
        parcel.writeTypedObject(this.mTaskSize, 0);
        parcel.writeTypedObject(this.mContentInsets, 0);
        parcel.writeTypedObject(this.mLetterboxInsets, 0);
        parcel.writeBoolean(this.mIsLowResolution);
        parcel.writeBoolean(this.mIsRealSnapshot);
        parcel.writeInt(this.mWindowingMode);
        parcel.writeInt(this.mAppearance);
        parcel.writeBoolean(this.mIsTranslucent);
        parcel.writeBoolean(this.mHasImeSurface);
        parcel.writeInt(this.mUiMode);
        parcel.writeTypedObject(this.mCutoutInsets, 0);
        parcel.writeBoolean(this.mContainsSecureLayers);
        synchronized (this) {
            if ((this.mInternalReferences & 16) != 0) {
                removeReference(16);
            }
        }
        synchronized (this) {
            if ((this.mInternalReferences & 16) != 0) {
                removeReference(16);
            }
        }
    }

    public String toString() {
        HardwareBuffer hardwareBuffer = this.mSnapshot;
        int width = hardwareBuffer != null ? hardwareBuffer.getWidth() : 0;
        HardwareBuffer hardwareBuffer2 = this.mSnapshot;
        return "TaskSnapshot{ mId=" + this.mId + " mCaptureTime=" + this.mCaptureTime + " mTopActivityComponent=" + this.mTopActivityComponent.flattenToShortString() + " mSnapshot=" + this.mSnapshot + " (" + width + "x" + (hardwareBuffer2 != null ? hardwareBuffer2.getHeight() : 0) + ") mColorSpace=" + this.mColorSpace.toString() + " mOrientation=" + this.mOrientation + " mRotation=" + this.mRotation + " mTaskSize=" + this.mTaskSize.toString() + " mContentInsets=" + this.mContentInsets.toShortString() + " mLetterboxInsets=" + this.mLetterboxInsets.toShortString() + " mIsLowResolution=" + this.mIsLowResolution + " mIsRealSnapshot=" + this.mIsRealSnapshot + " mWindowingMode=" + this.mWindowingMode + " mAppearance=" + this.mAppearance + " mIsTranslucent=" + this.mIsTranslucent + " mHasImeSurface=" + this.mHasImeSurface + " mInternalReferences=" + this.mInternalReferences + " mUiMode=" + Integer.toHexString(this.mUiMode);
    }

    public synchronized void addReference(int i) {
        this.mInternalReferences = i | this.mInternalReferences;
    }

    public synchronized void removeReference(int i) {
        HardwareBuffer hardwareBuffer;
        this.mInternalReferences = (~i) & this.mInternalReferences;
        if (Flags.releaseSnapshotAggressively() && this.mInternalReferences == 0 && (hardwareBuffer = this.mSnapshot) != null && !hardwareBuffer.isClosed()) {
            Consumer<HardwareBuffer> consumer = this.mSafeSnapshotReleaser;
            if (consumer != null) {
                consumer.accept(this.mSnapshot);
            } else {
                this.mSnapshot.close();
            }
        }
    }

    public synchronized void setSafeRelease(Consumer<HardwareBuffer> consumer) {
        if (Flags.safeReleaseSnapshotAggressively()) {
            this.mSafeSnapshotReleaser = consumer;
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
        private int mUiMode;
        private int mWindowingMode;

        public Builder setId(long j) {
            this.mId = j;
            return this;
        }

        public Builder setCaptureTime(long j) {
            this.mCaptureTime = j;
            return this;
        }

        public Builder setTopActivityComponent(ComponentName componentName) {
            this.mTopActivity = componentName;
            return this;
        }

        public Builder setSnapshot(HardwareBuffer hardwareBuffer) {
            this.mSnapshot = hardwareBuffer;
            return this;
        }

        public Builder setColorSpace(ColorSpace colorSpace) {
            this.mColorSpace = colorSpace;
            return this;
        }

        public Builder setOrientation(int i) {
            this.mOrientation = i;
            return this;
        }

        public Builder setRotation(int i) {
            this.mRotation = i;
            return this;
        }

        public Builder setTaskSize(Point point) {
            this.mTaskSize = point;
            return this;
        }

        public Builder setContentInsets(Rect rect) {
            this.mContentInsets = rect;
            return this;
        }

        public Builder setLetterboxInsets(Rect rect) {
            this.mLetterboxInsets = rect;
            return this;
        }

        public Builder setIsRealSnapshot(boolean z) {
            this.mIsRealSnapshot = z;
            return this;
        }

        public Builder setWindowingMode(int i) {
            this.mWindowingMode = i;
            return this;
        }

        public Builder setAppearance(int i) {
            this.mAppearance = i;
            return this;
        }

        public Builder setIsTranslucent(boolean z) {
            this.mIsTranslucent = z;
            return this;
        }

        public Builder setHasImeSurface(boolean z) {
            this.mHasImeSurface = z;
            return this;
        }

        public Builder setUiMode(int i) {
            this.mUiMode = i;
            return this;
        }

        public int getPixelFormat() {
            return this.mPixelFormat;
        }

        public Builder setPixelFormat(int i) {
            this.mPixelFormat = i;
            return this;
        }

        public Builder setCutoutInsets(Rect rect) {
            this.mCutoutInsets = rect;
            return this;
        }

        public Builder setContainsSecureLayers(boolean z) {
            this.mContainsSecureLayers = z;
            return this;
        }

        public Builder setFolded(boolean z) {
            this.mIsFolded = z;
            return this;
        }

        public TaskSnapshot build() {
            return new TaskSnapshot(this.mId, this.mCaptureTime, this.mTopActivity, this.mSnapshot, this.mColorSpace, this.mOrientation, this.mRotation, this.mTaskSize, this.mContentInsets, this.mLetterboxInsets, false, this.mIsRealSnapshot, this.mWindowingMode, this.mAppearance, this.mIsTranslucent, this.mHasImeSurface, this.mUiMode, this.mCutoutInsets, this.mContainsSecureLayers, this.mIsFolded);
        }
    }
}
