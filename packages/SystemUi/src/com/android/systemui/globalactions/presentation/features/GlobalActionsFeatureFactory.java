package com.android.systemui.globalactions.presentation.features;

import android.content.Context;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.globalactions.features.CapturedBlurStrategy;
import com.android.systemui.globalactions.features.CoverSupportStrategy;
import com.android.systemui.globalactions.features.FingerprintInDisplayStrategy;
import com.android.systemui.globalactions.features.KnoxMDMStrategy;
import com.android.systemui.globalactions.features.KnoxSDKStrategy;
import com.android.systemui.globalactions.features.SepBlurStrategy;
import com.android.systemui.globalactions.features.SideKeyStrategy;
import com.android.systemui.globalactions.util.KeyguardUpdateMonitorWrapper;
import com.android.systemui.globalactions.util.KnoxCustomManagerWrapper;
import com.android.systemui.globalactions.util.ProKioskManagerWrapper;
import com.android.systemui.globalactions.util.ScreenCapturePopupController;
import com.samsung.android.globalactions.features.DataModeStrategy;
import com.samsung.android.globalactions.features.DemoModeStrategy;
import com.samsung.android.globalactions.features.DesktopModeStrategy;
import com.samsung.android.globalactions.features.FOTAForceUpdateStrategy;
import com.samsung.android.globalactions.features.ForceRestartMessageStrategy;
import com.samsung.android.globalactions.features.FrontDisplayStrategy;
import com.samsung.android.globalactions.features.FrontLargeDisplayStrategy;
import com.samsung.android.globalactions.features.KnoxContainerStrategy;
import com.samsung.android.globalactions.features.LockdownModeStrategy;
import com.samsung.android.globalactions.features.NavigationBarStrategy;
import com.samsung.android.globalactions.features.PowerOffLockStrategy;
import com.samsung.android.globalactions.features.ReserveBatteryModeStrategy;
import com.samsung.android.globalactions.features.SafetyCareStrategy;
import com.samsung.android.globalactions.features.SecFOTAForceUpdateStrategy;
import com.samsung.android.globalactions.features.SktStrategy;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.features.Features;
import com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModelFactory;
import com.samsung.android.globalactions.util.BiometricPromptWrapper;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.DesktopModeManagerWrapper;
import com.samsung.android.globalactions.util.KeyGuardManagerWrapper;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.ScreenCaptureUtil;
import com.samsung.android.globalactions.util.SystemController;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.globalactions.util.UtilFactory;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GlobalActionsFeatureFactory implements FeatureFactory {
    public final ConditionChecker mConditionChecker;
    public final Context mContext;
    public final Features mFeatures;
    public final LogWrapper mLogWrapper;
    public final UtilFactory mUtilFactory;
    public final ExtendableGlobalActionsView mView;
    public final ActionViewModelFactory mViewModelFactory;

    public GlobalActionsFeatureFactory(Context context, ExtendableGlobalActionsView extendableGlobalActionsView, UtilFactory utilFactory, ActionViewModelFactory actionViewModelFactory, Features features, ConditionChecker conditionChecker, LogWrapper logWrapper) {
        this.mContext = context;
        this.mView = extendableGlobalActionsView;
        this.mUtilFactory = utilFactory;
        this.mViewModelFactory = actionViewModelFactory;
        this.mFeatures = features;
        this.mConditionChecker = conditionChecker;
        this.mLogWrapper = logWrapper;
    }

    public final List createActionInteractionStrategies(String str) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("DEMO_MODE") && str.equals(ImsProfile.PDN_EMERGENCY)) {
            arrayList.add(new DemoModeStrategy((ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
        }
        if (this.mFeatures.isEnabled("KNOX_DEVICE_MANAGER") && (str.equals("data_mode") || str.equals("power"))) {
            arrayList.add(new KnoxMDMStrategy(this.mConditionChecker));
        }
        str.getClass();
        switch (str) {
            case "power":
            case "restart":
            case "emergency":
                arrayList.add(new SktStrategy(this.mConditionChecker, (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class), (LogWrapper) this.mUtilFactory.get(LogWrapper.class)));
                break;
        }
        loggingStrategy("ActionInteractionStrategy", arrayList);
        return arrayList;
    }

    public final List createActionUpdateStrategies() {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("SUPPORT_SIDE_KEY")) {
            arrayList.add(new SideKeyStrategy(this.mViewModelFactory, this.mConditionChecker, this.mContext, (ScreenCapturePopupController) this.mUtilFactory.get(ScreenCapturePopupController.class)));
        }
        return arrayList;
    }

    public final List createActionsCreationStrategies(SamsungGlobalActions samsungGlobalActions) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("SUPPORT_SIDE_KEY")) {
            arrayList.add(new SideKeyStrategy(this.mViewModelFactory, this.mConditionChecker, this.mContext, (ScreenCapturePopupController) this.mUtilFactory.get(ScreenCapturePopupController.class)));
        }
        if (this.mFeatures.isEnabled("DATA_MODE")) {
            arrayList.add(new DataModeStrategy(this.mViewModelFactory, this.mConditionChecker));
        }
        if (this.mFeatures.isEnabled("LOCK_DOWN_MODE")) {
            arrayList.add(new LockdownModeStrategy(this.mViewModelFactory, this.mConditionChecker));
        }
        if (this.mFeatures.isEnabled("FORCE_RESTART_MESSAGE")) {
            arrayList.add(new ForceRestartMessageStrategy(this.mViewModelFactory, this.mConditionChecker));
        }
        if (this.mFeatures.isEnabled("FINGERPRINT_IN_DISPLAY")) {
            arrayList.add(new FingerprintInDisplayStrategy((KeyguardUpdateMonitorWrapper) this.mUtilFactory.get(KeyguardUpdateMonitorWrapper.class)));
        }
        if (this.mFeatures.isEnabled("KNOX_SDK")) {
            arrayList.add(new KnoxSDKStrategy(this.mViewModelFactory, this.mConditionChecker, (KnoxCustomManagerWrapper) this.mUtilFactory.get(KnoxCustomManagerWrapper.class), (ProKioskManagerWrapper) this.mUtilFactory.get(ProKioskManagerWrapper.class), (LogWrapper) this.mUtilFactory.get(LogWrapper.class), samsungGlobalActions));
        }
        loggingStrategy("ActionsCreationStrategy", arrayList);
        return arrayList;
    }

    public final List createDefaultActionsCreationStrategy(SamsungGlobalActions samsungGlobalActions, String str) {
        ArrayList arrayList = new ArrayList();
        if (str.equals(ImsProfile.PDN_EMERGENCY)) {
            if (this.mFeatures.isEnabled("SAFETY_CARE")) {
                arrayList.add(new SafetyCareStrategy(samsungGlobalActions, this.mConditionChecker, (LogWrapper) this.mUtilFactory.get(LogWrapper.class)));
            }
            if (this.mFeatures.isEnabled("DESKTOP_MODE")) {
                arrayList.add(new DesktopModeStrategy(this.mContext, this.mView, (DesktopModeManagerWrapper) this.mUtilFactory.get(DesktopModeManagerWrapper.class), this.mConditionChecker));
            }
            if (this.mFeatures.isEnabled("KNOX_SDK")) {
                arrayList.add(new KnoxSDKStrategy(this.mViewModelFactory, this.mConditionChecker, (KnoxCustomManagerWrapper) this.mUtilFactory.get(KnoxCustomManagerWrapper.class), (ProKioskManagerWrapper) this.mUtilFactory.get(ProKioskManagerWrapper.class), (LogWrapper) this.mUtilFactory.get(LogWrapper.class), samsungGlobalActions));
            }
            if (this.mFeatures.isEnabled("SCOVER")) {
                arrayList.add(new CoverSupportStrategy(this.mConditionChecker, (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
            }
            if (this.mFeatures.isEnabled("RESERVE_BATTERY_MODE")) {
                arrayList.add(new ReserveBatteryModeStrategy(this.mConditionChecker));
            }
        } else if (str.equals("bug_report")) {
            if (this.mFeatures.isEnabled("SCOVER")) {
                arrayList.add(new CoverSupportStrategy(this.mConditionChecker, (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
            }
        } else if (str.equals("emergency_call")) {
            if (this.mFeatures.isEnabled("SCOVER")) {
                arrayList.add(new CoverSupportStrategy(this.mConditionChecker, (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
            }
        } else if (str.equals("medical_info") && this.mFeatures.isEnabled("SCOVER")) {
            arrayList.add(new CoverSupportStrategy(this.mConditionChecker, (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
        }
        loggingStrategy("DefaultActionsCreationStrategy", arrayList);
        return arrayList;
    }

    public final List createDisposingStrategies(SamsungGlobalActions samsungGlobalActions) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("SCOVER")) {
            arrayList.add(new CoverSupportStrategy(this.mConditionChecker, (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
        }
        if (this.mFeatures.isEnabled("FINGERPRINT_IN_DISPLAY")) {
            arrayList.add(new FingerprintInDisplayStrategy((KeyguardUpdateMonitorWrapper) this.mUtilFactory.get(KeyguardUpdateMonitorWrapper.class)));
        }
        if (this.mFeatures.isEnabled("SUPPORT_SIDE_KEY")) {
            arrayList.add(new SideKeyStrategy(this.mViewModelFactory, this.mConditionChecker, this.mContext, (ScreenCapturePopupController) this.mUtilFactory.get(ScreenCapturePopupController.class)));
        }
        if (this.mFeatures.isEnabled("FRONT_LARGE_COVER_DISPLAY")) {
            arrayList.add(new FrontLargeDisplayStrategy(samsungGlobalActions, this.mLogWrapper, (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class)));
        }
        if (this.mFeatures.isEnabled("FRONT_COVER_DISPLAY")) {
            arrayList.add(new FrontDisplayStrategy(samsungGlobalActions, this.mLogWrapper, (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class)));
        }
        if (this.mFeatures.isEnabled("DESKTOP_MODE")) {
            arrayList.add(new DesktopModeStrategy(this.mContext, this.mView, (DesktopModeManagerWrapper) this.mUtilFactory.get(DesktopModeManagerWrapper.class), this.mConditionChecker));
        }
        loggingStrategy("DisposingStrategy", arrayList);
        return arrayList;
    }

    public final List createInitializationStrategies(SamsungGlobalActions samsungGlobalActions) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("SAFETY_CARE")) {
            arrayList.add(new SafetyCareStrategy(samsungGlobalActions, this.mConditionChecker, (LogWrapper) this.mUtilFactory.get(LogWrapper.class)));
        }
        if (this.mFeatures.isEnabled("SCOVER")) {
            arrayList.add(new CoverSupportStrategy(this.mConditionChecker, (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
        }
        if (this.mFeatures.isEnabled("DESKTOP_MODE")) {
            arrayList.add(new DesktopModeStrategy(this.mContext, this.mView, (DesktopModeManagerWrapper) this.mUtilFactory.get(DesktopModeManagerWrapper.class), this.mConditionChecker));
        }
        if (this.mFeatures.isEnabled("KNOX_SDK")) {
            arrayList.add(new KnoxSDKStrategy(this.mViewModelFactory, this.mConditionChecker, (KnoxCustomManagerWrapper) this.mUtilFactory.get(KnoxCustomManagerWrapper.class), (ProKioskManagerWrapper) this.mUtilFactory.get(ProKioskManagerWrapper.class), (LogWrapper) this.mUtilFactory.get(LogWrapper.class), samsungGlobalActions));
        }
        if (this.mFeatures.isEnabled("KNOX_CONTAINER")) {
            arrayList.add(new KnoxContainerStrategy(samsungGlobalActions, this.mConditionChecker));
        }
        loggingStrategy("InitializationStrategy", arrayList);
        return arrayList;
    }

    public final List createOnKeyListenerStrategy(SamsungGlobalActions samsungGlobalActions) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("KNOX_SDK")) {
            arrayList.add(new KnoxSDKStrategy(this.mViewModelFactory, this.mConditionChecker, (KnoxCustomManagerWrapper) this.mUtilFactory.get(KnoxCustomManagerWrapper.class), (ProKioskManagerWrapper) this.mUtilFactory.get(ProKioskManagerWrapper.class), (LogWrapper) this.mUtilFactory.get(LogWrapper.class), samsungGlobalActions));
        }
        loggingStrategy("OnKeyListenerStrategy", arrayList);
        return arrayList;
    }

    public final List createSecureConfirmStrategy(SamsungGlobalActions samsungGlobalActions, String str) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        str.getClass();
        switch (str) {
            case "safe_mode":
            case "data_mode":
            case "power":
            case "side_key_settings":
            case "restart":
            case "emergency":
                if (this.mFeatures.isEnabled("FINGERPRINT_IN_DISPLAY")) {
                    arrayList.add(new FingerprintInDisplayStrategy((KeyguardUpdateMonitorWrapper) this.mUtilFactory.get(KeyguardUpdateMonitorWrapper.class)));
                }
                if (this.mFeatures.isEnabled("SCOVER")) {
                    arrayList.add(new CoverSupportStrategy(this.mConditionChecker, (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
                    break;
                }
                break;
        }
        if (str.equals("power") && this.mFeatures.isEnabled("POWER_OFF_LOCK")) {
            arrayList.add(new PowerOffLockStrategy(this.mConditionChecker, (BiometricPromptWrapper) this.mUtilFactory.get(BiometricPromptWrapper.class)));
        }
        if (str.equals("power") || str.equals("restart")) {
            if (this.mFeatures.isEnabled("FRONT_LARGE_COVER_DISPLAY")) {
                arrayList.add(new FrontLargeDisplayStrategy(samsungGlobalActions, this.mLogWrapper, (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class)));
            }
            if (this.mFeatures.isEnabled("FRONT_COVER_DISPLAY")) {
                arrayList.add(new FrontDisplayStrategy(samsungGlobalActions, this.mLogWrapper, (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class)));
            }
        }
        return arrayList;
    }

    public final List createSoftwareUpdateStrategy(SamsungGlobalActions samsungGlobalActions, String str) {
        ArrayList arrayList = new ArrayList();
        if (str.equals("restart")) {
            arrayList.add(new FOTAForceUpdateStrategy(this.mConditionChecker, (SystemController) this.mUtilFactory.get(SystemController.class)));
            arrayList.add(new SecFOTAForceUpdateStrategy(this.mConditionChecker, (SystemController) this.mUtilFactory.get(SystemController.class)));
        }
        return arrayList;
    }

    public final List createViewInflateStrategy() {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("SF_EFFECT")) {
            arrayList.add(new SepBlurStrategy(this.mConditionChecker));
        } else if (this.mFeatures.isEnabled("CAPTURED_BLUR")) {
            arrayList.add(new CapturedBlurStrategy((ScreenCaptureUtil) this.mUtilFactory.get(ScreenCaptureUtil.class), this.mConditionChecker));
        }
        return arrayList;
    }

    public final List createWindowDecorationStrategies(SamsungGlobalActions samsungGlobalActions) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("NAV_BAR") && !this.mFeatures.isEnabled("FRONT_LARGE_COVER_DISPLAY")) {
            arrayList.add(new NavigationBarStrategy(this.mConditionChecker));
        }
        if (this.mFeatures.isEnabled("SCOVER")) {
            arrayList.add(new CoverSupportStrategy(this.mConditionChecker, (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
        }
        loggingStrategy("WindowDecorationStrategy", arrayList);
        return arrayList;
    }

    public final List createWindowManagerFunctionStrategy(SamsungGlobalActions samsungGlobalActions, String str) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("SCOVER")) {
            arrayList.add(new CoverSupportStrategy(this.mConditionChecker, (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class), samsungGlobalActions, (LogWrapper) this.mUtilFactory.get(LogWrapper.class), (KeyGuardManagerWrapper) this.mUtilFactory.get(KeyGuardManagerWrapper.class), (ToastController) this.mUtilFactory.get(ToastController.class), (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class)));
        }
        loggingStrategy("WindowManagerFunctionStrategy", arrayList);
        return arrayList;
    }

    public final void loggingStrategy(String str, List list) {
        Iterator it = ((ArrayList) list).iterator();
        String str2 = "";
        while (it.hasNext()) {
            Object next = it.next();
            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str2);
            m18m.append(next.getClass().getSimpleName());
            m18m.append(" ");
            str2 = m18m.toString();
        }
        this.mLogWrapper.i("GlobalActionsFeatureFactory", MotionLayout$$ExternalSyntheticOutline0.m22m("check ", str, " [ ", str2, "]"));
    }
}
