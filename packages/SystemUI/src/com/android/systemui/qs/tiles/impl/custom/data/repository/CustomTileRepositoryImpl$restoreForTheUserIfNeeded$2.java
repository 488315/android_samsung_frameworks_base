package com.android.systemui.qs.tiles.impl.custom.data.repository;

import android.os.UserHandle;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CustomTileRepositoryImpl$restoreForTheUserIfNeeded$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $user;
    int label;
    final /* synthetic */ CustomTileRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileRepositoryImpl$restoreForTheUserIfNeeded$2(CustomTileRepositoryImpl customTileRepositoryImpl, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileRepositoryImpl;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomTileRepositoryImpl$restoreForTheUserIfNeeded$2(this.this$0, this.$user, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileRepositoryImpl$restoreForTheUserIfNeeded$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5b
        Ld:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L15:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl r6 = r5.this$0
            com.android.systemui.qs.external.CustomTileStatePersister r1 = r6.customTileStatePersister
            android.os.UserHandle r3 = r5.$user
            com.android.systemui.qs.external.TileServiceKey r4 = new com.android.systemui.qs.external.TileServiceKey
            com.android.systemui.qs.pipeline.shared.TileSpec$CustomTileSpec r6 = r6.tileSpec
            android.content.ComponentName r6 = r6.componentName
            int r3 = r3.getIdentifier()
            r4.<init>(r6, r3)
            com.android.systemui.qs.external.CustomTileStatePersisterImpl r1 = (com.android.systemui.qs.external.CustomTileStatePersisterImpl) r1
            android.content.SharedPreferences r6 = r1.sharedPreferences
            java.lang.String r1 = r4.string
            r3 = 0
            java.lang.String r6 = r6.getString(r1, r3)
            if (r6 != 0) goto L3a
        L38:
            r6 = r3
            goto L4c
        L3a:
            android.service.quicksettings.Tile r6 = com.android.systemui.qs.external.CustomTileStatePersisterKt.readTileFromString(r6)     // Catch: org.json.JSONException -> L3f
            goto L4c
        L3f:
            r1 = move-exception
            java.lang.String r4 = "Bad saved state: "
            java.lang.String r6 = r4.concat(r6)
            java.lang.String r4 = "TileServicePersistence"
            android.util.Log.e(r4, r6, r1)
            goto L38
        L4c:
            if (r6 == 0) goto L5d
            com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl r1 = r5.this$0
            android.os.UserHandle r3 = r5.$user
            r5.label = r2
            java.lang.Object r5 = r1.updateWithTile(r3, r6, r2, r5)
            if (r5 != r0) goto L5b
            return r0
        L5b:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
        L5d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$restoreForTheUserIfNeeded$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
