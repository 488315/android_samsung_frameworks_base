package com.android.systemui.statusbar.policy;

import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSClockIndicatorViewController extends ViewController implements Dumpable, ConfigurationController.ConfigurationListener, TunerService.Tunable {
    public final ConfigurationController configurationController;
    public final DarkIconDispatcher darkIconDispatcher;
    public final QSClockIndicatorViewController$dateClockStateCallback$1 dateClockStateCallback;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final QSClockBellTower qsClockBellTower;
    public final SettingsHelper settingsHelper;
    public final QSClockIndicatorViewController$timeFormatChangedListener$1 timeFormatChangedListener;
    public final TunerService tunerService;
    public final QSClockIndicatorView view;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.policy.QSClockIndicatorViewController$dateClockStateCallback$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.policy.QSClockIndicatorViewController$timeFormatChangedListener$1] */
    public QSClockIndicatorViewController(QSClockIndicatorView qSClockIndicatorView, DarkIconDispatcher darkIconDispatcher, DumpManager dumpManager, ConfigurationController configurationController, TunerService tunerService, IndicatorScaleGardener indicatorScaleGardener, SlimIndicatorViewMediator slimIndicatorViewMediator, SettingsHelper settingsHelper, QSClockBellTower qSClockBellTower) {
        super(qSClockIndicatorView);
        this.view = qSClockIndicatorView;
        this.darkIconDispatcher = darkIconDispatcher;
        this.configurationController = configurationController;
        this.tunerService = tunerService;
        this.indicatorScaleGardener = indicatorScaleGardener;
        this.settingsHelper = settingsHelper;
        this.qsClockBellTower = qSClockBellTower;
        this.dateClockStateCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.policy.QSClockIndicatorViewController$dateClockStateCallback$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                QSClockIndicatorViewController.this.qsClockBellTower.ringBellOfTower();
            }
        };
        this.timeFormatChangedListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.policy.QSClockIndicatorViewController$timeFormatChangedListener$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Log.d("QSClockIndicatorView", "12-24 format changed");
                QSClockIndicatorViewController.this.qsClockBellTower.ringBellOfTower();
            }
        };
        qSClockIndicatorView.slimIndicatorViewMediator = slimIndicatorViewMediator;
        qSClockBellTower.ringBellOfTower();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        QSClockIndicatorView qSClockIndicatorView = this.view;
        printWriter.println(" visibility = " + qSClockIndicatorView.getVisibility() + " set from " + qSClockIndicatorView.callers);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m(" clockVisibleByQuickStar = ", qSClockIndicatorView.clockVisibleByUser, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m(" clockVisibleByDisableFlags = ", qSClockIndicatorView.mClockVisibleByPolicy, printWriter);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        this.view.setTextSize(0, (int) (getContext().getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size) * this.indicatorScaleGardener.getLatestScaleModel(getContext()).ratio));
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDisplayDeviceTypeChanged() {
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            onDensityOrFontScaleChanged();
        }
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        boolean z = !StatusBarIconController.getIconHideList(getContext(), str2).contains(SubRoom.EXTRA_VALUE_CLOCK);
        QSClockIndicatorView qSClockIndicatorView = this.view;
        if (qSClockIndicatorView.clockVisibleByUser == z) {
            return;
        }
        qSClockIndicatorView.clockVisibleByUser = z;
        qSClockIndicatorView.setVisibility(qSClockIndicatorView.calculateVisibility() ? 0 : 8);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.darkIconDispatcher.addDarkReceiver(this.view);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this);
        Uri[] uriArr = {Settings.System.getUriFor("time_12_24")};
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.registerCallback(this.timeFormatChangedListener, uriArr);
        this.tunerService.addTunable(this, "icon_blacklist");
        if (BasicRune.STATUS_LAYOUT_SHOW_DATE) {
            settingsHelper.registerCallback(this.dateClockStateCallback, Settings.System.getUriFor("status_bar_show_date"));
        }
        onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.darkIconDispatcher.removeDarkReceiver(this.view);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.unregisterCallback(this.timeFormatChangedListener);
        this.tunerService.removeTunable(this);
        if (BasicRune.STATUS_LAYOUT_SHOW_DATE) {
            settingsHelper.unregisterCallback(this.dateClockStateCallback);
        }
    }
}
