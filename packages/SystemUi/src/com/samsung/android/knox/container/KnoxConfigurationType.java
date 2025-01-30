package com.samsung.android.knox.container;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Pair;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class KnoxConfigurationType implements Parcelable {
    public static final Parcelable.Creator<KnoxConfigurationType> CREATOR = new Parcelable.Creator<KnoxConfigurationType>() { // from class: com.samsung.android.knox.container.KnoxConfigurationType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final KnoxConfigurationType createFromParcel(Parcel parcel) {
            return new KnoxConfigurationType(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final KnoxConfigurationType createFromParcel(Parcel parcel) {
            return new KnoxConfigurationType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final KnoxConfigurationType[] newArray(int i) {
            Log.d(KnoxConfigurationType.TAG, "KnoxConfigurationType[] array to be created");
            return new KnoxConfigurationType[i];
        }
    };
    public static final boolean DEBUG = false;
    public static final int MIN_INVALID_PASSWORD_LEN = 257;
    public static final String TAG = "KnoxConfigurationType";
    public int mAdminUid;
    public HashMap<String, List<Pair<String, String>>> mAllowChangeDataSettings;
    public boolean mAllowMultiwindowMode;
    public boolean mAllowSwitch;
    public boolean mAllowTaskManager;
    public boolean mAllowUSBDebugging;
    public List<String> mAppInstallationList;
    public List<String> mAppRemoveList;
    public AuthenticationConfig mAuthenticationConfig;
    public int mBiometricAuthValue;
    public String mCustomBadgeIcon;
    public String mCustomHomeScreenWallpaper;
    public String mCustomLockScreenWallpaper;
    public String mCustomStatusIcon;
    public String mCustomStatusLabel;
    public boolean mEC;
    public String mECBadge;
    public String mECIcon;
    public String mECName;
    public List<String> mFOTADisableAppList;
    public List<String> mFOTAReenableAppList;
    public List<String> mForbiddenStrings;
    public List<String> mGoogleAppsList;
    public boolean mIsBiometricAuthEnabled;
    public boolean mIsDefaultConfigType;
    public int mKeyguardDisabledFeatures;
    public int mLayoutType;
    public boolean mManagedType;
    public int mMaximumCharacterOccurences;
    public int mMaximumCharacterSequenceLength;
    public int mMaximumFailedPasswordsForWipe;
    public int mMaximumNumericSequenceLength;
    public int mMaximumTimeToLock;
    public boolean mMultifactorAuthEnabled;
    public String mName;
    public String mNameIcon;
    public int mPasswordMinimumLength;
    public int mPasswordMinimumLetters;
    public int mPasswordMinimumLowerCase;
    public int mPasswordMinimumNonLetters;
    public int mPasswordMinimumNumeric;
    public int mPasswordMinimumSymbols;
    public int mPasswordMinimumUpperCase;
    public String mPasswordPattern;
    public int mPasswordQuality;
    public List<Integer> mPersonaList;
    public List<String> mProtectedList;
    public HashMap<String, List<Pair<String, String>>> mRCPDataSettings;
    public HashMap<String, List<Pair<String, String>>> mRCPNotifSettings;
    public boolean mSimplePasswordEnabled;
    public int mUserId;
    public String mVersion;

    public KnoxConfigurationType() {
        this.mAdminUid = 0;
        this.mUserId = -1;
        this.mVersion = "custom";
        this.mPasswordMinimumNonLetters = 0;
        this.mPasswordMinimumLetters = 0;
        this.mPasswordMinimumNumeric = 0;
        this.mPasswordMinimumUpperCase = 0;
        this.mPasswordMinimumLowerCase = 0;
        this.mPasswordMinimumSymbols = 0;
        this.mPasswordQuality = 0;
        this.mMaximumFailedPasswordsForWipe = 0;
        this.mMaximumCharacterOccurences = 0;
        this.mMaximumCharacterSequenceLength = 0;
        this.mMaximumNumericSequenceLength = 0;
        this.mPasswordMinimumLength = 0;
        this.mMaximumTimeToLock = 0;
        this.mPasswordPattern = null;
        this.mName = null;
        this.mCustomBadgeIcon = null;
        this.mCustomHomeScreenWallpaper = null;
        this.mEC = false;
        this.mNameIcon = null;
        this.mECName = null;
        this.mECIcon = null;
        this.mECBadge = null;
        this.mCustomLockScreenWallpaper = null;
        this.mCustomStatusLabel = null;
        this.mCustomStatusIcon = null;
        this.mPersonaList = new ArrayList();
        this.mAppInstallationList = new ArrayList();
        this.mLayoutType = -1;
        this.mAllowSwitch = true;
        this.mIsDefaultConfigType = false;
        this.mAuthenticationConfig = new AuthenticationConfig();
        this.mAppRemoveList = new ArrayList();
        this.mFOTADisableAppList = new ArrayList();
        this.mFOTAReenableAppList = new ArrayList();
        this.mForbiddenStrings = new ArrayList();
        this.mProtectedList = new ArrayList();
        this.mGoogleAppsList = new ArrayList();
        this.mManagedType = false;
        this.mSimplePasswordEnabled = true;
        this.mMultifactorAuthEnabled = false;
        this.mAllowMultiwindowMode = true;
        this.mAllowTaskManager = true;
        this.mIsBiometricAuthEnabled = false;
        this.mBiometricAuthValue = -1;
        this.mAllowUSBDebugging = false;
        this.mKeyguardDisabledFeatures = -1;
        this.mRCPDataSettings = new HashMap<>();
        this.mAllowChangeDataSettings = new HashMap<>();
        this.mRCPNotifSettings = new HashMap<>();
    }

    public final void addPersonaId(int i) {
        if (this.mPersonaList.contains(Integer.valueOf(i))) {
            return;
        }
        this.mPersonaList.add(Integer.valueOf(i));
    }

    public final void allowLayoutSwitching(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("allowLayoutSwitching: allowSwitch ", z, TAG);
        this.mAllowSwitch = z;
    }

    public final void allowMultiwindowMode(boolean z) {
        this.mAllowMultiwindowMode = z;
    }

    public final void allowTaskManager(boolean z) {
        this.mAllowTaskManager = z;
    }

    public final void allowUSBDebugging(boolean z) {
        this.mAllowUSBDebugging = z;
    }

    public KnoxConfigurationType clone(String str) {
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "clone(): name is either null or empty, hence returning null");
            return null;
        }
        KnoxConfigurationType knoxConfigurationType = new KnoxConfigurationType();
        cloneConfiguration(knoxConfigurationType, str);
        return knoxConfigurationType;
    }

    public final void cloneConfiguration(KnoxConfigurationType knoxConfigurationType, String str) {
        knoxConfigurationType.setName(str);
        knoxConfigurationType.setPasswordMinimumNonLetters(this.mPasswordMinimumNonLetters);
        knoxConfigurationType.setPasswordMinimumLetters(this.mPasswordMinimumLetters);
        knoxConfigurationType.setPasswordMinimumNumeric(this.mPasswordMinimumNumeric);
        knoxConfigurationType.setPasswordMinimumUpperCase(this.mPasswordMinimumUpperCase);
        knoxConfigurationType.setPasswordMinimumLowerCase(this.mPasswordMinimumLowerCase);
        knoxConfigurationType.setPasswordMinimumSymbols(this.mPasswordMinimumSymbols);
        knoxConfigurationType.setPasswordQuality(this.mPasswordQuality);
        knoxConfigurationType.setMaximumFailedPasswordsForWipe(this.mMaximumFailedPasswordsForWipe);
        knoxConfigurationType.setManagedType(this.mManagedType);
        knoxConfigurationType.setCustomBadgeIcon(this.mCustomBadgeIcon);
        knoxConfigurationType.setCustomHomeScreenWallpaper(this.mCustomHomeScreenWallpaper);
        knoxConfigurationType.setCustomizedContainerNameIcon(this.mNameIcon);
        knoxConfigurationType.setCustomizedContainerEnabled(this.mEC);
        knoxConfigurationType.setCustomizedContainerName(this.mECName);
        knoxConfigurationType.setCustomizedContainerIcon(this.mECIcon);
        knoxConfigurationType.setCustomizedContainerBadge(this.mECBadge);
        knoxConfigurationType.setCustomLockScreenWallpaper(this.mCustomLockScreenWallpaper);
        knoxConfigurationType.setCustomStatusLabel(this.mCustomStatusLabel);
        knoxConfigurationType.setCustomStatusIcon(this.mCustomStatusIcon);
        knoxConfigurationType.setAppInstallationList(this.mAppInstallationList);
        knoxConfigurationType.setAppRemoveList(this.mAppRemoveList);
        knoxConfigurationType.setFOTADisableList(this.mFOTADisableAppList);
        knoxConfigurationType.setFOTAReenableList(this.mFOTAReenableAppList);
        knoxConfigurationType.setForbiddenStrings(this.mForbiddenStrings);
        knoxConfigurationType.setProtectedPackageList(this.mProtectedList);
        knoxConfigurationType.setGoogleAppsList(this.mGoogleAppsList);
        knoxConfigurationType.setMaximumCharacterOccurences(this.mMaximumCharacterOccurences);
        knoxConfigurationType.setMaximumCharacterSequenceLength(this.mMaximumCharacterSequenceLength);
        knoxConfigurationType.setMaximumNumericSequenceLength(this.mMaximumNumericSequenceLength);
        knoxConfigurationType.setPasswordMinimumLength(this.mPasswordMinimumLength);
        knoxConfigurationType.setSimplePasswordEnabled(this.mSimplePasswordEnabled);
        knoxConfigurationType.enforceMultifactorAuthentication(this.mMultifactorAuthEnabled);
        knoxConfigurationType.setRequiredPasswordPattern(this.mPasswordPattern);
        knoxConfigurationType.setRCPSyncPolicy(this.mRCPDataSettings, knoxConfigurationType.mRCPDataSettings);
        knoxConfigurationType.setRCPSyncPolicy(this.mAllowChangeDataSettings, knoxConfigurationType.mAllowChangeDataSettings);
        knoxConfigurationType.setRCPSyncPolicy(this.mRCPNotifSettings, knoxConfigurationType.mRCPNotifSettings);
        knoxConfigurationType.setMaximumTimeToLock(this.mMaximumTimeToLock);
        knoxConfigurationType.setVersion(this.mVersion);
        knoxConfigurationType.allowMultiwindowMode(this.mAllowMultiwindowMode);
        knoxConfigurationType.allowTaskManager(this.mAllowTaskManager);
        knoxConfigurationType.setBiometricAuthenticationEnabled(this.mBiometricAuthValue, this.mIsBiometricAuthEnabled);
        knoxConfigurationType.allowUSBDebugging(this.mAllowUSBDebugging);
        knoxConfigurationType.setContainerLayout(this.mLayoutType);
        knoxConfigurationType.allowLayoutSwitching(this.mAllowSwitch);
        knoxConfigurationType.setDefaultConfigType(this.mIsDefaultConfigType);
        knoxConfigurationType.setEnterpriseIdentityAuthenticationData(this.mAuthenticationConfig);
        knoxConfigurationType.setKeyguardDisabledFeatures(this.mKeyguardDisabledFeatures);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void deserializeRCPSettings(Parcel parcel, HashMap<String, List<Pair<String, String>>> hashMap) {
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            String readString = parcel.readString();
            ArrayList arrayList = new ArrayList();
            int readInt2 = parcel.readInt();
            for (int i2 = 0; i2 < readInt2; i2++) {
                arrayList.add(new Pair(parcel.readString(), parcel.readString()));
            }
            hashMap.put(readString, arrayList);
        }
    }

    public final void dumpEIDConfig(AuthenticationConfig authenticationConfig) {
        Log.d(TAG, "AuthenticationConfig:enforceRemoteAuthAlways:" + authenticationConfig.enforceRemoteAuthAlways);
        Log.d(TAG, "AuthenticationConfig:forceEnterpriseIdentityLock:" + authenticationConfig.enforceEnterpriseIdentityLock);
        Log.d(TAG, "AuthenticationConfig:hideEnterpriseIdentityLock:" + authenticationConfig.hideEnterpriseIdentityLock);
        Log.d(TAG, "AuthenticationConfig:authenticatorPkgName:" + authenticationConfig.authenticatorPkgName);
        ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("AuthenticationConfig:authenticatorPkgSignature:"), authenticationConfig.authenticatorPkgSignature, TAG);
        Bundle bundle = authenticationConfig.authenticatorConfig;
        if (bundle == null) {
            Log.d(TAG, "AuthenticationConfig:authenticatorConfig:null");
            return;
        }
        for (String str : bundle.keySet()) {
            if (authenticationConfig.authenticatorConfig.get(str) != null) {
                StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("AuthenticationConfig:authenticatorConfig:", str, ":");
                m4m.append(authenticationConfig.authenticatorConfig.get(str).toString());
                Log.d(TAG, m4m.toString());
            }
        }
    }

    public final void dumpRCPSettings(HashMap<String, List<Pair<String, String>>> hashMap) {
        Set<String> keySet = hashMap.keySet();
        if (keySet == null || keySet.isEmpty()) {
            return;
        }
        for (String str : keySet) {
            Log.d(TAG, " " + str + " {");
            List<Pair<String, String>> list = hashMap.get(str);
            if (list != null) {
                for (Pair<String, String> pair : list) {
                    StringBuilder sb = new StringBuilder("  ( ");
                    sb.append((String) pair.first);
                    sb.append(",");
                    ExifInterface$$ExternalSyntheticOutline0.m36m(sb, (String) pair.second, " )", TAG);
                }
            }
            Log.d(TAG, " }");
        }
    }

    public final void enforceMultifactorAuthentication(boolean z) {
        this.mMultifactorAuthEnabled = z;
    }

    public final int getAdminUid() {
        return this.mAdminUid;
    }

    public final boolean getAirCommandEnabled() {
        return true;
    }

    public final boolean getAllowAllShare() {
        return false;
    }

    public final HashMap<String, List<Pair<String, String>>> getAllowChangeDataSyncPolicy() {
        return this.mAllowChangeDataSettings;
    }

    public final boolean getAllowContainerReset() {
        return false;
    }

    public final boolean getAllowCustomBadgeIcon() {
        return true;
    }

    public final boolean getAllowCustomColorIdentification() {
        return true;
    }

    public final boolean getAllowCustomPersonaIcon() {
        return true;
    }

    public final boolean getAllowDLNADataTransfer() {
        return false;
    }

    public final boolean getAllowExportAndDeleteFiles() {
        return false;
    }

    public final boolean getAllowExportFiles() {
        return false;
    }

    public final boolean getAllowImportFiles() {
        return true;
    }

    public final boolean getAllowPrint() {
        return false;
    }

    public final boolean getAllowShortCutCreation() {
        return true;
    }

    public final boolean getAllowUniversalCallerId() {
        return true;
    }

    public final List<String> getAppInstallationList() {
        return this.mAppInstallationList;
    }

    public final List<String> getAppRemoveList() {
        return this.mAppRemoveList;
    }

    public final int getBiometricAuthenticationEnabledType() {
        return this.mBiometricAuthValue;
    }

    public final boolean getBiometricAuthenticationEnabledValue() {
        return this.mIsBiometricAuthEnabled;
    }

    public final boolean getCameraModeChangeEnabled() {
        return false;
    }

    public final int getContainerLayout() {
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("getContainerLayout: "), this.mLayoutType, TAG);
        return this.mLayoutType;
    }

    public final String getCustomBadgeIcon() {
        return this.mCustomBadgeIcon;
    }

    public final String getCustomHomeScreenWallpaper() {
        return this.mCustomHomeScreenWallpaper;
    }

    public final String getCustomLockScreenWallpaper() {
        return this.mCustomLockScreenWallpaper;
    }

    public final String getCustomStatusIcon() {
        return this.mCustomStatusIcon;
    }

    public final String getCustomStatusLabel() {
        return this.mCustomStatusLabel;
    }

    public final String getCustomizedContainerBadge() {
        return this.mECBadge;
    }

    public final String getCustomizedContainerIcon() {
        return this.mECIcon;
    }

    public final String getCustomizedContainerName() {
        return this.mECName;
    }

    public final String getCustomizedContainerNameIcon() {
        return this.mNameIcon;
    }

    public final HashMap<String, List<Pair<String, String>>> getDataSyncPolicy() {
        return this.mRCPDataSettings;
    }

    public final boolean getDisableSwitchWidgetOnLockScreen() {
        return false;
    }

    public final AuthenticationConfig getEnterpriseIdentityAuthentication() {
        return this.mAuthenticationConfig;
    }

    public final List<String> getFOTADisableList() {
        return this.mFOTADisableAppList;
    }

    public final List<String> getFOTAReenableList() {
        return this.mFOTAReenableAppList;
    }

    public final List<String> getForbiddenStrings() {
        return this.mForbiddenStrings;
    }

    public final boolean getGearSupportEnabled() {
        return true;
    }

    public final List<String> getGoogleAppsList() {
        return this.mGoogleAppsList;
    }

    public final int getKeyguardDisabledFeatures() {
        return this.mKeyguardDisabledFeatures;
    }

    public final List<String> getListFromAllowChangeDataSyncPolicy(String str, boolean z) {
        return getListFromSyncPolicy(str, Boolean.toString(z), this.mAllowChangeDataSettings);
    }

    public final List<String> getListFromDataSyncPolicy(String str, String str2) {
        return getListFromSyncPolicy(str, str2, this.mRCPDataSettings);
    }

    public final List<String> getListFromSyncPolicy(String str, String str2, HashMap<String, List<Pair<String, String>>> hashMap) {
        Set<String> keySet;
        ArrayList arrayList = null;
        if (hashMap != null && str != null && !str.isEmpty() && str2 != null && !str2.isEmpty() && (keySet = hashMap.keySet()) != null) {
            Pair pair = new Pair(str, str2);
            for (String str3 : keySet) {
                List<Pair<String, String>> list = hashMap.get(str3);
                if (list != null) {
                    Iterator<Pair<String, String>> it = list.iterator();
                    while (it.hasNext()) {
                        if (pair.equals(it.next())) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(str3);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public final boolean getManagedType() {
        return this.mManagedType;
    }

    public final int getMaximumCharacterOccurences() {
        return this.mMaximumCharacterOccurences;
    }

    public final int getMaximumCharacterSequenceLength() {
        return this.mMaximumCharacterSequenceLength;
    }

    public final int getMaximumFailedPasswordsForWipe() {
        return this.mMaximumFailedPasswordsForWipe;
    }

    public final int getMaximumNumericSequenceLength() {
        return this.mMaximumNumericSequenceLength;
    }

    public final int getMaximumTimeToLock() {
        return this.mMaximumTimeToLock;
    }

    public final boolean getModifyLockScreenTimeout() {
        return true;
    }

    public final String getName() {
        return this.mName;
    }

    public final HashMap<String, List<Pair<String, String>>> getNotificationSyncPolicy() {
        return this.mRCPNotifSettings;
    }

    public final List<String> getPackagesFromNotificationSyncPolicy(String str, String str2) {
        return getListFromSyncPolicy(str, str2, this.mRCPNotifSettings);
    }

    public final int getPasswordMinimumLength() {
        return this.mPasswordMinimumLength;
    }

    public final int getPasswordMinimumLetters() {
        return this.mPasswordMinimumLetters;
    }

    public final int getPasswordMinimumLowerCase() {
        return this.mPasswordMinimumLowerCase;
    }

    public final int getPasswordMinimumNonLetters() {
        return this.mPasswordMinimumNonLetters;
    }

    public final int getPasswordMinimumNumeric() {
        return this.mPasswordMinimumNumeric;
    }

    public final int getPasswordMinimumSymbols() {
        return this.mPasswordMinimumSymbols;
    }

    public final int getPasswordMinimumUpperCase() {
        return this.mPasswordMinimumUpperCase;
    }

    public final int getPasswordQuality() {
        return this.mPasswordQuality;
    }

    public final boolean getPenWindowEnabled() {
        return true;
    }

    public final List<Integer> getPersonaList() {
        return this.mPersonaList;
    }

    public final List<String> getProtectedPackageList() {
        return this.mProtectedList;
    }

    public final String getRCPSyncPolicy(String str, String str2, HashMap<String, List<Pair<String, String>>> hashMap) {
        List<Pair<String, String>> list;
        if (hashMap == null || str == null || str.isEmpty() || str2 == null || str2.isEmpty() || (list = hashMap.get(str)) == null) {
            return null;
        }
        for (Pair<String, String> pair : list) {
            if (((String) pair.first).equals(str2)) {
                return (String) pair.second;
            }
        }
        return null;
    }

    public final String getRequiredPwdPatternRestrictions() {
        return this.mPasswordPattern;
    }

    public final boolean getSimplePasswordEnabled() {
        return this.mSimplePasswordEnabled;
    }

    public final int getUserId() {
        return this.mUserId;
    }

    public final String getVersion() {
        return this.mVersion;
    }

    public final boolean isBiometricAuthenticationEnabled(int i) {
        int i2 = this.mBiometricAuthValue;
        if (i2 == -1 || (i2 & i) != i) {
            return false;
        }
        Log.d(TAG, "isBiometricAuthenticationEnabled: return true (hasValue)");
        return true;
    }

    public final boolean isCustomizedContainerEnabled() {
        return this.mEC;
    }

    public final boolean isDefaultConfigType() {
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("isDefaultConfigType: "), this.mIsDefaultConfigType, TAG);
        return this.mIsDefaultConfigType;
    }

    public final boolean isLayoutSwitchingAllowed() {
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("isLayoutSwitchingAllowed: "), this.mAllowSwitch, TAG);
        return this.mAllowSwitch;
    }

    public final boolean isMultifactorAuthenticationEnforced() {
        return this.mMultifactorAuthEnabled;
    }

    public final boolean isMultiwindowModeAllowed() {
        return this.mAllowMultiwindowMode;
    }

    public final boolean isTaskManagerAllowed() {
        return this.mAllowTaskManager;
    }

    public final boolean isUSBDebuggingAllowed() {
        return this.mAllowUSBDebugging;
    }

    public final boolean isUserManaged() {
        return this.mManagedType;
    }

    public final void removePersonaId(int i) {
        if (this.mPersonaList.contains(Integer.valueOf(i))) {
            this.mPersonaList.remove(Integer.valueOf(i));
        }
    }

    public final void serializeRCPSettings(Parcel parcel, HashMap<String, List<Pair<String, String>>> hashMap) {
        Set<String> keySet = hashMap.keySet();
        if (keySet == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(keySet.size());
        for (String str : keySet) {
            parcel.writeString(str);
            List<Pair<String, String>> list = hashMap.get(str);
            if (list != null) {
                parcel.writeInt(list.size());
                for (Pair<String, String> pair : list) {
                    parcel.writeString((String) pair.first);
                    parcel.writeString((String) pair.second);
                }
            } else {
                parcel.writeInt(0);
            }
        }
    }

    public final void setAdminUid(int i) {
        this.mAdminUid = i;
    }

    public final void setAllowChangeDataSyncPolicy(List<String> list, String str, boolean z) {
        setRCPSyncPolicy(list, str, Boolean.toString(z), this.mAllowChangeDataSettings);
    }

    public final void setAppInstallationList(List<String> list) {
        List<String> list2 = this.mAppInstallationList;
        if (list2 != null) {
            list2.clear();
            if (list == null || list.isEmpty()) {
                return;
            }
            this.mAppInstallationList.addAll(list);
        }
    }

    public final void setAppRemoveList(List<String> list) {
        List<String> list2 = this.mAppRemoveList;
        if (list2 != null) {
            list2.clear();
            if (list == null || list.isEmpty()) {
                return;
            }
            this.mAppRemoveList.addAll(list);
        }
    }

    public final void setBiometricAuthenticationEnabled(int i, boolean z) {
        if (i < 0) {
            return;
        }
        int i2 = this.mBiometricAuthValue;
        if (i2 <= 0) {
            i2 = 0;
        }
        int i3 = z ? i | i2 : (~i) & i2;
        this.mBiometricAuthValue = i3;
        if (i3 <= 0) {
            this.mIsBiometricAuthEnabled = false;
        } else {
            this.mIsBiometricAuthEnabled = true;
        }
        StringBuilder sb = new StringBuilder("setBiometricAuthenticationEnabled : bioAuth = ");
        sb.append(this.mBiometricAuthValue);
        sb.append(", enabled : ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mIsBiometricAuthEnabled, TAG);
    }

    public final void setContainerLayout(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("setDefaultContainerLayout: layoutType ", i, TAG);
        this.mLayoutType = i;
    }

    public final void setCustomBadgeIcon(String str) {
        this.mCustomBadgeIcon = str;
    }

    public final void setCustomHomeScreenWallpaper(String str) {
        this.mCustomHomeScreenWallpaper = str;
    }

    public final void setCustomLockScreenWallpaper(String str) {
        this.mCustomLockScreenWallpaper = str;
    }

    public final void setCustomStatusIcon(String str) {
        this.mCustomStatusIcon = str;
    }

    public final void setCustomStatusLabel(String str) {
        this.mCustomStatusLabel = str;
    }

    public final void setCustomizedContainerBadge(String str) {
        this.mECBadge = str;
    }

    public final void setCustomizedContainerEnabled(boolean z) {
        this.mEC = z;
    }

    public final void setCustomizedContainerIcon(String str) {
        this.mECIcon = str;
    }

    public final void setCustomizedContainerName(String str) {
        AbstractC0000x2c234b15.m3m("setting ecname ", str, TAG);
        this.mECName = str;
    }

    public final void setCustomizedContainerNameIcon(String str) {
        this.mNameIcon = str;
    }

    public final void setDataSyncPolicy(List<String> list, String str, String str2) {
        setRCPSyncPolicy(list, str, str2, this.mRCPDataSettings);
    }

    public final void setDefaultConfigType(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setDefaultConfigType: isDefaultConfigType ", z, TAG);
        this.mIsDefaultConfigType = z;
    }

    public final void setEnterpriseIdentityAuthentication(AuthenticationConfig authenticationConfig) {
        if (authenticationConfig != null) {
            this.mAuthenticationConfig = authenticationConfig;
        } else {
            this.mAuthenticationConfig = new AuthenticationConfig();
        }
        this.mAuthenticationConfig.isConfiguredByMDM = true;
    }

    public final void setEnterpriseIdentityAuthenticationData(AuthenticationConfig authenticationConfig) {
        if (authenticationConfig != null) {
            this.mAuthenticationConfig = authenticationConfig;
        }
    }

    public final void setFOTADisableList(List<String> list) {
        List<String> list2 = this.mFOTADisableAppList;
        if (list2 != null) {
            list2.clear();
            if (list == null || list.isEmpty()) {
                return;
            }
            this.mFOTADisableAppList.addAll(list);
        }
    }

    public final void setFOTAReenableList(List<String> list) {
        List<String> list2 = this.mFOTAReenableAppList;
        if (list2 != null) {
            list2.clear();
            if (list == null || list.isEmpty()) {
                return;
            }
            this.mFOTAReenableAppList.addAll(list);
        }
    }

    public final void setForbiddenStrings(List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mForbiddenStrings.clear();
        this.mForbiddenStrings.addAll(list);
    }

    public final void setGoogleAppsList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mGoogleAppsList.clear();
        this.mGoogleAppsList.addAll(list);
    }

    public final void setKeyguardDisabledFeatures(int i) {
        if (i == 0 || i == 16) {
            this.mKeyguardDisabledFeatures = i;
        }
    }

    public final void setManagedType(boolean z) {
        this.mManagedType = z;
    }

    public final void setMaximumCharacterOccurences(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mMaximumCharacterOccurences = i;
    }

    public final void setMaximumCharacterSequenceLength(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mMaximumCharacterSequenceLength = i;
    }

    public final void setMaximumFailedPasswordsForWipe(int i) {
        if (i >= 0) {
            this.mMaximumFailedPasswordsForWipe = i;
        }
    }

    public final void setMaximumNumericSequenceLength(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mMaximumNumericSequenceLength = i;
    }

    public final void setMaximumTimeToLock(int i) {
        if (i >= 0) {
            this.mMaximumTimeToLock = i;
        }
    }

    public final void setName(String str) {
        if (str == null || str.equals("")) {
            return;
        }
        this.mName = str;
    }

    public final void setNotificationSyncPolicy(List<String> list, String str, String str2) {
        setRCPSyncPolicy(list, str, str2, this.mRCPNotifSettings);
    }

    public final void setPasswordMinimumLength(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mPasswordMinimumLength = i;
    }

    public final void setPasswordMinimumLetters(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mPasswordMinimumLetters = i;
    }

    public final void setPasswordMinimumLowerCase(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mPasswordMinimumLowerCase = i;
    }

    public final void setPasswordMinimumNonLetters(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mPasswordMinimumNonLetters = i;
    }

    public final void setPasswordMinimumNumeric(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mPasswordMinimumNumeric = i;
    }

    public final void setPasswordMinimumSymbols(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mPasswordMinimumSymbols = i;
    }

    public final void setPasswordMinimumUpperCase(int i) {
        if (i < 0 || i >= 257) {
            return;
        }
        this.mPasswordMinimumUpperCase = i;
    }

    public final void setPasswordQuality(int i) {
        if (i >= 0) {
            this.mPasswordQuality = i;
        }
    }

    public final void setPersonaList(List<Integer> list) {
        this.mPersonaList.addAll(list);
    }

    public final void setProtectedPackageList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.mProtectedList.clear();
        this.mProtectedList.addAll(list);
    }

    public final void setRCPSyncPolicy(HashMap<String, List<Pair<String, String>>> hashMap, HashMap<String, List<Pair<String, String>>> hashMap2) {
        Set<String> keySet;
        if (hashMap2 == null) {
            return;
        }
        hashMap2.clear();
        if (hashMap == null || (keySet = hashMap.keySet()) == null) {
            return;
        }
        for (String str : keySet) {
            List<Pair<String, String>> list = hashMap.get(str);
            if (list != null && !list.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                for (Pair<String, String> pair : list) {
                    arrayList.add(new Pair((String) pair.first, (String) pair.second));
                }
                hashMap2.put(str, arrayList);
            }
        }
    }

    public final void setRequiredPasswordPattern(String str) {
        this.mPasswordPattern = str;
    }

    public final void setSimplePasswordEnabled(boolean z) {
        this.mSimplePasswordEnabled = z;
    }

    public final void setUserId(int i) {
        this.mUserId = i;
    }

    public final void setVersion(String str) {
        this.mVersion = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        String str = this.mName;
        if (str != null) {
            parcel.writeString(str);
        } else {
            parcel.writeString("custom");
        }
        String str2 = this.mVersion;
        if (str2 != null) {
            parcel.writeString(str2);
        } else {
            parcel.writeString("custom");
        }
        parcel.writeInt(this.mPasswordMinimumNonLetters);
        parcel.writeInt(this.mPasswordMinimumLetters);
        parcel.writeInt(this.mPasswordMinimumNumeric);
        parcel.writeInt(this.mPasswordMinimumUpperCase);
        parcel.writeInt(this.mPasswordMinimumLowerCase);
        parcel.writeInt(this.mPasswordMinimumSymbols);
        parcel.writeInt(this.mPasswordQuality);
        parcel.writeInt(this.mMaximumTimeToLock);
        parcel.writeInt(this.mMaximumFailedPasswordsForWipe);
        parcel.writeInt(this.mManagedType ? 1 : 0);
        String str3 = this.mCustomBadgeIcon;
        if (str3 != null) {
            parcel.writeString(str3);
        } else {
            parcel.writeString("");
        }
        String str4 = this.mCustomHomeScreenWallpaper;
        if (str4 != null) {
            parcel.writeString(str4);
        } else {
            parcel.writeString("");
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("writing to parcel mEC "), this.mEC, TAG);
        parcel.writeInt(this.mEC ? 1 : 0);
        String str5 = this.mNameIcon;
        if (str5 != null) {
            parcel.writeString(str5);
        } else {
            parcel.writeString("");
        }
        String str6 = this.mECName;
        if (str6 != null) {
            parcel.writeString(str6);
        } else {
            parcel.writeString("");
        }
        String str7 = this.mECIcon;
        if (str7 != null) {
            parcel.writeString(str7);
        } else {
            parcel.writeString("");
        }
        String str8 = this.mECBadge;
        if (str8 != null) {
            parcel.writeString(str8);
        } else {
            parcel.writeString("");
        }
        String str9 = this.mCustomLockScreenWallpaper;
        if (str9 != null) {
            parcel.writeString(str9);
        } else {
            parcel.writeString("");
        }
        String str10 = this.mCustomStatusLabel;
        if (str10 != null) {
            parcel.writeString(str10);
        } else {
            parcel.writeString("");
        }
        String str11 = this.mCustomStatusIcon;
        if (str11 != null) {
            parcel.writeString(str11);
        } else {
            parcel.writeString("");
        }
        parcel.writeStringList(this.mAppInstallationList);
        parcel.writeStringList(this.mForbiddenStrings);
        parcel.writeStringList(this.mProtectedList);
        parcel.writeStringList(this.mGoogleAppsList);
        parcel.writeInt(this.mMaximumCharacterOccurences);
        parcel.writeInt(this.mMaximumCharacterSequenceLength);
        parcel.writeInt(this.mMaximumNumericSequenceLength);
        parcel.writeInt(this.mPasswordMinimumLength);
        String str12 = this.mPasswordPattern;
        if (str12 != null) {
            parcel.writeString(str12);
        } else {
            parcel.writeString("");
        }
        parcel.writeInt(this.mSimplePasswordEnabled ? 1 : 0);
        parcel.writeInt(this.mMultifactorAuthEnabled ? 1 : 0);
        parcel.writeInt(this.mAllowMultiwindowMode ? 1 : 0);
        parcel.writeInt(this.mAllowTaskManager ? 1 : 0);
        parcel.writeInt(this.mIsBiometricAuthEnabled ? 1 : 0);
        parcel.writeInt(this.mBiometricAuthValue);
        parcel.writeInt(this.mAllowUSBDebugging ? 1 : 0);
        parcel.writeInt(this.mLayoutType);
        parcel.writeInt(this.mAllowSwitch ? 1 : 0);
        parcel.writeInt(this.mIsDefaultConfigType ? 1 : 0);
        serializeRCPSettings(parcel, this.mRCPDataSettings);
        serializeRCPSettings(parcel, this.mAllowChangeDataSettings);
        serializeRCPSettings(parcel, this.mRCPNotifSettings);
        parcel.writeParcelable(this.mAuthenticationConfig, i);
        parcel.writeInt(this.mKeyguardDisabledFeatures);
    }

    public final boolean getAllowChangeDataSyncPolicy(String str, String str2) {
        return Boolean.parseBoolean(getRCPSyncPolicy(str, str2, this.mAllowChangeDataSettings));
    }

    public final String getDataSyncPolicy(String str, String str2) {
        return getRCPSyncPolicy(str, str2, this.mRCPDataSettings);
    }

    public final String getNotificationSyncPolicy(String str, String str2) {
        return getRCPSyncPolicy(str, str2, this.mRCPNotifSettings);
    }

    public final void setRCPSyncPolicy(List<String> list, String str, String str2, HashMap<String, List<Pair<String, String>>> hashMap) {
        if (hashMap == null || list == null || list.isEmpty() || str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
            return;
        }
        Pair<String, String> pair = new Pair<>(str, str2);
        Pair<String, String> pair2 = null;
        while (true) {
            boolean z = true;
            for (String str3 : list) {
                List<Pair<String, String>> list2 = hashMap.get(str3);
                if (list2 == null) {
                    list2 = new ArrayList<>();
                } else {
                    for (Pair<String, String> pair3 : list2) {
                        if (pair3.equals(pair)) {
                            StringBuilder sb = new StringBuilder("Ignoring the duplicate entry: ");
                            sb.append((String) pair.first);
                            sb.append(" ");
                            ExifInterface$$ExternalSyntheticOutline0.m35m(sb, (String) pair.second, TAG);
                            z = false;
                        } else if (((String) pair3.first).equals(str)) {
                            StringBuilder sb2 = new StringBuilder("property found, remove and add it again: ");
                            sb2.append((String) pair.first);
                            sb2.append(" ");
                            ExifInterface$$ExternalSyntheticOutline0.m35m(sb2, (String) pair.second, TAG);
                            pair2 = pair3;
                        }
                    }
                    if (pair2 != null) {
                        list2.remove(pair2);
                        pair2 = null;
                    }
                }
                if (z) {
                    list2.add(pair);
                    hashMap.put(str3, list2);
                }
            }
            return;
        }
    }

    public void dumpState() {
    }

    public final void setAirCommandEnabled(boolean z) {
    }

    public final void setAllowAllShare(boolean z) {
    }

    public final void setAllowContainerReset(boolean z) {
    }

    public final void setAllowCustomBadgeIcon(boolean z) {
    }

    public final void setAllowCustomColorIdentification(boolean z) {
    }

    public final void setAllowCustomPersonaIcon(boolean z) {
    }

    public final void setAllowDLNADataTransfer(boolean z) {
    }

    public final void setAllowExportAndDeleteFiles(boolean z) {
    }

    public final void setAllowExportFiles(boolean z) {
    }

    public final void setAllowImportFiles(boolean z) {
    }

    public final void setAllowPrint(boolean z) {
    }

    public final void setAllowShortCutCreation(boolean z) {
    }

    public final void setAllowUniversalCallerId(boolean z) {
    }

    public final void setCameraModeChangeEnabled(boolean z) {
    }

    public final void setDisableSwitchWidgetOnLockScreen(boolean z) {
    }

    public final void setGearSupportEnabled(boolean z) {
    }

    public final void setModifyLockScreenTimeout(boolean z) {
    }

    public final void setPenWindowEnabled(boolean z) {
    }

    public KnoxConfigurationType(Parcel parcel) {
        this.mAdminUid = 0;
        this.mUserId = -1;
        this.mVersion = "custom";
        this.mPasswordMinimumNonLetters = 0;
        this.mPasswordMinimumLetters = 0;
        this.mPasswordMinimumNumeric = 0;
        this.mPasswordMinimumUpperCase = 0;
        this.mPasswordMinimumLowerCase = 0;
        this.mPasswordMinimumSymbols = 0;
        this.mPasswordQuality = 0;
        this.mMaximumFailedPasswordsForWipe = 0;
        this.mMaximumCharacterOccurences = 0;
        this.mMaximumCharacterSequenceLength = 0;
        this.mMaximumNumericSequenceLength = 0;
        this.mPasswordMinimumLength = 0;
        this.mMaximumTimeToLock = 0;
        String str = null;
        this.mPasswordPattern = null;
        this.mName = null;
        this.mCustomBadgeIcon = null;
        this.mCustomHomeScreenWallpaper = null;
        this.mEC = false;
        this.mNameIcon = null;
        this.mECName = null;
        this.mECIcon = null;
        this.mECBadge = null;
        this.mCustomLockScreenWallpaper = null;
        this.mCustomStatusLabel = null;
        this.mCustomStatusIcon = null;
        this.mPersonaList = new ArrayList();
        this.mAppInstallationList = new ArrayList();
        this.mLayoutType = -1;
        this.mAllowSwitch = true;
        this.mIsDefaultConfigType = false;
        this.mAuthenticationConfig = new AuthenticationConfig();
        this.mAppRemoveList = new ArrayList();
        this.mFOTADisableAppList = new ArrayList();
        this.mFOTAReenableAppList = new ArrayList();
        this.mForbiddenStrings = new ArrayList();
        this.mProtectedList = new ArrayList();
        this.mGoogleAppsList = new ArrayList();
        this.mManagedType = false;
        this.mSimplePasswordEnabled = true;
        this.mMultifactorAuthEnabled = false;
        this.mAllowMultiwindowMode = true;
        this.mAllowTaskManager = true;
        this.mIsBiometricAuthEnabled = false;
        this.mBiometricAuthValue = -1;
        this.mAllowUSBDebugging = false;
        this.mKeyguardDisabledFeatures = -1;
        this.mRCPDataSettings = new HashMap<>();
        this.mAllowChangeDataSettings = new HashMap<>();
        this.mRCPNotifSettings = new HashMap<>();
        this.mName = parcel.readString();
        String readString = parcel.readString();
        if (readString != null) {
            this.mVersion = readString;
        } else {
            this.mVersion = "custom";
        }
        this.mPasswordMinimumNonLetters = parcel.readInt();
        this.mPasswordMinimumLetters = parcel.readInt();
        this.mPasswordMinimumNumeric = parcel.readInt();
        this.mPasswordMinimumUpperCase = parcel.readInt();
        this.mPasswordMinimumLowerCase = parcel.readInt();
        this.mPasswordMinimumSymbols = parcel.readInt();
        this.mPasswordQuality = parcel.readInt();
        this.mMaximumTimeToLock = parcel.readInt();
        this.mMaximumFailedPasswordsForWipe = parcel.readInt();
        this.mManagedType = parcel.readInt() == 1;
        String readString2 = parcel.readString();
        this.mCustomBadgeIcon = (readString2 == null || readString2.isEmpty()) ? null : readString2;
        String readString3 = parcel.readString();
        this.mCustomHomeScreenWallpaper = (readString3 == null || readString3.isEmpty()) ? null : readString3;
        this.mEC = parcel.readInt() == 1;
        Log.d(TAG, "reading from parcel mEC " + this.mEC);
        String readString4 = parcel.readString();
        this.mNameIcon = (readString4 == null || readString4.isEmpty()) ? null : readString4;
        String readString5 = parcel.readString();
        this.mECName = (readString5 == null || readString5.isEmpty()) ? null : readString5;
        String readString6 = parcel.readString();
        this.mECIcon = (readString6 == null || readString6.isEmpty()) ? null : readString6;
        String readString7 = parcel.readString();
        this.mECBadge = (readString7 == null || readString7.isEmpty()) ? null : readString7;
        String readString8 = parcel.readString();
        this.mCustomLockScreenWallpaper = (readString8 == null || readString8.isEmpty()) ? null : readString8;
        String readString9 = parcel.readString();
        this.mCustomStatusLabel = (readString9 == null || readString9.isEmpty()) ? null : readString9;
        String readString10 = parcel.readString();
        this.mCustomStatusIcon = (readString10 == null || readString10.isEmpty()) ? null : readString10;
        parcel.readStringList(this.mAppInstallationList);
        parcel.readStringList(this.mForbiddenStrings);
        parcel.readStringList(this.mProtectedList);
        parcel.readStringList(this.mGoogleAppsList);
        this.mMaximumCharacterOccurences = parcel.readInt();
        this.mMaximumCharacterSequenceLength = parcel.readInt();
        this.mMaximumNumericSequenceLength = parcel.readInt();
        this.mPasswordMinimumLength = parcel.readInt();
        String readString11 = parcel.readString();
        if (readString11 != null && !readString11.isEmpty()) {
            str = readString11;
        }
        this.mPasswordPattern = str;
        this.mSimplePasswordEnabled = parcel.readInt() == 1;
        this.mMultifactorAuthEnabled = parcel.readInt() == 1;
        this.mAllowMultiwindowMode = parcel.readInt() == 1;
        this.mAllowTaskManager = parcel.readInt() == 1;
        this.mIsBiometricAuthEnabled = parcel.readInt() == 1;
        this.mBiometricAuthValue = parcel.readInt();
        this.mAllowUSBDebugging = parcel.readInt() == 1;
        this.mLayoutType = parcel.readInt();
        this.mAllowSwitch = parcel.readInt() == 1;
        this.mIsDefaultConfigType = parcel.readInt() == 1;
        deserializeRCPSettings(parcel, this.mRCPDataSettings);
        deserializeRCPSettings(parcel, this.mAllowChangeDataSettings);
        deserializeRCPSettings(parcel, this.mRCPNotifSettings);
        this.mAuthenticationConfig = (AuthenticationConfig) parcel.readParcelable(AuthenticationConfig.class.getClassLoader());
        this.mKeyguardDisabledFeatures = parcel.readInt();
    }
}
