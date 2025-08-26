package com.android.systemui.qs.panels.ui.compose;

import android.view.DragEvent;
import androidx.compose.foundation.draganddrop.AndroidDragAndDropSource_androidKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draganddrop.DragAndDropEvent;
import androidx.compose.ui.geometry.Offset;
import com.android.systemui.qs.panels.shared.model.SizedTileImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public abstract class DragAndDropStateKt {
    public static final long access$toOffset(DragAndDropEvent dragAndDropEvent) {
        DragEvent dragEvent = dragAndDropEvent.dragEvent;
        float x = dragEvent.getX();
        float y = dragEvent.getY();
        long jFloatToRawIntBits = (Float.floatToRawIntBits(x) << 32) | (Float.floatToRawIntBits(y) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Modifier dragAndDropTileSource(Modifier modifier, SizedTileImpl sizedTileImpl, DragAndDropState dragAndDropState, DragType dragType, Function0 function0, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(428400562);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.dragAndDropTileSource (DragAndDropState.kt:185)");
        }
        MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(dragAndDropState, composerImpl);
        composerImpl.startReplaceGroup(955041229);
        boolean z = true;
        boolean zChanged = composerImpl.changed(mutableStateRememberUpdatedState) | ((((i & 112) ^ 48) > 32 && composerImpl.changedInstance(sizedTileImpl)) || (i & 48) == 32) | ((((i & 7168) ^ 3072) > 2048 && composerImpl.changed(dragType)) || (i & 3072) == 2048);
        if ((((57344 & i) ^ 24576) <= 16384 || !composerImpl.changed(function0)) && (i & 24576) != 16384) {
            z = false;
        }
        boolean z2 = zChanged | z;
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!z2) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                DragAndDropStateKt$dragAndDropTileSource$1$1 dragAndDropStateKt$dragAndDropTileSource$1$1 = new DragAndDropStateKt$dragAndDropTileSource$1$1(sizedTileImpl, dragType, function0, mutableStateRememberUpdatedState, null);
                composerImpl.updateRememberedValue(dragAndDropStateKt$dragAndDropTileSource$1$1);
                objRememberedValue = dragAndDropStateKt$dragAndDropTileSource$1$1;
            }
        }
        composerImpl.end(false);
        Modifier modifierDragAndDropSource = AndroidDragAndDropSource_androidKt.dragAndDropSource(modifier, (Function2) objRememberedValue);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return modifierDragAndDropSource;
    }
}
