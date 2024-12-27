package com.android.server.audio;

import android.media.AudioPlaybackConfiguration;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LoudnessCodecHelper$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ LoudnessCodecHelper$$ExternalSyntheticLambda1(int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = i;
        this.f$1 = i2;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                int i2 = this.f$1;
                AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) obj;
                if (audioPlaybackConfiguration.getSessionId() != i || audioPlaybackConfiguration.getClientPid() != i2) {
                }
                break;
            default:
                int i3 = this.f$0;
                int i4 = this.f$1;
                AudioPlaybackConfiguration audioPlaybackConfiguration2 = (AudioPlaybackConfiguration) obj;
                if (audioPlaybackConfiguration2.getSessionId() != i3 || audioPlaybackConfiguration2.getClientPid() != i4) {
                }
                break;
        }
        return false;
    }
}
