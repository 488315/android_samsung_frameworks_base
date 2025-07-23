package com.android.systemui.qs.tiles.impl.night.domain.interactor;

import com.android.systemui.accessibility.data.repository.NightDisplayRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NightDisplayTileUserActionInteractor implements QSTileUserActionInteractor {
    public static final TileSpec spec;
    public final NightDisplayRepository nightDisplayRepository;
    public final QSTileLogger qsLogger;
    public final QSTileIntentUserInputHandler qsTileIntentUserActionHandler;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0085, code lost:
    
        if (r6.nightDisplayRepository.setNightDisplayActivated(r7, r0, r8) != r1) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0087, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0061, code lost:
    
        if (r6.nightDisplayRepository.setNightDisplayAutoMode(r8, r0) == r1) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileUserActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object handleInput(com.android.systemui.qs.tiles.base.domain.model.QSTileInput r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor$handleInput$1
            if (r0 == 0) goto L13
            r0 = r8
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
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L40
            if (r2 == r4) goto L33
            if (r2 != r3) goto L2b
            kotlin.ResultKt.throwOnFailure(r8)
            goto La1
        L2b:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L33:
            java.lang.Object r6 = r0.L$1
            r7 = r6
            com.android.systemui.qs.tiles.base.domain.model.QSTileInput r7 = (com.android.systemui.qs.tiles.base.domain.model.QSTileInput) r7
            java.lang.Object r6 = r0.L$0
            com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor r6 = (com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L64
        L40:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction r8 = r7.action
            boolean r2 = r8 instanceof com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.Click
            if (r2 == 0) goto L88
            java.lang.Object r8 = r7.data
            com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel r8 = (com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel) r8
            boolean r8 = r8.isEnrolledInForcedNightDisplayAutoMode()
            if (r8 == 0) goto L6d
            android.os.UserHandle r8 = r7.user
            r0.L$0 = r6
            r0.L$1 = r7
            r0.label = r4
            com.android.systemui.accessibility.data.repository.NightDisplayRepository r2 = r6.nightDisplayRepository
            java.lang.Object r8 = r2.setNightDisplayAutoMode(r8, r0)
            if (r8 != r1) goto L64
            goto L87
        L64:
            com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger r8 = r6.qsLogger
            com.android.systemui.qs.pipeline.shared.TileSpec r2 = com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor.spec
            java.lang.String r5 = "Enrolled in forced night display auto mode"
            r8.logInfo(r5, r2)
        L6d:
            java.lang.Object r8 = r7.data
            com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel r8 = (com.android.systemui.qs.tiles.impl.night.domain.model.NightDisplayTileModel) r8
            boolean r8 = r8.isActivated()
            r8 = r8 ^ r4
            android.os.UserHandle r7 = r7.user
            r2 = 0
            r0.L$0 = r2
            r0.L$1 = r2
            r0.label = r3
            com.android.systemui.accessibility.data.repository.NightDisplayRepository r6 = r6.nightDisplayRepository
            java.lang.Object r6 = r6.setNightDisplayActivated(r7, r0, r8)
            if (r6 != r1) goto La1
        L87:
            return r1
        L88:
            boolean r7 = r8 instanceof com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.LongClick
            if (r7 == 0) goto L9d
            com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction$LongClick r8 = (com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.LongClick) r8
            com.android.systemui.animation.Expandable r7 = r8.expandable
            android.content.Intent r8 = new android.content.Intent
            java.lang.String r0 = "android.settings.NIGHT_DISPLAY_SETTINGS"
            r8.<init>(r0)
            com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler r6 = r6.qsTileIntentUserActionHandler
            com.android.systemui.qs.tiles.base.domain.actions.QSTileIntentUserInputHandler.handle$default(r6, r7, r8)
            goto La1
        L9d:
            boolean r6 = r8 instanceof com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction.ToggleClick
            if (r6 == 0) goto La4
        La1:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        La4:
            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.night.domain.interactor.NightDisplayTileUserActionInteractor.handleInput(com.android.systemui.qs.tiles.base.domain.model.QSTileInput, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
