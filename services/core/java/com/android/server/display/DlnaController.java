package com.android.server.display;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.SemDlnaDevice;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.input.KeyboardMetricsCollector;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knoxguard.service.utils.Constants;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DlnaController {
    public final Context mContext;
    public SemDlnaDevice mDevice = new SemDlnaDevice();
    public DlnaClientDeathMonitor mDlnaMonitor;
    public final Handler mHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.DlnaController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    Log.d(
                            "DlnaController",
                            "sendDisconnectionRequestBroadcast::DLNA_DISCONNECTION_REQUEST");
                    Intent intent =
                            new Intent("com.sec.android.screensharing.DLNA_DISCONNECTION_REQUEST");
                    intent.putExtra("uid", ((DlnaController) this.this$0).mDevice.getUid());
                    ((DlnaController) this.this$0)
                            .mContext.sendBroadcastAsUser(
                                    intent,
                                    UserHandle.ALL,
                                    "android.permission.CONFIGURE_WIFI_DISPLAY");
                    break;
                case 1:
                    Intent intent2 = new Intent("com.samsung.intent.action.DLNA_STATUS_CHANGED");
                    int connectionState =
                            ((DlnaController) this.this$0).mDevice.getConnectionState();
                    intent2.putExtra(Constants.JSON_CLIENT_DATA_STATUS, connectionState);
                    intent2.putExtra(
                            LauncherConfigurationInternal.KEY_STATE_BOOLEAN, connectionState);
                    intent2.putExtra(
                            "player_type", ((DlnaController) this.this$0).mDevice.getDlnaType());
                    Log.d(
                            "DlnaController",
                            "sendStatusChangedBroadcast::SEM_ACTION_DLNA_STATUS_CHANGED state : "
                                    + connectionState);
                    ((DlnaController) this.this$0)
                            .mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                    break;
                default:
                    if (DlnaController.this.mDevice.isConnected()) {
                        DlnaController.this.mDevice.setConnectionState(0);
                        DlnaController dlnaController = DlnaController.this;
                        dlnaController.getClass();
                        dlnaController.mHandler.post(new AnonymousClass1(1, dlnaController));
                    }
                    DlnaClientDeathMonitor dlnaClientDeathMonitor =
                            DlnaController.this.mDlnaMonitor;
                    if (dlnaClientDeathMonitor != null) {
                        Log.i("DlnaController", "unlinkToDeath");
                        dlnaClientDeathMonitor.mBinder.unlinkToDeath(dlnaClientDeathMonitor, 0);
                        DlnaController.this.mDlnaMonitor = null;
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DlnaClientDeathMonitor implements IBinder.DeathRecipient {
        public final IBinder mBinder;

        public DlnaClientDeathMonitor(IBinder iBinder, int i) {
            DlnaController.this.getClass();
            Log.d(
                    "DlnaController",
                    "DlnaClientDeathMonitor, playerType : "
                            .concat(
                                    i != 0
                                            ? i != 1
                                                    ? i != 2
                                                            ? i != 3
                                                                    ? KeyboardMetricsCollector
                                                                            .DEFAULT_LANGUAGE_TAG
                                                                    : "Music_chn"
                                                            : "Music"
                                                    : "Image"
                                            : "Video"));
            this.mBinder = iBinder;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException unused) {
                binderDied();
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.i("DlnaController", "binderDied");
            DlnaController.this.mHandler.post(new AnonymousClass1(2, this));
        }
    }

    public DlnaController(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }
}
