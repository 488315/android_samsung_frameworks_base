package com.android.server.desktopmode;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class StateManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StateManager f$0;
    public final /* synthetic */ StateManager.InternalState f$1;

    public /* synthetic */ StateManager$$ExternalSyntheticLambda0(
            StateManager stateManager, StateManager.InternalState internalState, int i) {
        this.$r8$classId = i;
        this.f$0 = stateManager;
        this.f$1 = internalState;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                StateManager stateManager = this.f$0;
                StateManager.InternalState internalState = this.f$1;
                Iterator it = stateManager.mStateListeners.iterator();
                while (it.hasNext()) {
                    ((StateManager.StateListener) it.next())
                            .onForcedInternalScreenStateChanged(internalState);
                }
                break;
            case 1:
                StateManager stateManager2 = this.f$0;
                StateManager.InternalState internalState2 = this.f$1;
                Iterator it2 = stateManager2.mStateListeners.iterator();
                while (it2.hasNext()) {
                    ((StateManager.StateListener) it2.next())
                            .onNavBarGestureEnabled(internalState2);
                }
                break;
            case 2:
                StateManager stateManager3 = this.f$0;
                StateManager.InternalState internalState3 = this.f$1;
                Iterator it3 = stateManager3.mStateListeners.iterator();
                while (it3.hasNext()) {
                    ((StateManager.StateListener) it3.next())
                            .onExternalDisplayConnectionChanged(internalState3);
                }
                break;
            case 3:
                StateManager stateManager4 = this.f$0;
                StateManager.InternalState internalState4 = this.f$1;
                Iterator it4 = stateManager4.mStateListeners.iterator();
                while (it4.hasNext()) {
                    ((StateManager.StateListener) it4.next()).onSpenEnabled(internalState4);
                }
                break;
            case 4:
                StateManager stateManager5 = this.f$0;
                StateManager.InternalState internalState5 = this.f$1;
                Iterator it5 = stateManager5.mStateListeners.iterator();
                while (it5.hasNext()) {
                    ((StateManager.StateListener) it5.next())
                            .onPogoKeyboardConnectionChanged(internalState5);
                }
                break;
            case 5:
                StateManager stateManager6 = this.f$0;
                StateManager.InternalState internalState6 = this.f$1;
                Iterator it6 = stateManager6.mStateListeners.iterator();
                while (it6.hasNext()) {
                    ((StateManager.StateListener) it6.next())
                            .onExternalDisplayUpdated(internalState6);
                }
                break;
            case 6:
                StateManager stateManager7 = this.f$0;
                StateManager.InternalState internalState7 = this.f$1;
                Iterator it7 = stateManager7.mStateListeners.iterator();
                while (it7.hasNext()) {
                    ((StateManager.StateListener) it7.next()).onTouchpadEnabled(internalState7);
                }
                break;
            case 7:
                StateManager stateManager8 = this.f$0;
                StateManager.InternalState internalState8 = this.f$1;
                Iterator it8 = stateManager8.mStateListeners.iterator();
                while (it8.hasNext()) {
                    ((StateManager.StateListener) it8.next())
                            .onTouchpadAvailabilityChanged(internalState8);
                }
                break;
            case 8:
                StateManager stateManager9 = this.f$0;
                StateManager.InternalState internalState9 = this.f$1;
                Iterator it9 = stateManager9.mStateListeners.iterator();
                while (it9.hasNext()) {
                    ((StateManager.StateListener) it9.next()).onUserChanged(internalState9);
                }
                break;
            case 9:
                StateManager stateManager10 = this.f$0;
                StateManager.InternalState internalState10 = this.f$1;
                Iterator it10 = stateManager10.mStateListeners.iterator();
                while (it10.hasNext()) {
                    ((StateManager.StateListener) it10.next())
                            .onPackageStateChanged(internalState10);
                }
                break;
            case 10:
                StateManager stateManager11 = this.f$0;
                StateManager.InternalState internalState11 = this.f$1;
                Iterator it11 = stateManager11.mStateListeners.iterator();
                while (it11.hasNext()) {
                    ((StateManager.StateListener) it11.next()).onDockStateChanged(internalState11);
                }
                break;
            case 11:
                StateManager stateManager12 = this.f$0;
                StateManager.InternalState internalState12 = this.f$1;
                Iterator it12 = stateManager12.mStateListeners.iterator();
                while (it12.hasNext()) {
                    ((StateManager.StateListener) it12.next())
                            .onDesktopModeStateChanged(internalState12);
                }
                break;
            case 12:
                Iterator it13 = this.f$0.mStateListeners.iterator();
                while (it13.hasNext()) {
                    ((StateManager.StateListener) it13.next()).onEmergencyModeChanged();
                }
                break;
            default:
                StateManager stateManager13 = this.f$0;
                StateManager.InternalState internalState13 = this.f$1;
                Iterator it14 = stateManager13.mStateListeners.iterator();
                while (it14.hasNext()) {
                    ((StateManager.StateListener) it14.next())
                            .onDockLowChargerPowerChanged(internalState13);
                }
                break;
        }
    }
}
