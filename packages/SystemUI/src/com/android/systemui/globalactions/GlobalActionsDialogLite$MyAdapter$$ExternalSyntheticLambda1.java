package com.android.systemui.globalactions;

import android.util.Log;
import android.view.View;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1 implements View.OnLongClickListener {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1(GlobalActionsDialogLite.MyAdapter myAdapter, int i) {
        this.f$0 = myAdapter;
        this.f$1 = i;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                GlobalActionsDialogLite.MyAdapter myAdapter = (GlobalActionsDialogLite.MyAdapter) this.f$0;
                GlobalActionsDialogLite.Action item = myAdapter.this$0.mAdapter.getItem(this.f$1);
                if (!(item instanceof GlobalActionsDialogLite.LongPressAction)) {
                    return false;
                }
                GlobalActionsDialogLite globalActionsDialogLite = myAdapter.this$0;
                if (globalActionsDialogLite.mDialog != null) {
                    globalActionsDialogLite.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                    myAdapter.this$0.mDialog.dismiss();
                } else {
                    Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
                }
                return ((GlobalActionsDialogLite.LongPressAction) item).onLongPress();
            default:
                GlobalActionsDialogLite.MyPowerOptionsAdapter myPowerOptionsAdapter = (GlobalActionsDialogLite.MyPowerOptionsAdapter) this.f$0;
                GlobalActionsDialogLite.Action action = myPowerOptionsAdapter.this$0.mPowerItems.get(this.f$1);
                if (!(action instanceof GlobalActionsDialogLite.LongPressAction)) {
                    return false;
                }
                GlobalActionsDialogLite globalActionsDialogLite2 = myPowerOptionsAdapter.this$0;
                if (globalActionsDialogLite2.mDialog != null) {
                    globalActionsDialogLite2.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                    myPowerOptionsAdapter.this$0.mDialog.dismiss();
                } else {
                    Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
                }
                return ((GlobalActionsDialogLite.LongPressAction) action).onLongPress();
        }
    }

    public /* synthetic */ GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1(GlobalActionsDialogLite.MyPowerOptionsAdapter myPowerOptionsAdapter, int i) {
        this.f$0 = myPowerOptionsAdapter;
        this.f$1 = i;
    }
}
