package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.ui.semantics.LiveRegionMode;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutCustomizerKt$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ ShortcutCustomizerKt$$ExternalSyntheticLambda4(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
        switch (this.$r8$classId) {
            case 0:
                SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, this.f$0);
                LiveRegionMode.Companion.getClass();
                SemanticsPropertiesKt.m718setLiveRegionhR3wRGc(semanticsPropertyReceiver, 0);
                break;
            default:
                SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, this.f$0);
                break;
        }
        return Unit.INSTANCE;
    }
}
