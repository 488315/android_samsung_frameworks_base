package com.android.wm.shell.windowdecor.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/* loaded from: classes3.dex */
public class DesktopCaptionButton extends LinearLayout {
    public final ImageView mIconView;
    public final ImageView mNotification;
    public final View mRootView;
    public final TextView mTextView;

    public DesktopCaptionButton(Context context, AttributeSet attributeSet) {
        super(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.DayNight), attributeSet);
        View viewInflate = LayoutInflater.from(((LinearLayout) this).mContext).inflate(com.android.systemui.R.layout.mw_dex_caption_popup_button, this);
        this.mRootView = viewInflate;
        ImageView imageView = (ImageView) viewInflate.requireViewById(com.android.systemui.R.id.button_icon);
        this.mIconView = imageView;
        TextView textView = (TextView) viewInflate.requireViewById(com.android.systemui.R.id.button_description);
        this.mTextView = textView;
        this.mNotification = (ImageView) viewInflate.requireViewById(com.android.systemui.R.id.button_notification);
        setClickable(true);
        setFocusable(true);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        setBackgroundResource(typedValue.resourceId);
        TypedArray typedArrayObtainStyledAttributes = ((LinearLayout) this).mContext.obtainStyledAttributes(attributeSet, com.android.wm.shell.R.styleable.DesktopCaptionButton);
        try {
            textView.setText(typedArrayObtainStyledAttributes.getString(1));
            imageView.setImageDrawable(context.getResources().getDrawable(typedArrayObtainStyledAttributes.getResourceId(0, 0), null));
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.View
    public final View getRootView() {
        return this.mRootView;
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        setClickable(z);
        this.mTextView.setAlpha(z ? 1.0f : 0.4f);
        this.mIconView.setAlpha(z ? 1.0f : 0.4f);
    }
}
