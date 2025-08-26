package com.android.systemui.user.data.repository;

import android.os.Handler;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.util.settings.GlobalSettings;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* loaded from: classes3.dex */
final class UserSwitcherRepositoryImpl$isEnabled$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$isEnabled$1(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r5v5, types: [kotlinx.coroutines.channels.SendChannel] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object invokeSuspend$updateState(ProducerScope producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, ContinuationImpl continuationImpl) throws Throwable {
        UserSwitcherRepositoryImpl$isEnabled$1$updateState$1 userSwitcherRepositoryImpl$isEnabled$1$updateState$1;
        ChannelExt channelExt;
        ProducerScope producerScope2;
        if (continuationImpl instanceof UserSwitcherRepositoryImpl$isEnabled$1$updateState$1) {
            userSwitcherRepositoryImpl$isEnabled$1$updateState$1 = (UserSwitcherRepositoryImpl$isEnabled$1$updateState$1) continuationImpl;
            int i = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label = i - Integer.MIN_VALUE;
            } else {
                userSwitcherRepositoryImpl$isEnabled$1$updateState$1 = new UserSwitcherRepositoryImpl$isEnabled$1$updateState$1(continuationImpl);
            }
        }
        Object obj = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ChannelExt channelExt2 = ChannelExt.INSTANCE;
            userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$0 = channelExt2;
            userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$1 = producerScope;
            userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label = 1;
            int i3 = UserSwitcherRepositoryImpl.$r8$clinit;
            userSwitcherRepositoryImpl.getClass();
            Object objWithContext = BuildersKt.withContext(userSwitcherRepositoryImpl.bgDispatcher, new UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2(userSwitcherRepositoryImpl, null), userSwitcherRepositoryImpl$isEnabled$1$updateState$1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = objWithContext;
            channelExt = channelExt2;
            producerScope2 = producerScope;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r5 = (SendChannel) userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$1;
            channelExt = (ChannelExt) userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$0;
            ResultKt.throwOnFailure(obj);
            producerScope2 = r5;
        }
        ChannelExt.trySendWithFailureLogging$default(channelExt, producerScope2, obj, "UserSwitcherRepositoryImpl");
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserSwitcherRepositoryImpl$isEnabled$1 userSwitcherRepositoryImpl$isEnabled$1 = new UserSwitcherRepositoryImpl$isEnabled$1(this.this$0, continuation);
        userSwitcherRepositoryImpl$isEnabled$1.L$0 = obj;
        return userSwitcherRepositoryImpl$isEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherRepositoryImpl$isEnabled$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0067, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r11, r10) == r0) goto L16;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        final UserSwitcherRepositoryImpl$isEnabled$1$observer$1 userSwitcherRepositoryImpl$isEnabled$1$observer$1;
        ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope2 = (ProducerScope) this.L$0;
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            final GlobalSettings globalSettings = userSwitcherRepositoryImpl.globalSetting;
            final int i2 = ((UserRepositoryImpl) userSwitcherRepositoryImpl.userRepository).getSelectedUserInfo().id;
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl2 = this.this$0;
            final Handler handler = userSwitcherRepositoryImpl.bgHandler;
            SettingObserver settingObserver = new SettingObserver(globalSettings, handler, i2) { // from class: com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1
                @Override // com.android.systemui.qs.SettingObserver
                public final void handleValueChanged(int i3, boolean z) {
                    if (z) {
                        ProducerScope producerScope3 = producerScope2;
                        CoroutineTracingKt.launchTraced$default(producerScope3, null, null, new UserSwitcherRepositoryImpl$isEnabled$1$observer$1$handleValueChanged$1(producerScope3, userSwitcherRepositoryImpl2, null), 7);
                    }
                }
            };
            settingObserver.setListening(true);
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl3 = this.this$0;
            this.L$0 = producerScope2;
            this.L$1 = settingObserver;
            this.label = 1;
            if (invokeSuspend$updateState(producerScope2, userSwitcherRepositoryImpl3, this) != coroutineSingletons) {
                userSwitcherRepositoryImpl$isEnabled$1$observer$1 = settingObserver;
                producerScope = producerScope2;
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        UserSwitcherRepositoryImpl$isEnabled$1$observer$1 userSwitcherRepositoryImpl$isEnabled$1$observer$12 = (UserSwitcherRepositoryImpl$isEnabled$1$observer$1) this.L$1;
        producerScope = (ProducerScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        userSwitcherRepositoryImpl$isEnabled$1$observer$1 = userSwitcherRepositoryImpl$isEnabled$1$observer$12;
        Function0 function0 = new Function0() { // from class: com.android.systemui.user.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                setListening(false);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
    }
}
