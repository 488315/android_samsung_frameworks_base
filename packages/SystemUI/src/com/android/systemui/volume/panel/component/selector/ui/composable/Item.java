package com.android.systemui.volume.panel.component.selector.ui.composable;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Item {
    public final String contentDescription;
    public final Function3 icon;
    public final Function3 label;
    public final Function0 onItemSelected;

    public Item(Function0 function0, Function3 function3, Function3 function32, String str) {
        this.onItemSelected = function0;
        this.icon = function3;
        this.label = function32;
        this.contentDescription = str;
    }
}
