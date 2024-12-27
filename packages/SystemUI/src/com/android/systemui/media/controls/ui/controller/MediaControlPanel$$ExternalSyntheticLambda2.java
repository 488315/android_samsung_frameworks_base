package com.android.systemui.media.controls.ui.controller;

import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.util.MediaUiEvent;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaControlPanel f$0;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda2(MediaControlPanel mediaControlPanel, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaControlPanel;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        InstanceId instanceId;
        switch (this.$r8$classId) {
            case 0:
                MediaControlPanel mediaControlPanel = this.f$0;
                String str = mediaControlPanel.mPackageName;
                if (str != null && (instanceId = mediaControlPanel.mInstanceId) != null) {
                    mediaControlPanel.mLogger.logger.logWithInstanceId(MediaUiEvent.ACTION_SEEK, mediaControlPanel.mUid, str, instanceId);
                }
                mediaControlPanel.logSmartspaceCardReported(760, 0, 0);
                break;
            case 1:
                MediaControlPanel mediaControlPanel2 = this.f$0;
                int numberOfFittedRecommendations = mediaControlPanel2.getNumberOfFittedRecommendations();
                MediaViewController mediaViewController = mediaControlPanel2.mMediaViewController;
                ConstraintSet constraintSet = mediaViewController.expandedLayout;
                ConstraintSet constraintSet2 = mediaViewController.collapsedLayout;
                List list = mediaControlPanel2.mRecommendationViewHolder.mediaCoverContainers;
                int i = 0;
                while (i < 3) {
                    boolean z = true;
                    MediaControlPanel.setVisibleAndAlpha(constraintSet, ((ViewGroup) list.get(i)).getId(), i < numberOfFittedRecommendations);
                    int id = ((ViewGroup) list.get(i)).getId();
                    if (i >= numberOfFittedRecommendations) {
                        z = false;
                    }
                    MediaControlPanel.setVisibleAndAlpha(constraintSet2, id, z);
                    i++;
                }
                break;
            default:
                this.f$0.mMediaViewController.refreshState();
                break;
        }
        return Unit.INSTANCE;
    }
}
