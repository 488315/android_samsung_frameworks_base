package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.ui.Modifier;
import com.android.compose.animation.scene.ContentScope;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public interface GridLayout {
    void EditTileGrid(List list, Modifier.Companion companion, Function2 function2, Function1 function1, Function1 function12, Function0 function0, Composer composer);

    void TileGrid(ContentScope contentScope, List list, Function0 function0, Composer composer, int i);
}
