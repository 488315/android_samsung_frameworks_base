package android.media.effect.effects;

import android.filterpacks.imageproc.StraightenFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class StraightenEffect extends SingleFilterEffect {
    public StraightenEffect(EffectContext effectContext, String str) {
        super(effectContext, str, StraightenFilter.class, "image", "image", new Object[0]);
    }
}
