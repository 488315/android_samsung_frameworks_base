package com.google.android.material.appbar.model.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.util.theme.SeslThemeResourceHelper;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$OpenThemeResourceColor;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$ThemeResourceColor;
import androidx.appcompat.widget.SeslIndicator;
import androidx.viewpager2.widget.ViewPager2;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public abstract class BasicViewPagerAppBarWhiteCaseView extends BasicViewPagerAppBarView {
    /* JADX WARN: Multi-variable type inference failed */
    public BasicViewPagerAppBarWhiteCaseView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    private final ColorStateList getViewPagerBackgroundColorStateList(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_viewpager_background, R.color.sesl_viewpager_background_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_viewpager_background_for_theme));
        companion.getClass();
        return ColorStateList.valueOf(context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context)));
    }

    private final int getViewPagerIndicatorOffWithWhiteCaseColor(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_off_with_white_case, R.color.sesl_appbar_viewpager_indicator_off_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_off_with_white_case_for_theme, R.color.sesl_appbar_viewpager_indicator_off_dark_for_theme));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    private final int getViewPagerIndicatorOnWithWhiteCaseColor(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_on_with_white_case), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_viewpager_indicator_on_with_white_case_for_theme));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    @Override // com.google.android.material.appbar.model.view.ViewPagerAppBarView, com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) {
        Drawable drawableMutate;
        Drawable drawableMutate2;
        ViewPager2 viewpager = getViewpager();
        if (viewpager != null) {
            viewpager.setBackgroundTintList(getViewPagerBackgroundColorStateList(context));
        }
        SeslIndicator indicator = getIndicator();
        if (indicator != null) {
            Drawable drawable = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
            Drawable drawable2 = null;
            if (drawable == null || (drawableMutate = drawable.mutate()) == null) {
                drawableMutate = null;
            } else {
                drawableMutate.setTint(getViewPagerIndicatorOffWithWhiteCaseColor(context));
            }
            indicator.setDefaultCircle(drawableMutate);
            Drawable drawable3 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
            if (drawable3 != null && (drawableMutate2 = drawable3.mutate()) != null) {
                drawableMutate2.setTint(getViewPagerIndicatorOnWithWhiteCaseColor(context));
                drawable2 = drawableMutate2;
            }
            indicator.setSelectCircle(drawable2);
        }
    }

    public /* synthetic */ BasicViewPagerAppBarWhiteCaseView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public BasicViewPagerAppBarWhiteCaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
