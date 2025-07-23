package com.samsung.vekit.Layer;

import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class ImageLayer extends Layer {
    public ImageLayer(VEContext vEContext, int i, String str) {
        super(vEContext, LayerType.IMAGE, i, str);
        this.availableTypes = new ItemType[]{ItemType.IMAGE, ItemType.EMPTY};
    }
}
