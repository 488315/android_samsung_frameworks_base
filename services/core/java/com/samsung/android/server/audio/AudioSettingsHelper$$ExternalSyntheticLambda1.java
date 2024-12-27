package com.samsung.android.server.audio;

import android.util.Pair;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioSettingsHelper$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ AudioSettingsHelper$$ExternalSyntheticLambda1(String str) {
        this.$r8$classId = 1;
        this.f$0 = str;
        this.f$1 = "voip_live_translate_allow";
    }

    public /* synthetic */ AudioSettingsHelper$$ExternalSyntheticLambda1(String str, String str2) {
        this.$r8$classId = 0;
        this.f$0 = str;
        this.f$1 = str2;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = this.f$0;
                String str2 = this.f$1;
                Pair pair = (Pair) obj;
                if (!((String) pair.first).equals(str) || !((String) pair.second).equals(str2)) {
                }
                break;
            default:
                String str3 = this.f$0;
                String str4 = this.f$1;
                Pair pair2 = (Pair) obj;
                if (!((String) pair2.first).equals(str3) || !((String) pair2.second).equals(str4)) {
                }
                break;
        }
        return false;
    }
}
