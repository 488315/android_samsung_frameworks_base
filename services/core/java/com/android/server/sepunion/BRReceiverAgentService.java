package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DrmEventService;

import com.samsung.android.sepunion.IBRReceiverAgent;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class BRReceiverAgentService extends IBRReceiverAgent.Stub
        implements AbsSemSystemService {
    public static final Object mStartingServiceSync = new Object();
    public final AnonymousClass1 mBootCompleteReceiver = new AnonymousClass1();
    public final Context mContext;

    /* renamed from: com.android.server.sepunion.BRReceiverAgentService$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                new Thread(
                                new Runnable() { // from class:
                                    // com.android.server.sepunion.BRReceiverAgentService$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        BRReceiverAgentService bRReceiverAgentService =
                                                BRReceiverAgentService.this;
                                        Object obj = BRReceiverAgentService.mStartingServiceSync;
                                        bRReceiverAgentService.getClass();
                                        Intent intent2 = new Intent();
                                        intent2.setClass(
                                                bRReceiverAgentService.mContext,
                                                DrmEventService.class);
                                        synchronized (BRReceiverAgentService.mStartingServiceSync) {
                                            bRReceiverAgentService.mContext.startService(intent2);
                                        }
                                        bRReceiverAgentService.mContext.sendBroadcastAsUser(
                                                BatteryService$$ExternalSyntheticOutline0.m(
                                                        -1996488704,
                                                        "com.samsung.intent.action.LAZY_BOOT_COMPLETE"),
                                                UserHandle.CURRENT);
                                    }
                                })
                        .start();
            }
        }
    }

    public BRReceiverAgentService(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {}

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
            intentFilter.setPriority(-1000);
            this.mContext.registerReceiverAsUser(
                    this.mBootCompleteReceiver, UserHandle.ALL, intentFilter, null, null);
        }
    }

    public final void onCleanupUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {}

    public final void onDestroy() {}

    public final void onStart() {}

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}
}
