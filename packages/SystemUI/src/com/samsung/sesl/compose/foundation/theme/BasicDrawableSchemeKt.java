package com.samsung.sesl.compose.foundation.theme;

import android.graphics.drawable.Drawable;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import com.samsung.sesl.compose.component.tokens.SeslAppBarDrawableSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslAppBarTokens;
import com.samsung.sesl.compose.component.tokens.SeslCheckboxDrawableSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslCheckboxTokens;
import com.samsung.sesl.compose.component.tokens.SeslDialogDrawableSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslDialogTokens;
import com.samsung.sesl.compose.component.tokens.SeslDrawableSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import com.samsung.sesl.compose.component.tokens.SeslPopupDrawableSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslPopupTokens;
import com.samsung.sesl.compose.component.tokens.SeslRadioButtonDrawableSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslRadioButtonTokens;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes4.dex */
public abstract class BasicDrawableSchemeKt {
    public static final Drawable toDrawable(SeslDrawableSchemeKeyTokens seslDrawableSchemeKeyTokens, Composer composer) {
        Drawable drawable;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.theme.toDrawable (BasicDrawableScheme.kt:32)");
        }
        SeslTokenScheme seslTokenScheme = (SeslTokenScheme) ((ComposerImpl) composer).consume(TokenSchemeKt.LocalSeslTokenScheme);
        if (seslDrawableSchemeKeyTokens instanceof SeslAppBarDrawableSchemeKeyTokens) {
            SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
            appBarTokens.getClass();
            if (SeslAppBarTokens.WhenMappings.$EnumSwitchMapping$2[((SeslAppBarDrawableSchemeKeyTokens) seslDrawableSchemeKeyTokens).ordinal()] != 1) {
                throw new NoWhenBranchMatchedException();
            }
            drawable = appBarTokens.backIcon;
        } else if (seslDrawableSchemeKeyTokens instanceof SeslDialogDrawableSchemeKeyTokens) {
            SeslDialogTokens dialogTokens = seslTokenScheme.getDialogTokens();
            dialogTokens.getClass();
            if (SeslDialogTokens.WhenMappings.$EnumSwitchMapping$0[((SeslDialogDrawableSchemeKeyTokens) seslDrawableSchemeKeyTokens).ordinal()] != 1) {
                throw new NoWhenBranchMatchedException();
            }
            drawable = dialogTokens.background;
        } else if (seslDrawableSchemeKeyTokens instanceof SeslPopupDrawableSchemeKeyTokens) {
            SeslPopupTokens popupTokens = seslTokenScheme.getPopupTokens();
            popupTokens.getClass();
            if (SeslPopupTokens.WhenMappings.$EnumSwitchMapping$1[((SeslPopupDrawableSchemeKeyTokens) seslDrawableSchemeKeyTokens).ordinal()] != 1) {
                throw new NoWhenBranchMatchedException();
            }
            drawable = popupTokens.menuBackground;
        } else if (seslDrawableSchemeKeyTokens instanceof SeslCheckboxDrawableSchemeKeyTokens) {
            SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
            checkboxTokens.getClass();
            int i = SeslCheckboxTokens.WhenMappings.$EnumSwitchMapping$0[((SeslCheckboxDrawableSchemeKeyTokens) seslDrawableSchemeKeyTokens).ordinal()];
            if (i == 1) {
                drawable = checkboxTokens.selectedDrawable;
            } else if (i == 2) {
                drawable = checkboxTokens.unselectedDrawable;
            } else if (i == 3) {
                drawable = checkboxTokens.disabledOnDrawable;
            } else {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                drawable = checkboxTokens.disabledOffDrawable;
            }
        } else if (seslDrawableSchemeKeyTokens instanceof SeslRadioButtonDrawableSchemeKeyTokens) {
            SeslRadioButtonTokens radioButtonTokens = seslTokenScheme.getRadioButtonTokens();
            radioButtonTokens.getClass();
            int i2 = SeslRadioButtonTokens.WhenMappings.$EnumSwitchMapping$0[((SeslRadioButtonDrawableSchemeKeyTokens) seslDrawableSchemeKeyTokens).ordinal()];
            if (i2 == 1) {
                drawable = radioButtonTokens.selectedDrawable;
            } else if (i2 == 2) {
                drawable = radioButtonTokens.unselectedDrawable;
            } else if (i2 == 3) {
                drawable = radioButtonTokens.disabledOnDrawable;
            } else {
                if (i2 != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                drawable = radioButtonTokens.disabledOffDrawable;
            }
        } else {
            SeslDrawableTokens.Companion.getClass();
            drawable = SeslDrawableTokens.emptyDrawable;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return drawable;
    }
}
