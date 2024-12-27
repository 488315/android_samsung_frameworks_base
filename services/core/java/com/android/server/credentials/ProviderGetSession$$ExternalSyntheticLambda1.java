package com.android.server.credentials;

import android.credentials.selection.AuthenticationEntry;
import android.util.Pair;

import java.util.Map;
import java.util.function.Predicate;

public final /* synthetic */ class ProviderGetSession$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Pair pair = (Pair) obj;
                if (((AuthenticationEntry) pair.second).getStatus() == 1
                        || ((AuthenticationEntry) pair.second).getStatus() == 2) {}
                break;
            default:
                if (((AuthenticationEntry) ((Pair) ((Map.Entry) obj).getValue()).second).getStatus()
                        == 2) {}
                break;
        }
        return true;
    }
}
