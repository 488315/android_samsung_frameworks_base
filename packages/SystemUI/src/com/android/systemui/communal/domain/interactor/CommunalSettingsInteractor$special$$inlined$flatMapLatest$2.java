package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl;
import com.android.systemui.communal.shared.model.CommunalBackgroundType;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.settings.SettingsProxyExt;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class CommunalSettingsInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalSettingsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSettingsInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, CommunalSettingsInteractor communalSettingsInteractor) {
        super(3, continuation);
        this.this$0 = communalSettingsInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalSettingsInteractor$special$$inlined$flatMapLatest$2 communalSettingsInteractor$special$$inlined$flatMapLatest$2 = new CommunalSettingsInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        communalSettingsInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        communalSettingsInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return communalSettingsInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
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
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), SettingsProxyExt.INSTANCE.observerFlow(communalSettingsRepositoryImpl.secureSettings, userInfo.id, "glanceable_hub_background"));
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

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        Object next;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl = this.this$0;
                            int intForUser = communalSettingsRepositoryImpl.secureSettings.getIntForUser("glanceable_hub_background", communalSettingsRepositoryImpl.defaultBackgroundType.getValue(), this.$user$inlined.id);
                            Iterator<E> it = CommunalBackgroundType.$ENTRIES.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    next = null;
                                    break;
                                }
                                next = it.next();
                                if (((CommunalBackgroundType) next).getValue() == intForUser) {
                                    break;
                                }
                            }
                            CommunalBackgroundType communalBackgroundType = (CommunalBackgroundType) next;
                            if (communalBackgroundType == null) {
                                communalBackgroundType = communalSettingsRepositoryImpl.defaultBackgroundType;
                            }
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(communalBackgroundType, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector2, communalSettingsRepositoryImpl, userInfo), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (kotlinx.coroutines.flow.FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
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
