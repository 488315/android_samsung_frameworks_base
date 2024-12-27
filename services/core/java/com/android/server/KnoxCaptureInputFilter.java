package com.android.server;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Slog;
import android.view.InputEvent;
import android.view.InputFilter;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.android.internal.util.FrameworkStatsLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxCaptureInputFilter extends InputFilter {
    public static final boolean DEBUG = !"user".equals(SystemProperties.get("ro.build.type"));
    public int activeScanDeviceId;
    public final Context context;
    public final ScanEventHandler scanEventHandler;
    public final Set scannerDevices;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScanEventHandler extends Handler {
        public final List activeScanInput;
        public final StringBuilder unicodeCodepoint;

        public ScanEventHandler(Looper looper) {
            super(looper);
            this.activeScanInput = new ArrayList();
            this.unicodeCodepoint = new StringBuilder();
        }

        /* JADX WARN: Removed duplicated region for block: B:76:0x02a1  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x02f6  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x02ae  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x02bb  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r17) {
            /*
                Method dump skipped, instructions count: 1100
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.KnoxCaptureInputFilter.ScanEventHandler.handleMessage(android.os.Message):void");
        }
    }

    public KnoxCaptureInputFilter(Context context) {
        super(context.getMainLooper());
        this.activeScanDeviceId = -1;
        this.context = context;
        this.scanEventHandler =
                new ScanEventHandler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("ScanEventThread")
                                .getLooper());
        this.scannerDevices = new HashSet();
    }

    public final void onInputEvent(InputEvent inputEvent, int i) {
        if (DEBUG) {
            Slog.d(
                    "KnoxCaptureInputFilter",
                    "Received event: " + inputEvent + ", policyFlags=0x" + Integer.toHexString(i));
        }
        if (inputEvent instanceof MotionEvent) {
            super.onInputEvent(inputEvent, i);
            return;
        }
        if (inputEvent instanceof KeyEvent) {
            if (!((HashSet) this.scannerDevices)
                    .contains(Integer.valueOf(inputEvent.getDeviceId()))) {
                super.onInputEvent(inputEvent, i);
                return;
            }
            KeyEvent keyEvent = (KeyEvent) inputEvent;
            if (this.activeScanDeviceId == -1) {
                this.activeScanDeviceId = keyEvent.getDeviceId();
            }
            this.scanEventHandler.removeMessages(852);
            if (keyEvent.getAction() == 0 && keyEvent.getDeviceId() == this.activeScanDeviceId) {
                ScanEventHandler scanEventHandler = this.scanEventHandler;
                scanEventHandler.sendMessage(
                        Message.obtain(
                                scanEventHandler,
                                FrameworkStatsLog.VPN_CONNECTION_REPORTED,
                                new KeyEvent(keyEvent)));
            }
            this.scanEventHandler.sendEmptyMessageDelayed(852, 100L);
        }
    }

    public final void onInstalled() {
        if (DEBUG) {
            Slog.d("KnoxCaptureInputFilter", "KnoxCapture input filter installed");
        }
        super.onInstalled();
    }

    public final void onUninstalled() {
        if (DEBUG) {
            Slog.d("KnoxCaptureInputFilter", "KnoxCapture input filter uninstalled");
        }
        super.onUninstalled();
    }
}
