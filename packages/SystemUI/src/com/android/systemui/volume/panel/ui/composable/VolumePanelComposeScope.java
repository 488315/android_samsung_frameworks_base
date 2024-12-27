package com.android.systemui.volume.panel.ui.composable;

import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState;

public final class VolumePanelComposeScope {
    public final VolumePanelState state;

    public VolumePanelComposeScope(VolumePanelState volumePanelState) {
        this.state = volumePanelState;
    }
}
