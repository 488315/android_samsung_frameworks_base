package com.android.server.devicepolicy;

import android.app.admin.DeviceAdminAuthority;
import android.app.admin.DpcAuthority;
import android.app.admin.RoleAuthority;
import android.app.admin.UnknownAuthority;
import android.content.ComponentName;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.UserHandle;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.role.RoleManagerLocal;
import com.android.server.LocalManagerRegistry;
import com.android.server.utils.Slogf;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EnforcingAdmin {
    public final ActiveAdmin mActiveAdmin;
    public Set mAuthorities;
    public final ComponentName mComponentName;
    public final boolean mIsRoleAuthority;
    public final String mPackageName;
    public final int mUserId;

    public EnforcingAdmin(String str, int i, ActiveAdmin activeAdmin) {
        Objects.requireNonNull(str);
        this.mIsRoleAuthority = true;
        this.mPackageName = str;
        this.mUserId = i;
        this.mComponentName = null;
        this.mAuthorities = null;
        this.mActiveAdmin = activeAdmin;
    }

    public EnforcingAdmin(String str, ComponentName componentName, Set set, int i) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(set);
        this.mIsRoleAuthority = true;
        this.mPackageName = str;
        this.mComponentName = componentName;
        this.mAuthorities = new HashSet(set);
        this.mUserId = i;
        this.mActiveAdmin = null;
    }

    public EnforcingAdmin(
            String str, ComponentName componentName, Set set, int i, ActiveAdmin activeAdmin) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(set);
        this.mIsRoleAuthority = false;
        this.mPackageName = str;
        this.mComponentName = componentName;
        this.mAuthorities = new HashSet(set);
        this.mUserId = i;
        this.mActiveAdmin = activeAdmin;
    }

    public static EnforcingAdmin createEnterpriseEnforcingAdmin(
            int i, ComponentName componentName) {
        Objects.requireNonNull(componentName);
        return new EnforcingAdmin(
                componentName.getPackageName(), componentName, Set.of("enterprise"), i, null);
    }

    public static EnforcingAdmin createEnterpriseEnforcingAdmin(
            ComponentName componentName, int i, ActiveAdmin activeAdmin) {
        Objects.requireNonNull(componentName);
        return new EnforcingAdmin(
                componentName.getPackageName(),
                componentName,
                Set.of("enterprise"),
                i,
                activeAdmin);
    }

    public static Set getRoleAuthoritiesOrDefault(int i, String str) {
        Set roles = getRoles(i, str);
        HashSet hashSet = new HashSet();
        Iterator it = ((HashSet) roles).iterator();
        while (it.hasNext()) {
            hashSet.add("role:" + ((String) it.next()));
        }
        return hashSet.isEmpty() ? Set.of("default") : hashSet;
    }

    public static Set getRoles(int i, String str) {
        RoleManagerLocal roleManagerLocal =
                (RoleManagerLocal) LocalManagerRegistry.getManager(RoleManagerLocal.class);
        HashSet hashSet = new HashSet();
        Map rolesAndHolders = roleManagerLocal.getRolesAndHolders(i);
        for (String str2 : rolesAndHolders.keySet()) {
            if (((Set) rolesAndHolders.get(str2)).contains(str)) {
                hashSet.add(str2);
            }
        }
        return hashSet;
    }

    public static EnforcingAdmin readFromXml(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "package-name");
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((String) null, "is-role");
        String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "authorities");
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "user-id");
        if (attributeBoolean) {
            if (attributeValue != null) {
                return new EnforcingAdmin(attributeValue, attributeInt, null);
            }
            Slogf.wtf(
                    "EnforcingAdmin",
                    "Error parsing EnforcingAdmin with RoleAuthority, packageName is null.");
            return null;
        }
        if (attributeValue != null && attributeValue2 != null) {
            String attributeValue3 =
                    typedXmlPullParser.getAttributeValue((String) null, "class-name");
            return new EnforcingAdmin(
                    attributeValue,
                    attributeValue3 != null
                            ? new ComponentName(attributeValue, attributeValue3)
                            : null,
                    Set.of((Object[]) attributeValue2.split(";")),
                    attributeInt,
                    null);
        }
        StringBuilder sb = new StringBuilder("Error parsing EnforcingAdmin, packageName is ");
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

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || EnforcingAdmin.class != obj.getClass()) {
            return false;
        }
        EnforcingAdmin enforcingAdmin = (EnforcingAdmin) obj;
        if (Objects.equals(this.mPackageName, enforcingAdmin.mPackageName)
                && Objects.equals(this.mComponentName, enforcingAdmin.mComponentName)) {
            boolean z = this.mIsRoleAuthority;
            Boolean valueOf = Boolean.valueOf(z);
            boolean z2 = enforcingAdmin.mIsRoleAuthority;
            if (valueOf.equals(Boolean.valueOf(z2))) {
                if ((z && z2) ? true : getAuthorities().equals(enforcingAdmin.getAuthorities())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final Set getAuthorities() {
        if (this.mAuthorities == null && this.mIsRoleAuthority) {
            this.mAuthorities = getRoleAuthoritiesOrDefault(this.mUserId, this.mPackageName);
        }
        return this.mAuthorities;
    }

    public final android.app.admin.EnforcingAdmin getParcelableAdmin() {
        UnknownAuthority unknownAuthority;
        boolean z = this.mIsRoleAuthority;
        int i = this.mUserId;
        String str = this.mPackageName;
        if (z) {
            Set roles = getRoles(i, str);
            unknownAuthority =
                    ((HashSet) roles).isEmpty()
                            ? UnknownAuthority.UNKNOWN_AUTHORITY
                            : new RoleAuthority(roles);
        } else {
            unknownAuthority =
                    this.mAuthorities.contains("enterprise")
                            ? DpcAuthority.DPC_AUTHORITY
                            : this.mAuthorities.contains("device_admin")
                                    ? DeviceAdminAuthority.DEVICE_ADMIN_AUTHORITY
                                    : UnknownAuthority.UNKNOWN_AUTHORITY;
        }
        return new android.app.admin.EnforcingAdmin(
                str, unknownAuthority, UserHandle.of(i), this.mComponentName);
    }

    public final boolean hasAuthority(String str) {
        return getAuthorities().contains(str);
    }

    public final int hashCode() {
        boolean z = this.mIsRoleAuthority;
        int i = this.mUserId;
        Object obj = this.mPackageName;
        if (z) {
            return Objects.hash(obj, Integer.valueOf(i));
        }
        Object obj2 = this.mComponentName;
        if (obj2 != null) {
            obj = obj2;
        }
        return Objects.hash(obj, Integer.valueOf(i), getAuthorities());
    }

    public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.attribute((String) null, "package-name", this.mPackageName);
        boolean z = this.mIsRoleAuthority;
        typedXmlSerializer.attributeBoolean((String) null, "is-role", z);
        typedXmlSerializer.attributeInt((String) null, "user-id", this.mUserId);
        if (z) {
            return;
        }
        ComponentName componentName = this.mComponentName;
        if (componentName != null) {
            typedXmlSerializer.attribute((String) null, "class-name", componentName.getClassName());
        }
        typedXmlSerializer.attribute(
                (String) null, "authorities", String.join(";", getAuthorities()));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("EnforcingAdmin { mPackageName= ");
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
        return OptionalBool$$ExternalSyntheticOutline0.m(" }", sb, this.mIsRoleAuthority);
    }
}
