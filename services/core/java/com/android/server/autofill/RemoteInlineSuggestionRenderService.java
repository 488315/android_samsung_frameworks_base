package com.android.server.autofill;

import android.content.ComponentName;
import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallback;
import android.service.autofill.IInlineSuggestionRenderService;
import android.service.autofill.IInlineSuggestionUiCallback;
import android.service.autofill.InlinePresentation;
import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import com.android.server.autofill.AutofillManagerServiceImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RemoteInlineSuggestionRenderService extends AbstractMultiplePendingRequestsRemoteService {
    public static final /* synthetic */ int $r8$clinit = 0;

    public RemoteInlineSuggestionRenderService(Context context, ComponentName componentName, int i, AutofillManagerServiceImpl.InlineSuggestionRenderCallbacksImpl inlineSuggestionRenderCallbacksImpl, boolean z, boolean z2) {
        super(context, "android.service.autofill.InlineSuggestionRenderService", componentName, i, inlineSuggestionRenderCallbacksImpl, context.getMainThreadHandler(), z ? 4194304 : 0, z2, 2);
        scheduleBind();
    }

    public final void destroySuggestionViews(final int i, final int i2) {
        scheduleAsyncRequest(new AbstractRemoteService.AsyncRequest() { // from class: com.android.server.autofill.RemoteInlineSuggestionRenderService$$ExternalSyntheticLambda0
            public final void run(IInterface iInterface) {
                ((IInlineSuggestionRenderService) iInterface).destroySuggestionViews(i, i2);
            }
        });
    }

    public final void getInlineSuggestionsRendererInfo(final RemoteCallback remoteCallback) {
        scheduleAsyncRequest(new AbstractRemoteService.AsyncRequest() { // from class: com.android.server.autofill.RemoteInlineSuggestionRenderService$$ExternalSyntheticLambda1
            public final void run(IInterface iInterface) {
                ((IInlineSuggestionRenderService) iInterface).getInlineSuggestionsRendererInfo(remoteCallback);
            }
        });
    }

    public final IInterface getServiceInterface(IBinder iBinder) {
        return IInlineSuggestionRenderService.Stub.asInterface(iBinder);
    }

    public final long getTimeoutIdleBindMillis() {
        return 0L;
    }

    public final void handleOnConnectedStateChanged(boolean z) {
        super.handleOnConnectedStateChanged(z);
    }

    public final void renderSuggestion(final IInlineSuggestionUiCallback iInlineSuggestionUiCallback, final InlinePresentation inlinePresentation, final int i, final int i2, final IBinder iBinder, final int i3, final int i4, final int i5) {
        scheduleAsyncRequest(new AbstractRemoteService.AsyncRequest() { // from class: com.android.server.autofill.RemoteInlineSuggestionRenderService$$ExternalSyntheticLambda2
            public final void run(IInterface iInterface) {
                ((IInlineSuggestionRenderService) iInterface).renderSuggestion(iInlineSuggestionUiCallback, inlinePresentation, i, i2, iBinder, i3, i4, i5);
            }
        });
    }
}
