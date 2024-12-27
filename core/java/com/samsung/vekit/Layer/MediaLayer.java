package com.samsung.vekit.Layer;

import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class MediaLayer extends Layer {
    private int volume;

    public MediaLayer(VEContext context, int id, String name) {
        super(context, LayerType.MEDIA, id, name);
        this.volume = 100;
        this.availableTypes =
                new ItemType[] {
                    ItemType.IMAGE,
                    ItemType.VIDEO,
                    ItemType.COLOR,
                    ItemType.AUDIO,
                    ItemType.EMPTY,
                    ItemType.PORTRAIT_VIDEO
                };
    }

    public int getVolume() {
        return this.volume;
    }

    public MediaLayer setVolume(int volume) {
        this.volume = volume;
        return this;
    }
}
