package com.android.systemui.user;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class UserSwitchFullscreenDialog$onCreate$2 extends FunctionReferenceImpl implements Function0 {
    public UserSwitchFullscreenDialog$onCreate$2(Object obj) {
        super(0, obj, UserSwitchFullscreenDialog.class, "dismiss", "dismiss()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((UserSwitchFullscreenDialog) this.receiver).dismiss();
        return Unit.INSTANCE;
    }
}
