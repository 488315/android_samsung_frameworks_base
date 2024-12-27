package com.android.server.resources;

import android.app.IApplicationThread;
import android.content.Context;
import android.content.res.IResourcesManager;
import android.content.res.ResourceTimer;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import com.android.server.SystemService;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.ProcessRecord;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ResourcesManagerService extends SystemService {
    public ActivityManagerService mActivityManagerService;
    public final AnonymousClass1 mService;

    public ResourcesManagerService(Context context) {
        super(context);
        publishBinderService("resources", new IResourcesManager.Stub() { // from class: com.android.server.resources.ResourcesManagerService.1
            public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
                try {
                    ParcelFileDescriptor dup = ParcelFileDescriptor.dup(fileDescriptor);
                    try {
                        ResourcesManagerService.this.mActivityManagerService.dumpAllResources(dup, printWriter);
                        if (dup != null) {
                            dup.close();
                        }
                    } finally {
                    }
                } catch (Exception e) {
                    printWriter.println("Exception while trying to dump all resources: " + e.getMessage());
                    e.printStackTrace(printWriter);
                }
            }

            public final boolean dumpResources(String str, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) {
                IApplicationThread iApplicationThread;
                int callingUid = Binder.getCallingUid();
                if (callingUid != 0 && callingUid != 2000) {
                    remoteCallback.sendResult((Bundle) null);
                    throw new SecurityException("dump should only be called by shell");
                }
                ActivityManagerService activityManagerService = ResourcesManagerService.this.mActivityManagerService;
                activityManagerService.getClass();
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        ProcessRecord findProcessLOSP = activityManagerService.findProcessLOSP(-2, str, "dumpResources");
                        if (findProcessLOSP == null || (iApplicationThread = findProcessLOSP.mThread) == null) {
                            throw new IllegalArgumentException("Unknown process: " + str);
                        }
                        iApplicationThread.dumpResources(parcelFileDescriptor, remoteCallback);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return true;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final int handleShellCommand(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, String[] strArr) {
                return new ResourcesManagerShellCommand(this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
            }
        });
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        ResourceTimer.start();
    }
}
