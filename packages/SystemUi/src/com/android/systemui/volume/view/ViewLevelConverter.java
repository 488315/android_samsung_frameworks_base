package com.android.systemui.volume.view;

import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
