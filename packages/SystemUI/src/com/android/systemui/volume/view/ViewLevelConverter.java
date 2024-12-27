package com.android.systemui.volume.view;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ViewLevelConverter {
    static {
        new ViewLevelConverter();
    }

    private ViewLevelConverter() {
    }

    public static final int viewMaxLevel(VolumePanelRow volumePanelRow) {
        return VolumePanelValues.isSmartView(volumePanelRow.getStreamType()) ? volumePanelRow.getLevelMax() : volumePanelRow.getLevelMax() * 100;
    }

    public static final int viewMinLevel(VolumePanelRow volumePanelRow) {
        return VolumePanelValues.isSmartView(volumePanelRow.getStreamType()) ? volumePanelRow.getLevelMin() : volumePanelRow.getLevelMin() * 100;
    }

    public static final int viewRealLevel(VolumePanelRow volumePanelRow) {
        return (VolumePanelValues.isMediaStream(volumePanelRow.getStreamType()) || VolumePanelValues.isSmartView(volumePanelRow.getStreamType())) ? volumePanelRow.getRealLevel() : volumePanelRow.getRealLevel() * 100;
    }
}
