package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SelfTrackingLifetimeExtender implements NotifLifetimeExtender, Dumpable {
    public static final int $stable = 8;
    private final boolean debug;
    private NotifLifetimeExtender.OnEndLifetimeExtensionCallback mCallback;
    private boolean mEnding;
    private final ArrayMap<String, NotificationEntry> mEntriesExtended = new ArrayMap<>();
    private final Handler mainHandler;
    private final String name;
    private final String tag;

    public SelfTrackingLifetimeExtender(String str, String str2, boolean z, Handler handler) {
        this.tag = str;
        this.name = str2;
        this.debug = z;
        this.mainHandler = handler;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final void cancelLifetimeExtension(NotificationEntry notificationEntry) {
        boolean z = this.debug;
        String str = notificationEntry.mKey;
        if (z) {
            Log.d(this.tag, this.name + ".cancelLifetimeExtension(key=" + str + ") isExtending=" + isExtending(str));
        }
        warnIfEnding();
        this.mEntriesExtended.remove(str);
        onCanceledLifetimeExtension(notificationEntry);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0049, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0052, code lost:
    
        throw r1;
     */
    @Override // com.android.systemui.Dumpable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.PrintWriter r2, java.lang.String[] r3) {
        /*
            r1 = this;
            android.util.IndentingPrintWriter r2 = com.android.systemui.util.DumpUtilsKt.asIndenting(r2)
            java.lang.String r3 = "LifetimeExtender"
            java.lang.String r0 = r1.name
            com.android.systemui.util.DumpUtilsKt.println(r2, r3, r0)
            r2.increaseIndent()
            java.lang.String r3 = "mEntriesExtended"
            android.util.ArrayMap<java.lang.String, com.android.systemui.statusbar.notification.collection.NotificationEntry> r1 = r1.mEntriesExtended     // Catch: java.lang.Throwable -> L49
            java.util.Set r1 = r1.keySet()     // Catch: java.lang.Throwable -> L49
            java.util.Collection r1 = (java.util.Collection) r1     // Catch: java.lang.Throwable -> L49
            java.io.PrintWriter r3 = r2.append(r3)     // Catch: java.lang.Throwable -> L49
            java.lang.String r0 = ": "
            java.io.PrintWriter r3 = r3.append(r0)     // Catch: java.lang.Throwable -> L49
            int r0 = r1.size()     // Catch: java.lang.Throwable -> L49
            r3.println(r0)     // Catch: java.lang.Throwable -> L49
            r2.increaseIndent()     // Catch: java.lang.Throwable -> L49
            java.lang.Iterable r1 = (java.lang.Iterable) r1     // Catch: java.lang.Throwable -> L40
            java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> L40
        L32:
            boolean r3 = r1.hasNext()     // Catch: java.lang.Throwable -> L40
            if (r3 == 0) goto L42
            java.lang.Object r3 = r1.next()     // Catch: java.lang.Throwable -> L40
            r2.println(r3)     // Catch: java.lang.Throwable -> L40
            goto L32
        L40:
            r1 = move-exception
            goto L4b
        L42:
            r2.decreaseIndent()     // Catch: java.lang.Throwable -> L49
            r2.decreaseIndent()
            return
        L49:
            r1 = move-exception
            goto L4f
        L4b:
            r2.decreaseIndent()     // Catch: java.lang.Throwable -> L49
            throw r1     // Catch: java.lang.Throwable -> L49
        L4f:
            r2.decreaseIndent()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender.dump(java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void endAllLifetimeExtensions() {
        List<NotificationEntry> list = CollectionsKt___CollectionsKt.toList(this.mEntriesExtended.values());
        if (this.debug) {
            Log.d(this.tag, this.name + ".endAllLifetimeExtensions() entries=" + list);
        }
        this.mEntriesExtended.clear();
        warnIfEnding();
        this.mEnding = true;
        for (NotificationEntry notificationEntry : list) {
            NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback = this.mCallback;
            if (onEndLifetimeExtensionCallback == null) {
                onEndLifetimeExtensionCallback = null;
            }
            ((NotifCollection$$ExternalSyntheticLambda4) onEndLifetimeExtensionCallback).onEndLifetimeExtension(notificationEntry, this);
        }
        this.mEnding = false;
    }

    public final void endLifetimeExtension(String str) {
        if (this.debug) {
            Log.d(this.tag, this.name + ".endLifetimeExtension(key=" + str + ") isExtending=" + isExtending(str));
        }
        warnIfEnding();
        this.mEnding = true;
        NotificationEntry remove = this.mEntriesExtended.remove(str);
        if (remove != null) {
            NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback = this.mCallback;
            if (onEndLifetimeExtensionCallback == null) {
                onEndLifetimeExtensionCallback = null;
            }
            ((NotifCollection$$ExternalSyntheticLambda4) onEndLifetimeExtensionCallback).onEndLifetimeExtension(remove, this);
        }
        this.mEnding = false;
    }

    public final void endLifetimeExtensionAfterDelay(final String str, long j) {
        if (this.debug) {
            Log.d(this.tag, this.name + ".endLifetimeExtensionAfterDelay(key=" + str + ", delayMillis=" + j + ") isExtending=" + isExtending(str));
        }
        if (isExtending(str)) {
            this.mainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender$endLifetimeExtensionAfterDelay$1
                @Override // java.lang.Runnable
                public final void run() {
                    SelfTrackingLifetimeExtender.this.endLifetimeExtension(str);
                }
            }, j);
        }
    }

    public final ArrayMap<String, NotificationEntry> getMEntriesExtended() {
        return this.mEntriesExtended;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final String getName() {
        return this.name;
    }

    public final boolean isExtending(String str) {
        return this.mEntriesExtended.containsKey(str);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i) {
        boolean queryShouldExtendLifetime = queryShouldExtendLifetime(notificationEntry);
        boolean z = this.debug;
        String str = notificationEntry.mKey;
        if (z) {
            Log.d(this.tag, this.name + ".shouldExtendLifetime(key=" + str + ", reason=" + i + ") isExtending=" + isExtending(str) + " shouldExtend=" + queryShouldExtendLifetime);
        }
        warnIfEnding();
        if (queryShouldExtendLifetime && this.mEntriesExtended.put(str, notificationEntry) == null) {
            onStartedLifetimeExtension(notificationEntry);
        }
        return queryShouldExtendLifetime;
    }

    public abstract boolean queryShouldExtendLifetime(NotificationEntry notificationEntry);

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final void setCallback(NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback) {
        this.mCallback = onEndLifetimeExtensionCallback;
    }

    public final void warnIfEnding() {
        if (this.debug && this.mEnding) {
            Log.w(this.tag, "reentrant code while ending a lifetime extension");
        }
    }

    public void onCanceledLifetimeExtension(NotificationEntry notificationEntry) {
    }

    public void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
    }
}
