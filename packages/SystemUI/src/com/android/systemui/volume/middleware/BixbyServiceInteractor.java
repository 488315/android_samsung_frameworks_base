package com.android.systemui.volume.middleware;

import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeMiddleware;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BixbyServiceInteractor implements VolumeMiddleware {
    public final VolumeInfraMediator infraMediator;
    public boolean isBixbyStreamImportant;
    public boolean isPanelShowing;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[VolumePanelAction.ActionType.values().length];
            try {
                iArr[VolumePanelAction.ActionType.ACTION_PANEL_SHOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelAction.ActionType.ACTION_SWIPE_PANEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[VolumePanelState.StateType.values().length];
            try {
                iArr2[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public BixbyServiceInteractor(VolumeDependencyBase volumeDependencyBase) {
        this.infraMediator = (VolumeInfraMediator) ((VolumeDependency) volumeDependencyBase).get(VolumeInfraMediator.class);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final Object apply(Object obj) {
        VolumePanelAction volumePanelAction = (VolumePanelAction) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelAction.getActionType().ordinal()];
        VolumeInfraMediator volumeInfraMediator = this.infraMediator;
        if (i != 1) {
            if ((i != 2 && i != 3) || this.isBixbyStreamImportant || volumeInfraMediator.isKioskModeEnabled()) {
                return volumePanelAction;
            }
            List<Integer> importantStreamList = volumePanelAction.getImportantStreamList();
            List<Integer> unImportantStreamList = volumePanelAction.getUnImportantStreamList();
            List mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(11);
            if (volumeInfraMediator.isBixbyServiceOn()) {
                importantStreamList.addAll(mutableListOf);
                this.isBixbyStreamImportant = true;
            } else {
                unImportantStreamList.addAll(mutableListOf);
            }
            return new VolumePanelAction.Builder(volumePanelAction).setImportantStreamList(importantStreamList).setUnImportantStreamList(unImportantStreamList).build();
        }
        if (this.isPanelShowing) {
            return volumePanelAction;
        }
        this.isPanelShowing = true;
        List<Integer> importantStreamList2 = volumePanelAction.getImportantStreamList();
        List<Integer> unImportantStreamList2 = volumePanelAction.getUnImportantStreamList();
        ArrayList arrayList = new ArrayList();
        arrayList.add(11);
        if (volumeInfraMediator.isKioskModeEnabled() || !volumeInfraMediator.isBixbyServiceForeground()) {
            unImportantStreamList2.addAll(arrayList);
        } else {
            importantStreamList2.addAll(arrayList);
            this.isBixbyStreamImportant = true;
        }
        volumeInfraMediator.getBixbyServiceState();
        return new VolumePanelAction.Builder(volumePanelAction).setImportantStreamList(importantStreamList2).setUnImportantStreamList(unImportantStreamList2).build();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeMiddleware
    public final void applyState(Object obj) {
        if (WhenMappings.$EnumSwitchMapping$1[((VolumePanelState) obj).getStateType().ordinal()] == 1) {
            this.isPanelShowing = false;
            this.isBixbyStreamImportant = false;
        }
    }
}
