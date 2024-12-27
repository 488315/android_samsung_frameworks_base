package com.android.internal.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class NotificationExpandButton extends FrameLayout {
    private int mDefaultPillColor;
    private int mDefaultTextColor;
    private boolean mExpanded;
    private int mHighlightPillColor;
    private int mHighlightTextColor;
    private ImageView mIconView;
    private int mNumber;
    private TextView mNumberView;
    private Drawable mPillDrawable;

    public NotificationExpandButton(Context context) {
        this(context, null, 0, 0);
    }

    public NotificationExpandButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public NotificationExpandButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NotificationExpandButton(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mNumberView = (TextView) findViewById(R.id.expand_button_number);
        this.mIconView = (ImageView) findViewById(R.id.expand_button_icon);
    }

    @Override // android.view.View
    public void getBoundsOnScreen(Rect outRect, boolean clipToParent) {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null && parent.getId() == 16909031) {
            parent.getBoundsOnScreen(outRect, clipToParent);
        } else {
            super.getBoundsOnScreen(outRect, clipToParent);
        }
    }

    @Override // android.view.View
    public boolean pointInView(float localX, float localY, float slop) {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null && parent.getId() == 16909031) {
            return true;
        }
        return super.pointInView(localX, localY, slop);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(Button.class.getName());
    }

    @RemotableViewMethod
    public void setExpanded(boolean expanded) {
        this.mExpanded = expanded;
        updateExpandedState();
    }

    private void updateExpandedState() {
        int drawableId;
        int contentDescriptionId;
        if (this.mExpanded) {
            drawableId = R.drawable.ic_collapse_notification;
            contentDescriptionId = R.string.expand_button_content_description_expanded;
        } else {
            drawableId = R.drawable.ic_expand_notification;
            contentDescriptionId = R.string.expand_button_content_description_collapsed;
        }
        setContentDescription(this.mContext.getText(contentDescriptionId));
        this.mIconView.lambda$setImageURIAsync$0(getContext().getDrawable(drawableId));
        updateNumber();
    }

    private void updateNumber() {
        updateColors();
    }

    private void updateColors() {
        int color =
                Color.argb(
                        102,
                        Color.red(this.mDefaultTextColor),
                        Color.green(this.mDefaultTextColor),
                        Color.blue(this.mDefaultTextColor));
        this.mIconView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        if (this.mDefaultTextColor != 0) {
            this.mNumberView.setTextColor(this.mDefaultTextColor);
        }
    }

    private boolean shouldShowNumber() {
        return !this.mExpanded && this.mNumber > 1;
    }

    @RemotableViewMethod
    public void setDefaultTextColor(int color) {
        this.mDefaultTextColor = color;
        updateColors();
    }

    @RemotableViewMethod
    public void setDefaultPillColor(int color) {
        this.mDefaultPillColor = color;
        updateColors();
    }

    @RemotableViewMethod
    public void setHighlightTextColor(int color) {
        this.mHighlightTextColor = color;
        updateColors();
    }

    @RemotableViewMethod
    public void setHighlightPillColor(int color) {
        this.mHighlightPillColor = color;
        updateColors();
    }

    @RemotableViewMethod
    public void setNumber(int number) {
        if (this.mNumber != number) {
            this.mNumber = number;
            updateNumber();
        }
    }

    @RemotableViewMethod
    public void updateContentDescription() {
        int contentDescriptionId;
        if (this.mExpanded) {
            contentDescriptionId = R.string.expand_button_content_description_expanded;
        } else {
            contentDescriptionId = R.string.expand_button_content_description_collapsed;
        }
        setContentDescription(this.mContext.getText(contentDescriptionId));
    }
}
