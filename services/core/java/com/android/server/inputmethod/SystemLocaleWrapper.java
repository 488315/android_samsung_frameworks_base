package com.android.server.inputmethod;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.LocaleList;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public abstract class SystemLocaleWrapper {
    public static final AtomicReference sSystemLocale =
            new AtomicReference(new LocaleList(Locale.getDefault()));

    public final class LocaleChangeListener extends BroadcastReceiver {
        public final InputMethodManagerService$$ExternalSyntheticLambda2 mCallback;
        public final Context mContext;

        public LocaleChangeListener(
                Context context,
                InputMethodManagerService$$ExternalSyntheticLambda2
                        inputMethodManagerService$$ExternalSyntheticLambda2) {
            this.mContext = context;
            this.mCallback = inputMethodManagerService$$ExternalSyntheticLambda2;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                LocaleList locales = this.mContext.getResources().getConfiguration().getLocales();
                if (Objects.equals(
                        locales,
                        (LocaleList) SystemLocaleWrapper.sSystemLocale.getAndSet(locales))) {
                    return;
                }
                InputMethodManagerService inputMethodManagerService = this.mCallback.f$0;
                inputMethodManagerService.getClass();
                synchronized (ImfLock.class) {
                    try {
                        if (inputMethodManagerService.mSystemReady) {
                            for (int i :
                                    inputMethodManagerService.mUserManagerInternal.getUserIds()) {
                                InputMethodSettingsRepository.put(
                                        i,
                                        InputMethodManagerService.queryInputMethodServicesInternal(
                                                inputMethodManagerService.mContext,
                                                i,
                                                AdditionalSubtypeMapRepository.get(i),
                                                0));
                            }
                            inputMethodManagerService.postInputMethodSettingUpdatedLocked(true);
                            inputMethodManagerService.resetDefaultImeLocked(
                                    inputMethodManagerService.mContext);
                            inputMethodManagerService.updateFromSettingsLocked(true);
                            inputMethodManagerService.mIsNeedUpdateSubtypeForLocaleChanged = true;
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public static LocaleList get() {
        return (LocaleList) sSystemLocale.get();
    }
}
