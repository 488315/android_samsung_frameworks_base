package android.util.sysfwutil;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.UEventObserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/* loaded from: classes4.dex */
public class DexObserver {
    private static final String CCIC_DOCK_UEVENT_MATCH = "DEVPATH=/devices/virtual/sec/ccic";
    private static final String TAG = "DexObserverFW";
    private static final String USBPD_IDS_PATH = "/sys/class/sec/ccic/usbpd_ids";
    private static final String USBPD_TYPE_PATH = "/sys/class/sec/ccic/usbpd_type";
    private volatile boolean mDexMode = false;
    private volatile boolean mSemiDexMode = false;
    private final Object mDexStateLock = new Object();
    private final BlockingDeque<DexConnectionListener> mListeners = new LinkedBlockingDeque();
    private boolean mTestModeOn = false;
    private final UEventObserver mDexUEventObserver =
            new UEventObserver() { // from class: android.util.sysfwutil.DexObserver.1
                @Override // android.os.UEventObserver
                public void onUEvent(UEventObserver.UEvent event) {
                    try {
                        Slog.d(DexObserver.TAG, "UEventObserver, event : " + event);
                        DexObserver.this.setDexState(
                                Integer.parseInt(event.get("SWITCH_STATE")), event);
                    } catch (NumberFormatException e) {
                        Slog.e(DexObserver.TAG, "Could not parse switch state from event " + event);
                    }
                }
            };

    public DexObserver() {
        Slog.d(TAG, "Started" + (this.mTestModeOn ? " TestModeOn" : ""));
        checkDexStatebySysfs();
        this.mDexUEventObserver.startObserving(CCIC_DOCK_UEVENT_MATCH);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDexState(int state, UEventObserver.UEvent event) {
        Slog.d(TAG, "setDockState() : " + state);
        switch (state) {
            case 109:
            case 110:
            case 111:
            case 114:
                this.mDexMode = true;
                break;
            case 200:
                String s = event.get("USBPD_IDS");
                if (s != null && s.equals("04e8:a027")) {
                    this.mSemiDexMode = true;
                    break;
                }
                break;
            default:
                this.mDexMode = false;
                this.mSemiDexMode = false;
                break;
        }
        onUpdateDexMode();
    }

    private void checkDexStatebySysfs() {
        FileReader fr1;
        BufferedReader br1;
        String mVIDPID = null;
        String mUsbType = null;
        try {
            File usb_usbpd_ids_fd = new File(USBPD_IDS_PATH);
            File usb_usbpd_type_fd = new File(USBPD_TYPE_PATH);
            if (usb_usbpd_ids_fd.exists()) {
                fr1 = new FileReader(USBPD_IDS_PATH);
                try {
                    br1 = new BufferedReader(fr1);
                    try {
                        mVIDPID = br1.readLine();
                        br1.close();
                        fr1.close();
                    } finally {
                        try {
                            br1.close();
                        } catch (Throwable th) {
                            th.addSuppressed(th);
                        }
                    }
                } finally {
                }
            } else {
                Slog.e(TAG, "USBPD IDS File does not exist");
            }
            if (usb_usbpd_type_fd.exists()) {
                fr1 = new FileReader(USBPD_TYPE_PATH);
                try {
                    br1 = new BufferedReader(fr1);
                    try {
                        mUsbType = br1.readLine();
                        br1.close();
                        fr1.close();
                    } finally {
                    }
                } finally {
                }
            } else {
                Slog.e(TAG, "USBPD TYPE File does not exist");
            }
            if (mVIDPID != null && mUsbType != null) {
                if (this.mTestModeOn) {
                    Slog.d(
                            TAG,
                            "checkDexStatebySysfs() USBPD_IDS["
                                    + mVIDPID
                                    + "], USBPD_TYPE["
                                    + mUsbType
                                    + NavigationBarInflaterView.SIZE_MOD_END);
                }
            } else {
                Slog.d(TAG, "checkDexStatebySysfs() USBPD_IDS or USBPD_TYPE is NULL!!");
            }
            if ("200".equals(mUsbType) && "04e8:a027".equals(mVIDPID)) {
                if (this.mTestModeOn) {
                    Slog.d(TAG, "checkDexStatebySysfs() : SEMI DEX MODE is ON");
                }
                this.mSemiDexMode = true;
            } else if ("114".equals(mUsbType)) {
                if (this.mTestModeOn) {
                    Slog.d(TAG, "checkDexStatebySysfs() : DEX MODE is ON");
                }
                this.mDexMode = true;
            } else {
                this.mDexMode = false;
                this.mSemiDexMode = false;
            }
            if (this.mTestModeOn) {
                Slog.d(TAG, "checkDexStatebySysfs() : Update DeX Connection State");
            }
            onUpdateDexMode();
        } catch (FileNotFoundException fnfe) {
            Slog.e(TAG, "File not Found exception: " + fnfe.getMessage());
        } catch (IOException ioe) {
            Slog.e(TAG, "IOException: " + ioe.getMessage());
        } catch (IllegalArgumentException iae) {
            Slog.e(TAG, "IllegalArgumentException: " + iae.getMessage());
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [android.util.sysfwutil.DexObserver$2] */
    private void onUpdateDexMode() {
        if (this.mTestModeOn) {
            Slog.d(TAG, "setDexMode() : delay ++");
            try {
                Thread.sleep(30000L);
            } catch (InterruptedException e) {
            }
            Slog.d(TAG, "setDexMode() : delay --");
        }
        synchronized (this.mDexStateLock) {
            Slog.d(
                    TAG,
                    "setDexMode() : mDexMode "
                            + this.mDexMode
                            + " mSemiDexMode "
                            + this.mSemiDexMode);
            if (this.mDexMode || this.mSemiDexMode) {
                new Thread("notifyListeners") { // from class: android.util.sysfwutil.DexObserver.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        for (DexConnectionListener li : DexObserver.this.mListeners) {
                            li.onConnect();
                        }
                    }
                }.start();
            }
        }
    }

    public boolean isDexModeOn() {
        boolean z;
        synchronized (this.mDexStateLock) {
            Slog.d(TAG, "isDexModeOn() : " + this.mDexMode);
            z = this.mDexMode;
        }
        return z;
    }

    public boolean isSemiDexModeOn() {
        boolean z;
        synchronized (this.mDexStateLock) {
            Slog.d(TAG, "isSemiDexModeOn() : " + this.mSemiDexMode);
            z = this.mSemiDexMode;
        }
        return z;
    }

    public void addListener(DexConnectionListener listener) {
        this.mListeners.add(listener);
    }

    public void dump(PrintWriter pw) {
        synchronized (this.mDexStateLock) {
            pw.println("Current DexModeObserver state of DeXMode :" + this.mDexMode);
            pw.println("Current DexModeObserver state of SemiDeXMode :" + this.mSemiDexMode);
        }
    }
}
