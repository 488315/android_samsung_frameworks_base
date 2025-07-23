package android.media.effect.effects;

import android.filterpacks.imageproc.SharpenFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class SharpenEffect extends SingleFilterEffect {
    public SharpenEffect(EffectContext effectContext, String str) {
        super(effectContext, str, SharpenFilter.class, "image", "image", new Object[0]);
    }
}
