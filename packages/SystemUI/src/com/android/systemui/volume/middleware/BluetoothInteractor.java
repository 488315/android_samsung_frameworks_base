package com.android.systemui.volume.middleware;

import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.util.StreamUtil;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeMiddleware;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumeState;
import com.samsung.systemui.splugins.volume.VolumeStreamState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BluetoothInteractor implements VolumeMiddleware {
    public final VolumeInfraMediator infraMediator;
    public boolean isPanelShowing;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[VolumePanelAction.ActionType.values().length];
            try {
                iArr[VolumePanelAction.ActionType.ACTION_STATE_CHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_PANEL_SHOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumePanelState.StateType.values().length];
            try {
                iArr2[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public BluetoothInteractor(VolumeDependencyBase volumeDependencyBase) {
        this.infraMediator = (VolumeInfraMediator) ((VolumeDependency) volumeDependencyBase).get(VolumeInfraMediator.class);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final Object apply(Object obj) {
        VolumePanelAction volumePanelAction = (VolumePanelAction) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelAction.getActionType().ordinal()];
        String str = null;
        boolean z = true;
        if (i == 1) {
            VolumeState volumeState = volumePanelAction.getVolumeState();
            if (volumeState != null) {
                List<VolumeStreamState> streamStates = volumeState.getStreamStates();
                if (!(streamStates instanceof Collection) || !streamStates.isEmpty()) {
                    Iterator<T> it = streamStates.iterator();
                    while (it.hasNext()) {
                        if (((VolumeStreamState) it.next()).isEnabled(VolumeStreamState.BooleanStateKey.ROUTED_TO_BT)) {
                            break;
                        }
                    }
                }
                z = false;
                List<Integer> mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(22);
                if (!z) {
                    ((ArrayList) mutableListOf).addAll(volumePanelAction.getUnImportantStreamList());
                    return new VolumePanelAction.Builder(volumePanelAction).setUnImportantStreamList(mutableListOf).build();
                }
                VolumePanelAction.Builder builder = new VolumePanelAction.Builder(volumePanelAction);
                VolumeState volumeState2 = volumePanelAction.getVolumeState();
                if (volumeState2 != null) {
                    VolumeStreamState volumeStreamState = (VolumeStreamState) CollectionsKt___CollectionsKt.getOrNull(StreamUtil.getMusicStream(volumePanelAction.isMultiSoundBt()), volumeState2.getStreamStates());
                    if (volumeStreamState != null) {
                        str = volumeStreamState.getDualBtDeviceName();
                    }
                }
                if (str != null) {
                    builder.activeBtDeviceName(str);
                }
                if (volumeState.isDualAudio()) {
                    ((ArrayList) mutableListOf).addAll(volumePanelAction.getImportantStreamList());
                    return builder.setImportantStreamList(mutableListOf).build();
                }
                ((ArrayList) mutableListOf).addAll(volumePanelAction.getUnImportantStreamList());
                return builder.setUnImportantStreamList(mutableListOf).build();
            }
        } else if (i == 2 && !this.isPanelShowing) {
            this.isPanelShowing = true;
            VolumeInfraMediator volumeInfraMediator = this.infraMediator;
            boolean isBudsTogetherEnabled = volumeInfraMediator.isBudsTogetherEnabled();
            List<Integer> importantStreamList = volumePanelAction.getImportantStreamList();
            List<Integer> unImportantStreamList = volumePanelAction.getUnImportantStreamList();
            List mutableListOf2 = CollectionsKt__CollectionsKt.mutableListOf(23);
            if (isBudsTogetherEnabled) {
                importantStreamList.addAll(mutableListOf2);
                str = volumeInfraMediator.getAudioCastDeviceName();
            } else {
                unImportantStreamList.addAll(mutableListOf2);
            }
            return new VolumePanelAction.Builder(volumePanelAction).setImportantStreamList(importantStreamList).setUnImportantStreamList(unImportantStreamList).setStringValue(VolumePanelAction.StringStateKey.AUDIO_SHARING_DEVICE_NAME, str).build();
        }
        return volumePanelAction;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final void applyState(Object obj) {
        if (WhenMappings.$EnumSwitchMapping$1[((VolumePanelState) obj).getStateType().ordinal()] == 1) {
            this.isPanelShowing = false;
        }
    }
}
