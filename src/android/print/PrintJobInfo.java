package android.print;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class PrintJobInfo implements Parcelable {
    public static final Parcelable.Creator<PrintJobInfo> CREATOR = new Parcelable.Creator<PrintJobInfo>() { // from class: android.print.PrintJobInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrintJobInfo createFromParcel(Parcel parcel) {
            return new PrintJobInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrintJobInfo[] newArray(int i) {
            return new PrintJobInfo[i];
        }
    };
    public static final int STATE_ANY = -1;
    public static final int STATE_ANY_ACTIVE = -3;
    public static final int STATE_ANY_SCHEDULED = -4;
    public static final int STATE_ANY_VISIBLE_TO_CLIENTS = -2;
    public static final int STATE_BLOCKED = 4;
    public static final int STATE_CANCELED = 7;
    public static final int STATE_COMPLETED = 5;
    public static final int STATE_CREATED = 1;
    public static final int STATE_FAILED = 6;
    public static final int STATE_QUEUED = 2;
    public static final int STATE_STARTED = 3;
    private Bundle mAdvancedOptions;
    private int mAppId;
    private PrintAttributes mAttributes;
    private boolean mCanceling;
    private int mCopies;
    private long mCreationTime;
    private PrintDocumentInfo mDocumentInfo;
    private PrintJobId mId;
    private String mLabel;
    private PageRange[] mPageRanges;
    private PrinterId mPrinterId;
    private String mPrinterName;
    private float mProgress;
    private int mState;
    private CharSequence mStatus;
    private int mStatusRes;
    private CharSequence mStatusResAppPackageName;
    private String mTag;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PrintJobInfo() {
        this.mProgress = -1.0f;
    }

    public PrintJobInfo(PrintJobInfo printJobInfo) {
        this.mId = printJobInfo.mId;
        this.mLabel = printJobInfo.mLabel;
        this.mPrinterId = printJobInfo.mPrinterId;
        this.mPrinterName = printJobInfo.mPrinterName;
        this.mState = printJobInfo.mState;
        this.mAppId = printJobInfo.mAppId;
        this.mTag = printJobInfo.mTag;
        this.mCreationTime = printJobInfo.mCreationTime;
        this.mCopies = printJobInfo.mCopies;
        this.mPageRanges = printJobInfo.mPageRanges;
        this.mAttributes = printJobInfo.mAttributes;
        this.mDocumentInfo = printJobInfo.mDocumentInfo;
        this.mProgress = printJobInfo.mProgress;
        this.mStatus = printJobInfo.mStatus;
        this.mStatusRes = printJobInfo.mStatusRes;
        this.mStatusResAppPackageName = printJobInfo.mStatusResAppPackageName;
        this.mCanceling = printJobInfo.mCanceling;
        this.mAdvancedOptions = printJobInfo.mAdvancedOptions;
    }

    private PrintJobInfo(Parcel parcel) {
        this.mId = (PrintJobId) parcel.readParcelable(null, PrintJobId.class);
        this.mLabel = parcel.readString();
        this.mPrinterId = (PrinterId) parcel.readParcelable(null, PrinterId.class);
        this.mPrinterName = parcel.readString();
        this.mState = parcel.readInt();
        this.mAppId = parcel.readInt();
        this.mTag = parcel.readString();
        this.mCreationTime = parcel.readLong();
        this.mCopies = parcel.readInt();
        Parcelable[] parcelableArr = (Parcelable[]) parcel.readParcelableArray(null, PageRange.class);
        if (parcelableArr != null) {
            this.mPageRanges = new PageRange[parcelableArr.length];
            for (int i = 0; i < parcelableArr.length; i++) {
                this.mPageRanges[i] = (PageRange) parcelableArr[i];
            }
        }
        this.mAttributes = (PrintAttributes) parcel.readParcelable(null, PrintAttributes.class);
        this.mDocumentInfo = (PrintDocumentInfo) parcel.readParcelable(null, PrintDocumentInfo.class);
        this.mProgress = parcel.readFloat();
        this.mStatus = parcel.readCharSequence();
        this.mStatusRes = parcel.readInt();
        this.mStatusResAppPackageName = parcel.readCharSequence();
        this.mCanceling = parcel.readInt() == 1;
        Bundle readBundle = parcel.readBundle();
        this.mAdvancedOptions = readBundle;
        if (readBundle != null) {
            Preconditions.checkArgument(!readBundle.containsKey(null));
        }
    }

    public PrintJobId getId() {
        return this.mId;
    }

    public void setId(PrintJobId printJobId) {
        this.mId = printJobId;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public void setLabel(String str) {
        this.mLabel = str;
    }

    public PrinterId getPrinterId() {
        return this.mPrinterId;
    }

    public void setPrinterId(PrinterId printerId) {
        this.mPrinterId = printerId;
    }

    public String getPrinterName() {
        return this.mPrinterName;
    }

    public void setPrinterName(String str) {
        this.mPrinterName = str;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public void setProgress(float f) {
        Preconditions.checkArgumentInRange(f, 0.0f, 1.0f, "progress");
        this.mProgress = f;
    }

    public void setStatus(CharSequence charSequence) {
        this.mStatusRes = 0;
        this.mStatusResAppPackageName = null;
        this.mStatus = charSequence;
    }

    public void setStatus(int i, CharSequence charSequence) {
        this.mStatus = null;
        this.mStatusRes = i;
        this.mStatusResAppPackageName = charSequence;
    }

    public int getAppId() {
        return this.mAppId;
    }

    public void setAppId(int i) {
        this.mAppId = i;
    }

    public String getTag() {
        return this.mTag;
    }

    public void setTag(String str) {
        this.mTag = str;
    }

    public long getCreationTime() {
        return this.mCreationTime;
    }

    public void setCreationTime(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("creationTime must be non-negative.");
        }
        this.mCreationTime = j;
    }

    public int getCopies() {
        return this.mCopies;
    }

    public void setCopies(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Copies must be more than one.");
        }
        this.mCopies = i;
    }

    public PageRange[] getPages() {
        return this.mPageRanges;
    }

    public void setPages(PageRange[] pageRangeArr) {
        this.mPageRanges = pageRangeArr;
    }

    public PrintAttributes getAttributes() {
        return this.mAttributes;
    }

    public void setAttributes(PrintAttributes printAttributes) {
        this.mAttributes = printAttributes;
    }

    public PrintDocumentInfo getDocumentInfo() {
        return this.mDocumentInfo;
    }

    public void setDocumentInfo(PrintDocumentInfo printDocumentInfo) {
        this.mDocumentInfo = printDocumentInfo;
    }

    public boolean isCancelling() {
        return this.mCanceling;
    }

    public void setCancelling(boolean z) {
        this.mCanceling = z;
    }

    public boolean shouldStayAwake() {
        int i;
        return this.mCanceling || (i = this.mState) == 3 || i == 2;
    }

    public boolean hasAdvancedOption(String str) {
        Bundle bundle = this.mAdvancedOptions;
        return bundle != null && bundle.containsKey(str);
    }

    public String getAdvancedStringOption(String str) {
        Bundle bundle = this.mAdvancedOptions;
        if (bundle != null) {
            return bundle.getString(str);
        }
        return null;
    }

    public int getAdvancedIntOption(String str) {
        Bundle bundle = this.mAdvancedOptions;
        if (bundle != null) {
            return bundle.getInt(str);
        }
        return 0;
    }

    public Bundle getAdvancedOptions() {
        return this.mAdvancedOptions;
    }

    public void setAdvancedOptions(Bundle bundle) {
        this.mAdvancedOptions = bundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mId, i);
        parcel.writeString(this.mLabel);
        parcel.writeParcelable(this.mPrinterId, i);
        parcel.writeString(this.mPrinterName);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mAppId);
        parcel.writeString(this.mTag);
        parcel.writeLong(this.mCreationTime);
        parcel.writeInt(this.mCopies);
        parcel.writeParcelableArray(this.mPageRanges, i);
        parcel.writeParcelable(this.mAttributes, i);
        parcel.writeParcelable(this.mDocumentInfo, 0);
        parcel.writeFloat(this.mProgress);
        parcel.writeCharSequence(this.mStatus);
        parcel.writeInt(this.mStatusRes);
        parcel.writeCharSequence(this.mStatusResAppPackageName);
        parcel.writeInt(this.mCanceling ? 1 : 0);
        parcel.writeBundle(this.mAdvancedOptions);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PrintJobInfo{label: ");
        sb.append(this.mLabel);
        sb.append(", id: ");
        sb.append(this.mId);
        sb.append(", state: ");
        sb.append(stateToString(this.mState));
        sb.append(", printer: " + this.mPrinterId);
        sb.append(", tag: ");
        sb.append(this.mTag);
        sb.append(", creationTime: " + this.mCreationTime);
        sb.append(", copies: ");
        sb.append(this.mCopies);
        StringBuilder sb2 = new StringBuilder(", attributes: ");
        PrintAttributes printAttributes = this.mAttributes;
        sb2.append(printAttributes != null ? printAttributes.toString() : null);
        sb.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder(", documentInfo: ");
        PrintDocumentInfo printDocumentInfo = this.mDocumentInfo;
        sb3.append(printDocumentInfo != null ? printDocumentInfo.toString() : null);
        sb.append(sb3.toString());
        sb.append(", cancelling: " + this.mCanceling);
        StringBuilder sb4 = new StringBuilder(", pages: ");
        PageRange[] pageRangeArr = this.mPageRanges;
        sb4.append(pageRangeArr != null ? Arrays.toString(pageRangeArr) : null);
        sb.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder(", hasAdvancedOptions: ");
        sb5.append(this.mAdvancedOptions != null);
        sb.append(sb5.toString());
        sb.append(", progress: " + this.mProgress);
        StringBuilder sb6 = new StringBuilder(", status: ");
        CharSequence charSequence = this.mStatus;
        sb6.append(charSequence != null ? charSequence.toString() : null);
        sb.append(sb6.toString());
        sb.append(", statusRes: " + this.mStatusRes);
        StringBuilder sb7 = new StringBuilder(", statusResAppPackageName: ");
        CharSequence charSequence2 = this.mStatusResAppPackageName;
        sb7.append(charSequence2 != null ? charSequence2.toString() : null);
        sb.append(sb7.toString());
        sb.append("}");
        return sb.toString();
    }

    public static String stateToString(int i) {
        switch (i) {
            case 1:
                return "STATE_CREATED";
            case 2:
                return "STATE_QUEUED";
            case 3:
                return "STATE_STARTED";
            case 4:
                return "STATE_BLOCKED";
            case 5:
                return "STATE_COMPLETED";
            case 6:
                return "STATE_FAILED";
            case 7:
                return "STATE_CANCELED";
            default:
                return "STATE_UNKNOWN";
        }
    }

    public float getProgress() {
        return this.mProgress;
    }

    public CharSequence getStatus(PackageManager packageManager) {
        if (this.mStatusRes == 0) {
            return this.mStatus;
        }
        try {
            return packageManager.getResourcesForApplication(this.mStatusResAppPackageName.toString()).getString(this.mStatusRes);
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            return null;
        }
    }

    public static final class Builder {
        private final PrintJobInfo mPrototype;

        public Builder(PrintJobInfo printJobInfo) {
            PrintJobInfo printJobInfo2;
            if (printJobInfo != null) {
                printJobInfo2 = new PrintJobInfo(printJobInfo);
            } else {
                printJobInfo2 = new PrintJobInfo();
            }
            this.mPrototype = printJobInfo2;
        }

        public void setCopies(int i) {
            this.mPrototype.mCopies = i;
        }

        public void setAttributes(PrintAttributes printAttributes) {
            this.mPrototype.mAttributes = printAttributes;
        }

        public void setPages(PageRange[] pageRangeArr) {
            this.mPrototype.mPageRanges = pageRangeArr;
        }

        public void setProgress(float f) {
            Preconditions.checkArgumentInRange(f, 0.0f, 1.0f, "progress");
            this.mPrototype.mProgress = f;
        }

        public void setStatus(CharSequence charSequence) {
            this.mPrototype.mStatus = charSequence;
        }

        public void putAdvancedOption(String str, String str2) {
            Preconditions.checkNotNull(str, "key cannot be null");
            if (this.mPrototype.mAdvancedOptions == null) {
                this.mPrototype.mAdvancedOptions = new Bundle();
            }
            this.mPrototype.mAdvancedOptions.putString(str, str2);
        }

        public void putAdvancedOption(String str, int i) {
            if (this.mPrototype.mAdvancedOptions == null) {
                this.mPrototype.mAdvancedOptions = new Bundle();
            }
            this.mPrototype.mAdvancedOptions.putInt(str, i);
        }

        public PrintJobInfo build() {
            return this.mPrototype;
        }
    }
}
