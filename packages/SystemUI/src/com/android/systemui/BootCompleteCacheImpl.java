package com.android.systemui;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.assist.PhoneStateMonitor;
import com.android.systemui.assist.PhoneStateMonitor$$ExternalSyntheticLambda0;
import com.android.systemui.assist.PhoneStateMonitor$$ExternalSyntheticLambda2;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BootCompleteCacheImpl implements BootCompleteCache, Dumpable {
    public final AtomicBoolean bootComplete;
    public final List listeners;

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

    public BootCompleteCacheImpl(DumpManager dumpManager) {
        dumpManager.registerNormalDumpable("BootCompleteCacheImpl", this);
        this.listeners = new ArrayList();
        this.bootComplete = new AtomicBoolean(false);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("BootCompleteCache state:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  boot complete: ", this.bootComplete.get());
        if (this.bootComplete.get()) {
            return;
        }
        printWriter.println("  listeners:");
        synchronized (this.listeners) {
            try {
                Iterator it = this.listeners.iterator();
                while (it.hasNext()) {
                    printWriter.println("    " + ((WeakReference) it.next()));
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setBootComplete() {
        if (this.bootComplete.compareAndSet(false, true)) {
            synchronized (this.listeners) {
                try {
                    ArrayList arrayList = (ArrayList) this.listeners;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        PhoneStateMonitor$$ExternalSyntheticLambda0 phoneStateMonitor$$ExternalSyntheticLambda0 = (PhoneStateMonitor$$ExternalSyntheticLambda0) ((WeakReference) obj).get();
                        if (phoneStateMonitor$$ExternalSyntheticLambda0 != null) {
                            PhoneStateMonitor phoneStateMonitor = phoneStateMonitor$$ExternalSyntheticLambda0.f$0;
                            phoneStateMonitor.mBgHandler.post(new PhoneStateMonitor$$ExternalSyntheticLambda2(phoneStateMonitor, 0));
                        }
                    }
                    ((ArrayList) this.listeners).clear();
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
