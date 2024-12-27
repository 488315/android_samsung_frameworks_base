package com.android.systemui.biometrics.ui;

import android.content.Context;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.CredentialView;
import com.android.systemui.biometrics.ui.binder.CredentialViewBinder;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CredentialPasswordView extends LinearLayout implements CredentialView, View.OnApplyWindowInsetsListener {
    public CredentialPasswordView(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.biometrics.ui.CredentialPasswordView$accessibilityManager$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
            }
        });
    }

    @Override // com.android.systemui.biometrics.ui.CredentialView
    public final void init(CredentialViewModel credentialViewModel, CredentialView.Host host, AuthPanelController authPanelController, boolean z, Spaghetti.Callback callback) {
        CredentialViewBinder.bind$default(this, host, credentialViewModel, authPanelController, z, callback);
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        TextView textView;
        Insets insets = windowInsets.getInsets(WindowInsets.Type.statusBars());
        Insets insets2 = windowInsets.getInsets(WindowInsets.Type.ime());
        Insets insets3 = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        if (insets2.bottom != 0 && (textView = (TextView) findViewById(R.id.title)) != null) {
            textView.setSingleLine(false);
            textView.setEllipsize(null);
            textView.setSelected(false);
        }
        int i = insets.top;
        int i2 = insets2.bottom;
        if (i2 == 0) {
            i2 = insets3.bottom;
        }
        setPadding(0, i, 0, i2);
        return WindowInsets.CONSUMED;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setOnApplyWindowInsetsListener(this);
    }
}
