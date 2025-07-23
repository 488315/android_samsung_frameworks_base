package android.view.textservice;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.SpellCheckerSession;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import com.android.internal.textservice.ITextServicesManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class TextServicesManager {
    private static final boolean DBG = false;
    private static final String TAG = "TextServicesManager";

    @Deprecated
    private static TextServicesManager sInstance;
    private final InputMethodManager mInputMethodManager;
    private final ITextServicesManager mService = ITextServicesManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.TEXT_SERVICES_MANAGER_SERVICE));
    private final int mUserId;

    private TextServicesManager(int i, InputMethodManager inputMethodManager) throws ServiceManager.ServiceNotFoundException {
        this.mUserId = i;
        this.mInputMethodManager = inputMethodManager;
    }

    public static TextServicesManager createInstance(Context context) throws ServiceManager.ServiceNotFoundException {
        return new TextServicesManager(context.getUserId(), (InputMethodManager) context.getSystemService(InputMethodManager.class));
    }

    public static TextServicesManager getInstance() {
        TextServicesManager textServicesManager;
        synchronized (TextServicesManager.class) {
            if (sInstance == null) {
                try {
                    sInstance = new TextServicesManager(UserHandle.myUserId(), null);
                } catch (ServiceManager.ServiceNotFoundException e) {
                    throw new IllegalStateException(e);
                }
            }
            textServicesManager = sInstance;
        }
        return textServicesManager;
    }

    public InputMethodManager getInputMethodManager() {
        return this.mInputMethodManager;
    }

    private static String parseLanguageFromLocaleString(String str) {
        int indexOf = str.indexOf(95);
        return indexOf < 0 ? str : str.substring(0, indexOf);
    }

    public SpellCheckerSession newSpellCheckerSession(Bundle bundle, Locale locale, SpellCheckerSession.SpellCheckerSessionListener spellCheckerSessionListener, boolean z) {
        SpellCheckerSession.SpellCheckerSessionParams.Builder supportedAttributes = new SpellCheckerSession.SpellCheckerSessionParams.Builder().setLocale(locale).setShouldReferToSpellCheckerLanguageSettings(z).setSupportedAttributes(7);
        if (bundle != null) {
            supportedAttributes.setExtras(bundle);
        }
        return newSpellCheckerSession(supportedAttributes.build(), new HandlerExecutor(new Handler()), spellCheckerSessionListener);
    }

    public SpellCheckerSession newSpellCheckerSession(SpellCheckerSession.SpellCheckerSessionParams spellCheckerSessionParams, Executor executor, SpellCheckerSession.SpellCheckerSessionListener spellCheckerSessionListener) {
        SpellCheckerSubtype spellCheckerSubtype;
        Objects.requireNonNull(executor);
        Objects.requireNonNull(spellCheckerSessionListener);
        Locale locale = spellCheckerSessionParams.getLocale();
        if (!spellCheckerSessionParams.shouldReferToSpellCheckerLanguageSettings() && locale == null) {
            throw new IllegalArgumentException("Locale should not be null if you don't refer settings.");
        }
        if (spellCheckerSessionParams.shouldReferToSpellCheckerLanguageSettings() && !isSpellCheckerEnabled()) {
            return null;
        }
        try {
            SpellCheckerInfo currentSpellChecker = this.mService.getCurrentSpellChecker(this.mUserId, null);
            if (currentSpellChecker == null) {
                return null;
            }
            if (spellCheckerSessionParams.shouldReferToSpellCheckerLanguageSettings()) {
                spellCheckerSubtype = getCurrentSpellCheckerSubtype(true);
                if (spellCheckerSubtype == null) {
                    return null;
                }
                if (locale != null) {
                    String parseLanguageFromLocaleString = parseLanguageFromLocaleString(spellCheckerSubtype.getLocale());
                    if (parseLanguageFromLocaleString.length() < 2 || !locale.getLanguage().equals(parseLanguageFromLocaleString)) {
                        return null;
                    }
                }
            } else {
                String locale2 = locale.toString();
                int i = 0;
                SpellCheckerSubtype spellCheckerSubtype2 = null;
                while (true) {
                    if (i >= currentSpellChecker.getSubtypeCount()) {
                        spellCheckerSubtype = spellCheckerSubtype2;
                        break;
                    }
                    SpellCheckerSubtype subtypeAt = currentSpellChecker.getSubtypeAt(i);
                    String locale3 = subtypeAt.getLocale();
                    String parseLanguageFromLocaleString2 = parseLanguageFromLocaleString(locale3);
                    if (locale3.equals(locale2)) {
                        spellCheckerSubtype = subtypeAt;
                        break;
                    }
                    if (parseLanguageFromLocaleString2.length() >= 2 && locale.getLanguage().equals(parseLanguageFromLocaleString2)) {
                        spellCheckerSubtype2 = subtypeAt;
                    }
                    i++;
                }
            }
            if (spellCheckerSubtype == null) {
                return null;
            }
            SpellCheckerSession spellCheckerSession = new SpellCheckerSession(currentSpellChecker, this, spellCheckerSessionListener, executor);
            try {
                this.mService.getSpellCheckerService(this.mUserId, currentSpellChecker.getId(), spellCheckerSubtype.getLocale(), spellCheckerSession.getTextServicesSessionListener(), spellCheckerSession.getSpellCheckerSessionListener(), spellCheckerSessionParams.getExtras(), spellCheckerSessionParams.getSupportedAttributes());
                return spellCheckerSession;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (RemoteException unused) {
            return null;
        }
    }

    public SpellCheckerInfo[] getEnabledSpellCheckers() {
        try {
            return this.mService.getEnabledSpellCheckers(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<SpellCheckerInfo> getEnabledSpellCheckerInfos() {
        SpellCheckerInfo[] enabledSpellCheckers = getEnabledSpellCheckers();
        return enabledSpellCheckers != null ? Arrays.asList(enabledSpellCheckers) : Collections.EMPTY_LIST;
    }

    public SpellCheckerInfo getCurrentSpellCheckerInfo() {
        try {
            return this.mService.getCurrentSpellChecker(this.mUserId, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SpellCheckerInfo getCurrentSpellChecker() {
        return getCurrentSpellCheckerInfo();
    }

    public SpellCheckerSubtype getCurrentSpellCheckerSubtype(boolean z) {
        try {
            return this.mService.getCurrentSpellCheckerSubtype(this.mUserId, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSpellCheckerEnabled() {
        try {
            return this.mService.isSpellCheckerEnabled(this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void finishSpellCheckerService(ISpellCheckerSessionListener iSpellCheckerSessionListener) {
        try {
            this.mService.finishSpellCheckerService(this.mUserId, iSpellCheckerSessionListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
