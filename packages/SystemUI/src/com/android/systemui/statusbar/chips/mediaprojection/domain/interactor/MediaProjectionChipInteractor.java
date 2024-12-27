package com.android.systemui.statusbar.chips.mediaprojection.domain.interactor;

import android.content.pm.PackageManager;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionRepository;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class MediaProjectionChipInteractor {
    public final MediaProjectionRepository mediaProjectionRepository;
    public final PackageManager packageManager;
    public final ReadonlyStateFlow projection;
    public final CoroutineScope scope;

    public MediaProjectionChipInteractor(CoroutineScope coroutineScope, MediaProjectionRepository mediaProjectionRepository, PackageManager packageManager) {
        this.scope = coroutineScope;
        this.mediaProjectionRepository = mediaProjectionRepository;
        this.packageManager = packageManager;
        final ReadonlyStateFlow readonlyStateFlow = ((MediaProjectionManagerRepository) mediaProjectionRepository).mediaProjectionState;
        this.projection = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaProjectionChipInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaProjectionChipInteractor mediaProjectionChipInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaProjectionChipInteractor;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L65
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.mediaprojection.data.model.MediaProjectionState r5 = (com.android.systemui.mediaprojection.data.model.MediaProjectionState) r5
                        boolean r6 = r5 instanceof com.android.systemui.mediaprojection.data.model.MediaProjectionState.NotProjecting
                        if (r6 == 0) goto L3b
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$NotProjecting r5 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.NotProjecting.INSTANCE
                        goto L5a
                    L3b:
                        boolean r6 = r5 instanceof com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
                        if (r6 == 0) goto L68
                        com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting r5 = (com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting) r5
                        java.lang.String r6 = r5.getHostPackage()
                        com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor r2 = r4.this$0
                        android.content.pm.PackageManager r2 = r2.packageManager
                        boolean r6 = com.android.systemui.util.Utils.isHeadlessRemoteDisplayProvider(r2, r6)
                        if (r6 == 0) goto L52
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Type r6 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.Type.CAST_TO_OTHER_DEVICE
                        goto L54
                    L52:
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Type r6 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.Type.SHARE_TO_APP
                    L54:
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Projecting r2 = new com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Projecting
                        r2.<init>(r6, r5)
                        r5 = r2
                    L5a:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L65
                        return r1
                    L65:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L68:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), ProjectionChipModel.NotProjecting.INSTANCE);
    }

    public final void stopProjecting() {
        BuildersKt.launch$default(this.scope, null, null, new MediaProjectionChipInteractor$stopProjecting$1(this, null), 3);
    }
}
