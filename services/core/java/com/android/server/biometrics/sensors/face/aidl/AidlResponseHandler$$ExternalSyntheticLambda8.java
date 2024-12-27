package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.face.Face;
import android.util.Slog;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.biometrics.SemBioLoggingManager;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.RemovalConsumer;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Face f$0;
    public final /* synthetic */ int[] f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda8(Face face, int[] iArr, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = face;
        this.f$1 = iArr;
        this.f$2 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BiometricAuthenticator.Identifier identifier = this.f$0;
                int[] iArr = this.f$1;
                int i = this.f$2;
                ((RemovalConsumer) obj).onRemoved(identifier, (iArr.length - i) - 1);
                Slog.d("AidlResponseHandler", "onEnrollmentsRemoved : faceId: " + iArr[i]);
                SemFaceServiceExImpl semFaceServiceExImpl = SemFaceServiceExImpl.getInstance();
                int i2 = iArr[i];
                semFaceServiceExImpl.getClass();
                Slog.i("SemFace", "onRemovedExt BILG ");
                if (((ArrayList) semFaceServiceExImpl.mFaceUtils.getBiometricsForUser(semFaceServiceExImpl.mContext, semFaceServiceExImpl.mUserId)).size() > 0) {
                    semFaceServiceExImpl.sendBroadcast(i2, semFaceServiceExImpl.mUserId, "com.samsung.android.bio.face.intent.action.FACE_REMOVED");
                } else {
                    semFaceServiceExImpl.sendBroadcast(-1, semFaceServiceExImpl.mUserId, "com.samsung.android.bio.face.intent.action.FACE_RESET");
                }
                SemBioAnalyticsManager semBioAnalyticsManager = semFaceServiceExImpl.mSemAnalyticsManager;
                if (semBioAnalyticsManager != null) {
                    semBioAnalyticsManager.faceInsertLog(new SemBioAnalyticsManager.EventData(-1, 3, "FARM", String.valueOf(i2)));
                }
                SemBioLoggingManager semBioLoggingManager = SemBioLoggingManager.get();
                semBioLoggingManager.getClass();
                SemBioLoggingManager.LoggingInfo loggingInfo = new SemBioLoggingManager.LoggingInfo();
                loggingInfo.mType = "R";
                long currentTimeMillis = System.currentTimeMillis();
                loggingInfo.mStartTime = currentTimeMillis;
                loggingInfo.mResultTime = currentTimeMillis;
                loggingInfo.mPackageName = "";
                loggingInfo.mExtra = -1;
                semBioLoggingManager.mLastFaceRemoveLog = loggingInfo.toDumpFormat();
                semBioLoggingManager.faceAddLoggingInfo(loggingInfo);
                break;
            default:
                BiometricAuthenticator.Identifier identifier2 = this.f$0;
                int[] iArr2 = this.f$1;
                ((EnumerateConsumer) obj).onEnumerationResult(identifier2, (iArr2.length - this.f$2) - 1);
                break;
        }
    }
}
