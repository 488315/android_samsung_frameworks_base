package android.content.res;

import android.content.pm.ApplicationInfo;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.MergedConfiguration;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.WindowManager;

/* loaded from: classes.dex */
public class CompatibilityInfo implements Parcelable {
    private static final int ALWAYS_NEEDS_COMPAT = 2;
    public static final int DEFAULT_NORMAL_SHORT_DIMENSION = 320;
    private static final int HAS_OVERRIDE_SCALING = 32;
    public static final float MAXIMUM_ASPECT_RATIO = 1.7791667f;
    private static final int NEEDS_COMPAT_RES = 16;
    private static final int NEEDS_SCREEN_COMPAT = 8;
    private static final int NEVER_NEEDS_COMPAT = 4;
    private static final int SCALING_REQUIRED = 1;
    public final int applicationDensity;
    public final float applicationDensityInvertedScale;
    public final float applicationDensityScale;
    public int applicationDisplayRotation;
    public final float applicationInvertedScale;
    public final float applicationScale;
    private final int mCompatibilityFlags;
    public static final CompatibilityInfo DEFAULT_COMPATIBILITY_INFO = new CompatibilityInfo() { // from class: android.content.res.CompatibilityInfo.1
    };
    private static float sOverrideInvertedScale = 1.0f;
    private static float sOverrideDensityInvertScale = 1.0f;
    private static int sOverrideDisplayRotation = -1;
    public static final Parcelable.Creator<CompatibilityInfo> CREATOR = new Parcelable.Creator<CompatibilityInfo>() { // from class: android.content.res.CompatibilityInfo.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityInfo createFromParcel(Parcel parcel) {
            return new CompatibilityInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatibilityInfo[] newArray(int i) {
            return new CompatibilityInfo[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public CompatibilityInfo(ApplicationInfo applicationInfo, int i, int i2, boolean z) {
        this(applicationInfo, i, i2, z, 1.0f);
    }

    public CompatibilityInfo(ApplicationInfo applicationInfo, int i, int i2, boolean z, float f) {
        this(applicationInfo, i, i2, z, f, f);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x007b A[PHI: r8
      0x007b: PHI (r8v17 int) = (r8v9 int), (r8v19 int) binds: [B:49:0x0079, B:43:0x006c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CompatibilityInfo(ApplicationInfo applicationInfo, int i, int i2, boolean z, float f, float f2) {
        int i3;
        int i4;
        boolean z2;
        this.applicationDisplayRotation = -1;
        int i5 = 0;
        int i6 = applicationInfo.targetSdkVersion < 26 ? 16 : 0;
        if (f != 1.0f || f2 != 1.0f) {
            this.applicationScale = f;
            this.applicationInvertedScale = 1.0f / f;
            this.applicationDensityScale = f2;
            float f3 = 1.0f / f2;
            this.applicationDensityInvertedScale = f3;
            this.applicationDensity = (int) ((DisplayMetrics.DENSITY_DEVICE_STABLE * f3) + 0.5f);
            this.mCompatibilityFlags = 36;
            return;
        }
        if (applicationInfo.requiresSmallestWidthDp != 0 || applicationInfo.compatibleWidthLimitDp != 0 || applicationInfo.largestWidthLimitDp != 0) {
            if (applicationInfo.requiresSmallestWidthDp != 0) {
                i3 = applicationInfo.requiresSmallestWidthDp;
            } else {
                i3 = applicationInfo.compatibleWidthLimitDp;
            }
            i3 = i3 == 0 ? applicationInfo.largestWidthLimitDp : i3;
            int i7 = applicationInfo.compatibleWidthLimitDp != 0 ? applicationInfo.compatibleWidthLimitDp : i3;
            i7 = i7 < i3 ? i3 : i7;
            int i8 = applicationInfo.largestWidthLimitDp;
            if (i3 <= 320) {
                if (i8 != 0 && i2 > i8) {
                    i6 |= 10;
                } else if (i7 >= i2) {
                    i6 |= 4;
                } else if (z) {
                    i6 |= 8;
                }
                this.applicationDensity = DisplayMetrics.DENSITY_DEVICE;
                this.applicationScale = 1.0f;
                this.applicationInvertedScale = 1.0f;
                this.applicationDensityScale = 1.0f;
                this.applicationDensityInvertedScale = 1.0f;
                i4 = i6;
            }
        } else {
            boolean z3 = true;
            if ((applicationInfo.flags & 2048) == 0) {
                z2 = false;
            } else if (z) {
                z2 = true;
                i5 = 8;
            } else {
                i5 = 42;
                z2 = true;
            }
            if ((applicationInfo.flags & 524288) != 0) {
                i5 = z ? i5 : i5 | 34;
                z2 = true;
            }
            if ((applicationInfo.flags & 4096) != 0) {
                i5 |= 2;
            } else {
                z3 = z2;
            }
            i5 = z ? i5 & (-3) : i5;
            i4 = i6 | 8;
            int i9 = i & 15;
            if (i9 == 3) {
                i4 = (i5 & 8) != 0 ? i4 & (-9) : i4;
                if ((applicationInfo.flags & 2048) != 0) {
                }
                if ((i & 268435456) != 0) {
                }
                if ((applicationInfo.flags & 8192) == 0) {
                }
            } else {
                if (i9 == 4) {
                    i4 = (i5 & 32) != 0 ? i4 & (-9) : i4;
                    if ((applicationInfo.flags & 524288) != 0) {
                        i4 |= 4;
                    }
                }
                if ((i & 268435456) != 0) {
                    i4 = (i4 & (-9)) | 4;
                } else if ((i5 & 2) != 0) {
                    i4 &= -9;
                } else if (!z3) {
                    i4 |= 2;
                }
                if ((applicationInfo.flags & 8192) == 0) {
                    this.applicationDensity = DisplayMetrics.DENSITY_DEVICE;
                    this.applicationScale = 1.0f;
                    this.applicationInvertedScale = 1.0f;
                    this.applicationDensityScale = 1.0f;
                    this.applicationDensityInvertedScale = 1.0f;
                } else {
                    this.applicationDensity = 160;
                    float f4 = DisplayMetrics.DENSITY_DEVICE / 160.0f;
                    this.applicationScale = f4;
                    this.applicationInvertedScale = 1.0f / f4;
                    float f5 = DisplayMetrics.DENSITY_DEVICE / 160.0f;
                    this.applicationDensityScale = f5;
                    this.applicationDensityInvertedScale = 1.0f / f5;
                    i4 |= 1;
                }
            }
        }
        this.mCompatibilityFlags = i4;
    }

    private CompatibilityInfo(int i, int i2, float f, float f2) {
        this.applicationDisplayRotation = -1;
        this.mCompatibilityFlags = i;
        this.applicationDensity = i2;
        this.applicationScale = f;
        this.applicationInvertedScale = f2;
        float f3 = DisplayMetrics.DENSITY_DEVICE_STABLE / i2;
        this.applicationDensityScale = f3;
        this.applicationDensityInvertedScale = 1.0f / f3;
    }

    private CompatibilityInfo() {
        this(4, DisplayMetrics.DENSITY_DEVICE, 1.0f, 1.0f);
    }

    public boolean isScalingRequired() {
        return (this.mCompatibilityFlags & 1) != 0;
    }

    public boolean hasOverrideScaling() {
        return (this.mCompatibilityFlags & 32) != 0;
    }

    public boolean isOverrideDisplayRotationRequired() {
        return this.applicationDisplayRotation != -1;
    }

    public boolean supportsScreen() {
        return (this.mCompatibilityFlags & 8) == 0;
    }

    public boolean neverSupportsScreen() {
        return (this.mCompatibilityFlags & 2) != 0;
    }

    public boolean alwaysSupportsScreen() {
        return (this.mCompatibilityFlags & 4) != 0;
    }

    public boolean needsCompatResources() {
        return (this.mCompatibilityFlags & 16) != 0;
    }

    public Translator getTranslator() {
        if ((this.mCompatibilityFlags & 1) != 0) {
            return new Translator(this);
        }
        return null;
    }

    public class Translator {
        public final float applicationInvertedScale;
        public final float applicationScale;
        private Rect mContentInsetsBuffer;
        private Region mTouchableAreaBuffer;
        private Rect mVisibleInsetsBuffer;

        Translator(CompatibilityInfo compatibilityInfo, float f, float f2) {
            this.mContentInsetsBuffer = null;
            this.mVisibleInsetsBuffer = null;
            this.mTouchableAreaBuffer = null;
            this.applicationScale = f;
            this.applicationInvertedScale = f2;
        }

        Translator(CompatibilityInfo compatibilityInfo) {
            this(compatibilityInfo, compatibilityInfo.applicationScale, compatibilityInfo.applicationInvertedScale);
        }

        public void translateRegionInWindowToScreen(Region region) {
            region.scale(this.applicationScale);
        }

        public void translateCanvas(Canvas canvas) {
            if (this.applicationScale == 1.5f) {
                canvas.translate(0.0026143792f, 0.0026143792f);
            }
            float f = this.applicationScale;
            canvas.scale(f, f);
        }

        public void translateEventInScreenToAppWindow(MotionEvent motionEvent) {
            motionEvent.scale(this.applicationInvertedScale);
        }

        public void translateWindowLayout(WindowManager.LayoutParams layoutParams) {
            layoutParams.scale(this.applicationScale);
        }

        public float translateLengthInAppWindowToScreen(float f) {
            return f * this.applicationScale;
        }

        public void translateRectInAppWindowToScreen(Rect rect) {
            rect.scale(this.applicationScale);
        }

        public void translateRectInScreenToAppWindow(Rect rect) {
            if (rect == null) {
                return;
            }
            rect.scale(this.applicationInvertedScale);
        }

        public void translateInsetsStateInScreenToAppWindow(InsetsState insetsState) {
            insetsState.scale(this.applicationInvertedScale);
        }

        public void translateSourceControlsInScreenToAppWindow(InsetsSourceControl[] insetsSourceControlArr) {
            if (insetsSourceControlArr == null) {
                return;
            }
            float f = this.applicationInvertedScale;
            if (f == 1.0f) {
                return;
            }
            for (InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
                if (insetsSourceControl != null) {
                    Insets insetsHint = insetsSourceControl.getInsetsHint();
                    insetsSourceControl.setInsetsHint((int) (insetsHint.left * f), (int) (insetsHint.top * f), (int) (insetsHint.right * f), (int) (insetsHint.bottom * f));
                }
            }
        }

        public void translatePointInScreenToAppWindow(PointF pointF) {
            float f = this.applicationInvertedScale;
            if (f != 1.0f) {
                pointF.x *= f;
                pointF.y *= f;
            }
        }

        public void translateLayoutParamsInAppWindowToScreen(WindowManager.LayoutParams layoutParams) {
            layoutParams.scale(this.applicationScale);
        }

        public Rect getTranslatedContentInsets(Rect rect) {
            if (this.mContentInsetsBuffer == null) {
                this.mContentInsetsBuffer = new Rect();
            }
            this.mContentInsetsBuffer.set(rect);
            translateRectInAppWindowToScreen(this.mContentInsetsBuffer);
            return this.mContentInsetsBuffer;
        }

        public Rect getTranslatedVisibleInsets(Rect rect) {
            if (this.mVisibleInsetsBuffer == null) {
                this.mVisibleInsetsBuffer = new Rect();
            }
            this.mVisibleInsetsBuffer.set(rect);
            translateRectInAppWindowToScreen(this.mVisibleInsetsBuffer);
            return this.mVisibleInsetsBuffer;
        }

        public Region getTranslatedTouchableArea(Region region) {
            if (this.mTouchableAreaBuffer == null) {
                this.mTouchableAreaBuffer = new Region();
            }
            this.mTouchableAreaBuffer.set(region);
            this.mTouchableAreaBuffer.scale(this.applicationScale);
            return this.mTouchableAreaBuffer;
        }
    }

    public void applyDisplayMetricsIfNeeded(DisplayMetrics displayMetrics, boolean z) {
        if (hasOverrideScale()) {
            scaleDisplayMetrics(sOverrideInvertedScale, sOverrideDensityInvertScale, displayMetrics, z);
        } else {
            if (equals(DEFAULT_COMPATIBILITY_INFO)) {
                return;
            }
            applyToDisplayMetrics(displayMetrics);
        }
    }

    public void applyToDisplayMetrics(DisplayMetrics displayMetrics) {
        if (hasOverrideScale()) {
            return;
        }
        if (!supportsScreen()) {
            computeCompatibleScaling(displayMetrics, displayMetrics);
        } else {
            displayMetrics.widthPixels = displayMetrics.noncompatWidthPixels;
            displayMetrics.heightPixels = displayMetrics.noncompatHeightPixels;
        }
        if (isScalingRequired()) {
            scaleDisplayMetrics(this.applicationInvertedScale, this.applicationDensityInvertedScale, displayMetrics, true);
        }
    }

    private static void scaleDisplayMetrics(float f, float f2, DisplayMetrics displayMetrics, boolean z) {
        displayMetrics.density = displayMetrics.noncompatDensity * f2;
        displayMetrics.densityDpi = (int) ((displayMetrics.noncompatDensityDpi * f2) + 0.5f);
        displayMetrics.scaledDensity = displayMetrics.noncompatScaledDensity * f2;
        displayMetrics.xdpi = displayMetrics.noncompatXdpi * f2;
        displayMetrics.ydpi = displayMetrics.noncompatYdpi * f2;
        if (z) {
            displayMetrics.widthPixels = (int) ((displayMetrics.widthPixels * f) + 0.5f);
            displayMetrics.heightPixels = (int) ((displayMetrics.heightPixels * f) + 0.5f);
        }
    }

    public void applyToConfiguration(int i, Configuration configuration) {
        if (hasOverrideDisplayRotation()) {
            applyDisplayRotationConfiguration(sOverrideDisplayRotation, configuration);
        }
        if (hasOverrideScale()) {
            return;
        }
        if (!supportsScreen()) {
            configuration.screenLayout = (configuration.screenLayout & (-16)) | 2;
            configuration.screenWidthDp = configuration.compatScreenWidthDp;
            configuration.screenHeightDp = configuration.compatScreenHeightDp;
            configuration.smallestScreenWidthDp = configuration.compatSmallestScreenWidthDp;
        }
        configuration.densityDpi = i;
        if (isScalingRequired()) {
            scaleConfiguration(this.applicationInvertedScale, this.applicationDensityInvertedScale, configuration);
        }
    }

    public static void scaleConfiguration(float f, Configuration configuration) {
        scaleConfiguration(f, f, configuration);
    }

    public static void scaleConfiguration(float f, float f2, Configuration configuration) {
        configuration.densityDpi = (int) ((configuration.densityDpi * f2) + 0.5f);
        configuration.windowConfiguration.scale(f);
    }

    public static void applyDisplayRotationConfiguration(int i, Configuration configuration) {
        if (i != -1) {
            configuration.windowConfiguration.setDisplayRotation(i);
        }
    }

    public static void applyOverrideIfNeeded(Configuration configuration) {
        if (hasOverrideDisplayRotation()) {
            applyDisplayRotationConfiguration(sOverrideDisplayRotation, configuration);
        }
        if (hasOverrideScale()) {
            scaleConfiguration(sOverrideInvertedScale, sOverrideDensityInvertScale, configuration);
        }
    }

    public static void applyOverrideIfNeeded(MergedConfiguration mergedConfiguration) {
        if (hasOverrideDisplayRotation()) {
            applyDisplayRotationConfiguration(sOverrideDisplayRotation, mergedConfiguration.getGlobalConfiguration());
            applyDisplayRotationConfiguration(sOverrideDisplayRotation, mergedConfiguration.getOverrideConfiguration());
            applyDisplayRotationConfiguration(sOverrideDisplayRotation, mergedConfiguration.getMergedConfiguration());
        }
        if (hasOverrideScale()) {
            scaleConfiguration(sOverrideInvertedScale, sOverrideDensityInvertScale, mergedConfiguration.getGlobalConfiguration());
            scaleConfiguration(sOverrideInvertedScale, sOverrideDensityInvertScale, mergedConfiguration.getOverrideConfiguration());
            scaleConfiguration(sOverrideInvertedScale, sOverrideDensityInvertScale, mergedConfiguration.getMergedConfiguration());
        }
    }

    private static boolean hasOverrideScale() {
        return (sOverrideInvertedScale == 1.0f && sOverrideDensityInvertScale == 1.0f) ? false : true;
    }

    public static void setOverrideInvertedScale(float f) {
        setOverrideInvertedScale(f, f);
    }

    public static void setOverrideInvertedScale(float f, float f2) {
        sOverrideInvertedScale = f;
        sOverrideDensityInvertScale = f2;
    }

    public static float getOverrideInvertedScale() {
        return sOverrideInvertedScale;
    }

    public static float getOverrideDensityInvertedScale() {
        return sOverrideDensityInvertScale;
    }

    private static boolean hasOverrideDisplayRotation() {
        return sOverrideDisplayRotation != -1;
    }

    public static void setOverrideDisplayRotation(int i) {
        sOverrideDisplayRotation = i;
    }

    public static int getOverrideDisplayRotation() {
        return sOverrideDisplayRotation;
    }

    public static float computeCompatibleScaling(DisplayMetrics displayMetrics, DisplayMetrics displayMetrics2) {
        int i;
        int i2;
        int i3 = displayMetrics.noncompatWidthPixels;
        int i4 = displayMetrics.noncompatHeightPixels;
        if (i3 < i4) {
            i2 = i3;
            i = i4;
        } else {
            i = i3;
            i2 = i4;
        }
        int i5 = (int) ((displayMetrics.density * 320.0f) + 0.5f);
        float f = i / i2;
        if (f > 1.7791667f) {
            f = 1.7791667f;
        }
        int i6 = (int) ((i5 * f) + 0.5f);
        if (i3 >= i4) {
            i6 = i5;
            i5 = i6;
        }
        float f2 = i3 / i5;
        float f3 = i4 / i6;
        if (f2 >= f3) {
            f2 = f3;
        }
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        if (displayMetrics2 != null) {
            displayMetrics2.widthPixels = i5;
            displayMetrics2.heightPixels = i6;
        }
        return f2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompatibilityInfo)) {
            return false;
        }
        CompatibilityInfo compatibilityInfo = (CompatibilityInfo) obj;
        return isCompatibilityFlagsEqual(compatibilityInfo) && isScaleEqual(compatibilityInfo) && isDisplayRotationEqual(compatibilityInfo);
    }

    public int getCompatibilityChangesForConfig(CompatibilityInfo compatibilityInfo) {
        int i = !isDisplayRotationEqual(compatibilityInfo) ? 536870912 : 0;
        return (isScaleEqual(compatibilityInfo) && isCompatibilityFlagsEqual(compatibilityInfo)) ? i : i | 3328;
    }

    private boolean isScaleEqual(CompatibilityInfo compatibilityInfo) {
        return compatibilityInfo != null && this.applicationDensity == compatibilityInfo.applicationDensity && this.applicationScale == compatibilityInfo.applicationScale && this.applicationInvertedScale == compatibilityInfo.applicationInvertedScale && this.applicationDensityScale == compatibilityInfo.applicationDensityScale && this.applicationDensityInvertedScale == compatibilityInfo.applicationDensityInvertedScale;
    }

    private boolean isDisplayRotationEqual(CompatibilityInfo compatibilityInfo) {
        return compatibilityInfo != null && compatibilityInfo.applicationDisplayRotation == this.applicationDisplayRotation;
    }

    private boolean isCompatibilityFlagsEqual(CompatibilityInfo compatibilityInfo) {
        return compatibilityInfo != null && compatibilityInfo.mCompatibilityFlags == this.mCompatibilityFlags;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("{");
        sb.append(this.applicationDensity);
        sb.append("dpi");
        if (isScalingRequired()) {
            sb.append(" ");
            sb.append(this.applicationScale);
            sb.append("x");
        }
        if (hasOverrideScaling()) {
            sb.append(" overrideInvScale=");
            sb.append(this.applicationInvertedScale);
            sb.append(" overrideDensityInvScale=");
            sb.append(this.applicationDensityInvertedScale);
        }
        if (isOverrideDisplayRotationRequired()) {
            sb.append(" overrideDisplayRotation=");
            sb.append(this.applicationDisplayRotation);
        }
        if (!supportsScreen()) {
            sb.append(" resizing");
        }
        if (neverSupportsScreen()) {
            sb.append(" never-compat");
        }
        if (alwaysSupportsScreen()) {
            sb.append(" always-compat");
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return ((((((((((((527 + this.mCompatibilityFlags) * 31) + this.applicationDensity) * 31) + Float.floatToIntBits(this.applicationScale)) * 31) + Float.floatToIntBits(this.applicationInvertedScale)) * 31) + Float.floatToIntBits(this.applicationDensityScale)) * 31) + Float.floatToIntBits(this.applicationDensityInvertedScale)) * 31) + this.applicationDisplayRotation;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCompatibilityFlags);
        parcel.writeInt(this.applicationDensity);
        parcel.writeFloat(this.applicationScale);
        parcel.writeFloat(this.applicationInvertedScale);
        parcel.writeFloat(this.applicationDensityScale);
        parcel.writeFloat(this.applicationDensityInvertedScale);
        parcel.writeInt(this.applicationDisplayRotation);
    }

    private CompatibilityInfo(Parcel parcel) {
        this.applicationDisplayRotation = -1;
        this.mCompatibilityFlags = parcel.readInt();
        this.applicationDensity = parcel.readInt();
        this.applicationScale = parcel.readFloat();
        this.applicationInvertedScale = parcel.readFloat();
        this.applicationDensityScale = parcel.readFloat();
        this.applicationDensityInvertedScale = parcel.readFloat();
        this.applicationDisplayRotation = parcel.readInt();
    }

    public static final class CompatScale {
        public final float mDensityScaleFactor;
        public final float mScaleFactor;

        public CompatScale(float f) {
            this(f, f);
        }

        public CompatScale(float f, float f2) {
            this.mScaleFactor = f;
            this.mDensityScaleFactor = f2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CompatScale)) {
                return false;
            }
            try {
                CompatScale compatScale = (CompatScale) obj;
                if (this.mScaleFactor != compatScale.mScaleFactor) {
                    return false;
                }
                return this.mDensityScaleFactor == compatScale.mDensityScaleFactor;
            } catch (ClassCastException unused) {
                return false;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("mScaleFactor= ");
            sb.append(this.mScaleFactor);
            sb.append(" mDensityScaleFactor= ");
            sb.append(this.mDensityScaleFactor);
            return sb.toString();
        }

        public int hashCode() {
            return ((527 + Float.floatToIntBits(this.mScaleFactor)) * 31) + Float.floatToIntBits(this.mDensityScaleFactor);
        }
    }
}
