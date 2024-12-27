package android.app.admin;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.util.Preconditions;
import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: classes.dex */
public class PolicySizeVerifier {
    public static final int MAX_LONG_SUPPORT_MESSAGE_LENGTH = 20000;
    public static final int MAX_ORG_NAME_LENGTH = 200;
    public static final int MAX_PACKAGE_NAME_LENGTH = 223;
    public static final int MAX_POLICY_STRING_LENGTH = 65535;
    public static final int MAX_PROFILE_NAME_LENGTH = 200;
    public static final int MAX_SHORT_SUPPORT_MESSAGE_LENGTH = 200;

    public static void enforceMaxStringLength(String str, String argName) {
        Preconditions.checkArgument(str.length() <= 65535, argName + " loo long");
    }

    public static void enforceMaxPackageNameLength(String pkg) {
        Preconditions.checkArgument(pkg.length() <= 223, "Package name too long");
    }

    public static void enforceMaxStringLength(PersistableBundle bundle, String argName) {
        Queue<PersistableBundle> queue = new ArrayDeque<>();
        queue.add(bundle);
        while (!queue.isEmpty()) {
            PersistableBundle current = queue.remove();
            for (String key : current.keySet()) {
                enforceMaxStringLength(key, "key in " + argName);
                Object value = current.get(key);
                if (value instanceof String) {
                    String str = (String) value;
                    enforceMaxStringLength(str, "string value in " + argName);
                } else if (value instanceof String[]) {
                    String[] strArray = (String[]) value;
                    for (String str2 : strArray) {
                        enforceMaxStringLength(str2, "string value in " + argName);
                    }
                } else if (value instanceof PersistableBundle) {
                    PersistableBundle persistableBundle = (PersistableBundle) value;
                    queue.add(persistableBundle);
                }
            }
        }
    }

    public static void enforceMaxBundleFieldsLength(Bundle bundle) {
        Queue<Bundle> queue = new ArrayDeque<>();
        queue.add(bundle);
        while (!queue.isEmpty()) {
            Bundle current = queue.remove();
            for (String key : current.keySet()) {
                enforceMaxStringLength(key, "key in Bundle");
                Object value = current.get(key);
                if (value instanceof String) {
                    String str = (String) value;
                    enforceMaxStringLength(str, "string value in Bundle with key" + key);
                } else {
                    int i = 0;
                    if (value instanceof String[]) {
                        String[] strArray = (String[]) value;
                        int length = strArray.length;
                        while (i < length) {
                            String str2 = strArray[i];
                            enforceMaxStringLength(str2, "string value in Bundle with key" + key);
                            i++;
                        }
                    } else if (value instanceof Bundle) {
                        Bundle b = (Bundle) value;
                        queue.add(b);
                    } else if (value instanceof Parcelable[]) {
                        Parcelable[] parcelableArray = (Parcelable[]) value;
                        int length2 = parcelableArray.length;
                        while (i < length2) {
                            Parcelable parcelable = parcelableArray[i];
                            if (!(parcelable instanceof Bundle)) {
                                throw new IllegalArgumentException("bundle-array can only hold Bundles");
                            }
                            queue.add((Bundle) parcelable);
                            i++;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    public static void enforceMaxComponentNameLength(ComponentName componentName) {
        enforceMaxPackageNameLength(componentName.getPackageName());
        enforceMaxStringLength(componentName.flattenToString(), "componentName");
    }

    public static CharSequence truncateIfLonger(CharSequence input, int maxLength) {
        if (input == null || input.length() <= maxLength) {
            return input;
        }
        return input.subSequence(0, maxLength);
    }
}
