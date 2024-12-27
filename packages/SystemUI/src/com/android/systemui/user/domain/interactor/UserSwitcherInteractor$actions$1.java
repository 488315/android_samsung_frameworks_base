package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.user.shared.model.UserActionModel;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class UserSwitcherInteractor$actions$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ UserSwitcherInteractor this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[UserActionModel.values().length];
            try {
                iArr[UserActionModel.ENTER_GUEST_MODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[UserActionModel.ADD_USER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[UserActionModel.ADD_SUPERVISED_USER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherInteractor$actions$1(UserSwitcherInteractor userSwitcherInteractor, Continuation continuation) {
        super(5, continuation);
        this.this$0 = userSwitcherInteractor;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj4).booleanValue();
        UserSwitcherInteractor$actions$1 userSwitcherInteractor$actions$1 = new UserSwitcherInteractor$actions$1(this.this$0, (Continuation) obj5);
        userSwitcherInteractor$actions$1.L$0 = (List) obj2;
        userSwitcherInteractor$actions$1.L$1 = (UserSwitcherSettingsModel) obj3;
        userSwitcherInteractor$actions$1.Z$0 = booleanValue;
        return userSwitcherInteractor$actions$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x011a A[SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$actions$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
