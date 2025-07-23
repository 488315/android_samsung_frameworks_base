package com.samsung.sesl.compose.foundation.theme;

import com.samsung.sesl.compose.component.tokens.SeslAlertDialogTokens;
import com.samsung.sesl.compose.component.tokens.SeslAppBarTokens;
import com.samsung.sesl.compose.component.tokens.SeslCheckboxTokens;
import com.samsung.sesl.compose.component.tokens.SeslCommonTokens;
import com.samsung.sesl.compose.component.tokens.SeslDialogTokens;
import com.samsung.sesl.compose.component.tokens.SeslDividerTokens;
import com.samsung.sesl.compose.component.tokens.SeslListTokens;
import com.samsung.sesl.compose.component.tokens.SeslPopupTokens;
import com.samsung.sesl.compose.component.tokens.SeslSliderTokens;
import com.samsung.sesl.compose.component.tokens.SeslSpinnerTokens;
import com.samsung.sesl.compose.component.tokens.SeslSwitchTokens;
import com.samsung.sesl.compose.component.tokens.SeslTabTokens;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslTokenSchemeImpl implements SeslTokenScheme {
    public final SeslAlertDialogTokens alertDialogTokens;
    public final SeslAppBarTokens appBarTokens;
    public final SeslCheckboxTokens checkboxTokens;
    public final SeslCommonTokens commonTokens;
    public final SeslDialogTokens dialogTokens;
    public final SeslDividerTokens dividerTokens;
    public final SeslListTokens listTokens;
    public final SeslPopupTokens popupTokens;
    public final SeslSliderTokens sliderTokens;
    public final SeslSpinnerTokens spinnerTokens;
    public final SeslSwitchTokens switchTokens;
    public final SeslTabTokens tabTokens;

    public SeslTokenSchemeImpl(SeslCommonTokens seslCommonTokens, SeslSwitchTokens seslSwitchTokens, SeslCheckboxTokens seslCheckboxTokens, SeslSpinnerTokens seslSpinnerTokens, SeslPopupTokens seslPopupTokens, SeslDialogTokens seslDialogTokens, SeslAlertDialogTokens seslAlertDialogTokens, SeslSliderTokens seslSliderTokens, SeslTabTokens seslTabTokens, SeslListTokens seslListTokens, SeslAppBarTokens seslAppBarTokens, SeslDividerTokens seslDividerTokens) {
        this.commonTokens = seslCommonTokens;
        this.switchTokens = seslSwitchTokens;
        this.checkboxTokens = seslCheckboxTokens;
        this.spinnerTokens = seslSpinnerTokens;
        this.popupTokens = seslPopupTokens;
        this.dialogTokens = seslDialogTokens;
        this.alertDialogTokens = seslAlertDialogTokens;
        this.sliderTokens = seslSliderTokens;
        this.tabTokens = seslTabTokens;
        this.listTokens = seslListTokens;
        this.appBarTokens = seslAppBarTokens;
        this.dividerTokens = seslDividerTokens;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslTokenSchemeImpl)) {
            return false;
        }
        SeslTokenSchemeImpl seslTokenSchemeImpl = (SeslTokenSchemeImpl) obj;
        return Intrinsics.areEqual(this.commonTokens, seslTokenSchemeImpl.commonTokens) && Intrinsics.areEqual(this.switchTokens, seslTokenSchemeImpl.switchTokens) && Intrinsics.areEqual(this.checkboxTokens, seslTokenSchemeImpl.checkboxTokens) && Intrinsics.areEqual(this.spinnerTokens, seslTokenSchemeImpl.spinnerTokens) && Intrinsics.areEqual(this.popupTokens, seslTokenSchemeImpl.popupTokens) && Intrinsics.areEqual(this.dialogTokens, seslTokenSchemeImpl.dialogTokens) && Intrinsics.areEqual(this.alertDialogTokens, seslTokenSchemeImpl.alertDialogTokens) && Intrinsics.areEqual(this.sliderTokens, seslTokenSchemeImpl.sliderTokens) && Intrinsics.areEqual(this.tabTokens, seslTokenSchemeImpl.tabTokens) && Intrinsics.areEqual(this.listTokens, seslTokenSchemeImpl.listTokens) && Intrinsics.areEqual(this.appBarTokens, seslTokenSchemeImpl.appBarTokens) && Intrinsics.areEqual(this.dividerTokens, seslTokenSchemeImpl.dividerTokens);
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslAlertDialogTokens getAlertDialogTokens() {
        return this.alertDialogTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslAppBarTokens getAppBarTokens() {
        return this.appBarTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslCheckboxTokens getCheckboxTokens() {
        return this.checkboxTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslCommonTokens getCommonTokens() {
        return this.commonTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslDialogTokens getDialogTokens() {
        return this.dialogTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslDividerTokens getDividerTokens() {
        return this.dividerTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslListTokens getListTokens() {
        return this.listTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslPopupTokens getPopupTokens() {
        return this.popupTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslSliderTokens getSliderTokens() {
        return this.sliderTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslSpinnerTokens getSpinnerTokens() {
        return this.spinnerTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslSwitchTokens getSwitchTokens() {
        return this.switchTokens;
    }

    @Override // com.samsung.sesl.compose.foundation.theme.SeslTokenScheme
    public final SeslTabTokens getTabTokens() {
        return this.tabTokens;
    }

    public final int hashCode() {
        return this.dividerTokens.hashCode() + ((this.appBarTokens.hashCode() + ((this.listTokens.hashCode() + ((this.tabTokens.hashCode() + ((this.sliderTokens.hashCode() + ((this.alertDialogTokens.hashCode() + ((this.dialogTokens.background.hashCode() + ((this.popupTokens.hashCode() + ((this.spinnerTokens.hashCode() + ((this.checkboxTokens.hashCode() + ((this.switchTokens.hashCode() + (this.commonTokens.hashCode() * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "SeslTokenSchemeImpl(commonTokens=" + this.commonTokens + ", switchTokens=" + this.switchTokens + ", checkboxTokens=" + this.checkboxTokens + ", spinnerTokens=" + this.spinnerTokens + ", popupTokens=" + this.popupTokens + ", dialogTokens=" + this.dialogTokens + ", alertDialogTokens=" + this.alertDialogTokens + ", sliderTokens=" + this.sliderTokens + ", tabTokens=" + this.tabTokens + ", listTokens=" + this.listTokens + ", appBarTokens=" + this.appBarTokens + ", dividerTokens=" + this.dividerTokens + ")";
    }
}
