package com.android.server.display.color;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p000pm.PackageManagerInternal;
import android.content.res.Resources;
import android.database.ContentObserver;
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
import android.os.ParcelFileDescriptor;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.util.SparseIntArray;
import android.view.SurfaceControl;
import android.view.animation.AnimationUtils;
import com.android.internal.util.DumpUtils;
import com.android.server.DisplayThread;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.display.color.ColorDisplayService;
import com.android.server.twilight.TwilightListener;
import com.android.server.twilight.TwilightManager;
import com.android.server.twilight.TwilightState;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

/* loaded from: classes2.dex */
public final class ColorDisplayService extends SystemService {
    public static final ColorMatrixEvaluator COLOR_MATRIX_EVALUATOR;
    public static final float[] MATRIX_GRAYSCALE;
    public static final float[] MATRIX_IDENTITY;
    public static final float[] MATRIX_INVERT_COLOR;
    public final AppSaturationController mAppSaturationController;
    public boolean mBootCompleted;
    public final Object mCctTintApplierLock;
    public SparseIntArray mColorModeCompositionColorSpaces;
    public ContentObserver mContentObserver;
    public int mCurrentUser;
    final DisplayWhiteBalanceTintController mDisplayWhiteBalanceTintController;
    public final TintController mGlobalSaturationTintController;
    final Handler mHandler;
    public NightDisplayAutoMode mNightDisplayAutoMode;
    public final NightDisplayTintController mNightDisplayTintController;
    public final ReduceBrightColorsTintController mReduceBrightColorsTintController;
    public ContentObserver mUserSetupObserver;

    public interface ColorTransformController {
        void applyAppSaturation(float[] fArr, float[] fArr2);
    }

