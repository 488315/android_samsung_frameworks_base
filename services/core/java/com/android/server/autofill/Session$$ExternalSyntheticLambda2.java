package com.android.server.autofill;

import android.os.Bundle;
import android.service.autofill.FillEventHistory;

import java.util.function.Consumer;

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
