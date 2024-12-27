package com.android.systemui.volume.panel.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.ui.Modifier;
import com.android.systemui.volume.panel.shared.model.VolumePanelUiComponent;

public interface ComposeVolumePanelUiComponent extends VolumePanelUiComponent {
    void Content(VolumePanelComposeScope volumePanelComposeScope, Modifier modifier, Composer composer, int i);
}
