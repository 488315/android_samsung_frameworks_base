package android.media.tv;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class TvInputInfo implements Parcelable {
    public static final Parcelable.Creator<TvInputInfo> CREATOR = new Parcelable.Creator<TvInputInfo>() { // from class: android.media.tv.TvInputInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvInputInfo createFromParcel(Parcel parcel) {
            return new TvInputInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TvInputInfo[] newArray(int i) {
            return new TvInputInfo[i];
        }
    };
    private static final boolean DEBUG = false;
    public static final String EXTRA_INPUT_ID = "android.media.tv.extra.INPUT_ID";
    private static final String TAG = "TvInputInfo";
    public static final int TYPE_COMPONENT = 1004;
    public static final int TYPE_COMPOSITE = 1001;
    public static final int TYPE_DISPLAY_PORT = 1008;
    public static final int TYPE_DVI = 1006;
    public static final int TYPE_HDMI = 1007;
    public static final int TYPE_OTHER = 1000;
    public static final int TYPE_SCART = 1003;
    public static final int TYPE_SVIDEO = 1002;
    public static final int TYPE_TUNER = 0;
    public static final int TYPE_VGA = 1005;
    private final boolean mCanPauseRecording;
    private final boolean mCanRecord;
    private final Bundle mExtras;
    private final int mHdmiConnectionRelativePosition;
    private final HdmiDeviceInfo mHdmiDeviceInfo;
    private final Icon mIcon;
    private final Icon mIconDisconnected;
    private final Icon mIconStandby;
    private Uri mIconUri;
    private final String mId;
    private final boolean mIsConnectedToHdmiSwitch;
    private final boolean mIsHardwareInput;
    private final CharSequence mLabel;
    private final int mLabelResId;
    private final String mParentId;
    private final ResolveInfo mService;
    private final String mSetupActivity;
    private final int mTunerCount;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Deprecated
    public Intent createSettingsIntent() {
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo resolveInfo, HdmiDeviceInfo hdmiDeviceInfo, String str, String str2, Uri uri) throws XmlPullParserException, IOException {
        TvInputInfo tvInputInfoBuild = new Builder(context, resolveInfo).setHdmiDeviceInfo(hdmiDeviceInfo).setParentId(str).setLabel(str2).build();
        tvInputInfoBuild.mIconUri = uri;
        return tvInputInfoBuild;
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo resolveInfo, HdmiDeviceInfo hdmiDeviceInfo, String str, int i, Icon icon) throws XmlPullParserException, IOException {
        return new Builder(context, resolveInfo).setHdmiDeviceInfo(hdmiDeviceInfo).setParentId(str).setLabel(i).setIcon(icon).build();
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo resolveInfo, TvInputHardwareInfo tvInputHardwareInfo, String str, Uri uri) throws XmlPullParserException, IOException {
        TvInputInfo tvInputInfoBuild = new Builder(context, resolveInfo).setTvInputHardwareInfo(tvInputHardwareInfo).setLabel(str).build();
        tvInputInfoBuild.mIconUri = uri;
        return tvInputInfoBuild;
    }

    @SystemApi
    @Deprecated
    public static TvInputInfo createTvInputInfo(Context context, ResolveInfo resolveInfo, TvInputHardwareInfo tvInputHardwareInfo, int i, Icon icon) throws XmlPullParserException, IOException {
        return new Builder(context, resolveInfo).setTvInputHardwareInfo(tvInputHardwareInfo).setLabel(i).setIcon(icon).build();
    }

    private TvInputInfo(ResolveInfo resolveInfo, String str, int i, boolean z, CharSequence charSequence, int i2, Icon icon, Icon icon2, Icon icon3, String str2, boolean z2, boolean z3, int i3, HdmiDeviceInfo hdmiDeviceInfo, boolean z4, int i4, String str3, Bundle bundle) {
        this.mService = resolveInfo;
        this.mId = str;
        this.mType = i;
        this.mIsHardwareInput = z;
        this.mLabel = charSequence;
        this.mLabelResId = i2;
        this.mIcon = icon;
        this.mIconStandby = icon2;
        this.mIconDisconnected = icon3;
        this.mSetupActivity = str2;
        this.mCanRecord = z2;
        this.mCanPauseRecording = z3;
        this.mTunerCount = i3;
        this.mHdmiDeviceInfo = hdmiDeviceInfo;
        this.mIsConnectedToHdmiSwitch = z4;
        this.mHdmiConnectionRelativePosition = i4;
        this.mParentId = str3;
        this.mExtras = bundle;
    }

    public String getId() {
        return this.mId;
    }

    public String getParentId() {
        return this.mParentId;
    }

    public ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public Intent createSetupIntent() {
        if (TextUtils.isEmpty(this.mSetupActivity)) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName(this.mService.serviceInfo.packageName, this.mSetupActivity);
        intent.putExtra(EXTRA_INPUT_ID, getId());
        return intent;
    }

    public int getType() {
        return this.mType;
    }

    public int getTunerCount() {
        return this.mTunerCount;
    }

    public boolean canRecord() {
        return this.mCanRecord;
    }

    public boolean canPauseRecording() {
        return this.mCanPauseRecording;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @SystemApi
    public HdmiDeviceInfo getHdmiDeviceInfo() {
        if (this.mType == 1007) {
            return this.mHdmiDeviceInfo;
        }
        return null;
    }

    public boolean isPassthroughInput() {
        return this.mType != 0;
    }

    @SystemApi
    public boolean isHardwareInput() {
        return this.mIsHardwareInput;
    }

    @SystemApi
    public boolean isConnectedToHdmiSwitch() {
        return this.mIsConnectedToHdmiSwitch;
    }

    public int getHdmiConnectionRelativePosition() {
        return this.mHdmiConnectionRelativePosition;
    }

    public boolean isHidden(Context context) {
        return TvInputSettings.isHidden(context, this.mId, UserHandle.myUserId());
    }

    public CharSequence loadLabel(Context context) {
        if (this.mLabelResId != 0) {
            return context.getPackageManager().getText(this.mService.serviceInfo.packageName, this.mLabelResId, null);
        }
        if (!TextUtils.isEmpty(this.mLabel)) {
            return this.mLabel;
        }
        return this.mService.loadLabel(context.getPackageManager());
    }

    public CharSequence loadCustomLabel(Context context) {
        return TvInputSettings.getCustomLabel(context, this.mId, UserHandle.myUserId());
    }

    public Drawable loadIcon(Context context) throws IOException {
        Icon icon = this.mIcon;
        if (icon != null) {
            return icon.loadDrawable(context);
        }
        if (this.mIconUri != null) {
            try {
                InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(this.mIconUri);
                try {
                    Drawable drawableCreateFromStream = Drawable.createFromStream(inputStreamOpenInputStream, null);
                    if (drawableCreateFromStream != null) {
                        if (inputStreamOpenInputStream != null) {
                            inputStreamOpenInputStream.close();
                        }
                        return drawableCreateFromStream;
                    }
                    if (inputStreamOpenInputStream != null) {
                        inputStreamOpenInputStream.close();
                    }
                } finally {
                }
            } catch (IOException e) {
                Log.w(TAG, "Loading the default icon due to a failure on loading " + this.mIconUri, e);
            }
        }
        return loadServiceIcon(context);
    }

    @SystemApi
    public Drawable loadIcon(Context context, int i) {
        if (i == 0) {
            return loadIcon(context);
        }
        if (i == 1) {
            Icon icon = this.mIconStandby;
            if (icon != null) {
                return icon.loadDrawable(context);
            }
            return null;
        }
        if (i == 2) {
            Icon icon2 = this.mIconDisconnected;
            if (icon2 != null) {
                return icon2.loadDrawable(context);
            }
            return null;
        }
        throw new IllegalArgumentException("Unknown state: " + i);
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TvInputInfo)) {
            return false;
        }
        TvInputInfo tvInputInfo = (TvInputInfo) obj;
        return Objects.equals(this.mService, tvInputInfo.mService) && TextUtils.equals(this.mId, tvInputInfo.mId) && this.mType == tvInputInfo.mType && this.mIsHardwareInput == tvInputInfo.mIsHardwareInput && TextUtils.equals(this.mLabel, tvInputInfo.mLabel) && Objects.equals(this.mIconUri, tvInputInfo.mIconUri) && this.mLabelResId == tvInputInfo.mLabelResId && Objects.equals(this.mIcon, tvInputInfo.mIcon) && Objects.equals(this.mIconStandby, tvInputInfo.mIconStandby) && Objects.equals(this.mIconDisconnected, tvInputInfo.mIconDisconnected) && TextUtils.equals(this.mSetupActivity, tvInputInfo.mSetupActivity) && this.mCanRecord == tvInputInfo.mCanRecord && this.mCanPauseRecording == tvInputInfo.mCanPauseRecording && this.mTunerCount == tvInputInfo.mTunerCount && Objects.equals(this.mHdmiDeviceInfo, tvInputInfo.mHdmiDeviceInfo) && this.mIsConnectedToHdmiSwitch == tvInputInfo.mIsConnectedToHdmiSwitch && this.mHdmiConnectionRelativePosition == tvInputInfo.mHdmiConnectionRelativePosition && TextUtils.equals(this.mParentId, tvInputInfo.mParentId) && Objects.equals(this.mExtras, tvInputInfo.mExtras);
    }

    public String toString() {
        return "TvInputInfo{id=" + this.mId + ", pkg=" + this.mService.serviceInfo.packageName + ", service=" + this.mService.serviceInfo.name + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeByte(this.mIsHardwareInput ? (byte) 1 : (byte) 0);
        TextUtils.writeToParcel(this.mLabel, parcel, i);
        parcel.writeParcelable(this.mIconUri, i);
        parcel.writeInt(this.mLabelResId);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeParcelable(this.mIconStandby, i);
        parcel.writeParcelable(this.mIconDisconnected, i);
        parcel.writeString(this.mSetupActivity);
        parcel.writeByte(this.mCanRecord ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mCanPauseRecording ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mTunerCount);
        parcel.writeParcelable(this.mHdmiDeviceInfo, i);
        parcel.writeByte(this.mIsConnectedToHdmiSwitch ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mHdmiConnectionRelativePosition);
        parcel.writeString(this.mParentId);
        parcel.writeBundle(this.mExtras);
    }

    private Drawable loadServiceIcon(Context context) {
        if (this.mService.serviceInfo.icon == 0 && this.mService.serviceInfo.applicationInfo.icon == 0) {
            return null;
        }
        return this.mService.serviceInfo.loadIcon(context.getPackageManager());
    }

    private TvInputInfo(Parcel parcel) {
        this.mService = ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mType = parcel.readInt();
        this.mIsHardwareInput = parcel.readByte() == 1;
        this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIconUri = (Uri) parcel.readParcelable(null, Uri.class);
        this.mLabelResId = parcel.readInt();
        this.mIcon = (Icon) parcel.readParcelable(null, Icon.class);
        this.mIconStandby = (Icon) parcel.readParcelable(null, Icon.class);
        this.mIconDisconnected = (Icon) parcel.readParcelable(null, Icon.class);
        this.mSetupActivity = parcel.readString();
        this.mCanRecord = parcel.readByte() == 1;
        this.mCanPauseRecording = parcel.readByte() == 1;
        this.mTunerCount = parcel.readInt();
        this.mHdmiDeviceInfo = (HdmiDeviceInfo) parcel.readParcelable(null, HdmiDeviceInfo.class);
        this.mIsConnectedToHdmiSwitch = parcel.readByte() == 1;
        this.mHdmiConnectionRelativePosition = parcel.readInt();
        this.mParentId = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    public static final class Builder {
        private static final String DELIMITER_INFO_IN_ID = "/";
        private static final int LENGTH_HDMI_DEVICE_ID = 2;
        private static final int LENGTH_HDMI_PHYSICAL_ADDRESS = 4;
        private static final String PREFIX_HARDWARE_DEVICE = "HW";
        private static final String PREFIX_HDMI_DEVICE = "HDMI";
        private static final String XML_START_TAG_NAME = "tv-input";
        private static final SparseIntArray sHardwareTypeToTvInputType;
        private Boolean mCanPauseRecording;
        private Boolean mCanRecord;
        private final Context mContext;
        private Bundle mExtras;
        private HdmiDeviceInfo mHdmiDeviceInfo;
        private Icon mIcon;
        private Icon mIconDisconnected;
        private Icon mIconStandby;
        private CharSequence mLabel;
        private int mLabelResId;
        private String mParentId;
        private final ResolveInfo mResolveInfo;
        private String mSetupActivity;
        private Integer mTunerCount;
        private TvInputHardwareInfo mTvInputHardwareInfo;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sHardwareTypeToTvInputType = sparseIntArray;
            sparseIntArray.put(1, 1000);
            sparseIntArray.put(2, 0);
            sparseIntArray.put(3, 1001);
            sparseIntArray.put(4, 1002);
            sparseIntArray.put(5, 1003);
            sparseIntArray.put(6, 1004);
            sparseIntArray.put(7, 1005);
            sparseIntArray.put(8, 1006);
            sparseIntArray.put(9, 1007);
            sparseIntArray.put(10, 1008);
        }

        public Builder(Context context, ComponentName componentName) {
            if (context == null) {
                throw new IllegalArgumentException("context cannot be null.");
            }
            ResolveInfo resolveInfoResolveService = context.getPackageManager().resolveService(new Intent(TvInputService.SERVICE_INTERFACE).setComponent(componentName), 132);
            this.mResolveInfo = resolveInfoResolveService;
            if (resolveInfoResolveService == null) {
                throw new IllegalArgumentException("Invalid component. Can't find the service.");
            }
            this.mContext = context;
        }

        public Builder(Context context, ResolveInfo resolveInfo) {
            if (context == null) {
                throw new IllegalArgumentException("context cannot be null");
            }
            if (resolveInfo == null) {
                throw new IllegalArgumentException("resolveInfo cannot be null");
            }
            this.mContext = context;
            this.mResolveInfo = resolveInfo;
        }

        @SystemApi
        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        @SystemApi
        public Builder setIcon(Icon icon, int i) {
            if (i == 0) {
                this.mIcon = icon;
                return this;
            }
            if (i == 1) {
                this.mIconStandby = icon;
                return this;
            }
            if (i == 2) {
                this.mIconDisconnected = icon;
                return this;
            }
            throw new IllegalArgumentException("Unknown state: " + i);
        }

        @SystemApi
        public Builder setLabel(CharSequence charSequence) {
            if (this.mLabelResId != 0) {
                throw new IllegalStateException("Resource ID for label is already set.");
            }
            this.mLabel = charSequence;
            return this;
        }

        @SystemApi
        public Builder setLabel(int i) {
            if (this.mLabel != null) {
                throw new IllegalStateException("Label text is already set.");
            }
            this.mLabelResId = i;
            return this;
        }

        @SystemApi
        public Builder setHdmiDeviceInfo(HdmiDeviceInfo hdmiDeviceInfo) {
            if (this.mTvInputHardwareInfo != null) {
                Log.w(TvInputInfo.TAG, "TvInputHardwareInfo will not be used to build this TvInputInfo");
                this.mTvInputHardwareInfo = null;
            }
            this.mHdmiDeviceInfo = hdmiDeviceInfo;
            return this;
        }

        @SystemApi
        public Builder setParentId(String str) {
            this.mParentId = str;
            return this;
        }

        @SystemApi
        public Builder setTvInputHardwareInfo(TvInputHardwareInfo tvInputHardwareInfo) {
            if (this.mHdmiDeviceInfo != null) {
                Log.w(TvInputInfo.TAG, "mHdmiDeviceInfo will not be used to build this TvInputInfo");
                this.mHdmiDeviceInfo = null;
            }
            this.mTvInputHardwareInfo = tvInputHardwareInfo;
            return this;
        }

        public Builder setTunerCount(int i) {
            this.mTunerCount = Integer.valueOf(i);
            return this;
        }

        public Builder setCanRecord(boolean z) {
            this.mCanRecord = Boolean.valueOf(z);
            return this;
        }

        public Builder setCanPauseRecording(boolean z) {
            this.mCanPauseRecording = Boolean.valueOf(z);
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x008b  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x008e  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0098  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x009b  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00a6  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public TvInputInfo build() {
            String strGenerateInputId;
            int i;
            boolean z;
            boolean z2;
            int i2;
            int i3;
            ComponentName componentName = new ComponentName(this.mResolveInfo.serviceInfo.packageName, this.mResolveInfo.serviceInfo.name);
            HdmiDeviceInfo hdmiDeviceInfo = this.mHdmiDeviceInfo;
            if (hdmiDeviceInfo != null) {
                String strGenerateInputId2 = generateInputId(componentName, hdmiDeviceInfo);
                int relativePosition = getRelativePosition(this.mContext, this.mHdmiDeviceInfo);
                i3 = 1007;
                strGenerateInputId = strGenerateInputId2;
                i2 = relativePosition;
                z = true;
                z2 = relativePosition == 2;
            } else {
                TvInputHardwareInfo tvInputHardwareInfo = this.mTvInputHardwareInfo;
                if (tvInputHardwareInfo != null) {
                    String strGenerateInputId3 = generateInputId(componentName, tvInputHardwareInfo);
                    i3 = sHardwareTypeToTvInputType.get(this.mTvInputHardwareInfo.getType(), 0);
                    if (this.mTvInputHardwareInfo.getType() == 9) {
                        this.mHdmiDeviceInfo = HdmiDeviceInfo.hardwarePort(65535, this.mTvInputHardwareInfo.getHdmiPortId());
                    }
                    strGenerateInputId = strGenerateInputId3;
                    z = true;
                    z2 = false;
                    i2 = 0;
                } else {
                    strGenerateInputId = generateInputId(componentName);
                    i = 0;
                    z = false;
                    z2 = false;
                    i2 = 0;
                    parseServiceMetadata(i);
                    ResolveInfo resolveInfo = this.mResolveInfo;
                    CharSequence charSequence = this.mLabel;
                    int i4 = this.mLabelResId;
                    Icon icon = this.mIcon;
                    Icon icon2 = this.mIconStandby;
                    Icon icon3 = this.mIconDisconnected;
                    String str = this.mSetupActivity;
                    Boolean bool = this.mCanRecord;
                    boolean zBooleanValue = bool != null ? false : bool.booleanValue();
                    Boolean bool2 = this.mCanPauseRecording;
                    boolean zBooleanValue2 = bool2 != null ? false : bool2.booleanValue();
                    Integer num = this.mTunerCount;
                    return new TvInputInfo(resolveInfo, strGenerateInputId, i, z, charSequence, i4, icon, icon2, icon3, str, zBooleanValue, zBooleanValue2, num != null ? num.intValue() : 0, this.mHdmiDeviceInfo, z2, i2, this.mParentId, this.mExtras);
                }
            }
            i = i3;
            parseServiceMetadata(i);
            ResolveInfo resolveInfo2 = this.mResolveInfo;
            CharSequence charSequence2 = this.mLabel;
            int i42 = this.mLabelResId;
            Icon icon4 = this.mIcon;
            Icon icon22 = this.mIconStandby;
            Icon icon32 = this.mIconDisconnected;
            String str2 = this.mSetupActivity;
            Boolean bool3 = this.mCanRecord;
            if (bool3 != null) {
            }
            Boolean bool22 = this.mCanPauseRecording;
            if (bool22 != null) {
            }
            Integer num2 = this.mTunerCount;
            return new TvInputInfo(resolveInfo2, strGenerateInputId, i, z, charSequence2, i42, icon4, icon22, icon32, str2, zBooleanValue, zBooleanValue2, num2 != null ? num2.intValue() : 0, this.mHdmiDeviceInfo, z2, i2, this.mParentId, this.mExtras);
        }

        private static String generateInputId(ComponentName componentName) {
            return componentName.flattenToShortString();
        }

        private static String generateInputId(ComponentName componentName, HdmiDeviceInfo hdmiDeviceInfo) {
            return componentName.flattenToShortString() + String.format(Locale.ENGLISH, "/HDMI%04X%02X", Integer.valueOf(hdmiDeviceInfo.getPhysicalAddress()), Integer.valueOf(hdmiDeviceInfo.getId()));
        }

        private static String generateInputId(ComponentName componentName, TvInputHardwareInfo tvInputHardwareInfo) {
            return componentName.flattenToShortString() + "/HW" + tvInputHardwareInfo.getDeviceId();
        }

        private static int getRelativePosition(Context context, HdmiDeviceInfo hdmiDeviceInfo) {
            HdmiControlManager hdmiControlManager = (HdmiControlManager) context.getSystemService(Context.HDMI_CONTROL_SERVICE);
            if (hdmiControlManager == null) {
                return 0;
            }
            return HdmiUtils.getHdmiAddressRelativePosition(hdmiDeviceInfo.getPhysicalAddress(), hdmiControlManager.getPhysicalAddress());
        }

        private void parseServiceMetadata(int i) {
            int next;
            ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                try {
                    XmlResourceParser xmlResourceParserLoadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, TvInputService.SERVICE_META_DATA);
                    try {
                        if (xmlResourceParserLoadXmlMetaData == null) {
                            throw new IllegalStateException("No android.media.tv.input meta-data found for " + serviceInfo.name);
                        }
                        Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData);
                        do {
                            next = xmlResourceParserLoadXmlMetaData.next();
                            if (next == 1) {
                                break;
                            }
                        } while (next != 2);
                        if (!XML_START_TAG_NAME.equals(xmlResourceParserLoadXmlMetaData.getName())) {
                            throw new IllegalStateException("Meta-data does not start with tv-input tag for " + serviceInfo.name);
                        }
                        TypedArray typedArrayObtainAttributes = resourcesForApplication.obtainAttributes(attributeSetAsAttributeSet, R.styleable.TvInputService);
                        this.mSetupActivity = typedArrayObtainAttributes.getString(1);
                        if (this.mCanRecord == null) {
                            this.mCanRecord = Boolean.valueOf(typedArrayObtainAttributes.getBoolean(2, false));
                        }
                        if (this.mTunerCount == null && i == 0) {
                            this.mTunerCount = Integer.valueOf(typedArrayObtainAttributes.getInt(3, 1));
                        }
                        if (this.mCanPauseRecording == null) {
                            this.mCanPauseRecording = Boolean.valueOf(typedArrayObtainAttributes.getBoolean(4, false));
                        }
                        typedArrayObtainAttributes.recycle();
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            xmlResourceParserLoadXmlMetaData.close();
                        }
                    } catch (Throwable th) {
                        if (xmlResourceParserLoadXmlMetaData != null) {
                            try {
                                xmlResourceParserLoadXmlMetaData.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    throw new IllegalStateException("No resources found for " + serviceInfo.packageName, e);
                }
            } catch (IOException | XmlPullParserException e2) {
                throw new IllegalStateException("Failed reading meta-data for " + serviceInfo.packageName, e2);
            }
        }
    }

    @SystemApi
    public static final class TvInputSettings {
        private static final String CUSTOM_NAME_SEPARATOR = ",";
        private static final String TV_INPUT_SEPARATOR = ":";

        private TvInputSettings() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isHidden(Context context, String str, int i) {
            return getHiddenTvInputIds(context, i).contains(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String getCustomLabel(Context context, String str, int i) {
            return getCustomLabels(context, i).get(str);
        }

        @SystemApi
        public static Set<String> getHiddenTvInputIds(Context context, int i) {
            String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), Settings.Secure.TV_INPUT_HIDDEN_INPUTS, i);
            HashSet hashSet = new HashSet();
            if (!TextUtils.isEmpty(stringForUser)) {
                for (String str : stringForUser.split(":")) {
                    hashSet.add(Uri.decode(str));
                }
            }
            return hashSet;
        }

        @SystemApi
        public static Map<String, String> getCustomLabels(Context context, int i) {
            String stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), Settings.Secure.TV_INPUT_CUSTOM_LABELS, i);
            HashMap map = new HashMap();
            if (!TextUtils.isEmpty(stringForUser)) {
                for (String str : stringForUser.split(":")) {
                    String[] strArrSplit = str.split(",");
                    map.put(Uri.decode(strArrSplit[0]), Uri.decode(strArrSplit[1]));
                }
            }
            return map;
        }

        @SystemApi
        public static void putHiddenTvInputs(Context context, Set<String> set, int i) {
            StringBuilder sb = new StringBuilder();
            boolean z = true;
            for (String str : set) {
                ensureValidField(str);
                if (z) {
                    z = false;
                } else {
                    sb.append(":");
                }
                sb.append(Uri.encode(str));
            }
            Settings.Secure.putStringForUser(context.getContentResolver(), Settings.Secure.TV_INPUT_HIDDEN_INPUTS, sb.toString(), i);
            TvInputManager tvInputManager = (TvInputManager) context.getSystemService(Context.TV_INPUT_SERVICE);
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                TvInputInfo tvInputInfo = tvInputManager.getTvInputInfo(it.next());
                if (tvInputInfo != null) {
                    tvInputManager.updateTvInputInfo(tvInputInfo);
                }
            }
        }

        @SystemApi
        public static void putCustomLabels(Context context, Map<String, String> map, int i) {
            StringBuilder sb = new StringBuilder();
            boolean z = true;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                ensureValidField(entry.getKey());
                ensureValidField(entry.getValue());
                if (z) {
                    z = false;
                } else {
                    sb.append(":");
                }
                sb.append(Uri.encode(entry.getKey()));
                sb.append(",");
                sb.append(Uri.encode(entry.getValue()));
            }
            Settings.Secure.putStringForUser(context.getContentResolver(), Settings.Secure.TV_INPUT_CUSTOM_LABELS, sb.toString(), i);
            TvInputManager tvInputManager = (TvInputManager) context.getSystemService(Context.TV_INPUT_SERVICE);
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                TvInputInfo tvInputInfo = tvInputManager.getTvInputInfo(it.next());
                if (tvInputInfo != null) {
                    tvInputManager.updateTvInputInfo(tvInputInfo);
                }
            }
        }

        private static void ensureValidField(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException(str + " should not empty ");
            }
        }
    }
}
