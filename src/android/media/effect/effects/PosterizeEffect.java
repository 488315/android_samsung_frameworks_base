package android.media.effect.effects;

import android.filterpacks.imageproc.PosterizeFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class PosterizeEffect extends SingleFilterEffect {
    public PosterizeEffect(EffectContext effectContext, String str) {
        super(effectContext, str, PosterizeFilter.class, "image", "image", new Object[0]);
    }
}
