package com.android.server.textclassifier;

import android.util.Slog;
import android.view.textclassifier.TextClassificationManager;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IndentingPrintWriter;

public final /* synthetic */ class TextClassificationManagerService$$ExternalSyntheticLambda1
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TextClassificationManagerService$$ExternalSyntheticLambda1(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                ((TextClassificationManager)
                                ((TextClassificationManagerService) this.f$0)
                                        .mContext.getSystemService(TextClassificationManager.class))
                        .dump((IndentingPrintWriter) this.f$1);
                break;
            default:
                try {
                    ((FunctionalUtils.ThrowingConsumer) this.f$0)
                            .accept(
                                    ((TextClassificationManagerService.ServiceState) this.f$1)
                                            .mService);
                    break;
                } catch (Error | RuntimeException e) {
                    Slog.e(
                            "TextClassificationManagerService",
                            "Exception when consume textClassifierService: " + e);
                }
        }
    }
}
