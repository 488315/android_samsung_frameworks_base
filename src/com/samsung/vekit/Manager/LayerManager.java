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
    public LayerManager(VEContext vEContext) {
        super(vEContext, ManagerType.LAYER);
        this.TAG = getClass().getSimpleName();
    }

    public Layer create(LayerType layerType, String str) {
        Layer mediaLayer;
        try {
            int iGenerateUniqueId = generateUniqueId();
            int i = AnonymousClass1.$SwitchMap$com$samsung$vekit$Common$Type$LayerType[layerType.ordinal()];
            if (i == 1) {
                mediaLayer = new MediaLayer(this.context, iGenerateUniqueId, str);
            } else if (i == 2) {
                mediaLayer = new AudioLayer(this.context, iGenerateUniqueId, str);
            } else if (i == 3) {
                mediaLayer = new ImageLayer(this.context, iGenerateUniqueId, str);
            } else if (i == 4) {
                mediaLayer = new DoodleLayer(this.context, iGenerateUniqueId, str);
            } else {
                if (i != 5) {
                    return null;
                }
                mediaLayer = new CaptionLayer(this.context, iGenerateUniqueId, str);
            }
            add(mediaLayer);
            return mediaLayer;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }

    /* renamed from: com.samsung.vekit.Manager.LayerManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$LayerType;

        static {
            int[] iArr = new int[LayerType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$LayerType = iArr;
            try {
                iArr[LayerType.MEDIA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$LayerType[LayerType.AUDIO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$LayerType[LayerType.IMAGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$LayerType[LayerType.DOODLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$LayerType[LayerType.CAPTION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }
}
