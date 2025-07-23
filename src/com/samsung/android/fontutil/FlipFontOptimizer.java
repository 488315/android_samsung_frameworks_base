package com.samsung.android.fontutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import com.samsung.android.feature.SemCscFeature;

/* loaded from: classes6.dex */
public class FlipFontOptimizer {
    private static final String EXTRA_FLIPFONT_NAME = "flipfontName";
    private static final String FLIPFONT_ACTION = "samsung.settings.flipfont.APPLY_NEW_FONT";
    private static final String FONT_PACKAGE_ARIAL_NARROW = "com.monotype.android.font.ArialNarrowProRegular";

    public void setFlipfont(Context context) {
        String string = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigSpecialEdition");
        String fontNameFlipFont = Typeface.getFontNameFlipFont();
        if (string.contains("ThomBrowne") && fontNameFlipFont.equalsIgnoreCase("default")) {
            applyFlipFonts(context, FONT_PACKAGE_ARIAL_NARROW);
        }
    }

    public void applyFlipFonts(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction(FLIPFONT_ACTION);
        intent.setClassName("com.android.settings", "com.samsung.android.settings.flipfont.FlipFontReceiver");
        intent.putExtra(EXTRA_FLIPFONT_NAME, str);
        intent.setFlags(268435456);
        context.sendBroadcast(intent);
    }
}
