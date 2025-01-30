package com.android.systemui.wallpaper.view;

import android.app.SemWallpaperColors;
import android.app.SemWallpaperResourcesInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.view.DisplayInfo;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.Prefs;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.pluginlock.utils.BitmapUtils;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.FixedOrientationController;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperResultCallback;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.effect.ColorDecorFilterHelper;
import com.android.systemui.wallpaper.effect.HighlightFilterHelper;
import com.android.systemui.wallpaper.glwallpaper.ImageDarkModeFilter;
import com.android.systemui.wallpaper.glwallpaper.ImageSmartCropper;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.tilt.Drawer;
import com.android.systemui.wallpaper.tilt.SequentialAnimator;
import com.android.systemui.wallpaper.tilt.TiltColorController;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.samsung.android.knox.lockscreen.LSOConstants;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import javax.inject.Provider;
import libcore.io.IoUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardImageWallpaper extends SystemUIWallpaper implements Drawer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Paint mAlphaPaint;
    public int mBitmapHeight;
    public int mBitmapWidth;
    public Bitmap mCache;
    public final Provider mColorProvider;
    public final Context mContext;
    public int mCurrentUserId;
    public int mCurrentWhich;
    public final Matrix mDrawMatrix;
    public final Paint mDrawPaint;
    public final FixedOrientationController mFixedOrientationController;
    public final ImageSmartCropper mImageSmartCropper;
    public final AtomicBoolean mIsDrawRequested;
    public int mLastBottom;
    public int mLastRight;
    public final WallpaperLogger mLoggerWrapper;
    public boolean mNeedToRedraw;
    public Bitmap mOldBitmap;
    public int mOriginDx;
    public int mOriginDy;
    public final PluginWallpaperManager mPluginWallpaperManager;
    public final SettingsHelper mSettingsHelper;
    public final boolean mShouldEnableScreenRotation;
    public Rect mSmartCroppedResult;
    public final TiltColorController mTiltColorController;
    public int mUpdateWallpaperSequence;
    public boolean mUseCache;
    public int mViewHeight;
    public int mViewWidth;
    public final WallpaperManager mWallpaperManager;
    public AsyncTaskC37011 mWallpaperUpdator;

    public KeyguardImageWallpaper(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, WallpaperResultCallback wallpaperResultCallback, ExecutorService executorService, int i, boolean z, PluginWallpaperManager pluginWallpaperManager, SettingsHelper settingsHelper, WallpaperLogger wallpaperLogger, Consumer<Boolean> consumer, Provider provider) {
        super(context, keyguardUpdateMonitor, wallpaperResultCallback, executorService, consumer, false);
        this.mDrawMatrix = new Matrix();
        this.mCache = null;
        this.mOldBitmap = null;
        this.mIsDrawRequested = new AtomicBoolean(false);
        this.mDrawPaint = new Paint(2);
        this.mAlphaPaint = new Paint(2);
        this.mNeedToRedraw = false;
        this.mCurrentUserId = KeyguardUpdateMonitor.getCurrentUser();
        this.mUpdateWallpaperSequence = 0;
        this.mUseCache = false;
        AbstractC0731x5bb8a836.m72m("KeyguardImageWallpaper: which = ", i, " , useCache = ", z, "KeyguardImageWallpaper");
        this.mContext = context;
        this.mCurrentWhich = i;
        setWillNotDraw(false);
        this.mWallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
        this.mShouldEnableScreenRotation = DeviceState.shouldEnableKeyguardScreenRotation(context);
        this.mUseCache = z;
        this.mColorProvider = provider;
        this.mSettingsHelper = settingsHelper;
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mLoggerWrapper = wallpaperLogger;
        if (z) {
            PluginWallpaperManagerImpl pluginWallpaperManagerImpl = (PluginWallpaperManagerImpl) pluginWallpaperManager;
            if (pluginWallpaperManagerImpl.isDynamicWallpaperEnabled()) {
                if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                    if (pluginWallpaperManagerImpl.getWallpaperBitmap() != null) {
                        this.mUseCache = false;
                    }
                } else {
                    this.mUseCache = false;
                }
            }
        }
        this.mImageSmartCropper = new ImageSmartCropper(context, i);
        this.mSmartCroppedResult = null;
        if (WallpaperUtils.isEnableTilt(context)) {
            this.mTiltColorController = new TiltColorController(context, this);
        }
        this.mFixedOrientationController = new FixedOrientationController(this);
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !this.mUseCache && WallpaperUtils.isCachedWallpaperAvailable(i)) {
            Log.i("KeyguardImageWallpaper", "KeyguardImageWallpaper: recycle cache");
            WallpaperUtils.clearCachedWallpaper(i);
        }
        new IntelligentCropHelper();
        updateWallpaper(i);
    }

    public static void recycleBitmap(Bitmap bitmap) {
        Log.i("KeyguardImageWallpaper", "recycleBitmap: bmp = " + bitmap);
        if (bitmap != null) {
            synchronized (bitmap) {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    public final boolean canRotate() {
        FixedOrientationController fixedOrientationController = this.mFixedOrientationController;
        if (fixedOrientationController != null) {
            SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
            if (!(settingsHelper != null && fixedOrientationController.mShouldEnableScreenRotation && settingsHelper.isLockScreenRotationAllowed())) {
                return false;
            }
        }
        boolean isSubDisplay = SystemUIWallpaper.isSubDisplay();
        if ((LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE && !isSubDisplay) || DeviceType.isTablet()) {
            return true;
        }
        boolean isRotationRequired = isRotationRequired();
        Log.i("KeyguardImageWallpaper", "canRotate: which = " + WallpaperUtils.sCurrentWhich + " , isRotatingWallpaper = " + isRotationRequired);
        return isRotationRequired;
    }

    public final boolean checkPreCondition(int i) {
        if (!WallpaperUtils.isCachedWallpaperAvailable(i)) {
            Log.e("KeyguardImageWallpaper", "checkPreCondition: Cached wallpaper is null or is recycled");
            return false;
        }
        Log.i("KeyguardImageWallpaper", "checkPreCondition: getHeight()  = " + getHeight());
        if (WallpaperUtils.isStatusBarHeight(getContext(), this, getHeight())) {
            Log.e("KeyguardImageWallpaper", "checkPreCondition: getHeight() is same with statusBar height.");
            return false;
        }
        if (this.mViewWidth != 0 && this.mViewHeight != 0) {
            return true;
        }
        Log.e("KeyguardImageWallpaper", "checkPreCondition: mViewWidth == 0 || mViewHeight == 0");
        return false;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void cleanUp() {
        this.mTransitionAnimationListener = null;
        Log.d("KeyguardImageWallpaper", "cleanUp()");
        AsyncTaskC37011 asyncTaskC37011 = this.mWallpaperUpdator;
        if (asyncTaskC37011 != null && !asyncTaskC37011.isCancelled()) {
            asyncTaskC37011.cancel(true);
            this.mWallpaperUpdator = null;
        }
        TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("recycleCaches: WallpaperUtils.getCurrentWhich() = "), WallpaperUtils.sCurrentWhich, "KeyguardImageWallpaper");
        Bitmap wallpaperBitmap = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getWallpaperBitmap();
        boolean z = wallpaperBitmap != null && wallpaperBitmap == this.mCache;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("recycleCaches: isDlsBitmap=", z, "KeyguardImageWallpaper");
        if (!z) {
            recycleBitmap(this.mCache);
        }
        this.mCache = null;
        recycleBitmap(this.mOldBitmap);
        this.mOldBitmap = null;
        this.mExecutor.execute(new KeyguardImageWallpaper$$ExternalSyntheticLambda0(this));
        this.mDrawingState = 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ArrayList getIntelligentCropHints() {
        String str;
        Bundle wallpaperExtras;
        boolean z = LsRune.WALLPAPER_SUB_WATCHFACE;
        int i = z ? 6 : WallpaperUtils.sCurrentWhich;
        int screenId = PluginWallpaperManager.getScreenId(i);
        int i2 = z ? 6 : WallpaperUtils.sCurrentWhich;
        if (!(KeyguardUpdateMonitor.getCurrentUser() != 0)) {
            int screenId2 = PluginWallpaperManager.getScreenId(i2);
            if (((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled(screenId2)) {
                str = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getWallpaperIntelligentCrop(screenId2);
                Log.d("KeyguardImageWallpaper", "getIntelligentCropHintsFromDls: from DLS.");
            } else if (LsRune.KEYGUARD_FBE && !this.mUpdateMonitor.isUserUnlocked(this.mCurrentUserId) && isFbeWallpaper(screenId2)) {
                str = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getFbeWallpaperIntelligentCrop(screenId2);
                Log.d("KeyguardImageWallpaper", "getIntelligentCropHintsFromDls: from FBE.");
            } else if (this.mWallpaperManager.semGetWallpaperType(i2) == 3) {
                str = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).getFbeWallpaperIntelligentCrop(screenId2);
                Log.d("KeyguardImageWallpaper", "getIntelligentCropHintsFromDls: from FBE.");
            }
            if (TextUtils.isEmpty(str) && !isFbeWallpaper(screenId) && !((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled(screenId)) {
                wallpaperExtras = this.mWallpaperManager.getWallpaperExtras(i, this.mCurrentUserId);
                if (wallpaperExtras != null) {
                    Log.d("KeyguardImageWallpaper", "getIntelligentCropHints: extras is null.");
                    return null;
                }
                str = wallpaperExtras.getString("cropHints");
                Log.d("KeyguardImageWallpaper", "getIntelligentCropHints: from WMS.");
            }
            return IntelligentCropHelper.parseCropHints(str);
        }
        str = null;
        if (TextUtils.isEmpty(str)) {
            wallpaperExtras = this.mWallpaperManager.getWallpaperExtras(i, this.mCurrentUserId);
            if (wallpaperExtras != null) {
            }
        }
        return IntelligentCropHelper.parseCropHints(str);
    }

    public final Bitmap getOperatorWallpaper() {
        File oMCWallpaperFile = WallpaperManager.getOMCWallpaperFile(this.mContext, 2);
        File cSCWallpaperFile = WallpaperManager.getCSCWallpaperFile(this.mContext, 2, null, null);
        if (oMCWallpaperFile != null && oMCWallpaperFile.exists()) {
            return BitmapFactory.decodeFile(oMCWallpaperFile.getAbsolutePath());
        }
        if (cSCWallpaperFile == null || !cSCWallpaperFile.exists()) {
            return null;
        }
        return BitmapFactory.decodeFile(cSCWallpaperFile.getAbsolutePath());
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final Bitmap getWallpaperBitmap() {
        Bitmap bitmap = null;
        try {
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

    public final boolean init(int i) {
        float f;
        float f2;
        this.mViewWidth = (getWidth() - ((FrameLayout) this).mPaddingLeft) - ((FrameLayout) this).mPaddingRight;
        this.mViewHeight = (getHeight() - ((FrameLayout) this).mPaddingTop) - ((FrameLayout) this).mPaddingBottom;
        try {
            if (!checkPreCondition(i)) {
                Log.e("KeyguardImageWallpaper", "init: Fail to check precondition");
                this.mLastRight = -1;
                this.mLastBottom = -1;
                this.mDrawingState = 2;
                return false;
            }
            this.mBitmapWidth = WallpaperUtils.getCachedWallpaper(i).getWidth();
            int height = WallpaperUtils.getCachedWallpaper(i).getHeight();
            this.mBitmapHeight = height;
            int i2 = this.mBitmapWidth;
            int i3 = this.mViewHeight;
            int i4 = i2 * i3;
            int i5 = this.mViewWidth;
            if (i4 > i5 * height) {
                f = i3;
                f2 = height;
            } else {
                f = i5;
                f2 = i2;
            }
            float f3 = (f / f2) * 1.0f;
            float f4 = (i5 - (i2 * f3)) * 0.5f;
            float f5 = (i3 - (height * f3)) * 0.5f;
            this.mOriginDx = Math.round(f4);
            this.mOriginDy = Math.round(f5);
            this.mDrawMatrix.setScale(f3, f3);
            this.mDrawMatrix.postTranslate(this.mOriginDx, this.mOriginDy);
            Log.d("KeyguardImageWallpaper", "init: mBitmapWidth = " + this.mBitmapWidth);
            Log.d("KeyguardImageWallpaper", "init: mBitmapHeight = " + this.mBitmapHeight);
            Log.d("KeyguardImageWallpaper", "init: mViewWidth = " + this.mViewWidth);
            Log.d("KeyguardImageWallpaper", "init: mViewHeight = " + this.mViewHeight);
            Log.d("KeyguardImageWallpaper", "init: scale = " + f3);
            Log.d("KeyguardImageWallpaper", "init: dx = " + f4);
            Log.d("KeyguardImageWallpaper", "init: dy = " + f5);
            WallpaperResultCallback wallpaperResultCallback = this.mWallpaperResultCallback;
            if (wallpaperResultCallback != null) {
                wallpaperResultCallback.onPreviewReady();
            }
            invalidate();
            return true;
        } catch (Exception e) {
            Log.e("KeyguardImageWallpaper", "init: which = " + i);
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isFbeWallpaper(int i) {
        return ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isFbeRequired(i) && ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isFbeWallpaperAvailable(i);
    }

    public final boolean isRotationRequired() {
        if (Settings.System.getInt(this.mContext.getContentResolver(), SystemUIWallpaper.isSubDisplay() ? "sub_display_lockscreen_wallpaper_transparency" : "lockscreen_wallpaper_transparent", 1) == 0) {
            return true;
        }
        return LsRune.WALLPAPER_ROTATABLE_WALLPAPER && !SystemUIWallpaper.isSubDisplay() && ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isDynamicWallpaperEnabled();
    }

    public final boolean isSmartCropRequired() {
        if (Settings.System.getInt(this.mContext.getContentResolver(), SystemUIWallpaper.isSubDisplay() ? "sub_display_lockscreen_wallpaper_transparency" : "lockscreen_wallpaper_transparent", 1) == 0) {
            int i = SystemUIWallpaper.isSubDisplay() ? 16 : 4;
            Context context = this.mContext;
            boolean z = WallpaperUtils.mIsEmergencyMode;
            if (!(!TextUtils.isEmpty(new SemWallpaperProperties(context, i | 2, context.getUserId()).getImageCategory()))) {
                return true;
            }
        }
        return ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isCustomPackApplied();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("KeyguardImageWallpaper", "onAttachedToWindow: " + this);
        this.mExecutor.execute(new KeyguardImageWallpaper$$ExternalSyntheticLambda1(this, true));
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onBackDropLayoutChange() {
        if (!canRotate()) {
            Log.d("KeyguardImageWallpaper", "onBackDropLayoutChange: Rotation of lockscreen wallpaper is not allowed.");
            return;
        }
        int i = this.mCurDisplayInfo.rotation;
        updateDisplayInfo();
        awaitCall();
        int i2 = this.mCurDisplayInfo.rotation;
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onBackDropLayoutChange: prevRotation = ", i, ", curRotation = ", i2, "KeyguardImageWallpaper");
        if (i != i2) {
            this.mNeedToRedraw = true;
        }
        updateRotationState();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mOrientation != configuration.orientation) {
            this.mNeedToRedraw = true;
        }
        super.onConfigurationChanged(configuration);
        updateRotationState();
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("KeyguardImageWallpaper", "onDetachedFromWindow: " + this);
        this.mExecutor.execute(new KeyguardImageWallpaper$$ExternalSyntheticLambda0(this));
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        int i2;
        boolean z;
        int i3;
        int i4;
        boolean z2;
        int i5;
        boolean z3;
        Bitmap bitmap;
        Bitmap bitmap2;
        WallpaperResultCallback wallpaperResultCallback;
        Paint paint;
        SequentialAnimator.AnimatedValue animatedValue;
        float f;
        SequentialAnimator.AnimatedValue animatedValue2;
        this.mIsDrawRequested.set(false);
        int i6 = LsRune.WALLPAPER_SUB_WATCHFACE ? 6 : WallpaperUtils.sCurrentWhich;
        if (!WallpaperUtils.isCachedWallpaperAvailable(i6)) {
            Log.e("KeyguardImageWallpaper", "onDraw: Cached wallpaper is null or is recycled");
            this.mDrawingState = 2;
            return;
        }
        Bitmap cachedWallpaper = WallpaperUtils.getCachedWallpaper(i6);
        if (this.mBitmapWidth == 0 || this.mBitmapHeight == 0) {
            Log.e("KeyguardImageWallpaper", "mBitmapWidth == 0 || mBitmapHeight == 0");
            return;
        }
        canvas.save();
        int i7 = this.mCurDisplayInfo.rotation;
        boolean z4 = i7 == 1 || i7 == 3 || this.mOrientation == 2;
        TiltColorController tiltColorController = this.mTiltColorController;
        if (tiltColorController == null || !tiltColorController.mIsEnable) {
            i = 16;
            i2 = 4;
            this.mDrawPaint.setAlpha(255);
            this.mDrawPaint.setColorFilter(null);
            this.mAlphaPaint.setColorFilter(null);
        } else {
            Paint paint2 = this.mDrawPaint;
            tiltColorController.mIsDrawRequested.set(false);
            if (tiltColorController.mIsEnable) {
                if (paint2 != null) {
                    boolean z5 = tiltColorController.mNeedUpdateColorFilter;
                    SequentialAnimator.AnimatedValue animatedValue3 = tiltColorController.mAlpha;
                    if (z5) {
                        tiltColorController.mColorMatrix.reset();
                        tiltColorController.mColorMatrix.setSaturation(tiltColorController.mSaturation.currentValue);
                        float min = (Math.min(Math.max(tiltColorController.mHue.currentValue, -180.0f), 180.0f) / 180.0f) * 3.1415927f;
                        if (min != 0.0f) {
                            animatedValue2 = animatedValue3;
                            double d = min;
                            paint = paint2;
                            float cos = (float) Math.cos(d);
                            float sin = (float) Math.sin(d);
                            float f2 = (cos * (-0.715f)) + 0.715f;
                            float f3 = ((-0.072f) * cos) + 0.072f;
                            i2 = 4;
                            float f4 = ((-0.213f) * cos) + 0.213f;
                            i = 16;
                            tiltColorController.mColorMatrix.postConcat(new ColorMatrix(new float[]{(sin * (-0.213f)) + (0.787f * cos) + 0.213f, ((-0.715f) * sin) + f2, (sin * 0.928f) + f3, 0.0f, 0.0f, (0.143f * sin) + f4, (0.14f * sin) + (0.28500003f * cos) + 0.715f, ((-0.283f) * sin) + f3, 0.0f, 0.0f, ((-0.787f) * sin) + f4, (0.715f * sin) + f2, (sin * 0.072f) + (cos * 0.928f) + 0.072f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}));
                        } else {
                            paint = paint2;
                            animatedValue2 = animatedValue3;
                            i = 16;
                            i2 = 4;
                        }
                        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(tiltColorController.mColorMatrix);
                        tiltColorController.mColorFilter = colorMatrixColorFilter;
                        tiltColorController.mPaint.setColorFilter(colorMatrixColorFilter);
                        animatedValue = animatedValue2;
                        f = 255.0f;
                        tiltColorController.mPaint.setAlpha((int) (animatedValue.currentValue * 255.0f));
                    } else {
                        paint = paint2;
                        animatedValue = animatedValue3;
                        f = 255.0f;
                        i = 16;
                        i2 = 4;
                    }
                    Paint paint3 = paint;
                    paint3.setColorFilter(tiltColorController.mColorFilter);
                    paint3.setAlpha((int) (animatedValue.currentValue * f));
                } else {
                    i = 16;
                    i2 = 4;
                }
                float f5 = tiltColorController.mScale.currentValue;
                if (0.0f != f5) {
                    int width = canvas.getWidth();
                    int height = canvas.getHeight();
                    canvas.scale(f5, f5, width / 2, height / 2);
                    boolean z6 = width < height;
                    float f6 = (-(Math.min(Math.min(width, height) * 0.05f, Math.abs(width - height) / 2.0f) * tiltColorController.mTiltScale.currentValue)) / f5;
                    if (z6) {
                        canvas.translate(f6, 0.0f);
                    } else {
                        canvas.translate(0.0f, f6);
                    }
                }
                if (tiltColorController.mIsGyroAllowed) {
                    TiltColorController.HandlerC36922 handlerC36922 = tiltColorController.mTiltHandler;
                    if (handlerC36922.hasMessages(0)) {
                        handlerC36922.removeMessages(0);
                        handlerC36922.handleMessage(null);
                    }
                }
            } else {
                i = 16;
                i2 = 4;
            }
            this.mAlphaPaint.setColorFilter(this.mDrawPaint.getColorFilter());
        }
        if (!SystemUIWallpaper.isSubDisplay()) {
            i = i2;
        }
        if (cachedWallpaper != null) {
            i3 = cachedWallpaper.getWidth();
            i4 = cachedWallpaper.getHeight();
            String filterData = ColorDecorFilterHelper.getFilterData(i | 2, this.mContext, this.mCurrentUserId);
            if (TextUtils.isEmpty(filterData)) {
                Context context = this.mContext;
                PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Boolean canApplyFilterOnHome = HighlightFilterHelper.canApplyFilterOnHome(i);
                if (canApplyFilterOnHome == null || !canApplyFilterOnHome.booleanValue()) {
                    Log.d("HighlightFilterHelper", "canApplyFilterOnLock: Not applied on Home. result = " + canApplyFilterOnHome);
                } else if (((PluginWallpaperManagerImpl) pluginWallpaperManager).isDynamicWallpaperEnabled()) {
                    Log.d("HighlightFilterHelper", "canApplyFilterOnLock: DLS enabled");
                    canApplyFilterOnHome = Boolean.FALSE;
                } else if (WallpaperManager.getInstance(context).isSystemAndLockPaired(i)) {
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("canApplyFilterOnLock: true, mode = ", i, ", elapsed = ");
                    m1m.append(SystemClock.elapsedRealtime() - elapsedRealtime);
                    Log.d("HighlightFilterHelper", m1m.toString());
                    canApplyFilterOnHome = Boolean.TRUE;
                } else {
                    Log.d("HighlightFilterHelper", "canApplyFilterOnLock: Home & Lock is not paired.");
                    canApplyFilterOnHome = Boolean.FALSE;
                }
                if (canApplyFilterOnHome == null) {
                    canApplyFilterOnHome = Boolean.FALSE;
                }
                if (canApplyFilterOnHome.booleanValue()) {
                    cachedWallpaper = HighlightFilterHelper.createFilteredBitmap(cachedWallpaper, HighlightFilterHelper.getFilterAmount(this.mSettingsHelper));
                } else {
                    z = false;
                }
            } else {
                cachedWallpaper = ColorDecorFilterHelper.createFilteredBitmap(cachedWallpaper, filterData);
            }
            z = true;
        } else {
            z = false;
            i3 = 0;
            i4 = 0;
        }
        float[] wallpaperFilterColor = ImageDarkModeFilter.getWallpaperFilterColor(this.mContext, (SemWallpaperColors) this.mColorProvider.get());
        if (wallpaperFilterColor != null) {
            z2 = false;
            int argb = Color.argb(wallpaperFilterColor[3], wallpaperFilterColor[0], wallpaperFilterColor[1], wallpaperFilterColor[2]);
            this.mDrawPaint.setColorFilter(new PorterDuffColorFilter(argb, PorterDuff.Mode.SRC_OVER));
            Log.i("KeyguardImageWallpaper", "onDraw: draw filter color on ImageWallpaper " + argb);
        } else {
            z2 = false;
            this.mDrawPaint.setColorFilter(null);
        }
        ArrayList intelligentCropHints = getIntelligentCropHints();
        awaitCall();
        DisplayInfo displayInfo = this.mCurDisplayInfo;
        Rect nearestCropHint = IntelligentCropHelper.getNearestCropHint(displayInfo == null ? null : new Point(displayInfo.logicalWidth, displayInfo.logicalHeight), intelligentCropHints);
        boolean z7 = nearestCropHint != null ? true : z2;
        Matrix matrix = new Matrix();
        if (nearestCropHint != null) {
            float width2 = nearestCropHint.width();
            float height2 = nearestCropHint.height();
            float max = Math.max(this.mViewWidth / width2, this.mViewHeight / height2);
            float f7 = (-nearestCropHint.left) * max;
            z3 = z;
            if (width2 * max > this.mViewWidth) {
                i5 = i6;
                bitmap = cachedWallpaper;
                f7 = (float) (f7 - ((r12 - r3) / 2.0d));
            } else {
                i5 = i6;
                bitmap = cachedWallpaper;
            }
            float f8 = (-nearestCropHint.top) * max;
            if (height2 * max > this.mViewHeight) {
                f8 = (float) (f8 - ((r13 - r2) / 2.0d));
            }
            matrix.postScale(max, max);
            matrix.postTranslate(Math.round(f7), Math.round(f8));
        } else {
            i5 = i6;
            z3 = z;
            bitmap = cachedWallpaper;
            matrix = this.mDrawMatrix;
        }
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onDraw : bmpW=", i3, ", bmpH=", i4, ", src=");
        m45m.append(nearestCropHint);
        m45m.append(", mViewWidth=");
        m45m.append(this.mViewWidth);
        m45m.append(", mViewHeight=");
        RecyclerView$$ExternalSyntheticOutline0.m46m(m45m, this.mViewHeight, "KeyguardImageWallpaper");
        TiltColorController tiltColorController2 = this.mTiltColorController;
        if (tiltColorController2 == null || !tiltColorController2.mIsEnable) {
            bitmap2 = bitmap;
            if (z4 && canRotate() && !z7 && isSmartCropRequired()) {
                updateSmartCropRectIfNeeded(i5);
                StringBuilder sb = new StringBuilder("onDraw: landscape, mSmartCroppedResult=");
                sb.append(this.mSmartCroppedResult);
                sb.append(" viewW=");
                sb.append(this.mViewWidth);
                sb.append(" viewH=");
                TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, this.mViewHeight, "KeyguardImageWallpaper");
                if (this.mSmartCroppedResult != null) {
                    canvas.drawBitmap(bitmap2, this.mSmartCroppedResult, new RectF(0.0f, 0.0f, this.mViewWidth, this.mViewHeight), this.mDrawPaint);
                } else {
                    canvas.drawBitmap(bitmap2, matrix, this.mDrawPaint);
                }
            } else {
                Log.d("KeyguardImageWallpaper", "onDraw: cur bitmap");
                canvas.drawBitmap(bitmap2, matrix, this.mDrawPaint);
            }
        } else {
            bitmap2 = bitmap;
            canvas.drawBitmap(bitmap2, matrix, this.mAlphaPaint);
        }
        this.mDrawingState = 1;
        Bitmap bitmap3 = this.mOldBitmap;
        if (bitmap3 != null && bitmap3 != bitmap2) {
            recycleBitmap(bitmap3);
            this.mOldBitmap = null;
        }
        if (z3) {
            recycleBitmap(bitmap2);
        }
        canvas.restore();
        this.mNeedToRedraw = false;
        if (LsRune.WALLPAPER_CAPTURED_BLUR && DeviceState.isCapturedBlurAllowed() && (wallpaperResultCallback = this.mWallpaperResultCallback) != null) {
            wallpaperResultCallback.onDrawFinished();
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        super.onLayout(z, i, i2, i3, i4);
        StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("onLayout:  changed = [", z, "], left = [", i, "], top = [");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m66m, i2, "], right = [", i3, "], bottom = [");
        m66m.append(i4);
        m66m.append("]");
        Log.d("KeyguardImageWallpaper", m66m.toString());
        Log.d("KeyguardImageWallpaper", "onLayout: mLastRight = " + this.mLastRight + ", mLastBottom = " + this.mLastBottom);
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("onLayout: mNeedToRedraw = "), this.mNeedToRedraw, "KeyguardImageWallpaper");
        if (!z || i3 == 0 || i4 == 0) {
            return;
        }
        if (WallpaperUtils.isStatusBarHeight(getContext(), this, i4)) {
            Log.d("KeyguardImageWallpaper", "onLayout: It is status bar size. Ignored.");
            return;
        }
        boolean z2 = false;
        boolean z3 = (this.mLastRight != i3 || this.mLastBottom != i4) || this.mNeedToRedraw;
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            z2 = (!isRotationRequired() || (i5 = this.mLastRight) == 0 || i5 == i3 || i5 == i4) ? false : true;
        }
        if (z3) {
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("onLayout: redraw needed. ", i3, ", ", i4, " , needSmartCrop = "), z2, "KeyguardImageWallpaper");
            int i6 = LsRune.WALLPAPER_SUB_WATCHFACE ? 6 : WallpaperUtils.sCurrentWhich;
            if (z2) {
                this.mSmartCroppedResult = null;
                updateSmartCropRectIfNeeded(this.mCurrentWhich);
            }
            init(i6);
            this.mLastRight = i3;
            this.mLastBottom = i4;
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onPause() {
        this.mResumed = false;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void onResume() {
        int i;
        this.mResumed = true;
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("onResume, mDrawingState:"), this.mDrawingState, "KeyguardImageWallpaper");
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && (((i = this.mDrawingState) == 2 || i == 3) && !WallpaperUtils.isCachedWallpaperAvailable(this.mCurrentWhich))) {
            Log.d("KeyguardImageWallpaper", "onResume, reload");
            this.mDrawingState = 0;
            updateWallpaper(this.mCurrentWhich);
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        if (!canRotate()) {
            this.mFixedOrientationController.applyPortraitRotation();
        } else {
            if (layoutParams.width == -1 && layoutParams.height == -1) {
                return;
            }
            this.mFixedOrientationController.clearPortraitRotation();
        }
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("onWindowFocusChanged: ", z, "KeyguardImageWallpaper");
        if (LsRune.WALLPAPER_SUB_WATCHFACE && z) {
            init(6);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper
    public final void preInit() {
        Consumer consumer = this.mWcgConsumer;
        if (consumer != null) {
            consumer.accept(Boolean.TRUE);
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void setTransitionAnimationListener(KeyguardWallpaperController.C36634 c36634) {
        this.mTransitionAnimationListener = c36634;
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void update() {
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        if (this.mCurrentUserId != currentUser) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("update userId=", currentUser, "KeyguardImageWallpaper");
            this.mCurrentUserId = currentUser;
        }
        this.mUseCache = false;
        updateWallpaper(LsRune.WALLPAPER_SUB_WATCHFACE ? 6 : WallpaperUtils.sCurrentWhich);
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void updateDrawState(boolean z) {
        if (this.mTiltColorController != null) {
            Log.d("KeyguardImageWallpaper", "updateDrawState: [" + z + "]");
            TiltColorController tiltColorController = this.mTiltColorController;
            tiltColorController.getClass();
            StringBuilder sb = new StringBuilder("new: ");
            sb.append(z);
            sb.append(" prev:");
            NotificationListener$$ExternalSyntheticOutline0.m123m(sb, tiltColorController.mPrevState, "TiltColorController");
            if (tiltColorController.mIsEnable) {
                if (z) {
                    tiltColorController.startEnterAnimation(true);
                } else if (!((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
                    tiltColorController.startEnterAnimation(false);
                }
            }
            tiltColorController.mPrevState = z;
        }
    }

    public final void updateRotationState() {
        if (!canRotate()) {
            this.mFixedOrientationController.applyPortraitRotation();
            return;
        }
        FixedOrientationController fixedOrientationController = this.mFixedOrientationController;
        if (fixedOrientationController.mIsFixedOrientationApplied) {
            fixedOrientationController.clearPortraitRotation();
        }
    }

    public final void updateSmartCropRectIfNeeded(int i) {
        Rect cachedSmartCroppedRect = ((PluginWallpaperManagerImpl) this.mPluginWallpaperManager).isCustomPackApplied() ? this.mSmartCroppedResult : WallpaperUtils.getCachedSmartCroppedRect(i);
        this.mSmartCroppedResult = cachedSmartCroppedRect;
        if (cachedSmartCroppedRect != null && cachedSmartCroppedRect.right - cachedSmartCroppedRect.left > this.mBitmapWidth) {
            Log.d("KeyguardImageWallpaper", "updateSmartCropRectIfNeeded: Invalid smart crop rect.");
            this.mSmartCroppedResult = null;
        }
        boolean z = this.mSmartCroppedResult == null;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("updateSmartCropRectIfNeeded() needToUpdateCropRect: ", z, "KeyguardImageWallpaper");
        if (z) {
            this.mImageSmartCropper.updateSmartCropRect(WallpaperUtils.getCachedWallpaper(i), i);
            this.mSmartCroppedResult = this.mImageSmartCropper.mCropResult;
        }
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.wallpaper.view.KeyguardImageWallpaper$1] */
    public final void updateWallpaper(int i) {
        StringBuilder sb = new StringBuilder("updateWallpaper() START useCache=");
        sb.append(this.mUseCache);
        sb.append(" , user id = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(sb, this.mCurrentUserId, " , which = ", i, "KeyguardImageWallpaper");
        this.mCurrentWhich = i;
        this.mDrawingState = 0;
        final int i2 = this.mCurrentUserId;
        AsyncTaskC37011 asyncTaskC37011 = this.mWallpaperUpdator;
        if (asyncTaskC37011 != null && !asyncTaskC37011.isCancelled()) {
            Log.d("KeyguardImageWallpaper", "updateWallpaper: cancel update wallpaper");
            cancel(true);
            this.mWallpaperUpdator = null;
        }
        this.mExecutor.execute(new KeyguardImageWallpaper$$ExternalSyntheticLambda1(this, false));
        this.mWallpaperUpdator = new AsyncTask() { // from class: com.android.systemui.wallpaper.view.KeyguardImageWallpaper.1
            public final int seq;

            {
                int i3 = KeyguardImageWallpaper.this.mUpdateWallpaperSequence + 1;
                KeyguardImageWallpaper.this.mUpdateWallpaperSequence = i3;
                this.seq = i3;
            }

            /* JADX WARN: Can't wrap try/catch for region: R(25:0|1|(1:3)(1:208)|4|(1:207)(1:8)|9|(2:11|(1:205)(4:15|16|(3:190|191|(5:195|(1:197)|198|57|(2:59|60)(2:62|(2:64|65)(2:66|(2:68|69)(2:70|(9:78|(4:81|(1:83)(1:103)|84|(2:86|87)(2:88|(4:90|(3:96|97|(1:99))|92|(2:94|95))(1:102)))|104|(1:135)(1:107)|108|(1:110)|(2:112|(1:114))(8:117|(1:119)|120|(1:122)(1:134)|123|(1:125)|126|(1:133)(2:130|(1:132)))|115|116)(2:76|77))))))|18))(1:206)|19|(1:21)(1:189)|22|(1:24)(1:188)|(2:26|(5:28|(1:30)(1:176)|(1:32)(3:169|(1:171)(1:175)|(1:173)(1:174))|33|(1:35)(12:36|37|38|(3:147|148|(1:150)(2:151|(1:153)))(1:40)|41|(1:43)(2:138|(2:140|(6:145|(5:46|(1:48)|49|(1:54)|136)(1:137)|55|56|57|(0)(0)))(1:146))|44|(0)(0)|55|56|57|(0)(0)))(2:177|(2:184|(1:186))(1:183)))|187|37|38|(0)(0)|41|(0)(0)|44|(0)(0)|55|56|57|(0)(0)|(1:(0))) */
            /* JADX WARN: Code restructure failed: missing block: B:168:0x0266, code lost:
            
                r14 = th;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:137:0x0247 A[Catch: all -> 0x0266, OutOfMemoryError -> 0x0269, TRY_LEAVE, TryCatch #7 {OutOfMemoryError -> 0x0269, all -> 0x0266, blocks: (B:150:0x01ac, B:41:0x01d7, B:43:0x01e1, B:46:0x020a, B:48:0x0213, B:49:0x0216, B:51:0x0231, B:54:0x0238, B:136:0x023e, B:137:0x0247, B:138:0x01e7, B:140:0x01eb, B:142:0x01f5, B:145:0x01fd, B:146:0x0202, B:151:0x01ba, B:153:0x01c0), top: B:38:0x0187 }] */
            /* JADX WARN: Removed duplicated region for block: B:138:0x01e7 A[Catch: all -> 0x0266, OutOfMemoryError -> 0x0269, TryCatch #7 {OutOfMemoryError -> 0x0269, all -> 0x0266, blocks: (B:150:0x01ac, B:41:0x01d7, B:43:0x01e1, B:46:0x020a, B:48:0x0213, B:49:0x0216, B:51:0x0231, B:54:0x0238, B:136:0x023e, B:137:0x0247, B:138:0x01e7, B:140:0x01eb, B:142:0x01f5, B:145:0x01fd, B:146:0x0202, B:151:0x01ba, B:153:0x01c0), top: B:38:0x0187 }] */
            /* JADX WARN: Removed duplicated region for block: B:147:0x0189 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:188:0x00b0  */
            /* JADX WARN: Removed duplicated region for block: B:189:0x00a7  */
            /* JADX WARN: Removed duplicated region for block: B:21:0x00a5  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x00ae  */
            /* JADX WARN: Removed duplicated region for block: B:26:0x00b3  */
            /* JADX WARN: Removed duplicated region for block: B:40:0x01d6  */
            /* JADX WARN: Removed duplicated region for block: B:43:0x01e1 A[Catch: all -> 0x0266, OutOfMemoryError -> 0x0269, TryCatch #7 {OutOfMemoryError -> 0x0269, all -> 0x0266, blocks: (B:150:0x01ac, B:41:0x01d7, B:43:0x01e1, B:46:0x020a, B:48:0x0213, B:49:0x0216, B:51:0x0231, B:54:0x0238, B:136:0x023e, B:137:0x0247, B:138:0x01e7, B:140:0x01eb, B:142:0x01f5, B:145:0x01fd, B:146:0x0202, B:151:0x01ba, B:153:0x01c0), top: B:38:0x0187 }] */
            /* JADX WARN: Removed duplicated region for block: B:46:0x020a A[Catch: all -> 0x0266, OutOfMemoryError -> 0x0269, TryCatch #7 {OutOfMemoryError -> 0x0269, all -> 0x0266, blocks: (B:150:0x01ac, B:41:0x01d7, B:43:0x01e1, B:46:0x020a, B:48:0x0213, B:49:0x0216, B:51:0x0231, B:54:0x0238, B:136:0x023e, B:137:0x0247, B:138:0x01e7, B:140:0x01eb, B:142:0x01f5, B:145:0x01fd, B:146:0x0202, B:151:0x01ba, B:153:0x01c0), top: B:38:0x0187 }] */
            /* JADX WARN: Removed duplicated region for block: B:59:0x0287  */
            /* JADX WARN: Removed duplicated region for block: B:62:0x028e  */
            /* JADX WARN: Type inference failed for: r1v0, types: [int] */
            /* JADX WARN: Type inference failed for: r1v26, types: [java.lang.AutoCloseable] */
            /* JADX WARN: Type inference failed for: r1v27 */
            /* JADX WARN: Type inference failed for: r1v28 */
            /* JADX WARN: Type inference failed for: r1v30 */
            /* JADX WARN: Type inference failed for: r4v23, types: [java.lang.StringBuilder] */
            @Override // android.os.AsyncTask
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object doInBackground(Object[] objArr) {
                Bitmap bitmap;
                String str;
                LoaderResult loaderResult;
                boolean z;
                ParcelFileDescriptor parcelFileDescriptor;
                ParcelFileDescriptor parcelFileDescriptor2;
                Bitmap bitmap2;
                LoaderResult loaderResult2;
                Exception e;
                KeyguardImageWallpaper keyguardImageWallpaper = KeyguardImageWallpaper.this;
                ?? r1 = i2;
                keyguardImageWallpaper.getClass();
                StringBuilder sb2 = new StringBuilder();
                int i3 = LsRune.WALLPAPER_SUB_WATCHFACE ? 6 : WallpaperUtils.sCurrentWhich;
                sb2.append("loadBitmap which = " + i3);
                EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
                Object obj = null;
                if (edmMonitor != null && edmMonitor.mIsLockscreenWallpaperConfigured) {
                    str = LSOConstants.ADMIN_LOCKSCREEN_WALLPAPER_PORTRAIT;
                    File file = new File(LSOConstants.ADMIN_LOCKSCREEN_WALLPAPER_PORTRAIT);
                    if (file.exists() && file.canRead()) {
                        try {
                            bitmap = new BitmapDrawable(keyguardImageWallpaper.mContext.getResources(), LSOConstants.ADMIN_LOCKSCREEN_WALLPAPER_PORTRAIT).getBitmap();
                        } catch (Exception e2) {
                            e = e2;
                            bitmap = null;
                        }
                        if (bitmap != null) {
                            try {
                            } catch (Exception e3) {
                                e = e3;
                                Log.w("KeyguardImageWallpaper", "Can't load MDM wallpaper!", e);
                                Log.w("KeyguardImageWallpaper", "file problem!");
                                if (keyguardImageWallpaper.getIntelligentCropHints() == null) {
                                }
                                if (!(KeyguardUpdateMonitor.getCurrentUser() == 0)) {
                                }
                                z = false;
                                String str2 = str;
                                if (bitmap == null) {
                                }
                                if (((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).isDynamicWallpaperEnabled()) {
                                }
                                bitmap2 = bitmap;
                                if (bitmap2 == null) {
                                }
                                loaderResult = loaderResult2;
                                parcelFileDescriptor = parcelFileDescriptor2;
                                IoUtils.closeQuietly(parcelFileDescriptor);
                                if (loaderResult.success) {
                                }
                            }
                            if (bitmap.getWidth() != 0 && bitmap.getHeight() != 0) {
                                Log.w("KeyguardImageWallpaper", "load MDM wallpaper!");
                                WallpaperResultCallback wallpaperResultCallback = keyguardImageWallpaper.mWallpaperResultCallback;
                                if (wallpaperResultCallback != null) {
                                    wallpaperResultCallback.onDelegateBitmapReady(bitmap);
                                }
                                loaderResult = new LoaderResult(true, bitmap, false);
                                if (loaderResult.success) {
                                    Log.e("KeyguardImageWallpaper", "doInBackground, result is fail");
                                    return null;
                                }
                                if (isCancelled()) {
                                    Log.e("KeyguardImageWallpaper", "doInBackground, task is cancelled");
                                    return null;
                                }
                                int i4 = this.seq;
                                KeyguardImageWallpaper keyguardImageWallpaper2 = KeyguardImageWallpaper.this;
                                if (i4 != keyguardImageWallpaper2.mUpdateWallpaperSequence) {
                                    Log.e("KeyguardImageWallpaper", "doInBackground, request : " + this.seq + ", current : " + KeyguardImageWallpaper.this.mUpdateWallpaperSequence);
                                    return null;
                                }
                                boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
                                int i5 = loaderResult.which;
                                if (z2 && !LsRune.WALLPAPER_SUB_WATCHFACE && i5 != WallpaperUtils.sCurrentWhich) {
                                    Log.d("KeyguardImageWallpaper", "Loaded bitmap is not for current display. Just return here.");
                                    return null;
                                }
                                boolean z3 = loaderResult.fromPluginLock;
                                if (z2 && z3) {
                                    boolean z4 = (i5 & 16) != 0 ? Prefs.getBoolean(keyguardImageWallpaper2.mContext, "WPaperChangedByDlsSub", false) : Prefs.getBoolean(keyguardImageWallpaper2.mContext, "WPaperChangedByDls", false);
                                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("wallpaperUpdateFromDls: ", z4, "KeyguardImageWallpaper");
                                    if (!z4) {
                                        Log.e("KeyguardImageWallpaper", "Image loaded from PluginLock but DynamicWallpaper is not enabled at this moment. Just return here.");
                                        return null;
                                    }
                                    if (((PluginWallpaperManagerImpl) KeyguardImageWallpaper.this.mPluginWallpaperManager).isDynamicWallpaperEnabled()) {
                                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isDynamicWallpaperEnabled: ", z4, "KeyguardImageWallpaper");
                                        String str3 = loaderResult.wallpaerPath;
                                        if (str3 == null) {
                                            try {
                                                if (((PluginWallpaperManagerImpl) KeyguardImageWallpaper.this.mPluginWallpaperManager).getWallpaperPath() == null) {
                                                    Log.d("KeyguardImageWallpaper", "DLS does not have wallpaepr path. Just keep going.");
                                                }
                                            } catch (Exception e4) {
                                                AbstractC0790xf8f53ce8.m80m(e4, new StringBuilder("e = "), "KeyguardImageWallpaper");
                                            }
                                        }
                                        if (!str3.equals(((PluginWallpaperManagerImpl) KeyguardImageWallpaper.this.mPluginWallpaperManager).getWallpaperPath())) {
                                            Log.e("KeyguardImageWallpaper", "Bitmap and the path are not matched. loaded path = " + str3 + ", current path = " + ((PluginWallpaperManagerImpl) KeyguardImageWallpaper.this.mPluginWallpaperManager).getWallpaperPath());
                                            return null;
                                        }
                                    } else {
                                        Log.d("KeyguardImageWallpaper", "We are fine. Just keep going.");
                                    }
                                }
                                KeyguardImageWallpaper keyguardImageWallpaper3 = KeyguardImageWallpaper.this;
                                Bitmap bitmap3 = keyguardImageWallpaper3.mCache;
                                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardImageWallpaper3.mUpdateMonitor;
                                boolean z5 = keyguardUpdateMonitor.mDeviceInteractive;
                                boolean isKeyguardUnlocking = keyguardUpdateMonitor.isKeyguardUnlocking();
                                Log.i("KeyguardImageWallpaper", "applyOldBitmap: isDeviceInteractive = " + z5 + " , isUnlocked = " + isKeyguardUnlocking);
                                if (!keyguardImageWallpaper3.mUpdateMonitor.mDeviceInteractive || isKeyguardUnlocking) {
                                    KeyguardImageWallpaper.recycleBitmap(keyguardImageWallpaper3.mOldBitmap);
                                    keyguardImageWallpaper3.mOldBitmap = null;
                                } else {
                                    keyguardImageWallpaper3.mOldBitmap = bitmap3;
                                }
                                KeyguardImageWallpaper.this.mCache = loaderResult.bitmap;
                                Log.i("KeyguardImageWallpaper", "cache = " + KeyguardImageWallpaper.this.mCache + " , which = " + i5);
                                WallpaperUtils.setCachedWallpaper(KeyguardImageWallpaper.this.mCache, i5);
                                WallpaperResultCallback wallpaperResultCallback2 = KeyguardImageWallpaper.this.mWallpaperResultCallback;
                                if (wallpaperResultCallback2 != null) {
                                    wallpaperResultCallback2.onPreviewReady();
                                }
                                if (z3) {
                                    KeyguardImageWallpaper keyguardImageWallpaper4 = KeyguardImageWallpaper.this;
                                    keyguardImageWallpaper4.mSmartCroppedResult = null;
                                    if (((PluginWallpaperManagerImpl) keyguardImageWallpaper4.mPluginWallpaperManager).isCustomPackApplied()) {
                                        KeyguardImageWallpaper keyguardImageWallpaper5 = KeyguardImageWallpaper.this;
                                        keyguardImageWallpaper5.getClass();
                                        keyguardImageWallpaper5.mImageSmartCropper.updateSmartCropRect(WallpaperUtils.getCachedWallpaper(i5), i5);
                                        keyguardImageWallpaper5.mSmartCroppedResult = keyguardImageWallpaper5.mImageSmartCropper.mCropResult;
                                    }
                                } else {
                                    if (!KeyguardImageWallpaper.this.mUseCache) {
                                        WallpaperUtils.clearCachedSmartCroppedRect(i5);
                                    }
                                    Bitmap cachedWallpaper = WallpaperUtils.getCachedWallpaper(i5);
                                    Rect rect = new Rect();
                                    rect.left = 0;
                                    rect.top = 0;
                                    rect.right = cachedWallpaper != null ? cachedWallpaper.getWidth() : 0;
                                    rect.bottom = cachedWallpaper != null ? cachedWallpaper.getHeight() : 0;
                                    if (KeyguardImageWallpaper.this.canRotate() && KeyguardImageWallpaper.this.isSmartCropRequired()) {
                                        KeyguardImageWallpaper.this.updateSmartCropRectIfNeeded(i5);
                                        KeyguardImageWallpaper keyguardImageWallpaper6 = KeyguardImageWallpaper.this;
                                        Rect rect2 = keyguardImageWallpaper6.mSmartCroppedResult;
                                        if (rect2 != null) {
                                            keyguardImageWallpaper6.mWallpaperManager.semSetSmartCropRect(i5, rect, rect2);
                                        }
                                    } else {
                                        KeyguardImageWallpaper.this.mWallpaperManager.semSetSmartCropRect(i5, rect, rect);
                                    }
                                }
                                return loaderResult;
                            }
                        }
                        Log.w("KeyguardImageWallpaper", "file problem!");
                    } else {
                        bitmap = null;
                    }
                } else {
                    bitmap = null;
                    str = null;
                }
                boolean z6 = keyguardImageWallpaper.getIntelligentCropHints() == null;
                if (!(KeyguardUpdateMonitor.getCurrentUser() == 0)) {
                    int screenId = PluginWallpaperManager.getScreenId(i3);
                    if (((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).isDynamicWallpaperEnabled(screenId)) {
                        if (((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).getWallpaperBitmap() != null) {
                            bitmap = ((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).getWallpaperBitmap();
                            Log.d("KeyguardImageWallpaper", "loadBitmap wallpaperBitmap: " + bitmap);
                        } else {
                            if (((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).getWallpaperPath() != null) {
                                String wallpaperPath = ((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).getWallpaperPath();
                                AbstractC0000x2c234b15.m3m("loadBitmap from DLS path:", wallpaperPath, "KeyguardImageWallpaper");
                                str = wallpaperPath;
                                bitmap = BitmapUtils.getBitmapFromPath(((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).mContext, wallpaperPath, !z6, false);
                            } else {
                                Uri wallpaperUri = ((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).getWallpaperUri();
                                Log.d("KeyguardImageWallpaper", "loadBitmap from DLS uri:" + wallpaperUri);
                                PluginWallpaperManagerImpl pluginWallpaperManagerImpl = (PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager;
                                pluginWallpaperManagerImpl.getClass();
                                bitmap = BitmapUtils.getBitmapFromUri(pluginWallpaperManagerImpl.mContext, wallpaperUri, !z6, false);
                            }
                        }
                        sb2.append(", from DLS");
                        if (bitmap == null) {
                            Log.e("KeyguardImageWallpaper", "DLS returns null for ImageWallpaper bitmap.");
                        } else {
                            z = true;
                            String str22 = str;
                            if (bitmap == null) {
                                try {
                                    Log.d("KeyguardImageWallpaper", "loadBitmap: which = " + i3 + " , user id = " + r1);
                                    ParcelFileDescriptor lockWallpaperFile = keyguardImageWallpaper.mWallpaperManager.getLockWallpaperFile(keyguardImageWallpaper.mCurrentUserId, i3, z6 ^ true);
                                    if (lockWallpaperFile != null) {
                                        bitmap = BitmapFactory.decodeFileDescriptor(lockWallpaperFile.getFileDescriptor(), null, new BitmapFactory.Options());
                                        parcelFileDescriptor2 = lockWallpaperFile;
                                    } else {
                                        bitmap = keyguardImageWallpaper.getOperatorWallpaper();
                                        parcelFileDescriptor2 = lockWallpaperFile;
                                        if (bitmap == null) {
                                            bitmap = BitmapFactory.decodeStream(new SemWallpaperResourcesInfo(keyguardImageWallpaper.mContext).getDefaultImageWallpaper(i3), null, null);
                                            parcelFileDescriptor2 = lockWallpaperFile;
                                        }
                                    }
                                } catch (OutOfMemoryError unused) {
                                    r1 = 0;
                                    try {
                                        sb2.append(", fail(OOM)");
                                        ((WallpaperLoggerImpl) keyguardImageWallpaper.mLoggerWrapper).log("KeyguardImageWallpaper", sb2.toString());
                                        loaderResult = new LoaderResult(false, null, z);
                                        parcelFileDescriptor = r1;
                                        IoUtils.closeQuietly(parcelFileDescriptor);
                                        if (loaderResult.success) {
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        obj = r1;
                                        r1 = obj;
                                        IoUtils.closeQuietly((AutoCloseable) r1);
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    r1 = obj;
                                    IoUtils.closeQuietly((AutoCloseable) r1);
                                    throw th;
                                }
                            } else {
                                parcelFileDescriptor2 = null;
                            }
                            if (((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).isDynamicWallpaperEnabled()) {
                                Log.d("KeyguardImageWallpaper", "loadBitmap: Skip cropping when dynamic wallpaper is enabled.");
                            } else if (keyguardImageWallpaper.mShouldEnableScreenRotation) {
                                Log.d("KeyguardImageWallpaper", "loadBitmap: shouldEnableScreenRotation is true.");
                            } else {
                                Bitmap cropBitmap = WallpaperUtils.cropBitmap(bitmap, keyguardImageWallpaper.mMetricsWidth, keyguardImageWallpaper.mMetricsHeight);
                                if (cropBitmap != null && !cropBitmap.equals(bitmap) && bitmap != null) {
                                    bitmap.recycle();
                                    bitmap2 = cropBitmap;
                                    if (bitmap2 == null) {
                                        Log.i("KeyguardImageWallpaper", "loadBitmap end");
                                        WallpaperResultCallback wallpaperResultCallback3 = keyguardImageWallpaper.mWallpaperResultCallback;
                                        if (wallpaperResultCallback3 != null) {
                                            wallpaperResultCallback3.onDelegateBitmapReady(bitmap2);
                                        }
                                        sb2.append(", success");
                                        Log.d("KeyguardImageWallpaper", sb2.toString());
                                        ((WallpaperLoggerImpl) keyguardImageWallpaper.mLoggerWrapper).log("KeyguardImageWallpaper", sb2.toString());
                                        if (!LsRune.WALLPAPER_SUB_DISPLAY_MODE && !WallpaperUtils.isDexStandAloneMode()) {
                                            loaderResult2 = new LoaderResult(true, bitmap2, z);
                                        }
                                        loaderResult2 = new LoaderResult(true, bitmap2, z, str22, i3);
                                    } else {
                                        sb2.append(", fail");
                                        Log.d("KeyguardImageWallpaper", sb2.toString());
                                        ((WallpaperLoggerImpl) keyguardImageWallpaper.mLoggerWrapper).log("KeyguardImageWallpaper", sb2.toString());
                                        loaderResult2 = new LoaderResult(false, null, z);
                                    }
                                    loaderResult = loaderResult2;
                                    parcelFileDescriptor = parcelFileDescriptor2;
                                    IoUtils.closeQuietly(parcelFileDescriptor);
                                    if (loaderResult.success) {
                                    }
                                }
                            }
                            bitmap2 = bitmap;
                            if (bitmap2 == null) {
                            }
                            loaderResult = loaderResult2;
                            parcelFileDescriptor = parcelFileDescriptor2;
                            IoUtils.closeQuietly(parcelFileDescriptor);
                            if (loaderResult.success) {
                            }
                        }
                    } else if (LsRune.KEYGUARD_FBE && !keyguardImageWallpaper.mUpdateMonitor.isUserUnlocked(keyguardImageWallpaper.mCurrentUserId) && keyguardImageWallpaper.isFbeWallpaper(screenId)) {
                        bitmap = ((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).getFbeWallpaper(screenId, z6);
                        sb2.append(", from DLS(FBE)");
                    } else if (keyguardImageWallpaper.mWallpaperManager.semGetWallpaperType(i3) == 3) {
                        bitmap = ((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).getFbeWallpaper(screenId, z6);
                        sb2.append(", from DLS(FBE FORCE)");
                    }
                }
                z = false;
                String str222 = str;
                if (bitmap == null) {
                }
                if (((PluginWallpaperManagerImpl) keyguardImageWallpaper.mPluginWallpaperManager).isDynamicWallpaperEnabled()) {
                }
                bitmap2 = bitmap;
                if (bitmap2 == null) {
                }
                loaderResult = loaderResult2;
                parcelFileDescriptor = parcelFileDescriptor2;
                IoUtils.closeQuietly(parcelFileDescriptor);
                if (loaderResult.success) {
                }
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                LoaderResult loaderResult = (LoaderResult) obj;
                super.onPostExecute(loaderResult);
                if (loaderResult == null || !loaderResult.success) {
                    Log.d("KeyguardImageWallpaper", "return onPostExecute: result is null or fail");
                    return;
                }
                KeyguardImageWallpaper keyguardImageWallpaper = KeyguardImageWallpaper.this;
                int i3 = KeyguardImageWallpaper.$r8$clinit;
                boolean init = keyguardImageWallpaper.init(loaderResult.which);
                KeyguardImageWallpaper.this.updateRotationState();
                Log.d("KeyguardImageWallpaper", "updateWallpaper() DONE, init = " + init);
            }
        };
        if (!this.mUseCache || !WallpaperUtils.isCachedWallpaperAvailable(i)) {
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            return;
        }
        WallpaperResultCallback wallpaperResultCallback = this.mWallpaperResultCallback;
        if (wallpaperResultCallback != null) {
            wallpaperResultCallback.onDelegateBitmapReady(WallpaperUtils.getCachedWallpaper(WallpaperUtils.sCurrentWhich));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LoaderResult {
        public final Bitmap bitmap;
        public final boolean fromPluginLock;
        public final boolean success;
        public final String wallpaerPath;
        public final int which;

        public LoaderResult(boolean z, Bitmap bitmap, boolean z2) {
            this.success = z;
            this.bitmap = bitmap;
            this.which = 2;
            this.fromPluginLock = z2;
            this.wallpaerPath = null;
        }

        public LoaderResult(boolean z, Bitmap bitmap, boolean z2, String str, int i) {
            this.success = z;
            this.bitmap = bitmap;
            this.which = i;
            this.fromPluginLock = z2;
            this.wallpaerPath = str;
        }
    }

    @Override // com.android.systemui.wallpaper.view.SystemUIWallpaper, com.android.systemui.wallpaper.view.SystemUIWallpaperBase
    public final void updateBlurState(boolean z) {
    }
}
