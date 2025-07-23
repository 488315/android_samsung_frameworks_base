package android.media.effect.effects;

import android.filterpacks.imageproc.BlackWhiteFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class BlackWhiteEffect extends SingleFilterEffect {
    public BlackWhiteEffect(EffectContext effectContext, String str) {
        super(effectContext, str, BlackWhiteFilter.class, "image", "image", new Object[0]);
    }
}
