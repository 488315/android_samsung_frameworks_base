package com.android.server.display.color;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.ColorSpace;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.display.IColorDisplayManager;
import android.hardware.display.Time;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.SurfaceControl;
import android.view.animation.AnimationUtils;
import com.android.internal.util.DumpUtils;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DisplayThread;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.Flags;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.color.AppSaturationController;
import com.android.server.display.color.ColorDisplayService;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.pm.pu.ProfileUtilizationService;
import com.android.server.twilight.TwilightListener;
import com.android.server.twilight.TwilightManager;
import com.android.server.twilight.TwilightService;
import com.android.server.twilight.TwilightState;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ColorDisplayService extends SystemService {
    public static final ColorMatrixEvaluator COLOR_MATRIX_EVALUATOR;
    public static final float[] MATRIX_GRAYSCALE;
    public static final float[] MATRIX_IDENTITY;
    public static final float[] MATRIX_INVERT_COLOR;
    public final AppSaturationController mAppSaturationController;
    public boolean mBootCompleted;
    public final Object mCctTintApplierLock;
    public SparseIntArray mColorModeCompositionColorSpaces;
    public AnonymousClass2 mContentObserver;
    public int mCurrentUser;
    final DisplayWhiteBalanceTintController mDisplayWhiteBalanceTintController;
    public final GlobalSaturationTintController mGlobalSaturationTintController;
    final Handler mHandler;
    public NightDisplayAutoMode mNightDisplayAutoMode;
    public final NightDisplayTintController mNightDisplayTintController;
    public final ReduceBrightColorsTintController mReduceBrightColorsTintController;
    public ContentObserver mUserSetupObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class BinderService extends IColorDisplayManager.Stub {
        public BinderService() {
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(ColorDisplayService.this.getContext(), "ColorDisplayService", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ColorDisplayService.m476$$Nest$mdumpInternal(ColorDisplayService.this, printWriter);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final int getColorMode() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                float[] fArr = ColorDisplayService.MATRIX_IDENTITY;
                return colorDisplayService.getColorModeInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getNightDisplayAutoMode() {
            getNightDisplayAutoMode_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                float[] fArr = ColorDisplayService.MATRIX_IDENTITY;
                return colorDisplayService.getNightDisplayAutoModeInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getNightDisplayAutoModeRaw() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                float[] fArr = ColorDisplayService.MATRIX_IDENTITY;
                int i = -1;
                if (colorDisplayService.mCurrentUser != -10000) {
                    i = Settings.Secure.getIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_auto_mode", -1, colorDisplayService.mCurrentUser);
                }
                return i;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getNightDisplayColorTemperature() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NightDisplayTintController nightDisplayTintController = ColorDisplayService.this.mNightDisplayTintController;
                Integer num = nightDisplayTintController.mColorTemp;
                return num != null ? nightDisplayTintController.clampNightDisplayColorTemperature(num.intValue()) : nightDisplayTintController.getColorTemperatureSetting();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final Time getNightDisplayCustomEndTime() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.m477$$Nest$mgetNightDisplayCustomEndTimeInternal(ColorDisplayService.this);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final Time getNightDisplayCustomStartTime() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.m478$$Nest$mgetNightDisplayCustomStartTimeInternal(ColorDisplayService.this);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final float getReduceBrightColorsOffsetFactor() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                float[] fArr = ColorDisplayService.this.mReduceBrightColorsTintController.mCoefficients;
                return fArr[0] + fArr[1] + fArr[2];
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getReduceBrightColorsStrength() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.mReduceBrightColorsTintController.mStrength;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int getTransformCapabilities() {
            getTransformCapabilities_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                float[] fArr = ColorDisplayService.MATRIX_IDENTITY;
                colorDisplayService.getClass();
                boolean protectedContentSupport = SurfaceControl.getProtectedContentSupport();
                Resources resources = colorDisplayService.getContext().getResources();
                boolean z = protectedContentSupport;
                if (resources.getBoolean(R.bool.config_silenceSensorAvailable)) {
                    z = (protectedContentSupport ? 1 : 0) | 2;
                }
                int i = z;
                if (resources.getBoolean(R.bool.config_single_volume)) {
                    i = (z ? 1 : 0) | 4;
                }
                return i;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to use ADB color transform commands");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return new ColorDisplayShellCommand(ColorDisplayService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isDeviceColorManaged() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.m480$$Nest$misDeviceColorManagedInternal(ColorDisplayService.this);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isDisplayWhiteBalanceEnabled() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                float[] fArr = ColorDisplayService.MATRIX_IDENTITY;
                return colorDisplayService.isDisplayWhiteBalanceSettingEnabled();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isNightDisplayActivated() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.mNightDisplayTintController.isActivated();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isReduceBrightColorsActivated() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.mReduceBrightColorsTintController.isActivated();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isSaturationActivated() {
            boolean z;
            isSaturationActivated_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!ColorDisplayService.this.mGlobalSaturationTintController.isActivatedStateNotSet()) {
                    if (ColorDisplayService.this.mGlobalSaturationTintController.isActivated()) {
                        z = true;
                        return z;
                    }
                }
                z = false;
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setAppSaturationLevel(String str, int i) {
            setAppSaturationLevel_enforcePermission();
            String nameForUid = ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getNameForUid(Binder.getCallingUid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.setAppSaturationLevelInternal(i, nameForUid, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setColorMode(int i) {
            setColorMode_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService.m481$$Nest$msetColorModeInternal(ColorDisplayService.this, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setDisplayWhiteBalanceEnabled(boolean z) {
            boolean putIntForUser;
            setDisplayWhiteBalanceEnabled_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                if (colorDisplayService.mCurrentUser == -10000) {
                    putIntForUser = false;
                } else {
                    putIntForUser = Settings.Secure.putIntForUser(colorDisplayService.getContext().getContentResolver(), "display_white_balance_enabled", z ? 1 : 0, colorDisplayService.mCurrentUser);
                }
                return putIntForUser;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setNightDisplayActivated(boolean z) {
            setNightDisplayActivated_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NightDisplayTintController nightDisplayTintController = ColorDisplayService.this.mNightDisplayTintController;
                Boolean valueOf = Boolean.valueOf(z);
                nightDisplayTintController.getClass();
                nightDisplayTintController.setActivated(valueOf, LocalDateTime.now());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final boolean setNightDisplayAutoMode(int i) {
            setNightDisplayAutoMode_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                float[] fArr = ColorDisplayService.MATRIX_IDENTITY;
                if (colorDisplayService.getNightDisplayAutoModeInternal() != i) {
                    Settings.Secure.putStringForUser(colorDisplayService.getContext().getContentResolver(), "night_display_last_activated_time", null, colorDisplayService.mCurrentUser);
                }
                return Settings.Secure.putIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_auto_mode", i, colorDisplayService.mCurrentUser);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setNightDisplayColorTemperature(int i) {
            setNightDisplayColorTemperature_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                NightDisplayTintController nightDisplayTintController = ColorDisplayService.this.mNightDisplayTintController;
                nightDisplayTintController.mColorTemp = Integer.valueOf(i);
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                boolean putIntForUser = Settings.Secure.putIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_color_temperature", i, colorDisplayService.mCurrentUser);
                nightDisplayTintController.setMatrix(i);
                colorDisplayService.mHandler.sendEmptyMessage(2);
                return putIntForUser;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setNightDisplayCustomEndTime(Time time) {
            setNightDisplayCustomEndTime_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                float[] fArr = ColorDisplayService.MATRIX_IDENTITY;
                return Settings.Secure.putIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_custom_end_time", time.getLocalTime().toSecondOfDay() * 1000, colorDisplayService.mCurrentUser);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setNightDisplayCustomStartTime(Time time) {
            setNightDisplayCustomStartTime_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                float[] fArr = ColorDisplayService.MATRIX_IDENTITY;
                return Settings.Secure.putIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_custom_start_time", time.getLocalTime().toSecondOfDay() * 1000, colorDisplayService.mCurrentUser);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setReduceBrightColorsActivated(boolean z) {
            boolean putIntForUser;
            setReduceBrightColorsActivated_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                if (colorDisplayService.mCurrentUser == -10000) {
                    putIntForUser = false;
                } else {
                    putIntForUser = Settings.Secure.putIntForUser(colorDisplayService.getContext().getContentResolver(), "reduce_bright_colors_activated", z ? 1 : 0, colorDisplayService.mCurrentUser);
                }
                return putIntForUser;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setReduceBrightColorsStrength(int i) {
            setReduceBrightColorsStrength_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                return colorDisplayService.mCurrentUser == -10000 ? false : Settings.Secure.putIntForUser(colorDisplayService.getContext().getContentResolver(), "reduce_bright_colors_level", i, colorDisplayService.mCurrentUser);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setSaturationLevel(int i) {
            boolean z = ColorDisplayService.this.getContext().checkCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS") == 0;
            boolean z2 = ColorDisplayService.this.getContext().checkCallingPermission("android.permission.CONTROL_DISPLAY_SATURATION") == 0;
            boolean z3 = ColorDisplayService.this.getContext().checkCallingPermission("com.samsung.android.permission.SEM_CONTROL_DISPLAY_COLOR_TRANSFORMS") == 0;
            if (!z && !z2 && !z3) {
                throw new SecurityException("Permission required to set display saturation level");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                Message obtainMessage = colorDisplayService.mHandler.obtainMessage(4);
                obtainMessage.arg1 = i;
                colorDisplayService.mHandler.sendMessage(obtainMessage);
                return true;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ColorDisplayServiceInternal {
        public ColorDisplayServiceInternal() {
        }

        public final void applyEvenDimmerColorChanges(int i, boolean z) {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            colorDisplayService.mReduceBrightColorsTintController.setActivated(Boolean.valueOf(z));
            colorDisplayService.mReduceBrightColorsTintController.setMatrix(i);
            colorDisplayService.mHandler.sendEmptyMessage(6);
        }

        public final void onOpticalUdfpsStarted() {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            GlobalSaturationTintController globalSaturationTintController = colorDisplayService.mGlobalSaturationTintController;
            globalSaturationTintController.mIsActivationLocked = true;
            if (globalSaturationTintController.isActivated()) {
                colorDisplayService.mHandler.post(new ColorDisplayService$ColorDisplayServiceInternal$$ExternalSyntheticLambda0(this, 0));
            }
            NightDisplayTintController nightDisplayTintController = colorDisplayService.mNightDisplayTintController;
            nightDisplayTintController.mIsActivationLocked = true;
            if (nightDisplayTintController.isActivated()) {
                colorDisplayService.mHandler.sendEmptyMessage(2);
            }
            ReduceBrightColorsTintController reduceBrightColorsTintController = colorDisplayService.mReduceBrightColorsTintController;
            reduceBrightColorsTintController.mIsActivationLocked = true;
            if (reduceBrightColorsTintController.isActivated()) {
                colorDisplayService.mHandler.sendEmptyMessage(6);
            }
        }

        public final void onOpticalUdfpsStopped() {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            GlobalSaturationTintController globalSaturationTintController = colorDisplayService.mGlobalSaturationTintController;
            globalSaturationTintController.mIsActivationLocked = false;
            if (globalSaturationTintController.isActivated()) {
                colorDisplayService.mHandler.post(new ColorDisplayService$ColorDisplayServiceInternal$$ExternalSyntheticLambda0(this, 1));
            }
            NightDisplayTintController nightDisplayTintController = colorDisplayService.mNightDisplayTintController;
            nightDisplayTintController.mIsActivationLocked = false;
            if (nightDisplayTintController.isActivated()) {
                colorDisplayService.mHandler.sendEmptyMessage(2);
            }
            ReduceBrightColorsTintController reduceBrightColorsTintController = colorDisplayService.mReduceBrightColorsTintController;
            reduceBrightColorsTintController.mIsActivationLocked = false;
            if (reduceBrightColorsTintController.isActivated()) {
                colorDisplayService.mHandler.post(new ColorDisplayService$ColorDisplayServiceInternal$$ExternalSyntheticLambda0(this, 2));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ColorMatrixEvaluator implements TypeEvaluator {
        public final float[] mResultMatrix = new float[16];

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float[] fArr = (float[]) obj;
            float[] fArr2 = (float[]) obj2;
            int i = 0;
            while (true) {
                float[] fArr3 = this.mResultMatrix;
                if (i >= fArr3.length) {
                    return fArr3;
                }
                fArr3[i] = MathUtils.lerp(fArr[i], fArr2[i], f);
                i++;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ColorTransformController {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CustomNightDisplayAutoMode extends NightDisplayAutoMode implements AlarmManager.OnAlarmListener {
        public final AlarmManager mAlarmManager;
        public LocalTime mEndTime;
        public LocalDateTime mLastActivatedTime;
        public LocalTime mStartTime;
        public final AnonymousClass1 mTimeChangedReceiver = new BroadcastReceiver() { // from class: com.android.server.display.color.ColorDisplayService.CustomNightDisplayAutoMode.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                CustomNightDisplayAutoMode.this.updateActivated();
            }
        };

        /* JADX WARN: Type inference failed for: r2v4, types: [com.android.server.display.color.ColorDisplayService$CustomNightDisplayAutoMode$1] */
        public CustomNightDisplayAutoMode() {
            this.mAlarmManager = (AlarmManager) ColorDisplayService.this.getContext().getSystemService("alarm");
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public final void onActivated(boolean z) {
            this.mLastActivatedTime = ColorDisplayService.m479$$Nest$mgetNightDisplayLastActivatedTimeSetting(ColorDisplayService.this);
            updateNextAlarm(Boolean.valueOf(z), LocalDateTime.now());
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            Slog.d("ColorDisplayService", "onAlarm");
            updateActivated();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public final void onCustomEndTimeChanged(LocalTime localTime) {
            this.mEndTime = localTime;
            this.mLastActivatedTime = null;
            updateActivated();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public final void onCustomStartTimeChanged(LocalTime localTime) {
            this.mStartTime = localTime;
            this.mLastActivatedTime = null;
            updateActivated();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public final void onStart() {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            ColorDisplayService.this.getContext().registerReceiver(this.mTimeChangedReceiver, intentFilter);
            this.mStartTime = ColorDisplayService.m478$$Nest$mgetNightDisplayCustomStartTimeInternal(ColorDisplayService.this).getLocalTime();
            this.mEndTime = ColorDisplayService.m477$$Nest$mgetNightDisplayCustomEndTimeInternal(ColorDisplayService.this).getLocalTime();
            this.mLastActivatedTime = ColorDisplayService.m479$$Nest$mgetNightDisplayLastActivatedTimeSetting(ColorDisplayService.this);
            updateActivated();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public final void onStop() {
            ColorDisplayService.this.getContext().unregisterReceiver(this.mTimeChangedReceiver);
            this.mAlarmManager.cancel(this);
            this.mLastActivatedTime = null;
        }

        public final void updateActivated() {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime dateTimeBefore = ColorDisplayService.getDateTimeBefore(this.mStartTime, now);
            LocalDateTime dateTimeAfter = ColorDisplayService.getDateTimeAfter(this.mEndTime, dateTimeBefore);
            boolean isBefore = now.isBefore(dateTimeAfter);
            LocalDateTime localDateTime = this.mLastActivatedTime;
            if (localDateTime != null && localDateTime.isBefore(now) && this.mLastActivatedTime.isAfter(dateTimeBefore) && (this.mLastActivatedTime.isAfter(dateTimeAfter) || now.isBefore(dateTimeAfter))) {
                isBefore = ColorDisplayService.this.mNightDisplayTintController.isActivatedSetting();
            }
            if (ColorDisplayService.this.mNightDisplayTintController.isActivatedStateNotSet() || ColorDisplayService.this.mNightDisplayTintController.isActivated() != isBefore) {
                NightDisplayTintController nightDisplayTintController = ColorDisplayService.this.mNightDisplayTintController;
                Boolean valueOf = Boolean.valueOf(isBefore);
                if (!isBefore) {
                    dateTimeBefore = dateTimeAfter;
                }
                nightDisplayTintController.setActivated(valueOf, dateTimeBefore);
            }
            updateNextAlarm(Boolean.valueOf(ColorDisplayService.this.mNightDisplayTintController.isActivated()), now);
        }

        /* JADX WARN: Type inference failed for: r8v5, types: [java.time.ZonedDateTime] */
        public final void updateNextAlarm(Boolean bool, LocalDateTime localDateTime) {
            this.mAlarmManager.setExact(1, (bool.booleanValue() ? ColorDisplayService.getDateTimeAfter(this.mEndTime, localDateTime) : ColorDisplayService.getDateTimeAfter(this.mStartTime, localDateTime)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), "ColorDisplayService", this, null);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class NightDisplayAutoMode {
        public abstract void onActivated(boolean z);

        public void onCustomEndTimeChanged(LocalTime localTime) {
        }

        public void onCustomStartTimeChanged(LocalTime localTime) {
        }

        public abstract void onStart();

        public abstract void onStop();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NightDisplayTintController extends TintController {
        public Integer mColorTemp;
        public Boolean mIsAvailable;
        public final float[] mMatrix = new float[16];
        public final float[] mColorTempCoefficients = new float[9];

        public NightDisplayTintController() {
        }

        public final int clampNightDisplayColorTemperature(int i) {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            if (i == -1) {
                i = colorDisplayService.getContext().getResources().getInteger(R.integer.config_reevaluate_bootstrap_sim_data_usage_millis);
            }
            int minimumColorTemperature = ColorDisplayManager.getMinimumColorTemperature(colorDisplayService.getContext());
            int maximumColorTemperature = ColorDisplayManager.getMaximumColorTemperature(colorDisplayService.getContext());
            return i < minimumColorTemperature ? minimumColorTemperature : i > maximumColorTemperature ? maximumColorTemperature : i;
        }

        public final int getColorTemperatureSetting() {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            if (colorDisplayService.mCurrentUser == -10000) {
                return -1;
            }
            return clampNightDisplayColorTemperature(Settings.Secure.getIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_color_temperature", -1, colorDisplayService.mCurrentUser));
        }

        @Override // com.android.server.display.color.TintController
        public final int getLevel() {
            return 100;
        }

        @Override // com.android.server.display.color.TintController
        public final float[] getMatrix() {
            if (!this.mIsActivationLocked && isActivated()) {
                return this.mMatrix;
            }
            return ColorDisplayService.MATRIX_IDENTITY;
        }

        public final boolean isActivatedSetting() {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            return colorDisplayService.mCurrentUser != -10000 && Settings.Secure.getIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_activated", 0, colorDisplayService.mCurrentUser) == 1;
        }

        public final boolean isAvailable(Context context) {
            if (this.mIsAvailable == null) {
                this.mIsAvailable = Boolean.valueOf(ColorDisplayManager.isNightDisplayAvailable(context));
            }
            return this.mIsAvailable.booleanValue();
        }

        public final void setActivated(Boolean bool, LocalDateTime localDateTime) {
            if (bool == null) {
                this.mIsActivated = null;
                return;
            }
            boolean z = bool.booleanValue() != isActivated();
            boolean isActivatedStateNotSet = isActivatedStateNotSet();
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            if (!isActivatedStateNotSet && z) {
                Settings.Secure.putStringForUser(colorDisplayService.getContext().getContentResolver(), "night_display_last_activated_time", localDateTime.toString(), colorDisplayService.mCurrentUser);
            }
            if (isActivatedStateNotSet() || z) {
                this.mIsActivated = bool;
                if (isActivatedSetting() != bool.booleanValue()) {
                    Settings.Secure.putIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_activated", bool.booleanValue() ? 1 : 0, colorDisplayService.mCurrentUser);
                }
                boolean booleanValue = bool.booleanValue();
                Slog.i("ColorDisplayService", booleanValue ? "Turning on night display" : "Turning off night display");
                NightDisplayAutoMode nightDisplayAutoMode = colorDisplayService.mNightDisplayAutoMode;
                if (nightDisplayAutoMode != null) {
                    nightDisplayAutoMode.onActivated(booleanValue);
                }
                if (colorDisplayService.mDisplayWhiteBalanceTintController.isAvailable(colorDisplayService.getContext())) {
                    colorDisplayService.updateDisplayWhiteBalanceStatus();
                }
                colorDisplayService.mHandler.sendEmptyMessage(3);
            }
        }

        public final void setMatrix(int i) {
            float[] fArr = this.mMatrix;
            if (fArr.length != 16) {
                Slog.d("ColorDisplayService", "The display transformation matrix must be 4x4");
                return;
            }
            Matrix.setIdentityM(fArr, 0);
            float f = i * i;
            float[] fArr2 = this.mColorTempCoefficients;
            float f2 = i;
            float f3 = (fArr2[1] * f2) + (fArr2[0] * f) + fArr2[2];
            float f4 = (fArr2[4] * f2) + (fArr2[3] * f) + fArr2[5];
            float f5 = (f2 * fArr2[7]) + (f * fArr2[6]) + fArr2[8];
            fArr[0] = f3;
            fArr[5] = f4;
            fArr[10] = f5;
        }

        public final void setUp(Context context, boolean z) {
            String[] stringArray = context.getResources().getStringArray(z ? 17236262 : 17236263);
            for (int i = 0; i < 9 && i < stringArray.length; i++) {
                this.mColorTempCoefficients[i] = Float.parseFloat(stringArray[i]);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TintHandler extends Handler {
        public TintHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    ColorDisplayService.this.onUserChanged(message.arg1);
                    return;
                case 1:
                    ColorDisplayService.this.setUp();
                    return;
                case 2:
                    ColorDisplayService colorDisplayService = ColorDisplayService.this;
                    ColorDisplayService.m475$$Nest$mapplyTint(colorDisplayService, colorDisplayService.mNightDisplayTintController, true);
                    return;
                case 3:
                    ColorDisplayService colorDisplayService2 = ColorDisplayService.this;
                    ColorDisplayService.m475$$Nest$mapplyTint(colorDisplayService2, colorDisplayService2.mNightDisplayTintController, false);
                    return;
                case 4:
                    GlobalSaturationTintController globalSaturationTintController = ColorDisplayService.this.mGlobalSaturationTintController;
                    int i = message.arg1;
                    globalSaturationTintController.getClass();
                    if (i < 0) {
                        i = 0;
                    } else if (i > 100) {
                        i = 100;
                    }
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "Setting saturation level: ", "ColorDisplayService");
                    float[] fArr = globalSaturationTintController.mMatrixGlobalSaturation;
                    if (i == 100) {
                        globalSaturationTintController.mIsActivated = Boolean.FALSE;
                        Matrix.setIdentityM(fArr, 0);
                    } else {
                        globalSaturationTintController.mIsActivated = Boolean.TRUE;
                        Matrix.setIdentityM(fArr, 0);
                        float f = i * 0.01f;
                        float f2 = 1.0f - f;
                        float[] fArr2 = {0.231f * f2, 0.715f * f2, f2 * 0.072f};
                        fArr[0] = fArr2[0] + f;
                        float f3 = fArr2[0];
                        fArr[1] = f3;
                        fArr[2] = f3;
                        float f4 = fArr2[1];
                        fArr[4] = f4;
                        fArr[5] = f4 + f;
                        fArr[6] = f4;
                        float f5 = fArr2[2];
                        fArr[8] = f5;
                        fArr[9] = f5;
                        fArr[10] = f5 + f;
                    }
                    ColorDisplayService colorDisplayService3 = ColorDisplayService.this;
                    ColorDisplayService.m475$$Nest$mapplyTint(colorDisplayService3, colorDisplayService3.mGlobalSaturationTintController, false);
                    return;
                case 5:
                    final ColorDisplayService colorDisplayService4 = ColorDisplayService.this;
                    final DisplayWhiteBalanceTintController displayWhiteBalanceTintController = colorDisplayService4.mDisplayWhiteBalanceTintController;
                    synchronized (colorDisplayService4.mCctTintApplierLock) {
                        try {
                            displayWhiteBalanceTintController.cancelAnimator();
                            final DisplayTransformManager displayTransformManager = (DisplayTransformManager) colorDisplayService4.getLocalService(DisplayTransformManager.class);
                            final int i2 = displayWhiteBalanceTintController.mAppliedCct;
                            final int i3 = displayWhiteBalanceTintController.isActivated() ? displayWhiteBalanceTintController.mTargetCct : displayWhiteBalanceTintController.mDisplayNominalWhiteCct;
                            long j = i3 > i2 ? displayWhiteBalanceTintController.mTransitionDurationIncrease : displayWhiteBalanceTintController.mTransitionDurationDecrease;
                            Slog.d("ColorDisplayService", "DisplayWhiteBalanceTintController animation started: toCct=" + i3 + " fromCct=" + i2 + " with duration=" + j);
                            ValueAnimator ofInt = ValueAnimator.ofInt(i2, i3);
                            displayWhiteBalanceTintController.mAnimator = ofInt;
                            CctEvaluator cctEvaluator = displayWhiteBalanceTintController.mCctEvaluator;
                            if (cctEvaluator != null) {
                                ofInt.setEvaluator(cctEvaluator);
                            }
                            ofInt.setDuration(j);
                            ofInt.setInterpolator(AnimationUtils.loadInterpolator(colorDisplayService4.getContext(), R.interpolator.linear));
                            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.display.color.ColorDisplayService$$ExternalSyntheticLambda0
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    ColorDisplayService colorDisplayService5 = ColorDisplayService.this;
                                    DisplayWhiteBalanceTintController displayWhiteBalanceTintController2 = displayWhiteBalanceTintController;
                                    DisplayTransformManager displayTransformManager2 = displayTransformManager;
                                    synchronized (colorDisplayService5.mCctTintApplierLock) {
                                        try {
                                            int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                                            if (intValue != displayWhiteBalanceTintController2.mAppliedCct) {
                                                displayTransformManager2.setColorMatrix(125, displayWhiteBalanceTintController2.computeMatrixForCct(intValue));
                                                displayWhiteBalanceTintController2.mAppliedCct = intValue;
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            });
                            ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.server.display.color.ColorDisplayService.4
                                public boolean mIsCancelled;

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                    Slog.d("ColorDisplayService", displayWhiteBalanceTintController.getClass().getSimpleName().concat(" animation cancelled"));
                                    this.mIsCancelled = true;
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    synchronized (ColorDisplayService.this.mCctTintApplierLock) {
                                        try {
                                            Slog.d("ColorDisplayService", displayWhiteBalanceTintController.getClass().getSimpleName() + " animation ended: wasCancelled=" + this.mIsCancelled + " toCct=" + i3 + " fromCct=" + i2);
                                            if (!this.mIsCancelled) {
                                                DisplayTransformManager displayTransformManager2 = displayTransformManager;
                                                displayWhiteBalanceTintController.getClass();
                                                displayTransformManager2.setColorMatrix(125, displayWhiteBalanceTintController.computeMatrixForCct(i3));
                                                displayWhiteBalanceTintController.mAppliedCct = i3;
                                            }
                                            displayWhiteBalanceTintController.mAnimator = null;
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            });
                            ofInt.start();
                        } finally {
                        }
                    }
                    return;
                case 6:
                    ColorDisplayService colorDisplayService5 = ColorDisplayService.this;
                    ColorDisplayService.m475$$Nest$mapplyTint(colorDisplayService5, colorDisplayService5.mReduceBrightColorsTintController, true);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TintValueAnimator extends ValueAnimator {
        public float[] max;
        public float[] min;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TwilightNightDisplayAutoMode extends NightDisplayAutoMode implements TwilightListener {
        public LocalDateTime mLastActivatedTime;
        public final TwilightManager mTwilightManager;

        public TwilightNightDisplayAutoMode() {
            this.mTwilightManager = (TwilightManager) ColorDisplayService.this.getLocalService(TwilightManager.class);
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public final void onActivated(boolean z) {
            this.mLastActivatedTime = ColorDisplayService.m479$$Nest$mgetNightDisplayLastActivatedTimeSetting(ColorDisplayService.this);
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public final void onStart() {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            Handler handler = colorDisplayService.mHandler;
            TwilightService.AnonymousClass1 anonymousClass1 = (TwilightService.AnonymousClass1) this.mTwilightManager;
            anonymousClass1.registerListener(this, handler);
            this.mLastActivatedTime = ColorDisplayService.m479$$Nest$mgetNightDisplayLastActivatedTimeSetting(colorDisplayService);
            updateActivated(anonymousClass1.getLastTwilightState());
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public final void onStop() {
            ((TwilightService.AnonymousClass1) this.mTwilightManager).unregisterListener(this);
            this.mLastActivatedTime = null;
        }

        @Override // com.android.server.twilight.TwilightListener
        public final void onTwilightStateChanged(TwilightState twilightState) {
            StringBuilder sb = new StringBuilder("onTwilightStateChanged: isNight=");
            sb.append(twilightState == null ? null : Boolean.valueOf(twilightState.isNight()));
            Slog.d("ColorDisplayService", sb.toString());
            updateActivated(twilightState);
        }

        public final void updateActivated(TwilightState twilightState) {
            if (twilightState == null) {
                return;
            }
            boolean isNight = twilightState.isNight();
            LocalDateTime localDateTime = this.mLastActivatedTime;
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            if (localDateTime != null) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime ofInstant = LocalDateTime.ofInstant(Instant.ofEpochMilli(twilightState.mSunriseTimeMillis), TimeZone.getDefault().toZoneId());
                LocalDateTime ofInstant2 = LocalDateTime.ofInstant(Instant.ofEpochMilli(twilightState.mSunsetTimeMillis), TimeZone.getDefault().toZoneId());
                if (this.mLastActivatedTime.isBefore(now)) {
                    if (this.mLastActivatedTime.isBefore(ofInstant2) ^ this.mLastActivatedTime.isBefore(ofInstant)) {
                        isNight = colorDisplayService.mNightDisplayTintController.isActivatedSetting();
                    }
                }
            }
            boolean isActivatedStateNotSet = colorDisplayService.mNightDisplayTintController.isActivatedStateNotSet();
            NightDisplayTintController nightDisplayTintController = colorDisplayService.mNightDisplayTintController;
            if (isActivatedStateNotSet || nightDisplayTintController.isActivated() != isNight) {
                Boolean valueOf = Boolean.valueOf(isNight);
                nightDisplayTintController.getClass();
                nightDisplayTintController.setActivated(valueOf, LocalDateTime.now());
            }
        }
    }

    /* renamed from: -$$Nest$mapplyTint, reason: not valid java name */
    public static void m475$$Nest$mapplyTint(ColorDisplayService colorDisplayService, final TintController tintController, boolean z) {
        float[] copyOf;
        colorDisplayService.getClass();
        tintController.cancelAnimator();
        final DisplayTransformManager displayTransformManager = (DisplayTransformManager) colorDisplayService.getLocalService(DisplayTransformManager.class);
        int level = tintController.getLevel();
        synchronized (displayTransformManager.mColorMatrix) {
            float[] fArr = (float[]) displayTransformManager.mColorMatrix.get(level);
            copyOf = fArr == null ? null : Arrays.copyOf(fArr, fArr.length);
        }
        final float[] matrix = tintController.getMatrix();
        if (z) {
            displayTransformManager.setColorMatrix(tintController.getLevel(), matrix);
            return;
        }
        ColorMatrixEvaluator colorMatrixEvaluator = COLOR_MATRIX_EVALUATOR;
        if (copyOf == null) {
            copyOf = MATRIX_IDENTITY;
        }
        Object[] objArr = {copyOf, matrix};
        TintValueAnimator tintValueAnimator = new TintValueAnimator();
        tintValueAnimator.setObjectValues(objArr);
        tintValueAnimator.setEvaluator(colorMatrixEvaluator);
        float[] fArr2 = (float[]) objArr[0];
        tintValueAnimator.min = new float[fArr2.length];
        tintValueAnimator.max = new float[fArr2.length];
        for (int i = 0; i < fArr2.length; i++) {
            tintValueAnimator.min[i] = Float.MAX_VALUE;
            tintValueAnimator.max[i] = Float.MIN_VALUE;
        }
        tintController.mAnimator = tintValueAnimator;
        tintValueAnimator.setDuration(tintController.getTransitionDurationMilliseconds());
        tintValueAnimator.setInterpolator(AnimationUtils.loadInterpolator(colorDisplayService.getContext(), R.interpolator.fast_out_slow_in));
        tintValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.display.color.ColorDisplayService$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DisplayTransformManager displayTransformManager2 = DisplayTransformManager.this;
                TintController tintController2 = tintController;
                displayTransformManager2.setColorMatrix(tintController2.getLevel(), (float[]) valueAnimator.getAnimatedValue());
                ColorDisplayService.TintValueAnimator tintValueAnimator2 = (ColorDisplayService.TintValueAnimator) valueAnimator;
                float[] fArr3 = (float[]) tintValueAnimator2.getAnimatedValue();
                if (fArr3 == null) {
                    return;
                }
                for (int i2 = 0; i2 < fArr3.length; i2++) {
                    float[] fArr4 = tintValueAnimator2.min;
                    fArr4[i2] = Math.min(fArr4[i2], fArr3[i2]);
                    float[] fArr5 = tintValueAnimator2.max;
                    fArr5[i2] = Math.max(fArr5[i2], fArr3[i2]);
                }
            }
        });
        tintValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.server.display.color.ColorDisplayService.3
            public boolean mIsCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.mIsCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                TintValueAnimator tintValueAnimator2 = (TintValueAnimator) animator;
                Slog.d("ColorDisplayService", TintController.this.getClass().getSimpleName() + " Animation cancelled: " + this.mIsCancelled + " to matrix: " + TintController.matrixToString(16, matrix) + " min matrix coefficients: " + TintController.matrixToString(16, tintValueAnimator2.min) + " max matrix coefficients: " + TintController.matrixToString(16, tintValueAnimator2.max));
                if (!this.mIsCancelled) {
                    displayTransformManager.setColorMatrix(TintController.this.getLevel(), matrix);
                }
                TintController.this.mAnimator = null;
            }
        });
        tintValueAnimator.start();
    }

    /* renamed from: -$$Nest$mdumpInternal, reason: not valid java name */
    public static void m476$$Nest$mdumpInternal(ColorDisplayService colorDisplayService, PrintWriter printWriter) {
        colorDisplayService.getClass();
        printWriter.println("COLOR DISPLAY MANAGER dumpsys (color_display)");
        printWriter.println("Night display:");
        if (colorDisplayService.mNightDisplayTintController.isAvailable(colorDisplayService.getContext())) {
            printWriter.println("    Activated: " + colorDisplayService.mNightDisplayTintController.isActivated());
            StringBuilder sb = new StringBuilder("    Color temp: ");
            NightDisplayTintController nightDisplayTintController = colorDisplayService.mNightDisplayTintController;
            Integer num = nightDisplayTintController.mColorTemp;
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(sb, num != null ? nightDisplayTintController.clampNightDisplayColorTemperature(num.intValue()) : nightDisplayTintController.getColorTemperatureSetting(), printWriter);
        } else {
            printWriter.println("    Not available");
        }
        printWriter.println("Global saturation:");
        GlobalSaturationTintController globalSaturationTintController = colorDisplayService.mGlobalSaturationTintController;
        Context context = colorDisplayService.getContext();
        globalSaturationTintController.getClass();
        if (ColorDisplayManager.isColorTransformAccelerated(context)) {
            printWriter.println("    Activated: " + colorDisplayService.mGlobalSaturationTintController.isActivated());
        } else {
            printWriter.println("    Not available");
        }
        AppSaturationController appSaturationController = colorDisplayService.mAppSaturationController;
        synchronized (appSaturationController.mLock) {
            try {
                printWriter.println("App Saturation: ");
                if (((HashMap) appSaturationController.mAppsMap).size() == 0) {
                    printWriter.println("    No packages");
                } else {
                    ArrayList arrayList = new ArrayList(((HashMap) appSaturationController.mAppsMap).keySet());
                    Collections.sort(arrayList);
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        printWriter.println("    " + str + ":");
                        SparseArray sparseArray = (SparseArray) ((HashMap) appSaturationController.mAppsMap).get(str);
                        for (int i = 0; i < sparseArray.size(); i++) {
                            printWriter.println("        " + sparseArray.keyAt(i) + ":");
                            AppSaturationController.SaturationController.m474$$Nest$mdump((AppSaturationController.SaturationController) sparseArray.valueAt(i), printWriter);
                        }
                    }
                }
            } finally {
            }
        }
        printWriter.println("Display white balance:");
        if (colorDisplayService.mDisplayWhiteBalanceTintController.isAvailable(colorDisplayService.getContext())) {
            printWriter.println("    Activated: " + colorDisplayService.mDisplayWhiteBalanceTintController.isActivated());
            DisplayWhiteBalanceTintController displayWhiteBalanceTintController = colorDisplayService.mDisplayWhiteBalanceTintController;
            synchronized (displayWhiteBalanceTintController.mLock) {
                try {
                    printWriter.println("    mSetUp = " + displayWhiteBalanceTintController.mSetUp);
                    if (displayWhiteBalanceTintController.mSetUp) {
                        printWriter.println("    mTemperatureMin = " + displayWhiteBalanceTintController.mTemperatureMin);
                        printWriter.println("    mTemperatureMax = " + displayWhiteBalanceTintController.mTemperatureMax);
                        printWriter.println("    mTemperatureDefault = " + displayWhiteBalanceTintController.mTemperatureDefault);
                        printWriter.println("    mDisplayNominalWhiteCct = " + displayWhiteBalanceTintController.mDisplayNominalWhiteCct);
                        printWriter.println("    mCurrentColorTemperature = " + displayWhiteBalanceTintController.mCurrentColorTemperature);
                        printWriter.println("    mTargetCct = " + displayWhiteBalanceTintController.mTargetCct);
                        printWriter.println("    mAppliedCct = " + displayWhiteBalanceTintController.mAppliedCct);
                        printWriter.println("    mCurrentColorTemperatureXYZ = " + TintController.matrixToString(3, displayWhiteBalanceTintController.mCurrentColorTemperatureXYZ));
                        printWriter.println("    mDisplayColorSpaceRGB RGB-to-XYZ = " + TintController.matrixToString(3, displayWhiteBalanceTintController.mDisplayColorSpaceRGB.getTransform()));
                        printWriter.println("    mChromaticAdaptationMatrix = " + TintController.matrixToString(3, displayWhiteBalanceTintController.mChromaticAdaptationMatrix));
                        printWriter.println("    mDisplayColorSpaceRGB XYZ-to-RGB = " + TintController.matrixToString(3, displayWhiteBalanceTintController.mDisplayColorSpaceRGB.getInverseTransform()));
                        printWriter.println("    mMatrixDisplayWhiteBalance = " + TintController.matrixToString(4, displayWhiteBalanceTintController.mMatrixDisplayWhiteBalance));
                        printWriter.println("    mIsAllowed = " + displayWhiteBalanceTintController.mIsAllowed);
                        printWriter.println("    mTransitionDuration = " + displayWhiteBalanceTintController.mTransitionDuration);
                        printWriter.println("    mTransitionDurationIncrease = " + displayWhiteBalanceTintController.mTransitionDurationIncrease);
                        printWriter.println("    mTransitionDurationDecrease = " + displayWhiteBalanceTintController.mTransitionDurationDecrease);
                    }
                } finally {
                }
            }
        } else {
            printWriter.println("    Not available");
        }
        printWriter.println("Reduce bright colors:");
        ReduceBrightColorsTintController reduceBrightColorsTintController = colorDisplayService.mReduceBrightColorsTintController;
        Context context2 = colorDisplayService.getContext();
        reduceBrightColorsTintController.getClass();
        if (ColorDisplayManager.isColorTransformAccelerated(context2)) {
            printWriter.println("    Activated: " + colorDisplayService.mReduceBrightColorsTintController.isActivated());
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("    mStrength = "), colorDisplayService.mReduceBrightColorsTintController.mStrength, printWriter);
        } else {
            printWriter.println("    Not available");
        }
        printWriter.println("Color mode: " + colorDisplayService.getColorModeInternal());
    }

    /* renamed from: -$$Nest$mgetNightDisplayCustomEndTimeInternal, reason: not valid java name */
    public static Time m477$$Nest$mgetNightDisplayCustomEndTimeInternal(ColorDisplayService colorDisplayService) {
        int intForUser = Settings.Secure.getIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_custom_end_time", -1, colorDisplayService.mCurrentUser);
        if (intForUser == -1) {
            intForUser = colorDisplayService.getContext().getResources().getInteger(R.integer.config_displayWhiteBalanceIncreaseDebounce);
        }
        return new Time(LocalTime.ofSecondOfDay(intForUser / 1000));
    }

    /* renamed from: -$$Nest$mgetNightDisplayCustomStartTimeInternal, reason: not valid java name */
    public static Time m478$$Nest$mgetNightDisplayCustomStartTimeInternal(ColorDisplayService colorDisplayService) {
        int intForUser = Settings.Secure.getIntForUser(colorDisplayService.getContext().getContentResolver(), "night_display_custom_start_time", -1, colorDisplayService.mCurrentUser);
        if (intForUser == -1) {
            intForUser = colorDisplayService.getContext().getResources().getInteger(R.integer.config_displayWhiteBalanceTransitionTime);
        }
        return new Time(LocalTime.ofSecondOfDay(intForUser / 1000));
    }

    /* renamed from: -$$Nest$mgetNightDisplayLastActivatedTimeSetting, reason: not valid java name */
    public static LocalDateTime m479$$Nest$mgetNightDisplayLastActivatedTimeSetting(ColorDisplayService colorDisplayService) {
        String stringForUser = Settings.Secure.getStringForUser(colorDisplayService.getContext().getContentResolver(), "night_display_last_activated_time", colorDisplayService.getContext().getUserId());
        if (stringForUser != null) {
            try {
                try {
                    return LocalDateTime.parse(stringForUser);
                } catch (NumberFormatException | DateTimeException unused) {
                }
            } catch (DateTimeParseException unused2) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(stringForUser)), ZoneId.systemDefault());
            }
        }
        return LocalDateTime.MIN;
    }

    /* renamed from: -$$Nest$misDeviceColorManagedInternal, reason: not valid java name */
    public static boolean m480$$Nest$misDeviceColorManagedInternal(ColorDisplayService colorDisplayService) {
        ((DisplayTransformManager) colorDisplayService.getLocalService(DisplayTransformManager.class)).getClass();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        boolean z = false;
        try {
            try {
                DisplayTransformManager.sFlinger.transact(1030, obtain, obtain2, 0);
                z = obtain2.readBoolean();
            } catch (RemoteException e) {
                Slog.e("DisplayTransformManager", "Failed to query wide color support", e);
            }
            return z;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    /* renamed from: -$$Nest$msetColorModeInternal, reason: not valid java name */
    public static void m481$$Nest$msetColorModeInternal(ColorDisplayService colorDisplayService, int i) {
        if (!colorDisplayService.isColorModeAvailable(i)) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid colorMode: "));
        }
        Settings.System.putIntForUser(colorDisplayService.getContext().getContentResolver(), "display_color_mode", i, colorDisplayService.mCurrentUser);
    }

    static {
        float[] fArr = new float[16];
        MATRIX_IDENTITY = fArr;
        Matrix.setIdentityM(fArr, 0);
        COLOR_MATRIX_EVALUATOR = new ColorMatrixEvaluator();
        MATRIX_GRAYSCALE = new float[]{0.2126f, 0.2126f, 0.2126f, FullScreenMagnificationGestureHandler.MAX_SCALE, 0.7152f, 0.7152f, 0.7152f, FullScreenMagnificationGestureHandler.MAX_SCALE, 0.0722f, 0.0722f, 0.0722f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f};
        MATRIX_INVERT_COLOR = new float[]{0.402f, -0.598f, -0.599f, FullScreenMagnificationGestureHandler.MAX_SCALE, -1.174f, -0.174f, -1.175f, FullScreenMagnificationGestureHandler.MAX_SCALE, -0.228f, -0.228f, 0.772f, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f, 1.0f, 1.0f, 1.0f};
    }

    public ColorDisplayService(Context context) {
        super(context);
        this.mDisplayWhiteBalanceTintController = new DisplayWhiteBalanceTintController((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class), new DisplayManagerFlags());
        this.mNightDisplayTintController = new NightDisplayTintController();
        this.mGlobalSaturationTintController = new GlobalSaturationTintController();
        this.mReduceBrightColorsTintController = new ReduceBrightColorsTintController();
        this.mAppSaturationController = new AppSaturationController();
        this.mCurrentUser = -10000;
        this.mColorModeCompositionColorSpaces = null;
        this.mCctTintApplierLock = new Object();
        this.mHandler = new TintHandler(DisplayThread.get().getLooper());
    }

    public static LocalDateTime getDateTimeAfter(LocalTime localTime, LocalDateTime localDateTime) {
        LocalDateTime of = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), localTime.getHour(), localTime.getMinute());
        return of.isBefore(localDateTime) ? of.plusDays(1L) : of;
    }

    public static LocalDateTime getDateTimeBefore(LocalTime localTime, LocalDateTime localDateTime) {
        LocalDateTime of = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), localTime.getHour(), localTime.getMinute());
        return of.isAfter(localDateTime) ? of.minusDays(1L) : of;
    }

    public void cancelAllAnimators() {
        this.mNightDisplayTintController.cancelAnimator();
        this.mGlobalSaturationTintController.cancelAnimator();
        this.mReduceBrightColorsTintController.cancelAnimator();
        this.mDisplayWhiteBalanceTintController.cancelAnimator();
    }

    public final int getColorModeInternal() {
        int integer;
        ContentResolver contentResolver = getContext().getContentResolver();
        if ((Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_daltonizer_enabled", 0, this.mCurrentUser) != 0 || Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_inversion_enabled", 0, this.mCurrentUser) != 0) && (integer = getContext().getResources().getInteger(R.integer.config_audio_notif_vol_steps)) >= 0) {
            return integer;
        }
        int intForUser = Settings.System.getIntForUser(contentResolver, "display_color_mode", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = SystemProperties.getInt("persist.sys.sf.native_mode", 0);
            if (intForUser == 0) {
                intForUser = !ProfileUtilizationService.PU_VERSION.equals(SystemProperties.get("persist.sys.sf.color_saturation")) ? 1 : 0;
            } else if (intForUser == 1) {
                intForUser = 2;
            } else if (intForUser == 2) {
                intForUser = 3;
            } else if (intForUser < 256 || intForUser > 511) {
                intForUser = -1;
            }
        }
        if (isColorModeAvailable(intForUser)) {
            return intForUser;
        }
        int[] intArray = getContext().getResources().getIntArray(17236251);
        if (intForUser != -1 && intArray.length > intForUser && isColorModeAvailable(intArray[intForUser])) {
            return intArray[intForUser];
        }
        int[] intArray2 = getContext().getResources().getIntArray(R.array.config_notificationSignalExtractors);
        if (intArray2.length > 0) {
            return intArray2[0];
        }
        return -1;
    }

    public final int getNightDisplayAutoModeInternal() {
        int intForUser = this.mCurrentUser == -10000 ? -1 : Settings.Secure.getIntForUser(getContext().getContentResolver(), "night_display_auto_mode", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = getContext().getResources().getInteger(R.integer.config_displayWhiteBalanceDisplayNominalWhiteCct);
        }
        if (intForUser == 0 || intForUser == 1 || intForUser == 2) {
            return intForUser;
        }
        NandswapManager$$ExternalSyntheticOutline0.m(intForUser, "Invalid autoMode: ", "ColorDisplayService");
        return 0;
    }

    public final boolean isColorModeAvailable(int i) {
        int[] intArray = getContext().getResources().getIntArray(R.array.config_notificationSignalExtractors);
        if (intArray != null) {
            for (int i2 : intArray) {
                if (i2 == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isDisplayWhiteBalanceSettingEnabled() {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "display_white_balance_enabled", getContext().getResources().getBoolean(R.bool.config_dozeAfterScreenOffByDefault) ? 1 : 0, this.mCurrentUser) == 1;
    }

    public final void onAccessibilityDaltonizerChanged() {
        if (this.mCurrentUser == -10000) {
            return;
        }
        ContentResolver contentResolver = getContext().getContentResolver();
        int intForUser = Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_daltonizer_enabled", 0, this.mCurrentUser) != 0 ? Settings.Secure.getIntForUser(contentResolver, "accessibility_display_daltonizer", 12, this.mCurrentUser) : -1;
        int intForUser2 = Flags.enableColorCorrectionSaturation() ? Settings.Secure.getIntForUser(contentResolver, "accessibility_display_daltonizer_saturation_level", -1, this.mCurrentUser) : -1;
        DisplayTransformManager displayTransformManager = (DisplayTransformManager) getLocalService(DisplayTransformManager.class);
        if (intForUser == 0) {
            displayTransformManager.setColorMatrix(200, MATRIX_GRAYSCALE);
            displayTransformManager.setDaltonizerMode(-1, intForUser2);
        } else {
            displayTransformManager.setColorMatrix(200, null);
            displayTransformManager.setDaltonizerMode(intForUser, intForUser2);
        }
    }

    public final void onAccessibilityInversionChanged() {
        if (this.mCurrentUser == -10000) {
            return;
        }
        ((DisplayTransformManager) getLocalService(DisplayTransformManager.class)).setColorMatrix(300, Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_inversion_enabled", 0, this.mCurrentUser) != 0 ? MATRIX_INVERT_COLOR : null);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i >= 1000) {
            this.mBootCompleted = true;
            if (this.mCurrentUser == -10000 || this.mUserSetupObserver != null) {
                return;
            }
            this.mHandler.sendEmptyMessage(1);
        }
    }

    public final void onDisplayColorModeChanged(int i) {
        if (i == -1) {
            return;
        }
        NightDisplayTintController nightDisplayTintController = this.mNightDisplayTintController;
        nightDisplayTintController.cancelAnimator();
        this.mDisplayWhiteBalanceTintController.cancelAnimator();
        if (nightDisplayTintController.isAvailable(getContext())) {
            DisplayTransformManager displayTransformManager = (DisplayTransformManager) getLocalService(DisplayTransformManager.class);
            Context context = getContext();
            displayTransformManager.getClass();
            nightDisplayTintController.setUp(context, i != 2);
            nightDisplayTintController.setMatrix(nightDisplayTintController.getColorTemperatureSetting());
        }
        DisplayTransformManager displayTransformManager2 = (DisplayTransformManager) getLocalService(DisplayTransformManager.class);
        float[] matrix = nightDisplayTintController.getMatrix();
        SparseIntArray sparseIntArray = this.mColorModeCompositionColorSpaces;
        int i2 = sparseIntArray != null ? sparseIntArray.get(i, -1) : -1;
        displayTransformManager2.getClass();
        if (i == 0) {
            DisplayTransformManager.applySaturation(1.0f);
            DisplayTransformManager.setDisplayColor(0, i2);
        } else if (i == 1) {
            DisplayTransformManager.applySaturation(1.1f);
            DisplayTransformManager.setDisplayColor(0, i2);
        } else if (i == 2) {
            DisplayTransformManager.applySaturation(1.0f);
            DisplayTransformManager.setDisplayColor(1, i2);
        } else if (i == 3) {
            DisplayTransformManager.applySaturation(1.0f);
            DisplayTransformManager.setDisplayColor(2, i2);
        } else if (i >= 256 && i <= 511) {
            DisplayTransformManager.applySaturation(1.0f);
            DisplayTransformManager.setDisplayColor(i, i2);
        }
        displayTransformManager2.setColorMatrix(100, matrix);
        try {
            ActivityTaskManager.getService().updateConfiguration((Configuration) null);
        } catch (RemoteException e) {
            Slog.e("DisplayTransformManager", "Could not update configuration", e);
        }
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            updateDisplayWhiteBalanceStatus();
        }
    }

    public final void onNightDisplayAutoModeChanged(int i) {
        AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "onNightDisplayAutoModeChanged: autoMode=", "ColorDisplayService");
        NightDisplayAutoMode nightDisplayAutoMode = this.mNightDisplayAutoMode;
        if (nightDisplayAutoMode != null) {
            nightDisplayAutoMode.onStop();
            this.mNightDisplayAutoMode = null;
        }
        if (i == 1) {
            this.mNightDisplayAutoMode = new CustomNightDisplayAutoMode();
        } else if (i == 2) {
            this.mNightDisplayAutoMode = new TwilightNightDisplayAutoMode();
        }
        NightDisplayAutoMode nightDisplayAutoMode2 = this.mNightDisplayAutoMode;
        if (nightDisplayAutoMode2 != null) {
            nightDisplayAutoMode2.onStart();
        }
    }

    public final void onReduceBrightColorsActivationChanged() {
        if (this.mCurrentUser == -10000) {
            return;
        }
        this.mReduceBrightColorsTintController.setActivated(Boolean.valueOf(Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", 0, this.mCurrentUser) == 1));
    }

    public final void onReduceBrightColorsStrengthLevelChanged() {
        if (this.mCurrentUser == -10000) {
            return;
        }
        int intForUser = Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_level", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = getContext().getResources().getInteger(R.integer.config_sidefpsKeyguardPowerPressWindow);
        }
        this.mReduceBrightColorsTintController.setMatrix(intForUser);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("color_display", new BinderService());
        publishLocalService(ColorDisplayServiceInternal.class, new ColorDisplayServiceInternal());
        publishLocalService(DisplayTransformManager.class, new DisplayTransformManager());
    }

    public void onUserChanged(int i) {
        DisplayWhiteBalanceTintController displayWhiteBalanceTintController;
        ValueAnimator valueAnimator;
        final ContentResolver contentResolver = getContext().getContentResolver();
        if (this.mCurrentUser != -10000) {
            ContentObserver contentObserver = this.mUserSetupObserver;
            if (contentObserver != null) {
                contentResolver.unregisterContentObserver(contentObserver);
                this.mUserSetupObserver = null;
            } else if (this.mBootCompleted) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("tearDown: currentUser="), this.mCurrentUser, "ColorDisplayService");
                if (this.mContentObserver != null) {
                    getContext().getContentResolver().unregisterContentObserver(this.mContentObserver);
                }
                Context context = getContext();
                NightDisplayTintController nightDisplayTintController = this.mNightDisplayTintController;
                if (nightDisplayTintController.isAvailable(context)) {
                    NightDisplayAutoMode nightDisplayAutoMode = this.mNightDisplayAutoMode;
                    if (nightDisplayAutoMode != null) {
                        nightDisplayAutoMode.onStop();
                        this.mNightDisplayAutoMode = null;
                    }
                    ValueAnimator valueAnimator2 = nightDisplayTintController.mAnimator;
                    if (valueAnimator2 != null) {
                        valueAnimator2.end();
                        nightDisplayTintController.mAnimator = null;
                    }
                }
                if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext()) && (valueAnimator = (displayWhiteBalanceTintController = this.mDisplayWhiteBalanceTintController).mAnimator) != null) {
                    valueAnimator.end();
                    displayWhiteBalanceTintController.mAnimator = null;
                }
                Context context2 = getContext();
                GlobalSaturationTintController globalSaturationTintController = this.mGlobalSaturationTintController;
                globalSaturationTintController.getClass();
                if (ColorDisplayManager.isColorTransformAccelerated(context2)) {
                    globalSaturationTintController.mIsActivated = null;
                }
                Context context3 = getContext();
                ReduceBrightColorsTintController reduceBrightColorsTintController = this.mReduceBrightColorsTintController;
                reduceBrightColorsTintController.getClass();
                if (ColorDisplayManager.isColorTransformAccelerated(context3)) {
                    reduceBrightColorsTintController.setActivated(null);
                }
            }
        }
        this.mCurrentUser = i;
        if (i != -10000) {
            if (Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, i) != 1) {
                this.mUserSetupObserver = new ContentObserver(this.mHandler) { // from class: com.android.server.display.color.ColorDisplayService.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        if (Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, ColorDisplayService.this.mCurrentUser) == 1) {
                            contentResolver.unregisterContentObserver(this);
                            ColorDisplayService colorDisplayService = ColorDisplayService.this;
                            colorDisplayService.mUserSetupObserver = null;
                            if (colorDisplayService.mBootCompleted) {
                                colorDisplayService.setUp();
                            }
                        }
                    }
                };
                contentResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this.mUserSetupObserver, this.mCurrentUser);
            } else if (this.mBootCompleted) {
                setUp();
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        if (this.mCurrentUser == -10000) {
            Message obtainMessage = this.mHandler.obtainMessage(0);
            obtainMessage.arg1 = targetUser.getUserIdentifier();
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        if (this.mCurrentUser == targetUser.getUserIdentifier()) {
            Message obtainMessage = this.mHandler.obtainMessage(0);
            obtainMessage.arg1 = -10000;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        Message obtainMessage = this.mHandler.obtainMessage(0);
        obtainMessage.arg1 = targetUser2.getUserIdentifier();
        this.mHandler.sendMessage(obtainMessage);
    }

    public final boolean setAppSaturationLevelInternal(int i, String str, String str2) {
        boolean updateState;
        AppSaturationController appSaturationController = this.mAppSaturationController;
        int i2 = this.mCurrentUser;
        synchronized (appSaturationController.mLock) {
            AppSaturationController.SaturationController saturationControllerLocked = appSaturationController.getSaturationControllerLocked(i2, str2);
            if (i == 100) {
                saturationControllerLocked.mSaturationLevels.remove(str);
            } else {
                saturationControllerLocked.mSaturationLevels.put(str, Integer.valueOf(i));
            }
            updateState = !((ArrayList) saturationControllerLocked.mControllerRefs).isEmpty() ? saturationControllerLocked.updateState() : false;
        }
        return updateState;
    }

    /* JADX WARN: Type inference failed for: r7v32, types: [com.android.server.display.color.ColorDisplayService$2] */
    public final void setUp() {
        DisplayTransformManager displayTransformManager;
        SurfaceControl.CieXyz cieXyz;
        SurfaceControl.CieXyz cieXyz2;
        SurfaceControl.CieXyz cieXyz3;
        SurfaceControl.CieXyz cieXyz4;
        int[] intArray;
        boolean z = false;
        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("setUp: currentUser="), this.mCurrentUser, "ColorDisplayService");
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(this.mHandler) { // from class: com.android.server.display.color.ColorDisplayService.2
                @Override // android.database.ContentObserver
                public final void onChange(boolean z2, Uri uri) {
                    super.onChange(z2, uri);
                    String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                    if (lastPathSegment != null) {
                        switch (lastPathSegment) {
                            case "night_display_auto_mode":
                                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                                colorDisplayService.onNightDisplayAutoModeChanged(colorDisplayService.getNightDisplayAutoModeInternal());
                                break;
                            case "night_display_custom_end_time":
                                ColorDisplayService colorDisplayService2 = ColorDisplayService.this;
                                LocalTime localTime = ColorDisplayService.m477$$Nest$mgetNightDisplayCustomEndTimeInternal(colorDisplayService2).getLocalTime();
                                Slog.d("ColorDisplayService", "onNightDisplayCustomEndTimeChanged: endTime=" + localTime);
                                NightDisplayAutoMode nightDisplayAutoMode = colorDisplayService2.mNightDisplayAutoMode;
                                if (nightDisplayAutoMode != null) {
                                    nightDisplayAutoMode.onCustomEndTimeChanged(localTime);
                                    break;
                                }
                                break;
                            case "night_display_color_temperature":
                                int colorTemperatureSetting = ColorDisplayService.this.mNightDisplayTintController.getColorTemperatureSetting();
                                NightDisplayTintController nightDisplayTintController = ColorDisplayService.this.mNightDisplayTintController;
                                Integer num = nightDisplayTintController.mColorTemp;
                                if ((num != null ? nightDisplayTintController.clampNightDisplayColorTemperature(num.intValue()) : nightDisplayTintController.getColorTemperatureSetting()) != colorTemperatureSetting) {
                                    NightDisplayTintController nightDisplayTintController2 = ColorDisplayService.this.mNightDisplayTintController;
                                    nightDisplayTintController2.setMatrix(colorTemperatureSetting);
                                    ColorDisplayService.this.mHandler.sendEmptyMessage(2);
                                    break;
                                }
                                break;
                            case "accessibility_display_daltonizer_enabled":
                                ColorDisplayService.this.onAccessibilityDaltonizerChanged();
                                ColorDisplayService colorDisplayService3 = ColorDisplayService.this;
                                colorDisplayService3.onDisplayColorModeChanged(colorDisplayService3.getColorModeInternal());
                                break;
                            case "accessibility_display_inversion_enabled":
                                ColorDisplayService.this.onAccessibilityInversionChanged();
                                ColorDisplayService colorDisplayService4 = ColorDisplayService.this;
                                colorDisplayService4.onDisplayColorModeChanged(colorDisplayService4.getColorModeInternal());
                                break;
                            case "accessibility_display_daltonizer_saturation_level":
                                if (Flags.enableColorCorrectionSaturation()) {
                                    ColorDisplayService.this.onAccessibilityDaltonizerChanged();
                                    break;
                                }
                                break;
                            case "accessibility_display_daltonizer":
                                ColorDisplayService.this.onAccessibilityDaltonizerChanged();
                                break;
                            case "night_display_activated":
                                boolean isActivatedSetting = ColorDisplayService.this.mNightDisplayTintController.isActivatedSetting();
                                if (ColorDisplayService.this.mNightDisplayTintController.isActivatedStateNotSet() || ColorDisplayService.this.mNightDisplayTintController.isActivated() != isActivatedSetting) {
                                    NightDisplayTintController nightDisplayTintController3 = ColorDisplayService.this.mNightDisplayTintController;
                                    Boolean valueOf = Boolean.valueOf(isActivatedSetting);
                                    nightDisplayTintController3.getClass();
                                    nightDisplayTintController3.setActivated(valueOf, LocalDateTime.now());
                                    break;
                                }
                                break;
                            case "display_white_balance_enabled":
                                ColorDisplayService.this.updateDisplayWhiteBalanceStatus();
                                break;
                            case "reduce_bright_colors_level":
                                ColorDisplayService.this.onReduceBrightColorsStrengthLevelChanged();
                                ColorDisplayService.this.mHandler.sendEmptyMessage(6);
                                break;
                            case "display_color_mode":
                                ColorDisplayService colorDisplayService5 = ColorDisplayService.this;
                                colorDisplayService5.onDisplayColorModeChanged(colorDisplayService5.getColorModeInternal());
                                break;
                            case "night_display_custom_start_time":
                                ColorDisplayService colorDisplayService6 = ColorDisplayService.this;
                                LocalTime localTime2 = ColorDisplayService.m478$$Nest$mgetNightDisplayCustomStartTimeInternal(colorDisplayService6).getLocalTime();
                                Slog.d("ColorDisplayService", "onNightDisplayCustomStartTimeChanged: startTime=" + localTime2);
                                NightDisplayAutoMode nightDisplayAutoMode2 = colorDisplayService6.mNightDisplayAutoMode;
                                if (nightDisplayAutoMode2 != null) {
                                    nightDisplayAutoMode2.onCustomStartTimeChanged(localTime2);
                                    break;
                                }
                                break;
                            case "reduce_bright_colors_activated":
                                ColorDisplayService.this.onReduceBrightColorsActivationChanged();
                                ColorDisplayService.this.mHandler.sendEmptyMessage(6);
                                break;
                        }
                    }
                }
            };
        }
        ContentResolver contentResolver = getContext().getContentResolver();
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("night_display_activated"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("night_display_color_temperature"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("night_display_auto_mode"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("night_display_custom_start_time"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("night_display_custom_end_time"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.System.getUriFor("display_color_mode"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("accessibility_display_inversion_enabled"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("accessibility_display_daltonizer_enabled"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("accessibility_display_daltonizer"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("display_white_balance_enabled"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("reduce_bright_colors_activated"), false, this.mContentObserver, this.mCurrentUser);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("reduce_bright_colors_level"), false, this.mContentObserver, this.mCurrentUser);
        if (Flags.enableColorCorrectionSaturation()) {
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("accessibility_display_daltonizer_saturation_level"), false, this.mContentObserver, this.mCurrentUser);
        }
        onAccessibilityInversionChanged();
        onAccessibilityDaltonizerChanged();
        Resources resources = getContext().getResources();
        ColorSpace.Rgb rgb = null;
        this.mColorModeCompositionColorSpaces = null;
        int[] intArray2 = resources.getIntArray(R.array.config_toastCrossUserPackages);
        if (intArray2 != null && (intArray = resources.getIntArray(R.array.config_trustedAccessibilityServices)) != null) {
            if (intArray2.length != intArray.length) {
                Slog.e("ColorDisplayService", "Number of composition color spaces doesn't match specified color modes");
            } else {
                this.mColorModeCompositionColorSpaces = new SparseIntArray(intArray2.length);
                for (int i = 0; i < intArray2.length; i++) {
                    this.mColorModeCompositionColorSpaces.put(intArray2[i], intArray[i]);
                }
            }
        }
        onDisplayColorModeChanged(getColorModeInternal());
        DisplayTransformManager displayTransformManager2 = (DisplayTransformManager) getLocalService(DisplayTransformManager.class);
        if (this.mNightDisplayTintController.isAvailable(getContext())) {
            NightDisplayTintController nightDisplayTintController = this.mNightDisplayTintController;
            nightDisplayTintController.getClass();
            nightDisplayTintController.setActivated(null, LocalDateTime.now());
            NightDisplayTintController nightDisplayTintController2 = this.mNightDisplayTintController;
            Context context = getContext();
            displayTransformManager2.getClass();
            nightDisplayTintController2.setUp(context, SystemProperties.getInt("persist.sys.sf.native_mode", 1) != 1);
            NightDisplayTintController nightDisplayTintController3 = this.mNightDisplayTintController;
            nightDisplayTintController3.setMatrix(nightDisplayTintController3.getColorTemperatureSetting());
            onNightDisplayAutoModeChanged(getNightDisplayAutoModeInternal());
            if (this.mNightDisplayTintController.isActivatedStateNotSet()) {
                NightDisplayTintController nightDisplayTintController4 = this.mNightDisplayTintController;
                nightDisplayTintController4.setActivated(Boolean.valueOf(nightDisplayTintController4.isActivatedSetting()), LocalDateTime.now());
            }
        }
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            DisplayWhiteBalanceTintController displayWhiteBalanceTintController = this.mDisplayWhiteBalanceTintController;
            Context context2 = getContext();
            displayWhiteBalanceTintController.mSetUp = false;
            Resources resources2 = context2.getResources();
            displayWhiteBalanceTintController.mIsAllowed = resources2.getBoolean(R.bool.config_dozeAlwaysOnDisplayAvailable);
            SurfaceControl.DisplayPrimaries displayNativePrimaries = displayWhiteBalanceTintController.mDisplayManagerInternal.getDisplayNativePrimaries(0);
            if (displayNativePrimaries == null || (cieXyz = displayNativePrimaries.red) == null || (cieXyz2 = displayNativePrimaries.green) == null || (cieXyz3 = displayNativePrimaries.blue) == null || (cieXyz4 = displayNativePrimaries.white) == null) {
                displayTransformManager = displayTransformManager2;
            } else {
                displayTransformManager = displayTransformManager2;
                rgb = new ColorSpace.Rgb("Display Color Space", new float[]{cieXyz.X, cieXyz.Y, cieXyz.Z, cieXyz2.X, cieXyz2.Y, cieXyz2.Z, cieXyz3.X, cieXyz3.Y, cieXyz3.Z}, new float[]{cieXyz4.X, cieXyz4.Y, cieXyz4.Z}, 2.200000047683716d);
            }
            if (rgb == null) {
                Slog.w("ColorDisplayService", "Failed to get display color space from SurfaceControl, trying res");
                String[] stringArray = resources2.getStringArray(R.array.config_wearActivityModeRadios);
                float[] fArr = new float[9];
                float[] fArr2 = new float[3];
                for (int i2 = 0; i2 < 9; i2++) {
                    fArr[i2] = Float.parseFloat(stringArray[i2]);
                }
                int i3 = 0;
                for (int i4 = 3; i3 < i4; i4 = 3) {
                    fArr2[i3] = Float.parseFloat(stringArray[9 + i3]);
                    i3++;
                }
                rgb = new ColorSpace.Rgb("Display Color Space", fArr, fArr2, 2.200000047683716d);
            }
            float[] transform = rgb.getTransform();
            if (transform != null && transform.length == 9) {
                for (float f : transform) {
                    if (!Float.isNaN(f) && !Float.isInfinite(f)) {
                    }
                }
                float[] inverseTransform = rgb.getInverseTransform();
                if (inverseTransform != null && inverseTransform.length == 9) {
                    for (float f2 : inverseTransform) {
                        if (!Float.isNaN(f2) && !Float.isInfinite(f2)) {
                        }
                    }
                    String[] stringArray2 = resources2.getStringArray(R.array.config_waterfallCutoutArray);
                    float[] fArr3 = new float[3];
                    for (int i5 = 0; i5 < stringArray2.length; i5++) {
                        fArr3[i5] = Float.parseFloat(stringArray2[i5]);
                    }
                    int integer = resources2.getInteger(R.integer.config_fixedRefreshRateInHighZone);
                    int integer2 = resources2.getInteger(R.integer.config_extraFreeKbytesAdjust);
                    if (integer2 <= 0) {
                        Slog.e("ColorDisplayService", "Display white balance minimum temperature must be greater than 0");
                    } else {
                        int integer3 = resources2.getInteger(R.integer.config_extraFreeKbytesAbsolute);
                        if (integer3 < integer2) {
                            Slog.e("ColorDisplayService", "Display white balance max temp must be greater or equal to min");
                        } else {
                            int integer4 = resources2.getInteger(R.integer.config_externalDisplayPeakRefreshRate);
                            displayWhiteBalanceTintController.mTransitionDuration = resources2.getInteger(R.integer.config_globalActionsKeyTimeout);
                            if (displayWhiteBalanceTintController.mDisplayManagerFlags.mAdaptiveToneImprovements2.isEnabled()) {
                                displayWhiteBalanceTintController.mTransitionDurationIncrease = resources2.getInteger(R.integer.config_immersive_mode_confirmation_panic);
                                displayWhiteBalanceTintController.mTransitionDurationDecrease = resources2.getInteger(R.integer.config_hotwordDetectedResultMaxBundleSize);
                            } else {
                                long j = displayWhiteBalanceTintController.mTransitionDuration;
                                displayWhiteBalanceTintController.mTransitionDurationDecrease = j;
                                displayWhiteBalanceTintController.mTransitionDurationIncrease = j;
                            }
                            int[] intArray3 = resources2.getIntArray(R.array.crossSimSpnFormats);
                            int[] intArray4 = resources2.getIntArray(R.array.cross_profile_apps);
                            synchronized (displayWhiteBalanceTintController.mLock) {
                                displayWhiteBalanceTintController.mDisplayColorSpaceRGB = rgb;
                                displayWhiteBalanceTintController.mDisplayNominalWhiteXYZ = fArr3;
                                displayWhiteBalanceTintController.mDisplayNominalWhiteCct = integer;
                                displayWhiteBalanceTintController.mTargetCct = integer;
                                displayWhiteBalanceTintController.mAppliedCct = integer;
                                displayWhiteBalanceTintController.mTemperatureMin = integer2;
                                displayWhiteBalanceTintController.mTemperatureMax = integer3;
                                displayWhiteBalanceTintController.mTemperatureDefault = integer4;
                                displayWhiteBalanceTintController.mSetUp = true;
                                displayWhiteBalanceTintController.mCctEvaluator = new CctEvaluator(integer2, integer3, intArray3, intArray4);
                            }
                            int i6 = displayWhiteBalanceTintController.mTemperatureDefault;
                            if (!displayWhiteBalanceTintController.mSetUp) {
                                Slog.w("ColorDisplayService", "Can't set display white balance temperature: uninitialized");
                            } else if (i6 < displayWhiteBalanceTintController.mTemperatureMin) {
                                Slog.w("ColorDisplayService", "Requested display color temperature is below allowed minimum");
                                displayWhiteBalanceTintController.mTargetCct = displayWhiteBalanceTintController.mTemperatureMin;
                            } else if (i6 > displayWhiteBalanceTintController.mTemperatureMax) {
                                Slog.w("ColorDisplayService", "Requested display color temperature is above allowed maximum");
                                displayWhiteBalanceTintController.mTargetCct = displayWhiteBalanceTintController.mTemperatureMax;
                            } else {
                                displayWhiteBalanceTintController.mTargetCct = i6;
                            }
                            displayWhiteBalanceTintController.computeMatrixForCct(displayWhiteBalanceTintController.mTargetCct);
                        }
                    }
                    updateDisplayWhiteBalanceStatus();
                }
                Slog.e("ColorDisplayService", "Invalid display color space XYZ-to-RGB transform");
                updateDisplayWhiteBalanceStatus();
            }
            Slog.e("ColorDisplayService", "Invalid display color space RGB-to-XYZ transform");
            updateDisplayWhiteBalanceStatus();
        } else {
            displayTransformManager = displayTransformManager2;
        }
        ReduceBrightColorsTintController reduceBrightColorsTintController = this.mReduceBrightColorsTintController;
        Context context3 = getContext();
        reduceBrightColorsTintController.getClass();
        if (ColorDisplayManager.isColorTransformAccelerated(context3)) {
            ReduceBrightColorsTintController reduceBrightColorsTintController2 = this.mReduceBrightColorsTintController;
            Context context4 = getContext();
            displayTransformManager.getClass();
            boolean z2 = SystemProperties.getInt("persist.sys.sf.native_mode", 1) != 1;
            reduceBrightColorsTintController2.getClass();
            String[] stringArray3 = context4.getResources().getStringArray(z2 ? 17236287 : 17236288);
            for (int i7 = 0; i7 < 3 && i7 < stringArray3.length; i7++) {
                reduceBrightColorsTintController2.mCoefficients[i7] = Float.parseFloat(stringArray3[i7]);
            }
            onReduceBrightColorsStrengthLevelChanged();
            if (this.mCurrentUser != -10000) {
                boolean z3 = Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", 0, this.mCurrentUser) == 1;
                boolean z4 = Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_persist_across_reboots", 0, this.mCurrentUser) == 1;
                if (z3 && this.mReduceBrightColorsTintController.isActivatedStateNotSet() && z4) {
                    z = Settings.Secure.putIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", 0, this.mCurrentUser);
                }
            }
            if (z) {
                return;
            }
            onReduceBrightColorsActivationChanged();
            this.mHandler.sendEmptyMessage(6);
        }
    }

    public void updateDisplayWhiteBalanceStatus() {
        boolean isActivated = this.mDisplayWhiteBalanceTintController.isActivated();
        DisplayTransformManager displayTransformManager = (DisplayTransformManager) getLocalService(DisplayTransformManager.class);
        DisplayWhiteBalanceTintController displayWhiteBalanceTintController = this.mDisplayWhiteBalanceTintController;
        boolean z = false;
        if (isDisplayWhiteBalanceSettingEnabled() && !this.mNightDisplayTintController.isActivated() && Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_daltonizer_enabled", 0, this.mCurrentUser) == 0 && Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_inversion_enabled", 0, this.mCurrentUser) == 0) {
            displayTransformManager.getClass();
            if (SystemProperties.getInt("persist.sys.sf.native_mode", 1) != 1 && this.mDisplayWhiteBalanceTintController.mIsAllowed) {
                z = true;
            }
        }
        displayWhiteBalanceTintController.mIsActivated = Boolean.valueOf(z);
        boolean isActivated2 = this.mDisplayWhiteBalanceTintController.isActivated();
        if (!isActivated || isActivated2) {
            return;
        }
        this.mHandler.sendEmptyMessage(5);
    }
}
