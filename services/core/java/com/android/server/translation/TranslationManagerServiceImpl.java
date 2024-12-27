package com.android.server.translation;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.service.translation.TranslationServiceInfo;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.view.inputmethod.InputMethodInfo;
import android.view.translation.ITranslationServiceCallback;
import android.view.translation.TranslationCapability;
import android.view.translation.TranslationSpec;

import com.android.internal.os.TransferPipe;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import com.android.server.infra.ServiceNameBaseResolver;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TranslationManagerServiceImpl extends AbstractPerUserSystemService
        implements IBinder.DeathRecipient {
    public static final boolean DEBUG = Log.isLoggable("TranslationManagerServiceImpl", 3);
    public final ArrayMap mActiveTranslations;
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public final RemoteCallbackList mCallbacks;
    public WeakReference mLastActivityTokens;
    public final TranslationServiceRemoteCallback mRemoteServiceCallback;
    public RemoteTranslationService mRemoteTranslationService;
    public final RemoteCallbackList mTranslationCapabilityCallbacks;
    public TranslationServiceInfo mTranslationServiceInfo;
    public final ArraySet mWaitingFinishedCallbackActivities;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActiveTranslation {
        public boolean isPaused = false;
        public final String packageName;
        public final TranslationSpec sourceSpec;
        public final TranslationSpec targetSpec;
        public final int translatedAppUid;

        public ActiveTranslation(
                int i,
                TranslationSpec translationSpec,
                TranslationSpec translationSpec2,
                String str) {
            this.sourceSpec = translationSpec;
            this.targetSpec = translationSpec2;
            this.translatedAppUid = i;
            this.packageName = str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TranslationServiceRemoteCallback extends ITranslationServiceCallback.Stub {
        public TranslationServiceRemoteCallback() {}

        public final void updateTranslationCapability(TranslationCapability translationCapability) {
            if (translationCapability == null) {
                Slog.wtf(
                        "TranslationManagerServiceImpl",
                        "received a null TranslationCapability from TranslationService.");
                return;
            }
            TranslationManagerServiceImpl translationManagerServiceImpl =
                    TranslationManagerServiceImpl.this;
            translationManagerServiceImpl.getClass();
            final Bundle bundle = new Bundle();
            bundle.putParcelable("translation_capabilities", translationCapability);
            translationManagerServiceImpl.mTranslationCapabilityCallbacks.broadcast(
                    new BiConsumer() { // from class:
                                       // com.android.server.translation.TranslationManagerServiceImpl$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            try {
                                ((IRemoteCallback) obj).sendResult(bundle);
                            } catch (RemoteException e) {
                                AccountManagerService$$ExternalSyntheticOutline0.m(
                                        "Failed to invoke UiTranslationStateCallback: ",
                                        e,
                                        "TranslationManagerServiceImpl");
                            }
                        }
                    });
        }
    }

    public TranslationManagerServiceImpl(
            TranslationManagerService translationManagerService, Object obj, int i) {
        super(translationManagerService, obj, i);
        this.mRemoteServiceCallback = new TranslationServiceRemoteCallback();
        this.mTranslationCapabilityCallbacks = new RemoteCallbackList();
        this.mWaitingFinishedCallbackActivities = new ArraySet();
        this.mActiveTranslations = new ArrayMap();
        this.mCallbacks = new RemoteCallbackList();
        updateRemoteServiceLocked$4();
        this.mActivityTaskManagerInternal =
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class);
    }

    public static Bundle createResultForCallback(
            int i, TranslationSpec translationSpec, TranslationSpec translationSpec2, String str) {
        Bundle m =
                SystemUpdateManagerService$$ExternalSyntheticOutline0.m(
                        i, LauncherConfigurationInternal.KEY_STATE_BOOLEAN);
        if (translationSpec != null) {
            m.putSerializable("source_locale", translationSpec.getLocale());
            m.putSerializable("target_locale", translationSpec2.getLocale());
        }
        m.putString("package_name", str);
        return m;
    }

    public static int getAppUidByComponentName(
            Context context, ComponentName componentName, int i) {
        if (componentName == null) {
            return -1;
        }
        try {
            return context.getPackageManager()
                    .getApplicationInfoAsUser(componentName.getPackageName(), 0, i)
                    .uid;
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.d(
                    "TranslationManagerServiceImpl",
                    "Cannot find packageManager for" + componentName);
            return -1;
        }
    }

    public static void invokeCallback(
            int i, int i2, IRemoteCallback iRemoteCallback, Bundle bundle, List list) {
        if (i == i2) {
            try {
                iRemoteCallback.sendResult(bundle);
                return;
            } catch (RemoteException e) {
                AccountManagerService$$ExternalSyntheticOutline0.m(
                        "Failed to invoke UiTranslationStateCallback: ",
                        e,
                        "TranslationManagerServiceImpl");
                return;
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (i == ((InputMethodInfo) it.next()).getServiceInfo().applicationInfo.uid) {
                try {
                    iRemoteCallback.sendResult(bundle);
                    return;
                } catch (RemoteException e2) {
                    AccountManagerService$$ExternalSyntheticOutline0.m(
                            "Failed to invoke UiTranslationStateCallback: ",
                            e2,
                            "TranslationManagerServiceImpl");
                    return;
                }
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {}

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                this.mWaitingFinishedCallbackActivities.remove(iBinder);
                ActiveTranslation activeTranslation =
                        (ActiveTranslation) this.mActiveTranslations.remove(iBinder);
                if (activeTranslation != null) {
                    invokeCallbacks(
                            3,
                            activeTranslation.sourceSpec,
                            activeTranslation.targetSpec,
                            activeTranslation.packageName,
                            activeTranslation.translatedAppUid);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpLocked(FileDescriptor fileDescriptor, PrintWriter printWriter) {
        WeakReference weakReference = this.mLastActivityTokens;
        if (weakReference != null) {
            ActivityTaskManagerInternal.ActivityTokens activityTokens =
                    (ActivityTaskManagerInternal.ActivityTokens) weakReference.get();
            if (activityTokens == null) {
                return;
            }
            try {
                TransferPipe transferPipe = new TransferPipe();
                try {
                    activityTokens.mAppThread.dumpActivity(
                            transferPipe.getWriteFd(),
                            activityTokens.mActivityToken,
                            "  ",
                            new String[] {"--dump-dumpable", "UiTranslationController"});
                    transferPipe.go(fileDescriptor);
                    transferPipe.close();
                } catch (Throwable th) {
                    try {
                        transferPipe.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (RemoteException unused) {
                printWriter.println("  Got a RemoteException while dumping the activity");
            } catch (IOException e) {
                printWriter.println("  Failure while dumping the activity: " + e);
            }
        } else {
            printWriter.print("  ");
            printWriter.println("No requested UiTranslation Activity.");
        }
        int size = this.mWaitingFinishedCallbackActivities.size();
        if (size > 0) {
            printWriter.print("  ");
            printWriter.print("number waiting finish callback activities: ");
            printWriter.println(size);
            Iterator it = this.mWaitingFinishedCallbackActivities.iterator();
            while (it.hasNext()) {
                IBinder iBinder = (IBinder) it.next();
                printWriter.print("  ");
                printWriter.print("shareableActivityToken: ");
                printWriter.println(iBinder);
            }
        }
    }

    public final RemoteTranslationService ensureRemoteServiceLocked() {
        int i = this.mUserId;
        if (this.mRemoteTranslationService == null) {
            String componentNameLocked = getComponentNameLocked();
            AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
            if (componentNameLocked == null) {
                if (((TranslationManagerService) abstractMasterSystemService).verbose) {
                    Slog.v(
                            "TranslationManagerServiceImpl",
                            "ensureRemoteServiceLocked(): no service component name.");
                }
                return null;
            }
            ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(componentNameLocked);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ResolveInfo resolveServiceAsUser =
                        abstractMasterSystemService
                                .getContext()
                                .getPackageManager()
                                .resolveServiceAsUser(
                                        new Intent("android.service.translation.TranslationService")
                                                .setComponent(unflattenFromString),
                                        132,
                                        i);
                boolean z =
                        (resolveServiceAsUser == null || resolveServiceAsUser.serviceInfo == null)
                                ? false
                                : true;
                if (((TranslationManagerService) abstractMasterSystemService).verbose) {
                    Slog.v(
                            "TranslationManagerServiceImpl",
                            "ensureRemoteServiceLocked(): isServiceAvailableForUser=" + z);
                }
                if (!z) {
                    Slog.w(
                            "TranslationManagerServiceImpl",
                            "ensureRemoteServiceLocked(): "
                                    + unflattenFromString
                                    + " is not available,");
                    return null;
                }
                this.mRemoteTranslationService =
                        new RemoteTranslationService(
                                abstractMasterSystemService.getContext(),
                                unflattenFromString,
                                i,
                                this.mRemoteServiceCallback);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return this.mRemoteTranslationService;
    }

    public final void invokeCallbacks(
            int i,
            TranslationSpec translationSpec,
            TranslationSpec translationSpec2,
            String str,
            final int i2) {
        final Bundle createResultForCallback =
                createResultForCallback(i, translationSpec, translationSpec2, str);
        int registeredCallbackCount = this.mCallbacks.getRegisteredCallbackCount();
        if (DEBUG) {
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            registeredCallbackCount,
                            i,
                            "Invoking ",
                            " callbacks for translation state=",
                            " for app with uid=");
            m.append(i2);
            m.append(" packageName=");
            m.append(str);
            Slog.d("TranslationManagerServiceImpl", m.toString());
        }
        if (registeredCallbackCount == 0) {
            return;
        }
        final List enabledInputMethodListAsUser =
                ((InputMethodManagerInternal)
                                LocalServices.getService(InputMethodManagerInternal.class))
                        .getEnabledInputMethodListAsUser(this.mUserId);
        this.mCallbacks.broadcast(
                new BiConsumer() { // from class:
                                   // com.android.server.translation.TranslationManagerServiceImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        TranslationManagerServiceImpl translationManagerServiceImpl =
                                TranslationManagerServiceImpl.this;
                        int i3 = i2;
                        Bundle bundle = createResultForCallback;
                        List list = enabledInputMethodListAsUser;
                        translationManagerServiceImpl.getClass();
                        TranslationManagerServiceImpl.invokeCallback(
                                ((Integer) obj2).intValue(),
                                i3,
                                (IRemoteCallback) obj,
                                bundle,
                                list);
                    }
                });
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        Context context = abstractMasterSystemService.getContext();
        ServiceNameBaseResolver serviceNameBaseResolver =
                abstractMasterSystemService.mServiceNameResolver;
        int i = this.mUserId;
        TranslationServiceInfo translationServiceInfo =
                new TranslationServiceInfo(
                        context, componentName, serviceNameBaseResolver.isTemporary(i), i);
        this.mTranslationServiceInfo = translationServiceInfo;
        translationServiceInfo.getServiceInfo();
        return this.mTranslationServiceInfo.getServiceInfo();
    }

    public final void registerUiTranslationStateCallbackLocked(
            int i, IRemoteCallback iRemoteCallback) {
        this.mCallbacks.register(iRemoteCallback, Integer.valueOf(i));
        int size = this.mActiveTranslations.size();
        Slog.i(
                "TranslationManagerServiceImpl",
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        size,
                        "New registered callback for sourceUid=",
                        " with currently ",
                        " active translations"));
        if (size == 0) {
            return;
        }
        List enabledInputMethodListAsUser =
                ((InputMethodManagerInternal)
                                LocalServices.getService(InputMethodManagerInternal.class))
                        .getEnabledInputMethodListAsUser(this.mUserId);
        for (int i2 = 0; i2 < this.mActiveTranslations.size(); i2++) {
            ActiveTranslation activeTranslation =
                    (ActiveTranslation) this.mActiveTranslations.valueAt(i2);
            int i3 = activeTranslation.translatedAppUid;
            boolean z = DEBUG;
            String str = activeTranslation.packageName;
            if (z) {
                StringBuilder m =
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i,
                                i3,
                                "Triggering callback for sourceUid=",
                                " for translated app with uid=",
                                "packageName=");
                m.append(str);
                m.append(" isPaused=");
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "TranslationManagerServiceImpl", m, activeTranslation.isPaused);
            }
            invokeCallback(
                    i,
                    i3,
                    iRemoteCallback,
                    createResultForCallback(
                            0, activeTranslation.sourceSpec, activeTranslation.targetSpec, str),
                    enabledInputMethodListAsUser);
            if (activeTranslation.isPaused) {
                invokeCallback(
                        i,
                        i3,
                        iRemoteCallback,
                        createResultForCallback(
                                1, activeTranslation.sourceSpec, activeTranslation.targetSpec, str),
                        enabledInputMethodListAsUser);
            }
        }
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        updateRemoteServiceLocked$4();
        return updateLocked;
    }

    public final void updateRemoteServiceLocked$4() {
        if (this.mRemoteTranslationService != null) {
            if (((TranslationManagerService) this.mMaster).debug) {
                Slog.d(
                        "TranslationManagerServiceImpl",
                        "updateRemoteService(): destroying old remote service");
            }
            this.mRemoteTranslationService.unbind();
            this.mRemoteTranslationService = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00da  */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v23 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateUiTranslationStateLocked(
            int r18,
            android.view.translation.TranslationSpec r19,
            android.view.translation.TranslationSpec r20,
            java.util.List r21,
            android.os.IBinder r22,
            int r23,
            android.view.translation.UiTranslationSpec r24) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.translation.TranslationManagerServiceImpl.updateUiTranslationStateLocked(int,"
                    + " android.view.translation.TranslationSpec,"
                    + " android.view.translation.TranslationSpec, java.util.List,"
                    + " android.os.IBinder, int, android.view.translation.UiTranslationSpec):void");
    }
}
