package com.android.systemui.statusbar.chips.screenrecord.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionRepository;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepository;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ScreenRecordChipInteractor {
    public static final String TAG;
    public final LogBuffer logger;
    public final CoroutineScope scope;
    public final ScreenRecordRepository screenRecordRepository;
    public final ReadonlyStateFlow screenRecordState;
    public final ReadonlyStateFlow shouldAssumeIsRecording;

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
        TAG = StringsKt__StringsKt.padEnd(20, "ScreenRecord");
    }

    public ScreenRecordChipInteractor(CoroutineScope coroutineScope, ScreenRecordRepository screenRecordRepository, MediaProjectionRepository mediaProjectionRepository, LogBuffer logBuffer) {
        this.scope = coroutineScope;
        this.screenRecordRepository = screenRecordRepository;
        this.logger = logBuffer;
        ScreenRecordRepositoryImpl screenRecordRepositoryImpl = (ScreenRecordRepositoryImpl) screenRecordRepository;
        ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(screenRecordRepositoryImpl.screenRecordState, new ScreenRecordChipInteractor$shouldAssumeIsRecording$1(null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(transformLatest, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        this.shouldAssumeIsRecording = stateIn;
        this.screenRecordState = FlowKt.stateIn(FlowKt.combine(screenRecordRepositoryImpl.screenRecordState, ((MediaProjectionManagerRepository) mediaProjectionRepository).mediaProjectionState, stateIn, new ScreenRecordChipInteractor$screenRecordState$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), ScreenRecordChipModel.DoingNothing.INSTANCE);
    }

    public final void stopRecording() {
        BuildersKt.launch$default(this.scope, null, null, new ScreenRecordChipInteractor$stopRecording$1(this, null), 3);
    }
}
