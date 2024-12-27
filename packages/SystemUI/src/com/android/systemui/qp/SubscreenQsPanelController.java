package com.android.systemui.qp;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.InjectionInflationController;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SubscreenQsPanelController implements QsResetSettingsManager.ResetSettingsApplier, QsResetSettingsManager.DemoResetSettingsApplier {
    public ViewController mBaseViewController;
    public final SubroomQuickSettingsBaseViewFactory mBaseViewFactory;
    public final Context mContext;
    public final SubscreenQuickSettingsControllerFactory mFactory;
    public final QSTileHost mHost;
    public final InjectionInflationController mInjectionInflater;
    public SubscreenSubRoomQuickSettings mSubRoomQuickSettings;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SubroomQuickSettingsBaseViewFactory {
        public SubroomQuickSettingsBaseViewFactory() {
        }
    }

    public SubscreenQsPanelController(Context context, InjectionInflationController injectionInflationController, QSTileHost qSTileHost) {
        this.mContext = context;
        this.mInjectionInflater = injectionInflationController;
        this.mHost = qSTileHost;
        this.mSubRoomQuickSettings = SubscreenSubRoomQuickSettings.getInstance(context, injectionInflationController);
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).registerApplier(this);
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).registerDemoApplier(this);
        if (!QpRune.QUICK_SUBSCREEN_PANEL) {
            Settings.System.putInt(context.getContentResolver(), SettingsHelper.INDEX_SUBSCREEN_BRIGHTNESS_MODE, 0);
        }
        this.mFactory = new SubscreenQuickSettingsControllerFactory();
        this.mBaseViewFactory = new SubroomQuickSettingsBaseViewFactory();
    }

    @Override // com.android.systemui.util.QsResetSettingsManager.DemoResetSettingsApplier
    public final void applyDemoResetSetting() {
        Log.d("SubscreenQsPanelController", "applyDemoResetSetting");
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences("ShowDifferentHelpViewText", 0).edit();
        edit.putInt("helpViewTextCount", 0);
        edit.commit();
    }

    @Override // com.android.systemui.util.QsResetSettingsManager.ResetSettingsApplier
    public final void applyResetSetting() {
        Log.d("SubscreenQsPanelController", "applyResetSetting");
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            Settings.System.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_SUBSCREEN_BRIGHTNESS_MODE, 1);
        } else {
            Settings.System.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_SUBSCREEN_BRIGHTNESS_MODE, 0);
        }
    }

    public final SubscreenQSControllerContract$Presenter getInstance(int i) {
        Context context = this.mContext;
        this.mFactory.getClass();
        if (i == 1) {
            return SubscreenWifiController.getInstance(context);
        }
        if (i == 2) {
            return SubscreenBleController.getInstance(context);
        }
        if (i != 3) {
            return null;
        }
        return SubscreenAirplaneController.getInstance(context);
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.qp.SubscreenQSControllerContract$BaseViewController, com.android.systemui.util.ViewController] */
    public final void init() {
        SubroomQuickSettingsBaseViewFactory subroomQuickSettingsBaseViewFactory = this.mBaseViewFactory;
        subroomQuickSettingsBaseViewFactory.getClass();
        boolean z = QpRune.QUICK_SUBSCREEN_PANEL;
        SubscreenQsPanelController subscreenQsPanelController = SubscreenQsPanelController.this;
        this.mBaseViewController = z ? new SubscreenQsPanelControllerBase((SubroomQuickSettingsQSPanelBaseView) subscreenQsPanelController.mSubRoomQuickSettings.mMainView.findViewById(R.id.subscreen_quick_settings_baseview), subscreenQsPanelController.mHost) : QpRune.QUICK_SUBSCREEN_SETTINGS ? new SubscreenBaseViewControllerBase((SubroomQuickSettingsBaseView) subscreenQsPanelController.mSubRoomQuickSettings.mMainView.findViewById(R.id.subscreen_quick_settings_baseview)) : null;
        Log.d("SubscreenQsPanelController", "init() controller: " + this.mBaseViewController);
        ?? r4 = this.mBaseViewController;
        if (r4 != 0) {
            r4.initView();
        }
    }
}
