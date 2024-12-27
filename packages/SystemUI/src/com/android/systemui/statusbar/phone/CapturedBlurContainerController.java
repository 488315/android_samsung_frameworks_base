package com.android.systemui.statusbar.phone;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.LsRune;
import com.android.systemui.blur.BouncerColorCurve;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.CapturedBlurContainerController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.view.SemWindowManager;

public final class CapturedBlurContainerController extends ViewController {
    public final SemGfxImageFilter mBlurFilter;
    public SecQpBlurController.AnonymousClass2 mBlurUtils;
    public final BouncerColorCurve mBouncerColorCurve;
    public final QSColorCurve mColorCurve;
    public boolean mIsBouncerShowing;
    public boolean mIsFaceWidgetShowing;
    public final KeyguardFoldController mKeyguardFoldController;
    public BlurType mLastBlurType;
    public final Handler mMainHandler;
    public final SecPanelBackgroundController mPanelBackgroundController;
    public Bitmap mPrevWallpaper;
    private final SettingsHelper mSettingsHelper;
    public final Point mSizePoint;
    public final StatusBarStateController mStatusBarStateController;
    public final CapturedBlurContainer mView;

    public enum BlurType {
        BOUNCER,
        QUICK_PANEL,
        FACE_WIDGET
    }

    public CapturedBlurContainerController(CapturedBlurContainer capturedBlurContainer, StatusBarStateController statusBarStateController, SecPanelBackgroundController secPanelBackgroundController, SettingsHelper settingsHelper, KeyguardFoldController keyguardFoldController) {
        super(capturedBlurContainer);
        this.mBlurFilter = new SemGfxImageFilter();
        this.mSizePoint = new Point();
        this.mLastBlurType = null;
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.mIsBouncerShowing = false;
        this.mIsFaceWidgetShowing = false;
        this.mView = capturedBlurContainer;
        this.mStatusBarStateController = statusBarStateController;
        this.mPanelBackgroundController = secPanelBackgroundController;
        this.mSettingsHelper = settingsHelper;
        this.mColorCurve = new QSColorCurve(capturedBlurContainer.getContext());
        this.mBouncerColorCurve = new BouncerColorCurve();
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            this.mKeyguardFoldController = keyguardFoldController;
        }
        capturedBlurContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.CapturedBlurContainerController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                final CapturedBlurContainerController capturedBlurContainerController = CapturedBlurContainerController.this;
                capturedBlurContainerController.getClass();
                if (i == i5 && i3 == i7 && i2 == i6 && i4 == i8) {
                    return;
                }
                CapturedBlurContainer capturedBlurContainer2 = capturedBlurContainerController.mView;
                if (capturedBlurContainer2.getVisibility() != 0) {
                    return;
                }
                capturedBlurContainer2.getContext().getDisplay().getRealSize(capturedBlurContainerController.mSizePoint);
                Point point = capturedBlurContainerController.mSizePoint;
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

    /* JADX WARN: Removed duplicated region for block: B:71:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00cc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void captureAndSetBackground(com.android.systemui.statusbar.phone.CapturedBlurContainerController.BlurType r22) {
        /*
            Method dump skipped, instructions count: 562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.CapturedBlurContainerController.captureAndSetBackground(com.android.systemui.statusbar.phone.CapturedBlurContainerController$BlurType):void");
    }

    public final Bitmap getWMScreenshot() {
        Rect rect = new Rect(0, 0, 0, 0);
        CapturedBlurContainer capturedBlurContainer = this.mView;
        int displayId = ((WindowManager) capturedBlurContainer.getContext().getSystemService("window")).getDefaultDisplay().getDisplayId();
        boolean z = capturedBlurContainer.getContext().getResources().getConfiguration().orientation == 1;
        Point point = this.mSizePoint;
        int i = point.x;
        int i2 = point.y;
        int min = z ? Math.min(i, i2) : Math.max(i, i2);
        Point point2 = this.mSizePoint;
        return SemWindowManager.getInstance().screenshot(displayId, 2000, false, rect, min, z ? Math.max(point2.x, point2.y) : Math.min(point2.x, point2.y), false, 0, true);
    }

    public final void setAlpha(float f, BlurType blurType) {
        BlurType blurType2;
        CapturedBlurContainer capturedBlurContainer = this.mView;
        if (capturedBlurContainer.getVisibility() != 0) {
            return;
        }
        if (f == 0.0f) {
            capturedBlurContainer.setBackground(null);
        }
        if (f > 0.0f && (capturedBlurContainer.getBackground() == null || (blurType2 = this.mLastBlurType) == null || blurType2 != blurType)) {
            captureAndSetBackground(blurType);
        }
        capturedBlurContainer.setAlpha(f);
    }

    public final void updateContainerVisibility() {
        SecQpBlurController.AnonymousClass2 anonymousClass2;
        this.mView.setVisibility((!this.mIsBouncerShowing && !this.mIsFaceWidgetShowing && (this.mPanelBackgroundController.mMaxAlpha > 1.0f ? 1 : (this.mPanelBackgroundController.mMaxAlpha == 1.0f ? 0 : -1)) == 0) || ((anonymousClass2 = this.mBlurUtils) != null && SecQpBlurController.this.mIsBlurReduced) ? 8 : 0);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
    }
}
