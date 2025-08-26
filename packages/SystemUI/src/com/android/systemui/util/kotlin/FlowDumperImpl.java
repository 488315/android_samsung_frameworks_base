package com.android.systemui.util.kotlin;

import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public abstract class FlowDumperImpl extends SimpleFlowDumper {
    public static final int $stable = 8;
    private final DumpManager dumpManager;
    private final String dumpManagerName;
    private AtomicBoolean registered;
    private final String tag;

    public /* synthetic */ FlowDumperImpl(DumpManager dumpManager, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(dumpManager, (i & 2) != 0 ? null : str);
    }

    private final void updateRegistration(boolean z) {
        if (z && this.registered.get()) {
            return;
        }
        synchronized (this.registered) {
            try {
                boolean zIsNotEmpty = isNotEmpty();
                if (this.registered.getAndSet(zIsNotEmpty) != zIsNotEmpty) {
                    if (zIsNotEmpty) {
                        this.dumpManager.registerCriticalDumpable(this.dumpManagerName, this);
                    } else {
                        this.dumpManager.unregisterDumpable(this.dumpManagerName);
                    }
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.util.kotlin.SimpleFlowDumper
    public void onMapKeysChanged(boolean z) {
        updateRegistration(z);
    }

    public FlowDumperImpl(DumpManager dumpManager, String str) {
        this.dumpManager = dumpManager;
        this.tag = str;
        this.dumpManagerName = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("[", getIdString(this), "] ", str == null ? getClass().getSimpleName() : str);
        this.registered = new AtomicBoolean(false);
    }
}
