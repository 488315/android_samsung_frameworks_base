package com.android.systemui.controls.management;

import android.content.BroadcastReceiver;
import android.os.Handler;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.SecControlsController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlsRequestReceiver extends BroadcastReceiver {
    public static final Companion Companion = new Companion(null);
    public final ControlsController controller;
    public final Handler handler;
    public final SecControlsController secController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ControlsRequestReceiver() {
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x008d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008e  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReceive(android.content.Context r10, final android.content.Intent r11) {
        /*
            r9 = this;
            java.lang.String r0 = "android.service.controls.extra.CONTROL"
            java.lang.String r1 = "android.intent.extra.COMPONENT_NAME"
            java.lang.String r2 = "ControlsRequestReceiver"
            android.content.pm.PackageManager r3 = r10.getPackageManager()
            java.lang.String r4 = "android.software.controls"
            boolean r3 = r3.hasSystemFeature(r4)
            if (r3 != 0) goto L13
            return
        L13:
            java.lang.Class<android.content.ComponentName> r3 = android.content.ComponentName.class
            java.lang.Object r3 = r11.getParcelableExtra(r1, r3)     // Catch: java.lang.Exception -> Ld1
            android.content.ComponentName r3 = (android.content.ComponentName) r3     // Catch: java.lang.Exception -> Ld1
            if (r3 != 0) goto L23
            java.lang.String r9 = "Null target component"
            android.util.Log.e(r2, r9)
            return
        L23:
            java.lang.Class<android.service.controls.Control> r4 = android.service.controls.Control.class
            java.lang.Object r4 = r11.getParcelableExtra(r0, r4)     // Catch: java.lang.Exception -> Lca
            android.service.controls.Control r4 = (android.service.controls.Control) r4     // Catch: java.lang.Exception -> Lca
            if (r4 != 0) goto L33
            java.lang.String r9 = "Null control"
            android.util.Log.e(r2, r9)
            return
        L33:
            java.lang.String r5 = r3.getPackageName()
            com.android.systemui.controls.management.ControlsRequestReceiver$Companion r6 = com.android.systemui.controls.management.ControlsRequestReceiver.Companion
            r6.getClass()
            r6 = 0
            android.content.pm.PackageManager r7 = r10.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L74
            int r5 = r7.getPackageUid(r5, r6)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L74
            java.lang.Class<android.app.ActivityManager> r7 = android.app.ActivityManager.class
            java.lang.Object r7 = r10.getSystemService(r7)
            android.app.ActivityManager r7 = (android.app.ActivityManager) r7
            if (r7 == 0) goto L54
            int r7 = r7.getUidImportance(r5)
            goto L56
        L54:
            r7 = 1000(0x3e8, float:1.401E-42)
        L56:
            r8 = 100
            if (r7 == r8) goto L72
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "Uid "
            r7.<init>(r8)
            r7.append(r5)
            java.lang.String r5 = " not in foreground"
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            android.util.Log.w(r2, r5)
        L70:
            r5 = r6
            goto L8b
        L72:
            r5 = 1
            goto L8b
        L74:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "Package "
            r7.<init>(r8)
            r7.append(r5)
            java.lang.String r5 = " not found"
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            android.util.Log.w(r2, r5)
            goto L70
        L8b:
            if (r5 != 0) goto L8e
            return
        L8e:
            java.lang.String r5 = "android.service.controls.extra.CONTROL_AUTO_ADD"
            boolean r5 = r11.getBooleanExtra(r5, r6)
            if (r5 == 0) goto La9
            android.os.Handler r10 = r9.handler
            if (r10 == 0) goto La3
            com.android.systemui.controls.management.ControlsRequestReceiver$onReceive$1 r0 = new com.android.systemui.controls.management.ControlsRequestReceiver$onReceive$1
            r0.<init>()
            r10.post(r0)
            goto La8
        La3:
            java.lang.String r9 = "onReceive handler is null"
            android.util.Log.e(r2, r9)
        La8:
            return
        La9:
            android.content.Intent r9 = new android.content.Intent
            java.lang.Class<com.android.systemui.controls.management.ControlsRequestDialog> r11 = com.android.systemui.controls.management.ControlsRequestDialog.class
            r9.<init>(r10, r11)
            r9.putExtra(r1, r3)
            r9.putExtra(r0, r4)
            r11 = 268566528(0x10020000, float:2.563798E-29)
            r9.addFlags(r11)
            java.lang.String r11 = "android.intent.extra.USER_ID"
            int r0 = r10.getUserId()
            r9.putExtra(r11, r0)
            android.os.UserHandle r11 = android.os.UserHandle.SYSTEM
            r10.startActivityAsUser(r9, r11)
            return
        Lca:
            r9 = move-exception
            java.lang.String r10 = "Malformed intent extra Control"
            android.util.Log.e(r2, r10, r9)
            return
        Ld1:
            r9 = move-exception
            java.lang.String r10 = "Malformed intent extra ComponentName"
            android.util.Log.e(r2, r10, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.management.ControlsRequestReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    public ControlsRequestReceiver(ControlsController controlsController, SecControlsController secControlsController, Handler handler) {
        this();
        this.controller = controlsController;
        this.secController = secControlsController;
        this.handler = handler;
    }
}
