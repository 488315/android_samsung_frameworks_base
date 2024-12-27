package com.android.server.audio;

import android.media.AudioSystem;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.function.IntConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AudioService$$ExternalSyntheticLambda11 implements IntConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AudioService$$ExternalSyntheticLambda11(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                ((HashSet) obj).add(Integer.valueOf(i));
                break;
            default:
                ((PrintWriter) obj).print(AudioSystem.streamToString(i) + " ");
                break;
        }
    }
}
