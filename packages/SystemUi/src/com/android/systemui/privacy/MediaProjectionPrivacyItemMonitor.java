package com.android.systemui.privacy;

import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.util.IndentingPrintWriter;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemMonitor;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MediaProjectionPrivacyItemMonitor implements PrivacyItemMonitor {
    public final Handler bgHandler;
    public PrivacyItemMonitor.Callback callback;
    public boolean listening;
    public final PrivacyLogger logger;
    public boolean mediaProjectionAvailable;
    public final MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1 mediaProjectionCallback;
    public final MediaProjectionManager mediaProjectionManager;
    public final MediaProjectionPrivacyItemMonitor$optionsCallback$1 optionsCallback;
    public final PackageManager packageManager;
    public final PrivacyConfig privacyConfig;
    public final SystemClock systemClock;
    public final Object lock = new Object();
    public final List privacyItems = new ArrayList();

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
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$optionsCallback$1, com.android.systemui.privacy.PrivacyConfig$Callback] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1] */
    public MediaProjectionPrivacyItemMonitor(MediaProjectionManager mediaProjectionManager, PackageManager packageManager, PrivacyConfig privacyConfig, Handler handler, SystemClock systemClock, PrivacyLogger privacyLogger) {
        this.mediaProjectionManager = mediaProjectionManager;
        this.packageManager = packageManager;
        this.privacyConfig = privacyConfig;
        this.bgHandler = handler;
        this.systemClock = systemClock;
        this.logger = privacyLogger;
        this.mediaProjectionAvailable = privacyConfig.mediaProjectionAvailable;
        ?? r1 = new PrivacyConfig.Callback() { // from class: com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$optionsCallback$1
            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public final void onFlagMediaProjectionChanged() {
                MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor = MediaProjectionPrivacyItemMonitor.this;
                synchronized (mediaProjectionPrivacyItemMonitor.lock) {
                    mediaProjectionPrivacyItemMonitor.mediaProjectionAvailable = mediaProjectionPrivacyItemMonitor.privacyConfig.mediaProjectionAvailable;
                    mediaProjectionPrivacyItemMonitor.setListeningStateLocked();
                    Unit unit = Unit.INSTANCE;
                }
                MediaProjectionPrivacyItemMonitor.this.dispatchOnPrivacyItemsChanged();
            }
        };
        this.optionsCallback = r1;
        this.mediaProjectionCallback = new MediaProjectionManager.Callback() { // from class: com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1
            public final void onStart(MediaProjectionInfo mediaProjectionInfo) {
                MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor = MediaProjectionPrivacyItemMonitor.this;
                synchronized (mediaProjectionPrivacyItemMonitor.lock) {
                    PrivacyItem makePrivacyItem = mediaProjectionPrivacyItemMonitor.makePrivacyItem(mediaProjectionInfo);
                    ((ArrayList) mediaProjectionPrivacyItemMonitor.privacyItems).add(makePrivacyItem);
                    PrivacyApplication privacyApplication = makePrivacyItem.application;
                    mediaProjectionPrivacyItemMonitor.logger.logUpdatedItemFromMediaProjection(privacyApplication.uid, privacyApplication.packageName, true);
                    Unit unit = Unit.INSTANCE;
                }
                MediaProjectionPrivacyItemMonitor.this.dispatchOnPrivacyItemsChanged();
            }

            public final void onStop(MediaProjectionInfo mediaProjectionInfo) {
                MediaProjectionPrivacyItemMonitor mediaProjectionPrivacyItemMonitor = MediaProjectionPrivacyItemMonitor.this;
                synchronized (mediaProjectionPrivacyItemMonitor.lock) {
                    PrivacyItem makePrivacyItem = mediaProjectionPrivacyItemMonitor.makePrivacyItem(mediaProjectionInfo);
                    ArrayList arrayList = (ArrayList) mediaProjectionPrivacyItemMonitor.privacyItems;
                    Iterator it = arrayList.iterator();
                    int i = 0;
                    while (true) {
                        if (!it.hasNext()) {
                            i = -1;
                            break;
                        } else if (Intrinsics.areEqual(((PrivacyItem) it.next()).application, makePrivacyItem.application)) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    arrayList.remove(i);
                    PrivacyApplication privacyApplication = makePrivacyItem.application;
                    mediaProjectionPrivacyItemMonitor.logger.logUpdatedItemFromMediaProjection(privacyApplication.uid, privacyApplication.packageName, false);
                    Unit unit = Unit.INSTANCE;
                }
                MediaProjectionPrivacyItemMonitor.this.dispatchOnPrivacyItemsChanged();
            }
        };
        privacyConfig.addCallback(r1);
        setListeningStateLocked();
    }

    public final void dispatchOnPrivacyItemsChanged() {
        final PrivacyItemMonitor.Callback callback;
        synchronized (this.lock) {
            callback = this.callback;
        }
        if (callback != null) {
            this.bgHandler.post(new Runnable() { // from class: com.android.systemui.privacy.MediaProjectionPrivacyItemMonitor$dispatchOnPrivacyItemsChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    PrivacyItemController$privacyItemMonitorCallback$1 privacyItemController$privacyItemMonitorCallback$1 = (PrivacyItemController$privacyItemMonitorCallback$1) PrivacyItemMonitor.Callback.this;
                    privacyItemController$privacyItemMonitorCallback$1.getClass();
                    int i = PrivacyItemController.$r8$clinit;
                    PrivacyItemController privacyItemController = privacyItemController$privacyItemMonitorCallback$1.this$0;
                    privacyItemController.getClass();
                    ((ExecutorImpl) privacyItemController.bgExecutor).execute(new PrivacyItemController$update$1(privacyItemController));
                }
            });
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("MediaProjectionPrivacyItemMonitor:");
        asIndenting.increaseIndent();
        try {
            synchronized (this.lock) {
                asIndenting.println("Listening: " + this.listening);
                asIndenting.println("mediaProjectionAvailable: " + this.mediaProjectionAvailable);
                asIndenting.println("Callback: " + this.callback);
                asIndenting.println("Privacy Items: " + this.privacyItems);
                Unit unit = Unit.INSTANCE;
            }
            asIndenting.decreaseIndent();
            asIndenting.flush();
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public final List getActivePrivacyItems() {
        List list;
        synchronized (this.lock) {
            list = CollectionsKt___CollectionsKt.toList(this.privacyItems);
        }
        return list;
    }

    public final PrivacyItem makePrivacyItem(MediaProjectionInfo mediaProjectionInfo) {
        PrivacyApplication privacyApplication = new PrivacyApplication(mediaProjectionInfo.getPackageName(), this.packageManager.getPackageUidAsUser(mediaProjectionInfo.getPackageName(), mediaProjectionInfo.getUserHandle().getIdentifier()));
        ((SystemClockImpl) this.systemClock).getClass();
        return new PrivacyItem(PrivacyType.TYPE_MEDIA_PROJECTION, privacyApplication, android.os.SystemClock.elapsedRealtime(), false, 8, null);
    }

    public final void setListeningStateLocked() {
        boolean z = this.mediaProjectionAvailable;
        if (this.listening == z) {
            return;
        }
        this.listening = z;
        PrivacyLogger privacyLogger = this.logger;
        List list = this.privacyItems;
        MediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1 mediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1 = this.mediaProjectionCallback;
        MediaProjectionManager mediaProjectionManager = this.mediaProjectionManager;
        if (!z) {
            mediaProjectionManager.removeCallback(mediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1);
            ArrayList arrayList = (ArrayList) list;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PrivacyApplication privacyApplication = ((PrivacyItem) it.next()).application;
                privacyLogger.logUpdatedItemFromMediaProjection(privacyApplication.uid, privacyApplication.packageName, false);
            }
            arrayList.clear();
            dispatchOnPrivacyItemsChanged();
            return;
        }
        mediaProjectionManager.addCallback(mediaProjectionPrivacyItemMonitor$mediaProjectionCallback$1, this.bgHandler);
        MediaProjectionInfo activeProjectionInfo = mediaProjectionManager.getActiveProjectionInfo();
        if (activeProjectionInfo != null) {
            PrivacyItem makePrivacyItem = makePrivacyItem(activeProjectionInfo);
            ((ArrayList) list).add(makePrivacyItem);
            PrivacyApplication privacyApplication2 = makePrivacyItem.application;
            privacyLogger.logUpdatedItemFromMediaProjection(privacyApplication2.uid, privacyApplication2.packageName, true);
            dispatchOnPrivacyItemsChanged();
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public final void startListening(PrivacyItemController$privacyItemMonitorCallback$1 privacyItemController$privacyItemMonitorCallback$1) {
        synchronized (this.lock) {
            this.callback = privacyItemController$privacyItemMonitorCallback$1;
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public final void stopListening() {
        synchronized (this.lock) {
            this.callback = null;
            Unit unit = Unit.INSTANCE;
        }
    }
}
