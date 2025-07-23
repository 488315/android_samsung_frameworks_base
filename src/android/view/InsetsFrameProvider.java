package android.view;

import android.app.admin.DevicePolicyResources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.WindowInsets;
import android.view.WindowManager;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public class InsetsFrameProvider implements Parcelable {
    public static final Parcelable.Creator<InsetsFrameProvider> CREATOR = new Parcelable.Creator<InsetsFrameProvider>() { // from class: android.view.InsetsFrameProvider.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsFrameProvider createFromParcel(Parcel parcel) {
            return new InsetsFrameProvider(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsFrameProvider[] newArray(int i) {
            return new InsetsFrameProvider[i];
        }
    };
    public static final int SOURCE_ARBITRARY_RECTANGLE = 3;
    public static final int SOURCE_CONTAINER_BOUNDS = 1;
    public static final int SOURCE_DISPLAY = 0;
    public static final int SOURCE_FRAME = 2;
    private Rect mArbitraryRectangle;
    private Rect[] mBoundingRects;
    private int mFlags;
    private final int mId;
    private Insets mInsetsSize;
    private InsetsSizeOverride[] mInsetsSizeOverrides;
    private Insets mMinimalInsetsSizeInDisplayCutoutSafe;
    private int mSource;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InsetsFrameProvider(Object obj, int i, int i2) {
        this.mSource = 2;
        this.mInsetsSize = null;
        this.mInsetsSizeOverrides = null;
        this.mMinimalInsetsSizeInDisplayCutoutSafe = null;
        this.mBoundingRects = null;
        this.mId = InsetsSource.createId(obj, i, i2);
    }

    public int getId() {
        return this.mId;
    }

    public int getIndex() {
        return InsetsSource.getIndex(this.mId);
    }

    public int getType() {
        return InsetsSource.getType(this.mId);
    }

    public InsetsFrameProvider setSource(int i) {
        this.mSource = i;
        return this;
    }

    public int getSource() {
        return this.mSource;
    }

    public InsetsFrameProvider setFlags(int i) {
        this.mFlags = i;
        return this;
    }

    public InsetsFrameProvider setFlags(int i, int i2) {
        this.mFlags = (i & i2) | (this.mFlags & (~i2));
        return this;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean hasFlags(int i) {
        return (this.mFlags & i) == i;
    }

    public InsetsFrameProvider setInsetsSize(Insets insets) {
        this.mInsetsSize = insets;
        return this;
    }

    public Insets getInsetsSize() {
        return this.mInsetsSize;
    }

    public InsetsFrameProvider setArbitraryRectangle(Rect rect) {
        this.mArbitraryRectangle = new Rect(rect);
        return this;
    }

    public Rect getArbitraryRectangle() {
        return this.mArbitraryRectangle;
    }

    public InsetsFrameProvider setInsetsSizeOverrides(InsetsSizeOverride[] insetsSizeOverrideArr) {
        this.mInsetsSizeOverrides = insetsSizeOverrideArr;
        return this;
    }

    public InsetsSizeOverride[] getInsetsSizeOverrides() {
        return this.mInsetsSizeOverrides;
    }

    public InsetsFrameProvider setMinimalInsetsSizeInDisplayCutoutSafe(Insets insets) {
        this.mMinimalInsetsSizeInDisplayCutoutSafe = insets;
        return this;
    }

    public Insets getMinimalInsetsSizeInDisplayCutoutSafe() {
        return this.mMinimalInsetsSizeInDisplayCutoutSafe;
    }

    public InsetsFrameProvider setBoundingRects(Rect[] rectArr) {
        this.mBoundingRects = rectArr == null ? null : (Rect[]) rectArr.clone();
        return this;
    }

    public Rect[] getBoundingRects() {
        return this.mBoundingRects;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("InsetsFrameProvider: {id=#");
        sb.append(Integer.toHexString(this.mId));
        sb.append(", index=");
        sb.append(getIndex());
        sb.append(", type=");
        sb.append(WindowInsets.Type.toString(getType()));
        sb.append(", source=");
        sb.append(sourceToString(this.mSource));
        sb.append(", flags=[");
        sb.append(InsetsSource.flagsToString(this.mFlags));
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        if (this.mInsetsSize != null) {
            sb.append(", insetsSize=");
            sb.append(this.mInsetsSize);
        }
        if (this.mInsetsSizeOverrides != null) {
            sb.append(", insetsSizeOverrides=");
            sb.append(Arrays.toString(this.mInsetsSizeOverrides));
        }
        if (this.mArbitraryRectangle != null) {
            sb.append(", mArbitraryRectangle=");
            sb.append(this.mArbitraryRectangle.toShortString());
        }
        if (this.mMinimalInsetsSizeInDisplayCutoutSafe != null) {
            sb.append(", mMinimalInsetsSizeInDisplayCutoutSafe=");
            sb.append(this.mMinimalInsetsSizeInDisplayCutoutSafe);
        }
        if (this.mBoundingRects != null) {
            sb.append(", mBoundingRects=");
            sb.append(Arrays.toString(this.mBoundingRects));
        }
        sb.append("}");
        return sb.toString();
    }

    private static String sourceToString(int i) {
        if (i == 0) {
            return "DISPLAY";
        }
        if (i == 1) {
            return "CONTAINER_BOUNDS";
        }
        if (i == 2) {
            return "FRAME";
        }
        if (i == 3) {
            return "ARBITRARY_RECTANGLE";
        }
        return DevicePolicyResources.UNDEFINED;
    }

    public InsetsFrameProvider(Parcel parcel) {
        this.mSource = 2;
        this.mInsetsSize = null;
        this.mInsetsSizeOverrides = null;
        this.mMinimalInsetsSizeInDisplayCutoutSafe = null;
        this.mBoundingRects = null;
        this.mId = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mInsetsSize = (Insets) parcel.readTypedObject(Insets.CREATOR);
        this.mInsetsSizeOverrides = (InsetsSizeOverride[]) parcel.createTypedArray(InsetsSizeOverride.CREATOR);
        this.mArbitraryRectangle = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.mMinimalInsetsSizeInDisplayCutoutSafe = (Insets) parcel.readTypedObject(Insets.CREATOR);
        this.mBoundingRects = (Rect[]) parcel.createTypedArray(Rect.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mFlags);
        parcel.writeTypedObject(this.mInsetsSize, i);
        parcel.writeTypedArray(this.mInsetsSizeOverrides, i);
        parcel.writeTypedObject(this.mArbitraryRectangle, i);
        parcel.writeTypedObject(this.mMinimalInsetsSizeInDisplayCutoutSafe, i);
        parcel.writeTypedArray(this.mBoundingRects, i);
    }

    public boolean idEquals(InsetsFrameProvider insetsFrameProvider) {
        return this.mId == insetsFrameProvider.mId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            InsetsFrameProvider insetsFrameProvider = (InsetsFrameProvider) obj;
            if (this.mId == insetsFrameProvider.mId && this.mSource == insetsFrameProvider.mSource && this.mFlags == insetsFrameProvider.mFlags && Objects.equals(this.mInsetsSize, insetsFrameProvider.mInsetsSize) && Arrays.equals(this.mInsetsSizeOverrides, insetsFrameProvider.mInsetsSizeOverrides) && Objects.equals(this.mArbitraryRectangle, insetsFrameProvider.mArbitraryRectangle) && Objects.equals(this.mMinimalInsetsSizeInDisplayCutoutSafe, insetsFrameProvider.mMinimalInsetsSizeInDisplayCutoutSafe) && Arrays.equals(this.mBoundingRects, insetsFrameProvider.mBoundingRects)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mId), Integer.valueOf(this.mSource), Integer.valueOf(this.mFlags), this.mInsetsSize, Integer.valueOf(Arrays.hashCode(this.mInsetsSizeOverrides)), this.mArbitraryRectangle, this.mMinimalInsetsSizeInDisplayCutoutSafe, Integer.valueOf(Arrays.hashCode(this.mBoundingRects)));
    }

    public static class InsetsSizeOverride implements Parcelable {
        public static final Parcelable.Creator<InsetsSizeOverride> CREATOR = new Parcelable.Creator<InsetsSizeOverride>() { // from class: android.view.InsetsFrameProvider.InsetsSizeOverride.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InsetsSizeOverride createFromParcel(Parcel parcel) {
                return new InsetsSizeOverride(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InsetsSizeOverride[] newArray(int i) {
                return new InsetsSizeOverride[i];
            }
        };
        private final Insets mInsetsSize;
        private final int mWindowType;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected InsetsSizeOverride(Parcel parcel) {
            this.mWindowType = parcel.readInt();
            this.mInsetsSize = (Insets) parcel.readTypedObject(Insets.CREATOR);
        }

        public InsetsSizeOverride(int i, Insets insets) {
            this.mWindowType = i;
            this.mInsetsSize = insets;
        }

        public int getWindowType() {
            return this.mWindowType;
        }

        public Insets getInsetsSize() {
            return this.mInsetsSize;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mWindowType);
            parcel.writeTypedObject(this.mInsetsSize, i);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(32);
            sb.append("TypedInsetsSize: {windowType=");
            sb.append(ViewDebug.intToString(WindowManager.LayoutParams.class, "type", this.mWindowType));
            sb.append(", insetsSize=");
            sb.append(this.mInsetsSize);
            sb.append("}");
            return sb.toString();
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mWindowType), this.mInsetsSize);
        }
    }
}
