package android.provider;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class SettingsStringUtil {
    public static final String DELIMITER = ":";

    private SettingsStringUtil() {
    }

    public static abstract class ColonDelimitedSet<T> extends HashSet<T> {
        protected abstract T itemFromString(String str);

        public ColonDelimitedSet(String str) {
            for (String str2 : TextUtils.split(TextUtils.emptyIfNull(str), ":")) {
                add(itemFromString(str2));
            }
        }

        protected String itemToString(T t) {
            return String.valueOf(t);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractCollection
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator it = iterator();
            if (it.hasNext()) {
                sb.append(itemToString(it.next()));
                while (it.hasNext()) {
                    sb.append(":");
                    sb.append(itemToString(it.next()));
                }
            }
            return sb.toString();
        }

        public static class OfStrings extends ColonDelimitedSet<String> {
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.provider.SettingsStringUtil.ColonDelimitedSet
            public String itemFromString(String str) {
                return str;
            }

            public OfStrings(String str) {
                super(str);
            }

            public static String addAll(String str, Collection<String> collection) {
                OfStrings ofStrings = new OfStrings(str);
                return ofStrings.addAll(collection) ? ofStrings.toString() : str;
            }

            public static String add(String str, String str2) {
                OfStrings ofStrings = new OfStrings(str);
                if (ofStrings.contains(str2)) {
                    return str;
                }
                ofStrings.add(str2);
                return ofStrings.toString();
            }

            public static String remove(String str, String str2) {
                OfStrings ofStrings = new OfStrings(str);
                if (!ofStrings.contains(str2)) {
                    return str;
                }
                ofStrings.remove(str2);
                return ofStrings.toString();
            }

            public static boolean contains(String str, String str2) {
                return ArrayUtils.indexOf(TextUtils.split(str, ":"), str2) != -1;
            }
        }
    }

    public static class ComponentNameSet extends ColonDelimitedSet<ComponentName> {
        public ComponentNameSet(String str) {
            super(str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.SettingsStringUtil.ColonDelimitedSet
        public ComponentName itemFromString(String str) {
            return ComponentName.unflattenFromString(str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.provider.SettingsStringUtil.ColonDelimitedSet
        public String itemToString(ComponentName componentName) {
            return componentName != null ? componentName.flattenToString() : PerfettoProtoLogImpl.NULL_STRING;
        }

        public static String add(String str, ComponentName componentName) {
            ComponentNameSet componentNameSet = new ComponentNameSet(str);
            if (componentNameSet.contains(componentName)) {
                return str;
            }
            componentNameSet.add(componentName);
            return componentNameSet.toString();
        }

        public static String remove(String str, ComponentName componentName) {
            ComponentNameSet componentNameSet = new ComponentNameSet(str);
            if (!componentNameSet.contains(componentName)) {
                return str;
            }
            componentNameSet.remove(componentName);
            return componentNameSet.toString();
        }

        public static boolean contains(String str, ComponentName componentName) {
            return ColonDelimitedSet.OfStrings.contains(str, componentName.flattenToString());
        }
    }

    public static class SettingStringHelper {
        private final ContentResolver mContentResolver;
        private final String mSettingName;
        private final int mUserId;

        public SettingStringHelper(ContentResolver contentResolver, String str, int i) {
            this.mContentResolver = contentResolver;
            this.mUserId = i;
            this.mSettingName = str;
        }

        public String read() {
            return Settings.Secure.getStringForUser(this.mContentResolver, this.mSettingName, this.mUserId);
        }

        public boolean write(String str) {
            return Settings.Secure.putStringForUser(this.mContentResolver, this.mSettingName, str, this.mUserId);
        }

        public boolean modify(Function<String, String> function) {
            return write(function.apply(read()));
        }
    }
}
