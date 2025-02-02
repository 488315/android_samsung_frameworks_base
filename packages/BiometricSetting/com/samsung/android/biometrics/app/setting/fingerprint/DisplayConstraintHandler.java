package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.BackgroundThread;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.SysUiManager;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.SemMdnieManager;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* loaded from: classes.dex */
public final class DisplayConstraintHandler implements Handler.Callback {

    @VisibleForTesting
    static final long COLOR_INVERSION_RESTORE_DELAY = 50;

    @VisibleForTesting
    static final int ECS_SCHEDULE_AUTO = 2;

    @VisibleForTesting
    static final int ECS_SCHEDULE_MANUAL = 1;

    @VisibleForTesting
    static final int FEATURE_MDNIE_MODE;

    @VisibleForTesting
    static final boolean FEATURE_SUPPORT_ADAPTIVE_BLF;
    public static final boolean FEATURE_SUPPORT_COLOR_ADJUSTMENT;
    public static final boolean FEATURE_SUPPORT_COLOR_LENS;
    public static final boolean FEATURE_SUPPORT_COLOR_NEGATIVE;
    private boolean isNeedToRestoreColorNegativeOrGrayScale;
    private final Handler mBgHandler = new Handler(BackgroundThread.get().getLooper(), this);
    private final Context mContext;
    private int mDbAdaptiveBlueLightFilterOpacity;
    private int mDbBlueLightFilterOpacity;
    private int mDbColorAdjustment;
    private float mDbColorAdjustmentUseParameter;
    private int mDbColorGrayScale;
    private int mDbColorLens;
    private int mDbColorLensFilterOpacity;
    private int mDbColorLensFilterType;
    private int mDbColorNegative;
    private int mDbDirectAccessColorAdjustment;
    private int mDbDirectAccessColorLens;
    private int mDbDirectAccessColorNegative;
    private final FpServiceProvider mFpServiceProvider;
    private final Injector mInjector;
    private boolean mIsBlueLightFilterDisabled;
    private boolean mIsColorInversionDisabled;
    private boolean mIsDisabled;
    private boolean mIsEnabledAdaptiveBlueLightFilter;
    private boolean mIsEnabledBlueLightFilter;
    private boolean mIsEnabledColourCorrection;
    private boolean mIsStarted;
    private boolean mNeedToRestoreColorAdjustment;
    private boolean mNeedToRestoreColorLens;
    private boolean mNeedToRestoreColourCorrection;
    private boolean mNeedToRestoreColourInversion;
    private boolean mNeedToRestoreDirectAccessColorAdjustment;
    private boolean mNeedToRestoreDirectAccessColorLens;
    private boolean mNeedToRestoreDirectAccessColorNegative;

    public static class Injector {
        private final AccessibilityManager mA11yManager;
        private final Context mContext;
        private final SemMdnieManager mMdnieManager;

        public Injector(Context context) {
            this.mContext = context;
            this.mMdnieManager = (SemMdnieManager) context.getSystemService(SemMdnieManager.class);
            this.mA11yManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        }

        final void disableMdnieAccessibilityMode() {
            AccessibilityManager accessibilityManager = this.mA11yManager;
            if (accessibilityManager == null) {
                return;
            }
            accessibilityManager.semSetMdnieAccessibilityMode(DisplayConstraintHandler.ECS_SCHEDULE_MANUAL, false);
        }

        final void disableMdnieColorFilter() {
            AccessibilityManager accessibilityManager = this.mA11yManager;
            if (accessibilityManager == null) {
                return;
            }
            accessibilityManager.semDisableMdnieColorFilter();
        }

        final void enableEyeComfortShield(int i) {
            SemMdnieManager semMdnieManager = this.mMdnieManager;
            if (semMdnieManager == null) {
                return;
            }
            semMdnieManager.enableNightMode(i);
        }

        final void enableMdnieAccessibilityMode(int i) {
            AccessibilityManager accessibilityManager = this.mA11yManager;
            if (accessibilityManager == null) {
                return;
            }
            accessibilityManager.semSetMdnieAccessibilityMode(i, true);
        }

