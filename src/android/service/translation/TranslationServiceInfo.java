package android.service.translation;

import android.Manifest;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.io.PrintWriter;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class TranslationServiceInfo {
    private static final String TAG = "TranslationServiceInfo";
    private static final String XML_TAG_SERVICE = "translation-service";
    private final ServiceInfo mServiceInfo;
    private final String mSettingsActivity;

    private static ServiceInfo getServiceInfoOrThrow(ComponentName componentName, boolean z, int i) throws PackageManager.NameNotFoundException {
        ServiceInfo serviceInfo;
        try {
            serviceInfo = AppGlobals.getPackageManager().getServiceInfo(componentName, !z ? 1048704 : 128, i);
        } catch (RemoteException unused) {
            serviceInfo = null;
        }
        if (serviceInfo != null) {
            return serviceInfo;
        }
        StringBuilder sb = new StringBuilder("Could not get serviceInfo for ");
        sb.append(z ? " (temp)" : "(default system)");
        sb.append(" ");
        sb.append(componentName.flattenToShortString());
        throw new PackageManager.NameNotFoundException(sb.toString());
    }

    public ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivity;
    }

    public TranslationServiceInfo(Context context, ComponentName componentName, boolean z, int i) throws PackageManager.NameNotFoundException {
        this(context, getServiceInfoOrThrow(componentName, z, i));
    }

    private TranslationServiceInfo(Context context, ServiceInfo serviceInfo) throws Throwable {
        TypedArray typedArrayObtainAttributes;
        if (!Manifest.permission.BIND_TRANSLATION_SERVICE.equals(serviceInfo.permission)) {
            Slog.w(TAG, "TranslationServiceInfo from '" + serviceInfo.packageName + "' does not require permission android.permission.BIND_TRANSLATION_SERVICE");
            throw new SecurityException("Service does not require permission android.permission.BIND_TRANSLATION_SERVICE");
        }
        this.mServiceInfo = serviceInfo;
        XmlResourceParser xmlResourceParserLoadXmlMetaData = serviceInfo.loadXmlMetaData(context.getPackageManager(), TranslationService.SERVICE_META_DATA);
        String string = null;
        if (xmlResourceParserLoadXmlMetaData == null) {
            this.mSettingsActivity = null;
            return;
        }
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(serviceInfo.applicationInfo);
            for (int next = 0; next != 1 && next != 2; next = xmlResourceParserLoadXmlMetaData.next()) {
            }
            if (XML_TAG_SERVICE.equals(xmlResourceParserLoadXmlMetaData.getName())) {
                try {
                    typedArrayObtainAttributes = resourcesForApplication.obtainAttributes(Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData), R.styleable.TranslationService);
                    try {
                        string = typedArrayObtainAttributes.getString(0);
                        if (typedArrayObtainAttributes != null) {
                            typedArrayObtainAttributes.recycle();
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (typedArrayObtainAttributes != null) {
                            typedArrayObtainAttributes.recycle();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    typedArrayObtainAttributes = null;
                }
            } else {
                Log.e(TAG, "Meta-data does not start with translation-service tag");
            }
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
            Log.e(TAG, "Error parsing auto fill service meta-data", e);
        }
        this.mSettingsActivity = string;
    }

    public String toString() {
        return getClass().getSimpleName() + NavigationBarInflaterView.SIZE_MOD_START + this.mServiceInfo + ", settings:" + this.mSettingsActivity;
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("Component: ");
        printWriter.println(getServiceInfo().getComponentName());
        printWriter.print(str);
        printWriter.print("Settings: ");
        printWriter.println(this.mSettingsActivity);
    }
}
