package com.samsung.vekit.Layer;

import com.samsung.vekit.Common.Type.ItemType;
import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class AudioLayer extends Layer {
    private int volume;

    public AudioLayer(VEContext context, int id, String name) {
        super(context, LayerType.AUDIO, id, name);
        this.volume = 100;
        this.availableTypes =
                new ItemType[] {
                    ItemType.AUDIO,
                    ItemType.FRAGMENT_AUDIO,
                    ItemType.VIDEO,
                    ItemType.PORTRAIT_VIDEO,
                    ItemType.EMPTY
                };
    }

    public int getVolume() {
        return this.volume;
    }

    public AudioLayer setVolume(int volume) {
        this.volume = volume;
        return this;
    }
}
