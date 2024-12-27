package com.android.systemui.qs.panelresource;

import com.android.systemui.QpRune;

public final class SecQSPanelResourcePickHelper {
    public final SecQSPanelResourcePhonePicker phonePicker;
    public final SecQSPanelResourceTabletPicker tabletPicker;

    public SecQSPanelResourcePickHelper() {
        SecQSPanelResourceCommon secQSPanelResourceCommon = new SecQSPanelResourceCommon();
        this.phonePicker = new SecQSPanelResourcePhonePicker(secQSPanelResourceCommon);
        new SecQSPanelResourceFoldPicker(secQSPanelResourceCommon);
        this.tabletPicker = new SecQSPanelResourceTabletPicker(secQSPanelResourceCommon);
    }

    public final SecQSPanelResourcePhonePicker getTargetPicker() {
        return QpRune.QUICK_TABLET ? this.tabletPicker : this.phonePicker;
    }
}
