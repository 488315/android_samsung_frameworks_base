package com.samsung.android.biometrics.app.setting.face;

import android.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.SensorPrivacyManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.samsung.android.biometrics.app.setting.Utils;

/* loaded from: classes.dex */
public final class FaceEnrollFragment extends Fragment {
    private FaceEnrollActivity mActivity = null;
    private View mEnrollView = null;
    private Handler mHandler = null;

    public static /* synthetic */ void $r8$lambda$VTFNGJB9oFsX5rpSC0nAQeplndg(FaceEnrollFragment faceEnrollFragment, SensorPrivacyManager sensorPrivacyManager) {
        faceEnrollFragment.getClass();
        sensorPrivacyManager.setSensorPrivacy(2, 2, false);
        FaceEnrollActivity faceEnrollActivity = faceEnrollFragment.mActivity;
        if (faceEnrollActivity != null) {
            faceEnrollActivity.startEnrollment();
        }
    }

    public static /* synthetic */ void $r8$lambda$zNL_pmPQ7TNRBqspvthgzFO1qr8(final FaceEnrollFragment faceEnrollFragment) {
        Context context = faceEnrollFragment.getContext();
        if (context == null) {
            return;
        }
        final SensorPrivacyManager sensorPrivacyManager = (SensorPrivacyManager) context.getSystemService("sensor_privacy");
        if (!sensorPrivacyManager.isSensorPrivacyEnabled(2)) {
            FaceEnrollActivity faceEnrollActivity = faceEnrollFragment.mActivity;
            if (faceEnrollActivity != null) {
                faceEnrollActivity.startEnrollment();
                return;
            }
            return;
        }
        Log.i("BSS_FaceEnrollFragment", "Camera access is blocked.");
        AlertDialog.Builder builder = new AlertDialog.Builder(faceEnrollFragment.mActivity, (!Utils.isColorThemeEnabled(faceEnrollFragment.mActivity) || Utils.isNightThemeOn(faceEnrollFragment.mActivity)) ? R.style.Theme.DeviceDefault.Dialog.Alert : 0);
        builder.setTitle(com.samsung.android.biometrics.app.setting.R.string.camera_access_turn_on_title);
        builder.setMessage(com.samsung.android.biometrics.app.setting.R.string.camera_access_turn_on_message);
        builder.setPositiveButton(com.samsung.android.biometrics.app.setting.R.string.camera_access_turn_on_button, new DialogInterface.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FaceEnrollFragment.$r8$lambda$VTFNGJB9oFsX5rpSC0nAQeplndg(FaceEnrollFragment.this, sensorPrivacyManager);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FaceEnrollFragment.this.mActivity.finish();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("BSS_FaceEnrollFragment", "onCreate");
        this.mHandler = new Handler();
    }

    @Override // android.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d("BSS_FaceEnrollFragment", "onCreateView");
        this.mActivity = (FaceEnrollActivity) getActivity();
        View view = this.mEnrollView;
        if (view != null) {
            return view;
        }
        View inflate = layoutInflater.inflate(com.samsung.android.biometrics.app.setting.R.layout.face_enroll_layout, viewGroup, false);
        this.mEnrollView = inflate;
        if (this.mActivity != null && inflate != null) {
            Button button = (Button) inflate.findViewById(com.samsung.android.biometrics.app.setting.R.id.glasses_guide_btn);
            if (button != null) {
                button.semSetButtonShapeEnabled(true);
            }
            this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.face.FaceEnrollFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FaceEnrollFragment.$r8$lambda$zNL_pmPQ7TNRBqspvthgzFO1qr8(FaceEnrollFragment.this);
                }
            }, Utils.isTalkBackEnabled(this.mActivity) ? 2000L : 1000L);
            this.mActivity.initFaceEnroll(this.mEnrollView);
        }
        return this.mEnrollView;
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
