package android.app;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.wallpaper.WallpaperService;
import android.util.AttributeSet;
import android.util.Printer;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class WallpaperInfo implements Parcelable {
    public static final Parcelable.Creator<WallpaperInfo> CREATOR = new Parcelable.Creator<WallpaperInfo>() { // from class: android.app.WallpaperInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WallpaperInfo createFromParcel(Parcel parcel) {
            return new WallpaperInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WallpaperInfo[] newArray(int i) {
            return new WallpaperInfo[i];
        }
    };
    static final String TAG = "WallpaperInfo";
    final int mAuthorResource;
    final int mContextDescriptionResource;
    final int mContextUriResource;
    final int mDescriptionResource;
    final ResolveInfo mService;
    final String mSettingsActivityName;
    final String mSettingsSliceUri;
    final boolean mShouldUseDefaultUnfoldTransition;
    final boolean mShowMetadataInPreview;
    final boolean mSupportMultipleDisplays;
    final boolean mSupportsAmbientMode;
    final int mThumbnailResource;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WallpaperInfo(Context context, ResolveInfo resolveInfo) throws XmlPullParserException, IOException {
        int next;
        this.mService = resolveInfo;
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        PackageManager packageManager = context.getPackageManager();
        try {
            XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, WallpaperService.SERVICE_META_DATA);
            try {
                if (loadXmlMetaData == null) {
                    throw new XmlPullParserException("No android.service.wallpaper meta-data");
                }
                Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!"wallpaper".equals(loadXmlMetaData.getName())) {
                    throw new XmlPullParserException("Meta-data does not start with wallpaper tag");
                }
                TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.Wallpaper);
                this.mSettingsActivityName = obtainAttributes.getString(1);
                this.mThumbnailResource = obtainAttributes.getResourceId(2, -1);
                this.mAuthorResource = obtainAttributes.getResourceId(3, -1);
                this.mDescriptionResource = obtainAttributes.getResourceId(0, -1);
                this.mContextUriResource = obtainAttributes.getResourceId(4, -1);
                this.mContextDescriptionResource = obtainAttributes.getResourceId(5, -1);
                this.mShowMetadataInPreview = obtainAttributes.getBoolean(6, false);
                this.mSupportsAmbientMode = obtainAttributes.getBoolean(7, packageManager.hasSystemFeature(PackageManager.FEATURE_WATCH));
                this.mShouldUseDefaultUnfoldTransition = obtainAttributes.getBoolean(10, true);
                this.mSettingsSliceUri = obtainAttributes.getString(8);
                this.mSupportMultipleDisplays = obtainAttributes.getBoolean(9, false);
                obtainAttributes.recycle();
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
            } finally {
            }
        } catch (PackageManager.NameNotFoundException unused) {
            throw new XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
        }
    }

    WallpaperInfo(Parcel parcel) {
        this.mSettingsActivityName = parcel.readString();
        this.mThumbnailResource = parcel.readInt();
        this.mAuthorResource = parcel.readInt();
        this.mDescriptionResource = parcel.readInt();
        this.mContextUriResource = parcel.readInt();
        this.mContextDescriptionResource = parcel.readInt();
        this.mShowMetadataInPreview = parcel.readInt() != 0;
        this.mSupportsAmbientMode = parcel.readInt() != 0;
        this.mSettingsSliceUri = parcel.readString();
        this.mSupportMultipleDisplays = parcel.readInt() != 0;
        this.mShouldUseDefaultUnfoldTransition = parcel.readInt() != 0;
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
    }

    public String getPackageName() {
        return this.mService.serviceInfo.packageName;
    }

    public String getServiceName() {
        return this.mService.serviceInfo.name;
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public Drawable loadThumbnail(PackageManager packageManager) {
        if (this.mThumbnailResource < 0) {
            return null;
        }
        return packageManager.getDrawable(this.mService.serviceInfo.packageName, this.mThumbnailResource, this.mService.serviceInfo.applicationInfo);
    }

    public CharSequence loadAuthor(PackageManager packageManager) throws Resources.NotFoundException {
        ApplicationInfo applicationInfo;
        if (this.mAuthorResource <= 0) {
            throw new Resources.NotFoundException();
        }
        String str = this.mService.resolvePackageName;
        if (str == null) {
            str = this.mService.serviceInfo.packageName;
            applicationInfo = this.mService.serviceInfo.applicationInfo;
        } else {
            applicationInfo = null;
        }
        return packageManager.getText(str, this.mAuthorResource, applicationInfo);
    }

    public CharSequence loadDescription(PackageManager packageManager) throws Resources.NotFoundException {
        ApplicationInfo applicationInfo;
        String str = this.mService.resolvePackageName;
        if (str == null) {
            str = this.mService.serviceInfo.packageName;
            applicationInfo = this.mService.serviceInfo.applicationInfo;
        } else {
            applicationInfo = null;
        }
        if (this.mService.serviceInfo.descriptionRes != 0) {
            return packageManager.getText(str, this.mService.serviceInfo.descriptionRes, applicationInfo);
        }
        int i = this.mDescriptionResource;
        if (i <= 0) {
            throw new Resources.NotFoundException();
        }
        return packageManager.getText(str, i, this.mService.serviceInfo.applicationInfo);
    }

    public Uri loadContextUri(PackageManager packageManager) throws Resources.NotFoundException {
        ApplicationInfo applicationInfo;
        if (this.mContextUriResource <= 0) {
            throw new Resources.NotFoundException();
        }
        String str = this.mService.resolvePackageName;
        if (str == null) {
            str = this.mService.serviceInfo.packageName;
            applicationInfo = this.mService.serviceInfo.applicationInfo;
        } else {
            applicationInfo = null;
        }
        CharSequence text = packageManager.getText(str, this.mContextUriResource, applicationInfo);
        if (text == null) {
            return null;
        }
        return Uri.parse(text.toString());
    }

    public CharSequence loadContextDescription(PackageManager packageManager) throws Resources.NotFoundException {
        ApplicationInfo applicationInfo;
        if (this.mContextDescriptionResource <= 0) {
            throw new Resources.NotFoundException();
        }
        String str = this.mService.resolvePackageName;
        if (str == null) {
            str = this.mService.serviceInfo.packageName;
            applicationInfo = this.mService.serviceInfo.applicationInfo;
        } else {
            applicationInfo = null;
        }
        return packageManager.getText(str, this.mContextDescriptionResource, applicationInfo).toString();
    }

    public boolean getShowMetadataInPreview() {
        return this.mShowMetadataInPreview;
    }

    @SystemApi
    public boolean supportsAmbientMode() {
        return this.mSupportsAmbientMode;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivityName;
    }

    public Uri getSettingsSliceUri() {
        String str = this.mSettingsSliceUri;
        if (str == null) {
            return null;
        }
        return Uri.parse(str);
    }

    public boolean supportsMultipleDisplays() {
        return this.mSupportMultipleDisplays;
    }

    public boolean shouldUseDefaultUnfoldTransition() {
        return this.mShouldUseDefaultUnfoldTransition;
    }

    public void dump(Printer printer, String str) {
        printer.println(str + "Service:");
        this.mService.dump(printer, str + "  ");
        printer.println(str + "mSettingsActivityName=" + this.mSettingsActivityName);
    }

    public String toString() {
        return "WallpaperInfo{" + this.mService.serviceInfo.name + ", settings: " + this.mSettingsActivityName + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeInt(this.mThumbnailResource);
        parcel.writeInt(this.mAuthorResource);
        parcel.writeInt(this.mDescriptionResource);
        parcel.writeInt(this.mContextUriResource);
        parcel.writeInt(this.mContextDescriptionResource);
        parcel.writeInt(this.mShowMetadataInPreview ? 1 : 0);
        parcel.writeInt(this.mSupportsAmbientMode ? 1 : 0);
        parcel.writeString(this.mSettingsSliceUri);
        parcel.writeInt(this.mSupportMultipleDisplays ? 1 : 0);
        parcel.writeInt(this.mShouldUseDefaultUnfoldTransition ? 1 : 0);
        this.mService.writeToParcel(parcel, i);
    }
}
