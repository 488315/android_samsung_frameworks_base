package android.media.effect.effects;

import android.filterpacks.imageproc.NegativeFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class NegativeEffect extends SingleFilterEffect {
    public NegativeEffect(EffectContext effectContext, String str) {
        super(effectContext, str, NegativeFilter.class, "image", "image", new Object[0]);
    }
}
