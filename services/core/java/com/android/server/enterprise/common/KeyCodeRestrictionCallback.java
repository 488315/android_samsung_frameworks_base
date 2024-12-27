package com.android.server.enterprise.common;

import com.android.server.enterprise.impl.KeyCodeMediatorImpl;

import java.util.Set;

public interface KeyCodeRestrictionCallback {
    Set getRestrictedKeyCodes();

    String getServiceName();

    boolean isKeyCodeInputAllowed(int i);

    void setMediator(KeyCodeMediatorImpl keyCodeMediatorImpl);
}
