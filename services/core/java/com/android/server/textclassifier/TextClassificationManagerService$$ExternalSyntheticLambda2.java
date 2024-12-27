package com.android.server.textclassifier;

import android.service.textclassifier.ITextClassifierService;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifierEvent;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TextClassificationManagerService$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TextClassificationSessionId f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TextClassificationManagerService$$ExternalSyntheticLambda2(
            TextClassificationSessionId textClassificationSessionId,
            SelectionEvent selectionEvent) {
        this.$r8$classId = 2;
        this.f$0 = textClassificationSessionId;
        this.f$1 = selectionEvent;
    }

    public /* synthetic */ TextClassificationManagerService$$ExternalSyntheticLambda2(
            TextClassificationSessionId textClassificationSessionId,
            TextClassifierEvent textClassifierEvent) {
        this.$r8$classId = 0;
        this.f$0 = textClassificationSessionId;
        this.f$1 = textClassifierEvent;
    }

    public /* synthetic */ TextClassificationManagerService$$ExternalSyntheticLambda2(
            Object obj, TextClassificationSessionId textClassificationSessionId, int i) {
        this.$r8$classId = i;
        this.f$1 = obj;
        this.f$0 = textClassificationSessionId;
    }

    public final void acceptOrThrow(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((ITextClassifierService) obj)
                        .onTextClassifierEvent(this.f$0, (TextClassifierEvent) this.f$1);
                break;
            case 1:
                ((ITextClassifierService) obj)
                        .onCreateTextClassificationSession(
                                (TextClassificationContext) this.f$1, this.f$0);
                break;
            case 2:
                ((ITextClassifierService) obj)
                        .onSelectionEvent(this.f$0, (SelectionEvent) this.f$1);
                break;
            default:
                TextClassificationManagerService textClassificationManagerService =
                        (TextClassificationManagerService) this.f$1;
                TextClassificationSessionId textClassificationSessionId = this.f$0;
                textClassificationManagerService.getClass();
                ((ITextClassifierService) obj)
                        .onDestroyTextClassificationSession(textClassificationSessionId);
                textClassificationManagerService.mSessionCache.remove(
                        textClassificationSessionId.getToken());
                break;
        }
    }
}
