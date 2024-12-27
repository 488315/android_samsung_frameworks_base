package com.android.systemui.communal.data.repository;

import com.android.systemui.communal.data.model.CommunalMediaModel;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class CommunalMediaRepositoryImpl implements CommunalMediaRepository {
    public final StateFlowImpl _mediaModel;
    public final MediaDataManager mediaDataManager;
    public final Flow mediaModel;

    public CommunalMediaRepositoryImpl(MediaDataManager mediaDataManager, TableLogBuffer tableLogBuffer) {
        this.mediaDataManager = mediaDataManager;
        mediaDataManager.addListener(new MediaDataManager.Listener() { // from class: com.android.systemui.communal.data.repository.CommunalMediaRepositoryImpl$mediaDataListener$1
            @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
            public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
                CommunalMediaRepositoryImpl.this.updateMediaModel(mediaData);
            }

            @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
            public final void onMediaDataRemoved(String str, boolean z) {
                CommunalMediaRepositoryImpl.this.updateMediaModel(null);
            }
        });
        CommunalMediaModel.Companion.getClass();
        CommunalMediaModel communalMediaModel = CommunalMediaModel.INACTIVE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(communalMediaModel);
        this._mediaModel = MutableStateFlow;
        this.mediaModel = DiffableKt.logDiffsForTable(MutableStateFlow, tableLogBuffer, "", communalMediaModel);
    }

    public final void updateMediaModel(MediaData mediaData) {
        boolean hasActiveMediaOrRecommendation = this.mediaDataManager.hasActiveMediaOrRecommendation();
        StateFlowImpl stateFlowImpl = this._mediaModel;
        if (hasActiveMediaOrRecommendation) {
            stateFlowImpl.updateState(null, new CommunalMediaModel(true, mediaData != null ? mediaData.createdTimestampMillis : 0L));
        } else {
            CommunalMediaModel.Companion.getClass();
            stateFlowImpl.setValue(CommunalMediaModel.INACTIVE);
        }
    }
}
