package com.samsung.vekit.Layer;

import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.VEContext;

public class CaptionLayer extends Layer {
    public CaptionLayer(VEContext context, int id, String name) {
        super(context, LayerType.CAPTION, id, name);
    }
}
