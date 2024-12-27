package com.android.server.display;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.BrightnessCorrection;
import android.provider.Settings;
import android.util.LongArray;
import android.util.MathUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.Spline;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.util.Preconditions;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.display.config.DisplayBrightnessMappingConfig;
import com.android.server.display.config.EvenDimmerBrightnessData;
import com.android.server.display.utils.Plog$SystemPlog;
import com.android.server.power.PowerManagerUtil;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class BrightnessMappingStrategy {
    public static final Plog$SystemPlog PLOG = new Plog$SystemPlog();
    public static int sScreenExtendedBrightnessRangeMaximum;
    public boolean mLoggingEnabled;
    public final UserOffsetManager mUserOffsetManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class PhysicalMappingStrategy extends BrightnessMappingStrategy {
        public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        public final Spline mAdjustedNitsToBrightnessSpline;
        public float mAutoBrightnessAdjustment;
        public Spline mBrightnessSpline;
        public final Spline mBrightnessToAdjustedNitsSpline;
        public final Spline mBrightnessToNitsSpline;
        public BrightnessConfiguration mConfig;
        public final BrightnessConfiguration mDefaultConfig;
        public final float mMaxGamma;
        public final int mMode;
        public final Spline mNitsToBrightnessSpline;
        public final List mPreviousBrightnessSplines = new ArrayList();
        public final LongArray mBrightnessSplineChangeTimes = new LongArray();

        public PhysicalMappingStrategy(
                BrightnessConfiguration brightnessConfiguration,
                float[] fArr,
                float[] fArr2,
                float f,
                int i) {
            Preconditions.checkArgument(
                    (fArr.length == 0 || fArr2.length == 0) ? false : true,
                    "Nits and brightness arrays must not be empty!");
            Preconditions.checkArgument(
                    fArr.length == fArr2.length,
                    "Nits and brightness arrays must be the same length!");
            Objects.requireNonNull(brightnessConfiguration);
            Preconditions.checkArrayElementsInRange(
                    fArr, FullScreenMagnificationGestureHandler.MAX_SCALE, Float.MAX_VALUE, "nits");
            Preconditions.checkArrayElementsInRange(
                    fArr2,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    BrightnessMappingStrategy.sScreenExtendedBrightnessRangeMaximum / 255.0f,
                    "brightness");
            this.mMode = i;
            this.mMaxGamma = f;
            this.mAutoBrightnessAdjustment = FullScreenMagnificationGestureHandler.MAX_SCALE;
            this.mNitsToBrightnessSpline = Spline.createSpline(fArr, fArr2);
            Spline createSpline = Spline.createSpline(fArr2, fArr);
            this.mBrightnessToNitsSpline = createSpline;
            this.mAdjustedNitsToBrightnessSpline = this.mNitsToBrightnessSpline;
            this.mBrightnessToAdjustedNitsSpline = createSpline;
            Spline.createSpline(fArr, fArr2);
            Spline.createSpline(fArr2, fArr);
            this.mDefaultConfig = brightnessConfiguration;
            if (this.mLoggingEnabled) {
                BrightnessMappingStrategy.PLOG.start("physical mapping strategy");
            }
            this.mConfig = brightnessConfiguration;
            computeSpline();
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final void addUserDataPoint(float f, float f2) {
            if (!(Float.compare(f, FullScreenMagnificationGestureHandler.MAX_SCALE) >= 0
                    && (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                            || Float.compare(f, (float) PowerManagerUtil.HBM_LUX) < 0))) {
                Slog.e("BrightnessMappingStrategy", "addUserDataPoint: invalid user lux: " + f);
                return;
            }
            Pair curve = this.mConfig.getCurve();
            float interpolate =
                    this.mAdjustedNitsToBrightnessSpline.interpolate(
                            Spline.createSpline((float[]) curve.first, (float[]) curve.second)
                                    .interpolate(f));
            if (this.mLoggingEnabled) {
                Slog.d("BrightnessMappingStrategy", "addUserDataPoint: (" + f + "," + f2 + ")");
                Plog$SystemPlog plog$SystemPlog = BrightnessMappingStrategy.PLOG;
                plog$SystemPlog.start("add user data point");
                plog$SystemPlog.logPoint("user data point", f, f2);
                plog$SystemPlog.logPoint("current brightness", f, interpolate);
            }
            float inferAutoBrightnessAdjustment =
                    inferAutoBrightnessAdjustment(this.mMaxGamma, f2, interpolate);
            if (this.mLoggingEnabled) {
                Slog.d(
                        "BrightnessMappingStrategy",
                        "addUserDataPoint: "
                                + this.mAutoBrightnessAdjustment
                                + " => "
                                + inferAutoBrightnessAdjustment);
            }
            this.mAutoBrightnessAdjustment = inferAutoBrightnessAdjustment;
            this.mUserOffsetManager.addPoint(f, f2, interpolate);
            computeSpline();
            if (((ArrayList) this.mPreviousBrightnessSplines).size() == 5) {
                ((ArrayList) this.mPreviousBrightnessSplines).remove(0);
                this.mBrightnessSplineChangeTimes.remove(0);
            }
            ((ArrayList) this.mPreviousBrightnessSplines).add(this.mBrightnessSpline);
            this.mBrightnessSplineChangeTimes.add(System.currentTimeMillis());
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final void clearUserDataPoints() {
            UserOffsetManager userOffsetManager = this.mUserOffsetManager;
            if (userOffsetManager.hasUserDataPoints()) {
                if (this.mLoggingEnabled) {
                    Slog.d(
                            "BrightnessMappingStrategy",
                            "clearUserDataPoints: " + this.mAutoBrightnessAdjustment + " => 0");
                    BrightnessMappingStrategy.PLOG.start("clear user data points");
                }
                this.mAutoBrightnessAdjustment = FullScreenMagnificationGestureHandler.MAX_SCALE;
                userOffsetManager.clear();
                computeSpline();
            }
        }

        public final void computeSpline() {
            Pair curve = this.mConfig.getCurve();
            float[] fArr = (float[]) curve.first;
            float[] fArr2 = (float[]) curve.second;
            int length = fArr2.length;
            float[] fArr3 = new float[length];
            for (int i = 0; i < length; i++) {
                fArr3[i] = this.mAdjustedNitsToBrightnessSpline.interpolate(fArr2[i]);
            }
            if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM && !isDefaultConfig()) {
                Pair curve2 = this.mDefaultConfig.getCurve();
                float[] fArr4 = (float[]) curve2.first;
                float[] fArr5 = (float[]) curve2.second;
                int i2 = 0;
                for (float f : fArr) {
                    if (f < PowerManagerUtil.HBM_LUX) {
                        i2++;
                    }
                }
                int i3 = 0;
                for (float f2 : fArr4) {
                    if (f2 >= PowerManagerUtil.HBM_LUX) {
                        i3++;
                    }
                }
                int i4 = i2 + i3;
                float[] fArr6 = new float[i4];
                float[] fArr7 = new float[i4];
                System.arraycopy(fArr, 0, fArr6, 0, i2);
                System.arraycopy(fArr2, 0, fArr7, 0, i2);
                System.arraycopy(fArr4, fArr4.length - i3, fArr6, i2, i3);
                System.arraycopy(fArr5, fArr5.length - i3, fArr7, i2, i3);
                float[] fArr8 = new float[i4];
                for (int i5 = 0; i5 < i4; i5++) {
                    fArr8[i5] = this.mNitsToBrightnessSpline.interpolate(fArr7[i5]);
                }
                fArr = (float[]) fArr6.clone();
                fArr3 = (float[]) fArr8.clone();
            }
            Pair offsetAdjustedCurve = getOffsetAdjustedCurve(fArr, fArr3);
            float[] fArr9 = (float[]) offsetAdjustedCurve.first;
            float[] fArr10 = (float[]) offsetAdjustedCurve.second;
            int length2 = fArr10.length;
            float[] fArr11 = new float[length2];
            for (int i6 = 0; i6 < length2; i6++) {
                fArr11[i6] = this.mBrightnessToAdjustedNitsSpline.interpolate(fArr10[i6]);
            }
            this.mBrightnessSpline = Spline.createSpline(fArr9, fArr11);
            Slog.d(
                    "BrightnessMappingStrategy",
                    "computeSpline: mBrightnessSpline: " + this.mBrightnessSpline);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float convertToAdjustedNits(float f) {
            return this.mBrightnessToAdjustedNitsSpline.interpolate(f);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final int convertToBrightness(float f) {
            return MathUtils.constrain(
                    BrightnessSynchronizer.brightnessFloatToInt(
                            this.mNitsToBrightnessSpline.interpolate(f)),
                    0,
                    BrightnessMappingStrategy.sScreenExtendedBrightnessRangeMaximum);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float convertToNits(float f) {
            return this.mBrightnessToNitsSpline.interpolate(f);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final void dump(PrintWriter printWriter) {
            StringBuilder m$1 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            printWriter, "PhysicalMappingStrategy", "  mConfig=");
            m$1.append(this.mConfig);
            printWriter.println(m$1.toString());
            printWriter.println("  mBrightnessSpline=" + this.mBrightnessSpline);
            AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                    KillPolicyManager$$ExternalSyntheticOutline0.m(
                            new StringBuilder("  mMaxGamma="),
                            this.mMaxGamma,
                            printWriter,
                            "  mAutoBrightnessAdjustment="),
                    this.mAutoBrightnessAdjustment,
                    printWriter);
            this.mUserOffsetManager.dump(printWriter);
            printWriter.println("  mDefaultConfig=" + this.mDefaultConfig);
            printWriter.println("  mBrightnessRangeAdjustmentApplied=false");
            printWriter.println("  shortTermModelTimeout=" + getShortTermModelTimeout());
            printWriter.println("  Previous short-term models (oldest to newest): ");
            for (int i = 0; i < ((ArrayList) this.mPreviousBrightnessSplines).size(); i++) {
                printWriter.println(
                        "  Computed at "
                                + FORMAT.format(new Date(this.mBrightnessSplineChangeTimes.get(i)))
                                + ": ");
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            printWriter,
                            "  Difference between current config and default: ",
                            "  SEC_FEATURE_SUPPORT_LEGACY_PERFORMANCE_MODE="),
                    PowerManagerUtil.SEC_FEATURE_SUPPORT_LEGACY_PERFORMANCE_MODE,
                    printWriter);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final BrightnessConfiguration getAppliedBackupConfig(
                BrightnessConfiguration brightnessConfiguration) {
            Pair curve = this.mConfig.getCurve();
            float[] fArr = (float[]) curve.first;
            Object obj = curve.second;
            float[] copyOf = Arrays.copyOf((float[]) obj, ((float[]) obj).length);
            Pair curve2 = brightnessConfiguration.getCurve();
            float[] fArr2 = (float[]) curve2.first;
            float[] fArr3 = (float[]) curve2.second;
            Spline createSpline = Spline.createSpline(fArr2, fArr3);
            if (Float.compare(fArr[0], FullScreenMagnificationGestureHandler.MAX_SCALE) == 0
                    && Float.compare(fArr2[0], FullScreenMagnificationGestureHandler.MAX_SCALE) == 0
                    && Float.compare(copyOf[0], fArr3[0]) > 0) {
                Slog.d(
                        "BrightnessMappingStrategy",
                        "default brightness is higher than backup brightness");
                return null;
            }
            for (int i = 0; Float.compare(fArr[i], 30.0f) < 0; i++) {
                copyOf[i] = createSpline.interpolate(fArr[i]);
            }
            for (int i2 = 1; i2 < fArr.length; i2++) {
                if (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                        || Float.compare(fArr[i2], PowerManagerUtil.HBM_LUX) < 0) {
                    int i3 = i2 - 1;
                    if (Float.compare(copyOf[i3], copyOf[i2]) > 0) {
                        copyOf[i2] = copyOf[i3];
                    }
                }
            }
            BrightnessConfiguration.Builder builder =
                    new BrightnessConfiguration.Builder(fArr, copyOf);
            builder.setDescription(brightnessConfiguration.getDescription());
            return builder.build();
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float getAutoBrightnessAdjustment() {
            return this.mAutoBrightnessAdjustment;
        }

        public final float getBrightness(float f, String str, int i, Spline spline) {
            BrightnessCorrection correctionByCategory;
            BrightnessCorrection correctionByPackageName;
            float interpolate =
                    this.mAdjustedNitsToBrightnessSpline.interpolate(spline.interpolate(f));
            if (this.mUserOffsetManager.hasUserDataPoints()) {
                if (this.mLoggingEnabled) {
                    Slog.d("BrightnessMappingStrategy", "user point set, correction not applied");
                }
            } else if (str != null
                    && (correctionByPackageName = this.mConfig.getCorrectionByPackageName(str))
                            != null) {
                interpolate = correctionByPackageName.apply(interpolate);
            } else if (i != -1
                    && (correctionByCategory = this.mConfig.getCorrectionByCategory(i)) != null) {
                interpolate = correctionByCategory.apply(interpolate);
            }
            if (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                    || Float.compare(interpolate, 1.0f) <= 0
                    || Float.compare(f, PowerManagerUtil.HBM_LUX) >= 0) {
                return interpolate;
            }
            return 1.0f;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float getBrightness(String str, float f, int i) {
            return getBrightness(f, str, i, this.mBrightnessSpline);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final BrightnessConfiguration getBrightnessConfiguration() {
            return this.mConfig;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float getBrightnessForSpline(float f, Spline spline) {
            if (spline != null) {
                return getBrightness(f, null, -1, spline);
            }
            Slog.e("BrightnessMappingStrategy", "getBrightnessForSpline: null");
            return getBrightness(f, null, -1, this.mBrightnessSpline);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float getBrightnessFromNits(float f) {
            return this.mNitsToBrightnessSpline.interpolate(f);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final Spline getBrightnessSpline() {
            return this.mBrightnessSpline;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final BrightnessConfiguration getDefaultConfig() {
            return this.mDefaultConfig;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final int getMode() {
            return this.mMode;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final long getShortTermModelTimeout() {
            return this.mConfig.getShortTermModelTimeoutMillis() >= 0
                    ? this.mConfig.getShortTermModelTimeoutMillis()
                    : this.mDefaultConfig.getShortTermModelTimeoutMillis();
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final boolean hasUserDataPoints() {
            return this.mUserOffsetManager.hasUserDataPoints();
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final boolean isDefaultConfig() {
            return this.mDefaultConfig.equals(this.mConfig);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final boolean setAutoBrightnessAdjustment(float f) {
            Slog.d("BrightnessMappingStrategy", "setAutoBrightnessAdjustment: " + f);
            float constrain = MathUtils.constrain(f, -1.0f, 1.0f);
            if (constrain == this.mAutoBrightnessAdjustment) {
                return false;
            }
            if (this.mLoggingEnabled) {
                Slog.d(
                        "BrightnessMappingStrategy",
                        "setAutoBrightnessAdjustment: "
                                + this.mAutoBrightnessAdjustment
                                + " => "
                                + constrain);
                BrightnessMappingStrategy.PLOG.start("auto-brightness adjustment");
            }
            this.mAutoBrightnessAdjustment = constrain;
            computeSpline();
            return true;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final boolean setBrightnessConfiguration(
                BrightnessConfiguration brightnessConfiguration) {
            if (brightnessConfiguration == null) {
                brightnessConfiguration = this.mDefaultConfig;
            }
            if (brightnessConfiguration.equals(this.mConfig)) {
                return false;
            }
            if (this.mLoggingEnabled) {
                BrightnessMappingStrategy.PLOG.start("brightness configuration");
            }
            this.mConfig = brightnessConfiguration;
            computeSpline();
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SimpleMappingStrategy extends BrightnessMappingStrategy {
        public float mAutoBrightnessAdjustment;
        public final float[] mBrightness;
        public final float[] mLux;
        public final float mMaxGamma;
        public final int mMode;
        public final long mShortTermModelTimeout;
        public Spline mSpline;

        public SimpleMappingStrategy(float[] fArr, float[] fArr2, float f, int i) {
            Preconditions.checkArgument(
                    (fArr.length == 0 || fArr2.length == 0) ? false : true,
                    "Lux and brightness arrays must not be empty!");
            Preconditions.checkArgument(
                    fArr.length == fArr2.length,
                    "Lux and brightness arrays must be the same length!");
            Preconditions.checkArrayElementsInRange(
                    fArr, FullScreenMagnificationGestureHandler.MAX_SCALE, Float.MAX_VALUE, "lux");
            Preconditions.checkArrayElementsInRange(
                    fArr2,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    2.14748365E9f,
                    "brightness");
            int length = fArr2.length;
            this.mLux = new float[length];
            this.mBrightness = new float[length];
            for (int i2 = 0; i2 < length; i2++) {
                this.mLux[i2] = fArr[i2];
                this.mBrightness[i2] = fArr2[i2];
            }
            this.mMaxGamma = f;
            this.mAutoBrightnessAdjustment = FullScreenMagnificationGestureHandler.MAX_SCALE;
            if (this.mLoggingEnabled) {
                BrightnessMappingStrategy.PLOG.start("simple mapping strategy");
            }
            computeSpline$1();
            this.mShortTermModelTimeout = 600000L;
            this.mMode = i;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final void addUserDataPoint(float f, float f2) {
            if (!(Float.compare(f, FullScreenMagnificationGestureHandler.MAX_SCALE) >= 0
                    && (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                            || Float.compare(f, (float) PowerManagerUtil.HBM_LUX) < 0))) {
                Slog.e("BrightnessMappingStrategy", "addUserDataPoint: invalid user lux: " + f);
                return;
            }
            float interpolate = Spline.createSpline(this.mLux, this.mBrightness).interpolate(f);
            if (this.mLoggingEnabled) {
                Slog.d("BrightnessMappingStrategy", "addUserDataPoint: (" + f + "," + f2 + ")");
                Plog$SystemPlog plog$SystemPlog = BrightnessMappingStrategy.PLOG;
                plog$SystemPlog.start("add user data point");
                plog$SystemPlog.logPoint("user data point", f, f2);
                plog$SystemPlog.logPoint("current brightness", f, interpolate);
            }
            float inferAutoBrightnessAdjustment =
                    inferAutoBrightnessAdjustment(this.mMaxGamma, f2, interpolate);
            if (this.mLoggingEnabled) {
                Slog.d(
                        "BrightnessMappingStrategy",
                        "addUserDataPoint: "
                                + this.mAutoBrightnessAdjustment
                                + " => "
                                + inferAutoBrightnessAdjustment);
            }
            this.mAutoBrightnessAdjustment = inferAutoBrightnessAdjustment;
            this.mUserOffsetManager.addPoint(f, f2, interpolate);
            computeSpline$1();
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final void clearUserDataPoints() {
            UserOffsetManager userOffsetManager = this.mUserOffsetManager;
            if (userOffsetManager.hasUserDataPoints()) {
                if (this.mLoggingEnabled) {
                    Slog.d(
                            "BrightnessMappingStrategy",
                            "clearUserDataPoints: " + this.mAutoBrightnessAdjustment + " => 0");
                    BrightnessMappingStrategy.PLOG.start("clear user data points");
                }
                this.mAutoBrightnessAdjustment = FullScreenMagnificationGestureHandler.MAX_SCALE;
                userOffsetManager.clear();
                computeSpline$1();
            }
        }

        public final void computeSpline$1() {
            Pair offsetAdjustedCurve = getOffsetAdjustedCurve(this.mLux, this.mBrightness);
            this.mSpline =
                    Spline.createSpline(
                            (float[]) offsetAdjustedCurve.first,
                            (float[]) offsetAdjustedCurve.second);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float convertToAdjustedNits(float f) {
            return -1.0f;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final int convertToBrightness(float f) {
            return -1;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float convertToNits(float f) {
            return -1.0f;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final void dump(PrintWriter printWriter) {
            StringBuilder m$1 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            printWriter, "SimpleMappingStrategy", "  mSpline=");
            m$1.append(this.mSpline);
            printWriter.println(m$1.toString());
            AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                    KillPolicyManager$$ExternalSyntheticOutline0.m(
                            new StringBuilder("  mMaxGamma="),
                            this.mMaxGamma,
                            printWriter,
                            "  mAutoBrightnessAdjustment="),
                    this.mAutoBrightnessAdjustment,
                    printWriter);
            this.mUserOffsetManager.dump(printWriter);
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final BrightnessConfiguration getAppliedBackupConfig(
                BrightnessConfiguration brightnessConfiguration) {
            return null;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float getAutoBrightnessAdjustment() {
            return this.mAutoBrightnessAdjustment;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float getBrightness(String str, float f, int i) {
            float interpolate = this.mSpline.interpolate(f);
            if (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                    || Float.compare(interpolate, 1.0f) <= 0
                    || Float.compare(f, PowerManagerUtil.HBM_LUX) >= 0) {
                return interpolate;
            }
            return 1.0f;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final BrightnessConfiguration getBrightnessConfiguration() {
            return null;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float getBrightnessForSpline(float f, Spline spline) {
            return -1.0f;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final float getBrightnessFromNits(float f) {
            return Float.NaN;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final Spline getBrightnessSpline() {
            return null;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final BrightnessConfiguration getDefaultConfig() {
            return null;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final int getMode() {
            return this.mMode;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final long getShortTermModelTimeout() {
            return this.mShortTermModelTimeout;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final boolean hasUserDataPoints() {
            return this.mUserOffsetManager.hasUserDataPoints();
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final boolean isDefaultConfig() {
            return true;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final boolean setAutoBrightnessAdjustment(float f) {
            float constrain = MathUtils.constrain(f, -1.0f, 1.0f);
            if (constrain == this.mAutoBrightnessAdjustment) {
                return false;
            }
            if (this.mLoggingEnabled) {
                Slog.d(
                        "BrightnessMappingStrategy",
                        "setAutoBrightnessAdjustment: "
                                + this.mAutoBrightnessAdjustment
                                + " => "
                                + constrain);
                BrightnessMappingStrategy.PLOG.start("auto-brightness adjustment");
            }
            this.mAutoBrightnessAdjustment = constrain;
            computeSpline$1();
            return true;
        }

        @Override // com.android.server.display.BrightnessMappingStrategy
        public final boolean setBrightnessConfiguration(
                BrightnessConfiguration brightnessConfiguration) {
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserOffsetManager {
        public static boolean sDebugLogging;
        public Pair mCurve;
        public float mLastAddedLux;
        public ArrayList mUserPoints;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class UserPoint implements Comparable {
            public final float mBrightness;
            public final float mBrightnessOffset;
            public final float mLowerBoundary;
            public final float mLux;
            public final float mUpperBoundary;

            public UserPoint(float f, float f2, float f3) {
                this.mLux = f;
                this.mBrightness = f2;
                this.mBrightnessOffset = f2 - f3;
                this.mLowerBoundary = Math.min(f / 2.5f, f - 10.0f);
                this.mUpperBoundary = Math.max(2.5f * f, f + 10.0f);
            }

            @Override // java.lang.Comparable
            public final int compareTo(Object obj) {
                return Float.compare(this.mLux, ((UserPoint) obj).mLux);
            }

            public final boolean isInSameBoundary(float f) {
                return this.mLowerBoundary <= f && f <= this.mUpperBoundary;
            }

            public final String toString() {
                return String.format(
                        "%5.1f -> %5.1f (%+6.1f) @ %6.1f < %6.1f < %6.1f",
                        Float.valueOf((this.mBrightness - this.mBrightnessOffset) * 255.0f),
                        Float.valueOf(this.mBrightness * 255.0f),
                        Float.valueOf(this.mBrightnessOffset * 255.0f),
                        Float.valueOf(this.mLowerBoundary),
                        Float.valueOf(this.mLux),
                        Float.valueOf(this.mUpperBoundary));
            }
        }

        public static float[] convertFloatArrayListToArray(ArrayList arrayList) {
            float[] fArr = new float[arrayList.size()];
            Iterator it = arrayList.iterator();
            int i = 0;
            while (it.hasNext()) {
                Float f = (Float) it.next();
                int i2 = i + 1;
                fArr[i] = f != null ? f.floatValue() : Float.NaN;
                i = i2;
            }
            return fArr;
        }

        public final void addPoint(float f, float f2, float f3) {
            synchronized (this) {
                try {
                    final UserPoint userPoint = new UserPoint(f, f2, f3);
                    this.mUserPoints.removeIf(
                            new Predicate() { // from class:
                                              // com.android.server.display.BrightnessMappingStrategy$UserOffsetManager$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return BrightnessMappingStrategy.UserOffsetManager.UserPoint
                                            .this
                                            .isInSameBoundary(
                                                    ((BrightnessMappingStrategy.UserOffsetManager
                                                                            .UserPoint)
                                                                    obj)
                                                            .mLux);
                                }
                            });
                    this.mUserPoints.add(userPoint);
                    this.mLastAddedLux = f;
                    for (int i = 0; i < this.mUserPoints.size(); i++) {
                        Slog.d(
                                "BrightnessMappingStrategy",
                                "addUserDataPoint: [" + i + "] " + this.mUserPoints.get(i));
                    }
                    updateCurve();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void clear() {
            synchronized (this) {
                try {
                    if (this.mCurve != null) {
                        Plog$SystemPlog plog$SystemPlog = BrightnessMappingStrategy.PLOG;
                        plog$SystemPlog.start("clear user offset curve");
                        Pair pair = this.mCurve;
                        plog$SystemPlog.logCurve(
                                "offset curve", (float[]) pair.first, (float[]) pair.second);
                    }
                    this.mCurve = null;
                    this.mUserPoints.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("UserOffsetManager:");
            if (this.mCurve != null) {
                StringBuilder sb = new StringBuilder("  ");
                Pair pair = this.mCurve;
                float[] fArr = (float[]) pair.first;
                float[] fArr2 = (float[]) pair.second;
                StringBuilder sb2 = new StringBuilder("curve: [");
                int min = Math.min(fArr.length, fArr2.length);
                for (int i = 0; i < min; i++) {
                    sb2.append("(" + fArr[i] + "," + fArr2[i] + "),");
                }
                sb2.append("]");
                sb.append(sb2.toString());
                printWriter.println(sb.toString());
            }
            for (int i2 = 0; i2 < this.mUserPoints.size(); i2++) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(i2, "  mUserPoints[", "] ");
                m.append(this.mUserPoints.get(i2));
                printWriter.println(m.toString());
            }
            printWriter.println();
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("  sDebugLogging: "), sDebugLogging, printWriter);
        }

        public final Pair getCurve() {
            Pair pair;
            synchronized (this) {
                try {
                    Pair pair2 = this.mCurve;
                    if (pair2 != null) {
                        Object obj = pair2.first;
                        float[] copyOf = Arrays.copyOf((float[]) obj, ((float[]) obj).length);
                        Object obj2 = this.mCurve.second;
                        pair =
                                Pair.create(
                                        copyOf,
                                        Arrays.copyOf((float[]) obj2, ((float[]) obj2).length));
                    } else {
                        pair = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return pair;
        }

        public final boolean hasUserDataPoints() {
            boolean z;
            synchronized (this) {
                z = !this.mUserPoints.isEmpty();
            }
            return z;
        }

        public final void updateCurve() {
            ArrayList arrayList = new ArrayList(this.mUserPoints);
            arrayList.sort(Comparator.naturalOrder());
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                UserPoint userPoint = (UserPoint) arrayList.get(i);
                if (i <= 0
                        || !((UserPoint) arrayList.get(i - 1))
                                .isInSameBoundary(userPoint.mLowerBoundary)) {
                    arrayList2.add(Float.valueOf(userPoint.mLowerBoundary));
                    arrayList3.add(Float.valueOf(FullScreenMagnificationGestureHandler.MAX_SCALE));
                }
                arrayList2.add(Float.valueOf(userPoint.mLux));
                arrayList3.add(Float.valueOf(userPoint.mBrightnessOffset));
                if (i >= size - 1
                        || !((UserPoint) arrayList.get(i + 1))
                                .isInSameBoundary(userPoint.mUpperBoundary)) {
                    arrayList2.add(Float.valueOf(userPoint.mUpperBoundary));
                    arrayList3.add(Float.valueOf(FullScreenMagnificationGestureHandler.MAX_SCALE));
                }
            }
            float[] convertFloatArrayListToArray = convertFloatArrayListToArray(arrayList2);
            float[] convertFloatArrayListToArray2 = convertFloatArrayListToArray(arrayList3);
            this.mCurve = Pair.create(convertFloatArrayListToArray, convertFloatArrayListToArray2);
            if (sDebugLogging) {
                BrightnessMappingStrategy.PLOG.logCurve(
                        "offset curve",
                        convertFloatArrayListToArray,
                        convertFloatArrayListToArray2);
            }
        }
    }

    public BrightnessMappingStrategy() {
        UserOffsetManager userOffsetManager = new UserOffsetManager();
        synchronized (userOffsetManager) {
            userOffsetManager.mCurve = null;
            userOffsetManager.mUserPoints = new ArrayList();
        }
        this.mUserOffsetManager = userOffsetManager;
    }

    public static BrightnessMappingStrategy create(
            Context context, DisplayDeviceConfig displayDeviceConfig, int i) {
        float[] autoBrightnessBrighteningLevelsLux;
        float[] autoBrightnessBrighteningLevels;
        float[] fArr;
        int intForUser =
                Settings.System.getIntForUser(
                        context.getContentResolver(), "screen_brightness_for_als", 2, -2);
        if (i == 0) {
            DisplayBrightnessMappingConfig displayBrightnessMappingConfig =
                    displayDeviceConfig.mDisplayBrightnessMapping;
            float[] fArr2 =
                    displayBrightnessMappingConfig == null
                            ? null
                            : displayBrightnessMappingConfig.mBrightnessLevelsNits;
            autoBrightnessBrighteningLevelsLux =
                    displayDeviceConfig.getAutoBrightnessBrighteningLevelsLux(i, intForUser);
            float[] fArr3 = fArr2;
            autoBrightnessBrighteningLevels =
                    displayDeviceConfig.getAutoBrightnessBrighteningLevels(i, intForUser);
            fArr = fArr3;
        } else if (i == 1) {
            fArr =
                    getFloatArray(
                            context.getResources()
                                    .obtainTypedArray(R.array.config_minimumBrightnessCurveNits));
            int[] intArray =
                    context.getResources().getIntArray(R.array.config_network_type_tcp_buffers);
            autoBrightnessBrighteningLevelsLux = new float[intArray.length + 1];
            int i2 = 0;
            while (i2 < intArray.length) {
                int i3 = i2 + 1;
                autoBrightnessBrighteningLevelsLux[i3] = intArray[i2];
                i2 = i3;
            }
            autoBrightnessBrighteningLevels = null;
        } else if (i != 2) {
            fArr = null;
            autoBrightnessBrighteningLevels = null;
            autoBrightnessBrighteningLevelsLux = null;
        } else {
            autoBrightnessBrighteningLevelsLux =
                    displayDeviceConfig.getAutoBrightnessBrighteningLevelsLux(i, intForUser);
            autoBrightnessBrighteningLevels =
                    displayDeviceConfig.getAutoBrightnessBrighteningLevels(i, intForUser);
            fArr = null;
        }
        float fraction =
                context.getResources()
                        .getFraction(R.fraction.config_autoBrightnessAdjustmentMaxGamma, 1, 1);
        EvenDimmerBrightnessData evenDimmerBrightnessData =
                displayDeviceConfig.mEvenDimmerBrightnessData;
        float[] fArr4 =
                evenDimmerBrightnessData != null
                        ? evenDimmerBrightnessData.mNits
                        : displayDeviceConfig.mNits;
        float[] fArr5 =
                evenDimmerBrightnessData != null
                        ? evenDimmerBrightnessData.mBrightness
                        : displayDeviceConfig.mBrightness;
        sScreenExtendedBrightnessRangeMaximum =
                context.getResources()
                        .getInteger(R.integer.config_vibratorControlServiceDumpSizeLimit);
        if (!isValidMapping(fArr4, fArr5)
                || !isValidMapping(autoBrightnessBrighteningLevelsLux, fArr)) {
            if (!isValidMapping(
                    autoBrightnessBrighteningLevelsLux, autoBrightnessBrighteningLevels)) {
                return null;
            }
            Slog.d("BrightnessMappingStrategy", "Use SimpleMappingStrategy");
            return new SimpleMappingStrategy(
                    autoBrightnessBrighteningLevelsLux,
                    autoBrightnessBrighteningLevels,
                    fraction,
                    i);
        }
        Slog.d("BrightnessMappingStrategy", "Use PhysicalMappingStrategy");
        if (PowerManagerUtil.SEC_FEATURE_SUPPORT_LEGACY_PERFORMANCE_MODE) {
            for (int i4 = 0;
                    i4 < fArr.length
                            && (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                                    || autoBrightnessBrighteningLevelsLux[i4]
                                            < PowerManagerUtil.HBM_LUX);
                    i4++) {
                fArr[i4] = fArr[i4] * 0.885f;
            }
        }
        BrightnessConfiguration.Builder builder =
                new BrightnessConfiguration.Builder(autoBrightnessBrighteningLevelsLux, fArr);
        builder.setShortTermModelTimeoutMillis(600000L);
        builder.setShortTermModelLowerLuxMultiplier(0.6f);
        builder.setShortTermModelUpperLuxMultiplier(0.6f);
        return new PhysicalMappingStrategy(builder.build(), fArr4, fArr5, fraction, i);
    }

    public static float[] getFloatArray(TypedArray typedArray) {
        int length = typedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = typedArray.getFloat(i, -1.0f);
        }
        typedArray.recycle();
        return fArr;
    }

    public static boolean isValidMapping(float[] fArr, float[] fArr2) {
        if (fArr == null || fArr2 == null || fArr.length == 0 || fArr2.length == 0) {
            StringBuilder sb = new StringBuilder("x: ");
            sb.append(fArr == null ? "null" : Integer.valueOf(fArr.length));
            sb.append(" y: ");
            sb.append(fArr2 != null ? Integer.valueOf(fArr2.length) : "null");
            Slog.d("BrightnessMappingStrategy", sb.toString());
            return false;
        }
        if (fArr.length != fArr2.length) {
            StringBuilder sb2 = new StringBuilder("x.length: ");
            sb2.append(fArr.length);
            sb2.append(" y.length: ");
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    sb2, fArr2.length, "BrightnessMappingStrategy");
            return false;
        }
        int length = fArr.length;
        float f = fArr[0];
        float f2 = fArr2[0];
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE
                || f2 < FullScreenMagnificationGestureHandler.MAX_SCALE
                || Float.isNaN(f)
                || Float.isNaN(f2)) {
            Slog.d("BrightnessMappingStrategy", "prevX: " + f + " prevY: " + f2);
            return false;
        }
        for (int i = 1; i < length; i++) {
            float f3 = fArr[i];
            if (f >= f3 || f2 > fArr2[i]) {
                Slog.d(
                        "BrightnessMappingStrategy",
                        "prevX: "
                                + f
                                + " x["
                                + i
                                + "]: "
                                + fArr[i]
                                + " prevY: "
                                + f2
                                + " y["
                                + i
                                + "]: "
                                + fArr2[i]);
                return false;
            }
            if (Float.isNaN(f3) || Float.isNaN(fArr2[i])) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "x[", "]: ");
                m.append(fArr[i]);
                m.append(" y[");
                m.append(i);
                m.append("]: ");
                m.append(fArr2[i]);
                Slog.d("BrightnessMappingStrategy", m.toString());
                return false;
            }
            f = fArr[i];
            f2 = fArr2[i];
        }
        return true;
    }

    public static float permissibleMinimumRatio(float f, float f2) {
        if (f >= 3.0f || f2 >= 3.0f) {
            return MathUtils.exp((MathUtils.log(f + 0.25f) - MathUtils.log(f2 + 0.25f)) * 0.05f);
        }
        return 1.0f;
    }

    public abstract void addUserDataPoint(float f, float f2);

    public abstract void clearUserDataPoints();

    public abstract float convertToAdjustedNits(float f);

    public abstract int convertToBrightness(float f);

    public abstract float convertToNits(float f);

    public abstract void dump(PrintWriter printWriter);

    public abstract BrightnessConfiguration getAppliedBackupConfig(
            BrightnessConfiguration brightnessConfiguration);

    public abstract float getAutoBrightnessAdjustment();

    public abstract float getBrightness(String str, float f, int i);

    public abstract BrightnessConfiguration getBrightnessConfiguration();

    public abstract float getBrightnessForSpline(float f, Spline spline);

    public abstract float getBrightnessFromNits(float f);

    public abstract Spline getBrightnessSpline();

    public abstract BrightnessConfiguration getDefaultConfig();

    public abstract int getMode();

    public final Pair getOffsetAdjustedCurve(float[] fArr, float[] fArr2) {
        float f;
        float[] fArr3;
        float[] fArr4;
        float[] copyOf = Arrays.copyOf(fArr2, fArr2.length);
        if (this.mLoggingEnabled) {
            PLOG.logCurve("curve before adjust offset", fArr, copyOf);
        }
        if (this.mUserOffsetManager.hasUserDataPoints()) {
            Spline createSpline =
                    Spline.createSpline(
                            Arrays.copyOf(fArr, fArr.length), Arrays.copyOf(copyOf, copyOf.length));
            float[] copyOf2 = Arrays.copyOf(copyOf, copyOf.length);
            Pair curve = this.mUserOffsetManager.getCurve();
            int i = 0;
            if (curve != null) {
                Spline createLinearSpline =
                        Spline.createLinearSpline((float[]) curve.first, (float[]) curve.second);
                for (int i2 = 0; i2 < copyOf2.length; i2++) {
                    float f2 = copyOf2[i2];
                    if (f2 > 1.0f) {
                        break;
                    }
                    float interpolate = createLinearSpline.interpolate(fArr[i2]) + f2;
                    copyOf2[i2] = interpolate;
                    if (interpolate > 1.0f) {
                        copyOf2[i2] = 1.0f;
                    }
                }
            }
            if (this.mLoggingEnabled) {
                PLOG.logCurve("curve after offset plus", fArr, copyOf2);
            }
            Pair create = Pair.create(fArr, copyOf2);
            Pair curve2 = this.mUserOffsetManager.getCurve();
            if (curve2 != null) {
                float[] fArr5 = (float[]) curve2.first;
                float[] fArr6 = (float[]) curve2.second;
                for (int i3 = 0; i3 < fArr5.length; i3++) {
                    float f3 = fArr5[i3];
                    if (FullScreenMagnificationGestureHandler.MAX_SCALE <= f3
                            && (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                                    || f3 < PowerManagerUtil.HBM_LUX)) {
                        float interpolate2 = createSpline.interpolate(f3) + fArr6[i3];
                        float[] fArr7 = (float[]) create.first;
                        float[] fArr8 = (float[]) create.second;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= fArr7.length) {
                                i4 = fArr7.length;
                                break;
                            }
                            if (f3 <= fArr7[i4]) {
                                break;
                            }
                            i4++;
                        }
                        if (i4 == fArr7.length) {
                            fArr4 = Arrays.copyOf(fArr7, fArr7.length + 1);
                            fArr3 = Arrays.copyOf(fArr8, fArr8.length + 1);
                            fArr4[i4] = f3;
                            fArr3[i4] = interpolate2;
                        } else if (fArr7[i4] == f3) {
                            fArr4 = Arrays.copyOf(fArr7, fArr7.length);
                            fArr3 = Arrays.copyOf(fArr8, fArr8.length);
                            fArr3[i4] = interpolate2;
                        } else {
                            float[] copyOf3 = Arrays.copyOf(fArr7, fArr7.length + 1);
                            int i5 = i4 + 1;
                            System.arraycopy(copyOf3, i4, copyOf3, i5, fArr7.length - i4);
                            copyOf3[i4] = f3;
                            float[] copyOf4 = Arrays.copyOf(fArr8, fArr8.length + 1);
                            System.arraycopy(copyOf4, i4, copyOf4, i5, fArr8.length - i4);
                            copyOf4[i4] = interpolate2;
                            fArr3 = copyOf4;
                            fArr4 = copyOf3;
                        }
                        create = Pair.create(fArr4, fArr3);
                    }
                }
                UserOffsetManager userOffsetManager = this.mUserOffsetManager;
                synchronized (userOffsetManager) {
                    f = userOffsetManager.mLastAddedLux;
                }
                float[] fArr9 = (float[]) create.first;
                while (true) {
                    if (i >= fArr9.length) {
                        i = fArr9.length;
                        break;
                    }
                    if (f <= fArr9[i]) {
                        break;
                    }
                    i++;
                }
                float[] fArr10 = (float[]) create.first;
                float[] fArr11 = (float[]) create.second;
                boolean z = this.mLoggingEnabled;
                Plog$SystemPlog plog$SystemPlog = PLOG;
                if (z) {
                    plog$SystemPlog.logCurve("unsmoothed curve", fArr10, fArr11);
                }
                float f4 = fArr10[i];
                float f5 = fArr11[i];
                int i6 = i + 1;
                while (i6 < fArr10.length) {
                    float f6 = fArr10[i6];
                    float f7 = fArr11[i6];
                    if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                            && f6 >= PowerManagerUtil.HBM_LUX) {
                        break;
                    }
                    float max = Math.max(f7, permissibleMinimumRatio(f6, f4) * f5);
                    f5 = max > 1.0f ? 1.0f : max;
                    fArr11[i6] = f5;
                    i6++;
                    f4 = f6;
                }
                float f8 = fArr10[i];
                float f9 = fArr11[i];
                int i7 = i - 1;
                while (i7 >= 0) {
                    float f10 = fArr10[i7];
                    float f11 = fArr11[i7];
                    if (f10 < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        break;
                    }
                    f9 = Math.min(f11, permissibleMinimumRatio(f10, f8) * f9);
                    fArr11[i7] = f9;
                    i7--;
                    f8 = f10;
                }
                if (this.mLoggingEnabled) {
                    plog$SystemPlog.logCurve("smoothed curve", fArr10, fArr11);
                }
            }
            float[] fArr12 = (float[]) create.first;
            float[] fArr13 = (float[]) create.second;
            if (this.mLoggingEnabled) {
                PLOG.logCurve("curve after insert user point", fArr12, fArr13);
            }
            copyOf = fArr13;
            fArr = fArr12;
        }
        return Pair.create(fArr, copyOf);
    }

    public abstract long getShortTermModelTimeout();

    public abstract boolean hasUserDataPoints();

    public final float inferAutoBrightnessAdjustment(float f, float f2, float f3) {
        float f4;
        float f5 = Float.NaN;
        if (f3 <= 0.1f || f3 >= 0.9f) {
            f4 = f2 - f3;
        } else if (f2 == FullScreenMagnificationGestureHandler.MAX_SCALE) {
            f4 = -1.0f;
        } else if (f2 == 1.0f) {
            f4 = 1.0f;
        } else {
            f5 = MathUtils.log(f2) / MathUtils.log(f3);
            f4 = (-MathUtils.log(f5)) / MathUtils.log(f);
        }
        float constrain = MathUtils.constrain(f4, -1.0f, 1.0f);
        if (this.mLoggingEnabled) {
            StringBuilder sb = new StringBuilder("inferAutoBrightnessAdjustment: ");
            sb.append(f);
            sb.append("^");
            float f6 = -constrain;
            sb.append(f6);
            sb.append("=");
            sb.append(MathUtils.pow(f, f6));
            sb.append(" == ");
            sb.append(f5);
            Slog.d("BrightnessMappingStrategy", sb.toString());
            Slog.d(
                    "BrightnessMappingStrategy",
                    "inferAutoBrightnessAdjustment: "
                            + f3
                            + "^"
                            + f5
                            + "="
                            + MathUtils.pow(f3, f5)
                            + " == "
                            + f2);
        }
        return constrain;
    }

    public abstract boolean isDefaultConfig();

    public abstract boolean setAutoBrightnessAdjustment(float f);

    public abstract boolean setBrightnessConfiguration(
            BrightnessConfiguration brightnessConfiguration);
}
