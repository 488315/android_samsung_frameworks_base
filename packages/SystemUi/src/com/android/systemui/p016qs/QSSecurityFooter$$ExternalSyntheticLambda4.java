package com.android.systemui.p016qs;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSSecurityFooter$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ QSSecurityFooter f$0;

    @Override // java.lang.Runnable
    public final void run() {
        final QSSecurityFooter qSSecurityFooter = this.f$0;
        final String settingsButton = qSSecurityFooter.getSettingsButton();
        final View createDialogView = qSSecurityFooter.createDialogView();
        qSSecurityFooter.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.QSSecurityFooter$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                QSSecurityFooter qSSecurityFooter2 = QSSecurityFooter.this;
                String str = settingsButton;
                View view = createDialogView;
                qSSecurityFooter2.getClass();
                SystemUIDialog systemUIDialog = new SystemUIDialog(qSSecurityFooter2.mContext, 2132018528);
                qSSecurityFooter2.mDialog = systemUIDialog;
                systemUIDialog.requestWindowFeature(1);
                qSSecurityFooter2.mDialog.setButton(-1, qSSecurityFooter2.mContext.getString(R.string.f789ok), qSSecurityFooter2);
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
