package com.samsung.sesl.compose;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.samsung.sesl.compose.component.tokens.SeslAlertDialogTokens;
import com.samsung.sesl.compose.component.tokens.SeslAppBarTokens;
import com.samsung.sesl.compose.component.tokens.SeslButtonTokens;
import com.samsung.sesl.compose.component.tokens.SeslCheckboxTokens;
import com.samsung.sesl.compose.component.tokens.SeslCommonTokens;
import com.samsung.sesl.compose.component.tokens.SeslDialogTokens;
import com.samsung.sesl.compose.component.tokens.SeslDividerTokens;
import com.samsung.sesl.compose.component.tokens.SeslDpProducer;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import com.samsung.sesl.compose.component.tokens.SeslListTokens;
import com.samsung.sesl.compose.component.tokens.SeslPopupTokens;
import com.samsung.sesl.compose.component.tokens.SeslRadioButtonTokens;
import com.samsung.sesl.compose.component.tokens.SeslSliderTokens;
import com.samsung.sesl.compose.component.tokens.SeslSpinnerTokens;
import com.samsung.sesl.compose.component.tokens.SeslSwitchTokens;
import com.samsung.sesl.compose.component.tokens.SeslTabTokens;
import com.samsung.sesl.compose.foundation.theme.SeslTokenScheme;
import com.samsung.sesl.compose.phone.resources.DrawableResourcesKt;
import com.samsung.sesl.compose.utils.ext.ContextExtKt;
import com.samsung.sesl.compose.utils.ext.SeslTypedValueCompat;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes4.dex */
public final class SeslPhoneTokenScheme implements SeslTokenScheme {
    public final Lazy alertDialogTokens$delegate;
    public final Lazy appBarTokens$delegate;
    public final Lazy buttonTokens$delegate;
    public final Lazy checkboxTokens$delegate;
    public final Lazy commonTokens$delegate;
    public final Context context;
    public final Lazy dialogTokens$delegate;
    public final Lazy dividerTokens$delegate;
    public final boolean isDarkMode;
    public final Lazy listTokens$delegate;
    public final Lazy popupTokens$delegate;
    public final Lazy radioButtonTokens$delegate;
    public final Lazy sliderTokens$delegate;
    public final Lazy spinnerTokens$delegate;
    public final Lazy switchTokens$delegate;
    public final Lazy tabTokens$delegate;

