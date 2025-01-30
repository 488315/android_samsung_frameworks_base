package com.android.systemui.user.data.repository;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.Prefs;
import com.android.systemui.util.DeviceState;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Comparator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1", m277f = "UserRepository.kt", m278l = {189, IKnoxCustomManager.Stub.TRANSACTION_getVibrationIntensity}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserRepositoryImpl$refreshUsers$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$refreshUsers$1(UserRepositoryImpl userRepositoryImpl, Continuation<? super UserRepositoryImpl$refreshUsers$1> continuation) {
        super(2, continuation);
        this.this$0 = userRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserRepositoryImpl$refreshUsers$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$refreshUsers$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0094  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        UserHandle userHandle;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserRepositoryImpl userRepositoryImpl = this.this$0;
            CoroutineDispatcher coroutineDispatcher = userRepositoryImpl.backgroundDispatcher;
            UserRepositoryImpl$refreshUsers$1$result$1 userRepositoryImpl$refreshUsers$1$result$1 = new UserRepositoryImpl$refreshUsers$1$result$1(userRepositoryImpl, null);
            this.label = 1;
            obj = BuildersKt.withContext(coroutineDispatcher, userRepositoryImpl$refreshUsers$1$result$1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                userHandle = (UserHandle) obj;
                if (userHandle != null) {
                    this.this$0.mainUserId = userHandle.getIdentifier();
                }
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
        }
        List list = (List) obj;
        if (list != null) {
            this.this$0._userInfos.setValue(CollectionsKt___CollectionsKt.sortedWith(CollectionsKt___CollectionsKt.sortedWith(list, new Comparator() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$invokeSuspend$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((UserInfo) obj2).creationTime), Long.valueOf(((UserInfo) obj3).creationTime));
                }
            }), new Comparator() { // from class: com.android.systemui.user.data.repository.UserRepositoryImpl$refreshUsers$1$invokeSuspend$$inlined$sortedBy$2
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(((UserInfo) obj2).isGuest()), Boolean.valueOf(((UserInfo) obj3).isGuest()));
                }
            }));
            List list2 = (List) this.this$0._userInfos.getValue();
            if (list2 != null) {
                UserRepositoryImpl userRepositoryImpl2 = this.this$0;
                if (list2.size() > (!DeviceState.supportsMultipleUsers() ? 1 : 0)) {
                    Log.d("UserRepository", "refreshUsers: put SEEN_MULTI_USER as true");
                    Prefs.putBoolean(userRepositoryImpl2.appContext, "HasSeenMultiUser", true);
                }
            }
        }
        UserRepositoryImpl userRepositoryImpl3 = this.this$0;
        if (userRepositoryImpl3.mainUserId == -10000) {
            UserRepositoryImpl$refreshUsers$1$mainUser$1 userRepositoryImpl$refreshUsers$1$mainUser$1 = new UserRepositoryImpl$refreshUsers$1$mainUser$1(userRepositoryImpl3, null);
            this.label = 2;
            obj = BuildersKt.withContext(userRepositoryImpl3.backgroundDispatcher, userRepositoryImpl$refreshUsers$1$mainUser$1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            userHandle = (UserHandle) obj;
            if (userHandle != null) {
            }
        }
        return Unit.INSTANCE;
    }
}
