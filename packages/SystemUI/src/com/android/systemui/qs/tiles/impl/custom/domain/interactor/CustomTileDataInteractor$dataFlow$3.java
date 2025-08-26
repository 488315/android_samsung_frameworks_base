package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.service.quicksettings.Tile;
import com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.domain.model.CustomTileDataModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* loaded from: classes2.dex */
final class CustomTileDataInteractor$dataFlow$3 extends SuspendLambda implements Function5 {
    final /* synthetic */ UserHandle $user;
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    Object L$3;
    boolean Z$0;
    int label;
    final /* synthetic */ CustomTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileDataInteractor$dataFlow$3(CustomTileDataInteractor customTileDataInteractor, UserHandle userHandle, Continuation continuation) {
        super(5, continuation);
        this.this$0 = customTileDataInteractor;
        this.$user = userHandle;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int iIntValue = ((Number) obj2).intValue();
        CustomTileDataInteractor$dataFlow$3 customTileDataInteractor$dataFlow$3 = new CustomTileDataInteractor$dataFlow$3(this.this$0, this.$user, (Continuation) obj5);
        customTileDataInteractor$dataFlow$3.I$0 = iIntValue;
        customTileDataInteractor$dataFlow$3.L$0 = (Tile) obj3;
        customTileDataInteractor$dataFlow$3.L$1 = (CustomTileDefaults.Result) obj4;
        return customTileDataInteractor$dataFlow$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        Tile tile;
        ComponentName componentName;
        Icon icon;
        CharSequence charSequence;
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            i = this.I$0;
            tile = (Tile) this.L$0;
            CustomTileDefaults.Result result = (CustomTileDefaults.Result) this.L$1;
            CustomTileDataInteractor customTileDataInteractor = this.this$0;
            componentName = customTileDataInteractor.tileSpec.componentName;
            boolean zHasPendingBind = customTileDataInteractor.serviceInteractor.getTileServiceManager().hasPendingBind();
            CharSequence charSequence2 = result.label;
            Icon icon2 = result.icon;
            CustomTileInteractor customTileInteractor = this.this$0.customTileInteractor;
            this.L$0 = tile;
            this.L$1 = componentName;
            this.L$2 = charSequence2;
            this.L$3 = icon2;
            this.I$0 = i;
            this.Z$0 = zHasPendingBind;
            this.label = 1;
            Object objIsTileToggleable = ((CustomTileRepositoryImpl) customTileInteractor.customTileRepository).isTileToggleable(this);
            if (objIsTileToggleable == coroutineSingletons) {
                return coroutineSingletons;
            }
            icon = icon2;
            obj = objIsTileToggleable;
            charSequence = charSequence2;
            z = zHasPendingBind;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            boolean z2 = this.Z$0;
            i = this.I$0;
            Icon icon3 = (Icon) this.L$3;
            CharSequence charSequence3 = (CharSequence) this.L$2;
            componentName = (ComponentName) this.L$1;
            tile = (Tile) this.L$0;
            ResultKt.throwOnFailure(obj);
            z = z2;
            icon = icon3;
            charSequence = charSequence3;
        }
        Tile tile2 = tile;
        int i3 = i;
        return new CustomTileDataModel(this.$user, componentName, tile2, ((Boolean) obj).booleanValue(), i3, z, charSequence, icon);
    }
}
