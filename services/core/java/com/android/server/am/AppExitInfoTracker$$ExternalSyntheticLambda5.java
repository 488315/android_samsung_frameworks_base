package com.android.server.am;

import java.io.File;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppExitInfoTracker$$ExternalSyntheticLambda5
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        File file = (File) obj;
        switch (this.$r8$classId) {
            case 0:
                file.delete();
                break;
            default:
                file.delete();
                break;
        }
    }
}
