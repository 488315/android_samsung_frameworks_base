package androidx.viewpager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class PagerTabStrip extends PagerTitleStrip {
    public boolean mDrawFullUnderline;
    public final int mFullUnderlineHeight;
    public boolean mIgnoreTap;
    public final int mIndicatorColor;
    public final int mIndicatorHeight;
    public float mInitialMotionX;
    public float mInitialMotionY;
    public final int mMinPaddingBottom;
    public final int mMinStripHeight;
    public int mTabAlpha;
    public final int mTabPadding;
    public final Paint mTabPaint;
    public final int mTouchSlop;

    public PagerTabStrip(Context context) {
        this(context, null);
    }

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public final int getMinHeight() {
        return Math.max(super.getMinHeight(), this.mMinStripHeight);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int left = this.mCurrText.getLeft() - this.mTabPadding;
        int right = this.mCurrText.getRight() + this.mTabPadding;
        int i = height - this.mIndicatorHeight;
        this.mTabPaint.setColor((this.mTabAlpha << 24) | (this.mIndicatorColor & 16777215));
        float f = height;
        canvas.drawRect(left, i, right, f, this.mTabPaint);
        if (this.mDrawFullUnderline) {
            this.mTabPaint.setColor((this.mIndicatorColor & 16777215) | EmergencyPhoneWidget.BG_COLOR);
            canvas.drawRect(getPaddingLeft(), height - this.mFullUnderlineHeight, getWidth() - getPaddingRight(), f, this.mTabPaint);
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0 && this.mIgnoreTap) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (action == 0) {
            this.mInitialMotionX = x;
            this.mInitialMotionY = y;
            this.mIgnoreTap = false;
        } else if (action != 1) {
            if (action == 2 && (Math.abs(x - this.mInitialMotionX) > this.mTouchSlop || Math.abs(y - this.mInitialMotionY) > this.mTouchSlop)) {
                this.mIgnoreTap = true;
            }
        } else if (x < this.mCurrText.getLeft() - this.mTabPadding) {
            ViewPager viewPager = this.mPager;
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        } else if (x > this.mCurrText.getRight() + this.mTabPadding) {
            ViewPager viewPager2 = this.mPager;
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
        return true;
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        this.mDrawFullUnderline = (i & EmergencyPhoneWidget.BG_COLOR) == 0;
    }

    @Override // android.view.View
    public final void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        this.mDrawFullUnderline = drawable == null;
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        this.mDrawFullUnderline = i == 0;
    }

    @Override // android.view.View
    public final void setPadding(int i, int i2, int i3, int i4) {
        int i5 = this.mMinPaddingBottom;
        if (i4 < i5) {
            i4 = i5;
        }
        super.setPadding(i, i2, i3, i4);
    }

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public final void updateTextPositions(float f, int i, boolean z) {
        super.updateTextPositions(f, i, z);
        this.mTabAlpha = (int) (Math.abs(f - 0.5f) * 2.0f * 255.0f);
        invalidate();
    }

    public PagerTabStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mTabPaint = paint;
        new Rect();
        this.mTabAlpha = 255;
        this.mDrawFullUnderline = false;
        int i = this.mTextColor;
        this.mIndicatorColor = i;
        paint.setColor(i);
        float f = context.getResources().getDisplayMetrics().density;
        this.mIndicatorHeight = (int) ((3.0f * f) + 0.5f);
        this.mMinPaddingBottom = (int) ((6.0f * f) + 0.5f);
        int i2 = (int) (64.0f * f);
        this.mTabPadding = (int) ((16.0f * f) + 0.5f);
        this.mFullUnderlineHeight = (int) ((1.0f * f) + 0.5f);
        this.mMinStripHeight = (int) ((f * 32.0f) + 0.5f);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        int i3 = this.mScaledTextSpacing;
        this.mScaledTextSpacing = i3 >= i2 ? i3 : i2;
        requestLayout();
        setWillNotDraw(false);
        this.mPrevText.setFocusable(true);
        this.mPrevText.setOnClickListener(new View.OnClickListener() { // from class: androidx.viewpager.widget.PagerTabStrip.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PagerTabStrip.this.mPager.setCurrentItem(r0.getCurrentItem() - 1);
            }
        });
        this.mNextText.setFocusable(true);
        this.mNextText.setOnClickListener(new View.OnClickListener() { // from class: androidx.viewpager.widget.PagerTabStrip.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ViewPager viewPager = PagerTabStrip.this.mPager;
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
        if (getBackground() == null) {
            this.mDrawFullUnderline = true;
        }
    }
}
