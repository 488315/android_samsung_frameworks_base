package com.android.server.graphics.fonts;

import android.graphics.fonts.SystemFonts;

import java.util.Map;
import java.util.function.Function;

public final /* synthetic */ class UpdatableFontDir$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return SystemFonts.getSystemFontConfig((Map) obj, 0L, 0);
    }
}
