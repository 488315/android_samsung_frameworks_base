package com.android.server.permission.access.permission;

import android.os.IInstalld;
import com.android.server.permission.access.util.IntExtensionsKt;

/* compiled from: PermissionFlags.kt */
/* loaded from: classes2.dex */
public final class PermissionFlags {
    public static final PermissionFlags INSTANCE = new PermissionFlags();

    public final boolean isPermissionGranted(int i) {
        if (IntExtensionsKt.hasBits(i, 1)) {
            return true;
        }
        if (IntExtensionsKt.hasBits(i, 2)) {
            return false;
        }
        if (IntExtensionsKt.hasBits(i, 4) || IntExtensionsKt.hasBits(i, 1024) || IntExtensionsKt.hasBits(i, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES)) {
            return true;
        }
        if (IntExtensionsKt.hasBits(i, 262144)) {
            return false;
        }
        return IntExtensionsKt.hasBits(i, 16);
    }

    public final boolean isAppOpGranted(int i) {
        return isPermissionGranted(i) && !IntExtensionsKt.hasBits(i, 1048576);
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0082, code lost:
    
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(r6, 524288) != false) goto L40;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v18, types: [int] */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v23 */
    /* JADX WARN: Type inference failed for: r5v24 */
    /* JADX WARN: Type inference failed for: r5v25 */
    /* JADX WARN: Type inference failed for: r5v26 */
    /* JADX WARN: Type inference failed for: r5v27 */
    /* JADX WARN: Type inference failed for: r5v28 */
    /* JADX WARN: Type inference failed for: r5v29 */
    /* JADX WARN: Type inference failed for: r5v30 */
    /* JADX WARN: Type inference failed for: r5v31 */
    /* JADX WARN: Type inference failed for: r5v32 */
    /* JADX WARN: Type inference failed for: r5v33 */
    /* JADX WARN: Type inference failed for: r5v77 */
    /* JADX WARN: Type inference failed for: r5v78 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int toApiFlags(int i) {
        boolean z;
        boolean hasBits = IntExtensionsKt.hasBits(i, 32);
        boolean z2 = hasBits;
        if (IntExtensionsKt.hasBits(i, 64)) {
            z2 = (hasBits ? 1 : 0) | 2;
        }
        boolean z3 = z2;
        if (IntExtensionsKt.hasBits(i, 128)) {
            z3 = (z2 ? 1 : 0) | 4;
        }
        boolean z4 = z3;
        if (IntExtensionsKt.hasBits(i, 256)) {
            z4 = (z3 ? 1 : 0) | 16;
        }
        boolean z5 = z4;
        if (IntExtensionsKt.hasBits(i, 512)) {
            z5 = (z4 ? 1 : 0) | ' ';
        }
        boolean z6 = z5;
        if (IntExtensionsKt.hasBits(i, IInstalld.FLAG_USE_QUOTA)) {
            z6 = (z5 ? 1 : 0) | (IntExtensionsKt.hasBits(i, 1024) ? '@' : (char) 128);
        }
        boolean z7 = z6;
        if (IntExtensionsKt.hasBits(i, IInstalld.FLAG_FORCE)) {
            z7 = (z6 ? 1 : 0) | 256;
        }
        boolean z8 = z7;
        if (IntExtensionsKt.hasBits(i, 16384)) {
            z8 = (z7 ? 1 : 0) | 512;
        }
        boolean z9 = z8;
        if (IntExtensionsKt.hasBits(i, 32768)) {
            z9 = (z8 ? 1 : 0) | 2048;
        }
        boolean z10 = z9;
        if (IntExtensionsKt.hasBits(i, 65536)) {
            z10 = (z9 ? 1 : 0) | 4096;
        }
        boolean z11 = z10;
        if (IntExtensionsKt.hasBits(i, IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES)) {
            z11 = (z10 ? 1 : 0) | 8192;
        }
        if (!IntExtensionsKt.hasBits(i, 262144)) {
            z = z11;
        }
        z = (z11 ? 1 : 0) | 16384;
        boolean z12 = z;
        if (IntExtensionsKt.hasBits(i, 8)) {
            z12 = (z ? 1 : 0) | 32768;
        }
        boolean z13 = z12;
        if (IntExtensionsKt.hasBits(i, 1048576)) {
            z13 = (z12 ? 1 : 0) | '\b';
        }
        boolean z14 = z13;
        if (IntExtensionsKt.hasBits(i, 2097152)) {
            z14 = (z13 ? 1 : 0) | 0;
        }
        ?? r5 = z14;
        if (IntExtensionsKt.hasBits(i, 4194304)) {
            r5 = (z14 ? 1 : 0) | IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
        }
        return IntExtensionsKt.hasBits(i, 8388608) ? r5 | 524288 : r5;
    }

    public final int updateRuntimePermissionGranted(int i, boolean z) {
        return z ? 16 | i : IntExtensionsKt.andInv(i, 16);
    }

    public final int updateFlags(Permission permission, int i, int i2, int i3) {
        return fromApiFlags((i2 & i3) | IntExtensionsKt.andInv(toApiFlags(i), i2), permission, i);
    }

    public final int fromApiFlags(int i, Permission permission, int i2) {
        int i3 = (i2 & 1) | 0 | (i2 & 2) | (i2 & 4);
        if (IntExtensionsKt.hasBits(i, 32768)) {
            i3 |= 8;
        }
        int i4 = i3 | (i2 & 16);
        if (IntExtensionsKt.hasBits(i, 1)) {
            i4 |= 32;
        }
        if (IntExtensionsKt.hasBits(i, 2)) {
            i4 |= 64;
        }
        if (IntExtensionsKt.hasBits(i, 4)) {
            i4 |= 128;
        }
        if (IntExtensionsKt.hasBits(i, 16)) {
            i4 |= 256;
        }
        if (IntExtensionsKt.hasBits(i, 32)) {
            i4 |= 512;
        }
        int i5 = i4 | (i2 & 1024) | (i2 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
        if (IntExtensionsKt.hasBits(i, 64) || IntExtensionsKt.hasBits(i, 128)) {
            i5 |= IInstalld.FLAG_USE_QUOTA;
        }
        if (IntExtensionsKt.hasBits(i, 256)) {
            i5 |= IInstalld.FLAG_FORCE;
        }
        if (IntExtensionsKt.hasBits(i, 512)) {
            i5 |= 16384;
        }
        if (IntExtensionsKt.hasBits(i, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES)) {
            i5 |= 32768;
        }
        if (IntExtensionsKt.hasBits(i, IInstalld.FLAG_USE_QUOTA)) {
            i5 |= 65536;
        }
        if (IntExtensionsKt.hasBits(i, IInstalld.FLAG_FORCE)) {
            i5 |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
        }
        if (!IntExtensionsKt.hasAnyBit(i5, 229376)) {
            if (IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 4)) {
                i5 |= 262144;
            }
            if (IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 8)) {
                i5 |= 524288;
            }
        }
        if (IntExtensionsKt.hasBits(i, 8)) {
            i5 |= 1048576;
        }
        if (IntExtensionsKt.hasBits(i, 65536)) {
            i5 |= 2097152;
        }
        if (IntExtensionsKt.hasBits(i, IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES)) {
            i5 |= 4194304;
        }
        return IntExtensionsKt.hasBits(i, 524288) ? i5 | 8388608 : i5;
    }
}
