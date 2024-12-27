package com.android.server.policy.globalactions.presentation.features;

import android.content.Context;

import com.samsung.android.globalactions.features.DataModeStrategy;
import com.samsung.android.globalactions.features.DesktopModeStrategy;
import com.samsung.android.globalactions.features.FOTAForceUpdateStrategy;
import com.samsung.android.globalactions.features.KnoxContainerStrategy;
import com.samsung.android.globalactions.features.LockdownModeStrategy;
import com.samsung.android.globalactions.features.NavigationBarStrategy;
import com.samsung.android.globalactions.features.ReserveBatteryModeStrategy;
import com.samsung.android.globalactions.features.SafetyCareStrategy;
import com.samsung.android.globalactions.features.SecFOTAForceUpdateStrategy;
import com.samsung.android.globalactions.features.SktStrategy;
import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.features.Features;
import com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionViewModelFactory;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.DesktopModeManagerWrapper;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SystemController;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.globalactions.util.UtilFactory;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class GlobalActionsFeatureFactory implements FeatureFactory {
    public ConditionChecker mConditionChecker;
    public Context mContext;
    public Features mFeatures;
    public UtilFactory mUtilFactory;
    public ExtendableGlobalActionsView mView;
    public DefaultActionViewModelFactory mViewModelFactory;

    public final List createActionInteractionStrategies(String str) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        str.getClass();
        switch (str) {
            case "power":
            case "restart":
            case "emergency":
                arrayList.add(
                        new SktStrategy(
                                this.mConditionChecker,
                                (ToastController) this.mUtilFactory.get(ToastController.class),
                                (ResourcesWrapper) this.mUtilFactory.get(ResourcesWrapper.class),
                                (LogWrapper) this.mUtilFactory.get(LogWrapper.class)));
            default:
                return arrayList;
        }
    }

    public final List createActionsCreationStrategies(SamsungGlobalActions samsungGlobalActions) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("DATA_MODE")) {
            arrayList.add(new DataModeStrategy(this.mViewModelFactory, this.mConditionChecker));
        }
        if (this.mFeatures.isEnabled("LOCKDOWN_MODE")) {
            arrayList.add(new LockdownModeStrategy(this.mViewModelFactory, this.mConditionChecker));
        }
        return arrayList;
    }

    public final List createDefaultActionsCreationStrategy(
            SamsungGlobalActions samsungGlobalActions, String str) {
        ArrayList arrayList = new ArrayList();
        if (str.equals("emergency")) {
            if (this.mFeatures.isEnabled("SAFETY_CARE")) {
                arrayList.add(
                        new SafetyCareStrategy(
                                samsungGlobalActions,
                                this.mConditionChecker,
                                (LogWrapper) this.mUtilFactory.get(LogWrapper.class)));
            }
            if (this.mFeatures.isEnabled("DESKTOP_MODE")) {
                arrayList.add(
                        new DesktopModeStrategy(
                                this.mContext,
                                this.mView,
                                (DesktopModeManagerWrapper)
                                        this.mUtilFactory.get(DesktopModeManagerWrapper.class),
                                this.mConditionChecker));
            }
            if (this.mFeatures.isEnabled("RESERVE_BATTERY_MODE")) {
                arrayList.add(new ReserveBatteryModeStrategy(this.mConditionChecker));
            }
        }
        return arrayList;
    }

    public final List createDisposingStrategies(SamsungGlobalActions samsungGlobalActions) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("DESKTOP_MODE")) {
            arrayList.add(
                    new DesktopModeStrategy(
                            this.mContext,
                            this.mView,
                            (DesktopModeManagerWrapper)
                                    this.mUtilFactory.get(DesktopModeManagerWrapper.class),
                            this.mConditionChecker));
        }
        return arrayList;
    }

    public final List createInitializationStrategies(SamsungGlobalActions samsungGlobalActions) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("DESKTOP_MODE")) {
            arrayList.add(
                    new DesktopModeStrategy(
                            this.mContext,
                            this.mView,
                            (DesktopModeManagerWrapper)
                                    this.mUtilFactory.get(DesktopModeManagerWrapper.class),
                            this.mConditionChecker));
        }
        if (this.mFeatures.isEnabled("SAFETY_CARE")) {
            arrayList.add(
                    new SafetyCareStrategy(
                            samsungGlobalActions,
                            this.mConditionChecker,
                            (LogWrapper) this.mUtilFactory.get(LogWrapper.class)));
        }
        if (this.mFeatures.isEnabled("KNOX_CONTAINER")) {
            arrayList.add(new KnoxContainerStrategy(samsungGlobalActions, this.mConditionChecker));
        }
        return arrayList;
    }

    public final List createOnKeyListenerStrategy(SamsungGlobalActions samsungGlobalActions) {
        return new ArrayList();
    }

    public final List createSecureConfirmStrategy(
            SamsungGlobalActions samsungGlobalActions, String str) {
        return new ArrayList();
    }

    public final List createSoftwareUpdateStrategy(
            SamsungGlobalActions samsungGlobalActions, String str) {
        ArrayList arrayList = new ArrayList();
        if (str.equals("restart")) {
            arrayList.add(
                    new FOTAForceUpdateStrategy(
                            this.mConditionChecker,
                            (SystemController) this.mUtilFactory.get(SystemController.class)));
            arrayList.add(
                    new SecFOTAForceUpdateStrategy(
                            this.mConditionChecker,
                            (SystemController) this.mUtilFactory.get(SystemController.class)));
        }
        return arrayList;
    }

    public final List createViewInflateStrategy() {
        return new ArrayList();
    }

    public final List createWindowDecorationStrategies(SamsungGlobalActions samsungGlobalActions) {
        ArrayList arrayList = new ArrayList();
        if (this.mFeatures.isEnabled("NAV_BAR")) {
            arrayList.add(new NavigationBarStrategy(this.mConditionChecker));
        }
        return arrayList;
    }

    public final List createWindowManagerFunctionStrategy(
            SamsungGlobalActions samsungGlobalActions, String str) {
        return new ArrayList();
    }
}
