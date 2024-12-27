package com.android.keyguard.dagger;

import android.view.ViewGroup;
import com.android.keyguard.KeyguardSecSecurityContainerController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface KeyguardBouncerComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        KeyguardBouncerComponent create(ViewGroup viewGroup);
    }

    KeyguardSecSecurityContainerController getSecurityContainerController();
}
