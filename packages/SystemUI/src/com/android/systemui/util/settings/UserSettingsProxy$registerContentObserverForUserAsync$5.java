package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class UserSettingsProxy$registerContentObserverForUserAsync$5 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $notifyForDescendants;
    final /* synthetic */ ContentObserver $settingsObserver;
    final /* synthetic */ Uri $uri;
    final /* synthetic */ int $userHandle;
    int label;
    final /* synthetic */ UserSettingsProxy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSettingsProxy$registerContentObserverForUserAsync$5(UserSettingsProxy userSettingsProxy, Uri uri, boolean z, ContentObserver contentObserver, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSettingsProxy;
        this.$uri = uri;
        this.$notifyForDescendants = z;
        this.$settingsObserver = contentObserver;
        this.$userHandle = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSettingsProxy$registerContentObserverForUserAsync$5(this.this$0, this.$uri, this.$notifyForDescendants, this.$settingsObserver, this.$userHandle, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            this.this$0.registerContentObserverForUserSync(this.$uri, this.$notifyForDescendants, this.$settingsObserver, this.$userHandle);
            return Unit.INSTANCE;
        } catch (SecurityException e) {
            throw new SecurityException("registerContentObserverForUserAsync-E, uri: " + this.$uri, e);
        }
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((UserSettingsProxy$registerContentObserverForUserAsync$5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
