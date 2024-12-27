package com.android.server.autofill.ui;

import android.util.Slog;

import com.android.server.autofill.Helper;

public final /* synthetic */ class InlineContentProviderImpl$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InlineContentProviderImpl f$0;

    public /* synthetic */ InlineContentProviderImpl$$ExternalSyntheticLambda0(
            InlineContentProviderImpl inlineContentProviderImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = inlineContentProviderImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        RemoteInlineSuggestionUi remoteInlineSuggestionUi;
        RemoteInlineSuggestionUi remoteInlineSuggestionUi2;
        int i = this.$r8$classId;
        InlineContentProviderImpl inlineContentProviderImpl = this.f$0;
        switch (i) {
            case 0:
                if (Helper.sVerbose) {
                    inlineContentProviderImpl.getClass();
                    Slog.v("InlineContentProviderImpl", "handleGetSurfacePackage");
                }
                if (inlineContentProviderImpl.mProvideContentCalled
                        && (remoteInlineSuggestionUi =
                                        inlineContentProviderImpl.mRemoteInlineSuggestionUi)
                                != null) {
                    remoteInlineSuggestionUi.mHandler.post(
                            new RemoteInlineSuggestionUi$$ExternalSyntheticLambda0(
                                    remoteInlineSuggestionUi, 0));
                    break;
                }
                break;
            default:
                if (Helper.sVerbose) {
                    inlineContentProviderImpl.getClass();
                    Slog.v("InlineContentProviderImpl", "handleOnSurfacePackageReleased");
                }
                if (inlineContentProviderImpl.mProvideContentCalled
                        && (remoteInlineSuggestionUi2 =
                                        inlineContentProviderImpl.mRemoteInlineSuggestionUi)
                                != null) {
                    remoteInlineSuggestionUi2.mHandler.post(
                            new RemoteInlineSuggestionUi$$ExternalSyntheticLambda0(
                                    remoteInlineSuggestionUi2, 1));
                    break;
                }
                break;
        }
    }
}
