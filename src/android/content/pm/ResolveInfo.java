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

/* loaded from: classes.dex */
public class ResolveInfo implements Parcelable {
    public static final Parcelable.Creator<ResolveInfo> CREATOR = new Parcelable.Creator<ResolveInfo>() { // from class: android.content.pm.ResolveInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResolveInfo createFromParcel(Parcel parcel) {
            return new ResolveInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResolveInfo[] newArray(int i) {
            return new ResolveInfo[i];
        }
    };
    private static final String INTENT_FORWARDER_ACTIVITY = "com.android.internal.app.IntentForwarderActivity";
    private static final String TAG = "ResolveInfo";
    public ActivityInfo activityInfo;
    public AuxiliaryResolveInfo auxiliaryInfo;
    public IntentFilter filter;

    @SystemApi
    public boolean handleAllWebDataURI;
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ComponentInfo getComponentInfo() {
        ActivityInfo activityInfo = this.activityInfo;
        if (activityInfo != null) {
            return activityInfo;
        }
        ServiceInfo serviceInfo = this.serviceInfo;
        if (serviceInfo != null) {
            return serviceInfo;
        }
        ProviderInfo providerInfo = this.providerInfo;
        if (providerInfo != null) {
            return providerInfo;
        }
        throw new IllegalStateException("Missing ComponentInfo!");
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        CharSequence text;
        int i;
        CharSequence text2;
        CharSequence charSequence = this.nonLocalizedLabel;
        if (charSequence != null) {
            return charSequence;
        }
        Objects.requireNonNull(packageManager);
        String str = this.resolvePackageName;
        if (str != null && (i = this.labelRes) != 0 && (text2 = packageManager.getText(str, i, null)) != null) {
            return text2.toString().trim();
        }
        ComponentInfo componentInfo = getComponentInfo();
        ApplicationInfo applicationInfo = componentInfo.applicationInfo;
        if (this.labelRes != 0 && (text = packageManager.getText(componentInfo.packageName, this.labelRes, applicationInfo)) != null) {
            return text.toString().trim();
        }
        CharSequence charSequenceLoadLabel = componentInfo.loadLabel(packageManager);
        return charSequenceLoadLabel != null ? charSequenceLoadLabel.toString().trim() : charSequenceLoadLabel;
    }

    public int resolveLabelResId() {
        int i = this.labelRes;
        if (i != 0) {
            return i;
        }
        ComponentInfo componentInfo = getComponentInfo();
        if (componentInfo.labelRes != 0) {
            return componentInfo.labelRes;
        }
        return componentInfo.applicationInfo.labelRes;
    }

    public int resolveIconResId() {
        int i = this.icon;
        if (i != 0) {
            return i;
        }
        ComponentInfo componentInfo = getComponentInfo();
        if (componentInfo.icon != 0) {
            return componentInfo.icon;
        }
        return componentInfo.applicationInfo.icon;
    }

    public Drawable loadIcon(PackageManager packageManager) {
        int i;
        String str = this.resolvePackageName;
        Drawable drawable = null;
        if (str != null && (i = this.iconResourceId) != 0) {
            drawable = packageManager.getDrawable(str, i, null);
        }
        ComponentInfo componentInfo = getComponentInfo();
        if (drawable == null && this.iconResourceId != 0) {
            drawable = packageManager.getDrawable(componentInfo.packageName, this.iconResourceId, componentInfo.applicationInfo);
        }
        if (drawable != null) {
            return packageManager.getUserBadgedIcon(drawable, new UserHandle(packageManager.getUserId()));
        }
        return componentInfo.loadIcon(packageManager);
    }

    final int getIconResourceInternal() {
        int i = this.iconResourceId;
        if (i != 0) {
            return i;
        }
        ComponentInfo componentInfo = getComponentInfo();
        if (componentInfo != null) {
            return componentInfo.getIconResource();
        }
        return 0;
    }

    public final int getIconResource() {
        if (this.noResourceId) {
            return 0;
        }
        return getIconResourceInternal();
    }

    public void dump(Printer printer, String str) {
        dump(printer, str, 3);
    }

    public void dump(Printer printer, String str, int i) {
        if (this.filter != null) {
            printer.println(str + "Filter:");
            this.filter.dump(printer, str + "  ");
        }
        printer.println(str + "priority=" + this.priority + " preferredOrder=" + this.preferredOrder + " match=0x" + Integer.toHexString(this.match) + " specificIndex=" + this.specificIndex + " isDefault=" + this.isDefault);
        if (this.resolvePackageName != null) {
            printer.println(str + "resolvePackageName=" + this.resolvePackageName);
        }
        if (this.labelRes != 0 || this.nonLocalizedLabel != null || this.icon != 0) {
            printer.println(str + "labelRes=0x" + Integer.toHexString(this.labelRes) + " nonLocalizedLabel=" + ((Object) this.nonLocalizedLabel) + " icon=0x" + Integer.toHexString(this.icon));
        }
        if (this.activityInfo != null) {
            printer.println(str + "ActivityInfo:");
            this.activityInfo.dump(printer, str + "  ", i);
            return;
        }
        if (this.serviceInfo != null) {
            printer.println(str + "ServiceInfo:");
            this.serviceInfo.dump(printer, str + "  ", i);
            return;
        }
        if (this.providerInfo != null) {
            printer.println(str + "ProviderInfo:");
            this.providerInfo.dump(printer, str + "  ", i);
        }
    }

