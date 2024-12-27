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
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class QSClockIndicatorViewController extends ViewController implements Dumpable, ConfigurationController.ConfigurationListener, TunerService.Tunable {
    public final ConfigurationController configurationController;
    public final DarkIconDispatcher darkIconDispatcher;
    private final SettingsHelper.OnChangedCallback dateClockStateCallback;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public final QSClockBellTower qsClockBellTower;
    private final SettingsHelper settingsHelper;
    private final SettingsHelper.OnChangedCallback timeFormatChangedListener;
    public final TunerService tunerService;
    public final QSClockIndicatorView view;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        qSClockIndicatorView.setDependencies(slimIndicatorViewMediator, settingsHelper);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        QSClockIndicatorView qSClockIndicatorView = this.view;
        printWriter.println(" visibility = " + qSClockIndicatorView.getVisibility() + " set from " + qSClockIndicatorView.callers);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(" clockVisibleByQuickStar = ", qSClockIndicatorView.clockVisibleByUser, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(" clockVisibleByDisableFlags = ", qSClockIndicatorView.mClockVisibleByPolicy, printWriter);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        this.view.setTextSize(0, (int) (getContext().getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size) * this.indicatorScaleGardener.getLatestScaleModel(getContext()).ratio));
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
        this.settingsHelper.registerCallback(this.timeFormatChangedListener, Settings.System.getUriFor(SettingsHelper.INDEX_TIME_12_24));
        this.tunerService.addTunable(this, "icon_blacklist");
        if (BasicRune.STATUS_LAYOUT_SHOW_DATE) {
            this.settingsHelper.registerCallback(this.dateClockStateCallback, Settings.System.getUriFor(SettingsHelper.INDEX_STATUSBAR_SHOW_DATE));
        }
        onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.darkIconDispatcher.removeDarkReceiver(this.view);
        ((ConfigurationControllerImpl) this.configurationController).removeCallback(this);
        this.settingsHelper.unregisterCallback(this.timeFormatChangedListener);
        this.tunerService.removeTunable(this);
        if (BasicRune.STATUS_LAYOUT_SHOW_DATE) {
            this.settingsHelper.unregisterCallback(this.dateClockStateCallback);
        }
    }
}
