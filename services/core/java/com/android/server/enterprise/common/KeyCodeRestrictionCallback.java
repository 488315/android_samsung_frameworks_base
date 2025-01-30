package com.android.server.enterprise.common;

import java.util.Set;

/* loaded from: classes2.dex */
public interface KeyCodeRestrictionCallback extends KeyCodeCallback {
    Set getRestrictedKeyCodes();

    String getServiceName();

    boolean isKeyCodeInputAllowed(int i);
}
