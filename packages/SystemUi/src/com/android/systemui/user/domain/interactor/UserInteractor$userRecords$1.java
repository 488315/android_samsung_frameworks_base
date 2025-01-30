package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.user.data.source.UserRecord;
import com.android.systemui.user.shared.model.UserActionModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$userRecords$1", m277f = "UserInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut, 257}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class UserInteractor$userRecords$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    int label;
    final /* synthetic */ UserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserInteractor$userRecords$1(UserInteractor userInteractor, Continuation<? super UserInteractor$userRecords$1> continuation) {
        super(5, continuation);
        this.this$0 = userInteractor;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        UserInteractor$userRecords$1 userInteractor$userRecords$1 = new UserInteractor$userRecords$1(this.this$0, (Continuation) obj5);
        userInteractor$userRecords$1.L$0 = (List) obj;
        userInteractor$userRecords$1.L$1 = (UserInfo) obj2;
        userInteractor$userRecords$1.L$2 = (List) obj3;
        userInteractor$userRecords$1.L$3 = (UserSwitcherSettingsModel) obj4;
        return userInteractor$userRecords$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00c8  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x00f6 -> B:6:0x00f7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x00a3 -> B:27:0x00a4). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        UserInteractor userInteractor;
        UserInfo userInfo;
        List list;
        Collection arrayList;
        Iterator it;
        UserSwitcherSettingsModel userSwitcherSettingsModel;
        UserInteractor userInteractor2;
        UserInfo userInfo2;
        Collection collection;
        Collection collection2;
        Iterator it2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            List list2 = (List) this.L$0;
            UserInfo userInfo3 = (UserInfo) this.L$1;
            List list3 = (List) this.L$2;
            UserSwitcherSettingsModel userSwitcherSettingsModel2 = (UserSwitcherSettingsModel) this.L$3;
            userInteractor = this.this$0;
            userInfo = userInfo3;
            list = list3;
            arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            it = list2.iterator();
            userSwitcherSettingsModel = userSwitcherSettingsModel2;
            if (it.hasNext()) {
            }
        } else if (i == 1) {
            arrayList = (Collection) this.L$6;
            it = (Iterator) this.L$5;
            Collection collection3 = (Collection) this.L$4;
            userInteractor = (UserInteractor) this.L$3;
            userSwitcherSettingsModel = (UserSwitcherSettingsModel) this.L$2;
            list = (List) this.L$1;
            userInfo = (UserInfo) this.L$0;
            ResultKt.throwOnFailure(obj);
            arrayList.add((UserRecord) obj);
            arrayList = collection3;
            if (it.hasNext()) {
                UserInfo userInfo4 = (UserInfo) it.next();
                int i2 = userInfo.id;
                this.L$0 = userInfo;
                this.L$1 = list;
                this.L$2 = userSwitcherSettingsModel;
                this.L$3 = userInteractor;
                this.L$4 = arrayList;
                this.L$5 = it;
                this.L$6 = arrayList;
                this.label = 1;
                obj = UserInteractor.access$toRecord(userInteractor, userInfo4, i2, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                collection3 = arrayList;
                arrayList.add((UserRecord) obj);
                arrayList = collection3;
                if (it.hasNext()) {
                    UserInteractor userInteractor3 = this.this$0;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                    Iterator it3 = list.iterator();
                    userInteractor2 = userInteractor3;
                    userInfo2 = userInfo;
                    collection = (List) arrayList;
                    collection2 = arrayList2;
                    it2 = it3;
                    if (it2.hasNext()) {
                    }
                }
            }
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            collection2 = (Collection) this.L$6;
            collection = (Collection) this.L$5;
            it2 = (Iterator) this.L$4;
            Collection collection4 = (Collection) this.L$3;
            userInteractor2 = (UserInteractor) this.L$2;
            userSwitcherSettingsModel = (UserSwitcherSettingsModel) this.L$1;
            userInfo2 = (UserInfo) this.L$0;
            ResultKt.throwOnFailure(obj);
            collection2.add((UserRecord) obj);
            collection2 = collection4;
            if (it2.hasNext()) {
                UserActionModel userActionModel = (UserActionModel) it2.next();
                int i3 = userInfo2.id;
                boolean z = (userActionModel == UserActionModel.ENTER_GUEST_MODE || userActionModel == UserActionModel.NAVIGATE_TO_USER_MANAGEMENT || userSwitcherSettingsModel.isAddUsersFromLockscreen) ? false : true;
                this.L$0 = userInfo2;
                this.L$1 = userSwitcherSettingsModel;
                this.L$2 = userInteractor2;
                this.L$3 = collection2;
                this.L$4 = it2;
                this.L$5 = collection;
                this.L$6 = collection2;
                this.label = 2;
                obj = UserInteractor.access$toRecord(userInteractor2, userActionModel, i3, z, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                collection4 = collection2;
                collection2.add((UserRecord) obj);
                collection2 = collection4;
                if (it2.hasNext()) {
                    return new ArrayList(CollectionsKt___CollectionsKt.plus(collection2, collection));
                }
            }
        }
    }
}
