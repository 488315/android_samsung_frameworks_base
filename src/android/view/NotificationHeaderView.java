package android.view;

import android.app.Flags;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.NotificationExpandButton;
import java.util.ArrayList;

@RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class NotificationHeaderView extends RelativeLayout {
    private boolean mAcceptAllTouches;
    private View mAltExpandTarget;
    private Drawable mBackground;
    private boolean mEntireHeaderClickable;
    private NotificationExpandButton mExpandButton;
    private float mExpandButtonTranslation;
    private View.OnClickListener mExpandClickListener;
    private boolean mExpandOnlyOnButton;
    private CachingIconView mIcon;
    ViewOutlineProvider mProvider;
    private float mTopLineTranslation;
    private NotificationTopLineView mTopLineView;
    private HeaderTouchListener mTouchListener;
    private final int mTouchableHeight;

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public NotificationHeaderView(Context context) {
        this(context, null);
    }

    public NotificationHeaderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationHeaderView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationHeaderView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTouchListener = new HeaderTouchListener();
        this.mProvider = new ViewOutlineProvider() { // from class: android.view.NotificationHeaderView.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                if (NotificationHeaderView.this.mBackground != null) {
                    outline.setRect(0, 0, NotificationHeaderView.this.getWidth(), NotificationHeaderView.this.getHeight());
                    outline.setAlpha(1.0f);
                }
            }
        };
        Resources resources = getResources();
        this.mTouchableHeight = resources.getDimensionPixelSize(R.dimen.notification_header_touchable_height);
        this.mEntireHeaderClickable = resources.getBoolean(R.bool.config_notificationHeaderClickableForExpand);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mIcon = (CachingIconView) findViewById(16908294);
        this.mTopLineView = (NotificationTopLineView) findViewById(R.id.notification_top_line);
        this.mExpandButton = (NotificationExpandButton) findViewById(R.id.expand_button);
        this.mAltExpandTarget = findViewById(R.id.alternate_expand_target);
        setClipToPadding(false);
    }

    public void setHeaderBackgroundDrawable(Drawable drawable) {
        if (drawable != null) {
            setWillNotDraw(false);
            this.mBackground = drawable;
            drawable.setCallback(this);
            setOutlineProvider(this.mProvider);
        } else {
            setWillNotDraw(true);
            this.mBackground = null;
            setOutlineProvider(null);
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getHeight());
            this.mBackground.draw(canvas);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mBackground;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        Drawable drawable = this.mBackground;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        this.mBackground.setState(getDrawableState());
    }

    private void updateTouchListener() {
        if (this.mExpandClickListener == null) {
            setOnTouchListener(null);
        } else {
            setOnTouchListener(this.mTouchListener);
            this.mTouchListener.bindTouchRects();
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mExpandClickListener = onClickListener;
        this.mExpandButton.setOnClickListener(onClickListener);
        this.mAltExpandTarget.setOnClickListener(this.mExpandClickListener);
        updateTouchListener();
    }

    public void setTopLineExtraMarginEnd(int i) {
        this.mTopLineView.setHeaderTextMarginEnd(i);
    }

    @RemotableViewMethod
    public void setTopLineExtraMarginEndDp(float f) {
        setTopLineExtraMarginEnd((int) (f * getResources().getDisplayMetrics().density));
    }

    @RemotableViewMethod
    public void centerTopLine(boolean z) {
        if (Flags.notificationsRedesignTemplates()) {
            ViewGroup.LayoutParams layoutParams = this.mTopLineView.getLayoutParams();
            layoutParams.height = z ? -1 : -2;
            this.mTopLineView.setLayoutParams(layoutParams);
            centerExpandButton(z);
        }
    }

    private void centerExpandButton(boolean z) {
        ViewGroup.LayoutParams layoutParams = this.mExpandButton.getLayoutParams();
        layoutParams.height = z ? -1 : -2;
        if (layoutParams instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) layoutParams).gravity = z ? 17 : 8388661;
        }
        this.mExpandButton.setLayoutParams(layoutParams);
    }

    public NotificationTopLineView getTopLineView() {
        return this.mTopLineView;
    }

    public NotificationExpandButton getExpandButton() {
        return this.mExpandButton;
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (Flags.notificationsRedesignTemplates()) {
            NotificationTopLineView notificationTopLineView = this.mTopLineView;
            if (notificationTopLineView != null) {
                this.mTopLineTranslation = measureCenterTranslation(notificationTopLineView);
            }
            NotificationExpandButton notificationExpandButton = this.mExpandButton;
            if (notificationExpandButton != null) {
                this.mExpandButtonTranslation = measureCenterTranslation(notificationExpandButton);
            }
        }
    }

    private float measureCenterTranslation(View view) {
        return MathUtils.abs(((getMeasuredHeight() - view.getMeasuredHeight()) / 2.0f) - ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin);
    }

    public float getTopLineTranslation() {
        return this.mTopLineTranslation;
    }

    public float getExpandButtonTranslation() {
        return this.mExpandButtonTranslation;
    }

    @RemotableViewMethod
    public void styleTextAsTitle(boolean z) {
        int i = z ? R.style.TextAppearance_DeviceDefault_Notification_Title : R.style.TextAppearance_DeviceDefault_Notification_Info;
        View findViewById = findViewById(R.id.header_text);
        if (findViewById instanceof TextView) {
            ((TextView) findViewById).setTextAppearance(i);
        }
        View findViewById2 = findViewById(R.id.app_name_text);
        if (findViewById2 instanceof TextView) {
            ((TextView) findViewById2).setTextAppearance(i);
        }
    }

    public class HeaderTouchListener implements View.OnTouchListener {
        private Rect mAltExpandTargetRect;
        private float mDownX;
        private float mDownY;
        private Rect mExpandButtonRect;
        private final ArrayList<Rect> mTouchRects = new ArrayList<>();
        private int mTouchSlop;
        private boolean mTrackGesture;

        public HeaderTouchListener() {
        }

        public void bindTouchRects() {
            this.mTouchRects.clear();
            if (NotificationHeaderView.this.mIcon != null) {
                addRectAroundView(NotificationHeaderView.this.mIcon);
            }
            this.mExpandButtonRect = addRectAroundView(NotificationHeaderView.this.mExpandButton);
            this.mAltExpandTargetRect = addRectAroundView(NotificationHeaderView.this.mAltExpandTarget);
            addWidthRect();
            this.mTouchSlop = ViewConfiguration.get(NotificationHeaderView.this.getContext()).getScaledTouchSlop();
        }

        private void addWidthRect() {
            Rect rect = new Rect();
            rect.top = 0;
            rect.bottom = NotificationHeaderView.this.mTouchableHeight;
            rect.left = 0;
            rect.right = NotificationHeaderView.this.getWidth();
            this.mTouchRects.add(rect);
        }

        private Rect addRectAroundView(View view) {
            Rect rectAroundView = getRectAroundView(view);
            this.mTouchRects.add(rectAroundView);
            return rectAroundView;
        }

        private Rect getRectAroundView(View view) {
            float f = NotificationHeaderView.this.getResources().getDisplayMetrics().density * 48.0f;
            float max = Math.max(f, view.getWidth());
            float max2 = Math.max(f, view.getHeight());
            Rect rect = new Rect();
            if (view.getVisibility() == 8) {
                view = NotificationHeaderView.this.getFirstChildNotGone();
                rect.left = (int) (view.getLeft() - (max / 2.0f));
            } else {
                rect.left = (int) (((view.getLeft() + view.getRight()) / 2.0f) - (max / 2.0f));
            }
            rect.top = (int) (((view.getTop() + view.getBottom()) / 2.0f) - (max2 / 2.0f));
            rect.bottom = (int) (rect.top + max2);
            rect.right = (int) (rect.left + max);
            return rect;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int actionMasked = motionEvent.getActionMasked() & 255;
            if (actionMasked == 0) {
                this.mTrackGesture = false;
                if (isInside(x, y)) {
                    this.mDownX = x;
                    this.mDownY = y;
                    this.mTrackGesture = true;
                    return true;
                }
            } else if (actionMasked != 1) {
                if (actionMasked == 2 && this.mTrackGesture && (Math.abs(this.mDownX - x) > this.mTouchSlop || Math.abs(this.mDownY - y) > this.mTouchSlop)) {
                    this.mTrackGesture = false;
                }
            } else if (this.mTrackGesture) {
                float x2 = NotificationHeaderView.this.mTopLineView.getX();
                float y2 = NotificationHeaderView.this.mTopLineView.getY();
                if (!NotificationHeaderView.this.mTopLineView.onTouchUp(x - x2, y - y2, this.mDownX - x2, this.mDownY - y2)) {
                    NotificationHeaderView.this.mExpandButton.performClick();
                }
            }
            return this.mTrackGesture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isInside(float f, float f2) {
            if (NotificationHeaderView.this.mAcceptAllTouches) {
                return true;
            }
            if (NotificationHeaderView.this.mExpandOnlyOnButton) {
                int i = (int) f;
                int i2 = (int) f2;
                return this.mExpandButtonRect.contains(i, i2) || this.mAltExpandTargetRect.contains(i, i2);
            }
            for (int i3 = 0; i3 < this.mTouchRects.size(); i3++) {
                if (this.mTouchRects.get(i3).contains((int) f, (int) f2)) {
                    return true;
                }
            }
            return NotificationHeaderView.this.mTopLineView.isInTouchRect(f - NotificationHeaderView.this.mTopLineView.getX(), f2 - NotificationHeaderView.this.mTopLineView.getY());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getFirstChildNotGone() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                return childAt;
            }
        }
        return this;
    }

    public boolean isInTouchRect(float f, float f2) {
        if (this.mExpandClickListener == null) {
            return false;
        }
        return this.mTouchListener.isInside(f, f2);
    }

    @RemotableViewMethod
    public void setAcceptAllTouches(boolean z) {
        this.mAcceptAllTouches = this.mEntireHeaderClickable || z;
    }

    @RemotableViewMethod
    public void setExpandOnlyOnButton(boolean z) {
        this.mExpandOnlyOnButton = z;
    }
}
