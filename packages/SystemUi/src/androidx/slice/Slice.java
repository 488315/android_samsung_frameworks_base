package androidx.slice;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.core.graphics.drawable.IconCompat;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Slice extends CustomVersionedParcelable {
    public static final String[] NO_HINTS = new String[0];
    public static final SliceItem[] NO_ITEMS = new SliceItem[0];
    public String[] mHints;
    public SliceItem[] mItems;
    public SliceSpec mSpec;
    public String mUri;

    public Slice(ArrayList<SliceItem> arrayList, String[] strArr, Uri uri, SliceSpec sliceSpec) {
        this.mSpec = null;
        this.mItems = NO_ITEMS;
        this.mUri = null;
        this.mHints = strArr;
        this.mItems = (SliceItem[]) arrayList.toArray(new SliceItem[arrayList.size()]);
        this.mUri = uri.toString();
        this.mSpec = sliceSpec;
    }

    public static void appendHints(StringBuilder sb, String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return;
        }
        sb.append('(');
        int length = strArr.length - 1;
        for (int i = 0; i < length; i++) {
            sb.append(strArr[i]);
            sb.append(", ");
        }
        sb.append(strArr[length]);
        sb.append(")");
    }

    public static boolean isValidIcon(IconCompat iconCompat) {
        if (iconCompat == null) {
            return false;
        }
        if (iconCompat.mType != 2 || iconCompat.getResId() != 0) {
            return true;
        }
        throw new IllegalArgumentException("Failed to add icon, invalid resource id: " + iconCompat.getResId());
    }

    public final List getItems() {
        return Arrays.asList(this.mItems);
    }

    public final Uri getUri() {
        return Uri.parse(this.mUri);
    }

    public final String toString(String str) {
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, "Slice ");
        String[] strArr = this.mHints;
        if (strArr.length > 0) {
            appendHints(m2m, strArr);
            m2m.append(' ');
        }
        m2m.append('[');
        m2m.append(this.mUri);
        m2m.append("] {\n");
        String str2 = str + "  ";
        int i = 0;
        while (true) {
            SliceItem[] sliceItemArr = this.mItems;
            if (i >= sliceItemArr.length) {
                m2m.append(str);
                m2m.append('}');
                return m2m.toString();
            }
            m2m.append(sliceItemArr[i].toString(str2));
            i++;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public int mChildId;
        public SliceSpec mSpec;
        public final Uri mUri;
        public final ArrayList mItems = new ArrayList();
        public final ArrayList mHints = new ArrayList();

        public Builder(Uri uri) {
            this.mUri = uri;
        }

        public final void addAction(PendingIntent pendingIntent, Slice slice, String str) {
            pendingIntent.getClass();
            slice.getClass();
            this.mItems.add(new SliceItem(pendingIntent, slice, "action", str, slice.mHints));
        }

        public final void addHints(String... strArr) {
            this.mHints.addAll(Arrays.asList(strArr));
        }

        public final void addIcon(IconCompat iconCompat, String str, String... strArr) {
            iconCompat.getClass();
            if (Slice.isValidIcon(iconCompat)) {
                this.mItems.add(new SliceItem(iconCompat, "image", str, strArr));
            }
        }

        public final void addInt(int i, String str, String... strArr) {
            this.mItems.add(new SliceItem(Integer.valueOf(i), "int", str, strArr));
        }

        public final void addItem(SliceItem sliceItem) {
            this.mItems.add(sliceItem);
        }

        public final void addLong(long j, String str, String... strArr) {
            this.mItems.add(new SliceItem(Long.valueOf(j), "long", str, strArr));
        }

        public final void addSubSlice(Slice slice, String str) {
            slice.getClass();
            this.mItems.add(new SliceItem(slice, "slice", str, slice.mHints));
        }

        public final void addText(CharSequence charSequence, String str, String... strArr) {
            this.mItems.add(new SliceItem(charSequence, "text", str, strArr));
        }

        public final void addTimestamp(long j, String str, String... strArr) {
            this.mItems.add(new SliceItem(Long.valueOf(j), "long", str, strArr));
        }

        public final Slice build() {
            ArrayList arrayList = this.mHints;
            return new Slice(this.mItems, (String[]) arrayList.toArray(new String[arrayList.size()]), this.mUri, this.mSpec);
        }

        public Builder(Builder builder) {
            Uri.Builder appendPath = builder.mUri.buildUpon().appendPath("_gen");
            int i = builder.mChildId;
            builder.mChildId = i + 1;
            this.mUri = appendPath.appendPath(String.valueOf(i)).build();
        }
    }

    public Slice() {
        this.mSpec = null;
        this.mItems = NO_ITEMS;
        this.mHints = NO_HINTS;
        this.mUri = null;
    }

    public Slice(Bundle bundle) {
        this.mSpec = null;
        this.mItems = NO_ITEMS;
        this.mHints = NO_HINTS;
        this.mUri = null;
        this.mHints = bundle.getStringArray("hints");
        Parcelable[] parcelableArray = bundle.getParcelableArray("items");
        this.mItems = new SliceItem[parcelableArray.length];
        int i = 0;
        while (true) {
            SliceItem[] sliceItemArr = this.mItems;
            if (i >= sliceItemArr.length) {
                break;
            }
            Parcelable parcelable = parcelableArray[i];
            if (parcelable instanceof Bundle) {
                sliceItemArr[i] = new SliceItem((Bundle) parcelable);
            }
            i++;
        }
        this.mUri = bundle.getParcelable("uri").toString();
        this.mSpec = bundle.containsKey("type") ? new SliceSpec(bundle.getString("type"), bundle.getInt("revision")) : null;
    }

    public final String toString() {
        return toString("");
    }
}
