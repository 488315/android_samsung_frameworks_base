package android.app.admin;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.util.Preconditions;
import com.android.modules.utils.ModifiedUtf8;
import java.io.UTFDataFormatException;
import java.util.ArrayDeque;

/* loaded from: classes.dex */
public class PolicySizeVerifier {
    public static final int MAX_LONG_SUPPORT_MESSAGE_LENGTH = 20000;
    public static final int MAX_ORG_NAME_LENGTH = 200;
    public static final int MAX_PACKAGE_NAME_LENGTH = 223;
    public static final int MAX_PROFILE_NAME_LENGTH = 200;
    public static final int MAX_SHORT_SUPPORT_MESSAGE_LENGTH = 200;

    public static void enforceMaxStringLength(String str, String str2) {
        try {
            ModifiedUtf8.countBytes(str, true);
        } catch (UTFDataFormatException unused) {
            throw new IllegalArgumentException(str2 + " too long");
        }
    }

    public static void enforceMaxPackageNameLength(String str) {
        Preconditions.checkArgument(str.length() <= 223, "Package name too long");
    }

    public static void enforceMaxStringLength(PersistableBundle persistableBundle, String str) {
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(persistableBundle);
        while (!arrayDeque.isEmpty()) {
            PersistableBundle persistableBundle2 = (PersistableBundle) arrayDeque.remove();
            for (String str2 : persistableBundle2.keySet()) {
                enforceMaxStringLength(str2, "key in " + str);
                Object obj = persistableBundle2.get(str2);
                if (obj instanceof String) {
                    enforceMaxStringLength((String) obj, "string value in " + str);
                } else if (obj instanceof String[]) {
                    for (String str3 : (String[]) obj) {
                        enforceMaxStringLength(str3, "string value in " + str);
                    }
                } else if (obj instanceof PersistableBundle) {
                    arrayDeque.add((PersistableBundle) obj);
                }
            }
        }
    }

    public static void enforceMaxBundleFieldsLength(Bundle bundle) {
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(bundle);
        while (!arrayDeque.isEmpty()) {
            Bundle bundle2 = (Bundle) arrayDeque.remove();
            for (String str : bundle2.keySet()) {
                enforceMaxStringLength(str, "key in Bundle");
                Object obj = bundle2.get(str);
                if (obj instanceof String) {
                    enforceMaxStringLength((String) obj, "string value in Bundle with key" + str);
                } else {
                    int i = 0;
                    if (obj instanceof String[]) {
                        String[] strArr = (String[]) obj;
                        int length = strArr.length;
                        while (i < length) {
                            enforceMaxStringLength(strArr[i], "string value in Bundle with key" + str);
                            i++;
                        }
                    } else if (obj instanceof Bundle) {
                        arrayDeque.add((Bundle) obj);
                    } else if (obj instanceof Parcelable[]) {
                        Parcelable[] parcelableArr = (Parcelable[]) obj;
                        int length2 = parcelableArr.length;
                        while (i < length2) {
                            Parcelable parcelable = parcelableArr[i];
                            if (!(parcelable instanceof Bundle)) {
                                throw new IllegalArgumentException("bundle-array can only hold Bundles");
                            }
                            arrayDeque.add((Bundle) parcelable);
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

    public static CharSequence truncateIfLonger(CharSequence charSequence, int i) {
        return (charSequence == null || charSequence.length() <= i) ? charSequence : charSequence.subSequence(0, i);
    }
}
