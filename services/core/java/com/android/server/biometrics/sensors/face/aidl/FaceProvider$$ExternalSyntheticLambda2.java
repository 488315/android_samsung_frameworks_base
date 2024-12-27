package com.android.server.biometrics.sensors.face.aidl;

import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.PerformanceTracker;
import com.android.server.biometrics.sensors.face.aidl.FaceProvider;
import com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter;
import vendor.samsung.hardware.biometrics.face.ISehSession;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FaceProvider$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() {
        int pause;
        int loadTA;
        int resume;
        int pause2;
        int resume2;
        int unloadTA;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                FaceProvider faceProvider = (FaceProvider) obj;
                faceProvider.mDaemon = null;
                for (int i2 = 0; i2 < faceProvider.mFaceSensors.mSensors.size(); i2++) {
                    Sensor sensor = (Sensor) faceProvider.mFaceSensors.mSensors.valueAt(i2);
                    PerformanceTracker instanceForSensorId = PerformanceTracker.getInstanceForSensorId(faceProvider.mFaceSensors.mSensors.keyAt(i2));
                    if (instanceForSensorId != null) {
                        instanceForSensorId.mHALDeathCount++;
                    } else {
                        Slog.w(faceProvider.getTag(), "Performance tracker is null. Not counting HAL death.");
                    }
                    BaseClientMonitor currentClient = sensor.mScheduler.getCurrentClient();
                    if (currentClient != 0 && currentClient.isInterruptable()) {
                        Slog.e("Sensor", "Sending face hardware unavailable error for client: " + currentClient);
                        ((ErrorConsumer) currentClient).onError(1, 0);
                        FrameworkStatsLog.write(148, 4, 1, -1);
                    } else if (currentClient != 0) {
                        currentClient.cancel();
                    }
                    sensor.mScheduler.recordCrashState();
                    sensor.mScheduler.reset();
                    sensor.mCurrentSession = null;
                }
                break;
            case 1:
                SemFaceServiceExImpl serviceExtImpl = ((FaceProvider) obj).getServiceExtImpl();
                serviceExtImpl.getClass();
                long currentTimeMillis = System.currentTimeMillis();
                if (!(serviceExtImpl.mScheduler.getCurrentClient() instanceof FaceAuthenticationClient)) {
                    Slog.w("SemFace", "daemonPauseAuth : client is not Auth");
                    break;
                } else {
                    try {
                        if (serviceExtImpl.mIsHIDL) {
                            HidlToAidlSessionAdapter hidlToAidlSessionAdapter = (HidlToAidlSessionAdapter) ((AidlSession) serviceExtImpl.mSensor.mLazySession.get()).mSession;
                            hidlToAidlSessionAdapter.getClass();
                            try {
                                pause = ((ISehBiometricsFace) hidlToAidlSessionAdapter.mSession.get()).sehPauseEnrollment();
                            } catch (RemoteException e) {
                                Slog.e("HidlToAidlSessionAdapter", "semPauseEnroll HIDL : ", e);
                                pause = -1;
                            }
                        } else {
                            ISehSession iSehSession = serviceExtImpl.mISehSession;
                            if (iSehSession == null) {
                                Slog.w("SemFace", "daemonPauseAuth(): no face seh HAL!");
                                break;
                            } else {
                                pause = ((ISehSession.Stub.Proxy) iSehSession).pause();
                            }
                        }
                        Slog.w("SemFace", "pause FINISH (" + (System.currentTimeMillis() - currentTimeMillis) + "ms) RESULT: " + pause);
                        break;
                    } catch (Exception e2) {
                        Slog.e("SemFace", "daemonPauseAuth: ", e2);
                        return;
                    }
                }
            case 2:
                FaceProvider faceProvider2 = (FaceProvider) obj;
                faceProvider2.getHalInstance();
                SemFaceServiceExImpl serviceExtImpl2 = faceProvider2.getServiceExtImpl();
                serviceExtImpl2.getClass();
                long currentTimeMillis2 = System.currentTimeMillis();
                try {
                    if (serviceExtImpl2.mIsHIDL) {
                        HidlToAidlSessionAdapter hidlToAidlSessionAdapter2 = (HidlToAidlSessionAdapter) ((AidlSession) serviceExtImpl2.mSensor.mLazySession.get()).mSession;
                        hidlToAidlSessionAdapter2.getClass();
                        try {
                            loadTA = ((ISehBiometricsFace) hidlToAidlSessionAdapter2.mSession.get()).sehOpenTaSession();
                        } catch (RemoteException e3) {
                            Slog.e("HidlToAidlSessionAdapter", "semOpenTaSession HIDL : ", e3);
                            loadTA = -1;
                        }
                    } else {
                        ISehSession iSehSession2 = serviceExtImpl2.mISehSession;
                        if (iSehSession2 == null) {
                            Slog.w("SemFace", "daemonSessionOpen(): no face seh HAL!");
                            break;
                        } else {
                            loadTA = ((ISehSession.Stub.Proxy) iSehSession2).loadTA();
                        }
                    }
                    Slog.w("SemFace", "sehOpenTaSession FINISH (" + (System.currentTimeMillis() - currentTimeMillis2) + "ms) RESULT: " + loadTA);
                    break;
                } catch (Exception e4) {
                    Slog.e("SemFace", "daemonSessionOpen: ", e4);
                    return;
                }
            case 3:
                SemFaceServiceExImpl serviceExtImpl3 = ((FaceProvider) obj).getServiceExtImpl();
                serviceExtImpl3.getClass();
                long currentTimeMillis3 = System.currentTimeMillis();
                if (!(serviceExtImpl3.mScheduler.getCurrentClient() instanceof FaceAuthenticationClient)) {
                    Slog.w("SemFace", "daemonResumeAuth : client is not Auth");
                    break;
                } else {
                    try {
                        if (serviceExtImpl3.mIsHIDL) {
                            resume = ((HidlToAidlSessionAdapter) ((AidlSession) serviceExtImpl3.mSensor.mLazySession.get()).mSession).semResumeEnroll();
                        } else {
                            ISehSession iSehSession3 = serviceExtImpl3.mISehSession;
                            if (iSehSession3 == null) {
                                Slog.w("SemFace", "daemonResumeAuth(): no face seh HAL!");
                                break;
                            } else {
                                resume = ((ISehSession.Stub.Proxy) iSehSession3).resume();
                            }
                        }
                        Slog.w("SemFace", "resume FINISH (" + (System.currentTimeMillis() - currentTimeMillis3) + "ms) RESULT: " + resume);
                        break;
                    } catch (Exception e5) {
                        Slog.e("SemFace", "daemonResumeAuth: ", e5);
                        return;
                    }
                }
            case 4:
                SemFaceServiceExImpl serviceExtImpl4 = ((FaceProvider) obj).getServiceExtImpl();
                serviceExtImpl4.getClass();
                long currentTimeMillis4 = System.currentTimeMillis();
                if (serviceExtImpl4.mScheduler.getCurrentClient() instanceof FaceEnrollClient) {
                    try {
                        if (serviceExtImpl4.mIsHIDL) {
                            pause2 = ((HidlToAidlSessionAdapter) ((AidlSession) serviceExtImpl4.mSensor.mLazySession.get()).mSession).semResumeEnroll();
                        } else {
                            ISehSession iSehSession4 = serviceExtImpl4.mISehSession;
                            if (iSehSession4 == null) {
                                Slog.w("SemFace", "daemonPauseEnroll(): no face seh HAL!");
                                break;
                            } else {
                                pause2 = ((ISehSession.Stub.Proxy) iSehSession4).pause();
                            }
                        }
                        Slog.w("SemFace", "pause FINISH (" + (System.currentTimeMillis() - currentTimeMillis4) + "ms) RESULT: " + pause2);
                    } catch (Exception e6) {
                        Slog.e("SemFace", "daemonPauseEnroll: ", e6);
                    }
                    serviceExtImpl4.mIsEnrollPausing = true;
                    break;
                } else {
                    Slog.w("SemFace", "daemonPauseEnroll : client is not Enroll");
                    break;
                }
            case 5:
                FaceProvider faceProvider3 = (FaceProvider) obj;
                faceProvider3.getServiceExtImpl().mEnrollStartTime = System.currentTimeMillis();
                SemFaceServiceExImpl serviceExtImpl5 = faceProvider3.getServiceExtImpl();
                serviceExtImpl5.getClass();
                long currentTimeMillis5 = System.currentTimeMillis();
                if (serviceExtImpl5.mScheduler.getCurrentClient() instanceof FaceEnrollClient) {
                    try {
                        if (serviceExtImpl5.mIsHIDL) {
                            resume2 = ((HidlToAidlSessionAdapter) ((AidlSession) serviceExtImpl5.mSensor.mLazySession.get()).mSession).semResumeEnroll();
                        } else {
                            ISehSession iSehSession5 = serviceExtImpl5.mISehSession;
                            if (iSehSession5 == null) {
                                Slog.w("SemFace", "daemonResumeEnroll(): no face seh HAL!");
                                break;
                            } else {
                                resume2 = ((ISehSession.Stub.Proxy) iSehSession5).resume();
                            }
                        }
                        Slog.w("SemFace", "resume FINISH (" + (System.currentTimeMillis() - currentTimeMillis5) + "ms) RESULT: " + resume2);
                    } catch (Exception e7) {
                        Slog.e("SemFace", "daemonResumeEnroll: ", e7);
                    }
                    serviceExtImpl5.mIsEnrollPausing = false;
                    int i3 = Utils.isTalkBackEnabled(serviceExtImpl5.mContext) ? 60000 : 30000;
                    HermesService$3$$ExternalSyntheticOutline0.m(i3, "resumeEnrollExt : ", "SemFace");
                    SemFaceServiceExImpl.AnonymousClass1 anonymousClass1 = serviceExtImpl5.mHandlerMain;
                    anonymousClass1.removeMessages(1);
                    anonymousClass1.sendEmptyMessageDelayed(1, i3);
                    serviceExtImpl5.acquireDVFS(i3, 1);
                    break;
                } else {
                    Slog.w("SemFace", "daemonResumeEnroll : client is not Enroll");
                    break;
                }
            case 6:
                FaceProvider faceProvider4 = (FaceProvider) obj;
                faceProvider4.getHalInstance();
                SemFaceServiceExImpl serviceExtImpl6 = faceProvider4.getServiceExtImpl();
                serviceExtImpl6.getClass();
                long currentTimeMillis6 = System.currentTimeMillis();
                try {
                    if (serviceExtImpl6.mIsHIDL) {
                        HidlToAidlSessionAdapter hidlToAidlSessionAdapter3 = (HidlToAidlSessionAdapter) ((AidlSession) serviceExtImpl6.mSensor.mLazySession.get()).mSession;
                        hidlToAidlSessionAdapter3.getClass();
                        try {
                            unloadTA = ((ISehBiometricsFace) hidlToAidlSessionAdapter3.mSession.get()).sehCloseTaSession();
                        } catch (RemoteException e8) {
                            Slog.e("HidlToAidlSessionAdapter", "semCloseTaSession HIDL : ", e8);
                            unloadTA = -1;
                        }
                    } else {
                        ISehSession iSehSession6 = serviceExtImpl6.mISehSession;
                        if (iSehSession6 == null) {
                            Slog.w("SemFace", "daemonSessionClose(): no face seh HAL!");
                            break;
                        } else {
                            unloadTA = ((ISehSession.Stub.Proxy) iSehSession6).unloadTA();
                        }
                    }
                    Slog.w("SemFace", "sehCloseTaSession FINISH (" + (System.currentTimeMillis() - currentTimeMillis6) + "ms) RESULT: " + unloadTA);
                    break;
                } catch (Exception e9) {
                    Slog.e("SemFace", "daemonSessionClose: ", e9);
                    return;
                }
            default:
                FaceProvider.BiometricTaskStackListener biometricTaskStackListener = (FaceProvider.BiometricTaskStackListener) obj;
                for (int i4 = 0; i4 < FaceProvider.this.mFaceSensors.mSensors.size(); i4++) {
                    BaseClientMonitor currentClient2 = ((Sensor) FaceProvider.this.mFaceSensors.mSensors.valueAt(i4)).mScheduler.getCurrentClient();
                    if (!(currentClient2 instanceof AuthenticationClient)) {
                        Slog.e(FaceProvider.this.getTag(), "Task stack changed for client: " + currentClient2);
                    } else if (!Utils.isKeyguard(FaceProvider.this.mContext, currentClient2.mOwner) && !Utils.isSystem(FaceProvider.this.mContext, currentClient2.mOwner) && Utils.isBackground(currentClient2.mOwner) && !currentClient2.mAlreadyDone) {
                        Slog.e(FaceProvider.this.getTag(), "Stopping background authentication, currentClient: " + currentClient2);
                        ((Sensor) FaceProvider.this.mFaceSensors.mSensors.valueAt(i4)).mScheduler.cancelAuthenticationOrDetection(currentClient2.mToken, currentClient2.mRequestId);
                    }
                }
                break;
        }
    }
}
