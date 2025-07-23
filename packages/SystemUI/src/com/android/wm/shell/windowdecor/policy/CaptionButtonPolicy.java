package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.CaptionGlobalState;
import com.android.wm.shell.windowdecor.DesktopImmersiveCaptionAnimator;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CaptionButtonPolicy extends CaptionButtonStateManager implements WindowDecorButtonPolicy {
    public final Context mContext;
    public ImageView mDesktopAppIcon;
    public ImageButton mDesktopExpandButton;
    public final Handler mHandler;
    public DesktopImmersiveCaptionAnimator mImmersiveAnimator;
    public boolean mInFullImmersiveState;
    public boolean mIsCaptionTransparent;
    public boolean mIsInDesktopWindowing;
    public boolean mIsKeyguardShowing;
    public boolean mIsStatusBarVisible;

    public CaptionButtonPolicy(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController, Handler handler) {
        super(runningTaskInfo, context, displayController, false, false);
        this.mIsCaptionTransparent = false;
        this.mIsInDesktopWindowing = false;
        this.mInFullImmersiveState = false;
        this.mIsKeyguardShowing = false;
        this.mContext = context;
        this.mHandler = handler;
    }

    public final int getBackgroundColor(Context context) {
        if (this.mIsCaptionTransparent) {
            return 0;
        }
        if (CoreRune.MW_CAPTION_DESKTOP_IMMERSIVE && this.mInFullImmersiveState) {
            return 0;
        }
        return context.getResources().getColor(this.mIsNightMode ? R.color.mw_caption_background_color_dark : R.color.mw_caption_background_color_light, null);
    }

    public final void setupRootView(Context context, View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        View findViewById;
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.desktop_mode_caption);
        if (viewGroup == null) {
            return;
        }
        int displayId = context.getDisplayId();
        DesktopStateImpl.Companion.getClass();
        boolean inDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(displayId);
        this.mIsInDesktopWindowing = inDesktopWindowing;
        if (inDesktopWindowing) {
            viewGroup.setBackgroundColor(getBackgroundColor(context));
        }
        super.setupCaptionButtonState(context, (ViewGroup) viewGroup.findViewById(R.id.button_container), onTouchListener, onClickListener);
        View findViewById2 = viewGroup.findViewById(R.id.caption_handle);
        if (findViewById2 != null) {
            findViewById2.setOnTouchListener(onTouchListener);
        }
        if (CoreRune.MW_CAPTION_DESKTOP && this.mIsInDesktopWindowing && (findViewById = viewGroup.findViewById(R.id.open_menu_button)) != null) {
            findViewById.setOnTouchListener(onTouchListener);
            findViewById.setOnClickListener(onClickListener);
            this.mDesktopAppIcon = (ImageView) findViewById.findViewById(R.id.application_icon);
            ImageButton imageButton = (ImageButton) findViewById.findViewById(R.id.expand_menu_button);
            this.mDesktopExpandButton = imageButton;
            if (imageButton != null) {
                imageButton.setImageTintList(CaptionGlobalState.COLOR_THEME_ENABLED ? getThemeColorStateList(true) : getButtonFillColor(this.mIsNightMode));
            }
        }
    }
}
