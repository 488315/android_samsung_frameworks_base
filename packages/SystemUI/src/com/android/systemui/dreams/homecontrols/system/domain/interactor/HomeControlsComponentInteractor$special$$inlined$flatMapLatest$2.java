package com.android.systemui.dreams.homecontrols.system.domain.interactor;

import android.content.SharedPreferences;
import android.content.pm.UserInfo;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.SharedPreferencesExt;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class HomeControlsComponentInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ AuthorizedPanelsRepository $authorizedPanelsRepository$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsComponentInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, AuthorizedPanelsRepository authorizedPanelsRepository) {
        super(3, continuation);
        this.$authorizedPanelsRepository$inlined = authorizedPanelsRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        HomeControlsComponentInteractor$special$$inlined$flatMapLatest$2 homeControlsComponentInteractor$special$$inlined$flatMapLatest$2 = new HomeControlsComponentInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.$authorizedPanelsRepository$inlined);
        homeControlsComponentInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        homeControlsComponentInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return homeControlsComponentInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            UserInfo userInfo = (UserInfo) this.L$1;
            final AuthorizedPanelsRepositoryImpl authorizedPanelsRepositoryImpl = (AuthorizedPanelsRepositoryImpl) this.$authorizedPanelsRepository$inlined;
            final SharedPreferences sharedPreferencesInstantiateSharedPrefs = authorizedPanelsRepositoryImpl.instantiateSharedPrefs(userInfo.getUserHandle());
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), SharedPreferencesExt.INSTANCE.observe(sharedPreferencesInstantiateSharedPrefs));
            Flow flow = new Flow() { // from class: com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl$observeAuthorizedPanels$$inlined$map$1

                /* renamed from: com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl$observeAuthorizedPanels$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ SharedPreferences $prefs$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ AuthorizedPanelsRepositoryImpl this$0;

                    /* renamed from: com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl$observeAuthorizedPanels$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, AuthorizedPanelsRepositoryImpl authorizedPanelsRepositoryImpl, SharedPreferences sharedPreferences) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = authorizedPanelsRepositoryImpl;
                        this.$prefs$inlined = sharedPreferences;
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
                            SharedPreferences sharedPreferences = this.$prefs$inlined;
                            int i3 = AuthorizedPanelsRepositoryImpl.$r8$clinit;
                            this.this$0.getClass();
                            Set<String> stringSet = sharedPreferences.getStringSet("authorized_panels", EmptySet.INSTANCE);
                            stringSet.getClass();
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(stringSet, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector2, authorizedPanelsRepositoryImpl, sharedPreferencesInstantiateSharedPrefs), continuation);
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
