package com.android.systemui.wallpaper.theme.xmlparser;

import android.text.TextUtils;
import com.android.systemui.wallpaper.theme.builder.AnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameAnimationView;
import com.android.systemui.wallpaper.view.KeyguardAnimatedWallpaper;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
            FrameAnimationView frameAnimationView = new FrameAnimationView(parserData.mContext, parserData.mApkResources, animationBuilder.backgroundId, animationBuilder.imageViewSetId, animationBuilder.frameSize, animationBuilder.f411x, animationBuilder.f412y, animationBuilder.scale, animationBuilder.startIndex);
            frameAnimationView.setTop(animationBuilder.top);
            frameAnimationView.mMinInterval = animationBuilder.minInterval;
            KeyguardAnimatedWallpaper keyguardAnimatedWallpaper = parserData.mRootView;
            if (keyguardAnimatedWallpaper == null || parserData.mComplexAnimationBuilder == null) {
                return;
            }
            keyguardAnimatedWallpaper.addView(frameAnimationView, -2, -2);
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
