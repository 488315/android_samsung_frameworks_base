package android.media.effect.effects;

import android.filterpacks.imageproc.RedEyeFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class RedEyeEffect extends SingleFilterEffect {
    public RedEyeEffect(EffectContext effectContext, String str) {
        super(effectContext, str, RedEyeFilter.class, "image", "image", new Object[0]);
    }
}
