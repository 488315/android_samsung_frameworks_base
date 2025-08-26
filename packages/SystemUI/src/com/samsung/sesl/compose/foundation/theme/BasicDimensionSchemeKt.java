package com.samsung.sesl.compose.foundation.theme;

import com.samsung.sesl.compose.component.tokens.DimensionSchemeKeyTokensKt;
import com.samsung.sesl.compose.component.tokens.SeslAppBarDimensionSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslAppBarTokens;
import com.samsung.sesl.compose.component.tokens.SeslDpProducer;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes4.dex */
public abstract class BasicDimensionSchemeKt {
    public static final SeslDpProducer fromToken(SeslTokenScheme seslTokenScheme, SeslAppBarDimensionSchemeKeyTokens seslAppBarDimensionSchemeKeyTokens) {
        if (seslAppBarDimensionSchemeKeyTokens == null) {
            return DimensionSchemeKeyTokensKt.EmptySeslDpProducer;
        }
        SeslAppBarTokens appBarTokens = seslTokenScheme.getAppBarTokens();
        appBarTokens.getClass();
        if (SeslAppBarTokens.WhenMappings.$EnumSwitchMapping$1[seslAppBarDimensionSchemeKeyTokens.ordinal()] == 1) {
            return appBarTokens.topAppBarTopPaddingDp;
        }
        throw new NoWhenBranchMatchedException();
    }
}
