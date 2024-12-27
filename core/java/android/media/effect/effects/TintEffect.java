package android.media.effect.effects;

import android.filterpacks.imageproc.TintFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes2.dex */
public class TintEffect extends SingleFilterEffect {
    public TintEffect(EffectContext context, String name) {
        super(context, name, TintFilter.class, "image", "image", new Object[0]);
    }
}
