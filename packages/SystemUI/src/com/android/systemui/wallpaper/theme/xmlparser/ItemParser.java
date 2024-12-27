package com.android.systemui.wallpaper.theme.xmlparser;

import android.text.TextUtils;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.wallpaper.theme.builder.AnimationBuilder;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
                    if (equalsIgnoreCase) {
                        animationBuilder.imageViewSetId.add(Integer.valueOf(parserData.mApkResources.getIdentifier(attributeValue, BriefViewController.URI_PATH_DRAWABLE, str)));
                    } else if (attributeName.equalsIgnoreCase("background")) {
                        animationBuilder.backgroundId = parserData.mApkResources.getIdentifier(attributeValue, BriefViewController.URI_PATH_DRAWABLE, str);
                    } else if (attributeName.equalsIgnoreCase("x")) {
                        animationBuilder.x.add(Float.valueOf(Float.parseFloat(attributeValue)));
                    } else if (attributeName.equalsIgnoreCase("y")) {
                        animationBuilder.y.add(Float.valueOf(Float.parseFloat(attributeValue)));
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
