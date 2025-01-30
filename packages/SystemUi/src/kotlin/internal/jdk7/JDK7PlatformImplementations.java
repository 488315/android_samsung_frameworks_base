package kotlin.internal.jdk7;

import kotlin.internal.PlatformImplementations;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class JDK7PlatformImplementations extends PlatformImplementations {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ReflectSdkVersion {
        public static final Integer sdkVersion;

        /* JADX WARN: Removed duplicated region for block: B:7:0x0020  */
        static {
            Integer num;
            Object obj;
            new ReflectSdkVersion();
            Integer num2 = null;
            try {
                obj = Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
            } catch (Throwable unused) {
            }
            if (obj instanceof Integer) {
                num = (Integer) obj;
                if (num != null) {
                    if (num.intValue() > 0) {
                        num2 = num;
                    }
                }
                sdkVersion = num2;
            }
            num = null;
            if (num != null) {
            }
            sdkVersion = num2;
        }

        private ReflectSdkVersion() {
        }
    }

    @Override // kotlin.internal.PlatformImplementations
    public final void addSuppressed(Throwable th, Throwable th2) {
        Integer num = ReflectSdkVersion.sdkVersion;
        if (num == null || num.intValue() >= 19) {
            th.addSuppressed(th2);
        } else {
            super.addSuppressed(th, th2);
        }
    }
}
