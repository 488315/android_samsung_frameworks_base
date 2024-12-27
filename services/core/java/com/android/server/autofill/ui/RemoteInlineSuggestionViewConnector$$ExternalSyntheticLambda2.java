package com.android.server.autofill.ui;

import android.content.IntentSender;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ InlineFillUi.InlineSuggestionUiCallback f$0;

    public /* synthetic */ RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda2(
            InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback) {
        this.f$0 = inlineSuggestionUiCallback;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.startIntentSender((IntentSender) obj);
    }
}
