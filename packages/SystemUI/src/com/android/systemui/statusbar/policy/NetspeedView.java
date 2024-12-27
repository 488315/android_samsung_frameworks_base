package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.SwitchableDoubleShadowTextView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class NetspeedView extends LinearLayout implements DarkIconDispatcher.DarkReceiver, Observer {
    public int mContentMarginEnd;
    public boolean mContentUpdated;
    public SwitchableDoubleShadowTextView mContentView;
    public final Context mContext;
    public boolean mInStatusBar;
    public IndicatorCutoutUtil mIndicatorCutoutUtil;
    public int mScreenOrientation;
    public final StableWidthHelper mStableWidthHelper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class StableWidthHelper extends LinkedList<Integer> {
        private int mWidthSum = 0;

        public StableWidthHelper() {
        }

        public final int getStableWidth() {
            int size = size();
            if (size > 0) {
                return this.mWidthSum / size;
            }
            return 0;
        }

        public final void reset() {
            clear();
            this.mWidthSum = 0;
        }

        @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
        public final boolean add(Integer num) {
            boolean add = super.add((StableWidthHelper) num);
            if (add) {
                this.mWidthSum = num.intValue() + this.mWidthSum;
                if (size() > 10) {
                    int intValue = get(0).intValue();
                    removeFirst();
                    this.mWidthSum -= intValue;
                }
            }
            return add;
        }
    }

    public NetspeedView(Context context) {
        super(context);
        this.mStableWidthHelper = new StableWidthHelper();
        this.mScreenOrientation = 0;
        this.mContentUpdated = false;
        this.mInStatusBar = false;
        this.mContentMarginEnd = 0;
        this.mContext = context;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mScreenOrientation != configuration.orientation) {
            this.mStableWidthHelper.reset();
            this.mScreenOrientation = configuration.orientation;
        }
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        int tint = DarkIconDispatcher.getTint(arrayList, this, i);
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = this.mContentView;
        if (switchableDoubleShadowTextView != null) {
            switchableDoubleShadowTextView.setTextColor(tint);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = (SwitchableDoubleShadowTextView) findViewById(R.id.network_speed_contentview);
        this.mContentView = switchableDoubleShadowTextView;
        if (BasicRune.STATUS_LAYOUT_SHOW_ICONS_IN_UDC) {
            switchableDoubleShadowTextView.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT && this.mInStatusBar) {
            IndicatorCutoutUtil indicatorCutoutUtil = this.mIndicatorCutoutUtil;
            Rect displayCutoutAreaToExclude = indicatorCutoutUtil != null ? indicatorCutoutUtil.getDisplayCutoutAreaToExclude() : null;
            int i5 = 0;
            if (displayCutoutAreaToExclude != null) {
                Rect rect = new Rect();
                this.mContentView.getGlobalVisibleRect(rect);
                if (rect.left >= 0) {
                    rect.offset(this.mContentMarginEnd, 0);
                    int i6 = rect.right - displayCutoutAreaToExclude.left;
                    if (i6 >= 0) {
                        if (i6 < rect.width() + displayCutoutAreaToExclude.width()) {
                            i5 = i6;
                        }
                    }
                }
            }
            if (i5 != this.mContentMarginEnd) {
                this.mContentMarginEnd = i5;
                this.mStableWidthHelper.reset();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mContentView.getLayoutParams();
                layoutParams.setMarginEnd(i5);
                this.mContentView.setLayoutParams(layoutParams);
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView;
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        if (View.MeasureSpec.getMode(i) == 1073741824 || (switchableDoubleShadowTextView = this.mContentView) == null) {
            return;
        }
        int measuredWidth = switchableDoubleShadowTextView.getMeasuredWidth();
        if (this.mContentUpdated) {
            this.mStableWidthHelper.add(Integer.valueOf(measuredWidth));
            this.mContentUpdated = false;
        }
        int stableWidth = this.mStableWidthHelper.getStableWidth();
        if (stableWidth > measuredWidth) {
            measuredWidth = stableWidth;
        }
        if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
            measuredWidth += this.mContentMarginEnd;
        }
        if (size != measuredWidth) {
            setMeasuredDimension(measuredWidth, View.MeasureSpec.getSize(i2));
        }
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mStableWidthHelper.reset();
    }

    @Override // java.util.Observer
    public final void update(Observable observable, Object obj) {
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = this.mContentView;
        if (switchableDoubleShadowTextView != null) {
            switchableDoubleShadowTextView.setText((String) obj);
            this.mContentUpdated = true;
        }
    }

    public NetspeedView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStableWidthHelper = new StableWidthHelper();
        this.mScreenOrientation = 0;
        this.mContentUpdated = false;
        this.mInStatusBar = false;
        this.mContentMarginEnd = 0;
        this.mContext = context;
    }

    public NetspeedView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStableWidthHelper = new StableWidthHelper();
        this.mScreenOrientation = 0;
        this.mContentUpdated = false;
        this.mInStatusBar = false;
        this.mContentMarginEnd = 0;
        this.mContext = context;
    }
}
