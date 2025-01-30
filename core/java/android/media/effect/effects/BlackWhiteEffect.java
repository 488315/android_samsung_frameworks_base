package android.media.effect.effects;

import android.filterpacks.imageproc.BlackWhiteFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes2.dex */
public class BlackWhiteEffect extends SingleFilterEffect {
  public BlackWhiteEffect(EffectContext context, String name) {
    super(context, name, BlackWhiteFilter.class, "image", "image", new Object[0]);
  }
}
