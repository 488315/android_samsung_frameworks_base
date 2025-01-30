package com.android.systemui.user.domain.interactor;

import android.R;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.systemui.QpRune;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.shared.model.UserActionModel;
import com.android.systemui.util.DeviceState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$actions$1", m277f = "UserInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserInteractor$actions$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ UserInteractor this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public UserInteractor$actions$1(UserInteractor userInteractor, Continuation<? super UserInteractor$actions$1> continuation) {
        super(5, continuation);
        this.this$0 = userInteractor;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj4).booleanValue();
        UserInteractor$actions$1 userInteractor$actions$1 = new UserInteractor$actions$1(this.this$0, (Continuation) obj5);
        userInteractor$actions$1.L$0 = (List) obj2;
        userInteractor$actions$1.L$1 = (UserSwitcherSettingsModel) obj3;
        userInteractor$actions$1.Z$0 = booleanValue;
        return userInteractor$actions$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:112:0x0188, code lost:
    
        if (((r9.hasBaseUserRestriction("no_add_user", android.os.UserHandle.SYSTEM) ^ true) && r0.isAddUsersFromLockscreen) != false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ce, code lost:
    
        if (((r9.hasBaseUserRestriction("no_add_user", android.os.UserHandle.SYSTEM) ^ true) && r0.isAddUsersFromLockscreen) == false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0114, code lost:
    
        if (((r9.hasBaseUserRestriction("no_add_user", android.os.UserHandle.SYSTEM) ^ true) && r0.isAddUsersFromLockscreen) == false) goto L53;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01a5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01a5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01a5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01a0  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean canAddMoreUsers;
        boolean canAddMoreUsers2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        UserSwitcherSettingsModel userSwitcherSettingsModel = (UserSwitcherSettingsModel) this.L$1;
        boolean z4 = this.Z$0;
        UserInteractor userInteractor = this.this$0;
        ListBuilder listBuilder = new ListBuilder();
        boolean z5 = false;
        if (!z4 || userSwitcherSettingsModel.isAddUsersFromLockscreen) {
            List listOf = ((FeatureFlagsRelease) userInteractor.featureFlags).isEnabled(Flags.FULL_SCREEN_USER_SWITCHER) ? CollectionsKt__CollectionsKt.listOf(UserActionModel.ADD_USER, UserActionModel.ADD_SUPERVISED_USER, UserActionModel.ENTER_GUEST_MODE) : CollectionsKt__CollectionsKt.listOf(UserActionModel.ENTER_GUEST_MODE, UserActionModel.ADD_USER, UserActionModel.ADD_SUPERVISED_USER);
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
            Iterator it = listOf.iterator();
            while (it.hasNext()) {
                int i = WhenMappings.$EnumSwitchMapping$0[((UserActionModel) it.next()).ordinal()];
                UserRepository userRepository = userInteractor.repository;
                UserManager userManager = userInteractor.manager;
                if (i == 1) {
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator it2 = list.iterator();
                        while (it2.hasNext()) {
                            if (((UserInfo) it2.next()).isGuest()) {
                                z = true;
                                break;
                            }
                        }
                    }
                    z = false;
                    if (!z) {
                        if (!userInteractor.guestUserInteractor.isGuestUserAutoCreated) {
                            UserActionsUtil userActionsUtil = UserActionsUtil.INSTANCE;
                            if (userSwitcherSettingsModel.isUserSwitcherEnabled) {
                                userActionsUtil.getClass();
                                UserInfo selectedUserInfo = ((UserRepositoryImpl) userRepository).getSelectedUserInfo();
                                if (!((selectedUserInfo.isAdmin() || selectedUserInfo.id == 0) ? !userManager.hasBaseUserRestriction("no_add_user", UserHandle.SYSTEM) : false)) {
                                }
                                if (!QpRune.QUICK_MANAGE_TWO_PHONE || !DeviceState.supportsMultipleUsers()) {
                                    z3 = true;
                                    if (!z3) {
                                        z2 = false;
                                        if (!z2) {
                                            listBuilder.add(UserActionModel.ENTER_GUEST_MODE);
                                        }
                                    }
                                }
                            } else {
                                userActionsUtil.getClass();
                            }
                            z3 = false;
                            if (!z3) {
                            }
                        }
                        z2 = true;
                        if (!z2) {
                        }
                    }
                } else if (i == 2) {
                    UserActionsUtil userActionsUtil2 = UserActionsUtil.INSTANCE;
                    boolean z6 = userSwitcherSettingsModel.isUserSwitcherEnabled;
                    userActionsUtil2.getClass();
                    if (z6) {
                        UserInfo selectedUserInfo2 = ((UserRepositoryImpl) userRepository).getSelectedUserInfo();
                        if (!((selectedUserInfo2.isAdmin() || selectedUserInfo2.id == 0) ? !userManager.hasBaseUserRestriction("no_add_user", UserHandle.SYSTEM) : false)) {
                        }
                        canAddMoreUsers = userManager.canAddMoreUsers("android.os.usertype.full.SECONDARY");
                        if (!canAddMoreUsers) {
                            listBuilder.add(UserActionModel.ADD_USER);
                        }
                    }
                    canAddMoreUsers = false;
                    if (!canAddMoreUsers) {
                    }
                } else if (i == 3) {
                    UserActionsUtil userActionsUtil3 = UserActionsUtil.INSTANCE;
                    boolean z7 = userSwitcherSettingsModel.isUserSwitcherEnabled;
                    String string = userInteractor.applicationContext.getString(R.string.ext_media_status_bad_removal);
                    userActionsUtil3.getClass();
                    if (!(string == null || string.length() == 0) && z7) {
                        UserInfo selectedUserInfo3 = ((UserRepositoryImpl) userRepository).getSelectedUserInfo();
                        if (!((selectedUserInfo3.isAdmin() || selectedUserInfo3.id == 0) ? !userManager.hasBaseUserRestriction("no_add_user", UserHandle.SYSTEM) : false)) {
                        }
                        canAddMoreUsers2 = userManager.canAddMoreUsers("android.os.usertype.full.SECONDARY");
                        if (!canAddMoreUsers2) {
                            listBuilder.add(UserActionModel.ADD_SUPERVISED_USER);
                        }
                    }
                    canAddMoreUsers2 = false;
                    if (!canAddMoreUsers2) {
                    }
                }
                arrayList.add(Unit.INSTANCE);
            }
        }
        UserActionsUtil userActionsUtil4 = UserActionsUtil.INSTANCE;
        UserRepository userRepository2 = userInteractor.repository;
        boolean z8 = userSwitcherSettingsModel.isUserSwitcherEnabled;
        userActionsUtil4.getClass();
        if (z8 && (((UserRepositoryImpl) userRepository2).getSelectedUserInfo().isAdmin() || userSwitcherSettingsModel.isAddUsersFromLockscreen)) {
            z5 = true;
        }
        if (z5) {
            listBuilder.add(UserActionModel.NAVIGATE_TO_USER_MANAGEMENT);
        }
        listBuilder.build();
        return listBuilder;
    }
}
