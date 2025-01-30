package com.google.android.setupdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.google.android.setupdesign.template.ListMixin;
import com.google.android.setupdesign.template.ListViewScrollHandlingDelegate;
import com.google.android.setupdesign.template.RequireScrollMixin;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class GlifListLayout extends GlifLayout {
    public ListMixin listMixin;

    public GlifListLayout(Context context) {
        this(context, 0, 0);
    }

    private void init(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        ListMixin listMixin = new ListMixin(this, attributeSet, i);
        this.listMixin = listMixin;
        registerMixin(ListMixin.class, listMixin);
        RequireScrollMixin requireScrollMixin = (RequireScrollMixin) getMixin(RequireScrollMixin.class);
        new ListViewScrollHandlingDelegate(requireScrollMixin, this.listMixin.getListViewInternal());
        requireScrollMixin.getClass();
        View findViewById = findViewById(R.id.sud_landscape_content_area);
        if (findViewById != null) {
            tryApplyPartnerCustomizationContentPaddingTopStyle(findViewById);
        }
        updateLandscapeMiddleHorizontalSpacing();
    }

    @Override // com.google.android.setupdesign.GlifLayout, com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public final ViewGroup findContainer(int i) {
        if (i == 0) {
            i = android.R.id.list;
        }
        return super.findContainer(i);
    }

    @Override // com.google.android.setupdesign.GlifLayout, com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public final View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = GlifLayout.isEmbeddedActivityOnePaneEnabled(getContext()) ? R.layout.sud_glif_list_embedded_template : R.layout.sud_glif_list_template;
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ListMixin listMixin = this.listMixin;
        if (listMixin.divider == null) {
            listMixin.updateDivider();
        }
    }

    public GlifListLayout(Context context, int i) {
        this(context, i, 0);
    }

    public GlifListLayout(Context context, int i, int i2) {
        super(context, i, i2);
        init(null, 0);
    }

    public GlifListLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public GlifListLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }
}
