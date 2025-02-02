package com.samsung.android.biometrics.app.setting.fingerprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.android.internal.annotations.VisibleForTesting;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.SysUiWindow;
import com.samsung.android.biometrics.app.setting.Utils;

/* loaded from: classes.dex */
public class UdfpsSensorWindow extends SysUiWindow {

    @VisibleForTesting
    static final String FINGERPRINT_BG_WINDOW_CONTAINER_COLOR = "#252525";
    protected LottieAnimationView mAnimationView;
    protected UdfpsWindowCallback mCallback;
    protected DisplayMetrics mDisplayMetrics;
    protected DisplayStateManager mDisplayStateManager;
    protected FrameLayout mFingerprintLayout;
    protected RelativeLayout mFpIconContainer;
    LottieOnCompositionLoadedListener mLottieCompositionLoadedListener;
    int mLottieViewFilterColor;
    protected int mOriginPosX;
    protected int mOriginPosY;
    protected final UdfpsInfo mSensorInfo;

    @VisibleForTesting
    boolean mUseBaseWindow;

    /* renamed from: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow$1 */
    final class C02481 implements LottieOnCompositionLoadedListener {
        C02481() {
        }

        @Override // com.airbnb.lottie.LottieOnCompositionLoadedListener
        public final void onCompositionLoaded() {
            UdfpsSensorWindow.this.mAnimationView.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new UdfpsSensorWindow$1$$ExternalSyntheticLambda0(this));
        }
    }

    public UdfpsSensorWindow(Context context, UdfpsWindowCallback udfpsWindowCallback, UdfpsInfo udfpsInfo, DisplayStateManager displayStateManager) {
        super(context);
        this.mOriginPosX = 0;
        this.mOriginPosY = 0;
        this.mLottieViewFilterColor = 0;
        this.mSensorInfo = udfpsInfo;
        this.mCallback = udfpsWindowCallback;
        this.mDisplayStateManager = displayStateManager;
        this.mDisplayMetrics = Utils.getDisplayMetrics(getContext());
        Log.d("BSS_UdfpsSensorWindow", "DM = " + this.mDisplayMetrics);
    }

    private void handleRotation(int i) {
        int layoutDirection;
        this.mFpIconContainer.clearAnimation();
        this.mFpIconContainer.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(), i != 0 ? i != 1 ? i != 2 ? i != 3 ? 0 : R.anim.fingerprint_icon_rotation_270 : R.anim.fingerprint_icon_rotation_180 : R.anim.fingerprint_icon_rotation_90 : R.anim.fingerprint_icon_rotation), 0.0f));
        this.mFpIconContainer.startLayoutAnimation();
        Drawable background = this.mFpIconContainer.getBackground();
        if (background != null) {
            int applyDimension = (int) TypedValue.applyDimension(5, 14.5f, this.mDisplayMetrics);
            Bitmap createBitmap = Bitmap.createBitmap(applyDimension, applyDimension, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            if (i == 0) {
                i = 4;
            }
            float currentRotation = (i - this.mDisplayStateManager.getCurrentRotation()) * 90.0f;
            if (currentRotation == 0.0f && (layoutDirection = background.getLayoutDirection()) != i) {
                currentRotation = (i - layoutDirection) * 90.0f;
            }
            float f = applyDimension / 2.0f;
            canvas.rotate(currentRotation, f, f);
            background.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            background.draw(canvas);
            this.mFpIconContainer.setBackground(new BitmapDrawable(createBitmap));
        }
    }

    private void setSensorVisibility(int i) {
        Log.d("BSS_UdfpsSensorWindow", "setSensorVisibility: [" + i + "]");
        if (this.mFpIconContainer.getVisibility() == i) {
            return;
        }
        this.mFpIconContainer.setVisibility(i);
        invokeIconVisibilityCallback(i);
    }

    private void updateSensorViewLocation() {
        int currentRotation = this.mDisplayStateManager.getCurrentRotation();
        UdfpsInfo udfpsInfo = this.mSensorInfo;
        int imageSize = udfpsInfo.getImageSize();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mAnimationView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = this.mFpIconContainer.getLayoutParams();
        int applyDimension = (int) TypedValue.applyDimension(5, 14.5f, this.mDisplayMetrics);
        if (layoutParams2.width != applyDimension) {
            layoutParams2.width = applyDimension;
            layoutParams2.height = applyDimension;
            this.mFpIconContainer.setLayoutParams(layoutParams2);
            this.mFpIconContainer.requestLayout();
        }
        if (layoutParams.width != imageSize || layoutParams.height != imageSize) {
            layoutParams.width = imageSize;
            layoutParams.height = imageSize;
            this.mAnimationView.setLayoutParams(layoutParams);
            this.mAnimationView.requestLayout();
        }
        int currentRotation2 = this.mDisplayStateManager.getCurrentRotation();
        Point sensorImagePoint = udfpsInfo.getSensorImagePoint(Utils.getDisplaySize(getContext()));
        int applyDimension2 = (int) TypedValue.applyDimension(5, 14.5f, this.mDisplayMetrics);
        int imageSize2 = sensorImagePoint.x - ((applyDimension2 - udfpsInfo.getImageSize()) / 2);
        int imageSize3 = sensorImagePoint.y - ((applyDimension2 - udfpsInfo.getImageSize()) / 2);
        this.mFpIconContainer.setX(imageSize2);
        this.mFpIconContainer.setY(imageSize3);
        this.mOriginPosX = imageSize2;
        this.mOriginPosY = imageSize3;
        Log.i("BSS_UdfpsSensorWindow", "SIL: " + currentRotation2 + ", " + imageSize2 + ", " + imageSize3);
        handleRotation(currentRotation);
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public void addView() {
        if (this.mUseBaseWindow) {
            updateViewLayout();
        } else {
            super.addView();
        }
    }

    protected int getContainerColor() {
        return Color.parseColor(FINGERPRINT_BG_WINDOW_CONTAINER_COLOR);
    }

    protected int getIconColor() {
        return Color.parseColor("#fafafa");
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final WindowManager.LayoutParams getLayoutParams() {
        Point displaySize = Utils.getDisplaySize(getContext());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(displaySize.x, displaySize.y, 2619, 16777240, -3);
        layoutParams.flags &= -65537;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags |= 8272;
        if (!Utils.isTpaMode(getContext())) {
            layoutParams.privateFlags |= 1048576;
        }
        layoutParams.setFitInsetsTypes(0);
        layoutParams.semAddExtensionFlags(262144);
        layoutParams.semAddPrivateFlags(536870912);
        layoutParams.semAddExtensionFlags(131072);
        layoutParams.semAddExtensionFlags(8);
        layoutParams.setTitle("FP Iconview");
        return layoutParams;
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final String getLogTag() {
        return "BSS_UdfpsSensorWindow";
    }

    public final void hideSensorIcon() {
        setSensorVisibility(4);
    }

    public final void init() {
        initLayout((FrameLayout) getLayoutInflater().inflate(R.layout.sem_fingerprint_view, (ViewGroup) null));
    }

    public final void initFromBaseWindow(UdfpsSensorWindow udfpsSensorWindow) {
        if (udfpsSensorWindow == null) {
            throw new IllegalArgumentException("UdfpsSensorWindow must not be null");
        }
        this.mUseBaseWindow = true;
        initLayout(udfpsSensorWindow.mFingerprintLayout);
    }

    protected final void initLayout(FrameLayout frameLayout) {
        this.mFingerprintLayout = frameLayout;
        this.mBaseView = frameLayout;
        frameLayout.setLayoutDirection(0);
        this.mAnimationView = (LottieAnimationView) this.mFingerprintLayout.findViewById(R.id.sem_fingerprint_sensor_imageview);
        RelativeLayout relativeLayout = (RelativeLayout) this.mFingerprintLayout.findViewById(R.id.sem_fingerprint_icon_container);
        this.mFpIconContainer = relativeLayout;
        relativeLayout.setBackground(null);
        this.mFpIconContainer.setVisibility(4);
        initSensorLayout();
    }

    protected void initSensorLayout() {
        this.mAnimationView.cancelAnimation();
        this.mAnimationView.clearAnimation();
        this.mAnimationView.removeAllLottieOnCompositionLoadedListener();
        if (this.mLottieCompositionLoadedListener == null) {
            this.mLottieCompositionLoadedListener = new C02481();
        }
        updateSensorViewLocation();
        if (this.mUseBaseWindow) {
            setSensorIcon();
        }
    }

    protected final void invokeIconVisibilityCallback(final int i) {
        this.f22mH.post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UdfpsSensorWindow udfpsSensorWindow = UdfpsSensorWindow.this;
                int i2 = i;
                UdfpsWindowCallback udfpsWindowCallback = udfpsSensorWindow.mCallback;
                if (udfpsWindowCallback != null) {
                    udfpsWindowCallback.onSensorIconVisibilityChanged(i2);
                }
            }
        });
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
        this.mDisplayMetrics = Utils.getDisplayMetrics(getContext());
        updateSensorViewLocation();
        updateViewLayout();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public void onRotationInfoChanged(int i) {
        this.mDisplayMetrics = Utils.getDisplayMetrics(getContext());
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
        invokeIconVisibilityCallback(8);
    }

    protected final void setIconContainerColor() {
        int containerColor = getContainerColor();
        int applyDimension = (int) TypedValue.applyDimension(5, 0.5f, this.mDisplayMetrics);
        if (Color.alpha(containerColor) == 0) {
            return;
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(containerColor);
        gradientDrawable.setCornerRadius(Math.round(37 * Utils.getDisplayMetrics(getContext()).density));
        this.mFpIconContainer.setBackground(new InsetDrawable((Drawable) gradientDrawable, applyDimension, 0, applyDimension, 0));
    }

    @SuppressLint({"UseCompatLoadingForDrawables"})
    public void setSensorIcon() {
        int iconColor = getIconColor();
        this.mAnimationView.setImageDrawable(getContext().getDrawable(R.drawable.sem_fingerprint_icon_bg_white));
        this.mAnimationView.setColorFilter(iconColor, PorterDuff.Mode.SRC_ATOP);
        setIconContainerColor();
    }

    @Override // com.samsung.android.biometrics.app.setting.SysUiWindow
    public final void setVisibility(int i) {
        Log.i("BSS_UdfpsSensorWindow", "setVisibility: [" + i + "]");
        super.setVisibility(i);
    }

    public void showSensorIcon() {
        setVisibility(0);
        setSensorVisibility(0);
    }
}
