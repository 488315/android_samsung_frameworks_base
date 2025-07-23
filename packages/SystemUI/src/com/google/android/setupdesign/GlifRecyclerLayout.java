package com.google.android.setupdesign;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.ForceTwoPaneHelper;
import com.google.android.setupdesign.items.ItemHierarchy;
import com.google.android.setupdesign.items.ItemInflater;
import com.google.android.setupdesign.items.RecyclerItemAdapter;
import com.google.android.setupdesign.template.RecyclerMixin;
import com.google.android.setupdesign.template.RecyclerViewScrollHandlingDelegate;
import com.google.android.setupdesign.template.RequireScrollMixin;
import com.google.android.setupdesign.util.PartnerStyleHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GlifRecyclerLayout extends GlifLayout {
    public RecyclerMixin recyclerMixin;

    public GlifRecyclerLayout(Context context) {
        this(context, 0, 0);
    }

    @Override // com.google.android.setupdesign.GlifLayout, com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_recycler_view;
        }
        return super.findContainer(i);
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public final View findManagedViewById(int i) {
        View findViewById;
        View view = this.recyclerMixin.header;
        return (view == null || (findViewById = view.findViewById(i)) == null) ? super.findViewById(i) : findViewById;
    }

    public final void init(AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        if (isInEditMode()) {
            return;
        }
        RecyclerMixin recyclerMixin = this.recyclerMixin;
        TemplateLayout templateLayout = recyclerMixin.templateLayout;
        Context context = templateLayout.getContext();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudRecyclerMixin, i, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            ItemInflater itemInflater = new ItemInflater(context);
            XmlResourceParser xml = itemInflater.resources.getXml(resourceId);
            try {
                Object inflate = itemInflater.inflate(xml);
                xml.close();
                ItemHierarchy itemHierarchy = (ItemHierarchy) inflate;
                if (templateLayout instanceof GlifLayout) {
                    GlifLayout glifLayout = (GlifLayout) templateLayout;
                    z2 = glifLayout.shouldApplyPartnerHeavyThemeResource();
                    z = glifLayout.useFullDynamicColor();
                } else {
                    z = false;
                    z2 = false;
                }
                RecyclerItemAdapter recyclerItemAdapter = new RecyclerItemAdapter(itemHierarchy, z2, z);
                recyclerItemAdapter.setHasStableIds(obtainStyledAttributes.getBoolean(4, false));
                RecyclerView recyclerView = recyclerMixin.recyclerView;
                recyclerItemAdapter.recyclerView = recyclerView;
                recyclerView.setAdapter(recyclerItemAdapter);
            } catch (Throwable th) {
                xml.close();
                throw th;
            }
        }
        if (recyclerMixin.isDividerDisplay) {
            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, -1);
            if (dimensionPixelSize != -1) {
                recyclerMixin.dividerInsetStart = dimensionPixelSize;
                recyclerMixin.dividerInsetEnd = 0;
                recyclerMixin.updateDivider();
            } else {
                int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(3, 0);
                int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(2, 0);
                if (PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
                    PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
                    if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                        dimensionPixelSize2 = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f);
                    }
                    PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
                    PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
                    if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                        dimensionPixelSize3 = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, 0.0f);
                    }
                }
                recyclerMixin.dividerInsetStart = dimensionPixelSize2;
                recyclerMixin.dividerInsetEnd = dimensionPixelSize3;
                recyclerMixin.updateDivider();
            }
            obtainStyledAttributes.recycle();
        } else {
            obtainStyledAttributes.recycle();
        }
        registerMixin(RecyclerMixin.class, this.recyclerMixin);
        RequireScrollMixin requireScrollMixin = (RequireScrollMixin) getMixin(RequireScrollMixin.class);
        new RecyclerViewScrollHandlingDelegate(requireScrollMixin, this.recyclerMixin.recyclerView);
        requireScrollMixin.getClass();
        View findManagedViewById = findManagedViewById(R.id.sud_landscape_content_area);
        if (findManagedViewById != null) {
            tryApplyPartnerCustomizationContentPaddingTopStyle(findManagedViewById);
        }
        updateLandscapeMiddleHorizontalSpacing();
        if (PartnerConfigHelper.isGlifExpressiveEnabled(getContext())) {
            initScrollingListener();
        }
        initBackButton();
    }

    @Override // com.google.android.setupdesign.GlifLayout
    public final void initScrollingListener() {
        RecyclerView recyclerView = this.recyclerMixin.recyclerView;
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.google.android.setupdesign.GlifRecyclerLayout.1
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public final void onScrolled(RecyclerView recyclerView2, int i, int i2) {
                    GlifRecyclerLayout.this.onScrolling(!recyclerView2.canScrollVertically(1));
                }
            });
        }
    }

    @Override // com.google.android.setupdesign.GlifLayout, com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            if (GlifLayout.isEmbeddedActivityOnePaneEnabled(getContext())) {
                i = isGlifExpressiveEnabled() ? R.layout.sud_glif_expressive_recycler_embedded_template : R.layout.sud_glif_recycler_embedded_template;
            } else if (isGlifExpressiveEnabled()) {
                i = R.layout.sud_glif_expressive_recycler_template;
            } else {
                Context context = getContext();
                int i2 = ForceTwoPaneHelper.$r8$clinit;
                i = PartnerConfigHelper.isForceTwoPaneEnabled(context) ? R.layout.sud_glif_recycler_template_two_pane : R.layout.sud_glif_recycler_template;
            }
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        RecyclerMixin recyclerMixin = this.recyclerMixin;
        if (recyclerMixin.divider == null) {
            recyclerMixin.updateDivider();
        }
    }

    @Override // com.google.android.setupcompat.internal.TemplateLayout
    public void onTemplateInflated() {
        View findViewById = findViewById(R.id.sud_recycler_view);
        if (!(findViewById instanceof RecyclerView)) {
            throw new IllegalStateException("GlifRecyclerLayout should use a template with recycler view");
        }
        this.recyclerMixin = new RecyclerMixin(this, (RecyclerView) findViewById);
    }

    public GlifRecyclerLayout(Context context, int i) {
        this(context, i, 0);
    }

    public GlifRecyclerLayout(Context context, int i, int i2) {
        super(context, i, i2);
        init(null, 0);
    }

    public GlifRecyclerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public GlifRecyclerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }
}
