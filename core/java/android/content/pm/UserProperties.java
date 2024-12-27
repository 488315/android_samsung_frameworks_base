package android.content.pm;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Slog;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes.dex */
public final class UserProperties implements Parcelable {
    private static final String ATTR_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING =
            "allowStoppingUserWithDelayedLocking";
    private static final String ATTR_ALWAYS_VISIBLE = "alwaysVisible";
    private static final String ATTR_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE =
            "authAlwaysRequiredToDisableQuietMode";
    private static final String ATTR_CREDENTIAL_SHAREABLE_WITH_PARENT =
            "credentialShareableWithParent";
    private static final String ATTR_CROSS_PROFILE_CONTENT_SHARING_STRATEGY =
            "crossProfileContentSharingStrategy";
    private static final String ATTR_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL =
            "crossProfileIntentFilterAccessControl";
    private static final String ATTR_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY =
            "crossProfileIntentResolutionStrategy";
    private static final String ATTR_DELETE_APP_WITH_PARENT = "deleteAppWithParent";
    private static final String ATTR_INHERIT_DEVICE_POLICY = "inheritDevicePolicy";
    private static final String ATTR_MEDIA_SHARED_WITH_PARENT = "mediaSharedWithParent";
    private static final String ATTR_PROFILE_API_VISIBILITY = "profileApiVisibility";
    private static final String ATTR_SHOW_IN_LAUNCHER = "showInLauncher";
    private static final String ATTR_SHOW_IN_QUIET_MODE = "showInQuietMode";
    private static final String ATTR_SHOW_IN_SETTINGS = "showInSettings";
    private static final String ATTR_SHOW_IN_SHARING_SURFACES = "showInSharingSurfaces";
    private static final String ATTR_START_WITH_PARENT = "startWithParent";
    private static final String ATTR_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA =
            "updateCrossProfileIntentFiltersOnOTA";
    private static final String ATTR_USE_PARENTS_CONTACTS = "useParentsContacts";
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
    private static final String LOG_TAG = UserProperties.class.getSimpleName();
    public static final Parcelable.Creator<UserProperties> CREATOR =
            new Parcelable.Creator<
                    UserProperties>() { // from class: android.content.pm.UserProperties.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public UserProperties createFromParcel(Parcel source) {
                    return new UserProperties(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public UserProperties[] newArray(int size) {
                    return new UserProperties[size];
                }
            };

    @Retention(RetentionPolicy.SOURCE)
    public @interface CrossProfileContentSharingStrategy {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface CrossProfileIntentFilterAccessControlLevel {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface CrossProfileIntentResolutionStrategy {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface InheritDevicePolicy {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileApiVisibility {}

    @Retention(RetentionPolicy.SOURCE)
    private @interface PropertyIndex {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowInLauncher {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowInQuietMode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowInSettings {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowInSharingSurfaces {}

    public UserProperties(UserProperties defaultProperties) {
        this.mPropertiesPresent = 0L;
        this.mDefaultProperties = defaultProperties;
        this.mPropertiesPresent = 0L;
    }

    public UserProperties(
            UserProperties orig,
            boolean exposeAllFields,
            boolean hasManagePermission,
            boolean hasQueryOrManagePermission) {
        this.mPropertiesPresent = 0L;
        if (orig.mDefaultProperties == null) {
            throw new IllegalArgumentException("Attempting to copy a non-original UserProperties.");
        }
        this.mDefaultProperties = null;
        if (exposeAllFields) {
            setStartWithParent(orig.getStartWithParent());
            setInheritDevicePolicy(orig.getInheritDevicePolicy());
            setUpdateCrossProfileIntentFiltersOnOTA(orig.getUpdateCrossProfileIntentFiltersOnOTA());
            setCrossProfileIntentFilterAccessControl(
                    orig.getCrossProfileIntentFilterAccessControl());
            setCrossProfileIntentResolutionStrategy(orig.getCrossProfileIntentResolutionStrategy());
            setDeleteAppWithParent(orig.getDeleteAppWithParent());
            setAlwaysVisible(orig.getAlwaysVisible());
            setAllowStoppingUserWithDelayedLocking(orig.getAllowStoppingUserWithDelayedLocking());
        }
        if (hasManagePermission) {
            setShowInSettings(orig.getShowInSettings());
            setUseParentsContacts(orig.getUseParentsContacts());
            setAuthAlwaysRequiredToDisableQuietMode(orig.isAuthAlwaysRequiredToDisableQuietMode());
        }
        setShowInLauncher(orig.getShowInLauncher());
        setMediaSharedWithParent(orig.isMediaSharedWithParent());
        setCredentialShareableWithParent(orig.isCredentialShareableWithParent());
        setShowInQuietMode(orig.getShowInQuietMode());
        setShowInSharingSurfaces(orig.getShowInSharingSurfaces());
        setCrossProfileContentSharingStrategy(orig.getCrossProfileContentSharingStrategy());
        setProfileApiVisibility(orig.getProfileApiVisibility());
        setItemsRestrictedOnHomeScreen(orig.areItemsRestrictedOnHomeScreen());
    }

    private boolean isPresent(long index) {
        return (this.mPropertiesPresent & (1 << ((int) index))) != 0;
    }

    private void setPresent(long index) {
        this.mPropertiesPresent |= 1 << ((int) index);
    }

    public long getPropertiesPresent() {
        return this.mPropertiesPresent;
    }

    public int getShowInLauncher() {
        if (isPresent(0L)) {
            return this.mShowInLauncher;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mShowInLauncher;
        }
        throw new SecurityException("You don't have permission to query showInLauncher");
    }

    public void setShowInLauncher(int val) {
        this.mShowInLauncher = val;
        setPresent(0L);
    }

    public int getShowInSettings() {
        if (isPresent(2L)) {
            return this.mShowInSettings;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mShowInSettings;
        }
        throw new SecurityException("You don't have permission to query mShowInSettings");
    }

    public void setShowInSettings(int val) {
        this.mShowInSettings = val;
        setPresent(2L);
    }

    public int getShowInQuietMode() {
        if (isPresent(12L)) {
            return this.mShowInQuietMode;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mShowInQuietMode;
        }
        throw new SecurityException("You don't have permission to query ShowInQuietMode");
    }

    public void setShowInQuietMode(int showInQuietMode) {
        this.mShowInQuietMode = showInQuietMode;
        setPresent(12L);
    }

    public int getShowInSharingSurfaces() {
        if (isPresent(14L)) {
            return this.mShowInSharingSurfaces;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mShowInSharingSurfaces;
        }
        throw new SecurityException("You don't have permission to query ShowInSharingSurfaces");
    }

    public void setShowInSharingSurfaces(int showInSharingSurfaces) {
        this.mShowInSharingSurfaces = showInSharingSurfaces;
        setPresent(14L);
    }

    public boolean getStartWithParent() {
        if (isPresent(1L)) {
            return this.mStartWithParent;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mStartWithParent;
        }
        throw new SecurityException("You don't have permission to query startWithParent");
    }

    public void setStartWithParent(boolean val) {
        this.mStartWithParent = val;
        setPresent(1L);
    }

    public boolean getDeleteAppWithParent() {
        if (isPresent(10L)) {
            return this.mDeleteAppWithParent;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mDeleteAppWithParent;
        }
        throw new SecurityException("You don't have permission to query deleteAppWithParent");
    }

    public void setDeleteAppWithParent(boolean val) {
        this.mDeleteAppWithParent = val;
        setPresent(10L);
    }

    public boolean getAlwaysVisible() {
        if (isPresent(11L)) {
            return this.mAlwaysVisible;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mAlwaysVisible;
        }
        throw new SecurityException("You don't have permission to query alwaysVisible");
    }

    public void setAlwaysVisible(boolean val) {
        this.mAlwaysVisible = val;
        setPresent(11L);
    }

    public int getInheritDevicePolicy() {
        if (isPresent(3L)) {
            return this.mInheritDevicePolicy;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mInheritDevicePolicy;
        }
        throw new SecurityException("You don't have permission to query inheritDevicePolicy");
    }

    public void setInheritDevicePolicy(int val) {
        this.mInheritDevicePolicy = val;
        setPresent(3L);
    }

    public boolean getUseParentsContacts() {
        if (isPresent(4L)) {
            return this.mUseParentsContacts;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mUseParentsContacts;
        }
        throw new SecurityException("You don't have permission to query useParentsContacts");
    }

    public void setUseParentsContacts(boolean val) {
        this.mUseParentsContacts = val;
        setPresent(4L);
    }

    public boolean getUpdateCrossProfileIntentFiltersOnOTA() {
        if (isPresent(5L)) {
            return this.mUpdateCrossProfileIntentFiltersOnOTA;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mUpdateCrossProfileIntentFiltersOnOTA;
        }
        throw new SecurityException(
                "You don't have permission to query updateCrossProfileIntentFiltersOnOTA");
    }

    public void setUpdateCrossProfileIntentFiltersOnOTA(boolean val) {
        this.mUpdateCrossProfileIntentFiltersOnOTA = val;
        setPresent(5L);
    }

    public boolean isMediaSharedWithParent() {
        if (isPresent(8L)) {
            return this.mMediaSharedWithParent;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mMediaSharedWithParent;
        }
        throw new SecurityException("You don't have permission to query mediaSharedWithParent");
    }

    public void setMediaSharedWithParent(boolean val) {
        this.mMediaSharedWithParent = val;
        setPresent(8L);
    }

    public boolean isCredentialShareableWithParent() {
        if (isPresent(9L)) {
            return this.mCredentialShareableWithParent;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mCredentialShareableWithParent;
        }
        throw new SecurityException(
                "You don't have permission to query credentialShareableWithParent");
    }

    public void setCredentialShareableWithParent(boolean val) {
        this.mCredentialShareableWithParent = val;
        setPresent(9L);
    }

    public boolean isAuthAlwaysRequiredToDisableQuietMode() {
        if (isPresent(13L)) {
            return this.mAuthAlwaysRequiredToDisableQuietMode;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mAuthAlwaysRequiredToDisableQuietMode;
        }
        throw new SecurityException(
                "You don't have permission to query authAlwaysRequiredToDisableQuietMode");
    }

    public void setAuthAlwaysRequiredToDisableQuietMode(boolean val) {
        this.mAuthAlwaysRequiredToDisableQuietMode = val;
        setPresent(13L);
    }

    public boolean getAllowStoppingUserWithDelayedLocking() {
        if (isPresent(16L)) {
            return this.mAllowStoppingUserWithDelayedLocking;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mAllowStoppingUserWithDelayedLocking;
        }
        throw new SecurityException(
                "You don't have permission to query allowStoppingUserWithDelayedLocking");
    }

    public void setAllowStoppingUserWithDelayedLocking(boolean val) {
        this.mAllowStoppingUserWithDelayedLocking = val;
        setPresent(16L);
    }

    public int getCrossProfileIntentFilterAccessControl() {
        if (isPresent(6L)) {
            return this.mCrossProfileIntentFilterAccessControl;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mCrossProfileIntentFilterAccessControl;
        }
        throw new SecurityException(
                "You don't have permission to query crossProfileIntentFilterAccessControl");
    }

    public void setCrossProfileIntentFilterAccessControl(int val) {
        this.mCrossProfileIntentFilterAccessControl = val;
        setPresent(6L);
    }

    public int getCrossProfileIntentResolutionStrategy() {
        if (isPresent(7L)) {
            return this.mCrossProfileIntentResolutionStrategy;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mCrossProfileIntentResolutionStrategy;
        }
        throw new SecurityException(
                "You don't have permission to query crossProfileIntentResolutionStrategy");
    }

    public void setCrossProfileIntentResolutionStrategy(int val) {
        this.mCrossProfileIntentResolutionStrategy = val;
        setPresent(7L);
    }

    public int getCrossProfileContentSharingStrategy() {
        if (isPresent(15L)) {
            return this.mCrossProfileContentSharingStrategy;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mCrossProfileContentSharingStrategy;
        }
        throw new SecurityException(
                "You don't have permission to query crossProfileContentSharingStrategy");
    }

    public void setCrossProfileContentSharingStrategy(int val) {
        this.mCrossProfileContentSharingStrategy = val;
        setPresent(15L);
    }

    public int getProfileApiVisibility() {
        if (isPresent(17L)) {
            return this.mProfileApiVisibility;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mProfileApiVisibility;
        }
        throw new SecurityException("You don't have permission to query profileApiVisibility");
    }

    public void setProfileApiVisibility(int profileApiVisibility) {
        this.mProfileApiVisibility = profileApiVisibility;
        setPresent(17L);
    }

    public boolean areItemsRestrictedOnHomeScreen() {
        if (isPresent(18L)) {
            return this.mItemsRestrictedOnHomeScreen;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mItemsRestrictedOnHomeScreen;
        }
        throw new SecurityException(
                "You don't have permission to query mItemsRestrictedOnHomeScreen");
    }

    public void setItemsRestrictedOnHomeScreen(boolean val) {
        this.mItemsRestrictedOnHomeScreen = val;
        setPresent(18L);
    }

    public String toString() {
        return "UserProperties{mPropertiesPresent="
                + Long.toBinaryString(this.mPropertiesPresent)
                + ", mShowInLauncher="
                + getShowInLauncher()
                + ", mStartWithParent="
                + getStartWithParent()
                + ", mShowInSettings="
                + getShowInSettings()
                + ", mInheritDevicePolicy="
                + getInheritDevicePolicy()
                + ", mUseParentsContacts="
                + getUseParentsContacts()
                + ", mUpdateCrossProfileIntentFiltersOnOTA="
                + getUpdateCrossProfileIntentFiltersOnOTA()
                + ", mCrossProfileIntentFilterAccessControl="
                + getCrossProfileIntentFilterAccessControl()
                + ", mCrossProfileIntentResolutionStrategy="
                + getCrossProfileIntentResolutionStrategy()
                + ", mMediaSharedWithParent="
                + isMediaSharedWithParent()
                + ", mCredentialShareableWithParent="
                + isCredentialShareableWithParent()
                + ", mAuthAlwaysRequiredToDisableQuietMode="
                + isAuthAlwaysRequiredToDisableQuietMode()
                + ", mAllowStoppingUserWithDelayedLocking="
                + getAllowStoppingUserWithDelayedLocking()
                + ", mDeleteAppWithParent="
                + getDeleteAppWithParent()
                + ", mAlwaysVisible="
                + getAlwaysVisible()
                + ", mCrossProfileContentSharingStrategy="
                + getCrossProfileContentSharingStrategy()
                + ", mProfileApiVisibility="
                + getProfileApiVisibility()
                + ", mItemsRestrictedOnHomeScreen="
                + areItemsRestrictedOnHomeScreen()
                + "}";
    }

    public void println(PrintWriter pw, String prefix) {
        pw.println(prefix + "UserProperties:");
        pw.println(
                prefix + "    mPropertiesPresent=" + Long.toBinaryString(this.mPropertiesPresent));
        pw.println(prefix + "    mShowInLauncher=" + getShowInLauncher());
        pw.println(prefix + "    mStartWithParent=" + getStartWithParent());
        pw.println(prefix + "    mShowInSettings=" + getShowInSettings());
        pw.println(prefix + "    mInheritDevicePolicy=" + getInheritDevicePolicy());
        pw.println(prefix + "    mUseParentsContacts=" + getUseParentsContacts());
        pw.println(
                prefix
                        + "    mUpdateCrossProfileIntentFiltersOnOTA="
                        + getUpdateCrossProfileIntentFiltersOnOTA());
        pw.println(
                prefix
                        + "    mCrossProfileIntentFilterAccessControl="
                        + getCrossProfileIntentFilterAccessControl());
        pw.println(
                prefix
                        + "    mCrossProfileIntentResolutionStrategy="
                        + getCrossProfileIntentResolutionStrategy());
        pw.println(prefix + "    mMediaSharedWithParent=" + isMediaSharedWithParent());
        pw.println(
                prefix + "    mCredentialShareableWithParent=" + isCredentialShareableWithParent());
        pw.println(
                prefix
                        + "    mAuthAlwaysRequiredToDisableQuietMode="
                        + isAuthAlwaysRequiredToDisableQuietMode());
        pw.println(
                prefix
                        + "    mAllowStoppingUserWithDelayedLocking="
                        + getAllowStoppingUserWithDelayedLocking());
        pw.println(prefix + "    mDeleteAppWithParent=" + getDeleteAppWithParent());
        pw.println(prefix + "    mAlwaysVisible=" + getAlwaysVisible());
        pw.println(
                prefix
                        + "    mCrossProfileContentSharingStrategy="
                        + getCrossProfileContentSharingStrategy());
        pw.println(prefix + "    mProfileApiVisibility=" + getProfileApiVisibility());
        pw.println(prefix + "    mItemsRestrictedOnHomeScreen=" + areItemsRestrictedOnHomeScreen());
    }

    public UserProperties(TypedXmlPullParser parser, UserProperties defaultUserPropertiesReference)
            throws IOException, XmlPullParserException {
        this(defaultUserPropertiesReference);
        updateFromXml(parser);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void updateFromXml(TypedXmlPullParser parser)
            throws IOException, XmlPullParserException {
        char c;
        int attributeCount = parser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = parser.getAttributeName(i);
            switch (attributeName.hashCode()) {
                case -1740726433:
                    if (attributeName.equals(ATTR_CROSS_PROFILE_CONTENT_SHARING_STRATEGY)) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -1612179643:
                    if (attributeName.equals(ATTR_SHOW_IN_SETTINGS)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1572579485:
                    if (attributeName.equals(ATTR_ALWAYS_VISIBLE)) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -1093353053:
                    if (attributeName.equals(ATTR_PROFILE_API_VISIBILITY)) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case -1041116634:
                    if (attributeName.equals(ATTR_DELETE_APP_WITH_PARENT)) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -934956400:
                    if (attributeName.equals(ATTR_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -842277572:
                    if (attributeName.equals(ATTR_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -627117223:
                    if (attributeName.equals(ATTR_MEDIA_SHARED_WITH_PARENT)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -552376349:
                    if (attributeName.equals(ATTR_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -317094126:
                    if (attributeName.equals(ATTR_START_WITH_PARENT)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -159094078:
                    if (attributeName.equals(ATTR_SHOW_IN_LAUNCHER)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 428661202:
                    if (attributeName.equals(ATTR_CREDENTIAL_SHAREABLE_WITH_PARENT)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 490625987:
                    if (attributeName.equals(ATTR_INHERIT_DEVICE_POLICY)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 585305077:
                    if (attributeName.equals(ATTR_USE_PARENTS_CONTACTS)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 931602880:
                    if (attributeName.equals(ATTR_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1102355712:
                    if (attributeName.equals(ATTR_SHOW_IN_SHARING_SURFACES)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1298238789:
                    if (attributeName.equals(ITEMS_RESTRICTED_ON_HOME_SCREEN)) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 1404281621:
                    if (attributeName.equals(ATTR_SHOW_IN_QUIET_MODE)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2082796132:
                    if (attributeName.equals(ATTR_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    setShowInLauncher(parser.getAttributeInt(i));
                    break;
                case 1:
                    setStartWithParent(parser.getAttributeBoolean(i));
                    break;
                case 2:
                    setShowInSettings(parser.getAttributeInt(i));
                    break;
                case 3:
                    setShowInQuietMode(parser.getAttributeInt(i));
                    break;
                case 4:
                    setShowInSharingSurfaces(parser.getAttributeInt(i));
                    break;
                case 5:
                    setInheritDevicePolicy(parser.getAttributeInt(i));
                    break;
                case 6:
                    setUseParentsContacts(parser.getAttributeBoolean(i));
                    break;
                case 7:
                    setUpdateCrossProfileIntentFiltersOnOTA(parser.getAttributeBoolean(i));
                    break;
                case '\b':
                    setCrossProfileIntentFilterAccessControl(parser.getAttributeInt(i));
                    break;
                case '\t':
                    setCrossProfileIntentResolutionStrategy(parser.getAttributeInt(i));
                    break;
                case '\n':
                    setMediaSharedWithParent(parser.getAttributeBoolean(i));
                    break;
                case 11:
                    setCredentialShareableWithParent(parser.getAttributeBoolean(i));
                    break;
                case '\f':
                    setAuthAlwaysRequiredToDisableQuietMode(parser.getAttributeBoolean(i));
                    break;
                case '\r':
                    setAllowStoppingUserWithDelayedLocking(parser.getAttributeBoolean(i));
                    break;
                case 14:
                    setDeleteAppWithParent(parser.getAttributeBoolean(i));
                    break;
                case 15:
                    setAlwaysVisible(parser.getAttributeBoolean(i));
                    break;
                case 16:
                    setCrossProfileContentSharingStrategy(parser.getAttributeInt(i));
                    break;
                case 17:
                    setProfileApiVisibility(parser.getAttributeInt(i));
                    break;
                case 18:
                    setItemsRestrictedOnHomeScreen(parser.getAttributeBoolean(i));
                    break;
                default:
                    Slog.w(LOG_TAG, "Skipping unknown property " + attributeName);
                    break;
            }
        }
    }

    public void writeToXml(TypedXmlSerializer serializer)
            throws IOException, XmlPullParserException {
        if (isPresent(0L)) {
            serializer.attributeInt(null, ATTR_SHOW_IN_LAUNCHER, this.mShowInLauncher);
        }
        if (isPresent(1L)) {
            serializer.attributeBoolean(null, ATTR_START_WITH_PARENT, this.mStartWithParent);
        }
        if (isPresent(2L)) {
            serializer.attributeInt(null, ATTR_SHOW_IN_SETTINGS, this.mShowInSettings);
        }
        if (isPresent(12L)) {
            serializer.attributeInt(null, ATTR_SHOW_IN_QUIET_MODE, this.mShowInQuietMode);
        }
        if (isPresent(14L)) {
            serializer.attributeInt(
                    null, ATTR_SHOW_IN_SHARING_SURFACES, this.mShowInSharingSurfaces);
        }
        if (isPresent(3L)) {
            serializer.attributeInt(null, ATTR_INHERIT_DEVICE_POLICY, this.mInheritDevicePolicy);
        }
        if (isPresent(4L)) {
            serializer.attributeBoolean(null, ATTR_USE_PARENTS_CONTACTS, this.mUseParentsContacts);
        }
        if (isPresent(5L)) {
            serializer.attributeBoolean(
                    null,
                    ATTR_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA,
                    this.mUpdateCrossProfileIntentFiltersOnOTA);
        }
        if (isPresent(6L)) {
            serializer.attributeInt(
                    null,
                    ATTR_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL,
                    this.mCrossProfileIntentFilterAccessControl);
        }
        if (isPresent(7L)) {
            serializer.attributeInt(
                    null,
                    ATTR_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY,
                    this.mCrossProfileIntentResolutionStrategy);
        }
        if (isPresent(8L)) {
            serializer.attributeBoolean(
                    null, ATTR_MEDIA_SHARED_WITH_PARENT, this.mMediaSharedWithParent);
        }
        if (isPresent(9L)) {
            serializer.attributeBoolean(
                    null,
                    ATTR_CREDENTIAL_SHAREABLE_WITH_PARENT,
                    this.mCredentialShareableWithParent);
        }
        if (isPresent(13L)) {
            serializer.attributeBoolean(
                    null,
                    ATTR_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE,
                    this.mAuthAlwaysRequiredToDisableQuietMode);
        }
        if (isPresent(16L)) {
            serializer.attributeBoolean(
                    null,
                    ATTR_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING,
                    this.mAllowStoppingUserWithDelayedLocking);
        }
        if (isPresent(10L)) {
            serializer.attributeBoolean(
                    null, ATTR_DELETE_APP_WITH_PARENT, this.mDeleteAppWithParent);
        }
        if (isPresent(11L)) {
            serializer.attributeBoolean(null, ATTR_ALWAYS_VISIBLE, this.mAlwaysVisible);
        }
        if (isPresent(15L)) {
            serializer.attributeInt(
                    null,
                    ATTR_CROSS_PROFILE_CONTENT_SHARING_STRATEGY,
                    this.mCrossProfileContentSharingStrategy);
        }
        if (isPresent(17L)) {
            serializer.attributeInt(null, ATTR_PROFILE_API_VISIBILITY, this.mProfileApiVisibility);
        }
        if (isPresent(18L)) {
            serializer.attributeBoolean(
                    null, ITEMS_RESTRICTED_ON_HOME_SCREEN, this.mItemsRestrictedOnHomeScreen);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        dest.writeLong(this.mPropertiesPresent);
        dest.writeInt(this.mShowInLauncher);
        dest.writeBoolean(this.mStartWithParent);
        dest.writeInt(this.mShowInSettings);
        dest.writeInt(this.mShowInQuietMode);
        dest.writeInt(this.mShowInSharingSurfaces);
        dest.writeInt(this.mInheritDevicePolicy);
        dest.writeBoolean(this.mUseParentsContacts);
        dest.writeBoolean(this.mUpdateCrossProfileIntentFiltersOnOTA);
        dest.writeInt(this.mCrossProfileIntentFilterAccessControl);
        dest.writeInt(this.mCrossProfileIntentResolutionStrategy);
        dest.writeBoolean(this.mMediaSharedWithParent);
        dest.writeBoolean(this.mCredentialShareableWithParent);
        dest.writeBoolean(this.mAuthAlwaysRequiredToDisableQuietMode);
        dest.writeBoolean(this.mAllowStoppingUserWithDelayedLocking);
        dest.writeBoolean(this.mDeleteAppWithParent);
        dest.writeBoolean(this.mAlwaysVisible);
        dest.writeInt(this.mCrossProfileContentSharingStrategy);
        dest.writeInt(this.mProfileApiVisibility);
        dest.writeBoolean(this.mItemsRestrictedOnHomeScreen);
    }

    private UserProperties(Parcel source) {
        this.mPropertiesPresent = 0L;
        this.mDefaultProperties = null;
        this.mPropertiesPresent = source.readLong();
        this.mShowInLauncher = source.readInt();
        this.mStartWithParent = source.readBoolean();
        this.mShowInSettings = source.readInt();
        this.mShowInQuietMode = source.readInt();
        this.mShowInSharingSurfaces = source.readInt();
        this.mInheritDevicePolicy = source.readInt();
        this.mUseParentsContacts = source.readBoolean();
        this.mUpdateCrossProfileIntentFiltersOnOTA = source.readBoolean();
        this.mCrossProfileIntentFilterAccessControl = source.readInt();
        this.mCrossProfileIntentResolutionStrategy = source.readInt();
        this.mMediaSharedWithParent = source.readBoolean();
        this.mCredentialShareableWithParent = source.readBoolean();
        this.mAuthAlwaysRequiredToDisableQuietMode = source.readBoolean();
        this.mAllowStoppingUserWithDelayedLocking = source.readBoolean();
        this.mDeleteAppWithParent = source.readBoolean();
        this.mAlwaysVisible = source.readBoolean();
        this.mCrossProfileContentSharingStrategy = source.readInt();
        this.mProfileApiVisibility = source.readInt();
        this.mItemsRestrictedOnHomeScreen = source.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

        public Builder setShowInLauncher(int showInLauncher) {
            this.mShowInLauncher = showInLauncher;
            return this;
        }

        public Builder setStartWithParent(boolean startWithParent) {
            this.mStartWithParent = startWithParent;
            return this;
        }

        public Builder setShowInSettings(int showInSettings) {
            this.mShowInSettings = showInSettings;
            return this;
        }

        public Builder setShowInQuietMode(int showInQuietMode) {
            this.mShowInQuietMode = showInQuietMode;
            return this;
        }

        public Builder setShowInSharingSurfaces(int showInSharingSurfaces) {
            this.mShowInSharingSurfaces = showInSharingSurfaces;
            return this;
        }

        public Builder setInheritDevicePolicy(int inheritRestrictionsDevicePolicy) {
            this.mInheritDevicePolicy = inheritRestrictionsDevicePolicy;
            return this;
        }

        public Builder setUseParentsContacts(boolean useParentsContacts) {
            this.mUseParentsContacts = useParentsContacts;
            return this;
        }

        public Builder setUpdateCrossProfileIntentFiltersOnOTA(
                boolean updateCrossProfileIntentFiltersOnOTA) {
            this.mUpdateCrossProfileIntentFiltersOnOTA = updateCrossProfileIntentFiltersOnOTA;
            return this;
        }

        public Builder setCrossProfileIntentFilterAccessControl(
                int crossProfileIntentFilterAccessControl) {
            this.mCrossProfileIntentFilterAccessControl = crossProfileIntentFilterAccessControl;
            return this;
        }

        public Builder setCrossProfileIntentResolutionStrategy(
                int crossProfileIntentResolutionStrategy) {
            this.mCrossProfileIntentResolutionStrategy = crossProfileIntentResolutionStrategy;
            return this;
        }

        public Builder setMediaSharedWithParent(boolean mediaSharedWithParent) {
            this.mMediaSharedWithParent = mediaSharedWithParent;
            return this;
        }

        public Builder setCredentialShareableWithParent(boolean credentialShareableWithParent) {
            this.mCredentialShareableWithParent = credentialShareableWithParent;
            return this;
        }

        public Builder setAuthAlwaysRequiredToDisableQuietMode(
                boolean authAlwaysRequiredToDisableQuietMode) {
            this.mAuthAlwaysRequiredToDisableQuietMode = authAlwaysRequiredToDisableQuietMode;
            return this;
        }

        public Builder setAllowStoppingUserWithDelayedLocking(
                boolean allowStoppingUserWithDelayedLocking) {
            this.mAllowStoppingUserWithDelayedLocking = allowStoppingUserWithDelayedLocking;
            return this;
        }

        public Builder setDeleteAppWithParent(boolean deleteAppWithParent) {
            this.mDeleteAppWithParent = deleteAppWithParent;
            return this;
        }

        public Builder setAlwaysVisible(boolean alwaysVisible) {
            this.mAlwaysVisible = alwaysVisible;
            return this;
        }

        public Builder setCrossProfileContentSharingStrategy(
                int crossProfileContentSharingStrategy) {
            this.mCrossProfileContentSharingStrategy = crossProfileContentSharingStrategy;
            return this;
        }

        public Builder setProfileApiVisibility(int profileApiVisibility) {
            this.mProfileApiVisibility = profileApiVisibility;
            return this;
        }

        public Builder setItemsRestrictedOnHomeScreen(boolean itemsRestrictedOnHomeScreen) {
            this.mItemsRestrictedOnHomeScreen = itemsRestrictedOnHomeScreen;
            return this;
        }

        public UserProperties build() {
            return new UserProperties(
                    this.mShowInLauncher,
                    this.mStartWithParent,
                    this.mShowInSettings,
                    this.mShowInQuietMode,
                    this.mShowInSharingSurfaces,
                    this.mInheritDevicePolicy,
                    this.mUseParentsContacts,
                    this.mUpdateCrossProfileIntentFiltersOnOTA,
                    this.mCrossProfileIntentFilterAccessControl,
                    this.mCrossProfileIntentResolutionStrategy,
                    this.mMediaSharedWithParent,
                    this.mCredentialShareableWithParent,
                    this.mAuthAlwaysRequiredToDisableQuietMode,
                    this.mAllowStoppingUserWithDelayedLocking,
                    this.mDeleteAppWithParent,
                    this.mAlwaysVisible,
                    this.mCrossProfileContentSharingStrategy,
                    this.mProfileApiVisibility,
                    this.mItemsRestrictedOnHomeScreen);
        }
    }

    private UserProperties(
            int showInLauncher,
            boolean startWithParent,
            int showInSettings,
            int showInQuietMode,
            int showInSharingSurfaces,
            int inheritDevicePolicy,
            boolean useParentsContacts,
            boolean updateCrossProfileIntentFiltersOnOTA,
            int crossProfileIntentFilterAccessControl,
            int crossProfileIntentResolutionStrategy,
            boolean mediaSharedWithParent,
            boolean credentialShareableWithParent,
            boolean authAlwaysRequiredToDisableQuietMode,
            boolean allowStoppingUserWithDelayedLocking,
            boolean deleteAppWithParent,
            boolean alwaysVisible,
            int crossProfileContentSharingStrategy,
            int profileApiVisibility,
            boolean itemsRestrictedOnHomeScreen) {
        this.mPropertiesPresent = 0L;
        this.mDefaultProperties = null;
        setShowInLauncher(showInLauncher);
        setStartWithParent(startWithParent);
        setShowInSettings(showInSettings);
        setShowInQuietMode(showInQuietMode);
        setShowInSharingSurfaces(showInSharingSurfaces);
        setInheritDevicePolicy(inheritDevicePolicy);
        setUseParentsContacts(useParentsContacts);
        setUpdateCrossProfileIntentFiltersOnOTA(updateCrossProfileIntentFiltersOnOTA);
        setCrossProfileIntentFilterAccessControl(crossProfileIntentFilterAccessControl);
        setCrossProfileIntentResolutionStrategy(crossProfileIntentResolutionStrategy);
        setMediaSharedWithParent(mediaSharedWithParent);
        setCredentialShareableWithParent(credentialShareableWithParent);
        setAuthAlwaysRequiredToDisableQuietMode(authAlwaysRequiredToDisableQuietMode);
        setAllowStoppingUserWithDelayedLocking(allowStoppingUserWithDelayedLocking);
        setDeleteAppWithParent(deleteAppWithParent);
        setAlwaysVisible(alwaysVisible);
        setCrossProfileContentSharingStrategy(crossProfileContentSharingStrategy);
        setProfileApiVisibility(profileApiVisibility);
        setItemsRestrictedOnHomeScreen(itemsRestrictedOnHomeScreen);
    }
}
