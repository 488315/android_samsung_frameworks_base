package com.android.systemui.statusbar.chips.mediaprojection.domain.interactor;

import android.content.pm.PackageManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionRepository;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MediaProjectionChipInteractor {
    public static final String TAG;
    public final LogBuffer logger;
    public final MediaProjectionRepository mediaProjectionRepository;
    public final PackageManager packageManager;
    public final ReadonlyStateFlow projection;
    public final MediaProjectionManagerRepository$special$$inlined$map$1 projectionStartedDuringCallAndActivePostCallEvent;
    public final CoroutineScope scope;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        StatusBarChipLogTags.INSTANCE.getClass();
        TAG = StringsKt__StringsKt.padEnd(20, "MediaProjection");
    }

    public MediaProjectionChipInteractor(CoroutineScope coroutineScope, MediaProjectionRepository mediaProjectionRepository, PackageManager packageManager, LogBuffer logBuffer) {
        this.scope = coroutineScope;
        this.mediaProjectionRepository = mediaProjectionRepository;
        this.packageManager = packageManager;
        this.logger = logBuffer;
        MediaProjectionManagerRepository mediaProjectionManagerRepository = (MediaProjectionManagerRepository) mediaProjectionRepository;
        this.projectionStartedDuringCallAndActivePostCallEvent = mediaProjectionManagerRepository.projectionStartedDuringCallAndActivePostCallEvent;
        final ReadonlyStateFlow readonlyStateFlow = mediaProjectionManagerRepository.mediaProjectionState;
        this.projection = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                    /*
                        r9 = this;
                        boolean r0 = r11 instanceof com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r11
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
                        r0.<init>(r11)
                    L18:
                        java.lang.Object r11 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r11)
                        goto Lc3
                    L28:
                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                        r9.<init>(r10)
                        throw r9
                    L30:
                        kotlin.ResultKt.throwOnFailure(r11)
                        com.android.systemui.mediaprojection.data.model.MediaProjectionState r10 = (com.android.systemui.mediaprojection.data.model.MediaProjectionState) r10
                        boolean r11 = r10 instanceof com.android.systemui.mediaprojection.data.model.MediaProjectionState.NotProjecting
                        r2 = 0
                        com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor r4 = r9.this$0
                        if (r11 == 0) goto L4f
                        com.android.systemui.log.LogBuffer r10 = r4.logger
                        java.lang.String r11 = com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor.TAG
                        com.android.systemui.log.core.LogLevel r4 = com.android.systemui.log.core.LogLevel.INFO
                        com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$projection$1$2 r5 = com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$projection$1$2.INSTANCE
                        com.android.systemui.log.core.LogMessage r11 = r10.obtain(r11, r4, r5, r2)
                        r10.commit(r11)
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$NotProjecting r10 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.NotProjecting.INSTANCE
                        goto Lb8
                    L4f:
                        boolean r11 = r10 instanceof com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting
                        if (r11 == 0) goto Lc6
                        com.android.systemui.mediaprojection.MediaProjectionUtils r11 = com.android.systemui.mediaprojection.MediaProjectionUtils.INSTANCE
                        android.content.pm.PackageManager r5 = r4.packageManager
                        com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting r10 = (com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting) r10
                        java.lang.String r6 = r10.getHostPackage()
                        r11.getClass()
                        boolean r11 = com.android.systemui.util.Utils.isHeadlessRemoteDisplayProvider(r5, r6)
                        if (r11 == 0) goto L69
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Receiver r11 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.Receiver.CastToOtherDevice
                        goto L6b
                    L69:
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Receiver r11 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.Receiver.ShareToApp
                    L6b:
                        boolean r5 = r10 instanceof com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting.EntireScreen
                        if (r5 != 0) goto L81
                        boolean r5 = r10 instanceof com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting.SingleTask
                        if (r5 == 0) goto L74
                        goto L81
                    L74:
                        boolean r5 = r10 instanceof com.android.systemui.mediaprojection.data.model.MediaProjectionState.Projecting.NoScreen
                        if (r5 == 0) goto L7b
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$ContentType r5 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.ContentType.Audio
                        goto L83
                    L7b:
                        kotlin.NoWhenBranchMatchedException r9 = new kotlin.NoWhenBranchMatchedException
                        r9.<init>()
                        throw r9
                    L81:
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$ContentType r5 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.ContentType.Screen
                    L83:
                        java.lang.String r6 = com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor.TAG
                        com.android.systemui.log.core.LogLevel r7 = com.android.systemui.log.core.LogLevel.INFO
                        com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$projection$1$4 r8 = com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor$projection$1$4.INSTANCE
                        com.android.systemui.log.LogBuffer r4 = r4.logger
                        com.android.systemui.log.core.LogMessage r2 = r4.obtain(r6, r7, r8, r2)
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Receiver r6 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.Receiver.CastToOtherDevice
                        r7 = 0
                        if (r11 != r6) goto L96
                        r6 = r3
                        goto L97
                    L96:
                        r6 = r7
                    L97:
                        r8 = r2
                        com.android.systemui.log.LogMessageImpl r8 = (com.android.systemui.log.LogMessageImpl) r8
                        r8.bool1 = r6
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$ContentType r6 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.ContentType.Screen
                        if (r5 != r6) goto La1
                        r7 = r3
                    La1:
                        r8.bool2 = r7
                        java.lang.String r6 = r10.getHostPackage()
                        r8.str1 = r6
                        java.lang.String r6 = r10.getHostDeviceName()
                        r8.str2 = r6
                        r4.commit(r2)
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Projecting r2 = new com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Projecting
                        r2.<init>(r11, r5, r10)
                        r10 = r2
                    Lb8:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                        java.lang.Object r9 = r9.emit(r10, r0)
                        if (r9 != r1) goto Lc3
                        return r1
                    Lc3:
                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                        return r9
                    Lc6:
                        kotlin.NoWhenBranchMatchedException r9 = new kotlin.NoWhenBranchMatchedException
                        r9.<init>()
                        throw r9
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
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new MediaProjectionChipInteractor$stopProjecting$1(this, null), 7);
    }
}
