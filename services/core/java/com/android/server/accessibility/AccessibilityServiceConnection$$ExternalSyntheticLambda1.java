package com.android.server.accessibility;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityServiceConnection$$ExternalSyntheticLambda1
        implements Consumer {
    /* JADX WARN: Removed duplicated region for block: B:17:0x0046 A[Catch: all -> 0x0012, TryCatch #1 {all -> 0x0012, blocks: (B:4:0x0005, B:6:0x000f, B:10:0x0015, B:12:0x0024, B:17:0x0046, B:19:0x0052, B:20:0x0059, B:23:0x005b, B:38:0x0032), top: B:3:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0062 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.util.function.Consumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void accept(java.lang.Object r7) {
        /*
            r6 = this;
            com.android.server.accessibility.AccessibilityServiceConnection r7 = (com.android.server.accessibility.AccessibilityServiceConnection) r7
            java.lang.Object r6 = r7.mLock
            monitor-enter(r6)
            java.lang.ref.WeakReference r0 = r7.mUserStateWeakReference     // Catch: java.lang.Throwable -> L12
            java.lang.Object r0 = r0.get()     // Catch: java.lang.Throwable -> L12
            com.android.server.accessibility.AccessibilityUserState r0 = (com.android.server.accessibility.AccessibilityUserState) r0     // Catch: java.lang.Throwable -> L12
            if (r0 != 0) goto L15
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L12
            goto Lb9
        L12:
            r7 = move-exception
            goto Lba
        L15:
            java.util.Set r1 = r0.mBindingServices     // Catch: java.lang.Throwable -> L12
            java.util.Set r2 = r0.mCrashedServices     // Catch: java.lang.Throwable -> L12
            android.content.ComponentName r3 = r7.mComponentName     // Catch: java.lang.Throwable -> L12
            java.util.HashSet r1 = (java.util.HashSet) r1     // Catch: java.lang.Throwable -> L12
            boolean r3 = r1.contains(r3)     // Catch: java.lang.Throwable -> L12
            r4 = 0
            if (r3 != 0) goto L32
            android.content.ComponentName r3 = r7.mComponentName     // Catch: java.lang.Throwable -> L12
            r5 = r2
            java.util.HashSet r5 = (java.util.HashSet) r5     // Catch: java.lang.Throwable -> L12
            boolean r3 = r5.contains(r3)     // Catch: java.lang.Throwable -> L12
            if (r3 == 0) goto L30
            goto L32
        L30:
            r1 = 0
            goto L44
        L32:
            android.content.ComponentName r3 = r7.mComponentName     // Catch: java.lang.Throwable -> L12
            r1.remove(r3)     // Catch: java.lang.Throwable -> L12
            android.content.ComponentName r1 = r7.mComponentName     // Catch: java.lang.Throwable -> L12
            java.util.HashSet r2 = (java.util.HashSet) r2     // Catch: java.lang.Throwable -> L12
            r2.remove(r1)     // Catch: java.lang.Throwable -> L12
            android.accessibilityservice.AccessibilityServiceInfo r1 = r7.mAccessibilityServiceInfo     // Catch: java.lang.Throwable -> L12
            r1.crashed = r4     // Catch: java.lang.Throwable -> L12
            android.accessibilityservice.IAccessibilityServiceClient r1 = r7.mServiceInterface     // Catch: java.lang.Throwable -> L12
        L44:
            if (r1 == 0) goto L5b
            java.util.Set r0 = r0.mEnabledServices     // Catch: java.lang.Throwable -> L12
            android.content.ComponentName r2 = r7.mComponentName     // Catch: java.lang.Throwable -> L12
            java.util.HashSet r0 = (java.util.HashSet) r0     // Catch: java.lang.Throwable -> L12
            boolean r0 = r0.contains(r2)     // Catch: java.lang.Throwable -> L12
            if (r0 != 0) goto L5b
            com.android.server.accessibility.AbstractAccessibilityServiceConnection$SystemSupport r7 = r7.mSystemSupport     // Catch: java.lang.Throwable -> L12
            com.android.server.accessibility.AccessibilityManagerService r7 = (com.android.server.accessibility.AccessibilityManagerService) r7     // Catch: java.lang.Throwable -> L12
            r7.onClientChangeLocked(r4, r4)     // Catch: java.lang.Throwable -> L12
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L12
            goto Lb9
        L5b:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L12
            if (r1 != 0) goto L62
            r7.binderDied()
            goto Lb9
        L62:
            boolean r6 = r7.svcClientTracingEnabled()     // Catch: android.os.RemoteException -> L93
            if (r6 == 0) goto L95
            java.lang.String r6 = "init"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L93
            r0.<init>()     // Catch: android.os.RemoteException -> L93
            r0.append(r7)     // Catch: android.os.RemoteException -> L93
            java.lang.String r2 = ","
            r0.append(r2)     // Catch: android.os.RemoteException -> L93
            int r2 = r7.mId     // Catch: android.os.RemoteException -> L93
            r0.append(r2)     // Catch: android.os.RemoteException -> L93
            java.lang.String r2 = ","
            r0.append(r2)     // Catch: android.os.RemoteException -> L93
            android.util.SparseArray r2 = r7.mOverlayWindowTokens     // Catch: android.os.RemoteException -> L93
            java.lang.Object r2 = r2.get(r4)     // Catch: android.os.RemoteException -> L93
            r0.append(r2)     // Catch: android.os.RemoteException -> L93
            java.lang.String r0 = r0.toString()     // Catch: android.os.RemoteException -> L93
            r7.logTraceSvcClient(r6, r0)     // Catch: android.os.RemoteException -> L93
            goto L95
        L93:
            r6 = move-exception
            goto La3
        L95:
            int r6 = r7.mId     // Catch: android.os.RemoteException -> L93
            android.util.SparseArray r0 = r7.mOverlayWindowTokens     // Catch: android.os.RemoteException -> L93
            java.lang.Object r0 = r0.get(r4)     // Catch: android.os.RemoteException -> L93
            android.os.IBinder r0 = (android.os.IBinder) r0     // Catch: android.os.RemoteException -> L93
            r1.init(r7, r6, r0)     // Catch: android.os.RemoteException -> L93
            goto Lb9
        La3:
            java.lang.String r0 = "AccessibilityServiceConnection"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Error while setting connection for service: "
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            android.util.Slog.w(r0, r1, r6)
            r7.binderDied()
        Lb9:
            return
        Lba:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L12
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.AccessibilityServiceConnection$$ExternalSyntheticLambda1.accept(java.lang.Object):void");
    }
}
