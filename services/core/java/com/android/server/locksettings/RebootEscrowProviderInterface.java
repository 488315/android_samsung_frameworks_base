package com.android.server.locksettings;

import javax.crypto.SecretKey;

public interface RebootEscrowProviderInterface {
    void clearRebootEscrowKey();

    RebootEscrowKey getAndClearRebootEscrowKey(SecretKey secretKey);

    int getType();

    boolean hasRebootEscrowSupport();

    boolean storeRebootEscrowKey(RebootEscrowKey rebootEscrowKey, SecretKey secretKey);
}
