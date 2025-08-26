package com.samsung.sesl.compose.foundation.theme;

import com.samsung.sesl.compose.component.tokens.SeslAlertDialogTokens;
import com.samsung.sesl.compose.component.tokens.SeslAppBarTokens;
import com.samsung.sesl.compose.component.tokens.SeslButtonTokens;
import com.samsung.sesl.compose.component.tokens.SeslCheckboxTokens;
import com.samsung.sesl.compose.component.tokens.SeslCommonTokens;
import com.samsung.sesl.compose.component.tokens.SeslDialogTokens;
import com.samsung.sesl.compose.component.tokens.SeslDividerTokens;
import com.samsung.sesl.compose.component.tokens.SeslListTokens;
import com.samsung.sesl.compose.component.tokens.SeslPopupTokens;
import com.samsung.sesl.compose.component.tokens.SeslRadioButtonTokens;
import com.samsung.sesl.compose.component.tokens.SeslSliderTokens;
import com.samsung.sesl.compose.component.tokens.SeslSpinnerTokens;
import com.samsung.sesl.compose.component.tokens.SeslSwitchTokens;
import com.samsung.sesl.compose.component.tokens.SeslTabTokens;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslTokenSchemeImpl implements SeslTokenScheme {
    public final SeslAlertDialogTokens alertDialogTokens;
    public final SeslAppBarTokens appBarTokens;
    public final SeslButtonTokens buttonTokens;
    public final SeslCheckboxTokens checkboxTokens;
    public final SeslCommonTokens commonTokens;
    public final SeslDialogTokens dialogTokens;
    public final SeslDividerTokens dividerTokens;
    public final SeslListTokens listTokens;
    public final SeslPopupTokens popupTokens;
    public final SeslRadioButtonTokens radioButtonTokens;
    public final SeslSliderTokens sliderTokens;
    public final SeslSpinnerTokens spinnerTokens;
    public final SeslSwitchTokens switchTokens;
    public final SeslTabTokens tabTokens;

    public SeslTokenSchemeImpl(SeslAppBarTokens seslAppBarTokens, SeslAlertDialogTokens seslAlertDialogTokens, SeslButtonTokens seslButtonTokens, SeslCheckboxTokens seslCheckboxTokens, SeslCommonTokens seslCommonTokens, SeslDialogTokens seslDialogTokens, SeslDividerTokens seslDividerTokens, SeslListTokens seslListTokens, SeslPopupTokens seslPopupTokens, SeslRadioButtonTokens seslRadioButtonTokens, SeslSliderTokens seslSliderTokens, SeslSpinnerTokens seslSpinnerTokens, SeslSwitchTokens seslSwitchTokens, SeslTabTokens seslTabTokens) {
        this.appBarTokens = seslAppBarTokens;
        this.alertDialogTokens = seslAlertDialogTokens;
        this.buttonTokens = seslButtonTokens;
        this.checkboxTokens = seslCheckboxTokens;
        this.commonTokens = seslCommonTokens;
        this.dialogTokens = seslDialogTokens;
        this.dividerTokens = seslDividerTokens;
        this.listTokens = seslListTokens;
        this.popupTokens = seslPopupTokens;
        this.radioButtonTokens = seslRadioButtonTokens;
        this.sliderTokens = seslSliderTokens;
        this.spinnerTokens = seslSpinnerTokens;
        this.switchTokens = seslSwitchTokens;
        this.tabTokens = seslTabTokens;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslTokenSchemeImpl)) {
            return false;
        }
        SeslTokenSchemeImpl seslTokenSchemeImpl = (SeslTokenSchemeImpl) obj;
        return Intrinsics.areEqual(this.appBarTokens, seslTokenSchemeImpl.appBarTokens) && Intrinsics.areEqual(this.alertDialogTokens, seslTokenSchemeImpl.alertDialogTokens) && Intrinsics.areEqual(this.buttonTokens, seslTokenSchemeImpl.buttonTokens) && Intrinsics.areEqual(this.checkboxTokens, seslTokenSchemeImpl.checkboxTokens) && Intrinsics.areEqual(this.commonTokens, seslTokenSchemeImpl.commonTokens) && Intrinsics.areEqual(this.dialogTokens, seslTokenSchemeImpl.dialogTokens) && Intrinsics.areEqual(this.dividerTokens, seslTokenSchemeImpl.dividerTokens) && Intrinsics.areEqual(this.listTokens, seslTokenSchemeImpl.listTokens) && Intrinsics.areEqual(this.popupTokens, seslTokenSchemeImpl.popupTokens) && Intrinsics.areEqual(this.radioButtonTokens, seslTokenSchemeImpl.radioButtonTokens) && Intrinsics.areEqual(this.sliderTokens, seslTokenSchemeImpl.sliderTokens) && Intrinsics.areEqual(this.spinnerTokens, seslTokenSchemeImpl.spinnerTokens) && Intrinsics.areEqual(this.switchTokens, seslTokenSchemeImpl.switchTokens) && Intrinsics.areEqual(this.tabTokens, seslTokenSchemeImpl.tabTokens);
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
    public final SeslButtonTokens getButtonTokens() {
        return this.buttonTokens;
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
    public final SeslRadioButtonTokens getRadioButtonTokens() {
        return this.radioButtonTokens;
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
        return this.tabTokens.hashCode() + ((this.switchTokens.hashCode() + ((this.spinnerTokens.hashCode() + ((this.sliderTokens.hashCode() + ((this.radioButtonTokens.hashCode() + ((this.popupTokens.hashCode() + ((this.listTokens.hashCode() + ((this.dividerTokens.hashCode() + ((this.dialogTokens.background.hashCode() + ((this.commonTokens.hashCode() + ((this.checkboxTokens.hashCode() + ((this.buttonTokens.hashCode() + ((this.alertDialogTokens.hashCode() + (this.appBarTokens.hashCode() * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "SeslTokenSchemeImpl(appBarTokens=" + this.appBarTokens + ", alertDialogTokens=" + this.alertDialogTokens + ", buttonTokens=" + this.buttonTokens + ", checkboxTokens=" + this.checkboxTokens + ", commonTokens=" + this.commonTokens + ", dialogTokens=" + this.dialogTokens + ", dividerTokens=" + this.dividerTokens + ", listTokens=" + this.listTokens + ", popupTokens=" + this.popupTokens + ", radioButtonTokens=" + this.radioButtonTokens + ", sliderTokens=" + this.sliderTokens + ", spinnerTokens=" + this.spinnerTokens + ", switchTokens=" + this.switchTokens + ", tabTokens=" + this.tabTokens + ")";
    }
}
