package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class C2paManifestsCallback extends IC2paManifestsCallback.Stub {
    public abstract /* synthetic */ void onError(String str) throws RemoteException;

    @Override // com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback
    public void onPfdCreation(ParcelFileDescriptor parcelFileDescriptor, boolean z) throws RemoteException {
        try {
            try {
                try {
                    FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                    try {
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        try {
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            try {
                                onResult((String) bufferedReader.lines().collect(Collectors.joining("\n")), z, true);
                                bufferedReader.close();
                                inputStreamReader.close();
                                fileInputStream.close();
                                parcelFileDescriptor.close();
                            } finally {
                            }
                        } finally {
                        }
                    } catch (Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Exception unused) {
                    onError(C2paError.PFD_READ_ERROR.getErrString());
                    Log.e("C2paClient", "Error while reading c2pa manifest from pfd");
                    parcelFileDescriptor.close();
                }
            } catch (IOException | SecurityException e) {
                Log.e("C2paClient", "Error while closing pfd");
                onError(C2paError.PFD_READ_ERROR.getErrString());
                e.printStackTrace();
            }
        } catch (Throwable th3) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException | SecurityException e2) {
                Log.e("C2paClient", "Error while closing pfd");
                onError(C2paError.PFD_READ_ERROR.getErrString());
                e2.printStackTrace();
            }
            throw th3;
        }
    }

    public abstract /* synthetic */ void onResult(String str, boolean z, boolean z2) throws RemoteException;
}
