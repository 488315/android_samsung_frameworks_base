package com.android.wm.shell.pip.tv;

import android.app.PictureInPictureParams;
import android.content.Context;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.PipMenuController;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.PipUiEventLogger;
import com.android.wm.shell.pip.PipUtils;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipTaskOrganizer extends PipTaskOrganizer {
    public TvPipTaskOrganizer(Context context, SyncTransactionQueue syncTransactionQueue, PipTransitionState pipTransitionState, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipBoundsAlgorithm pipBoundsAlgorithm, PipMenuController pipMenuController, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, PipTransitionController pipTransitionController, PipParamsChangedForwarder pipParamsChangedForwarder, Optional<SplitScreenController> optional, DisplayController displayController, PipUiEventLogger pipUiEventLogger, ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor) {
        super(context, syncTransactionQueue, pipTransitionState, pipBoundsState, pipDisplayLayoutState, pipBoundsAlgorithm, pipMenuController, pipAnimationController, pipSurfaceTransactionHelper, pipTransitionController, pipParamsChangedForwarder, optional, displayController, pipUiEventLogger, shellTaskOrganizer, shellExecutor);
    }

    @Override // com.android.wm.shell.pip.PipTaskOrganizer
    public final void applyNewPictureInPictureParams(PictureInPictureParams pictureInPictureParams) {
        super.applyNewPictureInPictureParams(pictureInPictureParams);
        boolean aspectRatioChanged = PipUtils.aspectRatioChanged(pictureInPictureParams.getExpandedAspectRatioFloat(), this.mPictureInPictureParams.getExpandedAspectRatioFloat());
        PipParamsChangedForwarder pipParamsChangedForwarder = this.mPipParamsChangedForwarder;
        if (aspectRatioChanged) {
            float expandedAspectRatioFloat = pictureInPictureParams.getExpandedAspectRatioFloat();
            Iterator it = ((ArrayList) pipParamsChangedForwarder.mPipParamsChangedListeners).iterator();
            while (it.hasNext()) {
                ((PipParamsChangedForwarder.PipParamsChangedCallback) it.next()).onExpandedAspectRatioChanged(expandedAspectRatioFloat);
            }
        }
        if (!Objects.equals(pictureInPictureParams.getTitle(), this.mPictureInPictureParams.getTitle())) {
            pipParamsChangedForwarder.notifyTitleChanged(pictureInPictureParams.getTitle());
        }
        if (Objects.equals(pictureInPictureParams.getSubtitle(), this.mPictureInPictureParams.getSubtitle())) {
            return;
        }
        pipParamsChangedForwarder.notifySubtitleChanged(pictureInPictureParams.getSubtitle());
    }

    @Override // com.android.wm.shell.pip.PipTaskOrganizer
    public final boolean shouldSyncPipTransactionWithMenu() {
        return true;
    }
}
