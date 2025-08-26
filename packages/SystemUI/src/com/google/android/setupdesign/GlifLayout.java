package com.google.android.setupdesign;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
        boolean zIsEmbeddedActivityOnePaneEnabled = PartnerConfigHelper.isEmbeddedActivityOnePaneEnabled(context);
        ActivityEmbeddingController activityEmbeddingController = ActivityEmbeddingController.getInstance(context);
        Activity activityLookupActivityFromContext = PartnerConfigHelper.lookupActivityFromContext(context);
        EmbeddingInterfaceCompat embeddingInterfaceCompat = ((ExtensionEmbeddingBackend) activityEmbeddingController.backend).embeddingExtension;
        boolean zIsActivityEmbedded = embeddingInterfaceCompat != null ? ((EmbeddingCompat) embeddingInterfaceCompat).embeddingExtension.isActivityEmbedded(activityLookupActivityFromContext) : false;
        LOG.getClass();
        return zIsEmbeddedActivityOnePaneEnabled && zIsActivityEmbedded;
    }

    @Override // com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    public final ScrollView getScrollView() {
        View viewFindManagedViewById = findManagedViewById(R.id.sud_scroll_view);
        if (viewFindManagedViewById instanceof ScrollView) {
            return (ScrollView) viewFindManagedViewById;
        }
        return null;
    }

    public final void init$1(AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SudGlifLayout, i, 0);
        this.applyPartnerHeavyThemeResource = shouldApplyPartnerResource() && typedArrayObtainStyledAttributes.getBoolean(4, false);
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
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(2);
        if (colorStateList != null) {
            this.primaryColor = colorStateList;
            updateBackground();
            ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(ProgressBarMixin.class);
            progressBarMixin.color = colorStateList;
            ProgressBar progressBarPeekProgressBar = progressBarMixin.peekProgressBar();
            if (progressBarPeekProgressBar != null) {
                progressBarPeekProgressBar.setIndeterminateTintList(colorStateList);
                progressBarPeekProgressBar.setProgressBackgroundTintList(colorStateList);
            }
        }
        if (shouldApplyPartnerHeavyThemeResource() && !useFullDynamicColor()) {
            getRootView().setBackgroundColor(PartnerConfigHelper.get(getContext()).getColor(getContext(), PartnerConfig.CONFIG_LAYOUT_BACKGROUND_COLOR));
        }
        View viewFindManagedViewById = findManagedViewById(R.id.sud_layout_content);
        if (viewFindManagedViewById != null) {
            if (shouldApplyPartnerResource()) {
                LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(viewFindManagedViewById);
            }
            if (!(this instanceof GlifPreferenceLayout)) {
                tryApplyPartnerCustomizationContentPaddingTopStyle(viewFindManagedViewById);
            }
        }
        updateLandscapeMiddleHorizontalSpacing();
        if (KeyboardHelper.isKeyboardFocusEnhancementEnabled(getContext())) {
            View viewFindManagedViewById2 = findManagedViewById(R.id.sud_header_scroll_view);
            if (viewFindManagedViewById2 != null) {
                viewFindManagedViewById2.setFocusable(false);
            }
            View viewFindManagedViewById3 = findManagedViewById(R.id.sud_scroll_view);
            if (viewFindManagedViewById3 != null) {
                viewFindManagedViewById3.setFocusable(false);
            }
        }
        this.backgroundBaseColor = typedArrayObtainStyledAttributes.getColorStateList(0);
        updateBackground();
        this.backgroundPatterned = typedArrayObtainStyledAttributes.getBoolean(1, true);
        updateBackground();
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(3, 0);
        if (resourceId != 0) {
            ViewStub viewStub = (ViewStub) findManagedViewById(R.id.sud_layout_sticky_header);
            viewStub.setLayoutResource(resourceId);
            viewStub.inflate();
        }
        if (PartnerConfigHelper.isGlifExpressiveEnabled(getContext())) {
            initScrollingListener();
        }
        initBackButton();
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void initBackButton() {
        if (!PartnerConfigHelper.isGlifExpressiveEnabled(getContext())) {
            LOG.atDebug("isGlifExpressiveEnabled is false");
            return;
        }
        Activity activityLookupActivityFromContext = PartnerConfigHelper.lookupActivityFromContext(getContext());
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
        final GlifLayout$$ExternalSyntheticLambda1 glifLayout$$ExternalSyntheticLambda1 = new GlifLayout$$ExternalSyntheticLambda1(activityLookupActivityFromContext);
        Button backButton2 = floatingBackButtonMixin.getBackButton();
        if (backButton2 != null) {
            backButton2.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.setupdesign.template.FloatingBackButtonMixin$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FloatingBackButtonMixin floatingBackButtonMixin2 = floatingBackButtonMixin;
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
                    GlifLayout glifLayout = this.f$0;
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
            CustomEvent customEventCreate = CustomEvent.create(MetricKey.get("SetupDesignMetrics", this.activity), persistableBundle);
            SetupMetricsLogger.logCustomEvent(getContext(), customEventCreate);
            Logger logger = LOG;
            CustomEvent.toBundle(customEventCreate).toString();
            logger.getClass();
        }
        ScrollView scrollView = getScrollView();
        if (scrollView != null) {
            scrollView.getViewTreeObserver().removeOnScrollChangedListener(this.onScrollChangedListener);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        super.onFinishInflate();
        ((IconMixin) getMixin(IconMixin.class)).tryApplyPartnerCustomizationStyle();
        ((HeaderMixin) getMixin(HeaderMixin.class)).tryApplyPartnerCustomizationStyle();
        TemplateLayout templateLayout = ((DescriptionMixin) getMixin(DescriptionMixin.class)).templateLayout;
        TextView textView = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle);
        if (textView != null && PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            TextViewPartnerStyler.applyPartnerCustomizationStyle(textView, new TextViewPartnerStyler.TextPartnerConfigs(PartnerConfig.CONFIG_DESCRIPTION_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_LINK_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_TEXT_SIZE, PartnerConfig.CONFIG_DESCRIPTION_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_FONT_WEIGHT, PartnerConfig.CONFIG_DESCRIPTION_LINK_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_TOP, PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_BOTTOM, PartnerStyleHelper.getLayoutGravity(textView.getContext())));
        }
        ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(ProgressBarMixin.class);
        ProgressBar progressBarPeekProgressBar = progressBarMixin.peekProgressBar();
        if (progressBarMixin.useBottomProgressBar && progressBarPeekProgressBar != null) {
            TemplateLayout templateLayout2 = progressBarMixin.templateLayout;
            if (!(templateLayout2 instanceof GlifLayout) ? false : ((GlifLayout) templateLayout2).shouldApplyPartnerHeavyThemeResource()) {
                Context context = progressBarPeekProgressBar.getContext();
                ViewGroup.LayoutParams layoutParams = progressBarPeekProgressBar.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    int dimension = marginLayoutParams.topMargin;
                    PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_TOP;
                    if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                        dimension = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, context.getResources().getDimension(R.dimen.sud_progress_bar_margin_top));
                    }
                    int dimension2 = marginLayoutParams.bottomMargin;
                    PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_BOTTOM;
                    if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                        dimension2 = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, context.getResources().getDimension(R.dimen.sud_progress_bar_margin_bottom));
                    }
                    if (dimension != marginLayoutParams.topMargin || dimension2 != marginLayoutParams.bottomMargin) {
                        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, dimension, marginLayoutParams.rightMargin, dimension2);
                    }
                }
            } else {
                Context context2 = progressBarPeekProgressBar.getContext();
                ViewGroup.LayoutParams layoutParams2 = progressBarPeekProgressBar.getLayoutParams();
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
                Typeface typefaceCreate = Typeface.create(PartnerConfigHelper.get(context3).getString(context3, PartnerConfig.CONFIG_ACCOUNT_NAME_FONT_FAMILY), 0);
                if (typefaceCreate != null) {
                    textView2.setTypeface(typefaceCreate);
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
                    int dimension3 = (int) context4.getResources().getDimension(R.dimen.sud_glif_expressive_back_button_height);
                    PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_ICON_SIZE;
                    int dimension4 = PartnerConfigHelper.get(context4).isPartnerConfigAvailable(partnerConfig3) ? (int) PartnerConfigHelper.get(context4).getDimension(context4, partnerConfig3, 0.0f) : 0;
                    int i = dimension4 > dimension3 ? dimension4 - dimension3 : 0;
                    ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) layoutParams4;
                    PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_ICON_MARGIN_TOP;
                    int dimension5 = marginLayoutParams4.topMargin;
                    if (PartnerConfigHelper.get(context4).isPartnerConfigAvailable(partnerConfig4)) {
                        dimension5 = (int) PartnerConfigHelper.get(context4).getDimension(context4, partnerConfig4, 0.0f);
                    }
                    if (i != 0) {
                        dimension5 += i / 2;
                    }
                    if (dimension5 != marginLayoutParams4.topMargin) {
                        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-2, -2);
                        layoutParams5.setMargins(marginLayoutParams4.leftMargin, dimension5, marginLayoutParams4.rightMargin, marginLayoutParams4.bottomMargin);
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
        boolean zIsPartnerConfigAvailable = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
        if (shouldApplyPartnerResource() && zIsPartnerConfigAvailable && (dimension = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f)) != view.getPaddingTop()) {
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateLandscapeMiddleHorizontalSpacing() throws Resources.NotFoundException {
        int dimension;
        int dimension2;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sud_glif_land_middle_horizontal_spacing);
        if (shouldApplyPartnerResource()) {
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(getContext());
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAND_MIDDLE_HORIZONTAL_SPACING;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                dimensionPixelSize = (int) PartnerConfigHelper.get(getContext()).getDimension(getContext(), partnerConfig, 0.0f);
            }
        }
        View viewFindManagedViewById = findManagedViewById(R.id.sud_landscape_header_area);
        if (viewFindManagedViewById != null) {
            if (shouldApplyPartnerResource()) {
                PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(getContext());
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
                if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                    dimension2 = (int) PartnerConfigHelper.get(getContext()).getDimension(getContext(), partnerConfig2, 0.0f);
                } else {
                    TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.sudMarginEnd});
                    int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
                    typedArrayObtainStyledAttributes.recycle();
                    dimension2 = dimensionPixelSize2;
                }
                viewFindManagedViewById.setPadding(viewFindManagedViewById.getPaddingStart(), viewFindManagedViewById.getPaddingTop(), (dimensionPixelSize / 2) - dimension2, viewFindManagedViewById.getPaddingBottom());
            }
        }
        View viewFindManagedViewById2 = findManagedViewById(R.id.sud_landscape_content_area);
        if (viewFindManagedViewById2 != null) {
            if (shouldApplyPartnerResource()) {
                PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(getContext());
                PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
                if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
                    dimension = (int) PartnerConfigHelper.get(getContext()).getDimension(getContext(), partnerConfig3, 0.0f);
                } else {
                    TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(new int[]{R.attr.sudMarginStart});
                    int dimensionPixelSize3 = typedArrayObtainStyledAttributes2.getDimensionPixelSize(0, 0);
                    typedArrayObtainStyledAttributes2.recycle();
                    dimension = dimensionPixelSize3;
                }
            }
            viewFindManagedViewById2.setPadding(viewFindManagedViewById != null ? (dimensionPixelSize / 2) - dimension : 0, viewFindManagedViewById2.getPaddingTop(), viewFindManagedViewById2.getPaddingEnd(), viewFindManagedViewById2.getPaddingBottom());
        }
    }

    public GlifLayout(Context context, int i) {
        this(context, i, 0);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.google.android.setupdesign.GlifLayout$1] */
    public GlifLayout(Context context, int i, int i2) throws Resources.NotFoundException {
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
    public GlifLayout(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
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
    public GlifLayout(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
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
