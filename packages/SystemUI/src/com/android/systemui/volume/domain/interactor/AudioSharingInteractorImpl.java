package com.android.systemui.volume.domain.interactor;

import android.content.Context;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.volume.data.repository.AudioSharingRepository;
import com.android.settingslib.volume.domain.interactor.AudioVolumeInteractor;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class AudioSharingInteractorImpl implements AudioSharingInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AudioSharingRepository audioSharingRepository;
    public final AudioVolumeInteractor audioVolumeInteractor;
    public final CoroutineContext backgroundCoroutineContext;
    public final CoroutineScope coroutineScope;
    public final StateFlow isInAudioSharing;
    public final Flow volume;
    public final int volumeMax = 255;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.volume.domain.interactor.AudioSharingInteractorImpl$audioSharingVolumeBarAvailable$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Context $context;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            return Boolean.FALSE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$context.getContentResolver();
            boolean z = BluetoothUtils.DEBUG;
            return false;
        }
    }

    /* renamed from: com.android.systemui.volume.domain.interactor.AudioSharingInteractorImpl$handlePrimaryGroupChange$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AudioSharingInteractorImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final StateFlow primaryGroupId = AudioSharingInteractorImpl.this.audioSharingRepository.getPrimaryGroupId();
                final AudioSharingInteractorImpl audioSharingInteractorImpl = AudioSharingInteractorImpl.this;
                Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.volume.domain.interactor.AudioSharingInteractorImpl$handlePrimaryGroupChange$1$invokeSuspend$$inlined$map$1

                    /* renamed from: com.android.systemui.volume.domain.interactor.AudioSharingInteractorImpl$handlePrimaryGroupChange$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AudioSharingInteractorImpl this$0;

                        /* renamed from: com.android.systemui.volume.domain.interactor.AudioSharingInteractorImpl$handlePrimaryGroupChange$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, AudioSharingInteractorImpl audioSharingInteractorImpl) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = audioSharingInteractorImpl;
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
                                Object objM = CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(((Number) obj).intValue(), (Map) this.this$0.audioSharingRepository.getVolumeMap().getValue());
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(objM, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = primaryGroupId.collect(new AnonymousClass2(flowCollector, audioSharingInteractorImpl), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }));
                final AudioSharingInteractorImpl audioSharingInteractorImpl2 = AudioSharingInteractorImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.volume.domain.interactor.AudioSharingInteractorImpl.handlePrimaryGroupChange.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                        int iIntValue = ((Number) obj2).intValue();
                        int i2 = AudioSharingInteractorImpl.$r8$clinit;
                        AudioSharingInteractorImpl audioSharingInteractorImpl3 = audioSharingInteractorImpl2;
                        audioSharingInteractorImpl3.getClass();
                        Object objWithContext = BuildersKt.withContext(audioSharingInteractorImpl3.backgroundCoroutineContext, new AudioSharingInteractorImpl$setMusicStreamVolume$2(audioSharingInteractorImpl3, iIntValue, null), continuation);
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (objWithContext != coroutineSingletons2) {
                            objWithContext = Unit.INSTANCE;
                        }
                        return objWithContext == coroutineSingletons2 ? objWithContext : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowDistinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.volume.domain.interactor.AudioSharingInteractorImpl$setStreamVolume$1, reason: invalid class name and case insensitive filesystem */
    final class C11881 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $level;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11881(int i, Continuation continuation) {
            super(2, continuation);
            this.$level = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AudioSharingInteractorImpl.this.new C11881(this.$level, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11881) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                AudioSharingRepository audioSharingRepository = AudioSharingInteractorImpl.this.audioSharingRepository;
                int i2 = this.$level;
                this.label = 1;
                if (audioSharingRepository.setSecondaryVolume(i2, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public AudioSharingInteractorImpl(CoroutineScope coroutineScope, CoroutineContext coroutineContext, AudioVolumeInteractor audioVolumeInteractor, AudioSharingRepository audioSharingRepository) {
        this.coroutineScope = coroutineScope;
        this.backgroundCoroutineContext = coroutineContext;
        this.audioVolumeInteractor = audioVolumeInteractor;
        this.audioSharingRepository = audioSharingRepository;
        this.isInAudioSharing = audioSharingRepository.getInAudioSharing();
        this.volume = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(audioSharingRepository.getSecondaryGroupId(), audioSharingRepository.getVolumeMap(), new AudioSharingInteractorImpl$volume$1(null)));
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Object audioSharingVolumeBarAvailable(Context context, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundCoroutineContext, new AnonymousClass2(context, null), continuation);
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow getPrimaryDevice() {
        return this.audioSharingRepository.getPrimaryDevice();
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow getSecondaryDevice() {
        return this.audioSharingRepository.getSecondaryDevice();
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow getVolume() {
        return this.volume;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final int getVolumeMax() {
        return this.volumeMax;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final void handlePrimaryGroupChange() {
        BuildersKt.launch$default(this.coroutineScope, null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final Flow isInAudioSharing() {
        return this.isInAudioSharing;
    }

    @Override // com.android.systemui.volume.domain.interactor.AudioSharingInteractor
    public final void setStreamVolume(int i) {
        BuildersKt.launch$default(this.coroutineScope, null, null, new C11881(i, null), 3);
    }
}
