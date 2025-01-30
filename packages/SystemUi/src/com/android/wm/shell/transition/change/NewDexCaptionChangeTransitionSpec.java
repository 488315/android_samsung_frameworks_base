package com.android.wm.shell.transition.change;

import android.view.animation.Animation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NewDexCaptionChangeTransitionSpec extends StandardChangeTransitionSpec {
    @Override // com.android.wm.shell.transition.change.StandardChangeTransitionSpec, com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final Animation createSnapshotAnimation() {
        Animation createSnapshotAnimation = super.createSnapshotAnimation();
        if (this.mChange.getConfiguration().windowConfiguration.isSplitScreen()) {
            createSnapshotAnimation.setHasRoundedCorners(true);
            createSnapshotAnimation.setRoundedCornerRadius(getCornerRadius());
        }
        return createSnapshotAnimation;
    }

    @Override // com.android.wm.shell.transition.change.StandardChangeTransitionSpec
    public final float getCornerRadius() {
        return ChangeTransitionSpec.dipToPixel(12, this.mContext);
    }

    @Override // com.android.wm.shell.transition.change.ChangeTransitionSpec
    public final long getSnapshotAlphaAnimationDuration() {
        return (long) (this.mDurationScale * 300.0f);
    }
}
