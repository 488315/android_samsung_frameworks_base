package com.android.server.permission;

public interface PermissionManagerLocal {
    boolean isSignaturePermissionAllowlistForceEnforced();

    void setSignaturePermissionAllowlistForceEnforced(boolean z);
}
