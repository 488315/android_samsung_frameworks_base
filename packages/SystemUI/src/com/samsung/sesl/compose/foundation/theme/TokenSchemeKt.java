package com.samsung.sesl.compose.foundation.theme;

import androidx.compose.runtime.ComputedProvidableCompositionLocal;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class TokenSchemeKt {
    public static final ComputedProvidableCompositionLocal LocalSeslTokenScheme;
    public static final SeslTokenSchemeImpl SeslDarkTokenScheme;
    public static final SeslTokenSchemeImpl SeslLightTokenScheme;

    static {
        SeslCommonTokens.Companion.getClass();
        SeslCommonTokens seslCommonTokens = SeslCommonTokens.lightCommonTokens;
        SeslSwitchTokens.Companion.getClass();
        SeslSwitchTokens seslSwitchTokens = SeslSwitchTokens.lightSwitchTokens;
        SeslCheckboxTokens.Companion.getClass();
        SeslCheckboxTokens seslCheckboxTokens = SeslCheckboxTokens.lightCheckboxTokens;
        SeslSpinnerTokens.Companion.getClass();
        SeslSpinnerTokens seslSpinnerTokens = SeslSpinnerTokens.lightSpinnerTokens;
        SeslPopupTokens.Companion.getClass();
        SeslPopupTokens seslPopupTokens = SeslPopupTokens.lightPopupTokens;
        SeslDialogTokens.Companion.getClass();
        SeslDialogTokens seslDialogTokens = SeslDialogTokens.lightDialogTokens;
        SeslAlertDialogTokens.Companion.getClass();
        SeslAlertDialogTokens seslAlertDialogTokens = SeslAlertDialogTokens.lightAlertDialogTokens;
        SeslSliderTokens.Companion.getClass();
        SeslSliderTokens seslSliderTokens = SeslSliderTokens.lightSliderTokens;
        SeslTabTokens.Companion.getClass();
        SeslTabTokens seslTabTokens = SeslTabTokens.lightTabTokens;
        SeslListTokens.Companion.getClass();
        SeslListTokens seslListTokens = SeslListTokens.lightListTokens;
        SeslAppBarTokens.Companion.getClass();
        SeslAppBarTokens seslAppBarTokens = SeslAppBarTokens.lightAppBarTokens;
        SeslDividerTokens.Companion.getClass();
        SeslLightTokenScheme = new SeslTokenSchemeImpl(seslCommonTokens, seslSwitchTokens, seslCheckboxTokens, seslSpinnerTokens, seslPopupTokens, seslDialogTokens, seslAlertDialogTokens, seslSliderTokens, seslTabTokens, seslListTokens, seslAppBarTokens, SeslDividerTokens.lightDividerTokens);
        SeslDarkTokenScheme = new SeslTokenSchemeImpl(SeslCommonTokens.darkCommonTokens, SeslSwitchTokens.darkSwitchTokens, SeslCheckboxTokens.darkCheckboxTokens, SeslSpinnerTokens.darkSpinnerTokens, SeslPopupTokens.darkPopupTokens, SeslDialogTokens.darkDialogTokens, SeslAlertDialogTokens.darkAlertDialogTokens, SeslSliderTokens.darkSliderTokens, SeslTabTokens.darkTabTokens, SeslListTokens.darkListTokens, SeslAppBarTokens.darkAppBarTokens, SeslDividerTokens.darkDividerTokens);
        LocalSeslTokenScheme = new ComputedProvidableCompositionLocal(new TokenSchemeKt$$ExternalSyntheticLambda0());
    }
}
