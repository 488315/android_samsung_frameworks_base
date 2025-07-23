package android.media.effect.effects;

import android.filterpacks.imageproc.VignetteFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class VignetteEffect extends SingleFilterEffect {
    public VignetteEffect(EffectContext effectContext, String str) {
        super(effectContext, str, VignetteFilter.class, "image", "image", new Object[0]);
    }
}
