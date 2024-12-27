package com.android.systemui.wallpaper.theme.xmlparser;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.wallpaper.theme.view.FrameImageView;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ViewParser extends BaseParser {
    @Override // com.android.systemui.wallpaper.theme.xmlparser.BaseParser
    public final void parseAttribute(ParserData parserData) {
        XmlPullParser xmlPullParser;
        XmlPullParser xmlPullParser2;
        int i;
        if (parserData == null || (xmlPullParser = parserData.mXpp) == null) {
            return;
        }
        if (!parserData.mIsStartTag) {
            FrameLayout frameLayout = parserData.mRootView;
            FrameImageView frameImageView = parserData.mFrameImageView;
            if (frameImageView == null || frameLayout == null) {
                return;
            }
            if (parserData.mIsWallpaper) {
                frameImageView.setX(0.0f);
                frameImageView.setY(0.0f);
                parserData.mImageViewWidth = -1;
                parserData.mImageViewHeight = -1;
            }
            frameLayout.addView(frameImageView, parserData.mImageViewWidth, parserData.mImageViewHeight);
            parserData.mImageViewWidth = -2;
            parserData.mImageViewHeight = -2;
            return;
        }
        FrameImageView frameImageView2 = new FrameImageView(parserData.mContext);
        parserData.mFrameImageView = frameImageView2;
        int attributeCount = xmlPullParser.getAttributeCount();
        boolean z = parserData.mIsScaled;
        String lowerCase = xmlPullParser.getAttributeValue(3).toLowerCase();
        String lowerCase2 = xmlPullParser.getAttributeValue(4).toLowerCase();
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("parseAttribute: [", lowerCase, " , ", lowerCase2, "] , [");
        m.append(parserData.mPackageWidth);
        m.append(" , ");
        m.append(parserData.mPackageHeight);
        m.append("]");
        Log.d("ViewParser", m.toString());
        int i2 = 0;
        boolean z2 = Math.abs(Float.parseFloat(lowerCase) - parserData.mPackageWidth) < 1.0f && Math.abs(Float.parseFloat(lowerCase2) - parserData.mPackageHeight) < 1.0f;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("parseAttribute: isWallpaperView : ", "ViewParser", z2);
        parserData.mIsWallpaper = z2;
        while (i2 < attributeCount) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            String lowerCase3 = xmlPullParser.getAttributeValue(i2).toLowerCase();
            if (!TextUtils.isEmpty(attributeName) && !TextUtils.isEmpty(lowerCase3)) {
                Log.d("ViewParser", MotionLayout$$ExternalSyntheticOutline0.m("", attributeName, "=\"", lowerCase3, "\" "));
                if (attributeName.equalsIgnoreCase("img")) {
                    Resources resources = parserData.mApkResources;
                    Drawable drawable = resources.getDrawable(resources.getIdentifier(lowerCase3, BriefViewController.URI_PATH_DRAWABLE, parserData.mPkgName));
                    if (drawable != null) {
                        int intrinsicWidth = drawable.getIntrinsicWidth();
                        int intrinsicHeight = drawable.getIntrinsicHeight();
                        if (z || !z2) {
                            xmlPullParser2 = xmlPullParser;
                            i = attributeCount;
                        } else {
                            float f = parserData.mDeviceWidth;
                            float f2 = parserData.mDeviceDensity;
                            float f3 = f * f2;
                            float f4 = parserData.mDeviceHeight * f2;
                            float f5 = intrinsicWidth;
                            float f6 = intrinsicHeight;
                            float f7 = f5 * f4 > f3 * f6 ? f4 / f6 : f3 / f5;
                            float round = Math.round((f3 - (f5 * f7)) * 0.5f);
                            float round2 = Math.round((f4 - (f6 * f7)) * 0.5f);
                            parserData.mScaledRatio = f7;
                            parserData.mScaledDx = round;
                            parserData.mScaledDy = round2;
                            xmlPullParser2 = xmlPullParser;
                            parserData.mIsScaled = true;
                            i = attributeCount;
                            Log.d("ViewParser", "drawableWidth = " + intrinsicWidth);
                            Log.d("ViewParser", "drawableHeight = " + intrinsicHeight);
                            Log.d("ViewParser", "viewWidth = " + f3);
                            Log.d("ViewParser", "viewHeight = " + f4);
                            Log.d("ViewParser", "scaledRatio = " + f7);
                            Log.d("ViewParser", "scaledDx = " + round);
                            Log.d("ViewParser", "scaledDy = " + round2);
                        }
                        if (z2) {
                            frameImageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        } else {
                            frameImageView2.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        frameImageView2.setImageDrawable(drawable);
                    }
                } else {
                    xmlPullParser2 = xmlPullParser;
                    i = attributeCount;
                    if (attributeName.equalsIgnoreCase("x")) {
                        frameImageView2.setX(parserData.getDevicePixelX(Float.parseFloat(lowerCase3)) + parserData.mScaledDx);
                    } else if (attributeName.equalsIgnoreCase("y")) {
                        frameImageView2.setY(parserData.getDevicePixelY(Float.parseFloat(lowerCase3)) + parserData.mScaledDy);
                    } else if (attributeName.equalsIgnoreCase("width")) {
                        parserData.mImageViewWidth = (int) parserData.getDevicePixelX(Float.parseFloat(lowerCase3));
                    } else if (attributeName.equalsIgnoreCase("height")) {
                        parserData.mImageViewHeight = (int) parserData.getDevicePixelY(Float.parseFloat(lowerCase3));
                    } else if (attributeName.equalsIgnoreCase("pivotX")) {
                        frameImageView2.setPivotX(z2 ? parserData.getDevicePixelX(Float.parseFloat(lowerCase3)) + parserData.mScaledDx : parserData.getDevicePixelX(Float.parseFloat(lowerCase3)));
                    } else if (attributeName.equalsIgnoreCase("pivotY")) {
                        frameImageView2.setPivotY(z2 ? parserData.getDevicePixelY(Float.parseFloat(lowerCase3)) + parserData.mScaledDy : parserData.getDevicePixelY(Float.parseFloat(lowerCase3)));
                    } else if (attributeName.equalsIgnoreCase("alpha")) {
                        frameImageView2.setAlpha(Float.parseFloat(lowerCase3));
                    } else if (attributeName.equalsIgnoreCase("scaleX")) {
                        frameImageView2.setScaleX(parserData.getDevicePixelX(Float.parseFloat(lowerCase3)));
                    } else if (attributeName.equalsIgnoreCase("scaleY")) {
                        frameImageView2.setScaleY(parserData.getDevicePixelY(Float.parseFloat(lowerCase3)));
                    }
                }
                i2++;
                xmlPullParser = xmlPullParser2;
                attributeCount = i;
            }
            xmlPullParser2 = xmlPullParser;
            i = attributeCount;
            i2++;
            xmlPullParser = xmlPullParser2;
            attributeCount = i;
        }
    }
}
