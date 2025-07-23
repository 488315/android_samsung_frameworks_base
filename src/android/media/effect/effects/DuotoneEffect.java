package android.media.effect.effects;

import android.filterpacks.imageproc.DuotoneFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class DuotoneEffect extends SingleFilterEffect {
    public DuotoneEffect(EffectContext effectContext, String str) {
        super(effectContext, str, DuotoneFilter.class, "image", "image", new Object[0]);
    }
}
