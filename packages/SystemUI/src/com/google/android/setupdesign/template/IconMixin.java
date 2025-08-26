package com.google.android.setupdesign.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.util.PartnerStyleHelper;

/* loaded from: classes4.dex */
public class IconMixin implements Mixin {
    public final Context context;
    public final int originalHeight;
    public final ImageView.ScaleType originalScaleType;
    public final TemplateLayout templateLayout;

    public IconMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        ImageView view;
        ImageView view2;
        this.templateLayout = templateLayout;
        Context context = templateLayout.getContext();
        this.context = context;
        ImageView view3 = getView();
        if (view3 != null) {
            this.originalHeight = view3.getLayoutParams().height;
            this.originalScaleType = view3.getScaleType();
        } else {
            this.originalHeight = 0;
            this.originalScaleType = null;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudIconMixin, i, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if ((resourceId != 0 || PartnerConfigHelper.isGlifExpressiveEnabled(context)) && (view = getView()) != null) {
            view.setImageResource(resourceId);
            if (PartnerConfigHelper.isGlifExpressiveEnabled(context)) {
                view.setVisibility(resourceId != 0 ? 0 : 4);
            } else {
                view.setVisibility(resourceId != 0 ? 0 : 8);
            }
            int visibility = view.getVisibility();
            if (((FrameLayout) templateLayout.findManagedViewById(R.id.sud_layout_icon_container)) != null) {
                ((FrameLayout) templateLayout.findManagedViewById(R.id.sud_layout_icon_container)).setVisibility(visibility);
            }
        }
        boolean z = typedArrayObtainStyledAttributes.getBoolean(2, false);
        ImageView view4 = getView();
        if (view4 != null) {
            ViewGroup.LayoutParams layoutParams = view4.getLayoutParams();
            layoutParams.height = z ? view4.getMaxHeight() : this.originalHeight;
            view4.setLayoutParams(layoutParams);
            view4.setScaleType(z ? ImageView.ScaleType.FIT_CENTER : this.originalScaleType);
        }
        int color = typedArrayObtainStyledAttributes.getColor(1, 0);
        if (color != 0 && (view2 = getView()) != null) {
            view2.setColorFilter(color);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final ImageView getView() {
        return (ImageView) this.templateLayout.findManagedViewById(R.id.sud_layout_icon);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void tryApplyPartnerCustomizationStyle() {
        int i;
        int dimension;
        int i2;
        TemplateLayout templateLayout = this.templateLayout;
        if (PartnerStyleHelper.shouldApplyPartnerResource(templateLayout)) {
            final ImageView view = getView();
            FrameLayout frameLayout = (FrameLayout) templateLayout.findManagedViewById(R.id.sud_layout_icon_container);
            if (view == null || frameLayout == null) {
                return;
            }
            Context context = view.getContext();
            int layoutGravity = PartnerStyleHelper.getLayoutGravity(context);
            if (layoutGravity != 0 && !PartnerConfigHelper.isGlifExpressiveEnabled(context) && (view.getLayoutParams() instanceof FrameLayout.LayoutParams)) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                layoutParams.gravity = layoutGravity;
                view.setLayoutParams(layoutParams);
            }
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_ICON_SIZE;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
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
                ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
                layoutParams2.height = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, 0.0f);
                layoutParams2.width = -2;
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Drawable drawable = view.getDrawable();
                if (drawable == null || drawable.getIntrinsicWidth() <= drawable.getIntrinsicHeight() * 2 || (i2 = layoutParams2.height) <= (dimension = (int) context.getResources().getDimension(R.dimen.sud_horizontal_icon_height))) {
                    i = 0;
                } else {
                    i = i2 - dimension;
                    layoutParams2.height = dimension;
                }
            }
            ViewGroup.LayoutParams layoutParams3 = frameLayout.getLayoutParams();
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_ICON_MARGIN_TOP;
            if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2) && (layoutParams3 instanceof ViewGroup.MarginLayoutParams)) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams3;
                marginLayoutParams.setMargins(marginLayoutParams.leftMargin, ((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, 0.0f)) + i, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            }
        }
    }
}
