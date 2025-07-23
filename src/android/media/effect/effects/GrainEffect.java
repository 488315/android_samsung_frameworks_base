package android.media.effect.effects;

import android.filterpacks.imageproc.GrainFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class GrainEffect extends SingleFilterEffect {
    public GrainEffect(EffectContext effectContext, String str) {
        super(effectContext, str, GrainFilter.class, "image", "image", new Object[0]);
    }
}