        final void enableMdnieColorBlind(float f) {
            AccessibilityManager accessibilityManager = this.mA11yManager;
            if (accessibilityManager == null) {
                return;
            }
            accessibilityManager.semSetMdnieColorBlind(true, f);
        }

        final void enableMdnieColorFilter(int i, int i2) {
            AccessibilityManager accessibilityManager = this.mA11yManager;
            if (accessibilityManager == null) {
                return;
            }
            accessibilityManager.semEnableMdnieColorFilter(i, i2);
        }

        final int getAdaptiveEyeComfortShieldTemperature() {
            SemMdnieManager semMdnieManager = this.mMdnieManager;
            if (semMdnieManager == null) {
                return 0;
            }
            return semMdnieManager.getNightModeStep();
        }

        final int getColorAdjustmentSetting() {
            return Utils.getIntDb(this.mContext, "color_blind", false, -1);
        }

        final int getColorAdjustmentTypeSetting() {
            return Utils.getIntDb(this.mContext, "color_adjustment_type", true, DisplayConstraintHandler.ECS_SCHEDULE_AUTO);
        }

        final float getColorAdjustmentUseParameterSetting() {
            boolean z = Utils.DEBUG;
            try {
                return Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), "color_blind_user_parameter", 0.0f, -2);
            } catch (Exception e) {
                DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("getFloatDb: "), "BSS_Utils");
                return 0.0f;
            }
        }

        final int getColorLensFilterTypeOpacitySetting() {
            return Utils.getIntDb(this.mContext, "color_lens_opacity", true, 0);
        }

        final int getColorLensFilterTypeSetting() {
            return Utils.getIntDb(this.mContext, "color_lens_type", true, 0);
        }

        final int getColorLensSetting() {
            return Utils.getIntDb(this.mContext, "color_lens_switch", true, 0);
        }

        final int getColorNegativeSetting() {
            return Utils.getIntDb(this.mContext, "high_contrast", false, 0);
        }

        final int getIntDb(String str) {
            return Utils.getIntDb(this.mContext, str, false, 0);
        }

        final boolean isEnabledColorCorrectionAosp() {
            return Utils.getIntDb(this.mContext, "accessibility_display_daltonizer_enabled", true, 0) == DisplayConstraintHandler.ECS_SCHEDULE_MANUAL;
        }

        final void lockEyeComfortShield() {
            SemMdnieManager semMdnieManager = this.mMdnieManager;
            if (semMdnieManager == null) {
                return;
            }
            semMdnieManager.setNightModeBlock(false);
        }

        final void putIntDb(int i, String str, boolean z) {
            Utils.putIntDb(this.mContext, str, z, i);
        }

        final void setMdnieColorBlind(float f) {
            AccessibilityManager accessibilityManager = this.mA11yManager;
            if (accessibilityManager == null) {
                return;
            }
            accessibilityManager.semSetMdnieColorBlind(false, f);
        }

        final void turnOffColorCorrectionAosp() {
            Utils.putIntDb(this.mContext, "accessibility_display_daltonizer_enabled", true, 0);
        }

        final void turnOnColorCorrectionAosp() {
            Utils.putIntDb(this.mContext, "accessibility_display_daltonizer_enabled", true, DisplayConstraintHandler.ECS_SCHEDULE_MANUAL);
        }

        final void unlockEyeComfortShield() {
            SemMdnieManager semMdnieManager = this.mMdnieManager;
            if (semMdnieManager == null) {
                return;
            }
            semMdnieManager.setNightModeBlock(true);
        }
    }

    static {
        FEATURE_SUPPORT_ADAPTIVE_BLF = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE") > 0 ? ECS_SCHEDULE_MANUAL : false;
        int i = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_MDNIE_MODE");
        FEATURE_MDNIE_MODE = i;
        FEATURE_SUPPORT_COLOR_LENS = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_COLOR_LENS");
        FEATURE_SUPPORT_COLOR_NEGATIVE = (i & 256) != 0 ? ECS_SCHEDULE_MANUAL : false;
        FEATURE_SUPPORT_COLOR_ADJUSTMENT = (i & 2048) != 0;
    }

    @VisibleForTesting
    DisplayConstraintHandler(Context context, FpServiceProvider fpServiceProvider, Injector injector) {
        this.mContext = context;
        this.mFpServiceProvider = fpServiceProvider;
        this.mInjector = injector;
    }

    private void handleDisableBlueLightFilter() {
        if (this.mIsBlueLightFilterDisabled) {
            return;
        }
        this.mIsBlueLightFilterDisabled = true;
        updateBlueLightFilterValue();
        this.mInjector.lockEyeComfortShield();
        Log.i("BSS_DisplayConstraintHandler", "BLF: disable = " + this.mIsEnabledBlueLightFilter + ", " + this.mDbBlueLightFilterOpacity);
    }

    private void handleDisableColorInversion() {
        if (this.mIsColorInversionDisabled) {
            return;
        }
        this.mIsColorInversionDisabled = true;
        Injector injector = this.mInjector;
        Context context = this.mContext;
        injector.getClass();
        boolean z = Utils.getIntDb(context, "accessibility_display_inversion_enabled", true, 0) == ECS_SCHEDULE_MANUAL ? ECS_SCHEDULE_MANUAL : false;
        if (z) {
            this.mNeedToRestoreColourInversion = true;
            Injector injector2 = this.mInjector;
            Context context2 = this.mContext;
            injector2.getClass();
            Utils.putIntDb(context2, "accessibility_display_inversion_enabled", true, 0);
        }
        Log.i("BSS_DisplayConstraintHandler", "disableColorInversion: " + z);
    }

    private void handleRestoreBlueLightFilter() {
        Pair pair;
        if (this.mIsBlueLightFilterDisabled) {
            updateBlueLightFilterValue();
            if (this.mIsEnabledBlueLightFilter) {
                Injector injector = this.mInjector;
                Context context = this.mContext;
                injector.getClass();
                if (Utils.getIntDb(context, "blue_light_filter_scheduled", false, 0) != 0 ? ECS_SCHEDULE_MANUAL : false) {
                    Injector injector2 = this.mInjector;
                    Context context2 = this.mContext;
                    injector2.getClass();
                    int intDb = Utils.getIntDb(context2, "blue_light_filter_type", false, 0);
                    if (intDb == ECS_SCHEDULE_MANUAL || intDb == ECS_SCHEDULE_AUTO) {
                        if (intDb == ECS_SCHEDULE_MANUAL) {
                            Injector injector3 = this.mInjector;
                            Context context3 = this.mContext;
                            injector3.getClass();
                            pair = new Pair(Long.valueOf(Utils.getLongDb(context3, "blue_light_filter_on_time", 1140L)), Long.valueOf(Utils.getLongDb(context3, "blue_light_filter_off_time", 420L)));
                        } else {
                            Injector injector4 = this.mInjector;
                            Context context4 = this.mContext;
                            injector4.getClass();
                            pair = new Pair(Long.valueOf(Utils.getLongDb(context4, "blue_light_filter_automatic_on_time", 1140L)), Long.valueOf(Utils.getLongDb(context4, "blue_light_filter_automatic_off_time", 420L)));
                        }
                        this.mInjector.getClass();
                        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getDefault());
                        gregorianCalendar.setTime(new Date());
                        long j = gregorianCalendar.get(12) + (gregorianCalendar.get(11) * 60);
                        if (((Long) pair.first).longValue() < ((Long) pair.second).longValue()) {
                            if (j < ((Long) pair.first).longValue() || j >= ((Long) pair.second).longValue()) {
                                this.mIsEnabledBlueLightFilter = false;
                            }
                        } else if (j >= ((Long) pair.second).longValue() && j < ((Long) pair.first).longValue()) {
                            this.mIsEnabledBlueLightFilter = false;
                        }
                        Log.i("BSS_DisplayConstraintHandler", "Scheduled BLF: " + pair.first + ", " + pair.second + ", " + j + ", " + this.mIsEnabledBlueLightFilter);
                    }
                }
            }
            if (FEATURE_SUPPORT_ADAPTIVE_BLF) {
                Injector injector5 = this.mInjector;
                Context context5 = this.mContext;
                injector5.getClass();
                boolean z = Utils.getIntDb(context5, "blue_light_filter_adaptive_mode", false, 0) == ECS_SCHEDULE_MANUAL;
                this.mIsEnabledAdaptiveBlueLightFilter = z;
                if (z) {
                    this.mDbAdaptiveBlueLightFilterOpacity = this.mInjector.getAdaptiveEyeComfortShieldTemperature();
                }
            }
            this.mInjector.unlockEyeComfortShield();
            if (this.mIsEnabledBlueLightFilter) {
                Log.i("BSS_DisplayConstraintHandler", "restoreBlueLightFilter: " + this.mDbBlueLightFilterOpacity + ", " + this.mIsEnabledAdaptiveBlueLightFilter + ", " + this.mDbAdaptiveBlueLightFilterOpacity);
                this.mInjector.enableEyeComfortShield(this.mIsEnabledAdaptiveBlueLightFilter ? this.mDbAdaptiveBlueLightFilterOpacity : this.mDbBlueLightFilterOpacity);
            }
            this.mIsBlueLightFilterDisabled = false;
        }
    }

    private void handleRestoreColorInversion() {
        if (this.mIsColorInversionDisabled) {
            if (this.mNeedToRestoreColourInversion) {
                this.mNeedToRestoreColourInversion = false;
                Log.i("BSS_DisplayConstraintHandler", "restoreColorInversion");
                Injector injector = this.mInjector;
                Context context = this.mContext;
                injector.getClass();
                Utils.putIntDb(context, "accessibility_display_inversion_enabled", true, ECS_SCHEDULE_MANUAL);
            }
            this.mIsColorInversionDisabled = false;
        }
    }

    private void updateA11ySettingDB() {
        int i;
        if (FEATURE_SUPPORT_COLOR_ADJUSTMENT) {
            int colorAdjustmentSetting = this.mInjector.getColorAdjustmentSetting();
            this.mDbColorAdjustment = colorAdjustmentSetting;
            int i2 = 0;
            if (colorAdjustmentSetting == ECS_SCHEDULE_MANUAL) {
                i = this.mInjector.getColorAdjustmentTypeSetting();
                this.mDbColorAdjustmentUseParameter = this.mInjector.getColorAdjustmentUseParameterSetting();
            } else {
                i = 0;
            }
            if (this.mDbColorAdjustment == ECS_SCHEDULE_MANUAL && i == 0) {
                i2 = ECS_SCHEDULE_MANUAL;
            }
            this.mDbColorGrayScale = i2;
        }
        if (FEATURE_SUPPORT_COLOR_LENS) {
            int colorLensSetting = this.mInjector.getColorLensSetting();
            this.mDbColorLens = colorLensSetting;
            if (colorLensSetting == ECS_SCHEDULE_MANUAL) {
                this.mDbColorLensFilterType = this.mInjector.getColorLensFilterTypeSetting();
                this.mDbColorLensFilterOpacity = this.mInjector.getColorLensFilterTypeOpacitySetting();
            }
        }
        if (FEATURE_SUPPORT_COLOR_NEGATIVE) {
            this.mDbColorNegative = this.mInjector.getColorNegativeSetting();
        }
        this.mIsEnabledColourCorrection = this.mInjector.isEnabledColorCorrectionAosp();
        this.mDbDirectAccessColorNegative = this.mInjector.getIntDb("direct_negative");
        this.mDbDirectAccessColorAdjustment = this.mInjector.getIntDb("direct_color_adjustment");
        this.mDbDirectAccessColorLens = this.mInjector.getIntDb("direct_color_lens");
    }

    private void updateBlueLightFilterValue() {
        Injector injector = this.mInjector;
        Context context = this.mContext;
        injector.getClass();
        boolean z = Utils.getIntDb(context, "blue_light_filter", false, 0) == ECS_SCHEDULE_MANUAL;
        this.mIsEnabledBlueLightFilter = z;
        if (z) {
            Injector injector2 = this.mInjector;
            Context context2 = this.mContext;
            injector2.getClass();
            this.mDbBlueLightFilterOpacity = Utils.getIntDb(context2, "blue_light_filter_opacity", false, 0);
        }
    }

    public final void disableAllFunctions() {
        if (this.mIsStarted) {
            if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
                this.mBgHandler.removeMessages(102);
                this.mBgHandler.sendEmptyMessage(101);
            } else {
                this.mBgHandler.removeMessages(1002);
                this.mBgHandler.sendEmptyMessage(1001);
            }
        }
    }

    public final void handleDisplayStateChanged(int i) {
        if (!this.mIsStarted || i == 1001) {
            return;
        }
        disableAllFunctions();
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Log.i("BSS_DisplayConstraintHandler", Utils.getLogFormat(message));
        int i = message.what;
        if (i == ECS_SCHEDULE_MANUAL) {
            handleDisableBlueLightFilter();
        } else if (i == ECS_SCHEDULE_AUTO) {
            handleRestoreBlueLightFilter();
        } else if (i == 101) {
            handleDisableColorInversion();
        } else if (i == 102) {
            handleRestoreColorInversion();
        } else if (i != 1001) {
            if (i == 1002 && this.mIsDisabled) {
                ((SysUiManager) this.mFpServiceProvider).enableDisplayColorFunction();
                handleRestoreBlueLightFilter();
                handleRestoreColorInversion();
                updateA11ySettingDB();
                if (this.mNeedToRestoreColorAdjustment) {
                    this.mNeedToRestoreColorAdjustment = false;
                    if (this.mDbColorAdjustment == ECS_SCHEDULE_MANUAL) {
                        this.mInjector.enableMdnieColorBlind(this.mDbColorAdjustmentUseParameter);
                    }
                }
                if (this.mNeedToRestoreColorLens) {
                    this.mNeedToRestoreColorLens = false;
                    if (this.mDbColorLens == ECS_SCHEDULE_MANUAL) {
                        Log.d("BSS_DisplayConstraintHandler", "handleRestoreAll: COLOR LENS, " + this.mDbColorLensFilterType + ", " + this.mDbColorLensFilterOpacity);
                        this.mInjector.enableMdnieColorFilter(this.mDbColorLensFilterType, this.mDbColorLensFilterOpacity);
                    }
                }
                if (this.isNeedToRestoreColorNegativeOrGrayScale) {
                    this.isNeedToRestoreColorNegativeOrGrayScale = false;
                    boolean z = this.mDbColorGrayScale == ECS_SCHEDULE_MANUAL ? ECS_SCHEDULE_MANUAL : false;
                    boolean z2 = this.mDbColorNegative == ECS_SCHEDULE_MANUAL ? ECS_SCHEDULE_MANUAL : false;
                    if (z && z2) {
                        this.mInjector.enableMdnieAccessibilityMode(5);
                    } else if (z) {
                        this.mInjector.enableMdnieAccessibilityMode(4);
                    } else if (z2) {
                        this.mInjector.enableMdnieAccessibilityMode(ECS_SCHEDULE_MANUAL);
                    }
                }
                if (this.mNeedToRestoreColourInversion) {
                    this.mNeedToRestoreColourInversion = false;
                    Log.d("BSS_DisplayConstraintHandler", "handleRestoreAll: COLOR Inversion");
                    this.mInjector.putIntDb(ECS_SCHEDULE_MANUAL, "accessibility_display_inversion_enabled", true);
                }
                if (this.mNeedToRestoreColourCorrection) {
                    this.mNeedToRestoreColourCorrection = false;
                    Log.d("BSS_DisplayConstraintHandler", "handleRestoreAll: COLOR Correction");
                    this.mInjector.turnOnColorCorrectionAosp();
                }
                if (this.mNeedToRestoreDirectAccessColorNegative) {
                    this.mInjector.putIntDb(ECS_SCHEDULE_MANUAL, "direct_negative", false);
                    this.mNeedToRestoreDirectAccessColorNegative = false;
                }
                if (this.mNeedToRestoreDirectAccessColorAdjustment) {
                    this.mInjector.putIntDb(ECS_SCHEDULE_MANUAL, "direct_color_adjustment", false);
                    this.mNeedToRestoreDirectAccessColorAdjustment = false;
                }
                if (this.mNeedToRestoreDirectAccessColorLens) {
                    this.mInjector.putIntDb(ECS_SCHEDULE_MANUAL, "direct_color_lens", false);
                    this.mNeedToRestoreDirectAccessColorLens = false;
                }
                this.mIsDisabled = false;
            }
        } else if (!this.mIsDisabled) {
            this.mIsDisabled = true;
            ((SysUiManager) this.mFpServiceProvider).disableDisplayColorFunction();
            handleDisableBlueLightFilter();
            handleDisableColorInversion();
            updateA11ySettingDB();
            if (this.mDbColorAdjustment == ECS_SCHEDULE_MANUAL) {
                this.mNeedToRestoreColorAdjustment = true;
                this.mInjector.setMdnieColorBlind(this.mDbColorAdjustmentUseParameter);
            }
            if (this.mDbColorLens == ECS_SCHEDULE_MANUAL) {
                this.mNeedToRestoreColorLens = true;
                this.mInjector.disableMdnieColorFilter();
            }
            if (this.mDbColorNegative == ECS_SCHEDULE_MANUAL || this.mDbColorGrayScale == ECS_SCHEDULE_MANUAL) {
                this.isNeedToRestoreColorNegativeOrGrayScale = true;
                this.mInjector.disableMdnieAccessibilityMode();
            }
            if (this.mIsEnabledColourCorrection) {
                this.mNeedToRestoreColourCorrection = true;
                this.mInjector.turnOffColorCorrectionAosp();
            }
            if (this.mDbDirectAccessColorNegative == ECS_SCHEDULE_MANUAL) {
                this.mInjector.putIntDb(0, "direct_negative", false);
                this.mNeedToRestoreDirectAccessColorNegative = true;
            }
            if (this.mDbDirectAccessColorAdjustment == ECS_SCHEDULE_MANUAL) {
                this.mInjector.putIntDb(0, "direct_color_adjustment", false);
                this.mNeedToRestoreDirectAccessColorAdjustment = true;
            }
            if (this.mDbDirectAccessColorLens == ECS_SCHEDULE_MANUAL) {
                this.mInjector.putIntDb(0, "direct_color_lens", false);
                this.mNeedToRestoreDirectAccessColorLens = true;
            }
            Log.i("BSS_DisplayConstraintHandler", "handleDisableAll: " + this.mDbColorAdjustment + ", " + this.mDbColorLens + ", " + this.mDbColorNegative + ", " + this.mDbColorGrayScale + ", " + this.mIsEnabledColourCorrection + ", " + this.mDbDirectAccessColorNegative + ", " + this.mDbDirectAccessColorAdjustment + ", " + this.mDbDirectAccessColorLens);
        }
        return true;
    }

    public final void start() {
        if (Utils.Config.FP_FEATURE_LOCAL_HBM) {
            return;
        }
        this.mIsStarted = true;
    }

    public final void stop() {
        this.mBgHandler.removeCallbacksAndMessages(null);
        this.mIsStarted = false;
        long j = this.mNeedToRestoreColourInversion ? COLOR_INVERSION_RESTORE_DELAY : 0L;
        if (Utils.Config.FP_FEATURE_HW_LIGHT_SOURCE) {
            this.mBgHandler.removeMessages(101);
            this.mBgHandler.sendEmptyMessageDelayed(102, j);
        } else {
            this.mBgHandler.removeMessages(1001);
            this.mBgHandler.sendEmptyMessageDelayed(1002, j);
        }
    }
}
