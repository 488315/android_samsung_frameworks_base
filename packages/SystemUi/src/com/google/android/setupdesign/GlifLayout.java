package com.google.android.setupdesign;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.window.embedding.ActivityEmbeddingController;
import com.android.systemui.R;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.StatusBarMixin;
import com.google.android.setupdesign.template.DescriptionMixin;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class GlifLayout extends PartnerCustomizationLayout {
    public boolean applyPartnerHeavyThemeResource;
    public ColorStateList backgroundBaseColor;
    public boolean backgroundPatterned;
    public ColorStateList primaryColor;

    public GlifLayout(Context context) {
        this(context, 0, 0);
    }

    private void init(AttributeSet attributeSet, int i) {
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
        RequireScrollMixin requireScrollMixin = new RequireScrollMixin(this);
        registerMixin(RequireScrollMixin.class, requireScrollMixin);
        View findManagedViewById = findManagedViewById(R.id.sud_scroll_view);
        ScrollView scrollView = findManagedViewById instanceof ScrollView ? (ScrollView) findManagedViewById : null;
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
        View findManagedViewById2 = findManagedViewById(R.id.sud_layout_content);
        if (findManagedViewById2 != null) {
            if (shouldApplyPartnerResource()) {
                LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(findManagedViewById2);
            }
            if (!(this instanceof GlifPreferenceLayout)) {
                tryApplyPartnerCustomizationContentPaddingTopStyle(findManagedViewById2);
            }
        }
        updateLandscapeMiddleHorizontalSpacing();
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
        obtainStyledAttributes.recycle();
    }

    public static boolean isEmbeddedActivityOnePaneEnabled(Context context) {
        return PartnerConfigHelper.isEmbeddedActivityOnePaneEnabled(context) && ActivityEmbeddingController.getInstance(context).isActivityEmbedded(PartnerCustomizationLayout.lookupActivityFromContext(context));
    }

    @Override // com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        int i;
        ViewGroup.LayoutParams layoutParams;
        PartnerConfigHelper partnerConfigHelper;
        PartnerConfig partnerConfig;
        int dimension;
        int i2;
        super.onFinishInflate();
        IconMixin iconMixin = (IconMixin) getMixin(IconMixin.class);
        if (PartnerStyleHelper.shouldApplyPartnerResource(iconMixin.templateLayout)) {
            final ImageView view = iconMixin.getView();
            FrameLayout frameLayout = (FrameLayout) iconMixin.templateLayout.findManagedViewById(R.id.sud_layout_icon_container);
            if (view != null && frameLayout != null) {
                Context context = view.getContext();
                int layoutGravity = PartnerStyleHelper.getLayoutGravity(context);
                if (layoutGravity != 0 && (view.getLayoutParams() instanceof FrameLayout.LayoutParams)) {
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view.getLayoutParams();
                    layoutParams2.gravity = layoutGravity;
                    view.setLayoutParams(layoutParams2);
                }
                PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_ICON_SIZE;
                if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                    view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.setupdesign.util.HeaderAreaStyler$1
                        @Override // android.view.ViewTreeObserver.OnPreDrawListener
                        public final boolean onPreDraw() {
                            view.getViewTreeObserver().removeOnPreDrawListener(this);
                            if (view.getDrawable() == null || (view.getDrawable() instanceof VectorDrawable) || (view.getDrawable() instanceof VectorDrawableCompat)) {
                                return true;
                            }
                            String str = Build.TYPE;
                            if (!str.equals("userdebug") && !str.equals("eng")) {
                                return true;
                            }
                            Log.w("HeaderAreaStyler", "To achieve scaling icon in SetupDesign lib, should use vector drawable icon from " + view.getContext().getPackageName());
                            return true;
                        }
                    });
                    ViewGroup.LayoutParams layoutParams3 = view.getLayoutParams();
                    layoutParams3.height = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, 0.0f);
                    layoutParams3.width = -2;
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    Drawable drawable = view.getDrawable();
                    if (drawable != null && drawable.getIntrinsicWidth() > drawable.getIntrinsicHeight() * 2 && (i2 = layoutParams3.height) > (dimension = (int) context.getResources().getDimension(R.dimen.sud_horizontal_icon_height))) {
                        i = i2 - dimension;
                        layoutParams3.height = dimension;
                        layoutParams = frameLayout.getLayoutParams();
                        partnerConfigHelper = PartnerConfigHelper.get(context);
                        partnerConfig = PartnerConfig.CONFIG_ICON_MARGIN_TOP;
                        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig) && (layoutParams instanceof ViewGroup.MarginLayoutParams)) {
                            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, ((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f)) + i, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                        }
                    }
                }
                i = 0;
                layoutParams = frameLayout.getLayoutParams();
                partnerConfigHelper = PartnerConfigHelper.get(context);
                partnerConfig = PartnerConfig.CONFIG_ICON_MARGIN_TOP;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams;
                    marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, ((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f)) + i, marginLayoutParams2.rightMargin, marginLayoutParams2.bottomMargin);
                }
            }
        }
        ((HeaderMixin) getMixin(HeaderMixin.class)).tryApplyPartnerCustomizationStyle();
        TemplateLayout templateLayout = ((DescriptionMixin) getMixin(DescriptionMixin.class)).templateLayout;
        TextView textView = (TextView) templateLayout.findManagedViewById(R.id.sud_layout_subtitle);
        if (textView != null && PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            TextViewPartnerStyler.applyPartnerCustomizationStyle(textView, new TextViewPartnerStyler.TextPartnerConfigs(PartnerConfig.CONFIG_DESCRIPTION_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_LINK_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_TEXT_SIZE, PartnerConfig.CONFIG_DESCRIPTION_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_LINK_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_TOP, PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_BOTTOM, PartnerStyleHelper.getLayoutGravity(textView.getContext())));
        }
        ProgressBarMixin progressBarMixin = (ProgressBarMixin) getMixin(ProgressBarMixin.class);
        ProgressBar peekProgressBar = progressBarMixin.peekProgressBar();
        if (progressBarMixin.useBottomProgressBar && peekProgressBar != null) {
            TemplateLayout templateLayout2 = progressBarMixin.templateLayout;
            if (!(templateLayout2 instanceof GlifLayout) ? false : ((GlifLayout) templateLayout2).shouldApplyPartnerHeavyThemeResource()) {
                Context context2 = peekProgressBar.getContext();
                ViewGroup.LayoutParams layoutParams4 = peekProgressBar.getLayoutParams();
                if (layoutParams4 instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) layoutParams4;
                    int i3 = marginLayoutParams3.topMargin;
                    PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(context2);
                    PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_TOP;
                    if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
                        i3 = (int) PartnerConfigHelper.get(context2).getDimension(context2, partnerConfig3, context2.getResources().getDimension(R.dimen.sud_progress_bar_margin_top));
                    }
                    int i4 = marginLayoutParams3.bottomMargin;
                    PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(context2);
                    PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_BOTTOM;
                    if (partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)) {
                        i4 = (int) PartnerConfigHelper.get(context2).getDimension(context2, partnerConfig4, context2.getResources().getDimension(R.dimen.sud_progress_bar_margin_bottom));
                    }
                    if (i3 != marginLayoutParams3.topMargin || i4 != marginLayoutParams3.bottomMargin) {
                        marginLayoutParams3.setMargins(marginLayoutParams3.leftMargin, i3, marginLayoutParams3.rightMargin, i4);
                    }
                }
            } else {
                Context context3 = peekProgressBar.getContext();
                ViewGroup.LayoutParams layoutParams5 = peekProgressBar.getLayoutParams();
                if (layoutParams5 instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) layoutParams5;
                    marginLayoutParams4.setMargins(marginLayoutParams4.leftMargin, (int) context3.getResources().getDimension(R.dimen.sud_progress_bar_margin_top), marginLayoutParams4.rightMargin, (int) context3.getResources().getDimension(R.dimen.sud_progress_bar_margin_bottom));
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
                Context context4 = imageView.getContext();
                ViewGroup.LayoutParams layoutParams6 = imageView.getLayoutParams();
                if (layoutParams6 instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams5 = (ViewGroup.MarginLayoutParams) layoutParams6;
                    marginLayoutParams5.setMargins(marginLayoutParams5.leftMargin, marginLayoutParams5.topMargin, (int) PartnerConfigHelper.get(context4).getDimension(context4, PartnerConfig.CONFIG_ACCOUNT_AVATAR_MARGIN_END, 0.0f), marginLayoutParams5.bottomMargin);
                }
                imageView.setMaxHeight((int) PartnerConfigHelper.get(context4).getDimension(context4, PartnerConfig.CONFIG_ACCOUNT_AVATAR_SIZE, context4.getResources().getDimension(R.dimen.sud_account_avatar_max_height)));
                textView2.setTextSize(0, (int) PartnerConfigHelper.get(context4).getDimension(context4, PartnerConfig.CONFIG_ACCOUNT_NAME_TEXT_SIZE, context4.getResources().getDimension(R.dimen.sud_account_name_text_size)));
                Typeface create = Typeface.create(PartnerConfigHelper.get(context4).getString(context4, PartnerConfig.CONFIG_ACCOUNT_NAME_FONT_FAMILY), 0);
                if (create != null) {
                    textView2.setTypeface(create);
                }
                linearLayout.setGravity(PartnerStyleHelper.getLayoutGravity(linearLayout.getContext()));
            }
        }
        TextView textView3 = (TextView) findManagedViewById(R.id.sud_layout_description);
        if (textView3 != null) {
            if (this.applyPartnerHeavyThemeResource) {
                TextViewPartnerStyler.applyPartnerCustomizationStyle(textView3, new TextViewPartnerStyler.TextPartnerConfigs(PartnerConfig.CONFIG_DESCRIPTION_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_LINK_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_TEXT_SIZE, PartnerConfig.CONFIG_DESCRIPTION_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_LINK_FONT_FAMILY, null, null, PartnerStyleHelper.getLayoutGravity(textView3.getContext())));
            } else if (shouldApplyPartnerResource()) {
                TextViewPartnerStyler.TextPartnerConfigs textPartnerConfigs = new TextViewPartnerStyler.TextPartnerConfigs(null, null, null, null, null, null, null, PartnerStyleHelper.getLayoutGravity(textView3.getContext()));
                TextViewPartnerStyler.applyPartnerCustomizationVerticalMargins(textView3, textPartnerConfigs);
                textView3.setGravity(textPartnerConfigs.textGravity);
            }
        }
    }

    @Override // com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = isEmbeddedActivityOnePaneEnabled(getContext()) ? R.layout.sud_glif_embedded_template : R.layout.sud_glif_template;
        }
        return inflateTemplate(layoutInflater, 2132018010, i);
    }

    public final boolean shouldApplyPartnerHeavyThemeResource() {
        return this.applyPartnerHeavyThemeResource || (shouldApplyPartnerResource() && PartnerConfigHelper.shouldApplyExtendedPartnerConfig(getContext()));
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
    */
    public final void updateLandscapeMiddleHorizontalSpacing() {
        int i;
        int i2;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sud_glif_land_middle_horizontal_spacing);
        if (shouldApplyPartnerResource()) {
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(getContext());
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAND_MIDDLE_HORIZONTAL_SPACING;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                dimensionPixelSize = (int) PartnerConfigHelper.get(getContext()).getDimension(getContext(), partnerConfig, 0.0f);
            }
        }
        View findManagedViewById = findManagedViewById(R.id.sud_landscape_header_area);
        if (findManagedViewById != null) {
            if (shouldApplyPartnerResource()) {
                PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(getContext());
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
                if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                    i2 = (int) PartnerConfigHelper.get(getContext()).getDimension(getContext(), partnerConfig2, 0.0f);
                    findManagedViewById.setPadding(findManagedViewById.getPaddingStart(), findManagedViewById.getPaddingTop(), (dimensionPixelSize / 2) - i2, findManagedViewById.getPaddingBottom());
                }
            }
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.sudMarginEnd});
            int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            obtainStyledAttributes.recycle();
            i2 = dimensionPixelSize2;
            findManagedViewById.setPadding(findManagedViewById.getPaddingStart(), findManagedViewById.getPaddingTop(), (dimensionPixelSize / 2) - i2, findManagedViewById.getPaddingBottom());
        }
        View findManagedViewById2 = findManagedViewById(R.id.sud_landscape_content_area);
        if (findManagedViewById2 != null) {
            if (shouldApplyPartnerResource()) {
                PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(getContext());
                PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
                if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
                    i = (int) PartnerConfigHelper.get(getContext()).getDimension(getContext(), partnerConfig3, 0.0f);
                    findManagedViewById2.setPadding(findManagedViewById != null ? (dimensionPixelSize / 2) - i : 0, findManagedViewById2.getPaddingTop(), findManagedViewById2.getPaddingEnd(), findManagedViewById2.getPaddingBottom());
                }
            }
            TypedArray obtainStyledAttributes2 = getContext().obtainStyledAttributes(new int[]{R.attr.sudMarginStart});
            int dimensionPixelSize3 = obtainStyledAttributes2.getDimensionPixelSize(0, 0);
            obtainStyledAttributes2.recycle();
            i = dimensionPixelSize3;
            findManagedViewById2.setPadding(findManagedViewById != null ? (dimensionPixelSize / 2) - i : 0, findManagedViewById2.getPaddingTop(), findManagedViewById2.getPaddingEnd(), findManagedViewById2.getPaddingBottom());
        }
    }

    public GlifLayout(Context context, int i) {
        this(context, i, 0);
    }

    public GlifLayout(Context context, int i, int i2) {
        super(context, i, i2);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        init(null, R.attr.sudLayoutTheme);
    }

    public GlifLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        init(attributeSet, R.attr.sudLayoutTheme);
    }

    public GlifLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.backgroundPatterned = true;
        this.applyPartnerHeavyThemeResource = false;
        init(attributeSet, i);
    }
}
