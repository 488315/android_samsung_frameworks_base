package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecQSTileView extends SecQSTileBaseView {
    public final boolean mIsBrightnessView;
    public final boolean mIsLargeView;
    public final boolean mIsNonBGTile;
    public TextView mLabel;
    public ViewGroup mLabelContainer;
    public final int mMaxLabelLines;
    public final SecQSPanelResourcePicker mResourcePicker;
    public ColorStateList mSecLabelColor;
    public ColorStateList mSecSubLabelColor;
    public TextView mSecondLine;
    public int mState;

    public SecQSTileView(Context context, QSIconView qSIconView) {
        this(context, qSIconView, false);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView, com.android.systemui.plugins.qs.QSTileView
    public final int getDetailY() {
        return (this.mLabelContainer.getHeight() / 2) + this.mLabelContainer.getTop() + getTop();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getLabel() {
        return this.mLabel;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getLabelContainer() {
        return this.mLabelContainer;
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView
    public void handleStateChanged(QSTile.State state) {
        String str;
        super.handleStateChanged(state);
        if (!Objects.equals(this.mLabel.getText(), state.label) || this.mState != state.state) {
            this.mLabel.setTextColor(this.mSecLabelColor);
            this.mState = state.state;
            this.mLabel.setText(state.label);
            CharSequence charSequence = state.label;
            if (charSequence != null) {
                StringBuilder sb = new StringBuilder(charSequence.length());
                sb.append(state.label);
                str = sb.toString();
            } else {
                str = null;
            }
            if (str != null) {
                String replaceAll = str.replaceAll("\n", " ").replaceAll("-", "");
                String string = getResources().getString(R.string.quick_settings_label_button);
                String string2 = getResources().getString(R.string.quick_settings_detail_label_content_description);
                String string3 = getResources().getString(R.string.quick_settings_label_content_description);
                String string4 = getResources().getString(R.string.quick_settings_dual_label_content_description);
                StringBuilder sb2 = new StringBuilder(replaceAll.length());
                sb2.append(replaceAll);
                if (this.isDetailViewAvailable) {
                    sb2.append(", " + ((Object) string) + ", " + ((Object) string2));
                } else if (this.mIsLargeView) {
                    sb2.append(", " + ((Object) string) + ", " + ((Object) string4));
                } else {
                    sb2.append(", " + ((Object) string) + ", " + ((Object) string3));
                }
                this.mLabel.setContentDescription(sb2.toString());
            }
        }
        if (!Objects.equals(this.mSecondLine.getText(), state.secondaryLabel)) {
            this.mSecondLine.setText(state.secondaryLabel);
            this.mSecondLine.setTextColor(this.mSecSubLabelColor);
            this.mSecondLine.setVisibility((TextUtils.isEmpty(state.secondaryLabel) || this.mCollapsedView) ? 8 : 0);
        }
        this.mLabel.setEnabled(!state.disabledByPolicy);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView
    public final void init(SecQSTileBaseView$$ExternalSyntheticLambda1 secQSTileBaseView$$ExternalSyntheticLambda1, SecQSTileBaseView$$ExternalSyntheticLambda1 secQSTileBaseView$$ExternalSyntheticLambda12, SecQSTileBaseView$$ExternalSyntheticLambda2 secQSTileBaseView$$ExternalSyntheticLambda2) {
        if (this.mIsLargeView) {
            super.init(secQSTileBaseView$$ExternalSyntheticLambda1, secQSTileBaseView$$ExternalSyntheticLambda12, secQSTileBaseView$$ExternalSyntheticLambda2);
            setOnClickListener(secQSTileBaseView$$ExternalSyntheticLambda12);
        } else {
            setOnClickListener(secQSTileBaseView$$ExternalSyntheticLambda1);
        }
        this.mIconFrame.setBackground(null);
        this.mIcon.setBackground(null);
        setOnLongClickListener(secQSTileBaseView$$ExternalSyntheticLambda2);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        FontSizeUtils.updateFontSize(this.mLabel, R.dimen.sec_style_qs_tile_text_size, 0.8f, 1.25f);
        FontSizeUtils.updateFontSize(this.mSecondLine, R.dimen.sec_style_qs_tile_second_text_size, 0.8f, 1.25f);
        if (this.mIsLargeView) {
            updateLayout();
        }
        if (this.mIsBrightnessView) {
            updateBrightnessTileLayout();
        }
        updateTouchTargetArea();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        boolean z = false;
        this.mLabel.setSingleLine(false);
        super.onMeasure(i, i2);
        String charSequence = this.mLabel.getText().toString();
        if (this.mCollapsedView || this.mIsBrightnessView || (!charSequence.trim().matches("^[\\p{L}]+$") ? this.mLabel.getLineCount() > this.mMaxLabelLines : this.mLabel.getLineCount() > this.mMaxLabelLines - 1) || (!TextUtils.isEmpty(this.mSecondLine.getText()) && this.mLabel.getLineCount() > this.mMaxLabelLines - 1)) {
            z = true;
        }
        if (z) {
            this.mLabel.setSingleLine();
            super.onMeasure(i, i2);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView, com.android.systemui.plugins.qs.QSTileView
    public final void onPanelModeChanged() {
        super.onPanelModeChanged();
        ColorStateList valueOf = ColorStateList.valueOf(((LinearLayout) this).mContext.getColor(R.color.qs_tile_label));
        this.mSecLabelColor = valueOf;
        TextView textView = this.mLabel;
        if (textView != null) {
            textView.setTextColor(valueOf);
        }
        ColorStateList valueOf2 = ColorStateList.valueOf(((LinearLayout) this).mContext.getColor(R.color.qs_tile_sub_label));
        this.mSecSubLabelColor = valueOf2;
        TextView textView2 = this.mSecondLine;
        if (textView2 != null) {
            textView2.setTextColor(valueOf2);
        }
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setShowLabels(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.SecQSTileView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SecQSTileView.this.mLabelContainer.setVisibility(z ? 0 : 8);
            }
        });
    }

    public final void updateBrightnessTileLayout() {
        Resources resources = ((LinearLayout) this).mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.brightness_tile_height);
        resources.getConfiguration().getLayoutDirection();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconFrame.getLayoutParams();
        layoutParams.width = dimensionPixelSize;
        layoutParams.height = dimensionPixelSize;
        this.mIconFrame.setLayoutParams(layoutParams);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setTintList(ColorStateList.valueOf(0));
        shapeDrawable.setIntrinsicHeight(dimensionPixelSize);
        shapeDrawable.setIntrinsicWidth(dimensionPixelSize);
        this.mBg.setImageDrawable(shapeDrawable);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mBg.getLayoutParams();
        layoutParams2.width = dimensionPixelSize;
        layoutParams2.height = dimensionPixelSize;
        this.mBg.setLayoutParams(layoutParams2);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.brightness_tile_icon_size);
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mIcon.getLayoutParams();
        layoutParams3.width = dimensionPixelSize2;
        layoutParams3.height = dimensionPixelSize2;
        this.mIcon.setLayoutParams(layoutParams3);
        this.mRipple.setAlpha(0);
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mLabelContainer.getLayoutParams();
        layoutParams4.setMarginStart(resources.getDimensionPixelSize(R.dimen.brightness_tile_label_start_margin));
        layoutParams4.height = -2;
        layoutParams4.width = -2;
        this.mLabelContainer.setLayoutParams(layoutParams4);
        LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.mLabel.getLayoutParams();
        layoutParams5.height = -2;
        layoutParams5.width = -2;
        this.mLabel.setLayoutParams(layoutParams5);
        this.mLabel.setMaxLines(1);
    }

    public final void updateLayout() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconFrame.getLayoutParams();
        layoutParams.height = -1;
        if (this.mIsLargeView && this.mIsNonBGTile) {
            SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
            Context context = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker.getClass();
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.sec_style_qs_no_bg_tile_icon_size);
            SecQSPanelResourcePicker secQSPanelResourcePicker2 = this.mResourcePicker;
            Context context2 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker2.getClass();
            int dimensionPixelSize2 = context2.getResources().getDimensionPixelSize(R.dimen.large_no_bg_tile_temp_icon_margin_start) + dimensionPixelSize;
            SecQSPanelResourcePicker secQSPanelResourcePicker3 = this.mResourcePicker;
            Context context3 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker3.getClass();
            layoutParams.width = context3.getResources().getDimensionPixelSize(R.dimen.large_no_bg_tile_temp_label_margin_start) + dimensionPixelSize2;
            FrameLayout frameLayout = this.mIconFrame;
            SecQSPanelResourcePicker secQSPanelResourcePicker4 = this.mResourcePicker;
            Context context4 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker4.getClass();
            int dimensionPixelSize3 = context4.getResources().getDimensionPixelSize(R.dimen.large_no_bg_tile_temp_icon_margin_start);
            SecQSPanelResourcePicker secQSPanelResourcePicker5 = this.mResourcePicker;
            Context context5 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker5.getClass();
            frameLayout.setPaddingRelative(dimensionPixelSize3, 0, context5.getResources().getDimensionPixelSize(R.dimen.large_no_bg_tile_temp_label_margin_start), 0);
        } else {
            SecQSPanelResourcePicker secQSPanelResourcePicker6 = this.mResourcePicker;
            Context context6 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker6.getClass();
            int tileIconSize = SecQSPanelResourcePicker.getTileIconSize(context6);
            SecQSPanelResourcePicker secQSPanelResourcePicker7 = this.mResourcePicker;
            Context context7 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker7.getClass();
            int dimensionPixelSize4 = context7.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_icon_margin_start) + tileIconSize;
            SecQSPanelResourcePicker secQSPanelResourcePicker8 = this.mResourcePicker;
            Context context8 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker8.getClass();
            layoutParams.width = context8.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_label_margin_start) + dimensionPixelSize4;
            FrameLayout frameLayout2 = this.mIconFrame;
            SecQSPanelResourcePicker secQSPanelResourcePicker9 = this.mResourcePicker;
            Context context9 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker9.getClass();
            int dimensionPixelSize5 = context9.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_icon_margin_start);
            SecQSPanelResourcePicker secQSPanelResourcePicker10 = this.mResourcePicker;
            Context context10 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker10.getClass();
            frameLayout2.setPaddingRelative(dimensionPixelSize5, 0, context10.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_label_margin_start), 0);
        }
        this.mIconFrame.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLabelContainer.getLayoutParams();
        layoutParams2.height = -1;
        layoutParams2.weight = 1.0f;
        this.mLabelContainer.setLayoutParams(layoutParams2);
        ((LinearLayout) this.mLabelContainer).setGravity(17);
        this.mLabel.setGravity(8388611);
        this.mSecondLine.setGravity(8388611);
    }

    public void updateTouchTargetArea() {
        if (this.mIsLargeView || this.mIsBrightnessView) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconFrame.getLayoutParams();
        layoutParams.width = -1;
        this.mIconFrame.setLayoutParams(layoutParams);
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = ((LinearLayout) this).mContext;
        secQSPanelResourcePicker.getClass();
        setLayoutParams(new LinearLayout.LayoutParams(-1, SecQSPanelResourcePicker.getTileExpandedHeight(context)));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLabelContainer.getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker2 = this.mResourcePicker;
        Context context2 = ((LinearLayout) this).mContext;
        secQSPanelResourcePicker2.getClass();
        layoutParams2.height = SecQSPanelResourcePicker.getLabelHeight(context2);
        this.mLabelContainer.setLayoutParams(layoutParams2);
    }

    public SecQSTileView(Context context, QSIconView qSIconView, boolean z) {
        this(context, qSIconView, z, false, false, false);
    }

    public SecQSTileView(Context context, QSIconView qSIconView, boolean z, boolean z2, boolean z3, boolean z4) {
        super(context, qSIconView, z);
        this.mMaxLabelLines = 2;
        ((LinearLayout) this).mContext = context;
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mResourcePicker = secQSPanelResourcePicker;
        setClipChildren(false);
        setClipToPadding(false);
        setClickable(true);
        setId(View.generateViewId());
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.sec_qs_tile_label, (ViewGroup) this, false);
        this.mLabelContainer = viewGroup;
        viewGroup.setClipChildren(false);
        this.mLabelContainer.setClipToPadding(false);
        TextView textView = (TextView) this.mLabelContainer.findViewById(R.id.tile_label);
        this.mLabel = textView;
        textView.setSelected(true);
        TextView textView2 = this.mLabel;
        textView2.setPaintFlags(textView2.getPaintFlags() | 64 | 128);
        TextView textView3 = (TextView) this.mLabelContainer.findViewById(R.id.app_label);
        this.mSecondLine = textView3;
        textView3.setSelected(true);
        TextView textView4 = this.mSecondLine;
        textView4.setPaintFlags(textView4.getPaintFlags() | 64 | 128);
        FontSizeUtils.updateFontSize(this.mLabel, R.dimen.sec_style_qs_tile_text_size, 0.8f, 1.25f);
        FontSizeUtils.updateFontSize(this.mSecondLine, R.dimen.sec_style_qs_tile_second_text_size, 0.8f, 1.25f);
        addView(this.mLabelContainer);
        setOrientation((z2 || z3) ? 0 : 1);
        this.mIsLargeView = z2;
        this.mIsBrightnessView = z3;
        this.mIsNonBGTile = z4;
        if (z2) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_width), context.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_height));
            layoutParams.weight = 1.0f;
            setLayoutParams(layoutParams);
            setGravity(16);
            setBackground(context.getDrawable(R.drawable.sec_large_button_ripple_background));
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_label_margin_end);
            ViewGroup viewGroup2 = this.mLabelContainer;
            viewGroup2.setPaddingRelative(viewGroup2.getPaddingStart(), this.mLabelContainer.getPaddingTop(), dimensionPixelSize, this.mLabelContainer.getPaddingBottom());
            this.mLabel.setTextAlignment(5);
            this.mLabel.setLayoutDirection(3);
            this.mSecondLine.setTextAlignment(5);
            this.mSecondLine.setLayoutDirection(3);
            updateLayout();
        } else if (z3) {
            Context context2 = ((LinearLayout) this).mContext;
            secQSPanelResourcePicker.getClass();
            ViewGroup.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, SecQSPanelResourcePicker.getBrightnessTileLayoutHeight(context2));
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.brightness_tile_view_padding);
            setPadding(dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
            setLayoutParams(layoutParams2);
            setGravity(16);
            updateBrightnessTileLayout();
            setBackground(context.getDrawable(R.drawable.sec_brightness_tile_view_ripple_background));
        } else {
            setBackground(context.getDrawable(R.drawable.sec_tile_view_ripple_background));
        }
        float[] fArr = new float[8];
        Arrays.fill(fArr, ((LinearLayout) this).mContext.getResources().getInteger(R.integer.sec_style_qs_tile_label_background_radius));
        new ShapeDrawable(new RoundRectShape(fArr, null, null)).getPaint().setColor(-1);
        this.mSecLabelColor = ColorStateList.valueOf(((LinearLayout) this).mContext.getColor(R.color.qs_tile_label));
        this.mSecSubLabelColor = ColorStateList.valueOf(((LinearLayout) this).mContext.getColor(R.color.qs_tile_sub_label));
        if (!z2 && !z3) {
            setPadding(0, 0, 0, 0);
            updateTouchTargetArea();
            setGravity(49);
        }
        if (z2) {
            this.mIcon.setFocusable(true);
            this.mIcon.setImportantForAccessibility(1);
        } else {
            this.mIcon.setFocusable(false);
            this.mIcon.setImportantForAccessibility(2);
        }
        setFocusable(true);
        setImportantForAccessibility(1);
        ViewCompat.setAccessibilityDelegate(this.mLabelContainer, new AccessibilityDelegateCompat(this) { // from class: com.android.systemui.qs.tileimpl.SecQSTileView.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.setSelected(false);
            }
        });
    }
}
