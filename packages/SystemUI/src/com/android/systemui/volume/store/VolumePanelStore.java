package com.android.systemui.volume.store;

import android.os.Trace;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.DispatchException;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.middleware.AudioManagerController;
import com.android.systemui.volume.middleware.BixbyServiceInteractor;
import com.android.systemui.volume.middleware.BluetoothInteractor;
import com.android.systemui.volume.middleware.DeviceStateController;
import com.android.systemui.volume.middleware.JSonLogger;
import com.android.systemui.volume.middleware.SALogger;
import com.android.systemui.volume.middleware.SmartViewInteractor;
import com.android.systemui.volume.reducer.VolumePanelReducer;
import com.samsung.systemui.splugins.volume.VolumeDisposable;
import com.samsung.systemui.splugins.volume.VolumeMiddleware;
import com.samsung.systemui.splugins.volume.VolumeObservable;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumeUnsubscriber;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class VolumePanelStore implements VolumeObservable, VolumeObserver {
    public boolean isWorking;
    public final LogWrapper logWrapper;
    public final ArrayList middlewares;
    public final ArrayList observers = new ArrayList();
    public VolumePanelState currentState = new VolumePanelState.Builder().build();
    public final VolumePanelReducer reducer = new VolumePanelReducer();

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

    public VolumePanelStore(VolumeDependencyBase volumeDependencyBase) {
        this.logWrapper = (LogWrapper) ((VolumeDependency) volumeDependencyBase).get(LogWrapper.class);
        this.middlewares = CollectionsKt__CollectionsKt.arrayListOf(new JSonLogger(volumeDependencyBase), new DeviceStateController(volumeDependencyBase), new AudioManagerController(volumeDependencyBase), new SmartViewInteractor(volumeDependencyBase), new BluetoothInteractor(volumeDependencyBase), new BixbyServiceInteractor(volumeDependencyBase), new SALogger(volumeDependencyBase));
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObservable
    public final /* bridge */ /* synthetic */ void dispatch(Object obj, boolean z) {
        dispatch((VolumePanelState) obj);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObservable
    public final VolumeDisposable subscribe(VolumeObserver volumeObserver) {
        if (!this.observers.contains(volumeObserver)) {
            this.observers.add(volumeObserver);
        }
        this.logWrapper.d("VolumePanelStore", "subscribe : observer count=" + this.observers.size() + ", observer=" + volumeObserver);
        return new VolumeUnsubscriber(this.observers, volumeObserver);
    }

    public final void dispatch(VolumePanelState volumePanelState) {
        ArrayList arrayList = new ArrayList(this.observers);
        this.isWorking = true;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((VolumeObserver) obj).onChanged(volumePanelState);
        }
        this.isWorking = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelAction volumePanelAction) {
        long j;
        long j2;
        VolumePanelAction volumePanelAction2 = volumePanelAction;
        String str = "VolumePanelStore#onChanged(" + volumePanelAction2.getActionType() + ")";
        boolean zIsTagEnabled = Trace.isTagEnabled(4096L);
        VolumePanelReducer volumePanelReducer = this.reducer;
        LogWrapper logWrapper = this.logWrapper;
        boolean z = zIsTagEnabled;
        if (!z) {
            if (this.isWorking) {
                throw new DispatchException("use handler!!");
            }
            if (volumePanelAction2.getActionType() != VolumePanelAction.ActionType.ACTION_PLAY_SOUND_ON) {
                logWrapper.d("VolumePanelStore", "dispatch [" + volumePanelAction2.getActionType() + " with [" + this.currentState.getStateType() + "], currentAction={" + volumePanelAction2 + "}, currentState={" + this.currentState + "}");
            }
            ArrayList arrayList = this.middlewares;
            int size = arrayList.size();
            VolumePanelAction volumePanelAction3 = volumePanelAction2;
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                volumePanelAction2 = (VolumePanelAction) ((VolumeMiddleware) obj).apply(volumePanelAction3);
                if (!Intrinsics.areEqual(volumePanelAction2, volumePanelAction3)) {
                    volumePanelAction3 = volumePanelAction2;
                }
            }
            VolumePanelState volumePanelStateReduce = volumePanelReducer.reduce(volumePanelAction2, this.currentState);
            if (volumePanelAction3.getActionType() != VolumePanelAction.ActionType.ACTION_PLAY_SOUND_ON) {
                logWrapper.d("VolumePanelStore", "  > New State : [" + volumePanelStateReduce.getStateType() + "]");
            }
            ArrayList arrayList2 = this.middlewares;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                ((VolumeMiddleware) obj2).applyState(volumePanelStateReduce);
            }
            if (volumePanelStateReduce.getStateType() == VolumePanelState.StateType.STATE_NO_DISPATCH) {
                this.currentState = volumePanelStateReduce;
                return;
            } else {
                this.currentState = volumePanelStateReduce;
                dispatch(volumePanelStateReduce);
                return;
            }
        }
        Trace.traceBegin(4096L, str);
        try {
            try {
                if (this.isWorking) {
                    throw new DispatchException("use handler!!");
                }
                if (volumePanelAction2.getActionType() != VolumePanelAction.ActionType.ACTION_PLAY_SOUND_ON) {
                    VolumePanelAction.ActionType actionType = volumePanelAction2.getActionType();
                    VolumePanelState.StateType stateType = this.currentState.getStateType();
                    j2 = 4096;
                    logWrapper.d("VolumePanelStore", "dispatch [" + actionType + " with [" + stateType + "], currentAction={" + volumePanelAction2 + "}, currentState={" + this.currentState + "}");
                } else {
                    j2 = 4096;
                }
                ArrayList arrayList3 = this.middlewares;
                int size3 = arrayList3.size();
                VolumePanelAction volumePanelAction4 = volumePanelAction2;
                int i3 = 0;
                while (i3 < size3) {
                    Object obj3 = arrayList3.get(i3);
                    i3++;
                    volumePanelAction2 = (VolumePanelAction) ((VolumeMiddleware) obj3).apply(volumePanelAction4);
                    if (!Intrinsics.areEqual(volumePanelAction2, volumePanelAction4)) {
                        volumePanelAction4 = volumePanelAction2;
                    }
                }
                VolumePanelState volumePanelStateReduce2 = volumePanelReducer.reduce(volumePanelAction2, this.currentState);
                if (volumePanelAction4.getActionType() != VolumePanelAction.ActionType.ACTION_PLAY_SOUND_ON) {
                    logWrapper.d("VolumePanelStore", "  > New State : [" + volumePanelStateReduce2.getStateType() + "]");
                }
                ArrayList arrayList4 = this.middlewares;
                int size4 = arrayList4.size();
                int i4 = 0;
                while (i4 < size4) {
                    Object obj4 = arrayList4.get(i4);
                    i4++;
                    ((VolumeMiddleware) obj4).applyState(volumePanelStateReduce2);
                }
                if (volumePanelStateReduce2.getStateType() != VolumePanelState.StateType.STATE_NO_DISPATCH) {
                    this.currentState = volumePanelStateReduce2;
                    dispatch(volumePanelStateReduce2);
                } else {
                    this.currentState = volumePanelStateReduce2;
                }
                Unit unit = Unit.INSTANCE;
                Trace.traceEnd(j2);
            } catch (Throwable th) {
                th = th;
                j = z;
                Trace.traceEnd(j);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            j = 4096;
        }
    }
}
