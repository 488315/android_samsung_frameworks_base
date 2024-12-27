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

    @SystemApi @Deprecated public static final int SAFE_LABEL_FLAG_FIRST_LINE = 4;

    @SystemApi @Deprecated public static final int SAFE_LABEL_FLAG_SINGLE_LINE = 2;

    @SystemApi @Deprecated public static final int SAFE_LABEL_FLAG_TRIM = 1;
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

    @SystemApi
    public static void forceSafeLabels() {
        sForceSafeLabels = true;
    }

    public PackageItemInfo() {
        this.showUserIcon = -10000;
    }

    public PackageItemInfo(PackageItemInfo orig) {
        this.name = orig.name;
        if (this.name != null) {
            this.name = this.name.trim();
        }
        this.packageName = orig.packageName;
        this.labelRes = orig.labelRes;
        this.nonLocalizedLabel = orig.nonLocalizedLabel;
        if (this.nonLocalizedLabel != null) {
            this.nonLocalizedLabel = this.nonLocalizedLabel.toString().trim();
        }
        this.icon = orig.icon;
        this.banner = orig.banner;
        this.logo = orig.logo;
        this.metaData = orig.metaData;
        this.showUserIcon = orig.showUserIcon;
        this.isArchived = orig.isArchived;
    }

    public CharSequence loadLabel(PackageManager pm) {
        if (sForceSafeLabels
                && !Objects.equals(this.packageName, ActivityThread.currentPackageName())) {
            return loadSafeLabel(pm, 1000.0f, 5);
        }
        return TextUtils.trimToSize(loadUnsafeLabel(pm), 1000);
    }

    public CharSequence loadUnsafeLabel(PackageManager pm) {
        CharSequence label;
        boolean check = SystemProperties.getBoolean("sys.knox.app_name_change", false);
        if (check) {
            ApplicationPolicy appPolicy =
                    EnterpriseDeviceManager.getInstance().getApplicationPolicy();
            int userId = 0;
            ApplicationInfo ai = getApplicationInfo();
            if (ai != null) {
                userId = UserHandle.getUserId(ai.uid);
            }
            String newName = appPolicy.getApplicationNameFromDb(this.packageName, userId);
            if (newName != null) {
                return newName;
            }
        }
        if (this.nonLocalizedLabel != null) {
            return this.nonLocalizedLabel;
        }
        if (this.labelRes != 0
                && (label = pm.getText(this.packageName, this.labelRes, getApplicationInfo()))
                        != null) {
            return label.toString().trim();
        }
        if (this.name != null) {
            return this.name;
        }
        return this.packageName;
    }

    @SystemApi
    @Deprecated
    public CharSequence loadSafeLabel(PackageManager pm) {
        return loadSafeLabel(pm, 1000.0f, 5);
    }

    @SystemApi
    public CharSequence loadSafeLabel(PackageManager pm, float ellipsizeDip, int flags) {
        Objects.requireNonNull(pm);
        return TextUtils.makeSafeForPresentation(
                loadUnsafeLabel(pm).toString(), 1000, ellipsizeDip, flags);
    }

    public Drawable loadIcon(PackageManager pm) {
        return loadIcon(pm, false, 0);
    }

    public Drawable loadIcon(PackageManager pm, boolean forIconTray, int mode) {
        return pm.loadItemIcon(this, getApplicationInfo(), forIconTray, mode);
    }

    public Drawable loadUnbadgedIcon(PackageManager pm) {
        return pm.loadUnbadgedItemIcon(this, getApplicationInfo());
    }

    public Drawable loadBanner(PackageManager pm) {
        Drawable dr;
        if (this.banner != 0
                && (dr = pm.getDrawable(this.packageName, this.banner, getApplicationInfo()))
                        != null) {
            return dr;
        }
        return loadDefaultBanner(pm);
    }

    public Drawable loadDefaultIcon(PackageManager pm) {
        return pm.getDefaultActivityIcon();
    }

    protected Drawable loadDefaultBanner(PackageManager pm) {
        return null;
    }

    public Drawable loadLogo(PackageManager pm) {
        Drawable d;
        if (this.logo != 0
                && (d = pm.getDrawable(this.packageName, this.logo, getApplicationInfo()))
                        != null) {
            return d;
        }
        return loadDefaultLogo(pm);
    }

    protected Drawable loadDefaultLogo(PackageManager pm) {
        return null;
    }

    public XmlResourceParser loadXmlMetaData(PackageManager pm, String name) {
        int resid;
        if (this.metaData != null && (resid = this.metaData.getInt(name)) != 0) {
            return pm.getXml(this.packageName, resid, getApplicationInfo());
        }
        return null;
    }

    protected void dumpFront(Printer pw, String prefix) {
        if (this.name != null) {
            pw.println(prefix + "name=" + this.name);
        }
        pw.println(prefix + "packageName=" + this.packageName);
        if (this.labelRes != 0
                || this.nonLocalizedLabel != null
                || this.icon != 0
                || this.banner != 0) {
            pw.println(
                    prefix
                            + "labelRes=0x"
                            + Integer.toHexString(this.labelRes)
                            + " nonLocalizedLabel="
                            + ((Object) this.nonLocalizedLabel)
                            + " icon=0x"
                            + Integer.toHexString(this.icon)
                            + " banner=0x"
                            + Integer.toHexString(this.banner));
        }
    }

    protected void dumpBack(Printer pw, String prefix) {}

    public void writeToParcel(Parcel dest, int parcelableFlags) {
        dest.writeString8(this.name);
        dest.writeString8(this.packageName);
        dest.writeInt(this.labelRes);
        TextUtils.writeToParcel(this.nonLocalizedLabel, dest, parcelableFlags);
        dest.writeInt(this.icon);
        dest.writeInt(this.logo);
        dest.writeBundle(this.metaData);
        dest.writeInt(this.banner);
        dest.writeInt(this.showUserIcon);
        dest.writeBoolean(this.isArchived);
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId, int dumpFlags) {
        long token = proto.start(fieldId);
        if (this.name != null) {
            proto.write(1138166333441L, this.name);
        }
        proto.write(1138166333442L, this.packageName);
        proto.write(1120986464259L, this.labelRes);
        if (this.nonLocalizedLabel != null) {
            proto.write(1138166333444L, this.nonLocalizedLabel.toString());
        }
        proto.write(1120986464261L, this.icon);
        proto.write(1120986464262L, this.banner);
        proto.write(1133871366151L, this.isArchived);
        proto.end(token);
    }

    protected PackageItemInfo(Parcel source) {
        this.name = source.readString8();
        this.packageName = source.readString8();
        this.labelRes = source.readInt();
        this.nonLocalizedLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        this.icon = source.readInt();
        this.logo = source.readInt();
        this.metaData = source.readBundle();
        this.banner = source.readInt();
        this.showUserIcon = source.readInt();
        this.isArchived = source.readBoolean();
    }

    public ApplicationInfo getApplicationInfo() {
        return null;
    }

    public static class DisplayNameComparator implements Comparator<PackageItemInfo> {
        private final PackageManager mPM;
        private final Collator sCollator = Collator.getInstance();

        public DisplayNameComparator(PackageManager pm) {
            this.mPM = pm;
        }

        @Override // java.util.Comparator
        public final int compare(PackageItemInfo aa, PackageItemInfo ab) {
            CharSequence sa = aa.loadLabel(this.mPM);
            if (sa == null) {
                sa = aa.name;
            }
            CharSequence sb = ab.loadLabel(this.mPM);
            if (sb == null) {
                sb = ab.name;
            }
            return this.sCollator.compare(sa.toString(), sb.toString());
        }
    }
}
