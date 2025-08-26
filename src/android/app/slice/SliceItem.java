package android.app.slice;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.RemoteViews;
import com.android.internal.util.ArrayUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

@Deprecated
/* loaded from: classes.dex */
public final class SliceItem implements Parcelable {
    public static final Parcelable.Creator<SliceItem> CREATOR = new Parcelable.Creator<SliceItem>() { // from class: android.app.slice.SliceItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SliceItem createFromParcel(Parcel parcel) {
            return new SliceItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SliceItem[] newArray(int i) {
            return new SliceItem[i];
        }
    };
    public static final String FORMAT_ACTION = "action";
    public static final String FORMAT_BUNDLE = "bundle";
    public static final String FORMAT_IMAGE = "image";
    public static final String FORMAT_INT = "int";
    public static final String FORMAT_LONG = "long";
    public static final String FORMAT_REMOTE_INPUT = "input";
    public static final String FORMAT_SLICE = "slice";
    public static final String FORMAT_TEXT = "text";
    private static final String TAG = "SliceItem";
    private final String mFormat;
    protected String[] mHints;
    private final Object mObj;
    private final String mSubType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SliceType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SliceItem(Object obj, String str, String str2, List<String> list) {
        this(obj, str, str2, (String[]) list.toArray(new String[list.size()]));
    }

    public SliceItem(Object obj, String str, String str2, String[] strArr) {
        this.mHints = strArr;
        this.mFormat = str;
        this.mSubType = str2;
        this.mObj = obj;
    }

    public SliceItem(PendingIntent pendingIntent, Slice slice, String str, String str2, String[] strArr) {
        this(new Pair(pendingIntent, slice), str, str2, strArr);
    }

    public List<String> getHints() {
        return Arrays.asList(this.mHints);
    }

    public String getFormat() {
        return this.mFormat;
    }

    public String getSubType() {
        return this.mSubType;
    }

    public CharSequence getText() {
        return (CharSequence) this.mObj;
    }

    public Bundle getBundle() {
        return (Bundle) this.mObj;
    }

    public Icon getIcon() {
        return (Icon) this.mObj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PendingIntent getAction() {
        return (PendingIntent) ((Pair) this.mObj).first;
    }

    public RemoteViews getRemoteView() {
        return (RemoteViews) this.mObj;
    }

    public RemoteInput getRemoteInput() {
        return (RemoteInput) this.mObj;
    }

    public int getInt() {
        return ((Integer) this.mObj).intValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Slice getSlice() {
        if ("action".equals(getFormat())) {
            return (Slice) ((Pair) this.mObj).second;
        }
        return (Slice) this.mObj;
    }

    public long getLong() {
        return ((Long) this.mObj).longValue();
    }

    public boolean hasHint(String str) {
        return ArrayUtils.contains(this.mHints, str);
    }

    public SliceItem(Parcel parcel) {
        this.mHints = parcel.readStringArray();
        String string = parcel.readString();
        this.mFormat = string;
        this.mSubType = parcel.readString();
        this.mObj = readObj(string, parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(this.mHints);
        parcel.writeString(this.mFormat);
        parcel.writeString(this.mSubType);
        writeObj(parcel, i, this.mObj, this.mFormat);
    }

    public boolean hasHints(String[] strArr) {
        if (strArr == null) {
            return true;
        }
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str) && !ArrayUtils.contains(this.mHints, str)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyHints(String[] strArr) {
        if (strArr == null) {
            return false;
        }
        for (String str : strArr) {
            if (ArrayUtils.contains(this.mHints, str)) {
                return true;
            }
        }
        return false;
    }

    private static String getBaseType(String str) {
        int iIndexOf = str.indexOf(47);
        return iIndexOf >= 0 ? str.substring(0, iIndexOf) : str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void writeObj(Parcel parcel, int i, Object obj, String str) {
        String baseType = getBaseType(str);
        baseType.hashCode();
        switch (baseType) {
            case "action":
                Pair pair = (Pair) obj;
                ((PendingIntent) pair.first).writeToParcel(parcel, i);
                ((Slice) pair.second).writeToParcel(parcel, i);
                break;
            case "bundle":
            case "image":
            case "input":
            case "slice":
                ((Parcelable) obj).writeToParcel(parcel, i);
                break;
            case "int":
                parcel.writeInt(((Integer) obj).intValue());
                break;
            case "long":
                parcel.writeLong(((Long) obj).longValue());
                break;
            case "text":
                TextUtils.writeToParcel((CharSequence) obj, parcel, i);
                break;
        }
    }

    private static Object readObj(String str, Parcel parcel) {
        String baseType = getBaseType(str);
        baseType.hashCode();
        switch (baseType) {
            case "action":
                return new Pair(PendingIntent.CREATOR.createFromParcel(parcel), Slice.CREATOR.createFromParcel(parcel));
            case "bundle":
                return Bundle.CREATOR.createFromParcel(parcel);
            case "int":
                return Integer.valueOf(parcel.readInt());
            case "long":
                return Long.valueOf(parcel.readLong());
            case "text":
                return TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            case "image":
                return Icon.CREATOR.createFromParcel(parcel);
            case "input":
                return RemoteInput.CREATOR.createFromParcel(parcel);
            case "slice":
                return Slice.CREATOR.createFromParcel(parcel);
            default:
                throw new RuntimeException("Unsupported type " + str);
        }
    }
}
