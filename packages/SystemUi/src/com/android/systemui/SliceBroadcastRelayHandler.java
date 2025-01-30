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
import android.util.ArraySet;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceBroadcastRelayHandler implements CoreStartable {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final ArrayMap mRelays = new ArrayMap();
    public final C09841 mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.SliceBroadcastRelayHandler.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            SliceBroadcastRelayHandler.this.handleIntent(intent);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BroadcastRelay extends BroadcastReceiver {
        public final ArraySet mReceivers = new ArraySet();
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
    public SliceBroadcastRelayHandler(Context context, BroadcastDispatcher broadcastDispatcher) {
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    public void handleIntent(Intent intent) {
        BroadcastRelay broadcastRelay;
        boolean equals = "com.android.settingslib.action.REGISTER_SLICE_RECEIVER".equals(intent.getAction());
        Context context = this.mContext;
        ArrayMap arrayMap = this.mRelays;
        if (!equals) {
            if (!"com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER".equals(intent.getAction()) || (broadcastRelay = (BroadcastRelay) arrayMap.remove((Uri) intent.getParcelableExtra("uri"))) == null) {
                return;
            }
            context.unregisterReceiver(broadcastRelay);
            return;
        }
        Uri uri = (Uri) intent.getParcelableExtra("uri");
        ComponentName componentName = (ComponentName) intent.getParcelableExtra("receiver");
        IntentFilter intentFilter = (IntentFilter) intent.getParcelableExtra("filter");
        BroadcastRelay broadcastRelay2 = (BroadcastRelay) arrayMap.get(uri);
        if (broadcastRelay2 == null) {
            broadcastRelay2 = new BroadcastRelay(uri);
            arrayMap.put(uri, broadcastRelay2);
        }
        broadcastRelay2.mReceivers.add(componentName);
        context.registerReceiver(broadcastRelay2, intentFilter, 2);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        IntentFilter intentFilter = new IntentFilter("com.android.settingslib.action.REGISTER_SLICE_RECEIVER");
        intentFilter.addAction("com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER");
        this.mBroadcastDispatcher.registerReceiver(intentFilter, this.mReceiver);
    }
}
