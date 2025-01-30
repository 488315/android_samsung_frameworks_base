package com.android.systemui.biometrics;

import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Binder;
import android.util.Log;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthController.C10584;
import com.android.systemui.biometrics.AuthController.C10595;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.concurrency.ExecutionImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthController$6$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Binder f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ AuthController$6$$ExternalSyntheticLambda0(Binder binder, List list, int i) {
        this.$r8$classId = i;
        this.f$0 = binder;
        this.f$1 = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AuthController.C10606 c10606 = (AuthController.C10606) this.f$0;
                List list = this.f$1;
                final AuthController authController = AuthController.this;
                Execution execution = authController.mExecution;
                ((ExecutionImpl) execution).assertIsMainThread();
                Log.d("AuthController", "handleAllFingerprintAuthenticatorsRegistered | sensors: " + Arrays.toString(list.toArray()));
                authController.mAllFingerprintAuthenticatorsRegistered = true;
                authController.mFpProps = list;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : authController.mFpProps) {
                    if (fingerprintSensorPropertiesInternal.isAnyUdfpsType()) {
                        arrayList.add(fingerprintSensorPropertiesInternal);
                    }
                    if (fingerprintSensorPropertiesInternal.isAnySidefpsType()) {
                        arrayList2.add(fingerprintSensorPropertiesInternal);
                    }
                }
                if (arrayList.isEmpty()) {
                    arrayList = null;
                }
                authController.mUdfpsProps = arrayList;
                if (arrayList != null) {
                    UdfpsController udfpsController = (UdfpsController) authController.mUdfpsControllerFactory.get();
                    authController.mUdfpsController = udfpsController;
                    ((HashSet) udfpsController.mCallbacks).add(new UdfpsController.Callback() { // from class: com.android.systemui.biometrics.AuthController.3
                        public C10573() {
                        }

                        @Override // com.android.systemui.biometrics.UdfpsController.Callback
                        public final void onFingerDown() {
                            AuthDialog authDialog = AuthController.this.mCurrentDialog;
                            if (authDialog != null) {
                                AuthContainerView authContainerView = (AuthContainerView) authDialog;
                                if (authContainerView.mBiometricView == null) {
                                    Log.e("AuthContainerView", "onPointerDown(): mBiometricView is null");
                                } else if (((HashSet) authContainerView.mFailedModalities).contains(8)) {
                                    Log.d("AuthContainerView", "retrying failed modalities (pointer down)");
                                    ((HashSet) authContainerView.mFailedModalities).remove(8);
                                    authContainerView.mBiometricCallback.onAction(4);
                                }
                            }
                        }

                        @Override // com.android.systemui.biometrics.UdfpsController.Callback
                        public final void onFingerUp() {
                        }
                    });
                    UdfpsController udfpsController2 = authController.mUdfpsController;
                    udfpsController2.mAuthControllerUpdateUdfpsLocation = new AuthController$$ExternalSyntheticLambda1(authController, 1);
                    udfpsController2.mUdfpsDisplayMode = new UdfpsDisplayMode(authController.mContext, execution, authController, authController.mUdfpsLogger);
                    authController.mUdfpsBounds = ((FingerprintSensorPropertiesInternal) authController.mUdfpsProps.get(0)).getLocation().getRect();
                }
                if (arrayList2.isEmpty()) {
                    arrayList2 = null;
                }
                authController.mSidefpsProps = arrayList2;
                if (arrayList2 != null) {
                    authController.mSideFpsController = (SideFpsController) authController.mSidefpsControllerFactory.get();
                }
                authController.mFingerprintManager.registerBiometricStateListener(authController.new C10584());
                authController.updateSensorLocations();
                Iterator it = ((HashSet) authController.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((AuthController.Callback) it.next()).onAllAuthenticatorsRegistered(2);
                }
                break;
            default:
                AuthController.C10617 c10617 = (AuthController.C10617) this.f$0;
                List list2 = this.f$1;
                AuthController authController2 = AuthController.this;
                ((ExecutionImpl) authController2.mExecution).assertIsMainThread();
                Log.d("AuthController", "handleAllFaceAuthenticatorsRegistered | sensors: " + Arrays.toString(list2.toArray()));
                authController2.mFaceManager.registerBiometricStateListener(authController2.new C10595());
                Iterator it2 = ((HashSet) authController2.mCallbacks).iterator();
                while (it2.hasNext()) {
                    ((AuthController.Callback) it2.next()).onAllAuthenticatorsRegistered(8);
                }
                break;
        }
    }
}
