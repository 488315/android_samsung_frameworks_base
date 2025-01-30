package com.android.systemui.biometrics.p003ui.binder;

import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.p003ui.CredentialPasswordView;
import com.android.systemui.biometrics.p003ui.CredentialPatternView;
import com.android.systemui.biometrics.p003ui.CredentialView;
import com.android.systemui.biometrics.p003ui.viewmodel.CredentialViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CredentialViewBinder {
    static {
        new CredentialViewBinder();
    }

    private CredentialViewBinder() {
    }

    public static void bind$default(ViewGroup viewGroup, final CredentialView.Host host, CredentialViewModel credentialViewModel, AuthPanelController authPanelController, boolean z) {
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new CredentialViewBinder$bind$1(z, authPanelController, credentialViewModel, (TextView) viewGroup.requireViewById(R.id.title), viewGroup, (TextView) viewGroup.requireViewById(R.id.subtitle), (TextView) viewGroup.requireViewById(R.id.description), (ImageView) viewGroup.findViewById(R.id.icon), new Ref$ObjectRef(), 3000L, (TextView) viewGroup.requireViewById(R.id.error), host, null));
        if (!(viewGroup instanceof CredentialPasswordView)) {
            if (!(viewGroup instanceof CredentialPatternView)) {
                throw new IllegalStateException("unexpected view type: ".concat(viewGroup.getClass().getName()));
            }
            CredentialPatternView credentialPatternView = (CredentialPatternView) viewGroup;
            CredentialPatternViewBinder.INSTANCE.getClass();
            RepeatWhenAttachedKt.repeatWhenAttached(credentialPatternView, EmptyCoroutineContext.INSTANCE, new CredentialPatternViewBinder$bind$1(credentialViewModel, credentialPatternView.requireViewById(R.id.lockPattern), host, null));
            return;
        }
        CredentialPasswordView credentialPasswordView = (CredentialPasswordView) viewGroup;
        CredentialPasswordViewBinder.INSTANCE.getClass();
        Object systemService = credentialPasswordView.getContext().getSystemService((Class<Object>) InputMethodManager.class);
        Intrinsics.checkNotNull(systemService);
        RepeatWhenAttachedKt.repeatWhenAttached(credentialPasswordView, EmptyCoroutineContext.INSTANCE, new CredentialPasswordViewBinder$bind$1(true, credentialPasswordView.requireViewById(R.id.lockPassword), credentialPasswordView, credentialViewModel, new OnBackInvokedCallback() { // from class: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$onBackInvokedCallback$1
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                AuthContainerView authContainerView = (AuthContainerView) CredentialView.Host.this;
                authContainerView.sendEarlyUserCanceled();
                authContainerView.animateAway(1, true);
            }
        }, (InputMethodManager) systemService, host, null));
    }
}
