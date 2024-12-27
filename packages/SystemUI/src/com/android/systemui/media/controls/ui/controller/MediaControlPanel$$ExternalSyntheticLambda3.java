package com.android.systemui.media.controls.ui.controller;

import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda3 implements View.OnLongClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaControlPanel f$0;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda3(MediaControlPanel mediaControlPanel, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaControlPanel;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        int i = this.$r8$classId;
        MediaControlPanel mediaControlPanel = this.f$0;
        switch (i) {
            case 0:
                if (!mediaControlPanel.mFalsingManager.isFalseLongTap(1)) {
                    if (!mediaControlPanel.mMediaViewController.isGutsVisible) {
                        mediaControlPanel.openGuts();
                        break;
                    } else {
                        mediaControlPanel.closeGuts(false);
                        break;
                    }
                }
                break;
            default:
                if (!mediaControlPanel.mFalsingManager.isFalseLongTap(1)) {
                    if (!mediaControlPanel.mMediaViewController.isGutsVisible) {
                        mediaControlPanel.openGuts();
                        break;
                    } else {
                        mediaControlPanel.closeGuts(false);
                        break;
                    }
                }
                break;
        }
        return true;
    }
}
