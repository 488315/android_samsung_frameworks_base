package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Trace;
import android.provider.Settings;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SettingsProxy {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final float parseFloat(String str, float f) {
            if (str != null) {
                try {
                    return Float.parseFloat(str);
                } catch (NumberFormatException unused) {
                }
            }
            return f;
        }

        public final float parseFloatOrThrow(String str, String str2) throws Settings.SettingNotFoundException {
            if (str2 == null) {
                throw new Settings.SettingNotFoundException(str);
            }
            try {
                return Float.parseFloat(str2);
            } catch (NumberFormatException unused) {
                throw new Settings.SettingNotFoundException(str);
            }
        }

        public final long parseLongOrThrow(String str, String str2) throws Settings.SettingNotFoundException {
            if (str2 == null) {
                throw new Settings.SettingNotFoundException(str);
            }
            try {
                return Long.parseLong(str2);
            } catch (NumberFormatException unused) {
                throw new Settings.SettingNotFoundException(str);
            }
        }

        public final long parseLongOrUseDefault(String str, long j) {
            if (str != null) {
                try {
                    return Long.parseLong(str);
                } catch (NumberFormatException unused) {
                }
            }
            return j;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface CurrentUserIdProvider {
        int getUserId();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static java.lang.Object executeOnSettingsScopeDispatcher$suspendImpl(com.android.systemui.util.settings.SettingsProxy r4, java.lang.String r5, kotlin.jvm.functions.Function0 r6, kotlin.coroutines.Continuation r7) {
        /*
            boolean r0 = r7 instanceof com.android.systemui.util.settings.SettingsProxy$executeOnSettingsScopeDispatcher$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.util.settings.SettingsProxy$executeOnSettingsScopeDispatcher$1 r0 = (com.android.systemui.util.settings.SettingsProxy$executeOnSettingsScopeDispatcher$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.settings.SettingsProxy$executeOnSettingsScopeDispatcher$1 r0 = new com.android.systemui.util.settings.SettingsProxy$executeOnSettingsScopeDispatcher$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r7)
            goto L61
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.CoroutineScope r4 = r4.getSettingsScope()
            kotlin.coroutines.CoroutineContext r4 = r4.getCoroutineContext()
            kotlinx.coroutines.CoroutineDispatcher$Key r7 = kotlinx.coroutines.CoroutineDispatcher.Key
            kotlin.coroutines.CoroutineContext$Element r4 = r4.get(r7)
            kotlinx.coroutines.CoroutineDispatcher r4 = (kotlinx.coroutines.CoroutineDispatcher) r4
            if (r4 == 0) goto L64
            kotlin.coroutines.CoroutineContext r2 = r0.getContext()
            kotlin.coroutines.CoroutineContext$Element r7 = r2.get(r7)
            boolean r7 = r4.equals(r7)
            if (r7 != 0) goto L64
            com.android.systemui.util.settings.SettingsProxy$executeOnSettingsScopeDispatcher$2 r5 = new com.android.systemui.util.settings.SettingsProxy$executeOnSettingsScopeDispatcher$2
            r7 = 0
            r5.<init>(r6, r7)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r4 != r1) goto L61
            return r1
        L61:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L64:
            boolean r4 = android.os.Trace.isEnabled()
            if (r4 == 0) goto L6d
            com.android.app.tracing.TraceUtilsKt.beginSlice(r5)
        L6d:
            r6.invoke()     // Catch: java.lang.Throwable -> L7a
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L7a
            if (r4 == 0) goto L77
            com.android.app.tracing.TraceUtilsKt.endSlice()
        L77:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L7a:
            r5 = move-exception
            if (r4 == 0) goto L80
            com.android.app.tracing.TraceUtilsKt.endSlice()
        L80:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.settings.SettingsProxy.executeOnSettingsScopeDispatcher$suspendImpl(com.android.systemui.util.settings.SettingsProxy, java.lang.String, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation):java.lang.Object");
    }

    static float parseFloat(String str, float f) {
        return Companion.parseFloat(str, f);
    }

    static float parseFloatOrThrow(String str, String str2) throws Settings.SettingNotFoundException {
        return Companion.parseFloatOrThrow(str, str2);
    }

    static long parseLongOrThrow(String str, String str2) throws Settings.SettingNotFoundException {
        return Companion.parseLongOrThrow(str, str2);
    }

    static long parseLongOrUseDefault(String str, long j) {
        return Companion.parseLongOrUseDefault(str, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserver$lambda$1(SettingsProxy settingsProxy, String str, ContentObserver contentObserver) {
        settingsProxy.registerContentObserverSync(settingsProxy.getUriFor(str), contentObserver);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserver$lambda$2(SettingsProxy settingsProxy, Uri uri, ContentObserver contentObserver) {
        settingsProxy.registerContentObserverSync(uri, contentObserver);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserver$lambda$3(SettingsProxy settingsProxy, String str, boolean z, ContentObserver contentObserver) {
        settingsProxy.registerContentObserverSync(settingsProxy.getUriFor(str), z, contentObserver);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit registerContentObserver$lambda$6(SettingsProxy settingsProxy, Uri uri, boolean z, ContentObserver contentObserver) {
        settingsProxy.registerContentObserverSync(uri, z, contentObserver);
        return Unit.INSTANCE;
    }

    static Object registerContentObserver$suspendImpl(SettingsProxy settingsProxy, String str, ContentObserver contentObserver, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-A", new SettingsProxy$$ExternalSyntheticLambda2(settingsProxy, str, contentObserver), continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit unregisterContentObserver$lambda$9(SettingsProxy settingsProxy, ContentObserver contentObserver) {
        settingsProxy.unregisterContentObserverSync(contentObserver);
        return Unit.INSTANCE;
    }

    static Object unregisterContentObserver$suspendImpl(final SettingsProxy settingsProxy, final ContentObserver contentObserver, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("unregisterContentObserver", new Function0() { // from class: com.android.systemui.util.settings.SettingsProxy$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unregisterContentObserver$lambda$9;
                unregisterContentObserver$lambda$9 = SettingsProxy.unregisterContentObserver$lambda$9(SettingsProxy.this, contentObserver);
                return unregisterContentObserver$lambda$9;
            }
        }, continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    default Object executeOnSettingsScopeDispatcher(String str, Function0 function0, Continuation continuation) {
        return executeOnSettingsScopeDispatcher$suspendImpl(this, str, function0, continuation);
    }

    default boolean getBool(String str, boolean z) {
        return getInt(str, z ? 1 : 0) != 0;
    }

    ContentResolver getContentResolver();

    default float getFloat(String str, float f) {
        return Companion.parseFloat(getString(str), f);
    }

    default int getInt(String str, int i) {
        String string = getString(str);
        if (string != null) {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    default long getLong(String str, long j) {
        return Companion.parseLongOrUseDefault(getString(str), j);
    }

    CoroutineScope getSettingsScope();

    String getString(String str);

    Uri getUriFor(String str);

    default boolean putBool(String str, boolean z) {
        return putInt(str, z ? 1 : 0);
    }

    default boolean putFloat(String str, float f) {
        return putString(str, String.valueOf(f));
    }

    default boolean putInt(String str, int i) {
        return putString(str, String.valueOf(i));
    }

    default boolean putLong(String str, long j) {
        return putString(str, String.valueOf(j));
    }

    boolean putString(String str, String str2);

    boolean putString(String str, String str2, String str3, boolean z);

    default Object registerContentObserver(Uri uri, ContentObserver contentObserver, Continuation continuation) {
        return registerContentObserver$suspendImpl(this, uri, contentObserver, continuation);
    }

    default Job registerContentObserverAsync(String str, ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new SettingsProxy$registerContentObserverAsync$1(this, str, contentObserver, null), 6);
    }

    default void registerContentObserverSync(String str, ContentObserver contentObserver) {
        registerContentObserverSync(getUriFor(str), contentObserver);
    }

    default Object unregisterContentObserver(ContentObserver contentObserver, Continuation continuation) {
        return unregisterContentObserver$suspendImpl(this, contentObserver, continuation);
    }

    default Job unregisterContentObserverAsync(ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new SettingsProxy$unregisterContentObserverAsync$1(this, contentObserver, null), 6);
    }

    default void unregisterContentObserverSync(ContentObserver contentObserver) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("SP#unregisterObserver");
        }
        try {
            getContentResolver().unregisterContentObserver(contentObserver);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    default boolean getBool(String str) throws Settings.SettingNotFoundException {
        return getInt(str) != 0;
    }

    default Object registerContentObserver(Uri uri, boolean z, ContentObserver contentObserver, Continuation continuation) {
        return registerContentObserver$suspendImpl(this, uri, z, contentObserver, continuation);
    }

    default Job registerContentObserverAsync(String str, ContentObserver contentObserver, Runnable runnable) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new SettingsProxy$registerContentObserverAsync$2(this, str, contentObserver, runnable, null), 6);
    }

    default void registerContentObserverSync(Uri uri, ContentObserver contentObserver) {
        registerContentObserverSync(uri, false, contentObserver);
    }

    default float getFloat(String str) throws Settings.SettingNotFoundException {
        return Companion.parseFloatOrThrow(str, getString(str));
    }

    default int getInt(String str) throws Settings.SettingNotFoundException {
        String string = getString(str);
        if (string != null) {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException unused) {
                throw new Settings.SettingNotFoundException(str);
            }
        }
        throw new Settings.SettingNotFoundException(str);
    }

    default long getLong(String str) throws Settings.SettingNotFoundException {
        return Companion.parseLongOrThrow(str, getString(str));
    }

    default Object registerContentObserver(String str, ContentObserver contentObserver, Continuation continuation) {
        return registerContentObserver$suspendImpl(this, str, contentObserver, continuation);
    }

    default Job registerContentObserverAsync(Uri uri, ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new SettingsProxy$registerContentObserverAsync$3(this, uri, contentObserver, null), 6);
    }

    default void registerContentObserverSync(String str, boolean z, ContentObserver contentObserver) {
        registerContentObserverSync(getUriFor(str), z, contentObserver);
    }

    static Object registerContentObserver$suspendImpl(SettingsProxy settingsProxy, Uri uri, ContentObserver contentObserver, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-B", new SettingsProxy$$ExternalSyntheticLambda2(settingsProxy, uri, contentObserver), continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    default Object registerContentObserver(String str, boolean z, ContentObserver contentObserver, Continuation continuation) {
        return registerContentObserver$suspendImpl(this, str, z, contentObserver, continuation);
    }

    default Job registerContentObserverAsync(Uri uri, ContentObserver contentObserver, Runnable runnable) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new SettingsProxy$registerContentObserverAsync$4(this, uri, contentObserver, runnable, null), 6);
    }

    default void registerContentObserverSync(Uri uri, boolean z, ContentObserver contentObserver) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("SP#registerObserver#[" + uri + "]");
        }
        try {
            getContentResolver().registerContentObserver(uri, z, contentObserver);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    default Job registerContentObserverAsync(String str, boolean z, ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new SettingsProxy$registerContentObserverAsync$5(this, str, z, contentObserver, null), 6);
    }

    default Job registerContentObserverAsync(String str, boolean z, ContentObserver contentObserver, Runnable runnable) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new SettingsProxy$registerContentObserverAsync$6(this, str, z, contentObserver, runnable, null), 6);
    }

    static Object registerContentObserver$suspendImpl(SettingsProxy settingsProxy, String str, boolean z, ContentObserver contentObserver, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-C", new SettingsProxy$$ExternalSyntheticLambda1(settingsProxy, str, z, contentObserver), continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    default Job registerContentObserverAsync(Uri uri, boolean z, ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new SettingsProxy$registerContentObserverAsync$7(this, uri, z, contentObserver, null), 6);
    }

    default Job registerContentObserverAsync(Uri uri, boolean z, ContentObserver contentObserver, Runnable runnable) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new SettingsProxy$registerContentObserverAsync$8(this, uri, z, contentObserver, runnable, null), 6);
    }

    static Object registerContentObserver$suspendImpl(SettingsProxy settingsProxy, Uri uri, boolean z, ContentObserver contentObserver, Continuation continuation) {
        Object executeOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-D", new SettingsProxy$$ExternalSyntheticLambda1(settingsProxy, uri, z, contentObserver), continuation);
        return executeOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? executeOnSettingsScopeDispatcher : Unit.INSTANCE;
    }
}
