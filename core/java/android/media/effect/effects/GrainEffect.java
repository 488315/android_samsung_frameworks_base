package android.media.effect.effects;

import android.filterpacks.imageproc.GrainFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes2.dex */
public class GrainEffect extends SingleFilterEffect {
  public GrainEffect(EffectContext context, String name) {
    super(context, name, GrainFilter.class, "image", "image", new Object[0]);
  }
}
