package com.android.server.contentcapture;

import java.util.List;
import java.util.function.Predicate;

public final /* synthetic */ class ContentCaptureManagerService$$ExternalSyntheticLambda2
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return !((List) obj).isEmpty();
            default:
                return !((String) obj).isEmpty();
        }
    }
}
