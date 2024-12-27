package com.android.server.wm;

import android.content.BroadcastReceiver;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ActivityEmbeddedPackageRepository {
    public final ActivityTaskManagerService mAtm;
    public final List mRepository = new ArrayList();
    public final AnonymousClass1 mPackageReceiver = new BroadcastReceiver() { // from class: com.android.server.wm.ActivityEmbeddedPackageRepository.1
        /* JADX WARN: Removed duplicated region for block: B:16:0x004a  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0061  */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r6, android.content.Intent r7) {
            /*
                r5 = this;
                java.lang.String r0 = r7.getAction()
                int r6 = r6.getUserId()
                java.lang.String r1 = "android.intent.action.PACKAGE_ADDED"
                boolean r1 = r1.equals(r0)
                r2 = 0
                if (r1 != 0) goto L21
                java.lang.String r1 = "android.intent.action.PACKAGE_REMOVED"
                boolean r1 = r1.equals(r0)
                if (r1 != 0) goto L21
                java.lang.String r1 = "android.intent.action.PACKAGE_REPLACED"
                boolean r1 = r1.equals(r0)
                if (r1 == 0) goto L2b
            L21:
                android.net.Uri r7 = r7.getData()
                if (r7 == 0) goto L2b
                java.lang.String r2 = r7.getSchemeSpecificPart()
            L2b:
                if (r2 != 0) goto L2e
                return
            L2e:
                com.android.server.wm.ActivityEmbeddedPackageRepository r7 = com.android.server.wm.ActivityEmbeddedPackageRepository.this
                r7.getClass()
                r1 = 1
                r3 = 0
                com.android.server.wm.ActivityTaskManagerService r7 = r7.mAtm     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
                android.content.Context r7 = r7.mContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
                android.content.pm.PackageManager r7 = r7.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
                java.lang.String r4 = "android.window.PROPERTY_ACTIVITY_EMBEDDING_ALLOW_SYSTEM_OVERRIDE"
                android.content.pm.PackageManager$Property r7 = r7.getProperty(r4, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L47
                if (r7 == 0) goto L47
                r7 = r1
                goto L48
            L47:
                r7 = r3
            L48:
                if (r7 == 0) goto L61
                java.lang.String r7 = "android.intent.action.PACKAGE_ADDED"
                if (r0 == r7) goto L52
                java.lang.String r7 = "android.intent.action.PACKAGE_REPLACED"
                if (r0 != r7) goto L89
            L52:
                com.android.server.wm.ActivityEmbeddedPackageRepository r7 = com.android.server.wm.ActivityEmbeddedPackageRepository.this
                com.android.server.wm.ActivityTaskManagerService r7 = r7.mAtm
                com.android.server.wm.MultiTaskingController r7 = r7.mMultiTaskingController
                r7.updateEmbedActivityPackageEnabled(r1, r6, r2, r3)
                com.android.server.wm.ActivityEmbeddedPackageRepository r5 = com.android.server.wm.ActivityEmbeddedPackageRepository.this
                r5.add(r2)
                goto L89
            L61:
                java.lang.String r7 = "android.intent.action.PACKAGE_REMOVED"
                if (r0 != r7) goto L89
                com.android.server.wm.ActivityEmbeddedPackageRepository r7 = com.android.server.wm.ActivityEmbeddedPackageRepository.this
                monitor-enter(r7)
                java.util.List r0 = r7.mRepository     // Catch: java.lang.Throwable -> L86
                java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: java.lang.Throwable -> L86
                boolean r0 = r0.contains(r2)     // Catch: java.lang.Throwable -> L86
                if (r0 != 0) goto L74
                monitor-exit(r7)
                goto L89
            L74:
                java.util.List r0 = r7.mRepository     // Catch: java.lang.Throwable -> L86
                java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch: java.lang.Throwable -> L86
                r0.remove(r2)     // Catch: java.lang.Throwable -> L86
                monitor-exit(r7)
                com.android.server.wm.ActivityEmbeddedPackageRepository r5 = com.android.server.wm.ActivityEmbeddedPackageRepository.this
                com.android.server.wm.ActivityTaskManagerService r5 = r5.mAtm
                com.android.server.wm.MultiTaskingController r5 = r5.mMultiTaskingController
                r5.updateEmbedActivityPackageEnabled(r3, r6, r2, r1)
                goto L89
            L86:
                r5 = move-exception
                monitor-exit(r7)
                throw r5
            L89:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.ActivityEmbeddedPackageRepository.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.wm.ActivityEmbeddedPackageRepository$1] */
    public ActivityEmbeddedPackageRepository(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    public final synchronized void add(String str) {
        if (((ArrayList) this.mRepository).contains(str)) {
            return;
        }
        ((ArrayList) this.mRepository).add(str);
    }
}
