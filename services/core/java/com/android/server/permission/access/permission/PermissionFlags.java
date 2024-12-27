package com.android.server.permission.access.permission;

import com.android.server.permission.access.util.IntExtensionsKt;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.permission.jarjar.kotlin.ranges.IntRange;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PermissionFlags {
    public static boolean isAppOpGranted(int i) {
        return (!isPermissionGranted(i)
                        || IntExtensionsKt.hasAnyBit(i, 786432)
                        || IntExtensionsKt.hasBits(i, 1048576))
                ? false
                : true;
    }

    public static boolean isPermissionGranted(int i) {
        if (IntExtensionsKt.hasBits(i, 1)) {
            return true;
        }
        if (IntExtensionsKt.hasBits(i, 2)) {
            return false;
        }
        if (IntExtensionsKt.hasBits(i, 4)
                || IntExtensionsKt.hasBits(i, 1024)
                || IntExtensionsKt.hasBits(i, 2048)) {
            return true;
        }
        if (IntExtensionsKt.hasBits(i, 262144)) {
            return false;
        }
        return IntExtensionsKt.hasBits(i, 16);
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x008a, code lost:

       if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, 524288) != false) goto L43;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v17, types: [int] */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v27 */
    /* JADX WARN: Type inference failed for: r0v28 */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r0v31 */
    /* JADX WARN: Type inference failed for: r0v32 */
    /* JADX WARN: Type inference failed for: r0v76 */
    /* JADX WARN: Type inference failed for: r0v77 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int toApiFlags(int r7) {
        /*
            r0 = 32
            boolean r0 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r0)
            r1 = 64
            boolean r2 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r1)
            if (r2 == 0) goto L10
            r0 = r0 | 2
        L10:
            r2 = 128(0x80, float:1.794E-43)
            boolean r3 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r2)
            if (r3 == 0) goto L1a
            r0 = r0 | 4
        L1a:
            r3 = 256(0x100, float:3.59E-43)
            boolean r3 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r3)
            if (r3 == 0) goto L24
            r0 = r0 | 16
        L24:
            r3 = 512(0x200, float:7.175E-43)
            boolean r3 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r3)
            if (r3 == 0) goto L2e
            r0 = r0 | 32
        L2e:
            r3 = 4096(0x1000, float:5.74E-42)
            boolean r3 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r3)
            r4 = 8
            if (r3 == 0) goto L49
            r3 = 1024(0x400, float:1.435E-42)
            boolean r3 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r3)
            if (r3 == 0) goto L48
            boolean r2 = com.samsung.android.rune.PMRune.PERM_CHINA_COMPAT_LOW_SDK
            if (r2 == 0) goto L46
            r2 = r4
            goto L47
        L46:
            r2 = 0
        L47:
            r2 = r2 | r1
        L48:
            r0 = r0 | r2
        L49:
            r1 = 8192(0x2000, float:1.14794E-41)
            boolean r1 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r1)
            if (r1 == 0) goto L53
            r0 = r0 | 256(0x100, float:3.59E-43)
        L53:
            r1 = 16384(0x4000, float:2.2959E-41)
            boolean r1 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r1)
            if (r1 == 0) goto L5d
            r0 = r0 | 512(0x200, float:7.175E-43)
        L5d:
            r1 = 32768(0x8000, float:4.5918E-41)
            boolean r2 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r1)
            if (r2 == 0) goto L68
            r0 = r0 | 2048(0x800, float:2.87E-42)
        L68:
            r2 = 65536(0x10000, float:9.18355E-41)
            boolean r3 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r2)
            if (r3 == 0) goto L72
            r0 = r0 | 4096(0x1000, float:5.74E-42)
        L72:
            r3 = 131072(0x20000, float:1.83671E-40)
            boolean r5 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r3)
            if (r5 == 0) goto L7c
            r0 = r0 | 8192(0x2000, float:1.14794E-41)
        L7c:
            r5 = 262144(0x40000, float:3.67342E-40)
            boolean r5 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r5)
            r6 = 524288(0x80000, float:7.34684E-40)
            if (r5 != 0) goto L8c
            boolean r5 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r6)
            if (r5 == 0) goto L8e
        L8c:
            r0 = r0 | 16384(0x4000, float:2.2959E-41)
        L8e:
            boolean r4 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r4)
            if (r4 == 0) goto L95
            r0 = r0 | r1
        L95:
            r1 = 1048576(0x100000, float:1.469368E-39)
            boolean r1 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r1)
            if (r1 == 0) goto L9f
            r0 = r0 | 8
        L9f:
            r1 = 2097152(0x200000, float:2.938736E-39)
            boolean r1 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r1)
            if (r1 == 0) goto La8
            r0 = r0 | r2
        La8:
            r1 = 4194304(0x400000, float:5.877472E-39)
            boolean r1 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r1)
            if (r1 == 0) goto Lb1
            r0 = r0 | r3
        Lb1:
            r1 = 8388608(0x800000, float:1.17549435E-38)
            boolean r7 = com.android.server.permission.access.util.IntExtensionsKt.hasBits(r7, r1)
            if (r7 == 0) goto Lba
            r0 = r0 | r6
        Lba:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.permission.PermissionFlags.toApiFlags(int):int");
    }

    public static String toString(int i) {
        String str;
        StringBuilder sb = new StringBuilder("[");
        while (i != 0) {
            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            if (numberOfTrailingZeros == 1) {
                str = "INSTALL_GRANTED";
            } else if (numberOfTrailingZeros != 2) {
                switch (numberOfTrailingZeros) {
                    case 4:
                        str = "PROTECTION_GRANTED";
                        break;
                    case 8:
                        str = "ROLE";
                        break;
                    case 16:
                        str = "RUNTIME_GRANTED";
                        break;
                    case 32:
                        str = "USER_SET";
                        break;
                    case 64:
                        str = "USER_FIXED";
                        break;
                    case 128:
                        str = "POLICY_FIXED";
                        break;
                    case 256:
                        str = "SYSTEM_FIXED";
                        break;
                    case 512:
                        str = "PREGRANT";
                        break;
                    case 1024:
                        str = "LEGACY_GRANTED";
                        break;
                    case 2048:
                        str = "IMPLICIT_GRANTED";
                        break;
                    case 4096:
                        str = "IMPLICIT";
                        break;
                    case 8192:
                        str = "USER_SENSITIVE_WHEN_GRANTED";
                        break;
                    case EndpointMonitorConst
                            .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION /* 16384 */:
                        str = "USER_SENSITIVE_WHEN_REVOKED";
                        break;
                    case 32768:
                        str = "INSTALLER_EXEMPT";
                        break;
                    case EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT /* 65536 */:
                        str = "SYSTEM_EXEMPT";
                        break;
                    case 131072:
                        str = "UPGRADE_EXEMPT";
                        break;
                    case 262144:
                        str = "RESTRICTION_REVOKED";
                        break;
                    case 524288:
                        str = "SOFT_RESTRICTED";
                        break;
                    case 1048576:
                        str = "APP_OP_REVOKED";
                        break;
                    case 2097152:
                        str = "ONE_TIME";
                        break;
                    case 4194304:
                        str = "HIBERNATION";
                        break;
                    case 8388608:
                        str = "USER_SELECTED";
                        break;
                    default:
                        long j = numberOfTrailingZeros & 4294967295L;
                        if (16 > new IntRange(2, 36).last) {
                            throw new IllegalArgumentException(
                                    "radix 16 was not in valid range " + new IntRange(2, 36));
                        }
                        String l = Long.toString(j, 16);
                        Intrinsics.checkNotNullExpressionValue("toString(...)", l);
                        String upperCase = l.toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue("toUpperCase(...)", upperCase);
                        str = "0x".concat(upperCase);
                        break;
                }
            } else {
                str = "INSTALL_REVOKED";
            }
            sb.append(str);
            if (i != 0) {
                sb.append('|');
            }
        }
        sb.append("]");
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue("toString(...)", sb2);
        return sb2;
    }

    public static int updateFlags(Permission permission, int i, int i2, int i3) {
        int apiFlags = (i2 & i3) | (toApiFlags(i) & (~i2));
        int i4 = i & 7;
        if (IntExtensionsKt.hasBits(apiFlags, 32768)) {
            i4 |= 8;
        }
        int i5 = i4 | (i & 16);
        if (IntExtensionsKt.hasBits(apiFlags, 1)) {
            i5 |= 32;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 2)) {
            i5 |= 64;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 4)) {
            i5 |= 128;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 16)) {
            i5 |= 256;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 32)) {
            i5 |= 512;
        }
        int i6 = (i & 2048) | i5 | (i & 1024);
        if (IntExtensionsKt.hasBits(apiFlags, 64) || IntExtensionsKt.hasBits(apiFlags, 128)) {
            i6 |= 4096;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 256)) {
            i6 |= 8192;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 512)) {
            i6 |= EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 2048)) {
            i6 |= 32768;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 4096)) {
            i6 |= EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 8192)) {
            i6 |= 131072;
        }
        if (IntExtensionsKt.hasBits(
                apiFlags, EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)) {
            if (IntExtensionsKt.hasBits(permission.permissionInfo.flags, 4)) {
                i6 |= 262144;
            }
            if (IntExtensionsKt.hasBits(permission.permissionInfo.flags, 8)) {
                i6 |= 524288;
            }
        }
        if (IntExtensionsKt.hasBits(apiFlags, 8)) {
            i6 |= 1048576;
        }
        if (IntExtensionsKt.hasBits(
                apiFlags, EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT)) {
            i6 |= 2097152;
        }
        if (IntExtensionsKt.hasBits(apiFlags, 131072)) {
            i6 |= 4194304;
        }
        return IntExtensionsKt.hasBits(apiFlags, 524288) ? i6 | 8388608 : i6;
    }
}
