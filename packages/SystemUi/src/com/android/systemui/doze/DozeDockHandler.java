package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.settings.UserTracker;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeDockHandler implements DozeMachine.Part {
    public final DockEventListener mDockEventListener = new DockEventListener(this, 0);
    public final DockManager mDockManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.doze.DozeDockHandler$1 */
    public abstract /* synthetic */ class AbstractC12371 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DockEventListener implements DockManager.DockEventListener {
        public boolean mRegistered;

        public /* synthetic */ DockEventListener(DozeDockHandler dozeDockHandler, int i) {
            this();
        }

        private DockEventListener() {
        }
    }

    static {
        boolean z = DozeService.DEBUG;
    }

    public DozeDockHandler(AmbientDisplayConfiguration ambientDisplayConfiguration, DockManager dockManager, UserTracker userTracker) {
        this.mDockManager = dockManager;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void dump(PrintWriter printWriter) {
        printWriter.println("DozeDockHandler:");
        printWriter.println(" dockState=0");
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AbstractC12371.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()];
        DockEventListener dockEventListener = this.mDockEventListener;
        if (i == 1) {
            if (dockEventListener.mRegistered) {
                return;
            }
            DockManager dockManager = DozeDockHandler.this.mDockManager;
            dockEventListener.mRegistered = true;
            return;
        }
        if (i == 2 && dockEventListener.mRegistered) {
            DockManager dockManager2 = DozeDockHandler.this.mDockManager;
            dockEventListener.mRegistered = false;
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void setDozeMachine(DozeMachine dozeMachine) {
    }
}
