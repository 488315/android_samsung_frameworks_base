package com.android.server.credentials;

import android.credentials.selection.AuthenticationEntry;
import android.util.Pair;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ProviderGetSession$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Pair pair = (Pair) obj;
                if (((AuthenticationEntry) pair.second).getStatus() == 1 || ((AuthenticationEntry) pair.second).getStatus() == 2) {
                }
                break;
            default:
                if (((AuthenticationEntry) ((Pair) ((Map.Entry) obj).getValue()).second).getStatus() == 2) {
                }
                break;
        }
        return true;
    }
}
