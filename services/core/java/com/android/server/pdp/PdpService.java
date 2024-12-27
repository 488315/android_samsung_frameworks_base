package com.android.server.pdp;

import android.content.Context;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Slog;
import android.widget.Toast;
import com.android.server.SystemService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PdpService extends SystemService {
    public final Context mContext;
    public SvcPdpHandler mSvcPdpHandler;
    public final AnonymousClass1 mSvcPdpThread;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SvcPdpHandler extends Handler {
        public SvcPdpHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            if (message.what != 1) {
                return;
            }
            Slog.d("PdpService", "[PDP] handleMessage MSG_DISPLAY_PDPTOAST >");
            String str2 = SystemProperties.get("sys.pdp.last_job", "XXXX");
            String str3 = SystemProperties.get("ro.boot.carrierid", "XXXX");
            String str4 = SystemProperties.get("sys.pdp.toasted", "XXXX");
            boolean z = !str2.equals("XXXX");
            boolean equals = str3.equals("XAC");
            boolean equals2 = str3.equals("XAU");
            boolean z2 = !str4.equals("toasted");
            StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("[PDP] ", str2, " : ", str3, " : ");
            m.append(str4);
            Slog.d("PdpService", m.toString());
            Slog.d("PdpService", "[PDP] pdpInfoChecked : " + z);
            Slog.d("PdpService", "[PDP] pdpCarrierIsXAC : " + equals);
            Slog.d("PdpService", "[PDP] pdpCarrierIsXAU : " + equals2);
            Slog.d("PdpService", "[PDP] pdpToastChecked : " + z2);
            if (z2 && z) {
                SystemProperties.set("sys.pdp.toasted", "toasted");
                Slog.d("PdpService", "[PDP] Prepare Toast");
                PdpService pdpService = PdpService.this;
                if (equals || equals2) {
                    if (str2.equals("backup_fail_suw") || str2.equals("setupwizard")) {
                        str = "PdpService";
                        Slog.d(str, "[PDP] backup-fail toast is not allowed");
                    } else if (str2.equals("backup_fail_BFNE")) {
                        pdpService.handleDisplayPDPToast(1, "[PDP]\n system backup: on\n manual backup: NG [file not exist]");
                    } else if (str2.equals("backup_fail_cache")) {
                        pdpService.handleDisplayPDPToast(1, "[PDP]\n system backup: on\n manual backup: NG [/c size]");
                    } else if (str2.equals("backup_fail_etc")) {
                        pdpService.handleDisplayPDPToast(2, "[PDP]\n system backup: on\n manual backup: NG ETC");
                    } else if (str2.equals("backup_done")) {
                        pdpService.handleDisplayPDPToast(1, "[PDP]\n system backup: on\n manual backup: done");
                    } else if (str2.equals("backup_narrow_suc")) {
                        pdpService.handleDisplayPDPToast(2, "[PDP]\n system backup: on\n manual backup: [!] narrow success");
                    } else if (str2.equals("remove-bkup")) {
                        pdpService.handleDisplayPDPToast(1, "[PDP]\n system backup: on\n manual backup cancel: done");
                    } else if (str2.equals("restore_fail")) {
                        str = "PdpService";
                        Slog.d(str, "[PDP] restore-fail toast is not allowed");
                    } else {
                        str = "PdpService";
                        if (str2.equals("restore_done")) {
                            Slog.d(str, "[PDP] restore-done toast is not allowed");
                        } else if (str2.equals("restore_narrow_suc")) {
                            pdpService.handleDisplayPDPToast(2, "[PDP]\nrestore : narrow success\n\n [ ! ]\n size issue");
                        } else if (str2.equals("detect-bkup")) {
                            Slog.d(str, "[PDP] detect-backup toast is not allowed");
                        }
                    }
                } else if (str2.equals("backup_fail_suw") || str2.equals("setupwizard")) {
                    pdpService.handleDisplayPDPToast(5, "[PDP]\nback-up : fail\nsetup-wizard [ FINISH ]");
                } else if (str2.equals("backup_fail_BFNE")) {
                    pdpService.handleDisplayPDPToast(5, "[PDP]\nback-up : fail\nNo backup file");
                } else if (str2.equals("backup_fail_cache")) {
                    pdpService.handleDisplayPDPToast(5, "[PDP]\nback-up : fail\n/c partition size");
                } else if (str2.equals("backup_fail_etc")) {
                    pdpService.handleDisplayPDPToast(5, "[PDP]\nback-up : fail\n ETC");
                } else if (str2.equals("backup_done")) {
                    pdpService.handleDisplayPDPToast(3, "[PDP]\nback-up : success");
                } else if (str2.equals("backup_narrow_suc")) {
                    pdpService.handleDisplayPDPToast(9, "[PDP]\nback-up : NRW success\n");
                } else if (str2.equals("remove-bkup")) {
                    pdpService.handleDisplayPDPToast(17, "[PDP]\ndelete the back-up files : done");
                } else if (str2.equals("restore_fail")) {
                    pdpService.handleDisplayPDPToast(86, "[PDP]\nrestore : fail\nPlease download a userdata.img file again.");
                } else if (str2.equals("restore_done")) {
                    pdpService.handleDisplayPDPToast(86, "[PDP]\nrestore : success");
                } else if (str2.equals("restore_narrow_suc")) {
                    pdpService.handleDisplayPDPToast(86, "[PDP]\nrestore : narrow success\n\n [ ! ]\n size issue");
                } else if (str2.equals("detect-bkup")) {
                    pdpService.handleDisplayPDPToast(1, "[PDP]\nback-up files are detected");
                }
                str = "PdpService";
            } else {
                str = "PdpService";
                Slog.d(str, "[PDP] Already Toasted");
            }
            Slog.d(str, "[PDP] handleMessage MSG_DISPLAY_PDPTOAST <");
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.os.HandlerThread, com.android.server.pdp.PdpService$1] */
    public PdpService(Context context) {
        super(context);
        this.mContext = context;
        ?? r1 = new HandlerThread() { // from class: com.android.server.pdp.PdpService.1
            @Override // android.os.HandlerThread
            public final void onLooperPrepared() {
                PdpService pdpService = PdpService.this;
                PdpService pdpService2 = PdpService.this;
                pdpService.mSvcPdpHandler = pdpService2.new SvcPdpHandler(pdpService2.mSvcPdpThread.getLooper());
            }
        };
        this.mSvcPdpThread = r1;
        r1.start();
    }

    public final void handleDisplayPDPToast(int i, String str) {
        Slog.d("PdpService", "[PDP] displayPDPToast start");
        for (int i2 = 1; i2 <= i; i2++) {
            try {
                Toast makeText = Toast.makeText(this.mContext, str, 1);
                makeText.setGravity(16, 0, 0);
                makeText.show();
            } catch (Exception e) {
                Slog.e("PdpService", "[PDP] Toast exception", e);
            }
        }
        Slog.d("PdpService", "[PDP] displayPDPToast end");
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            Slog.d("PdpService", "[PDP] PHASE_BOOT_COMPLETED");
            Slog.d("PdpService", "[PDP] pdpServiceReady >");
            this.mSvcPdpHandler.removeMessages(1);
            this.mSvcPdpHandler.sendMessageDelayed(this.mSvcPdpHandler.obtainMessage(1), 6000L);
            Slog.d("PdpService", "[PDP] pdpServiceReady <");
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Slog.d("PdpService", "[PDP] onStart");
    }
}
