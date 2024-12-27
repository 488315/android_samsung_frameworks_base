package com.android.server.autofill.ui;

import android.app.ActivityManager;
import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.content.ContentProvider;
import android.graphics.drawable.Icon;
import android.os.RemoteException;
import android.service.autofill.IInlineSuggestionUi;
import android.util.Slog;

import com.android.internal.view.inline.IInlineContentCallback;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.autofill.Helper;
import com.android.server.autofill.ui.RemoteInlineSuggestionUi.AnonymousClass1;

import java.util.Iterator;

public final /* synthetic */ class RemoteInlineSuggestionUi$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RemoteInlineSuggestionUi f$0;

    public /* synthetic */ RemoteInlineSuggestionUi$$ExternalSyntheticLambda0(
            RemoteInlineSuggestionUi remoteInlineSuggestionUi, int i) {
        this.$r8$classId = i;
        this.f$0 = remoteInlineSuggestionUi;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        RemoteInlineSuggestionUi remoteInlineSuggestionUi = this.f$0;
        switch (i) {
            case 0:
                RemoteInlineSuggestionUi$$ExternalSyntheticLambda0
                        remoteInlineSuggestionUi$$ExternalSyntheticLambda0 =
                                remoteInlineSuggestionUi.mDelayedReleaseViewRunnable;
                Slice slice = null;
                if (remoteInlineSuggestionUi$$ExternalSyntheticLambda0 != null) {
                    remoteInlineSuggestionUi.mHandler.removeCallbacks(
                            remoteInlineSuggestionUi$$ExternalSyntheticLambda0);
                    remoteInlineSuggestionUi.mDelayedReleaseViewRunnable = null;
                }
                IInlineSuggestionUi iInlineSuggestionUi =
                        remoteInlineSuggestionUi.mInlineSuggestionUi;
                if (iInlineSuggestionUi != null) {
                    try {
                        iInlineSuggestionUi.getSurfacePackage(
                                remoteInlineSuggestionUi.new AnonymousClass1());
                        break;
                    } catch (RemoteException unused) {
                        Slog.w(
                                "RemoteInlineSuggestionUi",
                                "RemoteException calling getSurfacePackage.");
                        return;
                    }
                } else if (!remoteInlineSuggestionUi.mWaitingForUiCreation) {
                    RemoteInlineSuggestionViewConnector remoteInlineSuggestionViewConnector =
                            remoteInlineSuggestionUi.mRemoteInlineSuggestionViewConnector;
                    Slice slice2 =
                            remoteInlineSuggestionViewConnector.mInlinePresentation.getSlice();
                    if (slice2 != null) {
                        int currentUser = ActivityManager.getCurrentUser();
                        Iterator<SliceItem> it = slice2.getItems().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                SliceItem next = it.next();
                                if (next.getFormat().equals("image")) {
                                    Icon icon = next.getIcon();
                                    if (icon.getType() == 4 || icon.getType() == 6) {
                                        if (ContentProvider.getUserIdFromUri(
                                                        icon.getUri(), currentUser)
                                                != currentUser) {
                                            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                                                    currentUser,
                                                    "sanitizeSlice() user: ",
                                                    " cannot access icons in Slice",
                                                    "AutofillHelper");
                                        }
                                    }
                                }
                            } else {
                                slice = slice2;
                            }
                        }
                    }
                    if (slice == null) {
                        if (Helper.sDebug) {
                            Slog.d(
                                    "RemoteInlineSuggestionViewConnector",
                                    "Skipped rendering inline suggestion.");
                        }
                    } else if (remoteInlineSuggestionViewConnector.mRemoteRenderService != null) {
                        if (Helper.sDebug) {
                            Slog.d(
                                    "RemoteInlineSuggestionViewConnector",
                                    "Request to recreate the UI");
                        }
                        remoteInlineSuggestionViewConnector.mRemoteRenderService.renderSuggestion(
                                remoteInlineSuggestionUi.mInlineSuggestionUiCallback,
                                remoteInlineSuggestionViewConnector.mInlinePresentation,
                                remoteInlineSuggestionUi.mWidth,
                                remoteInlineSuggestionUi.mHeight,
                                remoteInlineSuggestionViewConnector.mHostInputToken,
                                remoteInlineSuggestionViewConnector.mDisplayId,
                                remoteInlineSuggestionViewConnector.mUserId,
                                remoteInlineSuggestionViewConnector.mSessionId);
                    }
                    remoteInlineSuggestionUi.mWaitingForUiCreation = true;
                    break;
                } else if (Helper.sDebug) {
                    Slog.d("RemoteInlineSuggestionUi", "Inline suggestion ui is not ready");
                    break;
                }
                break;
            case 1:
                remoteInlineSuggestionUi.handleUpdateRefCount(-1);
                break;
            case 2:
                if (remoteInlineSuggestionUi.mInlineSuggestionUi != null) {
                    try {
                        if (Helper.sVerbose) {
                            Slog.v("RemoteInlineSuggestionUi", "releasing the host");
                        }
                        remoteInlineSuggestionUi.mInlineSuggestionUi
                                .releaseSurfaceControlViewHost();
                        remoteInlineSuggestionUi.mInlineSuggestionUi = null;
                    } catch (RemoteException unused2) {
                        Slog.w(
                                "RemoteInlineSuggestionUi",
                                "RemoteException calling releaseSurfaceControlViewHost");
                    }
                }
                remoteInlineSuggestionUi.mDelayedReleaseViewRunnable = null;
                break;
            case 3:
                remoteInlineSuggestionUi.mRemoteInlineSuggestionViewConnector.mOnAutofillCallback
                        .run();
                IInlineContentCallback iInlineContentCallback =
                        remoteInlineSuggestionUi.mInlineContentCallback;
                if (iInlineContentCallback != null) {
                    try {
                        iInlineContentCallback.onClick();
                        break;
                    } catch (RemoteException unused3) {
                        Slog.w("RemoteInlineSuggestionUi", "RemoteException calling onClick");
                        return;
                    }
                }
                break;
            case 4:
                IInlineContentCallback iInlineContentCallback2 =
                        remoteInlineSuggestionUi.mInlineContentCallback;
                if (iInlineContentCallback2 != null) {
                    try {
                        iInlineContentCallback2.onLongClick();
                        break;
                    } catch (RemoteException unused4) {
                        Slog.w("RemoteInlineSuggestionUi", "RemoteException calling onLongClick");
                        return;
                    }
                }
                break;
            default:
                remoteInlineSuggestionUi.mRemoteInlineSuggestionViewConnector.mOnErrorCallback
                        .run();
                break;
        }
    }
}
