package android.print;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class PrinterInfo implements Parcelable {
    public static final Parcelable.Creator<PrinterInfo> CREATOR = new Parcelable.Creator<PrinterInfo>() { // from class: android.print.PrinterInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrinterInfo createFromParcel(Parcel parcel) {
            return new PrinterInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrinterInfo[] newArray(int i) {
            return new PrinterInfo[i];
        }
    };
    public static final int STATUS_BUSY = 2;
    public static final int STATUS_IDLE = 1;
    public static final int STATUS_UNAVAILABLE = 3;
    private final PrinterCapabilitiesInfo mCapabilities;
    private final int mCustomPrinterIconGen;
    private final String mDescription;
    private final boolean mHasCustomPrinterIcon;
    private final int mIconResourceId;
    private final PrinterId mId;
    private final PendingIntent mInfoIntent;
    private final String mName;
    private final int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PrinterInfo(PrinterId printerId, String str, int i, int i2, boolean z, String str2, PendingIntent pendingIntent, PrinterCapabilitiesInfo printerCapabilitiesInfo, int i3) {
        this.mId = printerId;
        this.mName = str;
        this.mStatus = i;
        this.mIconResourceId = i2;
        this.mHasCustomPrinterIcon = z;
        this.mDescription = str2;
        this.mInfoIntent = pendingIntent;
        this.mCapabilities = printerCapabilitiesInfo;
        this.mCustomPrinterIconGen = i3;
    }

    public PrinterId getId() {
        return this.mId;
    }

    public Drawable loadIcon(Context context) {
        Icon customPrinterIcon;
        PackageManager packageManager = context.getPackageManager();
        Drawable loadDrawable = (!this.mHasCustomPrinterIcon || (customPrinterIcon = ((PrintManager) context.getSystemService(Context.PRINT_SERVICE)).getCustomPrinterIcon(this.mId)) == null) ? null : customPrinterIcon.loadDrawable(context);
        if (loadDrawable == null) {
            try {
                String packageName = this.mId.getServiceName().getPackageName();
                ApplicationInfo applicationInfo = packageManager.getPackageInfo(packageName, 0).applicationInfo;
                int i = this.mIconResourceId;
                if (i != 0) {
                    loadDrawable = packageManager.getDrawable(packageName, i, applicationInfo);
                }
                if (loadDrawable == null) {
                    return applicationInfo.loadIcon(packageManager);
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return loadDrawable;
    }

    public boolean getHasCustomPrinterIcon() {
        return this.mHasCustomPrinterIcon;
    }

    public String getName() {
        return this.mName;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public PendingIntent getInfoIntent() {
        return this.mInfoIntent;
    }

    public PrinterCapabilitiesInfo getCapabilities() {
        return this.mCapabilities;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PrinterId checkPrinterId(PrinterId printerId) {
        return (PrinterId) Preconditions.checkNotNull(printerId, "printerId cannot be null.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkStatus(int i) {
        if (i == 1 || i == 2 || i == 3) {
            return i;
        }
        throw new IllegalArgumentException("status is invalid.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String checkName(String str) {
        return (String) Preconditions.checkStringNotEmpty(str, "name cannot be empty.");
    }

    private PrinterInfo(Parcel parcel) {
        this.mId = checkPrinterId((PrinterId) parcel.readParcelable(null, PrinterId.class));
        this.mName = checkName(parcel.readString());
        this.mStatus = checkStatus(parcel.readInt());
        this.mDescription = parcel.readString();
        this.mCapabilities = (PrinterCapabilitiesInfo) parcel.readParcelable(null, PrinterCapabilitiesInfo.class);
        this.mIconResourceId = parcel.readInt();
        this.mHasCustomPrinterIcon = parcel.readByte() != 0;
        this.mCustomPrinterIconGen = parcel.readInt();
        this.mInfoIntent = (PendingIntent) parcel.readParcelable(null, PendingIntent.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mId, i);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mStatus);
        parcel.writeString(this.mDescription);
        parcel.writeParcelable(this.mCapabilities, i);
        parcel.writeInt(this.mIconResourceId);
        parcel.writeByte(this.mHasCustomPrinterIcon ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mCustomPrinterIconGen);
        parcel.writeParcelable(this.mInfoIntent, i);
    }

    public int hashCode() {
        int hashCode = (((((this.mId.hashCode() + 31) * 31) + this.mName.hashCode()) * 31) + this.mStatus) * 31;
        String str = this.mDescription;
        int hashCode2 = (hashCode + (str != null ? str.hashCode() : 0)) * 31;
        PrinterCapabilitiesInfo printerCapabilitiesInfo = this.mCapabilities;
        int hashCode3 = (((((((hashCode2 + (printerCapabilitiesInfo != null ? printerCapabilitiesInfo.hashCode() : 0)) * 31) + this.mIconResourceId) * 31) + (this.mHasCustomPrinterIcon ? 1 : 0)) * 31) + this.mCustomPrinterIconGen) * 31;
        PendingIntent pendingIntent = this.mInfoIntent;
        return hashCode3 + (pendingIntent != null ? pendingIntent.hashCode() : 0);
    }

    public boolean equalsIgnoringStatus(PrinterInfo printerInfo) {
        if (!this.mId.equals(printerInfo.mId) || !this.mName.equals(printerInfo.mName) || !TextUtils.equals(this.mDescription, printerInfo.mDescription)) {
            return false;
        }
        PrinterCapabilitiesInfo printerCapabilitiesInfo = this.mCapabilities;
        if (printerCapabilitiesInfo == null) {
            if (printerInfo.mCapabilities != null) {
                return false;
            }
        } else if (!printerCapabilitiesInfo.equals(printerInfo.mCapabilities)) {
            return false;
        }
        if (this.mIconResourceId != printerInfo.mIconResourceId || this.mHasCustomPrinterIcon != printerInfo.mHasCustomPrinterIcon || this.mCustomPrinterIconGen != printerInfo.mCustomPrinterIconGen) {
            return false;
        }
        PendingIntent pendingIntent = this.mInfoIntent;
        return pendingIntent == null ? printerInfo.mInfoIntent == null : pendingIntent.equals(printerInfo.mInfoIntent);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrinterInfo printerInfo = (PrinterInfo) obj;
        return equalsIgnoringStatus(printerInfo) && this.mStatus == printerInfo.mStatus;
    }

    public String toString() {
        return "PrinterInfo{id=" + this.mId + ", name=" + this.mName + ", status=" + this.mStatus + ", description=" + this.mDescription + ", capabilities=" + this.mCapabilities + ", iconResId=" + this.mIconResourceId + ", hasCustomPrinterIcon=" + this.mHasCustomPrinterIcon + ", customPrinterIconGen=" + this.mCustomPrinterIconGen + ", infoIntent=" + this.mInfoIntent + "\"}";
    }

    public static final class Builder {
        private PrinterCapabilitiesInfo mCapabilities;
        private int mCustomPrinterIconGen;
        private String mDescription;
        private boolean mHasCustomPrinterIcon;
        private int mIconResourceId;
        private PendingIntent mInfoIntent;
        private String mName;
        private PrinterId mPrinterId;
        private int mStatus;

        public Builder(PrinterId printerId, String str, int i) {
            this.mPrinterId = PrinterInfo.checkPrinterId(printerId);
            this.mName = PrinterInfo.checkName(str);
            this.mStatus = PrinterInfo.checkStatus(i);
        }

        public Builder(PrinterInfo printerInfo) {
            this.mPrinterId = printerInfo.mId;
            this.mName = printerInfo.mName;
            this.mStatus = printerInfo.mStatus;
            this.mIconResourceId = printerInfo.mIconResourceId;
            this.mHasCustomPrinterIcon = printerInfo.mHasCustomPrinterIcon;
            this.mDescription = printerInfo.mDescription;
            this.mInfoIntent = printerInfo.mInfoIntent;
            this.mCapabilities = printerInfo.mCapabilities;
            this.mCustomPrinterIconGen = printerInfo.mCustomPrinterIconGen;
        }

        public Builder setStatus(int i) {
            this.mStatus = PrinterInfo.checkStatus(i);
            return this;
        }

        public Builder setIconResourceId(int i) {
            this.mIconResourceId = Preconditions.checkArgumentNonnegative(i, "iconResourceId can't be negative");
            return this;
        }

        public Builder setHasCustomPrinterIcon(boolean z) {
            this.mHasCustomPrinterIcon = z;
            return this;
        }

        public Builder setName(String str) {
            this.mName = PrinterInfo.checkName(str);
            return this;
        }

        public Builder setDescription(String str) {
            this.mDescription = str;
            return this;
        }

        public Builder setInfoIntent(PendingIntent pendingIntent) {
            this.mInfoIntent = pendingIntent;
            return this;
        }

        public Builder setCapabilities(PrinterCapabilitiesInfo printerCapabilitiesInfo) {
            this.mCapabilities = printerCapabilitiesInfo;
            return this;
        }

        public PrinterInfo build() {
            return new PrinterInfo(this.mPrinterId, this.mName, this.mStatus, this.mIconResourceId, this.mHasCustomPrinterIcon, this.mDescription, this.mInfoIntent, this.mCapabilities, this.mCustomPrinterIconGen);
        }

        public Builder incCustomPrinterIconGen() {
            this.mCustomPrinterIconGen++;
            return this;
        }
    }
}
