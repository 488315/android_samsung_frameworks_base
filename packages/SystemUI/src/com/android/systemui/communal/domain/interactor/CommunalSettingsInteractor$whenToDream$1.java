package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalSettingsInteractor$whenToDream$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalSettingsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSettingsInteractor$whenToDream$1(CommunalSettingsInteractor communalSettingsInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSettingsInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalSettingsInteractor$whenToDream$1 communalSettingsInteractor$whenToDream$1 = new CommunalSettingsInteractor$whenToDream$1(this.this$0, continuation);
        communalSettingsInteractor$whenToDream$1.L$0 = obj;
        return communalSettingsInteractor$whenToDream$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSettingsInteractor$whenToDream$1) create((UserInfo) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final UserInfo userInfo = (UserInfo) this.L$0;
        final CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl = (CommunalSettingsRepositoryImpl) this.this$0.repository;
        communalSettingsRepositoryImpl.getClass();
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), SettingsProxyExt.INSTANCE.observerFlow(communalSettingsRepositoryImpl.secureSettings, userInfo.id, "screensaver_activate_on_sleep", "screensaver_activate_on_dock", "screensaver_activate_on_postured"));
        return FlowKt.flowOn(new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserInfo $user$inlined;
                public final /* synthetic */ CommunalSettingsRepositoryImpl this$0;

                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl, UserInfo userInfo) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = communalSettingsRepositoryImpl;
                    this.$user$inlined = userInfo;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L9b
                    L28:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L30:
                        kotlin.ResultKt.throwOnFailure(r8)
                        kotlin.Unit r7 = (kotlin.Unit) r7
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl r7 = r6.this$0
                        com.android.systemui.util.settings.SecureSettings r8 = r7.secureSettings
                        kotlin.Lazy r2 = r7.dreamsActivatedOnSleepByDefault$delegate
                        java.lang.Object r2 = r2.getValue()
                        java.lang.Boolean r2 = (java.lang.Boolean) r2
                        boolean r2 = r2.booleanValue()
                        android.content.pm.UserInfo r4 = r6.$user$inlined
                        int r4 = r4.id
                        java.lang.String r5 = "screensaver_activate_on_sleep"
                        boolean r8 = r8.getBoolForUser(r5, r2, r4)
                        if (r8 == 0) goto L54
                        com.android.systemui.communal.shared.model.WhenToDream r7 = com.android.systemui.communal.shared.model.WhenToDream.WHILE_CHARGING
                        goto L90
                    L54:
                        com.android.systemui.util.settings.SecureSettings r8 = r7.secureSettings
                        kotlin.Lazy r2 = r7.dreamsActivatedOnDockByDefault$delegate
                        java.lang.Object r2 = r2.getValue()
                        java.lang.Boolean r2 = (java.lang.Boolean) r2
                        boolean r2 = r2.booleanValue()
                        android.content.pm.UserInfo r4 = r6.$user$inlined
                        int r4 = r4.id
                        java.lang.String r5 = "screensaver_activate_on_dock"
                        boolean r8 = r8.getBoolForUser(r5, r2, r4)
                        if (r8 == 0) goto L71
                        com.android.systemui.communal.shared.model.WhenToDream r7 = com.android.systemui.communal.shared.model.WhenToDream.WHILE_DOCKED
                        goto L90
                    L71:
                        com.android.systemui.util.settings.SecureSettings r8 = r7.secureSettings
                        kotlin.Lazy r7 = r7.dreamsActivatedOnPosturedByDefault$delegate
                        java.lang.Object r7 = r7.getValue()
                        java.lang.Boolean r7 = (java.lang.Boolean) r7
                        boolean r7 = r7.booleanValue()
                        android.content.pm.UserInfo r2 = r6.$user$inlined
                        int r2 = r2.id
                        java.lang.String r4 = "screensaver_activate_on_postured"
                        boolean r7 = r8.getBoolForUser(r4, r7, r2)
                        if (r7 == 0) goto L8e
                        com.android.systemui.communal.shared.model.WhenToDream r7 = com.android.systemui.communal.shared.model.WhenToDream.WHILE_POSTURED
                        goto L90
                    L8e:
                        com.android.systemui.communal.shared.model.WhenToDream r7 = com.android.systemui.communal.shared.model.WhenToDream.NEVER
                    L90:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto L9b
                        return r1
                    L9b:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, communalSettingsRepositoryImpl, userInfo), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, communalSettingsRepositoryImpl.bgDispatcher);
    }
}
