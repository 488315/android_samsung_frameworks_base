package com.android.systemui.qs.footer.data.repository;

import android.os.Handler;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.GlobalSettings;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1", m277f = "UserSwitcherRepository.kt", m278l = {91, 92}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class UserSwitcherRepositoryImpl$isEnabled$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$isEnabled$1(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super UserSwitcherRepositoryImpl$isEnabled$1> continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r5v5, types: [kotlinx.coroutines.channels.SendChannel] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object invokeSuspend$updateState(ProducerScope producerScope, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation continuation) {
        UserSwitcherRepositoryImpl$isEnabled$1$updateState$1 userSwitcherRepositoryImpl$isEnabled$1$updateState$1;
        int i;
        ChannelExt channelExt;
        ProducerScope producerScope2;
        if (continuation instanceof UserSwitcherRepositoryImpl$isEnabled$1$updateState$1) {
            userSwitcherRepositoryImpl$isEnabled$1$updateState$1 = (UserSwitcherRepositoryImpl$isEnabled$1$updateState$1) continuation;
            int i2 = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelExt channelExt2 = ChannelExt.INSTANCE;
                    userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$0 = channelExt2;
                    userSwitcherRepositoryImpl$isEnabled$1$updateState$1.L$1 = producerScope;
                    userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label = 1;
                    int i3 = UserSwitcherRepositoryImpl.$r8$clinit;
                    userSwitcherRepositoryImpl.getClass();
                    Object withContext = BuildersKt.withContext(userSwitcherRepositoryImpl.bgDispatcher, new UserSwitcherRepositoryImpl$isUserSwitcherEnabled$2(userSwitcherRepositoryImpl, null), userSwitcherRepositoryImpl$isEnabled$1$updateState$1);
                    if (withContext == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    obj = withContext;
                    channelExt = channelExt2;
                    producerScope2 = producerScope;
                } else {
                    if (i != 1) {
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
        }
        userSwitcherRepositoryImpl$isEnabled$1$updateState$1 = new UserSwitcherRepositoryImpl$isEnabled$1$updateState$1(continuation);
        Object obj2 = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = userSwitcherRepositoryImpl$isEnabled$1$updateState$1.label;
        if (i != 0) {
        }
        ChannelExt.trySendWithFailureLogging$default(channelExt, producerScope2, obj2, "UserSwitcherRepositoryImpl");
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ProducerScope producerScope;
        final UserSwitcherRepositoryImpl$isEnabled$1$observer$1 userSwitcherRepositoryImpl$isEnabled$1$observer$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope2 = (ProducerScope) this.L$0;
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            final GlobalSettings globalSettings = userSwitcherRepositoryImpl.globalSetting;
            final Handler handler = userSwitcherRepositoryImpl.bgHandler;
            final int userId = ((UserTrackerImpl) userSwitcherRepositoryImpl.userTracker).getUserId();
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl2 = this.this$0;
            SettingObserver settingObserver = new SettingObserver(globalSettings, handler, userId) { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1$observer$1
                @Override // com.android.systemui.qs.SettingObserver
                public final void handleValueChanged(int i2, boolean z) {
                    if (z) {
                        ProducerScope producerScope3 = ProducerScope.this;
                        BuildersKt.launch$default(producerScope3, null, null, new C2184x8ca4490b(producerScope3, userSwitcherRepositoryImpl2, null), 3);
                    }
                }
            };
            settingObserver.setListening(true);
            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl3 = this.this$0;
            this.L$0 = producerScope2;
            this.L$1 = settingObserver;
            this.label = 1;
            if (invokeSuspend$updateState(producerScope2, userSwitcherRepositoryImpl3, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            producerScope = producerScope2;
            userSwitcherRepositoryImpl$isEnabled$1$observer$1 = settingObserver;
        } else {
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
        }
        Function0 function0 = new Function0() { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                setListening(false);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
