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
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

public final class CommunalSettingsInteractor$special$$inlined$flatMapLatest$3 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalSettingsInteractor this$0;

    public CommunalSettingsInteractor$special$$inlined$flatMapLatest$3(Continuation continuation, CommunalSettingsInteractor communalSettingsInteractor) {
        super(3, continuation);
        this.this$0 = communalSettingsInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalSettingsInteractor$special$$inlined$flatMapLatest$3 communalSettingsInteractor$special$$inlined$flatMapLatest$3 = new CommunalSettingsInteractor$special$$inlined$flatMapLatest$3((Continuation) obj3, this.this$0);
        communalSettingsInteractor$special$$inlined$flatMapLatest$3.L$0 = (FlowCollector) obj;
        communalSettingsInteractor$special$$inlined$flatMapLatest$3.L$1 = obj2;
        return communalSettingsInteractor$special$$inlined$flatMapLatest$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final UserInfo userInfo = (UserInfo) this.L$1;
            final CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl = (CommunalSettingsRepositoryImpl) this.this$0.repository;
            communalSettingsRepositoryImpl.getClass();
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), SettingsProxyExt.INSTANCE.observerFlow(communalSettingsRepositoryImpl.secureSettings, userInfo.id, "glanceable_hub_background"));
            Flow flow = new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getBackground$$inlined$map$1

                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getBackground$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ UserInfo $user$inlined;
                    public final /* synthetic */ CommunalSettingsRepositoryImpl this$0;

                    /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getBackground$$inlined$map$1$2$1, reason: invalid class name */
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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                        /*
                            r5 = this;
                            boolean r0 = r7 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getBackground$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r7
                            com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getBackground$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getBackground$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getBackground$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getBackground$$inlined$map$1$2$1
                            r0.<init>(r7)
                        L18:
                            java.lang.Object r7 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r7)
                            goto L74
                        L27:
                            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                            r5.<init>(r6)
                            throw r5
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r7)
                            kotlin.Unit r6 = (kotlin.Unit) r6
                            com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl r6 = r5.this$0
                            com.android.systemui.util.settings.SecureSettings r6 = r6.secureSettings
                            com.android.systemui.communal.shared.model.CommunalBackgroundType r7 = com.android.systemui.communal.shared.model.CommunalBackgroundType.DEFAULT
                            int r7 = r7.getValue()
                            android.content.pm.UserInfo r2 = r5.$user$inlined
                            int r2 = r2.id
                            java.lang.String r4 = "glanceable_hub_background"
                            int r6 = r6.getIntForUser(r4, r7, r2)
                            kotlin.enums.EnumEntries r7 = com.android.systemui.communal.shared.model.CommunalBackgroundType.$ENTRIES
                            java.util.Iterator r7 = r7.iterator()
                        L4e:
                            boolean r2 = r7.hasNext()
                            if (r2 == 0) goto L62
                            java.lang.Object r2 = r7.next()
                            r4 = r2
                            com.android.systemui.communal.shared.model.CommunalBackgroundType r4 = (com.android.systemui.communal.shared.model.CommunalBackgroundType) r4
                            int r4 = r4.getValue()
                            if (r4 != r6) goto L4e
                            goto L63
                        L62:
                            r2 = 0
                        L63:
                            com.android.systemui.communal.shared.model.CommunalBackgroundType r2 = (com.android.systemui.communal.shared.model.CommunalBackgroundType) r2
                            if (r2 != 0) goto L69
                            com.android.systemui.communal.shared.model.CommunalBackgroundType r2 = com.android.systemui.communal.shared.model.CommunalBackgroundType.DEFAULT
                        L69:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                            java.lang.Object r5 = r5.emit(r2, r0)
                            if (r5 != r1) goto L74
                            return r1
                        L74:
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            return r5
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getBackground$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, communalSettingsRepositoryImpl, userInfo), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
