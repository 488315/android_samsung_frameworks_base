package android.util.sysfwutil;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.UEventObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/* loaded from: classes4.dex */
public class DexObserver {
    private static final String CCIC_DOCK_UEVENT_MATCH = "DEVPATH=/devices/virtual/sec/ccic";
    private static final String TAG = "DexObserverFW";
    private static final String USBPD_IDS_PATH = "/sys/class/sec/ccic/usbpd_ids";
    private static final String USBPD_TYPE_PATH = "/sys/class/sec/ccic/usbpd_type";
    private final UEventObserver mDexUEventObserver;
    private volatile boolean mDexMode = false;
    private volatile boolean mSemiDexMode = false;
    private final Object mDexStateLock = new Object();
    private final BlockingDeque<DexConnectionListener> mListeners = new LinkedBlockingDeque();
    private boolean mTestModeOn = false;

    public DexObserver() {
        UEventObserver uEventObserver = new UEventObserver() { // from class: android.util.sysfwutil.DexObserver.1
            @Override // android.os.UEventObserver
            public void onUEvent(UEventObserver.UEvent uEvent) {
                try {
                    Slog.d(DexObserver.TAG, "UEventObserver, event : " + uEvent);
                    DexObserver.this.setDexState(Integer.parseInt(uEvent.get("SWITCH_STATE")), uEvent);
                } catch (NumberFormatException unused) {
                    Slog.e(DexObserver.TAG, "Could not parse switch state from event " + uEvent);
                }
            }
        };
        this.mDexUEventObserver = uEventObserver;
        Slog.d(TAG, "Started".concat(this.mTestModeOn ? " TestModeOn" : ""));
        checkDexStatebySysfs();
        uEventObserver.startObserving(CCIC_DOCK_UEVENT_MATCH);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDexState(int i, UEventObserver.UEvent uEvent) {
        Slog.d(TAG, "setDockState() : " + i);
        if (i != 114) {
            if (i == 200) {
                String str = uEvent.get("USBPD_IDS");
                if (str != null && str.equals("04e8:a027")) {
                    this.mSemiDexMode = true;
                }
            } else {
                switch (i) {
                    case 109:
                    case 110:
                    case 111:
                        break;
                    default:
                        this.mDexMode = false;
                        this.mSemiDexMode = false;
                        break;
                }
            }
            onUpdateDexMode();
        }
        this.mDexMode = true;
        onUpdateDexMode();
    }

    private void checkDexStatebySysfs() {
        String str;
        FileReader fileReader;
        BufferedReader bufferedReader;
        try {
            File file = new File(USBPD_IDS_PATH);
            File file2 = new File(USBPD_TYPE_PATH);
            String str2 = null;
            if (file.exists()) {
                fileReader = new FileReader(USBPD_IDS_PATH);
                try {
                    bufferedReader = new BufferedReader(fileReader);
                    try {
                        str = bufferedReader.readLine();
                        bufferedReader.close();
                        fileReader.close();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th) {
                            th.addSuppressed(th);
                        }
                    }
                } finally {
                }
            } else {
                Slog.e(TAG, "USBPD IDS File does not exist");
                str = null;
            }
            if (file2.exists()) {
                fileReader = new FileReader(USBPD_TYPE_PATH);
                try {
                    bufferedReader = new BufferedReader(fileReader);
                    try {
                        str2 = bufferedReader.readLine();
                        bufferedReader.close();
                        fileReader.close();
                    } finally {
                    }
                } finally {
                }
            } else {
                Slog.e(TAG, "USBPD TYPE File does not exist");
            }
            if (str == null || str2 == null) {
                Slog.d(TAG, "checkDexStatebySysfs() USBPD_IDS or USBPD_TYPE is NULL!!");
            } else if (this.mTestModeOn) {
                Slog.d(TAG, "checkDexStatebySysfs() USBPD_IDS[" + str + "], USBPD_TYPE[" + str2 + NavigationBarInflaterView.SIZE_MOD_END);
            }
            if ("200".equals(str2) && "04e8:a027".equals(str)) {
                if (this.mTestModeOn) {
                    Slog.d(TAG, "checkDexStatebySysfs() : SEMI DEX MODE is ON");
                }
                this.mSemiDexMode = true;
            } else if ("114".equals(str2)) {
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
        } catch (FileNotFoundException e) {
            Slog.e(TAG, "File not Found exception: " + e.getMessage());
        } catch (IOException e2) {
            Slog.e(TAG, "IOException: " + e2.getMessage());
        } catch (IllegalArgumentException e3) {
            Slog.e(TAG, "IllegalArgumentException: " + e3.getMessage());
        }
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [android.util.sysfwutil.DexObserver$2] */
    private void onUpdateDexMode() {
        if (this.mTestModeOn) {
            Slog.d(TAG, "setDexMode() : delay ++");
            try {
                Thread.sleep(30000L);
            } catch (InterruptedException unused) {
            }
            Slog.d(TAG, "setDexMode() : delay --");
        }
        synchronized (this.mDexStateLock) {
            Slog.d(TAG, "setDexMode() : mDexMode " + this.mDexMode + " mSemiDexMode " + this.mSemiDexMode);
            if (this.mDexMode || this.mSemiDexMode) {
                new Thread("notifyListeners") { // from class: android.util.sysfwutil.DexObserver.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        Iterator it = DexObserver.this.mListeners.iterator();
                        while (it.hasNext()) {
                            ((DexConnectionListener) it.next()).onConnect();
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

    public void addListener(DexConnectionListener dexConnectionListener) {
        this.mListeners.add(dexConnectionListener);
    }

    public void dump(PrintWriter printWriter) {
        synchronized (this.mDexStateLock) {
            printWriter.println("Current DexModeObserver state of DeXMode :" + this.mDexMode);
            printWriter.println("Current DexModeObserver state of SemiDeXMode :" + this.mSemiDexMode);
        }
    }
}
