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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

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

    public VoiceInteractionServiceInfo(PackageManager pm, ComponentName comp, int userHandle)
            throws PackageManager.NameNotFoundException {
        this(pm, getServiceInfoOrThrow(comp, userHandle));
    }

    private static ServiceInfo getServiceInfoOrThrow(ComponentName comp, int userHandle)
            throws PackageManager.NameNotFoundException {
        try {
            ServiceInfo si =
                    AppGlobals.getPackageManager().getServiceInfo(comp, 786560L, userHandle);
            if (si != null) {
                return si;
            }
        } catch (RemoteException e) {
        }
        throw new PackageManager.NameNotFoundException(comp.toString());
    }

    public VoiceInteractionServiceInfo(PackageManager pm, ServiceInfo si) {
        int type;
        if (!Manifest.permission.BIND_VOICE_INTERACTION.equals(si.permission)) {
            this.mParseError =
                    "Service does not require permission android.permission.BIND_VOICE_INTERACTION";
            return;
        }
        try {
            XmlResourceParser parser =
                    si.loadXmlMetaData(pm, VoiceInteractionService.SERVICE_META_DATA);
            try {
                if (parser == null) {
                    this.mParseError =
                            "No android.voice_interaction meta-data for " + si.packageName;
                    if (parser != null) {
                        parser.close();
                        return;
                    }
                    return;
                }
                Resources res = pm.getResourcesForApplication(si.applicationInfo);
                AttributeSet attrs = Xml.asAttributeSet(parser);
                do {
                    type = parser.next();
                    if (type == 1) {
                        break;
                    }
                } while (type != 2);
                String nodeName = parser.getName();
                if (!"voice-interaction-service".equals(nodeName)) {
                    this.mParseError =
                            "Meta-data does not start with voice-interaction-service tag";
                    if (parser != null) {
                        parser.close();
                        return;
                    }
                    return;
                }
                TypedArray array = res.obtainAttributes(attrs, R.styleable.VoiceInteractionService);
                this.mSessionService = array.getString(1);
                this.mRecognitionService = array.getString(2);
                this.mSettingsActivity = array.getString(0);
                this.mSupportsAssist = array.getBoolean(3, false);
                this.mSupportsLaunchFromKeyguard = array.getBoolean(4, false);
                this.mSupportsLocalInteraction = array.getBoolean(5, false);
                this.mHotwordDetectionService = array.getString(6);
                this.mVisualQueryDetectionService = array.getString(7);
                array.recycle();
                if (this.mSessionService == null) {
                    this.mParseError = "No sessionService specified";
                    if (parser != null) {
                        parser.close();
                        return;
                    }
                    return;
                }
                if (this.mRecognitionService != null) {
                    if (parser != null) {
                        parser.close();
                    }
                    this.mServiceInfo = si;
                } else {
                    this.mParseError = "No recognitionService specified";
                    if (parser != null) {
                        parser.close();
                    }
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
