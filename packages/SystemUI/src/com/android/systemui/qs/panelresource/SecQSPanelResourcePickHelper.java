package com.android.systemui.qs.panelresource;

import com.android.systemui.QpRune;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
