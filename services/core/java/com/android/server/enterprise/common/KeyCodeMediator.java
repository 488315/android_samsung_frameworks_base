package com.android.server.enterprise.common;

/* loaded from: classes2.dex */
public interface KeyCodeMediator {
    void registerCallback(KeyCodeRestrictionCallback keyCodeRestrictionCallback);

    boolean update(int i);
}
