package android.service.voice;

import android.Manifest;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class VoiceInteractionServiceInfo {
    static final String TAG = "VoiceInteractionServiceInfo";
    private String mHotwordDetectionService;
    private String mParseError;
    private String mRecognitionService;
    private ServiceInfo mServiceInfo;
    private String mSessionService;
    private String mSettingsActivity;
    private boolean mSupportsAssist;
    private boolean mSupportsLaunchFromKeyguard;
    private boolean mSupportsLocalInteraction;
    private String mVisualQueryDetectionService;

    public VoiceInteractionServiceInfo(PackageManager packageManager, ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        this(packageManager, getServiceInfoOrThrow(componentName, i));
    }

    private static ServiceInfo getServiceInfoOrThrow(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        try {
            ServiceInfo serviceInfo = AppGlobals.getPackageManager().getServiceInfo(componentName, 786560L, i);
            if (serviceInfo != null) {
                return serviceInfo;
            }
        } catch (RemoteException unused) {
        }
        throw new PackageManager.NameNotFoundException(componentName.toString());
    }

    public VoiceInteractionServiceInfo(PackageManager packageManager, ServiceInfo serviceInfo) {
        int next;
        if (!Manifest.permission.BIND_VOICE_INTERACTION.equals(serviceInfo.permission)) {
            this.mParseError = "Service does not require permission android.permission.BIND_VOICE_INTERACTION";
            return;
        }
        try {
            XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, VoiceInteractionService.SERVICE_META_DATA);
            try {
                if (loadXmlMetaData == null) {
                    this.mParseError = "No android.voice_interaction meta-data for " + serviceInfo.packageName;
                    if (loadXmlMetaData == null) {
                        return;
                    }
                } else {
                    Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                    AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!"voice-interaction-service".equals(loadXmlMetaData.getName())) {
                        this.mParseError = "Meta-data does not start with voice-interaction-service tag";
                        if (loadXmlMetaData == null) {
                            return;
                        }
                    } else {
                        TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.VoiceInteractionService);
                        this.mSessionService = obtainAttributes.getString(1);
                        this.mRecognitionService = obtainAttributes.getString(2);
                        this.mSettingsActivity = obtainAttributes.getString(0);
                        this.mSupportsAssist = obtainAttributes.getBoolean(3, false);
                        this.mSupportsLaunchFromKeyguard = obtainAttributes.getBoolean(4, false);
                        this.mSupportsLocalInteraction = obtainAttributes.getBoolean(5, false);
                        this.mHotwordDetectionService = obtainAttributes.getString(6);
                        this.mVisualQueryDetectionService = obtainAttributes.getString(7);
                        obtainAttributes.recycle();
                        if (this.mSessionService == null) {
                            this.mParseError = "No sessionService specified";
                            if (loadXmlMetaData == null) {
                                return;
                            }
                        } else if (this.mRecognitionService == null) {
                            this.mParseError = "No recognitionService specified";
                            if (loadXmlMetaData == null) {
                                return;
                            }
                        } else {
                            if (loadXmlMetaData != null) {
                                loadXmlMetaData.close();
                            }
                            this.mServiceInfo = serviceInfo;
                            return;
                        }
                    }
                }
                loadXmlMetaData.close();
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
        } catch (PackageManager.NameNotFoundException | IOException | XmlPullParserException e) {
            this.mParseError = "Error parsing voice interation service meta-data: " + e;
            Log.w(TAG, "error parsing voice interaction service meta-data", e);
        }
    }

    public String getParseError() {
        return this.mParseError;
    }

    public ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public String getSessionService() {
        return this.mSessionService;
    }

    public String getRecognitionService() {
        return this.mRecognitionService;
    }

    public String getSettingsActivity() {
        return this.mSettingsActivity;
    }

    public boolean getSupportsAssist() {
        return this.mSupportsAssist;
    }

    public boolean getSupportsLaunchFromKeyguard() {
        return this.mSupportsLaunchFromKeyguard;
    }

    public boolean getSupportsLocalInteraction() {
        return this.mSupportsLocalInteraction;
    }

    public String getHotwordDetectionService() {
        return this.mHotwordDetectionService;
    }

    public String getVisualQueryDetectionService() {
        return this.mVisualQueryDetectionService;
    }
}
