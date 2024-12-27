package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.Utils;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class UdfpsSensorWindow extends SysUiWindow {
    static final String FINGERPRINT_BG_WINDOW_CONTAINER_COLOR = "#252525";
    public LottieAnimationView mAnimationView;
    public final UdfpsWindowCallback mCallback;
    public DisplayMetrics mDisplayMetrics;
    public final DisplayStateManager mDisplayStateManager;
    public int mFingerIconHideReason;
    public FrameLayout mFingerprintLayout;
    public RelativeLayout mFpIconContainer;
    public AnonymousClass1 mLottieCompositionLoadedListener;
    public int mLottieViewFilterColor;
    public int mOriginPosX;
    public int mOriginPosY;
    public final FingerprintSensorInfo mSensorInfo;
    boolean mUseBaseWindow;

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow$1, reason: invalid class name */
    public final class AnonymousClass1 implements LottieOnCompositionLoadedListener {
        public AnonymousClass1() {}
    }

    public UdfpsSensorWindow(
            Context context,
            UdfpsWindowCallback udfpsWindowCallback,
            FingerprintSensorInfo fingerprintSensorInfo,
            DisplayStateManager displayStateManager) {
        super(context);
        this.mOriginPosX = 0;
        this.mOriginPosY = 0;
        this.mLottieViewFilterColor = 0;
        this.mSensorInfo = fingerprintSensorInfo;
        this.mCallback = udfpsWindowCallback;
        this.mDisplayStateManager = displayStateManager;
        this.mDisplayMetrics = Utils.getDisplayMetrics(context);
        Log.d("BSS_UdfpsSensorWindow", "DM = " + this.mDisplayMetrics);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public void addView() {
        if (this.mUseBaseWindow) {
            updateViewLayout();
        } else {
            super.addView();
        }
    }

    public int getContainerColor() {
        return Color.parseColor(FINGERPRINT_BG_WINDOW_CONTAINER_COLOR);
    }

    public final InsetDrawable getContainerDrawable() {
        int containerColor = getContainerColor();
        if (Color.alpha(containerColor) == 0) {
            return null;
        }
        int applyDimension = (int) TypedValue.applyDimension(5, 0.5f, this.mDisplayMetrics);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(containerColor);
        gradientDrawable.setCornerRadius(
                Math.round(37 * Utils.getDisplayMetrics(this.mContext).density));
        return new InsetDrawable((Drawable) gradientDrawable, applyDimension, 0, applyDimension, 0);
    }

    public int getIconColor() {
        return Color.parseColor("#fafafa");
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        Point displaySize = Utils.getDisplaySize(this.mContext);
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(displaySize.x, displaySize.y, 2619, 16777240, -3);
        layoutParams.flags &= -65537;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags |= 8272;
        if (!Utils.isTpaMode(this.mContext)) {
            layoutParams.privateFlags |= 1048576;
        }
        layoutParams.setFitInsetsTypes(0);
        layoutParams.semAddExtensionFlags(262144);
        layoutParams.semAddPrivateFlags(536870912);
        layoutParams.semAddExtensionFlags(131072);
        layoutParams.semAddExtensionFlags(8);
        layoutParams.setTitle("FP Iconview");
        layoutParams.surfaceType = 5;
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_UdfpsSensorWindow";
    }

    public final void handleRotation(int i) {
        this.mFpIconContainer.clearAnimation();
        this.mFpIconContainer.setLayoutAnimation(
                new LayoutAnimationController(
                        AnimationUtils.loadAnimation(
                                this.mContext,
                                i != 0
                                        ? i != 1
                                                ? i != 2
                                                        ? i != 3
                                                                ? 0
                                                                : R.anim
                                                                        .fingerprint_icon_rotation_270
                                                        : R.anim.fingerprint_icon_rotation_180
                                                : R.anim.fingerprint_icon_rotation_90
                                        : R.anim.fingerprint_icon_rotation),
                        RecyclerView.DECELERATION_RATE));
        this.mFpIconContainer.startLayoutAnimation();
        InsetDrawable containerDrawable = getContainerDrawable();
        if (this.mFpIconContainer.getBackground() == null || containerDrawable == null) {
            return;
        }
        int applyDimension = (int) TypedValue.applyDimension(5, 14.5f, this.mDisplayMetrics);
        Bitmap createBitmap =
                Bitmap.createBitmap(applyDimension, applyDimension, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = applyDimension / 2.0f;
        canvas.rotate(i * 90.0f, f, f);
        containerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        containerDrawable.draw(canvas);
        this.mFpIconContainer.setBackground(new BitmapDrawable(createBitmap));
    }

    public final void hideSensorIcon(int i) {
        this.mFingerIconHideReason = i;
        setSensorVisibility(4);
    }

    public final void initFromBaseWindow(UdfpsSensorWindow udfpsSensorWindow) {
        if (udfpsSensorWindow == null) {
            throw new IllegalArgumentException("UdfpsSensorWindow must not be null");
        }
        this.mUseBaseWindow = true;
        initLayout(udfpsSensorWindow.mFingerprintLayout);
    }

    public final void initLayout(FrameLayout frameLayout) {
        this.mFingerprintLayout = frameLayout;
        this.mBaseView = frameLayout;
        frameLayout.setLayoutDirection(0);
        this.mAnimationView =
                (LottieAnimationView)
                        this.mFingerprintLayout.findViewById(R.id.sem_fingerprint_sensor_imageview);
        RelativeLayout relativeLayout =
                (RelativeLayout)
                        this.mFingerprintLayout.findViewById(R.id.sem_fingerprint_icon_container);
        this.mFpIconContainer = relativeLayout;
        relativeLayout.setBackground(null);
        this.mFpIconContainer.setVisibility(4);
        initSensorLayout();
    }

    public void initSensorLayout() {
        this.mAnimationView.cancelAnimation();
        this.mAnimationView.clearAnimation();
        this.mAnimationView.removeAllLottieOnCompositionLoadedListener();
        if (this.mLottieCompositionLoadedListener == null) {
            this.mLottieCompositionLoadedListener = new AnonymousClass1();
        }
        updateSensorViewLocation();
        if (this.mUseBaseWindow) {
            setSensorIcon();
        }
    }

    public final boolean isSensorIconShown() {
        return isVisible() && this.mFpIconContainer.getVisibility() == 0;
    }

    public final void moveIcon(int i, int i2) {
        if (Utils.DEBUG) {
            Log.d("BSS_UdfpsSensorWindow", "moveIcon: x = [" + i + "], y = [" + i2 + "]");
        }
        float f = this.mOriginPosX + i;
        this.mFpIconContainer.setX(f);
        this.mFpIconContainer.setY(this.mOriginPosY + i2);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public void onConfigurationChanged(Configuration configuration) {
        this.mDisplayMetrics = Utils.getDisplayMetrics(this.mContext);
        updateSensorViewLocation();
        updateViewLayout();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public void onRotationInfoChanged(int i) {
        this.mDisplayMetrics = Utils.getDisplayMetrics(this.mContext);
        handleRotation(i);
        updateViewLayout();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public void removeView() {
        if (this.mUseBaseWindow) {
            setVisibility(8);
        } else {
            super.removeView();
        }
        this.mH.post(new UdfpsSensorWindow$$ExternalSyntheticLambda0(this, 8));
    }

    public final void setLottieViewColorFilter(int i) {
        AnonymousClass1 anonymousClass1;
        this.mLottieViewFilterColor = i;
        LottieAnimationView lottieAnimationView = this.mAnimationView;
        if (lottieAnimationView == null
                || (anonymousClass1 = this.mLottieCompositionLoadedListener) == null) {
            return;
        }
        lottieAnimationView.addLottieOnCompositionLoadedListener(anonymousClass1);
    }

    public void setSensorIcon() {
        int iconColor = getIconColor();
        this.mAnimationView.setAnimation("ic_fingerprint_prompt.json");
        if (Color.alpha(iconColor) != 0) {
            setLottieViewColorFilter(iconColor);
        }
        InsetDrawable containerDrawable = getContainerDrawable();
        if (containerDrawable != null) {
            this.mFpIconContainer.setBackground(containerDrawable);
        }
    }

    public final void setSensorVisibility(int i) {
        Log.d("BSS_UdfpsSensorWindow", "setSensorVisibility: [" + i + "]");
        if (this.mFpIconContainer.getVisibility() == i) {
            return;
        }
        this.mFpIconContainer.setVisibility(i);
        this.mH.post(new UdfpsSensorWindow$$ExternalSyntheticLambda0(this, i));
    }

    public final void setVisibility(int i) {
        Log.i("BSS_UdfpsSensorWindow", "setVisibility: [" + i + "]");
        View view = this.mBaseView;
        if (view != null) {
            view.setVisibility(i);
        }
    }

    public void showSensorIcon() {
        setVisibility(0);
        setSensorVisibility(0);
    }

    public final void updateSensorViewLocation() {
        DisplayStateManager displayStateManager = this.mDisplayStateManager;
        int i = displayStateManager.mCurrentRotation;
        FingerprintSensorInfo fingerprintSensorInfo = this.mSensorInfo;
        int i2 = fingerprintSensorInfo.mSensorImageSize;
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) this.mAnimationView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = this.mFpIconContainer.getLayoutParams();
        int applyDimension = (int) TypedValue.applyDimension(5, 14.5f, this.mDisplayMetrics);
        if (layoutParams2.width != applyDimension) {
            layoutParams2.width = applyDimension;
            layoutParams2.height = applyDimension;
            this.mFpIconContainer.setLayoutParams(layoutParams2);
            this.mFpIconContainer.requestLayout();
        }
        if (layoutParams.width != i2 || layoutParams.height != i2) {
            layoutParams.width = i2;
            layoutParams.height = i2;
            this.mAnimationView.setLayoutParams(layoutParams);
            this.mAnimationView.requestLayout();
        }
        int i3 = displayStateManager.mCurrentRotation;
        Point displaySize = Utils.getDisplaySize(this.mContext);
        Point point = new Point();
        if (fingerprintSensorInfo.mIsAnyUdfps) {
            Point sensorImagePoint$2 = fingerprintSensorInfo.getSensorImagePoint$2();
            point.x = (displaySize.x / 2) - sensorImagePoint$2.x;
            point.y = displaySize.y - sensorImagePoint$2.y;
        }
        int applyDimension2 = (int) TypedValue.applyDimension(5, 14.5f, this.mDisplayMetrics);
        int i4 = point.x;
        int i5 = (applyDimension2 - fingerprintSensorInfo.mSensorImageSize) / 2;
        int i6 = i4 - i5;
        int i7 = point.y - i5;
        this.mFpIconContainer.setX(i6);
        this.mFpIconContainer.setY(i7);
        this.mOriginPosX = i6;
        this.mOriginPosY = i7;
        Log.i("BSS_UdfpsSensorWindow", "SIL: " + i3 + ", " + i6 + ", " + i7);
        handleRotation(i);
    }
}
