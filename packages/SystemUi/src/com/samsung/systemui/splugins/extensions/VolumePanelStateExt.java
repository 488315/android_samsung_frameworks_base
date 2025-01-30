package com.samsung.systemui.splugins.extensions;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class VolumePanelStateExt {
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
            return volumePanelState.isMultiSoundBt() ? isActiveStream(volumePanelState, 21) : isActiveStream(volumePanelState, 3) || isActiveStream(volumePanelState, 22);
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
