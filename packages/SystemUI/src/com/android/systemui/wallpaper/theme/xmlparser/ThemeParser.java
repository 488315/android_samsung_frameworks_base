package com.android.systemui.wallpaper.theme.xmlparser;

import android.text.TextUtils;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ThemeParser {
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
