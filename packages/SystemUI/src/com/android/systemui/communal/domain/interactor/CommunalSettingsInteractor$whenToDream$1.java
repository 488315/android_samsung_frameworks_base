package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl;
import com.android.systemui.communal.shared.model.WhenToDream;
import com.android.systemui.util.kotlin.FlowKt;
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
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

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
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), SettingsProxyExt.INSTANCE.observerFlow(communalSettingsRepositoryImpl.secureSettings, userInfo.id, "screensaver_activate_on_sleep", "screensaver_activate_on_dock", "screensaver_activate_on_postured"));
        return kotlinx.coroutines.flow.FlowKt.flowOn(new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWhenToDreamState$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        WhenToDream whenToDream = communalSettingsRepositoryImpl.secureSettings.getBoolForUser("screensaver_activate_on_sleep", ((Boolean) communalSettingsRepositoryImpl.dreamsActivatedOnSleepByDefault$delegate.getValue()).booleanValue(), this.$user$inlined.id) ? WhenToDream.WHILE_CHARGING : communalSettingsRepositoryImpl.secureSettings.getBoolForUser("screensaver_activate_on_dock", ((Boolean) communalSettingsRepositoryImpl.dreamsActivatedOnDockByDefault$delegate.getValue()).booleanValue(), this.$user$inlined.id) ? WhenToDream.WHILE_DOCKED : communalSettingsRepositoryImpl.secureSettings.getBoolForUser("screensaver_activate_on_postured", ((Boolean) communalSettingsRepositoryImpl.dreamsActivatedOnPosturedByDefault$delegate.getValue()).booleanValue(), this.$user$inlined.id) ? WhenToDream.WHILE_POSTURED : WhenToDream.NEVER;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(whenToDream, anonymousClass1) == coroutineSingletons) {
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, communalSettingsRepositoryImpl, userInfo), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, communalSettingsRepositoryImpl.bgDispatcher);
    }
}
