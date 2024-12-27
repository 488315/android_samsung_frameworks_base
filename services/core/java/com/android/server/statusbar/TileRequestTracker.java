package com.android.server.statusbar;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.SparseArrayMap;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TileRequestTracker {
    static final int MAX_NUM_DENIALS = 3;
    public final AnonymousClass1 mUninstallReceiver;
    public final Object mLock = new Object();
    public final SparseArrayMap mTrackingMap = new SparseArrayMap();
    public final ArraySet mComponentsToRemove = new ArraySet();

    public TileRequestTracker(Context context) {
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.statusbar.TileRequestTracker.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            return;
                        }
                        String encodedSchemeSpecificPart =
                                intent.getData().getEncodedSchemeSpecificPart();
                        if (intent.hasExtra("android.intent.extra.UID")) {
                            int userId =
                                    UserHandle.getUserId(
                                            intent.getIntExtra("android.intent.extra.UID", -1));
                            synchronized (TileRequestTracker.this.mLock) {
                                try {
                                    TileRequestTracker.this.mComponentsToRemove.clear();
                                    int numElementsForKey =
                                            TileRequestTracker.this.mTrackingMap.numElementsForKey(
                                                    userId);
                                    int indexOfKey =
                                            TileRequestTracker.this.mTrackingMap.indexOfKey(userId);
                                    for (int i = 0; i < numElementsForKey; i++) {
                                        ComponentName componentName =
                                                (ComponentName)
                                                        TileRequestTracker.this.mTrackingMap.keyAt(
                                                                indexOfKey, i);
                                        if (componentName
                                                .getPackageName()
                                                .equals(encodedSchemeSpecificPart)) {
                                            TileRequestTracker.this.mComponentsToRemove.add(
                                                    componentName);
                                        }
                                    }
                                    int size = TileRequestTracker.this.mComponentsToRemove.size();
                                    for (int i2 = 0; i2 < size; i2++) {
                                        TileRequestTracker.this.mTrackingMap.delete(
                                                userId,
                                                (ComponentName)
                                                        TileRequestTracker.this.mComponentsToRemove
                                                                .valueAt(i2));
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }
                };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
        context.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    }

    public final void dump(final IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("TileRequestTracker:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            this.mTrackingMap.forEach(
                    new SparseArrayMap
                            .TriConsumer() { // from class:
                                             // com.android.server.statusbar.TileRequestTracker$$ExternalSyntheticLambda0
                        public final void accept(int i, Object obj, Object obj2) {
                            IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                            StringBuilder m =
                                    BatteryService$$ExternalSyntheticOutline0.m(i, "user=", ", ");
                            m.append(((ComponentName) obj).toShortString());
                            m.append(": ");
                            m.append((Integer) obj2);
                            indentingPrintWriter2.println(m.toString());
                        }
                    });
        }
        indentingPrintWriter.decreaseIndent();
    }
}
