package android.printservice;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class PrintServiceInfo implements Parcelable {
    private static final String TAG_PRINT_SERVICE = "print-service";
    private final String mAddPrintersActivityName;
    private final String mAdvancedPrintOptionsActivityName;
    private final String mId;
    private boolean mIsEnabled;
    private final ResolveInfo mResolveInfo;
    private final String mSettingsActivityName;
    private static final String LOG_TAG = PrintServiceInfo.class.getSimpleName();
    public static final Parcelable.Creator<PrintServiceInfo> CREATOR =
            new Parcelable.Creator<
                    PrintServiceInfo>() { // from class: android.printservice.PrintServiceInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PrintServiceInfo createFromParcel(Parcel parcel) {
                    return new PrintServiceInfo(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PrintServiceInfo[] newArray(int size) {
                    return new PrintServiceInfo[size];
                }
            };

    public PrintServiceInfo(Parcel parcel) {
        this.mId = parcel.readString();
        this.mIsEnabled = parcel.readByte() != 0;
        this.mResolveInfo = (ResolveInfo) parcel.readParcelable(null, ResolveInfo.class);
        this.mSettingsActivityName = parcel.readString();
        this.mAddPrintersActivityName = parcel.readString();
        this.mAdvancedPrintOptionsActivityName = parcel.readString();
    }

    public PrintServiceInfo(
            ResolveInfo resolveInfo,
            String settingsActivityName,
            String addPrintersActivityName,
            String advancedPrintOptionsActivityName) {
        this.mId =
                new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name)
                        .flattenToString();
        this.mResolveInfo = resolveInfo;
        this.mSettingsActivityName = settingsActivityName;
        this.mAddPrintersActivityName = addPrintersActivityName;
        this.mAdvancedPrintOptionsActivityName = advancedPrintOptionsActivityName;
    }

    public ComponentName getComponentName() {
        return new ComponentName(
                this.mResolveInfo.serviceInfo.packageName, this.mResolveInfo.serviceInfo.name);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x00b8, code lost:

       if (r3 == null) goto L33;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.printservice.PrintServiceInfo create(
            android.content.Context r13, android.content.pm.ResolveInfo r14) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            android.content.pm.PackageManager r4 = r13.getPackageManager()
            android.content.pm.ServiceInfo r5 = r14.serviceInfo
            java.lang.String r6 = "android.printservice"
            android.content.res.XmlResourceParser r3 = r5.loadXmlMetaData(r4, r6)
            if (r3 == 0) goto Lc1
            r5 = 0
        L13:
            java.lang.String r6 = "Error reading meta-data:"
            r7 = 1
            if (r5 == r7) goto L21
            r8 = 2
            if (r5 == r8) goto L21
            int r6 = r3.next()     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            r5 = r6
            goto L13
        L21:
            java.lang.String r8 = r3.getName()     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            java.lang.String r9 = "print-service"
            boolean r9 = r9.equals(r8)     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            if (r9 != 0) goto L36
            java.lang.String r7 = android.printservice.PrintServiceInfo.LOG_TAG     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            java.lang.String r9 = "Ignoring meta-data that does not start with print-service tag"
            android.util.Log.e(r7, r9)     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            goto L5c
        L36:
            android.content.pm.ServiceInfo r9 = r14.serviceInfo     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            android.content.pm.ApplicationInfo r9 = r9.applicationInfo     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            android.content.res.Resources r9 = r4.getResourcesForApplication(r9)     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            android.util.AttributeSet r10 = android.util.Xml.asAttributeSet(r3)     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            int[] r11 = com.android.internal.R.styleable.PrintService     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            android.content.res.TypedArray r11 = r9.obtainAttributes(r10, r11)     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            r12 = 0
            java.lang.String r12 = r11.getString(r12)     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            r0 = r12
            java.lang.String r7 = r11.getString(r7)     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            r1 = r7
            r7 = 3
            java.lang.String r7 = r11.getString(r7)     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
            r2 = r7
            r11.recycle()     // Catch: java.lang.Throwable -> L62 android.content.pm.PackageManager.NameNotFoundException -> L64 org.xmlpull.v1.XmlPullParserException -> L85 java.io.IOException -> La0
        L5c:
            if (r3 == 0) goto Lc1
        L5e:
            r3.close()
            goto Lc1
        L62:
            r5 = move-exception
            goto Lbb
        L64:
            r5 = move-exception
            java.lang.String r6 = android.printservice.PrintServiceInfo.LOG_TAG     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62
            r7.<init>()     // Catch: java.lang.Throwable -> L62
            java.lang.String r8 = "Unable to load resources for: "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch: java.lang.Throwable -> L62
            android.content.pm.ServiceInfo r8 = r14.serviceInfo     // Catch: java.lang.Throwable -> L62
            java.lang.String r8 = r8.packageName     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch: java.lang.Throwable -> L62
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L62
            android.util.Log.e(r6, r7)     // Catch: java.lang.Throwable -> L62
            if (r3 == 0) goto Lc1
            goto L5e
        L85:
            r5 = move-exception
            java.lang.String r7 = android.printservice.PrintServiceInfo.LOG_TAG     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62
            r8.<init>()     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r6 = r8.append(r6)     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r6 = r6.append(r5)     // Catch: java.lang.Throwable -> L62
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L62
            android.util.Log.w(r7, r6)     // Catch: java.lang.Throwable -> L62
            if (r3 == 0) goto Lc1
            goto L5e
        La0:
            r5 = move-exception
            java.lang.String r7 = android.printservice.PrintServiceInfo.LOG_TAG     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62
            r8.<init>()     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r6 = r8.append(r6)     // Catch: java.lang.Throwable -> L62
            java.lang.StringBuilder r6 = r6.append(r5)     // Catch: java.lang.Throwable -> L62
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L62
            android.util.Log.w(r7, r6)     // Catch: java.lang.Throwable -> L62
            if (r3 == 0) goto Lc1
            goto L5e
        Lbb:
            if (r3 == 0) goto Lc0
            r3.close()
        Lc0:
            throw r5
        Lc1:
            android.printservice.PrintServiceInfo r5 = new android.printservice.PrintServiceInfo
            r5.<init>(r14, r0, r1, r2)
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.printservice.PrintServiceInfo.create(android.content.Context,"
                    + " android.content.pm.ResolveInfo):android.printservice.PrintServiceInfo");
    }

    public String getId() {
        return this.mId;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.mIsEnabled = isEnabled;
    }

    public ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    public String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public String getAddPrintersActivityName() {
        return this.mAddPrintersActivityName;
    }

    public String getAdvancedOptionsActivityName() {
        return this.mAdvancedPrintOptionsActivityName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeByte(this.mIsEnabled ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.mResolveInfo, 0);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeString(this.mAddPrintersActivityName);
        parcel.writeString(this.mAdvancedPrintOptionsActivityName);
    }

    public int hashCode() {
        return (this.mId == null ? 0 : this.mId.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrintServiceInfo other = (PrintServiceInfo) obj;
        if (this.mId == null) {
            if (other.mId != null) {
                return false;
            }
        } else if (!this.mId.equals(other.mId)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PrintServiceInfo{");
        builder.append("id=").append(this.mId);
        builder.append("isEnabled=").append(this.mIsEnabled);
        builder.append(", resolveInfo=").append(this.mResolveInfo);
        builder.append(", settingsActivityName=").append(this.mSettingsActivityName);
        builder.append(", addPrintersActivityName=").append(this.mAddPrintersActivityName);
        builder.append(", advancedPrintOptionsActivityName=")
                .append(this.mAdvancedPrintOptionsActivityName);
        builder.append("}");
        return builder.toString();
    }
}
