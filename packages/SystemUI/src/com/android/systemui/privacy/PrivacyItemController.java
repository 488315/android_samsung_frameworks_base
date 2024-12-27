package com.android.systemui.privacy;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class PrivacyItemController implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DelayableExecutor bgExecutor;
    public Runnable holdingRunnableCanceler;
    public final MyExecutor internalUiExecutor;
    public boolean listening;
    public final PrivacyLogger logger;
    public final PrivacyItemController$optionsCallback$1 optionsCallback;
    public final PrivacyConfig privacyConfig;
    public final PrivacyItemController$privacyItemMonitorCallback$1 privacyItemMonitorCallback;
    public final Set privacyItemMonitors;
    public final SystemClock systemClock;
    public final PrivacyItemController$updateListAndNotifyChanges$1 updateListAndNotifyChanges;
    public List privacyList = EmptyList.INSTANCE;
    public final List callbacks = new ArrayList();
    public final PrivacyItemController$notifyChanges$1 notifyChanges = new Runnable() { // from class: com.android.systemui.privacy.PrivacyItemController$notifyChanges$1
        @Override // java.lang.Runnable
        public final void run() {
            List privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core = PrivacyItemController.this.getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            Iterator it = ((ArrayList) PrivacyItemController.this.callbacks).iterator();
            while (it.hasNext()) {
                PrivacyItemController.Callback callback = (PrivacyItemController.Callback) ((WeakReference) it.next()).get();
                if (callback != null) {
                    callback.onPrivacyItemsChanged(privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core);
                }
            }
        }
    };

    public interface Callback extends PrivacyConfig.Callback {
        void onPrivacyItemsChanged(List list);
    }

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getTIME_TO_HOLD_INDICATORS$annotations() {
        }
    }

    public final class MyExecutor implements Executor {
        public final DelayableExecutor delegate;
        public Runnable listeningCanceller;

        public MyExecutor(DelayableExecutor delayableExecutor) {
            this.delegate = delayableExecutor;
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            this.delegate.execute(runnable);
        }
    }

    public final class NotifyChangesToCallback implements Runnable {
        public final Callback callback;
        public final List list;

        public NotifyChangesToCallback(Callback callback, List<PrivacyItem> list) {
            this.callback = callback;
            this.list = list;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Callback callback = this.callback;
            if (callback != null) {
                callback.onPrivacyItemsChanged(this.list);
            }
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.privacy.PrivacyConfig$Callback, com.android.systemui.privacy.PrivacyItemController$optionsCallback$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.privacy.PrivacyItemController$notifyChanges$1] */
    public PrivacyItemController(DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, PrivacyConfig privacyConfig, Set<PrivacyItemMonitor> set, PrivacyLogger privacyLogger, SystemClock systemClock, DumpManager dumpManager) {
        this.bgExecutor = delayableExecutor2;
        this.privacyConfig = privacyConfig;
        this.privacyItemMonitors = set;
        this.logger = privacyLogger;
        this.systemClock = systemClock;
        this.internalUiExecutor = new MyExecutor(delayableExecutor);
        this.updateListAndNotifyChanges = new PrivacyItemController$updateListAndNotifyChanges$1(this, delayableExecutor);
        ?? r1 = new PrivacyConfig.Callback() { // from class: com.android.systemui.privacy.PrivacyItemController$optionsCallback$1
            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public final void onFlagLocationChanged(boolean z) {
                Iterator it = PrivacyItemController.this.callbacks.iterator();
                while (it.hasNext()) {
                    PrivacyItemController.Callback callback = (PrivacyItemController.Callback) ((WeakReference) it.next()).get();
                    if (callback != null) {
                        callback.onFlagLocationChanged(z);
                    }
                }
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public final void onFlagMediaProjectionChanged() {
                Iterator it = ((ArrayList) PrivacyItemController.this.callbacks).iterator();
                while (it.hasNext()) {
                }
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public final void onFlagMicCameraChanged(boolean z) {
                Iterator it = PrivacyItemController.this.callbacks.iterator();
                while (it.hasNext()) {
                    PrivacyItemController.Callback callback = (PrivacyItemController.Callback) ((WeakReference) it.next()).get();
                    if (callback != null) {
                        callback.onFlagMicCameraChanged(z);
                    }
                }
            }
        };
        this.optionsCallback = r1;
        this.privacyItemMonitorCallback = new PrivacyItemController$privacyItemMonitorCallback$1(this);
        DumpManager.registerDumpable$default(dumpManager, "PrivacyItemController", this);
        privacyConfig.addCallback(r1);
    }

    public final void addCallback(Callback callback) {
        WeakReference weakReference = new WeakReference(callback);
        ((ArrayList) this.callbacks).add(weakReference);
        boolean z = !((ArrayList) this.callbacks).isEmpty();
        MyExecutor myExecutor = this.internalUiExecutor;
        if (!z || this.listening) {
            if (this.listening) {
                myExecutor.execute(new NotifyChangesToCallback((Callback) weakReference.get(), getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core()));
            }
        } else {
            Runnable runnable = myExecutor.listeningCanceller;
            if (runnable != null) {
                runnable.run();
            }
            myExecutor.listeningCanceller = myExecutor.delegate.executeDelayed(new PrivacyItemController$MyExecutor$updateListeningState$1(PrivacyItemController.this), 0L);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("PrivacyItemController state:");
        asIndenting.increaseIndent();
        try {
            asIndenting.println("Listening: " + this.listening);
            asIndenting.println("Privacy Items:");
            asIndenting.increaseIndent();
            try {
                Iterator it = getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core().iterator();
                while (it.hasNext()) {
                    asIndenting.println(((PrivacyItem) it.next()).toString());
                }
                asIndenting.decreaseIndent();
                asIndenting.println("Callbacks:");
                asIndenting.increaseIndent();
                try {
                    Iterator it2 = this.callbacks.iterator();
                    while (it2.hasNext()) {
                        Callback callback = (Callback) ((WeakReference) it2.next()).get();
                        if (callback != null) {
                            asIndenting.println(callback.toString());
                        }
                    }
                    asIndenting.decreaseIndent();
                    asIndenting.println("PrivacyItemMonitors:");
                    asIndenting.increaseIndent();
                    try {
                        Iterator it3 = this.privacyItemMonitors.iterator();
                        while (it3.hasNext()) {
                            ((AppOpsPrivacyItemMonitor) ((PrivacyItemMonitor) it3.next())).dump(asIndenting, strArr);
                        }
                        asIndenting.decreaseIndent();
                        asIndenting.flush();
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized List getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return CollectionsKt___CollectionsKt.toList(this.privacyList);
    }

    public final void removeCallback(Callback callback) {
        final WeakReference weakReference = new WeakReference(callback);
        ((ArrayList) this.callbacks).removeIf(new Predicate() { // from class: com.android.systemui.privacy.PrivacyItemController$removeCallback$1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                PrivacyItemController.Callback callback2 = (PrivacyItemController.Callback) ((WeakReference) obj).get();
                if (callback2 != null) {
                    return callback2.equals(weakReference.get());
                }
                return true;
            }
        });
        if (((ArrayList) this.callbacks).isEmpty()) {
            MyExecutor myExecutor = this.internalUiExecutor;
            Runnable runnable = myExecutor.listeningCanceller;
            if (runnable != null) {
                runnable.run();
            }
            myExecutor.listeningCanceller = myExecutor.delegate.executeDelayed(new PrivacyItemController$MyExecutor$updateListeningState$1(PrivacyItemController.this), 0L);
        }
    }

    public final void update$4$1() {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.PrivacyItemController$update$1
            @Override // java.lang.Runnable
            public final void run() {
                PrivacyItemController.this.updateListAndNotifyChanges.run();
            }
        });
    }

    public static /* synthetic */ void getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
