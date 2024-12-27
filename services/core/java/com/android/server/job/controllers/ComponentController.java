package com.android.server.job.controllers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArrayMap;
import android.util.proto.ProtoOutputStream;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ComponentController extends StateController {
    public static final boolean DEBUG;
    public final AnonymousClass1 mBroadcastReceiver;
    public final ComponentStateUpdateFunctor mComponentStateUpdateFunctor;
    public final SparseArrayMap mServiceProcessCache;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ComponentStateUpdateFunctor implements Consumer {
        public final ArraySet mChangedJobs = new ArraySet();

        public ComponentStateUpdateFunctor() {
        }

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            JobStatus jobStatus = (JobStatus) obj;
            if (ComponentController.this.updateComponentEnabledStateLocked(jobStatus)) {
                this.mChangedJobs.add(jobStatus);
            }
        }
    }

    /* renamed from: -$$Nest$mupdateComponentStateForPackage, reason: not valid java name */
    public static void m623$$Nest$mupdateComponentStateForPackage(ComponentController componentController, final int i, final String str) {
        synchronized (componentController.mLock) {
            componentController.clearComponentsForPackageLocked(i, str);
            componentController.updateComponentStatesLocked(new Predicate() { // from class: com.android.server.job.controllers.ComponentController$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    JobStatus jobStatus = (JobStatus) obj;
                    return UserHandle.getUserId(jobStatus.callingUid) == i && jobStatus.job.getService().getPackageName().equals(str);
                }
            });
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Component", 3);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.job.controllers.ComponentController$1] */
    public ComponentController(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.job.controllers.ComponentController.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                char c;
                String schemeSpecificPart;
                String action = intent.getAction();
                if (action == null) {
                    Slog.wtf("JobScheduler.Component", "Intent action was null");
                    return;
                }
                switch (action.hashCode()) {
                    case -742246786:
                        if (action.equals("android.intent.action.USER_STOPPED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 172491798:
                        if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 833559602:
                        if (action.equals("android.intent.action.USER_UNLOCKED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544582882:
                        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                    case 2:
                        final int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                        ComponentController componentController = ComponentController.this;
                        synchronized (componentController.mLock) {
                            componentController.mServiceProcessCache.delete(intExtra);
                            componentController.updateComponentStatesLocked(new Predicate() { // from class: com.android.server.job.controllers.ComponentController$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return UserHandle.getUserId(((JobStatus) obj).callingUid) == intExtra;
                                }
                            });
                        }
                        return;
                    case 1:
                        Uri data = intent.getData();
                        schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                        String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                        if (schemeSpecificPart == null || stringArrayExtra == null || stringArrayExtra.length <= 0) {
                            return;
                        }
                        ComponentController.m623$$Nest$mupdateComponentStateForPackage(ComponentController.this, UserHandle.getUserId(intent.getIntExtra("android.intent.extra.UID", -1)), schemeSpecificPart);
                        return;
                    case 3:
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Uri data2 = intent.getData();
                            schemeSpecificPart = data2 != null ? data2.getSchemeSpecificPart() : null;
                            if (schemeSpecificPart != null) {
                                ComponentController.m623$$Nest$mupdateComponentStateForPackage(ComponentController.this, UserHandle.getUserId(intent.getIntExtra("android.intent.extra.UID", -1)), schemeSpecificPart);
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.mServiceProcessCache = new SparseArrayMap();
        this.mComponentStateUpdateFunctor = new ComponentStateUpdateFunctor();
    }

    public final void clearComponentsForPackageLocked(int i, String str) {
        int indexOfKey = this.mServiceProcessCache.indexOfKey(i);
        for (int numElementsForKey = this.mServiceProcessCache.numElementsForKey(i) - 1; numElementsForKey >= 0; numElementsForKey--) {
            ComponentName componentName = (ComponentName) this.mServiceProcessCache.keyAt(indexOfKey, numElementsForKey);
            if (componentName.getPackageName().equals(str)) {
                this.mServiceProcessCache.delete(i, componentName);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(IndentingPrintWriter indentingPrintWriter, JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5) {
        for (int i = 0; i < this.mServiceProcessCache.numMaps(); i++) {
            int keyAt = this.mServiceProcessCache.keyAt(i);
            for (int i2 = 0; i2 < this.mServiceProcessCache.numElementsForKey(keyAt); i2++) {
                ComponentName componentName = (ComponentName) this.mServiceProcessCache.keyAt(i, i2);
                indentingPrintWriter.print(keyAt);
                indentingPrintWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
                indentingPrintWriter.print(componentName);
                indentingPrintWriter.print(": ");
                indentingPrintWriter.print((String) this.mServiceProcessCache.valueAt(i, i2));
                indentingPrintWriter.println();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(ProtoOutputStream protoOutputStream, JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5) {
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        updateComponentEnabledStateLocked(jobStatus);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onAppRemovedLocked(int i, String str) {
        clearComponentsForPackageLocked(UserHandle.getUserId(i), str);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUserRemovedLocked(int i) {
        this.mServiceProcessCache.delete(i);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void startTrackingLocked() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(this.mBroadcastReceiver, userHandle, intentFilter, null, null);
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, userHandle, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.USER_UNLOCKED", "android.intent.action.USER_STOPPED"), null, null);
    }

    public final boolean updateComponentEnabledStateLocked(JobStatus jobStatus) {
        ServiceInfo serviceInfo;
        String str;
        ComponentName service = jobStatus.job.getService();
        int userId = UserHandle.getUserId(jobStatus.callingUid);
        if (this.mServiceProcessCache.contains(userId, service)) {
            str = (String) this.mServiceProcessCache.get(userId, service);
        } else {
            try {
                serviceInfo = this.mContext.createContextAsUser(UserHandle.of(userId), 0).getPackageManager().getServiceInfo(service, 268435456);
            } catch (PackageManager.NameNotFoundException unused) {
                if (this.mService.areUsersStartedLocked(jobStatus)) {
                    Slog.e("JobScheduler.Component", "Job exists for non-existent package: " + service.getPackageName());
                }
                serviceInfo = null;
            }
            String str2 = serviceInfo != null ? serviceInfo.processName : null;
            this.mServiceProcessCache.add(userId, service, str2);
            str = str2;
        }
        if (DEBUG && str == null) {
            Slog.v("JobScheduler.Component", jobStatus.toShortString() + " component not present");
        }
        String str3 = jobStatus.serviceProcessName;
        jobStatus.serviceProcessName = str;
        return !Objects.equals(str3, str);
    }

    public final void updateComponentStatesLocked(Predicate predicate) {
        ComponentStateUpdateFunctor componentStateUpdateFunctor = this.mComponentStateUpdateFunctor;
        componentStateUpdateFunctor.mChangedJobs.clear();
        this.mService.mJobs.forEachJob(predicate, componentStateUpdateFunctor);
        if (componentStateUpdateFunctor.mChangedJobs.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(componentStateUpdateFunctor.mChangedJobs);
        }
    }
}
