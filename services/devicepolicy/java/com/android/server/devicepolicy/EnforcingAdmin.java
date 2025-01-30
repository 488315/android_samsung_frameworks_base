package com.android.server.devicepolicy;

import android.app.admin.Authority;
import android.app.admin.DeviceAdminAuthority;
import android.app.admin.DpcAuthority;
import android.app.admin.RoleAuthority;
import android.app.admin.UnknownAuthority;
import android.content.ComponentName;
import android.os.UserHandle;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.role.RoleManagerLocal;
import com.android.server.LocalManagerRegistry;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.utils.Slogf;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class EnforcingAdmin {
    public final ActiveAdmin mActiveAdmin;
    public Set mAuthorities;
    public final ComponentName mComponentName;
    public final boolean mIsRoleAuthority;
    public final String mPackageName;
    public final int mUserId;

    public static EnforcingAdmin createEnforcingAdmin(String str, int i, ActiveAdmin activeAdmin) {
        Objects.requireNonNull(str);
        return new EnforcingAdmin(str, i, activeAdmin);
    }

    public static EnforcingAdmin createEnterpriseEnforcingAdmin(ComponentName componentName, int i) {
        Objects.requireNonNull(componentName);
        return new EnforcingAdmin(componentName.getPackageName(), componentName, Set.of("enterprise"), i, null);
    }

    public static EnforcingAdmin createEnterpriseEnforcingAdmin(ComponentName componentName, int i, ActiveAdmin activeAdmin) {
        Objects.requireNonNull(componentName);
        return new EnforcingAdmin(componentName.getPackageName(), componentName, Set.of("enterprise"), i, activeAdmin);
    }

    public static EnforcingAdmin createDeviceAdminEnforcingAdmin(ComponentName componentName, int i, ActiveAdmin activeAdmin) {
        Objects.requireNonNull(componentName);
        return new EnforcingAdmin(componentName.getPackageName(), componentName, Set.of("device_admin"), i, activeAdmin);
    }

    public static String getRoleAuthorityOf(String str) {
        return "role:" + str;
    }

    public static Authority getParcelableAuthority(String str) {
        if (str == null || str.isEmpty()) {
            return UnknownAuthority.UNKNOWN_AUTHORITY;
        }
        if ("enterprise".equals(str)) {
            return DpcAuthority.DPC_AUTHORITY;
        }
        if ("device_admin".equals(str)) {
            return DeviceAdminAuthority.DEVICE_ADMIN_AUTHORITY;
        }
        if (str.startsWith("role:")) {
            return new RoleAuthority(Set.of(str.substring(5)));
        }
        return UnknownAuthority.UNKNOWN_AUTHORITY;
    }

    public EnforcingAdmin(String str, ComponentName componentName, Set set, int i, ActiveAdmin activeAdmin) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(set);
        this.mIsRoleAuthority = false;
        this.mPackageName = str;
        this.mComponentName = componentName;
        this.mAuthorities = new HashSet(set);
        this.mUserId = i;
        this.mActiveAdmin = activeAdmin;
    }

    public EnforcingAdmin(String str, int i, ActiveAdmin activeAdmin) {
        Objects.requireNonNull(str);
        this.mIsRoleAuthority = true;
        this.mPackageName = str;
        this.mUserId = i;
        this.mComponentName = null;
        this.mAuthorities = null;
        this.mActiveAdmin = activeAdmin;
    }

    public static Set getRoleAuthoritiesOrDefault(String str, int i) {
        Set roles = getRoles(str, i);
        HashSet hashSet = new HashSet();
        Iterator it = roles.iterator();
        while (it.hasNext()) {
            hashSet.add("role:" + ((String) it.next()));
        }
        return hashSet.isEmpty() ? Set.of("default") : hashSet;
    }

    public static Set getRoles(String str, int i) {
        RoleManagerLocal roleManagerLocal = (RoleManagerLocal) LocalManagerRegistry.getManager(RoleManagerLocal.class);
        HashSet hashSet = new HashSet();
        Map rolesAndHolders = roleManagerLocal.getRolesAndHolders(i);
        for (String str2 : rolesAndHolders.keySet()) {
            if (((Set) rolesAndHolders.get(str2)).contains(str)) {
                hashSet.add(str2);
            }
        }
        return hashSet;
    }

    public final Set getAuthorities() {
        if (this.mAuthorities == null && this.mIsRoleAuthority) {
            this.mAuthorities = getRoleAuthoritiesOrDefault(this.mPackageName, this.mUserId);
        }
        return this.mAuthorities;
    }

    public void reloadRoleAuthorities() {
        if (this.mIsRoleAuthority) {
            this.mAuthorities = getRoleAuthoritiesOrDefault(this.mPackageName, this.mUserId);
        }
    }

    public boolean hasAuthority(String str) {
        return getAuthorities().contains(str);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public ActiveAdmin getActiveAdmin() {
        return this.mActiveAdmin;
    }

    public android.app.admin.EnforcingAdmin getParcelableAdmin() {
        UnknownAuthority unknownAuthority;
        if (this.mIsRoleAuthority) {
            Set roles = getRoles(this.mPackageName, this.mUserId);
            if (roles.isEmpty()) {
                unknownAuthority = UnknownAuthority.UNKNOWN_AUTHORITY;
            } else {
                unknownAuthority = new RoleAuthority(roles);
            }
        } else if (this.mAuthorities.contains("enterprise")) {
            unknownAuthority = DpcAuthority.DPC_AUTHORITY;
        } else if (this.mAuthorities.contains("device_admin")) {
            unknownAuthority = DeviceAdminAuthority.DEVICE_ADMIN_AUTHORITY;
        } else {
            unknownAuthority = UnknownAuthority.UNKNOWN_AUTHORITY;
        }
        return new android.app.admin.EnforcingAdmin(this.mPackageName, unknownAuthority, UserHandle.of(this.mUserId));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || EnforcingAdmin.class != obj.getClass()) {
            return false;
        }
        EnforcingAdmin enforcingAdmin = (EnforcingAdmin) obj;
        return Objects.equals(this.mPackageName, enforcingAdmin.mPackageName) && Objects.equals(this.mComponentName, enforcingAdmin.mComponentName) && Objects.equals(Boolean.valueOf(this.mIsRoleAuthority), Boolean.valueOf(enforcingAdmin.mIsRoleAuthority)) && hasMatchingAuthorities(this, enforcingAdmin);
    }

    public static boolean hasMatchingAuthorities(EnforcingAdmin enforcingAdmin, EnforcingAdmin enforcingAdmin2) {
        if (enforcingAdmin.mIsRoleAuthority && enforcingAdmin2.mIsRoleAuthority) {
            return true;
        }
        return enforcingAdmin.getAuthorities().equals(enforcingAdmin2.getAuthorities());
    }

    public int hashCode() {
        if (this.mIsRoleAuthority) {
            return Objects.hash(this.mPackageName, Integer.valueOf(this.mUserId));
        }
        Object[] objArr = new Object[3];
        Object obj = this.mComponentName;
        if (obj == null) {
            obj = this.mPackageName;
        }
        objArr[0] = obj;
        objArr[1] = Integer.valueOf(this.mUserId);
        objArr[2] = getAuthorities();
        return Objects.hash(objArr);
    }

    public void saveToXml(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.attribute((String) null, "package-name", this.mPackageName);
        typedXmlSerializer.attributeBoolean((String) null, "is-role", this.mIsRoleAuthority);
        typedXmlSerializer.attributeInt((String) null, "user-id", this.mUserId);
        if (this.mIsRoleAuthority) {
            return;
        }
        ComponentName componentName = this.mComponentName;
        if (componentName != null) {
            typedXmlSerializer.attribute((String) null, "class-name", componentName.getClassName());
        }
        typedXmlSerializer.attribute((String) null, "authorities", String.join(KnoxVpnFirewallHelper.DELIMITER, getAuthorities()));
    }

    public static EnforcingAdmin readFromXml(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "package-name");
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "is-role");
        String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "authorities");
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "user-id");
        if (attributeBoolean) {
            if (attributeValue == null) {
                Slogf.wtf("EnforcingAdmin", "Error parsing EnforcingAdmin with RoleAuthority, packageName is null.");
                return null;
            }
            return new EnforcingAdmin(attributeValue, attributeInt, null);
        }
        if (attributeValue == null || attributeValue2 == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error parsing EnforcingAdmin, packageName is ");
            if (attributeValue == null) {
                attributeValue = "null";
            }
            sb.append(attributeValue);
            sb.append(", and authorities is ");
            if (attributeValue2 == null) {
                attributeValue2 = "null";
            }
            sb.append(attributeValue2);
            sb.append(".");
            Slogf.wtf("EnforcingAdmin", sb.toString());
            return null;
        }
        String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "class-name");
        return new EnforcingAdmin(attributeValue, attributeValue3 != null ? new ComponentName(attributeValue, attributeValue3) : null, Set.of((Object[]) attributeValue2.split(KnoxVpnFirewallHelper.DELIMITER)), attributeInt, null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EnforcingAdmin { mPackageName= ");
        sb.append(this.mPackageName);
        if (this.mComponentName != null) {
            sb.append(", mComponentName= ");
            sb.append(this.mComponentName);
        }
        if (this.mAuthorities != null) {
            sb.append(", mAuthorities= ");
            sb.append(this.mAuthorities);
        }
        sb.append(", mUserId= ");
        sb.append(this.mUserId);
        sb.append(", mIsRoleAuthority= ");
        sb.append(this.mIsRoleAuthority);
        sb.append(" }");
        return sb.toString();
    }
}
