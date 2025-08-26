package com.android.systemui.user.domain.interactor;

import android.R;
import android.content.pm.UserInfo;
import android.os.UserManager;
import com.android.systemui.QpRune;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.shared.model.UserActionModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* loaded from: classes3.dex */
final class UserSwitcherInteractor$actions$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ UserSwitcherInteractor this$0;

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
        boolean zBooleanValue = ((Boolean) obj4).booleanValue();
        UserSwitcherInteractor$actions$1 userSwitcherInteractor$actions$1 = new UserSwitcherInteractor$actions$1(this.this$0, (Continuation) obj5);
        userSwitcherInteractor$actions$1.L$0 = (List) obj2;
        userSwitcherInteractor$actions$1.L$1 = (UserSwitcherSettingsModel) obj3;
        userSwitcherInteractor$actions$1.Z$0 = zBooleanValue;
        return userSwitcherInteractor$actions$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0119 A[SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        UserSwitcherSettingsModel userSwitcherSettingsModel = (UserSwitcherSettingsModel) this.L$1;
        boolean z2 = this.Z$0;
        UserSwitcherInteractor userSwitcherInteractor = this.this$0;
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        boolean z3 = !z2 || userSwitcherSettingsModel.isAddUsersFromLockscreen;
        if (z3) {
            List listAsList = ((FeatureFlagsClassicRelease) userSwitcherInteractor.featureFlags).isEnabled(Flags.FULL_SCREEN_USER_SWITCHER) ? Arrays.asList(UserActionModel.ADD_USER, UserActionModel.ADD_SUPERVISED_USER, UserActionModel.ENTER_GUEST_MODE) : Arrays.asList(UserActionModel.ENTER_GUEST_MODE, UserActionModel.ADD_USER, UserActionModel.ADD_SUPERVISED_USER);
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listAsList, 10));
            Iterator it = listAsList.iterator();
            while (it.hasNext()) {
                int i = WhenMappings.$EnumSwitchMapping$0[((UserActionModel) it.next()).ordinal()];
                UserRepository userRepository = userSwitcherInteractor.repository;
                if (i == 1) {
                    List list2 = list;
                    if ((list2 instanceof Collection) && list2.isEmpty()) {
                        z = false;
                        if (!z) {
                        }
                    } else {
                        Iterator it2 = list2.iterator();
                        while (it2.hasNext()) {
                            if (((UserInfo) it2.next()).isGuest()) {
                                z = true;
                                break;
                            }
                        }
                        z = false;
                        if (!z) {
                            if (!userSwitcherInteractor.guestUserInteractor.isGuestUserAutoCreated) {
                                UserActionsUtil userActionsUtil = UserActionsUtil.INSTANCE;
                                UserManager userManager = userSwitcherInteractor.manager;
                                boolean z4 = userSwitcherSettingsModel.isUserSwitcherEnabled;
                                userActionsUtil.getClass();
                                boolean z5 = UserActionsUtil.canAddMoreUsers(userManager, userRepository, z4, z3, "android.os.usertype.full.GUEST") && !(QpRune.QUICK_MUM_TWO_PHONE && UserManager.supportsMultipleUsers());
                                if (z5) {
                                    listBuilderCreateListBuilder.add(UserActionModel.ENTER_GUEST_MODE);
                                }
                            }
                        }
                    }
                } else if (i == 2) {
                    UserActionsUtil userActionsUtil2 = UserActionsUtil.INSTANCE;
                    UserManager userManager2 = userSwitcherInteractor.manager;
                    boolean z6 = userSwitcherSettingsModel.isUserSwitcherEnabled;
                    userActionsUtil2.getClass();
                    if (UserActionsUtil.canAddMoreUsers(userManager2, userRepository, z6, z3, "android.os.usertype.full.SECONDARY")) {
                        listBuilderCreateListBuilder.add(UserActionModel.ADD_USER);
                    }
                } else if (i == 3) {
                    UserActionsUtil userActionsUtil3 = UserActionsUtil.INSTANCE;
                    UserManager userManager3 = userSwitcherInteractor.manager;
                    boolean z7 = userSwitcherSettingsModel.isUserSwitcherEnabled;
                    String string = userSwitcherInteractor.applicationContext.getString(R.string.face_recalibrate_notification_content);
                    userActionsUtil3.getClass();
                    if ((string == null || string.length() == 0) ? false : UserActionsUtil.canAddMoreUsers(userManager3, userRepository, z7, z3, "android.os.usertype.full.SECONDARY")) {
                        listBuilderCreateListBuilder.add(UserActionModel.ADD_SUPERVISED_USER);
                    }
                }
                arrayList.add(Unit.INSTANCE);
            }
        }
        UserActionsUtil userActionsUtil4 = UserActionsUtil.INSTANCE;
        UserRepository userRepository2 = userSwitcherInteractor.repository;
        boolean z8 = userSwitcherSettingsModel.isUserSwitcherEnabled;
        userActionsUtil4.getClass();
        if (z8 && ((UserRepositoryImpl) userRepository2).getSelectedUserInfo().isAdmin()) {
            listBuilderCreateListBuilder.add(UserActionModel.NAVIGATE_TO_USER_MANAGEMENT);
        }
        return listBuilderCreateListBuilder.build();
    }
}
