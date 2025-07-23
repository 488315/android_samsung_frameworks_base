package android.security;

import android.content.Context;
import java.security.KeyStore;

@Deprecated
/* loaded from: classes3.dex */
public final class KeyStoreParameter implements KeyStore.ProtectionParameter {
    public int getFlags() {
        return 0;
    }

    @Deprecated
    public boolean isEncryptionRequired() {
        return false;
    }

    private KeyStoreParameter(int i) {
    }

    @Deprecated
    public static final class Builder {
        public Builder setEncryptionRequired(boolean z) {
            return this;
        }

        public Builder(Context context) {
            if (context == null) {
                throw new NullPointerException("context == null");
            }
        }

        public KeyStoreParameter build() {
            return new KeyStoreParameter(0);
        }
    }
}
