package com.android.systemui.blur.domain.interactor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.blur.BouncerColorCurve;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.blur.di.ScreenShotBitmapFactory;
import com.android.systemui.blur.di.ScreenShotBitmapProvider;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.shade.domain.interactor.SecNotificationShadeWindowStateInteractor;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.graphics.SemGfxImageFilter;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes.dex */
public final class SecCapturedBlurBitmapGenerator {
    public static final String TAG;
    public final SemGfxImageFilter blurFilter = new SemGfxImageFilter();
    public final Context context;
    public final KeyguardFoldController keyguardFoldController;
    public final SecNotificationShadeWindowStateInteractor notificationShadeWindowStateInteractor;
    public final ScreenShotBitmapFactory screenShotBitmapFactory;
    public final SecBlurCustomColorInteractor secBlurCustomColorInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SecPanelBlurBinding.BlurType.values().length];
            try {
                iArr[SecPanelBlurBinding.BlurType.QUICK_PANEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SecPanelBlurBinding.BlurType.BOUNCER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SecPanelBlurBinding.BlurType.FULL_SCREEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SecPanelBlurBinding.BlurType.ALT_VIEW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SecPanelBlurBinding.BlurType.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(SecCapturedBlurBitmapGenerator.class).getSimpleName();
    }

    public SecCapturedBlurBitmapGenerator(Context context, SecBlurCustomColorInteractor secBlurCustomColorInteractor, SecNotificationShadeWindowStateInteractor secNotificationShadeWindowStateInteractor, KeyguardFoldController keyguardFoldController, ScreenShotBitmapFactory screenShotBitmapFactory) {
        this.context = context;
        this.secBlurCustomColorInteractor = secBlurCustomColorInteractor;
        this.notificationShadeWindowStateInteractor = secNotificationShadeWindowStateInteractor;
        this.keyguardFoldController = keyguardFoldController;
        this.screenShotBitmapFactory = screenShotBitmapFactory;
    }

    public final BitmapDrawable getBlurredBitmapWithEffect(SecPanelBlurBinding.BlurType blurType) {
        ScreenShotBitmapProvider screenShotBitmapProvider;
        Bitmap screenShot;
        boolean zIsWhiteKeyguardWallpaper;
        ScreenShotBitmapProvider screenShotBitmapProvider2;
        int iIntValue = ((Number) this.notificationShadeWindowStateInteractor.statusBarState.$$delegate_0.getValue()).intValue();
        ScreenShotBitmapFactory screenShotBitmapFactory = this.screenShotBitmapFactory;
        if ((iIntValue == 0 && blurType == SecPanelBlurBinding.BlurType.QUICK_PANEL) || WallpaperUtils.sWallpaperType == 7) {
            ScreenShotBitmapProvider.Type type = ScreenShotBitmapProvider.Type.WINDOW_MANAGER;
            screenShotBitmapFactory.getClass();
            int i = ScreenShotBitmapFactory.WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
            if (i == 1) {
                screenShotBitmapProvider2 = screenShotBitmapFactory.windowManagerScreenShotProvider;
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                screenShotBitmapProvider2 = screenShotBitmapFactory.wallpaperScreenShotProvider;
            }
            screenShot = screenShotBitmapProvider2.getScreenShot();
        } else {
            ScreenShotBitmapProvider.Type type2 = ScreenShotBitmapProvider.Type.WALLPAPER;
            screenShotBitmapFactory.getClass();
            int i2 = ScreenShotBitmapFactory.WhenMappings.$EnumSwitchMapping$0[type2.ordinal()];
            if (i2 == 1) {
                screenShotBitmapProvider = screenShotBitmapFactory.windowManagerScreenShotProvider;
            } else {
                if (i2 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                screenShotBitmapProvider = screenShotBitmapFactory.wallpaperScreenShotProvider;
            }
            screenShot = screenShotBitmapProvider.getScreenShot();
        }
        if (screenShot == null) {
            return null;
        }
        Resources resources = this.context.getResources();
        Log.d(TAG, "setBlurEffectOnBitmap : " + blurType);
        int i3 = WhenMappings.$EnumSwitchMapping$0[blurType.ordinal()];
        if (i3 == 1) {
            QSColorCurve qSColorCurve = new QSColorCurve(this.context);
            qSColorCurve.setFraction(1.0f);
            this.blurFilter.setBlurRadius(qSColorCurve.radius);
            boolean zBooleanValue = ((Boolean) this.secBlurCustomColorInteractor.hasCustomColorApplied.$$delegate_0.getValue()).booleanValue();
            this.blurFilter.setProportionalSaturation(zBooleanValue ? qSColorCurve.saturation : 0.0f);
            this.blurFilter.setCurveLevel(zBooleanValue ? 0.0f : qSColorCurve.curve);
            this.blurFilter.setCurveMinX(zBooleanValue ? 0.0f : qSColorCurve.minX);
            this.blurFilter.setCurveMaxX(zBooleanValue ? 255.0f : qSColorCurve.maxX);
            this.blurFilter.setCurveMinY(zBooleanValue ? 0.0f : qSColorCurve.minY);
            this.blurFilter.setCurveMaxY(zBooleanValue ? 255.0f : qSColorCurve.maxY);
        } else if (i3 == 2 || i3 == 3 || i3 == 4) {
            BouncerColorCurve bouncerColorCurve = new BouncerColorCurve();
            if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                ((KeyguardFoldControllerImpl) this.keyguardFoldController).isFoldOpened();
                zIsWhiteKeyguardWallpaper = false;
            } else {
                zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY);
            }
            bouncerColorCurve.setFraction(1.0f, zIsWhiteKeyguardWallpaper);
            this.blurFilter.setBlurRadius(bouncerColorCurve.mRadius);
            this.blurFilter.setProportionalSaturation(0.0f);
            this.blurFilter.setCurveLevel(bouncerColorCurve.mCurve);
            this.blurFilter.setCurveMinX(bouncerColorCurve.mMinX);
            this.blurFilter.setCurveMaxX(bouncerColorCurve.mMaxX);
            this.blurFilter.setCurveMinY(bouncerColorCurve.mMinY);
            this.blurFilter.setCurveMaxY(bouncerColorCurve.mMaxY);
        } else if (i3 != 5) {
            throw new NoWhenBranchMatchedException();
        }
        return new BitmapDrawable(resources, this.blurFilter.applyToBitmap(screenShot));
    }
}
