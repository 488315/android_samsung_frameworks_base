package com.android.wm.shell.pip;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.pip.PipMenuControlService;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.pip.phone.PhonePipMenuController$$ExternalSyntheticLambda1;
import com.android.server.LocalServices;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PipMenuControlService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ShellExecutor mMainExecutor;
    public Messenger mMessenger;
    public PhonePipMenuController mPhonePipMenuController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MessageHandler extends Handler {
        public /* synthetic */ MessageHandler(PipMenuControlService pipMenuControlService, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (PipMenuControlService.this.mPhonePipMenuController == null) {
                int i = PipMenuControlService.$r8$clinit;
                Log.e("PipMenuControlService", "mPhonePipMenuController is null");
                return;
            }
            int i2 = message.what;
            final int i3 = 1;
            if (i2 == 1) {
                int i4 = PipMenuControlService.$r8$clinit;
                Log.d("PipMenuControlService", "handle showMenu");
                final int i5 = 0;
                ((HandlerExecutor) PipMenuControlService.this.mMainExecutor).execute(new Runnable(this) { // from class: com.android.wm.shell.pip.PipMenuControlService$MessageHandler$$ExternalSyntheticLambda0
                    public final /* synthetic */ PipMenuControlService.MessageHandler f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i5) {
                            case 0:
                                PipMenuControlService.this.mPhonePipMenuController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(1));
                                break;
                            default:
                                PipMenuControlService.this.mPhonePipMenuController.hideMenu();
                                break;
                        }
                    }
                });
                return;
            }
            if (i2 != 2) {
                super.handleMessage(message);
                return;
            }
            int i6 = PipMenuControlService.$r8$clinit;
            Log.d("PipMenuControlService", "handle hideMenu");
            ((HandlerExecutor) PipMenuControlService.this.mMainExecutor).execute(new Runnable(this) { // from class: com.android.wm.shell.pip.PipMenuControlService$MessageHandler$$ExternalSyntheticLambda0
                public final /* synthetic */ PipMenuControlService.MessageHandler f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
                        case 0:
                            PipMenuControlService.this.mPhonePipMenuController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(1));
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
