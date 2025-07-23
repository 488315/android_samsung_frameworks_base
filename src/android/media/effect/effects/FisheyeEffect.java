package android.media.effect.effects;

import android.filterpacks.imageproc.FisheyeFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class FisheyeEffect extends SingleFilterEffect {
    public FisheyeEffect(EffectContext effectContext, String str) {
        super(effectContext, str, FisheyeFilter.class, "image", "image", new Object[0]);
    }
}
