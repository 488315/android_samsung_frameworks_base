package android.media.effect.effects;

import android.filterpacks.imageproc.CropRectFilter;
import android.media.effect.EffectContext;
import android.media.effect.SizeChangeEffect;

/* loaded from: classes2.dex */
public class CropEffect extends SizeChangeEffect {
  public CropEffect(EffectContext context, String name) {
    super(context, name, CropRectFilter.class, "image", "image", new Object[0]);
  }
}
