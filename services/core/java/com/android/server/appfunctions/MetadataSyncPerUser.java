package com.android.server.appfunctions;

import android.app.appsearch.AppSearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.SparseArray;

import com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class MetadataSyncPerUser {
    public static final SparseArray sPerUserMetadataSyncAdapter = new SparseArray();
    public static final Object sLock = new Object();

    public static MetadataSyncAdapter getPerUserMetadataSyncAdapter(
            UserHandle userHandle,
            Context context,
            AppFunctionAgentPolicyManager appFunctionAgentPolicyManager) {
        synchronized (sLock) {
            try {
                SparseArray sparseArray = sPerUserMetadataSyncAdapter;
                MetadataSyncAdapter metadataSyncAdapter =
                        (MetadataSyncAdapter) sparseArray.get(userHandle.getIdentifier(), null);
                if (metadataSyncAdapter == null) {
                    AppSearchManager appSearchManager =
                            (AppSearchManager) context.getSystemService(AppSearchManager.class);
                    PackageManager packageManager = context.getPackageManager();
                    if (appSearchManager != null) {
                        MetadataSyncAdapter metadataSyncAdapter2 =
                                new MetadataSyncAdapter(
                                        packageManager,
                                        appSearchManager,
                                        appFunctionAgentPolicyManager);
                        sparseArray.put(userHandle.getIdentifier(), metadataSyncAdapter2);
                        return metadataSyncAdapter2;
                    }
                }
                return metadataSyncAdapter;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
