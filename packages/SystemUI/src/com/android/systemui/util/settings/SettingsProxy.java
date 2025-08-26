package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Trace;
import android.provider.Settings;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* loaded from: classes3.dex */
public interface SettingsProxy {
    public static final Companion Companion = Companion.$$INSTANCE;

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

    public interface CurrentUserIdProvider {
        int getUserId();
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$executeOnSettingsScopeDispatcher$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SettingsProxy.executeOnSettingsScopeDispatcher$suspendImpl(SettingsProxy.this, null, null, this);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$executeOnSettingsScopeDispatcher$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function0 $block;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$block = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$block, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$block.invoke();
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$registerContentObserverAsync$1, reason: invalid class name and case insensitive filesystem */
    final class C11701 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $name;
        final /* synthetic */ ContentObserver $settingsObserver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11701(String str, ContentObserver contentObserver, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$settingsObserver = contentObserver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsProxy.this.new C11701(this.$name, this.$settingsObserver, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsProxy settingsProxy = SettingsProxy.this;
            settingsProxy.registerContentObserverSync(settingsProxy.getUriFor(this.$name), this.$settingsObserver);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11701) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$registerContentObserverAsync$2, reason: invalid class name and case insensitive filesystem */
    final class C11712 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $name;
        final /* synthetic */ Runnable $registered;
        final /* synthetic */ ContentObserver $settingsObserver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11712(String str, ContentObserver contentObserver, Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$settingsObserver = contentObserver;
            this.$registered = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsProxy.this.new C11712(this.$name, this.$settingsObserver, this.$registered, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsProxy settingsProxy = SettingsProxy.this;
            settingsProxy.registerContentObserverSync(settingsProxy.getUriFor(this.$name), this.$settingsObserver);
            this.$registered.run();
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11712) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$registerContentObserverAsync$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Uri uri, ContentObserver contentObserver, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$settingsObserver = contentObserver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsProxy.this.new AnonymousClass3(this.$uri, this.$settingsObserver, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsProxy.this.registerContentObserverSync(this.$uri, this.$settingsObserver);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$registerContentObserverAsync$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ Runnable $registered;
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(Uri uri, ContentObserver contentObserver, Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$settingsObserver = contentObserver;
            this.$registered = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsProxy.this.new AnonymousClass4(this.$uri, this.$settingsObserver, this.$registered, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsProxy.this.registerContentObserverSync(this.$uri, this.$settingsObserver);
            this.$registered.run();
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$registerContentObserverAsync$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $name;
        final /* synthetic */ boolean $notifyForDescendants;
        final /* synthetic */ ContentObserver $settingsObserver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(String str, boolean z, ContentObserver contentObserver, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$notifyForDescendants = z;
            this.$settingsObserver = contentObserver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsProxy.this.new AnonymousClass5(this.$name, this.$notifyForDescendants, this.$settingsObserver, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsProxy settingsProxy = SettingsProxy.this;
            settingsProxy.registerContentObserverSync(settingsProxy.getUriFor(this.$name), this.$notifyForDescendants, this.$settingsObserver);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$registerContentObserverAsync$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $name;
        final /* synthetic */ boolean $notifyForDescendants;
        final /* synthetic */ Runnable $registered;
        final /* synthetic */ ContentObserver $settingsObserver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(String str, boolean z, ContentObserver contentObserver, Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$notifyForDescendants = z;
            this.$settingsObserver = contentObserver;
            this.$registered = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsProxy.this.new AnonymousClass6(this.$name, this.$notifyForDescendants, this.$settingsObserver, this.$registered, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsProxy settingsProxy = SettingsProxy.this;
            settingsProxy.registerContentObserverSync(settingsProxy.getUriFor(this.$name), this.$notifyForDescendants, this.$settingsObserver);
            this.$registered.run();
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass6) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$registerContentObserverAsync$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $notifyForDescendants;
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(Uri uri, boolean z, ContentObserver contentObserver, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$notifyForDescendants = z;
            this.$settingsObserver = contentObserver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsProxy.this.new AnonymousClass7(this.$uri, this.$notifyForDescendants, this.$settingsObserver, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsProxy.this.registerContentObserverSync(this.$uri, this.$notifyForDescendants, this.$settingsObserver);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass7) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$registerContentObserverAsync$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $notifyForDescendants;
        final /* synthetic */ Runnable $registered;
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(Uri uri, boolean z, ContentObserver contentObserver, Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$notifyForDescendants = z;
            this.$settingsObserver = contentObserver;
            this.$registered = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsProxy.this.new AnonymousClass8(this.$uri, this.$notifyForDescendants, this.$settingsObserver, this.$registered, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SettingsProxy.this.registerContentObserverSync(this.$uri, this.$notifyForDescendants, this.$settingsObserver);
            this.$registered.run();
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass8) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxy$unregisterContentObserverAsync$1, reason: invalid class name and case insensitive filesystem */
    final class C11721 extends SuspendLambda implements Function2 {
        final /* synthetic */ ContentObserver $settingsObserver;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11721(ContentObserver contentObserver, Continuation continuation) {
            super(2, continuation);
            this.$settingsObserver = contentObserver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingsProxy.this.new C11721(this.$settingsObserver, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SettingsProxy settingsProxy = SettingsProxy.this;
                ContentObserver contentObserver = this.$settingsObserver;
                this.label = 1;
                if (settingsProxy.unregisterContentObserver(contentObserver, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11721) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static Object executeOnSettingsScopeDispatcher$suspendImpl(SettingsProxy settingsProxy, String str, Function0 function0, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = settingsProxy.new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineContext coroutineContext = settingsProxy.getSettingsScope().getCoroutineContext();
            CoroutineDispatcher.Key key = CoroutineDispatcher.Key;
            CoroutineDispatcher coroutineDispatcher = (CoroutineDispatcher) coroutineContext.get(key);
            if (coroutineDispatcher == null || coroutineDispatcher.equals(anonymousClass1.getContext().get(key))) {
                boolean zIsEnabled = Trace.isEnabled();
                if (zIsEnabled) {
                    TraceUtilsKt.beginSlice(str);
                }
                try {
                    function0.invoke();
                    Unit unit = Unit.INSTANCE;
                    return Unit.INSTANCE;
                } finally {
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            }
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(function0, null);
            anonymousClass1.label = 1;
            if (BuildersKt.withContext(coroutineDispatcher, anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
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
        Object objExecuteOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-A", new SettingsProxy$$ExternalSyntheticLambda2(settingsProxy, str, contentObserver), continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Unit unregisterContentObserver$lambda$9(SettingsProxy settingsProxy, ContentObserver contentObserver) {
        settingsProxy.unregisterContentObserverSync(contentObserver);
        return Unit.INSTANCE;
    }

    static Object unregisterContentObserver$suspendImpl(final SettingsProxy settingsProxy, final ContentObserver contentObserver, Continuation continuation) {
        Object objExecuteOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("unregisterContentObserver", new Function0() { // from class: com.android.systemui.util.settings.SettingsProxy$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsProxy.unregisterContentObserver$lambda$9(this.f$0, contentObserver);
            }
        }, continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
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
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new C11701(str, contentObserver, null), 6);
    }

    default void registerContentObserverSync(String str, ContentObserver contentObserver) {
        registerContentObserverSync(getUriFor(str), contentObserver);
    }

    default Object unregisterContentObserver(ContentObserver contentObserver, Continuation continuation) {
        return unregisterContentObserver$suspendImpl(this, contentObserver, continuation);
    }

    default Job unregisterContentObserverAsync(ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new C11721(contentObserver, null), 6);
    }

    default void unregisterContentObserverSync(ContentObserver contentObserver) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("SP#unregisterObserver");
        }
        try {
            getContentResolver().unregisterContentObserver(contentObserver);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (zIsEnabled) {
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
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new C11712(str, contentObserver, runnable, null), 6);
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
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass3(uri, contentObserver, null), 6);
    }

    default void registerContentObserverSync(String str, boolean z, ContentObserver contentObserver) {
        registerContentObserverSync(getUriFor(str), z, contentObserver);
    }

    static Object registerContentObserver$suspendImpl(SettingsProxy settingsProxy, Uri uri, ContentObserver contentObserver, Continuation continuation) {
        Object objExecuteOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-B", new SettingsProxy$$ExternalSyntheticLambda2(settingsProxy, uri, contentObserver), continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    default Object registerContentObserver(String str, boolean z, ContentObserver contentObserver, Continuation continuation) {
        return registerContentObserver$suspendImpl(this, str, z, contentObserver, continuation);
    }

    default Job registerContentObserverAsync(Uri uri, ContentObserver contentObserver, Runnable runnable) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass4(uri, contentObserver, runnable, null), 6);
    }

    default void registerContentObserverSync(Uri uri, boolean z, ContentObserver contentObserver) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("SP#registerObserver#[" + uri + "]");
        }
        try {
            getContentResolver().registerContentObserver(uri, z, contentObserver);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    default Job registerContentObserverAsync(String str, boolean z, ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass5(str, z, contentObserver, null), 6);
    }

    default Job registerContentObserverAsync(String str, boolean z, ContentObserver contentObserver, Runnable runnable) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass6(str, z, contentObserver, runnable, null), 6);
    }

    static Object registerContentObserver$suspendImpl(SettingsProxy settingsProxy, String str, boolean z, ContentObserver contentObserver, Continuation continuation) {
        Object objExecuteOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-C", new SettingsProxy$$ExternalSyntheticLambda1(settingsProxy, str, z, contentObserver), continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    default Job registerContentObserverAsync(Uri uri, boolean z, ContentObserver contentObserver) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass7(uri, z, contentObserver, null), 6);
    }

    default Job registerContentObserverAsync(Uri uri, boolean z, ContentObserver contentObserver, Runnable runnable) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass8(uri, z, contentObserver, runnable, null), 6);
    }

    static Object registerContentObserver$suspendImpl(SettingsProxy settingsProxy, Uri uri, boolean z, ContentObserver contentObserver, Continuation continuation) {
        Object objExecuteOnSettingsScopeDispatcher = settingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-D", new SettingsProxy$$ExternalSyntheticLambda1(settingsProxy, uri, z, contentObserver), continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
    }
}
