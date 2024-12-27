package com.samsung.android.cover;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;

/* loaded from: classes6.dex */
class CoverPowerKeyListenerDelegate extends INfcLedCoverTouchListenerCallback.Stub {
    private static final int MSG_SYSTEM_COVER_EVENT = 0;
    private ListenerDelegateHandler mHandler;
    private CoverManager.CoverPowerKeyListener mListener;

    private static final class SystemEvents {
        private static final String KEY_DISABLE_LCD_OFF_BY_COVER = "lcd_off_disabled_by_cover";
        private static final int LCD_OFF_DISABLED_BY_COVER = 4;
        private static final int LED_OFF = 0;
        private static final int NOTIFICATION_ADD = 2;
        private static final int NOTIFICATION_REMOVE = 3;
        private static final int POWER_BUTTON = 1;

        private SystemEvents() {
        }
    }

    CoverPowerKeyListenerDelegate(CoverManager.CoverPowerKeyListener listener, Handler handler, Context context) {
        this.mListener = listener;
        Looper looper = handler == null ? context.getMainLooper() : handler.getLooper();
        this.mHandler = new ListenerDelegateHandler(looper, this.mListener);
    }

    public Object getListener() {
        return this.mListener;
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTouchAccept() throws RemoteException {
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTouchReject() throws RemoteException {
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapLeft() throws RemoteException {
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapMid() throws RemoteException {
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapRight() throws RemoteException {
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onSystemCoverEvent(int event, Bundle args) throws RemoteException {
        if (event == 1) {
            this.mHandler.sendEmptyMessage(0);
        }
    }

    private static class ListenerDelegateHandler extends Handler {
        private final CoverManager.CoverPowerKeyListener mListener;

        ListenerDelegateHandler(Looper looper, CoverManager.CoverPowerKeyListener listener) {
            super(looper);
            this.mListener = listener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0 && this.mListener != null) {
                this.mListener.onPowerKeyPress();
            }
        }
    }
}
