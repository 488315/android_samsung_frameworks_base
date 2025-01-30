package com.android.wm.shell.windowdecor.viewholder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageButton;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopModeFocusedWindowDecorationViewHolder extends DesktopModeWindowDecorationViewHolder {
    public final ImageButton captionHandle;
    public final View captionView;

    public DesktopModeFocusedWindowDecorationViewHolder(View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        super(view);
        View findViewById = view.findViewById(R.id.desktop_mode_caption);
        this.captionView = findViewById;
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.caption_handle);
        this.captionHandle = imageButton;
        findViewById.setOnTouchListener(onTouchListener);
        imageButton.setOnTouchListener(onTouchListener);
        imageButton.setOnClickListener(onClickListener);
    }

    @Override // com.android.wm.shell.windowdecor.viewholder.DesktopModeWindowDecorationViewHolder
    public final void bindData(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ((GradientDrawable) this.captionView.getBackground()).setColor(runningTaskInfo.taskDescription.getStatusBarColor());
        boolean shouldUseLightCaptionColors = DesktopModeWindowDecorationViewHolder.shouldUseLightCaptionColors(runningTaskInfo);
        Context context = this.context;
        this.captionHandle.setImageTintList(ColorStateList.valueOf(shouldUseLightCaptionColors ? context.getColor(R.color.desktop_mode_caption_handle_bar_light) : context.getColor(R.color.desktop_mode_caption_handle_bar_dark)));
    }
}
