package android.media.effect.effects;

import android.filterpacks.imageproc.FlipFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes2.dex */
public class FlipEffect extends SingleFilterEffect {
    public FlipEffect(EffectContext context, String name) {
        super(context, name, FlipFilter.class, "image", "image", new Object[0]);
    }
}
