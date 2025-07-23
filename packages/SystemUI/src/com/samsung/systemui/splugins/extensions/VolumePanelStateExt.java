package com.samsung.systemui.splugins.extensions;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Object obj;
        Iterator<T> it = volumePanelState.getVolumeRowList().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((VolumePanelRow) obj).getStreamType() == i) {
                break;
            }
        }
        return (VolumePanelRow) obj;
    }

    public final boolean isRowVisible(VolumePanelState volumePanelState, int i) {
        VolumePanelRow findRow = findRow(volumePanelState, i);
        if (findRow != null) {
            return findRow.isVisible();
        }
        return false;
    }

    public static /* synthetic */ void isAODVolumePanel$annotations(VolumePanelState volumePanelState) {
    }

    public static /* synthetic */ void isDualViewEnabled$annotations(VolumePanelState volumePanelState) {
    }
}
