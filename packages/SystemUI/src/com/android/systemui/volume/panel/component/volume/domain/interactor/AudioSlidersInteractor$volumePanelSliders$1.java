package com.android.systemui.volume.panel.component.volume.domain.interactor;

import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class AudioSlidersInteractor$volumePanelSliders$1 extends SuspendLambda implements Function6 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ AudioSlidersInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor$volumePanelSliders$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlowCollector $$this$combineTransform;
        final /* synthetic */ MediaDeviceSessions $activeSessions;
        final /* synthetic */ Integer $audioSharingVolume;
        final /* synthetic */ MediaDeviceSession $defaultSession;
        final /* synthetic */ boolean $isOngoingCall;
        int label;
        final /* synthetic */ AudioSlidersInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FlowCollector flowCollector, boolean z, AudioSlidersInteractor audioSlidersInteractor, MediaDeviceSession mediaDeviceSession, MediaDeviceSessions mediaDeviceSessions, Integer num, Continuation continuation) {
            super(2, continuation);
            this.$$this$combineTransform = flowCollector;
            this.$isOngoingCall = z;
            this.this$0 = audioSlidersInteractor;
            this.$defaultSession = mediaDeviceSession;
            this.$activeSessions = mediaDeviceSessions;
            this.$audioSharingVolume = num;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$$this$combineTransform, this.$isOngoingCall, this.this$0, this.$defaultSession, this.$activeSessions, this.$audioSharingVolume, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0087 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r10) {
            /*
                r9 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r9.label
                r2 = 1
                if (r1 == 0) goto L16
                if (r1 != r2) goto Le
                kotlin.ResultKt.throwOnFailure(r10)
                goto L88
            Le:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r10)
                throw r9
            L16:
                kotlin.ResultKt.throwOnFailure(r10)
                boolean r10 = r9.$isOngoingCall
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor r1 = r9.this$0
                com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r3 = r9.$defaultSession
                com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions r4 = r9.$activeSessions
                kotlin.collections.builders.ListBuilder r5 = kotlin.collections.CollectionsKt__CollectionsJVMKt.createListBuilder()
                r6 = 0
                if (r10 == 0) goto L2b
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r1, r5, r6)
            L2b:
                r7 = 3
                if (r3 == 0) goto L55
                com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r8 = r4.remote
                android.media.session.MediaSession$Token r3 = r3.sessionToken
                if (r8 == 0) goto L37
                android.media.session.MediaSession$Token r8 = r8.sessionToken
                goto L38
            L37:
                r8 = 0
            L38:
                boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r8)
                if (r3 != r2) goto L55
                r1.getClass()
                com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r3 = r4.remote
                if (r3 == 0) goto L51
                boolean r4 = r3.canAdjustVolume
                if (r4 != r2) goto L51
                com.android.systemui.volume.panel.component.volume.domain.model.SliderType$MediaDeviceCast r4 = new com.android.systemui.volume.panel.component.volume.domain.model.SliderType$MediaDeviceCast
                r4.<init>(r3)
                r5.add(r4)
            L51:
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r1, r5, r7)
                goto L68
            L55:
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r1, r5, r7)
                com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r3 = r4.remote
                if (r3 == 0) goto L68
                boolean r4 = r3.canAdjustVolume
                if (r4 != r2) goto L68
                com.android.systemui.volume.panel.component.volume.domain.model.SliderType$MediaDeviceCast r4 = new com.android.systemui.volume.panel.component.volume.domain.model.SliderType$MediaDeviceCast
                r4.<init>(r3)
                r5.add(r4)
            L68:
                if (r10 != 0) goto L6d
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r1, r5, r6)
            L6d:
                r10 = 2
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r1, r5, r10)
                r10 = 5
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r1, r5, r10)
                r10 = 4
                com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor.access$addStream(r1, r5, r10)
                kotlin.collections.builders.ListBuilder r10 = r5.build()
                kotlinx.coroutines.flow.FlowCollector r1 = r9.$$this$combineTransform
                r9.label = r2
                java.lang.Object r9 = r1.emit(r10, r9)
                if (r9 != r0) goto L88
                return r0
            L88:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.volume.domain.interactor.AudioSlidersInteractor$volumePanelSliders$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioSlidersInteractor$volumePanelSliders$1(AudioSlidersInteractor audioSlidersInteractor, Continuation continuation) {
        super(6, continuation);
        this.this$0 = audioSlidersInteractor;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj4).booleanValue();
        AudioSlidersInteractor$volumePanelSliders$1 audioSlidersInteractor$volumePanelSliders$1 = new AudioSlidersInteractor$volumePanelSliders$1(this.this$0, (Continuation) obj6);
        audioSlidersInteractor$volumePanelSliders$1.L$0 = (FlowCollector) obj;
        audioSlidersInteractor$volumePanelSliders$1.L$1 = (MediaDeviceSessions) obj2;
        audioSlidersInteractor$volumePanelSliders$1.L$2 = (MediaDeviceSession) obj3;
        audioSlidersInteractor$volumePanelSliders$1.Z$0 = booleanValue;
        audioSlidersInteractor$volumePanelSliders$1.L$3 = (Integer) obj5;
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
            MediaDeviceSession mediaDeviceSession = (MediaDeviceSession) this.L$2;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, this.Z$0, this.this$0, mediaDeviceSession, mediaDeviceSessions, (Integer) this.L$3, null);
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
