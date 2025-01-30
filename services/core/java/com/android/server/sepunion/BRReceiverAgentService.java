package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import com.android.server.DrmEventService;
import com.android.server.sepunion.BRReceiverAgentService;
import com.samsung.android.sepunion.IBRReceiverAgent;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class BRReceiverAgentService extends IBRReceiverAgent.Stub implements AbsSemSystemService {
    public static final Object mStartingServiceSync = new Object();
    public BroadcastReceiver mBootCompleteReceiver = new C25131();
    public Context mContext;

    @Override // com.android.server.sepunion.AbsSemSystemService
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public void onCreate(Bundle bundle) {
    }

    public BRReceiverAgentService(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public void onBootPhase(int i) {
        if (i == 1000) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
            intentFilter.setPriority(-1000);
            this.mContext.registerReceiverAsUser(this.mBootCompleteReceiver, UserHandle.ALL, intentFilter, null, null);
        }
    }

    /* renamed from: com.android.server.sepunion.BRReceiverAgentService$1 */
    public class C25131 extends BroadcastReceiver {
        public C25131() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                new Thread(new Runnable() { // from class: com.android.server.sepunion.BRReceiverAgentService$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BRReceiverAgentService.C25131.this.lambda$onReceive$0();
                    }
                }).start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0() {
            BRReceiverAgentService.this.checkIsPossibleToSendIntent(getSendingUserId());
        }
    }

    public final void checkIsPossibleToSendIntent(int i) {
        Intent intent = new Intent();
        intent.setClass(this.mContext, DrmEventService.class);
        synchronized (mStartingServiceSync) {
            this.mContext.startService(intent);
        }
        Intent intent2 = new Intent("com.samsung.intent.action.LAZY_BOOT_COMPLETE");
        intent2.addFlags(-1996488704);
        this.mContext.sendBroadcastAsUser(intent2, new UserHandle(i));
    }
}
