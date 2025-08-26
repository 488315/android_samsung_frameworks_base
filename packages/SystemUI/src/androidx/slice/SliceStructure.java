package androidx.slice;

import android.net.Uri;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SliceStructure {
    public final String mStructure;
    public final Uri mUri;

    public SliceStructure(Slice slice) {
        StringBuilder sb = new StringBuilder();
        getStructure(slice, sb);
        this.mStructure = sb.toString();
        this.mUri = Uri.parse(slice.mUri);
    }

    public static void getStructure(Slice slice, StringBuilder sb) {
        sb.append("s{");
        Iterator it = Arrays.asList(slice.mItems).iterator();
        while (it.hasNext()) {
            getStructure((SliceItem) it.next(), sb);
        }
        sb.append("}");
    }

    public final boolean equals(Object obj) {
        if (obj instanceof SliceStructure) {
            return this.mStructure.equals(((SliceStructure) obj).mStructure);
        }
        return false;
    }

    public final int hashCode() {
        return this.mStructure.hashCode();
    }

    public SliceStructure(SliceItem sliceItem) {
        StringBuilder sb = new StringBuilder();
        getStructure(sliceItem, sb);
        this.mStructure = sb.toString();
        if (!"action".equals(sliceItem.mFormat) && !"slice".equals(sliceItem.mFormat)) {
            this.mUri = null;
        } else {
            this.mUri = Uri.parse(sliceItem.getSlice().mUri);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void getStructure(SliceItem sliceItem, StringBuilder sb) {
        char c;
        String str = sliceItem.mFormat;
        switch (str.hashCode()) {
            case -1422950858:
                if (!str.equals("action")) {
                    c = 65535;
                    break;
                } else {
                    c = 1;
                    break;
                }
            case -1377881982:
                if (str.equals("bundle")) {
                    c = 7;
                    break;
                }
                break;
            case 104431:
                if (str.equals("int")) {
                    c = 4;
                    break;
                }
                break;
            case 3327612:
                if (str.equals("long")) {
                    c = 5;
                    break;
                }
                break;
            case 3556653:
                if (str.equals("text")) {
                    c = 2;
                    break;
                }
                break;
            case 100313435:
                if (str.equals("image")) {
                    c = 3;
                    break;
                }
                break;
            case 100358090:
                if (str.equals("input")) {
                    c = 6;
                    break;
                }
                break;
            case 109526418:
                if (str.equals("slice")) {
                    c = 0;
                    break;
                }
                break;
        }
        if (c == 0) {
            getStructure(sliceItem.getSlice(), sb);
            return;
        }
        if (c == 1) {
            sb.append('a');
            if ("range".equals(sliceItem.mSubType)) {
                sb.append('r');
            }
            getStructure(sliceItem.getSlice(), sb);
            return;
        }
        if (c == 2) {
            sb.append('t');
        } else {
            if (c != 3) {
                return;
            }
            sb.append('i');
        }
    }
}
