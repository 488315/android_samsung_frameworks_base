package android.hardware.input;

import android.text.TextUtils;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface AppLaunchData {
    static AppLaunchData createLaunchDataForCategory(String str) {
        return new CategoryData(str);
    }

    static AppLaunchData createLaunchDataForRole(String str) {
        return new RoleData(str);
    }

    static AppLaunchData createLaunchDataForComponent(String str, String str2) {
        return new ComponentData(str, str2);
    }

    static AppLaunchData createLaunchData(String str, String str2, String str3, String str4) {
        if (!TextUtils.isEmpty(str)) {
            return new CategoryData(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            return new RoleData(str2);
        }
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            return null;
        }
        return new ComponentData(str3, str4);
    }

    public static class CategoryData implements AppLaunchData {
        private final String mCategory;

        public CategoryData(String str) {
            this.mCategory = str;
        }

        public String getCategory() {
            return this.mCategory;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof CategoryData) {
                return Objects.equals(this.mCategory, ((CategoryData) obj).mCategory);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mCategory);
        }

        public String toString() {
            return "CategoryData{mCategory='" + this.mCategory + "'}";
        }
    }

    public static class RoleData implements AppLaunchData {
        private final String mRole;

        public RoleData(String str) {
            this.mRole = str;
        }

        public String getRole() {
            return this.mRole;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof RoleData) {
                return Objects.equals(this.mRole, ((RoleData) obj).mRole);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mRole);
        }

        public String toString() {
            return "RoleData{mRole='" + this.mRole + "'}";
        }
    }

    public static class ComponentData implements AppLaunchData {
        private final String mClassName;
        private final String mPackageName;

        public ComponentData(String str, String str2) {
            this.mPackageName = str;
            this.mClassName = str2;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public String getClassName() {
            return this.mClassName;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ComponentData) {
                ComponentData componentData = (ComponentData) obj;
                if (Objects.equals(this.mPackageName, componentData.mPackageName) && Objects.equals(this.mClassName, componentData.mClassName)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mPackageName, this.mClassName);
        }

        public String toString() {
            return "ComponentData{mPackageName='" + this.mPackageName + "', mClassName='" + this.mClassName + "'}";
        }
    }
}
