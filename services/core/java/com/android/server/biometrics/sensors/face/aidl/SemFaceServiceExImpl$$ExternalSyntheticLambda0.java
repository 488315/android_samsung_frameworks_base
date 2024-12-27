package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.biometrics.face.ISession;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.biometrics.sensors.face.aidl.SemFaceServiceExImpl;
import vendor.samsung.hardware.biometrics.face.ISehSession;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemFaceServiceExImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SemFaceServiceExImpl f$0;

    public /* synthetic */ SemFaceServiceExImpl$$ExternalSyntheticLambda0(SemFaceServiceExImpl semFaceServiceExImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = semFaceServiceExImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SemFaceServiceExImpl semFaceServiceExImpl = this.f$0;
        switch (i) {
            case 0:
                if (semFaceServiceExImpl.mOrientationEventListener.canDetectOrientation()) {
                    semFaceServiceExImpl.mOrientationEventListener.enable();
                }
                SemFaceServiceExImpl.AnonymousClass3 anonymousClass3 = semFaceServiceExImpl.mProximitySensorMgr;
                if (anonymousClass3 != null && !anonymousClass3.mIsRegisterListener) {
                    try {
                        HandlerThread handlerThread = new HandlerThread("sensor thread", 10);
                        anonymousClass3.mSensorThread = handlerThread;
                        handlerThread.start();
                        anonymousClass3.mSmgr.registerListener(anonymousClass3, anonymousClass3.mPrxSensor, 3, anonymousClass3.mSensorThread.getThreadHandler());
                    } catch (Exception e) {
                        Slog.e("FaceService", "registerListener : failed to register sensor listener", e);
                        anonymousClass3.mSensorThread.quitSafely();
                    }
                    anonymousClass3.mIsRegisterListener = true;
                }
                SystemProperties.set("service.bioface.authenticating", "1");
                return;
            case 1:
                semFaceServiceExImpl.sendError(5, 0);
                return;
            case 2:
                semFaceServiceExImpl.mISehFace = null;
                ISehSession iSehSession = semFaceServiceExImpl.mISehSession;
                if (iSehSession != null) {
                    try {
                        ((ISehSession.Stub.Proxy) iSehSession).close();
                    } catch (Exception e2) {
                        Slog.e("SemFace", "semResetConnection Exception :", e2);
                    }
                }
                semFaceServiceExImpl.mISehSession = null;
                ISession iSession = semFaceServiceExImpl.mISession;
                if (iSession != null) {
                    try {
                        iSession.close();
                    } catch (Exception e3) {
                        Slog.e("SemFace", "semResetConnection Exception : ", e3);
                    }
                }
                semFaceServiceExImpl.mISession = null;
                semFaceServiceExImpl.mIsResetting = false;
                semFaceServiceExImpl.stopOperation();
                return;
            default:
                semFaceServiceExImpl.mOrientationEventListener.disable();
                SemFaceServiceExImpl.AnonymousClass3 anonymousClass32 = semFaceServiceExImpl.mProximitySensorMgr;
                if (anonymousClass32 != null) {
                    synchronized (anonymousClass32) {
                        if (anonymousClass32.mIsRegisterListener) {
                            try {
                                anonymousClass32.mSmgr.unregisterListener(anonymousClass32);
                            } catch (Exception e4) {
                                Slog.w("FaceService", "unregisterListener : failed to unregister sensor listener", e4);
                            }
                            HandlerThread handlerThread2 = anonymousClass32.mSensorThread;
                            if (handlerThread2 != null) {
                                handlerThread2.quitSafely();
                                anonymousClass32.mSensorThread = null;
                            }
                            anonymousClass32.mIsRegisterListener = false;
                        }
                    }
                }
                SystemProperties.set("service.bioface.authenticating", "0");
                return;
        }
    }
}
