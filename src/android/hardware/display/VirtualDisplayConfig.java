package android.hardware.display;

import android.annotation.SystemApi;
import android.hardware.display.IBrightnessListener;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.view.DisplayCutout;
import android.view.Surface;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class VirtualDisplayConfig implements Parcelable {
    public static final Parcelable.Creator<VirtualDisplayConfig> CREATOR = new Parcelable.Creator<VirtualDisplayConfig>() { // from class: android.hardware.display.VirtualDisplayConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualDisplayConfig[] newArray(int i) {
            return new VirtualDisplayConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualDisplayConfig createFromParcel(Parcel parcel) {
            return new VirtualDisplayConfig(parcel);
        }
    };
    private final IBrightnessListener mBrightnessListener;
    private final float mDefaultBrightness;
    private final int mDensityDpi;
    private final float mDimBrightness;
    private final ArraySet<String> mDisplayCategories;
    private final DisplayCutout mDisplayCutout;
    private final int mDisplayIdToMirror;
    private final int mFlags;
    private final int mHeight;
    private final boolean mIgnoreActivitySizeRestrictions;
    private final boolean mIsHomeSupported;
    private final String mName;
    private final float mRequestedRefreshRate;
    private final Surface mSurface;
    private final String mUniqueId;
    private final int mWidth;
    private final boolean mWindowManagerMirroringEnabled;

    public interface BrightnessListener {
        void onBrightnessChanged(float f);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualDisplayConfig(String str, int i, int i2, int i3, int i4, Surface surface, String str2, int i5, boolean z, ArraySet<String> arraySet, float f, boolean z2, DisplayCutout displayCutout, boolean z3, float f2, float f3, IBrightnessListener iBrightnessListener) {
        this.mName = str;
        this.mWidth = i;
        this.mHeight = i2;
        this.mDensityDpi = i3;
        this.mFlags = i4;
        this.mSurface = surface;
        this.mUniqueId = str2;
        this.mDisplayIdToMirror = i5;
        this.mWindowManagerMirroringEnabled = z;
        this.mDisplayCategories = arraySet;
        this.mRequestedRefreshRate = f;
        this.mIsHomeSupported = z2;
        this.mDisplayCutout = displayCutout;
        this.mIgnoreActivitySizeRestrictions = z3;
        this.mDefaultBrightness = f2;
        this.mDimBrightness = f3;
        this.mBrightnessListener = iBrightnessListener;
    }

    public String getName() {
        return this.mName;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getDensityDpi() {
        return this.mDensityDpi;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    @SystemApi
    public DisplayCutout getDisplayCutout() {
        return this.mDisplayCutout;
    }

    public float getDefaultBrightness() {
        return this.mDefaultBrightness;
    }

    public float getDimBrightness() {
        return this.mDimBrightness;
    }

    public IBrightnessListener getBrightnessListener() {
        return this.mBrightnessListener;
    }

    public String getUniqueId() {
        return this.mUniqueId;
    }

    public int getDisplayIdToMirror() {
        return this.mDisplayIdToMirror;
    }

    public boolean isWindowManagerMirroringEnabled() {
        return this.mWindowManagerMirroringEnabled;
    }

    @SystemApi
    public boolean isHomeSupported() {
        return this.mIsHomeSupported;
    }

    @SystemApi
    public boolean isIgnoreActivitySizeRestrictions() {
        return this.mIgnoreActivitySizeRestrictions && Flags.vdmForceAppUniversalResizableApi();
    }

    public Set<String> getDisplayCategories() {
        return Collections.unmodifiableSet(this.mDisplayCategories);
    }

    public float getRequestedRefreshRate() {
        return this.mRequestedRefreshRate;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeString8(this.mName);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mDensityDpi);
        parcel.writeInt(this.mFlags);
        parcel.writeTypedObject(this.mSurface, i);
        parcel.writeString8(this.mUniqueId);
        parcel.writeInt(this.mDisplayIdToMirror);
        parcel.writeBoolean(this.mWindowManagerMirroringEnabled);
        parcel.writeArraySet(this.mDisplayCategories);
        parcel.writeFloat(this.mRequestedRefreshRate);
        parcel.writeBoolean(this.mIsHomeSupported);
        DisplayCutout.ParcelableWrapper.writeCutoutToParcel(this.mDisplayCutout, parcel, i);
        parcel.writeBoolean(this.mIgnoreActivitySizeRestrictions);
        parcel.writeFloat(this.mDefaultBrightness);
        parcel.writeFloat(this.mDimBrightness);
        IBrightnessListener iBrightnessListener = this.mBrightnessListener;
        parcel.writeStrongBinder(iBrightnessListener != null ? iBrightnessListener.asBinder() : null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VirtualDisplayConfig)) {
            return false;
        }
        VirtualDisplayConfig virtualDisplayConfig = (VirtualDisplayConfig) obj;
        return Objects.equals(this.mName, virtualDisplayConfig.mName) && this.mWidth == virtualDisplayConfig.mWidth && this.mHeight == virtualDisplayConfig.mHeight && this.mDensityDpi == virtualDisplayConfig.mDensityDpi && this.mFlags == virtualDisplayConfig.mFlags && Objects.equals(this.mSurface, virtualDisplayConfig.mSurface) && Objects.equals(this.mUniqueId, virtualDisplayConfig.mUniqueId) && this.mDisplayIdToMirror == virtualDisplayConfig.mDisplayIdToMirror && this.mWindowManagerMirroringEnabled == virtualDisplayConfig.mWindowManagerMirroringEnabled && Objects.equals(this.mDisplayCategories, virtualDisplayConfig.mDisplayCategories) && this.mRequestedRefreshRate == virtualDisplayConfig.mRequestedRefreshRate && this.mIsHomeSupported == virtualDisplayConfig.mIsHomeSupported && this.mIgnoreActivitySizeRestrictions == virtualDisplayConfig.mIgnoreActivitySizeRestrictions && Objects.equals(this.mDisplayCutout, virtualDisplayConfig.mDisplayCutout) && this.mDefaultBrightness == virtualDisplayConfig.mDefaultBrightness && this.mDimBrightness == virtualDisplayConfig.mDimBrightness && Objects.equals(this.mBrightnessListener, virtualDisplayConfig.mBrightnessListener);
    }

    public int hashCode() {
        return Objects.hash(this.mName, Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.mDensityDpi), Integer.valueOf(this.mFlags), this.mSurface, this.mUniqueId, Integer.valueOf(this.mDisplayIdToMirror), Boolean.valueOf(this.mWindowManagerMirroringEnabled), this.mDisplayCategories, Float.valueOf(this.mRequestedRefreshRate), Boolean.valueOf(this.mIsHomeSupported), this.mDisplayCutout, Boolean.valueOf(this.mIgnoreActivitySizeRestrictions), Float.valueOf(this.mDefaultBrightness), Float.valueOf(this.mDimBrightness), this.mBrightnessListener);
    }

    public String toString() {
        return "VirtualDisplayConfig( mName=" + this.mName + " mHeight=" + this.mHeight + " mWidth=" + this.mWidth + " mDensityDpi=" + this.mDensityDpi + " mFlags=" + this.mFlags + " mSurface=" + this.mSurface + " mUniqueId=" + this.mUniqueId + " mDisplayIdToMirror=" + this.mDisplayIdToMirror + " mWindowManagerMirroringEnabled=" + this.mWindowManagerMirroringEnabled + " mDisplayCategories=" + this.mDisplayCategories + " mRequestedRefreshRate=" + this.mRequestedRefreshRate + " mIsHomeSupported=" + this.mIsHomeSupported + " mDisplayCutout=" + this.mDisplayCutout + " mIgnoreActivitySizeRestrictions=" + this.mIgnoreActivitySizeRestrictions + " mDefaultBrightness=" + this.mDefaultBrightness + " mDimBrightness=" + this.mDimBrightness + NavigationBarInflaterView.KEY_CODE_END;
    }

    private VirtualDisplayConfig(Parcel parcel) {
        this.mName = parcel.readString8();
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mDensityDpi = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mSurface = (Surface) parcel.readTypedObject(Surface.CREATOR);
        this.mUniqueId = parcel.readString8();
        this.mDisplayIdToMirror = parcel.readInt();
        this.mWindowManagerMirroringEnabled = parcel.readBoolean();
        this.mDisplayCategories = parcel.readArraySet(null);
        this.mRequestedRefreshRate = parcel.readFloat();
        this.mIsHomeSupported = parcel.readBoolean();
        this.mDisplayCutout = DisplayCutout.ParcelableWrapper.readCutoutFromParcel(parcel);
        this.mIgnoreActivitySizeRestrictions = parcel.readBoolean();
        this.mDefaultBrightness = parcel.readFloat();
        this.mDimBrightness = parcel.readFloat();
        this.mBrightnessListener = IBrightnessListener.Stub.asInterface(parcel.readStrongBinder());
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BrightnessListenerDelegate extends IBrightnessListener.Stub {
        private final Executor mExecutor;
        private final BrightnessListener mListener;

        BrightnessListenerDelegate(Executor executor, BrightnessListener brightnessListener) {
            this.mExecutor = executor;
            this.mListener = brightnessListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBrightnessChanged$0(float f) {
            this.mListener.onBrightnessChanged(f);
        }

        @Override // android.hardware.display.IBrightnessListener
        public void onBrightnessChanged(final float f) {
            this.mExecutor.execute(new Runnable() { // from class: android.hardware.display.VirtualDisplayConfig$BrightnessListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onBrightnessChanged$0(f);
                }
            });
        }
    }

    public static final class Builder {
        private final int mDensityDpi;
        private final int mHeight;
        private final String mName;
        private final int mWidth;
        private int mFlags = 0;
        private Surface mSurface = null;
        private String mUniqueId = null;
        private int mDisplayIdToMirror = 0;
        private boolean mWindowManagerMirroringEnabled = false;
        private ArraySet<String> mDisplayCategories = new ArraySet<>();
        private float mRequestedRefreshRate = 0.0f;
        private boolean mIsHomeSupported = false;
        private DisplayCutout mDisplayCutout = null;
        private boolean mIgnoreActivitySizeRestrictions = false;
        private float mDefaultBrightness = 0.0f;
        private float mDimBrightness = -1.0f;
        private IBrightnessListener mBrightnessListener = null;

        public Builder(String str, int i, int i2, int i3) {
            if (str == null) {
                throw new IllegalArgumentException("Virtual display name is required");
            }
            if (i <= 0) {
                throw new IllegalArgumentException("Virtual display width must be positive");
            }
            if (i2 <= 0) {
                throw new IllegalArgumentException("Virtual display height must be positive");
            }
            if (i3 <= 0) {
                throw new IllegalArgumentException("Virtual display density must be positive");
            }
            this.mName = str;
            this.mWidth = i;
            this.mHeight = i2;
            this.mDensityDpi = i3;
        }

        public Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public Builder setSurface(Surface surface) {
            this.mSurface = surface;
            return this;
        }

        public Builder setUniqueId(String str) {
            this.mUniqueId = str;
            return this;
        }

        public Builder setDisplayIdToMirror(int i) {
            this.mDisplayIdToMirror = i;
            return this;
        }

        public Builder setWindowManagerMirroringEnabled(boolean z) {
            this.mWindowManagerMirroringEnabled = z;
            return this;
        }

        public Builder setDisplayCategories(Set<String> set) {
            this.mDisplayCategories.clear();
            this.mDisplayCategories.addAll((Collection<? extends String>) Objects.requireNonNull(set));
            return this;
        }

        public Builder addDisplayCategory(String str) {
            this.mDisplayCategories.add((String) Objects.requireNonNull(str));
            return this;
        }

        public Builder setRequestedRefreshRate(float f) {
            if (f < 0.0f) {
                throw new IllegalArgumentException("Virtual display requested refresh rate must be non-negative");
            }
            this.mRequestedRefreshRate = f;
            return this;
        }

        @SystemApi
        public Builder setHomeSupported(boolean z) {
            this.mIsHomeSupported = z;
            return this;
        }

        @SystemApi
        public Builder setDisplayCutout(DisplayCutout displayCutout) {
            this.mDisplayCutout = displayCutout;
            return this;
        }

        @SystemApi
        public Builder setIgnoreActivitySizeRestrictions(boolean z) {
            this.mIgnoreActivitySizeRestrictions = z;
            return this;
        }

        public Builder setDefaultBrightness(float f) {
            if (!isValidBrightness(f)) {
                throw new IllegalArgumentException("Virtual display default brightness must be in range [0.0, 1.0]");
            }
            this.mDefaultBrightness = f;
            return this;
        }

        public Builder setDimBrightness(float f) {
            if (!isValidBrightness(f)) {
                throw new IllegalArgumentException("Virtual display dim brightness must be in range [0.0, 1.0]");
            }
            this.mDimBrightness = f;
            return this;
        }

        public Builder setBrightnessListener(Executor executor, BrightnessListener brightnessListener) {
            this.mBrightnessListener = new BrightnessListenerDelegate((Executor) Objects.requireNonNull(executor), (BrightnessListener) Objects.requireNonNull(brightnessListener));
            return this;
        }

        private boolean isValidBrightness(float f) {
            return !Float.isNaN(f) && 0.0f <= f && f <= 1.0f;
        }

        public VirtualDisplayConfig build() {
            if (isValidBrightness(this.mDimBrightness) && this.mDimBrightness > this.mDefaultBrightness) {
                throw new IllegalArgumentException("The dim brightness must not be greater than the default brightness");
            }
            return new VirtualDisplayConfig(this.mName, this.mWidth, this.mHeight, this.mDensityDpi, this.mFlags, this.mSurface, this.mUniqueId, this.mDisplayIdToMirror, this.mWindowManagerMirroringEnabled, this.mDisplayCategories, this.mRequestedRefreshRate, this.mIsHomeSupported, this.mDisplayCutout, this.mIgnoreActivitySizeRestrictions, this.mDefaultBrightness, this.mDimBrightness, this.mBrightnessListener);
        }
    }
}
