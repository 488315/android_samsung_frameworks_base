package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HandleMenu {
    public final Drawable mAppIcon;
    public WindowDecoration.AdditionalWindow mAppInfoPill;
    public int mAppInfoPillHeight;
    public final CharSequence mAppName;
    public final int mCaptionX;
    public final int mCaptionY;
    public final Context mContext;
    public int mCornerRadius;
    public final int mLayoutResId;
    public int mMarginMenuSpacing;
    public int mMarginMenuStart;
    public int mMarginMenuTop;
    public int mMenuWidth;
    public WindowDecoration.AdditionalWindow mMoreActionsPill;
    public int mMoreActionsPillHeight;
    public final View.OnClickListener mOnClickListener;
    public final View.OnTouchListener mOnTouchListener;
    public final WindowDecoration mParentDecor;
    public int mShadowRadius;
    public final boolean mShouldShowWindowingPill;
    public final ActivityManager.RunningTaskInfo mTaskInfo;
    public WindowDecoration.AdditionalWindow mWindowingPill;
    public int mWindowingPillHeight;
    public final PointF mAppInfoPillPosition = new PointF();
    public final PointF mWindowingPillPosition = new PointF();
    public final PointF mMoreActionsPillPosition = new PointF();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final WindowDecoration mParent;

        public Builder(WindowDecoration windowDecoration) {
            this.mParent = windowDecoration;
        }
    }

    public HandleMenu(WindowDecoration windowDecoration, int i, int i2, int i3, View.OnClickListener onClickListener, View.OnTouchListener onTouchListener, Drawable drawable, CharSequence charSequence, boolean z) {
        this.mParentDecor = windowDecoration;
        Context context = windowDecoration.mDecorWindowContext;
        this.mContext = context;
        this.mTaskInfo = windowDecoration.mTaskInfo;
        this.mLayoutResId = i;
        this.mCaptionX = i2;
        this.mCaptionY = i3;
        this.mOnClickListener = onClickListener;
        this.mOnTouchListener = onTouchListener;
        this.mAppIcon = drawable;
        this.mAppName = charSequence;
        this.mShouldShowWindowingPill = z;
        Resources resources = context.getResources();
        this.mMenuWidth = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_width, resources);
        this.mMarginMenuTop = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_margin_top, resources);
        this.mMarginMenuStart = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_margin_start, resources);
        this.mMarginMenuSpacing = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_pill_spacing_margin, resources);
        this.mAppInfoPillHeight = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_app_info_pill_height, resources);
        this.mWindowingPillHeight = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_windowing_pill_height, resources);
        this.mMoreActionsPillHeight = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_more_actions_pill_height, resources);
        this.mShadowRadius = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_shadow_radius, resources);
        this.mCornerRadius = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_corner_radius, resources);
        updateHandleMenuPillPositions();
    }

    public static int loadDimensionPixelSize(int i, Resources resources) {
        if (i == 0) {
            return 0;
        }
        return resources.getDimensionPixelSize(i);
    }

    public static boolean pointInView(View view, float f, float f2) {
        return view != null && ((float) view.getLeft()) <= f && ((float) view.getRight()) >= f && ((float) view.getTop()) <= f2 && ((float) view.getBottom()) >= f2;
    }

    public final void updateHandleMenuPillPositions() {
        int i;
        int i2;
        int width = this.mTaskInfo.getConfiguration().windowConfiguration.getBounds().width();
        int i3 = this.mLayoutResId;
        int i4 = this.mCaptionY;
        int i5 = this.mCaptionX;
        if (i3 == R.layout.desktop_mode_app_controls_window_decor) {
            i = i5 + this.mMarginMenuStart;
            i2 = this.mMarginMenuTop;
        } else {
            i = ((width / 2) + i5) - (this.mMenuWidth / 2);
            i2 = this.mMarginMenuStart;
        }
        int i6 = i4 + i2;
        float f = i;
        this.mAppInfoPillPosition.set(f, i6);
        boolean z = this.mShouldShowWindowingPill;
        PointF pointF = this.mMoreActionsPillPosition;
        if (!z) {
            pointF.set(f, i6 + this.mAppInfoPillHeight + this.mMarginMenuSpacing);
            return;
        }
        this.mWindowingPillPosition.set(f, i6 + this.mAppInfoPillHeight + this.mMarginMenuSpacing);
        pointF.set(f, r3 + this.mWindowingPillHeight + this.mMarginMenuSpacing);
    }
}