    public boolean isCrossProfileIntentForwarderActivity() {
        ActivityInfo activityInfo = this.activityInfo;
        return activityInfo != null && INTENT_FORWARDER_ACTIVITY.equals(activityInfo.targetActivity);
    }

    public boolean isAutoResolutionAllowed() {
        return this.mAutoResolutionAllowed;
    }

    public ResolveInfo() {
        this.specificIndex = -1;
        this.targetUserId = -2;
        this.mAutoResolutionAllowed = false;
    }

    public ResolveInfo(boolean z) {
        this.specificIndex = -1;
        this.targetUserId = -2;
        this.mAutoResolutionAllowed = z;
    }

    public ResolveInfo(ResolveInfo resolveInfo) {
        this.specificIndex = -1;
        this.activityInfo = resolveInfo.activityInfo;
        this.serviceInfo = resolveInfo.serviceInfo;
        this.providerInfo = resolveInfo.providerInfo;
        this.filter = resolveInfo.filter;
        this.priority = resolveInfo.priority;
        this.preferredOrder = resolveInfo.preferredOrder;
        this.match = resolveInfo.match;
        this.specificIndex = resolveInfo.specificIndex;
        this.labelRes = resolveInfo.labelRes;
        this.nonLocalizedLabel = resolveInfo.nonLocalizedLabel;
        this.icon = resolveInfo.icon;
        this.resolvePackageName = resolveInfo.resolvePackageName;
        this.noResourceId = resolveInfo.noResourceId;
        this.iconResourceId = resolveInfo.iconResourceId;
        this.system = resolveInfo.system;
        this.targetUserId = resolveInfo.targetUserId;
        this.handleAllWebDataURI = resolveInfo.handleAllWebDataURI;
        this.mAutoResolutionAllowed = resolveInfo.mAutoResolutionAllowed;
        this.isInstantAppAvailable = resolveInfo.isInstantAppAvailable;
        this.userHandle = resolveInfo.userHandle;
    }

    public String toString() {
        ComponentInfo componentInfo = getComponentInfo();
        StringBuilder sb = new StringBuilder(128);
        sb.append("ResolveInfo{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(' ');
        ComponentName.appendShortString(sb, componentInfo.packageName, componentInfo.name);
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
        UserHandle userHandle = this.userHandle;
        parcel.writeInt(userHandle != null ? userHandle.getIdentifier() : -2);
    }

    private ResolveInfo(Parcel parcel) {
        this.specificIndex = -1;
        this.activityInfo = null;
        this.serviceInfo = null;
        this.providerInfo = null;
        int i = parcel.readInt();
        if (i == 1) {
            this.activityInfo = ActivityInfo.CREATOR.createFromParcel(parcel);
        } else if (i == 2) {
            this.serviceInfo = ServiceInfo.CREATOR.createFromParcel(parcel);
        } else if (i == 3) {
            this.providerInfo = ProviderInfo.CREATOR.createFromParcel(parcel);
        } else {
            Slog.w(TAG, "Missing ComponentInfo!");
        }
        if (parcel.readInt() != 0) {
            this.filter = IntentFilter.CREATOR.createFromParcel(parcel);
        }
        this.priority = parcel.readInt();
        this.preferredOrder = parcel.readInt();
        this.match = parcel.readInt();
        this.specificIndex = parcel.readInt();
        this.labelRes = parcel.readInt();
        this.nonLocalizedLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.icon = parcel.readInt();
        this.resolvePackageName = parcel.readString8();
        this.targetUserId = parcel.readInt();
        this.system = parcel.readInt() != 0;
        this.noResourceId = parcel.readInt() != 0;
        this.iconResourceId = parcel.readInt();
        this.handleAllWebDataURI = parcel.readInt() != 0;
        this.mAutoResolutionAllowed = parcel.readInt() != 0;
        this.isInstantAppAvailable = parcel.readInt() != 0;
        int i2 = parcel.readInt();
        if (i2 != -2) {
            this.userHandle = UserHandle.of(i2);
        }
    }

    public static class DisplayNameComparator implements Comparator<ResolveInfo> {
        private final Collator mCollator;
        private final PackageManager mPM;

        public DisplayNameComparator(PackageManager packageManager) {
            Collator collator = Collator.getInstance();
            this.mCollator = collator;
            this.mPM = packageManager;
            collator.setStrength(0);
        }

        @Override // java.util.Comparator
        public final int compare(ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
            if (resolveInfo.targetUserId != -2) {
                return 1;
            }
            if (resolveInfo2.targetUserId != -2) {
                return -1;
            }
            CharSequence charSequenceLoadLabel = resolveInfo.loadLabel(this.mPM);
            if (charSequenceLoadLabel == null) {
                charSequenceLoadLabel = resolveInfo.activityInfo.name;
            }
            CharSequence charSequenceLoadLabel2 = resolveInfo2.loadLabel(this.mPM);
            if (charSequenceLoadLabel2 == null) {
                charSequenceLoadLabel2 = resolveInfo2.activityInfo.name;
            }
            return this.mCollator.compare(charSequenceLoadLabel.toString(), charSequenceLoadLabel2.toString());
        }
    }
}
