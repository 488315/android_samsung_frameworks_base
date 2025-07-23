package com.android.systemui.statusbar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ KeyguardIndicationController f$0;

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0074, code lost:
    
        if (r2.getDeviceOwnerType(r2.getDeviceOwnerComponentOnAnyUser()) == 1) goto L26;
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r5 = this;
            com.android.systemui.statusbar.KeyguardIndicationController r5 = r5.f$0
            android.app.admin.DevicePolicyManager r0 = r5.mDevicePolicyManager
            boolean r0 = r0.isDeviceManaged()
            if (r0 == 0) goto L11
            android.app.admin.DevicePolicyManager r0 = r5.mDevicePolicyManager
            java.lang.CharSequence r0 = r0.getDeviceOwnerOrganizationName()
            goto L4a
        L11:
            android.app.admin.DevicePolicyManager r0 = r5.mDevicePolicyManager
            boolean r0 = r0.isOrganizationOwnedDeviceWithManagedProfile()
            r1 = 0
            if (r0 == 0) goto L42
            int r0 = android.os.UserHandle.myUserId()
            android.os.UserManager r2 = r5.mUserManager
            java.util.List r0 = r2.getProfiles(r0)
            java.util.Iterator r0 = r0.iterator()
        L28:
            boolean r2 = r0.hasNext()
            r3 = -10000(0xffffffffffffd8f0, float:NaN)
            if (r2 == 0) goto L3f
            java.lang.Object r2 = r0.next()
            android.content.pm.UserInfo r2 = (android.content.pm.UserInfo) r2
            boolean r4 = r2.isManagedProfile()
            if (r4 == 0) goto L28
            int r0 = r2.id
            goto L40
        L3f:
            r0 = r3
        L40:
            if (r0 != r3) goto L44
        L42:
            r0 = r1
            goto L4a
        L44:
            android.app.admin.DevicePolicyManager r1 = r5.mDevicePolicyManager
            java.lang.CharSequence r0 = r1.getOrganizationNameForUser(r0)
        L4a:
            android.content.Context r1 = r5.mContext
            android.content.res.Resources r1 = r1.getResources()
            java.lang.String r2 = "device_policy_manager"
            java.lang.String r3 = "add-isfinanced-device"
            r4 = 1
            boolean r2 = android.provider.DeviceConfig.getBoolean(r2, r3, r4)
            if (r2 == 0) goto L62
            android.app.admin.DevicePolicyManager r2 = r5.mDevicePolicyManager
            boolean r2 = r2.isFinancedDevice()
            goto L79
        L62:
            android.app.admin.DevicePolicyManager r2 = r5.mDevicePolicyManager
            boolean r2 = r2.isDeviceManaged()
            if (r2 == 0) goto L77
            android.app.admin.DevicePolicyManager r2 = r5.mDevicePolicyManager
            android.content.ComponentName r3 = r2.getDeviceOwnerComponentOnAnyUser()
            int r2 = r2.getDeviceOwnerType(r3)
            if (r2 != r4) goto L77
            goto L78
        L77:
            r4 = 0
        L78:
            r2 = r4
        L79:
            if (r0 != 0) goto L8e
            android.app.admin.DevicePolicyManager r0 = r5.mDevicePolicyManager
            android.app.admin.DevicePolicyResourcesManager r0 = r0.getResources()
            com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda5 r2 = new com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda5
            r3 = 1
            r2.<init>(r1, r3)
            java.lang.String r1 = "SystemUi.KEYGUARD_MANAGEMENT_DISCLOSURE"
            java.lang.String r0 = r0.getString(r1, r2)
            goto Lb1
        L8e:
            if (r2 == 0) goto L9c
            r2 = 2131953142(0x7f1305f6, float:1.9542747E38)
            java.lang.Object[] r0 = new java.lang.Object[]{r0}
            java.lang.String r0 = r1.getString(r2, r0)
            goto Lb1
        L9c:
            android.app.admin.DevicePolicyManager r2 = r5.mDevicePolicyManager
            android.app.admin.DevicePolicyResourcesManager r2 = r2.getResources()
            com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda9 r3 = new com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda9
            r3.<init>()
            java.lang.Object[] r0 = new java.lang.Object[]{r0}
            java.lang.String r1 = "SystemUi.KEYGUARD_NAMED_MANAGEMENT_DISCLOSURE"
            java.lang.String r0 = r2.getString(r1, r3, r0)
        Lb1:
            com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda7 r1 = new com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda7
            r1.<init>()
            com.android.systemui.util.concurrency.DelayableExecutor r5 = r5.mExecutor
            r5.execute(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda6.run():void");
    }
}
