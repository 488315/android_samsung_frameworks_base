package com.android.systemui.display.data.repository;

import android.util.Log;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class PerDisplayStoreImpl implements PerDisplayStore, CoreStartable {
    public final CoroutineScope backgroundApplicationScope;
    public final DisplayRepository displayRepository;
    public final ConcurrentHashMap perDisplayInstances = new ConcurrentHashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public PerDisplayStoreImpl(CoroutineScope coroutineScope, DisplayRepository displayRepository) {
        this.backgroundApplicationScope = coroutineScope;
        this.displayRepository = displayRepository;
    }

    public abstract Object createInstanceForDisplay(int i);

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(this.perDisplayInstances);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        if (((DisplayRepositoryImpl) this.displayRepository).displayRepositoryFromLib.getDisplay(i) == null) {
            Log.e("PerDisplayStore", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i, "<", getInstanceClass().getSimpleName(), ">: Display with id ", " doesn't exist."));
            return null;
        }
        synchronized (this.perDisplayInstances) {
            try {
                Object obj = this.perDisplayInstances.get(Integer.valueOf(i));
                if (obj != null) {
                    return obj;
                }
                Object createInstanceForDisplay = createInstanceForDisplay(i);
                if (createInstanceForDisplay == null) {
                    Log.e("PerDisplayStore", "<" + getInstanceClass().getSimpleName() + "> returning null because createInstanceForDisplay(" + i + ") returned null.");
                } else {
                    this.perDisplayInstances.put(Integer.valueOf(i), createInstanceForDisplay);
                }
                return createInstanceForDisplay;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object getDefaultDisplay() {
        Object forDisplay = forDisplay(0);
        forDisplay.getClass();
        return forDisplay;
    }

    public abstract Class getInstanceClass();

    public Object onDisplayRemovalAction(Object obj) {
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        getInstanceClass().getSimpleName();
        CoroutineTracingKt.launchTraced$default(this.backgroundApplicationScope, null, null, new PerDisplayStoreImpl$start$1(this, null), 6);
    }
}
