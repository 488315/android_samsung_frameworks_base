package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.systemui.R;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.android.setupcompat.template.FooterBarMixin$1, reason: invalid class name */
    public class AnonymousClass1 {
        public final /* synthetic */ int val$id;

        public AnonymousClass1(int i) {
            this.val$id = i;
        }
    }

    public FooterBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SucFooterBarMixin, i, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(17, 0);
        this.defaultPadding = dimensionPixelSize;
        this.footerBarPaddingTop = obtainStyledAttributes.getDimensionPixelSize(16, dimensionPixelSize);
        this.footerBarPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(13, this.defaultPadding);
        this.footerBarPaddingStart = obtainStyledAttributes.getDimensionPixelSize(15, 0);
        this.footerBarPaddingEnd = obtainStyledAttributes.getDimensionPixelSize(14, 0);
        this.footerBarPrimaryBackgroundColor = obtainStyledAttributes.getColor(18, 0);
        int color = obtainStyledAttributes.getColor(22, 0);
        this.footerButtonAlignEnd = obtainStyledAttributes.getBoolean(0, false);
        this.footerBarPrimaryButtonEnabledTextColor = obtainStyledAttributes.getColor(21, 0);
        int color2 = obtainStyledAttributes.getColor(25, 0);
        this.footerBarPrimaryButtonDisabledTextColor = obtainStyledAttributes.getColor(20, 0);
        int color3 = obtainStyledAttributes.getColor(24, 0);
        this.footerBarButtonMiddleSpacing = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        int resourceId = obtainStyledAttributes.getResourceId(19, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(23, 0);
        obtainStyledAttributes.recycle();
        FooterButtonInflater footerButtonInflater = new FooterButtonInflater(context);
        if (resourceId2 != 0) {
            xml = footerButtonInflater.context.getResources().getXml(resourceId2);
            try {
                FooterButton inflate = footerButtonInflater.inflate(xml);
                xml.close();
                Preconditions.ensureOnMainThread("setSecondaryButton");
                ensureFooterInflated();
                int i2 = PartnerConfigHelper.isGlifExpressiveEnabled(context) ? R.style.SucGlifMaterialButton_Secondary : R.style.SucPartnerCustomizationButton_Secondary;
                FooterButtonPartnerConfig.Builder builder = new FooterButtonPartnerConfig.Builder(inflate);
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_BG_COLOR;
                builder.partnerTheme = getPartnerTheme(inflate, i2, partnerConfig2);
                builder.buttonBackgroundConfig = partnerConfig2;
                builder.buttonDisableAlphaConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_ALPHA;
                builder.buttonDisableBackgroundConfig = PartnerConfig.CONFIG_FOOTER_BUTTON_DISABLED_BG_COLOR;
                builder.buttonDisableTextColorConfig = PartnerConfig.CONFIG_FOOTER_SECONDARY_BUTTON_DISABLED_TEXT_COLOR;
                switch (inflate.buttonType) {
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
                FooterButtonPartnerConfig build = builder.build();
                Object inflateButton = inflateButton(inflate, build);
                Button button = (Button) inflateButton;
                this.secondaryButtonId = button.getId();
                if (inflateButton instanceof MaterialFooterActionButton) {
                    ((MaterialFooterActionButton) inflateButton).getClass();
                } else if (button instanceof FooterActionButton) {
                    ((FooterActionButton) inflateButton).isPrimaryButtonStyle = false;
                } else {
                    LOG.e("Set the primary button style error when setting secondary button.");
                }
                this.secondaryButton = inflate;
                this.secondaryButtonPartnerConfigForTesting = build;
                onFooterButtonInflated(button, color);
                onFooterButtonApplyPartnerResource(button, build);
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
                FooterButton inflate2 = footerButtonInflater.inflate(xml);
                xml.close();
                setPrimaryButton(inflate2);
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.setupcompat.template.IFooterActionButton inflateButton(com.google.android.setupcompat.template.FooterButton r8, com.google.android.setupcompat.internal.FooterButtonPartnerConfig r9) {
        /*
            r7 = this;
            android.content.Context r0 = r7.context
            boolean r1 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.isGlifExpressiveEnabled(r0)
            int r9 = r9.partnerTheme
            com.google.android.setupcompat.util.Logger r2 = com.google.android.setupcompat.template.FooterBarMixin.LOG
            r3 = 0
            if (r1 == 0) goto L4a
            r1 = 2132018158(0x7f1403ee, float:1.9674615E38)
            if (r9 != r1) goto L22
            com.google.android.setupcompat.template.MaterialFooterActionButton r4 = new com.google.android.setupcompat.template.MaterialFooterActionButton     // Catch: java.lang.IllegalArgumentException -> L20
            android.view.ContextThemeWrapper r5 = new android.view.ContextThemeWrapper     // Catch: java.lang.IllegalArgumentException -> L20
            r5.<init>(r0, r9)     // Catch: java.lang.IllegalArgumentException -> L20
            r6 = 2130970286(0x7f0406ae, float:1.7549278E38)
            r4.<init>(r5, r3, r6)     // Catch: java.lang.IllegalArgumentException -> L20
            goto L5e
        L20:
            r4 = move-exception
            goto L30
        L22:
            com.google.android.setupcompat.template.MaterialFooterActionButton r4 = new com.google.android.setupcompat.template.MaterialFooterActionButton     // Catch: java.lang.IllegalArgumentException -> L20
            android.view.ContextThemeWrapper r5 = new android.view.ContextThemeWrapper     // Catch: java.lang.IllegalArgumentException -> L20
            r5.<init>(r0, r9)     // Catch: java.lang.IllegalArgumentException -> L20
            r6 = 2130970287(0x7f0406af, float:1.754928E38)
            r4.<init>(r5, r3, r6)     // Catch: java.lang.IllegalArgumentException -> L20
            goto L5e
        L30:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Applyed invalid material theme: "
            r5.<init>(r6)
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            r2.e(r4)
            if (r9 != r1) goto L47
            r9 = 2132018161(0x7f1403f1, float:1.967462E38)
            goto L4a
        L47:
            r9 = 2132018162(0x7f1403f2, float:1.9674623E38)
        L4a:
            android.view.ContextThemeWrapper r1 = new android.view.ContextThemeWrapper
            r1.<init>(r0, r9)
            android.view.LayoutInflater r9 = android.view.LayoutInflater.from(r1)
            r0 = 2131559725(0x7f0d052d, float:1.8744802E38)
            r1 = 0
            android.view.View r9 = r9.inflate(r0, r3, r1)
            r4 = r9
            com.google.android.setupcompat.template.IFooterActionButton r4 = (com.google.android.setupcompat.template.IFooterActionButton) r4
        L5e:
            r9 = r4
            android.widget.Button r9 = (android.widget.Button) r9
            int r0 = android.view.View.generateViewId()
            r9.setId(r0)
            java.lang.CharSequence r0 = r8.text
            r9.setText(r0)
            r9.setOnClickListener(r8)
            int r0 = r8.visibility
            r9.setVisibility(r0)
            boolean r0 = r8.enabled
            r9.setEnabled(r0)
            boolean r0 = r4 instanceof com.google.android.setupcompat.template.MaterialFooterActionButton
            if (r0 == 0) goto L84
            r0 = r4
            com.google.android.setupcompat.template.MaterialFooterActionButton r0 = (com.google.android.setupcompat.template.MaterialFooterActionButton) r0
            r0.footerButton = r8
            goto L93
        L84:
            boolean r0 = r9 instanceof com.google.android.setupcompat.template.FooterActionButton
            if (r0 == 0) goto L8e
            r0 = r4
            com.google.android.setupcompat.template.FooterActionButton r0 = (com.google.android.setupcompat.template.FooterActionButton) r0
            r0.footerButton = r8
            goto L93
        L8e:
            java.lang.String r0 = "Set the footer button error!"
            r2.e(r0)
        L93:
            int r9 = r9.getId()
            com.google.android.setupcompat.template.FooterBarMixin$1 r0 = new com.google.android.setupcompat.template.FooterBarMixin$1
            r0.<init>(r9)
            r8.buttonListener = r0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupcompat.template.FooterBarMixin.inflateButton(com.google.android.setupcompat.template.FooterButton, com.google.android.setupcompat.internal.FooterButtonPartnerConfig):com.google.android.setupcompat.template.IFooterActionButton");
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

    /* JADX WARN: Removed duplicated region for block: B:61:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:92:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x024d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onFooterButtonApplyPartnerResource(android.widget.Button r19, com.google.android.setupcompat.internal.FooterButtonPartnerConfig r20) {
        /*
            Method dump skipped, instructions count: 664
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupcompat.template.FooterBarMixin.onFooterButtonApplyPartnerResource(android.widget.Button, com.google.android.setupcompat.internal.FooterButtonPartnerConfig):void");
    }

    public final void onFooterButtonInflated(Button button, int i) {
        if (!this.applyDynamicColor && i != 0) {
            HashMap hashMap = FooterButtonStyleUtils.defaultTextColor;
            button.getBackground().mutate().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        }
        this.buttonContainer.addView(button);
        autoSetButtonBarVisibility();
    }

    public final void repopulateButtons() {
        LinearLayout.LayoutParams layoutParams;
        LinearLayout.LayoutParams layoutParams2;
        LinearLayout ensureFooterInflated = ensureFooterInflated();
        Button primaryButtonView = getPrimaryButtonView();
        Button secondaryButtonView = getSecondaryButtonView();
        boolean isGlifExpressiveEnabled = PartnerConfigHelper.isGlifExpressiveEnabled(this.context);
        Logger logger = LOG;
        View view = null;
        if (isGlifExpressiveEnabled) {
            LinearLayout linearLayout = this.buttonContainer;
            if (linearLayout != null) {
                view = (Button) linearLayout.findViewById(0);
            }
        } else {
            logger.atDebug("Cannot get tertiary button when glif expressive is not enabled.");
        }
        ensureFooterInflated.removeAllViews();
        int i = this.context.getResources().getConfiguration().orientation;
        if (secondaryButtonView != null) {
            ensureFooterInflated.addView(secondaryButtonView);
        }
        if (!isFooterButtonAlignedEnd() && !PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            LinearLayout ensureFooterInflated2 = ensureFooterInflated();
            View view2 = new View(this.context);
            view2.setLayoutParams(new LinearLayout.LayoutParams(0, 0, 1.0f));
            view2.setVisibility(4);
            ensureFooterInflated2.addView(view2);
        }
        if (PartnerConfigHelper.isGlifExpressiveEnabled(this.context) && view != null) {
            if (isBothButtons(primaryButtonView, secondaryButtonView)) {
                ensureFooterInflated.addView(view);
            } else {
                logger.atDebug("Cannot add tertiary button when primary or secondary button is null.");
            }
        }
        if (primaryButtonView != null) {
            ensureFooterInflated.addView(primaryButtonView);
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

    public final void setPrimaryButton(FooterButton footerButton) {
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
        FooterButtonPartnerConfig build = builder.build();
        Object inflateButton = inflateButton(footerButton, build);
        Button button = (Button) inflateButton;
        this.primaryButtonId = button.getId();
        if (inflateButton instanceof MaterialFooterActionButton) {
            ((MaterialFooterActionButton) inflateButton).getClass();
        } else if (button instanceof FooterActionButton) {
            ((FooterActionButton) inflateButton).isPrimaryButtonStyle = true;
        } else {
            LOG.e("Set the primary button style error when setting primary button.");
        }
        this.primaryButton = footerButton;
        this.primaryButtonPartnerConfigForTesting = build;
        onFooterButtonInflated(button, this.footerBarPrimaryBackgroundColor);
        onFooterButtonApplyPartnerResource(button, build);
        if (PartnerConfigHelper.isGlifExpressiveEnabled(this.context)) {
            boolean z = this.primaryButton.enabled;
            int i2 = z ? this.footerBarPrimaryButtonEnabledTextColor : this.footerBarPrimaryButtonDisabledTextColor;
            if (z) {
                HashMap hashMap = FooterButtonStyleUtils.defaultTextColor;
                if (i2 != 0) {
                    button.setTextColor(ColorStateList.valueOf(i2));
                }
            } else {
                HashMap hashMap2 = FooterButtonStyleUtils.defaultTextColor;
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
        String charSequence = button.getText().toString();
        Paint paint = new Paint();
        paint.setTypeface(button.getTypeface());
        paint.setTextSize(button.getTextSize());
        float measureText = paint.measureText(charSequence) + button.getPaddingLeft() + button.getPaddingRight() + button.getPaddingStart() + button.getPaddingEnd();
        boolean z = measureText > f;
        Logger logger = LOG;
        logger.atDebug("isPrimaryButtonTextOverFlowing= " + z + ", primaryButtonWidth= " + measureText + ", maxButtonWidth= " + f);
        String charSequence2 = button2.getText().toString();
        Paint paint2 = new Paint();
        paint2.setTypeface(button2.getTypeface());
        paint2.setTextSize(button2.getTextSize());
        float measureText2 = paint2.measureText(charSequence2) + button2.getPaddingLeft() + button2.getPaddingRight() + button2.getPaddingStart() + button2.getPaddingEnd();
        boolean z2 = measureText2 > f;
        logger.atDebug("isSecondaryButtonTextOverFlowing= " + z2 + ", secondaryButtonWidth= " + measureText2 + ", maxButtonWidth= " + f);
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
