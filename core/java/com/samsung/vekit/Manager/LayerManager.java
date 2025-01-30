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
            switch (C53851.$SwitchMap$com$samsung$vekit$Common$Type$LayerType[type.ordinal()]) {
                case 1:
                    layer = new MediaLayer(this.context, uniqueId, name);
                    break;
                case 2:
                    layer = new AudioLayer(this.context, uniqueId, name);
                    break;
                case 3:
                    layer = new ImageLayer(this.context, uniqueId, name);
                    break;
                case 4:
                    layer = new DoodleLayer(this.context, uniqueId, name);
                    break;
                case 5:
                    layer = new CaptionLayer(this.context, uniqueId, name);
                    break;
                default:
                    return null;
            }
            add(layer);
            return layer;
        } catch (Exception e) {
            Log.m97e(this.TAG, "create: ", e);
            return null;
        }
    }

    /* renamed from: com.samsung.vekit.Manager.LayerManager$1 */
    static /* synthetic */ class C53851 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$LayerType;

        static {
            int[] iArr = new int[LayerType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$LayerType = iArr;
            try {
                iArr[LayerType.MEDIA.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$LayerType[LayerType.AUDIO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$LayerType[LayerType.IMAGE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$LayerType[LayerType.DOODLE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$LayerType[LayerType.CAPTION.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }
}
