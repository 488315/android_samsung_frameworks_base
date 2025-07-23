package android.os;

import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStatVfs;

/* loaded from: classes3.dex */
public class StatFs {
    private StructStatVfs mStat;

    public StatFs(String str) {
        this.mStat = doStat(str);
    }

    private static StructStatVfs doStat(String str) {
        try {
            return Os.statvfs(str);
        } catch (ErrnoException e) {
            throw new IllegalArgumentException("Invalid path: " + str, e);
        }
    }

    public void restat(String str) {
        this.mStat = doStat(str);
    }

    @Deprecated
    public int getBlockSize() {
        return (int) this.mStat.f_frsize;
    }

    public long getBlockSizeLong() {
        return this.mStat.f_frsize;
    }

    @Deprecated
    public int getBlockCount() {
        return (int) this.mStat.f_blocks;
    }

    public long getBlockCountLong() {
        return this.mStat.f_blocks;
    }

    @Deprecated
    public int getFreeBlocks() {
        return (int) this.mStat.f_bfree;
    }

    public long getFreeBlocksLong() {
        return this.mStat.f_bfree;
    }

    public long getFreeBytes() {
        return this.mStat.f_bfree * this.mStat.f_frsize;
    }

    @Deprecated
    public int getAvailableBlocks() {
        return (int) this.mStat.f_bavail;
    }

    public long getAvailableBlocksLong() {
        return this.mStat.f_bavail;
    }

    public long getAvailableBytes() {
        return this.mStat.f_bavail * this.mStat.f_frsize;
    }

    public long getTotalBytes() {
        return this.mStat.f_blocks * this.mStat.f_frsize;
    }

    public long getTotalFileNode() {
        return this.mStat.f_files;
    }

    public long getFreeFileNode() {
        return this.mStat.f_ffree;
    }
}
