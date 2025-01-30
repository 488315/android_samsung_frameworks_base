package com.android.systemui.user.ui.dialog;

import com.android.systemui.user.ui.dialog.ExitGuestDialog;
import kotlin.Function;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.user.ui.dialog.UserSwitcherDialogCoordinator$sam$com_android_systemui_user_ui_dialog_ExitGuestDialog_OnExitGuestUserListener$0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C3566x48b13c6a implements ExitGuestDialog.OnExitGuestUserListener, FunctionAdapter {
    public final /* synthetic */ Function3 function;

    public C3566x48b13c6a(Function3 function3) {
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

    public final /* synthetic */ void onExitGuestUser(int i, int i2, boolean z) {
        this.function.invoke(Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z));
    }
}
