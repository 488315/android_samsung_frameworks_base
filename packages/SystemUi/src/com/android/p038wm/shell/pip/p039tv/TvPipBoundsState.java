package com.android.p038wm.shell.pip.p039tv;

import android.app.PictureInPictureParams;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Insets;
import android.util.Size;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.pip.PipBoundsAlgorithm;
import com.android.p038wm.shell.pip.PipBoundsState;
import com.android.p038wm.shell.pip.PipDisplayLayoutState;
import com.android.p038wm.shell.pip.phone.PipSizeSpecHandler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipBoundsState extends PipBoundsState {
    public final Context mContext;
    public int mDefaultGravity;
    public float mDesiredTvExpandedAspectRatio;
    public boolean mIsRtl;
    public final boolean mIsTvExpandedPipSupported;
    public boolean mIsTvPipExpanded;
    public Insets mPipMenuPermanentDecorInsets;
    public Insets mPipMenuTemporaryDecorInsets;
    public int mPreviousCollapsedGravity;
    public Size mTvExpandedSize;
    public int mTvFixedPipOrientation;
    public int mTvPipGravity;
    public boolean mTvPipManuallyCollapsed;

    public TvPipBoundsState(Context context, PipSizeSpecHandler pipSizeSpecHandler, PipDisplayLayoutState pipDisplayLayoutState) {
        super(context, pipSizeSpecHandler, pipDisplayLayoutState);
        Insets insets = Insets.NONE;
        this.mPipMenuPermanentDecorInsets = insets;
        this.mPipMenuTemporaryDecorInsets = insets;
        this.mContext = context;
        updateDefaultGravity();
        this.mPreviousCollapsedGravity = this.mDefaultGravity;
        this.mIsTvExpandedPipSupported = context.getPackageManager().hasSystemFeature("android.software.expanded_picture_in_picture");
    }

    @Override // com.android.p038wm.shell.pip.PipBoundsState
    public final void onConfigurationChanged() {
        updateDefaultGravity();
    }

    @Override // com.android.p038wm.shell.pip.PipBoundsState
    public final void setBoundsStateForEntry(ComponentName componentName, ActivityInfo activityInfo, PictureInPictureParams pictureInPictureParams, PipBoundsAlgorithm pipBoundsAlgorithm) {
        super.setBoundsStateForEntry(componentName, activityInfo, pictureInPictureParams, pipBoundsAlgorithm);
        if (pictureInPictureParams == null) {
            return;
        }
        setDesiredTvExpandedAspectRatio(pictureInPictureParams.getExpandedAspectRatioFloat(), true);
    }

    public final void setDesiredTvExpandedAspectRatio(float f, boolean z) {
        int i;
        if (!z && (i = this.mTvFixedPipOrientation) != 0) {
            if ((f <= 1.0f || i != 2) && ((f > 1.0f || i != 1) && f != 0.0f)) {
                return;
            }
            this.mDesiredTvExpandedAspectRatio = f;
            return;
        }
        this.mTvFixedPipOrientation = 0;
        int i2 = this.mDefaultGravity;
        this.mTvPipGravity = i2;
        this.mPreviousCollapsedGravity = i2;
        this.mTvPipManuallyCollapsed = false;
        this.mDesiredTvExpandedAspectRatio = f;
        if (f != 0.0f) {
            if (f > 1.0f) {
                this.mTvFixedPipOrientation = 2;
            } else {
                this.mTvFixedPipOrientation = 1;
            }
        }
    }

    public final void updateDefaultGravity() {
        boolean z = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(this.mContext) == 1;
        this.mDefaultGravity = (z ? 3 : 5) | 80;
        if (this.mIsRtl != z) {
            int i = this.mPreviousCollapsedGravity;
            int i2 = i & 7;
            int i3 = i & 112;
            if ((i2 & 5) == 5) {
                this.mPreviousCollapsedGravity = 3 | i3;
            } else if ((i2 & 3) == 3) {
                this.mPreviousCollapsedGravity = i3 | 5;
            }
        }
        this.mIsRtl = z;
    }
}
