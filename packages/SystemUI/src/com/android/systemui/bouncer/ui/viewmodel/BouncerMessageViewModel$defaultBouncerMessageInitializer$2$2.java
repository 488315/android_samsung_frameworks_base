package com.android.systemui.bouncer.ui.viewmodel;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.shared.model.BouncerMessageStrings;
import com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel;
import com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes.dex */
final class BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2 extends SuspendLambda implements Function6 {
    final /* synthetic */ AuthenticationMethodModel $authMethod;
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2(BouncerMessageViewModel bouncerMessageViewModel, AuthenticationMethodModel authenticationMethodModel, Continuation continuation) {
        super(6, continuation);
        this.this$0 = bouncerMessageViewModel;
        this.$authMethod = authenticationMethodModel;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj4).booleanValue();
        int iIntValue = ((Number) obj5).intValue();
        BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2 bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2 = new BouncerMessageViewModel$defaultBouncerMessageInitializer$2$2(this.this$0, this.$authMethod, (Continuation) obj6);
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.L$0 = (DeviceEntryRestrictionReason) obj;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.L$1 = (MessageViewModel) obj2;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.Z$0 = zBooleanValue;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.Z$1 = zBooleanValue2;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.I$0 = iIntValue;
        return bouncerMessageViewModel$defaultBouncerMessageInitializer$2$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00fa  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        DeviceEntryRestrictionReason deviceEntryRestrictionReason;
        MessageViewModel messageViewModel;
        boolean z;
        boolean z2;
        boolean z3;
        MutableStateFlow mutableStateFlow;
        Pair pairAuthRequiredAfterUserLockdown;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            deviceEntryRestrictionReason = (DeviceEntryRestrictionReason) this.L$0;
            messageViewModel = (MessageViewModel) this.L$1;
            z = this.Z$0;
            z2 = this.Z$1;
            int i2 = this.I$0;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "authMethod.isSecure : ", "BouncerMessageViewModel");
            if (i2 > 0) {
                BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                StateFlowImpl stateFlowImpl = bouncerMessageViewModel.hintMessage;
                this.L$0 = deviceEntryRestrictionReason;
                this.L$1 = messageViewModel;
                this.L$2 = stateFlowImpl;
                this.Z$0 = z;
                this.Z$1 = z2;
                this.label = 1;
                Object hint = bouncerMessageViewModel.hintInteractor.getHint(this);
                if (hint == coroutineSingletons) {
                    return coroutineSingletons;
                }
                z3 = z2;
                obj = hint;
                mutableStateFlow = stateFlowImpl;
            }
            if (z2) {
                if (messageViewModel == null) {
                    BouncerMessageViewModel bouncerMessageViewModel2 = this.this$0;
                    BouncerMessageStrings.INSTANCE.getClass();
                    Pair pair = BouncerMessageStrings.EmptyMessage;
                    int i3 = BouncerMessageViewModel.$r8$clinit;
                    return bouncerMessageViewModel2.toMessage(pair);
                }
            } else if (messageViewModel == null) {
                BouncerMessageViewModel bouncerMessageViewModel3 = this.this$0;
                AuthenticationMethodModel authenticationMethodModel = this.$authMethod;
                int i4 = BouncerMessageViewModel.$r8$clinit;
                bouncerMessageViewModel3.getClass();
                switch (deviceEntryRestrictionReason == null ? -1 : BouncerMessageViewModel.WhenMappings.$EnumSwitchMapping$0[deviceEntryRestrictionReason.ordinal()]) {
                    case 1:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.authRequiredAfterUserLockdown(authenticationMethodModel);
                        break;
                    case 2:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.authRequiredAfterReboot(authenticationMethodModel);
                        break;
                    case 3:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.authRequiredAfterAdminLockdown(authenticationMethodModel);
                        break;
                    case 4:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.authRequiredForUnattendedUpdate(authenticationMethodModel);
                        break;
                    case 5:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.authRequiredForMainlineUpdate(authenticationMethodModel);
                        break;
                    case 6:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.authRequiredAfterPrimaryAuthTimeout(authenticationMethodModel);
                        break;
                    case 7:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.class3AuthLockedOut(authenticationMethodModel);
                        break;
                    case 8:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.faceLockedOut(authenticationMethodModel, z);
                        break;
                    case 9:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.nonStrongAuthTimeout(authenticationMethodModel, z);
                        break;
                    case 10:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.trustAgentDisabled(authenticationMethodModel, z);
                        break;
                    case 11:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.authRequiredAfterAdaptiveAuthRequest(authenticationMethodModel, z);
                        break;
                    default:
                        BouncerMessageStrings.INSTANCE.getClass();
                        pairAuthRequiredAfterUserLockdown = BouncerMessageStrings.defaultMessage(authenticationMethodModel, z);
                        break;
                }
                return bouncerMessageViewModel3.toMessage(pairAuthRequiredAfterUserLockdown);
            }
            return messageViewModel;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        z3 = this.Z$1;
        z = this.Z$0;
        mutableStateFlow = (MutableStateFlow) this.L$2;
        messageViewModel = (MessageViewModel) this.L$1;
        deviceEntryRestrictionReason = (DeviceEntryRestrictionReason) this.L$0;
        ResultKt.throwOnFailure(obj);
        mutableStateFlow.setValue(obj);
        z2 = z3;
        if (z2) {
        }
        return messageViewModel;
    }
}
