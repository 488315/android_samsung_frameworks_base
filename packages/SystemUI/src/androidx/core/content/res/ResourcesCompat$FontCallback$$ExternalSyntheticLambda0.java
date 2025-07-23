package androidx.core.content.res;

import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ResourcesCompat$FontCallback$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ResourcesCompat.FontCallback f$0;
    public final /* synthetic */ Typeface f$1;

    public /* synthetic */ ResourcesCompat$FontCallback$$ExternalSyntheticLambda0(ResourcesCompat.FontCallback fontCallback, Typeface typeface) {
        this.f$0 = fontCallback;
        this.f$1 = typeface;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.onFontRetrieved(this.f$1);
    }
}
