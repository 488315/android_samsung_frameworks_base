package android.media.effect.effects;

import android.filterpacks.imageproc.FlipFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class FlipEffect extends SingleFilterEffect {
    public FlipEffect(EffectContext effectContext, String str) {
        super(effectContext, str, FlipFilter.class, "image", "image", new Object[0]);
    }
}
