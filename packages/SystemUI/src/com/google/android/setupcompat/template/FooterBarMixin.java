package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonHelper;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.internal.FooterButtonPartnerConfig;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.internal.FooterBarMixinMetrics;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.RecoilHelper;
import com.google.android.setupcompat.view.ButtonBarLayout;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class FooterBarMixin implements Mixin {
    public static final Logger LOG = new Logger("FooterBarMixin");
    final boolean applyDynamicColor;
    final boolean applyPartnerResources;
    public LinearLayout buttonContainer;
    public final Context context;
    int defaultPadding;
    final int footerBarButtonMiddleSpacing;
    public int footerBarPaddingBottom;
    int footerBarPaddingEnd;
    int footerBarPaddingStart;
    public int footerBarPaddingTop;
    public final int footerBarPrimaryBackgroundColor;
    public final int footerBarPrimaryButtonDisabledTextColor;
    public final int footerBarPrimaryButtonEnabledTextColor;
    final boolean footerButtonAlignEnd;
    public final ViewStub footerStub;
    public String hostFragmentName;
    public String hostFragmentTag;
    public final FooterBarMixinMetrics metrics;
    public FooterButton primaryButton;
    public int primaryButtonId;
    public FooterButtonPartnerConfig primaryButtonPartnerConfigForTesting;
    public final FooterButton secondaryButton;
    public final int secondaryButtonId;
    public FooterButtonPartnerConfig secondaryButtonPartnerConfigForTesting;
    public FooterButtonPartnerConfig tertiaryButtonPartnerConfigForTesting;
    final boolean useFullDynamicColor;

    /* renamed from: com.google.android.setupcompat.template.FooterBarMixin$1, reason: invalid class name */
    public class AnonymousClass1 {
        public final /* synthetic */ int val$id;

        public AnonymousClass1(int i) {
            this.val$id = i;
        }
    }

    public FooterBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        XmlResourceParser xml;
        PartnerConfig partnerConfig;
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
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SucFooterBarMixin, i, 0);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(17, 0);
        this.defaultPadding = dimensionPixelSize;
        this.footerBarPaddingTop = typedArrayObtainStyledAttributes.getDimensionPixelSize(16, dimensionPixelSize);
        this.footerBarPaddingBottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(13, this.defaultPadding);
        this.footerBarPaddingStart = typedArrayObtainStyledAttributes.getDimensionPixelSize(15, 0);
        this.footerBarPaddingEnd = typedArrayObtainStyledAttributes.getDimensionPixelSize(14, 0);
        this.footerBarPrimaryBackgroundColor = typedArrayObtainStyledAttributes.getColor(18, 0);
        int color = typedArrayObtainStyledAttributes.getColor(22, 0);
        this.footerButtonAlignEnd = typedArrayObtainStyledAttributes.getBoolean(0, false);
        this.footerBarPrimaryButtonEnabledTextColor = typedArrayObtainStyledAttributes.getColor(21, 0);
        int color2 = typedArrayObtainStyledAttributes.getColor(25, 0);
        this.footerBarPrimaryButtonDisabledTextColor = typedArrayObtainStyledAttributes.getColor(20, 0);
        int color3 = typedArrayObtainStyledAttributes.getColor(24, 0);
        this.footerBarButtonMiddleSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(8, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(19, 0);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(23, 0);
        typedArrayObtainStyledAttributes.recycle();
        FooterButtonInflater footerButtonInflater = new FooterButtonInflater(context);
        if (resourceId2 != 0) {
            xml = footerButtonInflater.context.getResources().getXml(resourceId2);
            try {
                FooterButton footerButtonInflate = footerButtonInflater.inflate(xml);
                xml.close();
                Preconditions.ensureOnMainThread("setSecondaryButton");
                ensureFooterInflated();
                int i2 = PartnerConfigHelper.isGlifExpressiveEnabled(context) ? R.style.SucGlifMaterialButton_Secondary : R.style.SucPartnerCustomizationButton_Secondary;
                FooterButtonPartnerConfig.Builder builder = new FooterButtonPartnerConfig.Builder(footerButtonInflate);
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_BG_COLOR;
                builder.partnerTheme = getPartnerTheme(footerButtonInflate, i2, partnerConfig2);
                builder.buttonBackgroundConfig = partnerConfig2;
                builder.buttonDisableAlphaConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA;
                builder.buttonDisableBackgroundConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR;
                builder.buttonDisableTextColorConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_DISABLED_TEXT_COLOR;
                switch (footerButtonInflate.buttonType) {
                    case 1:
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_ADD_ANOTHER;
                        break;
                    case 2:
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CANCEL;
                        break;
                    case 3:
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CLEAR;
                        break;
                    case 4:
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_DONE;
                        break;
                    case 5:
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_NEXT;
                        break;
                    case 6:
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_OPT_IN;
                        break;
                    case 7:
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_SKIP;
                        break;
                    case 8:
                        partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_STOP;
                        break;
                    default:
                        partnerConfig = null;
                        break;
                }
                builder.buttonIconConfig = partnerConfig;
                builder.buttonRadiusConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_RADIUS;
                builder.buttonRippleColorAlphaConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_RIPPLE_COLOR_ALPHA;
                builder.buttonTextColorConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_TEXT_COLOR;
                builder.buttonMarginStartConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_MARGIN_START;
                builder.buttonTextSizeConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_SIZE;
                builder.buttonMinHeight = PartnerConfig.CONFIG_FOOTER_BUTTON_MIN_HEIGHT;
                builder.buttonTextTypeFaceConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_FAMILY;
                builder.buttonTextWeightConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_WEIGHT;
                builder.buttonTextStyleConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_STYLE;
                FooterButtonPartnerConfig footerButtonPartnerConfigBuild = builder.build();
                Object objInflateButton = inflateButton(footerButtonInflate, footerButtonPartnerConfigBuild);
                Button button = (Button) objInflateButton;
                this.secondaryButtonId = button.getId();
                if (objInflateButton instanceof MaterialFooterActionButton) {
                    ((MaterialFooterActionButton) objInflateButton).getClass();
                } else if (button instanceof FooterActionButton) {
                    ((FooterActionButton) objInflateButton).isPrimaryButtonStyle = false;
                } else {
                    LOG.e("Set the primary button style error when setting secondary button.");
                }
                this.secondaryButton = footerButtonInflate;
                this.secondaryButtonPartnerConfigForTesting = footerButtonPartnerConfigBuild;
                onFooterButtonInflated(button, color);
                onFooterButtonApplyPartnerResource(button, footerButtonPartnerConfigBuild);
                if (PartnerConfigHelper.isGlifExpressiveEnabled(context)) {
                    boolean z2 = this.secondaryButton.enabled;
                    color2 = z2 ? color2 : color3;
                    if (z2) {
                        if (color2 != 0) {
                            button.setTextColor(ColorStateList.valueOf(color2));
                        }
                    } else if (color2 != 0) {
                        button.setTextColor(ColorStateList.valueOf(color2));
                    }
                }
                repopulateButtons();
                button.post(new FooterBarMixin$$ExternalSyntheticLambda0(this, button, 1));
                RecoilHelper.apply(context, button);
                footerBarMixinMetrics.primaryButtonVisibility = footerBarMixinMetrics.primaryButtonVisibility.equals(C2paManifestList.UNKNOWN_VALUE) ? "VisibleUsingXml" : footerBarMixinMetrics.primaryButtonVisibility;
            } finally {
            }
        }
        if (resourceId != 0) {
            xml = footerButtonInflater.context.getResources().getXml(resourceId);
            try {
                FooterButton footerButtonInflate2 = footerButtonInflater.inflate(xml);
                xml.close();
                setPrimaryButton(footerButtonInflate2);
                footerBarMixinMetrics.secondaryButtonVisibility = footerBarMixinMetrics.secondaryButtonVisibility.equals(C2paManifestList.UNKNOWN_VALUE) ? "VisibleUsingXml" : footerBarMixinMetrics.secondaryButtonVisibility;
            } finally {
            }
        }
    }

    public static boolean isBothButtons(Button button, Button button2) {
        boolean z = button != null && button.getVisibility() == 0;
        boolean z2 = button2 != null && button2.getVisibility() == 0;
        LOG.atDebug("isPrimaryVisible=" + z + ", isSecondaryVisible=" + z2);
        return z && z2;
    }

    public final void autoSetButtonBarVisibility() {
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        int i = 0;
        boolean z = primaryButtonView != null && primaryButtonView.getVisibility() == 0;
        boolean z2 = secondaryButtonView != null && secondaryButtonView.getVisibility() == 0;
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout != null) {
            if (!z && !z2) {
                i = 8;
            }
            linearLayout.setVisibility(i);
        }
    }

    public final LinearLayout ensureFooterInflated() {
        int dimension;
        if (this.buttonContainer == null) {
            if (this.footerStub == null) {
                throw new IllegalStateException("Footer stub is not found in this template");
            }
            this.footerStub.setLayoutInflater(LayoutInflater.from(new ContextThemeWrapper(this.context, R.style.SucPartnerCustomizationButtonBar_Stackable)));
            this.footerStub.setLayoutResource(R.layout.suc_footer_button_bar);
            LinearLayout linearLayout = (LinearLayout) this.footerStub.inflate();
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
                    linearLayout2.setBackgroundColor(PartnerConfigHelper.get(this.context).getColor(this.context, PartnerConfig.CONFIG_FOOTER_BAR_BG_COLOR));
                }
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_TOP;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    this.footerBarPaddingTop = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_BUTTON_PADDING_BOTTOM;
                if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                    this.footerBarPaddingBottom = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig2, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_START;
                if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
                    this.footerBarPaddingStart = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig3, 0.0f);
                }
                PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_FOOTER_BAR_PADDING_END;
                if (partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)) {
                    this.footerBarPaddingEnd = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig4, 0.0f);
                }
                linearLayout2.setPadding(this.footerBarPaddingStart, this.footerBarPaddingTop, this.footerBarPaddingEnd, this.footerBarPaddingBottom);
                PartnerConfigHelper partnerConfigHelper5 = PartnerConfigHelper.get(this.context);
                PartnerConfig partnerConfig5 = PartnerConfig.CONFIG_FOOTER_BAR_MIN_HEIGHT;
                if (partnerConfigHelper5.isPartnerConfigAvailable(partnerConfig5) && (dimension = (int) PartnerConfigHelper.get(this.context).getDimension(this.context, partnerConfig5, 0.0f)) > 0) {
                    linearLayout2.setMinimumHeight(dimension);
                }
            }
        }
        return this.buttonContainer;
    }

    public final PersistableBundle getLoggingMetrics() {
        LOG.atDebug("FooterBarMixin fragment name=" + this.hostFragmentName + ", Tag=" + this.hostFragmentTag);
        FooterBarMixinMetrics footerBarMixinMetrics = this.metrics;
        footerBarMixinMetrics.getClass();
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(FooterBarMixinMetrics.EXTRA_PRIMARY_BUTTON_VISIBILITY, footerBarMixinMetrics.primaryButtonVisibility);
        persistableBundle.putString(FooterBarMixinMetrics.EXTRA_SECONDARY_BUTTON_VISIBILITY, footerBarMixinMetrics.secondaryButtonVisibility);
        if (PartnerConfigHelper.isEnhancedSetupDesignMetricsEnabled(this.context)) {
            String str = this.hostFragmentName;
            if (str != null) {
                persistableBundle.putString("HostFragmentName", CustomEvent.trimsStringOverMaxLength(str));
            }
            String str2 = this.hostFragmentTag;
            if (str2 != null) {
                persistableBundle.putString("HostFragmentTag", CustomEvent.trimsStringOverMaxLength(str2));
            }
        }
        return persistableBundle;
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
        if (i2 != 0 && !this.applyPartnerResources && !PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            i = i2;
        }
        return this.applyPartnerResources ? PartnerConfigHelper.get(this.context).getColor(this.context, partnerConfig) == 0 ? PartnerConfigHelper.isGlifExpressiveEnabled(this.context) ? R.style.SucGlifMaterialButton_Secondary : R.style.SucPartnerCustomizationButton_Secondary : PartnerConfigHelper.isGlifExpressiveEnabled(this.context) ? R.style.SucGlifMaterialButton_Primary : R.style.SucPartnerCustomizationButton_Primary : i;
    }

    public final Button getPrimaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.primaryButtonId);
    }

    public final Button getSecondaryButtonView() {
        LinearLayout linearLayout = this.buttonContainer;
        if (linearLayout == null) {
            return null;
        }
        return (Button) linearLayout.findViewById(this.secondaryButtonId);
    }

    public int getVisibility() {
        return this.buttonContainer.getVisibility();
    }

    public final IFooterActionButton inflateButton(FooterButton footerButton, FooterButtonPartnerConfig footerButtonPartnerConfig) {
        IFooterActionButton materialFooterActionButton;
        Context context = this.context;
        boolean zIsGlifExpressiveEnabled = PartnerConfigHelper.isGlifExpressiveEnabled(context);
        int i = footerButtonPartnerConfig.partnerTheme;
        Logger logger = LOG;
        if (zIsGlifExpressiveEnabled) {
            try {
                materialFooterActionButton = i == 2132018158 ? new MaterialFooterActionButton(new ContextThemeWrapper(context, i), null, R.attr.sucMaterialButtonStyle) : new MaterialFooterActionButton(new ContextThemeWrapper(context, i), null, R.attr.sucMaterialOutlinedButtonStyle);
            } catch (IllegalArgumentException e) {
                logger.e("Applyed invalid material theme: " + e);
                i = i == 2132018158 ? R.style.SucPartnerCustomizationButton_Primary : R.style.SucPartnerCustomizationButton_Secondary;
            }
        } else {
            materialFooterActionButton = (IFooterActionButton) LayoutInflater.from(new ContextThemeWrapper(context, i)).inflate(R.layout.suc_button, (ViewGroup) null, false);
        }
        Button button = (Button) materialFooterActionButton;
        button.setId(View.generateViewId());
        button.setText(footerButton.text);
        button.setOnClickListener(footerButton);
        button.setVisibility(footerButton.visibility);
        button.setEnabled(footerButton.enabled);
        if (materialFooterActionButton instanceof MaterialFooterActionButton) {
            ((MaterialFooterActionButton) materialFooterActionButton).footerButton = footerButton;
        } else if (button instanceof FooterActionButton) {
            ((FooterActionButton) materialFooterActionButton).footerButton = footerButton;
        } else {
            logger.e("Set the footer button error!");
        }
        footerButton.buttonListener = new AnonymousClass1(button.getId());
        return materialFooterActionButton;
    }

    public final boolean isFooterButtonAlignedEnd() {
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(this.context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ALIGNED_END;
        return partnerConfigHelper.isPartnerConfigAvailable(partnerConfig) ? PartnerConfigHelper.get(this.context).getBoolean(this.context, partnerConfig, false) : this.footerButtonAlignEnd;
    }

    public boolean isPrimaryButtonVisible() {
        return getPrimaryButtonView() != null && getPrimaryButtonView().getVisibility() == 0;
    }

    public boolean isSecondaryButtonVisible() {
        return getSecondaryButtonView() != null && getSecondaryButtonView().getVisibility() == 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x0205  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onFooterButtonApplyPartnerResource(Button button, FooterButtonPartnerConfig footerButtonPartnerConfig) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        float f;
        Typeface typefaceCreate;
        Drawable drawable;
        if (this.applyPartnerResources) {
            Context context = this.context;
            boolean z = this.applyDynamicColor;
            boolean z2 = button.getId() == this.primaryButtonId;
            FooterButtonStyleUtils.defaultTextColor.put(Integer.valueOf(button.getId()), button.getTextColors());
            PartnerConfig partnerConfig = footerButtonPartnerConfig.buttonDisableTextColorConfig;
            PartnerConfig partnerConfig2 = footerButtonPartnerConfig.buttonTextColorConfig;
            if (z) {
                f = 255.0f;
            } else {
                if (button.isEnabled()) {
                    int color = PartnerConfigHelper.get(context).getColor(context, partnerConfig2);
                    if (color != 0) {
                        button.setTextColor(ColorStateList.valueOf(color));
                    }
                } else {
                    FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(context, button, partnerConfig);
                }
                int color2 = PartnerConfigHelper.get(context).getColor(context, footerButtonPartnerConfig.buttonBackgroundConfig);
                float fraction = PartnerConfigHelper.get(context).getFraction(context, footerButtonPartnerConfig.buttonDisableAlphaConfig);
                int color3 = PartnerConfigHelper.get(context).getColor(context, footerButtonPartnerConfig.buttonDisableBackgroundConfig);
                int[] iArr = {-16842910};
                f = 255.0f;
                int[] iArr2 = new int[0];
                if (color2 != 0) {
                    if (fraction <= 0.0f) {
                        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{android.R.attr.disabledAlpha});
                        float f2 = typedArrayObtainStyledAttributes.getFloat(0, 0.26f);
                        typedArrayObtainStyledAttributes.recycle();
                        fraction = f2;
                    }
                    if (color3 == 0) {
                        color3 = color2;
                    }
                    ColorStateList colorStateList = new ColorStateList(new int[][]{iArr, iArr2}, new int[]{Color.argb((int) (fraction * 255.0f), Color.red(color3), Color.green(color3), Color.blue(color3)), color2});
                    button.getBackground().mutate().setState(new int[0]);
                    button.refreshDrawableState();
                    button.setBackgroundTintList(colorStateList);
                }
            }
            int defaultColor = z ? button.getTextColors().getDefaultColor() : PartnerConfigHelper.get(context).getColor(context, partnerConfig2);
            float fraction2 = PartnerConfigHelper.get(context).getFraction(context, footerButtonPartnerConfig.buttonRippleColorAlphaConfig);
            Drawable background = button.getBackground();
            RippleDrawable rippleDrawable = background instanceof InsetDrawable ? (RippleDrawable) ((InsetDrawable) background).getDrawable() : background instanceof RippleDrawable ? (RippleDrawable) background : null;
            if (rippleDrawable != null) {
                int[] iArr3 = {android.R.attr.state_pressed};
                int[] iArr4 = {android.R.attr.state_focused};
                int iArgb = Color.argb((int) (fraction2 * f), Color.red(defaultColor), Color.green(defaultColor), Color.blue(defaultColor));
                ColorStateList colorStateList2 = new ColorStateList(new int[][]{iArr3, iArr4, StateSet.NOTHING}, new int[]{iArgb, iArgb, 0});
                if (PartnerConfigHelper.isGlifExpressiveEnabled(context) && (button instanceof MaterialFooterActionButton)) {
                    MaterialFooterActionButton materialFooterActionButton = (MaterialFooterActionButton) button;
                    if (materialFooterActionButton.isUsingOriginalBackground()) {
                        MaterialButtonHelper materialButtonHelper = materialFooterActionButton.materialButtonHelper;
                        if (materialButtonHelper.rippleColor != colorStateList2) {
                            materialButtonHelper.rippleColor = colorStateList2;
                            MaterialButton materialButton = materialButtonHelper.materialButton;
                            if (materialButton.getBackground() instanceof RippleDrawable) {
                                ((RippleDrawable) materialButton.getBackground()).setColor(colorStateList2);
                            }
                        }
                    }
                } else {
                    rippleDrawable.setColor(colorStateList2);
                }
            }
            ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig3 = footerButtonPartnerConfig.buttonMarginStartConfig;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig3) && (layoutParams instanceof ViewGroup.MarginLayoutParams)) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.setMargins((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig3, 0.0f), marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            }
            float dimension = PartnerConfigHelper.get(context).getDimension(context, footerButtonPartnerConfig.buttonTextSizeConfig, 0.0f);
            if (dimension > 0.0f) {
                button.setTextSize(0, dimension);
            }
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig4 = footerButtonPartnerConfig.buttonMinHeightConfig;
            if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig4)) {
                float dimension2 = PartnerConfigHelper.get(context).getDimension(context, partnerConfig4, 0.0f);
                if (dimension2 > 0.0f) {
                    button.setMinHeight((int) dimension2);
                }
            }
            String string = PartnerConfigHelper.get(context).getString(context, footerButtonPartnerConfig.buttonTextTypeFaceConfig);
            PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig5 = footerButtonPartnerConfig.buttonTextStyleConfig;
            int integer = partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig5) ? PartnerConfigHelper.get(context).getInteger(context, partnerConfig5, 0) : 0;
            if (PartnerConfigHelper.isFontWeightEnabled(context)) {
                PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig6 = footerButtonPartnerConfig.buttonTextWeightConfig;
                typefaceCreate = partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig6) ? Typeface.create(Typeface.create(string, integer), PartnerConfigHelper.get(context).getInteger(context, partnerConfig6, 400), false) : Typeface.create(string, integer);
            }
            if (typefaceCreate != null) {
                button.setTypeface(typefaceCreate);
            }
            float dimension3 = PartnerConfigHelper.get(context).getDimension(context, footerButtonPartnerConfig.buttonRadiusConfig, 0.0f);
            if (PartnerConfigHelper.isGlifExpressiveEnabled(context) && (button instanceof MaterialFooterActionButton)) {
                MaterialFooterActionButton materialFooterActionButton2 = (MaterialFooterActionButton) button;
                int i = (int) dimension3;
                if (materialFooterActionButton2.isUsingOriginalBackground()) {
                    MaterialButtonHelper materialButtonHelper2 = materialFooterActionButton2.materialButtonHelper;
                    if (!materialButtonHelper2.cornerRadiusSet || materialButtonHelper2.cornerRadius != i) {
                        materialButtonHelper2.cornerRadius = i;
                        materialButtonHelper2.cornerRadiusSet = true;
                        materialButtonHelper2.setShapeAppearanceModel(materialButtonHelper2.shapeAppearanceModel.withCornerSize(i));
                    }
                }
            } else {
                GradientDrawable gradientDrawable = FooterButtonStyleUtils.getGradientDrawable(button);
                if (gradientDrawable != null) {
                    gradientDrawable.setCornerRadius(dimension3);
                }
            }
            PartnerConfig partnerConfig7 = footerButtonPartnerConfig.buttonIconConfig;
            Drawable drawable2 = partnerConfig7 != null ? PartnerConfigHelper.get(context).getDrawable(context, partnerConfig7) : null;
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
            }
            if (z2) {
                drawable = drawable2;
                drawable2 = null;
            } else {
                drawable = null;
            }
            button.setCompoundDrawablesRelative(drawable2, null, drawable, null);
            if (this.applyDynamicColor) {
                return;
            }
            if (!button.isEnabled()) {
                FooterButtonStyleUtils.updateButtonTextDisabledColorWithPartnerConfig(this.context, button, partnerConfig);
                return;
            }
            Context context2 = this.context;
            int color4 = PartnerConfigHelper.get(context2).getColor(context2, partnerConfig2);
            if (color4 != 0) {
                button.setTextColor(ColorStateList.valueOf(color4));
            }
        }
    }

    public final void onFooterButtonInflated(Button button, int i) {
        if (!this.applyDynamicColor && i != 0) {
            HashMap map = FooterButtonStyleUtils.defaultTextColor;
            button.getBackground().mutate().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        }
        this.buttonContainer.addView(button);
        autoSetButtonBarVisibility();
    }

    public final void repopulateButtons() {
        LinearLayout.LayoutParams layoutParams;
        LinearLayout.LayoutParams layoutParams2;
        LinearLayout linearLayoutEnsureFooterInflated = ensureFooterInflated();
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        boolean zIsGlifExpressiveEnabled = PartnerConfigHelper.isGlifExpressiveEnabled(this.context);
        Logger logger = LOG;
        View view = null;
        if (zIsGlifExpressiveEnabled) {
            LinearLayout linearLayout = this.buttonContainer;
            if (linearLayout != null) {
                view = (Button) linearLayout.findViewById(0);
            }
        } else {
            logger.atDebug("Cannot get tertiary button when glif expressive is not enabled.");
        }
        linearLayoutEnsureFooterInflated.removeAllViews();
        int i = this.context.getResources().getConfiguration().orientation;
        if (secondaryButtonView != null) {
            linearLayoutEnsureFooterInflated.addView(secondaryButtonView);
        }
        if (!isFooterButtonAlignedEnd() && !PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            LinearLayout linearLayoutEnsureFooterInflated2 = ensureFooterInflated();
            View view2 = new View(this.context);
            view2.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1.0f));
            view2.setVisibility(4);
            linearLayoutEnsureFooterInflated2.addView(view2);
        }
        if (PartnerConfigHelper.isGlifExpressiveEnabled(this.context) && view != null) {
            if (isBothButtons(primaryButtonView, secondaryButtonView)) {
                linearLayoutEnsureFooterInflated.addView(view);
            } else {
                logger.atDebug("Cannot add tertiary button when primary or secondary button is null.");
            }
        }
        if (primaryButtonView != null) {
            linearLayoutEnsureFooterInflated.addView(primaryButtonView);
        }
        if (primaryButtonView != null && (layoutParams2 = (LinearLayout.LayoutParams) primaryButtonView.getLayoutParams()) != null) {
            layoutParams2.width = -2;
            layoutParams2.weight = 0.0f;
            primaryButtonView.setLayoutParams(layoutParams2);
        }
        if (secondaryButtonView != null && (layoutParams = (LinearLayout.LayoutParams) secondaryButtonView.getLayoutParams()) != null) {
            layoutParams.width = -2;
            layoutParams.weight = 0.0f;
            secondaryButtonView.setLayoutParams(layoutParams);
        }
        if (PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            this.buttonContainer.post(new FooterBarMixin$$ExternalSyntheticLambda2(this));
        }
    }

    public final void setPrimaryButton(FooterButton footerButton) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        PartnerConfig partnerConfig;
        Preconditions.ensureOnMainThread("setPrimaryButton");
        ensureFooterInflated();
        int i = PartnerConfigHelper.isGlifExpressiveEnabled(this.context) ? R.style.SucGlifMaterialButton_Primary : R.style.SucPartnerCustomizationButton_Primary;
        FooterButtonPartnerConfig.Builder builder = new FooterButtonPartnerConfig.Builder(footerButton);
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_BG_COLOR;
        builder.partnerTheme = getPartnerTheme(footerButton, i, partnerConfig2);
        builder.buttonBackgroundConfig = partnerConfig2;
        builder.buttonDisableAlphaConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA;
        builder.buttonDisableBackgroundConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR;
        builder.buttonDisableTextColorConfig = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_DISABLED_TEXT_COLOR;
        switch (footerButton.buttonType) {
            case 1:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_ADD_ANOTHER;
                break;
            case 2:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CANCEL;
                break;
            case 3:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_CLEAR;
                break;
            case 4:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_DONE;
                break;
            case 5:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_NEXT;
                break;
            case 6:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_OPT_IN;
                break;
            case 7:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_SKIP;
                break;
            case 8:
                partnerConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_ICON_STOP;
                break;
            default:
                partnerConfig = null;
                break;
        }
        builder.buttonIconConfig = partnerConfig;
        builder.buttonRadiusConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_RADIUS;
        builder.buttonRippleColorAlphaConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_RIPPLE_COLOR_ALPHA;
        builder.buttonTextColorConfig = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_TEXT_COLOR;
        builder.buttonMarginStartConfig = PartnerConfig.CONFIG_FOOTER_PRIMARY_BUTTON_MARGIN_START;
        builder.buttonTextSizeConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_SIZE;
        builder.buttonMinHeight = PartnerConfig.CONFIG_FOOTER_BUTTON_MIN_HEIGHT;
        builder.buttonTextTypeFaceConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_FAMILY;
        builder.buttonTextWeightConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_FONT_WEIGHT;
        builder.buttonTextStyleConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_TEXT_STYLE;
        FooterButtonPartnerConfig footerButtonPartnerConfigBuild = builder.build();
        Object objInflateButton = inflateButton(footerButton, footerButtonPartnerConfigBuild);
        Button button = (Button) objInflateButton;
        this.primaryButtonId = button.getId();
        if (objInflateButton instanceof MaterialFooterActionButton) {
            ((MaterialFooterActionButton) objInflateButton).getClass();
        } else if (button instanceof FooterActionButton) {
            ((FooterActionButton) objInflateButton).isPrimaryButtonStyle = true;
        } else {
            LOG.e("Set the primary button style error when setting primary button.");
        }
        this.primaryButton = footerButton;
        this.primaryButtonPartnerConfigForTesting = footerButtonPartnerConfigBuild;
        onFooterButtonInflated(button, this.footerBarPrimaryBackgroundColor);
        onFooterButtonApplyPartnerResource(button, footerButtonPartnerConfigBuild);
        if (PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            boolean z = this.primaryButton.enabled;
            int i2 = z ? this.footerBarPrimaryButtonEnabledTextColor : this.footerBarPrimaryButtonDisabledTextColor;
            if (z) {
                HashMap map = FooterButtonStyleUtils.defaultTextColor;
                if (i2 != 0) {
                    button.setTextColor(ColorStateList.valueOf(i2));
                }
            } else {
                HashMap map2 = FooterButtonStyleUtils.defaultTextColor;
                if (i2 != 0) {
                    button.setTextColor(ColorStateList.valueOf(i2));
                }
            }
        }
        repopulateButtons();
        button.post(new FooterBarMixin$$ExternalSyntheticLambda0(this, button, 0));
        RecoilHelper.apply(this.context, button);
    }

    public boolean stackButtonIfTextOverFlow(Button button, Button button2, float f, int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) button2.getLayoutParams();
        String string = button.getText().toString();
        Paint paint = new Paint();
        paint.setTypeface(button.getTypeface());
        paint.setTextSize(button.getTextSize());
        float fMeasureText = paint.measureText(string) + button.getPaddingLeft() + button.getPaddingRight() + button.getPaddingStart() + button.getPaddingEnd();
        boolean z = fMeasureText > f;
        Logger logger = LOG;
        logger.atDebug("isPrimaryButtonTextOverFlowing= " + z + ", primaryButtonWidth= " + fMeasureText + ", maxButtonWidth= " + f);
        String string2 = button2.getText().toString();
        Paint paint2 = new Paint();
        paint2.setTypeface(button2.getTypeface());
        paint2.setTextSize(button2.getTextSize());
        float fMeasureText2 = paint2.measureText(string2) + button2.getPaddingLeft() + button2.getPaddingRight() + button2.getPaddingStart() + button2.getPaddingEnd();
        boolean z2 = fMeasureText2 > f;
        logger.atDebug("isSecondaryButtonTextOverFlowing= " + z2 + ", secondaryButtonWidth= " + fMeasureText2 + ", maxButtonWidth= " + f);
        if (z || z2) {
            LinearLayout linearLayout = this.buttonContainer;
            if (linearLayout instanceof ButtonBarLayout) {
                ButtonBarLayout buttonBarLayout = (ButtonBarLayout) linearLayout;
                if (PartnerConfigHelper.isGlifExpressiveEnabled(buttonBarLayout.getContext())) {
                    buttonBarLayout.stackedButtonForExpressiveStyle = true;
                } else {
                    buttonBarLayout.stackedButtonForExpressiveStyle = false;
                }
                int i2 = this.footerBarButtonMiddleSpacing / 2;
                layoutParams2.width = i;
                layoutParams2.topMargin = i2;
                button2.setLayoutParams(layoutParams2);
                layoutParams.width = i;
                layoutParams.bottomMargin = i2;
                button.setLayoutParams(layoutParams);
                return true;
            }
        }
        return false;
    }
}
