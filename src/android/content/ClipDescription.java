package android.content;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes.dex */
public class ClipDescription implements Parcelable {
    public static final int CLASSIFICATION_COMPLETE = 3;
    public static final int CLASSIFICATION_NOT_COMPLETE = 1;
    public static final int CLASSIFICATION_NOT_PERFORMED = 2;
    public static final Parcelable.Creator<ClipDescription> CREATOR = new Parcelable.Creator<ClipDescription>() { // from class: android.content.ClipDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClipDescription createFromParcel(Parcel parcel) {
            return new ClipDescription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClipDescription[] newArray(int i) {
            return new ClipDescription[i];
        }
    };
    public static final String EXTRA_ACTIVITY_OPTIONS = "android.intent.extra.ACTIVITY_OPTIONS";
    public static final String EXTRA_DRAG_AND_DROP_CLIENT = "com.samsung.android.intent.extra.DRAG_AND_DROP_CLIENT";
    public static final String EXTRA_DRAG_AND_DROP_IGNORE_LEFT_EDGE = "com.samsung.android.content.clipdescription.extra.IGNORE_LEFT_EDGE";
    public static final String EXTRA_DRAG_AND_DROP_IGNORE_RIGHT_EDGE = "com.samsung.android.content.clipdescription.extra.IGNORE_RIGHT_EDGE";
    public static final String EXTRA_DRAG_AND_DROP_REQUESTER = "com.samsung.android.intent.extra.DRAG_AND_DROP_REQUESTER";
    public static final String EXTRA_DRAG_FROM_RECENT = "com.samsung.android.content.clipdescription.extra.DRAG_FROM_RECENT";
    public static final String EXTRA_HIDE_DRAG_SOURCE_TASK_ID = "android.intent.extra.HIDE_DRAG_SOURCE_TASK_ID";
    public static final String EXTRA_IS_REMOTE_DEVICE = "android.content.extra.IS_REMOTE_DEVICE";
    public static final String EXTRA_IS_SENSITIVE = "android.content.extra.IS_SENSITIVE";
    public static final String EXTRA_LOGGING_INSTANCE_ID = "android.intent.extra.LOGGING_INSTANCE_ID";
    public static final String EXTRA_PENDING_INTENT = "android.intent.extra.PENDING_INTENT";
    public static final String MIMETYPE_APPLICATION_ACTIVITY = "application/vnd.android.activity";
    public static final String MIMETYPE_APPLICATION_SHORTCUT = "application/vnd.android.shortcut";
    public static final String MIMETYPE_APPLICATION_TASK = "application/vnd.android.task";
    public static final String MIMETYPE_TEXT_HTML = "text/html";
    public static final String MIMETYPE_TEXT_INTENT = "text/vnd.android.intent";
    public static final String MIMETYPE_TEXT_PLAIN = "text/plain";
    public static final String MIMETYPE_TEXT_URILIST = "text/uri-list";
    public static final String MIMETYPE_UNKNOWN = "application/octet-stream";
    private int mClassificationStatus;
    private final ArrayMap<String, Float> mEntityConfidence;
    private PersistableBundle mExtras;
    private boolean mIsStyledText;
    final CharSequence mLabel;
    private final ArrayList<String> mMimeTypes;
    private long mTimeStamp;

    @Retention(RetentionPolicy.SOURCE)
    @interface ClassificationStatus {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ClipDescription(CharSequence charSequence, String[] strArr) {
        this.mEntityConfidence = new ArrayMap<>();
        this.mClassificationStatus = 1;
        if (strArr == null) {
            throw new NullPointerException("mimeTypes is null");
        }
        this.mLabel = charSequence;
        this.mMimeTypes = new ArrayList<>(Arrays.asList(strArr));
    }

