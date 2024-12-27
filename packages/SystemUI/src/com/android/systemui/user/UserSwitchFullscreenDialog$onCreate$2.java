package com.android.systemui.user;

import com.android.systemui.popup.util.PopupUIUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class UserSwitchFullscreenDialog$onCreate$2 extends FunctionReferenceImpl implements Function0 {
    public UserSwitchFullscreenDialog$onCreate$2(Object obj) {
        super(0, obj, UserSwitchFullscreenDialog.class, PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS, "dismiss()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((UserSwitchFullscreenDialog) this.receiver).dismiss();
        return Unit.INSTANCE;
    }
}
