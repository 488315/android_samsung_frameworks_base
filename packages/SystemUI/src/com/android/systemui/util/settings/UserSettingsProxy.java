package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Trace;
import android.provider.Settings;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.util.settings.SettingsProxy;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface UserSettingsProxy extends SettingsProxy {
    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserver$lambda$0(UserSettingsProxy userSettingsProxy, Uri uri, ContentObserver contentObserver) {
        userSettingsProxy.registerContentObserverForUserSync(uri, contentObserver, userSettingsProxy.getUserId());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserver$lambda$1(UserSettingsProxy userSettingsProxy, Uri uri, boolean z, ContentObserver contentObserver) {
        userSettingsProxy.registerContentObserverForUserSync(uri, z, contentObserver, userSettingsProxy.getUserId());
        return Unit.INSTANCE;
    }

    static Object registerContentObserver$suspendImpl(final UserSettingsProxy userSettingsProxy, final Uri uri, final ContentObserver contentObserver, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-A", new Function0() { // from class: com.android.systemui.util.settings.UserSettingsProxy$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit registerContentObserver$lambda$0;
                registerContentObserver$lambda$0 = UserSettingsProxy.registerContentObserver$lambda$0(UserSettingsProxy.this, uri, contentObserver);
                return registerContentObserver$lambda$0;
            }
        }, continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserverForUser$lambda$2(UserSettingsProxy userSettingsProxy, String str, ContentObserver contentObserver, int i) {
        userSettingsProxy.registerContentObserverForUserSync(str, contentObserver, i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserverForUser$lambda$3(UserSettingsProxy userSettingsProxy, Uri uri, ContentObserver contentObserver, int i) {
        userSettingsProxy.registerContentObserverForUserSync(uri, contentObserver, i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserverForUser$lambda$4(UserSettingsProxy userSettingsProxy, String str, boolean z, ContentObserver contentObserver, int i) {
        userSettingsProxy.registerContentObserverForUserSync(str, z, contentObserver, i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserverForUser$lambda$7(UserSettingsProxy userSettingsProxy, Uri uri, boolean z, ContentObserver contentObserver, int i) {
        userSettingsProxy.registerContentObserverForUserSync(uri, z, contentObserver, userSettingsProxy.getRealUserHandle(i));
        return Unit.INSTANCE;
    }

    static Object registerContentObserverForUser$suspendImpl(UserSettingsProxy userSettingsProxy, String str, ContentObserver contentObserver, int i, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserverForUser-A", new UserSettingsProxy$$ExternalSyntheticLambda4(userSettingsProxy, str, contentObserver, i), continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean getBool(String str, boolean z) {
        return getBoolForUser(str, z, getUserId());
    }

    default boolean getBoolForUser(String str, boolean z, int i) {
        return getIntForUser(str, z ? 1 : 0, i) != 0;
    }

    SettingsProxy.CurrentUserIdProvider getCurrentUserProvider();

    default float getFloatForUser(String str, float f, int i) {
        return SettingsProxy.Companion.parseFloat(getStringForUser(str, i), f);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default int getInt(String str, int i) {
        return getIntForUser(str, i, getUserId());
    }

    default int getIntForUser(String str, int i, int i2) {
        String stringForUser = getStringForUser(str, i2);
        if (stringForUser != null) {
            try {
                return Integer.parseInt(stringForUser);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    default long getLongForUser(String str, long j, int i) {
        return SettingsProxy.Companion.parseLongOrUseDefault(getStringForUser(str, i), j);
    }

    default int getRealUserHandle(int i) {
        return i != -2 ? i : getCurrentUserProvider().getUserId();
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default String getString(String str) {
        return getStringForUser(str, getUserId());
    }

    String getStringForUser(String str, int i);

    default int getUserId() {
        return getContentResolver().getUserId();
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean putBool(String str, boolean z) {
        return putBoolForUser(str, z, getUserId());
    }

    default boolean putBoolForUser(String str, boolean z, int i) {
        return putIntForUser(str, z ? 1 : 0, i);
    }

    default boolean putFloatForUser(String str, float f, int i) {
        return putStringForUser(str, String.valueOf(f), i);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean putInt(String str, int i) {
        return putIntForUser(str, i, getUserId());
    }

    default boolean putIntForUser(String str, int i, int i2) {
        return putStringForUser(str, String.valueOf(i), i2);
    }

    default boolean putLongForUser(String str, long j, int i) {
        return putStringForUser(str, String.valueOf(j), i);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean putString(String str, String str2) {
        return putStringForUser(str, str2, getUserId());
    }

    boolean putString(String str, String str2, boolean z);

    boolean putStringForUser(String str, String str2, int i);

    boolean putStringForUser(String str, String str2, String str3, boolean z, int i, boolean z2);

    @Override // com.android.systemui.util.settings.SettingsProxy
    default Object registerContentObserver(Uri uri, ContentObserver contentObserver, Continuation continuation) {
        return registerContentObserver$suspendImpl(this, uri, contentObserver, continuation);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default Job registerContentObserverAsync(Uri uri, ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new UserSettingsProxy$registerContentObserverAsync$1(this, uri, contentObserver, null), 6);
    }

    default Object registerContentObserverForUser(Uri uri, ContentObserver contentObserver, int i, Continuation continuation) {
        return registerContentObserverForUser$suspendImpl(this, uri, contentObserver, i, continuation);
    }

    default Job registerContentObserverForUserAsync(String str, ContentObserver contentObserver, int i) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new UserSettingsProxy$registerContentObserverForUserAsync$1(this, str, contentObserver, i, null), 6);
    }

    default void registerContentObserverForUserSync(String str, ContentObserver contentObserver, int i) {
        registerContentObserverForUserSync(getUriFor(str), contentObserver, i);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default void registerContentObserverSync(Uri uri, ContentObserver contentObserver) {
        registerContentObserverForUserSync(uri, contentObserver, getUserId());
    }

    default void setUserId(int i) {
        throw new UnsupportedOperationException("userId cannot be set in interface, use setter from an implementation instead.");
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default boolean getBool(String str) throws Settings.SettingNotFoundException {
        return getBoolForUser(str, getUserId());
    }

    default boolean getBoolForUser(String str, int i) throws Settings.SettingNotFoundException {
        return getIntForUser(str, i) != 0;
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default int getInt(String str) throws Settings.SettingNotFoundException {
        return getIntForUser(str, getUserId());
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default Object registerContentObserver(Uri uri, boolean z, ContentObserver contentObserver, Continuation continuation) {
        return registerContentObserver$suspendImpl(this, uri, z, contentObserver, continuation);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default Job registerContentObserverAsync(Uri uri, boolean z, ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new UserSettingsProxy$registerContentObserverAsync$2(this, uri, z, contentObserver, null), 6);
    }

    default Object registerContentObserverForUser(Uri uri, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
        return registerContentObserverForUser$suspendImpl(this, uri, z, contentObserver, i, continuation);
    }

    default Job registerContentObserverForUserAsync(Uri uri, ContentObserver contentObserver, int i) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new UserSettingsProxy$registerContentObserverForUserAsync$2(this, uri, contentObserver, i, null), 6);
    }

    default void registerContentObserverForUserSync(Uri uri, ContentObserver contentObserver, int i) {
        registerContentObserverForUserSync(uri, false, contentObserver, i);
    }

    @Override // com.android.systemui.util.settings.SettingsProxy
    default void registerContentObserverSync(Uri uri, boolean z, ContentObserver contentObserver) {
        registerContentObserverForUserSync(uri, z, contentObserver, getUserId());
    }

    default float getFloatForUser(String str, int i) throws Settings.SettingNotFoundException {
        return SettingsProxy.Companion.parseFloatOrThrow(str, getStringForUser(str, i));
    }

    default int getIntForUser(String str, int i) throws Settings.SettingNotFoundException {
        String stringForUser = getStringForUser(str, i);
        if (stringForUser != null) {
            try {
                return Integer.parseInt(stringForUser);
            } catch (NumberFormatException unused) {
                throw new Settings.SettingNotFoundException(str);
            }
        }
        throw new Settings.SettingNotFoundException(str);
    }

    default long getLongForUser(String str, int i) throws Settings.SettingNotFoundException {
        return SettingsProxy.Companion.parseLongOrThrow(str, getStringForUser(str, i));
    }

    default Object registerContentObserverForUser(String str, ContentObserver contentObserver, int i, Continuation continuation) {
        return registerContentObserverForUser$suspendImpl(this, str, contentObserver, i, continuation);
    }

    default Job registerContentObserverForUserAsync(Uri uri, ContentObserver contentObserver, int i, Runnable runnable) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new UserSettingsProxy$registerContentObserverForUserAsync$3(this, uri, contentObserver, i, runnable, null), 6);
    }

    default void registerContentObserverForUserSync(String str, boolean z, ContentObserver contentObserver, int i) {
        registerContentObserverForUserSync(getUriFor(str), z, contentObserver, i);
    }

    static Object registerContentObserver$suspendImpl(final UserSettingsProxy userSettingsProxy, final Uri uri, final boolean z, final ContentObserver contentObserver, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-B", new Function0() { // from class: com.android.systemui.util.settings.UserSettingsProxy$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit registerContentObserver$lambda$1;
                registerContentObserver$lambda$1 = UserSettingsProxy.registerContentObserver$lambda$1(UserSettingsProxy.this, uri, z, contentObserver);
                return registerContentObserver$lambda$1;
            }
        }, continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    static Object registerContentObserverForUser$suspendImpl(UserSettingsProxy userSettingsProxy, Uri uri, ContentObserver contentObserver, int i, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserverForUser-B", new UserSettingsProxy$$ExternalSyntheticLambda4(userSettingsProxy, uri, contentObserver, i), continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    default Object registerContentObserverForUser(String str, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
        return registerContentObserverForUser$suspendImpl(this, str, z, contentObserver, i, continuation);
    }

    default void registerContentObserverForUserAsync(String str, boolean z, ContentObserver contentObserver, int i) {
        CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new UserSettingsProxy$registerContentObserverForUserAsync$4(this, str, z, contentObserver, i, null), 6);
    }

    default Job registerContentObserverForUserAsync(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new UserSettingsProxy$registerContentObserverForUserAsync$5(this, uri, z, contentObserver, i, null), 6);
    }

    default void registerContentObserverForUserSync(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("USP#registerObserver#[" + uri + "]");
        }
        try {
            getContentResolver().registerContentObserver(uri, z, contentObserver, getRealUserHandle(i));
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    static Object registerContentObserverForUser$suspendImpl(UserSettingsProxy userSettingsProxy, String str, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserverForUser-C", new UserSettingsProxy$$ExternalSyntheticLambda0(userSettingsProxy, str, z, contentObserver, i), continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    static Object registerContentObserverForUser$suspendImpl(UserSettingsProxy userSettingsProxy, Uri uri, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserverForUser-D", new UserSettingsProxy$$ExternalSyntheticLambda0(userSettingsProxy, uri, z, contentObserver, i), continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }
}
