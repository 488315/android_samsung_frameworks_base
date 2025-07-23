package android.media.effect.effects;

import android.filterpacks.imageproc.FillLightFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class FillLightEffect extends SingleFilterEffect {
    public FillLightEffect(EffectContext effectContext, String str) {
        super(effectContext, str, FillLightFilter.class, "image", "image", new Object[0]);
    }
}
