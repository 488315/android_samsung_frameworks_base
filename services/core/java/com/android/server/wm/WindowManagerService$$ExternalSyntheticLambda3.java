package com.android.server.wm;

import android.app.IAssistDataReceiver;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.server.SensitiveContentProtectionManagerService;
import com.android.server.SensitiveContentProtectionManagerService$$ExternalSyntheticLambda0;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda3(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                IAssistDataReceiver iAssistDataReceiver = (IAssistDataReceiver) this.f$0;
                Bitmap bitmap = (Bitmap) this.f$1;
                int i = WindowManagerService.MY_PID;
                try {
                    iAssistDataReceiver.onHandleAssistScreenshot(bitmap);
                    return;
                } catch (RemoteException unused) {
                    return;
                }
            default:
                WindowManagerInternal.OnWindowRemovedListener[] onWindowRemovedListenerArr =
                        (WindowManagerInternal.OnWindowRemovedListener[]) this.f$0;
                final IBinder iBinder = (IBinder) this.f$1;
                int i2 = WindowManagerService.MY_PID;
                for (WindowManagerInternal.OnWindowRemovedListener onWindowRemovedListener :
                        onWindowRemovedListenerArr) {
                    SensitiveContentProtectionManagerService
                            sensitiveContentProtectionManagerService =
                                    ((SensitiveContentProtectionManagerService$$ExternalSyntheticLambda0)
                                                    onWindowRemovedListener)
                                            .f$0;
                    synchronized (
                            sensitiveContentProtectionManagerService
                                    .mSensitiveContentProtectionLock) {
                        sensitiveContentProtectionManagerService.mPackagesShowingSensitiveContent
                                .removeIf(
                                        new Predicate() { // from class:
                                                          // com.android.server.SensitiveContentProtectionManagerService$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((SensitiveContentPackages.PackageInfo) obj)
                                                                .mWindowToken
                                                        == iBinder;
                                            }
                                        });
                    }
                }
                return;
        }
    }
}