    static {
        float[] fArr = new float[16];
        MATRIX_IDENTITY = fArr;
        Matrix.setIdentityM(fArr, 0);
        COLOR_MATRIX_EVALUATOR = new ColorMatrixEvaluator();
        MATRIX_GRAYSCALE = new float[]{0.2126f, 0.2126f, 0.2126f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 0.7152f, 0.7152f, 0.7152f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 0.0722f, 0.0722f, 0.0722f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f};
        MATRIX_INVERT_COLOR = new float[]{0.402f, -0.598f, -0.599f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -1.174f, -0.174f, -1.175f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -0.228f, -0.228f, 0.772f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f, 1.0f, 1.0f, 1.0f};
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ColorDisplayService(Context context) {
        super(context);
        this.mDisplayWhiteBalanceTintController = new DisplayWhiteBalanceTintController((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class));
        this.mNightDisplayTintController = new NightDisplayTintController();
        this.mGlobalSaturationTintController = new GlobalSaturationTintController();
        this.mReduceBrightColorsTintController = new ReduceBrightColorsTintController();
        this.mAppSaturationController = new AppSaturationController();
        this.mCurrentUser = -10000;
        this.mColorModeCompositionColorSpaces = null;
        this.mCctTintApplierLock = new Object();
        this.mHandler = new TintHandler(DisplayThread.get().getLooper());
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("color_display", new BinderService());
        publishLocalService(ColorDisplayServiceInternal.class, new ColorDisplayServiceInternal());
        publishLocalService(DisplayTransformManager.class, new DisplayTransformManager());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i >= 1000) {
            this.mBootCompleted = true;
            if (this.mCurrentUser == -10000 || this.mUserSetupObserver != null) {
                return;
            }
            this.mHandler.sendEmptyMessage(1);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(SystemService.TargetUser targetUser) {
        if (this.mCurrentUser == -10000) {
            Message obtainMessage = this.mHandler.obtainMessage(0);
            obtainMessage.arg1 = targetUser.getUserIdentifier();
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        Message obtainMessage = this.mHandler.obtainMessage(0);
        obtainMessage.arg1 = targetUser2.getUserIdentifier();
        this.mHandler.sendMessage(obtainMessage);
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(SystemService.TargetUser targetUser) {
        if (this.mCurrentUser == targetUser.getUserIdentifier()) {
            Message obtainMessage = this.mHandler.obtainMessage(0);
            obtainMessage.arg1 = -10000;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void onUserChanged(int i) {
        final ContentResolver contentResolver = getContext().getContentResolver();
        if (this.mCurrentUser != -10000) {
            ContentObserver contentObserver = this.mUserSetupObserver;
            if (contentObserver != null) {
                contentResolver.unregisterContentObserver(contentObserver);
                this.mUserSetupObserver = null;
            } else if (this.mBootCompleted) {
                tearDown();
            }
        }
        this.mCurrentUser = i;
        if (i != -10000) {
            if (!isUserSetupCompleted(contentResolver, i)) {
                this.mUserSetupObserver = new ContentObserver(this.mHandler) { // from class: com.android.server.display.color.ColorDisplayService.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z, Uri uri) {
                        if (ColorDisplayService.isUserSetupCompleted(contentResolver, ColorDisplayService.this.mCurrentUser)) {
                            contentResolver.unregisterContentObserver(this);
                            ColorDisplayService.this.mUserSetupObserver = null;
                            if (ColorDisplayService.this.mBootCompleted) {
                                ColorDisplayService.this.setUp();
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

    public static boolean isUserSetupCompleted(ContentResolver contentResolver, int i) {
        return Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, i) == 1;
    }

    public final void setUpDisplayCompositionColorSpaces(Resources resources) {
        int[] intArray;
        this.mColorModeCompositionColorSpaces = null;
        int[] intArray2 = resources.getIntArray(R.array.config_tether_dhcp_range);
        if (intArray2 == null || (intArray = resources.getIntArray(R.array.config_tether_upstream_types)) == null) {
            return;
        }
        if (intArray2.length != intArray.length) {
            Slog.e("ColorDisplayService", "Number of composition color spaces doesn't match specified color modes");
            return;
        }
        this.mColorModeCompositionColorSpaces = new SparseIntArray(intArray2.length);
        for (int i = 0; i < intArray2.length; i++) {
            this.mColorModeCompositionColorSpaces.put(intArray2[i], intArray[i]);
        }
    }

    public final void setUp() {
        Slog.d("ColorDisplayService", "setUp: currentUser=" + this.mCurrentUser);
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(this.mHandler) { // from class: com.android.server.display.color.ColorDisplayService.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z, Uri uri) {
                    super.onChange(z, uri);
                    String lastPathSegment = uri == null ? null : uri.getLastPathSegment();
                    if (lastPathSegment != null) {
                        switch (lastPathSegment) {
                            case "night_display_auto_mode":
                                ColorDisplayService colorDisplayService = ColorDisplayService.this;
                                colorDisplayService.onNightDisplayAutoModeChanged(colorDisplayService.getNightDisplayAutoModeInternal());
                                break;
                            case "night_display_custom_end_time":
                                ColorDisplayService colorDisplayService2 = ColorDisplayService.this;
                                colorDisplayService2.onNightDisplayCustomEndTimeChanged(colorDisplayService2.getNightDisplayCustomEndTimeInternal().getLocalTime());
                                break;
                            case "night_display_color_temperature":
                                int colorTemperatureSetting = ColorDisplayService.this.mNightDisplayTintController.getColorTemperatureSetting();
                                if (ColorDisplayService.this.mNightDisplayTintController.getColorTemperature() != colorTemperatureSetting) {
                                    ColorDisplayService.this.mNightDisplayTintController.onColorTemperatureChanged(colorTemperatureSetting);
                                    break;
                                }
                                break;
                            case "accessibility_display_daltonizer_enabled":
                                ColorDisplayService.this.onAccessibilityDaltonizerChanged();
                                ColorDisplayService.this.onAccessibilityActivated();
                                break;
                            case "accessibility_display_inversion_enabled":
                                ColorDisplayService.this.onAccessibilityInversionChanged();
                                ColorDisplayService.this.onAccessibilityActivated();
                                break;
                            case "accessibility_display_daltonizer":
                                ColorDisplayService.this.onAccessibilityDaltonizerChanged();
                                break;
                            case "night_display_activated":
                                boolean isActivatedSetting = ColorDisplayService.this.mNightDisplayTintController.isActivatedSetting();
                                if (ColorDisplayService.this.mNightDisplayTintController.isActivatedStateNotSet() || ColorDisplayService.this.mNightDisplayTintController.isActivated() != isActivatedSetting) {
                                    ColorDisplayService.this.mNightDisplayTintController.setActivated(Boolean.valueOf(isActivatedSetting));
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
                                ColorDisplayService colorDisplayService3 = ColorDisplayService.this;
                                colorDisplayService3.onDisplayColorModeChanged(colorDisplayService3.getColorModeInternal());
                                break;
                            case "night_display_custom_start_time":
                                ColorDisplayService colorDisplayService4 = ColorDisplayService.this;
                                colorDisplayService4.onNightDisplayCustomStartTimeChanged(colorDisplayService4.getNightDisplayCustomStartTimeInternal().getLocalTime());
                                break;
                            case "reduce_bright_colors_activated":
                                ColorDisplayService.this.onReduceBrightColorsActivationChanged(true);
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
        onAccessibilityInversionChanged();
        onAccessibilityDaltonizerChanged();
        setUpDisplayCompositionColorSpaces(getContext().getResources());
        onDisplayColorModeChanged(getColorModeInternal());
        DisplayTransformManager displayTransformManager = (DisplayTransformManager) getLocalService(DisplayTransformManager.class);
        if (this.mNightDisplayTintController.isAvailable(getContext())) {
            this.mNightDisplayTintController.setActivated(null);
            this.mNightDisplayTintController.setUp(getContext(), displayTransformManager.needsLinearColorMatrix());
            NightDisplayTintController nightDisplayTintController = this.mNightDisplayTintController;
            nightDisplayTintController.setMatrix(nightDisplayTintController.getColorTemperatureSetting());
            onNightDisplayAutoModeChanged(getNightDisplayAutoModeInternal());
            if (this.mNightDisplayTintController.isActivatedStateNotSet()) {
                NightDisplayTintController nightDisplayTintController2 = this.mNightDisplayTintController;
                nightDisplayTintController2.setActivated(Boolean.valueOf(nightDisplayTintController2.isActivatedSetting()));
            }
        }
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            this.mDisplayWhiteBalanceTintController.setUp(getContext(), true);
            updateDisplayWhiteBalanceStatus();
        }
        if (this.mReduceBrightColorsTintController.isAvailable(getContext())) {
            this.mReduceBrightColorsTintController.setUp(getContext(), displayTransformManager.needsLinearColorMatrix());
            onReduceBrightColorsStrengthLevelChanged();
            if (resetReduceBrightColors()) {
                return;
            }
            onReduceBrightColorsActivationChanged(false);
            this.mHandler.sendEmptyMessage(6);
        }
    }

    public final void tearDown() {
        Slog.d("ColorDisplayService", "tearDown: currentUser=" + this.mCurrentUser);
        if (this.mContentObserver != null) {
            getContext().getContentResolver().unregisterContentObserver(this.mContentObserver);
        }
        if (this.mNightDisplayTintController.isAvailable(getContext())) {
            NightDisplayAutoMode nightDisplayAutoMode = this.mNightDisplayAutoMode;
            if (nightDisplayAutoMode != null) {
                nightDisplayAutoMode.onStop();
                this.mNightDisplayAutoMode = null;
            }
            this.mNightDisplayTintController.endAnimator();
        }
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            this.mDisplayWhiteBalanceTintController.endAnimator();
        }
        if (this.mGlobalSaturationTintController.isAvailable(getContext())) {
            this.mGlobalSaturationTintController.setActivated(null);
        }
        if (this.mReduceBrightColorsTintController.isAvailable(getContext())) {
            this.mReduceBrightColorsTintController.setActivated(null);
        }
    }

    public final boolean resetReduceBrightColors() {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        boolean z = Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", 0, this.mCurrentUser) == 1;
        boolean z2 = Settings.Secure.getIntForUser(getContext().getContentResolver(), "reduce_bright_colors_persist_across_reboots", 0, this.mCurrentUser) == 1;
        if (z && this.mReduceBrightColorsTintController.isActivatedStateNotSet() && z2) {
            return Settings.Secure.putIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", 0, this.mCurrentUser);
        }
        return false;
    }

    public final void onNightDisplayAutoModeChanged(int i) {
        Slog.d("ColorDisplayService", "onNightDisplayAutoModeChanged: autoMode=" + i);
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

    public final void onNightDisplayCustomStartTimeChanged(LocalTime localTime) {
        Slog.d("ColorDisplayService", "onNightDisplayCustomStartTimeChanged: startTime=" + localTime);
        NightDisplayAutoMode nightDisplayAutoMode = this.mNightDisplayAutoMode;
        if (nightDisplayAutoMode != null) {
            nightDisplayAutoMode.onCustomStartTimeChanged(localTime);
        }
    }

    public final void onNightDisplayCustomEndTimeChanged(LocalTime localTime) {
        Slog.d("ColorDisplayService", "onNightDisplayCustomEndTimeChanged: endTime=" + localTime);
        NightDisplayAutoMode nightDisplayAutoMode = this.mNightDisplayAutoMode;
        if (nightDisplayAutoMode != null) {
            nightDisplayAutoMode.onCustomEndTimeChanged(localTime);
        }
    }

    public final int getCompositionColorSpace(int i) {
        SparseIntArray sparseIntArray = this.mColorModeCompositionColorSpaces;
        if (sparseIntArray == null) {
            return -1;
        }
        return sparseIntArray.get(i, -1);
    }

    public final void onDisplayColorModeChanged(int i) {
        if (i == -1) {
            return;
        }
        this.mNightDisplayTintController.cancelAnimator();
        this.mDisplayWhiteBalanceTintController.cancelAnimator();
        if (this.mNightDisplayTintController.isAvailable(getContext())) {
            this.mNightDisplayTintController.setUp(getContext(), ((DisplayTransformManager) getLocalService(DisplayTransformManager.class)).needsLinearColorMatrix(i));
            NightDisplayTintController nightDisplayTintController = this.mNightDisplayTintController;
            nightDisplayTintController.setMatrix(nightDisplayTintController.getColorTemperatureSetting());
        }
        ((DisplayTransformManager) getLocalService(DisplayTransformManager.class)).setColorMode(i, this.mNightDisplayTintController.getMatrix(), getCompositionColorSpace(i));
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            updateDisplayWhiteBalanceStatus();
        }
    }

    public final void onAccessibilityActivated() {
        onDisplayColorModeChanged(getColorModeInternal());
    }

    public final boolean isAccessiblityDaltonizerEnabled() {
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_daltonizer_enabled", 0, this.mCurrentUser) != 0;
    }

    public final boolean isAccessiblityInversionEnabled() {
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_inversion_enabled", 0, this.mCurrentUser) != 0;
    }

    public final boolean isAccessibilityEnabled() {
        return isAccessiblityDaltonizerEnabled() || isAccessiblityInversionEnabled();
    }

    public final void onAccessibilityDaltonizerChanged() {
        if (this.mCurrentUser == -10000) {
            return;
        }
        int intForUser = isAccessiblityDaltonizerEnabled() ? Settings.Secure.getIntForUser(getContext().getContentResolver(), "accessibility_display_daltonizer", 12, this.mCurrentUser) : -1;
        DisplayTransformManager displayTransformManager = (DisplayTransformManager) getLocalService(DisplayTransformManager.class);
        if (intForUser == 0) {
            displayTransformManager.setColorMatrix(200, MATRIX_GRAYSCALE);
            displayTransformManager.setDaltonizerMode(-1);
        } else {
            displayTransformManager.setColorMatrix(200, null);
            displayTransformManager.setDaltonizerMode(intForUser);
        }
    }

    public final void onAccessibilityInversionChanged() {
        if (this.mCurrentUser == -10000) {
            return;
        }
        ((DisplayTransformManager) getLocalService(DisplayTransformManager.class)).setColorMatrix(300, isAccessiblityInversionEnabled() ? MATRIX_INVERT_COLOR : null);
    }

    public final void onReduceBrightColorsActivationChanged(boolean z) {
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
            intForUser = getContext().getResources().getInteger(R.integer.config_satellite_modem_image_switching_duration_millis);
        }
        this.mReduceBrightColorsTintController.setMatrix(intForUser);
    }

    public final void applyTint(final TintController tintController, boolean z) {
        tintController.cancelAnimator();
        final DisplayTransformManager displayTransformManager = (DisplayTransformManager) getLocalService(DisplayTransformManager.class);
        float[] colorMatrix = displayTransformManager.getColorMatrix(tintController.getLevel());
        final float[] matrix = tintController.getMatrix();
        if (z) {
            displayTransformManager.setColorMatrix(tintController.getLevel(), matrix);
            return;
        }
        ColorMatrixEvaluator colorMatrixEvaluator = COLOR_MATRIX_EVALUATOR;
        Object[] objArr = new Object[2];
        if (colorMatrix == null) {
            colorMatrix = MATRIX_IDENTITY;
        }
        objArr[0] = colorMatrix;
        objArr[1] = matrix;
        TintValueAnimator ofMatrix = TintValueAnimator.ofMatrix(colorMatrixEvaluator, objArr);
        tintController.setAnimator(ofMatrix);
        ofMatrix.setDuration(tintController.getTransitionDurationMilliseconds());
        ofMatrix.setInterpolator(AnimationUtils.loadInterpolator(getContext(), R.interpolator.fast_out_slow_in));
        ofMatrix.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.display.color.ColorDisplayService$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ColorDisplayService.lambda$applyTint$0(DisplayTransformManager.this, tintController, valueAnimator);
            }
        });
        ofMatrix.addListener(new AnimatorListenerAdapter() { // from class: com.android.server.display.color.ColorDisplayService.3
            public boolean mIsCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.mIsCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                TintValueAnimator tintValueAnimator = (TintValueAnimator) animator;
                Slog.d("ColorDisplayService", tintController.getClass().getSimpleName() + " Animation cancelled: " + this.mIsCancelled + " to matrix: " + TintController.matrixToString(matrix, 16) + " min matrix coefficients: " + TintController.matrixToString(tintValueAnimator.getMin(), 16) + " max matrix coefficients: " + TintController.matrixToString(tintValueAnimator.getMax(), 16));
                if (!this.mIsCancelled) {
                    displayTransformManager.setColorMatrix(tintController.getLevel(), matrix);
                }
                tintController.setAnimator(null);
            }
        });
        ofMatrix.start();
    }

    public static /* synthetic */ void lambda$applyTint$0(DisplayTransformManager displayTransformManager, TintController tintController, ValueAnimator valueAnimator) {
        displayTransformManager.setColorMatrix(tintController.getLevel(), (float[]) valueAnimator.getAnimatedValue());
        ((TintValueAnimator) valueAnimator).updateMinMaxComponents();
    }

    public final void applyTintByCct(final ColorTemperatureTintController colorTemperatureTintController, boolean z) {
        synchronized (this.mCctTintApplierLock) {
            colorTemperatureTintController.cancelAnimator();
            final DisplayTransformManager displayTransformManager = (DisplayTransformManager) getLocalService(DisplayTransformManager.class);
            final int appliedCct = colorTemperatureTintController.getAppliedCct();
            final int targetCct = colorTemperatureTintController.isActivated() ? colorTemperatureTintController.getTargetCct() : colorTemperatureTintController.getDisabledCct();
            if (z) {
                Slog.d("ColorDisplayService", colorTemperatureTintController.getClass().getSimpleName() + " applied immediately: toCct=" + targetCct + " fromCct=" + appliedCct);
                displayTransformManager.setColorMatrix(colorTemperatureTintController.getLevel(), colorTemperatureTintController.computeMatrixForCct(targetCct));
                colorTemperatureTintController.setAppliedCct(targetCct);
            } else {
                Slog.d("ColorDisplayService", colorTemperatureTintController.getClass().getSimpleName() + " animation started: toCct=" + targetCct + " fromCct=" + appliedCct);
                ValueAnimator ofInt = ValueAnimator.ofInt(appliedCct, targetCct);
                colorTemperatureTintController.setAnimator(ofInt);
                CctEvaluator evaluator = colorTemperatureTintController.getEvaluator();
                if (evaluator != null) {
                    ofInt.setEvaluator(evaluator);
                }
                ofInt.setDuration(colorTemperatureTintController.getTransitionDurationMilliseconds());
                ofInt.setInterpolator(AnimationUtils.loadInterpolator(getContext(), R.interpolator.linear));
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.display.color.ColorDisplayService$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ColorDisplayService.this.lambda$applyTintByCct$1(colorTemperatureTintController, displayTransformManager, valueAnimator);
                    }
                });
                ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.server.display.color.ColorDisplayService.4
                    public boolean mIsCancelled;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        Slog.d("ColorDisplayService", colorTemperatureTintController.getClass().getSimpleName() + " animation cancelled");
                        this.mIsCancelled = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        synchronized (ColorDisplayService.this.mCctTintApplierLock) {
                            Slog.d("ColorDisplayService", colorTemperatureTintController.getClass().getSimpleName() + " animation ended: wasCancelled=" + this.mIsCancelled + " toCct=" + targetCct + " fromCct=" + appliedCct);
                            if (!this.mIsCancelled) {
                                displayTransformManager.setColorMatrix(colorTemperatureTintController.getLevel(), colorTemperatureTintController.computeMatrixForCct(targetCct));
                                colorTemperatureTintController.setAppliedCct(targetCct);
                            }
                            colorTemperatureTintController.setAnimator(null);
                        }
                    }
                });
                ofInt.start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyTintByCct$1(ColorTemperatureTintController colorTemperatureTintController, DisplayTransformManager displayTransformManager, ValueAnimator valueAnimator) {
        synchronized (this.mCctTintApplierLock) {
            int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            if (intValue != colorTemperatureTintController.getAppliedCct()) {
                displayTransformManager.setColorMatrix(colorTemperatureTintController.getLevel(), colorTemperatureTintController.computeMatrixForCct(intValue));
                colorTemperatureTintController.setAppliedCct(intValue);
            }
        }
    }

    public static LocalDateTime getDateTimeBefore(LocalTime localTime, LocalDateTime localDateTime) {
        LocalDateTime of = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), localTime.getHour(), localTime.getMinute());
        return of.isAfter(localDateTime) ? of.minusDays(1L) : of;
    }

    public static LocalDateTime getDateTimeAfter(LocalTime localTime, LocalDateTime localDateTime) {
        LocalDateTime of = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(), localTime.getHour(), localTime.getMinute());
        return of.isBefore(localDateTime) ? of.plusDays(1L) : of;
    }

    public void updateDisplayWhiteBalanceStatus() {
        boolean isActivated = this.mDisplayWhiteBalanceTintController.isActivated();
        this.mDisplayWhiteBalanceTintController.setActivated(Boolean.valueOf(isDisplayWhiteBalanceSettingEnabled() && !this.mNightDisplayTintController.isActivated() && !isAccessibilityEnabled() && ((DisplayTransformManager) getLocalService(DisplayTransformManager.class)).needsLinearColorMatrix() && this.mDisplayWhiteBalanceTintController.isAllowed()));
        boolean isActivated2 = this.mDisplayWhiteBalanceTintController.isActivated();
        if (!isActivated || isActivated2) {
            return;
        }
        this.mHandler.sendEmptyMessage(5);
    }

    public final boolean setDisplayWhiteBalanceSettingEnabled(boolean z) {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        return Settings.Secure.putIntForUser(getContext().getContentResolver(), "display_white_balance_enabled", z ? 1 : 0, this.mCurrentUser);
    }

    public final boolean isDisplayWhiteBalanceSettingEnabled() {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "display_white_balance_enabled", getContext().getResources().getBoolean(R.bool.config_defaultBinderHeavyHitterWatcherEnabled) ? 1 : 0, this.mCurrentUser) == 1;
    }

    public final boolean setReduceBrightColorsActivatedInternal(boolean z) {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        return Settings.Secure.putIntForUser(getContext().getContentResolver(), "reduce_bright_colors_activated", z ? 1 : 0, this.mCurrentUser);
    }

    public final boolean setReduceBrightColorsStrengthInternal(int i) {
        if (this.mCurrentUser == -10000) {
            return false;
        }
        return Settings.Secure.putIntForUser(getContext().getContentResolver(), "reduce_bright_colors_level", i, this.mCurrentUser);
    }

    public final boolean isDeviceColorManagedInternal() {
        return ((DisplayTransformManager) getLocalService(DisplayTransformManager.class)).isDeviceColorManaged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    public final int getTransformCapabilitiesInternal() {
        boolean protectedContentSupport = SurfaceControl.getProtectedContentSupport();
        Resources resources = getContext().getResources();
        ?? r0 = protectedContentSupport;
        if (resources.getBoolean(R.bool.config_pauseWallpaperRenderWhenStateChangeEnabled)) {
            r0 = (protectedContentSupport ? 1 : 0) | 2;
        }
        return resources.getBoolean(R.bool.config_pdp_reject_enable_retry) ? r0 | 4 : r0;
    }

    public final boolean setNightDisplayAutoModeInternal(int i) {
        if (getNightDisplayAutoModeInternal() != i) {
            Settings.Secure.putStringForUser(getContext().getContentResolver(), "night_display_last_activated_time", null, this.mCurrentUser);
        }
        return Settings.Secure.putIntForUser(getContext().getContentResolver(), "night_display_auto_mode", i, this.mCurrentUser);
    }

    public final int getNightDisplayAutoModeInternal() {
        int nightDisplayAutoModeRawInternal = getNightDisplayAutoModeRawInternal();
        if (nightDisplayAutoModeRawInternal == -1) {
            nightDisplayAutoModeRawInternal = getContext().getResources().getInteger(R.integer.config_deviceStateConcurrentRearDisplay);
        }
        if (nightDisplayAutoModeRawInternal == 0 || nightDisplayAutoModeRawInternal == 1 || nightDisplayAutoModeRawInternal == 2) {
            return nightDisplayAutoModeRawInternal;
        }
        Slog.e("ColorDisplayService", "Invalid autoMode: " + nightDisplayAutoModeRawInternal);
        return 0;
    }

    public final int getNightDisplayAutoModeRawInternal() {
        if (this.mCurrentUser == -10000) {
            return -1;
        }
        return Settings.Secure.getIntForUser(getContext().getContentResolver(), "night_display_auto_mode", -1, this.mCurrentUser);
    }

    public final Time getNightDisplayCustomStartTimeInternal() {
        int intForUser = Settings.Secure.getIntForUser(getContext().getContentResolver(), "night_display_custom_start_time", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = getContext().getResources().getInteger(R.integer.config_displayWhiteBalanceBrightnessFilterHorizon);
        }
        return new Time(LocalTime.ofSecondOfDay(intForUser / 1000));
    }

    public final boolean setNightDisplayCustomStartTimeInternal(Time time) {
        return Settings.Secure.putIntForUser(getContext().getContentResolver(), "night_display_custom_start_time", time.getLocalTime().toSecondOfDay() * 1000, this.mCurrentUser);
    }

    public final Time getNightDisplayCustomEndTimeInternal() {
        int intForUser = Settings.Secure.getIntForUser(getContext().getContentResolver(), "night_display_custom_end_time", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = getContext().getResources().getInteger(R.integer.config_deviceStateRearDisplay);
        }
        return new Time(LocalTime.ofSecondOfDay(intForUser / 1000));
    }

    public final boolean setNightDisplayCustomEndTimeInternal(Time time) {
        return Settings.Secure.putIntForUser(getContext().getContentResolver(), "night_display_custom_end_time", time.getLocalTime().toSecondOfDay() * 1000, this.mCurrentUser);
    }

    public final LocalDateTime getNightDisplayLastActivatedTimeSetting() {
        String stringForUser = Settings.Secure.getStringForUser(getContext().getContentResolver(), "night_display_last_activated_time", getContext().getUserId());
        if (stringForUser != null) {
            try {
                try {
                    return LocalDateTime.parse(stringForUser);
                } catch (DateTimeParseException unused) {
                    return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(stringForUser)), ZoneId.systemDefault());
                }
            } catch (NumberFormatException | DateTimeException unused2) {
            }
        }
        return LocalDateTime.MIN;
    }

