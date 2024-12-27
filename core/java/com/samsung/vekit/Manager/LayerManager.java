package com.samsung.vekit.Manager;

import android.util.Log;
import com.samsung.vekit.Common.Type.LayerType;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Layer.AudioLayer;
import com.samsung.vekit.Layer.CaptionLayer;
import com.samsung.vekit.Layer.DoodleLayer;
import com.samsung.vekit.Layer.ImageLayer;
import com.samsung.vekit.Layer.Layer;
import com.samsung.vekit.Layer.MediaLayer;

/* loaded from: classes6.dex */
public class LayerManager extends Manager<Layer> {
    public LayerManager(VEContext context) {
        super(context, ManagerType.LAYER);
        this.TAG = getClass().getSimpleName();
    }

    public Layer create(LayerType type, String name) {
        Layer layer;
        try {
            int uniqueId = generateUniqueId();
            switch (type) {
                case MEDIA:
                    layer = new MediaLayer(this.context, uniqueId, name);
                    break;
                case AUDIO:
                    layer = new AudioLayer(this.context, uniqueId, name);
                    break;
                case IMAGE:
                    layer = new ImageLayer(this.context, uniqueId, name);
                    break;
                case DOODLE:
                    layer = new DoodleLayer(this.context, uniqueId, name);
                    break;
                case CAPTION:
                    layer = new CaptionLayer(this.context, uniqueId, name);
                    break;
                default:
                    return null;
            }
            add(layer);
            return layer;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }
}
