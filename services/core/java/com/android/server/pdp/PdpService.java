package com.android.server.pdp;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Slog;
import android.widget.Toast;
import com.android.server.SystemService;

/* loaded from: classes2.dex */
public class PdpService extends SystemService {
    public Context mContext;
    public SvcPdpHandler mSvcPdpHandler;
    public HandlerThread mSvcPdpThread;

    public void handleDisplayPDPToast(String str, int i) {
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

    public final class SvcPdpHandler extends Handler {
        public SvcPdpHandler(Looper looper) {
            super(looper, null, true);
        }

        public long getCacheEmptySpaceRatio() {
            String str = SystemProperties.get("sys.pdp.ftc_ratio", "-1");
            Slog.d("PdpService", "[PDP] BackupFile to Cache Empty Size Ratio, sys.pdp.ftc_ratio= " + str);
            long parseLong = Long.parseLong(str);
            if (0 <= parseLong) {
                return parseLong;
            }
            return -1L;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
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
            Slog.d("PdpService", "[PDP] " + str2 + " : " + str3 + " : " + str4);
            StringBuilder sb = new StringBuilder();
            sb.append("[PDP] pdpInfoChecked : ");
            sb.append(z);
            Slog.d("PdpService", sb.toString());
            Slog.d("PdpService", "[PDP] pdpCarrierIsXAC : " + equals);
            Slog.d("PdpService", "[PDP] pdpCarrierIsXAU : " + equals2);
            Slog.d("PdpService", "[PDP] pdpToastChecked : " + z2);
            if (z2 && z) {
                SystemProperties.set("sys.pdp.toasted", "toasted");
                Slog.d("PdpService", "[PDP] Prepare Toast");
                if (equals || equals2) {
                    if (str2.equals("backup_fail_suw") || str2.equals("setupwizard")) {
                        str = "PdpService";
                        Slog.d(str, "[PDP] backup-fail toast is not allowed");
                    } else if (str2.equals("backup_fail_BFNE")) {
                        PdpService.this.handleDisplayPDPToast("[PDP]\n system backup: on\n manual backup: NG [file not exist]", 1);
                    } else if (str2.equals("backup_fail_cache")) {
                        PdpService.this.handleDisplayPDPToast("[PDP]\n system backup: on\n manual backup: NG [/c size]", 1);
                    } else if (str2.equals("backup_fail_etc")) {
                        PdpService.this.handleDisplayPDPToast("[PDP]\n system backup: on\n manual backup: NG ETC", 2);
                    } else if (str2.equals("backup_done")) {
                        PdpService.this.handleDisplayPDPToast("[PDP]\n system backup: on\n manual backup: done", 1);
                    } else if (str2.equals("backup_narrow_suc")) {
                        PdpService.this.handleDisplayPDPToast("[PDP]\n system backup: on\n manual backup: [!] narrow success", 2);
                    } else if (str2.equals("remove-bkup")) {
                        PdpService.this.handleDisplayPDPToast("[PDP]\n system backup: on\n manual backup cancel: done", 1);
                    } else if (str2.equals("restore_fail")) {
                        str = "PdpService";
                        Slog.d(str, "[PDP] restore-fail toast is not allowed");
                    } else {
                        str = "PdpService";
                        if (str2.equals("restore_done")) {
                            Slog.d(str, "[PDP] restore-done toast is not allowed");
                        } else if (str2.equals("restore_narrow_suc")) {
                            PdpService.this.handleDisplayPDPToast("[PDP]\nrestore : narrow success\n\n [ ! ]\n size issue", 2);
                        } else if (str2.equals("detect-bkup")) {
                            Slog.d(str, "[PDP] detect-backup toast is not allowed");
                        }
                    }
                } else if (str2.equals("backup_fail_suw") || str2.equals("setupwizard")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nback-up : fail\nsetup-wizard [ FINISH ]", 5);
                } else if (str2.equals("backup_fail_BFNE")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nback-up : fail\nNo backup file", 5);
                } else if (str2.equals("backup_fail_cache")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nback-up : fail\n/c partition size", 5);
                } else if (str2.equals("backup_fail_etc")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nback-up : fail\n ETC", 5);
                } else if (str2.equals("backup_done")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nback-up : success", 3);
                } else if (str2.equals("backup_narrow_suc")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nback-up : narrow success\n\n [ ! ]  " + Long.toString(getCacheEmptySpaceRatio()) + " % \n size issue", 9);
                } else if (str2.equals("remove-bkup")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\ndelete the back-up files : done", 17);
                } else if (str2.equals("restore_fail")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nrestore : fail\nPlease download a userdata.img file again.", 86);
                } else if (str2.equals("restore_done")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nrestore : success", 86);
                } else if (str2.equals("restore_narrow_suc")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nrestore : narrow success\n\n [ ! ]\n size issue", 86);
                } else if (str2.equals("detect-bkup")) {
                    PdpService.this.handleDisplayPDPToast("[PDP]\nback-up files are detected", 1);
                }
                str = "PdpService";
            } else {
                str = "PdpService";
                Slog.d(str, "[PDP] Already Toasted");
            }
            Slog.d(str, "[PDP] handleMessage MSG_DISPLAY_PDPTOAST <");
        }
    }

    public void pdpServiceReady() {
        Slog.d("PdpService", "[PDP] pdpServiceReady >");
        this.mSvcPdpHandler.removeMessages(1);
        this.mSvcPdpHandler.sendMessageDelayed(this.mSvcPdpHandler.obtainMessage(1), 6000L);
        Slog.d("PdpService", "[PDP] pdpServiceReady <");
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        Slog.d("PdpService", "[PDP] onStart");
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 1000) {
            Slog.d("PdpService", "[PDP] PHASE_BOOT_COMPLETED");
            pdpServiceReady();
        }
    }

    public PdpService(Context context) {
        super(context);
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("mSvcPdpThread") { // from class: com.android.server.pdp.PdpService.1
            @Override // android.os.HandlerThread
            public void onLooperPrepared() {
                PdpService pdpService = PdpService.this;
                PdpService pdpService2 = PdpService.this;
                pdpService.mSvcPdpHandler = pdpService2.new SvcPdpHandler(pdpService2.mSvcPdpThread.getLooper());
            }
        };
        this.mSvcPdpThread = handlerThread;
        handlerThread.start();
    }
}
