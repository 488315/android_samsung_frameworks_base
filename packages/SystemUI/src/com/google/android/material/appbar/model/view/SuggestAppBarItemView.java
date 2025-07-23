package com.google.android.material.appbar.model.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.util.theme.SeslThemeResourceHelper;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$OpenThemeResourceColor;
import androidx.appcompat.util.theme.resource.SeslThemeResourceColor$ThemeResourceColor;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslHoverPopupWindowReflector;
import com.android.systemui.R;
import com.google.android.material.util.MaxFontScaleRatio;
import com.google.android.material.util.SeslTextViewHelperKt;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SuggestAppBarItemView extends SuggestAppBarView {
    private ViewGroup rootView;

    public SuggestAppBarItemView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    private final int getButtonTextColor() {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        Context context = getContext();
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_button_text_color, R.color.sesl_button_text_color_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_button_text_color, R.color.sesl_button_text_color_dark_for_theme));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    private final int getSuggestButtonTextColor() {
        SeslThemeResourceHelper.Companion companion = SeslThemeResourceHelper.Companion;
        Context context = getContext();
        SeslThemeResourceColor$OpenThemeResourceColor seslThemeResourceColor$OpenThemeResourceColor = new SeslThemeResourceColor$OpenThemeResourceColor(new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_suggest_button_text_color, R.color.sesl_suggest_button_text_color_dark), new SeslThemeResourceColor$ThemeResourceColor(R.color.sesl_suggest_button_text_color, R.color.sesl_suggest_button_text_color_dark_for_theme));
        companion.getClass();
        return context.getColor(seslThemeResourceColor$OpenThemeResourceColor.getColor(context));
    }

    private final void updateButton(Button button) {
        button.setTextColor(getButtonTextColor());
        SeslTextViewHelperKt.checkMaxFontScale(button, R.dimen.sesl_appbar_button_text_size, MaxFontScaleRatio.MEDIUM);
    }

    @Override // android.view.View
    public final ViewGroup getRootView() {
        return this.rootView;
    }

    @Override // com.google.android.material.appbar.model.view.SuggestAppBarView
    public void inflate() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.sesl_app_bar_suggest_in_viewpager, (ViewGroup) this, false);
        ImageButton imageButton = null;
        ViewGroup viewGroup = inflate instanceof ViewGroup ? (ViewGroup) inflate : null;
        if (viewGroup == null) {
            return;
        }
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.findViewById(R.id.sesl_appbar_suggest_in_viewpager);
        viewGroup2.setBackgroundResource(R.drawable.sesl_viewpager_item_background);
        this.rootView = viewGroup2;
        setTitleView((TextView) viewGroup.findViewById(R.id.suggest_app_bar_title));
        ImageButton imageButton2 = (ImageButton) viewGroup.findViewById(R.id.suggest_app_bar_close);
        if (imageButton2 != null) {
            SeslViewReflector.semSetHoverPopupType(imageButton2, SeslHoverPopupWindowReflector.getField_TYPE_NONE());
            imageButton = imageButton2;
        }
        setClose(imageButton);
        setBottomLayout((ViewGroup) viewGroup.findViewById(R.id.suggest_app_bar_bottom_layout));
        updateResource(getContext());
        addView(viewGroup);
    }

    public final void setRootView(ViewGroup viewGroup) {
        this.rootView = viewGroup;
    }

    public final void updateButtons(List<? extends Button> list) {
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            updateButton((Button) it.next());
        }
    }

    @Override // com.google.android.material.appbar.model.view.SuggestAppBarView, com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) {
        super.updateResource(context);
        boolean isLightTheme = SeslMisc.isLightTheme(context);
        ViewGroup viewGroup = this.rootView;
        if (viewGroup != null) {
            viewGroup.setBackgroundTintList(ColorStateList.valueOf(context.getColor(isLightTheme ? R.color.sesl_viewpager_item_background : R.color.sesl_viewpager_item_background_dark)));
        }
        TextView titleView = getTitleView();
        if (titleView != null) {
            SeslTextViewHelperKt.checkMaxFontScale(titleView, R.dimen.sesl_appbar_suggest_title_text_size, MaxFontScaleRatio.SMALL);
        }
        updateButtons(getButtons());
    }

    public /* synthetic */ SuggestAppBarItemView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public SuggestAppBarItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate();
    }
}
