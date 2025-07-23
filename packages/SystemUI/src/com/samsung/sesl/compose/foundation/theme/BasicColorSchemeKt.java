package com.samsung.sesl.compose.foundation.theme;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import com.samsung.sesl.compose.component.tokens.SeslAlertDialogColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslAlertDialogTokens;
import com.samsung.sesl.compose.component.tokens.SeslAppBarColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslAppBarTokens;
import com.samsung.sesl.compose.component.tokens.SeslColorPrimitiveTokens;
import com.samsung.sesl.compose.component.tokens.SeslColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslCommonColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslCommonTokens;
import com.samsung.sesl.compose.component.tokens.SeslDividerColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslDividerTokens;
import com.samsung.sesl.compose.component.tokens.SeslListColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslListTokens;
import com.samsung.sesl.compose.component.tokens.SeslPopupColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslPopupTokens;
import com.samsung.sesl.compose.component.tokens.SeslSliderColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslSliderTokens;
import com.samsung.sesl.compose.component.tokens.SeslSpinnerColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslSpinnerTokens;
import com.samsung.sesl.compose.component.tokens.SeslSwitchColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslSwitchTokens;
import com.samsung.sesl.compose.component.tokens.SeslTabColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslTabTokens;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class BasicColorSchemeKt {
    public static final long toColor(SeslColorSchemeKeyTokens seslColorSchemeKeyTokens, Composer composer) {
        long j;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.theme.toColor (BasicColorScheme.kt:41)");
        }
        SeslTokenScheme seslTokenScheme = (SeslTokenScheme) ((ComposerImpl) composer).consume(TokenSchemeKt.LocalSeslTokenScheme);
        if (seslColorSchemeKeyTokens instanceof SeslColorPrimitiveTokens) {
            j = ((SeslColorPrimitiveTokens) seslColorSchemeKeyTokens).color;
        } else if (seslColorSchemeKeyTokens instanceof SeslCommonColorSchemeKeyTokens) {
            SeslCommonTokens commonTokens = seslTokenScheme.getCommonTokens();
            commonTokens.getClass();
            switch (SeslCommonTokens.WhenMappings.$EnumSwitchMapping$0[((SeslCommonColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()]) {
                case 1:
                    j = commonTokens.primaryColor;
                    break;
                case 2:
                    j = commonTokens.windowBackgroundColor;
                    break;
                case 3:
                    j = commonTokens.rippleColor;
                    break;
                case 4:
                    j = commonTokens.roundedCornerColor;
                    break;
                case 5:
                    j = commonTokens.mainTextColor;
                    break;
                case 6:
                    j = commonTokens.subTextColor;
                    break;
                case 7:
                    j = commonTokens.pointTextColor;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        } else if (seslColorSchemeKeyTokens instanceof SeslSwitchColorSchemeKeyTokens) {
            SeslSwitchTokens switchTokens = seslTokenScheme.getSwitchTokens();
            switchTokens.getClass();
            int i = SeslSwitchTokens.WhenMappings.$EnumSwitchMapping$0[((SeslSwitchColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()];
            if (i == 1) {
                j = switchTokens.trackOnColor;
            } else if (i == 2) {
                j = switchTokens.trackOffColor;
            } else if (i == 3) {
                j = switchTokens.thumbOnColor;
            } else {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                j = switchTokens.thumbOffColor;
            }
        } else if (seslColorSchemeKeyTokens instanceof SeslPopupColorSchemeKeyTokens) {
            SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
            popupTokens.getClass();
            if (SeslPopupTokens.WhenMappings.$EnumSwitchMapping$0[((SeslPopupColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()] != 1) {
                throw new NoWhenBranchMatchedException();
            }
            j = popupTokens.backgroundColor;
        } else if (seslColorSchemeKeyTokens instanceof SeslAlertDialogColorSchemeKeyTokens) {
            SeslAlertDialogTokens alertDialogTokens = seslTokenScheme.getAlertDialogTokens();
            alertDialogTokens.getClass();
            int i2 = SeslAlertDialogTokens.WhenMappings.$EnumSwitchMapping$0[((SeslAlertDialogColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()];
            if (i2 == 1) {
                j = alertDialogTokens.titleTextColor;
            } else if (i2 == 2) {
                j = alertDialogTokens.messageTextColor;
            } else {
                if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                j = alertDialogTokens.buttonTextColor;
            }
        } else if (seslColorSchemeKeyTokens instanceof SeslSliderColorSchemeKeyTokens) {
            SeslSliderTokens sliderTokens = seslTokenScheme.getSliderTokens();
            sliderTokens.getClass();
            switch (SeslSliderTokens.WhenMappings.$EnumSwitchMapping$0[((SeslSliderColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()]) {
                case 1:
                    j = sliderTokens.thumbFillColor;
                    break;
                case 2:
                    j = sliderTokens.activateThumbStrokeColor;
                    break;
                case 3:
                    j = sliderTokens.inactiveThumbStrokeColor;
                    break;
                case 4:
                    j = sliderTokens.activateTrackColor;
                    break;
                case 5:
                    j = sliderTokens.inactivateTrackColor;
                    break;
                case 6:
                    j = sliderTokens.overlapActivateColor;
                    break;
                case 7:
                    j = sliderTokens.overlapInactiveColor;
                    break;
                case 8:
                    j = sliderTokens.levelTrackColor;
                    break;
                case 9:
                    j = sliderTokens.activateTickColor;
                    break;
                case 10:
                    j = sliderTokens.inactiveTickColor;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        } else if (seslColorSchemeKeyTokens instanceof SeslSpinnerColorSchemeKeyTokens) {
            SeslSpinnerTokens spinnerTokens = seslTokenScheme.getSpinnerTokens();
            spinnerTokens.getClass();
            int i3 = SeslSpinnerTokens.WhenMappings.$EnumSwitchMapping$0[((SeslSpinnerColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()];
            if (i3 == 1) {
                j = spinnerTokens.itemTextColorNormal;
            } else {
                if (i3 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                j = spinnerTokens.iconColorDefault;
            }
        } else if (seslColorSchemeKeyTokens instanceof SeslTabColorSchemeKeyTokens) {
            SeslTabTokens tabTokens = seslTokenScheme.getTabTokens();
            tabTokens.getClass();
            switch (SeslTabTokens.WhenMappings.$EnumSwitchMapping$0[((SeslTabColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()]) {
                case 1:
                    j = tabTokens.textColor;
                    break;
                case 2:
                    j = tabTokens.selectedTextColor;
                    break;
                case 3:
                    j = tabTokens.subTabTextColor;
                    break;
                case 4:
                    j = tabTokens.subTabSelectedTextColor;
                    break;
                case 5:
                    j = tabTokens.subTabTwoLineTextColor;
                    break;
                case 6:
                    j = tabTokens.subTabTwoLineSelectedTextColor;
                    break;
                case 7:
                    j = tabTokens.subTabTwoLineSubTextColor;
                    break;
                case 8:
                    j = tabTokens.subTabTwoLineSubSelectedTextColor;
                    break;
                case 9:
                    j = tabTokens.subTabIndicatorBackgroundColor;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        } else if (seslColorSchemeKeyTokens instanceof SeslListColorSchemeKeyTokens) {
            SeslListTokens listTokens = seslTokenScheme.getListTokens();
            listTokens.getClass();
            int i4 = SeslListTokens.WhenMappings.$EnumSwitchMapping$0[((SeslListColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()];
            if (i4 == 1) {
                j = listTokens.scrollbarThumbActivateColor;
            } else {
                if (i4 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                j = listTokens.scrollbarThumbInactiveColor;
            }
        } else if (seslColorSchemeKeyTokens instanceof SeslAppBarColorSchemeKeyTokens) {
            SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
            appBarTokens.getClass();
            int i5 = SeslAppBarTokens.WhenMappings.$EnumSwitchMapping$0[((SeslAppBarColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()];
            if (i5 == 1) {
                j = appBarTokens.topAppBarBackgroundColor;
            } else if (i5 == 2) {
                j = appBarTokens.topAppBarTitleTextColor;
            } else if (i5 == 3) {
                j = appBarTokens.topAppBarSubTitleTextColor;
            } else if (i5 == 4) {
                j = appBarTokens.topAppBarMenuTextColor;
            } else {
                if (i5 != 5) {
                    throw new NoWhenBranchMatchedException();
                }
                j = appBarTokens.topExtendedAppBarSubTitleColor;
            }
        } else {
            if (!(seslColorSchemeKeyTokens instanceof SeslDividerColorSchemeKeyTokens)) {
                throw new NoWhenBranchMatchedException();
            }
            SeslDividerTokens dividerTokens = seslTokenScheme.getDividerTokens();
            dividerTokens.getClass();
            int i6 = SeslDividerTokens.WhenMappings.$EnumSwitchMapping$0[((SeslDividerColorSchemeKeyTokens) seslColorSchemeKeyTokens).ordinal()];
            if (i6 == 1) {
                j = dividerTokens.horizontalDividerColor;
            } else {
                if (i6 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                j = dividerTokens.verticalDividerColor;
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return j;
    }
}
