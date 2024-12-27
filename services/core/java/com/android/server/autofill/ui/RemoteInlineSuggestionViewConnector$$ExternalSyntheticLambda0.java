package com.android.server.autofill.ui;


public final /* synthetic */ class RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InlineFillUi.InlineSuggestionUiCallback f$0;

    public /* synthetic */ RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda0(
            InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = inlineSuggestionUiCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback = this.f$0;
        switch (i) {
            case 0:
                inlineSuggestionUiCallback.onError();
                break;
            default:
                inlineSuggestionUiCallback.onInflate();
                break;
        }
    }
}
