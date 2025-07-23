package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import android.content.Context;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.state.ToggleableState;
import com.android.systemui.qs.panels.ui.viewmodel.AccessibilityUiState;
import com.android.systemui.qs.panels.ui.viewmodel.IconProvider;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileKt$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TileKt$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ToggleableState toggleableState;
        switch (this.$r8$classId) {
            case 0:
                SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                AccessibilityUiState accessibilityUiState = (AccessibilityUiState) this.f$0;
                SemanticsPropertiesKt.m717setRolekuIjeqM(semanticsPropertyReceiver, accessibilityUiState.accessibilityRole);
                Role.Companion.getClass();
                if (accessibilityUiState.accessibilityRole == Role.Switch && (toggleableState = accessibilityUiState.toggleableState) != null) {
                    SemanticsPropertiesKt.setToggleableState(semanticsPropertyReceiver, toggleableState);
                }
                SemanticsPropertiesKt.setStateDescription(semanticsPropertyReceiver, accessibilityUiState.stateDescription);
                return Unit.INSTANCE;
            default:
                return TileKt.getTileIcon((Context) obj, (IconProvider) this.f$0);
        }
    }
}
