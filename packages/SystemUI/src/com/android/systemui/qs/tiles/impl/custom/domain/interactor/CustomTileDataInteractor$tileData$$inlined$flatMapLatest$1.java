package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$defaults$$inlined$filter$1;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$getTiles$$inlined$filter$1;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$getTiles$$inlined$map$1;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public final class CustomTileDataInteractor$tileData$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ UserHandle $user$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CustomTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileDataInteractor$tileData$$inlined$flatMapLatest$1(Continuation continuation, CustomTileDataInteractor customTileDataInteractor, UserHandle userHandle) {
        super(3, continuation);
        this.this$0 = customTileDataInteractor;
        this.$user$inlined = userHandle;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CustomTileDataInteractor$tileData$$inlined$flatMapLatest$1 customTileDataInteractor$tileData$$inlined$flatMapLatest$1 = new CustomTileDataInteractor$tileData$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$user$inlined);
        customTileDataInteractor$tileData$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        customTileDataInteractor$tileData$$inlined$flatMapLatest$1.L$1 = obj2;
        return customTileDataInteractor$tileData$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            CustomTileDataInteractor customTileDataInteractor = this.this$0;
            UserHandle userHandle = this.$user$inlined;
            CustomTileServiceInteractor customTileServiceInteractor = customTileDataInteractor.serviceInteractor;
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CustomTileDataInteractor$dataFlow$1(null), customTileServiceInteractor.tileReceivingInterface.mutableRefreshEvents);
            StateFlowImpl stateFlowImpl = customTileServiceInteractor.tileReceivingInterface.mutableCallingAppIds;
            CustomTileRepositoryImpl$getTiles$$inlined$map$1 customTileRepositoryImpl$getTiles$$inlined$map$1 = new CustomTileRepositoryImpl$getTiles$$inlined$map$1(new CustomTileRepositoryImpl$getTiles$$inlined$filter$1(((CustomTileRepositoryImpl) customTileDataInteractor.customTileInteractor.customTileRepository).tileWithUserState, userHandle));
            final CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1 customTileDefaultsRepositoryImpl$defaults$$inlined$map$1 = new CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1(new CustomTileDefaultsRepositoryImpl$defaults$$inlined$filter$1(((CustomTileDefaultsRepositoryImpl) customTileDataInteractor.defaultsRepository).defaults, userHandle));
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine = FlowKt.combine(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, stateFlowImpl, customTileRepositoryImpl$getTiles$$inlined$map$1, new Flow() { // from class: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1

                /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
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
                            CustomTileDefaults customTileDefaults = (CustomTileDefaults) obj;
                            CustomTileDefaults.Result result = customTileDefaults instanceof CustomTileDefaults.Result ? (CustomTileDefaults.Result) customTileDefaults : null;
                            if (result != null) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(result, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
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
                    Object objCollect = customTileDefaultsRepositoryImpl$defaults$$inlined$map$1.collect(new AnonymousClass2(flowCollector2), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, new CustomTileDataInteractor$dataFlow$3(customTileDataInteractor, userHandle, null));
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2Combine, this) == coroutineSingletons) {
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
