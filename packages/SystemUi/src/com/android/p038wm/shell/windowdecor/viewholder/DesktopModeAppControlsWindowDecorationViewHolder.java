package com.android.p038wm.shell.windowdecor.viewholder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopModeAppControlsWindowDecorationViewHolder extends DesktopModeWindowDecorationViewHolder {
    public final TextView appNameTextView;
    public final View captionHandle;
    public final View captionView;
    public final ImageButton closeWindowButton;
    public final ImageButton expandMenuButton;
    public final View openMenuButton;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DesktopModeAppControlsWindowDecorationViewHolder(View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener, CharSequence charSequence, Drawable drawable) {
        super(view);
        View findViewById = view.findViewById(R.id.desktop_mode_caption);
        this.captionView = findViewById;
        View findViewById2 = view.findViewById(R.id.caption_handle);
        this.captionHandle = findViewById2;
        View findViewById3 = view.findViewById(R.id.open_menu_button);
        this.openMenuButton = findViewById3;
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.close_window);
        this.closeWindowButton = imageButton;
        this.expandMenuButton = (ImageButton) view.findViewById(R.id.expand_menu_button);
        TextView textView = (TextView) view.findViewById(R.id.application_name);
        this.appNameTextView = textView;
        ImageView imageView = (ImageView) view.findViewById(R.id.application_icon);
        findViewById.setOnTouchListener(onTouchListener);
        findViewById2.setOnTouchListener(onTouchListener);
        findViewById3.setOnClickListener(onClickListener);
        findViewById3.setOnTouchListener(onTouchListener);
        imageButton.setOnClickListener(onClickListener);
        imageButton.setOnTouchListener(onTouchListener);
        textView.setText(charSequence);
        imageView.setImageDrawable(drawable);
    }

    @Override // com.android.p038wm.shell.windowdecor.viewholder.DesktopModeWindowDecorationViewHolder
    public final void bindData(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ((GradientDrawable) this.captionView.getBackground()).setColor(runningTaskInfo.taskDescription.getStatusBarColor());
        boolean shouldUseLightCaptionColors = DesktopModeWindowDecorationViewHolder.shouldUseLightCaptionColors(runningTaskInfo);
        Context context = this.context;
        this.closeWindowButton.setImageTintList(ColorStateList.valueOf(shouldUseLightCaptionColors ? context.getColor(R.color.desktop_mode_caption_close_button_light) : context.getColor(R.color.desktop_mode_caption_close_button_dark)));
        this.expandMenuButton.setImageTintList(ColorStateList.valueOf(DesktopModeWindowDecorationViewHolder.shouldUseLightCaptionColors(runningTaskInfo) ? context.getColor(R.color.desktop_mode_caption_expand_button_light) : context.getColor(R.color.desktop_mode_caption_expand_button_dark)));
        this.appNameTextView.setTextColor(DesktopModeWindowDecorationViewHolder.shouldUseLightCaptionColors(runningTaskInfo) ? context.getColor(R.color.desktop_mode_caption_app_name_light) : context.getColor(R.color.desktop_mode_caption_app_name_dark));
    }
}
