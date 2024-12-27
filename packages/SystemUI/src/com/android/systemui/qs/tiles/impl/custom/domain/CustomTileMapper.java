package com.android.systemui.qs.tiles.impl.custom.domain;

import android.app.IUriGrantsManager;
import android.content.Context;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CustomTileMapper implements QSTileDataToStateMapper {
    public final Context context;
    public final IUriGrantsManager uriGrantsManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class IconResult {
        public final boolean failedToLoad;
        public final Function0 iconProvider;

        public IconResult(Function0 function0, boolean z) {
            this.iconProvider = function0;
            this.failedToLoad = z;
        }
    }

    public CustomTileMapper(Context context, IUriGrantsManager iUriGrantsManager) {
        this.context = context;
        this.uriGrantsManager = iUriGrantsManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0032  */
    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataToStateMapper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.qs.tiles.viewmodel.QSTileState map(com.android.systemui.qs.tiles.viewmodel.QSTileConfig r7, java.lang.Object r8) {
        /*
            r6 = this;
            com.android.systemui.qs.tiles.impl.custom.domain.entity.CustomTileDataModel r8 = (com.android.systemui.qs.tiles.impl.custom.domain.entity.CustomTileDataModel) r8
            android.content.Context r7 = r6.context
            android.os.UserHandle r0 = new android.os.UserHandle
            android.os.UserHandle r1 = r8.user
            int r1 = r1.getIdentifier()
            r0.<init>(r1)
            r1 = 0
            android.content.Context r7 = r7.createContextAsUser(r0, r1)
            android.service.quicksettings.Tile r0 = r8.tile
            android.graphics.drawable.Icon r0 = r0.getIcon()
            int r2 = r8.callingAppUid
            android.content.ComponentName r3 = r8.componentName
            java.lang.String r3 = r3.getPackageName()
            android.graphics.drawable.Icon r4 = r8.defaultTileIcon
            r5 = 0
            if (r0 == 0) goto L2f
            android.app.IUriGrantsManager r6 = r6.uriGrantsManager     // Catch: java.lang.Exception -> L2e
            android.graphics.drawable.Drawable r6 = r0.loadDrawableCheckingUriGrant(r7, r6, r2, r3)     // Catch: java.lang.Exception -> L2e
            goto L30
        L2e:
            r1 = 1
        L2f:
            r6 = r5
        L30:
            if (r6 != 0) goto L39
            if (r4 == 0) goto L3a
            android.graphics.drawable.Drawable r5 = r4.loadDrawable(r7)
            goto L3a
        L39:
            r5 = r6
        L3a:
            com.android.systemui.qs.tiles.impl.custom.domain.CustomTileMapper$IconResult r6 = new com.android.systemui.qs.tiles.impl.custom.domain.CustomTileMapper$IconResult
            com.android.systemui.qs.tiles.impl.custom.domain.CustomTileMapper$getIconProvider$1 r7 = new com.android.systemui.qs.tiles.impl.custom.domain.CustomTileMapper$getIconProvider$1
            r7.<init>()
            r6.<init>(r7, r1)
            com.android.systemui.qs.tiles.viewmodel.QSTileState$Companion r7 = com.android.systemui.qs.tiles.viewmodel.QSTileState.Companion
            android.service.quicksettings.Tile r0 = r8.tile
            java.lang.CharSequence r0 = r0.getLabel()
            com.android.systemui.qs.tiles.impl.custom.domain.CustomTileMapper$map$1 r1 = new com.android.systemui.qs.tiles.impl.custom.domain.CustomTileMapper$map$1
            r1.<init>()
            r7.getClass()
            kotlin.jvm.functions.Function0 r6 = r6.iconProvider
            com.android.systemui.qs.tiles.viewmodel.QSTileState r6 = com.android.systemui.qs.tiles.viewmodel.QSTileState.Companion.build(r6, r0, r1)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.CustomTileMapper.map(com.android.systemui.qs.tiles.viewmodel.QSTileConfig, java.lang.Object):com.android.systemui.qs.tiles.viewmodel.QSTileState");
    }
}
