package android.media.effect.effects;

import android.filterpacks.imageproc.ToGrayFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class GrayscaleEffect extends SingleFilterEffect {
    public GrayscaleEffect(EffectContext effectContext, String str) {
        super(effectContext, str, ToGrayFilter.class, "image", "image", new Object[0]);
    }
}
