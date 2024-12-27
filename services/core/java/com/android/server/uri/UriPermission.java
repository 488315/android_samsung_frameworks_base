package com.android.server.uri;

import android.os.Binder;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UriPermission {
    public ArraySet mReadOwners;
    public ArraySet mWriteOwners;
    public final String sourcePkg;
    public String stringName;
    public final String targetPkg;
    public final int targetUid;
    public final int targetUserId;
    public final GrantUri uri;
    public int modeFlags = 0;
    public int ownedModeFlags = 0;
    public int globalModeFlags = 0;
    public int persistableModeFlags = 0;
    public int persistedModeFlags = 0;
    public long persistedCreateTime = Long.MIN_VALUE;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PersistedTimeComparator implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Long.compare(
                    ((UriPermission) obj).persistedCreateTime,
                    ((UriPermission) obj2).persistedCreateTime);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Snapshot {
        public final long persistedCreateTime;
        public final int persistedModeFlags;
        public final String sourcePkg;
        public final String targetPkg;
        public final int targetUserId;
        public final GrantUri uri;

        public Snapshot(UriPermission uriPermission) {
            this.targetUserId = uriPermission.targetUserId;
            this.sourcePkg = uriPermission.sourcePkg;
            this.targetPkg = uriPermission.targetPkg;
            this.uri = uriPermission.uri;
            this.persistedModeFlags = uriPermission.persistedModeFlags;
            this.persistedCreateTime = uriPermission.persistedCreateTime;
        }
    }

    public UriPermission(String str, String str2, int i, GrantUri grantUri) {
        this.targetUserId = UserHandle.getUserId(i);
        this.sourcePkg = str;
        this.targetPkg = str2;
        this.targetUid = i;
        this.uri = grantUri;
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        printWriter, "      ", "targetUserId=");
        m.append(this.targetUserId);
        printWriter.print(m.toString());
        printWriter.print(" sourcePkg=" + this.sourcePkg);
        printWriter.println(" targetPkg=" + this.targetPkg);
        printWriter.print("      ");
        printWriter.print("mode=0x" + Integer.toHexString(this.modeFlags));
        printWriter.print(" owned=0x" + Integer.toHexString(this.ownedModeFlags));
        printWriter.print(" global=0x" + Integer.toHexString(this.globalModeFlags));
        printWriter.print(" persistable=0x" + Integer.toHexString(this.persistableModeFlags));
        printWriter.print(" persisted=0x" + Integer.toHexString(this.persistedModeFlags));
        if (this.persistedCreateTime != Long.MIN_VALUE) {
            printWriter.print(" persistedCreate=" + this.persistedCreateTime);
        }
        printWriter.println();
        if (this.mReadOwners != null) {
            printWriter.print("      ");
            printWriter.println("readOwners:");
            Iterator it = this.mReadOwners.iterator();
            while (it.hasNext()) {
                UriPermissionOwner uriPermissionOwner = (UriPermissionOwner) it.next();
                printWriter.print("      ");
                printWriter.println("  * " + uriPermissionOwner);
            }
        }
        if (this.mWriteOwners != null) {
            printWriter.print("      ");
            printWriter.println("writeOwners:");
            Iterator it2 = this.mWriteOwners.iterator();
            while (it2.hasNext()) {
                UriPermissionOwner uriPermissionOwner2 = (UriPermissionOwner) it2.next();
                printWriter.print("      ");
                printWriter.println("  * " + uriPermissionOwner2);
            }
        }
    }

    public final void initPersistedModes(int i, long j) {
        int i2 = i & 3;
        this.persistableModeFlags = i2;
        this.persistedModeFlags = i2;
        this.persistedCreateTime = j;
        updateModeFlags();
    }

    public final boolean releasePersistableModes(int i) {
        int i2 = this.persistedModeFlags;
        int i3 = (~(i & 3)) & i2;
        this.persistedModeFlags = i3;
        if (i3 == 0) {
            this.persistedCreateTime = Long.MIN_VALUE;
        }
        updateModeFlags();
        return this.persistedModeFlags != i2;
    }

    public final void removeReadOwner(UriPermissionOwner uriPermissionOwner) {
        ArraySet arraySet = this.mReadOwners;
        if (arraySet == null || !arraySet.remove(uriPermissionOwner)) {
            Slog.wtf("UriPermission", "Unknown read owner " + uriPermissionOwner + " in " + this);
            return;
        }
        if (this.mReadOwners.size() == 0) {
            this.mReadOwners = null;
            this.ownedModeFlags &= -2;
            updateModeFlags();
        }
    }

    public final void removeWriteOwner(UriPermissionOwner uriPermissionOwner) {
        ArraySet arraySet = this.mWriteOwners;
        if (arraySet == null || !arraySet.remove(uriPermissionOwner)) {
            Slog.wtf("UriPermission", "Unknown write owner " + uriPermissionOwner + " in " + this);
            return;
        }
        if (this.mWriteOwners.size() == 0) {
            this.mWriteOwners = null;
            this.ownedModeFlags &= -3;
            updateModeFlags();
        }
    }

    public final boolean revokeModes(int i, boolean z) {
        boolean z2 = (i & 64) != 0;
        int i2 = this.persistedModeFlags;
        if ((i & 1) != 0) {
            if (z2) {
                this.persistableModeFlags &= -2;
                this.persistedModeFlags = i2 & (-2);
            }
            this.globalModeFlags &= -2;
            ArraySet arraySet = this.mReadOwners;
            if (arraySet != null && z) {
                this.ownedModeFlags &= -2;
                Iterator it = arraySet.iterator();
                while (it.hasNext()) {
                    UriPermissionOwner uriPermissionOwner = (UriPermissionOwner) it.next();
                    if (uriPermissionOwner != null) {
                        synchronized (uriPermissionOwner) {
                            try {
                                ArraySet arraySet2 = uriPermissionOwner.mReadPerms;
                                if (arraySet2 != null) {
                                    arraySet2.remove(this);
                                    if (uriPermissionOwner.mReadPerms.isEmpty()) {
                                        uriPermissionOwner.mReadPerms = null;
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                }
                this.mReadOwners = null;
            }
        }
        if ((i & 2) != 0) {
            if (z2) {
                this.persistableModeFlags &= -3;
                this.persistedModeFlags &= -3;
            }
            this.globalModeFlags &= -3;
            ArraySet arraySet3 = this.mWriteOwners;
            if (arraySet3 != null && z) {
                this.ownedModeFlags &= -3;
                Iterator it2 = arraySet3.iterator();
                while (it2.hasNext()) {
                    UriPermissionOwner uriPermissionOwner2 = (UriPermissionOwner) it2.next();
                    if (uriPermissionOwner2 != null) {
                        synchronized (uriPermissionOwner2) {
                            try {
                                ArraySet arraySet4 = uriPermissionOwner2.mWritePerms;
                                if (arraySet4 != null) {
                                    arraySet4.remove(this);
                                    if (uriPermissionOwner2.mWritePerms.isEmpty()) {
                                        uriPermissionOwner2.mWritePerms = null;
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                }
                this.mWriteOwners = null;
            }
        }
        if (this.persistedModeFlags == 0) {
            this.persistedCreateTime = Long.MIN_VALUE;
        }
        updateModeFlags();
        return this.persistedModeFlags != i2;
    }

    public final boolean takePersistableModes(int i) {
        int i2 = i & 3;
        int i3 = this.persistableModeFlags & i2;
        if (i3 != i2) {
            StringBuilder sb = new StringBuilder("Requested flags 0x");
            BatteryService$$ExternalSyntheticOutline0.m(i2, sb, ", but only 0x");
            sb.append(Integer.toHexString(this.persistableModeFlags));
            sb.append(" are allowed");
            Slog.w("UriPermission", sb.toString());
            return false;
        }
        int i4 = this.persistedModeFlags;
        int i5 = i3 | i4;
        this.persistedModeFlags = i5;
        if (i5 != 0) {
            this.persistedCreateTime = System.currentTimeMillis();
        }
        updateModeFlags();
        return this.persistedModeFlags != i4;
    }

    public final String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "UriPermission{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(' ');
        m.append(this.uri);
        m.append('}');
        String sb = m.toString();
        this.stringName = sb;
        return sb;
    }

    public final void updateModeFlags() {
        int i = this.modeFlags;
        this.modeFlags = this.ownedModeFlags | this.globalModeFlags | this.persistedModeFlags;
        if (!Log.isLoggable("UriPermission", 2) || this.modeFlags == i) {
            return;
        }
        Slog.d(
                "UriPermission",
                "Permission for "
                        + this.targetPkg
                        + " to "
                        + this.uri
                        + " is changing from 0x"
                        + Integer.toHexString(i)
                        + " to 0x"
                        + Integer.toHexString(this.modeFlags)
                        + " via calling UID "
                        + Binder.getCallingUid()
                        + " PID "
                        + Binder.getCallingPid(),
                new Throwable());
    }
}
