package com.android.systemui.qs.tiles;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.SystemUIDialogUtils;

/* loaded from: classes2.dex */
public final /* synthetic */ class MobileDataTile$$ExternalSyntheticLambda13 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileDataTile f$0;
    public final /* synthetic */ CharSequence f$1;
    public final /* synthetic */ CharSequence f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ DialogInterface.OnClickListener f$4;
    public final /* synthetic */ int f$5;
    public final /* synthetic */ DialogInterface.OnClickListener f$6;
    public final /* synthetic */ View f$7;

    public /* synthetic */ MobileDataTile$$ExternalSyntheticLambda13(MobileDataTile mobileDataTile, CharSequence charSequence, CharSequence charSequence2, int i, DialogInterface.OnClickListener onClickListener, int i2, DialogInterface.OnClickListener onClickListener2, View view, int i3) {
        this.$r8$classId = i3;
        this.f$0 = mobileDataTile;
        this.f$1 = charSequence;
        this.f$2 = charSequence2;
        this.f$3 = i;
        this.f$4 = onClickListener;
        this.f$5 = i2;
        this.f$6 = onClickListener2;
        this.f$7 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        DialogInterface.OnClickListener onClickListener;
        View view;
        SystemUIDialog systemUIDialog;
        switch (this.$r8$classId) {
            case 0:
                final MobileDataTile mobileDataTile = this.f$0;
                CharSequence charSequence = this.f$1;
                CharSequence charSequence2 = this.f$2;
                int i2 = this.f$3;
                DialogInterface.OnClickListener onClickListener2 = this.f$4;
                int i3 = this.f$5;
                DialogInterface.OnClickListener onClickListener3 = this.f$6;
                View view2 = this.f$7;
                Intent intent = MobileDataTile.DATA_SETTINGS;
                mobileDataTile.getClass();
                if (QpRune.QUICK_SUBSCREEN_PANEL) {
                    DisplayLifecycle displayLifecycle = mobileDataTile.mDisplayLifecycle;
                    if (displayLifecycle != null && !displayLifecycle.mIsFolderOpened) {
                        SubscreenUtil subscreenUtil = mobileDataTile.mSubscreenUtil;
                        if (subscreenUtil != null) {
                            subscreenUtil.closeSubscreenPanel();
                        }
                        mobileDataTile.mUiHandler.post(new MobileDataTile$$ExternalSyntheticLambda14());
                        new Handler().postDelayed(new MobileDataTile$$ExternalSyntheticLambda13(mobileDataTile, charSequence, charSequence2, i2, onClickListener2, i3, onClickListener3, view2, 1), 250L);
                        break;
                    } else {
                        i = i3;
                        onClickListener = onClickListener3;
                        view = view2;
                        systemUIDialog = SystemUIDialogUtils.createSystemUIDialogUtils(mobileDataTile.getContext$2(), R.style.Theme_SystemUI_Dialog_Alert);
                    }
                } else {
                    i = i3;
                    onClickListener = onClickListener3;
                    view = view2;
                    systemUIDialog = new SystemUIDialog(mobileDataTile.mContext, R.style.Theme_SystemUI_Dialog_Alert);
                }
                systemUIDialog.setTitle(charSequence);
                systemUIDialog.setMessage(charSequence2);
                if (view != null) {
                    Resources resources = mobileDataTile.mContext.getResources();
                    systemUIDialog.setView(view, resources.getDimensionPixelSize(R.dimen.checkbox_popup_text_margin), 0, resources.getDimensionPixelSize(R.dimen.checkbox_popup_text_margin), 0);
                }
                systemUIDialog.setPositiveButton(i2, onClickListener2);
                if (i != 0 && onClickListener != null) {
                    systemUIDialog.setNegativeButton(i, onClickListener);
                }
                ((PanelInteractorImpl) mobileDataTile.mPanelInteractor).collapsePanels();
                final int i4 = 0;
                systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.MobileDataTile$$ExternalSyntheticLambda16
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        int i5 = i4;
                        MobileDataTile mobileDataTile2 = mobileDataTile;
                        switch (i5) {
                            case 0:
                                Intent intent2 = MobileDataTile.DATA_SETTINGS;
                                mobileDataTile2.refreshState(null);
                                break;
                            default:
                                Intent intent3 = MobileDataTile.DATA_SETTINGS;
                                mobileDataTile2.refreshState(null);
                                break;
                        }
                    }
                });
                systemUIDialog.show();
                break;
            default:
                final MobileDataTile mobileDataTile2 = this.f$0;
                CharSequence charSequence3 = this.f$1;
                CharSequence charSequence4 = this.f$2;
                int i5 = this.f$3;
                DialogInterface.OnClickListener onClickListener4 = this.f$4;
                int i6 = this.f$5;
                DialogInterface.OnClickListener onClickListener5 = this.f$6;
                View view3 = this.f$7;
                Intent intent2 = MobileDataTile.DATA_SETTINGS;
                SystemUIDialog systemUIDialogCreateSystemUIDialogUtils = SystemUIDialogUtils.createSystemUIDialogUtils(mobileDataTile2.getContext$2(), R.style.Theme_SystemUI_Dialog_Alert);
                systemUIDialogCreateSystemUIDialogUtils.setTitle(charSequence3);
                systemUIDialogCreateSystemUIDialogUtils.setMessage(charSequence4);
                if (view3 != null) {
                    Resources resources2 = mobileDataTile2.getContext$2().getResources();
                    systemUIDialogCreateSystemUIDialogUtils.setView(view3, resources2.getDimensionPixelSize(R.dimen.checkbox_popup_checkbox_margin), 0, resources2.getDimensionPixelSize(R.dimen.checkbox_popup_checkbox_margin), 0);
                }
                systemUIDialogCreateSystemUIDialogUtils.setPositiveButton(i5, onClickListener4);
                if (i6 != 0 && onClickListener5 != null) {
                    systemUIDialogCreateSystemUIDialogUtils.setNegativeButton(i6, onClickListener5);
                }
                ((PanelInteractorImpl) mobileDataTile2.mPanelInteractor).collapsePanels();
                final int i7 = 1;
                systemUIDialogCreateSystemUIDialogUtils.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.MobileDataTile$$ExternalSyntheticLambda16
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        int i52 = i7;
                        MobileDataTile mobileDataTile22 = mobileDataTile2;
                        switch (i52) {
                            case 0:
                                Intent intent22 = MobileDataTile.DATA_SETTINGS;
                                mobileDataTile22.refreshState(null);
                                break;
                            default:
                                Intent intent3 = MobileDataTile.DATA_SETTINGS;
                                mobileDataTile22.refreshState(null);
                                break;
                        }
                    }
                });
                systemUIDialogCreateSystemUIDialogUtils.show();
                break;
        }
    }
}
