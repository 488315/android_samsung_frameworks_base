package com.android.server.desktopmode;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class StateManager$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StateManager f$0;

    public /* synthetic */ StateManager$$ExternalSyntheticLambda2(StateManager stateManager) {
        this.$r8$classId = 0;
        this.f$0 = stateManager;
    }

    public /* synthetic */ StateManager$$ExternalSyntheticLambda2(
            StateManager stateManager, StateManager.InternalState internalState, int i) {
        this.$r8$classId = i;
        this.f$0 = stateManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        StateManager stateManager = this.f$0;
        switch (i) {
            case 0:
                Iterator it = stateManager.mStateListeners.iterator();
                while (it.hasNext()) {
                    ((StateManager.StateListener) it.next()).onBootCompleted();
                }
                break;
            case 1:
                Iterator it2 = stateManager.mStateListeners.iterator();
                while (it2.hasNext()) {
                    ((StateManager.StateListener) it2.next()).getClass();
                }
                break;
            case 2:
                Iterator it3 = stateManager.mStateListeners.iterator();
                while (it3.hasNext()) {
                    ((StateManager.StateListener) it3.next()).getClass();
                }
                break;
            case 3:
                Iterator it4 = stateManager.mStateListeners.iterator();
                while (it4.hasNext()) {
                    ((StateManager.StateListener) it4.next()).getClass();
                }
                break;
            default:
                Iterator it5 = stateManager.mStateListeners.iterator();
                while (it5.hasNext()) {
                    ((StateManager.StateListener) it5.next()).getClass();
                }
                break;
        }
    }
}
