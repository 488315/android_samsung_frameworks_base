package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.internal.FooterButtonPartnerConfig;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.internal.FooterBarMixinMetrics;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FooterBarMixin implements Mixin {
    final boolean applyDynamicColor;
    final boolean applyPartnerResources;
    public LinearLayout buttonContainer;
    public final Context context;
    int defaultPadding;
    public int footerBarPaddingBottom;
    int footerBarPaddingEnd;
    int footerBarPaddingStart;
    public int footerBarPaddingTop;
    final boolean footerButtonAlignEnd;
    public final ViewStub footerStub;
    public boolean isSecondaryButtonInPrimaryStyle;
    public final FooterBarMixinMetrics metrics;
    public FooterButton primaryButton;
    public int primaryButtonId;
    public FooterButtonPartnerConfig primaryButtonPartnerConfigForTesting;
    public final boolean removeFooterBarWhenEmpty = true;
    public FooterButton secondaryButton;
    public int secondaryButtonId;
    public FooterButtonPartnerConfig secondaryButtonPartnerConfigForTesting;
    final boolean useFullDynamicColor;

    static {
        new AtomicInteger(1);
    }

    public FooterBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        XmlResourceParser xml;
        this.isSecondaryButtonInPrimaryStyle = false;
        FooterBarMixinMetrics footerBarMixinMetrics = new FooterBarMixinMetrics();
        this.metrics = footerBarMixinMetrics;
        Context context = templateLayout.getContext();
        this.context = context;
        this.footerStub = (ViewStub) templateLayout.findManagedViewById(R.id.suc_layout_footer);
        FooterButtonStyleUtils.defaultTextColor.clear();
        boolean z = templateLayout instanceof PartnerCustomizationLayout;
        this.applyPartnerResources = z && ((PartnerCustomizationLayout) templateLayout).shouldApplyPartnerResource();
        this.applyDynamicColor = z && ((PartnerCustomizationLayout) templateLayout).shouldApplyDynamicColor();
        this.useFullDynamicColor = z && ((PartnerCustomizationLayout) templateLayout).useFullDynamicColor();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SucFooterBarMixin, i, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(12, 0);
        this.defaultPadding = dimensionPixelSize;
        this.footerBarPaddingTop = obtainStyledAttributes.getDimensionPixelSize(11, dimensionPixelSize);
        this.footerBarPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(8, this.defaultPadding);
        this.footerBarPaddingStart = obtainStyledAttributes.getDimensionPixelSize(10, 0);
        this.footerBarPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(9, 0);
        int color = obtainStyledAttributes.getColor(13, 0);
        int color2 = obtainStyledAttributes.getColor(15, 0);
        this.footerButtonAlignEnd = obtainStyledAttributes.getBoolean(0, false);
        int resourceId = obtainStyledAttributes.getResourceId(14, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(16, 0);
        obtainStyledAttributes.recycle();
        FooterButtonInflater footerButtonInflater = new FooterButtonInflater(context);
        Context context2 = footerButtonInflater.context;
        if (resourceId2 != 0) {
            xml = context2.getResources().getXml(resourceId2);
            try {
                FooterButton inflate = footerButtonInflater.inflate(xml);
                xml.close();
                Preconditions.ensureOnMainThread("setSecondaryButton");
                this.isSecondaryButtonInPrimaryStyle = false;
                ensureFooterInflated();
                FooterButtonPartnerConfig.Builder builder = new FooterButtonPartnerConfig.Builder(inflate);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_BG_COLOR;
                builder.partnerTheme = getPartnerTheme(inflate, 2132017891, partnerConfig);
                builder.buttonBackgroundConfig = partnerConfig;
                builder.buttonDisableAlphaConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA;
                builder.buttonDisableBackgroundConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR;
                builder.buttonDisableTextColorConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_DISABLED_TEXT_COLOR;
                builder.buttonIconConfig = getDrawablePartnerConfig(inflate.buttonType);
                builder.buttonRadiusConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_RADIUS;
                builder.buttonRippleColorAlphaConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_RIPPLE_COLOR_ALPHA;
                builder.buttonTextColorConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_TEXT_COLOR;
                builder.buttonMarginStartConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_MARGIN_START;
                builder.buttonTextSizeConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_SIZE;
                builder.buttonMinHeight = PartnerConfig.CONFIG_FOOTER_BUTTON_MIN_HEIGHT;
                builder.buttonTextTypeFaceConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_FAMILY;
                builder.buttonTextStyleConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_STYLE;
                FooterButtonPartnerConfig build = builder.build();
                FooterActionButton inflateButton = inflateButton(inflate, build);
                this.secondaryButtonId = inflateButton.getId();
                inflateButton.isPrimaryButtonStyle = false;
                this.secondaryButton = inflate;
                this.secondaryButtonPartnerConfigForTesting = build;
                onFooterButtonInflated(inflateButton, color2);
                onFooterButtonApplyPartnerResource(inflateButton, build);
                repopulateButtons();
                footerBarMixinMetrics.primaryButtonVisibility = footerBarMixinMetrics.primaryButtonVisibility.equals("Unknown") ? "VisibleUsingXml" : footerBarMixinMetrics.primaryButtonVisibility;
            } finally {
            }
        }
        if (resourceId != 0) {
            xml = context2.getResources().getXml(resourceId);
            try {
                FooterButton inflate2 = footerButtonInflater.inflate(xml);
                xml.close();
                Preconditions.ensureOnMainThread("setPrimaryButton");
                ensureFooterInflated();
                FooterButtonPartnerConfig.Builder builder2 = new FooterButtonPartnerConfig.Builder(inflate2);
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_BG_COLOR;
                builder2.partnerTheme = getPartnerTheme(inflate2, 2132017890, partnerConfig2);
                builder2.buttonBackgroundConfig = partnerConfig2;
                builder2.buttonDisableAlphaConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA;
                builder2.buttonDisableBackgroundConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR;
                builder2.buttonDisableTextColorConfig = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR;
                builder2.buttonIconConfig = getDrawablePartnerConfig(inflate2.buttonType);
                builder2.buttonRadiusConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_RADIUS;
                builder2.buttonRippleColorAlphaConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_RIPPLE_COLOR_ALPHA;
                builder2.buttonTextColorConfig = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR;
                builder2.buttonMarginStartConfig = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_MARGIN_START;
                builder2.buttonTextSizeConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_SIZE;
                builder2.buttonMinHeight = PartnerConfig.CONFIG_FOOTER_BUTTON_MIN_HEIGHT;
                builder2.buttonTextTypeFaceConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_FAMILY;
                builder2.buttonTextStyleConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_STYLE;
                FooterButtonPartnerConfig build2 = builder2.build();
                FooterActionButton inflateButton2 = inflateButton(inflate2, build2);
                this.primaryButtonId = inflateButton2.getId();
                inflateButton2.isPrimaryButtonStyle = true;
                this.primaryButton = inflate2;
                this.primaryButtonPartnerConfigForTesting = build2;
                onFooterButtonInflated(inflateButton2, color);
                onFooterButtonApplyPartnerResource(inflateButton2, build2);
                repopulateButtons();
                footerBarMixinMetrics.secondaryButtonVisibility = footerBarMixinMetrics.secondaryButtonVisibility.equals("Unknown") ? "VisibleUsingXml" : footerBarMixinMetrics.secondaryButtonVisibility;
            } finally {
            }
        }
    }

    public static PartnerConfig getDrawablePartnerConfig(int i) {
        switch (i) {
            case 1:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_ADD_ANOTHER;
            case 2:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CANCEL;
            case 3:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CLEAR;
            case 4:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_DONE;
            case 5:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_NEXT;
            case 6:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_OPT_IN;
            case 7:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_SKIP;
            case 8:
                return PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_STOP;
            default:
                return null;
        }
    }

    public final void addSpace() {
        LinearLayout ensureFooterInflated = ensureFooterInflated();
        View view = new View(this.context);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1.0f));
        view.setVisibility(4);
        ensureFooterInflated.addView(view);
    }

    public final LinearLayout ensureFooterInflated() {
        int dimension;
        if (this.buttonContainer == null) {
            ViewStub viewStub = this.footerStub;
            if (viewStub == null) {
                throw new IllegalStateException("Footer stub is not found in this template");
            }
            Context context = this.context;
            viewStub.setLayoutInflater(LayoutInflater.from(new ContextThemeWrapper(context, 2132017893)));
            viewStub.setLayoutResource(R.layout.suc_footer_button_bar);
            LinearLayout linearLayout = (LinearLayout) viewStub.inflate();
            this.buttonContainer = linearLayout;
            if (linearLayout != null) {
                linearLayout.setId(View.generateViewId());
                linearLayout.setPadding(this.footerBarPaddingStart, this.footerBarPaddingTop, this.footerBarPaddingEnd, this.footerBarPaddingBottom);
                if (isFooterButtonAlignedEnd()) {
                    linearLayout.setGravity(8388629);
                }
            }
            LinearLayout linearLayout2 = this.buttonContainer;
            if (linearLayout2 != null && this.applyPartnerResources) {
                if (!this.useFullDynamicColor) {
                    linearLayout2.setBackgroundColor(PartnerConfigHelper.get(context).getColor(context, PartnerConfig.CONFIG_FOOTER_BAR_BG_COLOR));
                }
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_TOP;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    this.footerBarPaddingTop = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_BOTTOM;
                if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                    this.footerBarPaddingBottom = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_START;
                if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
                    this.footerBarPaddingStart = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig3, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_END;
                if (partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)) {
                    this.footerBarPaddingEnd = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig4, 0.0f);
                }
                linearLayout2.setPadding(this.footerBarPaddingStart, this.footerBarPaddingTop, this.footerBarPaddingEnd, this.footerBarPaddingBottom);
                PartnerConfigHelper partnerConfigHelper5 = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig5 = PartnerConfig.CONFIG_FOOTER_BAR_MIN_HEIGHT;
                if (partnerConfigHelper5.isPartnerConfigAvailable(partnerConfig5) && (dimension = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig5, 0.0f)) > 0) {
                    linearLayout2.setMinimumHeight(dimension);
                }
            }
        }
        return this.buttonContainer;
    }

    public int getPaddingBottom() {
        LinearLayout linearLayout = this.buttonContainer;
        return linearLayout != null ? linearLayout.getPaddingBottom() : this.footerStub.getPaddingBottom();
    }

    public int getPaddingTop() {
        LinearLayout linearLayout = this.buttonContainer;
        return linearLayout != null ? linearLayout.getPaddingTop() : this.footerStub.getPaddingTop();
    }

    public final int getPartnerTheme(FooterButton footerButton, int i, PartnerConfig partnerConfig) {
        int i2 = footerButton.theme;
        if (i2 != 0 && !this.applyPartnerResources) {
            i = i2;
        }
        if (!this.applyPartnerResources) {
            return i;
        }
        Context context = this.context;
        return PartnerConfigHelper.get(context).getColor(context, partnerConfig) == 0 ? 2132017891 : 2132017890;
    }

    public Button getPrimaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.primaryButtonId);
    }

    public Button getSecondaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.secondaryButtonId);
    }

    public int getVisibility() {
        return this.buttonContainer.getVisibility();
    }

    public final FooterActionButton inflateButton(FooterButton footerButton, FooterButtonPartnerConfig footerButtonPartnerConfig) {
        FooterActionButton footerActionButton = (FooterActionButton) LayoutInflater.from(new ContextThemeWrapper(this.context, footerButtonPartnerConfig.partnerTheme)).inflate(R.layout.suc_button, (ViewGroup) null, false);
        footerActionButton.setId(View.generateViewId());
        footerActionButton.setText(footerButton.text);
        footerActionButton.setOnClickListener(footerButton);
        footerActionButton.setVisibility(0);
        footerActionButton.setEnabled(footerButton.enabled);
        footerActionButton.footerButton = footerButton;
        new Object(this, footerActionButton.getId()) { // from class: com.google.android.setupcompat.template.FooterBarMixin.1
        };
        return footerActionButton;
    }

    public final boolean isFooterButtonAlignedEnd() {
        Context context = this.context;
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ALIGNED_END;
        return partnerConfigHelper.isPartnerConfigAvailable(partnerConfig) ? PartnerConfigHelper.get(context).getBoolean(context, partnerConfig, false) : this.footerButtonAlignEnd;
    }

    public boolean isPrimaryButtonVisible() {
        return getPrimaryButtonView() != null && getPrimaryButtonView().getVisibility() == 0;
    }

    public boolean isSecondaryButtonVisible() {
        return getSecondaryButtonView() != null && getSecondaryButtonView().getVisibility() == 0;
    }

    public final void onFooterButtonApplyPartnerResource(FooterActionButton footerActionButton, FooterButtonPartnerConfig footerButtonPartnerConfig) {
        float f;
        GradientDrawable gradientDrawable;
        Drawable drawable;
        if (this.applyPartnerResources) {
            boolean z = this.applyDynamicColor;
            boolean z2 = footerActionButton.getId() == this.primaryButtonId;
            FooterButtonStyleUtils.defaultTextColor.put(Integer.valueOf(footerActionButton.getId()), footerActionButton.getTextColors());
            PartnerConfig partnerConfig = footerButtonPartnerConfig.buttonDisableTextColorConfig;
            PartnerConfig partnerConfig2 = footerButtonPartnerConfig.buttonTextColorConfig;
            Context context = this.context;
            if (!z) {
                if (footerActionButton.isEnabled()) {
                    int color = PartnerConfigHelper.get(context).getColor(context, partnerConfig2);
                    if (color != 0) {
                        footerActionButton.setTextColor(ColorStateList.valueOf(color));
                    }
                } else {
                    FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(context, footerActionButton, partnerConfig);
                }
                int color2 = PartnerConfigHelper.get(context).getColor(context, footerButtonPartnerConfig.buttonBackgroundConfig);
                float fraction = PartnerConfigHelper.get(context).getFraction(context, footerButtonPartnerConfig.buttonDisableAlphaConfig);
                int color3 = PartnerConfigHelper.get(context).getColor(context, footerButtonPartnerConfig.buttonDisableBackgroundConfig);
                int[] iArr = {-16842910};
                int[] iArr2 = new int[0];
                if (color2 != 0) {
                    if (fraction <= 0.0f) {
                        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{android.R.attr.disabledAlpha});
                        float f2 = obtainStyledAttributes.getFloat(0, 0.26f);
                        obtainStyledAttributes.recycle();
                        fraction = f2;
                    }
                    if (color3 == 0) {
                        color3 = color2;
                    }
                    ColorStateList colorStateList = new ColorStateList(new int[][]{iArr, iArr2}, new int[]{Color.argb((int) (fraction * 255.0f), Color.red(color3), Color.green(color3), Color.blue(color3)), color2});
                    footerActionButton.getBackground().mutate().setState(new int[0]);
                    footerActionButton.refreshDrawableState();
                    footerActionButton.setBackgroundTintList(colorStateList);
                }
            }
            int defaultColor = z ? footerActionButton.getTextColors().getDefaultColor() : PartnerConfigHelper.get(context).getColor(context, partnerConfig2);
            float fraction2 = PartnerConfigHelper.get(context).getFraction(context, footerButtonPartnerConfig.buttonRippleColorAlphaConfig);
            Drawable background = footerActionButton.getBackground();
            RippleDrawable rippleDrawable = background instanceof InsetDrawable ? (RippleDrawable) ((InsetDrawable) background).getDrawable() : background instanceof RippleDrawable ? (RippleDrawable) background : null;
            if (rippleDrawable != null) {
                int[] iArr3 = {android.R.attr.state_pressed};
                int[] iArr4 = {android.R.attr.state_focused};
                int argb = Color.argb((int) (fraction2 * 255.0f), Color.red(defaultColor), Color.green(defaultColor), Color.blue(defaultColor));
                rippleDrawable.setColor(new ColorStateList(new int[][]{iArr3, iArr4, StateSet.NOTHING}, new int[]{argb, argb, 0}));
            }
            ViewGroup.LayoutParams layoutParams = footerActionButton.getLayoutParams();
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig3 = footerButtonPartnerConfig.buttonMarginStartConfig;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig3) && (layoutParams instanceof ViewGroup.MarginLayoutParams)) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                f = 0.0f;
                marginLayoutParams.setMargins((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig3, 0.0f), marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            } else {
                f = 0.0f;
            }
            float dimension = PartnerConfigHelper.get(context).getDimension(context, footerButtonPartnerConfig.buttonTextSizeConfig, f);
            if (dimension > f) {
                footerActionButton.setTextSize(0, dimension);
            }
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig4 = footerButtonPartnerConfig.buttonMinHeightConfig;
            if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig4)) {
                float dimension2 = PartnerConfigHelper.get(context).getDimension(context, partnerConfig4, f);
                if (dimension2 > f) {
                    footerActionButton.setMinHeight((int) dimension2);
                }
            }
            String string = PartnerConfigHelper.get(context).getString(context, footerButtonPartnerConfig.buttonTextTypeFaceConfig);
            PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig5 = footerButtonPartnerConfig.buttonTextStyleConfig;
            Typeface create = Typeface.create(string, partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig5) ? PartnerConfigHelper.get(context).getInteger(context, partnerConfig5) : 0);
            if (create != null) {
                footerActionButton.setTypeface(create);
            }
            float dimension3 = PartnerConfigHelper.get(context).getDimension(context, footerButtonPartnerConfig.buttonRadiusConfig, 0.0f);
            Drawable background2 = footerActionButton.getBackground();
            if (background2 instanceof InsetDrawable) {
                gradientDrawable = (GradientDrawable) ((LayerDrawable) ((InsetDrawable) background2).getDrawable()).getDrawable(0);
            } else if (background2 instanceof RippleDrawable) {
                RippleDrawable rippleDrawable2 = (RippleDrawable) background2;
                gradientDrawable = rippleDrawable2.getDrawable(0) instanceof GradientDrawable ? (GradientDrawable) rippleDrawable2.getDrawable(0) : (GradientDrawable) ((InsetDrawable) rippleDrawable2.getDrawable(0)).getDrawable();
            } else {
                gradientDrawable = null;
            }
            if (gradientDrawable != null) {
                gradientDrawable.setCornerRadius(dimension3);
            }
            PartnerConfig partnerConfig6 = footerButtonPartnerConfig.buttonIconConfig;
            Drawable drawable2 = partnerConfig6 != null ? PartnerConfigHelper.get(context).getDrawable(context, partnerConfig6) : null;
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
            }
            if (z2) {
                drawable = drawable2;
                drawable2 = null;
            } else {
                drawable = null;
            }
            footerActionButton.setCompoundDrawablesRelative(drawable2, null, drawable, null);
            if (this.applyDynamicColor) {
                return;
            }
            if (!footerActionButton.isEnabled()) {
                FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(context, footerActionButton, partnerConfig);
                return;
            }
            int color4 = PartnerConfigHelper.get(context).getColor(context, partnerConfig2);
            if (color4 != 0) {
                footerActionButton.setTextColor(ColorStateList.valueOf(color4));
            }
        }
    }

    public final void onFooterButtonInflated(FooterActionButton footerActionButton, int i) {
        if (!this.applyDynamicColor && i != 0) {
            HashMap hashMap = FooterButtonStyleUtils.defaultTextColor;
            footerActionButton.getBackground().mutate().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        }
        this.buttonContainer.addView(footerActionButton);
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        int i2 = 0;
        boolean z = primaryButtonView != null && primaryButtonView.getVisibility() == 0;
        boolean z2 = secondaryButtonView != null && secondaryButtonView.getVisibility() == 0;
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout != null) {
            if (!z && !z2) {
                i2 = this.removeFooterBarWhenEmpty ? 8 : 4;
            }
            linearLayout.setVisibility(i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void repopulateButtons() {
        boolean z;
        LinearLayout.LayoutParams layoutParams;
        LinearLayout.LayoutParams layoutParams2;
        LinearLayout ensureFooterInflated = ensureFooterInflated();
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        ensureFooterInflated.removeAllViews();
        boolean z2 = this.isSecondaryButtonInPrimaryStyle;
        Context context = this.context;
        if (z2) {
            PartnerConfigHelper.get(context);
            if (PartnerConfigHelper.applyNeutralButtonStyleBundle == null) {
                try {
                    PartnerConfigHelper.applyNeutralButtonStyleBundle = context.getContentResolver().call(PartnerConfigHelper.getContentUri(context), PartnerConfigHelper.IS_NEUTRAL_BUTTON_STYLE_ENABLED_METHOD, (String) null, (Bundle) null);
                } catch (IllegalArgumentException | SecurityException unused) {
                    Log.w("PartnerConfigHelper", "Neutral button style supporting status unknown; return as false.");
                    PartnerConfigHelper.applyNeutralButtonStyleBundle = null;
                }
            }
            Bundle bundle = PartnerConfigHelper.applyNeutralButtonStyleBundle;
            if (bundle != null && bundle.getBoolean(PartnerConfigHelper.IS_NEUTRAL_BUTTON_STYLE_ENABLED_METHOD, false)) {
                z = true;
                if ((context.getResources().getConfiguration().orientation == 2) && z && isFooterButtonAlignedEnd()) {
                    addSpace();
                }
                if (secondaryButtonView != null) {
                    if (this.isSecondaryButtonInPrimaryStyle) {
                        ensureFooterInflated.setPadding(ensureFooterInflated.getPaddingRight(), ensureFooterInflated.getPaddingTop(), ensureFooterInflated.getPaddingRight(), ensureFooterInflated.getPaddingBottom());
                    }
                    ensureFooterInflated.addView(secondaryButtonView);
                }
                if (!isFooterButtonAlignedEnd()) {
                    addSpace();
                }
                if (primaryButtonView != null) {
                    ensureFooterInflated.addView(primaryButtonView);
                }
                if (primaryButtonView == null && secondaryButtonView != null && z) {
                    primaryButtonView.measure(0, 0);
                    int measuredWidth = primaryButtonView.getMeasuredWidth();
                    secondaryButtonView.measure(0, 0);
                    int max = Math.max(measuredWidth, secondaryButtonView.getMeasuredWidth());
                    primaryButtonView.getLayoutParams().width = max;
                    secondaryButtonView.getLayoutParams().width = max;
                    return;
                }
                if (primaryButtonView != null && (layoutParams2 = (LinearLayout.LayoutParams) primaryButtonView.getLayoutParams()) != null) {
                    layoutParams2.width = -2;
                    layoutParams2.weight = 0.0f;
                    primaryButtonView.setLayoutParams(layoutParams2);
                }
                if (secondaryButtonView != null || (layoutParams = (LinearLayout.LayoutParams) secondaryButtonView.getLayoutParams()) == null) {
                }
                layoutParams.width = -2;
                layoutParams.weight = 0.0f;
                secondaryButtonView.setLayoutParams(layoutParams);
                return;
            }
        }
        z = false;
        if (context.getResources().getConfiguration().orientation == 2) {
            addSpace();
        }
        if (secondaryButtonView != null) {
        }
        if (!isFooterButtonAlignedEnd()) {
        }
        if (primaryButtonView != null) {
        }
        if (primaryButtonView == null) {
        }
        if (primaryButtonView != null) {
            layoutParams2.width = -2;
            layoutParams2.weight = 0.0f;
            primaryButtonView.setLayoutParams(layoutParams2);
        }
        if (secondaryButtonView != null) {
        }
    }
}
