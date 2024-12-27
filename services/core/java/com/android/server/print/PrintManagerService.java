package com.android.server.print;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.os.UserManager;
import android.print.IPrintDocumentAdapter;
import android.print.IPrintJobStateChangeListener;
import android.print.IPrintManager;
import android.print.IPrintServicesChangeListener;
import android.print.IPrinterDiscoveryObserver;
import android.print.PrintAttributes;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.printservice.PrintServiceInfo;
import android.printservice.recommendation.IRecommendationsChangeListener;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.widget.Toast;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.TransferPipe;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.dump.DumpUtils;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.print.UserState;
import com.android.server.print.UserState.AnonymousClass1;
import com.android.server.print.UserState.AnonymousClass2;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PrintManagerService extends SystemService {
    public final PrintManagerImpl mPrintManagerImpl;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PrintManagerImpl extends IPrintManager.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Context mContext;
        public final UserManager mUserManager;
        public final Object mLock = new Object();
        public final SparseArray mUserStates = new SparseArray();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.print.PrintManagerService$PrintManagerImpl$3, reason: invalid class name */
        public final class AnonymousClass3 implements Runnable {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ PrintManagerImpl this$1;
            public final /* synthetic */ int val$userId;

            public /* synthetic */ AnonymousClass3(PrintManagerImpl printManagerImpl, int i, int i2) {
                this.$r8$classId = i2;
                this.this$1 = printManagerImpl;
                this.val$userId = i;
            }

            @Override // java.lang.Runnable
            public final void run() {
                UserState orCreateUserStateLocked;
                switch (this.$r8$classId) {
                    case 0:
                        if (this.this$1.mUserManager.isUserUnlockingOrUnlocked(this.val$userId)) {
                            synchronized (this.this$1.mLock) {
                                orCreateUserStateLocked = this.this$1.getOrCreateUserStateLocked(this.val$userId, true, false, true);
                            }
                            orCreateUserStateLocked.removeObsoletePrintJobs();
                            return;
                        }
                        return;
                    default:
                        synchronized (this.this$1.mLock) {
                            try {
                                UserState userState = (UserState) this.this$1.mUserStates.get(this.val$userId);
                                if (userState != null) {
                                    userState.destroyLocked();
                                    this.this$1.mUserStates.remove(this.val$userId);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return;
                }
            }
        }

        public PrintManagerImpl(Context context) {
            this.mContext = context;
            this.mUserManager = (UserManager) context.getSystemService("user");
            final Uri uriFor = Settings.Secure.getUriFor("disabled_print_services");
            context.getContentResolver().registerContentObserver(uriFor, false, new ContentObserver(BackgroundThread.getHandler()) { // from class: com.android.server.print.PrintManagerService.PrintManagerImpl.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri, int i) {
                    int i2;
                    if (uriFor.equals(uri)) {
                        synchronized (PrintManagerImpl.this.mLock) {
                            try {
                                int size = PrintManagerImpl.this.mUserStates.size();
                                while (i2 < size) {
                                    i2 = (i == -1 || i == PrintManagerImpl.this.mUserStates.keyAt(i2)) ? 0 : i2 + 1;
                                    ((UserState) PrintManagerImpl.this.mUserStates.valueAt(i2)).updateIfNeededLocked();
                                }
                            } finally {
                            }
                        }
                    }
                }
            }, -1);
            new PackageMonitor() { // from class: com.android.server.print.PrintManagerService.PrintManagerImpl.2
                public static boolean hadPrintService(UserState userState, String str) {
                    List printServices = userState.getPrintServices(3);
                    if (printServices == null) {
                        return false;
                    }
                    int size = printServices.size();
                    for (int i = 0; i < size; i++) {
                        if (((PrintServiceInfo) printServices.get(i)).getResolveInfo().serviceInfo.packageName.equals(str)) {
                            return true;
                        }
                    }
                    return false;
                }

                public final boolean hasPrintService(String str) {
                    List queryIntentServicesAsUser = PrintManagerImpl.this.mContext.getPackageManager().queryIntentServicesAsUser(ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("android.printservice.PrintService", str), 276824068, getChangingUserId());
                    return (queryIntentServicesAsUser == null || queryIntentServicesAsUser.isEmpty()) ? false : true;
                }

                public final boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
                    if (!PrintManagerImpl.this.mUserManager.isUserUnlockingOrUnlocked(getChangingUserId())) {
                        return false;
                    }
                    synchronized (PrintManagerImpl.this.mLock) {
                        try {
                            UserState orCreateUserStateLocked = PrintManagerImpl.this.getOrCreateUserStateLocked(getChangingUserId(), false, false, false);
                            List printServices = orCreateUserStateLocked.getPrintServices(1);
                            if (printServices == null) {
                                return false;
                            }
                            Iterator it = printServices.iterator();
                            boolean z2 = false;
                            while (it.hasNext()) {
                                String packageName = ((PrintServiceInfo) it.next()).getComponentName().getPackageName();
                                int length = strArr.length;
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= length) {
                                        break;
                                    }
                                    if (!packageName.equals(strArr[i2])) {
                                        i2++;
                                    } else {
                                        if (!z) {
                                            return true;
                                        }
                                        z2 = true;
                                    }
                                }
                            }
                            if (z2) {
                                orCreateUserStateLocked.updateIfNeededLocked();
                            }
                            return false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public final void onPackageAdded(String str, int i) {
                    if (PrintManagerImpl.this.mUserManager.isUserUnlockingOrUnlocked(getChangingUserId())) {
                        synchronized (PrintManagerImpl.this.mLock) {
                            try {
                                if (hasPrintService(str)) {
                                    PrintManagerImpl.this.getOrCreateUserStateLocked(getChangingUserId(), false, false, false).updateIfNeededLocked();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                public final void onPackageModified(String str) {
                    if (PrintManagerImpl.this.mUserManager.isUserUnlockingOrUnlocked(getChangingUserId())) {
                        boolean z = false;
                        UserState orCreateUserStateLocked = PrintManagerImpl.this.getOrCreateUserStateLocked(getChangingUserId(), false, false, false);
                        synchronized (PrintManagerImpl.this.mLock) {
                            try {
                                if (!hadPrintService(orCreateUserStateLocked, str)) {
                                    if (hasPrintService(str)) {
                                    }
                                }
                                orCreateUserStateLocked.updateIfNeededLocked();
                                z = true;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (z) {
                            orCreateUserStateLocked.prunePrintServices();
                        }
                    }
                }

                public final void onPackageRemoved(String str, int i) {
                    if (PrintManagerImpl.this.mUserManager.isUserUnlockingOrUnlocked(getChangingUserId())) {
                        boolean z = false;
                        UserState orCreateUserStateLocked = PrintManagerImpl.this.getOrCreateUserStateLocked(getChangingUserId(), false, false, false);
                        synchronized (PrintManagerImpl.this.mLock) {
                            try {
                                if (hadPrintService(orCreateUserStateLocked, str)) {
                                    orCreateUserStateLocked.updateIfNeededLocked();
                                    z = true;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (z) {
                            orCreateUserStateLocked.prunePrintServices();
                        }
                    }
                }
            }.register(context, BackgroundThread.getHandler().getLooper(), UserHandle.ALL, true);
        }

        public static void dump(DualDumpOutputStream dualDumpOutputStream, ArrayList arrayList) {
            int i;
            int i2;
            long j;
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                long start = dualDumpOutputStream.start("user_states", 2246267895809L);
                UserState userState = (UserState) arrayList.get(i3);
                synchronized (userState.mLock) {
                    try {
                        dualDumpOutputStream.write("user_id", 1120986464257L, userState.mUserId);
                        int size2 = ((ArrayList) userState.mInstalledServices).size();
                        int i4 = 0;
                        while (i4 < size2) {
                            long start2 = dualDumpOutputStream.start("installed_services", 2246267895810L);
                            PrintServiceInfo printServiceInfo = (PrintServiceInfo) ((ArrayList) userState.mInstalledServices).get(i4);
                            ServiceInfo serviceInfo = printServiceInfo.getResolveInfo().serviceInfo;
                            DumpUtils.writeComponentName(dualDumpOutputStream, "component_name", 1146756268033L, new ComponentName(serviceInfo.packageName, serviceInfo.name));
                            DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "settings_activity", 1138166333442L, printServiceInfo.getSettingsActivityName());
                            DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "add_printers_activity", 1138166333443L, printServiceInfo.getAddPrintersActivityName());
                            DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "advanced_options_activity", 1138166333444L, printServiceInfo.getAdvancedOptionsActivityName());
                            dualDumpOutputStream.end(start2);
                            i4++;
                            i3 = i3;
                            size = size;
                            start = start;
                        }
                        i = size;
                        i2 = i3;
                        j = start;
                        Iterator it = ((ArraySet) userState.mDisabledServices).iterator();
                        while (it.hasNext()) {
                            DumpUtils.writeComponentName(dualDumpOutputStream, "disabled_services", 2246267895811L, (ComponentName) it.next());
                        }
                        int size3 = userState.mActiveServices.size();
                        for (int i5 = 0; i5 < size3; i5++) {
                            long start3 = dualDumpOutputStream.start("actives_services", 2246267895812L);
                            ((RemotePrintService) userState.mActiveServices.valueAt(i5)).dump(dualDumpOutputStream);
                            dualDumpOutputStream.end(start3);
                        }
                        userState.mPrintJobForAppCache.dumpLocked(dualDumpOutputStream);
                        if (userState.mPrinterDiscoverySession != null) {
                            long start4 = dualDumpOutputStream.start("discovery_service", 2246267895814L);
                            userState.mPrinterDiscoverySession.dumpLocked(dualDumpOutputStream);
                            dualDumpOutputStream.end(start4);
                        }
                    } finally {
                    }
                }
                long start5 = dualDumpOutputStream.start("print_spooler_state", 1146756268039L);
                RemotePrintSpooler remotePrintSpooler = userState.mSpooler;
                synchronized (remotePrintSpooler.mLock) {
                    dualDumpOutputStream.write("is_destroyed", 1133871366145L, remotePrintSpooler.mDestroyed);
                    dualDumpOutputStream.write("is_bound", 1133871366146L, remotePrintSpooler.mRemoteInstance != null);
                }
                try {
                    if (dualDumpOutputStream.isProto()) {
                        dualDumpOutputStream.write((String) null, 1146756268035L, TransferPipe.dumpAsync(remotePrintSpooler.getRemoteInstanceLazy().asBinder(), new String[]{"--proto"}));
                    } else {
                        try {
                            dualDumpOutputStream.writeNested("internal_state", TransferPipe.dumpAsync(remotePrintSpooler.getRemoteInstanceLazy().asBinder(), new String[0]));
                        } catch (RemoteException | IOException | InterruptedException | TimeoutException e) {
                            e = e;
                            Slog.e("RemotePrintSpooler", "Failed to dump remote instance", e);
                            dualDumpOutputStream.end(start5);
                            dualDumpOutputStream.end(j);
                            i3 = i2 + 1;
                            size = i;
                        }
                    }
                } catch (RemoteException | IOException | InterruptedException | TimeoutException e2) {
                    e = e2;
                }
                dualDumpOutputStream.end(start5);
                dualDumpOutputStream.end(j);
                i3 = i2 + 1;
                size = i;
            }
            dualDumpOutputStream.flush();
        }

        public static int getCurrentUserId() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return ActivityManager.getCurrentUser();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public static int resolveCallingUserEnforcingPermissions(int i) {
            try {
                return ActivityManager.getService().handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, true, true, "", (String) null);
            } catch (RemoteException unused) {
                return i;
            }
        }

        public final void addPrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i, int i2) {
            Objects.requireNonNull(iPrintJobStateChangeListener);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    resolveCallingAppEnforcingPermissions(i);
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (orCreateUserStateLocked.mLock) {
                            try {
                                orCreateUserStateLocked.throwIfDestroyedLocked();
                                if (orCreateUserStateLocked.mPrintJobStateChangeListenerRecords == null) {
                                    orCreateUserStateLocked.mPrintJobStateChangeListenerRecords = new ArrayList();
                                }
                                ((ArrayList) orCreateUserStateLocked.mPrintJobStateChangeListenerRecords).add(orCreateUserStateLocked.new AnonymousClass2(iPrintJobStateChangeListener, i));
                            } finally {
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) {
            Objects.requireNonNull(iRecommendationsChangeListener);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICE_RECOMMENDATIONS", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.addPrintServiceRecommendationsChangeListener(iRecommendationsChangeListener);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void addPrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) {
            Objects.requireNonNull(iPrintServicesChangeListener);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICES", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (orCreateUserStateLocked.mLock) {
                            try {
                                orCreateUserStateLocked.throwIfDestroyedLocked();
                                if (orCreateUserStateLocked.mPrintServicesChangeListenerRecords == null) {
                                    orCreateUserStateLocked.mPrintServicesChangeListenerRecords = new ArrayList();
                                }
                                ((ArrayList) orCreateUserStateLocked.mPrintServicesChangeListenerRecords).add(new UserState.AnonymousClass3(orCreateUserStateLocked, iPrintServicesChangeListener));
                            } finally {
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final void cancelPrintJob(PrintJobId printJobId, int i, int i2) {
            if (printJobId == null) {
                return;
            }
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    resolveCallingAppEnforcingPermissions(i);
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.cancelPrintJob(printJobId, i);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void createPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) {
            Objects.requireNonNull(iPrinterDiscoveryObserver);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.mSpooler.clearCustomPrinterIconCache();
                        synchronized (orCreateUserStateLocked.mLock) {
                            try {
                                orCreateUserStateLocked.throwIfDestroyedLocked();
                                UserState.AnonymousClass1 anonymousClass1 = orCreateUserStateLocked.mPrinterDiscoverySession;
                                if (anonymousClass1 == null) {
                                    UserState.AnonymousClass1 anonymousClass12 = orCreateUserStateLocked.new AnonymousClass1();
                                    orCreateUserStateLocked.mPrinterDiscoverySession = anonymousClass12;
                                    anonymousClass12.addObserverLocked(iPrinterDiscoveryObserver);
                                } else {
                                    anonymousClass1.addObserverLocked(iPrinterDiscoveryObserver);
                                }
                            } finally {
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) {
            Objects.requireNonNull(iPrinterDiscoveryObserver);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (orCreateUserStateLocked.mLock) {
                            try {
                                UserState.AnonymousClass1 anonymousClass1 = orCreateUserStateLocked.mPrinterDiscoverySession;
                                if (anonymousClass1 != null) {
                                    UserState$PrinterDiscoverySessionMediator$1 userState$PrinterDiscoverySessionMediator$1 = anonymousClass1.mDiscoveryObservers;
                                    userState$PrinterDiscoverySessionMediator$1.unregister(iPrinterDiscoveryObserver);
                                    if (userState$PrinterDiscoverySessionMediator$1.getRegisteredCallbackCount() == 0) {
                                        anonymousClass1.destroyLocked();
                                    }
                                }
                            } finally {
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            String str;
            Objects.requireNonNull(fileDescriptor);
            if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, "PrintManagerService", printWriter)) {
                int i = 0;
                boolean z = false;
                while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
                    i++;
                    if ("--proto".equals(str)) {
                        z = true;
                    } else {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Unknown argument: ", str, "; use -h for help");
                    }
                }
                ArrayList arrayList = new ArrayList();
                synchronized (this.mLock) {
                    try {
                        int size = this.mUserStates.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            arrayList.add((UserState) this.mUserStates.valueAt(i2));
                        }
                    } finally {
                    }
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (z) {
                        dump(new DualDumpOutputStream(new ProtoOutputStream(fileDescriptor)), arrayList);
                    } else {
                        printWriter.println("PRINT MANAGER STATE (dumpsys print)");
                        dump(new DualDumpOutputStream(new IndentingPrintWriter(printWriter, "  ")), arrayList);
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final boolean getBindInstantServiceAllowed(int i) {
            UserState orCreateUserStateLocked;
            int callingUid = Binder.getCallingUid();
            if (callingUid != 2000 && callingUid != 0) {
                throw new SecurityException("Can only be called by uid 2000 or 0");
            }
            synchronized (this.mLock) {
                orCreateUserStateLocked = getOrCreateUserStateLocked(i, false, true, false);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return orCreateUserStateLocked.mIsInstantServiceAllowed;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final Icon getCustomPrinterIcon(PrinterId printerId, int i) {
            String encodedUserInfo;
            Objects.requireNonNull(printerId);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    Icon icon = null;
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        Icon customPrinterIcon = orCreateUserStateLocked.getCustomPrinterIcon(printerId);
                        if (customPrinterIcon != null && ((customPrinterIcon.getType() == 4 || customPrinterIcon.getType() == 6) && (encodedUserInfo = customPrinterIcon.getUri().getEncodedUserInfo()) != null)) {
                            int resolveCallingUserEnforcingPermissions2 = resolveCallingUserEnforcingPermissions(Integer.parseInt(encodedUserInfo));
                            synchronized (this.mLock) {
                                try {
                                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions2) != getCurrentUserId()) {
                                    }
                                } finally {
                                }
                            }
                            return icon;
                        }
                        icon = customPrinterIcon;
                        return icon;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final UserState getOrCreateUserStateLocked(int i, boolean z, boolean z2, boolean z3) {
            if (z2 && !this.mUserManager.isUserUnlockingOrUnlocked(i)) {
                throw new IllegalStateException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "User ", " must be unlocked for printing to be available"));
            }
            UserState userState = (UserState) this.mUserStates.get(i);
            if (userState == null) {
                userState = new UserState(this.mContext, i, this.mLock, z);
                this.mUserStates.put(i, userState);
            } else if (z3) {
                userState.updateIfNeededLocked();
            }
            if (!z) {
                RemotePrintSpooler remotePrintSpooler = userState.mSpooler;
                if (remotePrintSpooler.mIsLowPriority) {
                    remotePrintSpooler.mIsLowPriority = false;
                    synchronized (remotePrintSpooler.mLock) {
                        remotePrintSpooler.throwIfDestroyedLocked();
                        while (!remotePrintSpooler.mCanUnbind) {
                            try {
                                remotePrintSpooler.mLock.wait();
                            } catch (InterruptedException unused) {
                                Slog.e("RemotePrintSpooler", "Interrupted while waiting for operation to complete");
                            }
                        }
                        remotePrintSpooler.unbindLocked();
                    }
                }
            }
            return userState;
        }

        public final PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int i, int i2) {
            if (printJobId == null) {
                return null;
            }
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    resolveCallingAppEnforcingPermissions(i);
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return orCreateUserStateLocked.getPrintJobInfo(printJobId, i);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final List getPrintJobInfos(int i, int i2) {
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    resolveCallingAppEnforcingPermissions(i);
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return orCreateUserStateLocked.getPrintJobInfos(i);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final List getPrintServiceRecommendations(int i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICE_RECOMMENDATIONS", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return orCreateUserStateLocked.mPrintServiceRecommendations;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final List getPrintServices(int i, int i2) {
            Preconditions.checkFlagsArgument(i, 3);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICES", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return orCreateUserStateLocked.getPrintServices(i);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean isPrintServiceEnabled(ComponentName componentName, int i) {
            for (String str : this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid())) {
                if (str.equals(componentName.getPackageName())) {
                    int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
                    synchronized (this.mLock) {
                        try {
                            if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                                return false;
                            }
                            UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                            synchronized (orCreateUserStateLocked.mLock) {
                                try {
                                    return !((ArraySet) orCreateUserStateLocked.mDisabledServices).contains(componentName);
                                } finally {
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
            throw new SecurityException("PrintService does not share UID with caller.");
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new PrintShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final Bundle print(String str, IPrintDocumentAdapter iPrintDocumentAdapter, PrintAttributes printAttributes, String str2, int i, int i2) {
            long clearCallingIdentity;
            Objects.requireNonNull(iPrintDocumentAdapter);
            if (!(!this.mUserManager.hasUserRestriction("no_printing", Binder.getCallingUserHandle()))) {
                DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                int callingUserId = UserHandle.getCallingUserId();
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    CharSequence printingDisabledReasonForUser = devicePolicyManagerInternal.getPrintingDisabledReasonForUser(callingUserId);
                    if (printingDisabledReasonForUser != null) {
                        Toast.makeText(this.mContext, Looper.getMainLooper(), printingDisabledReasonForUser, 1).show();
                    }
                    try {
                        iPrintDocumentAdapter.start();
                    } catch (RemoteException unused) {
                        Log.e("PrintManagerService", "Error calling IPrintDocumentAdapter.start()");
                    }
                    try {
                        iPrintDocumentAdapter.finish();
                    } catch (RemoteException unused2) {
                        Log.e("PrintManagerService", "Error calling IPrintDocumentAdapter.finish()");
                    }
                    return null;
                } finally {
                }
            }
            String str3 = (String) Preconditions.checkStringNotEmpty(str);
            String str4 = (String) Preconditions.checkStringNotEmpty(str2);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return null;
                    }
                    resolveCallingAppEnforcingPermissions(i);
                    for (String str5 : this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid())) {
                        if (str4.equals(str5)) {
                            UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                return orCreateUserStateLocked.print(str3, iPrintDocumentAdapter, printAttributes, str4, i);
                            } finally {
                            }
                        }
                    }
                    throw new IllegalArgumentException("packageName has to belong to the caller");
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removePrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int i) {
            Objects.requireNonNull(iPrintJobStateChangeListener);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.removePrintJobStateChangeListener(iPrintJobStateChangeListener);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int i) {
            Objects.requireNonNull(iRecommendationsChangeListener);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICE_RECOMMENDATIONS", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.removePrintServiceRecommendationsChangeListener(iRecommendationsChangeListener);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removePrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int i) {
            Objects.requireNonNull(iPrintServicesChangeListener);
            this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRINT_SERVICES", null);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.removePrintServicesChangeListener(iPrintServicesChangeListener);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void resolveCallingAppEnforcingPermissions(int i) {
            int appId;
            int callingUid = Binder.getCallingUid();
            if (callingUid != 0 && i != (appId = UserHandle.getAppId(callingUid)) && appId != 2000 && appId != 1000 && this.mContext.checkCallingPermission("com.android.printspooler.permission.ACCESS_ALL_PRINT_JOBS") != 0) {
                throw new SecurityException(DualAppManagerService$$ExternalSyntheticOutline0.m(appId, i, "Call from app ", " as app ", " without com.android.printspooler.permission.ACCESS_ALL_PRINT_JOBS"));
            }
        }

        public final int resolveCallingProfileParentLocked(int i) {
            if (i == getCurrentUserId()) {
                return i;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UserInfo profileParent = this.mUserManager.getProfileParent(i);
                if (profileParent != null) {
                    return profileParent.getUserHandle().getIdentifier();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -10;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void restartPrintJob(PrintJobId printJobId, int i, int i2) {
            if (printJobId == null || !(!this.mUserManager.hasUserRestriction("no_printing", Binder.getCallingUserHandle()))) {
                return;
            }
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i2);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    resolveCallingAppEnforcingPermissions(i);
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        PrintJobInfo printJobInfo = orCreateUserStateLocked.getPrintJobInfo(printJobId, i);
                        if (printJobInfo != null && printJobInfo.getState() == 6) {
                            orCreateUserStateLocked.mSpooler.setPrintJobState(printJobId, 2, null);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final void setBindInstantServiceAllowed(int i, boolean z) {
            UserState orCreateUserStateLocked;
            int callingUid = Binder.getCallingUid();
            if (callingUid != 2000 && callingUid != 0) {
                throw new SecurityException("Can only be called by uid 2000 or 0");
            }
            synchronized (this.mLock) {
                orCreateUserStateLocked = getOrCreateUserStateLocked(i, false, true, false);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (orCreateUserStateLocked.mLock) {
                    orCreateUserStateLocked.mIsInstantServiceAllowed = z;
                    orCreateUserStateLocked.updateIfNeededLocked();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setPrintServiceEnabled(ComponentName componentName, boolean z, int i) {
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            int appId = UserHandle.getAppId(Binder.getCallingUid());
            if (appId != 1000) {
                try {
                    if (appId != UserHandle.getAppId(this.mContext.getPackageManager().getPackageUidAsUser("com.android.printspooler", resolveCallingUserEnforcingPermissions))) {
                        throw new SecurityException("Only system and print spooler can call this");
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("PrintManagerService", "Could not verify caller", e);
                    return;
                }
            }
            Objects.requireNonNull(componentName);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.setPrintServiceEnabled(componentName, z);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void startPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, List list, int i) {
            Objects.requireNonNull(iPrinterDiscoveryObserver);
            if (list != null) {
                list = (List) Preconditions.checkCollectionElementsNotNull(list, "PrinterId");
            }
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (orCreateUserStateLocked.mLock) {
                            try {
                                orCreateUserStateLocked.throwIfDestroyedLocked();
                                UserState.AnonymousClass1 anonymousClass1 = orCreateUserStateLocked.mPrinterDiscoverySession;
                                if (anonymousClass1 != null) {
                                    anonymousClass1.startPrinterDiscoveryLocked(iPrinterDiscoveryObserver, list);
                                }
                            } finally {
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final void startPrinterStateTracking(PrinterId printerId, int i) {
            Objects.requireNonNull(printerId);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (orCreateUserStateLocked.mLock) {
                            try {
                                orCreateUserStateLocked.throwIfDestroyedLocked();
                                if (!orCreateUserStateLocked.mActiveServices.isEmpty()) {
                                    UserState.AnonymousClass1 anonymousClass1 = orCreateUserStateLocked.mPrinterDiscoverySession;
                                    if (anonymousClass1 != null) {
                                        anonymousClass1.startPrinterStateTrackingLocked(printerId);
                                    }
                                }
                            } finally {
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final void stopPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int i) {
            Objects.requireNonNull(iPrinterDiscoveryObserver);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (orCreateUserStateLocked.mLock) {
                            try {
                                orCreateUserStateLocked.throwIfDestroyedLocked();
                                UserState.AnonymousClass1 anonymousClass1 = orCreateUserStateLocked.mPrinterDiscoverySession;
                                if (anonymousClass1 != null) {
                                    anonymousClass1.stopPrinterDiscoveryLocked(iPrinterDiscoveryObserver);
                                }
                            } finally {
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final void stopPrinterStateTracking(PrinterId printerId, int i) {
            Objects.requireNonNull(printerId);
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.stopPrinterStateTracking(printerId);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void validatePrinters(List list, int i) {
            List list2 = (List) Preconditions.checkCollectionElementsNotNull(list, "PrinterId");
            int resolveCallingUserEnforcingPermissions = resolveCallingUserEnforcingPermissions(i);
            synchronized (this.mLock) {
                try {
                    if (resolveCallingProfileParentLocked(resolveCallingUserEnforcingPermissions) != getCurrentUserId()) {
                        return;
                    }
                    UserState orCreateUserStateLocked = getOrCreateUserStateLocked(resolveCallingUserEnforcingPermissions, false, true, false);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        orCreateUserStateLocked.validatePrinters(list2);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public PrintManagerService(Context context) {
        super(context);
        this.mPrintManagerImpl = new PrintManagerImpl(context);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("print", this.mPrintManagerImpl);
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        int i = PrintManagerImpl.$r8$clinit;
        PrintManagerImpl printManagerImpl = this.mPrintManagerImpl;
        printManagerImpl.getClass();
        BackgroundThread.getHandler().post(new PrintManagerImpl.AnonymousClass3(printManagerImpl, userIdentifier, 1));
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        int i = PrintManagerImpl.$r8$clinit;
        PrintManagerImpl printManagerImpl = this.mPrintManagerImpl;
        printManagerImpl.getClass();
        BackgroundThread.getHandler().post(new PrintManagerImpl.AnonymousClass3(printManagerImpl, userIdentifier, 0));
    }
}
