package com.android.systemui.statusbar.phone;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.NetspeedView;
import dagger.Lazy;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class PhoneStatusBarViewControllerExt implements Dumpable {
    public final ConfigurationController configurationController;
    public final PhoneStatusBarViewControllerExt$configurationListener$1 configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt$configurationListener$1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) throws Resources.NotFoundException {
            if (configuration == null) {
                return;
            }
            PhoneStatusBarViewControllerExt phoneStatusBarViewControllerExt = this.this$0;
            PhoneStatusBarViewController phoneStatusBarViewController = phoneStatusBarViewControllerExt.phoneStatusBarViewController;
            if (phoneStatusBarViewController != null) {
                phoneStatusBarViewControllerExt.indicatorGardenPresenter.onGardenConfigurationChanged(phoneStatusBarViewController, configuration);
            }
            IndicatorMarqueeGardener indicatorMarqueeGardener = phoneStatusBarViewControllerExt.indicatorMarqueeGardener;
            indicatorMarqueeGardener.context.getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift);
            int rotation = indicatorMarqueeGardener.context.getResources().getConfiguration().windowConfiguration.getRotation();
            if (indicatorMarqueeGardener.lastRotation != rotation) {
                indicatorMarqueeGardener.lastRotation = rotation;
                indicatorMarqueeGardener.updateMarqueeValues();
            }
            phoneStatusBarViewControllerExt.phoneStatusBarClockManager.updateResources();
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            final PhoneStatusBarViewControllerExt phoneStatusBarViewControllerExt = this.this$0;
            phoneStatusBarViewControllerExt.mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt$configurationListener$1$onDensityOrFontScaleChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    IndicatorGardenInputProperties indicatorGardenInputProperties = phoneStatusBarViewControllerExt.indicatorGardenPresenter.inputProperties;
                    indicatorGardenInputProperties.updateWindowMetrics();
                    indicatorGardenInputProperties.updatePaddingValues();
                }
            });
        }
    };
    public final DarkIconDispatcher darkIconDispatcher;
    public final DumpManager dumpManager;
    public final IndicatorCutoutUtil indicatorCutoutUtil;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public final IndicatorMarqueeGardener indicatorMarqueeGardener;
    public final KeyguardStateController keyguardStateController;
    public final KnoxStatusBarControlViewModel knoxStateBarControlViewModel;
    public final Handler mainHandler;
    public NetspeedView netspeedView;
    public final Lazy netspeedViewControllerLazy;
    public final PhoneStatusBarClockManager phoneStatusBarClockManager;
    public PhoneStatusBarView phoneStatusBarView;
    public PhoneStatusBarViewController phoneStatusBarViewController;
    public final PrivacyDotViewController privacyDotViewController;
    public final SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper;
    public final StatusIconContainerController statusIconContainerController;
    public final TwoPhoneModeIconController twoPhoneModeIconController;
    public final IndicatorGardenViewTreeLogHelper viewTreeLogHelper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.phone.PhoneStatusBarViewControllerExt$configurationListener$1] */
    public PhoneStatusBarViewControllerExt(Handler handler, DarkIconDispatcher darkIconDispatcher, ConfigurationController configurationController, KeyguardStateController keyguardStateController, IndicatorGardenPresenter indicatorGardenPresenter, PrivacyDotViewController privacyDotViewController, DumpManager dumpManager, IndicatorGardenViewTreeLogHelper indicatorGardenViewTreeLogHelper, IndicatorMarqueeGardener indicatorMarqueeGardener, StatusIconContainerController statusIconContainerController, IndicatorCutoutUtil indicatorCutoutUtil, TwoPhoneModeIconController twoPhoneModeIconController, PhoneStatusBarClockManager phoneStatusBarClockManager, KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, Lazy lazy, SamsungStatusBarGrayIconHelper samsungStatusBarGrayIconHelper) {
        this.mainHandler = handler;
        this.darkIconDispatcher = darkIconDispatcher;
        this.configurationController = configurationController;
        this.keyguardStateController = keyguardStateController;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
        this.privacyDotViewController = privacyDotViewController;
        this.dumpManager = dumpManager;
        this.viewTreeLogHelper = indicatorGardenViewTreeLogHelper;
        this.indicatorMarqueeGardener = indicatorMarqueeGardener;
        this.statusIconContainerController = statusIconContainerController;
        this.indicatorCutoutUtil = indicatorCutoutUtil;
        this.twoPhoneModeIconController = twoPhoneModeIconController;
        this.phoneStatusBarClockManager = phoneStatusBarClockManager;
        this.knoxStateBarControlViewModel = knoxStatusBarControlViewModel;
        this.netspeedViewControllerLazy = lazy;
        this.samsungStatusBarGrayIconHelper = samsungStatusBarGrayIconHelper;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PhoneStatusBarView phoneStatusBarView = this.phoneStatusBarView;
        if (phoneStatusBarView != null) {
            this.viewTreeLogHelper.getClass();
            printWriter.println("IndicatorGardenViewTreeLogHelper");
            IndicatorGardenViewTreeLogHelper.printDumpLog(printWriter, phoneStatusBarView, 0, 0);
            IndicatorGardenViewTreeLogHelper.printChildWidthRecursive(printWriter, phoneStatusBarView, 1);
        }
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            this.statusIconContainerController.dump(printWriter);
        }
        KnoxStatusBarControlViewModel knoxStatusBarControlViewModel = this.knoxStateBarControlViewModel;
        knoxStatusBarControlViewModel.getClass();
        printWriter.println();
        printWriter.println("  KnoxStatusBarControlViewModel");
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    statusBarHidden=", knoxStatusBarControlViewModel.statusBarHidden.$$delegate_0.getValue(), printWriter);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("    statusBarIconsEnabled=", knoxStatusBarControlViewModel.statusBarIconsEnabled.$$delegate_0.getValue(), printWriter);
        printWriter.println("    knoxStatusBarCustomText=" + knoxStatusBarControlViewModel.knoxStatusBarCustomText.$$delegate_0.getValue());
        printWriter.println(" BasicRune.STATUS_LAYOUT_MARQUEE: true");
    }
}
