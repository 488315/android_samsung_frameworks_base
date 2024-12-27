package com.android.server.autofill;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.os.IResultReceiver;
import com.android.server.infra.AbstractPerUserSystemService;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AutofillManagerServiceShellCommand$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ AutofillManagerServiceShellCommand f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ IResultReceiver f$2;

    public /* synthetic */ AutofillManagerServiceShellCommand$$ExternalSyntheticLambda1(
            AutofillManagerServiceShellCommand autofillManagerServiceShellCommand,
            int i,
            AutofillManagerServiceShellCommand.AnonymousClass1 anonymousClass1) {
        this.f$0 = autofillManagerServiceShellCommand;
        this.f$1 = i;
        this.f$2 = anonymousClass1;
    }

    public /* synthetic */ AutofillManagerServiceShellCommand$$ExternalSyntheticLambda1(
            AutofillManagerServiceShellCommand autofillManagerServiceShellCommand,
            int i,
            AutofillManagerServiceShellCommand.AnonymousClass2 anonymousClass2) {
        this.f$0 = autofillManagerServiceShellCommand;
        this.f$1 = i;
        this.f$2 = anonymousClass2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AutofillManagerServiceShellCommand autofillManagerServiceShellCommand = this.f$0;
                int i = this.f$1;
                IResultReceiver iResultReceiver = this.f$2;
                AutofillManagerService autofillManagerService =
                        autofillManagerServiceShellCommand.mService;
                autofillManagerService.getClass();
                Slog.i("AutofillManagerService", "listSessions() for userId " + i);
                autofillManagerService.enforceCallingPermissionForManagement();
                Bundle bundle = new Bundle();
                ArrayList<String> arrayList = new ArrayList<>();
                synchronized (autofillManagerService.mLock) {
                    try {
                        if (i != -1) {
                            AutofillManagerServiceImpl autofillManagerServiceImpl =
                                    (AutofillManagerServiceImpl)
                                            autofillManagerService.peekServiceForUserLocked(i);
                            if (autofillManagerServiceImpl != null) {
                                autofillManagerServiceImpl.listSessionsLocked(arrayList);
                            }
                        } else {
                            int size = autofillManagerService.mServicesCacheList.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                List list =
                                        (List)
                                                autofillManagerService.mServicesCacheList.valueAt(
                                                        i2);
                                for (int i3 = 0; i3 < list.size(); i3++) {
                                    ((AutofillManagerServiceImpl)
                                                    ((AbstractPerUserSystemService) list.get(i3)))
                                            .listSessionsLocked(arrayList);
                                }
                            }
                        }
                    } finally {
                    }
                }
                bundle.putStringArrayList("sessions", arrayList);
                try {
                    iResultReceiver.send(0, bundle);
                    return;
                } catch (RemoteException unused) {
                    return;
                }
            default:
                AutofillManagerServiceShellCommand autofillManagerServiceShellCommand2 = this.f$0;
                int i4 = this.f$1;
                IResultReceiver iResultReceiver2 = this.f$2;
                AutofillManagerService autofillManagerService2 =
                        autofillManagerServiceShellCommand2.mService;
                autofillManagerService2.getClass();
                Slog.i("AutofillManagerService", "removeAllSessions() for userId " + i4);
                autofillManagerService2.enforceCallingPermissionForManagement();
                synchronized (autofillManagerService2.mLock) {
                    try {
                        if (i4 != -1) {
                            AutofillManagerServiceImpl autofillManagerServiceImpl2 =
                                    (AutofillManagerServiceImpl)
                                            autofillManagerService2.peekServiceForUserLocked(i4);
                            if (autofillManagerServiceImpl2 != null) {
                                autofillManagerServiceImpl2.forceRemoveAllSessionsLocked();
                            }
                        } else {
                            int size2 = autofillManagerService2.mServicesCacheList.size();
                            for (int i5 = 0; i5 < size2; i5++) {
                                List list2 =
                                        (List)
                                                autofillManagerService2.mServicesCacheList.valueAt(
                                                        i5);
                                for (int i6 = 0; i6 < list2.size(); i6++) {
                                    ((AutofillManagerServiceImpl)
                                                    ((AbstractPerUserSystemService) list2.get(i6)))
                                            .forceRemoveAllSessionsLocked();
                                }
                            }
                        }
                    } finally {
                    }
                }
                try {
                    iResultReceiver2.send(0, new Bundle());
                    return;
                } catch (RemoteException unused2) {
                    return;
                }
        }
    }
}
