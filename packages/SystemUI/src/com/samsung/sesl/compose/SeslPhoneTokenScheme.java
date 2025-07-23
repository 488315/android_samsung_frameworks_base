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
import com.samsung.sesl.compose.component.tokens.SeslCheckboxTokens;
import com.samsung.sesl.compose.component.tokens.SeslCommonTokens;
import com.samsung.sesl.compose.component.tokens.SeslDialogTokens;
import com.samsung.sesl.compose.component.tokens.SeslDividerTokens;
import com.samsung.sesl.compose.component.tokens.SeslDpProducer;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import com.samsung.sesl.compose.component.tokens.SeslListTokens;
import com.samsung.sesl.compose.component.tokens.SeslPopupTokens;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslPhoneTokenScheme implements SeslTokenScheme {
    public final Lazy alertDialogTokens$delegate;
    public final Lazy appBarTokens$delegate;
    public final Lazy checkboxTokens$delegate;
    public final Lazy commonTokens$delegate;
    public final Context context;
    public final Lazy dialogTokens$delegate;
    public final Lazy dividerTokens$delegate;
    public final boolean isDarkMode;
    public final Lazy listTokens$delegate;
    public final Lazy popupTokens$delegate;
    public final Lazy sliderTokens$delegate;
    public final Lazy spinnerTokens$delegate;
    public final Lazy switchTokens$delegate;
    public final Lazy tabTokens$delegate;

    public SeslPhoneTokenScheme(Context context) {
        this.context = context;
        this.isDarkMode = ContextExtKt.isSystemInDarkTheme(context);
        final int i = 0;
        this.commonTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i2 = 5;
        this.switchTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i2) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i3 = 6;
        this.checkboxTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i3) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i4 = 7;
        this.spinnerTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i4) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i5 = 8;
        this.popupTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i5) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i6 = 9;
        this.dialogTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i6) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i7 = 10;
        this.alertDialogTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i7) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i8 = 11;
        this.sliderTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i8) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i9 = 1;
        this.tabTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i9) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i10 = 2;
        this.listTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i10) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i11 = 3;
        this.appBarTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i11) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
                }
            }
        });
        final int i12 = 4;
        this.dividerTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$$ExternalSyntheticLambda0
            public final /* synthetic */ SeslPhoneTokenScheme f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                long Color;
                Drawable drawable;
                switch (i12) {
                    case 0:
                        SeslPhoneTokenScheme seslPhoneTokenScheme = this.f$0;
                        Context context2 = seslPhoneTokenScheme.context;
                        long seslColorResource = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context2) ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context2);
                        Context context3 = seslPhoneTokenScheme.context;
                        long seslColorResource2 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context3) ? R.color.sesl_background_color_dark : R.color.sesl_background_color_light, context3);
                        Context context4 = seslPhoneTokenScheme.context;
                        long seslColorResource3 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context4) ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light, context4);
                        Context context5 = seslPhoneTokenScheme.context;
                        long seslColorResource4 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context5) ? R.color.sesl_ripple_color_dark : R.color.sesl_ripple_color_light, context5);
                        Context context6 = seslPhoneTokenScheme.context;
                        long seslColorResource5 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context6) ? R.color.sesl_primary_text_color_dark : R.color.sesl_primary_text_color_light, context6);
                        Context context7 = seslPhoneTokenScheme.context;
                        long seslColorResource6 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context7) ? R.color.sesl_secondary_text_color_dark : R.color.sesl_secondary_text_color_light, context7);
                        Context context8 = seslPhoneTokenScheme.context;
                        return new SeslCommonTokens(seslColorResource, seslColorResource2, seslColorResource4, seslColorResource3, seslColorResource5, seslColorResource6, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context8) ? R.color.sesl_primary_dark_color_dark : R.color.sesl_primary_dark_color_light, context8), null);
                    case 1:
                        SeslPhoneTokenScheme seslPhoneTokenScheme2 = this.f$0;
                        Context context9 = seslPhoneTokenScheme2.context;
                        boolean z = seslPhoneTokenScheme2.isDarkMode;
                        return new SeslTabTokens(ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_default_dark : R.color.sesl_tablayout_text_color_default, context9), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_selected_text_color_dark : R.color.sesl_tablayout_selected_text_color, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_text_color_selected_dark : R.color.sesl_tablayout_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_selected_dark : R.color.sesl_tablayout_subtab_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_text_color_default_dark : R.color.sesl_tablayout_subtab_text_color_default, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_sub_text_color_selected_dark : R.color.sesl_tablayout_subtab_sub_text_color_selected, seslPhoneTokenScheme2.context), ContextExtKt.seslColorResource(z ? R.color.sesl_tablayout_subtab_indicator_background_dark : R.color.sesl_tablayout_subtab_indicator_background, seslPhoneTokenScheme2.context), null);
                    case 2:
                        SeslPhoneTokenScheme seslPhoneTokenScheme3 = this.f$0;
                        Context context10 = seslPhoneTokenScheme3.context;
                        boolean z2 = seslPhoneTokenScheme3.isDarkMode;
                        Color = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), 0.6f, Color.m459getColorSpaceimpl(ContextExtKt.seslColorResource(z2 ? R.color.sesl_primary_color_dark : R.color.sesl_primary_color_light, context10)));
                        return new SeslListTokens(Color, ContextExtKt.seslColorResource(z2 ? R.color.sesl_scrollbar_handle_tint_color_dark : R.color.sesl_scrollbar_handle_tint_color_light, seslPhoneTokenScheme3.context), null);
                    case 3:
                        final SeslPhoneTokenScheme seslPhoneTokenScheme4 = this.f$0;
                        Context context11 = seslPhoneTokenScheme4.context;
                        boolean z3 = seslPhoneTokenScheme4.isDarkMode;
                        return new SeslAppBarTokens(ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_background_color_dark : R.color.sesl_action_bar_background_color_light, context11), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_title_dark : R.color.sesl_action_bar_text_color_title_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_subtitle_dark : R.color.sesl_action_bar_text_color_subtitle_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_action_bar_text_color_menu_dark : R.color.sesl_action_bar_text_color_menu_light, seslPhoneTokenScheme4.context), ContextExtKt.seslColorResource(z3 ? R.color.sesl_extended_appbar_subtitle_dark : R.color.sesl_extended_appbar_subtitle_light, seslPhoneTokenScheme4.context), new SeslDpProducer() { // from class: com.samsung.sesl.compose.SeslPhoneTokenScheme$appBarTokens$2$1
                            @Override // com.samsung.sesl.compose.component.tokens.SeslDpProducer
                            /* renamed from: produce-u2uoSUM, reason: not valid java name */
                            public final float mo3317produceu2uoSUM(SeslDpProducer.Params params) {
                                Context context12 = SeslPhoneTokenScheme.this.context;
                                try {
                                    float dimension = context12.getResources().getDimension(R.dimen.sesl_action_bar_top_padding);
                                    SeslTypedValueCompat seslTypedValueCompat = SeslTypedValueCompat.INSTANCE;
                                    DisplayMetrics displayMetrics = context12.getResources().getDisplayMetrics();
                                    seslTypedValueCompat.getClass();
                                    float deriveDimension = TypedValue.deriveDimension(1, dimension, displayMetrics);
                                    Dp.Companion companion = Dp.Companion;
                                    return deriveDimension;
                                } catch (Resources.NotFoundException unused) {
                                    Dp.Companion.getClass();
                                    return Dp.Unspecified;
                                }
                            }
                        }, null);
                    case 4:
                        SeslPhoneTokenScheme seslPhoneTokenScheme5 = this.f$0;
                        Context context12 = seslPhoneTokenScheme5.context;
                        boolean z4 = seslPhoneTokenScheme5.isDarkMode;
                        return new SeslDividerTokens(ContextExtKt.seslColorResource(z4 ? R.color.sesl_list_divider_color_dark : R.color.sesl_list_divider_color_light, context12), ContextExtKt.seslColorResource(z4 ? R.color.sesl_switch_divider_color_dark : R.color.sesl_switch_divider_color_light, seslPhoneTokenScheme5.context), null);
                    case 5:
                        SeslPhoneTokenScheme seslPhoneTokenScheme6 = this.f$0;
                        long seslColorResource7 = ContextExtKt.seslColorResource(R.color.sesl_switch_track_on_color_light, seslPhoneTokenScheme6.context);
                        Context context13 = seslPhoneTokenScheme6.context;
                        return new SeslSwitchTokens(seslColorResource7, ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context13) ? R.color.sesl_switch_track_off_color_dark : R.color.sesl_switch_track_off_color, context13), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_on_color, seslPhoneTokenScheme6.context), ContextExtKt.seslColorResource(R.color.sesl_switch_thumb_off_color, seslPhoneTokenScheme6.context), null);
                    case 6:
                        SeslPhoneTokenScheme seslPhoneTokenScheme7 = this.f$0;
                        return new SeslCheckboxTokens(DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_024, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_000, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_025, seslPhoneTokenScheme7.context), DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_btn_check_to_on_001, seslPhoneTokenScheme7.context));
                    case 7:
                        SeslPhoneTokenScheme seslPhoneTokenScheme8 = this.f$0;
                        Context context14 = seslPhoneTokenScheme8.context;
                        boolean z5 = seslPhoneTokenScheme8.isDarkMode;
                        return new SeslSpinnerTokens(ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_item_text_color_normal_dark : R.color.sesl_spinner_item_text_color_normal_light, context14), ContextExtKt.seslColorResource(z5 ? R.color.sesl_spinner_icon_color_default_dark : R.color.sesl_spinner_icon_color_default_light, seslPhoneTokenScheme8.context), null);
                    case 8:
                        SeslPhoneTokenScheme seslPhoneTokenScheme9 = this.f$0;
                        Context context15 = seslPhoneTokenScheme9.context;
                        long seslColorResource8 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context15) ? R.color.sesl_menu_popup_background_color_dark : R.color.sesl_menu_popup_background_color, context15);
                        Context context16 = seslPhoneTokenScheme9.context;
                        Drawable seslDrawableResource = DrawableResourcesKt.seslDrawableResource(ContextExtKt.isSystemInDarkTheme(context16) ? R.drawable.sesl_menu_popup_background_dark : R.drawable.sesl_menu_popup_background, context16);
                        if (seslDrawableResource instanceof LayerDrawable) {
                            seslDrawableResource = null;
                        }
                        if (seslDrawableResource == null) {
                            SeslDrawableTokens.Companion.getClass();
                            seslDrawableResource = SeslDrawableTokens.emptyDrawable;
                        }
                        return new SeslPopupTokens(seslColorResource8, seslDrawableResource, null);
                    case 9:
                        Drawable seslDrawableResource2 = DrawableResourcesKt.seslDrawableResource(R.drawable.sesl_dialog_background, this.f$0.context);
                        if ((seslDrawableResource2 instanceof InsetDrawable) && (drawable = ((InsetDrawable) seslDrawableResource2).getDrawable()) != null) {
                            seslDrawableResource2 = drawable;
                        }
                        return new SeslDialogTokens(seslDrawableResource2);
                    case 10:
                        SeslPhoneTokenScheme seslPhoneTokenScheme10 = this.f$0;
                        Context context17 = seslPhoneTokenScheme10.context;
                        long seslColorResource9 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context17) ? R.color.sesl_dialog_button_text_color_dark : R.color.sesl_dialog_button_text_color_light, context17);
                        Context context18 = seslPhoneTokenScheme10.context;
                        long seslColorResource10 = ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context18) ? R.color.sesl_dialog_body_text_color_dark : R.color.sesl_dialog_body_text_color_light, context18);
                        Context context19 = seslPhoneTokenScheme10.context;
                        return new SeslAlertDialogTokens(ContextExtKt.seslColorResource(ContextExtKt.isSystemInDarkTheme(context19) ? R.color.sesl_dialog_title_text_color_dark : R.color.sesl_dialog_title_text_color_light, context19), seslColorResource10, seslColorResource9, null);
                    default:
                        SeslPhoneTokenScheme seslPhoneTokenScheme11 = this.f$0;
                        long seslColorResource11 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_fill_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource12 = ContextExtKt.seslColorResource(R.color.sesl_thumb_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource13 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_disable_color_activated_dark, seslPhoneTokenScheme11.context);
                        long seslColorResource14 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_activated, seslPhoneTokenScheme11.context);
                        long seslColorResource15 = ContextExtKt.seslColorResource(R.color.sesl_seekbar_control_color_default, seslPhoneTokenScheme11.context);
                        Context context20 = seslPhoneTokenScheme11.context;
                        boolean z6 = seslPhoneTokenScheme11.isDarkMode;
                        return new SeslSliderTokens(seslColorResource11, seslColorResource12, seslColorResource13, seslColorResource14, seslColorResource15, ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_activated_dark : R.color.sesl_seekbar_overlap_color_activated_light, context20), ContextExtKt.seslColorResource(z6 ? R.color.sesl_seekbar_overlap_color_default_dark : R.color.sesl_seekbar_overlap_color_default_light, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_seekbar_level_control_color_default, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), ContextExtKt.seslColorResource(R.color.sesl_tick_mark_seekbar_level, seslPhoneTokenScheme11.context), null);
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
