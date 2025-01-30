package com.google.android.setupdesign.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.DividerItemDecoration;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.items.ItemHierarchy;
import com.google.android.setupdesign.items.ItemInflater;
import com.google.android.setupdesign.items.RecyclerItemAdapter;
import com.google.android.setupdesign.util.DrawableLayoutDirectionHelper;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.view.HeaderRecyclerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RecyclerMixin implements Mixin {
    public Drawable defaultDivider;
    public Drawable divider;
    public final DividerItemDecoration dividerDecoration;
    public int dividerInsetEnd;
    public int dividerInsetStart;
    public final View header;
    public final boolean isDividerDisplay;
    public final RecyclerView recyclerView;
    public final TemplateLayout templateLayout;

    public RecyclerMixin(TemplateLayout templateLayout, RecyclerView recyclerView) {
        this.isDividerDisplay = true;
        this.templateLayout = templateLayout;
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(templateLayout.getContext());
        this.dividerDecoration = dividerItemDecoration;
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(templateLayout.getContext()));
        if (recyclerView instanceof HeaderRecyclerView) {
            this.header = ((HeaderRecyclerView) recyclerView).header;
        }
        Context context = templateLayout.getContext();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.sudDividerShown, typedValue, true);
        boolean z = typedValue.data != 0;
        if (PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(recyclerView.getContext());
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_ITEMS_DIVIDER_SHOWN;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                z = PartnerConfigHelper.get(recyclerView.getContext()).getBoolean(recyclerView.getContext(), partnerConfig, z);
            }
        }
        this.isDividerDisplay = z;
        if (z) {
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
    }

    public final void parseAttributes(AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        TemplateLayout templateLayout = this.templateLayout;
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
                this.recyclerView.setAdapter(recyclerItemAdapter);
            } catch (Throwable th) {
                xml.close();
                throw th;
            }
        }
        if (!this.isDividerDisplay) {
            obtainStyledAttributes.recycle();
            return;
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, -1);
        if (dimensionPixelSize != -1) {
            this.dividerInsetStart = dimensionPixelSize;
            this.dividerInsetEnd = 0;
            updateDivider();
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
            this.dividerInsetStart = dimensionPixelSize2;
            this.dividerInsetEnd = dimensionPixelSize3;
            updateDivider();
        }
        obtainStyledAttributes.recycle();
    }

    public final void updateDivider() {
        TemplateLayout templateLayout = this.templateLayout;
        if (templateLayout.isLayoutDirectionResolved()) {
            Drawable drawable = this.defaultDivider;
            DividerItemDecoration dividerItemDecoration = this.dividerDecoration;
            if (drawable == null) {
                this.defaultDivider = dividerItemDecoration.divider;
            }
            InsetDrawable createRelativeInsetDrawable = DrawableLayoutDirectionHelper.createRelativeInsetDrawable(this.defaultDivider, this.dividerInsetStart, this.dividerInsetEnd, templateLayout);
            this.divider = createRelativeInsetDrawable;
            dividerItemDecoration.getClass();
            dividerItemDecoration.dividerIntrinsicHeight = createRelativeInsetDrawable.getIntrinsicHeight();
            dividerItemDecoration.divider = createRelativeInsetDrawable;
        }
    }
}
