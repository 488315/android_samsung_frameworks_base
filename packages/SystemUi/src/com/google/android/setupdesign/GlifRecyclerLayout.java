package com.google.android.setupdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.google.android.setupdesign.template.RecyclerMixin;
import com.google.android.setupdesign.template.RecyclerViewScrollHandlingDelegate;
import com.google.android.setupdesign.template.RequireScrollMixin;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class GlifRecyclerLayout extends GlifLayout {
    public RecyclerMixin recyclerMixin;

    public GlifRecyclerLayout(Context context) {
        this(context, 0, 0);
    }

    private void init(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        this.recyclerMixin.parseAttributes(attributeSet, i);
        registerMixin(RecyclerMixin.class, this.recyclerMixin);
        RequireScrollMixin requireScrollMixin = (RequireScrollMixin) getMixin(RequireScrollMixin.class);
        new RecyclerViewScrollHandlingDelegate(requireScrollMixin, this.recyclerMixin.recyclerView);
        requireScrollMixin.getClass();
        View findManagedViewById = findManagedViewById(R.id.sud_landscape_content_area);
        if (findManagedViewById != null) {
            tryApplyPartnerCustomizationContentPaddingTopStyle(findManagedViewById);
        }
        updateLandscapeMiddleHorizontalSpacing();
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

    @Override // com.google.android.setupdesign.GlifLayout, com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            i = GlifLayout.isEmbeddedActivityOnePaneEnabled(getContext()) ? R.layout.sud_glif_recycler_embedded_template : R.layout.sud_glif_recycler_template;
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
