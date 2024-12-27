package com.android.server.textclassifier;

import android.service.textclassifier.ITextClassifierCallback;
import android.service.textclassifier.ITextClassifierService;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class TextClassificationManagerService$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ int $r8$classId = 4;
    public final /* synthetic */ TextClassificationSessionId f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ ITextClassifierCallback f$2;

    public /* synthetic */ TextClassificationManagerService$$ExternalSyntheticLambda0(
            TextClassificationSessionId textClassificationSessionId,
            ConversationActions.Request request,
            ITextClassifierCallback iTextClassifierCallback) {
        this.f$0 = textClassificationSessionId;
        this.f$1 = request;
        this.f$2 = iTextClassifierCallback;
    }

    public /* synthetic */ TextClassificationManagerService$$ExternalSyntheticLambda0(
            TextClassificationSessionId textClassificationSessionId,
            TextClassification.Request request,
            ITextClassifierCallback iTextClassifierCallback) {
        this.f$0 = textClassificationSessionId;
        this.f$1 = request;
        this.f$2 = iTextClassifierCallback;
    }

    public /* synthetic */ TextClassificationManagerService$$ExternalSyntheticLambda0(
            TextClassificationSessionId textClassificationSessionId,
            TextLanguage.Request request,
            ITextClassifierCallback iTextClassifierCallback) {
        this.f$0 = textClassificationSessionId;
        this.f$1 = request;
        this.f$2 = iTextClassifierCallback;
    }

    public /* synthetic */ TextClassificationManagerService$$ExternalSyntheticLambda0(
            TextClassificationSessionId textClassificationSessionId,
            TextLinks.Request request,
            ITextClassifierCallback iTextClassifierCallback) {
        this.f$0 = textClassificationSessionId;
        this.f$1 = request;
        this.f$2 = iTextClassifierCallback;
    }

    public /* synthetic */ TextClassificationManagerService$$ExternalSyntheticLambda0(
            TextClassificationSessionId textClassificationSessionId,
            TextSelection.Request request,
            ITextClassifierCallback iTextClassifierCallback) {
        this.f$0 = textClassificationSessionId;
        this.f$1 = request;
        this.f$2 = iTextClassifierCallback;
    }

    public final void acceptOrThrow(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((ITextClassifierService) obj)
                        .onSuggestSelection(
                                this.f$0,
                                (TextSelection.Request) this.f$1,
                                new TextClassificationManagerService.CallbackWrapper(this.f$2));
                break;
            case 1:
                ((ITextClassifierService) obj)
                        .onGenerateLinks(this.f$0, (TextLinks.Request) this.f$1, this.f$2);
                break;
            case 2:
                ((ITextClassifierService) obj)
                        .onClassifyText(
                                this.f$0,
                                (TextClassification.Request) this.f$1,
                                new TextClassificationManagerService.CallbackWrapper(this.f$2));
                break;
            case 3:
                ((ITextClassifierService) obj)
                        .onDetectLanguage(this.f$0, (TextLanguage.Request) this.f$1, this.f$2);
                break;
            default:
                ((ITextClassifierService) obj)
                        .onSuggestConversationActions(
                                this.f$0,
                                (ConversationActions.Request) this.f$1,
                                new TextClassificationManagerService.CallbackWrapper(this.f$2));
                break;
        }
    }
}
