package com.android.systemui.biometrics.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.CredentialPasswordView;
import com.android.systemui.biometrics.ui.CredentialPatternView;
import com.android.systemui.biometrics.ui.CredentialView;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CredentialViewBinder {
    static {
        new CredentialViewBinder();
    }

    private CredentialViewBinder() {
    }

    public static void bind$default(ViewGroup viewGroup, CredentialView.Host host, CredentialViewModel credentialViewModel, AuthPanelController authPanelController, boolean z, Spaghetti.Callback callback) {
        final CredentialView.Host host2;
        TextView textView = (TextView) viewGroup.requireViewById(R.id.title);
        TextView textView2 = (TextView) viewGroup.requireViewById(R.id.subtitle);
        TextView textView3 = (TextView) viewGroup.requireViewById(R.id.description);
        LinearLayout linearLayout = (LinearLayout) viewGroup.requireViewById(R.id.customized_view_container);
        ImageView imageView = (ImageView) viewGroup.findViewById(R.id.icon);
        TextView textView4 = (TextView) viewGroup.requireViewById(R.id.error);
        Button button = (Button) viewGroup.findViewById(R.id.cancel_button);
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new CredentialViewBinder$bind$1(z, authPanelController, credentialViewModel, textView, viewGroup, textView2, textView3, linearLayout, callback, imageView, (Button) viewGroup.requireViewById(R.id.emergencyCallButton), new Ref$ObjectRef(), 3000L, textView4, button, host, null));
        if (button != null) {
            host2 = host;
            button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.ui.binder.CredentialViewBinder$bind$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AuthContainerView authContainerView = (AuthContainerView) CredentialView.Host.this;
                    authContainerView.sendEarlyUserCanceled();
                    authContainerView.animateAway(1, true);
                }
            });
        } else {
            host2 = host;
        }
        if (!(viewGroup instanceof CredentialPasswordView)) {
            if (!(viewGroup instanceof CredentialPatternView)) {
                throw new IllegalStateException("unexpected view type: ".concat(viewGroup.getClass().getName()));
            }
            CredentialPatternView credentialPatternView = (CredentialPatternView) viewGroup;
            CredentialPatternViewBinder.INSTANCE.getClass();
            RepeatWhenAttachedKt.repeatWhenAttached(credentialPatternView, EmptyCoroutineContext.INSTANCE, new CredentialPatternViewBinder$bind$1(credentialViewModel, credentialPatternView.requireViewById(R.id.lockPattern), host2, null));
            return;
        }
        CredentialPasswordView credentialPasswordView = (CredentialPasswordView) viewGroup;
        CredentialPasswordViewBinder.INSTANCE.getClass();
        Object systemService = credentialPasswordView.getContext().getSystemService((Class<Object>) InputMethodManager.class);
        Intrinsics.checkNotNull(systemService);
        RepeatWhenAttachedKt.repeatWhenAttached(credentialPasswordView, EmptyCoroutineContext.INSTANCE, new CredentialPasswordViewBinder$bind$1(credentialViewModel, credentialPasswordView.requireViewById(R.id.lockPassword), true, new OnBackInvokedCallback() { // from class: com.android.systemui.biometrics.ui.binder.CredentialPasswordViewBinder$bind$onBackInvokedCallback$1
            @Override // android.window.OnBackInvokedCallback
            public final void onBackInvoked() {
                AuthContainerView authContainerView = (AuthContainerView) CredentialView.Host.this;
                authContainerView.sendEarlyUserCanceled();
                authContainerView.animateAway(1, true);
            }
        }, credentialPasswordView, (InputMethodManager) systemService, host, null));
    }
}
