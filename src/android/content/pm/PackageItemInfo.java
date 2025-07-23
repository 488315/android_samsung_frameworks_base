package android.content.pm;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.sec.enterprise.ApplicationPolicy;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.text.TextUtils;
import android.util.Printer;
import android.util.proto.ProtoOutputStream;
import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes.dex */
public class PackageItemInfo {
    public static final float DEFAULT_MAX_LABEL_SIZE_PX = 1000.0f;
    public static final int DUMP_FLAG_ALL = 3;
    public static final int DUMP_FLAG_APPLICATION = 2;
    public static final int DUMP_FLAG_DETAILS = 1;
    public static final int MAX_SAFE_LABEL_LENGTH = 1000;

    @SystemApi
    @Deprecated
    public static final int SAFE_LABEL_FLAG_FIRST_LINE = 4;

    @SystemApi
    @Deprecated
    public static final int SAFE_LABEL_FLAG_SINGLE_LINE = 2;

    @SystemApi
    @Deprecated
    public static final int SAFE_LABEL_FLAG_TRIM = 1;
    private static volatile boolean sForceSafeLabels = false;
    public int banner;
    public int icon;
    public boolean isArchived;
    public int labelRes;
    public int logo;
    public Bundle metaData;
    public String name;
    public CharSequence nonLocalizedLabel;
    public String packageName;
    public int showUserIcon;

    protected void dumpBack(Printer printer, String str) {
    }

    public ApplicationInfo getApplicationInfo() {
        return null;
    }

    protected Drawable loadDefaultBanner(PackageManager packageManager) {
        return null;
    }

    protected Drawable loadDefaultLogo(PackageManager packageManager) {
        return null;
    }

    @SystemApi
    public static void forceSafeLabels() {
        sForceSafeLabels = true;
    }

    public PackageItemInfo() {
        this.showUserIcon = -10000;
    }

    public PackageItemInfo(PackageItemInfo packageItemInfo) {
        String str = packageItemInfo.name;
        this.name = str;
        if (str != null) {
            this.name = str.trim();
        }
        this.packageName = packageItemInfo.packageName;
        this.labelRes = packageItemInfo.labelRes;
        CharSequence charSequence = packageItemInfo.nonLocalizedLabel;
        this.nonLocalizedLabel = charSequence;
        if (charSequence != null) {
            this.nonLocalizedLabel = charSequence.toString().trim();
        }
        this.icon = packageItemInfo.icon;
        this.banner = packageItemInfo.banner;
        this.logo = packageItemInfo.logo;
        this.metaData = packageItemInfo.metaData;
        this.showUserIcon = packageItemInfo.showUserIcon;
        this.isArchived = packageItemInfo.isArchived;
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        if (sForceSafeLabels && !Objects.equals(this.packageName, ActivityThread.currentPackageName())) {
            return loadSafeLabel(packageManager, 1000.0f, 5);
        }
        return TextUtils.trimToSize(loadUnsafeLabel(packageManager), 1000);
    }

    public CharSequence loadUnsafeLabel(PackageManager packageManager) {
        CharSequence text;
        if (SystemProperties.getBoolean("sys.knox.app_name_change", false)) {
            ApplicationPolicy applicationPolicy = EnterpriseDeviceManager.getInstance().getApplicationPolicy();
            ApplicationInfo applicationInfo = getApplicationInfo();
            String applicationNameFromDb = applicationPolicy.getApplicationNameFromDb(this.packageName, applicationInfo != null ? UserHandle.getUserId(applicationInfo.uid) : 0);
            if (applicationNameFromDb != null) {
                return applicationNameFromDb;
            }
        }
        CharSequence charSequence = this.nonLocalizedLabel;
        if (charSequence != null) {
            return charSequence;
        }
        int i = this.labelRes;
        if (i != 0 && (text = packageManager.getText(this.packageName, i, getApplicationInfo())) != null) {
            return text.toString().trim();
        }
        String str = this.name;
        return str != null ? str : this.packageName;
    }

    @SystemApi
    @Deprecated
    public CharSequence loadSafeLabel(PackageManager packageManager) {
        return loadSafeLabel(packageManager, 1000.0f, 5);
    }

    @SystemApi
    public CharSequence loadSafeLabel(PackageManager packageManager, float f, int i) {
        Objects.requireNonNull(packageManager);
        return TextUtils.makeSafeForPresentation(loadUnsafeLabel(packageManager).toString(), 1000, f, i);
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return loadIcon(packageManager, false, 0);
    }

