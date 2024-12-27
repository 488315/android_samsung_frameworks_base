package com.android.systemui.wallpaper.theme.xmlparser;

import android.text.TextUtils;
import org.xmlpull.v1.XmlPullParser;

public final class ScreenParser extends BaseParser {
    @Override // com.android.systemui.wallpaper.theme.xmlparser.BaseParser
    public final void parseAttribute(ParserData parserData) {
        XmlPullParser xmlPullParser;
        if (parserData == null || (xmlPullParser = parserData.mXpp) == null) {
            return;
        }
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String lowerCase = xmlPullParser.getAttributeValue(i).toLowerCase();
            if (!TextUtils.isEmpty(attributeName) && !TextUtils.isEmpty(lowerCase)) {
                if (attributeName.equalsIgnoreCase("width")) {
                    parserData.mPackageWidth = Float.parseFloat(lowerCase);
                } else if (attributeName.equalsIgnoreCase("height")) {
                    parserData.mPackageHeight = Float.parseFloat(lowerCase);
                }
            }
        }
    }
}
