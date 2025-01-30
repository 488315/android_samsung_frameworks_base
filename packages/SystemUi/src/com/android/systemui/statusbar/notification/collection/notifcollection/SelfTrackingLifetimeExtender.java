package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda8;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SelfTrackingLifetimeExtender implements NotifLifetimeExtender, Dumpable {
    public final boolean debug;
    public NotifCollection$$ExternalSyntheticLambda8 mCallback;
    public boolean mEnding;
    public final ArrayMap mEntriesExtended = new ArrayMap();
    public final Handler mainHandler;
    public final String name;
    public final String tag;

    public SelfTrackingLifetimeExtender(String str, String str2, boolean z, Handler handler) {
        this.tag = str;
        this.name = str2;
        this.debug = z;
        this.mainHandler = handler;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final void cancelLifetimeExtension(NotificationEntry notificationEntry) {
        String str = notificationEntry.mKey;
        if (this.debug) {
            boolean isExtending = isExtending(str);
            StringBuilder sb = new StringBuilder();
            AppOpItem$$ExternalSyntheticOutline0.m97m(sb, this.name, ".cancelLifetimeExtension(key=", str, ") isExtending=");
            sb.append(isExtending);
            Log.d(this.tag, sb.toString());
        }
        warnIfEnding();
        this.mEntriesExtended.remove(str);
        onCanceledLifetimeExtension(notificationEntry);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("LifetimeExtender: " + this.name + ":");
        ArrayMap arrayMap = this.mEntriesExtended;
        printWriter.println("  mEntriesExtended: " + arrayMap.size());
        Iterator it = arrayMap.entrySet().iterator();
        while (it.hasNext()) {
            printWriter.println("  * " + ((Map.Entry) it.next()).getKey());
        }
    }

    public final void endAllLifetimeExtensions() {
        ArrayMap arrayMap = this.mEntriesExtended;
        List<NotificationEntry> list = CollectionsKt___CollectionsKt.toList(arrayMap.values());
        if (this.debug) {
            Log.d(this.tag, this.name + ".endAllLifetimeExtensions() entries=" + list);
        }
        arrayMap.clear();
        warnIfEnding();
        this.mEnding = true;
        for (NotificationEntry notificationEntry : list) {
            NotifCollection$$ExternalSyntheticLambda8 notifCollection$$ExternalSyntheticLambda8 = this.mCallback;
            if (notifCollection$$ExternalSyntheticLambda8 == null) {
                notifCollection$$ExternalSyntheticLambda8 = null;
            }
            notifCollection$$ExternalSyntheticLambda8.onEndLifetimeExtension(notificationEntry, this);
        }
        this.mEnding = false;
    }

    public final void endLifetimeExtension(String str) {
        if (this.debug) {
            boolean isExtending = isExtending(str);
            StringBuilder sb = new StringBuilder();
            AppOpItem$$ExternalSyntheticOutline0.m97m(sb, this.name, ".endLifetimeExtension(key=", str, ") isExtending=");
            sb.append(isExtending);
            Log.d(this.tag, sb.toString());
        }
        warnIfEnding();
        this.mEnding = true;
        NotificationEntry notificationEntry = (NotificationEntry) this.mEntriesExtended.remove(str);
        if (notificationEntry != null) {
            NotifCollection$$ExternalSyntheticLambda8 notifCollection$$ExternalSyntheticLambda8 = this.mCallback;
            if (notifCollection$$ExternalSyntheticLambda8 == null) {
                notifCollection$$ExternalSyntheticLambda8 = null;
            }
            notifCollection$$ExternalSyntheticLambda8.onEndLifetimeExtension(notificationEntry, this);
        }
        this.mEnding = false;
    }

    public final void endLifetimeExtensionAfterDelay(final String str, long j) {
        if (this.debug) {
            boolean isExtending = isExtending(str);
            StringBuilder sb = new StringBuilder();
            AppOpItem$$ExternalSyntheticOutline0.m97m(sb, this.name, ".endLifetimeExtensionAfterDelay(key=", str, ", delayMillis=");
            sb.append(j);
            sb.append(") isExtending=");
            sb.append(isExtending);
            Log.d(this.tag, sb.toString());
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
        String str = notificationEntry.mKey;
        if (this.debug) {
            boolean isExtending = isExtending(str);
            StringBuilder sb = new StringBuilder();
            AppOpItem$$ExternalSyntheticOutline0.m97m(sb, this.name, ".shouldExtendLifetime(key=", str, ", reason=");
            sb.append(i);
            sb.append(") isExtending=");
            sb.append(isExtending);
            sb.append(" shouldExtend=");
            sb.append(queryShouldExtendLifetime);
            Log.d(this.tag, sb.toString());
        }
        warnIfEnding();
        if (queryShouldExtendLifetime && this.mEntriesExtended.put(str, notificationEntry) == null) {
            onStartedLifetimeExtension(notificationEntry);
        }
        return queryShouldExtendLifetime;
    }

    public abstract boolean queryShouldExtendLifetime(NotificationEntry notificationEntry);

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final void setCallback(NotifCollection$$ExternalSyntheticLambda8 notifCollection$$ExternalSyntheticLambda8) {
        this.mCallback = notifCollection$$ExternalSyntheticLambda8;
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
