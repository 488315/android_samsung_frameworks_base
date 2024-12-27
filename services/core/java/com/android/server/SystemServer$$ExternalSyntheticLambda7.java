package com.android.server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.IBinder;
import com.android.server.devicepolicy.DevicePolicyManagerService;
import com.android.server.input.InputManagerService;
import com.android.server.media.MediaRouterService;
import com.android.server.net.NetworkManagementService;
import com.android.server.net.NetworkPolicyManagerService;
import com.android.server.net.UrspService;
import com.android.server.timedetector.NetworkTimeUpdateService;
import com.android.server.utils.TimingsTraceAndSlog;
import com.samsung.accessory.manager.SAccessoryManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SystemServer$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ SystemServer f$0;
    public final /* synthetic */ TimingsTraceAndSlog f$1;
    public final /* synthetic */ VcnManagementService f$10;
    public final /* synthetic */ UrspService f$11;
    public final /* synthetic */ HsumBootUserInitializer f$12;
    public final /* synthetic */ IBinder f$13;
    public final /* synthetic */ SAccessoryManager f$14;
    public final /* synthetic */ IBinder f$15;
    public final /* synthetic */ CountryDetectorService f$16;
    public final /* synthetic */ NetworkTimeUpdateService f$17;
    public final /* synthetic */ InputManagerService f$18;
    public final /* synthetic */ TelephonyRegistry f$19;
    public final /* synthetic */ DevicePolicyManagerService.Lifecycle f$2;
    public final /* synthetic */ MediaRouterService f$20;
    public final /* synthetic */ MmsServiceBroker f$21;
    public final /* synthetic */ boolean f$22;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ Context f$4;
    public final /* synthetic */ boolean f$5;
    public final /* synthetic */ ConnectivityManager f$6;
    public final /* synthetic */ NetworkManagementService f$7;
    public final /* synthetic */ NetworkPolicyManagerService f$8;
    public final /* synthetic */ VpnManagerService f$9;

    public /* synthetic */ SystemServer$$ExternalSyntheticLambda7(SystemServer systemServer, TimingsTraceAndSlog timingsTraceAndSlog, DevicePolicyManagerService.Lifecycle lifecycle, boolean z, Context context, boolean z2, ConnectivityManager connectivityManager, NetworkManagementService networkManagementService, NetworkPolicyManagerService networkPolicyManagerService, VpnManagerService vpnManagerService, VcnManagementService vcnManagementService, UrspService urspService, HsumBootUserInitializer hsumBootUserInitializer, IBinder iBinder, SAccessoryManager sAccessoryManager, IBinder iBinder2, CountryDetectorService countryDetectorService, NetworkTimeUpdateService networkTimeUpdateService, InputManagerService inputManagerService, TelephonyRegistry telephonyRegistry, MediaRouterService mediaRouterService, MmsServiceBroker mmsServiceBroker, boolean z3) {
        this.f$0 = systemServer;
        this.f$1 = timingsTraceAndSlog;
        this.f$2 = lifecycle;
        this.f$3 = z;
        this.f$4 = context;
        this.f$5 = z2;
        this.f$6 = connectivityManager;
        this.f$7 = networkManagementService;
        this.f$8 = networkPolicyManagerService;
        this.f$9 = vpnManagerService;
        this.f$10 = vcnManagementService;
        this.f$11 = urspService;
        this.f$12 = hsumBootUserInitializer;
        this.f$13 = iBinder;
        this.f$14 = sAccessoryManager;
        this.f$15 = iBinder2;
        this.f$16 = countryDetectorService;
        this.f$17 = networkTimeUpdateService;
        this.f$18 = inputManagerService;
        this.f$19 = telephonyRegistry;
        this.f$20 = mediaRouterService;
        this.f$21 = mmsServiceBroker;
        this.f$22 = z3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SystemServer.$r8$lambda$Djmghhk0H4gEPRWdl1V39kc2N1M(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, this.f$8, this.f$9, this.f$10, this.f$11, this.f$12, this.f$13, this.f$14, this.f$15, this.f$16, this.f$17, this.f$18, this.f$19, this.f$20, this.f$21, this.f$22);
    }
}
