package com.google.android.setupdesign;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.window.embedding.ActivityEmbeddingController;
import androidx.window.embedding.EmbeddingCompat;
import androidx.window.embedding.EmbeddingInterfaceCompat;
import androidx.window.embedding.ExtensionEmbeddingBackend;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.StatusBarMixin;
import com.google.android.setupcompat.template.SystemNavBarMixin;
import com.google.android.setupcompat.util.ForceTwoPaneHelper;
import com.google.android.setupcompat.util.KeyboardHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.template.DescriptionMixin;
import com.google.android.setupdesign.template.FloatingBackButtonMixin;
import com.google.android.setupdesign.template.HeaderMixin;
import com.google.android.setupdesign.template.IconMixin;
import com.google.android.setupdesign.template.IllustrationProgressMixin;
import com.google.android.setupdesign.template.ProfileMixin;
import com.google.android.setupdesign.template.ProgressBarMixin;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.google.android.setupdesign.template.ScrollViewScrollHandlingDelegate;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.util.TextViewPartnerStyler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GlifLayout extends PartnerCustomizationLayout {
    public static final Logger LOG = new Logger((Class<?>) GlifLayout.class);
    public boolean applyPartnerHeavyThemeResource;
    public ColorStateList backgroundBaseColor;
    public boolean backgroundPatterned;
    public final AnonymousClass1 onScrollChangedListener;
    public ColorStateList primaryColor;

    public GlifLayout(Context context) {
        this(context, 0, 0);
    }

    public static boolean isEmbeddedActivityOnePaneEnabled(Context context) {
        boolean isEmbeddedActivityOnePaneEnabled = PartnerConfigHelper.isEmbeddedActivityOnePaneEnabled(context);
        ActivityEmbeddingController activityEmbeddingController = ActivityEmbeddingController.getInstance(context);
        Activity lookupActivityFromContext = PartnerConfigHelper.lookupActivityFromContext(context);
        EmbeddingInterfaceCompat embeddingInterfaceCompat = ((ExtensionEmbeddingBackend) activityEmbeddingController.backend).embeddingExtension;
        boolean isActivityEmbedded = embeddingInterfaceCompat != null ? ((EmbeddingCompat) embeddingInterfaceCompat).embeddingExtension.isActivityEmbedded(lookupActivityFromContext) : false;
        LOG.getClass();
        return isEmbeddedActivityOnePaneEnabled && isActivityEmbedded;
    }

    @Override // com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    public final ScrollView getScrollView() {
        View findManagedViewById = findManagedViewById(R.id.sud_scroll_view);
        if (findManagedViewById instanceof ScrollView) {
            return (ScrollView) findManagedViewById;
        }
        return null;
    }

    public final void init$1(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SudGlifLayout, i, 0);
        this.applyPartnerHeavyThemeResource = shouldApplyPartnerResource() && obtainStyledAttributes.getBoolean(4, false);
        registerMixin(HeaderMixin.class, new HeaderMixin(this, attributeSet, i));
        registerMixin(DescriptionMixin.class, new DescriptionMixin(this, attributeSet, i));
        registerMixin(IconMixin.class, new IconMixin(this, attributeSet, i));
        registerMixin(ProfileMixin.class, new ProfileMixin(this, attributeSet, i));
        registerMixin(ProgressBarMixin.class, new ProgressBarMixin(this, attributeSet, i));
        registerMixin(IllustrationProgressMixin.class, new IllustrationProgressMixin(this));
        registerMixin(FloatingBackButtonMixin.class, new FloatingBackButtonMixin(this, attributeSet, i));
        RequireScrollMixin requireScrollMixin = new RequireScrollMixin(this);
        registerMixin(RequireScrollMixin.class, requireScrollMixin);
        ScrollView scrollView = getScrollView();
        if (scrollView != null) {
            new ScrollViewScrollHandlingDelegate(requireScrollMixin, scrollView);
        }
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(2);
        if (colorStateList != null) {
            this.primaryColor = colorStateList;
            updateBackground();
            ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(ProgressBarMixin.class);
            progressBarMixin.color = colorStateList;
            ProgressBar peekProgressBar = progressBarMixin.peekProgressBar();
            if (peekProgressBar != null) {
                peekProgressBar.setIndeterminateTintList(colorStateList);
                peekProgressBar.setProgressBackgroundTintList(colorStateList);
            }
        }
        if (shouldApplyPartnerHeavyThemeResource() && !useFullDynamicColor()) {
            getRootView().setBackgroundColor(PartnerConfigHelper.get(getContext()).getColor(getContext(), PartnerConfig.CONFIG_LAYOUT_BACKGROUND_COLOR));
        }
        View findManagedViewById = findManagedViewById(R.id.sud_layout_content);
        if (findManagedViewById != null) {
            if (shouldApplyPartnerResource()) {
                LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(findManagedViewById);
            }
            if (!(this instanceof GlifPreferenceLayout)) {
                tryApplyPartnerCustomizationContentPaddingTopStyle(findManagedViewById);
            }
        }
        updateLandscapeMiddleHorizontalSpacing();
        if (KeyboardHelper.isKeyboardFocusEnhancementEnabled(getContext())) {
            View findManagedViewById2 = findManagedViewById(R.id.sud_header_scroll_view);
            if (findManagedViewById2 != null) {
                findManagedViewById2.setFocusable(false);
            }
            View findManagedViewById3 = findManagedViewById(R.id.sud_scroll_view);
            if (findManagedViewById3 != null) {
                findManagedViewById3.setFocusable(false);
            }
        }
        this.backgroundBaseColor = obtainStyledAttributes.getColorStateList(0);
        updateBackground();
        this.backgroundPatterned = obtainStyledAttributes.getBoolean(1, true);
        updateBackground();
        int resourceId = obtainStyledAttributes.getResourceId(3, 0);
        if (resourceId != 0) {
            ViewStub viewStub = (ViewStub) findManagedViewById(R.id.sud_layout_sticky_header);
            viewStub.setLayoutResource(resourceId);
            viewStub.inflate();
        }
        if (PartnerConfigHelper.isGlifExpressiveEnabled(getContext())) {
            initScrollingListener();
        }
        initBackButton();
        obtainStyledAttributes.recycle();
    }

    public final void initBackButton() {
        if (!PartnerConfigHelper.isGlifExpressiveEnabled(getContext())) {
            LOG.atDebug("isGlifExpressiveEnabled is false");
            return;
        }
        Activity lookupActivityFromContext = PartnerConfigHelper.lookupActivityFromContext(getContext());
        final FloatingBackButtonMixin floatingBackButtonMixin = (FloatingBackButtonMixin) getMixin(FloatingBackButtonMixin.class);
        if (floatingBackButtonMixin == null) {
            LOG.w("FloatingBackButtonMixin button is null");
            return;
        }
        Button backButton = floatingBackButtonMixin.getBackButton();
        if (backButton != null) {
            backButton.setVisibility(0);
            ((FrameLayout) floatingBackButtonMixin.templateLayout.findManagedViewById(R.id.sud_layout_floating_back_button_container)).setVisibility(0);
        }
        final GlifLayout$$ExternalSyntheticLambda1 glifLayout$$ExternalSyntheticLambda1 = new GlifLayout$$ExternalSyntheticLambda1(lookupActivityFromContext);
        Button backButton2 = floatingBackButtonMixin.getBackButton();
        if (backButton2 != null) {
            backButton2.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.setupdesign.template.FloatingBackButtonMixin$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FloatingBackButtonMixin floatingBackButtonMixin2 = FloatingBackButtonMixin.this;
                    glifLayout$$ExternalSyntheticLambda1.onClick(view);
                    floatingBackButtonMixin2.clickCount++;
                }
            });
        }
    }

    public void initScrollingListener() {
        final ScrollView scrollView = getScrollView();
        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(this.onScrollChangedListener);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.google.android.setupdesign.GlifLayout$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GlifLayout glifLayout = GlifLayout.this;
                    ScrollView scrollView2 = scrollView;
                    Logger logger = GlifLayout.LOG;
                    glifLayout.getClass();
                    View childAt = scrollView2.getChildAt(0);
                    if (childAt == null || childAt.getHeight() <= scrollView2.getHeight()) {
                        return;
                    }
                    glifLayout.onScrolling(false);
                }
            }, 100L);
        }
    }

    public final boolean isGlifExpressiveEnabled() {
        return PartnerConfigHelper.isGlifExpressiveEnabled(getContext());
    }

    @Override // com.google.android.setupcompat.PartnerCustomizationLayout, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        PersistableBundle persistableBundle;
        super.onDetachedFromWindow();
        if (WizardManagerHelper.isAnySetupWizard(this.activity.getIntent()) && PartnerConfigHelper.isGlifExpressiveEnabled(getContext())) {
            FloatingBackButtonMixin floatingBackButtonMixin = (FloatingBackButtonMixin) getMixin(FloatingBackButtonMixin.class);
            if (floatingBackButtonMixin != null) {
                persistableBundle = new PersistableBundle();
                persistableBundle.putInt("BackButton_onClickCount", floatingBackButtonMixin.clickCount);
            } else {
                persistableBundle = PersistableBundle.EMPTY;
            }
            CustomEvent create = CustomEvent.create(MetricKey.get("SetupDesignMetrics", this.activity), persistableBundle);
            SetupMetricsLogger.logCustomEvent(getContext(), create);
            Logger logger = LOG;
            CustomEvent.toBundle(create).toString();
            logger.getClass();
        }
        ScrollView scrollView = getScrollView();
        if (scrollView != null) {
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(this.onScrollChangedListener);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ((IconMixin) getMixin(IconMixin.class)).tryApplyPartnerCustomizationStyle();
        ((HeaderMixin) getMixin(HeaderMixin.class)).tryApplyPartnerCustomizationStyle();
        TemplateLayout templateLayout = ((DescriptionMixin) getMixin(DescriptionMixin.class)).templateLayout;
        TextView textView = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle);
        if (textView != null && PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            TextViewPartnerStyler.applyPartnerCustomizationStyle(textView, new TextViewPartnerStyler.TextPartnerConfigs(PartnerConfig.CONFIG_DESCRIPTION_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_LINK_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_TEXT_SIZE, PartnerConfig.CONFIG_DESCRIPTION_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_FONT_WEIGHT, PartnerConfig.CONFIG_DESCRIPTION_LINK_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_TOP, PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_BOTTOM, PartnerStyleHelper.getLayoutGravity(textView.getContext())));
        }
        ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(ProgressBarMixin.class);
        ProgressBar peekProgressBar = progressBarMixin.peekProgressBar();
        if (progressBarMixin.useBottomProgressBar && peekProgressBar != null) {
            TemplateLayout templateLayout2 = progressBarMixin.templateLayout;
            if (!(templateLayout2 instanceof GlifLayout) ? false : ((GlifLayout) templateLayout2).shouldApplyPartnerHeavyThemeResource()) {
                Context context = peekProgressBar.getContext();
                ViewGroup.LayoutParams layoutParams = peekProgressBar.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    int i = marginLayoutParams.topMargin;
                    PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_TOP;
                    if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                        i = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, context.getResources().getDimension(R.dimen.sud_progress_bar_margin_top));
                    }
                    int i2 = marginLayoutParams.bottomMargin;
                    PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_BOTTOM;
                    if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                        i2 = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, context.getResources().getDimension(R.dimen.sud_progress_bar_margin_bottom));
                    }
                    if (i != marginLayoutParams.topMargin || i2 != marginLayoutParams.bottomMargin) {
                        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, i, marginLayoutParams.rightMargin, i2);
                    }
                }
            } else {
                Context context2 = peekProgressBar.getContext();
                ViewGroup.LayoutParams layoutParams2 = peekProgressBar.getLayoutParams();
                if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams2;
                    marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, (int) context2.getResources().getDimension(R.dimen.sud_progress_bar_margin_top), marginLayoutParams2.rightMargin, (int) context2.getResources().getDimension(R.dimen.sud_progress_bar_margin_bottom));
                }
            }
        }
        TemplateLayout templateLayout3 = ((ProfileMixin) getMixin(ProfileMixin.class)).templateLayout;
        if (PartnerStyleHelper.shouldApplyPartnerResource(templateLayout3)) {
            ImageView imageView = (ImageView) templateLayout3.findManagedViewById(R.id.sud_account_avatar);
            TextView textView2 = (TextView) templateLayout3.findManagedViewById(R.id.sud_account_name);
            LinearLayout linearLayout = (LinearLayout) templateLayout3.findManagedViewById(R.id.sud_layout_profile);
            LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(templateLayout3.findManagedViewById(R.id.sud_layout_header));
            if (imageView != null && textView2 != null) {
                Context context3 = imageView.getContext();
                ViewGroup.LayoutParams layoutParams3 = imageView.getLayoutParams();
                if (layoutParams3 instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) layoutParams3;
                    marginLayoutParams3.setMargins(marginLayoutParams3.leftMargin, marginLayoutParams3.topMargin, (int) PartnerConfigHelper.get(context3).getDimension(context3, PartnerConfig.CONFIG_ACCOUNT_AVATAR_MARGIN_END, 0.0f), marginLayoutParams3.bottomMargin);
                }
                imageView.setMaxHeight((int) PartnerConfigHelper.get(context3).getDimension(context3, PartnerConfig.CONFIG_ACCOUNT_AVATAR_SIZE, context3.getResources().getDimension(R.dimen.sud_account_avatar_max_height)));
                textView2.setTextSize(0, (int) PartnerConfigHelper.get(context3).getDimension(context3, PartnerConfig.CONFIG_ACCOUNT_NAME_TEXT_SIZE, context3.getResources().getDimension(R.dimen.sud_account_name_text_size)));
                Typeface create = Typeface.create(PartnerConfigHelper.get(context3).getString(context3, PartnerConfig.CONFIG_ACCOUNT_NAME_FONT_FAMILY), 0);
                if (create != null) {
                    textView2.setTypeface(create);
                }
                linearLayout.setGravity(PartnerStyleHelper.getLayoutGravity(linearLayout.getContext()));
            }
        }
        FloatingBackButtonMixin floatingBackButtonMixin = (FloatingBackButtonMixin) getMixin(FloatingBackButtonMixin.class);
        if (PartnerStyleHelper.shouldApplyPartnerResource(floatingBackButtonMixin.templateLayout)) {
            TemplateLayout templateLayout4 = floatingBackButtonMixin.templateLayout;
            if (((FrameLayout) templateLayout4.findManagedViewById(R.id.sud_layout_floating_back_button_container)) != null) {
                LayoutStyler.applyPartnerCustomizationExtraPaddingStyle((FrameLayout) templateLayout4.findManagedViewById(R.id.sud_layout_floating_back_button_container));
                FrameLayout frameLayout = (FrameLayout) templateLayout4.findManagedViewById(R.id.sud_layout_floating_back_button_container);
                if (frameLayout != null) {
                    Context context4 = frameLayout.getContext();
                    ViewGroup.LayoutParams layoutParams4 = frameLayout.getLayoutParams();
                    int dimension = (int) context4.getResources().getDimension(R.dimen.sud_glif_expressive_back_button_height);
                    PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_ICON_SIZE;
                    int dimension2 = PartnerConfigHelper.get(context4).isPartnerConfigAvailable(partnerConfig3) ? (int) PartnerConfigHelper.get(context4).getDimension(context4, partnerConfig3, 0.0f) : 0;
                    int i3 = dimension2 > dimension ? dimension2 - dimension : 0;
                    ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) layoutParams4;
                    PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_ICON_MARGIN_TOP;
                    int i4 = marginLayoutParams4.topMargin;
                    if (PartnerConfigHelper.get(context4).isPartnerConfigAvailable(partnerConfig4)) {
                        i4 = (int) PartnerConfigHelper.get(context4).getDimension(context4, partnerConfig4, 0.0f);
                    }
                    if (i3 != 0) {
                        i4 += i3 / 2;
                    }
                    if (i4 != marginLayoutParams4.topMargin) {
                        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-2, -2);
                        layoutParams5.setMargins(marginLayoutParams4.leftMargin, i4, marginLayoutParams4.rightMargin, marginLayoutParams4.bottomMargin);
                        frameLayout.setLayoutParams(layoutParams5);
                    }
                }
            }
        }
        TextView textView3 = (TextView) findManagedViewById(R.id.sud_layout_description);
        if (textView3 != null) {
            if (this.applyPartnerHeavyThemeResource) {
                TextViewPartnerStyler.applyPartnerCustomizationStyle(textView3, new TextViewPartnerStyler.TextPartnerConfigs(PartnerConfig.CONFIG_DESCRIPTION_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_LINK_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_TEXT_SIZE, PartnerConfig.CONFIG_DESCRIPTION_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_FONT_WEIGHT, PartnerConfig.CONFIG_DESCRIPTION_LINK_FONT_FAMILY, null, null, PartnerStyleHelper.getLayoutGravity(textView3.getContext())));
            } else if (shouldApplyPartnerResource()) {
                TextViewPartnerStyler.TextPartnerConfigs textPartnerConfigs = new TextViewPartnerStyler.TextPartnerConfigs(null, null, null, null, null, null, null, null, PartnerStyleHelper.getLayoutGravity(textView3.getContext()));
                TextViewPartnerStyler.applyPartnerCustomizationVerticalMargins(textView3, textPartnerConfigs);
                textView3.setGravity(textPartnerConfigs.textGravity);
            }
        }
    }

    @Override // com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            if (isEmbeddedActivityOnePaneEnabled(getContext())) {
                i = isGlifExpressiveEnabled() ? R.layout.sud_glif_expressive_embedded_template : R.layout.sud_glif_embedded_template;
            } else if (isGlifExpressiveEnabled()) {
                i = R.layout.sud_glif_expressive_template;
            } else {
                Context context = getContext();
                int i2 = ForceTwoPaneHelper.$r8$clinit;
                i = PartnerConfigHelper.isForceTwoPaneEnabled(context) ? R.layout.sud_glif_template_two_pane : R.layout.sud_glif_template;
            }
        }
        return inflateTemplate(layoutInflater, R.style.SudThemeGlif_Light, i);
    }

    public final void onScrolling(boolean z) {
        LinearLayout linearLayout;
        FooterBarMixin footerBarMixin = (FooterBarMixin) getMixin(FooterBarMixin.class);
        SystemNavBarMixin systemNavBarMixin = (SystemNavBarMixin) getMixin(SystemNavBarMixin.class);
        if (footerBarMixin == null || (linearLayout = footerBarMixin.buttonContainer) == null) {
            return;
        }
        if (z) {
            linearLayout.setBackgroundColor(0);
            if (systemNavBarMixin != null) {
                systemNavBarMixin.setSystemNavBarBackground(0);
                return;
            }
            return;
        }
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.sudFooterBackgroundColor, typedValue, true);
        linearLayout.setBackgroundColor(typedValue.data);
        if (systemNavBarMixin != null) {
            TypedValue typedValue2 = new TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.sudFooterBackgroundColor, typedValue2, true);
            systemNavBarMixin.setSystemNavBarBackground(typedValue2.data);
        }
    }

    public final boolean shouldApplyPartnerHeavyThemeResource() {
        if (this.applyPartnerHeavyThemeResource) {
            return true;
        }
        return shouldApplyPartnerResource() && PartnerConfigHelper.shouldApplyExtendedPartnerConfig(getContext());
    }

    public final void tryApplyPartnerCustomizationContentPaddingTopStyle(View view) {
        int dimension;
        Context context = view.getContext();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_CONTENT_PADDING_TOP;
        boolean isPartnerConfigAvailable = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
        if (shouldApplyPartnerResource() && isPartnerConfigAvailable && (dimension = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f)) != view.getPaddingTop()) {
            view.setPadding(view.getPaddingStart(), dimension, view.getPaddingEnd(), view.getPaddingBottom());
        }
    }

    public final void updateBackground() {
        int defaultColor;
        if (findManagedViewById(R.id.suc_layout_status) != null) {
            ColorStateList colorStateList = this.backgroundBaseColor;
            if (colorStateList != null) {
                defaultColor = colorStateList.getDefaultColor();
            } else {
                ColorStateList colorStateList2 = this.primaryColor;
                defaultColor = colorStateList2 != null ? colorStateList2.getDefaultColor() : 0;
            }
            ((StatusBarMixin) getMixin(StatusBarMixin.class)).setStatusBarBackground(this.backgroundPatterned ? new GlifPatternDrawable(defaultColor) : new ColorDrawable(defaultColor));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateLandscapeMiddleHorizontalSpacing() {
        /*
            r8 = this;
            android.content.res.Resources r0 = r8.getResources()
            r1 = 2131171445(0x7f071875, float:1.7957277E38)
            int r0 = r0.getDimensionPixelSize(r1)
            boolean r1 = r8.shouldApplyPartnerResource()
            r2 = 0
            if (r1 == 0) goto L33
            android.content.Context r1 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r1 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r1)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r3 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_LAND_MIDDLE_HORIZONTAL_SPACING
            boolean r1 = r1.isPartnerConfigAvailable(r3)
            if (r1 == 0) goto L33
            android.content.Context r0 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r0 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r0)
            android.content.Context r1 = r8.getContext()
            float r0 = r0.getDimension(r1, r3, r2)
            int r0 = (int) r0
        L33:
            r1 = 2131365221(0x7f0a0d65, float:1.8350301E38)
            android.view.View r1 = r8.findManagedViewById(r1)
            r3 = 0
            if (r1 == 0) goto L8e
            boolean r4 = r8.shouldApplyPartnerResource()
            if (r4 == 0) goto L65
            android.content.Context r4 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r4 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r4)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r5 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_LAYOUT_MARGIN_END
            boolean r4 = r4.isPartnerConfigAvailable(r5)
            if (r4 == 0) goto L65
            android.content.Context r4 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r4 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r4)
            android.content.Context r6 = r8.getContext()
            float r4 = r4.getDimension(r6, r5, r2)
            int r4 = (int) r4
            goto L7c
        L65:
            android.content.Context r4 = r8.getContext()
            r5 = 2130970410(0x7f04072a, float:1.754953E38)
            int[] r5 = new int[]{r5}
            android.content.res.TypedArray r4 = r4.obtainStyledAttributes(r5)
            int r5 = r4.getDimensionPixelSize(r3, r3)
            r4.recycle()
            r4 = r5
        L7c:
            int r5 = r0 / 2
            int r5 = r5 - r4
            int r4 = r1.getPaddingStart()
            int r6 = r1.getPaddingTop()
            int r7 = r1.getPaddingBottom()
            r1.setPadding(r4, r6, r5, r7)
        L8e:
            r4 = 2131365220(0x7f0a0d64, float:1.83503E38)
            android.view.View r4 = r8.findManagedViewById(r4)
            if (r4 == 0) goto Leb
            boolean r5 = r8.shouldApplyPartnerResource()
            if (r5 == 0) goto Lbf
            android.content.Context r5 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r5)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r6 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_LAYOUT_MARGIN_START
            boolean r5 = r5.isPartnerConfigAvailable(r6)
            if (r5 == 0) goto Lbf
            android.content.Context r5 = r8.getContext()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r5)
            android.content.Context r8 = r8.getContext()
            float r8 = r5.getDimension(r8, r6, r2)
            int r8 = (int) r8
            goto Ld6
        Lbf:
            android.content.Context r8 = r8.getContext()
            r2 = 2130970411(0x7f04072b, float:1.7549531E38)
            int[] r2 = new int[]{r2}
            android.content.res.TypedArray r8 = r8.obtainStyledAttributes(r2)
            int r2 = r8.getDimensionPixelSize(r3, r3)
            r8.recycle()
            r8 = r2
        Ld6:
            if (r1 == 0) goto Ldc
            int r0 = r0 / 2
            int r3 = r0 - r8
        Ldc:
            int r8 = r4.getPaddingTop()
            int r0 = r4.getPaddingEnd()
            int r1 = r4.getPaddingBottom()
            r4.setPadding(r3, r8, r0, r1)
        Leb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.GlifLayout.updateLandscapeMiddleHorizontalSpacing():void");
    }

    public GlifLayout(Context context, int i) {
        this(context, i, 0);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.google.android.setupdesign.GlifLayout$1] */
    public GlifLayout(Context context, int i, int i2) {
        super(context, i, i2);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        this.onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.google.android.setupdesign.GlifLayout.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                if (GlifLayout.this.getScrollView() != null) {
                    GlifLayout.this.onScrolling(!r0.canScrollVertically(1));
                }
            }
        };
        init$1(null, R.attr.sudLayoutTheme);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.google.android.setupdesign.GlifLayout$1] */
    public GlifLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        this.onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.google.android.setupdesign.GlifLayout.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                if (GlifLayout.this.getScrollView() != null) {
                    GlifLayout.this.onScrolling(!r0.canScrollVertically(1));
                }
            }
        };
        init$1(attributeSet, R.attr.sudLayoutTheme);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.google.android.setupdesign.GlifLayout$1] */
    public GlifLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        this.onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.google.android.setupdesign.GlifLayout.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                if (GlifLayout.this.getScrollView() != null) {
                    GlifLayout.this.onScrolling(!r0.canScrollVertically(1));
                }
            }
        };
        init$1(attributeSet, i);
    }
}
