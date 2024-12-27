package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.Set;

public final class EditTileViewModel {
    public final Text appName;
    public final Set availableEditActions;
    public final Icon icon;
    public final boolean isCurrent;
    public final Text label;
    public final TileSpec tileSpec;

    public EditTileViewModel(TileSpec tileSpec, Icon icon, Text text, Text text2, boolean z, Set<? extends AvailableEditActions> set) {
        this.availableEditActions = set;
    }
}
