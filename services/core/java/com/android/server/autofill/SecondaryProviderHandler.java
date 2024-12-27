package com.android.server.autofill;

import android.content.ComponentName;
import android.content.Context;
import android.content.IntentSender;
import android.os.SystemClock;
import android.service.autofill.FillResponse;
import android.util.Slog;
import android.util.SparseArray;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SecondaryProviderHandler implements RemoteFillService.FillServiceCallbacks {
    public final Session$$ExternalSyntheticLambda0 mCallback;
    public int mLastFlag;
    public final RemoteFillService mRemoteFillService;

    public SecondaryProviderHandler(
            Context context,
            int i,
            boolean z,
            Session$$ExternalSyntheticLambda0 session$$ExternalSyntheticLambda0,
            ComponentName componentName,
            ComponentName componentName2) {
        this.mRemoteFillService =
                new RemoteFillService(context, componentName, i, this, z, componentName2);
        this.mCallback = session$$ExternalSyntheticLambda0;
        Slog.v(
                "SecondaryProviderHandler",
                "Creating a secondary provider handler with component name, " + componentName);
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public final void onFillRequestFailure(int i, Throwable th) {}

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public final void onFillRequestSuccess(int i, FillResponse fillResponse, int i2) {
        Slog.v("SecondaryProviderHandler", "Received a fill response: " + fillResponse);
        Session$$ExternalSyntheticLambda0 session$$ExternalSyntheticLambda0 = this.mCallback;
        int i3 = this.mLastFlag;
        Session session = session$$ExternalSyntheticLambda0.f$0;
        if (fillResponse == null) {
            session.getClass();
            return;
        }
        synchronized (session.mLock) {
            try {
                session.mFillResponseEventLogger.startLogForNewResponse();
                session.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(
                                fillResponse.getRequestId(), 5));
                session.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(session.uid, 2));
                session.mFillResponseEventLogger.maybeSetResponseStatus(2);
                FillResponseEventLogger fillResponseEventLogger = session.mFillResponseEventLogger;
                fillResponseEventLogger.getClass();
                fillResponseEventLogger.startResponseProcessingTimestamp =
                        SystemClock.elapsedRealtime();
                int elapsedRealtime =
                        (int) (SystemClock.elapsedRealtime() - session.mLatencyBaseTime);
                session.mPresentationStatsEventLogger.mEventInternal.ifPresent(
                        new PresentationStatsEventLogger$$ExternalSyntheticLambda1(
                                elapsedRealtime, 10));
                session.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(elapsedRealtime, 7));
                if (session.mDestroyed) {
                    Slog.w(
                            "AutofillSession",
                            "Call to Session#onSecondaryFillResponse() rejected - session: "
                                    + session.id
                                    + " destroyed");
                    session.mFillResponseEventLogger.maybeSetResponseStatus(5);
                    session.mFillResponseEventLogger.logAndEndEvent();
                    return;
                }
                List datasets = fillResponse.getDatasets();
                int size = datasets == null ? 0 : datasets.size();
                session.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(size, 6));
                session.mFillResponseEventLogger.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda0(size, 1));
                if (session.mSecondaryResponses == null) {
                    session.mSecondaryResponses = new SparseArray(2);
                }
                session.mSecondaryResponses.put(fillResponse.getRequestId(), fillResponse);
                session.setViewStatesLocked(fillResponse, 2, false, false);
                ViewState viewState = (ViewState) session.mViewStates.get(session.mCurrentViewId);
                if (viewState != null) {
                    viewState.maybeCallOnFillReady(i3);
                }
                FillResponseEventLogger fillResponseEventLogger2 = session.mFillResponseEventLogger;
                fillResponseEventLogger2.mEventInternal.ifPresent(
                        new FillResponseEventLogger$$ExternalSyntheticLambda2(
                                1, fillResponseEventLogger2));
                session.mFillResponseEventLogger.logAndEndEvent();
            } finally {
            }
        }
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public final void onSaveRequestFailure(String str, CharSequence charSequence) {}

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public final void onSaveRequestSuccess(String str, IntentSender intentSender) {}

    public final void onServiceDied(Object obj) {
        this.mRemoteFillService.unbind();
    }
}
