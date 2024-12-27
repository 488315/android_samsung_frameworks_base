package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastSender;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SemRemoteServiceStateManager {
    public static final ArrayList remoteServiceKeySet = new ArrayList(Arrays.asList("mcf_continuity_nearby_device_state", "samsungflow_clipboard_sync_state", "ltw_clipboard_sync_state", "multi_control_connection_state"));
    public final BroadcastSender mBoardcasteSender;
    public final ConnectionStateClearHandler mClipboardClearHandler;
    public final Context mContext;
    public final HashMap mRemoteServiceStateMap;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ConnectionStateClearHandler extends Handler {
        public ConnectionStateClearHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 101) {
                Log.w("SemRemoteServiceStateManager", "ConnectionStateClearHandler received unknown message " + message.what);
            } else {
                ArrayList arrayList = SemRemoteServiceStateManager.remoteServiceKeySet;
                SemRemoteServiceStateManager semRemoteServiceStateManager = SemRemoteServiceStateManager.this;
                semRemoteServiceStateManager.getClass();
                Iterator it = SemRemoteServiceStateManager.remoteServiceKeySet.iterator();
                while (it.hasNext()) {
                    semRemoteServiceStateManager.mRemoteServiceStateMap.put((String) it.next(), 0);
                }
                semRemoteServiceStateManager.mBoardcasteSender.sendBroadcast(new Intent("com.samsung.systemui.clipboard.UPDATE_CONNECTION_STATE"));
            }
            super.handleMessage(message);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DexOnPcConnectionStateChangeObserver extends ContentObserver {
        public DexOnPcConnectionStateChangeObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            int i = Settings.System.getInt(SemRemoteServiceStateManager.this.mContext.getContentResolver(), "dexonpc_connection_state", 0);
            Log.i("SemRemoteServiceStateManager", "Update DexOnPc connection state (" + i + ")");
            SemRemoteServiceStateManager.this.mRemoteServiceStateMap.put("dexonpc_connection_state", Integer.valueOf(i));
            super.onChange(z);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LinkToWindowConnectionStateChangeObserver extends ContentObserver {
        public LinkToWindowConnectionStateChangeObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            int i = Settings.Secure.getInt(SemRemoteServiceStateManager.this.mContext.getContentResolver(), "ltw_clipboard_sync_state", 0);
            Log.i("SemRemoteServiceStateManager", "Update LinkToWindow connection state (" + i + ")");
            SemRemoteServiceStateManager.this.mRemoteServiceStateMap.put("ltw_clipboard_sync_state", Integer.valueOf(i));
            super.onChange(z);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class McfContinuityConnectionStateChangeObserver extends ContentObserver {
        public McfContinuityConnectionStateChangeObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            int i = Settings.Secure.getInt(SemRemoteServiceStateManager.this.mContext.getContentResolver(), "mcf_continuity_nearby_device_state", 0);
            Log.i("SemRemoteServiceStateManager", "Update McfContinuity connection state (" + i + ")");
            SemRemoteServiceStateManager.this.mRemoteServiceStateMap.put("mcf_continuity_nearby_device_state", Integer.valueOf(i));
            super.onChange(z);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MultiControlConnectionStateChangeObserver extends ContentObserver {
        public MultiControlConnectionStateChangeObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            int intForUser = Settings.Secure.getIntForUser(SemRemoteServiceStateManager.this.mContext.getContentResolver(), "multi_control_connection_state", 0, 0);
            Log.i("SemRemoteServiceStateManager", "Update MultiControl connection state (" + intForUser + ")");
            SemRemoteServiceStateManager.this.mRemoteServiceStateMap.put("multi_control_connection_state", Integer.valueOf(intForUser));
            super.onChange(z);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SamsungFlowConnectionStateChangeObserver extends ContentObserver {
        public SamsungFlowConnectionStateChangeObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            int i = Settings.Secure.getInt(SemRemoteServiceStateManager.this.mContext.getContentResolver(), "samsungflow_clipboard_sync_state", 0);
            Log.i("SemRemoteServiceStateManager", "Update SamsungFlow connection state (" + i + ")");
            SemRemoteServiceStateManager.this.mRemoteServiceStateMap.put("samsungflow_clipboard_sync_state", Integer.valueOf(i));
            super.onChange(z);
        }
    }

    public SemRemoteServiceStateManager(Context context, BroadcastSender broadcastSender) {
        this.mContext = context;
        this.mBoardcasteSender = broadcastSender;
        HashMap hashMap = new HashMap();
        this.mRemoteServiceStateMap = hashMap;
        hashMap.put("dexonpc_connection_state", 0);
        Iterator it = remoteServiceKeySet.iterator();
        while (it.hasNext()) {
            this.mRemoteServiceStateMap.put((String) it.next(), 0);
        }
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("dexonpc_connection_state"), false, new DexOnPcConnectionStateChangeObserver());
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("mcf_continuity_nearby_device_state"), false, new McfContinuityConnectionStateChangeObserver());
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("samsungflow_clipboard_sync_state"), false, new SamsungFlowConnectionStateChangeObserver());
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("ltw_clipboard_sync_state"), false, new LinkToWindowConnectionStateChangeObserver());
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("multi_control_connection_state"), false, new MultiControlConnectionStateChangeObserver());
        HandlerThread handlerThread = new HandlerThread("SemRemoteServiceStateManager");
        handlerThread.start();
        this.mClipboardClearHandler = new ConnectionStateClearHandler(handlerThread.getThreadHandler().getLooper());
    }
}
