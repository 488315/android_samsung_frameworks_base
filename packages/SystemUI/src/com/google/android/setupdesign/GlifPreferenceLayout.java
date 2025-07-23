package com.google.android.setupdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.ForceTwoPaneHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.template.RecyclerMixin;
import com.google.android.setupdesign.util.ThemeHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GlifPreferenceLayout extends GlifRecyclerLayout {
    public GlifPreferenceLayout(Context context) {
        super(context);
    }

    @Override // com.google.android.setupdesign.GlifRecyclerLayout, com.google.android.setupdesign.GlifLayout, com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public final ViewGroup findContainer(int i) {
        if (i == 0) {
            i = R.id.sud_layout_content;
        }
        return super.findContainer(i);
    }

    @Override // com.google.android.setupdesign.GlifRecyclerLayout, com.google.android.setupdesign.GlifLayout, com.google.android.setupcompat.PartnerCustomizationLayout, com.google.android.setupcompat.internal.TemplateLayout
    public final View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        if (i == 0) {
            if (GlifLayout.isEmbeddedActivityOnePaneEnabled(getContext())) {
                i = isGlifExpressiveEnabled() ? R.layout.sud_glif_expressive_preference_embedded_template : R.layout.sud_glif_preference_embedded_template;
            } else if (isGlifExpressiveEnabled()) {
                i = R.layout.sud_glif_expressive_preference_template;
            } else {
                Context context = getContext();
                int i2 = ForceTwoPaneHelper.$r8$clinit;
                i = PartnerConfigHelper.isForceTwoPaneEnabled(context) ? R.layout.sud_glif_preference_template_two_pane : R.layout.sud_glif_preference_template;
            }
        }
        return super.onInflateTemplate(layoutInflater, i);
    }

    @Override // com.google.android.setupdesign.GlifRecyclerLayout, com.google.android.setupcompat.internal.TemplateLayout
    public final void onTemplateInflated() {
        LayoutInflater from = LayoutInflater.from(getContext());
        Context context = getContext();
        int i = ForceTwoPaneHelper.$r8$clinit;
        int i2 = PartnerConfigHelper.isForceTwoPaneEnabled(context) ? R.layout.sud_glif_preference_recycler_view_compat_two_pane : R.layout.sud_glif_preference_recycler_view;
        Context context2 = getContext();
        Logger logger = ThemeHelper.LOG;
        if (PartnerConfigHelper.isGlifExpressiveEnabled(context2)) {
            i2 = R.layout.sud_glif_expressive_preference_recycler_view;
        }
        this.recyclerMixin = new RecyclerMixin(this, (RecyclerView) from.inflate(i2, (ViewGroup) this, false));
    }

    public GlifPreferenceLayout(Context context, int i, int i2) {
        super(context, i, i2);
    }

    public GlifPreferenceLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GlifPreferenceLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
