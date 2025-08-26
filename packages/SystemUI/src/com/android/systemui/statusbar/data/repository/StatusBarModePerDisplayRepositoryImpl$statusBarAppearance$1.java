package com.android.systemui.statusbar.data.repository;

import com.android.systemui.statusbar.data.model.StatusBarAppearance;
import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* loaded from: classes3.dex */
final class StatusBarModePerDisplayRepositoryImpl$statusBarAppearance$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ StatusBarModePerDisplayRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarModePerDisplayRepositoryImpl$statusBarAppearance$1(StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl, Continuation continuation) {
        super(6, continuation);
        this.this$0 = statusBarModePerDisplayRepositoryImpl;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj5).booleanValue();
        StatusBarModePerDisplayRepositoryImpl$statusBarAppearance$1 statusBarModePerDisplayRepositoryImpl$statusBarAppearance$1 = new StatusBarModePerDisplayRepositoryImpl$statusBarAppearance$1(this.this$0, (Continuation) obj6);
        statusBarModePerDisplayRepositoryImpl$statusBarAppearance$1.L$0 = (StatusBarModePerDisplayRepositoryImpl.ModifiedStatusBarAttributes) obj;
        statusBarModePerDisplayRepositoryImpl$statusBarAppearance$1.Z$0 = zBooleanValue;
        statusBarModePerDisplayRepositoryImpl$statusBarAppearance$1.Z$1 = zBooleanValue2;
        statusBarModePerDisplayRepositoryImpl$statusBarAppearance$1.L$1 = (OngoingCallModel) obj4;
        statusBarModePerDisplayRepositoryImpl$statusBarAppearance$1.Z$2 = zBooleanValue3;
        return statusBarModePerDisplayRepositoryImpl$statusBarAppearance$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        StatusBarMode statusBarMode;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        StatusBarModePerDisplayRepositoryImpl.ModifiedStatusBarAttributes modifiedStatusBarAttributes = (StatusBarModePerDisplayRepositoryImpl.ModifiedStatusBarAttributes) this.L$0;
        boolean z = this.Z$0;
        OngoingCallModel ongoingCallModel = (OngoingCallModel) this.L$1;
        if (modifiedStatusBarAttributes == null) {
            return null;
        }
        if (ongoingCallModel instanceof OngoingCallModel.InCall) {
            boolean z2 = ((OngoingCallModel.InCall) ongoingCallModel).isAppVisible;
        }
        StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl = this.this$0;
        int i = StatusBarModePerDisplayRepositoryImpl.$r8$clinit;
        statusBarModePerDisplayRepositoryImpl.getClass();
        if (z) {
            statusBarMode = StatusBarMode.SEMI_TRANSPARENT;
        } else {
            int i2 = modifiedStatusBarAttributes.appearance;
            statusBarMode = (i2 & 5) == 5 ? StatusBarMode.LIGHTS_OUT : (i2 & 4) != 0 ? StatusBarMode.LIGHTS_OUT_TRANSPARENT : (i2 & 1) != 0 ? StatusBarMode.OPAQUE : (i2 & 32) != 0 ? StatusBarMode.SEMI_TRANSPARENT : StatusBarMode.TRANSPARENT;
        }
        return new StatusBarAppearance(statusBarMode, modifiedStatusBarAttributes.statusBarBounds, modifiedStatusBarAttributes.appearanceRegions, modifiedStatusBarAttributes.navbarColorManagedByIme);
    }
}
