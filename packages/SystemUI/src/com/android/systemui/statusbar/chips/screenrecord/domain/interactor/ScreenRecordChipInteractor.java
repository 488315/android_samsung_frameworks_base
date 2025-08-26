package com.android.systemui.statusbar.chips.screenrecord.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionRepository;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepository;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class ScreenRecordChipInteractor {
    public static final String TAG;
    public final LogBuffer logger;
    public final CoroutineScope scope;
    public final ScreenRecordRepository screenRecordRepository;
    public final ReadonlyStateFlow screenRecordState;
    public final ReadonlyStateFlow shouldAssumeIsRecording;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor$stopRecording$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ScreenRecordChipInteractor.this.new AnonymousClass1(continuation);
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
                ScreenRecordRepository screenRecordRepository = ScreenRecordChipInteractor.this.screenRecordRepository;
                this.label = 1;
                if (((ScreenRecordRepositoryImpl) screenRecordRepository).stopRecording(4, this) == coroutineSingletons) {
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
        StatusBarChipLogTags.INSTANCE.getClass();
        TAG = StringsKt__StringsKt.padEnd(20, "ScreenRecord");
    }

    public ScreenRecordChipInteractor(CoroutineScope coroutineScope, ScreenRecordRepository screenRecordRepository, MediaProjectionRepository mediaProjectionRepository, LogBuffer logBuffer) {
        this.scope = coroutineScope;
        this.screenRecordRepository = screenRecordRepository;
        this.logger = logBuffer;
        ScreenRecordRepositoryImpl screenRecordRepositoryImpl = (ScreenRecordRepositoryImpl) screenRecordRepository;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(screenRecordRepositoryImpl.screenRecordState, new ScreenRecordChipInteractor$shouldAssumeIsRecording$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(channelFlowTransformLatestTransformLatest, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        this.shouldAssumeIsRecording = readonlyStateFlowStateIn;
        this.screenRecordState = FlowKt.stateIn(FlowKt.combine(screenRecordRepositoryImpl.screenRecordState, ((MediaProjectionManagerRepository) mediaProjectionRepository).mediaProjectionState, readonlyStateFlowStateIn, new ScreenRecordChipInteractor$screenRecordState$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ScreenRecordChipModel.DoingNothing.INSTANCE);
    }

    public final void stopRecording() {
        BuildersKt.launch$default(this.scope, null, null, new AnonymousClass1(null), 3);
    }
}
