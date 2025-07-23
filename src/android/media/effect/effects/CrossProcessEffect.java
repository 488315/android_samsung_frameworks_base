package android.media.effect.effects;

import android.filterpacks.imageproc.CrossProcessFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class CrossProcessEffect extends SingleFilterEffect {
    public CrossProcessEffect(EffectContext effectContext, String str) {
        super(effectContext, str, CrossProcessFilter.class, "image", "image", new Object[0]);
    }
}
