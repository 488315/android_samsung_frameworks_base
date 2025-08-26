package com.android.bouncer.ui.composable;

import androidx.compose.ui.focus.FocusState;
import androidx.compose.ui.focus.FocusStateImpl;
import com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class SecPasswordBouncerKt$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PasswordBouncerViewModel f$0;

    public /* synthetic */ SecPasswordBouncerKt$$ExternalSyntheticLambda3(PasswordBouncerViewModel passwordBouncerViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = passwordBouncerViewModel;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final PasswordBouncerViewModel passwordBouncerViewModel = this.f$0;
                break;
            case 1:
                PasswordBouncerViewModel passwordBouncerViewModel2 = this.f$0;
                if (((CharSequence) passwordBouncerViewModel2._password.getValue()).length() > 0) {
                    AuthMethodBouncerViewModel.tryAuthenticate$default(passwordBouncerViewModel2, null, false, 3);
                }
                break;
            default:
                boolean zIsFocused = ((FocusStateImpl) ((FocusState) obj)).isFocused();
                Boolean boolValueOf = Boolean.valueOf(zIsFocused);
                PasswordBouncerViewModel passwordBouncerViewModel3 = this.f$0;
                passwordBouncerViewModel3.isTextFieldFocused.updateState(null, boolValueOf);
                if (zIsFocused && passwordBouncerViewModel3.isExternalDesktopWindowing) {
                    passwordBouncerViewModel3.keyguardViewMediator.dismiss(null, null);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
