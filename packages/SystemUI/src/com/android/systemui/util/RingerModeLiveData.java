package com.android.systemui.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class RingerModeLiveData extends MutableLiveData {
    public static final int $stable = 8;
    private final BroadcastDispatcher broadcastDispatcher;
    private final Executor executor;
    private final IntentFilter filter;
    private final Function0 getter;
    private boolean initialSticky;
    private final RingerModeLiveData$receiver$1 receiver = new BroadcastReceiver() { // from class: com.android.systemui.util.RingerModeLiveData$receiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            RingerModeLiveData.this.initialSticky = isInitialStickyBroadcast();
            RingerModeLiveData.this.postValue(Integer.valueOf(intent.getIntExtra("android.media.EXTRA_RINGER_MODE", -1)));
        }
    };

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.util.RingerModeLiveData$receiver$1] */
    public RingerModeLiveData(BroadcastDispatcher broadcastDispatcher, Executor executor, String str, Function0 function0) {
        this.broadcastDispatcher = broadcastDispatcher;
        this.executor = executor;
        this.getter = function0;
        this.filter = new IntentFilter(str);
    }

    public final boolean getInitialSticky() {
        return this.initialSticky;
    }

    @Override // androidx.lifecycle.LiveData
    public void onActive() {
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.receiver, this.filter, this.executor, UserHandle.ALL, 0, null, 48);
        this.executor.execute(new Runnable() { // from class: com.android.systemui.util.RingerModeLiveData$onActive$1
            @Override // java.lang.Runnable
            public final void run() {
                Function0 function0;
                RingerModeLiveData ringerModeLiveData = RingerModeLiveData.this;
                function0 = ringerModeLiveData.getter;
                ringerModeLiveData.postValue(function0.invoke());
            }
        });
    }

    @Override // androidx.lifecycle.LiveData
    public void onInactive() {
        this.broadcastDispatcher.unregisterReceiver(this.receiver);
    }

    @Override // androidx.lifecycle.LiveData
    public Integer getValue() {
        Integer num = (Integer) super.getValue();
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }
}
