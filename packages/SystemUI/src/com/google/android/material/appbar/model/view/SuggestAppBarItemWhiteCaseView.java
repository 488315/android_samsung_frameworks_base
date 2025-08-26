package com.google.android.material.appbar.model.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.util.theme.SeslThemeResourceHelper;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$OpenThemeResourceColor;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$ThemeResourceColor;
import androidx.appcompat.util.theme.resource.SeslThemeResourceDrawable$OpenThemeResourceDrawable;
import androidx.appcompat.util.theme.resource.SeslThemeResourceDrawable$ThemeResourceDrawable;
import com.android.systemui.R;

/* loaded from: classes4.dex */
public class SuggestAppBarItemWhiteCaseView extends SuggestAppBarItemView {
    public SuggestAppBarItemWhiteCaseView(Context context) {
        super(context, null, 2, null);
    }

    private final Drawable getCloseDrawable(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceDrawable$OpenThemeResourceDrawable seslThemeResourceDrawable$OpenThemeResourceDrawable = new SeslThemeResourceDrawable$OpenThemeResourceDrawable(new SeslThemeResourceDrawable$ThemeResourceDrawable(R.drawable.sesl_close_button_recoil_background_with_white_case, R.drawable.sesl_close_button_recoil_background_dark), new SeslThemeResourceDrawable$ThemeResourceDrawable(R.drawable.sesl_close_button_recoil_background_with_white_case_for_theme, R.drawable.sesl_close_button_recoil_background_dark_for_theme));
        companion.getClass();
        return context.getDrawable(seslThemeResourceDrawable$OpenThemeResourceDrawable.getDrawable(context));
    }

    private final int getSuggestButtonTextColorWithWhiteCase() {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        Context context = getContext();
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_suggest_button_text_color_with_white_case, R.color.sesl_suggest_button_text_color_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_suggest_button_text_color_with_white_case, R.color.sesl_suggest_button_text_color_dark_for_theme));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    private final int getSuggestTitleWithWhiteCaseColor(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_suggest_title_with_white_case, R.color.sesl_appbar_suggest_title_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_appbar_suggest_title_with_white_case, R.color.sesl_appbar_suggest_title_dark_for_theme));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    private final int getViewPagerItemBackgroundWhiteWhiteCaseColor(Context context) {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_viewpager_item_background_with_white_case, R.color.sesl_viewpager_item_background_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_viewpager_item_background_with_white_case_for_theme, R.color.sesl_viewpager_item_background_dark));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    @Override // com.google.android.material.appbar.model.view.SuggestAppBarItemView, com.google.android.material.appbar.model.view.SuggestAppBarView, com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) throws Resources.NotFoundException {
        super.updateResource(context);
        SeslMisc.isLightTheme(context);
        ViewGroup rootView = getRootView();
        if (rootView != null) {
            rootView.setBackgroundTintList(ColorStateList.valueOf(getViewPagerItemBackgroundWhiteWhiteCaseColor(context)));
        }
        TextView titleView = getTitleView();
        if (titleView != null) {
            titleView.setTextColor(getSuggestTitleWithWhiteCaseColor(context));
        }
        ImageButton close = getClose();
        if (close != null) {
            close.setBackground(getCloseDrawable(context));
        }
        updateButtons(getButtons());
    }
}
