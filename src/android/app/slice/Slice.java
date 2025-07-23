package android.app.slice;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.ArrayUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Deprecated
/* loaded from: classes.dex */
public final class Slice implements Parcelable {
    public static final Parcelable.Creator<Slice> CREATOR = new Parcelable.Creator<Slice>() { // from class: android.app.slice.Slice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Slice createFromParcel(Parcel parcel) {
            return new Slice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Slice[] newArray(int i) {
            return new Slice[i];
        }
    };
    public static final String EXTRA_RANGE_VALUE = "android.app.slice.extra.RANGE_VALUE";
    public static final String EXTRA_TOGGLE_STATE = "android.app.slice.extra.TOGGLE_STATE";
    public static final String HINT_ACTIONS = "actions";
    public static final String HINT_CALLER_NEEDED = "caller_needed";
    public static final String HINT_ERROR = "error";
    public static final String HINT_HORIZONTAL = "horizontal";
    public static final String HINT_KEYWORDS = "keywords";
    public static final String HINT_LARGE = "large";
    public static final String HINT_LAST_UPDATED = "last_updated";
    public static final String HINT_LIST = "list";
    public static final String HINT_LIST_ITEM = "list_item";
    public static final String HINT_NO_TINT = "no_tint";
    public static final String HINT_PARTIAL = "partial";
    public static final String HINT_PERMISSION_REQUEST = "permission_request";
    public static final String HINT_SEE_MORE = "see_more";
    public static final String HINT_SELECTED = "selected";
    public static final String HINT_SHORTCUT = "shortcut";
    public static final String HINT_SUMMARY = "summary";
    public static final String HINT_TITLE = "title";
    public static final String HINT_TOGGLE = "toggle";
    public static final String HINT_TTL = "ttl";
    public static final String SUBTYPE_COLOR = "color";
    public static final String SUBTYPE_CONTENT_DESCRIPTION = "content_description";
    public static final String SUBTYPE_LAYOUT_DIRECTION = "layout_direction";
    public static final String SUBTYPE_MAX = "max";
    public static final String SUBTYPE_MESSAGE = "message";
    public static final String SUBTYPE_MILLIS = "millis";
    public static final String SUBTYPE_PRIORITY = "priority";
    public static final String SUBTYPE_RANGE = "range";
    public static final String SUBTYPE_SOURCE = "source";
    public static final String SUBTYPE_TOGGLE = "toggle";
    public static final String SUBTYPE_VALUE = "value";
    private final String[] mHints;
    private final SliceItem[] mItems;
    private SliceSpec mSpec;
    private Uri mUri;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SliceHint {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SliceSubtype {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Slice(ArrayList<SliceItem> arrayList, String[] strArr, Uri uri, SliceSpec sliceSpec) {
        this.mHints = strArr;
        this.mItems = (SliceItem[]) arrayList.toArray(new SliceItem[arrayList.size()]);
        this.mUri = uri;
        this.mSpec = sliceSpec;
    }

    protected Slice(Parcel parcel) {
        this.mHints = parcel.readStringArray();
        this.mItems = (SliceItem[]) parcel.createTypedArray(SliceItem.CREATOR);
        this.mUri = Uri.CREATOR.createFromParcel(parcel);
        this.mSpec = (SliceSpec) parcel.readTypedObject(SliceSpec.CREATOR);
    }

    public SliceSpec getSpec() {
        return this.mSpec;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public List<SliceItem> getItems() {
        return Arrays.asList(this.mItems);
    }

    public List<String> getHints() {
        return Arrays.asList(this.mHints);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(this.mHints);
        parcel.writeTypedArray(this.mItems, i);
        this.mUri.writeToParcel(parcel, 0);
        parcel.writeTypedObject(this.mSpec, i);
    }

    public boolean hasHint(String str) {
        return ArrayUtils.contains(this.mHints, str);
    }

    public boolean isCallerNeeded() {
        return hasHint(HINT_CALLER_NEEDED);
    }

    @Deprecated
    public static class Builder {
        private SliceSpec mSpec;
        private final Uri mUri;
        private ArrayList<SliceItem> mItems = new ArrayList<>();
        private ArrayList<String> mHints = new ArrayList<>();

        public Builder(Uri uri, SliceSpec sliceSpec) {
            this.mUri = uri;
            this.mSpec = sliceSpec;
        }

        public Builder(Builder builder) {
            this.mUri = builder.mUri.buildUpon().appendPath("_gen").appendPath(String.valueOf(this.mItems.size())).build();
        }

        public Builder setCallerNeeded(boolean z) {
            if (z) {
                this.mHints.add(Slice.HINT_CALLER_NEEDED);
                return this;
            }
            this.mHints.remove(Slice.HINT_CALLER_NEEDED);
            return this;
        }

        public Builder addHints(List<String> list) {
            this.mHints.addAll(list);
            return this;
        }

        public Builder addSubSlice(Slice slice, String str) {
            Objects.requireNonNull(slice);
            this.mItems.add(new SliceItem(slice, "slice", str, (String[]) slice.getHints().toArray(new String[slice.getHints().size()])));
            return this;
        }

        public Builder addAction(PendingIntent pendingIntent, Slice slice, String str) {
            Objects.requireNonNull(pendingIntent);
            Objects.requireNonNull(slice);
            List<String> hints = slice.getHints();
            slice.mSpec = null;
            this.mItems.add(new SliceItem(pendingIntent, slice, "action", str, (String[]) hints.toArray(new String[hints.size()])));
            return this;
        }

        public Builder addText(CharSequence charSequence, String str, List<String> list) {
            this.mItems.add(new SliceItem(charSequence, "text", str, list));
            return this;
        }

        public Builder addIcon(Icon icon, String str, List<String> list) {
            Objects.requireNonNull(icon);
            this.mItems.add(new SliceItem(icon, "image", str, list));
            return this;
        }

        public Builder addRemoteInput(RemoteInput remoteInput, String str, List<String> list) {
            Objects.requireNonNull(remoteInput);
            this.mItems.add(new SliceItem(remoteInput, "input", str, list));
            return this;
        }

        public Builder addInt(int i, String str, List<String> list) {
            this.mItems.add(new SliceItem(Integer.valueOf(i), "int", str, list));
            return this;
        }

        public Builder addLong(long j, String str, List<String> list) {
            this.mItems.add(new SliceItem(Long.valueOf(j), SliceItem.FORMAT_LONG, str, (String[]) list.toArray(new String[list.size()])));
            return this;
        }

        public Builder addBundle(Bundle bundle, String str, List<String> list) {
            Objects.requireNonNull(bundle);
            this.mItems.add(new SliceItem(bundle, SliceItem.FORMAT_BUNDLE, str, list));
            return this;
        }

        public Slice build() {
            ArrayList<SliceItem> arrayList = this.mItems;
            ArrayList<String> arrayList2 = this.mHints;
            return new Slice(arrayList, (String[]) arrayList2.toArray(new String[arrayList2.size()]), this.mUri, this.mSpec);
        }
    }

    public String toString() {
        return toString("");
    }

    private String toString(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mItems.length; i++) {
            sb.append(str);
            if (Objects.equals(this.mItems[i].getFormat(), "slice")) {
                sb.append("slice:\n");
                sb.append(this.mItems[i].getSlice().toString(str + "   "));
            } else if (Objects.equals(this.mItems[i].getFormat(), "text")) {
                sb.append("text: ");
                sb.append(this.mItems[i].getText());
                sb.append(ShaderAssembler.NEWLINE);
            } else {
                sb.append(this.mItems[i].getFormat());
                sb.append(ShaderAssembler.NEWLINE);
            }
        }
        return sb.toString();
    }
}
