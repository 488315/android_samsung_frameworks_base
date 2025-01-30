package com.android.systemui.wallpaper.view;

import android.app.WallpaperManager;
import android.content.APKContents;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.util.DeviceState;
import com.android.systemui.wallpaper.FixedOrientationController;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.WallpaperUtils;
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
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardAnimatedWallpaper extends SystemUIWallpaper {
    public ComplexAnimationBuilder mComplexAnimationBuilder;
    public final Context mContext;
    public final int mCurrentWhich;
    public final FixedOrientationController mFixedOrientationController;
    public boolean mHasWindowFocus;
    public boolean mIsBlurEnabled;
    public boolean mIsPlayingAnimation;
    public final boolean mIsPreview;
    public final Handler mMainHandler;
    public String mPackageName;
    public boolean mShowing;
    public final int mViewHeight;
    public final int mViewWidth;

    public KeyguardAnimatedWallpaper(Context context, String str, KeyguardUpdateMonitor keyguardUpdateMonitor, ExecutorService executorService, WallpaperResultCallback wallpaperResultCallback, Consumer<Boolean> consumer, int i) {
        this(context, str, false, 0, 0, keyguardUpdateMonitor, executorService, wallpaperResultCallback, consumer, i);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void cleanUp() {
        Log.d("KeyguardAnimatedWallpaper", "cleanUp");
        stopAnimation();
        removeAllViews();
        this.mDrawingState = 3;
    }

    public final ComplexAnimationBuilder getComplexAnimation(Context context, Resources resources) {
        ThemeParser themeParser;
        ParserData parserData;
        String str;
        if (resources == null || context == null || TextUtils.isEmpty(this.mPackageName) || (parserData = (themeParser = new ThemeParser(new ParserData(resources, context, this.mPackageName, this, this.mViewWidth, this.mViewHeight, this.mIsPreview, 0, this.mWallpaperResultCallback))).mParserData) == null) {
            return null;
        }
        Resources resources2 = parserData.mApkResources;
        parserData.mXpp = (resources2 == null || (str = parserData.mPkgName) == null) ? null : resources2.getXml(resources2.getIdentifier("animation", "xml", str));
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

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final Bitmap getWallpaperBitmap() {
        Bitmap bitmap = null;
        try {
            Log.d("KeyguardAnimatedWallpaper", "getWallpaperBitmap() hw accelerated: " + isHardwareAccelerated());
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            draw(new Canvas(bitmap));
            return bitmap;
        } catch (Throwable th) {
            th.printStackTrace();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return super.getWallpaperBitmap();
        }
    }

    public final void init(String str) {
        Resources resources;
        this.mPackageName = str;
        Log.d("KeyguardAnimatedWallpaper", "XmlName = animation;Default package name = " + this.mPackageName);
        setLayoutDirection(0);
        setBackgroundColor(EmergencyPhoneWidget.BG_COLOR);
        stopAnimation();
        clearAnimation();
        removeAllViews();
        try {
            try {
                resources = this.mContext.createPackageContext(this.mPackageName, 3).getResources();
            } catch (Exception e) {
                e.printStackTrace();
                resources = null;
            }
            if (resources == null) {
                resources = new APKContents(APKContents.getMainThemePackagePath(this.mPackageName)).getResources();
            }
            this.mComplexAnimationBuilder = getComplexAnimation(this.mContext, resources);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (this.mWallpaperResultCallback != null) {
            if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed()) {
                this.mWallpaperResultCallback.onDrawFinished();
            }
            this.mWallpaperResultCallback.onPreviewReady();
        }
        if (this.mShowing && this.mHasWindowFocus && !this.mIsBlurEnabled) {
            playAnimation();
        }
        this.mDrawingState = 1;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mShowing = WallpaperUtils.sDrawState;
        Log.d("KeyguardAnimatedWallpaper", "onAttachedToWindow: " + this);
        if (this.mShowing && this.mHasWindowFocus && !this.mIsBlurEnabled) {
            playAnimation();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onBackDropLayoutChange() {
        updateDisplayInfo();
        if (!this.mIsPreview) {
            this.mFixedOrientationController.applyPortraitRotation();
        }
        refreshViews();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z = this.mOrientation == configuration.orientation;
        super.onConfigurationChanged(configuration);
        if (z) {
            return;
        }
        if (!this.mIsPreview) {
            this.mFixedOrientationController.applyPortraitRotation();
        }
        refreshViews();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("KeyguardAnimatedWallpaper", "onDetachedFromWindow: " + this);
        stopAnimation();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onPause() {
        Log.d("KeyguardAnimatedWallpaper", "onPause() - screenTurnedOff");
        stopAnimation();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onResume() {
        int i;
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("onResume, mDrawingState:"), this.mDrawingState, "KeyguardAnimatedWallpaper");
        if (!this.mIsPreview) {
            this.mFixedOrientationController.applyPortraitRotation();
        }
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !this.mIsPreview && ((i = this.mDrawingState) == 3 || i == 2)) {
            Log.d("KeyguardAnimatedWallpaper", "onResume, reload");
            this.mDrawingState = 0;
            refreshViews();
        }
        playAnimation();
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (this.mHasWindowFocus != z) {
            this.mShowing = WallpaperUtils.sDrawState;
            this.mHasWindowFocus = z;
            if (!z) {
                stopAnimation();
                return;
            }
            View view = (View) getParent();
            StringBuilder sb = new StringBuilder("onWindowFocusChanged() mShowing = ");
            sb.append(this.mShowing);
            sb.append(", blur = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mIsBlurEnabled, "KeyguardAnimatedWallpaper");
            if (view != null && view.getVisibility() == 0 && this.mShowing) {
                playAnimation();
            }
        }
    }

    public final void playAnimation() {
        this.mMainHandler.post(new KeyguardAnimatedWallpaper$$ExternalSyntheticLambda0(this, 0));
    }

    public final void refreshViews() {
        StringBuilder sb = new StringBuilder("refreshViews: isBlurEnabled = ");
        sb.append(this.mIsBlurEnabled);
        sb.append(", focus = ");
        NotificationListener$$ExternalSyntheticOutline0.m123m(sb, this.mHasWindowFocus, "KeyguardAnimatedWallpaper");
        String animatedPkgName = WallpaperManager.getInstance(this.mContext).getAnimatedPkgName(this.mCurrentWhich);
        if (animatedPkgName != null) {
            init(animatedPkgName);
            if (this.mShowing && this.mHasWindowFocus) {
                playAnimation();
            }
            this.mDrawingState = 1;
        }
    }

    public final void stopAnimation() {
        this.mMainHandler.post(new KeyguardAnimatedWallpaper$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void update() {
        Log.d("KeyguardAnimatedWallpaper", "update New wallpaper!");
        String animatedPkgName = WallpaperManager.getInstance(this.mContext).getAnimatedPkgName(this.mCurrentWhich);
        if (animatedPkgName != null && !animatedPkgName.equals(this.mPackageName)) {
            cleanUp();
            init(animatedPkgName);
        } else {
            WallpaperResultCallback wallpaperResultCallback = this.mWallpaperResultCallback;
            if (wallpaperResultCallback != null) {
                wallpaperResultCallback.onPreviewReady();
            }
            this.mDrawingState = 1;
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void updateBlurState(boolean z) {
        this.mIsBlurEnabled = z;
        StringBuilder sb = new StringBuilder("updateBlurState: showing = ");
        sb.append(this.mShowing);
        sb.append(", focus = ");
        sb.append(this.mHasWindowFocus);
        sb.append(" , blur = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mIsBlurEnabled, "KeyguardAnimatedWallpaper");
        if (this.mShowing && this.mHasWindowFocus) {
            if (z) {
                stopAnimation();
            } else {
                playAnimation();
            }
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void updateDrawState(boolean z) {
        this.mShowing = z;
    }

    public KeyguardAnimatedWallpaper(Context context, String str, boolean z, int i, int i2, KeyguardUpdateMonitor keyguardUpdateMonitor, ExecutorService executorService, WallpaperResultCallback wallpaperResultCallback, Consumer<Boolean> consumer, int i3) {
        super(context, keyguardUpdateMonitor, wallpaperResultCallback, executorService, consumer, z);
        this.mIsPlayingAnimation = false;
        this.mCurrentWhich = 2;
        this.mHasWindowFocus = false;
        this.mShowing = false;
        this.mIsBlurEnabled = false;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mContext = context;
        this.mIsPreview = z;
        this.mViewWidth = i;
        this.mViewHeight = i2;
        this.mCurrentWhich = i3;
        this.mFixedOrientationController = new FixedOrientationController(this);
        this.mHasWindowFocus = getRootView().hasWindowFocus();
        this.mShowing = WallpaperUtils.sDrawState;
        init(str);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void handleTouchEvent(MotionEvent motionEvent) {
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onUnlock() {
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void reset() {
    }
}
