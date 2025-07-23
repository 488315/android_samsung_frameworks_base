package com.android.systemui.qs.tiles.impl.irecording.domain.interactor;

import android.os.Build;
import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.recordissue.IssueRecordingState;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IssueRecordingDataInteractor implements QSTileDataInteractor {
    public final CoroutineContext bgCoroutineContext;
    public final IssueRecordingState state;

    public IssueRecordingDataInteractor(IssueRecordingState issueRecordingState, CoroutineContext coroutineContext) {
        this.state = issueRecordingState;
        this.bgCoroutineContext = coroutineContext;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(Build.IS_DEBUGGABLE));
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.flowOn(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new IssueRecordingDataInteractor$tileData$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new IssueRecordingDataInteractor$tileData$1(this, null)))), this.bgCoroutineContext);
    }
}
