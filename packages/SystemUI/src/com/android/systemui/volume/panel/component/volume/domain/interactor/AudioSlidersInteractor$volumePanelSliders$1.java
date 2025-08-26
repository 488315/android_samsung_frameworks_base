package com.android.systemui.volume.panel.component.volume.domain.interactor;

import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import com.android.systemui.volume.panel.component.volume.domain.model.SliderType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class AudioSlidersInteractor$volumePanelSliders$1 extends SuspendLambda implements Function6 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ AudioSlidersInteractor this$0;

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

        /* JADX WARN: Removed duplicated region for block: B:25:0x0055  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                boolean z = this.$isOngoingCall;
                AudioSlidersInteractor audioSlidersInteractor = this.this$0;
                MediaDeviceSession mediaDeviceSession = this.$defaultSession;
                MediaDeviceSessions mediaDeviceSessions = this.$activeSessions;
                ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                if (z) {
                    AudioSlidersInteractor.access$addStream(audioSlidersInteractor, listBuilderCreateListBuilder, 0);
                }
                if (mediaDeviceSession != null) {
                    MediaDeviceSession mediaDeviceSession2 = mediaDeviceSessions.remote;
                    if (Intrinsics.areEqual(mediaDeviceSession.sessionToken, mediaDeviceSession2 != null ? mediaDeviceSession2.sessionToken : null)) {
                        audioSlidersInteractor.getClass();
                        MediaDeviceSession mediaDeviceSession3 = mediaDeviceSessions.remote;
                        if (mediaDeviceSession3 != null && mediaDeviceSession3.canAdjustVolume) {
                            listBuilderCreateListBuilder.add(new SliderType.MediaDeviceCast(mediaDeviceSession3));
                        }
                        AudioSlidersInteractor.access$addStream(audioSlidersInteractor, listBuilderCreateListBuilder, 3);
                    } else {
                        AudioSlidersInteractor.access$addStream(audioSlidersInteractor, listBuilderCreateListBuilder, 3);
                        MediaDeviceSession mediaDeviceSession4 = mediaDeviceSessions.remote;
                        if (mediaDeviceSession4 != null && mediaDeviceSession4.canAdjustVolume) {
                            listBuilderCreateListBuilder.add(new SliderType.MediaDeviceCast(mediaDeviceSession4));
                        }
                    }
                    if (!z) {
                        AudioSlidersInteractor.access$addStream(audioSlidersInteractor, listBuilderCreateListBuilder, 0);
                    }
                    AudioSlidersInteractor.access$addStream(audioSlidersInteractor, listBuilderCreateListBuilder, 2);
                    AudioSlidersInteractor.access$addStream(audioSlidersInteractor, listBuilderCreateListBuilder, 5);
                    AudioSlidersInteractor.access$addStream(audioSlidersInteractor, listBuilderCreateListBuilder, 4);
                    ListBuilder listBuilderBuild = listBuilderCreateListBuilder.build();
                    FlowCollector flowCollector = this.$$this$combineTransform;
                    this.label = 1;
                    if (flowCollector.emit(listBuilderBuild, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioSlidersInteractor$volumePanelSliders$1(AudioSlidersInteractor audioSlidersInteractor, Continuation continuation) {
        super(6, continuation);
        this.this$0 = audioSlidersInteractor;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj4).booleanValue();
        AudioSlidersInteractor$volumePanelSliders$1 audioSlidersInteractor$volumePanelSliders$1 = new AudioSlidersInteractor$volumePanelSliders$1(this.this$0, (Continuation) obj6);
        audioSlidersInteractor$volumePanelSliders$1.L$0 = (FlowCollector) obj;
        audioSlidersInteractor$volumePanelSliders$1.L$1 = (MediaDeviceSessions) obj2;
        audioSlidersInteractor$volumePanelSliders$1.L$2 = (MediaDeviceSession) obj3;
        audioSlidersInteractor$volumePanelSliders$1.Z$0 = zBooleanValue;
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
