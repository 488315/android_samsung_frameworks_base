package android.hardware.broadcastradio.V2_0;

import android.os.HidlSupport;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Announcement {
    public ProgramSelector selector;
    public byte type;
    public ArrayList vendorInfo;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Announcement.class) {
            return false;
        }
        Announcement announcement = (Announcement) obj;
        return HidlSupport.deepEquals(this.selector, announcement.selector)
                && this.type == announcement.type
                && HidlSupport.deepEquals(this.vendorInfo, announcement.vendorInfo);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.selector)),
                Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.type))),
                Integer.valueOf(HidlSupport.deepHashCode(this.vendorInfo)));
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("{.selector = ");
        sb.append(this.selector);
        sb.append(", .type = ");
        byte b = this.type;
        if (b == 1) {
            str = "EMERGENCY";
        } else if (b == 2) {
            str = "WARNING";
        } else if (b == 3) {
            str = "TRAFFIC";
        } else if (b == 4) {
            str = "WEATHER";
        } else if (b == 5) {
            str = "NEWS";
        } else if (b == 6) {
            str = "EVENT";
        } else if (b == 7) {
            str = "SPORT";
        } else if (b == 8) {
            str = "MISC";
        } else {
            str = "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }
        sb.append(str);
        sb.append(", .vendorInfo = ");
        sb.append(this.vendorInfo);
        sb.append("}");
        return sb.toString();
    }
}
