package com.android.systemui.shade;

import com.android.app.tracing.TraceStateLogger;
import com.android.app.tracing.TrackGroupUtils;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class ShadeExpansionStateManager implements Dumpable {
    public boolean expanded;
    public float fraction;
    public float oldFraction;
    public int state;
    public boolean tracking;
    public final CopyOnWriteArrayList expansionListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList stateListeners = new CopyOnWriteArrayList();
    public final TraceStateLogger stateLogger = new TraceStateLogger(TrackGroupUtils.trackGroup("shade", "ShadeExpansionState"), false, false, false, 14, null);

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

    public ShadeExpansionStateManager(DumpManager dumpManager) {
        dumpManager.registerDumpable(this);
    }

    public final ShadeExpansionChangeEvent addExpansionListener(ShadeExpansionListener shadeExpansionListener) {
        this.expansionListeners.add(shadeExpansionListener);
        return new ShadeExpansionChangeEvent(this.fraction, this.expanded, this.tracking);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        if (printWriter != null) {
            printWriter.println("ShadeExpansionStateManager:  expansionListeners{" + this.expansionListeners.size() + "}=");
            Iterator it = this.expansionListeners.iterator();
            while (it.hasNext()) {
                printWriter.println("    " + ((ShadeExpansionListener) it.next()));
            }
        }
    }

    public final boolean isClosed() {
        return this.state == 0;
    }

    public final void removeExpansionListener(ShadeExpansionListener shadeExpansionListener) {
        this.expansionListeners.remove(shadeExpansionListener);
    }

    public final void updateStateInternal(int i) {
        ShadeExpansionStateManagerKt.panelStateToString(this.state);
        ShadeExpansionStateManagerKt.panelStateToString(i);
        int i2 = this.state;
        if (i2 != i) {
            MediaSessions$H$$ExternalSyntheticOutline0.m("updateStateInternal: ", ShadeExpansionStateManagerKt.panelStateToString(i2), " -> ", ShadeExpansionStateManagerKt.panelStateToString(i), ShadeExpansionStateManagerKt.TAG);
        }
        this.state = i;
        Iterator it = this.stateListeners.iterator();
        while (it.hasNext()) {
            ((ShadeStateListener) it.next()).onPanelStateChanged$2(i);
        }
    }
}
