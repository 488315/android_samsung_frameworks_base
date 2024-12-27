package com.android.server.autofill;

import android.util.Slog;

import java.util.function.BiConsumer;

public final /* synthetic */ class Session$$ExternalSyntheticLambda9 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        AutofillManagerServiceImpl autofillManagerServiceImpl = (AutofillManagerServiceImpl) obj;
        Session session = (Session) obj2;
        synchronized (autofillManagerServiceImpl.mLock) {
            try {
                if (autofillManagerServiceImpl.mSessions.get(session.id) != null) {
                    session.callSaveLocked();
                    return;
                }
                Slog.w(
                        "AutofillManagerServiceImpl",
                        "handleSessionSave(): already gone: " + session.id);
            } finally {
            }
        }
    }
}
