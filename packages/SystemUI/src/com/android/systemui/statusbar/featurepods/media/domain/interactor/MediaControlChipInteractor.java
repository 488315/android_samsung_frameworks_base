package com.android.systemui.statusbar.featurepods.media.domain.interactor;

import android.graphics.drawable.Icon;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.statusbar.featurepods.media.shared.model.MediaControlChipModel;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MediaControlChipInteractor {
    public final StateFlowImpl _mediaControlChipModel;
    public final StateFlowImpl isEnabled;
    public final ReadonlyStateFlow mediaControlChipModel;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 mediaControlChipModelForScene;
    public final StateFlowImpl mediaControlChipModelLegacy;

    public MediaControlChipInteractor(CoroutineScope coroutineScope, MediaFilterRepository mediaFilterRepository) {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.isEnabled = MutableStateFlow;
        this.mediaControlChipModelForScene = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mediaFilterRepository.currentMedia, mediaFilterRepository.selectedUserEntries, new MediaControlChipInteractor$mediaControlChipModelForScene$1(null));
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this.mediaControlChipModelLegacy = MutableStateFlow2;
        this._mediaControlChipModel = MutableStateFlow2;
        this.mediaControlChipModel = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow2, MutableStateFlow, new MediaControlChipInteractor$mediaControlChipModel$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.statusbar.featurepods.media.shared.model.MediaControlChipModel] */
    public final void updateMediaControlChipModelLegacy(MediaData mediaData) {
        if (mediaData != null) {
            Icon icon = mediaData.appIcon;
            CharSequence charSequence = mediaData.song;
            MediaButton mediaButton = mediaData.semanticActions;
            r0 = new MediaControlChipModel(icon, mediaData.app, charSequence, mediaButton != null ? mediaButton.playOrPause : null);
        }
        this.mediaControlChipModelLegacy.setValue(r0);
    }
}
