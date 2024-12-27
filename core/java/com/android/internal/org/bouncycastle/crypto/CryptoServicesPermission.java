package com.android.internal.org.bouncycastle.crypto;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

public class CryptoServicesPermission extends Permission {
    public static final String DEFAULT_RANDOM = "defaultRandomConfig";
    public static final String GLOBAL_CONFIG = "globalConfig";
    public static final String THREAD_LOCAL_CONFIG = "threadLocalConfig";
    private final Set<String> actions;

    public CryptoServicesPermission(String name) {
        super(name);
        this.actions = new HashSet();
        this.actions.add(name);
    }

    @Override // java.security.Permission
    public boolean implies(Permission permission) {
        if (permission instanceof CryptoServicesPermission) {
            CryptoServicesPermission other = (CryptoServicesPermission) permission;
            return getName().equals(other.getName()) || this.actions.containsAll(other.actions);
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CryptoServicesPermission) {
            CryptoServicesPermission other = (CryptoServicesPermission) obj;
            if (this.actions.equals(other.actions)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        return this.actions.hashCode();
    }

    @Override // java.security.Permission
    public String getActions() {
        return this.actions.toString();
    }
}
