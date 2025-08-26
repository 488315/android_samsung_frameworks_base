package com.android.wm.shell.pip;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.android.server.LocalServices;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.pip.PipMenuControlService;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.pip.phone.PhonePipMenuController$$ExternalSyntheticLambda2;

/* loaded from: classes3.dex */
public class PipMenuControlService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ShellExecutor mMainExecutor;
    public Messenger mMessenger;
    public PhonePipMenuController mPhonePipMenuController;

    public class MessageHandler extends Handler {
        public /* synthetic */ MessageHandler(PipMenuControlService pipMenuControlService, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            final int i = 1;
            PipMenuControlService pipMenuControlService = PipMenuControlService.this;
            if (pipMenuControlService.mPhonePipMenuController == null) {
                int i2 = PipMenuControlService.$r8$clinit;
                Log.e("PipMenuControlService", "mPhonePipMenuController is null");
                return;
            }
            int i3 = message.what;
            if (i3 == 1) {
                int i4 = PipMenuControlService.$r8$clinit;
                Log.d("PipMenuControlService", "handle showMenu");
                ShellExecutor shellExecutor = pipMenuControlService.mMainExecutor;
                final int i5 = 0;
                shellExecutor.execute(new Runnable(this) { // from class: com.android.wm.shell.pip.PipMenuControlService$MessageHandler$$ExternalSyntheticLambda0
                    public final /* synthetic */ PipMenuControlService.MessageHandler f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i6 = i5;
                        PipMenuControlService.MessageHandler messageHandler = this.f$0;
                        switch (i6) {
                            case 0:
                                PipMenuControlService.this.mPhonePipMenuController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(1));
                                break;
                            default:
                                PipMenuControlService.this.mPhonePipMenuController.hideMenu();
                                break;
                        }
                    }
                });
                return;
            }
            if (i3 != 2) {
                super.handleMessage(message);
                return;
            }
            int i6 = PipMenuControlService.$r8$clinit;
            Log.d("PipMenuControlService", "handle hideMenu");
            pipMenuControlService.mMainExecutor.execute(new Runnable(this) { // from class: com.android.wm.shell.pip.PipMenuControlService$MessageHandler$$ExternalSyntheticLambda0
                public final /* synthetic */ PipMenuControlService.MessageHandler f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i62 = i;
                    PipMenuControlService.MessageHandler messageHandler = this.f$0;
                    switch (i62) {
                        case 0:
                            PipMenuControlService.this.mPhonePipMenuController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(1));
                            break;
                        default:
                            PipMenuControlService.this.mPhonePipMenuController.hideMenu();
                            break;
                    }
                }
            });
        }

        private MessageHandler() {
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (LocalServices.getService(PipMenuControlService.class) == null) {
            LocalServices.addService(PipMenuControlService.class, this);
        }
        Log.d("PipMenuControlService", "onBind");
        return this.mMessenger.getBinder();
    }

    @Override // android.app.Service
    public final void onCreate() {
        this.mMessenger = new Messenger(new MessageHandler(this, 0));
    }
}
