package com.samsung.systemui.splugins.extensions;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class VolumePanelStateExt {
    public static final int $stable = 0;
    public static final VolumePanelStateExt INSTANCE = new VolumePanelStateExt();

    private VolumePanelStateExt() {
    }

    public static final boolean isAODVolumePanel(VolumePanelState volumePanelState) {
        return volumePanelState.isAodVolumePanel();
    }

    public static final boolean isActiveStream(VolumePanelState volumePanelState, int i) {
        return volumePanelState.getActiveStream() == i;
    }

    public static final boolean isDualViewEnabled(VolumePanelState volumePanelState) {
        if (volumePanelState.isDualAudio()) {
            if (volumePanelState.isMultiSoundBt() ? isActiveStream(volumePanelState, 21) : isActiveStream(volumePanelState, 3) || isActiveStream(volumePanelState, 22)) {
                return true;
            }
        }
        return false;
    }

    public final VolumePanelRow findRow(VolumePanelState volumePanelState, int i) {
        Object next;
        Iterator<T> it = volumePanelState.getVolumeRowList().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((VolumePanelRow) next).getStreamType() == i) {
                break;
            }
        }
        return (VolumePanelRow) next;
    }

    public final boolean isRowVisible(VolumePanelState volumePanelState, int i) {
        VolumePanelRow volumePanelRowFindRow = findRow(volumePanelState, i);
        if (volumePanelRowFindRow != null) {
            return volumePanelRowFindRow.isVisible();
        }
        return false;
    }

    public static /* synthetic */ void isAODVolumePanel$annotations(VolumePanelState volumePanelState) {
    }

    public static /* synthetic */ void isDualViewEnabled$annotations(VolumePanelState volumePanelState) {
    }
}
