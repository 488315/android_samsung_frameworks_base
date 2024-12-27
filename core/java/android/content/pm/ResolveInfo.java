package android.content.pm;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Printer;
import android.util.Slog;

import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

public class ResolveInfo implements Parcelable {
    public static final Parcelable.Creator<ResolveInfo> CREATOR =
            new Parcelable.Creator<ResolveInfo>() { // from class: android.content.pm.ResolveInfo.1
                @Override // android.os.Parcelable.Creator
                public ResolveInfo createFromParcel(Parcel source) {
                    return new ResolveInfo(source);
                }

                @Override // android.os.Parcelable.Creator
                public ResolveInfo[] newArray(int size) {
                    return new ResolveInfo[size];
                }
            };
    private static final String INTENT_FORWARDER_ACTIVITY =
            "com.android.internal.app.IntentForwarderActivity";
    private static final String TAG = "ResolveInfo";
    public ActivityInfo activityInfo;
    public AuxiliaryResolveInfo auxiliaryInfo;
    public IntentFilter filter;

    @SystemApi public boolean handleAllWebDataURI;
    public int icon;
    public int iconResourceId;
    public boolean isDefault;
    public boolean isInstantAppAvailable;
    public int labelRes;
    private final boolean mAutoResolutionAllowed;
    public int match;
    public boolean noResourceId;
    public CharSequence nonLocalizedLabel;
    public int preferredOrder;
    public int priority;
    public ProviderInfo providerInfo;
    public String resolvePackageName;
    public ServiceInfo serviceInfo;
    public int specificIndex;
    public boolean system;
    public int targetUserId;
    public UserHandle userHandle;

    public ComponentInfo getComponentInfo() {
        if (this.activityInfo != null) {
            return this.activityInfo;
        }
        if (this.serviceInfo != null) {
            return this.serviceInfo;
        }
        if (this.providerInfo != null) {
            return this.providerInfo;
        }
        throw new IllegalStateException("Missing ComponentInfo!");
    }

    public CharSequence loadLabel(PackageManager pm) {
        CharSequence label;
        CharSequence label2;
        if (this.nonLocalizedLabel != null) {
            return this.nonLocalizedLabel;
        }
        Objects.requireNonNull(pm);
        if (this.resolvePackageName != null
                && this.labelRes != 0
                && (label2 = pm.getText(this.resolvePackageName, this.labelRes, null)) != null) {
            return label2.toString().trim();
        }
        ComponentInfo ci = getComponentInfo();
        ApplicationInfo ai = ci.applicationInfo;
        if (this.labelRes != 0 && (label = pm.getText(ci.packageName, this.labelRes, ai)) != null) {
            return label.toString().trim();
        }
        CharSequence data = ci.loadLabel(pm);
        return data != null ? data.toString().trim() : data;
    }

    public int resolveLabelResId() {
        if (this.labelRes != 0) {
            return this.labelRes;
        }
        ComponentInfo componentInfo = getComponentInfo();
        if (componentInfo.labelRes != 0) {
            return componentInfo.labelRes;
        }
        return componentInfo.applicationInfo.labelRes;
    }

    public int resolveIconResId() {
        if (this.icon != 0) {
            return this.icon;
        }
        ComponentInfo componentInfo = getComponentInfo();
        if (componentInfo.icon != 0) {
            return componentInfo.icon;
        }
        return componentInfo.applicationInfo.icon;
    }

    public Drawable loadIcon(PackageManager pm) {
        Drawable dr = null;
        if (this.resolvePackageName != null && this.iconResourceId != 0) {
            dr = pm.getDrawable(this.resolvePackageName, this.iconResourceId, null);
        }
        ComponentInfo ci = getComponentInfo();
        if (dr == null && this.iconResourceId != 0) {
            ApplicationInfo ai = ci.applicationInfo;
            dr = pm.getDrawable(ci.packageName, this.iconResourceId, ai);
        }
        if (dr != null) {
            return pm.getUserBadgedIcon(dr, new UserHandle(pm.getUserId()));
        }
        return ci.loadIcon(pm);
    }

