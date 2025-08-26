package com.android.internal.widget;

import android.app.Flags;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.secutil.Log;
import android.view.RemotableViewMethod;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class NotificationExpandButton extends FrameLayout {
    private static final String TAG = "NotificationExpandButton";
    private int mDefaultPillColor;
    private int mDefaultTextColor;
    private boolean mExpanded;
    private int mHighlightPillColor;
    private int mHighlightTextColor;
    private ImageView mIconView;
    private int mNumber;
    private TextView mNumberView;
    private Drawable mPillDrawable;
    private LinearLayout mPillView;

    public NotificationExpandButton(Context context) {
        this(context, null, 0, 0);
    }

    public NotificationExpandButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public NotificationExpandButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationExpandButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mNumberView = (TextView) findViewById(R.id.expand_button_number);
        this.mIconView = (ImageView) findViewById(R.id.expand_button_icon);
    }

    @Override // android.view.View
    public void getBoundsOnScreen(Rect rect, boolean z) {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null && viewGroup.getId() == 16909069) {
            viewGroup.getBoundsOnScreen(rect, z);
        } else {
            super.getBoundsOnScreen(rect, z);
        }
    }

    @Override // android.view.View
    public boolean pointInView(float f, float f2, float f3) {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null || viewGroup.getId() != 16909069) {
            return super.pointInView(f, f2, f3);
        }
        return true;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    @RemotableViewMethod
    public void setExpanded(boolean z) throws Resources.NotFoundException {
        this.mExpanded = z;
        updateExpandedState();
    }

    @RemotableViewMethod
    public void setStartPadding(int i) {
        setPaddingRelative(i, getPaddingTop(), getPaddingEnd(), getPaddingBottom());
    }

    private void updateExpandedState() throws Resources.NotFoundException {
        int i;
        int i2;
        if (this.mExpanded) {
            i = Flags.notificationsRedesignTemplates() ? R.drawable.ic_notification_2025_collapse : R.drawable.ic_collapse_notification;
            i2 = R.string.expand_button_content_description_expanded;
        } else {
            i = Flags.notificationsRedesignTemplates() ? R.drawable.ic_notification_2025_expand : R.drawable.ic_expand_notification;
            i2 = R.string.expand_button_content_description_collapsed;
        }
        setContentDescription(this.mContext.getText(i2));
        this.mIconView.lambda$setImageURIAsync$0(getContext().getDrawable(i));
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        updateNumber();
    }

    private void updateNumber() throws Resources.NotFoundException {
        updateColors();
        updatePadding();
    }

    private void updatePadding() throws Resources.NotFoundException {
        if (Flags.notificationsRedesignTemplates()) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.notification_2025_expand_button_reduced_end_padding);
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.notification_2025_expand_button_horizontal_icon_padding);
            LinearLayout linearLayout = this.mPillView;
            int paddingTop = linearLayout.getPaddingTop();
            if (!shouldShowNumber()) {
                dimensionPixelSize = dimensionPixelSize2;
            }
            linearLayout.setPaddingRelative(dimensionPixelSize2, paddingTop, dimensionPixelSize, this.mPillView.getPaddingBottom());
        }
    }

    private void updateColors() {
        this.mIconView.setColorFilter(this.mDefaultTextColor, PorterDuff.Mode.SRC_IN);
        int i = this.mDefaultTextColor;
        if (i != 0) {
            this.mNumberView.setTextColor(i);
        }
    }

    private boolean shouldShowNumber() {
        return Flags.notificationsRedesignTemplates() ? this.mNumber > 1 : !this.mExpanded && this.mNumber > 1;
    }

    @RemotableViewMethod
    public void setDefaultTextColor(int i) {
        this.mDefaultTextColor = i;
        updateColors();
    }

    @RemotableViewMethod
    public void setDefaultPillColor(int i) {
        this.mDefaultPillColor = i;
        updateColors();
    }

    @RemotableViewMethod
    public void setHighlightTextColor(int i) {
        this.mHighlightTextColor = i;
        updateColors();
    }

    @RemotableViewMethod
    public void setHighlightPillColor(int i) {
        this.mHighlightPillColor = i;
        updateColors();
    }

    @RemotableViewMethod
    public void setNumber(int i) throws Resources.NotFoundException {
        if (this.mNumber != i) {
            this.mNumber = i;
            updateNumber();
        }
    }

    @RemotableViewMethod
    public void updateContentDescription() {
        setContentDescription(this.mContext.getText(this.mExpanded ? R.string.expand_button_content_description_expanded : R.string.expand_button_content_description_collapsed));
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        super.setAlpha(f);
        if (f == 0.0f) {
            Log.d(TAG, Debug.getCallers(5));
        }
    }
}
