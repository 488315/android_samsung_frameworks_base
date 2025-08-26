package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.os.Handler;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes3.dex */
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
        if (this.debug) {
            String str = this.tag;
            String str2 = this.name;
            String str3 = notificationEntry.mKey;
            Log.d(str, str2 + ".cancelLifetimeExtension(key=" + str3 + ") isExtending=" + isExtending(str3));
        }
        warnIfEnding();
        this.mEntriesExtended.remove(notificationEntry.mKey);
        onCanceledLifetimeExtension(notificationEntry);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0049, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0052, code lost:
    
        throw r1;
     */
    @Override // com.android.systemui.Dumpable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "LifetimeExtender", this.name);
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            Set<String> setKeySet = this.mEntriesExtended.keySet();
            indentingPrintWriterAsIndenting.append("mEntriesExtended").append((CharSequence) ": ").println(setKeySet.size());
            indentingPrintWriterAsIndenting.increaseIndent();
            Iterator<T> it = setKeySet.iterator();
            while (it.hasNext()) {
                indentingPrintWriterAsIndenting.println(it.next());
            }
            indentingPrintWriterAsIndenting.decreaseIndent();
        } finally {
        }
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
            ((NotifCollection$$ExternalSyntheticLambda4) onEndLifetimeExtensionCallback).onEndLifetimeExtension(this, notificationEntry);
        }
        this.mEnding = false;
    }

    public final void endLifetimeExtension(String str) {
        if (this.debug) {
            Log.d(this.tag, this.name + ".endLifetimeExtension(key=" + str + ") isExtending=" + isExtending(str));
        }
        warnIfEnding();
        this.mEnding = true;
        NotificationEntry notificationEntryRemove = this.mEntriesExtended.remove(str);
        if (notificationEntryRemove != null) {
            NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback = this.mCallback;
            if (onEndLifetimeExtensionCallback == null) {
                onEndLifetimeExtensionCallback = null;
            }
            ((NotifCollection$$ExternalSyntheticLambda4) onEndLifetimeExtensionCallback).onEndLifetimeExtension(this, notificationEntryRemove);
        }
        this.mEnding = false;
    }

    public final void endLifetimeExtensionAfterDelay(final String str, long j) {
        if (this.debug) {
            Log.d(this.tag, this.name + ".endLifetimeExtensionAfterDelay(key=" + str + ", delayMillis=" + j + ") isExtending=" + isExtending(str));
        }
        if (isExtending(str)) {
            this.mainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender.endLifetimeExtensionAfterDelay.1
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
        boolean zQueryShouldExtendLifetime = queryShouldExtendLifetime(notificationEntry);
        if (this.debug) {
            String str = this.tag;
            String str2 = this.name;
            String str3 = notificationEntry.mKey;
            Log.d(str, str2 + ".shouldExtendLifetime(key=" + str3 + ", reason=" + i + ") isExtending=" + isExtending(str3) + " shouldExtend=" + zQueryShouldExtendLifetime);
        }
        warnIfEnding();
        if (zQueryShouldExtendLifetime && this.mEntriesExtended.put(notificationEntry.mKey, notificationEntry) == null) {
            onStartedLifetimeExtension(notificationEntry);
        }
        return zQueryShouldExtendLifetime;
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
