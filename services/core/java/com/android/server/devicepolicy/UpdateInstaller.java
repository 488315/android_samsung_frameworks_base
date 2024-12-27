package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.StartInstallingUpdateCallback;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class UpdateInstaller {
    public final StartInstallingUpdateCallback mCallback;
    public final DevicePolicyConstants mConstants;
    public final Context mContext;
    public File mCopiedUpdateFile;
    public final DevicePolicyManagerService.Injector mInjector;
    public final ParcelFileDescriptor mUpdateFileDescriptor;

    public UpdateInstaller(
            Context context,
            ParcelFileDescriptor parcelFileDescriptor,
            StartInstallingUpdateCallback startInstallingUpdateCallback,
            DevicePolicyManagerService.Injector injector,
            DevicePolicyConstants devicePolicyConstants) {
        this.mContext = context;
        this.mCallback = startInstallingUpdateCallback;
        this.mUpdateFileDescriptor = parcelFileDescriptor;
        this.mInjector = injector;
        this.mConstants = devicePolicyConstants;
    }

    public abstract void installUpdateInThread();

    public final void notifyCallbackOnError(int i, String str) {
        File file = this.mCopiedUpdateFile;
        if (file != null && file.exists()) {
            this.mCopiedUpdateFile.delete();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("userId", this.mContext.getUserId());
        DevicePolicyEventLogger.createEvent(74).setInt(i).setKnoxBundleValue(bundle).write();
        try {
            this.mCallback.onStartInstallingUpdateError(i, str);
        } catch (RemoteException e) {
            Log.d("UpdateInstaller", "Error while calling callback", e);
        }
    }
}
