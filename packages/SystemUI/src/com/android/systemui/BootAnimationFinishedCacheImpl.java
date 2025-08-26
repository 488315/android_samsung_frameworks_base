package com.android.systemui;

import android.os.Handler;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BootAnimationFinishedCacheImpl implements BootAnimationFinishedCache, BootAnimationFinishedTrigger, Dumpable {
    public final AtomicBoolean bootAnimationFinished;
    public final List directListeners;
    public final List frontQueueListeners;
    public final List postlisteners;

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

    public BootAnimationFinishedCacheImpl(DumpManager dumpManager) {
        DumpManager.registerDumpable$default(dumpManager, "BootAnimationFinishedCacheImpl", this);
        this.directListeners = new ArrayList();
        this.postlisteners = new ArrayList();
        this.frontQueueListeners = new ArrayList();
        this.bootAnimationFinished = new AtomicBoolean(false);
    }

    public final boolean addListener(BootAnimationFinishedCache.BootAnimationFinishedListener bootAnimationFinishedListener) {
        if (this.bootAnimationFinished.get()) {
            bootAnimationFinishedListener.onBootAnimationFinished();
            return true;
        }
        synchronized (this) {
            if (this.bootAnimationFinished.get()) {
                bootAnimationFinishedListener.onBootAnimationFinished();
                return true;
            }
            ((ArrayList) this.postlisteners).add(bootAnimationFinishedListener);
            return false;
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("BootAnimationFinishedCache state:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  boot animation finished: ", this.bootAnimationFinished.get());
        if (this.bootAnimationFinished.get()) {
            return;
        }
        printWriter.println("  listeners:");
        synchronized (this) {
            try {
                Iterator it = this.directListeners.iterator();
                while (it.hasNext()) {
                    printWriter.println("    " + ((BootAnimationFinishedCache.BootAnimationFinishedListener) it.next()));
                }
                Iterator it2 = this.postlisteners.iterator();
                while (it2.hasNext()) {
                    printWriter.println("    " + ((BootAnimationFinishedCache.BootAnimationFinishedListener) it2.next()));
                }
                Iterator it3 = this.frontQueueListeners.iterator();
                while (it3.hasNext()) {
                    printWriter.println("    " + ((BootAnimationFinishedCache.BootAnimationFinishedListener) it3.next()));
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setBootAnimationFinished() {
        int i = 0;
        if (this.bootAnimationFinished.compareAndSet(false, true)) {
            synchronized (this) {
                try {
                    ArrayList arrayList = (ArrayList) this.directListeners;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((BootAnimationFinishedCache.BootAnimationFinishedListener) obj).onBootAnimationFinished();
                    }
                    ArrayList arrayList2 = (ArrayList) this.postlisteners;
                    int size2 = arrayList2.size();
                    int i3 = 0;
                    while (i3 < size2) {
                        Object obj2 = arrayList2.get(i3);
                        i3++;
                        final BootAnimationFinishedCache.BootAnimationFinishedListener bootAnimationFinishedListener = (BootAnimationFinishedCache.BootAnimationFinishedListener) obj2;
                        new Handler().post(new Runnable() { // from class: com.android.systemui.BootAnimationFinishedCacheImpl$setBootAnimationFinished$1$2$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                bootAnimationFinishedListener.onBootAnimationFinished();
                            }
                        });
                    }
                    ArrayList arrayList3 = (ArrayList) this.frontQueueListeners;
                    int size3 = arrayList3.size();
                    while (i < size3) {
                        Object obj3 = arrayList3.get(i);
                        i++;
                        final BootAnimationFinishedCache.BootAnimationFinishedListener bootAnimationFinishedListener2 = (BootAnimationFinishedCache.BootAnimationFinishedListener) obj3;
                        new Handler().postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.BootAnimationFinishedCacheImpl$setBootAnimationFinished$1$3$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                bootAnimationFinishedListener2.onBootAnimationFinished();
                            }
                        });
                    }
                    ((ArrayList) this.directListeners).clear();
                    ((ArrayList) this.postlisteners).clear();
                    ((ArrayList) this.frontQueueListeners).clear();
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
