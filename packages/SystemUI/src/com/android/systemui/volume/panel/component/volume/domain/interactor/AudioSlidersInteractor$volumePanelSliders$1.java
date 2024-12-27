package com.android.systemui.volume.panel.component.volume.domain.interactor;

import android.media.AudioDeviceInfo;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function5;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowCollector;

final class AudioSlidersInteractor$volumePanelSliders$1 extends SuspendLambda implements Function5 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    int label;
    final /* synthetic */ AudioSlidersInteractor this$0;

    /* renamed from: com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor$volumePanelSliders$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlowCollector $$this$combineTransform;
        final /* synthetic */ MediaDeviceSessions $activeSessions;
        final /* synthetic */ AudioDeviceInfo $communicationDevice;
        final /* synthetic */ MediaDeviceSession $defaultSession;
        int label;
        final /* synthetic */ AudioSlidersInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FlowCollector flowCollector, MediaDeviceSession mediaDeviceSession, MediaDeviceSessions mediaDeviceSessions, AudioSlidersInteractor audioSlidersInteractor, AudioDeviceInfo audioDeviceInfo, Continuation continuation) {
            super(2, continuation);
            this.$$this$combineTransform = flowCollector;
            this.$defaultSession = mediaDeviceSession;
            this.$activeSessions = mediaDeviceSessions;
            this.this$0 = audioSlidersInteractor;
            this.$communicationDevice = audioDeviceInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$$this$combineTransform, this.$defaultSession, this.$activeSessions, this.this$0, this.$communicationDevice, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x008f A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) {
            /*
                r8 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r8.label
                r2 = 1
                if (r1 == 0) goto L16
                if (r1 != r2) goto Le
                kotlin.ResultKt.throwOnFailure(r9)
                goto L90
            Le:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L16:
                kotlin.ResultKt.throwOnFailure(r9)
                com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r9 = r8.$defaultSession
                com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions r1 = r8.$activeSessions
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor r3 = r8.this$0
                android.media.AudioDeviceInfo r4 = r8.$communicationDevice
                kotlin.collections.builders.ListBuilder r5 = new kotlin.collections.builders.ListBuilder
                r5.<init>()
                r6 = 3
                if (r9 == 0) goto L50
                com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r7 = r1.remote
                android.media.session.MediaSession$Token r9 = r9.sessionToken
                if (r7 == 0) goto L32
                android.media.session.MediaSession$Token r7 = r7.sessionToken
                goto L33
            L32:
                r7 = 0
            L33:
                boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r7)
                if (r9 != r2) goto L50
                r3.getClass()
                com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r9 = r1.remote
                if (r9 == 0) goto L4c
                boolean r1 = r9.canAdjustVolume
                if (r1 != r2) goto L4c
                com.android.systemui.volume.panel.component.volume.domain.model.SliderType$MediaDeviceCast r1 = new com.android.systemui.volume.panel.component.volume.domain.model.SliderType$MediaDeviceCast
                r1.<init>(r9)
                r5.add(r1)
            L4c:
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r3, r5, r6)
                goto L63
            L50:
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r3, r5, r6)
                com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r9 = r1.remote
                if (r9 == 0) goto L63
                boolean r1 = r9.canAdjustVolume
                if (r1 != r2) goto L63
                com.android.systemui.volume.panel.component.volume.domain.model.SliderType$MediaDeviceCast r1 = new com.android.systemui.volume.panel.component.volume.domain.model.SliderType$MediaDeviceCast
                r1.<init>(r9)
                r5.add(r1)
            L63:
                if (r4 == 0) goto L71
                int r9 = r4.getType()
                r1 = 7
                if (r9 != r1) goto L71
                r9 = 6
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r3, r5, r9)
                goto L75
            L71:
                r9 = 0
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r3, r5, r9)
            L75:
                r9 = 2
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r3, r5, r9)
                r9 = 5
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r3, r5, r9)
                r9 = 4
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r3, r5, r9)
                kotlin.collections.builders.ListBuilder r9 = r5.build()
                kotlinx.coroutines.flow.FlowCollector r1 = r8.$$this$combineTransform
                r8.label = r2
                java.lang.Object r8 = r1.emit(r9, r8)
                if (r8 != r0) goto L90
                return r0
            L90:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor$volumePanelSliders$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioSlidersInteractor$volumePanelSliders$1(AudioSlidersInteractor audioSlidersInteractor, Continuation continuation) {
        super(5, continuation);
        this.this$0 = audioSlidersInteractor;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        AudioSlidersInteractor$volumePanelSliders$1 audioSlidersInteractor$volumePanelSliders$1 = new AudioSlidersInteractor$volumePanelSliders$1(this.this$0, (Continuation) obj5);
        audioSlidersInteractor$volumePanelSliders$1.L$0 = (FlowCollector) obj;
        audioSlidersInteractor$volumePanelSliders$1.L$1 = (MediaDeviceSessions) obj2;
        audioSlidersInteractor$volumePanelSliders$1.L$2 = (MediaDeviceSession) obj3;
        audioSlidersInteractor$volumePanelSliders$1.L$3 = (AudioDeviceInfo) obj4;
        return audioSlidersInteractor$volumePanelSliders$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MediaDeviceSessions mediaDeviceSessions = (MediaDeviceSessions) this.L$1;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, (MediaDeviceSession) this.L$2, mediaDeviceSessions, this.this$0, (AudioDeviceInfo) this.L$3, null);
            this.L$0 = null;
            this.L$1 = null;
            this.L$2 = null;
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
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
