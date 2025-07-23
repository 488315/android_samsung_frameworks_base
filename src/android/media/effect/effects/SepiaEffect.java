package android.media.effect.effects;

import android.filterpacks.imageproc.SepiaFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class SepiaEffect extends SingleFilterEffect {
    public SepiaEffect(EffectContext effectContext, String str) {
        super(effectContext, str, SepiaFilter.class, "image", "image", new Object[0]);
    }
}
