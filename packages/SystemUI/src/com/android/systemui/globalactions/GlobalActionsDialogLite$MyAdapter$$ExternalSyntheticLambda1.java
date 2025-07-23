package com.android.systemui.globalactions;

import android.util.Log;
import android.view.View;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                GlobalActionsDialogLite.Action item = GlobalActionsDialogLite.this.mAdapter.getItem(this.f$1);
                if (!(item instanceof GlobalActionsDialogLite.LongPressAction)) {
                    return false;
                }
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite.mDialog != null) {
                    globalActionsDialogLite.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                    GlobalActionsDialogLite.this.mDialog.dismiss();
                } else {
                    Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
                }
                return ((GlobalActionsDialogLite.LongPressAction) item).onLongPress();
            default:
                GlobalActionsDialogLite.MyPowerOptionsAdapter myPowerOptionsAdapter = (GlobalActionsDialogLite.MyPowerOptionsAdapter) this.f$0;
                int i = this.f$1;
                int i2 = GlobalActionsDialogLite.MyPowerOptionsAdapter.$r8$clinit;
                GlobalActionsDialogLite.Action action = GlobalActionsDialogLite.this.mPowerItems.get(i);
                if (!(action instanceof GlobalActionsDialogLite.LongPressAction)) {
                    return false;
                }
                GlobalActionsDialogLite globalActionsDialogLite2 = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite2.mDialog != null) {
                    globalActionsDialogLite2.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                    GlobalActionsDialogLite.this.mDialog.dismiss();
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
