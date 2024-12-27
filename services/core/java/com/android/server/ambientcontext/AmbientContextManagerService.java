package com.android.server.ambientcontext;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.app.ambientcontext.AmbientContextEventRequest;
import android.app.ambientcontext.IAmbientContextManager;
import android.app.ambientcontext.IAmbientContextObserver;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.util.DumpUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;

import com.google.android.collect.Sets;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AmbientContextManagerService extends AbstractMasterSystemService {
    public static final Set DEFAULT_EVENT_SET = Sets.newHashSet(new Integer[] {1, 2, 3});
    public final Context mContext;
    public final Set mExistingClientRequests;
    public boolean mIsServiceEnabled;
    public boolean mIsWearableServiceEnabled;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AmbientContextManagerInternal extends IAmbientContextManager.Stub {
        public AmbientContextManagerInternal() {}

        public final int checkStatusCode(
                AmbientContextManagerPerUserService ambientContextManagerPerUserService,
                int[] iArr) {
            if (ambientContextManagerPerUserService.getServiceType()
                            == AmbientContextManagerPerUserService.ServiceType.DEFAULT
                    && !AmbientContextManagerService.this.mIsServiceEnabled) {
                Set set = AmbientContextManagerService.DEFAULT_EVENT_SET;
                Slog.d("AmbientContextManagerService", "Service not enabled.");
                return 3;
            }
            if (ambientContextManagerPerUserService.getServiceType()
                            == AmbientContextManagerPerUserService.ServiceType.WEARABLE
                    && !AmbientContextManagerService.this.mIsWearableServiceEnabled) {
                Set set2 = AmbientContextManagerService.DEFAULT_EVENT_SET;
                Slog.d("AmbientContextManagerService", "Wearable Service not available.");
                return 3;
            }
            if (!AmbientContextManagerService.m211$$Nest$mcontainsMixedEvents(
                    AmbientContextManagerService.this, iArr)) {
                return 1;
            }
            Slog.d(
                    "AmbientContextManagerService",
                    "AmbientContextEventRequest contains mixed events, this is not supported.");
            return 2;
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            Context context = AmbientContextManagerService.this.mContext;
            Set set = AmbientContextManagerService.DEFAULT_EVENT_SET;
            if (DumpUtils.checkDumpPermission(
                    context, "AmbientContextManagerService", printWriter)) {
                synchronized (AmbientContextManagerService.this.mLock) {
                    AmbientContextManagerService.this.dumpLocked(printWriter);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            new AmbientContextShellCommand(AmbientContextManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void queryServiceStatus(
                int[] iArr, String str, RemoteCallback remoteCallback) {
            Objects.requireNonNull(iArr);
            Objects.requireNonNull(str);
            Objects.requireNonNull(remoteCallback);
            Context context = AmbientContextManagerService.this.mContext;
            Set set = AmbientContextManagerService.DEFAULT_EVENT_SET;
            context.enforceCallingOrSelfPermission(
                    "android.permission.ACCESS_AMBIENT_CONTEXT_EVENT",
                    "AmbientContextManagerService");
            AmbientContextManagerService.this.assertCalledByPackageOwner(str);
            synchronized (AmbientContextManagerService.this.mLock) {
                try {
                    AmbientContextManagerService ambientContextManagerService =
                            AmbientContextManagerService.this;
                    int callingUserId = UserHandle.getCallingUserId();
                    AmbientContextManagerService.this.getClass();
                    AmbientContextManagerPerUserService
                            ambientContextManagerPerUserServiceForEventTypes =
                                    ambientContextManagerService
                                            .getAmbientContextManagerPerUserServiceForEventTypes(
                                                    callingUserId,
                                                    AmbientContextManagerService
                                                            .intArrayToIntegerSet(iArr));
                    if (ambientContextManagerPerUserServiceForEventTypes == null) {
                        Slog.w(
                                "AmbientContextManagerService",
                                "queryServiceStatus unavailable user_id: "
                                        + UserHandle.getCallingUserId());
                    } else {
                        int checkStatusCode =
                                checkStatusCode(
                                        ambientContextManagerPerUserServiceForEventTypes, iArr);
                        if (checkStatusCode == 1) {
                            ambientContextManagerPerUserServiceForEventTypes.onQueryServiceStatus(
                                    iArr, str, remoteCallback);
                        } else {
                            AmbientContextManagerPerUserService.sendStatusCallback(
                                    checkStatusCode, remoteCallback);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void registerObserver(
                AmbientContextEventRequest ambientContextEventRequest,
                final PendingIntent pendingIntent,
                final RemoteCallback remoteCallback) {
            Objects.requireNonNull(ambientContextEventRequest);
            Objects.requireNonNull(pendingIntent);
            Objects.requireNonNull(remoteCallback);
            final AmbientContextManagerPerUserService
                    ambientContextManagerPerUserServiceForEventTypes =
                            AmbientContextManagerService.this
                                    .getAmbientContextManagerPerUserServiceForEventTypes(
                                            UserHandle.getCallingUserId(),
                                            ambientContextEventRequest.getEventTypes());
            registerObserverWithCallback(
                    ambientContextEventRequest,
                    pendingIntent.getCreatorPackage(),
                    new IAmbientContextObserver
                            .Stub() { // from class:
                                      // com.android.server.ambientcontext.AmbientContextManagerService.AmbientContextManagerInternal.1
                        public final void onEvents(List list) {
                            AmbientContextManagerPerUserService
                                    ambientContextManagerPerUserService =
                                            AmbientContextManagerPerUserService.this;
                            PendingIntent pendingIntent2 = pendingIntent;
                            ambientContextManagerPerUserService.getClass();
                            Intent intent = new Intent();
                            intent.putExtra(
                                    "android.app.ambientcontext.extra.AMBIENT_CONTEXT_EVENTS",
                                    new ArrayList(list));
                            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
                            try {
                                pendingIntent2.send(
                                        ambientContextManagerPerUserService.mMaster.getContext(),
                                        0,
                                        intent,
                                        null,
                                        null,
                                        null,
                                        makeBasic.toBundle());
                                Slog.i(
                                        "AmbientContextManagerPerUserService",
                                        "Sending PendingIntent to "
                                                + pendingIntent2.getCreatorPackage()
                                                + ": "
                                                + list);
                            } catch (PendingIntent.CanceledException unused) {
                                Slog.w(
                                        "AmbientContextManagerPerUserService",
                                        "Couldn't deliver pendingIntent:" + pendingIntent2);
                            }
                        }

                        public final void onRegistrationComplete(int i) {
                            AmbientContextManagerPerUserService
                                    ambientContextManagerPerUserService =
                                            AmbientContextManagerPerUserService.this;
                            RemoteCallback remoteCallback2 = remoteCallback;
                            ambientContextManagerPerUserService.getClass();
                            AmbientContextManagerPerUserService.sendStatusCallback(
                                    i, remoteCallback2);
                        }
                    });
        }

        public final void registerObserverWithCallback(
                AmbientContextEventRequest ambientContextEventRequest,
                String str,
                IAmbientContextObserver iAmbientContextObserver) {
            Set set = AmbientContextManagerService.DEFAULT_EVENT_SET;
            Slog.i(
                    "AmbientContextManagerService",
                    "AmbientContextManagerService registerObserverWithCallback.");
            Objects.requireNonNull(ambientContextEventRequest);
            Objects.requireNonNull(str);
            Objects.requireNonNull(iAmbientContextObserver);
            AmbientContextManagerService.this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.ACCESS_AMBIENT_CONTEXT_EVENT",
                    "AmbientContextManagerService");
            AmbientContextManagerService.this.assertCalledByPackageOwner(str);
            AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes =
                    AmbientContextManagerService.this
                            .getAmbientContextManagerPerUserServiceForEventTypes(
                                    UserHandle.getCallingUserId(),
                                    ambientContextEventRequest.getEventTypes());
            if (ambientContextManagerPerUserServiceForEventTypes == null) {
                Slog.w(
                        "AmbientContextManagerService",
                        "onRegisterObserver unavailable user_id: " + UserHandle.getCallingUserId());
                return;
            }
            Set eventTypes = ambientContextEventRequest.getEventTypes();
            int[] iArr = new int[eventTypes.size()];
            Iterator it = eventTypes.iterator();
            int i = 0;
            while (it.hasNext()) {
                iArr[i] = ((Integer) it.next()).intValue();
                i++;
            }
            int checkStatusCode =
                    checkStatusCode(ambientContextManagerPerUserServiceForEventTypes, iArr);
            if (checkStatusCode == 1) {
                ambientContextManagerPerUserServiceForEventTypes.onRegisterObserver(
                        ambientContextEventRequest, str, iAmbientContextObserver);
            } else {
                AmbientContextManagerPerUserService.completeRegistration(
                        iAmbientContextObserver, checkStatusCode);
            }
        }

        public final void startConsentActivity(int[] iArr, String str) {
            ComponentName unflattenFromString;
            Objects.requireNonNull(iArr);
            Objects.requireNonNull(str);
            AmbientContextManagerService.this.assertCalledByPackageOwner(str);
            Context context = AmbientContextManagerService.this.mContext;
            Set set = AmbientContextManagerService.DEFAULT_EVENT_SET;
            context.enforceCallingOrSelfPermission(
                    "android.permission.ACCESS_AMBIENT_CONTEXT_EVENT",
                    "AmbientContextManagerService");
            if (AmbientContextManagerService.m211$$Nest$mcontainsMixedEvents(
                    AmbientContextManagerService.this, iArr)) {
                Slog.d(
                        "AmbientContextManagerService",
                        "AmbientContextEventRequest contains mixed events, this is not supported.");
                return;
            }
            AmbientContextManagerService ambientContextManagerService =
                    AmbientContextManagerService.this;
            int callingUserId = UserHandle.getCallingUserId();
            AmbientContextManagerService.this.getClass();
            AmbientContextManagerPerUserService ambientContextManagerPerUserServiceForEventTypes =
                    ambientContextManagerService
                            .getAmbientContextManagerPerUserServiceForEventTypes(
                                    callingUserId,
                                    AmbientContextManagerService.intArrayToIntegerSet(iArr));
            if (ambientContextManagerPerUserServiceForEventTypes == null) {
                Slog.w(
                        "AmbientContextManagerService",
                        "startConsentActivity unavailable user_id: "
                                + UserHandle.getCallingUserId());
                return;
            }
            Slog.d(
                    "AmbientContextManagerPerUserService",
                    "Opening consent activity of " + Arrays.toString(iArr) + " for " + str);
            try {
                ParceledListSlice recentTasks =
                        ActivityTaskManager.getService()
                                .getRecentTasks(
                                        1,
                                        0,
                                        ambientContextManagerPerUserServiceForEventTypes.mUserId);
                if (recentTasks == null || recentTasks.getList().isEmpty()) {
                    Slog.e("AmbientContextManagerPerUserService", "Recent task list is empty!");
                    return;
                }
                ActivityManager.RecentTaskInfo recentTaskInfo =
                        (ActivityManager.RecentTaskInfo) recentTasks.getList().get(0);
                if (!str.equals(recentTaskInfo.topActivityInfo.packageName)) {
                    Slog.e(
                            "AmbientContextManagerPerUserService",
                            "Recent task package name: "
                                    + recentTaskInfo.topActivityInfo.packageName
                                    + " doesn't match with client package name: "
                                    + str);
                    return;
                }
                AbstractMasterSystemService abstractMasterSystemService =
                        ambientContextManagerPerUserServiceForEventTypes.mMaster;
                String string =
                        abstractMasterSystemService
                                .getContext()
                                .getResources()
                                .getString(
                                        ambientContextManagerPerUserServiceForEventTypes
                                                .getConsentComponentConfig());
                if (TextUtils.isEmpty(string)) {
                    unflattenFromString = null;
                } else {
                    Slog.i(
                            "AmbientContextManagerPerUserService",
                            "Consent component name: " + string);
                    unflattenFromString = ComponentName.unflattenFromString(string);
                }
                if (unflattenFromString == null) {
                    Slog.e("AmbientContextManagerPerUserService", "Consent component not found!");
                    return;
                }
                Slog.d(
                        "AmbientContextManagerPerUserService",
                        "Starting consent activity for ".concat(str));
                Intent intent = new Intent();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        Context context2 = abstractMasterSystemService.getContext();
                        String string2 =
                                context2.getResources()
                                        .getString(
                                                ambientContextManagerPerUserServiceForEventTypes
                                                        .getAmbientContextPackageNameExtraKeyConfig());
                        String string3 =
                                context2.getResources()
                                        .getString(
                                                ambientContextManagerPerUserServiceForEventTypes
                                                        .getAmbientContextEventArrayExtraKeyConfig());
                        intent.setComponent(unflattenFromString);
                        if (string2 != null) {
                            intent.putExtra(string2, str);
                        } else {
                            Slog.d(
                                    "AmbientContextManagerPerUserService",
                                    "Missing packageNameExtraKey for consent activity");
                        }
                        if (string3 != null) {
                            intent.putExtra(string3, iArr);
                        } else {
                            Slog.d(
                                    "AmbientContextManagerPerUserService",
                                    "Missing eventArrayExtraKey for consent activity");
                        }
                        ActivityOptions makeBasic = ActivityOptions.makeBasic();
                        makeBasic.setLaunchTaskId(recentTaskInfo.taskId);
                        context2.startActivityAsUser(
                                intent, makeBasic.toBundle(), context2.getUser());
                    } catch (ActivityNotFoundException unused) {
                        Slog.e(
                                "AmbientContextManagerPerUserService",
                                "unable to start consent activity");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (RemoteException unused2) {
                Slog.e("AmbientContextManagerPerUserService", "Failed to query recent tasks!");
            }
        }

        public final void unregisterObserver(String str) {
            unregisterObserver_enforcePermission();
            AmbientContextManagerService.this.assertCalledByPackageOwner(str);
            synchronized (AmbientContextManagerService.this.mLock) {
                try {
                    for (ClientRequest clientRequest :
                            AmbientContextManagerService.this.mExistingClientRequests) {
                        if (clientRequest != null && clientRequest.mPackageName.equals(str)) {
                            AmbientContextManagerPerUserService
                                    ambientContextManagerPerUserServiceForEventTypes =
                                            AmbientContextManagerService.this
                                                    .getAmbientContextManagerPerUserServiceForEventTypes(
                                                            UserHandle.getCallingUserId(),
                                                            clientRequest.mRequest.getEventTypes());
                            if (ambientContextManagerPerUserServiceForEventTypes != null) {
                                synchronized (
                                        ambientContextManagerPerUserServiceForEventTypes.mLock) {
                                    ambientContextManagerPerUserServiceForEventTypes.stopDetection(
                                            str);
                                    AmbientContextManagerService ambientContextManagerService =
                                            (AmbientContextManagerService)
                                                    ambientContextManagerPerUserServiceForEventTypes
                                                            .mMaster;
                                    int i =
                                            ambientContextManagerPerUserServiceForEventTypes
                                                    .mUserId;
                                    ambientContextManagerService.getClass();
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            "Remove client: ", str, "AmbientContextManagerService");
                                    synchronized (
                                            ambientContextManagerService.mExistingClientRequests) {
                                        ambientContextManagerService.mExistingClientRequests
                                                .removeAll(
                                                        ambientContextManagerService
                                                                .findExistingRequests(i, str));
                                    }
                                }
                            } else {
                                Set set = AmbientContextManagerService.DEFAULT_EVENT_SET;
                                Slog.w(
                                        "AmbientContextManagerService",
                                        "onUnregisterObserver unavailable user_id: "
                                                + UserHandle.getCallingUserId());
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClientRequest {
        public final IAmbientContextObserver mObserver;
        public final String mPackageName;
        public final AmbientContextEventRequest mRequest;
        public final int mUserId;

        public ClientRequest(
                int i,
                AmbientContextEventRequest ambientContextEventRequest,
                String str,
                IAmbientContextObserver iAmbientContextObserver) {
            this.mUserId = i;
            this.mRequest = ambientContextEventRequest;
            this.mPackageName = str;
            this.mObserver = iAmbientContextObserver;
        }

        public final boolean hasUserIdAndPackageName(int i, String str) {
            return i == this.mUserId && str.equals(this.mPackageName);
        }
    }

    /* renamed from: -$$Nest$mcontainsMixedEvents, reason: not valid java name */
    public static boolean m211$$Nest$mcontainsMixedEvents(
            AmbientContextManagerService ambientContextManagerService, int[] iArr) {
        ambientContextManagerService.getClass();
        Integer[] numArr = new Integer[iArr.length];
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            numArr[i2] = Integer.valueOf(iArr[i]);
            i++;
            i2++;
        }
        if (isWearableEventTypesOnly(new HashSet(Arrays.asList(numArr)))) {
            return false;
        }
        for (int i3 : iArr) {
            if (!DEFAULT_EVENT_SET.contains(Integer.valueOf(i3))) {
                Slog.w(
                        "AmbientContextManagerService",
                        "Received mixed event types, this is not supported.");
                return true;
            }
        }
        return false;
    }

    public AmbientContextManagerService(Context context) {
        super(context, new FrameworkResourcesServiceNameResolver(context), null, 68);
        this.mContext = context;
        this.mExistingClientRequests = ConcurrentHashMap.newKeySet();
    }

    public static Set intArrayToIntegerSet(int[] iArr) {
        HashSet hashSet = new HashSet();
        for (int i : iArr) {
            hashSet.add(Integer.valueOf(i));
        }
        return hashSet;
    }

    public static boolean isWearableEventTypesOnly(Set set) {
        if (set.isEmpty()) {
            Slog.d("AmbientContextManagerService", "empty event types.");
            return false;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (((Integer) it.next()).intValue() < 100000) {
                Slog.d("AmbientContextManagerService", "Not all events types are wearable events.");
                return false;
            }
        }
        Slog.d("AmbientContextManagerService", "only wearable events.");
        return true;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext()
                .enforceCallingPermission(
                        "android.permission.ACCESS_AMBIENT_CONTEXT_EVENT",
                        "AmbientContextManagerService");
    }

    public final Set findExistingRequests(int i, String str) {
        ArraySet arraySet = new ArraySet();
        for (ClientRequest clientRequest : this.mExistingClientRequests) {
            if (clientRequest.hasUserIdAndPackageName(i, str)) {
                arraySet.add(clientRequest);
            }
        }
        return arraySet;
    }

    public final AmbientContextManagerPerUserService
            getAmbientContextManagerPerUserServiceForEventTypes(int i, Set set) {
        return isWearableEventTypesOnly(set)
                ? getServiceForType(i, AmbientContextManagerPerUserService.ServiceType.WEARABLE)
                : getServiceForType(i, AmbientContextManagerPerUserService.ServiceType.DEFAULT);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 30000;
    }

    public final AmbientContextManagerPerUserService getServiceForType(
            int i, AmbientContextManagerPerUserService.ServiceType serviceType) {
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "getServiceForType with userid: ", " service type: ");
        m.append(serviceType.name());
        Slog.d("AmbientContextManagerService", m.toString());
        synchronized (this.mLock) {
            try {
                List<AmbientContextManagerPerUserService> serviceListForUserLocked =
                        getServiceListForUserLocked(i);
                StringBuilder sb = new StringBuilder("Services that are available: ");
                sb.append(
                        serviceListForUserLocked == null
                                ? "null services"
                                : serviceListForUserLocked.size() + " number of services");
                Slog.d("AmbientContextManagerService", sb.toString());
                if (serviceListForUserLocked == null) {
                    return null;
                }
                for (AmbientContextManagerPerUserService ambientContextManagerPerUserService :
                        serviceListForUserLocked) {
                    if (ambientContextManagerPerUserService.getServiceType() == serviceType) {
                        return ambientContextManagerPerUserService;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void newClientAdded(
            int i,
            AmbientContextEventRequest ambientContextEventRequest,
            String str,
            IAmbientContextObserver iAmbientContextObserver) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "New client added: ", str, "AmbientContextManagerService");
        synchronized (this.mExistingClientRequests) {
            this.mExistingClientRequests.removeAll(findExistingRequests(i, str));
            this.mExistingClientRequests.add(
                    new ClientRequest(i, ambientContextEventRequest, str, iAmbientContextObserver));
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final List newServiceListLocked(int i, String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            Slog.i(
                    "AmbientContextManagerService",
                    "serviceNames sent in newServiceListLocked is null, or empty");
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        int length = strArr.length;
        AmbientContextManagerPerUserService.ServiceType serviceType =
                AmbientContextManagerPerUserService.ServiceType.WEARABLE;
        AmbientContextManagerPerUserService.ServiceType serviceType2 =
                AmbientContextManagerPerUserService.ServiceType.DEFAULT;
        Object obj = this.mLock;
        if (length == 2) {
            String str = strArr[0];
            String string =
                    this.mContext.getResources().getString(R.string.data_usage_wifi_limit_title);
            if (string == null || !string.equals(str)) {
                String str2 = strArr[1];
                String string2 =
                        this.mContext
                                .getResources()
                                .getString(R.string.device_storage_monitor_notification_channel);
                if (string2 == null || !string2.equals(str2)) {
                    Slog.i(
                            "AmbientContextManagerService",
                            "Not using default services, services provided for testing should be"
                                + " exactly two services.");
                    arrayList.add(
                            new DefaultAmbientContextManagerPerUserService(
                                    this, obj, i, strArr[0]));
                    arrayList.add(
                            new WearableAmbientContextManagerPerUserService(
                                    this, obj, i, strArr[1]));
                    return arrayList;
                }
            }
        }
        if (strArr.length > 2) {
            Slog.i(
                    "AmbientContextManagerService",
                    "Incorrect number of services provided for testing.");
        }
        for (String str3 : strArr) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "newServicesListLocked with service name: ",
                    str3,
                    "AmbientContextManagerService");
            String string3 =
                    this.mContext
                            .getResources()
                            .getString(R.string.device_storage_monitor_notification_channel);
            if (((string3 == null || !string3.equals(str3)) ? serviceType2 : serviceType)
                    == serviceType) {
                arrayList.add(new WearableAmbientContextManagerPerUserService(this, obj, i, str3));
            } else {
                arrayList.add(new DefaultAmbientContextManagerPerUserService(this, obj, i, str3));
            }
        }
        return arrayList;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final /* bridge */ /* synthetic */ AbstractPerUserSystemService newServiceLocked(
            int i, boolean z) {
        return null;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService,
              // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            DeviceConfig.addOnPropertiesChangedListener(
                    "ambient_context_manager_service",
                    getContext().getMainExecutor(),
                    new DeviceConfig
                            .OnPropertiesChangedListener() { // from class:
                                                             // com.android.server.ambientcontext.AmbientContextManagerService$$ExternalSyntheticLambda0
                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            AmbientContextManagerService ambientContextManagerService =
                                    AmbientContextManagerService.this;
                            ambientContextManagerService.getClass();
                            if (properties.getKeyset().contains("service_enabled")) {
                                ambientContextManagerService.mIsServiceEnabled =
                                        DeviceConfig.getBoolean(
                                                "ambient_context_manager_service",
                                                "service_enabled",
                                                true);
                                ambientContextManagerService.mIsWearableServiceEnabled =
                                        DeviceConfig.getBoolean(
                                                "wearable_sensing", "service_enabled", true);
                            }
                        }
                    });
            this.mIsServiceEnabled =
                    DeviceConfig.getBoolean(
                            "ambient_context_manager_service", "service_enabled", true);
            this.mIsWearableServiceEnabled =
                    DeviceConfig.getBoolean("wearable_sensing", "service_enabled", true);
        }
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageRestartedLocked(int i) {
        Slog.d(
                "AmbientContextManagerService",
                "Restoring remote request. Reason: Service package restarted.");
        restorePreviouslyEnabledClients(i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServicePackageUpdatedLocked(int i) {
        Slog.d(
                "AmbientContextManagerService",
                "Restoring remote request. Reason: Service package updated.");
        restorePreviouslyEnabledClients(i);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void onServiceRemoved(
            AbstractPerUserSystemService abstractPerUserSystemService, int i) {
        AmbientContextManagerPerUserService ambientContextManagerPerUserService =
                (AmbientContextManagerPerUserService) abstractPerUserSystemService;
        Slog.d("AmbientContextManagerService", "onServiceRemoved");
        ambientContextManagerPerUserService.getClass();
        Slog.d(
                "AmbientContextManagerPerUserService",
                "Trying to cancel the remote request. Reason: Service destroyed.");
        RemoteAmbientDetectionService remoteService =
                ambientContextManagerPerUserService.getRemoteService();
        if (remoteService != null) {
            synchronized (ambientContextManagerPerUserService.mLock) {
                remoteService.unbind();
                ambientContextManagerPerUserService.clearRemoteService();
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("ambient_context", new AmbientContextManagerInternal());
    }

    public final void queryServiceStatus(
            int i, String str, int[] iArr, RemoteCallback remoteCallback) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", "AmbientContextManagerService");
        synchronized (this.mLock) {
            try {
                AmbientContextManagerPerUserService
                        ambientContextManagerPerUserServiceForEventTypes =
                                getAmbientContextManagerPerUserServiceForEventTypes(
                                        i, intArrayToIntegerSet(iArr));
                if (ambientContextManagerPerUserServiceForEventTypes != null) {
                    ambientContextManagerPerUserServiceForEventTypes.onQueryServiceStatus(
                            iArr, str, remoteCallback);
                } else {
                    Slog.i(
                            "AmbientContextManagerService",
                            "query service not available for user_id: " + i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void restorePreviouslyEnabledClients(int i) {
        synchronized (this.mLock) {
            try {
                for (AmbientContextManagerPerUserService ambientContextManagerPerUserService :
                        getServiceListForUserLocked(i)) {
                    for (ClientRequest clientRequest : this.mExistingClientRequests) {
                        if (clientRequest.mUserId == i) {
                            Slog.d(
                                    "AmbientContextManagerService",
                                    "Restoring detection for " + clientRequest.mPackageName);
                            ambientContextManagerPerUserService.startDetection(
                                    clientRequest.mRequest,
                                    clientRequest.mPackageName,
                                    clientRequest.mObserver);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startDetection(
            int i,
            AmbientContextEventRequest ambientContextEventRequest,
            String str,
            AmbientContextShellCommand.TestableCallbackInternal.AnonymousClass1 anonymousClass1) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.ACCESS_AMBIENT_CONTEXT_EVENT", "AmbientContextManagerService");
        synchronized (this.mLock) {
            try {
                AmbientContextManagerPerUserService
                        ambientContextManagerPerUserServiceForEventTypes =
                                getAmbientContextManagerPerUserServiceForEventTypes(
                                        i, ambientContextEventRequest.getEventTypes());
                if (ambientContextManagerPerUserServiceForEventTypes != null) {
                    ambientContextManagerPerUserServiceForEventTypes.startDetection(
                            ambientContextEventRequest, str, anonymousClass1);
                } else {
                    Slog.i(
                            "AmbientContextManagerService",
                            "service not available for user_id: " + i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
