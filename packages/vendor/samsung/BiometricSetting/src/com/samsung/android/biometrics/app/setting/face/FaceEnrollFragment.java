package com.samsung.android.biometrics.app.setting.face;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.hardware.SensorPrivacyManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.RenderMode;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class FaceEnrollFragment extends Fragment {
    public FaceEnrollActivity mActivity = null;
    public View mEnrollView = null;
    public Handler mHandler = null;
    public PunchHoleVIView mPunchHoleVIView;

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        this.mCalled = true;
        restoreChildFragmentState();
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl.mCurState < 1) {
            fragmentManagerImpl.dispatchCreate();
        }
        Log.d("BSS_FaceEnrollFragment", "onCreate");
        this.mHandler = new Handler();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        Log.d("BSS_FaceEnrollFragment", "onCreateView");
        FragmentActivity.HostCallbacks hostCallbacks = this.mHost;
        this.mActivity =
                (FaceEnrollActivity)
                        (hostCallbacks == null ? null : (FragmentActivity) hostCallbacks.activity);
        View view = this.mEnrollView;
        if (view != null) {
            return view;
        }
        View inflate = layoutInflater.inflate(R.layout.face_enroll_layout, viewGroup, false);
        this.mEnrollView = inflate;
        if (this.mActivity != null && inflate != null) {
            Button button = (Button) inflate.findViewById(R.id.glasses_guide_btn);
            if (button != null) {
                button.semSetButtonShapeEnabled(true);
            }
            this.mHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.samsung.android.biometrics.app.setting.face.FaceEnrollFragment$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            final FaceEnrollFragment faceEnrollFragment = FaceEnrollFragment.this;
                            FragmentActivity.HostCallbacks hostCallbacks2 =
                                    faceEnrollFragment.mHost;
                            Context context =
                                    hostCallbacks2 == null ? null : hostCallbacks2.context;
                            if (context == null) {
                                return;
                            }
                            Context context2 =
                                    hostCallbacks2 == null ? null : hostCallbacks2.context;
                            PunchHoleVIView punchHoleVIView = new PunchHoleVIView();
                            punchHoleVIView.mPunchHoleIcon = null;
                            punchHoleVIView.mPunchCutView = null;
                            punchHoleVIView.mContext = context2;
                            PunchHoleVIHelper punchHoleVIHelper = new PunchHoleVIHelper(context2);
                            if (punchHoleVIHelper.initialize()) {
                                android.util.Log.d("BSS_PunchHoleVIView", "start initialize");
                                View inflate2 =
                                        ((LayoutInflater)
                                                        context2.getSystemService(
                                                                "layout_inflater"))
                                                .inflate(
                                                        R.layout.face_punch_cut_vi_layout,
                                                        (ViewGroup) null);
                                punchHoleVIView.mPunchCutView = inflate2;
                                LottieAnimationView lottieAnimationView =
                                        (LottieAnimationView)
                                                inflate2.findViewById(
                                                        R.id.punchCutLottieAnimationView);
                                punchHoleVIView.mPunchHoleIcon = lottieAnimationView;
                                lottieAnimationView.setAnimation(
                                        punchHoleVIHelper.getAnimationName(true));
                                punchHoleVIView.mPunchHoleIcon.loop(true);
                                punchHoleVIView.mPunchHoleIcon.setRenderMode(RenderMode.HARDWARE);
                                Rect punchHoleVIRect = punchHoleVIHelper.getPunchHoleVIRect();
                                punchHoleVIView.mPunchHoleIcon.setTranslationX(
                                        punchHoleVIRect.left);
                                punchHoleVIView.mPunchHoleIcon.setTranslationY(punchHoleVIRect.top);
                                RelativeLayout.LayoutParams layoutParams =
                                        (RelativeLayout.LayoutParams)
                                                punchHoleVIView.mPunchHoleIcon.getLayoutParams();
                                layoutParams.width = punchHoleVIRect.width();
                                layoutParams.height = punchHoleVIRect.height();
                                punchHoleVIView.mPunchHoleIcon.setLayoutParams(layoutParams);
                                WindowManager.LayoutParams layoutParams2 =
                                        new WindowManager.LayoutParams(2008, 296, -3);
                                layoutParams2.layoutInDisplayCutoutMode = 1;
                                layoutParams2.gravity = 48;
                                WindowManager windowManager =
                                        (WindowManager) context2.getSystemService("window");
                                punchHoleVIView.mWindowManager = windowManager;
                                windowManager.addView(punchHoleVIView.mPunchCutView, layoutParams2);
                            } else {
                                android.util.Log.i(
                                        "BSS_PunchHoleVIView", "Punch hole initialize failed.");
                            }
                            faceEnrollFragment.mPunchHoleVIView = punchHoleVIView;
                            final SensorPrivacyManager sensorPrivacyManager =
                                    (SensorPrivacyManager)
                                            context.getSystemService("sensor_privacy");
                            if (sensorPrivacyManager.isSensorPrivacyEnabled(2)) {
                                Log.i("BSS_FaceEnrollFragment", "Camera access is blocked.");
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(
                                                faceEnrollFragment.mActivity,
                                                (!Utils.isColorThemeEnabled(
                                                                        faceEnrollFragment
                                                                                .mActivity)
                                                                || Utils.isNightThemeOn(
                                                                        faceEnrollFragment
                                                                                .mActivity))
                                                        ? android.R.style
                                                                .Theme
                                                                .DeviceDefault
                                                                .Dialog
                                                                .Alert
                                                        : 0);
                                builder.setTitle(R.string.camera_access_turn_on_title);
                                builder.setMessage(R.string.camera_access_turn_on_message);
                                builder.setPositiveButton(
                                        R.string.camera_access_turn_on_button,
                                        new DialogInterface
                                                .OnClickListener() { // from class:
                                                                     // com.samsung.android.biometrics.app.setting.face.FaceEnrollFragment$$ExternalSyntheticLambda1
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public final void onClick(
                                                    DialogInterface dialogInterface, int i) {
                                                FaceEnrollFragment faceEnrollFragment2 =
                                                        FaceEnrollFragment.this;
                                                SensorPrivacyManager sensorPrivacyManager2 =
                                                        sensorPrivacyManager;
                                                faceEnrollFragment2.getClass();
                                                sensorPrivacyManager2.setSensorPrivacy(2, 2, false);
                                                FaceEnrollActivity faceEnrollActivity =
                                                        faceEnrollFragment2.mActivity;
                                                if (faceEnrollActivity != null) {
                                                    faceEnrollActivity.startEnrollment();
                                                    PunchHoleVIView punchHoleVIView2 =
                                                            faceEnrollFragment2.mPunchHoleVIView;
                                                    if (punchHoleVIView2 != null) {
                                                        punchHoleVIView2.play();
                                                    }
                                                }
                                            }
                                        });
                                builder.setNegativeButton(
                                        android.R.string.cancel,
                                        new DialogInterface
                                                .OnClickListener() { // from class:
                                                                     // com.samsung.android.biometrics.app.setting.face.FaceEnrollFragment$$ExternalSyntheticLambda2
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public final void onClick(
                                                    DialogInterface dialogInterface, int i) {
                                                FaceEnrollFragment.this.mActivity.finish();
                                            }
                                        });
                                builder.setCancelable(false);
                                builder.create().show();
                                return;
                            }
                            FaceEnrollActivity faceEnrollActivity = faceEnrollFragment.mActivity;
                            if (faceEnrollActivity != null) {
                                faceEnrollActivity.startEnrollment();
                                PunchHoleVIView punchHoleVIView2 =
                                        faceEnrollFragment.mPunchHoleVIView;
                                if (punchHoleVIView2 != null) {
                                    punchHoleVIView2.play();
                                }
                            }
                        }
                    },
                    Utils.isTalkBackEnabled(this.mActivity) ? 2000L : 1000L);
            FaceEnrollActivity faceEnrollActivity = this.mActivity;
            View view2 = this.mEnrollView;
            faceEnrollActivity.getClass();
            android.util.Log.d("BSS_FaceEnrollActivity", "initFaceEnroll");
            faceEnrollActivity.mFaceGuideText = (TextView) view2.findViewById(R.id.guide_text);
            faceEnrollActivity.mFaceProgressText =
                    (TextView) view2.findViewById(R.id.progress_text);
            faceEnrollActivity.mGuideContinueBtn =
                    (Button) view2.findViewById(R.id.glasses_guide_btn);
            faceEnrollActivity.mGlassesOffVi =
                    (LottieAnimationView) view2.findViewById(R.id.glasses_guide_video);
            faceEnrollActivity.mFaceHelpGuideText =
                    (TextView) view2.findViewById(R.id.guide_text_additional);
            faceEnrollActivity.mEnrollAnimationView =
                    (FaceRegisterEffectView) view2.findViewById(R.id.scan_animation_view);
            View findViewById = view2.findViewById(R.id.knox_logo_view);
            faceEnrollActivity.mSecuredKnoxLogoView = findViewById;
            if (findViewById != null) {
                findViewById.setVisibility(0);
            }
            if (Utils.Config.FACE_FEATURE_POSE_ENROLL) {
                faceEnrollActivity.mPoseGuideVi =
                        (LottieAnimationView) view2.findViewById(R.id.pose_guide_video);
            }
            if (Utils.Config.FEATURE_FACE_HAL) {
                ImageView imageView = (ImageView) view2.findViewById(R.id.camera_preview);
                faceEnrollActivity.mCameraPreview = imageView;
                imageView.setVisibility(0);
            }
            if (!Utils.DEBUG) {
                faceEnrollActivity.getWindow().addFlags(8192);
            }
            if (Utils.Config.FEATURE_SUPPORT_DUAL_DISPLAY
                    && !Utils.getFolderOpenState(
                            faceEnrollActivity, faceEnrollActivity.mIsSupportDualDisplay)) {
                faceEnrollActivity.showFolderOpenDialog();
            }
            TextView textView = faceEnrollActivity.mFaceProgressText;
            if (textView != null) {
                textView.setText(
                        TextUtils.expandTemplate(
                                faceEnrollActivity.getText(
                                        R.string.face_enroll_percent_format_text),
                                String.format("%d", 0)));
            }
            TextView textView2 = faceEnrollActivity.mFaceGuideText;
            if (textView2 != null) {
                textView2.setText(R.string.face_guide_look_ahead);
                if (faceEnrollActivity.mIsTalkbackEnabled) {
                    faceEnrollActivity.mFaceGuideText.setText(
                            faceEnrollActivity.getPositionGuideText(false));
                    faceEnrollActivity.mFaceGuideText.setVisibility(0);
                }
            }
            Button button2 = faceEnrollActivity.mGuideContinueBtn;
            if (button2 != null && faceEnrollActivity.mIsSetupWizard) {
                button2.setVisibility(8);
            }
            FaceRegisterEffectView faceRegisterEffectView = faceEnrollActivity.mEnrollAnimationView;
            if (faceRegisterEffectView != null) {
                faceRegisterEffectView.mState = 1;
                faceRegisterEffectView.mStartingTimestamp = SystemClock.elapsedRealtime();
                faceRegisterEffectView.mIsPreviewMask = true;
                faceRegisterEffectView.stopScanEffect();
            }
        }
        return this.mEnrollView;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        View view;
        Log.d("BSS_FaceEnrollFragment", "onDestroy");
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (this.mPunchHoleVIView != null) {
            Log.i("BSS_FaceEnrollFragment", "remove PunchHoleVIView");
            PunchHoleVIView punchHoleVIView = this.mPunchHoleVIView;
            punchHoleVIView.pause();
            WindowManager windowManager = punchHoleVIView.mWindowManager;
            if (windowManager != null && (view = punchHoleVIView.mPunchCutView) != null) {
                try {
                    windowManager.removeView(view);
                    punchHoleVIView.mPunchCutView = null;
                    punchHoleVIView.mPunchHoleIcon = null;
                } catch (Exception e) {
                    android.util.Log.e("BSS_PunchHoleVIView", e.toString());
                }
            }
            this.mPunchHoleVIView = null;
        }
        this.mCalled = true;
    }
}
