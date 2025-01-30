package com.android.systemui.p016qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p016qs.AutoAddTracker;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AutoAddTracker implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArraySet autoAdded = new ArraySet();
    public final Executor backgroundExecutor;
    public final QSHost qsHost;
    public Map restoredTiles;
    public final SecureSettings secureSettings;
    public final int userId;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        new IntentFilter("android.os.action.SETTING_RESTORED");
    }

    public AutoAddTracker(SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, QSHost qSHost, DumpManager dumpManager, final Handler handler, Executor executor, int i) {
        this.secureSettings = secureSettings;
        this.qsHost = qSHost;
        this.backgroundExecutor = executor;
        this.userId = i;
        new ContentObserver(handler) { // from class: com.android.systemui.qs.AutoAddTracker$contentObserver$1
            public final void onChange(boolean z, Collection collection, int i2, int i3) {
                AutoAddTracker autoAddTracker = AutoAddTracker.this;
                if (i3 != autoAddTracker.userId) {
                    return;
                }
                synchronized (autoAddTracker.autoAdded) {
                    autoAddTracker.autoAdded.clear();
                    ArraySet arraySet = autoAddTracker.autoAdded;
                    String stringForUser = ((SecureSettingsImpl) autoAddTracker.secureSettings).getStringForUser(autoAddTracker.userId, "qs_auto_tiles");
                    arraySet.addAll(stringForUser != null ? StringsKt__StringsKt.split$default(stringForUser, new String[]{","}, 0, 6) : EmptySet.INSTANCE);
                }
            }
        };
        new BroadcastReceiver() { // from class: com.android.systemui.qs.AutoAddTracker$restoreReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String join;
                Map emptyMap;
                if (Intrinsics.areEqual(intent.getAction(), "android.os.action.SETTING_RESTORED")) {
                    AutoAddTracker autoAddTracker = AutoAddTracker.this;
                    int i2 = AutoAddTracker.$r8$clinit;
                    autoAddTracker.getClass();
                    String stringExtra = intent.getStringExtra("setting_name");
                    int i3 = 0;
                    if (!Intrinsics.areEqual(stringExtra, "sysui_qs_tiles")) {
                        if (Intrinsics.areEqual(stringExtra, "qs_auto_tiles")) {
                            if (autoAddTracker.restoredTiles == null) {
                                IconCompat$$ExternalSyntheticOutline0.m30m("qs_auto_tiles restored before sysui_qs_tiles for user ", autoAddTracker.userId, "AutoAddTracker");
                                return;
                            }
                            String stringExtra2 = intent.getStringExtra("new_value");
                            Collection split$default = stringExtra2 != null ? StringsKt__StringsKt.split$default(stringExtra2, new String[]{","}, 0, 6) : EmptyList.INSTANCE;
                            String stringExtra3 = intent.getStringExtra("previous_value");
                            Iterable split$default2 = stringExtra3 != null ? StringsKt__StringsKt.split$default(stringExtra3, new String[]{","}, 0, 6) : EmptyList.INSTANCE;
                            ArrayList arrayList = new ArrayList();
                            for (Object obj : split$default) {
                                if (!r6.containsKey((String) obj)) {
                                    arrayList.add(obj);
                                }
                            }
                            if (!arrayList.isEmpty()) {
                                autoAddTracker.qsHost.removeTiles(arrayList);
                            }
                            synchronized (autoAddTracker.autoAdded) {
                                autoAddTracker.autoAdded.clear();
                                autoAddTracker.autoAdded.addAll(CollectionsKt___CollectionsKt.plus(split$default2, split$default));
                                join = TextUtils.join(",", autoAddTracker.autoAdded);
                            }
                            autoAddTracker.saveTiles(join);
                            return;
                        }
                        return;
                    }
                    String stringExtra4 = intent.getStringExtra("new_value");
                    if (stringExtra4 != null) {
                        List split$default3 = StringsKt__StringsKt.split$default(stringExtra4, new String[]{","}, 0, 6);
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default3, 10));
                        for (Object obj2 : split$default3) {
                            int i4 = i3 + 1;
                            if (i3 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            arrayList2.add(new AutoAddTracker.AutoTile(i3, (String) obj2));
                            i3 = i4;
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
        };
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m77m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(new StringBuilder("Current user: "), this.userId, printWriter, "Added tiles: ");
        m77m.append(this.autoAdded);
        printWriter.println(m77m.toString());
    }

    public final void saveTiles(String str) {
        SecureSettingsImpl secureSettingsImpl = (SecureSettingsImpl) this.secureSettings;
        Settings.Secure.putStringForUser(secureSettingsImpl.mContentResolver, "qs_auto_tiles", str, null, false, secureSettingsImpl.getRealUserHandle(this.userId), true);
    }
}
