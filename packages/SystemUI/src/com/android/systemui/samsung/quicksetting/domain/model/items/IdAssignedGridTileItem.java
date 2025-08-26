package com.android.systemui.samsung.quicksetting.domain.model.items;

import java.util.UUID;

/* loaded from: classes2.dex */
public abstract class IdAssignedGridTileItem implements GridTileItem {
    public final String id = UUID.randomUUID().toString();
}
