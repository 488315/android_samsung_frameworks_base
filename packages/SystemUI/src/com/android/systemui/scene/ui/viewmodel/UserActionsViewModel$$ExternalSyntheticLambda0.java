package com.android.systemui.scene.ui.viewmodel;

import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserActionsViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ UserActionsViewModel f$0;

    public /* synthetic */ UserActionsViewModel$$ExternalSyntheticLambda0(UserActionsViewModel userActionsViewModel) {
        this.f$0 = userActionsViewModel;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        this.f$0._actions.setValue((Map) obj);
        return Unit.INSTANCE;
    }
}
