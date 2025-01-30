package com.android.wm.shell.pip.tv;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipBackgroundView extends FrameLayout {
    public final View mBackgroundView;
    public int mCurrentMenuMode;
    public final int mElevationAllActionsMenu;
    public final int mElevationMoveMenu;
    public final int mElevationNoMenu;
    public final int mPipMenuFadeAnimationDuration;

    public TvPipBackgroundView(Context context) {
        super(context, null, 0, 0);
        this.mCurrentMenuMode = 0;
        FrameLayout.inflate(context, R.layout.tv_pip_menu_background, this);
        this.mBackgroundView = findViewById(R.id.background_view);
        Resources resources = ((FrameLayout) this).mContext.getResources();
        this.mElevationNoMenu = resources.getDimensionPixelSize(R.dimen.pip_menu_elevation_no_menu);
        this.mElevationMoveMenu = resources.getDimensionPixelSize(R.dimen.pip_menu_elevation_move_menu);
        this.mElevationAllActionsMenu = resources.getDimensionPixelSize(R.dimen.pip_menu_elevation_all_actions_menu);
        this.mPipMenuFadeAnimationDuration = resources.getInteger(R.integer.tv_window_menu_fade_animation_duration);
    }
}