    public SeslPhoneTokenScheme(Context context) {
        this.context = context;
        this.isDarkMode = ContextExtKt.isSystemInDarkTheme(context);
        final int i = 0;
        this.appBarTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i2 = 9;
        this.alertDialogTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i2) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i3 = 10;
        this.buttonTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i3) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i4 = 11;
        this.checkboxTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i4) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i5 = 12;
        this.commonTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i5) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i6 = 13;
        this.dialogTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i6) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i7 = 1;
        this.dividerTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i7) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i8 = 2;
        this.listTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i8) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i9 = 3;
        this.popupTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i9) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i10 = 4;
        this.radioButtonTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i10) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i11 = 5;
        this.sliderTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i11) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i12 = 6;
        this.spinnerTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i12) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i13 = 7;
        this.switchTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i13) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
        final int i14 = 8;
        this.tabTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Drawable drawable;
                Drawable drawable2;
                switch (i14) {
                    case 0:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        boolean z = seslPhoneTokenScheme.isDarkMode;
                        long jSeslColorResource = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context2);
                        long jSeslColorResource2 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource3 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource4 = ContextExtKt.seslColorResource(z ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme.context);
                        long jSeslColorResource5 = ContextExtKt.seslColorResource(z ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme.context);
                        SeslDpProducer seslDpProducer = new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3335produceu2uoSUM(SeslDpProducer.Params params) throws Resources.NotFoundException {
                                Context context3 = seslPhoneTokenScheme.context;
                                try {
                                    float dimension = context3.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context3.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float fDeriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return fDeriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        };
                        Drawable drawableSeslDrawableResource = DrawableResourcesKt.seslDrawableResource(z ? R.drawable.sesl_ic_ab_back_dark : R.drawable.sesl_ic_ab_back_light, seslPhoneTokenScheme.context);
                        return new SeslAppBarTokens(jSeslColorResource, jSeslColorResource2, jSeslColorResource3, jSeslColorResource4, jSeslColorResource5, seslDpProducer, (!(drawableSeslDrawableResource instanceof InsetDrawable) || (drawable = ((InsetDrawable) drawableSeslDrawableResource).getDrawable()) == null) ? drawableSeslDrawableResource : drawable, null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context3 = seslPhoneTokenScheme2.context;
                        boolean z2 = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z2 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context3), ContextExtKt.seslColorResource(z2 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context4 = seslPhoneTokenScheme3.context;
                        boolean z3 = seslPhoneTokenScheme3.isDarkMode;
                        long jSeslColorResource6 = ContextExtKt.seslColorResource(z3 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context4);
                        return new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(jSeslColorResource6), Color.m462getGreenimpl(jSeslColorResource6), Color.m460getBlueimpl(jSeslColorResource6), 0.6f, Color.m461getColorSpaceimpl(jSeslColorResource6)), ContextExtKt.seslColorResource(z3 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context5 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource7 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context5);
                        Context context6 = seslPhoneTokenScheme4.context;
                        long jSeslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_menu_popup_background_stroke_color_dark : R.color.sesl_menu_popup_background_stroke_color, context6);
                        Context context7 = seslPhoneTokenScheme4.context;
                        Drawable drawableSeslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context7);
                        if (drawableSeslDrawableResource2 instanceof LayerDrawable) {
                            drawableSeslDrawableResource2 = null;
                        }
                        if (drawableSeslDrawableResource2 == null) {
                            SeslDrawableTokens.Companion.getClass();
                            drawableSeslDrawableResource2 = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(jSeslColorResource7, jSeslColorResource8, drawableSeslDrawableResource2, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        return new SeslRadioButtonTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_024, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_000, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_025, seslPhoneTokenScheme5.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_radio_to_on_001, seslPhoneTokenScheme5.context));
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long jSeslColorResource9 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource10 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme6.context);
                        long jSeslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme6.context);
                        long jSeslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme6.context);
                        Context context8 = seslPhoneTokenScheme6.context;
                        boolean z4 = seslPhoneTokenScheme6.isDarkMode;
                        return new SeslSliderTokens(jSeslColorResource9, jSeslColorResource10, jSeslColorResource11, jSeslColorResource12, jSeslColorResource13, ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context8), ContextExtKt.seslColorResource(z4 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        Context context9 = seslPhoneTokenScheme7.context;
                        boolean z5 = seslPhoneTokenScheme7.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context9), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme7.context), null);
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        long jSeslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme8.context);
                        Context context10 = seslPhoneTokenScheme8.context;
                        return new SeslSwitchTokens(jSeslColorResource14, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context10) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context10), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme8.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context11 = seslPhoneTokenScheme9.context;
                        boolean z6 = seslPhoneTokenScheme9.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context11), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme9.context), ContextExtKt.seslColorResource(z6 ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme9.context), null);
                    case 9:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context12 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource15 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context12) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context12);
                        Context context13 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource16 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context13);
                        Context context14 = seslPhoneTokenScheme10.context;
                        long jSeslColorResource17 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context14) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context14);
                        Context context15 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(jSeslColorResource17, jSeslColorResource16, jSeslColorResource15, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_dialog_list_text_color_dark : R.color.sesl_dialog_list_text_color_light, context15), null);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        Context context16 = seslPhoneTokenScheme11.context;
                        boolean z7 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslButtonTokens(ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_background_color_dark : R.color.sesl_btn_background_color_light, context16), ContextExtKt.seslColorResource(z7 ? R.color.sesl_btn_text_color_dark : R.color.sesl_btn_text_color_light, seslPhoneTokenScheme11.context), null);
                    case 11:
                        SeslPhoneTokenScheme seslPhoneTokenScheme12 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme12.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme12.context));
                    case 12:
                        SeslPhoneTokenScheme seslPhoneTokenScheme13 = this.f$0;
                        Context context17 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource18 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context17);
                        Context context18 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource19 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context18);
                        Context context19 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource20 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context19);
                        Context context20 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource21 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context20) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context20);
                        Context context21 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource22 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context21) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context21);
                        Context context22 = seslPhoneTokenScheme13.context;
                        long jSeslColorResource23 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context22) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context22);
                        Context context23 = seslPhoneTokenScheme13.context;
                        return new SeslCommonTokens(jSeslColorResource18, jSeslColorResource19, jSeslColorResource21, jSeslColorResource20, jSeslColorResource22, jSeslColorResource23, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context23) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context23), null);
                    default:
                        Drawable drawableSeslDrawableResource3 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((drawableSeslDrawableResource3 instanceof InsetDrawable) && (drawable2 = ((InsetDrawable) drawableSeslDrawableResource3).getDrawable()) != null) {
                            drawableSeslDrawableResource3 = drawable2;
                        }
                        return new SeslDialogTokens(drawableSeslDrawableResource3);
                }
            }
        });
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslAlertDialogTokens getAlertDialogTokens() {
        return (SeslAlertDialogTokens) this.alertDialogTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslAppBarTokens getAppBarTokens() {
        return (SeslAppBarTokens) this.appBarTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslButtonTokens getButtonTokens() {
        return (SeslButtonTokens) this.buttonTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslCheckboxTokens getCheckboxTokens() {
        return (SeslCheckboxTokens) this.checkboxTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslCommonTokens getCommonTokens() {
        return (SeslCommonTokens) this.commonTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslDialogTokens getDialogTokens() {
        return (SeslDialogTokens) this.dialogTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslDividerTokens getDividerTokens() {
        return (SeslDividerTokens) this.dividerTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslListTokens getListTokens() {
        return (SeslListTokens) this.listTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslPopupTokens getPopupTokens() {
        return (SeslPopupTokens) this.popupTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslRadioButtonTokens getRadioButtonTokens() {
        return (SeslRadioButtonTokens) this.radioButtonTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslSliderTokens getSliderTokens() {
        return (SeslSliderTokens) this.sliderTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslSpinnerTokens getSpinnerTokens() {
        return (SeslSpinnerTokens) this.spinnerTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslSwitchTokens getSwitchTokens() {
        return (SeslSwitchTokens) this.switchTokens$delegate.getValue();
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslTabTokens getTabTokens() {
        return (SeslTabTokens) this.tabTokens$delegate.getValue();
    }
}
