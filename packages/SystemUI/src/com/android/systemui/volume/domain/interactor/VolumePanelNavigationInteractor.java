package com.android.systemui.volume.domain.interactor;

import android.content.Context;
import com.android.systemui.volume.panel.shared.flag.VolumePanelFlag;

public final class VolumePanelNavigationInteractor {
    public final VolumePanelFlag volumePanelFlag;

    public VolumePanelNavigationInteractor(Context context, VolumePanelFlag volumePanelFlag) {
        this.volumePanelFlag = volumePanelFlag;
    }
}
