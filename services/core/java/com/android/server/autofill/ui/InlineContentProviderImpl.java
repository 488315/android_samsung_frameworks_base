package com.android.server.autofill.ui;

import android.os.Handler;
import android.util.Slog;
import com.android.internal.view.inline.IInlineContentCallback;
import com.android.internal.view.inline.IInlineContentProvider;
import com.android.server.FgThread;
import com.android.server.autofill.Helper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class InlineContentProviderImpl extends IInlineContentProvider.Stub {
    public final Handler mHandler = FgThread.getHandler();
    public boolean mProvideContentCalled = false;
    public RemoteInlineSuggestionUi mRemoteInlineSuggestionUi;
    public final RemoteInlineSuggestionViewConnector mRemoteInlineSuggestionViewConnector;

    public InlineContentProviderImpl(RemoteInlineSuggestionViewConnector remoteInlineSuggestionViewConnector, RemoteInlineSuggestionUi remoteInlineSuggestionUi) {
        this.mRemoteInlineSuggestionViewConnector = remoteInlineSuggestionViewConnector;
        this.mRemoteInlineSuggestionUi = remoteInlineSuggestionUi;
    }

    public final void onSurfacePackageReleased() {
        this.mHandler.post(new InlineContentProviderImpl$$ExternalSyntheticLambda0(this, 1));
    }

    public final void provideContent(final int i, final int i2, final IInlineContentCallback iInlineContentCallback) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.autofill.ui.InlineContentProviderImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                InlineContentProviderImpl inlineContentProviderImpl = InlineContentProviderImpl.this;
                int i3 = i;
                int i4 = i2;
                IInlineContentCallback iInlineContentCallback2 = iInlineContentCallback;
                if (Helper.sVerbose) {
                    inlineContentProviderImpl.getClass();
                    Slog.v("InlineContentProviderImpl", "handleProvideContent");
                }
                if (inlineContentProviderImpl.mProvideContentCalled) {
                    return;
                }
                inlineContentProviderImpl.mProvideContentCalled = true;
                RemoteInlineSuggestionUi remoteInlineSuggestionUi = inlineContentProviderImpl.mRemoteInlineSuggestionUi;
                if (remoteInlineSuggestionUi == null || remoteInlineSuggestionUi.mWidth != i3 || remoteInlineSuggestionUi.mHeight != i4) {
                    inlineContentProviderImpl.mRemoteInlineSuggestionUi = new RemoteInlineSuggestionUi(inlineContentProviderImpl.mRemoteInlineSuggestionViewConnector, i3, i4, inlineContentProviderImpl.mHandler);
                }
                RemoteInlineSuggestionUi remoteInlineSuggestionUi2 = inlineContentProviderImpl.mRemoteInlineSuggestionUi;
                remoteInlineSuggestionUi2.getClass();
                remoteInlineSuggestionUi2.mHandler.post(new RemoteInlineSuggestionUi$$ExternalSyntheticLambda1(0, remoteInlineSuggestionUi2, iInlineContentCallback2));
                RemoteInlineSuggestionUi remoteInlineSuggestionUi3 = inlineContentProviderImpl.mRemoteInlineSuggestionUi;
                remoteInlineSuggestionUi3.getClass();
                remoteInlineSuggestionUi3.mHandler.post(new RemoteInlineSuggestionUi$$ExternalSyntheticLambda0(remoteInlineSuggestionUi3, 0));
            }
        });
    }

    public final void requestSurfacePackage() {
        this.mHandler.post(new InlineContentProviderImpl$$ExternalSyntheticLambda0(this, 0));
    }
}
