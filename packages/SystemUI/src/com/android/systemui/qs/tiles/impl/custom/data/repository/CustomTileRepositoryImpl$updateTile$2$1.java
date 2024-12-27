package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.os.UserHandle;
import android.service.quicksettings.Tile;
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.CustomTileStatePersisterImpl;
import com.android.systemui.qs.external.CustomTileStatePersisterKt;
import com.android.systemui.qs.external.TileServiceKey;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class CustomTileRepositoryImpl$updateTile$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Tile $tileToUpdate;
    final /* synthetic */ UserHandle $user;
    int label;
    final /* synthetic */ CustomTileRepositoryImpl this$0;

    public CustomTileRepositoryImpl$updateTile$2$1(CustomTileRepositoryImpl customTileRepositoryImpl, UserHandle userHandle, Tile tile, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileRepositoryImpl;
        this.$user = userHandle;
        this.$tileToUpdate = tile;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomTileRepositoryImpl$updateTile$2$1(this.this$0, this.$user, this.$tileToUpdate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileRepositoryImpl$updateTile$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CustomTileRepositoryImpl customTileRepositoryImpl = this.this$0;
        CustomTileStatePersister customTileStatePersister = customTileRepositoryImpl.customTileStatePersister;
        TileServiceKey tileServiceKey = new TileServiceKey(customTileRepositoryImpl.tileSpec.componentName, this.$user.getIdentifier());
        Tile tile = this.$tileToUpdate;
        CustomTileStatePersisterImpl customTileStatePersisterImpl = (CustomTileStatePersisterImpl) customTileStatePersister;
        customTileStatePersisterImpl.getClass();
        customTileStatePersisterImpl.sharedPreferences.edit().putString(tileServiceKey.string, CustomTileStatePersisterKt.writeToString(tile)).apply();
        return Unit.INSTANCE;
    }
}
