package com.samsung.android.server.contextengine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.android.server.SystemService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemContextEngineService extends SystemService {
    public boolean isScreenOn;
    public final AnonymousClass1 mBroadcastReceiver;
    public final Context mContext;
    public final SemContextEngineServiceImpl mSceImpl;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.server.contextengine.SemContextEngineService$1] */
    public SemContextEngineService(Context context) {
        super(context);
        this.isScreenOn = false;
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.contextengine.SemContextEngineService.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (intent != null) {
                    String action = intent.getAction();
                    if ("android.intent.action.SCREEN_ON".equals(action)) {
                        SemContextEngineService semContextEngineService = SemContextEngineService.this;
                        if (semContextEngineService.isScreenOn) {
                            return;
                        }
                        semContextEngineService.isScreenOn = true;
                        Log.d("SemContextEngineService", "ACTION_SCREEN_ON");
                        Intent intent2 = new Intent("com.samsung.android.ce.SCREEN_ON");
                        intent2.setPackage("com.samsung.android.mcfds");
                        SemContextEngineService.this.mContext.sendBroadcast(intent2);
                        return;
                    }
                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
                        SemContextEngineService semContextEngineService2 = SemContextEngineService.this;
                        if (semContextEngineService2.isScreenOn) {
                            semContextEngineService2.isScreenOn = false;
                            Log.d("SemContextEngineService", "ACTION_SCREEN_OFF");
                            Intent intent3 = new Intent("com.samsung.android.ce.SCREEN_OFF");
                            intent3.setPackage("com.samsung.android.mcfds");
                            SemContextEngineService.this.mContext.sendBroadcast(intent3);
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                        Log.d("SemContextEngineService", "ACTION_AIRPLANE_MODE_CHANGED");
                        Intent intent4 = new Intent("com.samsung.android.ce.AIRPLANE_MODE");
                        intent4.setPackage("com.samsung.android.mcfds");
                        intent4.putExtras(intent.getExtras());
                        SemContextEngineService.this.mContext.sendBroadcast(intent4);
                        return;
                    }
                    if ("android.telephony.action.SIM_CARD_STATE_CHANGED".equals(action)) {
                        Log.d("SemContextEngineService", "ACTION_SIM_CARD_STATE_CHANGED");
                        Intent intent5 = new Intent("com.samsung.android.ce.SIM_CARD_STATE_CHANGED");
                        intent5.setPackage("com.samsung.android.mcfds");
                        intent5.putExtras(intent.getExtras());
                        SemContextEngineService.this.mContext.sendBroadcast(intent5);
                    }
                }
            }
        };
        Log.i("SemContextEngineService", "SemContextEngineService create");
        this.mSceImpl = new SemContextEngineServiceImpl(context);
        this.mContext = context;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Log.i("SemContextEngineService", "onStart");
        publishBinderService("SemContextEngineService", this.mSceImpl);
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        Log.d("SemContextEngineService", "onUserUnlocking : " + targetUser.getUserHandle().toString());
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.telephony.action.SIM_CARD_STATE_CHANGED");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, null, null);
    }
}
