package android.media.tv.interactive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class TvInteractiveAppServiceInfo implements Parcelable {
    public static final Parcelable.Creator<TvInteractiveAppServiceInfo> CREATOR = new Parcelable.Creator<TvInteractiveAppServiceInfo>() { // from class: android.media.tv.interactive.TvInteractiveAppServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvInteractiveAppServiceInfo createFromParcel(Parcel parcel) {
            return new TvInteractiveAppServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvInteractiveAppServiceInfo[] newArray(int i) {
            return new TvInteractiveAppServiceInfo[i];
        }
    };
    private static final boolean DEBUG = false;
    public static final int INTERACTIVE_APP_TYPE_ATSC = 2;
    public static final int INTERACTIVE_APP_TYPE_GINGA = 4;
    public static final int INTERACTIVE_APP_TYPE_HBBTV = 1;
    public static final int INTERACTIVE_APP_TYPE_OTHER = Integer.MIN_VALUE;
    public static final int INTERACTIVE_APP_TYPE_TARGETED_AD = 8;
    private static final String TAG = "TvInteractiveAppServiceInfo";
    private static final String XML_START_TAG_NAME = "tv-interactive-app";
    private final List<String> mExtraTypes;
    private final String mId;
    private final ResolveInfo mService;
    private int mTypes;

    @Retention(RetentionPolicy.SOURCE)
    public @interface InteractiveAppType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TvInteractiveAppServiceInfo(Context context, ComponentName componentName) {
        this.mExtraTypes = new ArrayList();
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null.");
        }
        ResolveInfo resolveService = context.getPackageManager().resolveService(new Intent(TvInteractiveAppService.SERVICE_INTERFACE).setComponent(componentName), 132);
        if (resolveService == null) {
            throw new IllegalArgumentException("Invalid component. Can't find the service.");
        }
        String generateInteractiveAppServiceId = generateInteractiveAppServiceId(new ComponentName(resolveService.serviceInfo.packageName, resolveService.serviceInfo.name));
        ArrayList arrayList = new ArrayList();
        parseServiceMetadata(resolveService, context, arrayList);
        this.mService = resolveService;
        this.mId = generateInteractiveAppServiceId;
        toTypesFlag(arrayList);
    }

    private TvInteractiveAppServiceInfo(ResolveInfo resolveInfo, String str, int i, List<String> list) {
        ArrayList arrayList = new ArrayList();
        this.mExtraTypes = arrayList;
        this.mService = resolveInfo;
        this.mId = str;
        this.mTypes = i;
        arrayList.addAll(list);
    }

    private TvInteractiveAppServiceInfo(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mExtraTypes = arrayList;
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mTypes = parcel.readInt();
        parcel.readStringList(arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeInt(this.mTypes);
        parcel.writeStringList(this.mExtraTypes);
    }

    public String getId() {
        return this.mId;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public int getSupportedTypes() {
        return this.mTypes;
    }

    public List<String> getCustomSupportedTypes() {
        return this.mExtraTypes;
    }

    private static String generateInteractiveAppServiceId(ComponentName componentName) {
        return componentName.flattenToShortString();
    }

    private static void parseServiceMetadata(ResolveInfo resolveInfo, Context context, List<String> list) {
        int next;
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        PackageManager packageManager = context.getPackageManager();
        try {
            try {
                XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, TvInteractiveAppService.SERVICE_META_DATA);
                try {
                    if (loadXmlMetaData == null) {
                        throw new IllegalStateException("No android.media.tv.interactive.app meta-data found for " + serviceInfo.name);
                    }
                    Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                    AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!XML_START_TAG_NAME.equals(loadXmlMetaData.getName())) {
                        throw new IllegalStateException("Meta-data does not start with tv-interactive-app tag for " + serviceInfo.name);
                    }
                    TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.TvInteractiveAppService);
                    for (CharSequence charSequence : obtainAttributes.getTextArray(0)) {
                        list.add(charSequence.toString().toLowerCase());
                    }
                    obtainAttributes.recycle();
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                } catch (Throwable th) {
                    if (loadXmlMetaData != null) {
                        try {
                            loadXmlMetaData.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IOException | XmlPullParserException e) {
                throw new IllegalStateException("Failed reading meta-data for " + serviceInfo.packageName, e);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            throw new IllegalStateException("No resources found for " + serviceInfo.packageName, e2);
        }
    }

    private void toTypesFlag(List<String> list) {
        this.mTypes = 0;
        this.mExtraTypes.clear();
        for (String str : list) {
            str.hashCode();
            switch (str) {
                case "targeted_ad":
                    this.mTypes |= 8;
                    break;
                case "atsc":
                    this.mTypes |= 2;
                    continue;
                case "ginga":
                    this.mTypes |= 4;
                    continue;
                case "hbbtv":
                    this.mTypes |= 1;
                    continue;
            }
            this.mTypes |= Integer.MIN_VALUE;
            this.mExtraTypes.add(str);
        }
    }
}
