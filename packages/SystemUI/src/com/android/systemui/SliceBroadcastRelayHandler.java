package com.android.systemui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.UserHandle;
import android.util.ArrayMap;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

public final class SliceBroadcastRelayHandler implements CoreStartable {
    public final Executor mBackgroundExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final ArrayMap mRelays = new ArrayMap();
    public final AnonymousClass1 mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.SliceBroadcastRelayHandler.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            SliceBroadcastRelayHandler.this.handleIntent(intent);
        }
    };

    public final class BroadcastRelay extends BroadcastReceiver {
        public final CopyOnWriteArraySet mReceivers = new CopyOnWriteArraySet();
        public final Uri mUri;
        public final UserHandle mUserId;

        public BroadcastRelay(Uri uri) {
            this.mUserId = new UserHandle(ContentProvider.getUserIdFromUri(uri));
            this.mUri = uri;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            Iterator it = this.mReceivers.iterator();
            while (it.hasNext()) {
                intent.setComponent((ComponentName) it.next());
                intent.putExtra("uri", this.mUri.toString());
                context.sendBroadcastAsUser(intent, this.mUserId);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.SliceBroadcastRelayHandler$1] */
    public SliceBroadcastRelayHandler(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mBackgroundExecutor = executor;
    }

    public void handleIntent(Intent intent) {
        BroadcastRelay broadcastRelay;
        BroadcastRelay broadcastRelay2;
        if (!"com.android.settingslib.action.REGISTER_SLICE_RECEIVER".equals(intent.getAction())) {
            if ("com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER".equals(intent.getAction())) {
                Uri uri = (Uri) intent.getParcelableExtra("uri", Uri.class);
                synchronized (this.mRelays) {
                    broadcastRelay = (BroadcastRelay) this.mRelays.remove(uri);
                }
                if (broadcastRelay != null) {
                    this.mContext.unregisterReceiver(broadcastRelay);
                    return;
                }
                return;
            }
            return;
        }
        Uri uri2 = (Uri) intent.getParcelableExtra("uri", Uri.class);
        ComponentName componentName = (ComponentName) intent.getParcelableExtra("receiver", ComponentName.class);
        IntentFilter intentFilter = (IntentFilter) intent.getParcelableExtra("filter", IntentFilter.class);
        synchronized (this.mRelays) {
            try {
                broadcastRelay2 = (BroadcastRelay) this.mRelays.get(uri2);
                if (broadcastRelay2 == null) {
                    broadcastRelay2 = new BroadcastRelay(uri2);
                    this.mRelays.put(uri2, broadcastRelay2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Context context = this.mContext;
        broadcastRelay2.mReceivers.add(componentName);
        context.registerReceiver(broadcastRelay2, intentFilter, 2);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        IntentFilter intentFilter = new IntentFilter("com.android.settingslib.action.REGISTER_SLICE_RECEIVER");
        intentFilter.addAction("com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER");
        Flags.FEATURE_FLAGS.getClass();
        this.mBroadcastDispatcher.registerReceiver(intentFilter, this.mReceiver);
    }
}
