package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.content.Context;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel;
import com.android.systemui.volume.panel.shared.model.ResultKt$filterData$$inlined$map$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MediaOutputComponentInteractor$mediaOutputModel$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ MediaOutputComponentInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputComponentInteractor$mediaOutputModel$1(MediaOutputComponentInteractor mediaOutputComponentInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = mediaOutputComponentInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        MediaOutputComponentInteractor$mediaOutputModel$1 mediaOutputComponentInteractor$mediaOutputModel$1 = new MediaOutputComponentInteractor$mediaOutputModel$1(this.this$0, (Continuation) obj4);
        mediaOutputComponentInteractor$mediaOutputModel$1.Z$0 = booleanValue;
        mediaOutputComponentInteractor$mediaOutputModel$1.Z$1 = booleanValue2;
        mediaOutputComponentInteractor$mediaOutputModel$1.L$0 = (AudioOutputDevice) obj3;
        return mediaOutputComponentInteractor$mediaOutputModel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        final AudioOutputDevice audioOutputDevice = (AudioOutputDevice) this.L$0;
        if (z2) {
            Context context = this.this$0.context;
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new MediaOutputComponentModel.Calling(audioOutputDevice, z, false));
        }
        final ResultKt$filterData$$inlined$map$1 filterData = com.android.systemui.volume.panel.shared.model.ResultKt.filterData(this.this$0.sessionWithPlaybackState);
        return new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ AudioOutputDevice $currentAudioDevice$inlined;
                public final /* synthetic */ boolean $isInAudioSharing$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AudioOutputDevice audioOutputDevice, boolean z) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$currentAudioDevice$inlined = audioOutputDevice;
                    this.$isInAudioSharing$inlined = z;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        r11 = this;
                        boolean r0 = r13 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r13
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1$2$1
                        r0.<init>(r13)
                    L18:
                        java.lang.Object r13 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L6a
                    L27:
                        java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                        java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                        r11.<init>(r12)
                        throw r11
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r13)
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.SessionWithPlaybackState r12 = (com.android.systemui.volume.panel.component.mediaoutput.shared.model.SessionWithPlaybackState) r12
                        r13 = 0
                        if (r12 != 0) goto L48
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel$Idle r12 = new com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel$Idle
                        com.android.systemui.volume.domain.model.AudioOutputDevice r2 = r11.$currentAudioDevice$inlined
                        boolean r4 = r11.$isInAudioSharing$inlined
                        if (r4 != 0) goto L44
                        boolean r5 = r2 instanceof com.android.systemui.volume.domain.model.AudioOutputDevice.Unknown
                        if (r5 != 0) goto L44
                        r13 = r3
                    L44:
                        r12.<init>(r2, r4, r13)
                        goto L5f
                    L48:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel$MediaSession r5 = new com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel$MediaSession
                        com.android.systemui.volume.domain.model.AudioOutputDevice r8 = r11.$currentAudioDevice$inlined
                        boolean r9 = r11.$isInAudioSharing$inlined
                        if (r9 != 0) goto L56
                        boolean r2 = r8 instanceof com.android.systemui.volume.domain.model.AudioOutputDevice.Unknown
                        if (r2 != 0) goto L56
                        r10 = r3
                        goto L57
                    L56:
                        r10 = r13
                    L57:
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r6 = r12.session
                        boolean r7 = r12.isPlaybackActive
                        r5.<init>(r6, r7, r8, r9, r10)
                        r12 = r5
                    L5f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                        java.lang.Object r11 = r11.emit(r12, r0)
                        if (r11 != r1) goto L6a
                        return r1
                    L6a:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, audioOutputDevice, z), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