    final int getIconResourceInternal() {
        if (this.iconResourceId != 0) {
            return this.iconResourceId;
        }
        ComponentInfo ci = getComponentInfo();
        if (ci != null) {
            return ci.getIconResource();
        }
        return 0;
    }

    public final int getIconResource() {
        if (this.noResourceId) {
            return 0;
        }
        return getIconResourceInternal();
    }

    public void dump(Printer pw, String prefix) {
        dump(pw, prefix, 3);
    }

    public void dump(Printer pw, String prefix, int dumpFlags) {
        if (this.filter != null) {
            pw.println(prefix + "Filter:");
            this.filter.dump(pw, prefix + "  ");
        }
        pw.println(
                prefix
                        + "priority="
                        + this.priority
                        + " preferredOrder="
                        + this.preferredOrder
                        + " match=0x"
                        + Integer.toHexString(this.match)
                        + " specificIndex="
                        + this.specificIndex
                        + " isDefault="
                        + this.isDefault);
        if (this.resolvePackageName != null) {
            pw.println(prefix + "resolvePackageName=" + this.resolvePackageName);
        }
        if (this.labelRes != 0 || this.nonLocalizedLabel != null || this.icon != 0) {
            pw.println(
                    prefix
                            + "labelRes=0x"
                            + Integer.toHexString(this.labelRes)
                            + " nonLocalizedLabel="
                            + ((Object) this.nonLocalizedLabel)
                            + " icon=0x"
                            + Integer.toHexString(this.icon));
        }
        if (this.activityInfo != null) {
            pw.println(prefix + "ActivityInfo:");
            this.activityInfo.dump(pw, prefix + "  ", dumpFlags);
        } else if (this.serviceInfo != null) {
            pw.println(prefix + "ServiceInfo:");
            this.serviceInfo.dump(pw, prefix + "  ", dumpFlags);
        } else if (this.providerInfo != null) {
            pw.println(prefix + "ProviderInfo:");
            this.providerInfo.dump(pw, prefix + "  ", dumpFlags);
        }
    }

    public boolean isCrossProfileIntentForwarderActivity() {
        return this.activityInfo != null
                && INTENT_FORWARDER_ACTIVITY.equals(this.activityInfo.targetActivity);
    }

    public boolean isAutoResolutionAllowed() {
        return this.mAutoResolutionAllowed;
    }

    public ResolveInfo() {
        this.specificIndex = -1;
        this.targetUserId = -2;
        this.mAutoResolutionAllowed = false;
    }

    public ResolveInfo(boolean autoResolutionAllowed) {
        this.specificIndex = -1;
        this.targetUserId = -2;
        this.mAutoResolutionAllowed = autoResolutionAllowed;
    }

    public ResolveInfo(ResolveInfo orig) {
        this.specificIndex = -1;
        this.activityInfo = orig.activityInfo;
        this.serviceInfo = orig.serviceInfo;
        this.providerInfo = orig.providerInfo;
        this.filter = orig.filter;
        this.priority = orig.priority;
        this.preferredOrder = orig.preferredOrder;
        this.match = orig.match;
        this.specificIndex = orig.specificIndex;
        this.labelRes = orig.labelRes;
        this.nonLocalizedLabel = orig.nonLocalizedLabel;
        this.icon = orig.icon;
        this.resolvePackageName = orig.resolvePackageName;
        this.noResourceId = orig.noResourceId;
        this.iconResourceId = orig.iconResourceId;
        this.system = orig.system;
        this.targetUserId = orig.targetUserId;
        this.handleAllWebDataURI = orig.handleAllWebDataURI;
        this.mAutoResolutionAllowed = orig.mAutoResolutionAllowed;
        this.isInstantAppAvailable = orig.isInstantAppAvailable;
        this.userHandle = orig.userHandle;
    }

