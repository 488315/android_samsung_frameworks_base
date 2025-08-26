package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import com.android.systemui.qs.panels.ui.dialog.QSResetDialogDelegate;
import com.android.systemui.qs.panels.ui.viewmodel.InfiniteGridViewModel;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class InfiniteGridLayout$EditTileGrid$3$1 extends FunctionReferenceImpl implements Function0 {
    public InfiniteGridLayout$EditTileGrid$3$1(Object obj) {
        super(0, obj, InfiniteGridViewModel.class, "showResetDialog", "showResetDialog()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        QSResetDialogDelegate qSResetDialogDelegate = ((InfiniteGridViewModel) this.receiver).resetDialogDelegate;
        if (qSResetDialogDelegate.currentDialog == null) {
            qSResetDialogDelegate.createDialog();
        }
        ComponentSystemUIDialog componentSystemUIDialog = qSResetDialogDelegate.currentDialog;
        if (componentSystemUIDialog != null) {
            componentSystemUIDialog.show();
        }
        return Unit.INSTANCE;
    }
}
