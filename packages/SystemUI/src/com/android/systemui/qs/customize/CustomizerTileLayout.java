package com.android.systemui.qs.customize;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$2;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerInteractionManager$initializeListeners$3;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.util.ShadowDelegateUtil;
import java.util.ArrayList;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class CustomizerTileLayout extends ViewGroup {
    public static final boolean DEBUG = Log.isLoggable("CustomizerTileLayout", 3);
    public final ArrayList mBoundaryBox;
    public int mCellHeight;
    public int mCellWidth;
    public FrameLayout mCircle;
    public QSTileCustomizerInteractionManager$initializeListeners$3 mClickListener;
    public int mColumns;
    public final Context mContext;
    public CustomActionManager mCustomActionManager;
    public final ArrayList mCustomTilesInfo;
    public boolean mIsScrollView;
    public boolean mIsTopEdit;
    public int mMaxRows;
    public final SecQSPanelResourcePicker mResourcePicker;
    public boolean mShowLabel;
    public int mSidePadding;
    public int mTileHorizontalMargin;
    public int mTileVerticalMargin;

    public class QSCustomIconView extends QSIconViewImpl {
        public View mIconRemove;
        public final GradientDrawable mIconStroke;
        public final boolean mIsActive;
        public final boolean mIsTopEdit;
        public final int mRemoveButtonSizePx;
        public final int mRemoveIconSizePx;

        public QSCustomIconView(Context context) {
            super(context);
        }

        public final void addRemoveButton(SecCustomizeTileView secCustomizeTileView, QSTileCustomizerInteractionManager$initializeListeners$3 qSTileCustomizerInteractionManager$initializeListeners$3, CharSequence charSequence) {
            if (this.mIsActive) {
                Drawable drawable = ((ViewGroup) this).mContext.getDrawable(R.drawable.qs_setting_edit_remove_badge);
                FrameLayout frameLayout = new FrameLayout(((ViewGroup) this).mContext);
                ImageView imageView = new ImageView(((ViewGroup) this).mContext);
                imageView.setImageDrawable(ShadowDelegateUtil.INSTANCE.createShadowDrawable(drawable, ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R.dimen.qs_edit_dot_icon_size), 0.3f, this.mRemoveIconSizePx));
                frameLayout.setClipChildren(false);
                frameLayout.setVisibility(0);
                frameLayout.setId(SecQSSettingEditResources.REMOVE_ICON_ID);
                frameLayout.addView(imageView);
                this.mIconRemove = frameLayout;
                frameLayout.setTag(secCustomizeTileView);
                this.mIconRemove.setContentDescription(((Object) charSequence) + ", " + getResources().getString(R.string.qs_edit_remove) + ", " + getResources().getString(R.string.accessibility_button));
                this.mIconRemove.setOnClickListener(qSTileCustomizerInteractionManager$initializeListeners$3);
                addView(this.mIconRemove);
            }
        }

        @Override // com.android.systemui.qs.tileimpl.QSIconViewImpl, android.view.ViewGroup, android.view.View
        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
            getLayoutDirection();
            int measuredWidth = (getMeasuredWidth() - this.mIcon.getMeasuredWidth()) / 2;
            View view = this.mIcon;
            int i5 = this.mRemoveIconSizePx / 3;
            view.layout(measuredWidth, i5, view.getMeasuredWidth() + measuredWidth, view.getMeasuredHeight() + i5);
            View view2 = this.mIconRemove;
            if (view2 == null) {
                return;
            }
            view2.layout(0, 0, view2.getMeasuredWidth(), view2.getMeasuredHeight());
        }

        @Override // com.android.systemui.qs.tileimpl.QSIconViewImpl, android.view.View
        public final void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mIconSizePx, 1073741824);
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mRemoveButtonSizePx, 1073741824);
            int i3 = this.mRemoveIconSizePx;
            int i4 = i3 / (this.mIsTopEdit ? 4 : 3);
            this.mIcon.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            setMeasuredDimension((i4 * 2) + size, this.mIcon.getMeasuredHeight() + (i3 / 3));
            View view = this.mIconRemove;
            if (view != null) {
                view.measure(iMakeMeasureSpec2, iMakeMeasureSpec2);
            }
        }

        @Override // com.android.systemui.qs.tileimpl.QSIconViewImpl
        public final void setIcon(ImageView imageView, QSTile.State state, boolean z) {
            Supplier<QSTile.Icon> supplier = state.iconSupplier;
            QSTile.Icon icon = supplier != null ? supplier.get() : state.icon;
            Drawable drawable = icon != null ? imageView.isShown() ? icon.getDrawable(((ViewGroup) this).mContext) : icon.getInvisibleDrawable(((ViewGroup) this).mContext) : null;
            if (drawable instanceof ScalingDrawableWrapper) {
                imageView.setScaleType(ImageView.ScaleType.CENTER);
            }
            imageView.setImageDrawable(drawable);
            imageView.setColorFilter(this.mIsActive ? getColor(state) : getContext().getColor(R.color.qs_edit_tile_icon_available), PorterDuff.Mode.SRC_IN);
            if (this.mIsActive) {
                return;
            }
            imageView.setBackground(this.mIconStroke);
        }

        public QSCustomIconView(Context context, boolean z, boolean z2) throws Resources.NotFoundException {
            super(context);
            this.mIsTopEdit = z2;
            this.mIconSizePx = ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(R.dimen.qs_edit_tile_icon_size);
            int dimensionPixelSize = ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(z2 ? R.dimen.qs_edit_top_active_remove_button_size : R.dimen.qs_edit_active_remove_button_size);
            this.mRemoveButtonSizePx = dimensionPixelSize;
            this.mRemoveIconSizePx = dimensionPixelSize;
            this.mIsActive = z;
            GradientDrawable gradientDrawable = new GradientDrawable();
            this.mIconStroke = gradientDrawable;
            gradientDrawable.setShape(1);
            gradientDrawable.setColor((ColorStateList) null);
        }
    }

    public CustomizerTileLayout(Context context) {
        this(context, null);
    }

    public final void addBackgroundBox(int i, int i2, QSTileCustomizerInteractionManager$initializeListeners$2 qSTileCustomizerInteractionManager$initializeListeners$2) {
        Log.d("CustomizerTileLayout", "addBackgroundBox listener = " + qSTileCustomizerInteractionManager$initializeListeners$2 + "row = " + i + "col = " + i2);
        int i3 = i * i2;
        for (int i4 = 0; i4 < i3; i4++) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.mContext).inflate(R.layout.qs_customize_boundary_panel, (ViewGroup) this, false);
            frameLayout.setTag(Integer.valueOf(i4));
            frameLayout.setOnDragListener(qSTileCustomizerInteractionManager$initializeListeners$2);
            addView(frameLayout);
            this.mBoundaryBox.add(frameLayout);
        }
        int iWithDefaultDensity = withDefaultDensity(R.dimen.qs_edit_tile_icon_size);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iWithDefaultDensity, iWithDefaultDensity, 1);
        FrameLayout frameLayout2 = (FrameLayout) LayoutInflater.from(this.mContext).inflate(R.layout.qs_customize_circle_panel, (ViewGroup) this, false);
        this.mCircle = frameLayout2;
        frameLayout2.findViewById(R.id.qs_edit_panel_circle).setLayoutParams(layoutParams);
        this.mCircle.setAlpha(0.0f);
        addView(this.mCircle);
    }

    public final void addTile(CustomTileInfo customTileInfo) {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("tile = ");
            sb.append(customTileInfo.spec);
            sb.append("tileInfo.isactive = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, customTileInfo.isActive, "CustomizerTileLayout");
        }
        CustomTileInfo customTileInfo2 = new CustomTileInfo();
        QSTile.State state = customTileInfo.state;
        customTileInfo2.state = state;
        customTileInfo2.spec = customTileInfo.spec;
        state.dualTarget = false;
        customTileInfo2.isActive = customTileInfo.isActive;
        customTileInfo2.longClickListener = customTileInfo.longClickListener;
        customTileInfo2.customizeTileContentDes = customTileInfo.customizeTileContentDes;
        SecCustomizeTileView secCustomizeTileViewCreateCustomizeTileView = createCustomizeTileView(customTileInfo2);
        customTileInfo2.customTileView = secCustomizeTileViewCreateCustomizeTileView;
        secCustomizeTileViewCreateCustomizeTileView.setTag(customTileInfo2);
        this.mCustomTilesInfo.add(customTileInfo2);
        addView(secCustomizeTileViewCreateCustomizeTileView);
    }

    public final SecCustomizeTileView createCustomizeTileView(CustomTileInfo customTileInfo) {
        Log.d("CustomizerTileLayout", "createCustomizeTileView" + customTileInfo);
        QSCustomIconView qSCustomIconView = new QSCustomIconView(this.mContext, customTileInfo.isActive, this.mIsTopEdit);
        SecCustomizeTileView secCustomizeTileView = new SecCustomizeTileView(this.mContext, qSCustomIconView, customTileInfo.isActive);
        secCustomizeTileView.handleStateChanged(customTileInfo.state);
        secCustomizeTileView.mIcon.setImportantForAccessibility(2);
        secCustomizeTileView.mLabel.setImportantForAccessibility(2);
        secCustomizeTileView.setOnLongClickListener(customTileInfo.longClickListener);
        secCustomizeTileView.setOnClickListener(this.mClickListener);
        secCustomizeTileView.setClickable(false);
        secCustomizeTileView.setContentDescription(customTileInfo.customizeTileContentDes);
        secCustomizeTileView.setScreenReaderFocusable(true);
        secCustomizeTileView.setFocusable(false);
        secCustomizeTileView.mLabelContainer.setVisibility(this.mShowLabel ? 0 : 8);
        CustomActionManager customActionManager = this.mCustomActionManager;
        if (customActionManager != null) {
            CustomActionDelegate customActionDelegate = new CustomActionDelegate(secCustomizeTileView);
            customActionDelegate.mCustomActionManager = customActionManager;
            secCustomizeTileView.setAccessibilityDelegate(customActionDelegate);
        }
        qSCustomIconView.addRemoveButton(secCustomizeTileView, this.mClickListener, customTileInfo.state.label);
        qSCustomIconView.setFocusable(true);
        return secCustomizeTileView;
    }

    public final void dropTile(CustomTileInfo customTileInfo, Boolean bool) {
        Log.d("CustomizerTileLayout", "dropTile tileInfo =  " + customTileInfo.spec);
        int iIndexOf = indexOf(customTileInfo);
        if (iIndexOf < 0) {
            return;
        }
        SecCustomizeTileView secCustomizeTileView = ((CustomTileInfo) this.mCustomTilesInfo.get(iIndexOf)).customTileView;
        Log.d("CustomizerTileLayout", "dropTile tileView =  " + secCustomizeTileView);
        secCustomizeTileView.mLabelContainer.setVisibility(this.mShowLabel ? 0 : 8);
        secCustomizeTileView.setAlpha(1.0f);
        this.mCircle.setAlpha(0.0f);
        if (bool.booleanValue()) {
            secCustomizeTileView.mLabel.setAlpha(1.0f);
        }
        showRemoveIcon(customTileInfo, true);
        requestLayout();
    }

    public final CustomTileInfo getInfo(int i) {
        boolean z = DEBUG;
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "getInfo position = ", "CustomizerTileLayout");
        }
        int size = this.mCustomTilesInfo.size() - 1;
        if (i > size) {
            if (z) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(size, "position is invalid position is  ", "CustomizerTileLayout");
            }
            i = size;
        }
        return (CustomTileInfo) this.mCustomTilesInfo.get(i);
    }

    public final int indexOf(CustomTileInfo customTileInfo) {
        for (int i = 0; i < this.mCustomTilesInfo.size(); i++) {
            CustomTileInfo customTileInfo2 = (CustomTileInfo) this.mCustomTilesInfo.get(i);
            if (customTileInfo.spec.equals(customTileInfo2.spec)) {
                Log.d("CustomizerTileLayout", "diffInfo.spec = " + customTileInfo2.spec + " i = " + i);
                return i;
            }
        }
        Log.d("CustomizerTileLayout", "diffInfo.spec is null");
        return -1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("CustomizerTileLayout", "onAttachedToWindow()");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int width = getWidth();
        boolean z2 = getLayoutDirection() == 1;
        int iMin = Math.min(this.mColumns * this.mMaxRows, this.mCustomTilesInfo.size());
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < iMin) {
            int i9 = this.mColumns;
            if (i7 == i9) {
                i8++;
                i7 -= i9;
            }
            SecCustomizeTileView secCustomizeTileView = ((CustomTileInfo) this.mCustomTilesInfo.get(i6)).customTileView;
            int i10 = z2 ? (this.mColumns - i7) - 1 : i7;
            int i11 = this.mCellWidth;
            int i12 = ((this.mTileHorizontalMargin + i11) * i10) + this.mSidePadding;
            int i13 = this.mCellHeight;
            int i14 = this.mTileVerticalMargin;
            int i15 = ((i13 + i14) * i8) + i14;
            int i16 = i11 + i12;
            if (secCustomizeTileView != null) {
                secCustomizeTileView.setTranslationX(0.0f);
                secCustomizeTileView.setTranslationY(0.0f);
                secCustomizeTileView.layout(i12, i15, i16, this.mCellHeight + i15);
            }
            i6++;
            i7++;
        }
        int size = this.mBoundaryBox.size();
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        while (i17 < size) {
            int i20 = this.mColumns;
            if (i18 == i20) {
                i19++;
                i18 -= i20;
            }
            int i21 = this.mCellWidth;
            int i22 = ((this.mTileHorizontalMargin + i21) * i18) + this.mSidePadding;
            int i23 = this.mCellHeight;
            int i24 = this.mTileVerticalMargin;
            int i25 = ((i23 + i24) * i19) + i24;
            if (z2) {
                int i26 = width - i22;
                i22 = i26 - i21;
                i5 = i26;
            } else {
                i5 = i21 + i22;
            }
            ((FrameLayout) this.mBoundaryBox.get(i17)).layout(i22, i25, i5, this.mCellHeight + i25);
            i17++;
            i18++;
        }
        this.mCircle.layout(0, withDefaultDensity(R.dimen.qs_edit_tile_icon_frame_size) - withDefaultDensity(R.dimen.qs_edit_tile_icon_size), this.mCircle.getMeasuredWidth(), this.mCircle.getMeasuredHeight());
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        int panelWidth = (int) (secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getPanelWidth(this.mContext) * 0.0192f);
        int i3 = this.mCellWidth;
        int i4 = this.mColumns;
        int i5 = ((size - (panelWidth * 2)) - (i3 * i4)) / (i4 + 1);
        this.mTileHorizontalMargin = i5;
        int i6 = this.mCellHeight;
        int i7 = this.mMaxRows;
        int i8 = (size2 - (i6 * i7)) / (i7 + 1);
        this.mTileVerticalMargin = i8;
        if (i8 < 0) {
            this.mTileVerticalMargin = 0;
        }
        this.mSidePadding = i5 + panelWidth;
        int iWithDefaultDensity = withDefaultDensity(R.dimen.qs_edit_tile_icon_size);
        int i9 = this.mCellWidth;
        int iM = AbsActionBarView$$ExternalSyntheticOutline0.m(iWithDefaultDensity, i9, 2, panelWidth);
        if (this.mIsTopEdit && this.mSidePadding < iM) {
            this.mSidePadding = iM;
            int i10 = size - (iM * 2);
            int i11 = this.mColumns;
            this.mTileHorizontalMargin = (i10 - (i9 * i11)) / (i11 - 1);
        }
        int iMin = Math.min(this.mColumns * this.mMaxRows, this.mCustomTilesInfo.size());
        for (int i12 = 0; i12 < iMin; i12++) {
            SecCustomizeTileView secCustomizeTileView = ((CustomTileInfo) this.mCustomTilesInfo.get(i12)).customTileView;
            if (secCustomizeTileView != null && secCustomizeTileView.getVisibility() != 8) {
                secCustomizeTileView.measure(View.MeasureSpec.makeMeasureSpec(this.mCellWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCellHeight, 1073741824));
            }
        }
        int size3 = this.mBoundaryBox.size();
        for (int i13 = 0; i13 < size3; i13++) {
            ((FrameLayout) this.mBoundaryBox.get(i13)).measure(View.MeasureSpec.makeMeasureSpec(this.mCellWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCellHeight, 1073741824));
        }
        this.mCircle.measure(View.MeasureSpec.makeMeasureSpec(this.mCellWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCellHeight, 1073741824));
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        if (DEBUG) {
            Log.d("CustomizerTileLayout", "removeAllViews clear");
        }
        this.mCustomTilesInfo.clear();
        this.mBoundaryBox.clear();
        super.removeAllViews();
    }

    public final void removeTile(final CustomTileInfo customTileInfo, boolean z) {
        final int iIndexOf = indexOf(customTileInfo);
        if (iIndexOf < 0) {
            return;
        }
        int iMin = Math.min(this.mColumns * this.mMaxRows, this.mCustomTilesInfo.size());
        if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iIndexOf, "removeTile index = ", "tile = "), customTileInfo.spec, "CustomizerTileLayout");
        }
        final SecCustomizeTileView secCustomizeTileView = ((CustomTileInfo) this.mCustomTilesInfo.get(iIndexOf)).customTileView;
        if (!z) {
            this.mCustomTilesInfo.remove(iIndexOf);
            removeView(secCustomizeTileView);
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        int i = iIndexOf;
        while (i < iMin - 1) {
            int i2 = i + 1;
            SecCustomizeTileView secCustomizeTileView2 = ((CustomTileInfo) this.mCustomTilesInfo.get(i2)).customTileView;
            animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "x", ((FrameLayout) this.mBoundaryBox.get(i2)).getLeft(), ((FrameLayout) this.mBoundaryBox.get(i)).getLeft()));
            animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "y", ((FrameLayout) this.mBoundaryBox.get(i2)).getTop(), ((FrameLayout) this.mBoundaryBox.get(i)).getTop()));
            i = i2;
        }
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.CustomizerTileLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                CustomizerTileLayout.this.mCustomTilesInfo.remove(iIndexOf);
                CustomizerTileLayout.this.removeView(secCustomizeTileView);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                CustomizerTileLayout.this.mCustomTilesInfo.remove(iIndexOf);
                CustomizerTileLayout.this.removeView(secCustomizeTileView);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) throws Resources.NotFoundException {
                if (CustomizerTileLayout.DEBUG) {
                    Log.d("CustomizerTileLayout", "moveTile onAnimationStart");
                }
                CustomizerTileLayout.this.setCircleTranslation(iIndexOf, customTileInfo.isActive);
                CustomizerTileLayout.this.mCircle.setAlpha(0.0f);
            }
        });
        animatorSet.setDuration(150L);
        animatorSet.start();
    }

    public final void selectTile(CustomTileInfo customTileInfo, boolean z) throws Resources.NotFoundException {
        int iIndexOf = indexOf(customTileInfo);
        if (iIndexOf < 0) {
            return;
        }
        if (iIndexOf >= this.mCustomTilesInfo.size()) {
            iIndexOf = this.mCustomTilesInfo.size() - 1;
        }
        SecCustomizeTileView secCustomizeTileView = ((CustomTileInfo) this.mCustomTilesInfo.get(iIndexOf)).customTileView;
        if (z) {
            secCustomizeTileView.setAlpha(0.0f);
        }
        if (DEBUG) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(iIndexOf, "selectTile position = ", "CustomizerTileLayout");
        }
        if (this.mCircle != null) {
            setCircleTranslation(iIndexOf, customTileInfo.isActive);
            this.mCircle.setAlpha(1.0f);
        }
    }

    public final void setCircleTranslation(int i, boolean z) throws Resources.NotFoundException {
        ImageView imageView = (ImageView) this.mCircle.findViewById(R.id.qs_edit_panel_circle);
        int color = getResources().getColor((z || (getResources().getConfiguration().uiMode & 48) != 32) ? R.color.qs_edit_button_icon_color : R.color.qs_edit_panel_button_divider_color, null);
        if (imageView.getTag() == null || color != ((Integer) imageView.getTag()).intValue()) {
            imageView.setTag(Integer.valueOf(color));
            LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.qs_customizer_shape_circle, null);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.qs_edit_icon_radius));
            gradientDrawable.setStroke(getResources().getDimensionPixelSize(R.dimen.qs_edit_icon_stroke_width), color);
            layerDrawable.setDrawable(0, gradientDrawable);
            imageView.setBackground(layerDrawable);
        }
        this.mCircle.setTranslationX(((FrameLayout) this.mBoundaryBox.get(i)).getLeft());
        this.mCircle.setTranslationY(((FrameLayout) this.mBoundaryBox.get(i)).getTop());
    }

    public final void showRemoveIcon(CustomTileInfo customTileInfo, boolean z) {
        View view;
        int iIndexOf = indexOf(customTileInfo);
        if (iIndexOf >= this.mCustomTilesInfo.size()) {
            iIndexOf = this.mCustomTilesInfo.size() - 1;
        }
        QSCustomIconView qSCustomIconView = (QSCustomIconView) ((CustomTileInfo) this.mCustomTilesInfo.get(iIndexOf)).customTileView.mCustomizeIcon;
        if (qSCustomIconView == null || (view = qSCustomIconView.mIconRemove) == null) {
            return;
        }
        view.setVisibility(z ? 0 : 4);
    }

    public final int withDefaultDensity(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i);
    }

    public CustomizerTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCustomTilesInfo = new ArrayList();
        this.mBoundaryBox = new ArrayList();
        this.mShowLabel = true;
        this.mIsTopEdit = false;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mContext = context;
    }

    public final void addTile(final CustomTileInfo customTileInfo, int i, boolean z) {
        boolean z2 = false;
        int iMin = Math.min(this.mColumns * this.mMaxRows, this.mCustomTilesInfo.size());
        int i2 = i;
        if (i2 > iMin) {
            i2 = iMin;
        }
        if (DEBUG) {
            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i2, iMin, "addTile position = ", "total = ", "idx = ");
            sbM.append(i2);
            sbM.append(" spec = ");
            sbM.append(customTileInfo.spec);
            sbM.append("withAnimation");
            sbM.append(z);
            Log.d("CustomizerTileLayout", sbM.toString());
        }
        final CustomTileInfo customTileInfo2 = new CustomTileInfo();
        QSTile.State state = customTileInfo.state;
        customTileInfo2.state = state;
        customTileInfo2.isActive = customTileInfo.isActive;
        state.dualTarget = false;
        customTileInfo2.spec = customTileInfo.spec;
        customTileInfo2.longClickListener = customTileInfo.longClickListener;
        customTileInfo2.customizeTileContentDes = customTileInfo.customizeTileContentDes;
        final SecCustomizeTileView secCustomizeTileViewCreateCustomizeTileView = createCustomizeTileView(customTileInfo2);
        customTileInfo2.customTileView = secCustomizeTileViewCreateCustomizeTileView;
        secCustomizeTileViewCreateCustomizeTileView.setTag(customTileInfo2);
        if (z) {
            AnimatorSet animatorSet = new AnimatorSet();
            int i3 = i2;
            while (i3 < iMin) {
                SecCustomizeTileView secCustomizeTileView = ((CustomTileInfo) this.mCustomTilesInfo.get(i3)).customTileView;
                Animator[] animatorArr = new Animator[1];
                float left = ((FrameLayout) this.mBoundaryBox.get(i3)).getLeft();
                int i4 = i3 + 1;
                float left2 = ((FrameLayout) this.mBoundaryBox.get(i4)).getLeft();
                boolean z3 = z2;
                float[] fArr = new float[2];
                fArr[z3 ? 1 : 0] = left;
                fArr[1] = left2;
                animatorArr[z3 ? 1 : 0] = ObjectAnimator.ofFloat(secCustomizeTileView, "x", fArr);
                animatorSet.playTogether(animatorArr);
                Animator[] animatorArr2 = new Animator[1];
                float top = ((FrameLayout) this.mBoundaryBox.get(i3)).getTop();
                float top2 = ((FrameLayout) this.mBoundaryBox.get(i4)).getTop();
                float[] fArr2 = new float[2];
                fArr2[z3 ? 1 : 0] = top;
                fArr2[1] = top2;
                animatorArr2[z3 ? 1 : 0] = ObjectAnimator.ofFloat(secCustomizeTileView, "y", fArr2);
                animatorSet.playTogether(animatorArr2);
                i3 = i4;
                z2 = z3 ? 1 : 0;
            }
            final int i5 = i2;
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.CustomizerTileLayout.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    CustomizerTileLayout.this.addView(secCustomizeTileViewCreateCustomizeTileView);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CustomizerTileLayout.this.addView(secCustomizeTileViewCreateCustomizeTileView);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) throws Resources.NotFoundException {
                    if (CustomizerTileLayout.DEBUG) {
                        Log.d("CustomizerTileLayout", "moveTile onAnimationStart");
                    }
                    secCustomizeTileViewCreateCustomizeTileView.setAlpha(0.0f);
                    CustomizerTileLayout.this.mCustomTilesInfo.add(i5, customTileInfo2);
                    CustomizerTileLayout.this.setCircleTranslation(i5, customTileInfo.isActive);
                    CustomizerTileLayout.this.mCircle.setAlpha(1.0f);
                }
            });
            animatorSet.setDuration(150L);
            animatorSet.start();
            return;
        }
        this.mCustomTilesInfo.add(i2, customTileInfo2);
        addView(secCustomizeTileViewCreateCustomizeTileView);
    }
}
