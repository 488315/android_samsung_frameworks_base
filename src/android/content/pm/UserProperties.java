package android.content.pm;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Slog;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes.dex */
public final class UserProperties implements Parcelable {
    private static final String ATTR_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING = "allowStoppingUserWithDelayedLocking";
    private static final String ATTR_ALWAYS_VISIBLE = "alwaysVisible";
    private static final String ATTR_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE = "authAlwaysRequiredToDisableQuietMode";
    private static final String ATTR_CREDENTIAL_SHAREABLE_WITH_PARENT = "credentialShareableWithParent";
    private static final String ATTR_CROSS_PROFILE_CONTENT_SHARING_STRATEGY = "crossProfileContentSharingStrategy";
    private static final String ATTR_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL = "crossProfileIntentFilterAccessControl";
    private static final String ATTR_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY = "crossProfileIntentResolutionStrategy";
    private static final String ATTR_DELETE_APP_WITH_PARENT = "deleteAppWithParent";
    private static final String ATTR_INHERIT_DEVICE_POLICY = "inheritDevicePolicy";
    private static final String ATTR_MEDIA_SHARED_WITH_PARENT = "mediaSharedWithParent";
    private static final String ATTR_PROFILE_API_VISIBILITY = "profileApiVisibility";
    private static final String ATTR_SHOW_IN_LAUNCHER = "showInLauncher";
    private static final String ATTR_SHOW_IN_QUIET_MODE = "showInQuietMode";
    private static final String ATTR_SHOW_IN_SETTINGS = "showInSettings";
    private static final String ATTR_SHOW_IN_SHARING_SURFACES = "showInSharingSurfaces";
    private static final String ATTR_START_WITH_PARENT = "startWithParent";
    private static final String ATTR_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA = "updateCrossProfileIntentFiltersOnOTA";
    private static final String ATTR_USE_PARENTS_CONTACTS = "useParentsContacts";
    public static final Parcelable.Creator<UserProperties> CREATOR = new Parcelable.Creator<UserProperties>() { // from class: android.content.pm.UserProperties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserProperties createFromParcel(Parcel parcel) {
            return new UserProperties(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UserProperties[] newArray(int i) {
            return new UserProperties[i];
        }
    };
    public static final int CROSS_PROFILE_CONTENT_SHARING_DELEGATE_FROM_PARENT = 1;
    public static final int CROSS_PROFILE_CONTENT_SHARING_NO_DELEGATION = 0;
    public static final int CROSS_PROFILE_CONTENT_SHARING_UNKNOWN = -1;
    public static final int CROSS_PROFILE_INTENT_FILTER_ACCESS_LEVEL_ALL = 0;
    public static final int CROSS_PROFILE_INTENT_FILTER_ACCESS_LEVEL_SYSTEM = 10;
    public static final int CROSS_PROFILE_INTENT_FILTER_ACCESS_LEVEL_SYSTEM_ADD_ONLY = 20;
    public static final int CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY_DEFAULT = 0;
    public static final int CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY_NO_FILTERING = 1;
    private static final int INDEX_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING = 16;
    private static final int INDEX_ALWAYS_VISIBLE = 11;
    private static final int INDEX_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE = 13;
    private static final int INDEX_CREDENTIAL_SHAREABLE_WITH_PARENT = 9;
    private static final int INDEX_CROSS_PROFILE_CONTENT_SHARING_STRATEGY = 15;
    private static final int INDEX_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL = 6;
    private static final int INDEX_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY = 7;
    private static final int INDEX_DELETE_APP_WITH_PARENT = 10;
    private static final int INDEX_INHERIT_DEVICE_POLICY = 3;
    private static final int INDEX_ITEMS_RESTRICTED_ON_HOME_SCREEN = 18;
    private static final int INDEX_MEDIA_SHARED_WITH_PARENT = 8;
    private static final int INDEX_PROFILE_API_VISIBILITY = 17;
    private static final int INDEX_SHOW_IN_LAUNCHER = 0;
    private static final int INDEX_SHOW_IN_QUIET_MODE = 12;
    private static final int INDEX_SHOW_IN_SETTINGS = 2;
    private static final int INDEX_SHOW_IN_SHARING_SURFACES = 14;
    private static final int INDEX_START_WITH_PARENT = 1;
    private static final int INDEX_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA = 5;
    private static final int INDEX_USE_PARENTS_CONTACTS = 4;
    public static final int INHERIT_DEVICE_POLICY_FROM_PARENT = 1;
    public static final int INHERIT_DEVICE_POLICY_NO = 0;
    private static final String ITEMS_RESTRICTED_ON_HOME_SCREEN = "itemsRestrictedOnHomeScreen";
    private static final String LOG_TAG = "UserProperties";
    public static final int PROFILE_API_VISIBILITY_HIDDEN = 1;
    public static final int PROFILE_API_VISIBILITY_UNKNOWN = -1;
    public static final int PROFILE_API_VISIBILITY_VISIBLE = 0;
    public static final int SHOW_IN_LAUNCHER_NO = 2;
    public static final int SHOW_IN_LAUNCHER_SEPARATE = 1;
    public static final int SHOW_IN_LAUNCHER_UNKNOWN = -1;
    public static final int SHOW_IN_LAUNCHER_WITH_PARENT = 0;
    public static final int SHOW_IN_QUIET_MODE_DEFAULT = 2;
    public static final int SHOW_IN_QUIET_MODE_HIDDEN = 1;
    public static final int SHOW_IN_QUIET_MODE_PAUSED = 0;
    public static final int SHOW_IN_QUIET_MODE_UNKNOWN = -1;
    public static final int SHOW_IN_SETTINGS_NO = 2;
    public static final int SHOW_IN_SETTINGS_SEPARATE = 1;
    public static final int SHOW_IN_SETTINGS_UNKNOWN = -1;
    public static final int SHOW_IN_SETTINGS_WITH_PARENT = 0;
    public static final int SHOW_IN_SHARING_SURFACES_NO = 2;
    public static final int SHOW_IN_SHARING_SURFACES_SEPARATE = 1;
    public static final int SHOW_IN_SHARING_SURFACES_UNKNOWN = -1;
    public static final int SHOW_IN_SHARING_SURFACES_WITH_PARENT = 0;
    private boolean mAllowStoppingUserWithDelayedLocking;
    private boolean mAlwaysVisible;
    private boolean mAuthAlwaysRequiredToDisableQuietMode;
    private boolean mCredentialShareableWithParent;
    private int mCrossProfileContentSharingStrategy;
    private int mCrossProfileIntentFilterAccessControl;
    private int mCrossProfileIntentResolutionStrategy;
    private final UserProperties mDefaultProperties;
    private boolean mDeleteAppWithParent;
    private int mInheritDevicePolicy;
    private boolean mItemsRestrictedOnHomeScreen;
    private boolean mMediaSharedWithParent;
    private int mProfileApiVisibility;
    private long mPropertiesPresent;
    private int mShowInLauncher;
    private int mShowInQuietMode;
    private int mShowInSettings;
    private int mShowInSharingSurfaces;
    private boolean mStartWithParent;
    private boolean mUpdateCrossProfileIntentFiltersOnOTA;
    private boolean mUseParentsContacts;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CrossProfileContentSharingStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CrossProfileIntentFilterAccessControlLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CrossProfileIntentResolutionStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InheritDevicePolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileApiVisibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface PropertyIndex {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowInLauncher {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowInQuietMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowInSettings {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowInSharingSurfaces {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UserProperties(UserProperties userProperties) {
        this.mDefaultProperties = userProperties;
        this.mPropertiesPresent = 0L;
    }

    public UserProperties(UserProperties userProperties, boolean z, boolean z2, boolean z3) {
        this.mPropertiesPresent = 0L;
        if (userProperties.mDefaultProperties == null) {
            throw new IllegalArgumentException("Attempting to copy a non-original UserProperties.");
        }
        this.mDefaultProperties = null;
        if (z) {
            setStartWithParent(userProperties.getStartWithParent());
            setInheritDevicePolicy(userProperties.getInheritDevicePolicy());
            setUpdateCrossProfileIntentFiltersOnOTA(userProperties.getUpdateCrossProfileIntentFiltersOnOTA());
            setCrossProfileIntentFilterAccessControl(userProperties.getCrossProfileIntentFilterAccessControl());
            setCrossProfileIntentResolutionStrategy(userProperties.getCrossProfileIntentResolutionStrategy());
            setDeleteAppWithParent(userProperties.getDeleteAppWithParent());
            setAlwaysVisible(userProperties.getAlwaysVisible());
            setAllowStoppingUserWithDelayedLocking(userProperties.getAllowStoppingUserWithDelayedLocking());
        }
        if (z2) {
            setShowInSettings(userProperties.getShowInSettings());
            setUseParentsContacts(userProperties.getUseParentsContacts());
            setAuthAlwaysRequiredToDisableQuietMode(userProperties.isAuthAlwaysRequiredToDisableQuietMode());
        }
        setShowInLauncher(userProperties.getShowInLauncher());
        setMediaSharedWithParent(userProperties.isMediaSharedWithParent());
        setCredentialShareableWithParent(userProperties.isCredentialShareableWithParent());
        setShowInQuietMode(userProperties.getShowInQuietMode());
        setShowInSharingSurfaces(userProperties.getShowInSharingSurfaces());
        setCrossProfileContentSharingStrategy(userProperties.getCrossProfileContentSharingStrategy());
        setProfileApiVisibility(userProperties.getProfileApiVisibility());
        setItemsRestrictedOnHomeScreen(userProperties.areItemsRestrictedOnHomeScreen());
    }

    private boolean isPresent(long j) {
        return ((1 << ((int) j)) & this.mPropertiesPresent) != 0;
    }

    private void setPresent(long j) {
        this.mPropertiesPresent = (1 << ((int) j)) | this.mPropertiesPresent;
    }

    public long getPropertiesPresent() {
        return this.mPropertiesPresent;
    }

    public int getShowInLauncher() {
        if (isPresent(0L)) {
            return this.mShowInLauncher;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mShowInLauncher;
        }
        throw new SecurityException("You don't have permission to query showInLauncher");
    }

    public void setShowInLauncher(int i) {
        this.mShowInLauncher = i;
        setPresent(0L);
    }

    public int getShowInSettings() {
        if (isPresent(2L)) {
            return this.mShowInSettings;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mShowInSettings;
        }
        throw new SecurityException("You don't have permission to query mShowInSettings");
    }

    public void setShowInSettings(int i) {
        this.mShowInSettings = i;
        setPresent(2L);
    }

    public int getShowInQuietMode() {
        if (isPresent(12L)) {
            return this.mShowInQuietMode;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mShowInQuietMode;
        }
        throw new SecurityException("You don't have permission to query ShowInQuietMode");
    }

    public void setShowInQuietMode(int i) {
        this.mShowInQuietMode = i;
        setPresent(12L);
    }

    public int getShowInSharingSurfaces() {
        if (isPresent(14L)) {
            return this.mShowInSharingSurfaces;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mShowInSharingSurfaces;
        }
        throw new SecurityException("You don't have permission to query ShowInSharingSurfaces");
    }

    public void setShowInSharingSurfaces(int i) {
        this.mShowInSharingSurfaces = i;
        setPresent(14L);
    }

    public boolean getStartWithParent() {
        if (isPresent(1L)) {
            return this.mStartWithParent;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mStartWithParent;
        }
        throw new SecurityException("You don't have permission to query startWithParent");
    }

    public void setStartWithParent(boolean z) {
        this.mStartWithParent = z;
        setPresent(1L);
    }

    public boolean getDeleteAppWithParent() {
        if (isPresent(10L)) {
            return this.mDeleteAppWithParent;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mDeleteAppWithParent;
        }
        throw new SecurityException("You don't have permission to query deleteAppWithParent");
    }

    public void setDeleteAppWithParent(boolean z) {
        this.mDeleteAppWithParent = z;
        setPresent(10L);
    }

    public boolean getAlwaysVisible() {
        if (isPresent(11L)) {
            return this.mAlwaysVisible;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mAlwaysVisible;
        }
        throw new SecurityException("You don't have permission to query alwaysVisible");
    }

    public void setAlwaysVisible(boolean z) {
        this.mAlwaysVisible = z;
        setPresent(11L);
    }

    public int getInheritDevicePolicy() {
        if (isPresent(3L)) {
            return this.mInheritDevicePolicy;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mInheritDevicePolicy;
        }
        throw new SecurityException("You don't have permission to query inheritDevicePolicy");
    }

    public void setInheritDevicePolicy(int i) {
        this.mInheritDevicePolicy = i;
        setPresent(3L);
    }

    public boolean getUseParentsContacts() {
        if (isPresent(4L)) {
            return this.mUseParentsContacts;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mUseParentsContacts;
        }
        throw new SecurityException("You don't have permission to query useParentsContacts");
    }

    public void setUseParentsContacts(boolean z) {
        this.mUseParentsContacts = z;
        setPresent(4L);
    }

    public boolean getUpdateCrossProfileIntentFiltersOnOTA() {
        if (isPresent(5L)) {
            return this.mUpdateCrossProfileIntentFiltersOnOTA;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mUpdateCrossProfileIntentFiltersOnOTA;
        }
        throw new SecurityException("You don't have permission to query updateCrossProfileIntentFiltersOnOTA");
    }

    public void setUpdateCrossProfileIntentFiltersOnOTA(boolean z) {
        this.mUpdateCrossProfileIntentFiltersOnOTA = z;
        setPresent(5L);
    }

    public boolean isMediaSharedWithParent() {
        if (isPresent(8L)) {
            return this.mMediaSharedWithParent;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mMediaSharedWithParent;
        }
        throw new SecurityException("You don't have permission to query mediaSharedWithParent");
    }

    public void setMediaSharedWithParent(boolean z) {
        this.mMediaSharedWithParent = z;
        setPresent(8L);
    }

    public boolean isCredentialShareableWithParent() {
        if (isPresent(9L)) {
            return this.mCredentialShareableWithParent;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mCredentialShareableWithParent;
        }
        throw new SecurityException("You don't have permission to query credentialShareableWithParent");
    }

    public void setCredentialShareableWithParent(boolean z) {
        this.mCredentialShareableWithParent = z;
        setPresent(9L);
    }

    public boolean isAuthAlwaysRequiredToDisableQuietMode() {
        if (isPresent(13L)) {
            return this.mAuthAlwaysRequiredToDisableQuietMode;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mAuthAlwaysRequiredToDisableQuietMode;
        }
        throw new SecurityException("You don't have permission to query authAlwaysRequiredToDisableQuietMode");
    }

    public void setAuthAlwaysRequiredToDisableQuietMode(boolean z) {
        this.mAuthAlwaysRequiredToDisableQuietMode = z;
        setPresent(13L);
    }

    public boolean getAllowStoppingUserWithDelayedLocking() {
        if (isPresent(16L)) {
            return this.mAllowStoppingUserWithDelayedLocking;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mAllowStoppingUserWithDelayedLocking;
        }
        throw new SecurityException("You don't have permission to query allowStoppingUserWithDelayedLocking");
    }

    public void setAllowStoppingUserWithDelayedLocking(boolean z) {
        this.mAllowStoppingUserWithDelayedLocking = z;
        setPresent(16L);
    }

    public int getCrossProfileIntentFilterAccessControl() {
        if (isPresent(6L)) {
            return this.mCrossProfileIntentFilterAccessControl;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mCrossProfileIntentFilterAccessControl;
        }
        throw new SecurityException("You don't have permission to query crossProfileIntentFilterAccessControl");
    }

    public void setCrossProfileIntentFilterAccessControl(int i) {
        this.mCrossProfileIntentFilterAccessControl = i;
        setPresent(6L);
    }

    public int getCrossProfileIntentResolutionStrategy() {
        if (isPresent(7L)) {
            return this.mCrossProfileIntentResolutionStrategy;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mCrossProfileIntentResolutionStrategy;
        }
        throw new SecurityException("You don't have permission to query crossProfileIntentResolutionStrategy");
    }

    public void setCrossProfileIntentResolutionStrategy(int i) {
        this.mCrossProfileIntentResolutionStrategy = i;
        setPresent(7L);
    }

    public int getCrossProfileContentSharingStrategy() {
        if (isPresent(15L)) {
            return this.mCrossProfileContentSharingStrategy;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mCrossProfileContentSharingStrategy;
        }
        throw new SecurityException("You don't have permission to query crossProfileContentSharingStrategy");
    }

    public void setCrossProfileContentSharingStrategy(int i) {
        this.mCrossProfileContentSharingStrategy = i;
        setPresent(15L);
    }

    public int getProfileApiVisibility() {
        if (isPresent(17L)) {
            return this.mProfileApiVisibility;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mProfileApiVisibility;
        }
        throw new SecurityException("You don't have permission to query profileApiVisibility");
    }

    public void setProfileApiVisibility(int i) {
        this.mProfileApiVisibility = i;
        setPresent(17L);
    }

    public boolean areItemsRestrictedOnHomeScreen() {
        if (isPresent(18L)) {
            return this.mItemsRestrictedOnHomeScreen;
        }
        UserProperties userProperties = this.mDefaultProperties;
        if (userProperties != null) {
            return userProperties.mItemsRestrictedOnHomeScreen;
        }
        throw new SecurityException("You don't have permission to query mItemsRestrictedOnHomeScreen");
    }

    public void setItemsRestrictedOnHomeScreen(boolean z) {
        this.mItemsRestrictedOnHomeScreen = z;
        setPresent(18L);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("UserProperties{mPropertiesPresent=");
        sb.append(Long.toBinaryString(this.mPropertiesPresent));
        try {
            sb.append((CharSequence) listPropertiesAsStringBuilder());
        } catch (SecurityException unused) {
        }
        sb.append("}");
        return sb.toString();
    }

    private StringBuilder listPropertiesAsStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(", mShowInLauncher=");
        sb.append(getShowInLauncher());
        sb.append(", mStartWithParent=");
        sb.append(getStartWithParent());
        sb.append(", mShowInSettings=");
        sb.append(getShowInSettings());
        sb.append(", mInheritDevicePolicy=");
        sb.append(getInheritDevicePolicy());
        sb.append(", mUseParentsContacts=");
        sb.append(getUseParentsContacts());
        sb.append(", mUpdateCrossProfileIntentFiltersOnOTA=");
        sb.append(getUpdateCrossProfileIntentFiltersOnOTA());
        sb.append(", mCrossProfileIntentFilterAccessControl=");
        sb.append(getCrossProfileIntentFilterAccessControl());
        sb.append(", mCrossProfileIntentResolutionStrategy=");
        sb.append(getCrossProfileIntentResolutionStrategy());
        sb.append(", mMediaSharedWithParent=");
        sb.append(isMediaSharedWithParent());
        sb.append(", mCredentialShareableWithParent=");
        sb.append(isCredentialShareableWithParent());
        sb.append(", mAuthAlwaysRequiredToDisableQuietMode=");
        sb.append(isAuthAlwaysRequiredToDisableQuietMode());
        sb.append(", mAllowStoppingUserWithDelayedLocking=");
        sb.append(getAllowStoppingUserWithDelayedLocking());
        sb.append(", mDeleteAppWithParent=");
        sb.append(getDeleteAppWithParent());
        sb.append(", mAlwaysVisible=");
        sb.append(getAlwaysVisible());
        sb.append(", mCrossProfileContentSharingStrategy=");
        sb.append(getCrossProfileContentSharingStrategy());
        sb.append(", mProfileApiVisibility=");
        sb.append(getProfileApiVisibility());
        sb.append(", mItemsRestrictedOnHomeScreen=");
        sb.append(areItemsRestrictedOnHomeScreen());
        return sb;
    }

    public void println(PrintWriter printWriter, String str) {
        printWriter.println(str + "UserProperties:");
        printWriter.println(str + "    mPropertiesPresent=" + Long.toBinaryString(this.mPropertiesPresent));
        printWriter.println(str + "    mShowInLauncher=" + getShowInLauncher());
        printWriter.println(str + "    mStartWithParent=" + getStartWithParent());
        printWriter.println(str + "    mShowInSettings=" + getShowInSettings());
        printWriter.println(str + "    mInheritDevicePolicy=" + getInheritDevicePolicy());
        printWriter.println(str + "    mUseParentsContacts=" + getUseParentsContacts());
        printWriter.println(str + "    mUpdateCrossProfileIntentFiltersOnOTA=" + getUpdateCrossProfileIntentFiltersOnOTA());
        printWriter.println(str + "    mCrossProfileIntentFilterAccessControl=" + getCrossProfileIntentFilterAccessControl());
        printWriter.println(str + "    mCrossProfileIntentResolutionStrategy=" + getCrossProfileIntentResolutionStrategy());
        printWriter.println(str + "    mMediaSharedWithParent=" + isMediaSharedWithParent());
        printWriter.println(str + "    mCredentialShareableWithParent=" + isCredentialShareableWithParent());
        printWriter.println(str + "    mAuthAlwaysRequiredToDisableQuietMode=" + isAuthAlwaysRequiredToDisableQuietMode());
        printWriter.println(str + "    mAllowStoppingUserWithDelayedLocking=" + getAllowStoppingUserWithDelayedLocking());
        printWriter.println(str + "    mDeleteAppWithParent=" + getDeleteAppWithParent());
        printWriter.println(str + "    mAlwaysVisible=" + getAlwaysVisible());
        printWriter.println(str + "    mCrossProfileContentSharingStrategy=" + getCrossProfileContentSharingStrategy());
        printWriter.println(str + "    mProfileApiVisibility=" + getProfileApiVisibility());
        printWriter.println(str + "    mItemsRestrictedOnHomeScreen=" + areItemsRestrictedOnHomeScreen());
    }

    public UserProperties(TypedXmlPullParser typedXmlPullParser, UserProperties userProperties) throws IOException, XmlPullParserException {
        this(userProperties);
        updateFromXml(typedXmlPullParser);
    }

    public void updateFromXml(TypedXmlPullParser typedXmlPullParser) throws IOException, XmlPullParserException {
        String attributeName;
        int attributeCount = typedXmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            attributeName = typedXmlPullParser.getAttributeName(i);
            attributeName.hashCode();
            switch (attributeName) {
                case "crossProfileContentSharingStrategy":
                    setCrossProfileContentSharingStrategy(typedXmlPullParser.getAttributeInt(i));
                    break;
                case "showInSettings":
                    setShowInSettings(typedXmlPullParser.getAttributeInt(i));
                    break;
                case "alwaysVisible":
                    setAlwaysVisible(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "profileApiVisibility":
                    setProfileApiVisibility(typedXmlPullParser.getAttributeInt(i));
                    break;
                case "deleteAppWithParent":
                    setDeleteAppWithParent(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "updateCrossProfileIntentFiltersOnOTA":
                    setUpdateCrossProfileIntentFiltersOnOTA(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "crossProfileIntentFilterAccessControl":
                    setCrossProfileIntentFilterAccessControl(typedXmlPullParser.getAttributeInt(i));
                    break;
                case "mediaSharedWithParent":
                    setMediaSharedWithParent(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "allowStoppingUserWithDelayedLocking":
                    setAllowStoppingUserWithDelayedLocking(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "startWithParent":
                    setStartWithParent(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "showInLauncher":
                    setShowInLauncher(typedXmlPullParser.getAttributeInt(i));
                    break;
                case "credentialShareableWithParent":
                    setCredentialShareableWithParent(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "inheritDevicePolicy":
                    setInheritDevicePolicy(typedXmlPullParser.getAttributeInt(i));
                    break;
                case "useParentsContacts":
                    setUseParentsContacts(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "authAlwaysRequiredToDisableQuietMode":
                    setAuthAlwaysRequiredToDisableQuietMode(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "showInSharingSurfaces":
                    setShowInSharingSurfaces(typedXmlPullParser.getAttributeInt(i));
                    break;
                case "itemsRestrictedOnHomeScreen":
                    setItemsRestrictedOnHomeScreen(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case "showInQuietMode":
                    setShowInQuietMode(typedXmlPullParser.getAttributeInt(i));
                    break;
                case "crossProfileIntentResolutionStrategy":
                    setCrossProfileIntentResolutionStrategy(typedXmlPullParser.getAttributeInt(i));
                    break;
                default:
                    Slog.w(LOG_TAG, "Skipping unknown property " + attributeName);
                    break;
            }
        }
    }

    public void writeToXml(TypedXmlSerializer typedXmlSerializer) throws IOException, XmlPullParserException {
        if (isPresent(0L)) {
            typedXmlSerializer.attributeInt(null, ATTR_SHOW_IN_LAUNCHER, this.mShowInLauncher);
        }
        if (isPresent(1L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_START_WITH_PARENT, this.mStartWithParent);
        }
        if (isPresent(2L)) {
            typedXmlSerializer.attributeInt(null, ATTR_SHOW_IN_SETTINGS, this.mShowInSettings);
        }
        if (isPresent(12L)) {
            typedXmlSerializer.attributeInt(null, ATTR_SHOW_IN_QUIET_MODE, this.mShowInQuietMode);
        }
        if (isPresent(14L)) {
            typedXmlSerializer.attributeInt(null, ATTR_SHOW_IN_SHARING_SURFACES, this.mShowInSharingSurfaces);
        }
        if (isPresent(3L)) {
            typedXmlSerializer.attributeInt(null, ATTR_INHERIT_DEVICE_POLICY, this.mInheritDevicePolicy);
        }
        if (isPresent(4L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_USE_PARENTS_CONTACTS, this.mUseParentsContacts);
        }
        if (isPresent(5L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA, this.mUpdateCrossProfileIntentFiltersOnOTA);
        }
        if (isPresent(6L)) {
            typedXmlSerializer.attributeInt(null, ATTR_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL, this.mCrossProfileIntentFilterAccessControl);
        }
        if (isPresent(7L)) {
            typedXmlSerializer.attributeInt(null, ATTR_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY, this.mCrossProfileIntentResolutionStrategy);
        }
        if (isPresent(8L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_MEDIA_SHARED_WITH_PARENT, this.mMediaSharedWithParent);
        }
        if (isPresent(9L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_CREDENTIAL_SHAREABLE_WITH_PARENT, this.mCredentialShareableWithParent);
        }
        if (isPresent(13L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE, this.mAuthAlwaysRequiredToDisableQuietMode);
        }
        if (isPresent(16L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING, this.mAllowStoppingUserWithDelayedLocking);
        }
        if (isPresent(10L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_DELETE_APP_WITH_PARENT, this.mDeleteAppWithParent);
        }
        if (isPresent(11L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_ALWAYS_VISIBLE, this.mAlwaysVisible);
        }
        if (isPresent(15L)) {
            typedXmlSerializer.attributeInt(null, ATTR_CROSS_PROFILE_CONTENT_SHARING_STRATEGY, this.mCrossProfileContentSharingStrategy);
        }
        if (isPresent(17L)) {
            typedXmlSerializer.attributeInt(null, ATTR_PROFILE_API_VISIBILITY, this.mProfileApiVisibility);
        }
        if (isPresent(18L)) {
            typedXmlSerializer.attributeBoolean(null, ITEMS_RESTRICTED_ON_HOME_SCREEN, this.mItemsRestrictedOnHomeScreen);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mPropertiesPresent);
        parcel.writeInt(this.mShowInLauncher);
        parcel.writeBoolean(this.mStartWithParent);
        parcel.writeInt(this.mShowInSettings);
        parcel.writeInt(this.mShowInQuietMode);
        parcel.writeInt(this.mShowInSharingSurfaces);
        parcel.writeInt(this.mInheritDevicePolicy);
        parcel.writeBoolean(this.mUseParentsContacts);
        parcel.writeBoolean(this.mUpdateCrossProfileIntentFiltersOnOTA);
        parcel.writeInt(this.mCrossProfileIntentFilterAccessControl);
        parcel.writeInt(this.mCrossProfileIntentResolutionStrategy);
        parcel.writeBoolean(this.mMediaSharedWithParent);
        parcel.writeBoolean(this.mCredentialShareableWithParent);
        parcel.writeBoolean(this.mAuthAlwaysRequiredToDisableQuietMode);
        parcel.writeBoolean(this.mAllowStoppingUserWithDelayedLocking);
        parcel.writeBoolean(this.mDeleteAppWithParent);
        parcel.writeBoolean(this.mAlwaysVisible);
        parcel.writeInt(this.mCrossProfileContentSharingStrategy);
        parcel.writeInt(this.mProfileApiVisibility);
        parcel.writeBoolean(this.mItemsRestrictedOnHomeScreen);
    }

    private UserProperties(Parcel parcel) {
        this.mPropertiesPresent = 0L;
        this.mDefaultProperties = null;
        this.mPropertiesPresent = parcel.readLong();
        this.mShowInLauncher = parcel.readInt();
        this.mStartWithParent = parcel.readBoolean();
        this.mShowInSettings = parcel.readInt();
        this.mShowInQuietMode = parcel.readInt();
        this.mShowInSharingSurfaces = parcel.readInt();
        this.mInheritDevicePolicy = parcel.readInt();
        this.mUseParentsContacts = parcel.readBoolean();
        this.mUpdateCrossProfileIntentFiltersOnOTA = parcel.readBoolean();
        this.mCrossProfileIntentFilterAccessControl = parcel.readInt();
        this.mCrossProfileIntentResolutionStrategy = parcel.readInt();
        this.mMediaSharedWithParent = parcel.readBoolean();
        this.mCredentialShareableWithParent = parcel.readBoolean();
        this.mAuthAlwaysRequiredToDisableQuietMode = parcel.readBoolean();
        this.mAllowStoppingUserWithDelayedLocking = parcel.readBoolean();
        this.mDeleteAppWithParent = parcel.readBoolean();
        this.mAlwaysVisible = parcel.readBoolean();
        this.mCrossProfileContentSharingStrategy = parcel.readInt();
        this.mProfileApiVisibility = parcel.readInt();
        this.mItemsRestrictedOnHomeScreen = parcel.readBoolean();
    }

    public static final class Builder {
        private int mShowInLauncher = 0;
        private boolean mStartWithParent = false;
        private int mShowInSettings = 0;
        private int mShowInQuietMode = 0;
        private int mShowInSharingSurfaces = 1;
        private int mInheritDevicePolicy = 0;
        private boolean mUseParentsContacts = false;
        private boolean mUpdateCrossProfileIntentFiltersOnOTA = false;
        private int mCrossProfileIntentFilterAccessControl = 0;
        private int mCrossProfileIntentResolutionStrategy = 0;
        private boolean mMediaSharedWithParent = false;
        private boolean mCredentialShareableWithParent = false;
        private boolean mAuthAlwaysRequiredToDisableQuietMode = false;
        private boolean mAllowStoppingUserWithDelayedLocking = false;
        private boolean mDeleteAppWithParent = false;
        private boolean mAlwaysVisible = false;
        private int mCrossProfileContentSharingStrategy = 0;
        private int mProfileApiVisibility = 0;
        private boolean mItemsRestrictedOnHomeScreen = false;

        public Builder setShowInLauncher(int i) {
            this.mShowInLauncher = i;
            return this;
        }

        public Builder setStartWithParent(boolean z) {
            this.mStartWithParent = z;
            return this;
        }

        public Builder setShowInSettings(int i) {
            this.mShowInSettings = i;
            return this;
        }

        public Builder setShowInQuietMode(int i) {
            this.mShowInQuietMode = i;
            return this;
        }

        public Builder setShowInSharingSurfaces(int i) {
            this.mShowInSharingSurfaces = i;
            return this;
        }

        public Builder setInheritDevicePolicy(int i) {
            this.mInheritDevicePolicy = i;
            return this;
        }

        public Builder setUseParentsContacts(boolean z) {
            this.mUseParentsContacts = z;
            return this;
        }

        public Builder setUpdateCrossProfileIntentFiltersOnOTA(boolean z) {
            this.mUpdateCrossProfileIntentFiltersOnOTA = z;
            return this;
        }

        public Builder setCrossProfileIntentFilterAccessControl(int i) {
            this.mCrossProfileIntentFilterAccessControl = i;
            return this;
        }

        public Builder setCrossProfileIntentResolutionStrategy(int i) {
            this.mCrossProfileIntentResolutionStrategy = i;
            return this;
        }

        public Builder setMediaSharedWithParent(boolean z) {
            this.mMediaSharedWithParent = z;
            return this;
        }

        public Builder setCredentialShareableWithParent(boolean z) {
            this.mCredentialShareableWithParent = z;
            return this;
        }

        public Builder setAuthAlwaysRequiredToDisableQuietMode(boolean z) {
            this.mAuthAlwaysRequiredToDisableQuietMode = z;
            return this;
        }

        public Builder setAllowStoppingUserWithDelayedLocking(boolean z) {
            this.mAllowStoppingUserWithDelayedLocking = z;
            return this;
        }

        public Builder setDeleteAppWithParent(boolean z) {
            this.mDeleteAppWithParent = z;
            return this;
        }

        public Builder setAlwaysVisible(boolean z) {
            this.mAlwaysVisible = z;
            return this;
        }

        public Builder setCrossProfileContentSharingStrategy(int i) {
            this.mCrossProfileContentSharingStrategy = i;
            return this;
        }

        public Builder setProfileApiVisibility(int i) {
            this.mProfileApiVisibility = i;
            return this;
        }

        public Builder setItemsRestrictedOnHomeScreen(boolean z) {
            this.mItemsRestrictedOnHomeScreen = z;
            return this;
        }

        public UserProperties build() {
            return new UserProperties(this.mShowInLauncher, this.mStartWithParent, this.mShowInSettings, this.mShowInQuietMode, this.mShowInSharingSurfaces, this.mInheritDevicePolicy, this.mUseParentsContacts, this.mUpdateCrossProfileIntentFiltersOnOTA, this.mCrossProfileIntentFilterAccessControl, this.mCrossProfileIntentResolutionStrategy, this.mMediaSharedWithParent, this.mCredentialShareableWithParent, this.mAuthAlwaysRequiredToDisableQuietMode, this.mAllowStoppingUserWithDelayedLocking, this.mDeleteAppWithParent, this.mAlwaysVisible, this.mCrossProfileContentSharingStrategy, this.mProfileApiVisibility, this.mItemsRestrictedOnHomeScreen);
        }
    }

    private UserProperties(int i, boolean z, int i2, int i3, int i4, int i5, boolean z2, boolean z3, int i6, int i7, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, int i8, int i9, boolean z10) {
        this.mPropertiesPresent = 0L;
        this.mDefaultProperties = null;
        setShowInLauncher(i);
        setStartWithParent(z);
        setShowInSettings(i2);
        setShowInQuietMode(i3);
        setShowInSharingSurfaces(i4);
        setInheritDevicePolicy(i5);
        setUseParentsContacts(z2);
        setUpdateCrossProfileIntentFiltersOnOTA(z3);
        setCrossProfileIntentFilterAccessControl(i6);
        setCrossProfileIntentResolutionStrategy(i7);
        setMediaSharedWithParent(z4);
        setCredentialShareableWithParent(z5);
        setAuthAlwaysRequiredToDisableQuietMode(z6);
        setAllowStoppingUserWithDelayedLocking(z7);
        setDeleteAppWithParent(z8);
        setAlwaysVisible(z9);
        setCrossProfileContentSharingStrategy(i8);
        setProfileApiVisibility(i9);
        setItemsRestrictedOnHomeScreen(z10);
    }
}
