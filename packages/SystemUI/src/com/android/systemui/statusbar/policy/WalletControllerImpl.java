package com.android.systemui.statusbar.policy;

import android.service.quickaccesswallet.QuickAccessWalletClient;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class WalletControllerImpl implements WalletController {
    public final QuickAccessWalletClient quickAccessWalletClient;

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
