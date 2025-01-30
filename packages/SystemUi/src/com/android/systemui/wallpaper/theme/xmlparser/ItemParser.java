package com.android.systemui.wallpaper.theme.xmlparser;

import android.content.res.Resources;
import android.text.TextUtils;
import com.android.systemui.wallpaper.theme.builder.AnimationBuilder;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ItemParser extends BaseParser {
    @Override // com.android.systemui.wallpaper.theme.xmlparser.BaseParser
    public final void parseAttribute(ParserData parserData) {
        AnimationBuilder animationBuilder;
        XmlPullParser xmlPullParser;
        if (parserData == null || (animationBuilder = parserData.mAnimationBuilder) == null || (xmlPullParser = parserData.mXpp) == null) {
            return;
        }
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if (!TextUtils.isEmpty(attributeName) && !TextUtils.isEmpty(attributeValue)) {
                if (attributeName.equalsIgnoreCase("frameSize")) {
                    animationBuilder.frameSize.add(Integer.valueOf(Integer.parseInt(attributeValue)));
                } else {
                    boolean equalsIgnoreCase = attributeName.equalsIgnoreCase("image");
                    String str = parserData.mPkgName;
                    Resources resources = parserData.mApkResources;
                    if (equalsIgnoreCase) {
                        animationBuilder.imageViewSetId.add(Integer.valueOf(resources.getIdentifier(attributeValue, "drawable", str)));
                    } else if (attributeName.equalsIgnoreCase("background")) {
                        animationBuilder.backgroundId = resources.getIdentifier(attributeValue, "drawable", str);
                    } else if (attributeName.equalsIgnoreCase("x")) {
                        animationBuilder.f411x.add(Float.valueOf(Float.parseFloat(attributeValue)));
                    } else if (attributeName.equalsIgnoreCase("y")) {
                        animationBuilder.f412y.add(Float.valueOf(Float.parseFloat(attributeValue)));
                    } else if (attributeName.equalsIgnoreCase("scale")) {
                        parserData.mAnimationBuilder.scale.add(Float.valueOf(Float.parseFloat(attributeValue)));
                    } else if (attributeName.equalsIgnoreCase("startIndex")) {
                        animationBuilder.startIndex.add(Integer.valueOf(Integer.parseInt(attributeValue)));
                    }
                }
            }
        }
        parserData.mAnimationBuilder = animationBuilder;
    }
}
