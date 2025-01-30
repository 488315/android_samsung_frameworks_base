package com.android.systemui.wallpaper.theme.xmlparser;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.theme.view.FrameImageView;
import com.android.systemui.wallpaper.view.KeyguardAnimatedWallpaper;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ViewParser extends BaseParser {
    @Override // com.android.systemui.wallpaper.theme.xmlparser.BaseParser
    public final void parseAttribute(ParserData parserData) {
        XmlPullParser xmlPullParser;
        KeyguardAnimatedWallpaper keyguardAnimatedWallpaper;
        XmlPullParser xmlPullParser2;
        int i;
        WallpaperResultCallback wallpaperResultCallback;
        Bitmap createBitmap;
        if (parserData == null || (xmlPullParser = parserData.mXpp) == null) {
            return;
        }
        if (!parserData.mIsStartTag) {
            FrameImageView frameImageView = parserData.mFrameImageView;
            if (frameImageView == null || (keyguardAnimatedWallpaper = parserData.mRootView) == null) {
                return;
            }
            if (parserData.mIsWallpaper) {
                frameImageView.setX(0.0f);
                frameImageView.setY(0.0f);
                parserData.mImageViewWidth = -1;
                parserData.mImageViewHeight = -1;
            }
            keyguardAnimatedWallpaper.addView(frameImageView, parserData.mImageViewWidth, parserData.mImageViewHeight);
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
        StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("parseAttribute: [", lowerCase, " , ", lowerCase2, "] , [");
        m87m.append(parserData.mPackageWidth);
        m87m.append(" , ");
        m87m.append(parserData.mPackageHeight);
        m87m.append("]");
        Log.d("ViewParser", m87m.toString());
        boolean z2 = Math.abs(Float.parseFloat(lowerCase) - parserData.mPackageWidth) < 1.0f && Math.abs(Float.parseFloat(lowerCase2) - parserData.mPackageHeight) < 1.0f;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("parseAttribute: isWallpaperView : ", z2, "ViewParser");
        parserData.mIsWallpaper = z2;
        int i2 = 0;
        while (i2 < attributeCount) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            String lowerCase3 = xmlPullParser.getAttributeValue(i2).toLowerCase();
            if (!TextUtils.isEmpty(attributeName) && !TextUtils.isEmpty(lowerCase3)) {
                Log.d("ViewParser", MotionLayout$$ExternalSyntheticOutline0.m22m("", attributeName, "=\"", lowerCase3, "\" "));
                if (attributeName.equalsIgnoreCase("img")) {
                    String str = parserData.mPkgName;
                    Resources resources = parserData.mApkResources;
                    Drawable drawable = resources.getDrawable(resources.getIdentifier(lowerCase3, "drawable", str));
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
                            SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(new StringBuilder("scaledDy = "), round2, "ViewParser");
                        }
                        if (z2) {
                            frameImageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            if (!parserData.mIsPreview && !z && (wallpaperResultCallback = parserData.mWallpaperResultCallback) != null) {
                                boolean z3 = WallpaperUtils.mIsEmergencyMode;
                                if (drawable instanceof BitmapDrawable) {
                                    createBitmap = ((BitmapDrawable) drawable).getBitmap();
                                } else {
                                    createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                                    Canvas canvas = new Canvas(createBitmap);
                                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                    drawable.draw(canvas);
                                }
                                wallpaperResultCallback.onDelegateBitmapReady(createBitmap);
                            }
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
                attributeCount = i;
                xmlPullParser = xmlPullParser2;
            }
            xmlPullParser2 = xmlPullParser;
            i = attributeCount;
            i2++;
            attributeCount = i;
            xmlPullParser = xmlPullParser2;
        }
    }
}
