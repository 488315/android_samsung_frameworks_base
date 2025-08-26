package kotlin.internal.jdk8;

import kotlin.internal.jdk7.JDK7PlatformImplementations;

/* loaded from: classes4.dex */
public class JDK8PlatformImplementations extends JDK7PlatformImplementations {

    public final class ReflectSdkVersion {
        public static final Integer sdkVersion;

        static {
            Object obj;
            new ReflectSdkVersion();
            Integer num = null;
            try {
                obj = Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
            } catch (Throwable unused) {
            }
            Integer num2 = obj instanceof Integer ? (Integer) obj : null;
            if (num2 != null && num2.intValue() > 0) {
                num = num2;
            }
            sdkVersion = num;
        }

        private ReflectSdkVersion() {
        }
    }
}