    public String toString() {
        ComponentInfo ci = getComponentInfo();
        StringBuilder sb = new StringBuilder(128);
        sb.append("ResolveInfo{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(' ');
        ComponentName.appendShortString(sb, ci.packageName, ci.name);
        if (this.priority != 0) {
            sb.append(" p=");
            sb.append(this.priority);
        }
        if (this.preferredOrder != 0) {
            sb.append(" o=");
            sb.append(this.preferredOrder);
        }
        sb.append(" m=0x");
        sb.append(Integer.toHexString(this.match));
        if (this.targetUserId != -2) {
            sb.append(" targetUserId=");
            sb.append(this.targetUserId);
        }
        sb.append(" userHandle=");
        sb.append(this.userHandle);
        sb.append('}');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.activityInfo != null) {
            parcel.writeInt(1);
            this.activityInfo.writeToParcel(parcel, i);
        } else if (this.serviceInfo != null) {
            parcel.writeInt(2);
            this.serviceInfo.writeToParcel(parcel, i);
        } else if (this.providerInfo != null) {
            parcel.writeInt(3);
            this.providerInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.filter != null) {
            parcel.writeInt(1);
            this.filter.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.priority);
        parcel.writeInt(this.preferredOrder);
        parcel.writeInt(this.match);
        parcel.writeInt(this.specificIndex);
        parcel.writeInt(this.labelRes);
        TextUtils.writeToParcel(this.nonLocalizedLabel, parcel, i);
        parcel.writeInt(this.icon);
        parcel.writeString8(this.resolvePackageName);
        parcel.writeInt(this.targetUserId);
        parcel.writeInt(this.system ? 1 : 0);
        parcel.writeInt(this.noResourceId ? 1 : 0);
        parcel.writeInt(this.iconResourceId);
        parcel.writeInt(this.handleAllWebDataURI ? 1 : 0);
        parcel.writeInt(this.mAutoResolutionAllowed ? 1 : 0);
        parcel.writeInt(this.isInstantAppAvailable ? 1 : 0);
        parcel.writeInt(this.userHandle != null ? this.userHandle.getIdentifier() : -2);
    }

    private ResolveInfo(Parcel source) {
        this.specificIndex = -1;
        this.activityInfo = null;
        this.serviceInfo = null;
        this.providerInfo = null;
        switch (source.readInt()) {
            case 1:
                this.activityInfo = ActivityInfo.CREATOR.createFromParcel(source);
                break;
            case 2:
                this.serviceInfo = ServiceInfo.CREATOR.createFromParcel(source);
                break;
            case 3:
                this.providerInfo = ProviderInfo.CREATOR.createFromParcel(source);
                break;
            default:
                Slog.w(TAG, "Missing ComponentInfo!");
                break;
        }
        if (source.readInt() != 0) {
            this.filter = IntentFilter.CREATOR.createFromParcel(source);
        }
        this.priority = source.readInt();
        this.preferredOrder = source.readInt();
        this.match = source.readInt();
        this.specificIndex = source.readInt();
        this.labelRes = source.readInt();
        this.nonLocalizedLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        this.icon = source.readInt();
        this.resolvePackageName = source.readString8();
        this.targetUserId = source.readInt();
        this.system = source.readInt() != 0;
        this.noResourceId = source.readInt() != 0;
        this.iconResourceId = source.readInt();
        this.handleAllWebDataURI = source.readInt() != 0;
        this.mAutoResolutionAllowed = source.readInt() != 0;
        this.isInstantAppAvailable = source.readInt() != 0;
        int userHandleId = source.readInt();
        if (userHandleId != -2) {
            this.userHandle = UserHandle.of(userHandleId);
        }
    }

    public static class DisplayNameComparator implements Comparator<ResolveInfo> {
        private final Collator mCollator = Collator.getInstance();
        private final PackageManager mPM;

        public DisplayNameComparator(PackageManager pm) {
            this.mPM = pm;
            this.mCollator.setStrength(0);
        }

        @Override // java.util.Comparator
        public final int compare(ResolveInfo a, ResolveInfo b) {
            if (a.targetUserId != -2) {
                return 1;
            }
            if (b.targetUserId != -2) {
                return -1;
            }
            CharSequence sa = a.loadLabel(this.mPM);
            if (sa == null) {
                sa = a.activityInfo.name;
            }
            CharSequence sb = b.loadLabel(this.mPM);
            if (sb == null) {
                sb = b.activityInfo.name;
            }
            return this.mCollator.compare(sa.toString(), sb.toString());
        }
    }
}
