package com.samsung.vekit.Layer;

import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class DoodleLayer extends Layer {
    public DoodleLayer(VEContext context, int id, String name) {
        super(context, LayerType.DOODLE, id, name);
        this.availableTypes = new ItemType[] {ItemType.DOODLE, ItemType.EMPTY};
    }
}
