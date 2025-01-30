package com.android.systemui.p016qs.customize;

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
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import com.android.systemui.p016qs.customize.SecQSCustomizerBase;
import com.android.systemui.p016qs.customize.setting.SecQSSettingEditResources;
import com.android.systemui.p016qs.tileimpl.QSIconViewImpl;
import com.android.systemui.p016qs.tileimpl.SlashImageView;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.util.ShadowDelegateUtil;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class CustomizerTileLayout extends ViewGroup {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList mBoundaryBox;
    public int mCellHeight;
    public int mCellWidth;
    public FrameLayout mCircle;
    public View.OnClickListener mClickListener;
    public int mColumns;
    public final Context mContext;
    public CustomActionManager mCustomActionManager;
    public final ArrayList mCustomTilesInfo;
    public boolean mIsTopEdit;
    public int mMaxRows;
    public final SecQSPanelResourcePicker mResourcePicker;
    public boolean mShowLabel;
    public int mSidePadding;
    public int mTileHorizontalMargin;
    public int mTileVerticalMargin;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class QSCustomIconView extends QSIconViewImpl {
        public View mIconRemove;
        public final GradientDrawable mIconStroke;
        public final boolean mIsActive;
        public final boolean mIsTopEdit;
        public final int mRemoveButtonSizePx;
        public final int mRemoveIconSizePx;
        public final SecQSPanelResourcePicker mResourcePicker;

        public QSCustomIconView(Context context) {
            super(context);
        }

        public final void addRemoveButton(SecCustomizeTileView secCustomizeTileView, View.OnClickListener onClickListener, CharSequence charSequence) {
            if (this.mIsActive) {
                Context context = ((ViewGroup) this).mContext;
                this.mResourcePicker.getClass();
                Drawable drawable = context.getDrawable(SecQSPanelResourcePicker.isNightMode(context) ? R.drawable.qs_setting_edit_remove_badge_dark : R.drawable.qs_setting_edit_remove_badge);
                FrameLayout frameLayout = new FrameLayout(((ViewGroup) this).mContext);
                ImageView imageView = new ImageView(((ViewGroup) this).mContext);
                ShadowDelegateUtil shadowDelegateUtil = ShadowDelegateUtil.INSTANCE;
                SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
                Context context2 = getContext();
                secQSPanelResourcePicker.getClass();
                float dimensionPixelOffset = context2.getResources().getDimensionPixelOffset(R.dimen.qs_edit_dot_icon_size);
                int i = this.mRemoveIconSizePx;
                shadowDelegateUtil.getClass();
                imageView.setImageDrawable(ShadowDelegateUtil.createShadowDrawable(drawable, dimensionPixelOffset, 0.3f, i));
                frameLayout.setClipChildren(false);
                frameLayout.setVisibility(0);
                frameLayout.setId(SecQSSettingEditResources.REMOVE_ICON_ID);
                frameLayout.addView(imageView);
                this.mIconRemove = frameLayout;
                frameLayout.setTag(secCustomizeTileView);
                this.mIconRemove.setContentDescription(((Object) charSequence) + ", " + getResources().getString(R.string.qs_edit_remove) + ", " + getResources().getString(R.string.accessibility_button));
                this.mIconRemove.setOnClickListener(onClickListener);
                addView(this.mIconRemove);
            }
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSIconViewImpl, android.view.ViewGroup, android.view.View
        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
            getLayoutDirection();
            int measuredWidth = (getMeasuredWidth() - this.mIcon.getMeasuredWidth()) / 2;
            SlashImageView slashImageView = this.mIcon;
            int i5 = (this.mRemoveIconSizePx / 3) + 0;
            slashImageView.layout(measuredWidth, i5, slashImageView.getMeasuredWidth() + measuredWidth, slashImageView.getMeasuredHeight() + i5);
            View view = this.mIconRemove;
            if (view == null) {
                return;
            }
            view.layout(0, 0, view.getMeasuredWidth() + 0, view.getMeasuredHeight() + 0);
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSIconViewImpl, android.view.View
        public final void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mIconSizePx, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mRemoveButtonSizePx, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            View.MeasureSpec.makeMeasureSpec(this.mRemoveIconSizePx, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            int i3 = this.mRemoveIconSizePx;
            int i4 = i3 / (this.mIsTopEdit ? 4 : 3);
            this.mIcon.measure(makeMeasureSpec, makeMeasureSpec);
            setMeasuredDimension((i4 * 2) + size, this.mIcon.getMeasuredHeight() + (i3 / 3));
            View view = this.mIconRemove;
            if (view != null) {
                view.measure(makeMeasureSpec2, makeMeasureSpec2);
            }
        }

        @Override // com.android.systemui.p016qs.tileimpl.QSIconViewImpl
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

        public QSCustomIconView(Context context, boolean z, SecQSPanelResourcePicker secQSPanelResourcePicker, boolean z2) {
            super(context);
            this.mIsTopEdit = z2;
            this.mResourcePicker = secQSPanelResourcePicker;
            Context context2 = getContext();
            secQSPanelResourcePicker.getClass();
            this.mIconSizePx = context2.getResources().getDimensionPixelOffset(R.dimen.qs_edit_tile_icon_size);
            int i = z2 ? R.dimen.qs_edit_top_active_remove_button_size : R.dimen.qs_edit_active_remove_button_size;
            Context context3 = getContext();
            secQSPanelResourcePicker.getClass();
            int dimensionPixelOffset = context3.getResources().getDimensionPixelOffset(i);
            this.mRemoveButtonSizePx = dimensionPixelOffset;
            this.mRemoveIconSizePx = dimensionPixelOffset;
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

    public static int exactly(int i) {
        return View.MeasureSpec.makeMeasureSpec(i, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
    }

    public final void addBackgroundBox(int i, int i2, View.OnDragListener onDragListener) {
        Log.d("CustomizerTileLayout", "addBackgroundBox listener = " + onDragListener + "row = " + i + "col = " + i2);
        int i3 = i * i2;
        for (int i4 = 0; i4 < i3; i4++) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.mContext).inflate(R.layout.qs_customize_boundary_panel, (ViewGroup) this, false);
            frameLayout.setTag(Integer.valueOf(i4));
            frameLayout.setOnDragListener(onDragListener);
            addView(frameLayout);
            this.mBoundaryBox.add(frameLayout);
        }
        int withDefaultDensity = withDefaultDensity(R.dimen.qs_edit_tile_icon_size);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(withDefaultDensity, withDefaultDensity, 1);
        FrameLayout frameLayout2 = (FrameLayout) LayoutInflater.from(this.mContext).inflate(R.layout.qs_customize_circle_panel, (ViewGroup) this, false);
        this.mCircle = frameLayout2;
        frameLayout2.findViewById(R.id.qs_edit_panel_circle).setLayoutParams(layoutParams);
        this.mCircle.setAlpha(0.0f);
        addView(this.mCircle);
    }

    public final void addTile(SecQSCustomizerBase.CustomTileInfo customTileInfo) {
        StringBuilder sb = new StringBuilder("tile = ");
        sb.append(customTileInfo.spec);
        sb.append("tileInfo.isactive = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, customTileInfo.isActive, "CustomizerTileLayout");
        if (this.mCustomTilesInfo.stream().anyMatch(new CustomizerTileLayout$$ExternalSyntheticLambda0(customTileInfo))) {
            ExifInterface$$ExternalSyntheticOutline0.m36m(new StringBuilder(), customTileInfo.spec, "is duplicated", "CustomizerTileLayout");
            return;
        }
        SecQSCustomizerBase.CustomTileInfo customTileInfo2 = new SecQSCustomizerBase.CustomTileInfo();
        QSTile.State state = customTileInfo.state;
        customTileInfo2.state = state;
        customTileInfo2.spec = customTileInfo.spec;
        state.dualTarget = false;
        customTileInfo2.isActive = customTileInfo.isActive;
        customTileInfo2.longClickListener = customTileInfo.longClickListener;
        customTileInfo2.customizeTileContentDes = customTileInfo.customizeTileContentDes;
        customTileInfo2.isNewCustomTile = customTileInfo.isNewCustomTile;
        SecCustomizeTileView createCustomizeTileView = createCustomizeTileView(customTileInfo2);
        customTileInfo2.customTileView = createCustomizeTileView;
        createCustomizeTileView.setTag(customTileInfo2);
        this.mCustomTilesInfo.add(customTileInfo2);
        addView(createCustomizeTileView);
    }

    public final SecCustomizeTileView createCustomizeTileView(SecQSCustomizerBase.CustomTileInfo customTileInfo) {
        Log.d("CustomizerTileLayout", "createCustomizeTileView" + customTileInfo);
        QSCustomIconView qSCustomIconView = new QSCustomIconView(this.mContext, customTileInfo.isActive, this.mResourcePicker, this.mIsTopEdit);
        SecCustomizeTileView secCustomizeTileView = new SecCustomizeTileView(this.mContext, qSCustomIconView, customTileInfo.isActive);
        secCustomizeTileView.customTileHandleStateChange(customTileInfo.state);
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

    public final void dropTile(SecQSCustomizerBase.CustomTileInfo customTileInfo, Boolean bool) {
        Log.d("CustomizerTileLayout", "dropTile tileInfo =  " + customTileInfo.spec);
        int indexOf = indexOf(customTileInfo);
        if (indexOf < 0) {
            return;
        }
        SecCustomizeTileView secCustomizeTileView = ((SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(indexOf)).customTileView;
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

    public final SecQSCustomizerBase.CustomTileInfo getInfo(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("getInfo position = ", i, "CustomizerTileLayout");
        int size = this.mCustomTilesInfo.size() - 1;
        if (i > size) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("position is invalid position is  ", size, "CustomizerTileLayout");
            i = size;
        }
        return (SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(i);
    }

    public final int indexOf(SecQSCustomizerBase.CustomTileInfo customTileInfo) {
        for (int i = 0; i < this.mCustomTilesInfo.size(); i++) {
            SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(i);
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
        int min = Math.min(this.mColumns * this.mMaxRows, this.mCustomTilesInfo.size());
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < min) {
            int i9 = this.mColumns;
            if (i7 == i9) {
                i8++;
                i7 -= i9;
            }
            SecCustomizeTileView secCustomizeTileView = ((SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(i6)).customTileView;
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
        Context context = this.mContext;
        secQSPanelResourcePicker.getClass();
        int panelWidth = (int) (SecQSPanelResourcePicker.getPanelWidth(context) * 0.0192f);
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
        this.mSidePadding = panelWidth + i5;
        int min = Math.min(i4 * i7, this.mCustomTilesInfo.size());
        for (int i9 = 0; i9 < min; i9++) {
            SecCustomizeTileView secCustomizeTileView = ((SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(i9)).customTileView;
            if (secCustomizeTileView != null && secCustomizeTileView.getVisibility() != 8) {
                secCustomizeTileView.measure(exactly(this.mCellWidth), exactly(this.mCellHeight));
            }
        }
        int size3 = this.mBoundaryBox.size();
        for (int i10 = 0; i10 < size3; i10++) {
            ((FrameLayout) this.mBoundaryBox.get(i10)).measure(exactly(this.mCellWidth), exactly(this.mCellHeight));
        }
        this.mCircle.measure(exactly(this.mCellWidth), exactly(this.mCellHeight));
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        Log.d("CustomizerTileLayout", "removeAllViews clear");
        this.mCustomTilesInfo.clear();
        this.mBoundaryBox.clear();
        super.removeAllViews();
    }

    public final void removeTile(final SecQSCustomizerBase.CustomTileInfo customTileInfo, boolean z) {
        final int indexOf = indexOf(customTileInfo);
        if (indexOf < 0) {
            return;
        }
        int min = Math.min(this.mColumns * this.mMaxRows, this.mCustomTilesInfo.size());
        ExifInterface$$ExternalSyntheticOutline0.m35m(AbstractC0000x2c234b15.m1m("removeTile index = ", indexOf, "tile = "), customTileInfo.spec, "CustomizerTileLayout");
        final SecCustomizeTileView secCustomizeTileView = ((SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(indexOf)).customTileView;
        if (!z) {
            this.mCustomTilesInfo.remove(indexOf);
            removeView(secCustomizeTileView);
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        int i = indexOf;
        while (i < min - 1) {
            int i2 = i + 1;
            SecCustomizeTileView secCustomizeTileView2 = ((SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(i2)).customTileView;
            animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "x", ((FrameLayout) this.mBoundaryBox.get(i2)).getLeft(), ((FrameLayout) this.mBoundaryBox.get(i)).getLeft()));
            animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView2, "y", ((FrameLayout) this.mBoundaryBox.get(i2)).getTop(), ((FrameLayout) this.mBoundaryBox.get(i)).getTop()));
            i = i2;
        }
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.CustomizerTileLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                CustomizerTileLayout.this.mCustomTilesInfo.remove(indexOf);
                CustomizerTileLayout.this.removeView(secCustomizeTileView);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                CustomizerTileLayout.this.mCustomTilesInfo.remove(indexOf);
                CustomizerTileLayout.this.removeView(secCustomizeTileView);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                Log.d("CustomizerTileLayout", "moveTile onAnimationStart");
                CustomizerTileLayout customizerTileLayout = CustomizerTileLayout.this;
                int i3 = indexOf;
                boolean z2 = customTileInfo.isActive;
                int i4 = CustomizerTileLayout.$r8$clinit;
                customizerTileLayout.setCircleTranslation(i3, z2);
                CustomizerTileLayout.this.mCircle.setAlpha(0.0f);
            }
        });
        animatorSet.setDuration(150L);
        animatorSet.start();
    }

    public final void selectTile(SecQSCustomizerBase.CustomTileInfo customTileInfo, boolean z) {
        int indexOf = indexOf(customTileInfo);
        if (indexOf < 0) {
            return;
        }
        if (indexOf >= this.mCustomTilesInfo.size()) {
            indexOf = this.mCustomTilesInfo.size() - 1;
        }
        SecCustomizeTileView secCustomizeTileView = ((SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(indexOf)).customTileView;
        if (z) {
            secCustomizeTileView.setAlpha(0.0f);
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("selectTile position = ", indexOf, "CustomizerTileLayout");
        if (this.mCircle != null) {
            setCircleTranslation(indexOf, customTileInfo.isActive);
            this.mCircle.setAlpha(1.0f);
        }
    }

    public final void setCircleTranslation(int i, boolean z) {
        int i2;
        int color;
        ImageView imageView = (ImageView) this.mCircle.findViewById(R.id.qs_edit_panel_circle);
        Resources resources = getResources();
        if (!z) {
            if ((getResources().getConfiguration().uiMode & 48) == 32) {
                i2 = R.color.qs_edit_panel_button_divider_color;
                color = resources.getColor(i2, null);
                if (imageView.getTag() != null || color != ((Integer) imageView.getTag()).intValue()) {
                    imageView.setTag(Integer.valueOf(color));
                    LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.sec_qs_edit_button_background, null);
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.qs_edit_icon_radius));
                    gradientDrawable.setStroke(getResources().getDimensionPixelSize(R.dimen.qs_edit_icon_stroke_width), color);
                    layerDrawable.setDrawable(0, gradientDrawable);
                    imageView.setBackground(layerDrawable);
                }
                this.mCircle.setTranslationX(((FrameLayout) this.mBoundaryBox.get(i)).getLeft());
                this.mCircle.setTranslationY(((FrameLayout) this.mBoundaryBox.get(i)).getTop());
            }
        }
        i2 = R.color.qs_edit_button_icon_color;
        color = resources.getColor(i2, null);
        if (imageView.getTag() != null) {
        }
        imageView.setTag(Integer.valueOf(color));
        LayerDrawable layerDrawable2 = (LayerDrawable) getResources().getDrawable(R.drawable.sec_qs_edit_button_background, null);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setCornerRadius(getResources().getDimensionPixelSize(R.dimen.qs_edit_icon_radius));
        gradientDrawable2.setStroke(getResources().getDimensionPixelSize(R.dimen.qs_edit_icon_stroke_width), color);
        layerDrawable2.setDrawable(0, gradientDrawable2);
        imageView.setBackground(layerDrawable2);
        this.mCircle.setTranslationX(((FrameLayout) this.mBoundaryBox.get(i)).getLeft());
        this.mCircle.setTranslationY(((FrameLayout) this.mBoundaryBox.get(i)).getTop());
    }

    public final void showRemoveIcon(SecQSCustomizerBase.CustomTileInfo customTileInfo, boolean z) {
        View view;
        int indexOf = indexOf(customTileInfo);
        if (indexOf >= this.mCustomTilesInfo.size()) {
            indexOf = this.mCustomTilesInfo.size() - 1;
        }
        QSCustomIconView qSCustomIconView = (QSCustomIconView) ((SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(indexOf)).customTileView.mCustomizeIcon;
        if (qSCustomIconView == null || (view = qSCustomIconView.mIconRemove) == null) {
            return;
        }
        view.setVisibility(z ? 0 : 4);
    }

    public final int withDefaultDensity(int i) {
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = this.mContext;
        secQSPanelResourcePicker.getClass();
        return context.getResources().getDimensionPixelOffset(i);
    }

    public CustomizerTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCustomTilesInfo = new ArrayList();
        this.mBoundaryBox = new ArrayList();
        this.mShowLabel = true;
        this.mIsTopEdit = false;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mContext = context;
    }

    public final void addTile(final SecQSCustomizerBase.CustomTileInfo customTileInfo, final int i, boolean z) {
        int min = Math.min(this.mColumns * this.mMaxRows, this.mCustomTilesInfo.size());
        if (i > min) {
            i = min;
        }
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("addTile position = ", i, "total = ", min, "idx = ");
        m45m.append(i);
        m45m.append(" spec = ");
        m45m.append(customTileInfo.spec);
        m45m.append("withAnimation");
        m45m.append(z);
        Log.d("CustomizerTileLayout", m45m.toString());
        if (this.mCustomTilesInfo.stream().anyMatch(new CustomizerTileLayout$$ExternalSyntheticLambda0(customTileInfo))) {
            ExifInterface$$ExternalSyntheticOutline0.m36m(new StringBuilder(), customTileInfo.spec, "is duplicated", "CustomizerTileLayout");
            return;
        }
        final SecQSCustomizerBase.CustomTileInfo customTileInfo2 = new SecQSCustomizerBase.CustomTileInfo();
        QSTile.State state = customTileInfo.state;
        customTileInfo2.state = state;
        customTileInfo2.isActive = customTileInfo.isActive;
        state.dualTarget = false;
        customTileInfo2.spec = customTileInfo.spec;
        customTileInfo2.longClickListener = customTileInfo.longClickListener;
        customTileInfo2.customizeTileContentDes = customTileInfo.customizeTileContentDes;
        if (z) {
            AnimatorSet animatorSet = new AnimatorSet();
            if (i < min) {
                for (int i2 = i; i2 > min - 1; i2++) {
                    SecCustomizeTileView secCustomizeTileView = ((SecQSCustomizerBase.CustomTileInfo) this.mCustomTilesInfo.get(i2)).customTileView;
                    animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView, "x", ((FrameLayout) this.mBoundaryBox.get(i2)).getLeft(), ((FrameLayout) this.mBoundaryBox.get(r8)).getLeft()));
                    animatorSet.playTogether(ObjectAnimator.ofFloat(secCustomizeTileView, "y", ((FrameLayout) this.mBoundaryBox.get(i2)).getTop(), ((FrameLayout) this.mBoundaryBox.get(r8)).getTop()));
                }
            }
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.customize.CustomizerTileLayout.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    CustomizerTileLayout customizerTileLayout = CustomizerTileLayout.this;
                    SecQSCustomizerBase.CustomTileInfo customTileInfo3 = customTileInfo2;
                    int i3 = CustomizerTileLayout.$r8$clinit;
                    SecCustomizeTileView createCustomizeTileView = customizerTileLayout.createCustomizeTileView(customTileInfo3);
                    SecQSCustomizerBase.CustomTileInfo customTileInfo4 = customTileInfo2;
                    customTileInfo4.customTileView = createCustomizeTileView;
                    createCustomizeTileView.setTag(customTileInfo4);
                    CustomizerTileLayout.this.mCustomTilesInfo.add(i, customTileInfo2);
                    CustomizerTileLayout.this.addView(createCustomizeTileView);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CustomizerTileLayout customizerTileLayout = CustomizerTileLayout.this;
                    SecQSCustomizerBase.CustomTileInfo customTileInfo3 = customTileInfo2;
                    int i3 = CustomizerTileLayout.$r8$clinit;
                    SecCustomizeTileView createCustomizeTileView = customizerTileLayout.createCustomizeTileView(customTileInfo3);
                    SecQSCustomizerBase.CustomTileInfo customTileInfo4 = customTileInfo2;
                    customTileInfo4.customTileView = createCustomizeTileView;
                    createCustomizeTileView.setTag(customTileInfo4);
                    CustomizerTileLayout.this.mCustomTilesInfo.add(i, customTileInfo2);
                    CustomizerTileLayout.this.addView(createCustomizeTileView);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    Log.d("CustomizerTileLayout", "moveTile onAnimationStart");
                    CustomizerTileLayout customizerTileLayout = CustomizerTileLayout.this;
                    int i3 = i;
                    boolean z2 = customTileInfo.isActive;
                    int i4 = CustomizerTileLayout.$r8$clinit;
                    customizerTileLayout.setCircleTranslation(i3, z2);
                    CustomizerTileLayout.this.mCircle.setAlpha(1.0f);
                }
            });
            animatorSet.setDuration(150L);
            animatorSet.start();
            return;
        }
        SecCustomizeTileView createCustomizeTileView = createCustomizeTileView(customTileInfo2);
        customTileInfo2.customTileView = createCustomizeTileView;
        createCustomizeTileView.setTag(customTileInfo2);
        this.mCustomTilesInfo.add(i, customTileInfo2);
        addView(createCustomizeTileView);
    }
}
