package com.android.systemui.shade;

import com.android.systemui.Dependency;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.util.LogUtil;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeExpansionStateManager implements ShadeStateEvents {
    public float dragDownPxAmount;
    public boolean expanded;
    public float fraction;
    public boolean qsExpanded;
    public int state;
    public boolean tracking;
    public final CopyOnWriteArrayList expansionListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList fullExpansionListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList qsExpansionListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList stateListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList shadeStateEventsListeners = new CopyOnWriteArrayList();
    public final Lazy panelLogger$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.ShadeExpansionStateManager$panelLogger$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecPanelLogger) Dependency.get(SecPanelLogger.class);
        }
    });
    public final StringBuilder logBuilder = new StringBuilder();

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

    public final ShadeExpansionChangeEvent addExpansionListener(ShadeExpansionListener shadeExpansionListener) {
        this.expansionListeners.add(shadeExpansionListener);
        return new ShadeExpansionChangeEvent(this.fraction, this.expanded, this.tracking, this.dragDownPxAmount);
    }

    public final void addFullExpansionListener(ShadeFullExpansionListener shadeFullExpansionListener) {
        this.fullExpansionListeners.add(shadeFullExpansionListener);
        shadeFullExpansionListener.onShadeExpansionFullyChanged(this.qsExpanded);
    }

    public final void addQsExpansionListener(ShadeQsExpansionListener shadeQsExpansionListener) {
        this.qsExpansionListeners.add(shadeQsExpansionListener);
        shadeQsExpansionListener.onQsExpansionChanged(this.qsExpanded);
    }

    public final void updateStateInternal(int i) {
        ShadeExpansionStateManagerKt.panelStateToString(this.state);
        ShadeExpansionStateManagerKt.panelStateToString(i);
        String str = i != 0 ? i != 1 ? i != 2 ? "" : "STATE_OPEN" : "STATE_OPENING" : "STATE_CLOSED";
        StringBuilder sb = this.logBuilder;
        sb.setLength(0);
        sb.append("ShadeExpansionStateManager updateStateInternal : ".concat(str));
        sb.append(LogUtil.getCaller());
        ((SecPanelLoggerImpl) ((SecPanelLogger) this.panelLogger$delegate.getValue())).addPanelStateInfoLog(sb, true);
        this.state = i;
        Iterator it = this.stateListeners.iterator();
        while (it.hasNext()) {
            ((ShadeStateListener) it.next()).onPanelStateChanged(i);
        }
    }
}
