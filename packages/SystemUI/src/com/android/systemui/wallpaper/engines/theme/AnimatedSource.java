package com.android.systemui.wallpaper.engines.theme;

import android.app.WallpaperManager;
import android.content.APKContents;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.theme.builder.ComplexAnimationBuilder;
import com.android.systemui.wallpaper.theme.xmlparser.AnimationParser;
import com.android.systemui.wallpaper.theme.xmlparser.BaseParser;
import com.android.systemui.wallpaper.theme.xmlparser.FrameParser;
import com.android.systemui.wallpaper.theme.xmlparser.ItemParser;
import com.android.systemui.wallpaper.theme.xmlparser.ParserData;
import com.android.systemui.wallpaper.theme.xmlparser.SceneParser;
import com.android.systemui.wallpaper.theme.xmlparser.ScreenParser;
import com.android.systemui.wallpaper.theme.xmlparser.ThemeParser;
import com.android.systemui.wallpaper.theme.xmlparser.ViewParser;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.wallpaper.Rune;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AnimatedSource {
    public final String TAG;
    public final Context mContext;
    public final String mPackageName;
    public final FrameLayout mRootView;
    public final int mWhich;

    public AnimatedSource(Context context, int i, FrameLayout frameLayout) {
        this.mContext = context;
        int sourceWhich = WhichChecker.getSourceWhich(i);
        this.mWhich = sourceWhich;
        this.mRootView = frameLayout;
        String m = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[AnimatedSource]");
        this.TAG = m;
        String animatedPkgName = WallpaperManager.getInstance(context).getAnimatedPkgName(sourceWhich);
        this.mPackageName = animatedPkgName;
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("AnimatedSource packageName : ", animatedPkgName, m);
    }

    public final ComplexAnimationBuilder createComplexAnimation(int i, int i2) {
        Resources resources;
        String str;
        String str2 = this.mPackageName;
        try {
            resources = this.mContext.createPackageContext(str2, 3).getResources();
        } catch (Exception e) {
            Log.e(this.TAG, "onSurfaceCreated: e = " + e, e);
            resources = null;
        }
        if (resources == null) {
            resources = new APKContents(APKContents.getMainThemePackagePath(str2)).getResources();
        }
        Resources resources2 = resources;
        if (resources2 == null || this.mContext == null || TextUtils.isEmpty(str2)) {
            return null;
        }
        this.mRootView.removeAllViews();
        ThemeParser themeParser = new ThemeParser(new ParserData(resources2, this.mContext, this.mPackageName, this.mRootView, i, i2, false, isFixedOrientation()));
        ParserData parserData = themeParser.mParserData;
        if (parserData == null) {
            return null;
        }
        Resources resources3 = parserData.mApkResources;
        parserData.mXpp = (resources3 == null || (str = parserData.mPkgName) == null) ? null : resources3.getXml(resources3.getIdentifier("animation", "xml", str));
        themeParser.mParserMap = new HashMap();
        XmlPullParser xmlPullParser = parserData.mXpp;
        for (int eventType = xmlPullParser.getEventType(); eventType != 1; eventType = xmlPullParser.next()) {
            if (eventType == 2) {
                parserData.mIsStartTag = true;
            } else if (eventType == 3) {
                parserData.mIsStartTag = false;
            }
            String name = xmlPullParser.getName();
            BaseParser baseParser = (BaseParser) themeParser.mParserMap.get(name);
            if (baseParser == null) {
                if (!TextUtils.isEmpty(name)) {
                    if (name.equalsIgnoreCase(PluginLock.KEY_SCREEN)) {
                        baseParser = new ScreenParser();
                    } else if (name.equalsIgnoreCase("view")) {
                        baseParser = new ViewParser();
                    } else if (name.equalsIgnoreCase("scene")) {
                        baseParser = new SceneParser();
                    } else if (!TextUtils.isEmpty(ThemeParser.getAnimationTagName(name))) {
                        baseParser = new AnimationParser(ThemeParser.getAnimationTagName(name));
                    } else if (name.equalsIgnoreCase("frame")) {
                        baseParser = new FrameParser();
                    } else if (name.equalsIgnoreCase("item")) {
                        baseParser = new ItemParser();
                    }
                }
                baseParser = null;
            }
            if (baseParser != null) {
                themeParser.mParserMap.put(name, baseParser);
            }
            if (baseParser != null) {
                Log.d("ThemeParser", "tagName : " + name);
                baseParser.parseAttribute(parserData);
            }
        }
        return parserData.mComplexAnimationBuilder;
    }

    public final boolean isFixedOrientation() {
        boolean z = Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE;
        boolean isFlagEnabled = z ? WhichChecker.isFlagEnabled(this.mWhich, 16) : true;
        Log.i(this.TAG, "isFixedOrientation: , isFold=" + z + ", isFixedOrientation=" + isFlagEnabled);
        return isFlagEnabled;
    }
}
