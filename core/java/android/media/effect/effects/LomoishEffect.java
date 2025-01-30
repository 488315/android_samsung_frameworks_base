package android.media.effect.effects;

import android.filterpacks.imageproc.LomoishFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes2.dex */
public class LomoishEffect extends SingleFilterEffect {
    public LomoishEffect(EffectContext context, String name) {
        super(context, name, LomoishFilter.class, "image", "image", new Object[0]);
    }
}
