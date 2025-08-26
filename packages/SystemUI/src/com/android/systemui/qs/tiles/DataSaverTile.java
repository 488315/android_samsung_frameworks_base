package com.android.systemui.qs.tiles;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* loaded from: classes2.dex */
public class DataSaverTile extends QSTileImpl implements DataSaverController.Listener {
    public final DataSaverController mDataSaverController;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final ShadeDialogContextInteractor mShadeDialogContextInteractor;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;

    public DataSaverTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, DataSaverController dataSaverController, DialogTransitionAnimator dialogTransitionAnimator, SystemUIDialog.Factory factory, ShadeDialogContextInteractor shadeDialogContextInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mDataSaverController = dataSaverController;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mSystemUIDialogFactory = factory;
        this.mShadeDialogContextInteractor = shadeDialogContextInteractor;
        dataSaverController.observe(this.mLifecycle, this);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.DATA_SAVER_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_registerSystemUiCallback;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.data_saver);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        if (((QSTile.BooleanState) this.mState).value || Prefs.getBoolean(this.mContext, "QsDataSaverDialogShown", false)) {
            toggleDataSaver();
        } else {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.DataSaverTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final DataSaverTile dataSaverTile = this.f$0;
                    Expandable expandable2 = expandable;
                    SystemUIDialog systemUIDialogCreate = dataSaverTile.mSystemUIDialogFactory.create(((ShadeDialogContextInteractorImpl) dataSaverTile.mShadeDialogContextInteractor).getContext());
                    systemUIDialogCreate.setTitle(android.R.string.font_family_headline_material);
                    systemUIDialogCreate.setMessage(android.R.string.font_family_display_1_material);
                    systemUIDialogCreate.setPositiveButton(android.R.string.font_family_display_4_material, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.DataSaverTile$$ExternalSyntheticLambda1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            DataSaverTile dataSaverTile2 = dataSaverTile;
                            dataSaverTile2.toggleDataSaver();
                            Prefs.putBoolean(dataSaverTile2.mContext, "QsDataSaverDialogShown", true);
                        }
                    });
                    systemUIDialogCreate.setButton(-3, android.R.string.cancel, null, true);
                    SystemUIDialog.setShowForAllUsers(systemUIDialogCreate);
                    if (expandable2 == null) {
                        systemUIDialogCreate.show();
                        return;
                    }
                    DialogTransitionAnimator.Controller controllerDialogTransitionController = expandable2.dialogTransitionController(new DialogCuj(58, "start_data_saver"));
                    if (controllerDialogTransitionController != null) {
                        dataSaverTile.mDialogTransitionAnimator.show(systemUIDialogCreate, controllerDialogTransitionController, false);
                    } else {
                        systemUIDialogCreate.show();
                    }
                }
            });
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        boolean zBooleanValue = obj instanceof Boolean ? ((Boolean) obj).booleanValue() : ((DataSaverControllerImpl) this.mDataSaverController).isDataSaverEnabled$1();
        booleanState.value = zBooleanValue;
        booleanState.state = zBooleanValue ? 2 : 1;
        String string = this.mContext.getString(R.string.data_saver);
        booleanState.label = string;
        booleanState.contentDescription = string;
        int i = booleanState.value ? R.drawable.qs_data_saver_icon_on : R.drawable.qs_data_saver_icon_off;
        int i2 = QsInCompose.$r8$clinit;
        booleanState.icon = QSTileImpl.ResourceIcon.get(i);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.policy.DataSaverController.Listener
    public final void onDataSaverChanged(boolean z) {
        refreshState(Boolean.valueOf(z));
    }

    public final void toggleDataSaver() {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) this.mState;
        DataSaverController dataSaverController = this.mDataSaverController;
        booleanState.value = !((DataSaverControllerImpl) dataSaverController).isDataSaverEnabled$1();
        ((DataSaverControllerImpl) dataSaverController).setDataSaverEnabled(((QSTile.BooleanState) this.mState).value);
        refreshState(Boolean.valueOf(((QSTile.BooleanState) this.mState).value));
    }
}
