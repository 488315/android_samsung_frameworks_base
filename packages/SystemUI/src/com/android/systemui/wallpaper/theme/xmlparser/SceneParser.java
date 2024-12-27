package com.android.systemui.wallpaper.theme.xmlparser;

import android.text.TextUtils;
import android.widget.FrameLayout;
import com.android.systemui.wallpaper.theme.builder.ComplexAnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FlowerView;
import com.android.systemui.wallpaper.theme.view.LeafView;
import com.android.systemui.wallpaper.theme.view.RainView;
import com.android.systemui.wallpaper.theme.view.SnowView;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SceneParser extends BaseParser {
    @Override // com.android.systemui.wallpaper.theme.xmlparser.BaseParser
    public final void parseAttribute(ParserData parserData) {
        XmlPullParser xmlPullParser;
        if (parserData == null || (xmlPullParser = parserData.mXpp) == null) {
            return;
        }
        ComplexAnimationBuilder complexAnimationBuilder = parserData.mComplexAnimationBuilder;
        FrameLayout frameLayout = parserData.mRootView;
        if (complexAnimationBuilder == null || frameLayout == null) {
            return;
        }
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if (!TextUtils.isEmpty(attributeName) && !TextUtils.isEmpty(attributeValue) && attributeName.equalsIgnoreCase("type")) {
                if (attributeValue.equals("snow")) {
                    SnowView snowView = new SnowView(parserData.mContext);
                    complexAnimationBuilder.mLockscreenCallback = snowView;
                    frameLayout.addView(snowView, -1, -1);
                } else if (attributeValue.equals("rain")) {
                    RainView rainView = new RainView(parserData.mContext);
                    complexAnimationBuilder.mLockscreenCallback = rainView;
                    frameLayout.addView(rainView, -1, -1);
                } else if (attributeValue.equals("leaf")) {
                    LeafView leafView = new LeafView(parserData.mContext);
                    complexAnimationBuilder.mLockscreenCallback = leafView;
                    frameLayout.addView(leafView, -1, -1);
                } else if (attributeValue.equals("flower")) {
                    FlowerView flowerView = new FlowerView(parserData.mContext);
                    complexAnimationBuilder.mLockscreenCallback = flowerView;
                    frameLayout.addView(flowerView, -1, -1);
                }
            }
        }
    }
}
