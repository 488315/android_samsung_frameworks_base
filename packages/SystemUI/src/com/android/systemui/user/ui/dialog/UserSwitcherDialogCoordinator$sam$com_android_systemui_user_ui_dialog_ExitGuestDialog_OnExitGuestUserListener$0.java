package com.android.systemui.user.ui.dialog;

import com.android.systemui.user.ui.dialog.ExitGuestDialog;
import kotlin.Function;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0 implements ExitGuestDialog.OnExitGuestUserListener, FunctionAdapter {
    public final /* synthetic */ Function3 function;

    public UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0(Function3 function3) {
        this.function = function3;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ExitGuestDialog.OnExitGuestUserListener) || !(obj instanceof FunctionAdapter)) {
            return false;
        }
        return Intrinsics.areEqual(this.function, ((FunctionAdapter) obj).getFunctionDelegate());
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return this.function;
    }

    public final int hashCode() {
        return this.function.hashCode();
    }
}
