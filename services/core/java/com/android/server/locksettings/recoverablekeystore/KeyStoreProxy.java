package com.android.server.locksettings.recoverablekeystore;

import java.security.Key;
import java.security.KeyStore;

/* loaded from: classes2.dex */
public interface KeyStoreProxy {
    boolean containsAlias(String str);

    void deleteEntry(String str);

    Key getKey(String str, char[] cArr);

    void setEntry(String str, KeyStore.Entry entry, KeyStore.ProtectionParameter protectionParameter);
}