    public void setSaturationLevelInternal(int i) {
        Message obtainMessage = this.mHandler.obtainMessage(4);
        obtainMessage.arg1 = i;
        this.mHandler.sendMessage(obtainMessage);
    }

    public boolean setAppSaturationLevelInternal(String str, String str2, int i) {
        return this.mAppSaturationController.setSaturationLevel(str, str2, this.mCurrentUser, i);
    }

    public final void setColorModeInternal(int i) {
        if (!isColorModeAvailable(i)) {
            throw new IllegalArgumentException("Invalid colorMode: " + i);
        }
        Settings.System.putIntForUser(getContext().getContentResolver(), "display_color_mode", i, this.mCurrentUser);
    }

    public final int getColorModeInternal() {
        int integer;
        ContentResolver contentResolver = getContext().getContentResolver();
        if (isAccessibilityEnabled() && (integer = getContext().getResources().getInteger(R.integer.config_audio_notif_vol_steps)) >= 0) {
            return integer;
        }
        int intForUser = Settings.System.getIntForUser(contentResolver, "display_color_mode", -1, this.mCurrentUser);
        if (intForUser == -1) {
            intForUser = getCurrentColorModeFromSystemProperties();
        }
        if (isColorModeAvailable(intForUser)) {
            return intForUser;
        }
        int[] intArray = getContext().getResources().getIntArray(R.array.vendor_required_attestation_certificates);
        if (intForUser != -1 && intArray.length > intForUser && isColorModeAvailable(intArray[intForUser])) {
            return intArray[intForUser];
        }
        int[] intArray2 = getContext().getResources().getIntArray(R.array.config_oemUsbModeOverride);
        if (intArray2.length > 0) {
            return intArray2[0];
        }
        return -1;
    }

