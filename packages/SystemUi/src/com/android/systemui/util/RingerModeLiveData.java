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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RingerModeLiveData extends MutableLiveData {
    public final BroadcastDispatcher broadcastDispatcher;
    public final Executor executor;
    public final IntentFilter filter;
    public final Function0 getter;
    public boolean initialSticky;
    public final RingerModeLiveData$receiver$1 receiver = new BroadcastReceiver() { // from class: com.android.systemui.util.RingerModeLiveData$receiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
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

    @Override // androidx.lifecycle.LiveData
    public final void onActive() {
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.receiver, this.filter, this.executor, UserHandle.ALL, 0, null, 48);
        this.executor.execute(new Runnable() { // from class: com.android.systemui.util.RingerModeLiveData$onActive$1
            @Override // java.lang.Runnable
            public final void run() {
                RingerModeLiveData ringerModeLiveData = RingerModeLiveData.this;
                ringerModeLiveData.postValue(ringerModeLiveData.getter.invoke());
            }
        });
    }

    @Override // androidx.lifecycle.LiveData
    public final void onInactive() {
        this.broadcastDispatcher.unregisterReceiver(this.receiver);
    }

    @Override // androidx.lifecycle.LiveData
    public final Integer getValue() {
        Integer num = (Integer) super.getValue();
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }
}
