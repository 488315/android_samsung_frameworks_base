package com.android.systemui.controls.ui.view;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.systemui.R;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsActionButton {
    public CharSequence actionButtonDescription;
    public final ImageView actionIcon;
    public final ProgressBar actionIconProgress;
    public CharSequence title = "";
    public CharSequence subTitle = "";

    public ControlsActionButton(ViewStub viewStub) {
        viewStub.setLayoutResource(R.layout.controls_action_button);
        View inflate = viewStub.inflate();
        ImageView imageView = (ImageView) inflate.findViewById(R.id.action_icon);
        if (imageView != null) {
            imageView.setSoundEffectsEnabled(false);
        } else {
            imageView = null;
        }
        this.actionIcon = imageView;
        this.actionIconProgress = (ProgressBar) inflate.findViewById(R.id.action_icon_progress_circle);
        this.actionButtonDescription = viewStub.getContext().getString(R.string.controls_action_button);
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        ImageView imageView = this.actionIcon;
        if (imageView != null) {
            imageView.setOnClickListener(onClickListener);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void updateContentDescription() {
        ImageView imageView = this.actionIcon;
        if (imageView != null) {
            imageView.setContentDescription(((Object) this.subTitle) + " " + ((Object) this.title) + ", " + ((Object) this.actionButtonDescription));
        }
    }
}
