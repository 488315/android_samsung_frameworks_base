package android.hardware.health.V2_0;

import android.os.HidlSupport;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DiskStats {
    public StorageAttribute attr;
    public long ioInFlight;
    public long ioInQueue;
    public long ioTicks;
    public long readMerges;
    public long readSectors;
    public long readTicks;
    public long reads;
    public long writeMerges;
    public long writeSectors;
    public long writeTicks;
    public long writes;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != DiskStats.class) {
            return false;
        }
        DiskStats diskStats = (DiskStats) obj;
        return this.reads == diskStats.reads
                && this.readMerges == diskStats.readMerges
                && this.readSectors == diskStats.readSectors
                && this.readTicks == diskStats.readTicks
                && this.writes == diskStats.writes
                && this.writeMerges == diskStats.writeMerges
                && this.writeSectors == diskStats.writeSectors
                && this.writeTicks == diskStats.writeTicks
                && this.ioInFlight == diskStats.ioInFlight
                && this.ioTicks == diskStats.ioTicks
                && this.ioInQueue == diskStats.ioInQueue
                && HidlSupport.deepEquals(this.attr, diskStats.attr);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.reads))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.readMerges))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.readSectors))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.readTicks))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.writes))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.writeMerges))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.writeSectors))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.writeTicks))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.ioInFlight))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.ioTicks))),
                Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.ioInQueue))),
                Integer.valueOf(HidlSupport.deepHashCode(this.attr)));
    }

    public final String toString() {
        return "{.reads = "
                + this.reads
                + ", .readMerges = "
                + this.readMerges
                + ", .readSectors = "
                + this.readSectors
                + ", .readTicks = "
                + this.readTicks
                + ", .writes = "
                + this.writes
                + ", .writeMerges = "
                + this.writeMerges
                + ", .writeSectors = "
                + this.writeSectors
                + ", .writeTicks = "
                + this.writeTicks
                + ", .ioInFlight = "
                + this.ioInFlight
                + ", .ioTicks = "
                + this.ioTicks
                + ", .ioInQueue = "
                + this.ioInQueue
                + ", .attr = "
                + this.attr
                + "}";
    }
}
