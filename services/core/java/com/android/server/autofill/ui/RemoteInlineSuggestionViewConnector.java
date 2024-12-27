package com.android.server.autofill.ui;

import android.os.IBinder;
import android.service.autofill.InlinePresentation;
import com.android.server.autofill.RemoteInlineSuggestionRenderService;
import com.android.server.autofill.ui.InlineFillUi;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RemoteInlineSuggestionViewConnector {
    public final int mDisplayId;
    public final IBinder mHostInputToken;
    public final InlinePresentation mInlinePresentation;
    public final Runnable mOnAutofillCallback;
    public final RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda0 mOnErrorCallback;
    public final RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda0 mOnInflateCallback;
    public final RemoteInlineSuggestionRenderService mRemoteRenderService;
    public final int mSessionId;
    public final RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda2 mStartIntentSenderFromClientApp;
    public final int mUserId;

    public RemoteInlineSuggestionViewConnector(InlineFillUi.InlineFillUiInfo inlineFillUiInfo, InlinePresentation inlinePresentation, Runnable runnable, InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback) {
        this.mRemoteRenderService = inlineFillUiInfo.mRemoteRenderService;
        this.mInlinePresentation = inlinePresentation;
        this.mHostInputToken = inlineFillUiInfo.mInlineRequest.getHostInputToken();
        this.mDisplayId = inlineFillUiInfo.mInlineRequest.getHostDisplayId();
        this.mUserId = inlineFillUiInfo.mUserId;
        this.mSessionId = inlineFillUiInfo.mSessionId;
        this.mOnAutofillCallback = runnable;
        this.mOnErrorCallback = new RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda0(inlineSuggestionUiCallback, 0);
        this.mOnInflateCallback = new RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda0(inlineSuggestionUiCallback, 1);
        this.mStartIntentSenderFromClientApp = new RemoteInlineSuggestionViewConnector$$ExternalSyntheticLambda2(inlineSuggestionUiCallback);
    }
}
