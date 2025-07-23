package android.media.effect.effects;

import android.filterpacks.imageproc.SaturateFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class SaturateEffect extends SingleFilterEffect {
    public SaturateEffect(EffectContext effectContext, String str) {
        super(effectContext, str, SaturateFilter.class, "image", "image", new Object[0]);
    }
}
