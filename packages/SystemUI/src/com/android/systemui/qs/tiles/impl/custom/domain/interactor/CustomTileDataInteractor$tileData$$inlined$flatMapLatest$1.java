package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.UserHandle;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine = FlowKt.combine(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, stateFlowImpl, customTileRepositoryImpl$getTiles$$inlined$map$1, new Flow() { // from class: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L49
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults r5 = (com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults) r5
                            boolean r6 = r5 instanceof com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults.Result
                            if (r6 == 0) goto L3b
                            com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults$Result r5 = (com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults.Result) r5
                            goto L3c
                        L3b:
                            r5 = 0
                        L3c:
                            if (r5 == 0) goto L49
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L49
                            return r1
                        L49:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$dataFlow$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, new CustomTileDataInteractor$dataFlow$3(customTileDataInteractor, userHandle, null));
            this.label = 1;
            if (FlowKt.emitAll(this, combine, flowCollector) == coroutineSingletons) {
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
