package com.google.android.setupdesign.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LayoutStyler {
    private LayoutStyler() {
    }

    public static void applyPartnerCustomizationExtraPaddingStyle(View view) {
        int paddingEnd;
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
        boolean isPartnerConfigAvailable = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
        PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
        boolean isPartnerConfigAvailable2 = partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2);
        if (PartnerStyleHelper.shouldApplyPartnerResource(view)) {
            if (isPartnerConfigAvailable || isPartnerConfigAvailable2) {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.sudMarginStart, R.attr.sudMarginEnd});
                int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, 0);
                int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(1, 0);
                obtainStyledAttributes.recycle();
                int dimension = isPartnerConfigAvailable ? ((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f)) - dimensionPixelSize : view.getPaddingStart();
                if (isPartnerConfigAvailable2) {
                    paddingEnd = ((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, 0.0f)) - dimensionPixelSize2;
                    if (view.getId() == R.id.sud_layout_content) {
                        paddingEnd = ((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f)) - dimensionPixelSize2;
                    }
                } else {
                    paddingEnd = view.getPaddingEnd();
                    if (view.getId() == R.id.sud_layout_content) {
                        paddingEnd = view.getPaddingStart();
                    }
                }
                if (dimension == view.getPaddingStart() && paddingEnd == view.getPaddingEnd()) {
                    return;
                }
                if (view.getId() != R.id.sud_layout_content) {
                    view.setPadding(dimension, view.getPaddingTop(), paddingEnd, view.getPaddingBottom());
                    return;
                }
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : new ViewGroup.MarginLayoutParams(layoutParams);
                marginLayoutParams.setMargins(dimension, marginLayoutParams.topMargin, paddingEnd, marginLayoutParams.bottomMargin);
            }
        }
    }

    public static void applyPartnerCustomizationLayoutPaddingStyle(View view) {
        Context context = view.getContext();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_LAYOUT_MARGIN_START;
        boolean isPartnerConfigAvailable = partnerConfigHelper.isPartnerConfigAvailable(partnerConfig);
        PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_LAYOUT_MARGIN_END;
        boolean isPartnerConfigAvailable2 = partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2);
        if (PartnerStyleHelper.shouldApplyPartnerResource(view)) {
            if (isPartnerConfigAvailable || isPartnerConfigAvailable2) {
                int dimension = isPartnerConfigAvailable ? (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f) : view.getPaddingStart();
                int dimension2 = isPartnerConfigAvailable2 ? (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, 0.0f) : view.getPaddingEnd();
                if (dimension == view.getPaddingStart() && dimension2 == view.getPaddingEnd()) {
                    return;
                }
                view.setPadding(dimension, view.getPaddingTop(), dimension2, view.getPaddingBottom());
            }
        }
    }
}
