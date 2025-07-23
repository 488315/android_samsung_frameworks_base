package android.service.contentcapture;

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
public class ContentCaptureServiceInfo {
    private static final String TAG = "ContentCaptureServiceInfo";
    private static final String XML_TAG_SERVICE = "content-capture-service";
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

    public ContentCaptureServiceInfo(Context context, ComponentName componentName, boolean z, int i) throws PackageManager.NameNotFoundException {
        this(context, getServiceInfoOrThrow(componentName, z, i));
    }

    private ContentCaptureServiceInfo(Context context, ServiceInfo serviceInfo) {
        TypedArray typedArray;
        if (!Manifest.permission.BIND_CONTENT_CAPTURE_SERVICE.equals(serviceInfo.permission)) {
            Slog.w(TAG, "ContentCaptureService from '" + serviceInfo.packageName + "' does not require permission android.permission.BIND_CONTENT_CAPTURE_SERVICE");
            throw new SecurityException("Service does not require permission android.permission.BIND_CONTENT_CAPTURE_SERVICE");
        }
        this.mServiceInfo = serviceInfo;
        XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(context.getPackageManager(), ContentCaptureService.SERVICE_META_DATA);
        String str = null;
        if (loadXmlMetaData == null) {
            this.mSettingsActivity = null;
            return;
        }
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(serviceInfo.applicationInfo);
            for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
            }
            if (XML_TAG_SERVICE.equals(loadXmlMetaData.getName())) {
                try {
                    typedArray = resourcesForApplication.obtainAttributes(Xml.asAttributeSet(loadXmlMetaData), R.styleable.ContentCaptureService);
                } catch (Throwable th) {
                    th = th;
                    typedArray = null;
                }
                try {
                    str = typedArray.getString(0);
                    if (typedArray != null) {
                        typedArray.recycle();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (typedArray != null) {
                        typedArray.recycle();
                    }
                    throw th;
                }
            } else {
                Log.e(TAG, "Meta-data does not start with content-capture-service tag");
            }
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
            Log.e(TAG, "Error parsing auto fill service meta-data", e);
        }
        this.mSettingsActivity = str;
    }

    public ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivity;
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
