package com.samsung.sesl.compose.foundation.theme;

import androidx.compose.runtime.ComputedProvidableCompositionLocal;
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

/* loaded from: classes4.dex */
public abstract class TokenSchemeKt {
    public static final ComputedProvidableCompositionLocal LocalSeslTokenScheme;
    public static final SeslTokenSchemeImpl SeslDarkTokenScheme;
    public static final SeslTokenSchemeImpl SeslLightTokenScheme;

    static {
        SeslAppBarTokens.Companion.getClass();
        SeslAppBarTokens seslAppBarTokens = SeslAppBarTokens.lightAppBarTokens;
        SeslAlertDialogTokens.Companion.getClass();
        SeslAlertDialogTokens seslAlertDialogTokens = SeslAlertDialogTokens.lightAlertDialogTokens;
        SeslButtonTokens.Companion.getClass();
        SeslButtonTokens seslButtonTokens = SeslButtonTokens.lightButtonTokens;
        SeslCheckboxTokens.Companion.getClass();
        SeslCheckboxTokens seslCheckboxTokens = SeslCheckboxTokens.lightCheckboxTokens;
        SeslCommonTokens.Companion.getClass();
        SeslCommonTokens seslCommonTokens = SeslCommonTokens.lightCommonTokens;
        SeslDialogTokens.Companion.getClass();
        SeslDialogTokens seslDialogTokens = SeslDialogTokens.lightDialogTokens;
        SeslDividerTokens.Companion.getClass();
        SeslDividerTokens seslDividerTokens = SeslDividerTokens.lightDividerTokens;
        SeslListTokens.Companion.getClass();
        SeslListTokens seslListTokens = SeslListTokens.lightListTokens;
        SeslPopupTokens.Companion.getClass();
        SeslPopupTokens seslPopupTokens = SeslPopupTokens.lightPopupTokens;
        SeslRadioButtonTokens.Companion.getClass();
        SeslRadioButtonTokens seslRadioButtonTokens = SeslRadioButtonTokens.lightRadioButtonTokens;
        SeslSliderTokens.Companion.getClass();
        SeslSliderTokens seslSliderTokens = SeslSliderTokens.lightSliderTokens;
        SeslSpinnerTokens.Companion.getClass();
        SeslSpinnerTokens seslSpinnerTokens = SeslSpinnerTokens.lightSpinnerTokens;
        SeslSwitchTokens.Companion.getClass();
        SeslSwitchTokens seslSwitchTokens = SeslSwitchTokens.lightSwitchTokens;
        SeslTabTokens.Companion.getClass();
        SeslLightTokenScheme = new SeslTokenSchemeImpl(seslAppBarTokens, seslAlertDialogTokens, seslButtonTokens, seslCheckboxTokens, seslCommonTokens, seslDialogTokens, seslDividerTokens, seslListTokens, seslPopupTokens, seslRadioButtonTokens, seslSliderTokens, seslSpinnerTokens, seslSwitchTokens, SeslTabTokens.lightTabTokens);
        SeslDarkTokenScheme = new SeslTokenSchemeImpl(SeslAppBarTokens.darkAppBarTokens, SeslAlertDialogTokens.darkAlertDialogTokens, SeslButtonTokens.darkButtonTokens, SeslCheckboxTokens.darkCheckboxTokens, SeslCommonTokens.darkCommonTokens, SeslDialogTokens.darkDialogTokens, SeslDividerTokens.darkDividerTokens, SeslListTokens.darkListTokens, SeslPopupTokens.darkPopupTokens, SeslRadioButtonTokens.darkRadioButtonTokens, SeslSliderTokens.darkSliderTokens, SeslSpinnerTokens.darkSpinnerTokens, SeslSwitchTokens.darkSwitchTokens, SeslTabTokens.darkTabTokens);
        LocalSeslTokenScheme = new ComputedProvidableCompositionLocal(new TokenSchemeKt$$ExternalSyntheticLambda0());
    }
}
