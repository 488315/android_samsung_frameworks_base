package com.android.server.biometrics.sensors.face;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.face.Face;
import android.os.AsyncTask;
import android.util.SparseArray;
import com.android.server.biometrics.sensors.BiometricUtils;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FaceUtils implements BiometricUtils {
    public static final Object sInstanceLock = new Object();
    public static SparseArray sInstances;
    public final String mFileName;
    public final SparseArray mUserStates = new SparseArray();

    public FaceUtils(String str) {
        this.mFileName = str;
    }

    public static FaceUtils getInstance(int i, String str) {
        FaceUtils faceUtils;
        synchronized (sInstanceLock) {
            try {
                if (sInstances == null) {
                    sInstances = new SparseArray();
                }
                if (sInstances.get(i) == null) {
                    if (str == null) {
                        str = "settings_face_" + i + ".xml";
                    }
                    sInstances.put(i, new FaceUtils(str));
                }
                faceUtils = (FaceUtils) sInstances.get(i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return faceUtils;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public final void addBiometricForUser(Context context, int i, BiometricAuthenticator.Identifier identifier) {
        Face face = (Face) identifier;
        FaceUserState stateForUser = getStateForUser(context, i);
        synchronized (stateForUser) {
            stateForUser.mBiometrics.add(face);
            AsyncTask.execute(stateForUser.mWriteStateRunnable);
        }
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public final List getBiometricsForUser(Context context, int i) {
        return getStateForUser(context, i).getBiometrics();
    }

    public final FaceUserState getStateForUser(Context context, int i) {
        FaceUserState faceUserState;
        synchronized (this) {
            try {
                faceUserState = (FaceUserState) this.mUserStates.get(i);
                if (faceUserState == null) {
                    faceUserState = new FaceUserState(context, this.mFileName, i);
                    this.mUserStates.put(i, faceUserState);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return faceUserState;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public final void removeBiometricForUser(Context context, int i, int i2) {
        getStateForUser(context, i).removeBiometric(i2);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUtils
    public final void setInvalidationInProgress(Context context, int i, boolean z) {
        FaceUserState stateForUser = getStateForUser(context, i);
        synchronized (stateForUser) {
            stateForUser.mInvalidationInProgress = z;
            AsyncTask.execute(stateForUser.mWriteStateRunnable);
        }
    }
}
