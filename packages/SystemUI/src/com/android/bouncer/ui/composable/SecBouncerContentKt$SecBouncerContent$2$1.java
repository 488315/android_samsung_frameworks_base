package com.android.bouncer.ui.composable;

import android.view.KeyEvent;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import com.android.systemui.flags.RefactorFlagUtils;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class SecBouncerContentKt$SecBouncerContent$2$1 extends FunctionReferenceImpl implements Function1 {
    public SecBouncerContentKt$SecBouncerContent$2$1(Object obj) {
        super(1, obj, BouncerOverlayContentViewModel.class, "onKeyEvent", "onKeyEvent-ZmokQxo(Landroid/view/KeyEvent;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj).nativeKeyEvent;
        BouncerOverlayContentViewModel bouncerOverlayContentViewModel = (BouncerOverlayContentViewModel) this.receiver;
        bouncerOverlayContentViewModel.keyguardMediaKeyInteractor.getClass();
        ComposeBouncerFlags composeBouncerFlags = ComposeBouncerFlags.INSTANCE;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ComposeBouncerFlags.INSTANCE.getClass();
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag || ComposeBouncerFlag to be enabled.");
        AuthMethodBouncerViewModel authMethodBouncerViewModel = (AuthMethodBouncerViewModel) bouncerOverlayContentViewModel.authMethodViewModel.$$delegate_0.getValue();
        return Boolean.valueOf(authMethodBouncerViewModel != null ? authMethodBouncerViewModel.mo1058onKeyEventuiMRsoQ(KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent), keyEvent.getKeyCode()) : false);
    }
}
