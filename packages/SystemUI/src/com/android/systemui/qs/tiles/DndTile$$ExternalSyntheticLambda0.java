package com.android.systemui.qs.tiles;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import com.android.settingslib.notification.modes.EnableDndDialogFactory;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DndTile$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DndTile$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                DndTile dndTile = (DndTile) obj;
                Intent intent = DndTile.DND_SETTINGS;
                dndTile.getClass();
                AlertDialog createDialog = new EnableDndDialogFactory(new ContextThemeWrapper((!QpRune.QUICK_SUBSCREEN_PANEL || ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) ? dndTile.mContext : ((SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class)).mContext, R.style.Theme_SystemUI_Dnd_Dialog)).createDialog();
                createDialog.semSetBackgroundBlurEnabled(true);
                createDialog.getWindow().setType(2009);
                SystemUIDialog.setShowForAllUsers(createDialog);
                SystemUIDialog.registerDismissListener(createDialog);
                SystemUIDialog.setWindowOnTop(createDialog, true);
                SystemUIDialog.setDialogSize(createDialog);
                dndTile.mUiHandler.post(new DndTile$$ExternalSyntheticLambda0(createDialog, i));
                ((PanelInteractorImpl) dndTile.mPanelInteractor).collapsePanels();
                break;
            default:
                Intent intent2 = DndTile.DND_SETTINGS;
                ((AlertDialog) obj).show();
                break;
        }
    }
}
