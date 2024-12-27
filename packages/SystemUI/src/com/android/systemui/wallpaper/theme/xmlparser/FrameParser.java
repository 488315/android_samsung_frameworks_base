package com.android.systemui.wallpaper.theme.xmlparser;

import android.text.TextUtils;
import android.widget.FrameLayout;
import com.android.systemui.wallpaper.theme.builder.AnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameAnimationView;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class FrameParser extends BaseParser {
    @Override // com.android.systemui.wallpaper.theme.xmlparser.BaseParser
    public final void parseAttribute(ParserData parserData) {
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
                int parseInt = Integer.parseInt(attributeValue);
                if (attributeName.equalsIgnoreCase("top")) {
                    parserData.mAnimationBuilder.top = parseInt;
                } else if (attributeName.equalsIgnoreCase("minInterval")) {
                    parserData.mAnimationBuilder.minInterval = parseInt;
                }
            }
        }
        parserData.mAnimationBuilder = animationBuilder2;
    }
}
