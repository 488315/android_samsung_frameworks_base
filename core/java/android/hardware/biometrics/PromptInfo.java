package android.hardware.biometrics;

import android.content.ComponentName;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class PromptInfo implements Parcelable {
    public static final Parcelable.Creator<PromptInfo> CREATOR =
            new Parcelable.Creator<
                    PromptInfo>() { // from class: android.hardware.biometrics.PromptInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PromptInfo createFromParcel(Parcel in) {
                    return new PromptInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PromptInfo[] newArray(int size) {
                    return new PromptInfo[size];
                }
            };
    private boolean mAllowBackgroundAuthentication;
    private List<Integer> mAllowedSensorIds;
    private int mAuthenticators;
    private ComponentName mComponentNameForConfirmDeviceCredentialActivity;
    private boolean mConfirmationRequested;
    private PromptContentViewParcelable mContentView;
    private CharSequence mDescription;
    private boolean mDeviceCredentialAllowed;
    private CharSequence mDeviceCredentialDescription;
    private CharSequence mDeviceCredentialSubtitle;
    private CharSequence mDeviceCredentialTitle;
    private boolean mDisallowBiometricsIfPolicyExists;
    private boolean mIgnoreEnrollmentState;
    private boolean mIsForLegacyFingerprintManager;
    private Bitmap mLogoBitmap;
    private String mLogoDescription;
    private int mLogoRes;
    private CharSequence mNegativeButtonText;
    private boolean mReceiveSystemEvents;
    private int mSemBiometricType;
    private byte[] mSemChallengeData;
    private int mSemDisplayId;
    private int mSemPrivilegedFlag;
    private int mSemTaskId;
    private boolean mShowEmergencyCallButton;
    private CharSequence mSubtitle;
    private CharSequence mTitle;
    private boolean mUseDefaultSubtitle;
    private boolean mUseDefaultTitle;
    private boolean mUseParentProfileForDeviceCredential;

    public PromptInfo() {
        this.mConfirmationRequested = true;
        this.mAllowedSensorIds = new ArrayList();
        this.mIsForLegacyFingerprintManager = false;
        this.mShowEmergencyCallButton = false;
        this.mUseParentProfileForDeviceCredential = false;
        this.mComponentNameForConfirmDeviceCredentialActivity = null;
    }

    PromptInfo(Parcel in) {
        this.mConfirmationRequested = true;
        this.mAllowedSensorIds = new ArrayList();
        this.mIsForLegacyFingerprintManager = false;
        this.mShowEmergencyCallButton = false;
        this.mUseParentProfileForDeviceCredential = false;
        this.mComponentNameForConfirmDeviceCredentialActivity = null;
        this.mLogoRes = in.readInt();
        this.mLogoBitmap = (Bitmap) in.readTypedObject(Bitmap.CREATOR);
        this.mLogoDescription = in.readString();
        this.mTitle = in.readCharSequence();
        this.mUseDefaultTitle = in.readBoolean();
        this.mSubtitle = in.readCharSequence();
        this.mUseDefaultSubtitle = in.readBoolean();
        this.mDescription = in.readCharSequence();
        this.mContentView =
                (PromptContentViewParcelable)
                        in.readParcelable(
                                PromptContentViewParcelable.class.getClassLoader(),
                                PromptContentViewParcelable.class);
        this.mDeviceCredentialTitle = in.readCharSequence();
        this.mDeviceCredentialSubtitle = in.readCharSequence();
        this.mDeviceCredentialDescription = in.readCharSequence();
        this.mNegativeButtonText = in.readCharSequence();
        this.mConfirmationRequested = in.readBoolean();
        this.mDeviceCredentialAllowed = in.readBoolean();
        this.mAuthenticators = in.readInt();
        this.mDisallowBiometricsIfPolicyExists = in.readBoolean();
        this.mReceiveSystemEvents = in.readBoolean();
        this.mAllowedSensorIds = in.readArrayList(Integer.class.getClassLoader(), Integer.class);
        this.mAllowBackgroundAuthentication = in.readBoolean();
        this.mIgnoreEnrollmentState = in.readBoolean();
        this.mIsForLegacyFingerprintManager = in.readBoolean();
        this.mShowEmergencyCallButton = in.readBoolean();
        this.mUseParentProfileForDeviceCredential = in.readBoolean();
        this.mComponentNameForConfirmDeviceCredentialActivity =
                (ComponentName)
                        in.readParcelable(
                                ComponentName.class.getClassLoader(), ComponentName.class);
        this.mSemDisplayId = in.readInt();
        this.mSemTaskId = in.readInt();
        this.mSemBiometricType = in.readInt();
        this.mSemPrivilegedFlag = in.readInt();
        int challengeDataLength = in.readInt();
        if (challengeDataLength > 0) {
            this.mSemChallengeData = new byte[challengeDataLength];
            in.readByteArray(this.mSemChallengeData);
        } else {
            this.mSemChallengeData = null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mLogoRes);
        dest.writeTypedObject(this.mLogoBitmap, 0);
        dest.writeString(this.mLogoDescription);
        dest.writeCharSequence(this.mTitle);
        dest.writeBoolean(this.mUseDefaultTitle);
        dest.writeCharSequence(this.mSubtitle);
        dest.writeBoolean(this.mUseDefaultSubtitle);
        dest.writeCharSequence(this.mDescription);
        dest.writeParcelable(this.mContentView, 0);
        dest.writeCharSequence(this.mDeviceCredentialTitle);
        dest.writeCharSequence(this.mDeviceCredentialSubtitle);
        dest.writeCharSequence(this.mDeviceCredentialDescription);
        dest.writeCharSequence(this.mNegativeButtonText);
        dest.writeBoolean(this.mConfirmationRequested);
        dest.writeBoolean(this.mDeviceCredentialAllowed);
        dest.writeInt(this.mAuthenticators);
        dest.writeBoolean(this.mDisallowBiometricsIfPolicyExists);
        dest.writeBoolean(this.mReceiveSystemEvents);
        dest.writeList(this.mAllowedSensorIds);
        dest.writeBoolean(this.mAllowBackgroundAuthentication);
        dest.writeBoolean(this.mIgnoreEnrollmentState);
        dest.writeBoolean(this.mIsForLegacyFingerprintManager);
        dest.writeBoolean(this.mShowEmergencyCallButton);
        dest.writeBoolean(this.mUseParentProfileForDeviceCredential);
        dest.writeParcelable(this.mComponentNameForConfirmDeviceCredentialActivity, 0);
        dest.writeInt(this.mSemDisplayId);
        dest.writeInt(this.mSemTaskId);
        dest.writeInt(this.mSemBiometricType);
        dest.writeInt(this.mSemPrivilegedFlag);
        if (this.mSemChallengeData != null && this.mSemChallengeData.length > 0) {
            dest.writeInt(this.mSemChallengeData.length);
            dest.writeByteArray(this.mSemChallengeData);
        } else {
            dest.writeInt(0);
        }
    }

    public boolean requiresTestOrInternalPermission() {
        if (this.mIsForLegacyFingerprintManager
                && this.mAllowedSensorIds.size() == 1
                && !this.mAllowBackgroundAuthentication) {
            return false;
        }
        return !this.mAllowedSensorIds.isEmpty()
                || this.mAllowBackgroundAuthentication
                || this.mIsForLegacyFingerprintManager
                || this.mIgnoreEnrollmentState
                || this.mShowEmergencyCallButton
                || this.mComponentNameForConfirmDeviceCredentialActivity != null;
    }

    public boolean requiresInternalPermission() {
        return this.mDisallowBiometricsIfPolicyExists
                || this.mUseDefaultTitle
                || this.mUseDefaultSubtitle
                || this.mDeviceCredentialTitle != null
                || this.mDeviceCredentialSubtitle != null
                || this.mDeviceCredentialDescription != null
                || this.mReceiveSystemEvents;
    }

    public boolean requiresAdvancedPermission() {
        if (this.mLogoRes != 0 || this.mLogoBitmap != null || this.mLogoDescription != null) {
            return true;
        }
        if (this.mContentView == null || !isContentViewMoreOptionsButtonUsed()) {
            return Flags.mandatoryBiometrics() && (this.mAuthenticators & 65536) != 0;
        }
        return true;
    }

    public boolean shouldUseParentProfileForDeviceCredential() {
        return this.mUseParentProfileForDeviceCredential;
    }

    public boolean isContentViewMoreOptionsButtonUsed() {
        return Flags.customBiometricPrompt()
                && this.mContentView != null
                && (this.mContentView instanceof PromptContentViewWithMoreOptionsButton);
    }

    public void setLogo(int logoRes, Bitmap logoBitmap) {
        this.mLogoRes = logoRes;
        this.mLogoBitmap = logoBitmap;
    }

    public void setLogoDescription(String logoDescription) {
        this.mLogoDescription = logoDescription;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    public void setUseDefaultTitle(boolean useDefaultTitle) {
        this.mUseDefaultTitle = useDefaultTitle;
    }

    public void setSubtitle(CharSequence subtitle) {
        this.mSubtitle = subtitle;
    }

    public void setUseDefaultSubtitle(boolean useDefaultSubtitle) {
        this.mUseDefaultSubtitle = useDefaultSubtitle;
    }

    public void setDescription(CharSequence description) {
        this.mDescription = description;
    }

    public void setContentView(PromptContentView view) {
        this.mContentView = (PromptContentViewParcelable) view;
    }

    public void setDeviceCredentialTitle(CharSequence deviceCredentialTitle) {
        this.mDeviceCredentialTitle = deviceCredentialTitle;
    }

    public void setDeviceCredentialSubtitle(CharSequence deviceCredentialSubtitle) {
        this.mDeviceCredentialSubtitle = deviceCredentialSubtitle;
    }

    public void setDeviceCredentialDescription(CharSequence deviceCredentialDescription) {
        this.mDeviceCredentialDescription = deviceCredentialDescription;
    }

    public void setNegativeButtonText(CharSequence negativeButtonText) {
        this.mNegativeButtonText = negativeButtonText;
    }

    public void setConfirmationRequested(boolean confirmationRequested) {
        this.mConfirmationRequested = confirmationRequested;
    }

    public void setDeviceCredentialAllowed(boolean deviceCredentialAllowed) {
        this.mDeviceCredentialAllowed = deviceCredentialAllowed;
    }

    public void setAuthenticators(int authenticators) {
        this.mAuthenticators = authenticators;
    }

    public void setDisallowBiometricsIfPolicyExists(boolean disallowBiometricsIfPolicyExists) {
        this.mDisallowBiometricsIfPolicyExists = disallowBiometricsIfPolicyExists;
    }

    public void setReceiveSystemEvents(boolean receiveSystemEvents) {
        this.mReceiveSystemEvents = receiveSystemEvents;
    }

    public void setAllowedSensorIds(List<Integer> sensorIds) {
        this.mAllowedSensorIds.clear();
        this.mAllowedSensorIds.addAll(sensorIds);
    }

    public void setAllowBackgroundAuthentication(boolean allow) {
        this.mAllowBackgroundAuthentication = allow;
    }

    public void setIgnoreEnrollmentState(boolean ignoreEnrollmentState) {
        this.mIgnoreEnrollmentState = ignoreEnrollmentState;
    }

    public void setIsForLegacyFingerprintManager(int sensorId) {
        this.mIsForLegacyFingerprintManager = true;
        this.mAllowedSensorIds.clear();
        this.mAllowedSensorIds.add(Integer.valueOf(sensorId));
    }

    public void setShowEmergencyCallButton(boolean showEmergencyCallButton) {
        this.mShowEmergencyCallButton = showEmergencyCallButton;
    }

    public void setComponentNameForConfirmDeviceCredentialActivity(
            ComponentName componentNameForConfirmDeviceCredentialActivity) {
        this.mComponentNameForConfirmDeviceCredentialActivity =
                componentNameForConfirmDeviceCredentialActivity;
    }

    public void setUseParentProfileForDeviceCredential(
            boolean useParentProfileForDeviceCredential) {
        this.mUseParentProfileForDeviceCredential = useParentProfileForDeviceCredential;
    }

    public Bitmap getLogo() {
        return this.mLogoBitmap;
    }

    public int getLogoRes() {
        return this.mLogoRes;
    }

    public Bitmap getLogoBitmap() {
        if (this.mLogoRes == 0) {
            return this.mLogoBitmap;
        }
        return null;
    }

    public String getLogoDescription() {
        return this.mLogoDescription;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public boolean isUseDefaultTitle() {
        return this.mUseDefaultTitle;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public boolean isUseDefaultSubtitle() {
        return this.mUseDefaultSubtitle;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public PromptContentView getContentView() {
        return this.mContentView;
    }

    public CharSequence getDeviceCredentialTitle() {
        return this.mDeviceCredentialTitle;
    }

    public CharSequence getDeviceCredentialSubtitle() {
        return this.mDeviceCredentialSubtitle;
    }

    public CharSequence getDeviceCredentialDescription() {
        return this.mDeviceCredentialDescription;
    }

    public CharSequence getNegativeButtonText() {
        return this.mNegativeButtonText;
    }

    public boolean isConfirmationRequested() {
        return this.mConfirmationRequested;
    }

    @Deprecated
    public boolean isDeviceCredentialAllowed() {
        return this.mDeviceCredentialAllowed;
    }

    public int getAuthenticators() {
        return this.mAuthenticators;
    }

    public boolean isDisallowBiometricsIfPolicyExists() {
        return this.mDisallowBiometricsIfPolicyExists;
    }

    public boolean isReceiveSystemEvents() {
        return this.mReceiveSystemEvents;
    }

    public List<Integer> getAllowedSensorIds() {
        return this.mAllowedSensorIds;
    }

    public boolean isAllowBackgroundAuthentication() {
        return this.mAllowBackgroundAuthentication;
    }

    public boolean isIgnoreEnrollmentState() {
        return this.mIgnoreEnrollmentState;
    }

    public boolean isForLegacyFingerprintManager() {
        return this.mIsForLegacyFingerprintManager;
    }

    public boolean isShowEmergencyCallButton() {
        return this.mShowEmergencyCallButton;
    }

    public ComponentName getComponentNameForConfirmDeviceCredentialActivity() {
        return this.mComponentNameForConfirmDeviceCredentialActivity;
    }

    public void semSetDisplayId(int displayId) {
        this.mSemDisplayId = displayId;
    }

    public int semGetDisplayId() {
        return this.mSemDisplayId;
    }

    public void semSetTaskId(int taskId) {
        this.mSemTaskId = taskId;
    }

    public int semGetTaskId() {
        return this.mSemTaskId;
    }

    public void semSetBiometricType(int type) {
        this.mSemBiometricType = type;
    }

    public int semGetBiometricType() {
        return this.mSemBiometricType;
    }

    public void semSetPrivilegedFlag(int flag) {
        this.mSemPrivilegedFlag = flag;
    }

    public int semGetPrivilegedFlag() {
        return this.mSemPrivilegedFlag;
    }

    public void semSetChallengeData(byte[] data) {
        this.mSemChallengeData = Arrays.copyOf(data, data.length);
    }

    public byte[] semGetChallengeData() {
        return this.mSemChallengeData;
    }

    private boolean semIsContentViewVerticalListUsed() {
        return Flags.customBiometricPrompt()
                && this.mContentView != null
                && (this.mContentView instanceof PromptVerticalListContentView);
    }

    public boolean semIsDescriptionOptionalUsed() {
        return isContentViewMoreOptionsButtonUsed() || semIsContentViewVerticalListUsed();
    }
}
