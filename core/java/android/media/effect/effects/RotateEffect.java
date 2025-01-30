package android.media.effect.effects;

import android.filterpacks.imageproc.RotateFilter;
import android.media.effect.EffectContext;
import android.media.effect.SizeChangeEffect;

/* loaded from: classes2.dex */
public class RotateEffect extends SizeChangeEffect {
    public RotateEffect(EffectContext context, String name) {
        super(context, name, RotateFilter.class, "image", "image", new Object[0]);
    }
}
