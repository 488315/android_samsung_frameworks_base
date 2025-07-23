package com.samsung.sesl.compose.foundation.theme;

import android.graphics.drawable.Drawable;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import com.samsung.sesl.compose.component.tokens.SeslCheckboxDrawableSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslCheckboxTokens;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class BasicDrawableSchemeKt {
    public static final Drawable toDrawable(SeslCheckboxDrawableSchemeKeyTokens seslCheckboxDrawableSchemeKeyTokens, Composer composer) {
        Drawable drawable;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.theme.toDrawable (BasicDrawableScheme.kt:28)");
        }
        SeslTokenScheme seslTokenScheme = (SeslTokenScheme) ((ComposerImpl) composer).consume(TokenSchemeKt.LocalSeslTokenScheme);
        if (seslCheckboxDrawableSchemeKeyTokens != null) {
            SeslCheckboxTokens checkboxTokens = seslTokenScheme.getCheckboxTokens();
            checkboxTokens.getClass();
            int i = SeslCheckboxTokens.WhenMappings.$EnumSwitchMapping$0[seslCheckboxDrawableSchemeKeyTokens.ordinal()];
            if (i == 1) {
                drawable = checkboxTokens.checkboxSelected;
            } else if (i == 2) {
                drawable = checkboxTokens.checkboxUnselected;
            } else if (i == 3) {
                drawable = checkboxTokens.checkboxDisabledOn;
            } else {
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                drawable = checkboxTokens.checkboxDisabledOff;
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
