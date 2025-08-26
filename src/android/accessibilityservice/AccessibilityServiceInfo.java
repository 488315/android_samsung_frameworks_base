package android.accessibilityservice;

import android.accessibilityservice.util.AccessibilityUtils;
import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.hardware.fingerprint.FingerprintManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.Flags;
import com.android.internal.R;
import com.android.internal.compat.IPlatformCompat;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AccessibilityServiceInfo implements Parcelable {
    public static final int CAPABILITY_CAN_CONTROL_MAGNIFICATION = 16;
    public static final int CAPABILITY_CAN_PERFORM_GESTURES = 32;

    @Deprecated
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_FINGERPRINT_GESTURES = 64;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    public static final int CAPABILITY_CAN_TAKE_SCREENSHOT = 128;
    public static final Parcelable.Creator<AccessibilityServiceInfo> CREATOR = new Parcelable.Creator<AccessibilityServiceInfo>() { // from class: android.accessibilityservice.AccessibilityServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityServiceInfo createFromParcel(Parcel parcel) {
            AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
            accessibilityServiceInfo.initFromParcel(parcel);
            return accessibilityServiceInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccessibilityServiceInfo[] newArray(int i) {
            return new AccessibilityServiceInfo[i];
        }
    };
    public static final int DEFAULT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_AUDIBLE = 4;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FEEDBACK_GENERIC = 16;
    public static final int FEEDBACK_HAPTIC = 2;
    public static final int FEEDBACK_SPOKEN = 1;
    public static final int FEEDBACK_VISUAL = 8;
    public static final int FLAG_ENABLE_ACCESSIBILITY_VOLUME = 128;
    public static final int FLAG_FORCE_DIRECT_BOOT_AWARE = 65536;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_INPUT_METHOD_EDITOR = 32768;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_2_FINGER_PASSTHROUGH = 8192;
    public static final int FLAG_REQUEST_ACCESSIBILITY_BUTTON = 256;

    @Deprecated
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_FINGERPRINT_GESTURES = 512;
    public static final int FLAG_REQUEST_MULTI_FINGER_GESTURES = 4096;
    public static final int FLAG_REQUEST_SHORTCUT_WARNING_DIALOG_SPOKEN_FEEDBACK = 1024;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
    public static final int FLAG_RETRIEVE_INTERACTIVE_WINDOWS = 64;
    public static final int FLAG_SEND_MOTION_EVENTS = 16384;
    public static final int FLAG_SERVICE_HANDLES_DOUBLE_TAP = 2048;
    private static final long REQUEST_ACCESSIBILITY_BUTTON_CHANGE = 136293963;
    private static final String TAG_ACCESSIBILITY_SERVICE = "accessibility-service";
    private static SparseArray<CapabilityInfo> sAvailableCapabilityInfos;
    public boolean crashed;
    public int eventTypes;
    public int feedbackType;
    public int flags;
    private int mAnimatedImageRes;
    private int mCapabilities;
    private ComponentName mComponentName;
    private int mDescriptionResId;
    private final DynamicPropertyDefaults mDynamicPropertyDefaults;
    private int mHtmlDescriptionRes;
    private int mInteractiveUiTimeout;
    private int mIntroResId;
    private boolean mIsAccessibilityTool;
    private int mMotionEventSources;
    private int mNonInteractiveUiTimeout;
    private String mNonLocalizedDescription;
    private String mNonLocalizedSummary;
    private int mObservedMotionEventSources;
    private ResolveInfo mResolveInfo;
    private String mSettingsActivityName;
    private int mSummaryResId;
    private String mTileServiceName;
    public long notificationTimeout;
    public String[] packageNames;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FeedbackType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MotionEventSources {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static class DynamicPropertyDefaults {
        private final int mEventTypesDefault;
        private final int mFeedbackTypeDefault;
        private final int mFlagsDefault;
        private final int mInteractiveUiTimeoutDefault;
        private final int mMotionEventSourcesDefault;
        private final int mNonInteractiveUiTimeoutDefault;
        private final long mNotificationTimeoutDefault;
        private final int mObservedMotionEventSourcesDefault;
        private final List<String> mPackageNamesDefault;

        DynamicPropertyDefaults(AccessibilityServiceInfo accessibilityServiceInfo) {
            this.mEventTypesDefault = accessibilityServiceInfo.eventTypes;
            if (accessibilityServiceInfo.packageNames != null) {
                this.mPackageNamesDefault = List.of((Object[]) accessibilityServiceInfo.packageNames);
            } else {
                this.mPackageNamesDefault = null;
            }
            this.mFeedbackTypeDefault = accessibilityServiceInfo.feedbackType;
            this.mNotificationTimeoutDefault = accessibilityServiceInfo.notificationTimeout;
            this.mNonInteractiveUiTimeoutDefault = accessibilityServiceInfo.mNonInteractiveUiTimeout;
            this.mInteractiveUiTimeoutDefault = accessibilityServiceInfo.mInteractiveUiTimeout;
            this.mFlagsDefault = accessibilityServiceInfo.flags;
            this.mMotionEventSourcesDefault = accessibilityServiceInfo.mMotionEventSources;
            this.mObservedMotionEventSourcesDefault = accessibilityServiceInfo.mObservedMotionEventSources;
        }
    }

    public AccessibilityServiceInfo() {
        this.mIsAccessibilityTool = false;
        this.mMotionEventSources = 0;
        this.mObservedMotionEventSources = 0;
        this.mDynamicPropertyDefaults = new DynamicPropertyDefaults(this);
    }

    public AccessibilityServiceInfo(ResolveInfo resolveInfo, Context context) throws XmlPullParserException, IOException {
        this.mIsAccessibilityTool = false;
        this.mMotionEventSources = 0;
        this.mObservedMotionEventSources = 0;
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        this.mComponentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
        this.mResolveInfo = resolveInfo;
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                PackageManager packageManager = context.getPackageManager();
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                XmlResourceParser xmlResourceParserLoadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, AccessibilityService.SERVICE_META_DATA);
                long jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
                if (jElapsedRealtime2 > 100) {
                    Log.i("AccessibilityServiceInfo", "took more than 100ms mComponentName : " + this.mComponentName + ", elapsedTime : " + jElapsedRealtime2);
                }
                if (xmlResourceParserLoadXmlMetaData != null) {
                    for (int next = 0; next != 1 && next != 2; next = xmlResourceParserLoadXmlMetaData.next()) {
                    }
                    if (!TAG_ACCESSIBILITY_SERVICE.equals(xmlResourceParserLoadXmlMetaData.getName())) {
                        throw new XmlPullParserException("Meta-data does not start withaccessibility-service tag");
                    }
                    TypedArray typedArrayObtainAttributes = packageManager.getResourcesForApplication(serviceInfo.applicationInfo).obtainAttributes(Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData), R.styleable.AccessibilityService);
                    this.eventTypes = typedArrayObtainAttributes.getInt(3, 0);
                    String string = typedArrayObtainAttributes.getString(4);
                    if (string != null) {
                        this.packageNames = string.split("(\\s)*,(\\s)*");
                    }
                    this.feedbackType = typedArrayObtainAttributes.getInt(5, 0);
                    this.notificationTimeout = typedArrayObtainAttributes.getInt(6, 0);
                    this.mNonInteractiveUiTimeout = typedArrayObtainAttributes.getInt(15, 0);
                    this.mInteractiveUiTimeout = typedArrayObtainAttributes.getInt(16, 0);
                    this.flags = typedArrayObtainAttributes.getInt(7, 0);
                    this.mSettingsActivityName = typedArrayObtainAttributes.getString(2);
                    if (typedArrayObtainAttributes.getBoolean(8, false)) {
                        this.mCapabilities |= 1;
                    }
                    if (typedArrayObtainAttributes.getBoolean(9, false)) {
                        this.mCapabilities = 2 | this.mCapabilities;
                    }
                    if (typedArrayObtainAttributes.getBoolean(11, false)) {
                        this.mCapabilities |= 8;
                    }
                    if (typedArrayObtainAttributes.getBoolean(12, false)) {
                        this.mCapabilities = 16 | this.mCapabilities;
                    }
                    if (typedArrayObtainAttributes.getBoolean(13, false)) {
                        this.mCapabilities |= 32;
                    }
                    if (typedArrayObtainAttributes.getBoolean(14, false)) {
                        this.mCapabilities |= 64;
                    }
                    if (typedArrayObtainAttributes.getBoolean(19, false)) {
                        this.mCapabilities |= 128;
                    }
                    try {
                        TypedValue typedValuePeekValue = typedArrayObtainAttributes.peekValue(0);
                        if (typedValuePeekValue != null) {
                            this.mDescriptionResId = typedValuePeekValue.resourceId;
                            CharSequence charSequenceCoerceToString = typedValuePeekValue.coerceToString();
                            if (charSequenceCoerceToString != null) {
                                this.mNonLocalizedDescription = charSequenceCoerceToString.toString().trim();
                            }
                        }
                    } catch (IndexOutOfBoundsException unused) {
                        this.mNonLocalizedDescription = " ";
                    }
                    TypedValue typedValuePeekValue2 = typedArrayObtainAttributes.peekValue(1);
                    if (typedValuePeekValue2 != null) {
                        this.mSummaryResId = typedValuePeekValue2.resourceId;
                        CharSequence charSequenceCoerceToString2 = typedValuePeekValue2.coerceToString();
                        if (charSequenceCoerceToString2 != null) {
                            this.mNonLocalizedSummary = charSequenceCoerceToString2.toString().trim();
                        }
                    }
                    TypedValue typedValuePeekValue3 = typedArrayObtainAttributes.peekValue(17);
                    if (typedValuePeekValue3 != null) {
                        this.mAnimatedImageRes = typedValuePeekValue3.resourceId;
                    }
                    TypedValue typedValuePeekValue4 = typedArrayObtainAttributes.peekValue(18);
                    if (typedValuePeekValue4 != null) {
                        this.mHtmlDescriptionRes = typedValuePeekValue4.resourceId;
                    }
                    this.mIsAccessibilityTool = typedArrayObtainAttributes.getBoolean(20, false);
                    this.mTileServiceName = typedArrayObtainAttributes.getString(21);
                    TypedValue typedValuePeekValue5 = typedArrayObtainAttributes.peekValue(22);
                    if (typedValuePeekValue5 != null) {
                        this.mIntroResId = typedValuePeekValue5.resourceId;
                    }
                    typedArrayObtainAttributes.recycle();
                    if (xmlResourceParserLoadXmlMetaData != null) {
                        xmlResourceParserLoadXmlMetaData.close();
                    }
                    this.mDynamicPropertyDefaults = new DynamicPropertyDefaults(this);
                    return;
                }
                if (xmlResourceParserLoadXmlMetaData != null) {
                    xmlResourceParserLoadXmlMetaData.close();
                }
                this.mDynamicPropertyDefaults = new DynamicPropertyDefaults(this);
            } catch (PackageManager.NameNotFoundException unused2) {
                throw new XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            this.mDynamicPropertyDefaults = new DynamicPropertyDefaults(this);
            throw th;
        }
    }

    public void resetDynamicallyConfigurableProperties() {
        this.eventTypes = this.mDynamicPropertyDefaults.mEventTypesDefault;
        if (this.mDynamicPropertyDefaults.mPackageNamesDefault == null) {
            this.packageNames = null;
        } else {
            this.packageNames = (String[]) this.mDynamicPropertyDefaults.mPackageNamesDefault.toArray(new String[0]);
        }
        this.feedbackType = this.mDynamicPropertyDefaults.mFeedbackTypeDefault;
        this.notificationTimeout = this.mDynamicPropertyDefaults.mNotificationTimeoutDefault;
        this.mNonInteractiveUiTimeout = this.mDynamicPropertyDefaults.mNonInteractiveUiTimeoutDefault;
        this.mInteractiveUiTimeout = this.mDynamicPropertyDefaults.mInteractiveUiTimeoutDefault;
        this.flags = this.mDynamicPropertyDefaults.mFlagsDefault;
        this.mMotionEventSources = this.mDynamicPropertyDefaults.mMotionEventSourcesDefault;
        if (Flags.motionEventObserving()) {
            this.mObservedMotionEventSources = this.mDynamicPropertyDefaults.mObservedMotionEventSourcesDefault;
        }
    }

    public void updateDynamicallyConfigurableProperties(IPlatformCompat iPlatformCompat, AccessibilityServiceInfo accessibilityServiceInfo) {
        if (isRequestAccessibilityButtonChangeEnabled(iPlatformCompat)) {
            int i = accessibilityServiceInfo.flags & (-257);
            accessibilityServiceInfo.flags = i;
            accessibilityServiceInfo.flags = i | (this.flags & 256);
        }
        this.eventTypes = accessibilityServiceInfo.eventTypes;
        this.packageNames = accessibilityServiceInfo.packageNames;
        this.feedbackType = accessibilityServiceInfo.feedbackType;
        this.notificationTimeout = accessibilityServiceInfo.notificationTimeout;
        this.mNonInteractiveUiTimeout = accessibilityServiceInfo.mNonInteractiveUiTimeout;
        this.mInteractiveUiTimeout = accessibilityServiceInfo.mInteractiveUiTimeout;
        this.flags = accessibilityServiceInfo.flags;
        this.mMotionEventSources = accessibilityServiceInfo.mMotionEventSources;
        if (Flags.motionEventObserving()) {
            setObservedMotionEventSources(accessibilityServiceInfo.mObservedMotionEventSources);
        }
    }

    private boolean isRequestAccessibilityButtonChangeEnabled(IPlatformCompat iPlatformCompat) {
        ResolveInfo resolveInfo = this.mResolveInfo;
        if (resolveInfo == null) {
            return true;
        }
        if (iPlatformCompat != null) {
            try {
                return iPlatformCompat.isChangeEnabled(REQUEST_ACCESSIBILITY_BUTTON_CHANGE, resolveInfo.serviceInfo.applicationInfo);
            } catch (RemoteException unused) {
            }
        }
        return this.mResolveInfo.serviceInfo.applicationInfo.targetSdkVersion > 29;
    }

    public void setComponentName(ComponentName componentName) {
        this.mComponentName = componentName;
    }

    public void setResolveInfo(ResolveInfo resolveInfo) {
        this.mResolveInfo = resolveInfo;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public String getId() {
        ComponentName componentName = this.mComponentName;
        if (componentName == null) {
            return null;
        }
        return componentName.flattenToShortString();
    }

    public ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    public String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public String getTileServiceName() {
        return this.mTileServiceName;
    }

    public int getAnimatedImageRes() {
        return this.mAnimatedImageRes;
    }

    public Drawable loadAnimatedImage(Context context) {
        if (this.mAnimatedImageRes == 0) {
            return null;
        }
        return AccessibilityUtils.loadSafeAnimatedImage(context, this.mResolveInfo.serviceInfo.applicationInfo, this.mAnimatedImageRes);
    }

    public boolean getCanRetrieveWindowContent() {
        return (this.mCapabilities & 1) != 0;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public void setCapabilities(int i) {
        this.mCapabilities = i;
    }

    public int getMotionEventSources() {
        return this.mMotionEventSources;
    }

    public void setMotionEventSources(int i) {
        this.mMotionEventSources = i;
        this.mObservedMotionEventSources = 0;
    }

    public void setObservedMotionEventSources(int i) {
        int i2 = this.mMotionEventSources;
        if (((~i2) & i) != 0) {
            throw new IllegalArgumentException(String.format("Requested motion event sources for listening = 0x%x but requested motion event sources for observing = 0x%x.", Integer.valueOf(i2), Integer.valueOf(i)));
        }
        this.mObservedMotionEventSources = i;
    }

    public int getObservedMotionEventSources() {
        return this.mObservedMotionEventSources;
    }

    public CharSequence loadSummary(PackageManager packageManager) {
        if (this.mSummaryResId == 0) {
            return this.mNonLocalizedSummary;
        }
        ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        CharSequence text = packageManager.getText(serviceInfo.packageName, this.mSummaryResId, serviceInfo.applicationInfo);
        if (text != null) {
            return text.toString().trim();
        }
        return null;
    }

    public CharSequence loadIntro(PackageManager packageManager) {
        if (this.mIntroResId == 0) {
            return null;
        }
        ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        CharSequence text = packageManager.getText(serviceInfo.packageName, this.mIntroResId, serviceInfo.applicationInfo);
        if (text != null) {
            return text.toString().trim();
        }
        return null;
    }

    public String getDescription() {
        return this.mNonLocalizedDescription;
    }

    public String loadDescription(PackageManager packageManager) {
        if (this.mDescriptionResId == 0) {
            return this.mNonLocalizedDescription;
        }
        ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        CharSequence text = packageManager.getText(serviceInfo.packageName, this.mDescriptionResId, serviceInfo.applicationInfo);
        if (text != null) {
            return text.toString().trim();
        }
        return null;
    }

    public String loadHtmlDescription(PackageManager packageManager) {
        if (this.mHtmlDescriptionRes == 0) {
            return null;
        }
        ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        CharSequence text = packageManager.getText(serviceInfo.packageName, this.mHtmlDescriptionRes, serviceInfo.applicationInfo);
        if (text != null) {
            return AccessibilityUtils.getFilteredHtmlText(text.toString().trim());
        }
        return null;
    }

    public void setNonInteractiveUiTimeoutMillis(int i) {
        this.mNonInteractiveUiTimeout = i;
    }

    public int getNonInteractiveUiTimeoutMillis() {
        return this.mNonInteractiveUiTimeout;
    }

    public void setInteractiveUiTimeoutMillis(int i) {
        this.mInteractiveUiTimeout = i;
    }

    public int getInteractiveUiTimeoutMillis() {
        return this.mInteractiveUiTimeout;
    }

    public boolean isDirectBootAware() {
        return (this.flags & 65536) != 0 || this.mResolveInfo.serviceInfo.directBootAware;
    }

    @SystemApi
    public void setAccessibilityTool(boolean z) {
        this.mIsAccessibilityTool = z;
    }

    public boolean isAccessibilityTool() {
        return this.mIsAccessibilityTool;
    }

    public final boolean isWithinParcelableSize() {
        Parcel parcelObtain = Parcel.obtain();
        writeToParcel(parcelObtain, 0);
        boolean z = parcelObtain.dataSize() <= 65536;
        parcelObtain.recycle();
        return z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.eventTypes);
        parcel.writeStringArray(this.packageNames);
        parcel.writeInt(this.feedbackType);
        parcel.writeLong(this.notificationTimeout);
        parcel.writeInt(this.mNonInteractiveUiTimeout);
        parcel.writeInt(this.mInteractiveUiTimeout);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.crashed ? 1 : 0);
        parcel.writeParcelable(this.mComponentName, i);
        parcel.writeParcelable(this.mResolveInfo, 0);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeInt(this.mCapabilities);
        parcel.writeInt(this.mSummaryResId);
        parcel.writeString(this.mNonLocalizedSummary);
        parcel.writeInt(this.mDescriptionResId);
        parcel.writeInt(this.mAnimatedImageRes);
        parcel.writeInt(this.mHtmlDescriptionRes);
        parcel.writeString(this.mNonLocalizedDescription);
        parcel.writeBoolean(this.mIsAccessibilityTool);
        parcel.writeString(this.mTileServiceName);
        parcel.writeInt(this.mIntroResId);
        parcel.writeInt(this.mMotionEventSources);
        parcel.writeInt(this.mObservedMotionEventSources);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(Parcel parcel) {
        this.eventTypes = parcel.readInt();
        this.packageNames = parcel.readStringArray();
        this.feedbackType = parcel.readInt();
        this.notificationTimeout = parcel.readLong();
        this.mNonInteractiveUiTimeout = parcel.readInt();
        this.mInteractiveUiTimeout = parcel.readInt();
        this.flags = parcel.readInt();
        this.crashed = parcel.readInt() != 0;
        this.mComponentName = (ComponentName) parcel.readParcelable(getClass().getClassLoader(), ComponentName.class);
        this.mResolveInfo = (ResolveInfo) parcel.readParcelable(null, ResolveInfo.class);
        this.mSettingsActivityName = parcel.readString();
        this.mCapabilities = parcel.readInt();
        this.mSummaryResId = parcel.readInt();
        this.mNonLocalizedSummary = parcel.readString();
        this.mDescriptionResId = parcel.readInt();
        this.mAnimatedImageRes = parcel.readInt();
        this.mHtmlDescriptionRes = parcel.readInt();
        this.mNonLocalizedDescription = parcel.readString();
        this.mIsAccessibilityTool = parcel.readBoolean();
        this.mTileServiceName = parcel.readString();
        this.mIntroResId = parcel.readInt();
        this.mMotionEventSources = parcel.readInt();
        setObservedMotionEventSources(parcel.readInt());
    }

    public int hashCode() {
        ComponentName componentName = this.mComponentName;
        return (componentName == null ? 0 : componentName.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo) obj;
        ComponentName componentName = this.mComponentName;
        if (componentName == null) {
            if (accessibilityServiceInfo.mComponentName != null) {
                return false;
            }
        } else if (!componentName.equals(accessibilityServiceInfo.mComponentName)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendEventTypes(sb, this.eventTypes);
        sb.append(", ");
        appendPackageNames(sb, this.packageNames);
        sb.append(", ");
        appendFeedbackTypes(sb, this.feedbackType);
        sb.append(", ");
        sb.append("notificationTimeout: ");
        sb.append(this.notificationTimeout);
        sb.append(", ");
        sb.append("nonInteractiveUiTimeout: ");
        sb.append(this.mNonInteractiveUiTimeout);
        sb.append(", ");
        sb.append("interactiveUiTimeout: ");
        sb.append(this.mInteractiveUiTimeout);
        sb.append(", ");
        appendFlags(sb, this.flags);
        sb.append(", ");
        sb.append("id: ");
        sb.append(getId());
        sb.append(", ");
        sb.append("resolveInfo: ");
        sb.append(this.mResolveInfo);
        sb.append(", ");
        sb.append("settingsActivityName: ");
        sb.append(this.mSettingsActivityName);
        sb.append(", ");
        sb.append("tileServiceName: ");
        sb.append(this.mTileServiceName);
        sb.append(", ");
        sb.append("summary: ");
        sb.append(this.mNonLocalizedSummary);
        sb.append(", ");
        sb.append("isAccessibilityTool: ");
        sb.append(this.mIsAccessibilityTool);
        sb.append(", ");
        appendCapabilities(sb, this.mCapabilities);
        return sb.toString();
    }

    private static void appendFeedbackTypes(StringBuilder sb, int i) {
        sb.append("feedbackTypes:");
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            sb.append(feedbackTypeToString(iNumberOfTrailingZeros));
            i &= ~iNumberOfTrailingZeros;
            if (i != 0) {
                sb.append(", ");
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
    }

    private static void appendPackageNames(StringBuilder sb, String[] strArr) {
        sb.append("packageNames:");
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        if (strArr != null) {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                sb.append(strArr[i]);
                if (i < length - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
    }

    private static void appendEventTypes(StringBuilder sb, int i) {
        sb.append("eventTypes:");
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            sb.append(AccessibilityEvent.eventTypeToString(iNumberOfTrailingZeros));
            i &= ~iNumberOfTrailingZeros;
            if (i != 0) {
                sb.append(", ");
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
    }

    private static void appendFlags(StringBuilder sb, int i) {
        sb.append("flags:");
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            sb.append(flagToString(iNumberOfTrailingZeros));
            i &= ~iNumberOfTrailingZeros;
            if (i != 0) {
                sb.append(", ");
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
    }

    private static void appendCapabilities(StringBuilder sb, int i) {
        sb.append("capabilities:");
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            sb.append(capabilityToString(iNumberOfTrailingZeros));
            i &= ~iNumberOfTrailingZeros;
            if (i != 0) {
                sb.append(", ");
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
    }

    public static String feedbackTypeToString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            i &= ~iNumberOfTrailingZeros;
            if (iNumberOfTrailingZeros == 1) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append("FEEDBACK_SPOKEN");
            } else if (iNumberOfTrailingZeros == 2) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append("FEEDBACK_HAPTIC");
            } else if (iNumberOfTrailingZeros == 4) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append("FEEDBACK_AUDIBLE");
            } else if (iNumberOfTrailingZeros == 8) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append("FEEDBACK_VISUAL");
            } else if (iNumberOfTrailingZeros == 16) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append("FEEDBACK_GENERIC");
            } else if (iNumberOfTrailingZeros == 32) {
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                sb.append("FEEDBACK_BRAILLE");
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static String flagToString(int i) {
        if (i == 1) {
            return "DEFAULT";
        }
        if (i == 2) {
            return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
        }
        switch (i) {
            case 4:
                return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
            case 8:
                return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
            case 16:
                return "FLAG_REPORT_VIEW_IDS";
            case 32:
                return "FLAG_REQUEST_FILTER_KEY_EVENTS";
            case 64:
                return "FLAG_RETRIEVE_INTERACTIVE_WINDOWS";
            case 128:
                return "FLAG_ENABLE_ACCESSIBILITY_VOLUME";
            case 256:
                return "FLAG_REQUEST_ACCESSIBILITY_BUTTON";
            case 512:
                return "FLAG_REQUEST_FINGERPRINT_GESTURES";
            case 1024:
                return "FLAG_REQUEST_SHORTCUT_WARNING_DIALOG_SPOKEN_FEEDBACK";
            case 2048:
                return "FLAG_SERVICE_HANDLES_DOUBLE_TAP";
            case 4096:
                return "FLAG_REQUEST_MULTI_FINGER_GESTURES";
            case 8192:
                return "FLAG_REQUEST_2_FINGER_PASSTHROUGH";
            case 16384:
                return "FLAG_SEND_MOTION_EVENTS";
            case 32768:
                return "FLAG_INPUT_METHOD_EDITOR";
            default:
                return null;
        }
    }

    public static String capabilityToString(int i) {
        if (i == 1) {
            return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
        }
        if (i == 2) {
            return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
        }
        if (i == 8) {
            return "CAPABILITY_CAN_REQUEST_FILTER_KEY_EVENTS";
        }
        if (i == 16) {
            return "CAPABILITY_CAN_CONTROL_MAGNIFICATION";
        }
        if (i == 32) {
            return "CAPABILITY_CAN_PERFORM_GESTURES";
        }
        if (i == 64) {
            return "CAPABILITY_CAN_REQUEST_FINGERPRINT_GESTURES";
        }
        if (i == 128) {
            return "CAPABILITY_CAN_TAKE_SCREENSHOT";
        }
        return "UNKNOWN";
    }

    public List<CapabilityInfo> getCapabilityInfos() {
        return getCapabilityInfos(null);
    }

    public List<CapabilityInfo> getCapabilityInfos(Context context) {
        int i = this.mCapabilities;
        if (i == 0) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        SparseArray<CapabilityInfo> capabilityInfoSparseArray = getCapabilityInfoSparseArray(context);
        while (i != 0) {
            int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            i &= ~iNumberOfTrailingZeros;
            CapabilityInfo capabilityInfo = capabilityInfoSparseArray.get(iNumberOfTrailingZeros);
            if (capabilityInfo != null) {
                arrayList.add(capabilityInfo);
            }
        }
        return arrayList;
    }

    public List<SemCapabilityInfo> semGetCapabilityInfos(Context context) {
        ArrayList arrayList = new ArrayList();
        for (CapabilityInfo capabilityInfo : getCapabilityInfos(context)) {
            arrayList.add(new SemCapabilityInfo(context.getString(capabilityInfo.titleResId), context.getString(capabilityInfo.descResId)));
        }
        return arrayList;
    }

    private static SparseArray<CapabilityInfo> getCapabilityInfoSparseArray(Context context) {
        if (sAvailableCapabilityInfos == null) {
            SparseArray<CapabilityInfo> sparseArray = new SparseArray<>();
            sAvailableCapabilityInfos = sparseArray;
            sparseArray.put(1, new CapabilityInfo(1, R.string.capability_title_canRetrieveWindowContent, R.string.capability_desc_canRetrieveWindowContent));
            sAvailableCapabilityInfos.put(2, new CapabilityInfo(2, R.string.capability_title_canRequestTouchExploration, R.string.capability_desc_canRequestTouchExploration));
            sAvailableCapabilityInfos.put(8, new CapabilityInfo(8, R.string.capability_title_canRequestFilterKeyEvents, R.string.capability_desc_canRequestFilterKeyEvents));
            sAvailableCapabilityInfos.put(16, new CapabilityInfo(16, R.string.capability_title_canControlMagnification, R.string.capability_desc_canControlMagnification));
            sAvailableCapabilityInfos.put(32, new CapabilityInfo(32, R.string.capability_title_canPerformGestures, R.string.capability_desc_canPerformGestures));
            sAvailableCapabilityInfos.put(128, new CapabilityInfo(128, R.string.capability_title_canTakeScreenshot, R.string.capability_desc_canTakeScreenshot));
            if (context == null || fingerprintAvailable(context)) {
                sAvailableCapabilityInfos.put(64, new CapabilityInfo(64, R.string.capability_title_canCaptureFingerprintGestures, R.string.capability_desc_canCaptureFingerprintGestures));
            }
        }
        return sAvailableCapabilityInfos;
    }

    private static boolean fingerprintAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) && ((FingerprintManager) context.getSystemService(FingerprintManager.class)).isHardwareDetected();
    }

    public static final class CapabilityInfo {
        public final int capability;
        public final int descResId;
        public final int titleResId;

        public CapabilityInfo(int i, int i2, int i3) {
            this.capability = i;
            this.titleResId = i2;
            this.descResId = i3;
        }
    }

    public static final class SemCapabilityInfo {
        private final String description;
        private final String title;

        public String getTitle() {
            return this.title;
        }

        public String getDescription() {
            return this.description;
        }

        public SemCapabilityInfo(String str, String str2) {
            this.title = str;
            this.description = str2;
        }
    }
}
