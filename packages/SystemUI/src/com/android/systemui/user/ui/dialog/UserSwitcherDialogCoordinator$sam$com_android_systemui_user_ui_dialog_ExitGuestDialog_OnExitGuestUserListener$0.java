package com.android.systemui.user.ui.dialog;

import com.android.systemui.user.ui.dialog.ExitGuestDialog;
import kotlin.Function;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0 implements ExitGuestDialog.OnExitGuestUserListener, FunctionAdapter {
    public final /* synthetic */ Function3 function;

    public UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0(Function3 function3) {
        this.function = function3;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof ExitGuestDialog.OnExitGuestUserListener) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return this.function;
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }
}