    public Drawable loadIcon(PackageManager packageManager, boolean z, int i) {
        return packageManager.loadItemIcon(this, getApplicationInfo(), z, i);
    }

    public Drawable loadUnbadgedIcon(PackageManager packageManager) {
        return packageManager.loadUnbadgedItemIcon(this, getApplicationInfo());
    }

    public Drawable loadBanner(PackageManager packageManager) {
        Drawable drawable;
        int i = this.banner;
        return (i == 0 || (drawable = packageManager.getDrawable(this.packageName, i, getApplicationInfo())) == null) ? loadDefaultBanner(packageManager) : drawable;
    }

    public Drawable loadDefaultIcon(PackageManager packageManager) {
        return packageManager.getDefaultActivityIcon();
    }

    public Drawable loadLogo(PackageManager packageManager) {
        Drawable drawable;
        int i = this.logo;
        return (i == 0 || (drawable = packageManager.getDrawable(this.packageName, i, getApplicationInfo())) == null) ? loadDefaultLogo(packageManager) : drawable;
    }

    public XmlResourceParser loadXmlMetaData(PackageManager packageManager, String str) {
        int i;
        Bundle bundle = this.metaData;
        if (bundle == null || (i = bundle.getInt(str)) == 0) {
            return null;
        }
        return packageManager.getXml(this.packageName, i, getApplicationInfo());
    }

    protected void dumpFront(Printer printer, String str) {
        if (this.name != null) {
            printer.println(str + "name=" + this.name);
        }
        printer.println(str + "packageName=" + this.packageName);
        if (this.labelRes == 0 && this.nonLocalizedLabel == null && this.icon == 0 && this.banner == 0) {
            return;
        }
        printer.println(str + "labelRes=0x" + Integer.toHexString(this.labelRes) + " nonLocalizedLabel=" + ((Object) this.nonLocalizedLabel) + " icon=0x" + Integer.toHexString(this.icon) + " banner=0x" + Integer.toHexString(this.banner));
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.name);
        parcel.writeString8(this.packageName);
        parcel.writeInt(this.labelRes);
        TextUtils.writeToParcel(this.nonLocalizedLabel, parcel, i);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.logo);
        parcel.writeBundle(this.metaData);
        parcel.writeInt(this.banner);
        parcel.writeInt(this.showUserIcon);
        parcel.writeBoolean(this.isArchived);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        String str = this.name;
        if (str != null) {
            protoOutputStream.write(1138166333441L, str);
        }
        protoOutputStream.write(1138166333442L, this.packageName);
        protoOutputStream.write(1120986464259L, this.labelRes);
        CharSequence charSequence = this.nonLocalizedLabel;
        if (charSequence != null) {
            protoOutputStream.write(1138166333444L, charSequence.toString());
        }
        protoOutputStream.write(1120986464261L, this.icon);
        protoOutputStream.write(1120986464262L, this.banner);
        protoOutputStream.write(1133871366151L, this.isArchived);
        protoOutputStream.end(start);
    }

    protected PackageItemInfo(Parcel parcel) {
        this.name = parcel.readString8();
        this.packageName = parcel.readString8();
        this.labelRes = parcel.readInt();
        this.nonLocalizedLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.icon = parcel.readInt();
        this.logo = parcel.readInt();
        this.metaData = parcel.readBundle();
        this.banner = parcel.readInt();
        this.showUserIcon = parcel.readInt();
        this.isArchived = parcel.readBoolean();
    }

    public static class DisplayNameComparator implements Comparator<PackageItemInfo> {
        private final PackageManager mPM;
        private final Collator sCollator = Collator.getInstance();

        public DisplayNameComparator(PackageManager packageManager) {
            this.mPM = packageManager;
        }

        @Override // java.util.Comparator
        public final int compare(PackageItemInfo packageItemInfo, PackageItemInfo packageItemInfo2) {
            CharSequence loadLabel = packageItemInfo.loadLabel(this.mPM);
            if (loadLabel == null) {
                loadLabel = packageItemInfo.name;
            }
            CharSequence loadLabel2 = packageItemInfo2.loadLabel(this.mPM);
            if (loadLabel2 == null) {
                loadLabel2 = packageItemInfo2.name;
            }
            return this.sCollator.compare(loadLabel.toString(), loadLabel2.toString());
        }
    }
}
