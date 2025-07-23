package com.android.settingslib.volume.domain.interactor;

import com.android.settingslib.notification.domain.interactor.NotificationsSoundPolicyInteractor;
import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.settingslib.volume.shared.model.AudioStream;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AudioVolumeInteractor {
    public final AudioRepository audioRepository;
    public final NotificationsSoundPolicyInteractor notificationsSoundPolicyInteractor;

    public AudioVolumeInteractor(AudioRepository audioRepository, NotificationsSoundPolicyInteractor notificationsSoundPolicyInteractor) {
        this.audioRepository = audioRepository;
        this.notificationsSoundPolicyInteractor = notificationsSoundPolicyInteractor;
    }

    /* renamed from: canChangeVolume-tLTdkI8, reason: not valid java name */
    public final Flow m983canChangeVolumetLTdkI8(int i) {
        NotificationsSoundPolicyInteractor notificationsSoundPolicyInteractor = this.notificationsSoundPolicyInteractor;
        if (i != 5) {
            final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 m974isZenMutedtLTdkI8 = notificationsSoundPolicyInteractor.m974isZenMutedtLTdkI8(i);
            return new Flow() { // from class: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$2

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$2$2$1, reason: invalid class name */
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
                            boolean r0 = r6 instanceof com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolumetLTdkI8$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$2$2$1 r0 = (com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolumetLTdkI8$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$2$2$1 r0 = new com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$2$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L48
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            java.lang.Boolean r5 = (java.lang.Boolean) r5
                            boolean r5 = r5.booleanValue()
                            r5 = r5 ^ r3
                            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L48
                            return r1
                        L48:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolumetLTdkI8$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        }
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 m974isZenMutedtLTdkI82 = notificationsSoundPolicyInteractor.m974isZenMutedtLTdkI8(i);
        AudioStream.m989constructorimpl(2);
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 m984getAudioStreamtLTdkI8 = m984getAudioStreamtLTdkI8(2);
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(m974isZenMutedtLTdkI82, new Flow() { // from class: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolumetLTdkI8$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$1$2$1 r0 = (com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolumetLTdkI8$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$1$2$1 r0 = new com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.settingslib.volume.shared.model.AudioStreamModel r5 = (com.android.settingslib.volume.shared.model.AudioStreamModel) r5
                        boolean r5 = r5.isMuted
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolumetLTdkI8$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new AudioVolumeInteractor$canChangeVolume$2(null));
    }

    /* renamed from: getAudioStream-tLTdkI8, reason: not valid java name */
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 m984getAudioStreamtLTdkI8(int i) {
        AudioRepositoryImpl audioRepositoryImpl = (AudioRepositoryImpl) this.audioRepository;
        return FlowKt.combine(audioRepositoryImpl.m978getAudioStreamtLTdkI8(i), audioRepositoryImpl.ringerMode, this.notificationsSoundPolicyInteractor.m974isZenMutedtLTdkI8(i), new AudioVolumeInteractor$getAudioStream$1(this, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00f4, code lost:
    
        if (r10.m986setVolumeZdW0WiI(r9, r12 + 1, r0) == r1) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x007f, code lost:
    
        if (r11 == r1) goto L53;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* renamed from: setMuted-ZdW0WiI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m985setMutedZdW0WiI(int r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor.m985setMutedZdW0WiI(int, kotlin.coroutines.jvm.internal.ContinuationImpl, boolean):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00a4, code lost:
    
        if (r5.m985setMutedZdW0WiI(r10, r0, true) == r1) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b9, code lost:
    
        if (r5.m985setMutedZdW0WiI(r10, r0, false) == r1) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x006f, code lost:
    
        if (r11 == r1) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* renamed from: setVolume-ZdW0WiI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m986setVolumeZdW0WiI(int r9, int r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            Method dump skipped, instructions count: 194
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor.m986setVolumeZdW0WiI(int, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
