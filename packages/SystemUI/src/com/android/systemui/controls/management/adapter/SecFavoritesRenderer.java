package com.android.systemui.controls.management.adapter;

import android.content.res.Resources;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SecFavoritesRenderer {
    public final Function1 favoriteFunction;
    public final Function1 getActiveFlag;
    public final Resources resources;
    public final Function2 setActiveFlag;
    public final Function2 setActivePanelFlag;

    public SecFavoritesRenderer(Resources resources, Function1 function1, Function1 function12, Function2 function2, Function2 function22) {
        this.resources = resources;
        this.favoriteFunction = function1;
        this.getActiveFlag = function12;
        this.setActiveFlag = function2;
        this.setActivePanelFlag = function22;
    }
}
