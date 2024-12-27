package com.android.server.autofill;

import android.os.Bundle;
import android.service.autofill.FillEventHistory;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class Session$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Session session = (Session) obj;
        switch (this.$r8$classId) {
            case 0:
                AutofillManagerServiceImpl autofillManagerServiceImpl = session.mService;
                int i = session.id;
                Bundle bundle = session.mClientState;
                synchronized (autofillManagerServiceImpl.mLock) {
                    try {
                        if (autofillManagerServiceImpl.isValidEventLocked(i, "logSaveShown()")) {
                            autofillManagerServiceImpl.mEventHistory.addEvent(
                                    new FillEventHistory.Event(
                                            3, null, bundle, null, null, null, null, null, null,
                                            null, null));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            default:
                session.removeFromService();
                return;
        }
    }
}
