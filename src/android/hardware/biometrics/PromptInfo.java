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
    public static final Parcelable.Creator<PromptInfo> CREATOR = new Parcelable.Creator<PromptInfo>() { // from class: android.hardware.biometrics.PromptInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptInfo createFromParcel(Parcel parcel) {
            return new PromptInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptInfo[] newArray(int i) {
            return new PromptInfo[i];
        }
    };
    private boolean mAllowBackgroundAuthentication;
    private List<Integer> mAllowedSensorIds;
    private int mAuthenticators;
    private String mClassNameIfItIsConfirmDeviceCredentialActivity;
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
    private ComponentName mRealCallerForConfirmDeviceCredentialActivity;
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PromptInfo() {
        this.mConfirmationRequested = true;
        this.mAllowedSensorIds = new ArrayList();
        this.mIsForLegacyFingerprintManager = false;
        this.mShowEmergencyCallButton = false;
        this.mUseParentProfileForDeviceCredential = false;
        this.mRealCallerForConfirmDeviceCredentialActivity = null;
        this.mClassNameIfItIsConfirmDeviceCredentialActivity = null;
    }

    PromptInfo(Parcel parcel) {
        this.mConfirmationRequested = true;
        this.mAllowedSensorIds = new ArrayList();
        this.mIsForLegacyFingerprintManager = false;
        this.mShowEmergencyCallButton = false;
        this.mUseParentProfileForDeviceCredential = false;
        this.mRealCallerForConfirmDeviceCredentialActivity = null;
        this.mClassNameIfItIsConfirmDeviceCredentialActivity = null;
        this.mLogoRes = parcel.readInt();
        this.mLogoBitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
        this.mLogoDescription = parcel.readString();
        this.mTitle = parcel.readCharSequence();
        this.mUseDefaultTitle = parcel.readBoolean();
        this.mSubtitle = parcel.readCharSequence();
        this.mUseDefaultSubtitle = parcel.readBoolean();
        this.mDescription = parcel.readCharSequence();
        this.mContentView = (PromptContentViewParcelable) parcel.readParcelable(PromptContentViewParcelable.class.getClassLoader(), PromptContentViewParcelable.class);
        this.mDeviceCredentialTitle = parcel.readCharSequence();
        this.mDeviceCredentialSubtitle = parcel.readCharSequence();
        this.mDeviceCredentialDescription = parcel.readCharSequence();
        this.mNegativeButtonText = parcel.readCharSequence();
        this.mConfirmationRequested = parcel.readBoolean();
        this.mDeviceCredentialAllowed = parcel.readBoolean();
        this.mAuthenticators = parcel.readInt();
        this.mDisallowBiometricsIfPolicyExists = parcel.readBoolean();
        this.mReceiveSystemEvents = parcel.readBoolean();
        this.mAllowedSensorIds = parcel.readArrayList(Integer.class.getClassLoader(), Integer.class);
        this.mAllowBackgroundAuthentication = parcel.readBoolean();
        this.mIgnoreEnrollmentState = parcel.readBoolean();
        this.mIsForLegacyFingerprintManager = parcel.readBoolean();
        this.mShowEmergencyCallButton = parcel.readBoolean();
        this.mUseParentProfileForDeviceCredential = parcel.readBoolean();
        this.mRealCallerForConfirmDeviceCredentialActivity = (ComponentName) parcel.readParcelable(ComponentName.class.getClassLoader(), ComponentName.class);
        this.mClassNameIfItIsConfirmDeviceCredentialActivity = parcel.readString();
        this.mSemDisplayId = parcel.readInt();
        this.mSemTaskId = parcel.readInt();
        this.mSemBiometricType = parcel.readInt();
        this.mSemPrivilegedFlag = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            byte[] bArr = new byte[readInt];
            this.mSemChallengeData = bArr;
            parcel.readByteArray(bArr);
            return;
        }
        this.mSemChallengeData = null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLogoRes);
        parcel.writeTypedObject(this.mLogoBitmap, 0);
        parcel.writeString(this.mLogoDescription);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeBoolean(this.mUseDefaultTitle);
        parcel.writeCharSequence(this.mSubtitle);
        parcel.writeBoolean(this.mUseDefaultSubtitle);
        parcel.writeCharSequence(this.mDescription);
        parcel.writeParcelable(this.mContentView, 0);
        parcel.writeCharSequence(this.mDeviceCredentialTitle);
        parcel.writeCharSequence(this.mDeviceCredentialSubtitle);
        parcel.writeCharSequence(this.mDeviceCredentialDescription);
        parcel.writeCharSequence(this.mNegativeButtonText);
        parcel.writeBoolean(this.mConfirmationRequested);
        parcel.writeBoolean(this.mDeviceCredentialAllowed);
        parcel.writeInt(this.mAuthenticators);
        parcel.writeBoolean(this.mDisallowBiometricsIfPolicyExists);
        parcel.writeBoolean(this.mReceiveSystemEvents);
        parcel.writeList(this.mAllowedSensorIds);
        parcel.writeBoolean(this.mAllowBackgroundAuthentication);
        parcel.writeBoolean(this.mIgnoreEnrollmentState);
        parcel.writeBoolean(this.mIsForLegacyFingerprintManager);
        parcel.writeBoolean(this.mShowEmergencyCallButton);
        parcel.writeBoolean(this.mUseParentProfileForDeviceCredential);
        parcel.writeParcelable(this.mRealCallerForConfirmDeviceCredentialActivity, 0);
        parcel.writeString(this.mClassNameIfItIsConfirmDeviceCredentialActivity);
        parcel.writeInt(this.mSemDisplayId);
        parcel.writeInt(this.mSemTaskId);
        parcel.writeInt(this.mSemBiometricType);
        parcel.writeInt(this.mSemPrivilegedFlag);
        byte[] bArr = this.mSemChallengeData;
        if (bArr != null && bArr.length > 0) {
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(this.mSemChallengeData);
        } else {
            parcel.writeInt(0);
        }
    }

    public boolean requiresTestOrInternalPermission() {
        if (this.mIsForLegacyFingerprintManager && this.mAllowedSensorIds.size() == 1 && !this.mAllowBackgroundAuthentication) {
            return false;
        }
        return !this.mAllowedSensorIds.isEmpty() || this.mAllowBackgroundAuthentication || this.mIsForLegacyFingerprintManager || this.mIgnoreEnrollmentState || this.mShowEmergencyCallButton || this.mRealCallerForConfirmDeviceCredentialActivity != null;
    }

    public boolean requiresInternalPermission() {
        return this.mDisallowBiometricsIfPolicyExists || this.mUseDefaultTitle || this.mUseDefaultSubtitle || this.mDeviceCredentialTitle != null || this.mDeviceCredentialSubtitle != null || this.mDeviceCredentialDescription != null || this.mReceiveSystemEvents;
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
        PromptContentViewParcelable promptContentViewParcelable = this.mContentView;
        return promptContentViewParcelable != null && (promptContentViewParcelable instanceof PromptContentViewWithMoreOptionsButton);
    }

    public void setLogo(int i, Bitmap bitmap) {
        this.mLogoRes = i;
        this.mLogoBitmap = bitmap;
    }

    public void setLogoDescription(String str) {
        this.mLogoDescription = str;
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    public void setUseDefaultTitle(boolean z) {
        this.mUseDefaultTitle = z;
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
    }

    public void setUseDefaultSubtitle(boolean z) {
        this.mUseDefaultSubtitle = z;
    }

    public void setDescription(CharSequence charSequence) {
        this.mDescription = charSequence;
    }

    public void setContentView(PromptContentView promptContentView) {
        this.mContentView = (PromptContentViewParcelable) promptContentView;
    }

    public void setDeviceCredentialTitle(CharSequence charSequence) {
        this.mDeviceCredentialTitle = charSequence;
    }

    public void setDeviceCredentialSubtitle(CharSequence charSequence) {
        this.mDeviceCredentialSubtitle = charSequence;
    }

    public void setDeviceCredentialDescription(CharSequence charSequence) {
        this.mDeviceCredentialDescription = charSequence;
    }

    public void setNegativeButtonText(CharSequence charSequence) {
        this.mNegativeButtonText = charSequence;
    }

    public void setConfirmationRequested(boolean z) {
        this.mConfirmationRequested = z;
    }

    public void setDeviceCredentialAllowed(boolean z) {
        this.mDeviceCredentialAllowed = z;
    }

    public void setAuthenticators(int i) {
        this.mAuthenticators = i;
    }

    public void setDisallowBiometricsIfPolicyExists(boolean z) {
        this.mDisallowBiometricsIfPolicyExists = z;
    }

    public void setReceiveSystemEvents(boolean z) {
        this.mReceiveSystemEvents = z;
    }

    public void setAllowedSensorIds(List<Integer> list) {
        this.mAllowedSensorIds.clear();
        this.mAllowedSensorIds.addAll(list);
    }

    public void setAllowBackgroundAuthentication(boolean z) {
        this.mAllowBackgroundAuthentication = z;
    }

    public void setIgnoreEnrollmentState(boolean z) {
        this.mIgnoreEnrollmentState = z;
    }

    public void setIsForLegacyFingerprintManager(int i) {
        this.mIsForLegacyFingerprintManager = true;
        this.mAllowedSensorIds.clear();
        this.mAllowedSensorIds.add(Integer.valueOf(i));
    }

    public void setShowEmergencyCallButton(boolean z) {
        this.mShowEmergencyCallButton = z;
    }

    public void setRealCallerForConfirmDeviceCredentialActivity(ComponentName componentName) {
        this.mRealCallerForConfirmDeviceCredentialActivity = componentName;
    }

    public void setUseParentProfileForDeviceCredential(boolean z) {
        this.mUseParentProfileForDeviceCredential = z;
    }

    void setClassNameIfItIsConfirmDeviceCredentialActivity(String str) {
        this.mClassNameIfItIsConfirmDeviceCredentialActivity = str;
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

    public ComponentName getRealCallerForConfirmDeviceCredentialActivity() {
        return this.mRealCallerForConfirmDeviceCredentialActivity;
    }

    public String getClassNameIfItIsConfirmDeviceCredentialActivity() {
        return this.mClassNameIfItIsConfirmDeviceCredentialActivity;
    }

    public void semSetDisplayId(int i) {
        this.mSemDisplayId = i;
    }

    public int semGetDisplayId() {
        return this.mSemDisplayId;
    }

    public void semSetTaskId(int i) {
        this.mSemTaskId = i;
    }

    public int semGetTaskId() {
        return this.mSemTaskId;
    }

    public void semSetBiometricType(int i) {
        this.mSemBiometricType = i;
    }

    public int semGetBiometricType() {
        return this.mSemBiometricType;
    }

    public void semSetPrivilegedFlag(int i) {
        this.mSemPrivilegedFlag = i;
    }

    public int semGetPrivilegedFlag() {
        return this.mSemPrivilegedFlag;
    }

    public void semSetChallengeData(byte[] bArr) {
        this.mSemChallengeData = Arrays.copyOf(bArr, bArr.length);
    }

    public byte[] semGetChallengeData() {
        return this.mSemChallengeData;
    }

    private boolean semIsContentViewVerticalListUsed() {
        PromptContentViewParcelable promptContentViewParcelable;
        return Flags.customBiometricPrompt() && (promptContentViewParcelable = this.mContentView) != null && (promptContentViewParcelable instanceof PromptVerticalListContentView);
    }

    public boolean semIsDescriptionOptionalUsed() {
        return isContentViewMoreOptionsButtonUsed() || semIsContentViewVerticalListUsed();
    }
}
