package android.media.effect.effects;

import android.filterpacks.imageproc.ContrastFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class ContrastEffect extends SingleFilterEffect {
    public ContrastEffect(EffectContext effectContext, String str) {
        super(effectContext, str, ContrastFilter.class, "image", "image", new Object[0]);
    }
}