    public ClipDescription(ClipDescription clipDescription) {
        this.mEntityConfidence = new ArrayMap<>();
        this.mClassificationStatus = 1;
        this.mLabel = clipDescription.mLabel;
        this.mMimeTypes = new ArrayList<>(clipDescription.mMimeTypes);
        this.mTimeStamp = clipDescription.mTimeStamp;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean compareMimeTypes(String str, String str2) {
        int length = str2.length();
        if (length == 3 && str2.equals("*/*")) {
            return true;
        }
        int iIndexOf = str2.indexOf(47);
        if (iIndexOf > 0) {
            if (length == iIndexOf + 2) {
                int i = iIndexOf + 1;
                if (str2.charAt(i) == '*') {
                    if (str2.regionMatches(0, str, 0, i)) {
                        return true;
                    }
                } else if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setTimestamp(long j) {
        this.mTimeStamp = j;
    }

    public long getTimestamp() {
        return this.mTimeStamp;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public boolean hasMimeType(String str) {
        int size = this.mMimeTypes.size();
        for (int i = 0; i < size; i++) {
            if (compareMimeTypes(this.mMimeTypes.get(i), str)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMimeType(String[] strArr) {
        for (String str : strArr) {
            if (hasMimeType(str)) {
                return true;
            }
        }
        return false;
    }

    public String[] filterMimeTypes(String str) {
        int size = this.mMimeTypes.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            if (compareMimeTypes(this.mMimeTypes.get(i), str)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(this.mMimeTypes.get(i));
            }
        }
        if (arrayList == null) {
            return null;
        }
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        return strArr;
    }

    public int getMimeTypeCount() {
        return this.mMimeTypes.size();
    }

    public String getMimeType(int i) {
        return this.mMimeTypes.get(i);
    }

    void addMimeTypes(String[] strArr) {
        for (int i = 0; i != strArr.length; i++) {
            String str = strArr[i];
            if (!this.mMimeTypes.contains(str)) {
                this.mMimeTypes.add(str);
            }
        }
    }

    public PersistableBundle getExtras() {
        return this.mExtras;
    }

    public void setExtras(PersistableBundle persistableBundle) {
        this.mExtras = new PersistableBundle(persistableBundle);
    }

    public void validate() {
        ArrayList<String> arrayList = this.mMimeTypes;
        if (arrayList == null) {
            throw new NullPointerException("null mime types");
        }
        int size = arrayList.size();
        if (size <= 0) {
            throw new IllegalArgumentException("must have at least 1 mime type");
        }
        for (int i = 0; i < size; i++) {
            if (this.mMimeTypes.get(i) == null) {
                throw new NullPointerException("mime type at " + i + " is null");
            }
        }
    }

    public boolean isStyledText() {
        return this.mIsStyledText;
    }

    void setIsStyledText(boolean z) {
        this.mIsStyledText = z;
    }

    public void setClassificationStatus(int i) {
        this.mClassificationStatus = i;
    }

    public float getConfidenceScore(String str) {
        if (this.mClassificationStatus != 3) {
            throw new IllegalStateException("Classification not complete");
        }
        return this.mEntityConfidence.getOrDefault(str, Float.valueOf(0.0f)).floatValue();
    }

    public int getClassificationStatus() {
        return this.mClassificationStatus;
    }

    public void setConfidenceScores(Map<String, Float> map) {
        this.mEntityConfidence.clear();
        this.mEntityConfidence.putAll(map);
        this.mClassificationStatus = 3;
    }

    public void setDragFromRecent(boolean z) {
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        this.mExtras.putBoolean(EXTRA_DRAG_FROM_RECENT, z);
    }

    public boolean isDragFromRecent() {
        try {
            PersistableBundle persistableBundle = this.mExtras;
            if (persistableBundle != null) {
                if (persistableBundle.getBoolean(EXTRA_DRAG_FROM_RECENT)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("ClipDescription { ");
        toShortString(sb, true);
        sb.append(" }");
        return sb.toString();
    }

    public boolean toShortString(StringBuilder sb, boolean z) {
        boolean shortStringTypesOnly = toShortStringTypesOnly(sb);
        boolean z2 = !shortStringTypesOnly;
        boolean z3 = false;
        if (this.mLabel != null) {
            if (shortStringTypesOnly) {
                sb.append(' ');
            }
            if (z) {
                sb.append("hasLabel(");
                sb.append(this.mLabel.length());
                sb.append(')');
            } else {
                sb.append('\"');
                sb.append(this.mLabel);
                sb.append('\"');
            }
            z2 = false;
        }
        if (this.mExtras != null) {
            if (!z2) {
                sb.append(' ');
            }
            if (z) {
                if (this.mExtras.isParcelled()) {
                    sb.append("hasExtras");
                } else {
                    sb.append("hasExtras(");
                    sb.append(this.mExtras.size());
                    sb.append(')');
                }
            } else {
                sb.append(this.mExtras.toString());
            }
            z2 = false;
        }
        if (this.mTimeStamp > 0) {
            if (!z2) {
                sb.append(' ');
            }
            sb.append('<');
            sb.append(TimeUtils.logTimeOfDay(this.mTimeStamp));
            sb.append('>');
        } else {
            z3 = z2;
        }
        return !z3;
    }

    public boolean toShortStringTypesOnly(StringBuilder sb) {
        int size = this.mMimeTypes.size();
        boolean z = true;
        int i = 0;
        while (i < size) {
            if (!z) {
                sb.append(' ');
            }
            sb.append(this.mMimeTypes.get(i));
            i++;
            z = false;
        }
        return !z;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        int size = this.mMimeTypes.size();
        for (int i = 0; i < size; i++) {
            protoOutputStream.write(2237677961217L, this.mMimeTypes.get(i));
        }
        CharSequence charSequence = this.mLabel;
        if (charSequence != null) {
            protoOutputStream.write(1138166333442L, charSequence.toString());
        }
        PersistableBundle persistableBundle = this.mExtras;
        if (persistableBundle != null) {
            persistableBundle.dumpDebug(protoOutputStream, 1146756268035L);
        }
        long j2 = this.mTimeStamp;
        if (j2 > 0) {
            protoOutputStream.write(1112396529668L, j2);
        }
        protoOutputStream.end(jStart);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        TextUtils.writeToParcel(this.mLabel, parcel, i);
        parcel.writeStringList(this.mMimeTypes);
        parcel.writePersistableBundle(this.mExtras);
        parcel.writeLong(this.mTimeStamp);
        parcel.writeBoolean(this.mIsStyledText);
        parcel.writeInt(this.mClassificationStatus);
        parcel.writeBundle(confidencesToBundle());
    }

    private Bundle confidencesToBundle() {
        Bundle bundle = new Bundle();
        int size = this.mEntityConfidence.size();
        for (int i = 0; i < size; i++) {
            bundle.putFloat(this.mEntityConfidence.keyAt(i), this.mEntityConfidence.valueAt(i).floatValue());
        }
        return bundle;
    }

    private void readBundleToConfidences(Bundle bundle) {
        for (String str : bundle.keySet()) {
            this.mEntityConfidence.put(str, Float.valueOf(bundle.getFloat(str)));
        }
    }

    ClipDescription(Parcel parcel) {
        this.mEntityConfidence = new ArrayMap<>();
        this.mClassificationStatus = 1;
        this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mMimeTypes = parcel.createStringArrayList();
        this.mExtras = parcel.readPersistableBundle();
        this.mTimeStamp = parcel.readLong();
        this.mIsStyledText = parcel.readBoolean();
        this.mClassificationStatus = parcel.readInt();
        readBundleToConfidences(parcel.readBundle());
    }

    public ArrayList<String> semGetMimeTypes() {
        if (this.mMimeTypes == null) {
            Log.w("ClipDescription", "get mimetypes - null");
        }
        return this.mMimeTypes;
    }
}
