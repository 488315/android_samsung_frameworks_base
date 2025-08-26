package com.android.systemui.volume.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class BroadcastReceiverManager {
    public final BroadcastDispatcher broadcastDispatcher;
    public final Map broadcastReceiverItemMap;
    public final LogWrapper logWrapper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BroadcastReceiverManager(Context context, LogWrapper logWrapper, BroadcastDispatcher broadcastDispatcher) {
        this.logWrapper = logWrapper;
        this.broadcastDispatcher = broadcastDispatcher;
        BroadcastReceiverType broadcastReceiverType = BroadcastReceiverType.DISPLAY_MANAGER;
        BroadcastReceiverIntentFilterFactory.INSTANCE.getClass();
        int i = 2;
        Pair pair = new Pair(broadcastReceiverType, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType), null, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType2 = BroadcastReceiverType.ALLSOUND_OFF;
        Pair pair2 = new Pair(broadcastReceiverType2, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType2), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType3 = BroadcastReceiverType.MIRROR_LINK;
        Pair pair3 = new Pair(broadcastReceiverType3, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType3), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType4 = BroadcastReceiverType.BUDS_TOGETHER;
        Pair pair4 = new Pair(broadcastReceiverType4, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType4), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType5 = BroadcastReceiverType.MUSIC_SHARE;
        Pair pair5 = new Pair(broadcastReceiverType5, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType5), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType6 = BroadcastReceiverType.DUAL_AUDIO_MODE;
        Pair pair6 = new Pair(broadcastReceiverType6, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType6), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType7 = BroadcastReceiverType.HEADSET_CONNECTION;
        Pair pair7 = new Pair(broadcastReceiverType7, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType7), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType8 = BroadcastReceiverType.A2DP_ACTIVE_DEVICE_CHANGE;
        Pair pair8 = new Pair(broadcastReceiverType8, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType8), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType9 = BroadcastReceiverType.BUDS_ICON_SERVER_CHANGE;
        Pair pair9 = new Pair(broadcastReceiverType9, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType9), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType10 = BroadcastReceiverType.UNINSTALL_PACKAGE;
        Pair pair10 = new Pair(broadcastReceiverType10, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType10), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0));
        BroadcastReceiverType broadcastReceiverType11 = BroadcastReceiverType.STREAM_DEVICES_CHANGED;
        this.broadcastReceiverItemMap = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, pair9, pair10, new Pair(broadcastReceiverType11, new BroadcastReceiverItem(BroadcastReceiverIntentFilterFactory.create(broadcastReceiverType11), 0 == true ? 1 : 0, i, 0 == true ? 1 : 0)));
    }

    public final class BroadcastReceiverItem {
        public final IntentFilter intentFilter;
        public BroadcastReceiver receiver;

        public BroadcastReceiverItem(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.intentFilter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BroadcastReceiverItem)) {
                return false;
            }
            BroadcastReceiverItem broadcastReceiverItem = (BroadcastReceiverItem) obj;
            return Intrinsics.areEqual(this.intentFilter, broadcastReceiverItem.intentFilter) && Intrinsics.areEqual(this.receiver, broadcastReceiverItem.receiver);
        }

        public final int hashCode() {
            int iHashCode = this.intentFilter.hashCode() * 31;
            BroadcastReceiver broadcastReceiver = this.receiver;
            return iHashCode + (broadcastReceiver == null ? 0 : broadcastReceiver.hashCode());
        }

        public final String toString() {
            return "BroadcastReceiverItem(intentFilter=" + this.intentFilter + ", receiver=" + this.receiver + ")";
        }

        public /* synthetic */ BroadcastReceiverItem(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(intentFilter, (i & 2) != 0 ? null : broadcastReceiver);
        }
    }
}
