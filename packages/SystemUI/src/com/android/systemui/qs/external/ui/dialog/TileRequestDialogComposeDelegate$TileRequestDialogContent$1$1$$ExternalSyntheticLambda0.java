package com.android.systemui.qs.external.ui.dialog;

import android.content.DialogInterface;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class TileRequestDialogComposeDelegate$TileRequestDialogContent$1$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TileRequestDialogComposeDelegate f$0;
    public final /* synthetic */ SystemUIDialog f$1;

    public /* synthetic */ TileRequestDialogComposeDelegate$TileRequestDialogContent$1$1$$ExternalSyntheticLambda0(TileRequestDialogComposeDelegate tileRequestDialogComposeDelegate, SystemUIDialog systemUIDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = tileRequestDialogComposeDelegate;
        this.f$1 = systemUIDialog;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                TileRequestDialogComposeDelegate tileRequestDialogComposeDelegate = this.f$0;
                break;
            case 1:
                DialogInterface.OnClickListener onClickListener = this.f$0.dialogListener;
                SystemUIDialog systemUIDialog = this.f$1;
                onClickListener.onClick(systemUIDialog, -1);
                systemUIDialog.dismiss();
                break;
            default:
                DialogInterface.OnClickListener onClickListener2 = this.f$0.dialogListener;
                SystemUIDialog systemUIDialog2 = this.f$1;
                onClickListener2.onClick(systemUIDialog2, -2);
                systemUIDialog2.dismiss();
                break;
        }
        return Unit.INSTANCE;
    }
}
