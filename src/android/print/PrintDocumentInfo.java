package android.print;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class PrintDocumentInfo implements Parcelable {
    public static final int CONTENT_TYPE_DOCUMENT = 0;
    public static final int CONTENT_TYPE_PHOTO = 1;
    public static final int CONTENT_TYPE_UNKNOWN = -1;
    public static final Parcelable.Creator<PrintDocumentInfo> CREATOR = new Parcelable.Creator<PrintDocumentInfo>() { // from class: android.print.PrintDocumentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrintDocumentInfo createFromParcel(Parcel parcel) {
            return new PrintDocumentInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrintDocumentInfo[] newArray(int i) {
            return new PrintDocumentInfo[i];
        }
    };
    public static final int PAGE_COUNT_UNKNOWN = -1;
    private int mContentType;
    private long mDataSize;
    private String mName;
    private int mPageCount;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PrintDocumentInfo() {
    }

    private PrintDocumentInfo(PrintDocumentInfo printDocumentInfo) {
        this.mName = printDocumentInfo.mName;
        this.mPageCount = printDocumentInfo.mPageCount;
        this.mContentType = printDocumentInfo.mContentType;
        this.mDataSize = printDocumentInfo.mDataSize;
    }

    private PrintDocumentInfo(Parcel parcel) {
        this.mName = (String) Preconditions.checkStringNotEmpty(parcel.readString());
        int readInt = parcel.readInt();
        this.mPageCount = readInt;
        Preconditions.checkArgument(readInt == -1 || readInt > 0);
        this.mContentType = parcel.readInt();
        this.mDataSize = Preconditions.checkArgumentNonnegative(parcel.readLong());
    }

    public String getName() {
        return this.mName;
    }

    public int getPageCount() {
        return this.mPageCount;
    }

    public int getContentType() {
        return this.mContentType;
    }

    public long getDataSize() {
        return this.mDataSize;
    }

    public void setDataSize(long j) {
        this.mDataSize = j;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mPageCount);
        parcel.writeInt(this.mContentType);
        parcel.writeLong(this.mDataSize);
    }

    public int hashCode() {
        String str = this.mName;
        int hashCode = ((((((str != null ? str.hashCode() : 0) + 31) * 31) + this.mContentType) * 31) + this.mPageCount) * 31;
        long j = this.mDataSize;
        return ((hashCode + ((int) j)) * 31) + ((int) (j >> 32));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrintDocumentInfo printDocumentInfo = (PrintDocumentInfo) obj;
        return TextUtils.equals(this.mName, printDocumentInfo.mName) && this.mContentType == printDocumentInfo.mContentType && this.mPageCount == printDocumentInfo.mPageCount && this.mDataSize == printDocumentInfo.mDataSize;
    }

    public String toString() {
        return "PrintDocumentInfo{name=" + this.mName + ", pageCount=" + this.mPageCount + ", contentType=" + contentTypeToString(this.mContentType) + ", dataSize=" + this.mDataSize + "}";
    }

    private String contentTypeToString(int i) {
        if (i == 0) {
            return "CONTENT_TYPE_DOCUMENT";
        }
        if (i == 1) {
            return "CONTENT_TYPE_PHOTO";
        }
        return "CONTENT_TYPE_UNKNOWN";
    }

    public static final class Builder {
        private final PrintDocumentInfo mPrototype;

        public Builder(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("name cannot be empty");
            }
            PrintDocumentInfo printDocumentInfo = new PrintDocumentInfo();
            this.mPrototype = printDocumentInfo;
            printDocumentInfo.mName = str;
        }

        public Builder setPageCount(int i) {
            if (i < 0 && i != -1) {
                throw new IllegalArgumentException("pageCount must be greater than or equal to zero or DocumentInfo#PAGE_COUNT_UNKNOWN");
            }
            this.mPrototype.mPageCount = i;
            return this;
        }

        public Builder setContentType(int i) {
            this.mPrototype.mContentType = i;
            return this;
        }

        public PrintDocumentInfo build() {
            if (this.mPrototype.mPageCount == 0) {
                this.mPrototype.mPageCount = -1;
            }
            return new PrintDocumentInfo();
        }
    }
}
