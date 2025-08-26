package com.android.systemui.wallpaper.theme.xmlparser;

import android.text.TextUtils;
import android.widget.FrameLayout;
import com.android.systemui.wallpaper.theme.builder.AnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameAnimationView;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes3.dex */
public class FrameParser extends BaseParser {
    @Override // com.android.systemui.wallpaper.theme.xmlparser.BaseParser
    public final void parseAttribute(ParserData parserData) throws NumberFormatException {
        XmlPullParser xmlPullParser;
        if (parserData == null || (xmlPullParser = parserData.mXpp) == null) {
            return;
        }
        if (!parserData.mIsStartTag) {
            AnimationBuilder animationBuilder = parserData.mAnimationBuilder;
            if (animationBuilder == null) {
                return;
            }
            FrameAnimationView frameAnimationView = new FrameAnimationView(parserData.mContext, parserData.mApkResources, animationBuilder.backgroundId, animationBuilder.imageViewSetId, animationBuilder.frameSize, animationBuilder.x, animationBuilder.y, animationBuilder.scale, animationBuilder.startIndex);
            frameAnimationView.setTop(animationBuilder.top);
            frameAnimationView.mMinInterval = animationBuilder.minInterval;
            FrameLayout frameLayout = parserData.mRootView;
            if (frameLayout == null || parserData.mComplexAnimationBuilder == null) {
                return;
            }
            frameLayout.addView(frameAnimationView, -2, -2);
            parserData.mComplexAnimationBuilder.mFestivalSpriteView = frameAnimationView;
            return;
        }
        AnimationBuilder animationBuilder2 = new AnimationBuilder();
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if (!TextUtils.isEmpty(attributeName) && !TextUtils.isEmpty(attributeValue)) {
                int i2 = Integer.parseInt(attributeValue);
                if (attributeName.equalsIgnoreCase("top")) {
                    parserData.mAnimationBuilder.top = i2;
                } else if (attributeName.equalsIgnoreCase("minInterval")) {
                    parserData.mAnimationBuilder.minInterval = i2;
                }
            }
        }
        parserData.mAnimationBuilder = animationBuilder2;
    }
}
