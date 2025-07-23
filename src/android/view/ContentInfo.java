package android.view;

import android.app.slice.Slice;
import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import android.view.inputmethod.InputContentInfo;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public final class ContentInfo implements Parcelable {
    public static final Parcelable.Creator<ContentInfo> CREATOR = new Parcelable.Creator<ContentInfo>() { // from class: android.view.ContentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentInfo createFromParcel(Parcel parcel) {
            ClipData createFromParcel = ClipData.CREATOR.createFromParcel(parcel);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            Uri createFromParcel2 = Uri.CREATOR.createFromParcel(parcel);
            Bundle readBundle = parcel.readBundle();
            return new Builder(createFromParcel, readInt).setFlags(readInt2).setLinkUri(createFromParcel2).setExtras(readBundle).setInputContentInfo(parcel.readInt() != 0 ? InputContentInfo.CREATOR.createFromParcel(parcel) : null).setDragAndDropPermissions(parcel.readInt() != 0 ? DragAndDropPermissions.CREATOR.createFromParcel(parcel) : null).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentInfo[] newArray(int i) {
            return new ContentInfo[i];
        }
    };
    public static final int FLAG_CONVERT_TO_PLAIN_TEXT = 1;
    public static final int SOURCE_APP = 0;
    public static final int SOURCE_AUTOFILL = 4;
    public static final int SOURCE_CLIPBOARD = 1;
    public static final int SOURCE_DRAG_AND_DROP = 3;
    public static final int SOURCE_INPUT_METHOD = 2;
    public static final int SOURCE_PROCESS_TEXT = 5;
    private final ClipData mClip;
    private final DragAndDropPermissions mDragAndDropPermissions;
    private final Bundle mExtras;
    private final int mFlags;
    private final InputContentInfo mInputContentInfo;
    private final Uri mLinkUri;
    private final int mSource;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static String sourceToString(int i) {
        if (i == 0) {
            return "SOURCE_APP";
        }
        if (i == 1) {
            return "SOURCE_CLIPBOARD";
        }
        if (i == 2) {
            return "SOURCE_INPUT_METHOD";
        }
        if (i == 3) {
            return "SOURCE_DRAG_AND_DROP";
        }
        if (i == 4) {
            return "SOURCE_AUTOFILL";
        }
        if (i == 5) {
            return "SOURCE_PROCESS_TEXT";
        }
        return String.valueOf(i);
    }

    static String flagsToString(int i) {
        if ((i & 1) != 0) {
            return "FLAG_CONVERT_TO_PLAIN_TEXT";
        }
        return String.valueOf(i);
    }

    private ContentInfo(Builder builder) {
        this.mClip = (ClipData) Objects.requireNonNull(builder.mClip);
        this.mSource = Preconditions.checkArgumentInRange(builder.mSource, 0, 5, Slice.SUBTYPE_SOURCE);
        this.mFlags = Preconditions.checkFlagsArgument(builder.mFlags, 1);
        this.mLinkUri = builder.mLinkUri;
        this.mExtras = builder.mExtras;
        this.mInputContentInfo = builder.mInputContentInfo;
        this.mDragAndDropPermissions = builder.mDragAndDropPermissions;
    }

    public void releasePermissions() {
        InputContentInfo inputContentInfo = this.mInputContentInfo;
        if (inputContentInfo != null) {
            inputContentInfo.releasePermission();
        }
        DragAndDropPermissions dragAndDropPermissions = this.mDragAndDropPermissions;
        if (dragAndDropPermissions != null) {
            dragAndDropPermissions.release();
        }
    }

    public String toString() {
        return "ContentInfo{clip=" + this.mClip + ", source=" + sourceToString(this.mSource) + ", flags=" + flagsToString(this.mFlags) + ", linkUri=" + this.mLinkUri + ", extras=" + this.mExtras + "}";
    }

    public ClipData getClip() {
        return this.mClip;
    }

    public int getSource() {
        return this.mSource;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public Uri getLinkUri() {
        return this.mLinkUri;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Pair<ContentInfo, ContentInfo> partition(Predicate<ClipData.Item> predicate) {
        if (this.mClip.getItemCount() == 1) {
            boolean test = predicate.test(this.mClip.getItemAt(0));
            ContentInfo contentInfo = test ? this : null;
            if (test) {
                this = null;
            }
            return Pair.create(contentInfo, this);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < this.mClip.getItemCount(); i++) {
            ClipData.Item itemAt = this.mClip.getItemAt(i);
            if (predicate.test(itemAt)) {
                arrayList.add(itemAt);
            } else {
                arrayList2.add(itemAt);
            }
        }
        if (arrayList.isEmpty()) {
            return Pair.create(null, this);
        }
        if (arrayList2.isEmpty()) {
            return Pair.create(this, null);
        }
        return Pair.create(new Builder(this).setClip(new ClipData(new ClipDescription(this.mClip.getDescription()), (ArrayList<ClipData.Item>) arrayList)).build(), new Builder(this).setClip(new ClipData(new ClipDescription(this.mClip.getDescription()), (ArrayList<ClipData.Item>) arrayList2)).build());
    }

    public static final class Builder {
        private ClipData mClip;
        private DragAndDropPermissions mDragAndDropPermissions;
        private Bundle mExtras;
        private int mFlags;
        private InputContentInfo mInputContentInfo;
        private Uri mLinkUri;
        private int mSource;

        public Builder(ContentInfo contentInfo) {
            this.mClip = contentInfo.mClip;
            this.mSource = contentInfo.mSource;
            this.mFlags = contentInfo.mFlags;
            this.mLinkUri = contentInfo.mLinkUri;
            this.mExtras = contentInfo.mExtras;
            this.mInputContentInfo = contentInfo.mInputContentInfo;
            this.mDragAndDropPermissions = contentInfo.mDragAndDropPermissions;
        }

        public Builder(ClipData clipData, int i) {
            this.mClip = clipData;
            this.mSource = i;
        }

        public Builder setClip(ClipData clipData) {
            this.mClip = clipData;
            return this;
        }

        public Builder setSource(int i) {
            this.mSource = i;
            return this;
        }

        public Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public Builder setLinkUri(Uri uri) {
            this.mLinkUri = uri;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setInputContentInfo(InputContentInfo inputContentInfo) {
            this.mInputContentInfo = inputContentInfo;
            return this;
        }

        public Builder setDragAndDropPermissions(DragAndDropPermissions dragAndDropPermissions) {
            this.mDragAndDropPermissions = dragAndDropPermissions;
            return this;
        }

        public ContentInfo build() {
            return new ContentInfo(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mClip.writeToParcel(parcel, i);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mFlags);
        Uri.writeToParcel(parcel, this.mLinkUri);
        parcel.writeBundle(this.mExtras);
        if (this.mInputContentInfo == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mInputContentInfo.writeToParcel(parcel, i);
        }
        if (this.mDragAndDropPermissions == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mDragAndDropPermissions.writeToParcel(parcel, i);
        }
    }
}
