package com.android.server.am;

import java.io.File;
import java.util.function.Consumer;

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
