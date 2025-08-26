package com.android.systemui.navigationbar.views.buttons;

import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class NavBarButtonClickLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NavBarButtonClickLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return "Ime Switcher Triggered";
            case 1:
                return "Home Button Triggered";
            case 2:
                return "Back Button Triggered";
            case 3:
                return "Accessibility Button Triggered";
            default:
                return "Recents Button Triggered";
        }
    }
}
