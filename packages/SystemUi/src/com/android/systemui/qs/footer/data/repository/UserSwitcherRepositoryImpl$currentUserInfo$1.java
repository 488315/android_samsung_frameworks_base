package com.android.systemui.qs.footer.data.repository;

import android.graphics.drawable.Drawable;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1", m277f = "UserSwitcherRepository.kt", m278l = {120}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class UserSwitcherRepositoryImpl$currentUserInfo$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherRepositoryImpl$currentUserInfo$1(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super UserSwitcherRepositoryImpl$currentUserInfo$1> continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserSwitcherRepositoryImpl$currentUserInfo$1 userSwitcherRepositoryImpl$currentUserInfo$1 = new UserSwitcherRepositoryImpl$currentUserInfo$1(this.this$0, continuation);
        userSwitcherRepositoryImpl$currentUserInfo$1.L$0 = obj;
        return userSwitcherRepositoryImpl$currentUserInfo$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherRepositoryImpl$currentUserInfo$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
            final UserInfoController.OnUserInfoChangedListener onUserInfoChangedListener = new UserInfoController.OnUserInfoChangedListener() { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1$1", m277f = "UserSwitcherRepository.kt", m278l = {114}, m279m = "invokeSuspend")
                /* renamed from: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1$1 */
                final class C21801 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
                    final /* synthetic */ Drawable $picture;
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    int label;
                    final /* synthetic */ UserSwitcherRepositoryImpl this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C21801(ProducerScope producerScope, Drawable drawable, UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Continuation<? super C21801> continuation) {
                        super(2, continuation);
                        this.$$this$conflatedCallbackFlow = producerScope;
                        this.$picture = drawable;
                        this.this$0 = userSwitcherRepositoryImpl;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C21801(this.$$this$conflatedCallbackFlow, this.$picture, this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C21801) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        SendChannel sendChannel;
                        Drawable drawable;
                        ChannelExt channelExt;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            ChannelExt channelExt2 = ChannelExt.INSTANCE;
                            sendChannel = this.$$this$conflatedCallbackFlow;
                            Drawable drawable2 = this.$picture;
                            UserSwitcherRepositoryImpl userSwitcherRepositoryImpl = this.this$0;
                            this.L$0 = channelExt2;
                            this.L$1 = sendChannel;
                            this.L$2 = drawable2;
                            this.label = 1;
                            int i2 = UserSwitcherRepositoryImpl.$r8$clinit;
                            userSwitcherRepositoryImpl.getClass();
                            Object withContext = BuildersKt.withContext(userSwitcherRepositoryImpl.bgDispatcher, new UserSwitcherRepositoryImpl$isGuestUser$2(userSwitcherRepositoryImpl, null), this);
                            if (withContext == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            drawable = drawable2;
                            obj = withContext;
                            channelExt = channelExt2;
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            drawable = (Drawable) this.L$2;
                            sendChannel = (SendChannel) this.L$1;
                            channelExt = (ChannelExt) this.L$0;
                            ResultKt.throwOnFailure(obj);
                        }
                        ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, new Pair(drawable, obj), "UserSwitcherRepositoryImpl");
                        return Unit.INSTANCE;
                    }
                }

                @Override // com.android.systemui.statusbar.policy.UserInfoController.OnUserInfoChangedListener
                public final void onUserInfoChanged(String str, Drawable drawable, String str2) {
                    UserSwitcherRepositoryImpl userSwitcherRepositoryImpl2 = userSwitcherRepositoryImpl;
                    ProducerScope producerScope2 = ProducerScope.this;
                    BuildersKt.launch$default(producerScope2, null, null, new C21801(producerScope2, drawable, userSwitcherRepositoryImpl2, null), 3);
                }
            };
            ((UserInfoControllerImpl) this.this$0.userInfoController).addCallback(onUserInfoChangedListener);
            final UserSwitcherRepositoryImpl userSwitcherRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$currentUserInfo$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((UserInfoControllerImpl) UserSwitcherRepositoryImpl.this.userInfoController).removeCallback(onUserInfoChangedListener);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
}
