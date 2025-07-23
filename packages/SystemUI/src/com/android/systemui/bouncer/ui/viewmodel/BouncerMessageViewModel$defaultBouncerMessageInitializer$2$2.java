package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2 extends SuspendLambda implements Function6 {
    final /* synthetic */ AuthenticationMethodModel $authMethod;
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2(BouncerMessageViewModel bouncerMessageViewModel, AuthenticationMethodModel authenticationMethodModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = bouncerMessageViewModel;
        this.$authMethod = authenticationMethodModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
        int intValue = ((Number) obj5).intValue();
        BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2 bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2 = new BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2(this.this$0, this.$authMethod, (Continuation) obj6);
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.L$0 = (DeviceEntryRestrictionReason) obj;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.L$1 = (MessageViewModel) obj2;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.Z$0 = booleanValue;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.Z$1 = booleanValue2;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.I$0 = intValue;
        return bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0063  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            Method dump skipped, instructions count: 296
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
