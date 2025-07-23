package com.android.internal.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class NotificationCloseButton extends ImageView {
    private int mBackgroundColor;
    private int mForegroundColor;
    private Drawable mPillDrawable;

    public NotificationCloseButton(Context context) {
        this(context, null, 0, 0);
    }

    public NotificationCloseButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public NotificationCloseButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationCloseButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        setContentDescription(this.mContext.getText(R.string.close_button_text));
        this.mPillDrawable = ((LayerDrawable) getBackground()).findDrawableByLayerId(R.id.close_button_pill_colorized_layer);
        setVisibility(Resources.getSystem().getBoolean(R.bool.config_notificationCloseButtonSupported) ? 0 : 8);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    private void updateColors() {
        int i = this.mBackgroundColor;
        if (i != 0) {
            this.mPillDrawable.setTintList(ColorStateList.valueOf(i));
        }
        int i2 = this.mForegroundColor;
        if (i2 != 0) {
            setImageTintList(ColorStateList.valueOf(i2));
        }
    }

    @RemotableViewMethod
    public void setForegroundColor(int i) {
        this.mForegroundColor = i;
        updateColors();
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        updateColors();
    }
}
