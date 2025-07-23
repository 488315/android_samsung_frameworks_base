package com.android.wm.shell;

import com.android.wm.shell.ShellTaskOrganizer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShellTaskOrganizer$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ShellTaskOrganizer.AnonymousClass1 f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ ShellTaskOrganizer$1$$ExternalSyntheticLambda0(ShellTaskOrganizer.AnonymousClass1 anonymousClass1, String str) {
        this.f$0 = anonymousClass1;
        this.f$1 = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003e  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r7 = this;
            int r0 = r7.$r8$classId
            switch(r0) {
                case 0: goto L6d;
                default: goto L5;
            }
        L5:
            com.android.wm.shell.ShellTaskOrganizer$1 r0 = r7.f$0
            java.lang.String r7 = r7.f$1
            com.android.wm.shell.ShellTaskOrganizer r0 = r0.this$0
            com.android.wm.shell.splitscreen.ForcedResizableInfoActivityController r0 = r0.mForcedResizableController
            r0.getClass()
            long r1 = android.os.SystemClock.elapsedRealtime()
            long r3 = r0.mLastShowingTime
            long r3 = r1 - r3
            r5 = 5000(0x1388, double:2.4703E-320)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 >= 0) goto L1f
            goto L6c
        L1f:
            boolean r3 = android.text.TextUtils.isEmpty(r7)
            if (r3 != 0) goto L3b
            android.content.Context r3 = r0.mContext     // Catch: java.lang.Exception -> L3b
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch: java.lang.Exception -> L3b
            r4 = 0
            android.content.pm.ApplicationInfo r7 = r3.getApplicationInfo(r7, r4)     // Catch: java.lang.Exception -> L3b
            if (r7 == 0) goto L3b
            java.lang.CharSequence r7 = r7.loadLabel(r3)     // Catch: java.lang.Exception -> L3b
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Exception -> L3b
            goto L3c
        L3b:
            r7 = 0
        L3c:
            if (r7 == 0) goto L4c
            android.content.Context r3 = r0.mContext
            r4 = 2131955202(0x7f130e02, float:1.9546925E38)
            java.lang.Object[] r7 = new java.lang.Object[]{r7}
            java.lang.String r7 = r3.getString(r4, r7)
            goto L55
        L4c:
            android.content.Context r7 = r0.mContext
            r3 = 2131955201(0x7f130e01, float:1.9546923E38)
            java.lang.String r7 = r7.getString(r3)
        L55:
            android.content.Context r3 = r0.mContext
            r4 = 1
            android.widget.Toast r7 = android.widget.Toast.makeText(r3, r7, r4)
            r7.show()
            r0.mLastShowingTime = r1
            boolean r7 = com.samsung.android.rune.CoreRune.MW_SA_LOGGING
            if (r7 == 0) goto L6c
            java.lang.String r7 = "1005"
            java.lang.String r0 = "Switch to MW-incompatible app"
            com.samsung.android.core.CoreSaLogger.logForAdvanced(r7, r0)
        L6c:
            return
        L6d:
            com.android.wm.shell.ShellTaskOrganizer$1 r0 = r7.f$0
            java.lang.String r7 = r7.f$1
            com.android.wm.shell.ShellTaskOrganizer r0 = r0.this$0
            com.android.wm.shell.splitscreen.ForcedResizableInfoActivityController r0 = r0.mForcedResizableController
            r0.getClass()
            if (r7 != 0) goto L7b
            goto L8e
        L7b:
            java.lang.String r1 = "com.android.systemui"
            boolean r1 = r1.equals(r7)
            if (r1 == 0) goto L84
            goto L8e
        L84:
            android.util.ArraySet r1 = r0.mPackagesShownInSession
            r1.contains(r7)
            android.util.ArraySet r0 = r0.mPackagesShownInSession
            r0.add(r7)
        L8e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.ShellTaskOrganizer$1$$ExternalSyntheticLambda0.run():void");
    }

    public /* synthetic */ ShellTaskOrganizer$1$$ExternalSyntheticLambda0(ShellTaskOrganizer.AnonymousClass1 anonymousClass1, String str, int i, int i2) {
        this.f$0 = anonymousClass1;
        this.f$1 = str;
    }
}
