package android.printservice;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes3.dex */
public final class PrintServiceInfo implements Parcelable {
    public static final Parcelable.Creator<PrintServiceInfo> CREATOR = new Parcelable.Creator<PrintServiceInfo>() { // from class: android.printservice.PrintServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrintServiceInfo createFromParcel(Parcel parcel) {
            return new PrintServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrintServiceInfo[] newArray(int i) {
            return new PrintServiceInfo[i];
        }
    };
    private static final String LOG_TAG = "PrintServiceInfo";
    private static final String TAG_PRINT_SERVICE = "print-service";
    private final String mAddPrintersActivityName;
    private final String mAdvancedPrintOptionsActivityName;
    private final String mId;
    private boolean mIsEnabled;
    private final ResolveInfo mResolveInfo;
    private final String mSettingsActivityName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PrintServiceInfo(Parcel parcel) {
        this.mId = parcel.readString();
        this.mIsEnabled = parcel.readByte() != 0;
        this.mResolveInfo = (ResolveInfo) parcel.readParcelable(null, ResolveInfo.class);
        this.mSettingsActivityName = parcel.readString();
        this.mAddPrintersActivityName = parcel.readString();
        this.mAdvancedPrintOptionsActivityName = parcel.readString();
    }

    public PrintServiceInfo(ResolveInfo resolveInfo, String str, String str2, String str3) {
        this.mId = new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name).flattenToString();
        this.mResolveInfo = resolveInfo;
        this.mSettingsActivityName = str;
        this.mAddPrintersActivityName = str2;
        this.mAdvancedPrintOptionsActivityName = str3;
    }

    public ComponentName getComponentName() {
        return new ComponentName(this.mResolveInfo.serviceInfo.packageName, this.mResolveInfo.serviceInfo.name);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ce A[PHI: r1 r2 r3
      0x00ce: PHI (r1v16 java.lang.String) = (r1v14 java.lang.String), (r1v17 java.lang.String) binds: [B:41:0x00b4, B:45:0x00cc] A[DONT_GENERATE, DONT_INLINE]
      0x00ce: PHI (r2v9 java.lang.String) = (r2v7 java.lang.String), (r2v10 java.lang.String) binds: [B:41:0x00b4, B:45:0x00cc] A[DONT_GENERATE, DONT_INLINE]
      0x00ce: PHI (r3v18 java.lang.String) = (r3v16 java.lang.String), (r3v19 java.lang.String) binds: [B:41:0x00b4, B:45:0x00cc] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static PrintServiceInfo create(Context context, ResolveInfo resolveInfo) {
        String str;
        String string;
        String str2;
        PackageManager packageManager = context.getPackageManager();
        XmlResourceParser xmlResourceParserLoadXmlMetaData = resolveInfo.serviceInfo.loadXmlMetaData(packageManager, PrintService.SERVICE_META_DATA);
        String string2 = null;
        if (xmlResourceParserLoadXmlMetaData != null) {
            for (int next = 0; next != 1 && next != 2; next = xmlResourceParserLoadXmlMetaData.next()) {
                try {
                    try {
                    } finally {
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            xmlResourceParserLoadXmlMetaData.close();
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    str = null;
                    string = null;
                    Log.e(LOG_TAG, "Unable to load resources for: " + resolveInfo.serviceInfo.packageName);
                    if (xmlResourceParserLoadXmlMetaData != null) {
                    }
                    return new PrintServiceInfo(resolveInfo, string2, string, str);
                } catch (IOException e) {
                    e = e;
                    str2 = null;
                    string = null;
                    Log.w(LOG_TAG, "Error reading meta-data:" + e);
                    if (xmlResourceParserLoadXmlMetaData != null) {
                    }
                    str = str2;
                    return new PrintServiceInfo(resolveInfo, string2, string, str);
                } catch (XmlPullParserException e2) {
                    e = e2;
                    str2 = null;
                    string = null;
                    Log.w(LOG_TAG, "Error reading meta-data:" + e);
                    if (xmlResourceParserLoadXmlMetaData != null) {
                    }
                    str = str2;
                    return new PrintServiceInfo(resolveInfo, string2, string, str);
                }
            }
            if (!TAG_PRINT_SERVICE.equals(xmlResourceParserLoadXmlMetaData.getName())) {
                Log.e(LOG_TAG, "Ignoring meta-data that does not start with print-service tag");
                str = null;
                string = null;
            } else {
                TypedArray typedArrayObtainAttributes = packageManager.getResourcesForApplication(resolveInfo.serviceInfo.applicationInfo).obtainAttributes(Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData), R.styleable.PrintService);
                String string3 = typedArrayObtainAttributes.getString(0);
                try {
                    string = typedArrayObtainAttributes.getString(1);
                    try {
                        string2 = typedArrayObtainAttributes.getString(3);
                        typedArrayObtainAttributes.recycle();
                        str = string2;
                        string2 = string3;
                    } catch (PackageManager.NameNotFoundException unused2) {
                        str = string2;
                        string2 = string3;
                        Log.e(LOG_TAG, "Unable to load resources for: " + resolveInfo.serviceInfo.packageName);
                        if (xmlResourceParserLoadXmlMetaData != null) {
                        }
                        return new PrintServiceInfo(resolveInfo, string2, string, str);
                    } catch (IOException e3) {
                        e = e3;
                        str2 = string2;
                        string2 = string3;
                        Log.w(LOG_TAG, "Error reading meta-data:" + e);
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            xmlResourceParserLoadXmlMetaData.close();
                        }
                        str = str2;
                        return new PrintServiceInfo(resolveInfo, string2, string, str);
                    } catch (XmlPullParserException e4) {
                        e = e4;
                        str2 = string2;
                        string2 = string3;
                        Log.w(LOG_TAG, "Error reading meta-data:" + e);
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            xmlResourceParserLoadXmlMetaData.close();
                        }
                        str = str2;
                        return new PrintServiceInfo(resolveInfo, string2, string, str);
                    }
                } catch (PackageManager.NameNotFoundException unused3) {
                    str = null;
                    string = null;
                } catch (IOException e5) {
                    e = e5;
                    string = null;
                    string2 = string3;
                    str2 = null;
                } catch (XmlPullParserException e6) {
                    e = e6;
                    string = null;
                    string2 = string3;
                    str2 = null;
                }
            }
        } else {
            str = null;
            string = null;
        }
        return new PrintServiceInfo(resolveInfo, string2, string, str);
    }

    public String getId() {
        return this.mId;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public void setIsEnabled(boolean z) {
        this.mIsEnabled = z;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeByte(this.mIsEnabled ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.mResolveInfo, 0);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeString(this.mAddPrintersActivityName);
        parcel.writeString(this.mAdvancedPrintOptionsActivityName);
    }

    public int hashCode() {
        String str = this.mId;
        return (str == null ? 0 : str.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrintServiceInfo printServiceInfo = (PrintServiceInfo) obj;
        String str = this.mId;
        if (str == null) {
            if (printServiceInfo.mId != null) {
                return false;
            }
        } else if (!str.equals(printServiceInfo.mId)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "PrintServiceInfo{id=" + this.mId + "isEnabled=" + this.mIsEnabled + ", resolveInfo=" + this.mResolveInfo + ", settingsActivityName=" + this.mSettingsActivityName + ", addPrintersActivityName=" + this.mAddPrintersActivityName + ", advancedPrintOptionsActivityName=" + this.mAdvancedPrintOptionsActivityName + "}";
    }
}
