package com.android.systemui.qs.tiles.impl.night.domain.interactor;

import com.android.systemui.accessibility.data.repository.NightDisplayRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.logging.QSTileLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NightDisplayTileUserActionInteractor implements QSTileUserActionInteractor {
    public static final TileSpec spec;
    public final NightDisplayRepository nightDisplayRepository;
    public final QSTileLogger qsLogger;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        TileSpec.Companion.getClass();
        spec = TileSpec.Companion.create("night");
    }

    public NightDisplayTileUserActionInteractor(NightDisplayRepository nightDisplayRepository, QSTileIntentUserInputHandler qSTileIntentUserInputHandler, QSTileLogger qSTileLogger) {
        this.nightDisplayRepository = nightDisplayRepository;
        this.qsTileIntentUserActionHandler = qSTileIntentUserInputHandler;
        this.qsLogger = qSTileLogger;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0082 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileUserActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object handleInput(com.android.systemui.qs.tiles.base.interactor.QSTileInput r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor$handleInput$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor$handleInput$1 r0 = (com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor$handleInput$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor$handleInput$1 r0 = new com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor$handleInput$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3f
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L97
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            java.lang.Object r5 = r0.L$1
            r6 = r5
            com.android.systemui.qs.tiles.base.interactor.QSTileInput r6 = (com.android.systemui.qs.tiles.base.interactor.QSTileInput) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor r5 = (com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L63
        L3f:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.qs.tiles.viewmodel.QSTileUserAction r7 = r6.action
            boolean r2 = r7 instanceof com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.Click
            if (r2 == 0) goto L83
            java.lang.Object r7 = r6.data
            com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel r7 = (com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel) r7
            boolean r7 = r7.isEnrolledInForcedNightDisplayAutoMode()
            if (r7 == 0) goto L68
            android.os.UserHandle r7 = r6.user
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r4
            com.android.systemui.accessibility.data.repository.NightDisplayRepository r2 = r5.nightDisplayRepository
            java.lang.Object r7 = r2.setNightDisplayAutoMode(r7, r0)
            if (r7 != r1) goto L63
            return r1
        L63:
            com.android.systemui.qs.tiles.base.logging.QSTileLogger r7 = r5.qsLogger
            r7.logInfo()
        L68:
            java.lang.Object r7 = r6.data
            com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel r7 = (com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel) r7
            boolean r7 = r7.isActivated()
            r7 = r7 ^ r4
            android.os.UserHandle r6 = r6.user
            r2 = 0
            r0.L$0 = r2
            r0.L$1 = r2
            r0.label = r3
            com.android.systemui.accessibility.data.repository.NightDisplayRepository r5 = r5.nightDisplayRepository
            java.lang.Object r5 = r5.setNightDisplayActivated(r6, r0, r7)
            if (r5 != r1) goto L97
            return r1
        L83:
            boolean r6 = r7 instanceof com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.LongClick
            if (r6 == 0) goto L97
            com.android.systemui.qs.tiles.viewmodel.QSTileUserAction$LongClick r7 = (com.android.systemui.qs.tiles.viewmodel.QSTileUserAction.LongClick) r7
            com.android.systemui.animation.Expandable r6 = r7.expandable
            android.content.Intent r7 = new android.content.Intent
            java.lang.String r0 = "android.settings.NIGHT_DISPLAY_SETTINGS"
            r7.<init>(r0)
            com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler r5 = r5.qsTileIntentUserActionHandler
            com.android.systemui.qs.tiles.base.actions.QSTileIntentUserInputHandler.handle$default(r5, r6, r7)
        L97:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor.handleInput(com.android.systemui.qs.tiles.base.interactor.QSTileInput, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
