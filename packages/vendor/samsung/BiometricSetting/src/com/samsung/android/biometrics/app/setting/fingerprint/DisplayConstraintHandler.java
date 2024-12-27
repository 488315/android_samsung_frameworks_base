package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.accessibility.AccessibilityManager;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.biometrics.app.setting.BackgroundThread;
import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.FpServiceProviderImpl;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.SemMdnieManager;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class DisplayConstraintHandler implements Handler.Callback {
    static final long COLOR_INVERSION_RESTORE_DELAY = 50;
    static final int ECS_SCHEDULE_AUTO = 2;
    static final int ECS_SCHEDULE_MANUAL = 1;
    static final int FEATURE_MDNIE_MODE;
    static final boolean FEATURE_SUPPORT_ADAPTIVE_BLF;
    public static final boolean FEATURE_SUPPORT_COLOR_ADJUSTMENT;
    public static final boolean FEATURE_SUPPORT_COLOR_LENS;
    public static final boolean FEATURE_SUPPORT_COLOR_NEGATIVE;
    public boolean isNeedToRestoreColorNegativeOrGrayScale;
    public final Handler mBgHandler = new Handler(BackgroundThread.get().getLooper(), this);
    public final Context mContext;
    public int mDbAdaptiveBlueLightFilterOpacity;
    public int mDbBlueLightFilterOpacity;
    public int mDbColorAdjustment;
    public float mDbColorAdjustmentUseParameter;
    public int mDbColorGrayScale;
    public int mDbColorLens;
    public int mDbColorLensFilterOpacity;
    public int mDbColorLensFilterType;
    public int mDbColorNegative;
    public int mDbDirectAccessColorAdjustment;
    public int mDbDirectAccessColorLens;
    public int mDbDirectAccessColorNegative;
    public final FpServiceProvider mFpServiceProvider;
    public final Injector mInjector;
    public boolean mIsBlueLightFilterDisabled;
    public boolean mIsColorInversionDisabled;
    public boolean mIsDisabled;
    public boolean mIsEnabledAdaptiveBlueLightFilter;
    public boolean mIsEnabledBlueLightFilter;
    public boolean mIsEnabledColourCorrection;
    public boolean mIsStarted;
    public boolean mNeedToRestoreColorAdjustment;
    public boolean mNeedToRestoreColorLens;
    public boolean mNeedToRestoreColourCorrection;
    public boolean mNeedToRestoreColourInversion;
    public boolean mNeedToRestoreDirectAccessColorAdjustment;
    public boolean mNeedToRestoreDirectAccessColorLens;
    public boolean mNeedToRestoreDirectAccessColorNegative;

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public final class Injector {
        public final AccessibilityManager mA11yManager;
        public final Context mContext;
        public final SemMdnieManager mMdnieManager;

        public Injector(Context context) {
            this.mContext = context;
            this.mMdnieManager = (SemMdnieManager) context.getSystemService(SemMdnieManager.class);
            this.mA11yManager =
                    (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        }

        public final void putIntDb(int i, String str, boolean z) {
            Utils.putIntDb(this.mContext, str, z, i);
        }
    }

    static {
        FEATURE_SUPPORT_ADAPTIVE_BLF =
                SemFloatingFeature.getInstance()
                                        .getInt(
                                                "SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE")
                                > 0
                        ? ECS_SCHEDULE_MANUAL
                        : false;
        int i =
                SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_MDNIE_MODE");
        FEATURE_MDNIE_MODE = i;
        FEATURE_SUPPORT_COLOR_LENS =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_COLOR_LENS");
        FEATURE_SUPPORT_COLOR_NEGATIVE = (i & 256) != 0 ? ECS_SCHEDULE_MANUAL : false;
        FEATURE_SUPPORT_COLOR_ADJUSTMENT = (i & 2048) != 0 ? ECS_SCHEDULE_MANUAL : false;
    }

    public DisplayConstraintHandler(
            Context context, FpServiceProvider fpServiceProvider, Injector injector) {
        this.mContext = context;
        this.mFpServiceProvider = fpServiceProvider;
        this.mInjector = injector;
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

    public final void handleDisableBlueLightFilter() {
        if (this.mIsBlueLightFilterDisabled) {
            return;
        }
        this.mIsBlueLightFilterDisabled = true;
        updateBlueLightFilterValue();
        SemMdnieManager semMdnieManager = this.mInjector.mMdnieManager;
        if (semMdnieManager != null) {
            semMdnieManager.setNightModeBlock(false);
        }
        Log.i(
                "BSS_DisplayConstraintHandler",
                "BLF: disable = "
                        + this.mIsEnabledBlueLightFilter
                        + ", "
                        + this.mDbBlueLightFilterOpacity);
    }

    public final void handleDisableColorInversion() {
        if (this.mIsColorInversionDisabled) {
            return;
        }
        this.mIsColorInversionDisabled = true;
        Injector injector = this.mInjector;
        Context context = this.mContext;
        injector.getClass();
        boolean z =
                Utils.getIntDb(context, "accessibility_display_inversion_enabled", true, 0)
                                == ECS_SCHEDULE_MANUAL
                        ? ECS_SCHEDULE_MANUAL
                        : false;
        if (z) {
            this.mNeedToRestoreColourInversion = true;
            Injector injector2 = this.mInjector;
            Context context2 = this.mContext;
            injector2.getClass();
            Utils.putIntDb(context2, "accessibility_display_inversion_enabled", true, 0);
        }
        Log.i("BSS_DisplayConstraintHandler", "disableColorInversion: " + z);
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        AccessibilityManager accessibilityManager;
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
                ((FpServiceProviderImpl) this.mFpServiceProvider)
                        .requestToFpSvc(7, ECS_SCHEDULE_MANUAL, 0L, null);
                handleRestoreBlueLightFilter();
                handleRestoreColorInversion();
                updateA11ySettingDB();
                if (this.mNeedToRestoreColorAdjustment) {
                    this.mNeedToRestoreColorAdjustment = false;
                    if (this.mDbColorAdjustment == ECS_SCHEDULE_MANUAL) {
                        Injector injector = this.mInjector;
                        float f = this.mDbColorAdjustmentUseParameter;
                        AccessibilityManager accessibilityManager2 = injector.mA11yManager;
                        if (accessibilityManager2 != null) {
                            accessibilityManager2.semSetMdnieColorBlind(true, f);
                        }
                    }
                }
                if (this.mNeedToRestoreColorLens) {
                    this.mNeedToRestoreColorLens = false;
                    if (this.mDbColorLens == ECS_SCHEDULE_MANUAL) {
                        Log.d(
                                "BSS_DisplayConstraintHandler",
                                "handleRestoreAll: COLOR LENS, "
                                        + this.mDbColorLensFilterType
                                        + ", "
                                        + this.mDbColorLensFilterOpacity);
                        Injector injector2 = this.mInjector;
                        int i2 = this.mDbColorLensFilterType;
                        int i3 = this.mDbColorLensFilterOpacity;
                        AccessibilityManager accessibilityManager3 = injector2.mA11yManager;
                        if (accessibilityManager3 != null) {
                            accessibilityManager3.semEnableMdnieColorFilter(i2, i3);
                        }
                    }
                }
                if (this.isNeedToRestoreColorNegativeOrGrayScale) {
                    this.isNeedToRestoreColorNegativeOrGrayScale = false;
                    boolean z =
                            this.mDbColorGrayScale == ECS_SCHEDULE_MANUAL
                                    ? ECS_SCHEDULE_MANUAL
                                    : false;
                    boolean z2 =
                            this.mDbColorNegative == ECS_SCHEDULE_MANUAL
                                    ? ECS_SCHEDULE_MANUAL
                                    : false;
                    if (z && z2) {
                        AccessibilityManager accessibilityManager4 = this.mInjector.mA11yManager;
                        if (accessibilityManager4 != null) {
                            accessibilityManager4.semSetMdnieAccessibilityMode(5, true);
                        }
                    } else if (z) {
                        AccessibilityManager accessibilityManager5 = this.mInjector.mA11yManager;
                        if (accessibilityManager5 != null) {
                            accessibilityManager5.semSetMdnieAccessibilityMode(4, true);
                        }
                    } else if (z2 && (accessibilityManager = this.mInjector.mA11yManager) != null) {
                        accessibilityManager.semSetMdnieAccessibilityMode(
                                ECS_SCHEDULE_MANUAL, true);
                    }
                }
                if (this.mNeedToRestoreColourInversion) {
                    this.mNeedToRestoreColourInversion = false;
                    Log.d("BSS_DisplayConstraintHandler", "handleRestoreAll: COLOR Inversion");
                    this.mInjector.putIntDb(
                            ECS_SCHEDULE_MANUAL, "accessibility_display_inversion_enabled", true);
                }
                if (this.mNeedToRestoreColourCorrection) {
                    this.mNeedToRestoreColourCorrection = false;
                    Log.d("BSS_DisplayConstraintHandler", "handleRestoreAll: COLOR Correction");
                    Utils.putIntDb(
                            this.mInjector.mContext,
                            "accessibility_display_daltonizer_enabled",
                            true,
                            ECS_SCHEDULE_MANUAL);
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
            ((FpServiceProviderImpl) this.mFpServiceProvider).requestToFpSvc(7, 0, 0L, null);
            handleDisableBlueLightFilter();
            handleDisableColorInversion();
            updateA11ySettingDB();
            if (this.mDbColorAdjustment == ECS_SCHEDULE_MANUAL) {
                this.mNeedToRestoreColorAdjustment = true;
                Injector injector3 = this.mInjector;
                float f2 = this.mDbColorAdjustmentUseParameter;
                AccessibilityManager accessibilityManager6 = injector3.mA11yManager;
                if (accessibilityManager6 != null) {
                    accessibilityManager6.semSetMdnieColorBlind(false, f2);
                }
            }
            if (this.mDbColorLens == ECS_SCHEDULE_MANUAL) {
                this.mNeedToRestoreColorLens = true;
                AccessibilityManager accessibilityManager7 = this.mInjector.mA11yManager;
                if (accessibilityManager7 != null) {
                    accessibilityManager7.semDisableMdnieColorFilter();
                }
            }
            if (this.mDbColorNegative == ECS_SCHEDULE_MANUAL
                    || this.mDbColorGrayScale == ECS_SCHEDULE_MANUAL) {
                this.isNeedToRestoreColorNegativeOrGrayScale = true;
                AccessibilityManager accessibilityManager8 = this.mInjector.mA11yManager;
                if (accessibilityManager8 != null) {
                    accessibilityManager8.semSetMdnieAccessibilityMode(ECS_SCHEDULE_MANUAL, false);
                }
            }
            if (this.mIsEnabledColourCorrection) {
                this.mNeedToRestoreColourCorrection = true;
                Utils.putIntDb(
                        this.mInjector.mContext,
                        "accessibility_display_daltonizer_enabled",
                        true,
                        0);
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
            Log.i(
                    "BSS_DisplayConstraintHandler",
                    "handleDisableAll: "
                            + this.mDbColorAdjustment
                            + ", "
                            + this.mDbColorLens
                            + ", "
                            + this.mDbColorNegative
                            + ", "
                            + this.mDbColorGrayScale
                            + ", "
                            + this.mIsEnabledColourCorrection
                            + ", "
                            + this.mDbDirectAccessColorNegative
                            + ", "
                            + this.mDbDirectAccessColorAdjustment
                            + ", "
                            + this.mDbDirectAccessColorLens);
        }
        return true;
    }

    public final void handleRestoreBlueLightFilter() {
        Pair pair;
        if (this.mIsBlueLightFilterDisabled) {
            updateBlueLightFilterValue();
            if (this.mIsEnabledBlueLightFilter) {
                Injector injector = this.mInjector;
                Context context = this.mContext;
                injector.getClass();
                if (Utils.getIntDb(context, "blue_light_filter_scheduled", false, 0) != 0) {
                    Injector injector2 = this.mInjector;
                    Context context2 = this.mContext;
                    injector2.getClass();
                    int intDb = Utils.getIntDb(context2, "blue_light_filter_type", false, 0);
                    if (intDb == ECS_SCHEDULE_MANUAL || intDb == ECS_SCHEDULE_AUTO) {
                        if (intDb == ECS_SCHEDULE_MANUAL) {
                            Injector injector3 = this.mInjector;
                            Context context3 = this.mContext;
                            injector3.getClass();
                            pair =
                                    new Pair(
                                            Long.valueOf(
                                                    Utils.getLongDb(
                                                            context3,
                                                            "blue_light_filter_on_time",
                                                            1140L)),
                                            Long.valueOf(
                                                    Utils.getLongDb(
                                                            context3,
                                                            "blue_light_filter_off_time",
                                                            420L)));
                        } else {
                            Injector injector4 = this.mInjector;
                            Context context4 = this.mContext;
                            injector4.getClass();
                            pair =
                                    new Pair(
                                            Long.valueOf(
                                                    Utils.getLongDb(
                                                            context4,
                                                            "blue_light_filter_automatic_on_time",
                                                            1140L)),
                                            Long.valueOf(
                                                    Utils.getLongDb(
                                                            context4,
                                                            "blue_light_filter_automatic_off_time",
                                                            420L)));
                        }
                        this.mInjector.getClass();
                        GregorianCalendar gregorianCalendar =
                                new GregorianCalendar(TimeZone.getDefault());
                        gregorianCalendar.setTime(new Date());
                        long j = gregorianCalendar.get(12) + (gregorianCalendar.get(11) * 60);
                        if (((Long) pair.first).longValue() < ((Long) pair.second).longValue()) {
                            if (j < ((Long) pair.first).longValue()
                                    || j >= ((Long) pair.second).longValue()) {
                                this.mIsEnabledBlueLightFilter = false;
                            }
                        } else if (j >= ((Long) pair.second).longValue()
                                && j < ((Long) pair.first).longValue()) {
                            this.mIsEnabledBlueLightFilter = false;
                        }
                        Log.i(
                                "BSS_DisplayConstraintHandler",
                                "Scheduled BLF: "
                                        + pair.first
                                        + ", "
                                        + pair.second
                                        + ", "
                                        + j
                                        + ", "
                                        + this.mIsEnabledBlueLightFilter);
                    }
                }
            }
            if (FEATURE_SUPPORT_ADAPTIVE_BLF) {
                Injector injector5 = this.mInjector;
                Context context5 = this.mContext;
                injector5.getClass();
                boolean z =
                        Utils.getIntDb(context5, "blue_light_filter_adaptive_mode", false, 0)
                                        == ECS_SCHEDULE_MANUAL
                                ? ECS_SCHEDULE_MANUAL
                                : false;
                this.mIsEnabledAdaptiveBlueLightFilter = z;
                if (z) {
                    SemMdnieManager semMdnieManager = this.mInjector.mMdnieManager;
                    this.mDbAdaptiveBlueLightFilterOpacity =
                            semMdnieManager == null ? 0 : semMdnieManager.getNightModeStep();
                }
            }
            SemMdnieManager semMdnieManager2 = this.mInjector.mMdnieManager;
            if (semMdnieManager2 != null) {
                semMdnieManager2.setNightModeBlock(true);
            }
            if (this.mIsEnabledBlueLightFilter) {
                Log.i(
                        "BSS_DisplayConstraintHandler",
                        "restoreBlueLightFilter: "
                                + this.mDbBlueLightFilterOpacity
                                + ", "
                                + this.mIsEnabledAdaptiveBlueLightFilter
                                + ", "
                                + this.mDbAdaptiveBlueLightFilterOpacity);
                Injector injector6 = this.mInjector;
                int i =
                        this.mIsEnabledAdaptiveBlueLightFilter
                                ? this.mDbAdaptiveBlueLightFilterOpacity
                                : this.mDbBlueLightFilterOpacity;
                SemMdnieManager semMdnieManager3 = injector6.mMdnieManager;
                if (semMdnieManager3 != null) {
                    semMdnieManager3.enableNightMode(i);
                }
            }
            this.mIsBlueLightFilterDisabled = false;
        }
    }

    public final void handleRestoreColorInversion() {
        if (this.mIsColorInversionDisabled) {
            if (this.mNeedToRestoreColourInversion) {
                this.mNeedToRestoreColourInversion = false;
                Log.i("BSS_DisplayConstraintHandler", "restoreColorInversion");
                Injector injector = this.mInjector;
                Context context = this.mContext;
                injector.getClass();
                Utils.putIntDb(
                        context,
                        "accessibility_display_inversion_enabled",
                        true,
                        ECS_SCHEDULE_MANUAL);
            }
            this.mIsColorInversionDisabled = false;
        }
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

    public final void updateA11ySettingDB() {
        int i;
        if (FEATURE_SUPPORT_COLOR_ADJUSTMENT) {
            int intDb = Utils.getIntDb(this.mInjector.mContext, "color_blind", false, -1);
            this.mDbColorAdjustment = intDb;
            if (intDb == ECS_SCHEDULE_MANUAL) {
                i =
                        Utils.getIntDb(
                                this.mInjector.mContext,
                                "color_adjustment_type",
                                true,
                                ECS_SCHEDULE_AUTO);
                ContentResolver contentResolver = this.mInjector.mContext.getContentResolver();
                float f = RecyclerView.DECELERATION_RATE;
                try {
                    f =
                            Settings.Secure.getFloatForUser(
                                    contentResolver,
                                    "color_blind_user_parameter",
                                    RecyclerView.DECELERATION_RATE,
                                    -2);
                } catch (Exception e) {
                    FocusableWindow$$ExternalSyntheticOutline0.m(
                            e, new StringBuilder("getFloatDb: "), "BSS_Utils");
                }
                this.mDbColorAdjustmentUseParameter = f;
            } else {
                i = 0;
            }
            this.mDbColorGrayScale =
                    (this.mDbColorAdjustment == ECS_SCHEDULE_MANUAL && i == 0)
                            ? ECS_SCHEDULE_MANUAL
                            : 0;
        }
        if (FEATURE_SUPPORT_COLOR_LENS) {
            int intDb2 = Utils.getIntDb(this.mInjector.mContext, "color_lens_switch", true, 0);
            this.mDbColorLens = intDb2;
            if (intDb2 == ECS_SCHEDULE_MANUAL) {
                this.mDbColorLensFilterType =
                        Utils.getIntDb(this.mInjector.mContext, "color_lens_type", true, 0);
                this.mDbColorLensFilterOpacity =
                        Utils.getIntDb(this.mInjector.mContext, "color_lens_opacity", true, 0);
            }
        }
        if (FEATURE_SUPPORT_COLOR_NEGATIVE) {
            this.mDbColorNegative =
                    Utils.getIntDb(this.mInjector.mContext, "high_contrast", false, 0);
        }
        this.mIsEnabledColourCorrection =
                Utils.getIntDb(
                                this.mInjector.mContext,
                                "accessibility_display_daltonizer_enabled",
                                true,
                                0)
                        == ECS_SCHEDULE_MANUAL;
        this.mDbDirectAccessColorNegative =
                Utils.getIntDb(this.mInjector.mContext, "direct_negative", false, 0);
        this.mDbDirectAccessColorAdjustment =
                Utils.getIntDb(this.mInjector.mContext, "direct_color_adjustment", false, 0);
        this.mDbDirectAccessColorLens =
                Utils.getIntDb(this.mInjector.mContext, "direct_color_lens", false, 0);
    }

    public final void updateBlueLightFilterValue() {
        Injector injector = this.mInjector;
        Context context = this.mContext;
        injector.getClass();
        boolean z = Utils.getIntDb(context, "blue_light_filter", false, 0) == ECS_SCHEDULE_MANUAL;
        this.mIsEnabledBlueLightFilter = z;
        if (z) {
            Injector injector2 = this.mInjector;
            Context context2 = this.mContext;
            injector2.getClass();
            this.mDbBlueLightFilterOpacity =
                    Utils.getIntDb(context2, "blue_light_filter_opacity", false, 0);
        }
    }
}
