package android.media.tv.ad;

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
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class TvAdServiceInfo implements Parcelable {
    public static final Parcelable.Creator<TvAdServiceInfo> CREATOR = new Parcelable.Creator<TvAdServiceInfo>() { // from class: android.media.tv.ad.TvAdServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvAdServiceInfo createFromParcel(Parcel parcel) {
            return new TvAdServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvAdServiceInfo[] newArray(int i) {
            return new TvAdServiceInfo[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "TvAdServiceInfo";
    private static final String XML_START_TAG_NAME = "tv-ad-service";
    private final String mId;
    private final ResolveInfo mService;
    private final List<String> mTypes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TvAdServiceInfo(Context context, ComponentName componentName) {
        ArrayList arrayList = new ArrayList();
        this.mTypes = arrayList;
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null.");
        }
        ResolveInfo resolveService = context.getPackageManager().resolveService(new Intent(TvAdService.SERVICE_INTERFACE).setComponent(componentName), 132);
        if (resolveService == null) {
            throw new IllegalArgumentException("Invalid component. Can't find the service.");
        }
        String generateAdServiceId = generateAdServiceId(new ComponentName(resolveService.serviceInfo.packageName, resolveService.serviceInfo.name));
        ArrayList arrayList2 = new ArrayList();
        parseServiceMetadata(resolveService, context, arrayList2);
        this.mService = resolveService;
        this.mId = generateAdServiceId;
        arrayList.addAll(arrayList2);
    }

    private TvAdServiceInfo(ResolveInfo resolveInfo, String str, List<String> list) {
        ArrayList arrayList = new ArrayList();
        this.mTypes = arrayList;
        this.mService = resolveInfo;
        this.mId = str;
        arrayList.addAll(list);
    }

    private TvAdServiceInfo(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        this.mTypes = arrayList;
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        parcel.readStringList(arrayList);
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

    public List<String> getSupportedTypes() {
        return this.mTypes;
    }

    private static String generateAdServiceId(ComponentName componentName) {
        return componentName.flattenToShortString();
    }

    private static void parseServiceMetadata(ResolveInfo resolveInfo, Context context, List<String> list) {
        int next;
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        PackageManager packageManager = context.getPackageManager();
        try {
            try {
                XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, TvAdService.SERVICE_META_DATA);
                try {
                    if (loadXmlMetaData == null) {
                        throw new IllegalStateException("No android.media.tv.ad.service meta-data found for " + serviceInfo.name);
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
                        throw new IllegalStateException("Meta-data does not start with tv-ad-service tag for " + serviceInfo.name);
                    }
                    TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.TvAdService);
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

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeStringList(this.mTypes);
    }
}
