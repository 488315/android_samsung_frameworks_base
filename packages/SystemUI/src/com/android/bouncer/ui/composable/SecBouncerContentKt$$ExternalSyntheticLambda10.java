package com.android.bouncer.ui.composable;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusManager;
import androidx.compose.ui.focus.FocusOwnerImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class SecBouncerContentKt$$ExternalSyntheticLambda10 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecBouncerContentKt$$ExternalSyntheticLambda10(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                FocusOwnerImpl focusOwnerImpl = (FocusOwnerImpl) ((FocusManager) this.f$0);
                focusOwnerImpl.getClass();
                FocusDirection.Companion.getClass();
                focusOwnerImpl.m370clearFocusI7lrPNg(FocusDirection.Exit, false, true);
                break;
            default:
                ((MutableState) this.f$0).setValue(Boolean.valueOf(!((Boolean) r3.getValue()).booleanValue()));
                break;
        }
        return Unit.INSTANCE;
    }
}
