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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TvAdServiceInfo implements Parcelable {
    public static final Parcelable.Creator<TvAdServiceInfo> CREATOR =
            new Parcelable.Creator<
                    TvAdServiceInfo>() { // from class: android.media.tv.ad.TvAdServiceInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TvAdServiceInfo createFromParcel(Parcel in) {
                    return new TvAdServiceInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TvAdServiceInfo[] newArray(int size) {
                    return new TvAdServiceInfo[size];
                }
            };
    private static final boolean DEBUG = false;
    private static final String TAG = "TvAdServiceInfo";
    private static final String XML_START_TAG_NAME = "tv-ad-service";
    private final String mId;
    private final ResolveInfo mService;
    private final List<String> mTypes;

    public TvAdServiceInfo(Context context, ComponentName component) {
        this.mTypes = new ArrayList();
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null.");
        }
        Intent intent = new Intent(TvAdService.SERVICE_INTERFACE).setComponent(component);
        ResolveInfo resolveInfo = context.getPackageManager().resolveService(intent, 132);
        if (resolveInfo == null) {
            throw new IllegalArgumentException("Invalid component. Can't find the service.");
        }
        ComponentName componentName =
                new ComponentName(
                        resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
        String id = generateAdServiceId(componentName);
        List<String> types = new ArrayList<>();
        parseServiceMetadata(resolveInfo, context, types);
        this.mService = resolveInfo;
        this.mId = id;
        this.mTypes.addAll(types);
    }

    private TvAdServiceInfo(ResolveInfo service, String id, List<String> types) {
        this.mTypes = new ArrayList();
        this.mService = service;
        this.mId = id;
        this.mTypes.addAll(types);
    }

    private TvAdServiceInfo(Parcel in) {
        this.mTypes = new ArrayList();
        this.mService = ResolveInfo.CREATOR.createFromParcel(in);
        this.mId = in.readString();
        in.readStringList(this.mTypes);
    }

    public String getId() {
        return this.mId;
    }

    public ComponentName getComponent() {
        return new ComponentName(
                this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public List<String> getSupportedTypes() {
        return this.mTypes;
    }

    private static String generateAdServiceId(ComponentName name) {
        return name.flattenToShortString();
    }

    private static void parseServiceMetadata(
            ResolveInfo resolveInfo, Context context, List<String> types) {
        int type;
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        PackageManager pm = context.getPackageManager();
        try {
            try {
                XmlResourceParser parser =
                        serviceInfo.loadXmlMetaData(pm, TvAdService.SERVICE_META_DATA);
                try {
                    if (parser == null) {
                        throw new IllegalStateException(
                                "No android.media.tv.ad.service meta-data found for "
                                        + serviceInfo.name);
                    }
                    Resources resources =
                            pm.getResourcesForApplication(serviceInfo.applicationInfo);
                    AttributeSet attrs = Xml.asAttributeSet(parser);
                    do {
                        type = parser.next();
                        if (type == 1) {
                            break;
                        }
                    } while (type != 2);
                    String nodeName = parser.getName();
                    if (!XML_START_TAG_NAME.equals(nodeName)) {
                        throw new IllegalStateException(
                                "Meta-data does not start with tv-ad-service tag for "
                                        + serviceInfo.name);
                    }
                    TypedArray sa = resources.obtainAttributes(attrs, R.styleable.TvAdService);
                    CharSequence[] textArr = sa.getTextArray(0);
                    for (CharSequence cs : textArr) {
                        types.add(cs.toString().toLowerCase());
                    }
                    sa.recycle();
                    if (parser != null) {
                        parser.close();
                    }
                } catch (Throwable th) {
                    if (parser != null) {
                        try {
                            parser.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IOException | XmlPullParserException e) {
                throw new IllegalStateException(
                        "Failed reading meta-data for " + serviceInfo.packageName, e);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            throw new IllegalStateException(
                    "No resources found for " + serviceInfo.packageName, e2);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mService.writeToParcel(dest, flags);
        dest.writeString(this.mId);
        dest.writeStringList(this.mTypes);
    }
}
