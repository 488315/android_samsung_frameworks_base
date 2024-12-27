package com.android.server.contentcapture;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.os.IResultReceiver;
import com.android.server.infra.AbstractPerUserSystemService;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class ContentCaptureManagerServiceShellCommand$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ContentCaptureManagerServiceShellCommand f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ IResultReceiver f$2;

    public /* synthetic */ ContentCaptureManagerServiceShellCommand$$ExternalSyntheticLambda0(
            ContentCaptureManagerServiceShellCommand contentCaptureManagerServiceShellCommand,
            int i,
            ContentCaptureManagerServiceShellCommand.AnonymousClass1 anonymousClass1) {
        this.f$0 = contentCaptureManagerServiceShellCommand;
        this.f$1 = i;
        this.f$2 = anonymousClass1;
    }

    public /* synthetic */ ContentCaptureManagerServiceShellCommand$$ExternalSyntheticLambda0(
            ContentCaptureManagerServiceShellCommand contentCaptureManagerServiceShellCommand,
            int i,
            ContentCaptureManagerServiceShellCommand.AnonymousClass2 anonymousClass2) {
        this.f$0 = contentCaptureManagerServiceShellCommand;
        this.f$1 = i;
        this.f$2 = anonymousClass2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ContentCaptureManagerServiceShellCommand contentCaptureManagerServiceShellCommand =
                        this.f$0;
                int i = this.f$1;
                IResultReceiver iResultReceiver = this.f$2;
                ContentCaptureManagerService contentCaptureManagerService =
                        contentCaptureManagerServiceShellCommand.mService;
                contentCaptureManagerService.getClass();
                Slog.i("ContentCaptureManagerService", "listSessions() for userId " + i);
                contentCaptureManagerService.enforceCallingPermissionForManagement();
                Bundle bundle = new Bundle();
                ArrayList<String> arrayList = new ArrayList<>();
                synchronized (contentCaptureManagerService.mLock) {
                    try {
                        if (i != -1) {
                            ContentCapturePerUserService contentCapturePerUserService =
                                    (ContentCapturePerUserService)
                                            contentCaptureManagerService.peekServiceForUserLocked(
                                                    i);
                            if (contentCapturePerUserService != null) {
                                contentCapturePerUserService.listSessionsLocked(arrayList);
                            }
                        } else {
                            int size = contentCaptureManagerService.mServicesCacheList.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                List list =
                                        (List)
                                                contentCaptureManagerService.mServicesCacheList
                                                        .valueAt(i2);
                                for (int i3 = 0; i3 < list.size(); i3++) {
                                    ((ContentCapturePerUserService)
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
                ContentCaptureManagerServiceShellCommand contentCaptureManagerServiceShellCommand2 =
                        this.f$0;
                int i4 = this.f$1;
                IResultReceiver iResultReceiver2 = this.f$2;
                ContentCaptureManagerService contentCaptureManagerService2 =
                        contentCaptureManagerServiceShellCommand2.mService;
                contentCaptureManagerService2.getClass();
                Slog.i("ContentCaptureManagerService", "destroySessions() for userId " + i4);
                contentCaptureManagerService2.enforceCallingPermissionForManagement();
                synchronized (contentCaptureManagerService2.mLock) {
                    try {
                        if (i4 != -1) {
                            ContentCapturePerUserService contentCapturePerUserService2 =
                                    (ContentCapturePerUserService)
                                            contentCaptureManagerService2.peekServiceForUserLocked(
                                                    i4);
                            if (contentCapturePerUserService2 != null) {
                                contentCapturePerUserService2.destroySessionsLocked();
                            }
                        } else {
                            int size2 = contentCaptureManagerService2.mServicesCacheList.size();
                            for (int i5 = 0; i5 < size2; i5++) {
                                List list2 =
                                        (List)
                                                contentCaptureManagerService2.mServicesCacheList
                                                        .valueAt(i5);
                                for (int i6 = 0; i6 < list2.size(); i6++) {
                                    ((ContentCapturePerUserService)
                                                    ((AbstractPerUserSystemService) list2.get(i6)))
                                            .destroySessionsLocked();
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
