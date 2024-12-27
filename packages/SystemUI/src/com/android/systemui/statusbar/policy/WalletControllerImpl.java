package com.android.systemui.statusbar.policy;

import android.service.quickaccesswallet.QuickAccessWalletClient;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class WalletControllerImpl implements WalletController {
    public final QuickAccessWalletClient quickAccessWalletClient;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public WalletControllerImpl(QuickAccessWalletClient quickAccessWalletClient) {
    }
}
