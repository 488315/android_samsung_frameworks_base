package com.android.systemui.communal.data.repository;

import com.android.systemui.communal.data.model.CommunalMediaModel;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalMediaRepositoryImpl implements CommunalMediaRepository, MediaDataManager.Listener {
    public final StateFlowImpl _mediaModel;
    public final MediaDataManager mediaDataManager;
    public final Flow mediaModel;

    public CommunalMediaRepositoryImpl(MediaDataManager mediaDataManager, TableLogBuffer tableLogBuffer) {
        this.mediaDataManager = mediaDataManager;
        CommunalMediaModel.Companion.getClass();
        CommunalMediaModel communalMediaModel = CommunalMediaModel.INACTIVE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(communalMediaModel);
        this._mediaModel = MutableStateFlow;
        this.mediaModel = DiffableKt.logDiffsForTable(MutableStateFlow, tableLogBuffer, "", communalMediaModel);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
        updateMediaModel(mediaData);
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str, boolean z) {
        updateMediaModel(null);
    }

    public final void updateMediaModel(MediaData mediaData) {
        boolean hasAnyMediaOrRecommendation = this.mediaDataManager.hasAnyMediaOrRecommendation();
        StateFlowImpl stateFlowImpl = this._mediaModel;
        if (hasAnyMediaOrRecommendation) {
            stateFlowImpl.updateState(null, new CommunalMediaModel(true, mediaData != null ? mediaData.createdTimestampMillis : 0L));
        } else {
            CommunalMediaModel.Companion.getClass();
            stateFlowImpl.setValue(CommunalMediaModel.INACTIVE);
        }
    }
}
