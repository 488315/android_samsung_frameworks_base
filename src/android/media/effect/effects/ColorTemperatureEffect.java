package android.media.effect.effects;

import android.filterpacks.imageproc.ColorTemperatureFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class ColorTemperatureEffect extends SingleFilterEffect {
    public ColorTemperatureEffect(EffectContext effectContext, String str) {
        super(effectContext, str, ColorTemperatureFilter.class, "image", "image", new Object[0]);
    }
}
