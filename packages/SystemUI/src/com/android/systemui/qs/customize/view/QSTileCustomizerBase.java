package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.CustomizerTileViewPager;
import com.android.systemui.qs.customize.SecQSSettingEditResources;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;

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
    public int mCutoutBottomMargin;
    public int mCutoutTopMargin;
    public TextView mEditButtonSummary;
    public SecQSSettingEditResources mEditResources;
    public boolean mIsDragging;
    public boolean mIsMultiTouch;
    public boolean mIsTopEdit;
    public int mMinNum;
    public final SecQSPanelResourcePicker mResourcePicker;
    public Toast mToast;

    public QSTileCustomizerBase(Context context, int i, int i2) {
        super(context);
        this.mIsDragging = false;
        this.mIsMultiTouch = false;
        this.mActiveRows = 3;
        this.mActiveColumns = 4;
        this.mAvailableRows = 2;
        this.mAvailableColumns = 4;
        this.mActiveShowLabel = true;
        this.mIsTopEdit = false;
        this.mCutoutTopMargin = 0;
        this.mCutoutBottomMargin = 0;
        DeviceState.getDisplayHeight(context);
        DeviceState.getDisplayWidth(context);
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mCutoutTopMargin = i;
        this.mCutoutBottomMargin = i2;
        this.mContext = context;
        if (QpRune.QUICK_POP_OVER_CUSTOMIZER && isLargeScreen()) {
            LayoutInflater.from(getContext()).inflate(R.layout.qs_pop_over_customize_tile_edit_layout, this);
        } else {
            LayoutInflater.from(getContext()).inflate(R.layout.qs_customize_tile_edit_layout, this);
        }
        this.mAvailableTileLayout = (CustomizerTileViewPager) findViewById(R.id.qs_customizer_available_pager);
        this.mActiveTileLayout = (CustomizerTileViewPager) findViewById(R.id.qs_customizer_active_pager);
        if (isLargeScreen()) {
            setClipChildren(true);
        }
        setVisibility(8);
        bringToFront();
    }

    public static boolean isLargeScreen() {
        return ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
    }

    public void calculateAvailableArea() throws Resources.NotFoundException {
        if (!isLargeScreen()) {
            int displayHeight = DeviceState.getDisplayHeight(this.mContext);
            int dateButtonContainerTopMargin = this.mResourcePicker.resourcePickHelper.getTargetPicker().getDateButtonContainerTopMargin(this.mContext);
            View viewFindViewById = findViewById(R.id.qs_customize_top_summary_buttons);
            int height = viewFindViewById != null ? viewFindViewById.getHeight() : 0;
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
            int dimensionPixelSize2 = (displayHeight - (((((dateButtonContainerTopMargin + height) + dimensionPixelSize) + getResources().getDimensionPixelSize(R.dimen.qs_edit_available_active_between_margin)) + getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height)) + this.mResourcePicker.getNavBarHeight(this.mContext))) / (getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height) + dimensionPixelSize);
            if (!this.mIsTopEdit || this.mAvailableRows <= dimensionPixelSize2) {
                return;
            }
            this.mAvailableRows = dimensionPixelSize2;
            return;
        }
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_label_height) + getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_frame_size);
        int displayHeight2 = DeviceState.getDisplayHeight(this.mContext);
        int navBarHeight = this.mResourcePicker.getNavBarHeight(this.mContext);
        int dateButtonContainerTopMargin2 = this.mResourcePicker.resourcePickHelper.getTargetPicker().getDateButtonContainerTopMargin(this.mContext);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById(R.id.tile_edit_layout).getLayoutParams();
        int i = displayHeight2 - navBarHeight;
        if (i > getResources().getDimensionPixelSize(R.dimen.qs_edit_tablet_height)) {
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.qs_edit_tablet_height);
        } else {
            layoutParams.height = -1;
        }
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(R.dimen.qs_edit_indicator_vertical_margin_tablet);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) ((LinearLayout) findViewById(R.id.qs_available_paged_indicator_container)).getLayoutParams();
        layoutParams2.topMargin = dimensionPixelSize4;
        layoutParams2.bottomMargin = dimensionPixelSize4;
        int dimensionPixelSize5 = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_active_between_margin_tablet);
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) ((LinearLayout) findViewById(R.id.qs_edit_summary_container)).getLayoutParams();
        layoutParams3.topMargin = dimensionPixelSize5;
        layoutParams3.bottomMargin = dimensionPixelSize5;
        int dimensionPixelSize6 = getResources().getDimensionPixelSize(R.dimen.qs_edit_available_text_height) + getResources().getDimensionPixelSize(R.dimen.qs_edit_summary_text) + getResources().getDimensionPixelSize(R.dimen.qs_edit_buttons_top_margin) + (dimensionPixelSize4 * 2) + (dimensionPixelSize5 * 2) + getResources().getDimensionPixelSize(R.dimen.qs_edit_buttons_height);
        if (this.mIsTopEdit) {
            dimensionPixelSize6 += getResources().getDimensionPixelSize(R.dimen.qs_edit_summary_text);
        }
        int iMin = ((Math.min(i - dateButtonContainerTopMargin2, getResources().getDimensionPixelSize(R.dimen.qs_edit_tablet_height)) - dimensionPixelSize6) / dimensionPixelSize3) - 1;
        if (!this.mIsTopEdit) {
            this.mAvailableRows = 2;
            if (iMin < 2) {
                findViewById(R.id.qs_edit_summary).setVisibility(8);
                this.mAvailableRows = 1;
                return;
            }
            return;
        }
        this.mAvailableRows = 4;
        if (iMin >= 4) {
            iMin = 4;
        }
        if (iMin > 1) {
            this.mAvailableRows = iMin;
            return;
        }
        findViewById(R.id.qs_edit_summary).setVisibility(8);
        findViewById(R.id.qs_edit_more_summary).setVisibility(8);
        this.mAvailableRows = 1;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) throws Resources.NotFoundException {
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            int safeInsetTop = displayCutout.getSafeInsetTop() - displayCutout.getSafeInsetBottom();
            if (safeInsetTop < 0) {
                safeInsetTop = this.mContext.getResources().getDimensionPixelSize(17105896);
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
    public final void onAttachedToWindow() throws Resources.NotFoundException {
        super.onAttachedToWindow();
        calculateAvailableArea();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        calculateAvailableArea();
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
    public final void onMeasure(int i, int i2) throws Resources.NotFoundException {
        if (!QpRune.QUICK_POP_OVER_CUSTOMIZER || !isLargeScreen()) {
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
        }
        updateResources();
        super.onMeasure(i, i2);
    }

    public final void updateResources() throws Resources.NotFoundException {
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
        ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldWide();
        TextView textView = (TextView) findViewById(R.id.qs_edit_summary);
        this.mEditButtonSummary = textView;
        if (textView != null) {
            textView.setText(R.string.qp_edit_button_summary);
            this.mEditButtonSummary.setTextColor(this.mContext.getResources().getColor(R.color.qs_edit_panel_summary_color));
        }
        calculateAvailableArea();
    }
}
