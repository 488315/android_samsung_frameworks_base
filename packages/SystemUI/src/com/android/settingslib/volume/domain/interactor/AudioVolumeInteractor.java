package com.android.settingslib.volume.domain.interactor;

import com.android.settingslib.notification.domain.interactor.NotificationsSoundPolicyInteractor;
import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
import com.android.settingslib.volume.shared.model.RingerMode;
import kotlin.ResultKt;
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

/* loaded from: classes.dex */
public final class AudioVolumeInteractor {
    public final AudioRepository audioRepository;
    public final NotificationsSoundPolicyInteractor notificationsSoundPolicyInteractor;

    public AudioVolumeInteractor(AudioRepository audioRepository, NotificationsSoundPolicyInteractor notificationsSoundPolicyInteractor) {
        this.audioRepository = audioRepository;
        this.notificationsSoundPolicyInteractor = notificationsSoundPolicyInteractor;
    }

    /* renamed from: canChangeVolume-tLTdkI8, reason: not valid java name */
    public final Flow m985canChangeVolumetLTdkI8(int i) {
        NotificationsSoundPolicyInteractor notificationsSoundPolicyInteractor = this.notificationsSoundPolicyInteractor;
        if (i != 5) {
            final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3M976isZenMutedtLTdkI8 = notificationsSoundPolicyInteractor.m976isZenMutedtLTdkI8(i);
            return new Flow() { // from class: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$2

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
                            Boolean boolValueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3M976isZenMutedtLTdkI8.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
        }
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3M976isZenMutedtLTdkI82 = notificationsSoundPolicyInteractor.m976isZenMutedtLTdkI8(i);
        AudioStream.m991constructorimpl(2);
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI8 = m986getAudioStreamtLTdkI8(2);
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3M976isZenMutedtLTdkI82, new Flow() { // from class: com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor$canChangeVolume-tLTdkI8$$inlined$map$1

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
                        Boolean boolValueOf = Boolean.valueOf(((AudioStreamModel) obj).isMuted);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI8.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new AudioVolumeInteractor$canChangeVolume$2(null));
    }

    /* renamed from: getAudioStream-tLTdkI8, reason: not valid java name */
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 m986getAudioStreamtLTdkI8(int i) {
        AudioRepositoryImpl audioRepositoryImpl = (AudioRepositoryImpl) this.audioRepository;
        return FlowKt.combine(audioRepositoryImpl.m980getAudioStreamtLTdkI8(i), audioRepositoryImpl.ringerMode, this.notificationsSoundPolicyInteractor.m976isZenMutedtLTdkI8(i), new AudioVolumeInteractor$getAudioStream$1(this, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f4, code lost:
    
        if (r10.m988setVolumeZdW0WiI(r9, r12 + 1, r0) == r1) goto L53;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: setMuted-ZdW0WiI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m987setMutedZdW0WiI(int i, ContinuationImpl continuationImpl, boolean z) {
        AudioVolumeInteractor$setMuted$1 audioVolumeInteractor$setMuted$1;
        AudioVolumeInteractor audioVolumeInteractor;
        boolean z2;
        AudioVolumeInteractor audioVolumeInteractor2;
        boolean z3;
        int i2;
        AudioVolumeInteractor audioVolumeInteractor3;
        AudioStreamModel audioStreamModel;
        int i3;
        if (continuationImpl instanceof AudioVolumeInteractor$setMuted$1) {
            audioVolumeInteractor$setMuted$1 = (AudioVolumeInteractor$setMuted$1) continuationImpl;
            int i4 = audioVolumeInteractor$setMuted$1.label;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                audioVolumeInteractor$setMuted$1.label = i4 - Integer.MIN_VALUE;
            } else {
                audioVolumeInteractor$setMuted$1 = new AudioVolumeInteractor$setMuted$1(this, continuationImpl);
            }
        }
        Object objFirst = audioVolumeInteractor$setMuted$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i5 = audioVolumeInteractor$setMuted$1.label;
        if (i5 == 0) {
            ResultKt.throwOnFailure(objFirst);
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI8 = m986getAudioStreamtLTdkI8(i);
            audioVolumeInteractor$setMuted$1.L$0 = this;
            audioVolumeInteractor$setMuted$1.I$0 = i;
            audioVolumeInteractor$setMuted$1.Z$0 = z;
            audioVolumeInteractor$setMuted$1.label = 1;
            objFirst = FlowKt.first(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI8, audioVolumeInteractor$setMuted$1);
            if (objFirst != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i5 != 1) {
            if (i5 == 2) {
                z2 = audioVolumeInteractor$setMuted$1.Z$0;
                i = audioVolumeInteractor$setMuted$1.I$0;
                audioVolumeInteractor = (AudioVolumeInteractor) audioVolumeInteractor$setMuted$1.L$0;
                ResultKt.throwOnFailure(objFirst);
                AudioVolumeInteractor audioVolumeInteractor4 = audioVolumeInteractor;
                z = z2;
                this = audioVolumeInteractor4;
                AudioRepository audioRepository = this.audioRepository;
                audioVolumeInteractor$setMuted$1.L$0 = this;
                audioVolumeInteractor$setMuted$1.I$0 = i;
                audioVolumeInteractor$setMuted$1.Z$0 = z;
                audioVolumeInteractor$setMuted$1.label = 3;
                objFirst = ((AudioRepositoryImpl) audioRepository).m982setMutedZdW0WiI(i, z, audioVolumeInteractor$setMuted$1);
                if (objFirst != coroutineSingletons) {
                    boolean z4 = z;
                    audioVolumeInteractor2 = this;
                    z3 = z4;
                    if (((Boolean) objFirst).booleanValue()) {
                        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI82 = audioVolumeInteractor2.m986getAudioStreamtLTdkI8(i);
                        audioVolumeInteractor$setMuted$1.L$0 = audioVolumeInteractor2;
                        audioVolumeInteractor$setMuted$1.I$0 = i;
                        audioVolumeInteractor$setMuted$1.label = 4;
                        objFirst = FlowKt.first(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI82, audioVolumeInteractor$setMuted$1);
                        if (objFirst != coroutineSingletons) {
                        }
                    }
                    return Unit.INSTANCE;
                }
                return coroutineSingletons;
            }
            if (i5 != 3) {
                if (i5 != 4) {
                    if (i5 != 5) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(objFirst);
                    return Unit.INSTANCE;
                }
                i2 = audioVolumeInteractor$setMuted$1.I$0;
                audioVolumeInteractor3 = (AudioVolumeInteractor) audioVolumeInteractor$setMuted$1.L$0;
                ResultKt.throwOnFailure(objFirst);
                audioStreamModel = (AudioStreamModel) objFirst;
                i3 = audioStreamModel.volume;
                if (i3 == audioStreamModel.minVolume) {
                    audioVolumeInteractor$setMuted$1.L$0 = null;
                    audioVolumeInteractor$setMuted$1.label = 5;
                }
                return Unit.INSTANCE;
            }
            z3 = audioVolumeInteractor$setMuted$1.Z$0;
            i = audioVolumeInteractor$setMuted$1.I$0;
            audioVolumeInteractor2 = (AudioVolumeInteractor) audioVolumeInteractor$setMuted$1.L$0;
            ResultKt.throwOnFailure(objFirst);
            if (((Boolean) objFirst).booleanValue() && !z3) {
                FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI822 = audioVolumeInteractor2.m986getAudioStreamtLTdkI8(i);
                audioVolumeInteractor$setMuted$1.L$0 = audioVolumeInteractor2;
                audioVolumeInteractor$setMuted$1.I$0 = i;
                audioVolumeInteractor$setMuted$1.label = 4;
                objFirst = FlowKt.first(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI822, audioVolumeInteractor$setMuted$1);
                if (objFirst != coroutineSingletons) {
                    i2 = i;
                    audioVolumeInteractor3 = audioVolumeInteractor2;
                    audioStreamModel = (AudioStreamModel) objFirst;
                    i3 = audioStreamModel.volume;
                    if (i3 == audioStreamModel.minVolume) {
                    }
                }
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }
        z = audioVolumeInteractor$setMuted$1.Z$0;
        i = audioVolumeInteractor$setMuted$1.I$0;
        this = (AudioVolumeInteractor) audioVolumeInteractor$setMuted$1.L$0;
        ResultKt.throwOnFailure(objFirst);
        if (!((AudioStreamModel) objFirst).isAffectedByMute) {
            return Unit.INSTANCE;
        }
        if (i != 2) {
            AudioRepository audioRepository2 = this.audioRepository;
            audioVolumeInteractor$setMuted$1.L$0 = this;
            audioVolumeInteractor$setMuted$1.I$0 = i;
            audioVolumeInteractor$setMuted$1.Z$0 = z;
            audioVolumeInteractor$setMuted$1.label = 3;
            objFirst = ((AudioRepositoryImpl) audioRepository2).m982setMutedZdW0WiI(i, z, audioVolumeInteractor$setMuted$1);
            if (objFirst != coroutineSingletons) {
            }
        } else {
            int i6 = z ? 1 : 2;
            AudioRepository audioRepository3 = this.audioRepository;
            RingerMode.m993constructorimpl(i6);
            audioVolumeInteractor$setMuted$1.L$0 = this;
            audioVolumeInteractor$setMuted$1.I$0 = i;
            audioVolumeInteractor$setMuted$1.Z$0 = z;
            audioVolumeInteractor$setMuted$1.label = 2;
            if (((AudioRepositoryImpl) audioRepository3).m983setRingerModeInternal2JRsiQU(i6, audioVolumeInteractor$setMuted$1) != coroutineSingletons) {
                boolean z5 = z;
                audioVolumeInteractor = this;
                z2 = z5;
                AudioVolumeInteractor audioVolumeInteractor42 = audioVolumeInteractor;
                z = z2;
                this = audioVolumeInteractor42;
                AudioRepository audioRepository22 = this.audioRepository;
                audioVolumeInteractor$setMuted$1.L$0 = this;
                audioVolumeInteractor$setMuted$1.I$0 = i;
                audioVolumeInteractor$setMuted$1.Z$0 = z;
                audioVolumeInteractor$setMuted$1.label = 3;
                objFirst = ((AudioRepositoryImpl) audioRepository22).m982setMutedZdW0WiI(i, z, audioVolumeInteractor$setMuted$1);
                if (objFirst != coroutineSingletons) {
                }
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a4, code lost:
    
        if (r5.m987setMutedZdW0WiI(r10, r0, true) == r1) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b9, code lost:
    
        if (r5.m987setMutedZdW0WiI(r10, r0, false) == r1) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: setVolume-ZdW0WiI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m988setVolumeZdW0WiI(int i, int i2, ContinuationImpl continuationImpl) {
        AudioVolumeInteractor$setVolume$1 audioVolumeInteractor$setVolume$1;
        AudioStreamModel audioStreamModel;
        int i3;
        int i4;
        AudioVolumeInteractor audioVolumeInteractor;
        int i5;
        int i6;
        if (continuationImpl instanceof AudioVolumeInteractor$setVolume$1) {
            audioVolumeInteractor$setVolume$1 = (AudioVolumeInteractor$setVolume$1) continuationImpl;
            int i7 = audioVolumeInteractor$setVolume$1.label;
            if ((i7 & Integer.MIN_VALUE) != 0) {
                audioVolumeInteractor$setVolume$1.label = i7 - Integer.MIN_VALUE;
            } else {
                audioVolumeInteractor$setVolume$1 = new AudioVolumeInteractor$setVolume$1(this, continuationImpl);
            }
        }
        Object objFirst = audioVolumeInteractor$setVolume$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i8 = audioVolumeInteractor$setVolume$1.label;
        if (i8 == 0) {
            ResultKt.throwOnFailure(objFirst);
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI8 = m986getAudioStreamtLTdkI8(i);
            audioVolumeInteractor$setVolume$1.L$0 = this;
            audioVolumeInteractor$setVolume$1.I$0 = i;
            audioVolumeInteractor$setVolume$1.I$1 = i2;
            audioVolumeInteractor$setVolume$1.label = 1;
            objFirst = FlowKt.first(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1M986getAudioStreamtLTdkI8, audioVolumeInteractor$setVolume$1);
            if (objFirst != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i8 != 1) {
            if (i8 != 2) {
                if (i8 == 3) {
                    ResultKt.throwOnFailure(objFirst);
                    return Unit.INSTANCE;
                }
                if (i8 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objFirst);
                return Unit.INSTANCE;
            }
            i5 = audioVolumeInteractor$setVolume$1.I$2;
            i4 = audioVolumeInteractor$setVolume$1.I$1;
            i3 = audioVolumeInteractor$setVolume$1.I$0;
            audioStreamModel = (AudioStreamModel) audioVolumeInteractor$setVolume$1.L$1;
            audioVolumeInteractor = (AudioVolumeInteractor) audioVolumeInteractor$setVolume$1.L$0;
            ResultKt.throwOnFailure(objFirst);
            i6 = audioStreamModel.minVolume;
            if (i4 == i6) {
                if (i5 == i6 && i4 > i6) {
                    audioVolumeInteractor$setVolume$1.L$0 = null;
                    audioVolumeInteractor$setVolume$1.L$1 = null;
                    audioVolumeInteractor$setVolume$1.label = 4;
                }
                return Unit.INSTANCE;
            }
            audioVolumeInteractor$setVolume$1.L$0 = null;
            audioVolumeInteractor$setVolume$1.L$1 = null;
            audioVolumeInteractor$setVolume$1.label = 3;
            return coroutineSingletons;
        }
        i2 = audioVolumeInteractor$setVolume$1.I$1;
        i = audioVolumeInteractor$setVolume$1.I$0;
        this = (AudioVolumeInteractor) audioVolumeInteractor$setVolume$1.L$0;
        ResultKt.throwOnFailure(objFirst);
        audioStreamModel = (AudioStreamModel) objFirst;
        int i9 = audioStreamModel.volume;
        if (i2 != i9) {
            AudioRepository audioRepository = this.audioRepository;
            audioVolumeInteractor$setVolume$1.L$0 = this;
            audioVolumeInteractor$setVolume$1.L$1 = audioStreamModel;
            audioVolumeInteractor$setVolume$1.I$0 = i;
            audioVolumeInteractor$setVolume$1.I$1 = i2;
            audioVolumeInteractor$setVolume$1.I$2 = i9;
            audioVolumeInteractor$setVolume$1.label = 2;
            if (((AudioRepositoryImpl) audioRepository).m984setVolumeZdW0WiI(i, i2, audioVolumeInteractor$setVolume$1) != coroutineSingletons) {
                int i10 = i2;
                i3 = i;
                i4 = i10;
                audioVolumeInteractor = this;
                i5 = i9;
                i6 = audioStreamModel.minVolume;
                if (i4 == i6) {
                }
            }
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
