package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.qs.customize.CustomizerTileViewPager;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class QSTileCustomizerBase extends LinearLayout {
    public int mActiveColumns;
    public int mActiveRows;
    public boolean mActiveShowLabel;
    public final CustomizerTileViewPager mActiveTileLayout;
    public int mAvailableColumns;
    public int mAvailableRows;
    public final CustomizerTileViewPager mAvailableTileLayout;
    public final Context mContext;
    public int mCutOutHeight;
    public TextView mEditButtonSummary;
    public SecQSSettingEditResources mEditResources;
    public boolean mIsDragging;
    public boolean mIsMultiTouch;
    public boolean mIsTopEdit;
    public int mMinNum;
    public Toast mToast;

    public QSTileCustomizerBase(Context context) {
        super(context);
        this.mIsDragging = false;
        this.mIsMultiTouch = false;
        this.mActiveRows = 3;
        this.mActiveColumns = 4;
        this.mAvailableRows = 2;
        this.mAvailableColumns = 4;
        this.mActiveShowLabel = true;
        this.mIsTopEdit = false;
        DeviceState.getDisplayHeight(context);
        DeviceState.getDisplayWidth(context);
        this.mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.qs_customize_tile_edit_layout, this);
        this.mAvailableTileLayout = (CustomizerTileViewPager) findViewById(R.id.qs_customizer_available_pager);
        this.mActiveTileLayout = (CustomizerTileViewPager) findViewById(R.id.qs_customizer_active_pager);
        setVisibility(8);
        bringToFront();
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            int safeInsetTop = displayCutout.getSafeInsetTop() - displayCutout.getSafeInsetBottom();
            if (safeInsetTop < 0) {
                safeInsetTop = this.mContext.getResources().getDimensionPixelSize(17105794);
            }
            if (this.mCutOutHeight != safeInsetTop) {
                this.mCutOutHeight = safeInsetTop;
                updateResources();
            }
        } else if (this.mCutOutHeight != 0) {
            this.mCutOutHeight = 0;
            updateResources();
        }
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            return true;
        }
        if (motionEvent.getAction() == 2) {
            if (this.mIsDragging) {
                this.mIsMultiTouch = true;
            } else {
                this.mIsMultiTouch = false;
            }
            CustomizerTileViewPager customizerTileViewPager = this.mAvailableTileLayout;
            boolean z = this.mIsMultiTouch;
            customizerTileViewPager.mIsMultiTouch = z;
            this.mActiveTileLayout.mIsMultiTouch = z;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        if (this.mIsTopEdit) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) requireViewById(R.id.qs_active_page_content).getLayoutParams();
            layoutParams.height = requireViewById(R.id.qs_customizer_active_pager).getHeight();
            requireViewById(R.id.qs_active_page_content).setLayoutParams(layoutParams);
        } else {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_customize_full_active_top_margin);
            int height = (requireViewById(R.id.customize_container).getHeight() - dimensionPixelSize) - requireViewById(R.id.qs_available_area).getHeight();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) requireViewById(R.id.qs_active_page_content).getLayoutParams();
            layoutParams2.topMargin = dimensionPixelSize;
            layoutParams2.height = height;
            requireViewById(R.id.qs_active_page_content).setLayoutParams(layoutParams2);
        }
        updateResources();
        super.onMeasure(i, i2);
    }

    public final void updateResources() {
        CustomizerTileViewPager customizerTileViewPager = this.mActiveTileLayout;
        int i = this.mActiveRows;
        int i2 = this.mActiveColumns;
        customizerTileViewPager.mRows = i;
        customizerTileViewPager.mColumns = i2;
        customizerTileViewPager.mShowLabel = this.mActiveShowLabel;
        customizerTileViewPager.updateResources();
        CustomizerTileViewPager customizerTileViewPager2 = this.mAvailableTileLayout;
        int i3 = this.mAvailableRows;
        int i4 = this.mAvailableColumns;
        customizerTileViewPager2.mRows = i3;
        customizerTileViewPager2.mColumns = i4;
        customizerTileViewPager2.updateResources();
        TextView textView = (TextView) findViewById(R.id.qs_edit_summary);
        this.mEditButtonSummary = textView;
        if (textView != null) {
            textView.setText(R.string.qp_edit_button_summary);
            this.mEditButtonSummary.setTextColor(this.mContext.getResources().getColor(R.color.qs_edit_panel_summary_color));
        }
    }
}
