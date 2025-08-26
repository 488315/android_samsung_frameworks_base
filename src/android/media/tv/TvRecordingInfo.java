package android.media.tv;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class TvRecordingInfo implements Parcelable {
    public static final Parcelable.Creator<TvRecordingInfo> CREATOR = new Parcelable.Creator<TvRecordingInfo>() { // from class: android.media.tv.TvRecordingInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvRecordingInfo createFromParcel(Parcel parcel) {
            return new TvRecordingInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvRecordingInfo[] newArray(int i) {
            return new TvRecordingInfo[i];
        }
    };
    public static final int FRIDAY = 32;
    public static final int MONDAY = 2;
    public static final int RECORDING_ALL = 3;
    public static final int RECORDING_IN_PROGRESS = 2;
    public static final int RECORDING_SCHEDULED = 1;
    public static final int SATURDAY = 64;
    public static final int SUNDAY = 1;
    public static final int THURSDAY = 16;
    public static final int TUESDAY = 4;
    public static final int WEDNESDAY = 8;
    private Uri mChannelUri;
    private List<TvContentRating> mContentRatings;
    private String mDescription;
    private long mEndPaddingMillis;
    private String mName;
    private Uri mProgramUri;
    private long mRecordingDurationMillis;
    private String mRecordingId;
    private long mRecordingStartTimeMillis;
    private Uri mRecordingUri;
    private int mRepeatDays;
    private long mScheduledDurationMillis;
    private long mScheduledStartTimeMillis;
    private long mStartPaddingMillis;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DaysOfWeek {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TvRecordingListType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TvRecordingInfo(String str, long j, long j2, int i, String str2, String str3, long j3, long j4, Uri uri, Uri uri2, List<TvContentRating> list, Uri uri3, long j5, long j6) {
        this.mRecordingId = str;
        this.mStartPaddingMillis = j;
        this.mEndPaddingMillis = j2;
        this.mRepeatDays = i;
        this.mName = str2;
        this.mDescription = str3;
        this.mScheduledStartTimeMillis = j3;
        this.mScheduledDurationMillis = j4;
        this.mChannelUri = uri;
        this.mProgramUri = uri2;
        this.mContentRatings = list;
        this.mRecordingUri = uri3;
        this.mRecordingStartTimeMillis = j5;
        this.mRecordingDurationMillis = j6;
    }

    public String getRecordingId() {
        return this.mRecordingId;
    }

    public long getStartPaddingMillis() {
        return this.mStartPaddingMillis;
    }

    public long getEndPaddingMillis() {
        return this.mEndPaddingMillis;
    }

    public int getRepeatDays() {
        return this.mRepeatDays;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String str) {
        this.mDescription = str;
    }

    public long getScheduledStartTimeMillis() {
        return this.mScheduledStartTimeMillis;
    }

    public long getScheduledDurationMillis() {
        return this.mScheduledDurationMillis;
    }

    public Uri getChannelUri() {
        return this.mChannelUri;
    }

    public Uri getProgramUri() {
        return this.mProgramUri;
    }

    public List<TvContentRating> getContentRatings() {
        return this.mContentRatings;
    }

    public Uri getRecordingUri() {
        return this.mRecordingUri;
    }

    public long getRecordingStartTimeMillis() {
        return this.mRecordingStartTimeMillis;
    }

    public long getRecordingDurationMillis() {
        return this.mRecordingDurationMillis;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mRecordingId);
        parcel.writeLong(this.mStartPaddingMillis);
        parcel.writeLong(this.mEndPaddingMillis);
        parcel.writeInt(this.mRepeatDays);
        parcel.writeString(this.mName);
        parcel.writeString(this.mDescription);
        parcel.writeLong(this.mScheduledStartTimeMillis);
        parcel.writeLong(this.mScheduledDurationMillis);
        Uri uri = this.mChannelUri;
        parcel.writeString(uri == null ? null : uri.toString());
        Uri uri2 = this.mProgramUri;
        parcel.writeString(uri2 == null ? null : uri2.toString());
        final ArrayList arrayList = new ArrayList();
        this.mContentRatings.forEach(new Consumer() { // from class: android.media.tv.TvRecordingInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add(((TvContentRating) obj).flattenToString());
            }
        });
        parcel.writeList(this.mContentRatings);
        parcel.writeString(this.mRecordingUri != null ? this.mProgramUri.toString() : null);
        parcel.writeLong(this.mRecordingDurationMillis);
        parcel.writeLong(this.mRecordingStartTimeMillis);
    }

    private TvRecordingInfo(Parcel parcel) {
        this.mRecordingId = parcel.readString();
        this.mStartPaddingMillis = parcel.readLong();
        this.mEndPaddingMillis = parcel.readLong();
        this.mRepeatDays = parcel.readInt();
        this.mName = parcel.readString();
        this.mDescription = parcel.readString();
        this.mScheduledStartTimeMillis = parcel.readLong();
        this.mScheduledDurationMillis = parcel.readLong();
        this.mChannelUri = Uri.parse(parcel.readString());
        this.mProgramUri = Uri.parse(parcel.readString());
        this.mContentRatings = new ArrayList();
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        arrayList.forEach(new Consumer() { // from class: android.media.tv.TvRecordingInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$new$1((String) obj);
            }
        });
        this.mRecordingUri = Uri.parse(parcel.readString());
        this.mRecordingDurationMillis = parcel.readLong();
        this.mRecordingStartTimeMillis = parcel.readLong();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(String str) {
        this.mContentRatings.add(TvContentRating.unflattenFromString(str));
    }
}
