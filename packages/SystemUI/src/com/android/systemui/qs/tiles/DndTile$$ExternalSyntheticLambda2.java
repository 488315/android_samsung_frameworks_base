package com.android.systemui.qs.tiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.ContextThemeWrapper;
import com.android.settingslib.notification.modes.EnableDndDialogFactory;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* loaded from: classes2.dex */
public final /* synthetic */ class DndTile$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DndTile$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                Intent intent = DndTile.DND_SETTINGS;
                ((AlertDialog) obj).show();
                break;
            case 1:
                int i2 = DndTile.AnonymousClass3.$r8$clinit;
                SystemUIDialog.setDialogSize((AlertDialog) obj);
                break;
            default:
                DndTile dndTile = (DndTile) obj;
                Intent intent2 = DndTile.DND_SETTINGS;
                final Context applicationContext = dndTile.mContext.getApplicationContext();
                AlertDialog alertDialogCreateDialog = new EnableDndDialogFactory(new ContextThemeWrapper((!QpRune.QUICK_SUBSCREEN_PANEL || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) ? dndTile.mContext : ((SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class)).mContext, R.style.Theme_SystemUI_Dnd_Dialog)).createDialog();
                alertDialogCreateDialog.semSetBackgroundBlurEnabled(true);
                alertDialogCreateDialog.getWindow().setType(2009);
                SystemUIDialog.setShowForAllUsers(alertDialogCreateDialog);
                SystemUIDialog.registerDismissListener(alertDialogCreateDialog);
                SystemUIDialog.setWindowOnTop(alertDialogCreateDialog, true);
                SystemUIDialog.setDialogSize(alertDialogCreateDialog);
                final DndTile.AnonymousClass3 anonymousClass3 = new DndTile.AnonymousClass3(dndTile, alertDialogCreateDialog);
                applicationContext.registerReceiver(anonymousClass3, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
                alertDialogCreateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.DndTile$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        Context context = applicationContext;
                        DndTile.AnonymousClass3 anonymousClass32 = anonymousClass3;
                        Intent intent3 = DndTile.DND_SETTINGS;
                        context.unregisterReceiver(anonymousClass32);
                    }
                });
                dndTile.mUiHandler.post(new DndTile$$ExternalSyntheticLambda2(alertDialogCreateDialog, 0));
                ((PanelInteractorImpl) dndTile.mPanelInteractor).collapsePanels();
                break;
        }
    }
}
