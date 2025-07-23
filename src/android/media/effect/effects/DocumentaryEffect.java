package android.media.effect.effects;

import android.filterpacks.imageproc.DocumentaryFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

/* loaded from: classes3.dex */
public class DocumentaryEffect extends SingleFilterEffect {
    public DocumentaryEffect(EffectContext effectContext, String str) {
        super(effectContext, str, DocumentaryFilter.class, "image", "image", new Object[0]);
    }
}
