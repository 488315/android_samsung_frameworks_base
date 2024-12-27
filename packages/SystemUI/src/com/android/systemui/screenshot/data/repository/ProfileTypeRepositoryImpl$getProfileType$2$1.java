package com.android.systemui.screenshot.data.repository;

import com.android.systemui.screenshot.data.model.ProfileType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ProfileTypeRepositoryImpl$getProfileType$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ ProfileTypeRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProfileTypeRepositoryImpl$getProfileType$2$1(ProfileTypeRepositoryImpl profileTypeRepositoryImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = profileTypeRepositoryImpl;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ProfileTypeRepositoryImpl$getProfileType$2$1(this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ProfileTypeRepositoryImpl$getProfileType$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = this.this$0.userManager.getUserInfo(this.$userId).userType;
        if (str != null) {
            switch (str.hashCode()) {
                case -1384622829:
                    if (str.equals("android.os.usertype.profile.COMMUNAL")) {
                        return ProfileType.COMMUNAL;
                    }
                    break;
                case -1309576832:
                    if (str.equals("android.os.usertype.profile.PRIVATE")) {
                        return ProfileType.PRIVATE;
                    }
                    break;
                case -159818852:
                    if (str.equals("android.os.usertype.profile.MANAGED")) {
                        return ProfileType.WORK;
                    }
                    break;
                case 1966344346:
                    if (str.equals("android.os.usertype.profile.CLONE")) {
                        return ProfileType.CLONE;
                    }
                    break;
            }
        }
        return ProfileType.NONE;
    }
}
