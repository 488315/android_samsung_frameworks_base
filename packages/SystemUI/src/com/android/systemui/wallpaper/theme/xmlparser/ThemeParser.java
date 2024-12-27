package com.android.systemui.wallpaper.theme.xmlparser;

import android.text.TextUtils;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ThemeParser {
    public final ParserData mParserData;
    public HashMap mParserMap;

    public ThemeParser(ParserData parserData) {
        this.mParserData = parserData;
    }

    public static String getAnimationTagName(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.equalsIgnoreCase("rotate") || str.equalsIgnoreCase("parabola") || str.equalsIgnoreCase("sinX") || str.equalsIgnoreCase("sinY") || str.equalsIgnoreCase("round") || str.equalsIgnoreCase("ellipse") || str.equalsIgnoreCase("alpha") || str.equalsIgnoreCase("translateX") || str.equalsIgnoreCase("translateY") || str.equalsIgnoreCase("scaleX") || str.equalsIgnoreCase("scaleY") || str.equalsIgnoreCase("ImageResource")) {
            return str.equals("rotate") ? "rotation" : str.equals("translateX") ? "x" : str.equals("translateY") ? "y" : str;
        }
        return null;
    }
}
