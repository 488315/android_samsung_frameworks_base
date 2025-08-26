package android.app.admin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Slog;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.function.Supplier;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ParcelableResource implements Parcelable {
    private static final String ATTR_PACKAGE_NAME = "package-name";
    private static final String ATTR_RESOURCE_ID = "resource-id";
    private static final String ATTR_RESOURCE_NAME = "resource-name";
    private static final String ATTR_RESOURCE_TYPE = "resource-type";
    public static final Parcelable.Creator<ParcelableResource> CREATOR = new Parcelable.Creator<ParcelableResource>() { // from class: android.app.admin.ParcelableResource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableResource createFromParcel(Parcel parcel) {
            return new ParcelableResource(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableResource[] newArray(int i) {
            return new ParcelableResource[i];
        }
    };
    public static final int RESOURCE_TYPE_DRAWABLE = 1;
    public static final int RESOURCE_TYPE_STRING = 2;
    private static String TAG = "DevicePolicyManager";
    private final String mPackageName;
    private final int mResourceId;
    private final String mResourceName;
    private final int mResourceType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResourceType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParcelableResource(Context context, int i, int i2) throws IllegalStateException, IllegalArgumentException {
        Objects.requireNonNull(context, "context must be provided");
        verifyResourceExistsInCallingPackage(context, i, i2);
        this.mResourceId = i;
        this.mPackageName = context.getResources().getResourcePackageName(i);
        this.mResourceName = context.getResources().getResourceName(i);
        this.mResourceType = i2;
    }

    private ParcelableResource(int i, String str, String str2, int i2) {
        this.mResourceId = i;
        this.mPackageName = (String) Objects.requireNonNull(str);
        this.mResourceName = (String) Objects.requireNonNull(str2);
        this.mResourceType = i2;
    }

    private static void verifyResourceExistsInCallingPackage(Context context, int i, int i2) throws IllegalStateException, IllegalArgumentException {
        if (i2 == 1) {
            if (!hasDrawableInCallingPackage(context, i)) {
                throw new IllegalStateException(String.format("Drawable with id %d doesn't exist in the calling package %s", Integer.valueOf(i), context.getPackageName()));
            }
        } else if (i2 == 2) {
            if (!hasStringInCallingPackage(context, i)) {
                throw new IllegalStateException(String.format("String with id %d doesn't exist in the calling package %s", Integer.valueOf(i), context.getPackageName()));
            }
        } else {
            throw new IllegalArgumentException("Unknown ResourceType: " + i2);
        }
    }

    private static boolean hasDrawableInCallingPackage(Context context, int i) {
        try {
            return "drawable".equals(context.getResources().getResourceTypeName(i));
        } catch (Resources.NotFoundException unused) {
            return false;
        }
    }

    private static boolean hasStringInCallingPackage(Context context, int i) {
        try {
            return "string".equals(context.getResources().getResourceTypeName(i));
        } catch (Resources.NotFoundException unused) {
            return false;
        }
    }

    public int getResourceId() {
        return this.mResourceId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getResourceName() {
        return this.mResourceName;
    }

    public int getResourceType() {
        return this.mResourceType;
    }

    public Drawable getDrawable(Context context, int i, Supplier<Drawable> supplier) {
        try {
            Resources appResourcesWithCallersConfiguration = getAppResourcesWithCallersConfiguration(context);
            verifyResourceName(appResourcesWithCallersConfiguration);
            return appResourcesWithCallersConfiguration.getDrawableForDensity(this.mResourceId, i, context.getTheme());
        } catch (PackageManager.NameNotFoundException | RuntimeException e) {
            Slog.e(TAG, "Unable to load drawable resource " + this.mResourceName, e);
            return loadDefaultDrawable(supplier);
        }
    }

    public String getString(Context context, Supplier<String> supplier) {
        try {
            Resources appResourcesWithCallersConfiguration = getAppResourcesWithCallersConfiguration(context);
            verifyResourceName(appResourcesWithCallersConfiguration);
            return appResourcesWithCallersConfiguration.getString(this.mResourceId);
        } catch (PackageManager.NameNotFoundException | RuntimeException e) {
            Slog.e(TAG, "Unable to load string resource " + this.mResourceName, e);
            return loadDefaultString(supplier);
        }
    }

    public String getString(Context context, Supplier<String> supplier, Object... objArr) {
        try {
            Resources appResourcesWithCallersConfiguration = getAppResourcesWithCallersConfiguration(context);
            verifyResourceName(appResourcesWithCallersConfiguration);
            return String.format(context.getResources().getConfiguration().getLocales().get(0), appResourcesWithCallersConfiguration.getString(this.mResourceId), objArr);
        } catch (PackageManager.NameNotFoundException | RuntimeException e) {
            Slog.e(TAG, "Unable to load string resource " + this.mResourceName, e);
            return loadDefaultString(supplier);
        }
    }

    private Resources getAppResourcesWithCallersConfiguration(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getResourcesForApplication(packageManager.getApplicationInfo(this.mPackageName, 9216), context.getResources().getConfiguration());
    }

    private void verifyResourceName(Resources resources) throws IllegalStateException, Resources.NotFoundException {
        String resourceName = resources.getResourceName(this.mResourceId);
        if (!this.mResourceName.equals(resourceName)) {
            throw new IllegalStateException(String.format("Current resource name %s for resource id %d has changed from the previously stored resource name %s.", resourceName, Integer.valueOf(this.mResourceId), this.mResourceName));
        }
    }

    public static Drawable loadDefaultDrawable(Supplier<Drawable> supplier) {
        Objects.requireNonNull(supplier, "defaultDrawableLoader can't be null");
        return supplier.get();
    }

    public static String loadDefaultString(Supplier<String> supplier) {
        Objects.requireNonNull(supplier, "defaultStringLoader can't be null");
        return supplier.get();
    }

    public void writeToXmlFile(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attributeInt(null, ATTR_RESOURCE_ID, this.mResourceId);
        typedXmlSerializer.attribute(null, ATTR_PACKAGE_NAME, this.mPackageName);
        typedXmlSerializer.attribute(null, ATTR_RESOURCE_NAME, this.mResourceName);
        typedXmlSerializer.attributeInt(null, ATTR_RESOURCE_TYPE, this.mResourceType);
    }

    public static ParcelableResource createFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        return new ParcelableResource(typedXmlPullParser.getAttributeInt(null, ATTR_RESOURCE_ID), typedXmlPullParser.getAttributeValue(null, ATTR_PACKAGE_NAME), typedXmlPullParser.getAttributeValue(null, ATTR_RESOURCE_NAME), typedXmlPullParser.getAttributeInt(null, ATTR_RESOURCE_TYPE));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ParcelableResource parcelableResource = (ParcelableResource) obj;
            if (this.mResourceId == parcelableResource.mResourceId && this.mPackageName.equals(parcelableResource.mPackageName) && this.mResourceName.equals(parcelableResource.mResourceName) && this.mResourceType == parcelableResource.mResourceType) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mResourceId), this.mPackageName, this.mResourceName, Integer.valueOf(this.mResourceType));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResourceId);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mResourceName);
        parcel.writeInt(this.mResourceType);
    }
}
