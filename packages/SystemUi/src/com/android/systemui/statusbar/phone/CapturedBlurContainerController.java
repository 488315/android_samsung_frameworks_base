package com.android.systemui.statusbar.phone;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.QpRune;
import com.android.systemui.blur.BouncerColorCurve;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.CapturedBlurContainerController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.view.SystemUIWallpaperBase;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CapturedBlurContainerController extends ViewController {
    public final SemGfxImageFilter mBlurFilter;
    public SecQpBlurController.C11322 mBlurUtils;
    public final BouncerColorCurve mBouncerColorCurve;
    public final QSColorCurve mColorCurve;
    public boolean mIsBouncerShowing;
    public final KeyguardWallpaper mKeyguardWallpaper;
    public BlurType mLastBlurType;
    public final Handler mMainHandler;
    public final SecPanelBackgroundController mPanelBackgroundController;
    public Bitmap mPrevWallpaper;
    public final SettingsHelper mSettingsHelper;
    public final Point mSizePoint;
    public final StatusBarStateController mStatusBarStateController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.CapturedBlurContainerController$1 */
    public abstract /* synthetic */ class AbstractC29991 {

        /* renamed from: $SwitchMap$com$android$systemui$statusbar$phone$CapturedBlurContainerController$BlurType */
        public static final /* synthetic */ int[] f353x5dedbc2f;

        static {
            int[] iArr = new int[BlurType.values().length];
            f353x5dedbc2f = iArr;
            try {
                iArr[BlurType.QUICK_PANEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f353x5dedbc2f[BlurType.BOUNCER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum BlurType {
        BOUNCER,
        QUICK_PANEL
    }

    public CapturedBlurContainerController(CapturedBlurContainer capturedBlurContainer, KeyguardWallpaper keyguardWallpaper, StatusBarStateController statusBarStateController, SecPanelBackgroundController secPanelBackgroundController, SettingsHelper settingsHelper, CentralSurfaces centralSurfaces) {
        super(capturedBlurContainer);
        this.mBlurFilter = new SemGfxImageFilter();
        this.mSizePoint = new Point();
        this.mLastBlurType = null;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mIsBouncerShowing = false;
        this.mKeyguardWallpaper = keyguardWallpaper;
        this.mStatusBarStateController = statusBarStateController;
        this.mPanelBackgroundController = secPanelBackgroundController;
        this.mSettingsHelper = settingsHelper;
        this.mColorCurve = new QSColorCurve(getContext());
        this.mBouncerColorCurve = new BouncerColorCurve();
        ((CapturedBlurContainer) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.CapturedBlurContainerController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                final CapturedBlurContainerController capturedBlurContainerController = CapturedBlurContainerController.this;
                capturedBlurContainerController.getClass();
                if (i == i5 && i3 == i7 && i2 == i6 && i4 == i8) {
                    return;
                }
                if (((CapturedBlurContainer) capturedBlurContainerController.mView).getVisibility() != 0) {
                    return;
                }
                Display display = capturedBlurContainerController.getContext().getDisplay();
                Point point = capturedBlurContainerController.mSizePoint;
                display.getRealSize(point);
                point.x /= 5;
                point.y /= 5;
                capturedBlurContainerController.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.CapturedBlurContainerController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CapturedBlurContainerController capturedBlurContainerController2 = CapturedBlurContainerController.this;
                        if (capturedBlurContainerController2.mIsBouncerShowing) {
                            capturedBlurContainerController2.captureAndSetBackground(CapturedBlurContainerController.BlurType.BOUNCER);
                        }
                    }
                });
            }
        });
    }

    public final void captureAndSetBackground(BlurType blurType) {
        Bitmap createScaledBitmap;
        Bitmap bitmap;
        if (((CapturedBlurContainer) this.mView).getVisibility() != 0) {
            return;
        }
        if (SecQpBlurController.this.mIsWakingUp && blurType == BlurType.QUICK_PANEL) {
            return;
        }
        if (this.mStatusBarStateController.getState() == 0 && blurType == BlurType.QUICK_PANEL) {
            Log.d("CapturedBlurContainerController", "getShadeScreenshot() SHADE WM screenshot");
            createScaledBitmap = getWMScreenshot();
        } else if (WallpaperUtils.isLiveWallpaperAppliedOnLock(getContext())) {
            Log.d("CapturedBlurContainerController", "getLiveWallpaperScreenshot() isExternalLiveWallpaper WM screenshot");
            createScaledBitmap = getWMScreenshot();
        } else {
            KeyguardWallpaperController keyguardWallpaperController = (KeyguardWallpaperController) this.mKeyguardWallpaper;
            SystemUIWallpaperBase systemUIWallpaperBase = keyguardWallpaperController.mWallpaperView;
            Bitmap capturedWallpaper = systemUIWallpaperBase != null ? systemUIWallpaperBase.getCapturedWallpaper() : null;
            if (capturedWallpaper == null) {
                Log.e("CapturedBlurContainerController", "Try to get wallpaper bitmap");
                capturedWallpaper = keyguardWallpaperController.getWallpaperBitmap();
                if (capturedWallpaper == null) {
                    Log.e("CapturedBlurContainerController", "Wallpaper capture failed.");
                    createScaledBitmap = null;
                }
            }
            Log.d("CapturedBlurContainerController", "getNormalWallpaperScreenShot() type == " + blurType);
            if (((((float) (capturedWallpaper.getColor(capturedWallpaper.getWidth() / 2, capturedWallpaper.getHeight() / 2).toArgb() >>> 24)) * 1.0f) / 255.0f == 0.0f) && (bitmap = this.mPrevWallpaper) != null) {
                capturedWallpaper = bitmap;
            }
            if (WallpaperUtils.isVideoWallpaper()) {
                capturedWallpaper = WallpaperUtils.cropBitmap(capturedWallpaper, ((CapturedBlurContainer) this.mView).getRootView().getWidth(), ((CapturedBlurContainer) this.mView).getRootView().getHeight());
            }
            Bitmap bitmap2 = capturedWallpaper;
            this.mPrevWallpaper = bitmap2;
            if (!QpRune.QUICK_TABLET && this.mSettingsHelper.getLockscreenWallpaperTransparent(false) != 0) {
                Configuration configuration = getContext().getResources().getConfiguration();
                if (configuration.orientation != 1) {
                    int rotation = configuration.windowConfiguration.getRotation();
                    int i = rotation != 1 ? rotation != 3 ? 0 : 90 : 270;
                    Matrix matrix = new Matrix();
                    matrix.postRotate(i);
                    bitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
                }
            }
            createScaledBitmap = Bitmap.createScaledBitmap(bitmap2, bitmap2.getWidth() / 5, bitmap2.getHeight() / 5, true);
        }
        if (createScaledBitmap == null) {
            return;
        }
        Log.d("CapturedBlurContainerController", "setBlurEffectOnBitmap : " + blurType);
        this.mLastBlurType = blurType;
        int i2 = AbstractC29991.f353x5dedbc2f[blurType.ordinal()];
        SemGfxImageFilter semGfxImageFilter = this.mBlurFilter;
        if (i2 != 2) {
            QSColorCurve qSColorCurve = this.mColorCurve;
            qSColorCurve.setFraction(1.0f);
            semGfxImageFilter.setBlurRadius(qSColorCurve.radius);
            SecQpBlurController.C11322 c11322 = this.mBlurUtils;
            r3 = c11322 != null && c11322.hasCustomColorForPanelBG();
            semGfxImageFilter.setProportionalSaturation(r3 ? qSColorCurve.saturation : 0.0f);
            semGfxImageFilter.setCurveLevel(r3 ? 0.0f : qSColorCurve.curve);
            semGfxImageFilter.setCurveMinX(r3 ? 0.0f : qSColorCurve.minX);
            semGfxImageFilter.setCurveMaxX(r3 ? 255.0f : qSColorCurve.maxX);
            semGfxImageFilter.setCurveMinY(r3 ? 0.0f : qSColorCurve.minY);
            semGfxImageFilter.setCurveMaxY(r3 ? 255.0f : qSColorCurve.maxY);
        } else {
            if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                SemWallpaperColors cachedSemWallpaperColors = Boolean.valueOf(((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).isFoldOpened()) != null ? WallpaperUtils.getCachedSemWallpaperColors(!r1.booleanValue()) : WallpaperUtils.getCachedSemWallpaperColors(WallpaperUtils.isSubDisplay());
                if (cachedSemWallpaperColors == null || cachedSemWallpaperColors.get(512L).getFontColor() != 1) {
                    r3 = false;
                }
            } else {
                r3 = WallpaperUtils.isWhiteKeyguardWallpaper("background");
            }
            BouncerColorCurve bouncerColorCurve = this.mBouncerColorCurve;
            bouncerColorCurve.setFraction(1.0f, r3);
            semGfxImageFilter.setBlurRadius(bouncerColorCurve.mRadius);
            semGfxImageFilter.setProportionalSaturation(0.0f);
            semGfxImageFilter.setCurveLevel(bouncerColorCurve.mCurve);
            semGfxImageFilter.setCurveMinX(bouncerColorCurve.mMinX);
            semGfxImageFilter.setCurveMaxX(bouncerColorCurve.mMaxX);
            semGfxImageFilter.setCurveMinY(bouncerColorCurve.mMinY);
            semGfxImageFilter.setCurveMaxY(bouncerColorCurve.mMaxY);
        }
        ((CapturedBlurContainer) this.mView).setBackgroundDrawable(new BitmapDrawable(getContext().getResources(), semGfxImageFilter.applyToBitmap(createScaledBitmap)));
    }

    public final Bitmap getWMScreenshot() {
        Rect rect = new Rect(0, 0, 0, 0);
        int displayId = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getDisplayId();
        boolean z = getContext().getResources().getConfiguration().orientation == 1;
        Point point = this.mSizePoint;
        int i = point.x;
        int i2 = point.y;
        return SemWindowManager.getInstance().screenshot(displayId, 2000, false, rect, z ? Math.min(i, i2) : Math.max(i, i2), z ? Math.max(point.x, point.y) : Math.min(point.x, point.y), false, 0, true);
    }

    public final void updateContainerVisibility() {
        SecQpBlurController.C11322 c11322;
        ((CapturedBlurContainer) this.mView).setVisibility((this.mPanelBackgroundController.mMaxAlpha > 1.0f ? 1 : (this.mPanelBackgroundController.mMaxAlpha == 1.0f ? 0 : -1)) == 0 || ((c11322 = this.mBlurUtils) != null && SecQpBlurController.this.mIsBlurReduced) ? 8 : 0);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
    }
}