    public final int getCurrentColorModeFromSystemProperties() {
        int i = SystemProperties.getInt("persist.sys.sf.native_mode", 0);
        if (i == 0) {
            return !"1.0".equals(SystemProperties.get("persist.sys.sf.color_saturation")) ? 1 : 0;
        }
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 3;
        }
        if (i < 256 || i > 511) {
            return -1;
        }
        return i;
    }

    public final boolean isColorModeAvailable(int i) {
        int[] intArray = getContext().getResources().getIntArray(R.array.config_oemUsbModeOverride);
        if (intArray != null) {
            for (int i2 : intArray) {
                if (i2 == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void dumpInternal(PrintWriter printWriter) {
        printWriter.println("COLOR DISPLAY MANAGER dumpsys (color_display)");
        printWriter.println("Night display:");
        if (this.mNightDisplayTintController.isAvailable(getContext())) {
            printWriter.println("    Activated: " + this.mNightDisplayTintController.isActivated());
            printWriter.println("    Color temp: " + this.mNightDisplayTintController.getColorTemperature());
        } else {
            printWriter.println("    Not available");
        }
        printWriter.println("Global saturation:");
        if (this.mGlobalSaturationTintController.isAvailable(getContext())) {
            printWriter.println("    Activated: " + this.mGlobalSaturationTintController.isActivated());
        } else {
            printWriter.println("    Not available");
        }
        this.mAppSaturationController.dump(printWriter);
        printWriter.println("Display white balance:");
        if (this.mDisplayWhiteBalanceTintController.isAvailable(getContext())) {
            printWriter.println("    Activated: " + this.mDisplayWhiteBalanceTintController.isActivated());
            this.mDisplayWhiteBalanceTintController.dump(printWriter);
        } else {
            printWriter.println("    Not available");
        }
        printWriter.println("Reduce bright colors:");
        if (this.mReduceBrightColorsTintController.isAvailable(getContext())) {
            printWriter.println("    Activated: " + this.mReduceBrightColorsTintController.isActivated());
            this.mReduceBrightColorsTintController.dump(printWriter);
        } else {
            printWriter.println("    Not available");
        }
        printWriter.println("Color mode: " + getColorModeInternal());
    }

    public abstract class NightDisplayAutoMode {
        public abstract void onActivated(boolean z);

        public void onCustomEndTimeChanged(LocalTime localTime) {
        }

        public void onCustomStartTimeChanged(LocalTime localTime) {
        }

        public abstract void onStart();

        public abstract void onStop();

        public NightDisplayAutoMode() {
        }
    }

    public final class CustomNightDisplayAutoMode extends NightDisplayAutoMode implements AlarmManager.OnAlarmListener {
        public final AlarmManager mAlarmManager;
        public LocalTime mEndTime;
        public LocalDateTime mLastActivatedTime;
        public LocalTime mStartTime;
        public final BroadcastReceiver mTimeChangedReceiver;

        public CustomNightDisplayAutoMode() {
            super();
            this.mAlarmManager = (AlarmManager) ColorDisplayService.this.getContext().getSystemService("alarm");
            this.mTimeChangedReceiver = new BroadcastReceiver() { // from class: com.android.server.display.color.ColorDisplayService.CustomNightDisplayAutoMode.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    CustomNightDisplayAutoMode.this.updateActivated();
                }
            };
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
            if (bool != null) {
                this.mAlarmManager.setExact(1, (bool.booleanValue() ? ColorDisplayService.getDateTimeAfter(this.mEndTime, localDateTime) : ColorDisplayService.getDateTimeAfter(this.mStartTime, localDateTime)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), "ColorDisplayService", this, null);
            }
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onStart() {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            ColorDisplayService.this.getContext().registerReceiver(this.mTimeChangedReceiver, intentFilter);
            this.mStartTime = ColorDisplayService.this.getNightDisplayCustomStartTimeInternal().getLocalTime();
            this.mEndTime = ColorDisplayService.this.getNightDisplayCustomEndTimeInternal().getLocalTime();
            this.mLastActivatedTime = ColorDisplayService.this.getNightDisplayLastActivatedTimeSetting();
            updateActivated();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onStop() {
            ColorDisplayService.this.getContext().unregisterReceiver(this.mTimeChangedReceiver);
            this.mAlarmManager.cancel(this);
            this.mLastActivatedTime = null;
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onActivated(boolean z) {
            this.mLastActivatedTime = ColorDisplayService.this.getNightDisplayLastActivatedTimeSetting();
            updateNextAlarm(Boolean.valueOf(z), LocalDateTime.now());
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onCustomStartTimeChanged(LocalTime localTime) {
            this.mStartTime = localTime;
            this.mLastActivatedTime = null;
            updateActivated();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onCustomEndTimeChanged(LocalTime localTime) {
            this.mEndTime = localTime;
            this.mLastActivatedTime = null;
            updateActivated();
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            Slog.d("ColorDisplayService", "onAlarm");
            updateActivated();
        }
    }

    public final class TwilightNightDisplayAutoMode extends NightDisplayAutoMode implements TwilightListener {
        public LocalDateTime mLastActivatedTime;
        public final TwilightManager mTwilightManager;

        public TwilightNightDisplayAutoMode() {
            super();
            this.mTwilightManager = (TwilightManager) ColorDisplayService.this.getLocalService(TwilightManager.class);
        }

        public final void updateActivated(TwilightState twilightState) {
            if (twilightState == null) {
                return;
            }
            boolean isNight = twilightState.isNight();
            if (this.mLastActivatedTime != null) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime sunrise = twilightState.sunrise();
                LocalDateTime sunset = twilightState.sunset();
                if (this.mLastActivatedTime.isBefore(now)) {
                    if (this.mLastActivatedTime.isBefore(sunset) ^ this.mLastActivatedTime.isBefore(sunrise)) {
                        isNight = ColorDisplayService.this.mNightDisplayTintController.isActivatedSetting();
                    }
                }
            }
            if (ColorDisplayService.this.mNightDisplayTintController.isActivatedStateNotSet() || ColorDisplayService.this.mNightDisplayTintController.isActivated() != isNight) {
                ColorDisplayService.this.mNightDisplayTintController.setActivated(Boolean.valueOf(isNight));
            }
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onActivated(boolean z) {
            this.mLastActivatedTime = ColorDisplayService.this.getNightDisplayLastActivatedTimeSetting();
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onStart() {
            this.mTwilightManager.registerListener(this, ColorDisplayService.this.mHandler);
            this.mLastActivatedTime = ColorDisplayService.this.getNightDisplayLastActivatedTimeSetting();
            updateActivated(this.mTwilightManager.getLastTwilightState());
        }

        @Override // com.android.server.display.color.ColorDisplayService.NightDisplayAutoMode
        public void onStop() {
            this.mTwilightManager.unregisterListener(this);
            this.mLastActivatedTime = null;
        }

        @Override // com.android.server.twilight.TwilightListener
        public void onTwilightStateChanged(TwilightState twilightState) {
            StringBuilder sb = new StringBuilder();
            sb.append("onTwilightStateChanged: isNight=");
            sb.append(twilightState == null ? null : Boolean.valueOf(twilightState.isNight()));
            Slog.d("ColorDisplayService", sb.toString());
            updateActivated(twilightState);
        }
    }

    public class TintValueAnimator extends ValueAnimator {
        public float[] max;
        public float[] min;

        public static TintValueAnimator ofMatrix(ColorMatrixEvaluator colorMatrixEvaluator, Object... objArr) {
            TintValueAnimator tintValueAnimator = new TintValueAnimator();
            tintValueAnimator.setObjectValues(objArr);
            tintValueAnimator.setEvaluator(colorMatrixEvaluator);
            if (objArr == null || objArr.length == 0) {
                return null;
            }
            float[] fArr = (float[]) objArr[0];
            tintValueAnimator.min = new float[fArr.length];
            tintValueAnimator.max = new float[fArr.length];
            for (int i = 0; i < fArr.length; i++) {
                tintValueAnimator.min[i] = Float.MAX_VALUE;
                tintValueAnimator.max[i] = Float.MIN_VALUE;
            }
            return tintValueAnimator;
        }

        public void updateMinMaxComponents() {
            float[] fArr = (float[]) getAnimatedValue();
            if (fArr == null) {
                return;
            }
            for (int i = 0; i < fArr.length; i++) {
                float[] fArr2 = this.min;
                fArr2[i] = Math.min(fArr2[i], fArr[i]);
                float[] fArr3 = this.max;
                fArr3[i] = Math.max(fArr3[i], fArr[i]);
            }
        }

        public float[] getMin() {
            return this.min;
        }

        public float[] getMax() {
            return this.max;
        }
    }

    public class ColorMatrixEvaluator implements TypeEvaluator {
        public final float[] mResultMatrix;

        public ColorMatrixEvaluator() {
            this.mResultMatrix = new float[16];
        }

        @Override // android.animation.TypeEvaluator
        public float[] evaluate(float f, float[] fArr, float[] fArr2) {
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

    public final class NightDisplayTintController extends TintController {
        public Integer mColorTemp;
        public final float[] mColorTempCoefficients;
        public Boolean mIsAvailable;
        public final float[] mMatrix;

        @Override // com.android.server.display.color.TintController
        public int getLevel() {
            return 100;
        }

        public NightDisplayTintController() {
            this.mMatrix = new float[16];
            this.mColorTempCoefficients = new float[9];
        }

        public void setUp(Context context, boolean z) {
            String[] stringArray = context.getResources().getStringArray(z ? 17236254 : 17236255);
            for (int i = 0; i < 9 && i < stringArray.length; i++) {
                this.mColorTempCoefficients[i] = Float.parseFloat(stringArray[i]);
            }
        }

        @Override // com.android.server.display.color.TintController
        public void setMatrix(int i) {
            float[] fArr = this.mMatrix;
            if (fArr.length != 16) {
                Slog.d("ColorDisplayService", "The display transformation matrix must be 4x4");
                return;
            }
            Matrix.setIdentityM(fArr, 0);
            float f = i * i;
            float[] fArr2 = this.mColorTempCoefficients;
            float f2 = i;
            float f3 = (fArr2[0] * f) + (fArr2[1] * f2) + fArr2[2];
            float f4 = (fArr2[3] * f) + (fArr2[4] * f2) + fArr2[5];
            float f5 = (f * fArr2[6]) + (f2 * fArr2[7]) + fArr2[8];
            float[] fArr3 = this.mMatrix;
            fArr3[0] = f3;
            fArr3[5] = f4;
            fArr3[10] = f5;
        }

        @Override // com.android.server.display.color.TintController
        public float[] getMatrix() {
            if (isActivationLock()) {
                return ColorDisplayService.MATRIX_IDENTITY;
            }
            return isActivated() ? this.mMatrix : ColorDisplayService.MATRIX_IDENTITY;
        }

        @Override // com.android.server.display.color.TintController
        public void setActivated(Boolean bool) {
            setActivated(bool, LocalDateTime.now());
        }

        public void setActivated(Boolean bool, LocalDateTime localDateTime) {
            if (bool == null) {
                super.setActivated(null);
                return;
            }
            boolean z = bool.booleanValue() != isActivated();
            if (!isActivatedStateNotSet() && z) {
                Settings.Secure.putStringForUser(ColorDisplayService.this.getContext().getContentResolver(), "night_display_last_activated_time", localDateTime.toString(), ColorDisplayService.this.mCurrentUser);
            }
            if (isActivatedStateNotSet() || z) {
                super.setActivated(bool);
                if (isActivatedSetting() != bool.booleanValue()) {
                    Settings.Secure.putIntForUser(ColorDisplayService.this.getContext().getContentResolver(), "night_display_activated", bool.booleanValue() ? 1 : 0, ColorDisplayService.this.mCurrentUser);
                }
                onActivated(bool.booleanValue());
            }
        }

        @Override // com.android.server.display.color.TintController
        public boolean isAvailable(Context context) {
            if (this.mIsAvailable == null) {
                this.mIsAvailable = Boolean.valueOf(ColorDisplayManager.isNightDisplayAvailable(context));
            }
            return this.mIsAvailable.booleanValue();
        }

        public final void onActivated(boolean z) {
            Slog.i("ColorDisplayService", z ? "Turning on night display" : "Turning off night display");
            if (ColorDisplayService.this.mNightDisplayAutoMode != null) {
                ColorDisplayService.this.mNightDisplayAutoMode.onActivated(z);
            }
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            if (colorDisplayService.mDisplayWhiteBalanceTintController.isAvailable(colorDisplayService.getContext())) {
                ColorDisplayService.this.updateDisplayWhiteBalanceStatus();
            }
            ColorDisplayService.this.mHandler.sendEmptyMessage(3);
        }

        public int getColorTemperature() {
            Integer num = this.mColorTemp;
            return num != null ? clampNightDisplayColorTemperature(num.intValue()) : getColorTemperatureSetting();
        }

        public boolean setColorTemperature(int i) {
            this.mColorTemp = Integer.valueOf(i);
            boolean putIntForUser = Settings.Secure.putIntForUser(ColorDisplayService.this.getContext().getContentResolver(), "night_display_color_temperature", i, ColorDisplayService.this.mCurrentUser);
            onColorTemperatureChanged(i);
            return putIntForUser;
        }

        public void onColorTemperatureChanged(int i) {
            setMatrix(i);
            ColorDisplayService.this.mHandler.sendEmptyMessage(2);
        }

        public boolean isActivatedSetting() {
            return ColorDisplayService.this.mCurrentUser != -10000 && Settings.Secure.getIntForUser(ColorDisplayService.this.getContext().getContentResolver(), "night_display_activated", 0, ColorDisplayService.this.mCurrentUser) == 1;
        }

        public int getColorTemperatureSetting() {
            if (ColorDisplayService.this.mCurrentUser == -10000) {
                return -1;
            }
            return clampNightDisplayColorTemperature(Settings.Secure.getIntForUser(ColorDisplayService.this.getContext().getContentResolver(), "night_display_color_temperature", -1, ColorDisplayService.this.mCurrentUser));
        }

        public final int clampNightDisplayColorTemperature(int i) {
            if (i == -1) {
                i = ColorDisplayService.this.getContext().getResources().getInteger(R.integer.config_ntpRetry);
            }
            int minimumColorTemperature = ColorDisplayManager.getMinimumColorTemperature(ColorDisplayService.this.getContext());
            int maximumColorTemperature = ColorDisplayManager.getMaximumColorTemperature(ColorDisplayService.this.getContext());
            return i < minimumColorTemperature ? minimumColorTemperature : i > maximumColorTemperature ? maximumColorTemperature : i;
        }
    }

    public class ColorDisplayServiceInternal {
        public ColorDisplayServiceInternal() {
        }

        public boolean isReduceBrightColorsActivated() {
            return ColorDisplayService.this.mReduceBrightColorsTintController.isActivated();
        }

        public int getReduceBrightColorsStrength() {
            return ColorDisplayService.this.mReduceBrightColorsTintController.getStrength();
        }

        public float getReduceBrightColorsAdjustedBrightnessNits(float f) {
            return ColorDisplayService.this.mReduceBrightColorsTintController.getAdjustedBrightness(f);
        }

        public boolean attachColorTransformController(String str, int i, WeakReference weakReference) {
            return ColorDisplayService.this.mAppSaturationController.addColorTransformController(str, i, weakReference);
        }

        public void onOpticalUdfpsStarted() {
            ColorDisplayService.this.mGlobalSaturationTintController.setActivationLock(true);
            if (ColorDisplayService.this.mGlobalSaturationTintController.isActivated()) {
                ColorDisplayService.this.mHandler.post(new Runnable() { // from class: com.android.server.display.color.ColorDisplayService$ColorDisplayServiceInternal$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        ColorDisplayService.ColorDisplayServiceInternal.this.lambda$onOpticalUdfpsStarted$0();
                    }
                });
            }
            ColorDisplayService.this.mNightDisplayTintController.setActivationLock(true);
            if (ColorDisplayService.this.mNightDisplayTintController.isActivated()) {
                ColorDisplayService.this.mHandler.sendEmptyMessage(2);
            }
            ColorDisplayService.this.mReduceBrightColorsTintController.setActivationLock(true);
            if (ColorDisplayService.this.mReduceBrightColorsTintController.isActivated()) {
                ColorDisplayService.this.mHandler.sendEmptyMessage(6);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpticalUdfpsStarted$0() {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            colorDisplayService.applyTint(colorDisplayService.mGlobalSaturationTintController, true);
        }

        public void onOpticalUdfpsStopped() {
            ColorDisplayService.this.mGlobalSaturationTintController.setActivationLock(false);
            if (ColorDisplayService.this.mGlobalSaturationTintController.isActivated()) {
                ColorDisplayService.this.mHandler.post(new Runnable() { // from class: com.android.server.display.color.ColorDisplayService$ColorDisplayServiceInternal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ColorDisplayService.ColorDisplayServiceInternal.this.lambda$onOpticalUdfpsStopped$1();
                    }
                });
            }
            ColorDisplayService.this.mNightDisplayTintController.setActivationLock(false);
            if (ColorDisplayService.this.mNightDisplayTintController.isActivated()) {
                ColorDisplayService.this.mHandler.sendEmptyMessage(2);
            }
            ColorDisplayService.this.mReduceBrightColorsTintController.setActivationLock(false);
            if (ColorDisplayService.this.mReduceBrightColorsTintController.isActivated()) {
                ColorDisplayService.this.mHandler.post(new Runnable() { // from class: com.android.server.display.color.ColorDisplayService$ColorDisplayServiceInternal$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ColorDisplayService.ColorDisplayServiceInternal.this.lambda$onOpticalUdfpsStopped$2();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpticalUdfpsStopped$1() {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            colorDisplayService.applyTint(colorDisplayService.mGlobalSaturationTintController, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpticalUdfpsStopped$2() {
            ColorDisplayService colorDisplayService = ColorDisplayService.this;
            colorDisplayService.applyTint(colorDisplayService.mReduceBrightColorsTintController, false);
        }
    }

    public final class TintHandler extends Handler {
        public TintHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    ColorDisplayService.this.onUserChanged(message.arg1);
                    break;
                case 1:
                    ColorDisplayService.this.setUp();
                    break;
                case 2:
                    ColorDisplayService colorDisplayService = ColorDisplayService.this;
                    colorDisplayService.applyTint(colorDisplayService.mNightDisplayTintController, true);
                    break;
                case 3:
                    ColorDisplayService colorDisplayService2 = ColorDisplayService.this;
                    colorDisplayService2.applyTint(colorDisplayService2.mNightDisplayTintController, false);
                    break;
                case 4:
                    ColorDisplayService.this.mGlobalSaturationTintController.setMatrix(message.arg1);
                    ColorDisplayService colorDisplayService3 = ColorDisplayService.this;
                    colorDisplayService3.applyTint(colorDisplayService3.mGlobalSaturationTintController, false);
                    break;
                case 5:
                    ColorDisplayService colorDisplayService4 = ColorDisplayService.this;
                    colorDisplayService4.applyTintByCct(colorDisplayService4.mDisplayWhiteBalanceTintController, false);
                    break;
                case 6:
                    ColorDisplayService colorDisplayService5 = ColorDisplayService.this;
                    colorDisplayService5.applyTint(colorDisplayService5.mReduceBrightColorsTintController, true);
                    break;
            }
        }
    }

    final class BinderService extends IColorDisplayManager.Stub {
        public BinderService() {
        }

        public void setColorMode(int i) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to set display color mode");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService.this.setColorModeInternal(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getColorMode() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.getColorModeInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isDeviceColorManaged() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.isDeviceColorManagedInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setSaturationLevel(int i) {
            boolean z = ColorDisplayService.this.getContext().checkCallingPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS") == 0;
            boolean z2 = ColorDisplayService.this.getContext().checkCallingPermission("android.permission.CONTROL_DISPLAY_SATURATION") == 0;
            boolean z3 = ColorDisplayService.this.getContext().checkCallingPermission("com.samsung.android.permission.SEM_CONTROL_DISPLAY_COLOR_TRANSFORMS") == 0;
            if (!z && !z2 && !z3) {
                throw new SecurityException("Permission required to set display saturation level");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService.this.setSaturationLevelInternal(i);
                return true;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isSaturationActivated() {
            boolean z;
            super.isSaturationActivated_enforcePermission();
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

        public boolean setAppSaturationLevel(String str, int i) {
            super.setAppSaturationLevel_enforcePermission();
            String nameForUid = ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getNameForUid(Binder.getCallingUid());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.setAppSaturationLevelInternal(nameForUid, str, i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getTransformCapabilities() {
            super.getTransformCapabilities_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.getTransformCapabilitiesInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setNightDisplayActivated(boolean z) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to set night display activated");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ColorDisplayService.this.mNightDisplayTintController.setActivated(Boolean.valueOf(z));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean isNightDisplayActivated() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.mNightDisplayTintController.isActivated();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setNightDisplayColorTemperature(int i) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to set night display temperature");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.mNightDisplayTintController.setColorTemperature(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getNightDisplayColorTemperature() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.mNightDisplayTintController.getColorTemperature();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setNightDisplayAutoMode(int i) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to set night display auto mode");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.setNightDisplayAutoModeInternal(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getNightDisplayAutoMode() {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to get night display auto mode");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.getNightDisplayAutoModeInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getNightDisplayAutoModeRaw() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.getNightDisplayAutoModeRawInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setNightDisplayCustomStartTime(Time time) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to set night display custom start time");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.setNightDisplayCustomStartTimeInternal(time);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public Time getNightDisplayCustomStartTime() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.getNightDisplayCustomStartTimeInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setNightDisplayCustomEndTime(Time time) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to set night display custom end time");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.setNightDisplayCustomEndTimeInternal(time);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public Time getNightDisplayCustomEndTime() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.getNightDisplayCustomEndTimeInternal();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setDisplayWhiteBalanceEnabled(boolean z) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to set night display activated");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.setDisplayWhiteBalanceSettingEnabled(z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isDisplayWhiteBalanceEnabled() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.isDisplayWhiteBalanceSettingEnabled();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isReduceBrightColorsActivated() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.mReduceBrightColorsTintController.isActivated();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setReduceBrightColorsActivated(boolean z) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to set reduce bright colors activation state");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.setReduceBrightColorsActivatedInternal(z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getReduceBrightColorsStrength() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.mReduceBrightColorsTintController.getStrength();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public float getReduceBrightColorsOffsetFactor() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.mReduceBrightColorsTintController.getOffsetFactor();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean setReduceBrightColorsStrength(int i) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to set reduce bright colors strength");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ColorDisplayService.this.setReduceBrightColorsStrengthInternal(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(ColorDisplayService.this.getContext(), "ColorDisplayService", printWriter)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ColorDisplayService.this.dumpInternal(printWriter);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
            ColorDisplayService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DISPLAY_COLOR_TRANSFORMS", "Permission required to use ADB color transform commands");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return new ColorDisplayShellCommand(ColorDisplayService.this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }
}
