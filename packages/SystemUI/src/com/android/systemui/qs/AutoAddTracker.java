package com.android.systemui.qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.AutoAddTracker;
import com.android.systemui.util.UserAwareController;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AutoAddTracker implements UserAwareController, Dumpable {
    public static final IntentFilter FILTER;
    public final ArraySet autoAdded = new ArraySet();
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final QSHost qsHost;
    public final AutoAddTracker$restoreReceiver$1 restoreReceiver;
    public Map restoredTiles;
    public final SecureSettings secureSettings;
    public int userId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AutoTile {
        public final int index;
        public final String tileType;

        public AutoTile(int i, String str) {
            this.index = i;
            this.tileType = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AutoTile)) {
                return false;
            }
            AutoTile autoTile = (AutoTile) obj;
            return this.index == autoTile.index && Intrinsics.areEqual(this.tileType, autoTile.tileType);
        }

        public final int hashCode() {
            return this.tileType.hashCode() + (Integer.hashCode(this.index) * 31);
        }

        public final String toString() {
            return "AutoTile(index=" + this.index + ", tileType=" + this.tileType + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Builder {
        public final BroadcastDispatcher broadcastDispatcher;
        public final DumpManager dumpManager;
        public final Executor executor;
        public final Handler handler;
        public final QSHost qsHost;
        public final SecureSettings secureSettings;
        public int userId;

        public Builder(SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, QSHost qSHost, DumpManager dumpManager, Handler handler, Executor executor) {
            this.secureSettings = secureSettings;
            this.broadcastDispatcher = broadcastDispatcher;
            this.qsHost = qSHost;
            this.dumpManager = dumpManager;
            this.handler = handler;
            this.executor = executor;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        FILTER = new IntentFilter("android.os.action.SETTING_RESTORED");
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.AutoAddTracker$restoreReceiver$1] */
    public AutoAddTracker(SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, QSHost qSHost, DumpManager dumpManager, final Handler handler, Executor executor, int i) {
        this.secureSettings = secureSettings;
        this.broadcastDispatcher = broadcastDispatcher;
        this.qsHost = qSHost;
        this.backgroundExecutor = executor;
        this.userId = i;
        new ContentObserver(handler) { // from class: com.android.systemui.qs.AutoAddTracker$contentObserver$1
            public final void onChange(boolean z, Collection collection, int i2, int i3) {
                AutoAddTracker autoAddTracker = AutoAddTracker.this;
                if (i3 != autoAddTracker.userId) {
                    return;
                }
                autoAddTracker.loadTiles();
            }
        };
        this.restoreReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.AutoAddTracker$restoreReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String join;
                Map emptyMap;
                if (Intrinsics.areEqual(intent.getAction(), "android.os.action.SETTING_RESTORED")) {
                    AutoAddTracker autoAddTracker = AutoAddTracker.this;
                    IntentFilter intentFilter = AutoAddTracker.FILTER;
                    autoAddTracker.getClass();
                    String stringExtra = intent.getStringExtra("setting_name");
                    if (stringExtra != null) {
                        int hashCode = stringExtra.hashCode();
                        int i2 = 0;
                        if (hashCode == -81231054) {
                            if (stringExtra.equals("qs_auto_tiles")) {
                                if (autoAddTracker.restoredTiles == null) {
                                    RecordingInputConnection$$ExternalSyntheticOutline0.m(autoAddTracker.userId, "qs_auto_tiles restored before sysui_qs_tiles for user ", "AutoAddTracker");
                                    return;
                                }
                                String stringExtra2 = intent.getStringExtra("new_value");
                                Collection split$default = stringExtra2 != null ? StringsKt__StringsKt.split$default(stringExtra2, new String[]{","}, 0, 6) : EmptyList.INSTANCE;
                                String stringExtra3 = intent.getStringExtra("previous_value");
                                Iterable split$default2 = stringExtra3 != null ? StringsKt__StringsKt.split$default(stringExtra3, new String[]{","}, 0, 6) : EmptyList.INSTANCE;
                                ArrayList arrayList = new ArrayList();
                                for (Object obj : split$default) {
                                    if (!r9.containsKey((String) obj)) {
                                        arrayList.add(obj);
                                    }
                                }
                                if (!arrayList.isEmpty()) {
                                    Log.d("AutoAddTracker", "Removing tiles: " + arrayList);
                                    autoAddTracker.qsHost.removeTiles(arrayList);
                                }
                                synchronized (autoAddTracker.autoAdded) {
                                    autoAddTracker.autoAdded.clear();
                                    autoAddTracker.autoAdded.addAll(CollectionsKt___CollectionsKt.plus(split$default2, split$default));
                                    join = TextUtils.join(",", autoAddTracker.autoAdded);
                                }
                                autoAddTracker.secureSettings.putStringForUser("qs_auto_tiles", join, null, false, autoAddTracker.userId, true);
                                return;
                            }
                            return;
                        }
                        if (hashCode == 837356422 && stringExtra.equals("sysui_qs_tiles")) {
                            String stringExtra4 = intent.getStringExtra("new_value");
                            if (stringExtra4 != null) {
                                List split$default3 = StringsKt__StringsKt.split$default(stringExtra4, new String[]{","}, 0, 6);
                                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default3, 10));
                                for (Object obj2 : split$default3) {
                                    int i3 = i2 + 1;
                                    if (i2 < 0) {
                                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                                        throw null;
                                    }
                                    arrayList2.add(new AutoAddTracker.AutoTile(i2, (String) obj2));
                                    i2 = i3;
                                }
                                int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                                if (mapCapacity < 16) {
                                    mapCapacity = 16;
                                }
                                emptyMap = new LinkedHashMap(mapCapacity);
                                Iterator it = arrayList2.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    emptyMap.put(((AutoAddTracker.AutoTile) next).tileType, next);
                                }
                            } else {
                                Log.w("AutoAddTracker", "Null restored tiles for user " + autoAddTracker.userId);
                                emptyMap = MapsKt__MapsKt.emptyMap();
                            }
                            autoAddTracker.restoredTiles = emptyMap;
                        }
                    }
                }
            }
        };
    }

    @Override // com.android.systemui.util.UserAwareController
    public final void changeUser(UserHandle userHandle) {
        if (userHandle.getIdentifier() == this.userId) {
            return;
        }
        this.broadcastDispatcher.unregisterReceiver(this.restoreReceiver);
        this.userId = userHandle.getIdentifier();
        this.restoredTiles = null;
        loadTiles();
        IntentFilter intentFilter = FILTER;
        Executor executor = this.backgroundExecutor;
        UserHandle of = UserHandle.of(this.userId);
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.restoreReceiver, intentFilter, executor, of, 0, null, 48);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("Current user: ", this.userId, printWriter);
        printWriter.println("Restored tiles: " + this.restoredTiles);
        printWriter.println("Added tiles: " + this.autoAdded);
    }

    @Override // com.android.systemui.util.UserAwareController
    public final int getCurrentUserId() {
        return this.userId;
    }

    public final void loadTiles() {
        synchronized (this.autoAdded) {
            this.autoAdded.clear();
            ArraySet arraySet = this.autoAdded;
            String stringForUser = this.secureSettings.getStringForUser("qs_auto_tiles", this.userId);
            arraySet.addAll(stringForUser != null ? StringsKt__StringsKt.split$default(stringForUser, new String[]{","}, 0, 6) : EmptySet.INSTANCE);
        }
    }
}
