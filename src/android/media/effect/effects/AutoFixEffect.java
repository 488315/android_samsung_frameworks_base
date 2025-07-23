package android.media.effect.effects;

import android.filterpacks.imageproc.AutoFixFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class AutoFixEffect extends SingleFilterEffect {
    public AutoFixEffect(EffectContext effectContext, String str) {
        super(effectContext, str, AutoFixFilter.class, "image", "image", new Object[0]);
    }
}
