package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Trace;
import android.provider.Settings;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.util.settings.SettingsProxy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* loaded from: classes3.dex */
public interface UserSettingsProxy extends SettingsProxy {

    /* renamed from: com.android.systemui.util.settings.UserSettingsProxy$registerContentObserverAsync$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Uri uri, ContentObserver contentObserver, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$settingsObserver = contentObserver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSettingsProxy.this.new AnonymousClass1(this.$uri, this.$settingsObserver, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            UserSettingsProxy userSettingsProxy = UserSettingsProxy.this;
            userSettingsProxy.registerContentObserverForUserSync(this.$uri, this.$settingsObserver, userSettingsProxy.getUserId());
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.UserSettingsProxy$registerContentObserverAsync$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $notifyForDescendants;
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ Uri $uri;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Uri uri, boolean z, ContentObserver contentObserver, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$notifyForDescendants = z;
            this.$settingsObserver = contentObserver;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSettingsProxy.this.new AnonymousClass2(this.$uri, this.$notifyForDescendants, this.$settingsObserver, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            UserSettingsProxy userSettingsProxy = UserSettingsProxy.this;
            userSettingsProxy.registerContentObserverForUserSync(this.$uri, this.$notifyForDescendants, this.$settingsObserver, userSettingsProxy.getUserId());
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.UserSettingsProxy$registerContentObserverForUserAsync$1, reason: invalid class name and case insensitive filesystem */
    final class C11731 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $name;
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ int $userHandle;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11731(String str, ContentObserver contentObserver, int i, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$settingsObserver = contentObserver;
            this.$userHandle = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSettingsProxy.this.new C11731(this.$name, this.$settingsObserver, this.$userHandle, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                UserSettingsProxy userSettingsProxy = UserSettingsProxy.this;
                userSettingsProxy.registerContentObserverForUserSync(userSettingsProxy.getUriFor(this.$name), this.$settingsObserver, this.$userHandle);
                return Unit.INSTANCE;
            } catch (SecurityException e) {
                throw new SecurityException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("registerContentObserverForUserAsync-A, name: ", this.$name), e);
            }
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11731) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.UserSettingsProxy$registerContentObserverForUserAsync$2, reason: invalid class name and case insensitive filesystem */
    final class C11742 extends SuspendLambda implements Function2 {
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ Uri $uri;
        final /* synthetic */ int $userHandle;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11742(Uri uri, ContentObserver contentObserver, int i, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$settingsObserver = contentObserver;
            this.$userHandle = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSettingsProxy.this.new C11742(this.$uri, this.$settingsObserver, this.$userHandle, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                UserSettingsProxy.this.registerContentObserverForUserSync(this.$uri, this.$settingsObserver, this.$userHandle);
                return Unit.INSTANCE;
            } catch (SecurityException e) {
                throw new SecurityException("registerContentObserverForUserAsync-B, uri: " + this.$uri, e);
            }
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C11742) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.UserSettingsProxy$registerContentObserverForUserAsync$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Runnable $registered;
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ Uri $uri;
        final /* synthetic */ int $userHandle;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Uri uri, ContentObserver contentObserver, int i, Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$settingsObserver = contentObserver;
            this.$userHandle = i;
            this.$registered = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSettingsProxy.this.new AnonymousClass3(this.$uri, this.$settingsObserver, this.$userHandle, this.$registered, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                UserSettingsProxy.this.registerContentObserverForUserSync(this.$uri, this.$settingsObserver, this.$userHandle);
                this.$registered.run();
                return Unit.INSTANCE;
            } catch (SecurityException e) {
                throw new SecurityException("registerContentObserverForUserAsync-C, uri: " + this.$uri, e);
            }
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.UserSettingsProxy$registerContentObserverForUserAsync$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $name;
        final /* synthetic */ boolean $notifyForDescendants;
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ int $userHandle;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(String str, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
            super(2, continuation);
            this.$name = str;
            this.$notifyForDescendants = z;
            this.$settingsObserver = contentObserver;
            this.$userHandle = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSettingsProxy.this.new AnonymousClass4(this.$name, this.$notifyForDescendants, this.$settingsObserver, this.$userHandle, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                UserSettingsProxy userSettingsProxy = UserSettingsProxy.this;
                userSettingsProxy.registerContentObserverForUserSync(userSettingsProxy.getUriFor(this.$name), this.$notifyForDescendants, this.$settingsObserver, this.$userHandle);
                return Unit.INSTANCE;
            } catch (SecurityException e) {
                throw new SecurityException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("registerContentObserverForUserAsync-D, name: ", this.$name), e);
            }
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.UserSettingsProxy$registerContentObserverForUserAsync$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $notifyForDescendants;
        final /* synthetic */ ContentObserver $settingsObserver;
        final /* synthetic */ Uri $uri;
        final /* synthetic */ int $userHandle;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(Uri uri, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
            super(2, continuation);
            this.$uri = uri;
            this.$notifyForDescendants = z;
            this.$settingsObserver = contentObserver;
            this.$userHandle = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserSettingsProxy.this.new AnonymousClass5(this.$uri, this.$notifyForDescendants, this.$settingsObserver, this.$userHandle, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                UserSettingsProxy.this.registerContentObserverForUserSync(this.$uri, this.$notifyForDescendants, this.$settingsObserver, this.$userHandle);
                return Unit.INSTANCE;
            } catch (SecurityException e) {
                throw new SecurityException("registerContentObserverForUserAsync-E, uri: " + this.$uri, e);
            }
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

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
        Object objExecuteOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-A", new Function0() { // from class: com.android.systemui.util.settings.UserSettingsProxy$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return UserSettingsProxy.registerContentObserver$lambda$0(this.f$0, uri, contentObserver);
            }
        }, continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
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
        Object objExecuteOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserverForUser-A", new UserSettingsProxy$$ExternalSyntheticLambda4(userSettingsProxy, str, contentObserver, i), continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
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
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass1(uri, contentObserver, null), 6);
    }

    default Object registerContentObserverForUser(Uri uri, ContentObserver contentObserver, int i, Continuation continuation) {
        return registerContentObserverForUser$suspendImpl(this, uri, contentObserver, i, continuation);
    }

    default Job registerContentObserverForUserAsync(String str, ContentObserver contentObserver, int i) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new C11731(str, contentObserver, i, null), 6);
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
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass2(uri, z, contentObserver, null), 6);
    }

    default Object registerContentObserverForUser(Uri uri, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
        return registerContentObserverForUser$suspendImpl(this, uri, z, contentObserver, i, continuation);
    }

    default Job registerContentObserverForUserAsync(Uri uri, ContentObserver contentObserver, int i) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new C11742(uri, contentObserver, i, null), 6);
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
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass3(uri, contentObserver, i, runnable, null), 6);
    }

    default void registerContentObserverForUserSync(String str, boolean z, ContentObserver contentObserver, int i) {
        registerContentObserverForUserSync(getUriFor(str), z, contentObserver, i);
    }

    static Object registerContentObserver$suspendImpl(final UserSettingsProxy userSettingsProxy, final Uri uri, final boolean z, final ContentObserver contentObserver, Continuation continuation) {
        Object objExecuteOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserver-B", new Function0() { // from class: com.android.systemui.util.settings.UserSettingsProxy$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return UserSettingsProxy.registerContentObserver$lambda$1(this.f$0, uri, z, contentObserver);
            }
        }, continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    static Object registerContentObserverForUser$suspendImpl(UserSettingsProxy userSettingsProxy, Uri uri, ContentObserver contentObserver, int i, Continuation continuation) {
        Object objExecuteOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserverForUser-B", new UserSettingsProxy$$ExternalSyntheticLambda4(userSettingsProxy, uri, contentObserver, i), continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    default Object registerContentObserverForUser(String str, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
        return registerContentObserverForUser$suspendImpl(this, str, z, contentObserver, i, continuation);
    }

    default void registerContentObserverForUserAsync(String str, boolean z, ContentObserver contentObserver, int i) {
        CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass4(str, z, contentObserver, i, null), 6);
    }

    default Job registerContentObserverForUserAsync(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        return CoroutineTracingKt.launchTraced$default(getSettingsScope(), null, null, new AnonymousClass5(uri, z, contentObserver, i, null), 6);
    }

    default void registerContentObserverForUserSync(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("USP#registerObserver#[" + uri + "]");
        }
        try {
            getContentResolver().registerContentObserver(uri, z, contentObserver, getRealUserHandle(i));
            Unit unit = Unit.INSTANCE;
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    static Object registerContentObserverForUser$suspendImpl(UserSettingsProxy userSettingsProxy, String str, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
        Object objExecuteOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserverForUser-C", new UserSettingsProxy$$ExternalSyntheticLambda0(userSettingsProxy, str, z, contentObserver, i), continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
    }

    static Object registerContentObserverForUser$suspendImpl(UserSettingsProxy userSettingsProxy, Uri uri, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
        Object objExecuteOnSettingsScopeDispatcher = userSettingsProxy.executeOnSettingsScopeDispatcher("registerContentObserverForUser-D", new UserSettingsProxy$$ExternalSyntheticLambda0(userSettingsProxy, uri, z, contentObserver, i), continuation);
        return objExecuteOnSettingsScopeDispatcher == CoroutineSingletons.COROUTINE_SUSPENDED ? objExecuteOnSettingsScopeDispatcher : Unit.INSTANCE;
    }
}
