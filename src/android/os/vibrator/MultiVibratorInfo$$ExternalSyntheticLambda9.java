package android.os.vibrator;

import android.os.VibratorInfo;
import java.util.function.Function;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class MultiVibratorInfo$$ExternalSyntheticLambda9 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Float.valueOf(((VibratorInfo) obj).getResonantFrequencyHz());
    }
}
