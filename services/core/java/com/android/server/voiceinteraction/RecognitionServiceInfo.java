package com.android.server.voiceinteraction;

import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RecognitionServiceInfo {
    public final String mParseError;
    public final boolean mSelectableAsDefault;
    public final ServiceInfo mServiceInfo;

    public RecognitionServiceInfo(ServiceInfo serviceInfo, boolean z, String str) {
        this.mServiceInfo = serviceInfo;
        this.mSelectableAsDefault = z;
        this.mParseError = str;
    }

    public static RecognitionServiceInfo parseInfo(PackageManager packageManager, ServiceInfo serviceInfo) {
        String str;
        XmlResourceParser loadXmlMetaData;
        boolean z = true;
        try {
            loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, "android.speech");
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
            str = "Error parsing recognition service meta-data: " + e;
        }
        try {
            if (loadXmlMetaData == null) {
                RecognitionServiceInfo recognitionServiceInfo = new RecognitionServiceInfo(serviceInfo, true, "No android.speech meta-data for " + serviceInfo.packageName);
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
                return recognitionServiceInfo;
            }
            Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
            AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
            for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
            }
            if (!"recognition-service".equals(loadXmlMetaData.getName())) {
                throw new XmlPullParserException("Meta-data does not start with recognition-service tag");
            }
            TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.RecognitionService);
            z = obtainAttributes.getBoolean(1, true);
            obtainAttributes.recycle();
            loadXmlMetaData.close();
            str = "";
            return new RecognitionServiceInfo(serviceInfo, z, str);
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
    }
}
