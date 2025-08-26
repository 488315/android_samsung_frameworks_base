package com.samsung.sesl.compose.foundation.theme;

import android.graphics.drawable.Drawable;
import androidx.compose.ui.graphics.Color;
import com.samsung.sesl.compose.component.tokens.DimensionSchemeKeyTokensKt;
import com.samsung.sesl.compose.component.tokens.DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1;
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
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ULong;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslMergedTokenScheme implements SeslTokenScheme {
    public final Lazy alertDialogTokens$delegate;
    public final Lazy appBarTokens$delegate;
    public final Lazy buttonTokens$delegate;
    public final Lazy checkboxTokens$delegate;
    public final Lazy commonTokens$delegate;
    public final Lazy dialogTokens$delegate;
    public final Lazy dividerTokens$delegate;
    public final Lazy listTokens$delegate;
    public final Lazy popupTokens$delegate;
    public final Lazy radioButtonTokens$delegate;
    public final Lazy sliderTokens$delegate;
    public final Lazy spinnerTokens$delegate;
    public final Lazy switchTokens$delegate;
    public final Lazy tabTokens$delegate;

    public SeslMergedTokenScheme(final SeslTokenScheme seslTokenScheme, final SeslTokenScheme seslTokenScheme2) {
        final int i = 0;
        this.appBarTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i2 = 9;
        this.alertDialogTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i3 = 10;
        this.buttonTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i4 = 11;
        this.checkboxTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i5 = 12;
        this.commonTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i6 = 13;
        this.dialogTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i7 = 1;
        this.dividerTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i8 = 2;
        this.listTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i8) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i9 = 3;
        this.popupTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i9) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i10 = 4;
        this.radioButtonTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i10) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i11 = 5;
        this.sliderTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i11) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i12 = 6;
        this.spinnerTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i12) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i13 = 7;
        this.switchTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i13) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                }
            }
        });
        final int i14 = 8;
        this.tabTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i14) {
                    case 0:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3447equalsimpl0(j2, j)) {
                            j2 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j3 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3447equalsimpl0(j3, j)) {
                            j3 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j4 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3447equalsimpl0(j4, j)) {
                            j4 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j5 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3447equalsimpl0(j5, j)) {
                            j5 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j6 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3447equalsimpl0(j6, j)) {
                            j6 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        Drawable drawable = appBarTokens.backIcon;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslAppBarTokens(j2, j3, j4, j5, j6, seslDpProducer, Intrinsics.areEqual(drawable, SeslDrawableTokens.emptyDrawable) ? appBarTokens2.backIcon : appBarTokens.backIcon, null);
                    case 1:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j7 = Color.Unspecified;
                        long j8 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3447equalsimpl0(j8, j7)) {
                            j8 = dividerTokens2.horizontalDividerColor;
                        }
                        long j9 = dividerTokens.verticalDividerColor;
                        if (ULong.m3447equalsimpl0(j9, j7)) {
                            j9 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j8, j9, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3447equalsimpl0(j11, j10)) {
                            j11 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j12 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3447equalsimpl0(j12, j10)) {
                            j12 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j11, j12, null);
                    case 3:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j13 = Color.Unspecified;
                        long j14 = popupTokens.backgroundColor;
                        if (ULong.m3447equalsimpl0(j14, j13)) {
                            j14 = popupTokens2.backgroundColor;
                        }
                        long j15 = popupTokens.borderColor;
                        if (ULong.m3447equalsimpl0(j15, j13)) {
                            j15 = popupTokens2.borderColor;
                        }
                        Drawable drawable2 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j14, j15, Intrinsics.areEqual(drawable2, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 4:
                        SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
                        SeslRadioButtonTokens radioButtonTokens2 = seslTokenScheme2.getRadioButtonTokens();
                        Drawable drawable3 = radioButtonTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable4 = SeslDrawableTokens.emptyDrawable;
                        return new SeslRadioButtonTokens(Intrinsics.areEqual(drawable3, drawable4) ? radioButtonTokens2.selectedDrawable : radioButtonTokens.selectedDrawable, Intrinsics.areEqual(radioButtonTokens.unselectedDrawable, drawable4) ? radioButtonTokens2.unselectedDrawable : radioButtonTokens.unselectedDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOnDrawable, drawable4) ? radioButtonTokens2.disabledOnDrawable : radioButtonTokens.disabledOnDrawable, Intrinsics.areEqual(radioButtonTokens.disabledOffDrawable, drawable4) ? radioButtonTokens2.disabledOffDrawable : radioButtonTokens.disabledOffDrawable);
                    case 5:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j16 = Color.Unspecified;
                        long j17 = sliderTokens.thumbFillColor;
                        if (ULong.m3447equalsimpl0(j17, j16)) {
                            j17 = sliderTokens2.thumbFillColor;
                        }
                        long j18 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j18, j16)) {
                            j18 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j19 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3447equalsimpl0(j19, j16)) {
                            j19 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j20 = sliderTokens.activateTrackColor;
                        if (ULong.m3447equalsimpl0(j20, j16)) {
                            j20 = sliderTokens2.activateTrackColor;
                        }
                        long j21 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3447equalsimpl0(j21, j16)) {
                            j21 = sliderTokens2.inactivateTrackColor;
                        }
                        long j22 = j17;
                        long j23 = sliderTokens.overlapActivateColor;
                        if (ULong.m3447equalsimpl0(j23, j16)) {
                            j23 = sliderTokens2.overlapActivateColor;
                        }
                        long j24 = j23;
                        long j25 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3447equalsimpl0(j25, j16)) {
                            j25 = sliderTokens2.overlapInactiveColor;
                        }
                        long j26 = j25;
                        long j27 = sliderTokens.levelTrackColor;
                        if (ULong.m3447equalsimpl0(j27, j16)) {
                            j27 = sliderTokens2.levelTrackColor;
                        }
                        long j28 = j27;
                        long j29 = sliderTokens.activateTickColor;
                        if (ULong.m3447equalsimpl0(j29, j16)) {
                            j29 = sliderTokens2.activateTickColor;
                        }
                        long j30 = sliderTokens.inactiveTickColor;
                        if (ULong.m3447equalsimpl0(j30, j16)) {
                            j30 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j22, j18, j19, j20, j21, j24, j26, j28, j29, j30, null);
                    case 6:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j31 = Color.Unspecified;
                        long j32 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3447equalsimpl0(j32, j31)) {
                            j32 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j33 = spinnerTokens.iconColorDefault;
                        if (ULong.m3447equalsimpl0(j33, j31)) {
                            j33 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j32, j33, null);
                    case 7:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j34 = Color.Unspecified;
                        long j35 = switchTokens.trackOnColor;
                        if (ULong.m3447equalsimpl0(j35, j34)) {
                            j35 = switchTokens2.trackOnColor;
                        }
                        long j36 = switchTokens.trackOffColor;
                        if (ULong.m3447equalsimpl0(j36, j34)) {
                            j36 = switchTokens2.trackOffColor;
                        }
                        long j37 = switchTokens.thumbOnColor;
                        if (ULong.m3447equalsimpl0(j37, j34)) {
                            j37 = switchTokens2.thumbOnColor;
                        }
                        long j38 = switchTokens.thumbOffColor;
                        if (ULong.m3447equalsimpl0(j38, j34)) {
                            j38 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j35, j36, j37, j38, null);
                    case 8:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j39 = Color.Unspecified;
                        long j40 = tabTokens.textColor;
                        if (ULong.m3447equalsimpl0(j40, j39)) {
                            j40 = tabTokens2.textColor;
                        }
                        long j41 = tabTokens.selectedTextColor;
                        if (ULong.m3447equalsimpl0(j41, j39)) {
                            j41 = tabTokens2.selectedTextColor;
                        }
                        long j42 = tabTokens.subTabTextColor;
                        if (ULong.m3447equalsimpl0(j42, j39)) {
                            j42 = tabTokens2.subTabTextColor;
                        }
                        long j43 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j43, j39)) {
                            j43 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j44 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3447equalsimpl0(j44, j39)) {
                            j44 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j45 = j40;
                        long j46 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j46, j39)) {
                            j46 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j47 = j46;
                        long j48 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3447equalsimpl0(j48, j39)) {
                            j48 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j49 = j48;
                        long j50 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3447equalsimpl0(j50, j39)) {
                            j50 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j51 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3447equalsimpl0(j51, j39)) {
                            j51 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j45, j41, j42, j43, j44, j47, j49, j50, j51, null);
                    case 9:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j52 = Color.Unspecified;
                        long j53 = alertDialogTokens.titleTextColor;
                        if (ULong.m3447equalsimpl0(j53, j52)) {
                            j53 = alertDialogTokens2.titleTextColor;
                        }
                        long j54 = alertDialogTokens.messageTextColor;
                        if (ULong.m3447equalsimpl0(j54, j52)) {
                            j54 = alertDialogTokens2.messageTextColor;
                        }
                        long j55 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3447equalsimpl0(j55, j52)) {
                            j55 = alertDialogTokens2.buttonTextColor;
                        }
                        long j56 = alertDialogTokens.listTextColor;
                        if (ULong.m3447equalsimpl0(j56, j52)) {
                            j56 = alertDialogTokens2.listTextColor;
                        }
                        return new SeslAlertDialogTokens(j53, j54, j55, j56, null);
                    case 10:
                        SeslButtonTokens buttonTokens = seslTokenScheme.getButtonTokens();
                        SeslButtonTokens buttonTokens2 = seslTokenScheme2.getButtonTokens();
                        buttonTokens.getClass();
                        Color.Companion.getClass();
                        long j57 = Color.Unspecified;
                        long j58 = buttonTokens.containerColor;
                        if (ULong.m3447equalsimpl0(j58, j57)) {
                            j58 = buttonTokens2.containerColor;
                        }
                        long j59 = buttonTokens.contentColor;
                        if (ULong.m3447equalsimpl0(j59, j57)) {
                            j59 = buttonTokens2.contentColor;
                        }
                        return new SeslButtonTokens(j58, j59, null);
                    case 11:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable5 = checkboxTokens.selectedDrawable;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable6 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable5, drawable6) ? checkboxTokens2.selectedDrawable : checkboxTokens.selectedDrawable, Intrinsics.areEqual(checkboxTokens.unselectedDrawable, drawable6) ? checkboxTokens2.unselectedDrawable : checkboxTokens.unselectedDrawable, Intrinsics.areEqual(checkboxTokens.disabledOnDrawable, drawable6) ? checkboxTokens2.disabledOnDrawable : checkboxTokens.disabledOnDrawable, Intrinsics.areEqual(checkboxTokens.disabledOffDrawable, drawable6) ? checkboxTokens2.disabledOffDrawable : checkboxTokens.disabledOffDrawable);
                    case 12:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j60 = Color.Unspecified;
                        long j61 = commonTokens.primaryColor;
                        if (ULong.m3447equalsimpl0(j61, j60)) {
                            j61 = commonTokens2.primaryColor;
                        }
                        long j62 = commonTokens.windowBackgroundColor;
                        if (ULong.m3447equalsimpl0(j62, j60)) {
                            j62 = commonTokens2.windowBackgroundColor;
                        }
                        long j63 = commonTokens.rippleColor;
                        if (ULong.m3447equalsimpl0(j63, j60)) {
                            j63 = commonTokens2.rippleColor;
                        }
                        long j64 = commonTokens.roundedCornerColor;
                        if (ULong.m3447equalsimpl0(j64, j60)) {
                            j64 = commonTokens2.roundedCornerColor;
                        }
                        long j65 = commonTokens.mainTextColor;
                        if (ULong.m3447equalsimpl0(j65, j60)) {
                            j65 = commonTokens2.mainTextColor;
                        }
                        long j66 = j61;
                        long j67 = commonTokens.subTextColor;
                        if (ULong.m3447equalsimpl0(j67, j60)) {
                            j67 = commonTokens2.subTextColor;
                        }
                        long j68 = commonTokens.pointTextColor;
                        if (ULong.m3447equalsimpl0(j68, j60)) {
                            j68 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j66, j62, j63, j64, j65, j67, j68, null);
                    default:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable7 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable7, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
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
