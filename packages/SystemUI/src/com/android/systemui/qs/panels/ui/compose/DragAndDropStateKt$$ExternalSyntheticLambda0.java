package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.ui.draganddrop.DragAndDropEvent;
import androidx.compose.ui.draganddrop.DragAndDrop_androidKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class DragAndDropStateKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        DragAndDropEvent dragAndDropEvent = (DragAndDropEvent) obj;
        switch (this.$r8$classId) {
        }
        return Boolean.valueOf(DragAndDrop_androidKt.mimeTypes(dragAndDropEvent).contains("qstile/tilespec"));
    }
}
