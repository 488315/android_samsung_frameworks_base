package com.android.server.translation;

import android.R;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.service.translation.ITranslationService;
import android.service.translation.TranslationServiceInfo;
import android.util.Slog;
import android.view.translation.ITranslationManager;
import android.view.translation.TranslationContext;
import android.view.translation.TranslationSpec;
import android.view.translation.UiTranslationSpec;

import com.android.internal.infra.ServiceConnector;
import com.android.internal.os.IResultReceiver;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.SyncResultReceiver;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.android.server.infra.ServiceNameBaseResolver;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TranslationManagerService extends AbstractMasterSystemService {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TranslationManagerServiceStub extends ITranslationManager.Stub {
        public TranslationManagerServiceStub() {}

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    TranslationManagerService.this.getContext(),
                    "TranslationManagerService",
                    printWriter)) {
                synchronized (TranslationManagerService.this.mLock) {
                    try {
                        TranslationManagerService.this.dumpLocked(printWriter);
                        TranslationManagerServiceImpl translationManagerServiceImpl =
                                (TranslationManagerServiceImpl)
                                        TranslationManagerService.this.getServiceForUserLocked(
                                                UserHandle.getCallingUserId());
                        if (translationManagerServiceImpl != null) {
                            translationManagerServiceImpl.dumpLocked(fileDescriptor, printWriter);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void getServiceSettingsActivity(IResultReceiver iResultReceiver, int i) {
            TranslationManagerServiceImpl translationManagerServiceImpl;
            String settingsActivity;
            synchronized (TranslationManagerService.this.mLock) {
                translationManagerServiceImpl =
                        (TranslationManagerServiceImpl)
                                TranslationManagerService.this.getServiceForUserLocked(i);
            }
            if (translationManagerServiceImpl == null) {
                try {
                    iResultReceiver.send(2, (Bundle) null);
                    return;
                } catch (RemoteException e) {
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            "Unable to send getServiceSettingsActivity(): ",
                            e,
                            "TranslationManagerService");
                    return;
                }
            }
            TranslationServiceInfo translationServiceInfo =
                    translationManagerServiceImpl.mTranslationServiceInfo;
            ComponentName componentName =
                    (translationServiceInfo == null
                                    || (settingsActivity =
                                                    translationServiceInfo.getSettingsActivity())
                                            == null)
                            ? null
                            : new ComponentName(
                                    translationManagerServiceImpl.mTranslationServiceInfo
                                            .getServiceInfo()
                                            .packageName,
                                    settingsActivity);
            if (componentName == null) {
                try {
                    iResultReceiver.send(1, (Bundle) null);
                } catch (RemoteException e2) {
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            "Unable to send getServiceSettingsActivity(): ",
                            e2,
                            "TranslationManagerService");
                }
            }
            Intent intent = new Intent();
            intent.setComponent(componentName);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    iResultReceiver.send(
                            1,
                            SyncResultReceiver.bundleFor(
                                    PendingIntent.getActivityAsUser(
                                            TranslationManagerService.this.getContext(),
                                            0,
                                            intent,
                                            67108864,
                                            null,
                                            new UserHandle(i))));
                } catch (RemoteException e3) {
                    Slog.w(
                            "TranslationManagerService",
                            "Unable to send getServiceSettingsActivity(): " + e3);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void onSessionCreated(
                final TranslationContext translationContext,
                final int i,
                final IResultReceiver iResultReceiver,
                int i2) {
            synchronized (TranslationManagerService.this.mLock) {
                try {
                    TranslationManagerServiceImpl translationManagerServiceImpl =
                            (TranslationManagerServiceImpl)
                                    TranslationManagerService.this.getServiceForUserLocked(i2);
                    if (translationManagerServiceImpl == null
                            || (!TranslationManagerService.m975$$Nest$misDefaultServiceLocked(
                                            TranslationManagerService.this, i2)
                                    && !TranslationManagerService
                                            .m974$$Nest$misCalledByServiceAppLocked(
                                                    TranslationManagerService.this,
                                                    i2,
                                                    "onSessionCreated"))) {
                        Slog.v(
                                "TranslationManagerService",
                                "onSessionCreated(): no service for " + i2);
                        iResultReceiver.send(2, (Bundle) null);
                    }
                    RemoteTranslationService ensureRemoteServiceLocked =
                            translationManagerServiceImpl.ensureRemoteServiceLocked();
                    if (ensureRemoteServiceLocked != null) {
                        ensureRemoteServiceLocked.run(
                                new ServiceConnector
                                        .VoidJob() { // from class:
                                                     // com.android.server.translation.RemoteTranslationService$$ExternalSyntheticLambda1
                                    public final void runNoResult(Object obj) {
                                        TranslationContext translationContext2 = translationContext;
                                        int i3 = i;
                                        IResultReceiver iResultReceiver2 = iResultReceiver;
                                        int i4 = RemoteTranslationService.$r8$clinit;
                                        ((ITranslationService) obj)
                                                .onCreateTranslationSession(
                                                        translationContext2, i3, iResultReceiver2);
                                    }
                                });
                    } else {
                        Slog.v(
                                "TranslationManagerServiceImpl",
                                "onSessionCreatedLocked(): no remote service.");
                        iResultReceiver.send(2, (Bundle) null);
                    }
                } catch (Throwable th) {
                    throw th;
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
            new TranslationManagerServiceShellCommand(TranslationManagerService.this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void onTranslationCapabilitiesRequest(
                final int i, final int i2, final ResultReceiver resultReceiver, int i3) {
            synchronized (TranslationManagerService.this.mLock) {
                try {
                    TranslationManagerServiceImpl translationManagerServiceImpl =
                            (TranslationManagerServiceImpl)
                                    TranslationManagerService.this.getServiceForUserLocked(i3);
                    if (translationManagerServiceImpl == null
                            || (!TranslationManagerService.m975$$Nest$misDefaultServiceLocked(
                                            TranslationManagerService.this, i3)
                                    && !TranslationManagerService
                                            .m974$$Nest$misCalledByServiceAppLocked(
                                                    TranslationManagerService.this,
                                                    i3,
                                                    "getTranslationCapabilities"))) {
                        Slog.v(
                                "TranslationManagerService",
                                "onGetTranslationCapabilitiesLocked(): no service for " + i3);
                        resultReceiver.send(2, null);
                    }
                    RemoteTranslationService ensureRemoteServiceLocked =
                            translationManagerServiceImpl.ensureRemoteServiceLocked();
                    if (ensureRemoteServiceLocked != null) {
                        ensureRemoteServiceLocked.run(
                                new ServiceConnector
                                        .VoidJob() { // from class:
                                                     // com.android.server.translation.RemoteTranslationService$$ExternalSyntheticLambda2
                                    public final void runNoResult(Object obj) {
                                        int i4 = i;
                                        int i5 = i2;
                                        ResultReceiver resultReceiver2 = resultReceiver;
                                        int i6 = RemoteTranslationService.$r8$clinit;
                                        ((ITranslationService) obj)
                                                .onTranslationCapabilitiesRequest(
                                                        i4, i5, resultReceiver2);
                                    }
                                });
                    } else {
                        Slog.v(
                                "TranslationManagerServiceImpl",
                                "onTranslationCapabilitiesRequestLocked(): no remote service.");
                        resultReceiver.send(2, null);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onTranslationFinished(
                boolean z, IBinder iBinder, ComponentName componentName, int i) {
            synchronized (TranslationManagerService.this.mLock) {
                TranslationManagerServiceImpl translationManagerServiceImpl =
                        (TranslationManagerServiceImpl)
                                TranslationManagerService.this.getServiceForUserLocked(i);
                int appUidByComponentName =
                        TranslationManagerServiceImpl.getAppUidByComponentName(
                                translationManagerServiceImpl.mMaster.getContext(),
                                componentName,
                                translationManagerServiceImpl.mUserId);
                String packageName = componentName.getPackageName();
                if (z
                        || translationManagerServiceImpl.mWaitingFinishedCallbackActivities
                                .contains(iBinder)) {
                    translationManagerServiceImpl.invokeCallbacks(
                            3, null, null, packageName, appUidByComponentName);
                    translationManagerServiceImpl.mWaitingFinishedCallbackActivities.remove(
                            iBinder);
                    translationManagerServiceImpl.mActiveTranslations.remove(iBinder);
                }
            }
        }

        public final void registerTranslationCapabilityCallback(
                IRemoteCallback iRemoteCallback, int i) {
            TranslationManagerServiceImpl translationManagerServiceImpl;
            synchronized (TranslationManagerService.this.mLock) {
                translationManagerServiceImpl =
                        (TranslationManagerServiceImpl)
                                TranslationManagerService.this.getServiceForUserLocked(i);
            }
            if (translationManagerServiceImpl != null) {
                translationManagerServiceImpl.mTranslationCapabilityCallbacks.register(
                        iRemoteCallback, Integer.valueOf(Binder.getCallingUid()));
                translationManagerServiceImpl.ensureRemoteServiceLocked();
            }
        }

        public final void registerUiTranslationStateCallback(
                IRemoteCallback iRemoteCallback, int i) {
            synchronized (TranslationManagerService.this.mLock) {
                try {
                    TranslationManagerServiceImpl translationManagerServiceImpl =
                            (TranslationManagerServiceImpl)
                                    TranslationManagerService.this.getServiceForUserLocked(i);
                    if (translationManagerServiceImpl != null) {
                        translationManagerServiceImpl.registerUiTranslationStateCallbackLocked(
                                Binder.getCallingUid(), iRemoteCallback);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void unregisterTranslationCapabilityCallback(
                IRemoteCallback iRemoteCallback, int i) {
            TranslationManagerServiceImpl translationManagerServiceImpl;
            synchronized (TranslationManagerService.this.mLock) {
                translationManagerServiceImpl =
                        (TranslationManagerServiceImpl)
                                TranslationManagerService.this.getServiceForUserLocked(i);
            }
            if (translationManagerServiceImpl != null) {
                translationManagerServiceImpl.mTranslationCapabilityCallbacks.unregister(
                        iRemoteCallback);
            }
        }

        public final void unregisterUiTranslationStateCallback(
                IRemoteCallback iRemoteCallback, int i) {
            TranslationManagerServiceImpl translationManagerServiceImpl;
            synchronized (TranslationManagerService.this.mLock) {
                translationManagerServiceImpl =
                        (TranslationManagerServiceImpl)
                                TranslationManagerService.this.getServiceForUserLocked(i);
            }
            if (translationManagerServiceImpl != null) {
                translationManagerServiceImpl.mCallbacks.unregister(iRemoteCallback);
            }
        }

        public final void updateUiTranslationState(
                int i,
                TranslationSpec translationSpec,
                TranslationSpec translationSpec2,
                List list,
                IBinder iBinder,
                int i2,
                UiTranslationSpec uiTranslationSpec,
                int i3) {
            TranslationManagerService translationManagerService = TranslationManagerService.this;
            translationManagerService.getClass();
            translationManagerService
                    .getContext()
                    .enforceCallingPermission(
                            "android.permission.MANAGE_UI_TRANSLATION",
                            "Permission Denial from pid ="
                                    + Binder.getCallingPid()
                                    + ", uid="
                                    + Binder.getCallingUid()
                                    + " doesn't hold android.permission.MANAGE_UI_TRANSLATION");
            synchronized (TranslationManagerService.this.mLock) {
                try {
                    TranslationManagerServiceImpl translationManagerServiceImpl =
                            (TranslationManagerServiceImpl)
                                    TranslationManagerService.this.getServiceForUserLocked(i3);
                    if (translationManagerServiceImpl != null) {
                        if (!TranslationManagerService.m975$$Nest$misDefaultServiceLocked(
                                TranslationManagerService.this, i3)) {
                            if (TranslationManagerService.m974$$Nest$misCalledByServiceAppLocked(
                                    TranslationManagerService.this,
                                    i3,
                                    "updateUiTranslationState")) {}
                        }
                        translationManagerServiceImpl.updateUiTranslationStateLocked(
                                i,
                                translationSpec,
                                translationSpec2,
                                list,
                                iBinder,
                                i2,
                                uiTranslationSpec);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* renamed from: -$$Nest$misCalledByServiceAppLocked, reason: not valid java name */
    public static boolean m974$$Nest$misCalledByServiceAppLocked(
            TranslationManagerService translationManagerService, int i, String str) {
        translationManagerService.getClass();
        int callingUid = Binder.getCallingUid();
        String serviceName = translationManagerService.mServiceNameResolver.getServiceName(i);
        if (serviceName == null) {
            Slog.e(
                    "TranslationManagerService",
                    str
                            + ": called by UID "
                            + callingUid
                            + ", but there's no service set for user "
                            + i);
            return false;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(serviceName);
        if (unflattenFromString == null) {
            Slog.w("TranslationManagerService", str + ": invalid service name: " + serviceName);
            return false;
        }
        try {
            int packageUidAsUser =
                    translationManagerService
                            .getContext()
                            .getPackageManager()
                            .getPackageUidAsUser(unflattenFromString.getPackageName(), i);
            if (callingUid == packageUidAsUser) {
                return true;
            }
            Slog.e(
                    "TranslationManagerService",
                    str
                            + ": called by UID "
                            + callingUid
                            + ", but service UID is "
                            + packageUidAsUser);
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.w("TranslationManagerService", str + ": could not verify UID for " + serviceName);
            return false;
        }
    }

    /* renamed from: -$$Nest$misDefaultServiceLocked, reason: not valid java name */
    public static boolean m975$$Nest$misDefaultServiceLocked(
            TranslationManagerService translationManagerService, int i) {
        ServiceNameBaseResolver serviceNameBaseResolver =
                translationManagerService.mServiceNameResolver;
        String defaultServiceName = serviceNameBaseResolver.getDefaultServiceName(i);
        if (defaultServiceName == null) {
            return false;
        }
        return defaultServiceName.equals(serviceNameBaseResolver.getServiceName(i));
    }

    public TranslationManagerService(Context context) {
        super(
                context,
                new FrameworkResourcesServiceNameResolver(
                        context, R.string.device_ownership_relinquished),
                null,
                4);
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final void enforceCallingPermissionForManagement() {
        getContext()
                .enforceCallingPermission(
                        "android.permission.MANAGE_UI_TRANSLATION", "TranslationManagerService");
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final int getMaximumTemporaryServiceDurationMs() {
        return 120000;
    }

    @Override // com.android.server.infra.AbstractMasterSystemService
    public final AbstractPerUserSystemService newServiceLocked(int i, boolean z) {
        return new TranslationManagerServiceImpl(this, this.mLock, i);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("translation", new TranslationManagerServiceStub());
    }
}
