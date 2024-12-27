package com.android.systemui.qs;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda28 implements Runnable {
    public final /* synthetic */ QSSecurityFooter f$0;

    @Override // java.lang.Runnable
    public final void run() {
        final QSSecurityFooter qSSecurityFooter = this.f$0;
        final String settingsButton = qSSecurityFooter.getSettingsButton();
        final View createDialogView = qSSecurityFooter.createDialogView();
        qSSecurityFooter.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                QSSecurityFooter qSSecurityFooter2 = QSSecurityFooter.this;
                String str = settingsButton;
                View view = createDialogView;
                qSSecurityFooter2.getClass();
                SystemUIDialog systemUIDialog = new SystemUIDialog(qSSecurityFooter2.mContext, R.style.Theme_SystemUI_Dialog_Alert);
                qSSecurityFooter2.mDialog = systemUIDialog;
                systemUIDialog.requestWindowFeature(1);
                qSSecurityFooter2.mDialog.setButton(-1, qSSecurityFooter2.mContext.getString(R.string.ok), qSSecurityFooter2);
                SystemUIDialog systemUIDialog2 = qSSecurityFooter2.mDialog;
                if (!qSSecurityFooter2.mShouldUseSettingsButton.get()) {
                    str = ((SecurityControllerImpl) qSSecurityFooter2.mSecurityController).isParentalControlsEnabled() ? qSSecurityFooter2.mContext.getString(R.string.monitoring_button_view_controls) : null;
                }
                systemUIDialog2.setButton(-2, str, qSSecurityFooter2);
                qSSecurityFooter2.mDialog.getWindow().setGravity(81);
                qSSecurityFooter2.mDialog.setView(view);
                qSSecurityFooter2.mDialog.show();
            }
        });
    }
}
