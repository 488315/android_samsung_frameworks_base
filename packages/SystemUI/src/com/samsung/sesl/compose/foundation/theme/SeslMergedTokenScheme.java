package com.samsung.sesl.compose.foundation.theme;

import android.graphics.drawable.Drawable;
import androidx.compose.ui.graphics.Color;
import com.samsung.sesl.compose.component.tokens.DimensionSchemeKeyTokensKt;
import com.samsung.sesl.compose.component.tokens.DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1;
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
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ULong;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslMergedTokenScheme implements SeslTokenScheme {
    public final Lazy alertDialogTokens$delegate;
    public final Lazy appBarTokens$delegate;
    public final Lazy checkboxTokens$delegate;
    public final Lazy commonTokens$delegate;
    public final Lazy dialogTokens$delegate;
    public final Lazy dividerTokens$delegate;
    public final Lazy listTokens$delegate;
    public final Lazy popupTokens$delegate;
    public final Lazy sliderTokens$delegate;
    public final Lazy spinnerTokens$delegate;
    public final Lazy switchTokens$delegate;
    public final Lazy tabTokens$delegate;

    public SeslMergedTokenScheme(final SeslTokenScheme seslTokenScheme, final SeslTokenScheme seslTokenScheme2) {
        final int i = 0;
        this.commonTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i2 = 5;
        this.switchTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i3 = 6;
        this.checkboxTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i4 = 7;
        this.spinnerTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i5 = 8;
        this.popupTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i6 = 9;
        this.dialogTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i7 = 10;
        this.alertDialogTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i8 = 11;
        this.sliderTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i8) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i9 = 1;
        this.tabTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i9) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i10 = 2;
        this.listTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i10) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i11 = 3;
        this.appBarTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i11) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
                }
            }
        });
        final int i12 = 4;
        this.dividerTokens$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.samsung.sesl.compose.foundation.theme.SeslMergedTokenScheme$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i12) {
                    case 0:
                        SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
                        SeslCommonTokens commonTokens2 = seslTokenScheme2.getCommonTokens();
                        commonTokens.getClass();
                        Color.Companion.getClass();
                        long j = Color.Unspecified;
                        long j2 = commonTokens.primaryColor;
                        if (ULong.m3427equalsimpl0(j2, j)) {
                            j2 = commonTokens2.primaryColor;
                        }
                        long j3 = commonTokens.windowBackgroundColor;
                        if (ULong.m3427equalsimpl0(j3, j)) {
                            j3 = commonTokens2.windowBackgroundColor;
                        }
                        long j4 = commonTokens.rippleColor;
                        if (ULong.m3427equalsimpl0(j4, j)) {
                            j4 = commonTokens2.rippleColor;
                        }
                        long j5 = commonTokens.roundedCornerColor;
                        if (ULong.m3427equalsimpl0(j5, j)) {
                            j5 = commonTokens2.roundedCornerColor;
                        }
                        long j6 = commonTokens.mainTextColor;
                        if (ULong.m3427equalsimpl0(j6, j)) {
                            j6 = commonTokens2.mainTextColor;
                        }
                        long j7 = j2;
                        long j8 = commonTokens.subTextColor;
                        if (ULong.m3427equalsimpl0(j8, j)) {
                            j8 = commonTokens2.subTextColor;
                        }
                        long j9 = commonTokens.pointTextColor;
                        if (ULong.m3427equalsimpl0(j9, j)) {
                            j9 = commonTokens2.pointTextColor;
                        }
                        return new SeslCommonTokens(j7, j3, j4, j5, j6, j8, j9, null);
                    case 1:
                        SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
                        SeslTabTokens tabTokens2 = seslTokenScheme2.getTabTokens();
                        tabTokens.getClass();
                        Color.Companion.getClass();
                        long j10 = Color.Unspecified;
                        long j11 = tabTokens.textColor;
                        if (ULong.m3427equalsimpl0(j11, j10)) {
                            j11 = tabTokens2.textColor;
                        }
                        long j12 = tabTokens.selectedTextColor;
                        if (ULong.m3427equalsimpl0(j12, j10)) {
                            j12 = tabTokens2.selectedTextColor;
                        }
                        long j13 = tabTokens.subTabTextColor;
                        if (ULong.m3427equalsimpl0(j13, j10)) {
                            j13 = tabTokens2.subTabTextColor;
                        }
                        long j14 = tabTokens.subTabSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j14, j10)) {
                            j14 = tabTokens2.subTabSelectedTextColor;
                        }
                        long j15 = tabTokens.subTabTwoLineTextColor;
                        if (ULong.m3427equalsimpl0(j15, j10)) {
                            j15 = tabTokens2.subTabTwoLineTextColor;
                        }
                        long j16 = j11;
                        long j17 = tabTokens.subTabTwoLineSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j17, j10)) {
                            j17 = tabTokens2.subTabTwoLineSelectedTextColor;
                        }
                        long j18 = j17;
                        long j19 = tabTokens.subTabTwoLineSubTextColor;
                        if (ULong.m3427equalsimpl0(j19, j10)) {
                            j19 = tabTokens2.subTabTwoLineSubTextColor;
                        }
                        long j20 = j19;
                        long j21 = tabTokens.subTabTwoLineSubSelectedTextColor;
                        if (ULong.m3427equalsimpl0(j21, j10)) {
                            j21 = tabTokens2.subTabTwoLineSubSelectedTextColor;
                        }
                        long j22 = tabTokens.subTabIndicatorBackgroundColor;
                        if (ULong.m3427equalsimpl0(j22, j10)) {
                            j22 = tabTokens2.subTabIndicatorBackgroundColor;
                        }
                        return new SeslTabTokens(j16, j12, j13, j14, j15, j18, j20, j21, j22, null);
                    case 2:
                        SeslListTokens listTokens = seslTokenScheme.getListTokens();
                        SeslListTokens listTokens2 = seslTokenScheme2.getListTokens();
                        listTokens.getClass();
                        Color.Companion.getClass();
                        long j23 = Color.Unspecified;
                        long j24 = listTokens.scrollbarThumbActivateColor;
                        if (ULong.m3427equalsimpl0(j24, j23)) {
                            j24 = listTokens2.scrollbarThumbActivateColor;
                        }
                        long j25 = listTokens.scrollbarThumbInactiveColor;
                        if (ULong.m3427equalsimpl0(j25, j23)) {
                            j25 = listTokens2.scrollbarThumbInactiveColor;
                        }
                        return new SeslListTokens(j24, j25, null);
                    case 3:
                        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
                        SeslAppBarTokens appBarTokens2 = seslTokenScheme2.getAppBarTokens();
                        appBarTokens.getClass();
                        Color.Companion.getClass();
                        long j26 = Color.Unspecified;
                        long j27 = appBarTokens.topAppBarBackgroundColor;
                        if (ULong.m3427equalsimpl0(j27, j26)) {
                            j27 = appBarTokens2.topAppBarBackgroundColor;
                        }
                        long j28 = appBarTokens.topAppBarTitleTextColor;
                        if (ULong.m3427equalsimpl0(j28, j26)) {
                            j28 = appBarTokens2.topAppBarTitleTextColor;
                        }
                        long j29 = appBarTokens.topAppBarSubTitleTextColor;
                        if (ULong.m3427equalsimpl0(j29, j26)) {
                            j29 = appBarTokens2.topAppBarSubTitleTextColor;
                        }
                        long j30 = appBarTokens.topAppBarMenuTextColor;
                        if (ULong.m3427equalsimpl0(j30, j26)) {
                            j30 = appBarTokens2.topAppBarMenuTextColor;
                        }
                        long j31 = appBarTokens.topExtendedAppBarSubTitleColor;
                        if (ULong.m3427equalsimpl0(j31, j26)) {
                            j31 = appBarTokens2.topExtendedAppBarSubTitleColor;
                        }
                        DimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1 = DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
                        SeslDpProducer seslDpProducer = appBarTokens.topAppBarTopPaddingDp;
                        if (Intrinsics.areEqual(seslDpProducer, dimensionSchemeKeyTokensKt$EmptySeslDpProducer$1)) {
                            seslDpProducer = appBarTokens2.topAppBarTopPaddingDp;
                        }
                        return new SeslAppBarTokens(j27, j28, j29, j30, j31, seslDpProducer, null);
                    case 4:
                        SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
                        SeslDividerTokens dividerTokens2 = seslTokenScheme2.getDividerTokens();
                        dividerTokens.getClass();
                        Color.Companion.getClass();
                        long j32 = Color.Unspecified;
                        long j33 = dividerTokens.horizontalDividerColor;
                        if (ULong.m3427equalsimpl0(j33, j32)) {
                            j33 = dividerTokens2.horizontalDividerColor;
                        }
                        long j34 = dividerTokens.verticalDividerColor;
                        if (ULong.m3427equalsimpl0(j34, j32)) {
                            j34 = dividerTokens2.verticalDividerColor;
                        }
                        return new SeslDividerTokens(j33, j34, null);
                    case 5:
                        SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
                        SeslSwitchTokens switchTokens2 = seslTokenScheme2.getSwitchTokens();
                        switchTokens.getClass();
                        Color.Companion.getClass();
                        long j35 = Color.Unspecified;
                        long j36 = switchTokens.trackOnColor;
                        if (ULong.m3427equalsimpl0(j36, j35)) {
                            j36 = switchTokens2.trackOnColor;
                        }
                        long j37 = switchTokens.trackOffColor;
                        if (ULong.m3427equalsimpl0(j37, j35)) {
                            j37 = switchTokens2.trackOffColor;
                        }
                        long j38 = switchTokens.thumbOnColor;
                        if (ULong.m3427equalsimpl0(j38, j35)) {
                            j38 = switchTokens2.thumbOnColor;
                        }
                        long j39 = switchTokens.thumbOffColor;
                        if (ULong.m3427equalsimpl0(j39, j35)) {
                            j39 = switchTokens2.thumbOffColor;
                        }
                        return new SeslSwitchTokens(j36, j37, j38, j39, null);
                    case 6:
                        SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
                        SeslCheckboxTokens checkboxTokens2 = seslTokenScheme2.getCheckboxTokens();
                        Drawable drawable = checkboxTokens.checkboxSelected;
                        SeslDrawableTokens.Companion.getClass();
                        Drawable drawable2 = SeslDrawableTokens.emptyDrawable;
                        return new SeslCheckboxTokens(Intrinsics.areEqual(drawable, drawable2) ? checkboxTokens2.checkboxSelected : checkboxTokens.checkboxSelected, Intrinsics.areEqual(checkboxTokens.checkboxUnselected, drawable2) ? checkboxTokens2.checkboxUnselected : checkboxTokens.checkboxUnselected, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOn, drawable2) ? checkboxTokens2.checkboxDisabledOn : checkboxTokens.checkboxDisabledOn, Intrinsics.areEqual(checkboxTokens.checkboxDisabledOff, drawable2) ? checkboxTokens2.checkboxDisabledOff : checkboxTokens.checkboxDisabledOff);
                    case 7:
                        SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
                        SeslSpinnerTokens spinnerTokens2 = seslTokenScheme2.getSpinnerTokens();
                        spinnerTokens.getClass();
                        Color.Companion.getClass();
                        long j40 = Color.Unspecified;
                        long j41 = spinnerTokens.itemTextColorNormal;
                        if (ULong.m3427equalsimpl0(j41, j40)) {
                            j41 = spinnerTokens2.itemTextColorNormal;
                        }
                        long j42 = spinnerTokens.iconColorDefault;
                        if (ULong.m3427equalsimpl0(j42, j40)) {
                            j42 = spinnerTokens2.iconColorDefault;
                        }
                        return new SeslSpinnerTokens(j41, j42, null);
                    case 8:
                        SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
                        SeslPopupTokens popupTokens2 = seslTokenScheme2.getPopupTokens();
                        popupTokens.getClass();
                        Color.Companion.getClass();
                        long j43 = Color.Unspecified;
                        long j44 = popupTokens.backgroundColor;
                        if (ULong.m3427equalsimpl0(j44, j43)) {
                            j44 = popupTokens2.backgroundColor;
                        }
                        Drawable drawable3 = popupTokens.menuBackground;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslPopupTokens(j44, Intrinsics.areEqual(drawable3, SeslDrawableTokens.emptyDrawable) ? popupTokens2.menuBackground : popupTokens.menuBackground, null);
                    case 9:
                        SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
                        SeslDialogTokens dialogTokens2 = seslTokenScheme2.getDialogTokens();
                        Drawable drawable4 = dialogTokens.background;
                        SeslDrawableTokens.Companion.getClass();
                        return new SeslDialogTokens(Intrinsics.areEqual(drawable4, SeslDrawableTokens.emptyDrawable) ? dialogTokens2.background : dialogTokens.background);
                    case 10:
                        SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
                        SeslAlertDialogTokens alertDialogTokens2 = seslTokenScheme2.getAlertDialogTokens();
                        alertDialogTokens.getClass();
                        Color.Companion.getClass();
                        long j45 = Color.Unspecified;
                        long j46 = alertDialogTokens.titleTextColor;
                        if (ULong.m3427equalsimpl0(j46, j45)) {
                            j46 = alertDialogTokens2.titleTextColor;
                        }
                        long j47 = alertDialogTokens.messageTextColor;
                        if (ULong.m3427equalsimpl0(j47, j45)) {
                            j47 = alertDialogTokens2.messageTextColor;
                        }
                        long j48 = alertDialogTokens.buttonTextColor;
                        if (ULong.m3427equalsimpl0(j48, j45)) {
                            j48 = alertDialogTokens2.buttonTextColor;
                        }
                        return new SeslAlertDialogTokens(j46, j47, j48, null);
                    default:
                        SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
                        SeslSliderTokens sliderTokens2 = seslTokenScheme2.getSliderTokens();
                        sliderTokens.getClass();
                        Color.Companion.getClass();
                        long j49 = Color.Unspecified;
                        long j50 = sliderTokens.thumbFillColor;
                        if (ULong.m3427equalsimpl0(j50, j49)) {
                            j50 = sliderTokens2.thumbFillColor;
                        }
                        long j51 = sliderTokens.activateThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j51, j49)) {
                            j51 = sliderTokens2.activateThumbStrokeColor;
                        }
                        long j52 = sliderTokens.inactiveThumbStrokeColor;
                        if (ULong.m3427equalsimpl0(j52, j49)) {
                            j52 = sliderTokens2.inactiveThumbStrokeColor;
                        }
                        long j53 = sliderTokens.activateTrackColor;
                        if (ULong.m3427equalsimpl0(j53, j49)) {
                            j53 = sliderTokens2.activateTrackColor;
                        }
                        long j54 = sliderTokens.inactivateTrackColor;
                        if (ULong.m3427equalsimpl0(j54, j49)) {
                            j54 = sliderTokens2.inactivateTrackColor;
                        }
                        long j55 = j50;
                        long j56 = sliderTokens.overlapActivateColor;
                        if (ULong.m3427equalsimpl0(j56, j49)) {
                            j56 = sliderTokens2.overlapActivateColor;
                        }
                        long j57 = j56;
                        long j58 = sliderTokens.overlapInactiveColor;
                        if (ULong.m3427equalsimpl0(j58, j49)) {
                            j58 = sliderTokens2.overlapInactiveColor;
                        }
                        long j59 = j58;
                        long j60 = sliderTokens.levelTrackColor;
                        if (ULong.m3427equalsimpl0(j60, j49)) {
                            j60 = sliderTokens2.levelTrackColor;
                        }
                        long j61 = j60;
                        long j62 = sliderTokens.activateTickColor;
                        if (ULong.m3427equalsimpl0(j62, j49)) {
                            j62 = sliderTokens2.activateTickColor;
                        }
                        long j63 = sliderTokens.inactiveTickColor;
                        if (ULong.m3427equalsimpl0(j63, j49)) {
                            j63 = sliderTokens2.inactiveTickColor;
                        }
                        return new SeslSliderTokens(j55, j51, j52, j53, j54, j57, j59, j61, j62, j63, null);
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
