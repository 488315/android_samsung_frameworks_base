package android.content.pm;

import android.annotation.SystemApi;
import android.app.Person;
import android.app.appsearch.GenericDocument;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.content.pm.CapabilityParams;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public final class ShortcutInfo implements Parcelable {
    private static final String ANDROID_PACKAGE_NAME = "android";
    public static final int CLONE_REMOVE_FOR_APP_PREDICTION = 9;
    public static final int CLONE_REMOVE_FOR_CREATOR = 9;
    public static final int CLONE_REMOVE_FOR_LAUNCHER = 27;
    public static final int CLONE_REMOVE_FOR_LAUNCHER_APPROVAL = 26;
    private static final int CLONE_REMOVE_ICON = 1;
    private static final int CLONE_REMOVE_INTENT = 2;
    public static final int CLONE_REMOVE_NON_KEY_INFO = 4;
    public static final int CLONE_REMOVE_PERSON = 16;
    public static final int CLONE_REMOVE_RES_NAMES = 8;
    public static final Parcelable.Creator<ShortcutInfo> CREATOR = new Parcelable.Creator<ShortcutInfo>() { // from class: android.content.pm.ShortcutInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShortcutInfo createFromParcel(Parcel parcel) {
            return new ShortcutInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShortcutInfo[] newArray(int i) {
            return new ShortcutInfo[i];
        }
    };
    public static final int DISABLED_REASON_APP_CHANGED = 2;
    public static final int DISABLED_REASON_BACKUP_NOT_SUPPORTED = 101;
    public static final int DISABLED_REASON_BY_APP = 1;
    public static final int DISABLED_REASON_NOT_DISABLED = 0;
    public static final int DISABLED_REASON_OTHER_RESTORE_ISSUE = 103;
    private static final int DISABLED_REASON_RESTORE_ISSUE_START = 100;
    public static final int DISABLED_REASON_SIGNATURE_MISMATCH = 102;
    public static final int DISABLED_REASON_UNKNOWN = 3;
    public static final int DISABLED_REASON_VERSION_LOWER = 100;
    public static final int FLAG_ADAPTIVE_BITMAP = 512;
    public static final int FLAG_CACHED_ALL = 1610629120;
    public static final int FLAG_CACHED_BUBBLES = 1073741824;
    public static final int FLAG_CACHED_NOTIFICATIONS = 16384;
    public static final int FLAG_CACHED_PEOPLE_TILE = 536870912;
    public static final int FLAG_DISABLED = 64;
    public static final int FLAG_DYNAMIC = 1;
    public static final int FLAG_HAS_ICON_FILE = 8;
    public static final int FLAG_HAS_ICON_RES = 4;
    public static final int FLAG_HAS_ICON_URI = 32768;
    public static final int FLAG_ICON_FILE_PENDING_SAVE = 2048;
    public static final int FLAG_IMMUTABLE = 256;
    public static final int FLAG_KEY_FIELDS_ONLY = 16;
    public static final int FLAG_LONG_LIVED = 8192;
    public static final int FLAG_MANIFEST = 32;
    public static final int FLAG_PINNED = 2;
    public static final int FLAG_RETURNED_BY_SERVICE = 1024;
    public static final int FLAG_SHADOW = 4096;
    public static final int FLAG_STRINGS_RESOLVED = 128;
    private static final int IMPLICIT_RANK_MASK = Integer.MAX_VALUE;
    public static final int MAX_ID_LENGTH = 1000;
    public static final int RANK_CHANGED_BIT = Integer.MIN_VALUE;
    public static final int RANK_NOT_SET = Integer.MAX_VALUE;
    private static final String RES_TYPE_STRING = "string";
    public static final String SHORTCUT_CATEGORY_CONVERSATION = "android.shortcut.conversation";
    public static final int SURFACE_LAUNCHER = 1;
    static final String TAG = "Shortcut";
    public static final int VERSION_CODE_UNKNOWN = -1;
    private ComponentName mActivity;
    private String mBitmapPath;
    private Map<String, Map<String, List<String>>> mCapabilityBindings;
    private ArraySet<String> mCategories;
    private CharSequence mDisabledMessage;
    private int mDisabledMessageResId;
    private String mDisabledMessageResName;
    private int mDisabledReason;
    private int mExcludedSurfaces;
    private PersistableBundle mExtras;
    private int mFlags;
    private Icon mIcon;
    private int mIconResId;
    private String mIconResName;
    private String mIconUri;
    private final String mId;
    private int mImplicitRank;
    private PersistableBundle[] mIntentPersistableExtrases;
    private Intent[] mIntents;
    private long mLastChangedTimestamp;
    private LocusId mLocusId;
    private final String mPackageName;
    private Person[] mPersons;
    private int mRank;
    private String mStartingThemeResName;
    private CharSequence mText;
    private int mTextResId;
    private String mTextResName;
    private CharSequence mTitle;
    private int mTitleResId;
    private String mTitleResName;
    private final int mUserId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CloneFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisabledReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShortcutFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Surface {
    }

    public static boolean isDisabledForRestoreIssue(int i) {
        return i >= 100;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String getDisabledReasonDebugString(int i) {
        if (i == 0) {
            return "[Not disabled]";
        }
        if (i == 1) {
            return "[Disabled: by app]";
        }
        if (i == 2) {
            return "[Disabled: app changed]";
        }
        switch (i) {
            case 100:
                return "[Disabled: lower version]";
            case 101:
                return "[Disabled: backup not supported]";
            case 102:
                return "[Disabled: signature mismatch]";
            case 103:
                return "[Disabled: unknown restore issue]";
            default:
                return "[Disabled: unknown reason:" + i + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static String getDisabledReasonForRestoreIssue(Context context, int i) {
        Resources resources = context.getResources();
        if (i != 3) {
            switch (i) {
                case 100:
                    return resources.getString(R.string.shortcut_restored_on_lower_version);
                case 101:
                    return resources.getString(R.string.shortcut_restore_not_supported);
                case 102:
                    return resources.getString(R.string.shortcut_restore_signature_mismatch);
                case 103:
                    return resources.getString(R.string.shortcut_restore_unknown_issue);
                default:
                    return null;
            }
        }
        return resources.getString(R.string.shortcut_disabled_reason_unknown);
    }

    private ShortcutInfo(Builder builder) {
        this.mUserId = builder.mContext.getUserId();
        this.mId = getSafeId((String) Preconditions.checkStringNotEmpty(builder.mId, "Shortcut ID must be provided"));
        this.mPackageName = builder.mContext.getPackageName();
        this.mActivity = builder.mActivity;
        this.mIcon = builder.mIcon;
        this.mTitle = builder.mTitle;
        this.mTitleResId = builder.mTitleResId;
        this.mText = builder.mText;
        this.mTextResId = builder.mTextResId;
        this.mDisabledMessage = builder.mDisabledMessage;
        this.mDisabledMessageResId = builder.mDisabledMessageResId;
        this.mCategories = cloneCategories(builder.mCategories);
        this.mIntents = cloneIntents(builder.mIntents);
        fixUpIntentExtras();
        this.mPersons = clonePersons(builder.mPersons);
        if (builder.mIsLongLived) {
            setLongLived();
        }
        this.mExcludedSurfaces = builder.mExcludedSurfaces;
        this.mRank = builder.mRank;
        this.mExtras = builder.mExtras;
        this.mLocusId = builder.mLocusId;
        this.mCapabilityBindings = cloneCapabilityBindings(builder.mCapabilityBindings);
        this.mStartingThemeResName = builder.mStartingThemeResId != 0 ? builder.mContext.getResources().getResourceName(builder.mStartingThemeResId) : null;
        updateTimestamp();
    }

    private void fixUpIntentExtras() {
        Intent[] intentArr = this.mIntents;
        if (intentArr == null) {
            this.mIntentPersistableExtrases = null;
            return;
        }
        this.mIntentPersistableExtrases = new PersistableBundle[intentArr.length];
        int i = 0;
        while (true) {
            Intent[] intentArr2 = this.mIntents;
            if (i >= intentArr2.length) {
                return;
            }
            Intent intent = intentArr2[i];
            Bundle extras = intent.getExtras();
            if (extras == null) {
                this.mIntentPersistableExtrases[i] = null;
            } else {
                this.mIntentPersistableExtrases[i] = new PersistableBundle(extras);
                intent.replaceExtras((Bundle) null);
            }
            i++;
        }
    }

    private static ArraySet<String> cloneCategories(Set<String> set) {
        if (set == null) {
            return null;
        }
        ArraySet<String> arraySet = new ArraySet<>(set.size());
        for (String str : set) {
            if (!TextUtils.isEmpty(str)) {
                arraySet.add(str.toString().intern());
            }
        }
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Intent[] cloneIntents(Intent[] intentArr) {
        if (intentArr == null) {
            return null;
        }
        int length = intentArr.length;
        Intent[] intentArr2 = new Intent[length];
        for (int i = 0; i < length; i++) {
            if (intentArr[i] != null) {
                intentArr2[i] = new Intent(intentArr[i]);
            }
        }
        return intentArr2;
    }

    private static PersistableBundle[] clonePersistableBundle(PersistableBundle[] persistableBundleArr) {
        if (persistableBundleArr == null) {
            return null;
        }
        int length = persistableBundleArr.length;
        PersistableBundle[] persistableBundleArr2 = new PersistableBundle[length];
        for (int i = 0; i < length; i++) {
            if (persistableBundleArr[i] != null) {
                persistableBundleArr2[i] = new PersistableBundle(persistableBundleArr[i]);
            }
        }
        return persistableBundleArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Person[] clonePersons(Person[] personArr) {
        if (personArr == null) {
            return null;
        }
        int length = personArr.length;
        Person[] personArr2 = new Person[length];
        for (int i = 0; i < length; i++) {
            Person person = personArr[i];
            if (person != null) {
                personArr2[i] = person.toBuilder().setIcon(null).build();
            }
        }
        return personArr2;
    }

    private static String getSafeId(String str) {
        return str.length() > 1000 ? str.substring(0, 1000) : str;
    }

    public void enforceMandatoryFields(boolean z) {
        Preconditions.checkStringNotEmpty(this.mId, "Shortcut ID must be provided");
        if (!z) {
            Objects.requireNonNull(this.mActivity, "Activity must be provided");
        }
        if (this.mTitle == null && this.mTitleResId == 0) {
            throw new IllegalArgumentException("Short label must be provided");
        }
        Objects.requireNonNull(this.mIntents, "Shortcut Intent must be provided");
        Preconditions.checkArgument(this.mIntents.length > 0, "Shortcut Intent must be provided");
    }

    private ShortcutInfo(ShortcutInfo shortcutInfo, int i) {
        this.mUserId = shortcutInfo.mUserId;
        this.mId = shortcutInfo.mId;
        this.mPackageName = shortcutInfo.mPackageName;
        this.mActivity = shortcutInfo.mActivity;
        int i2 = shortcutInfo.mFlags;
        this.mFlags = i2;
        this.mLastChangedTimestamp = shortcutInfo.mLastChangedTimestamp;
        this.mDisabledReason = shortcutInfo.mDisabledReason;
        this.mLocusId = shortcutInfo.mLocusId;
        this.mExcludedSurfaces = shortcutInfo.mExcludedSurfaces;
        this.mIconResId = shortcutInfo.mIconResId;
        if ((i & 4) == 0) {
            if ((i & 1) == 0) {
                this.mIcon = shortcutInfo.mIcon;
                this.mBitmapPath = shortcutInfo.mBitmapPath;
                this.mIconUri = shortcutInfo.mIconUri;
            }
            this.mTitle = shortcutInfo.mTitle;
            this.mTitleResId = shortcutInfo.mTitleResId;
            this.mText = shortcutInfo.mText;
            this.mTextResId = shortcutInfo.mTextResId;
            this.mDisabledMessage = shortcutInfo.mDisabledMessage;
            this.mDisabledMessageResId = shortcutInfo.mDisabledMessageResId;
            this.mCategories = cloneCategories(shortcutInfo.mCategories);
            if ((i & 16) == 0) {
                this.mPersons = clonePersons(shortcutInfo.mPersons);
            }
            if ((i & 2) == 0) {
                this.mIntents = cloneIntents(shortcutInfo.mIntents);
                this.mIntentPersistableExtrases = clonePersistableBundle(shortcutInfo.mIntentPersistableExtrases);
            }
            this.mRank = shortcutInfo.mRank;
            this.mExtras = shortcutInfo.mExtras;
            if ((i & 8) == 0) {
                this.mTitleResName = shortcutInfo.mTitleResName;
                this.mTextResName = shortcutInfo.mTextResName;
                this.mDisabledMessageResName = shortcutInfo.mDisabledMessageResName;
                this.mIconResName = shortcutInfo.mIconResName;
            }
        } else {
            this.mFlags = i2 | 16;
        }
        this.mCapabilityBindings = cloneCapabilityBindings(shortcutInfo.mCapabilityBindings);
        this.mStartingThemeResName = shortcutInfo.mStartingThemeResName;
    }

    public static ShortcutInfo createFromGenericDocument(Context context, GenericDocument genericDocument) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(genericDocument);
        return createFromGenericDocument(context.getUserId(), genericDocument);
    }

    public static ShortcutInfo createFromGenericDocument(int i, GenericDocument genericDocument) {
        return new AppSearchShortcutInfo(genericDocument).toShortcutInfo(i);
    }

    private CharSequence getResourceString(Resources resources, int i, CharSequence charSequence) {
        try {
            return resources.getString(i);
        } catch (Resources.NotFoundException unused) {
            Log.e("Shortcut", "Resource for ID=" + i + " not found in package " + this.mPackageName);
            return charSequence;
        }
    }

    public void resolveResourceStrings(Resources resources) {
        this.mFlags |= 128;
        int i = this.mTitleResId;
        if (i == 0 && this.mTextResId == 0 && this.mDisabledMessageResId == 0) {
            return;
        }
        if (i != 0) {
            this.mTitle = getResourceString(resources, i, this.mTitle);
        }
        int i2 = this.mTextResId;
        if (i2 != 0) {
            this.mText = getResourceString(resources, i2, this.mText);
        }
        int i3 = this.mDisabledMessageResId;
        if (i3 != 0) {
            this.mDisabledMessage = getResourceString(resources, i3, this.mDisabledMessage);
        }
    }

    public static String lookUpResourceName(Resources resources, int i, boolean z, String str) {
        if (i == 0) {
            return null;
        }
        try {
            String resourceName = resources.getResourceName(i);
            if ("android".equals(getResourcePackageName(resourceName))) {
                return String.valueOf(i);
            }
            if (z) {
                return getResourceTypeAndEntryName(resourceName);
            }
            return getResourceEntryName(resourceName);
        } catch (Resources.NotFoundException unused) {
            Log.e("Shortcut", "Resource name for ID=" + i + " not found in package " + str + ". Resource IDs may change when the application is upgraded, and the system may not be able to find the correct resource.");
            return null;
        }
    }

    public static String getResourcePackageName(String str) {
        int iIndexOf = str.indexOf(58);
        if (iIndexOf < 0) {
            return null;
        }
        return str.substring(0, iIndexOf);
    }

    public static String getResourceTypeName(String str) {
        int i;
        int iIndexOf;
        int iIndexOf2 = str.indexOf(58);
        if (iIndexOf2 >= 0 && (iIndexOf = str.indexOf(47, (i = iIndexOf2 + 1))) >= 0) {
            return str.substring(i, iIndexOf);
        }
        return null;
    }

    public static String getResourceTypeAndEntryName(String str) {
        int iIndexOf = str.indexOf(58);
        if (iIndexOf < 0) {
            return null;
        }
        return str.substring(iIndexOf + 1);
    }

    public static String getResourceEntryName(String str) {
        int iIndexOf = str.indexOf(47);
        if (iIndexOf < 0) {
            return null;
        }
        return str.substring(iIndexOf + 1);
    }

    public static int lookUpResourceId(Resources resources, String str, String str2, String str3) {
        try {
            if (str == null) {
                return 0;
            }
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                return resources.getIdentifier(str, str2, str3);
            }
        } catch (Resources.NotFoundException unused2) {
            Log.e("Shortcut", "Resource ID for name=" + str + " not found in package " + str3);
            return 0;
        }
    }

    public void lookupAndFillInResourceNames(Resources resources) {
        int i = this.mTitleResId;
        if (i == 0 && this.mTextResId == 0 && this.mDisabledMessageResId == 0 && this.mIconResId == 0) {
            return;
        }
        this.mTitleResName = lookUpResourceName(resources, i, false, this.mPackageName);
        this.mTextResName = lookUpResourceName(resources, this.mTextResId, false, this.mPackageName);
        this.mDisabledMessageResName = lookUpResourceName(resources, this.mDisabledMessageResId, false, this.mPackageName);
        this.mIconResName = lookUpResourceName(resources, this.mIconResId, true, this.mPackageName);
    }

    public void lookupAndFillInResourceIds(Resources resources) {
        String str = this.mTitleResName;
        if (str == null && this.mTextResName == null && this.mDisabledMessageResName == null && this.mIconResName == null) {
            return;
        }
        this.mTitleResId = lookUpResourceId(resources, str, RES_TYPE_STRING, this.mPackageName);
        this.mTextResId = lookUpResourceId(resources, this.mTextResName, RES_TYPE_STRING, this.mPackageName);
        this.mDisabledMessageResId = lookUpResourceId(resources, this.mDisabledMessageResName, RES_TYPE_STRING, this.mPackageName);
        this.mIconResId = lookUpResourceId(resources, this.mIconResName, null, this.mPackageName);
    }

    public ShortcutInfo clone(int i) {
        return new ShortcutInfo(this, i);
    }

    public void ensureUpdatableWith(ShortcutInfo shortcutInfo, boolean z) {
        if (z) {
            Preconditions.checkState(isVisibleToPublisher(), "[Framework BUG] Invisible shortcuts can't be updated");
        }
        Preconditions.checkState(this.mUserId == shortcutInfo.mUserId, "Owner User ID must match");
        Preconditions.checkState(this.mId.equals(shortcutInfo.mId), "ID must match");
        Preconditions.checkState(this.mPackageName.equals(shortcutInfo.mPackageName), "Package name must match");
        if (isVisibleToPublisher()) {
            Preconditions.checkState(!isImmutable(), "Target ShortcutInfo is immutable");
        }
    }

    public void copyNonNullFieldsFrom(ShortcutInfo shortcutInfo) {
        ensureUpdatableWith(shortcutInfo, true);
        ComponentName componentName = shortcutInfo.mActivity;
        if (componentName != null) {
            this.mActivity = componentName;
        }
        Icon icon = shortcutInfo.mIcon;
        if (icon != null) {
            this.mIcon = icon;
            this.mIconResId = 0;
            this.mIconResName = null;
            this.mBitmapPath = null;
            this.mIconUri = null;
        }
        CharSequence charSequence = shortcutInfo.mTitle;
        if (charSequence != null) {
            this.mTitle = charSequence;
            this.mTitleResId = 0;
            this.mTitleResName = null;
        } else {
            int i = shortcutInfo.mTitleResId;
            if (i != 0) {
                this.mTitle = null;
                this.mTitleResId = i;
                this.mTitleResName = null;
            }
        }
        CharSequence charSequence2 = shortcutInfo.mText;
        if (charSequence2 != null) {
            this.mText = charSequence2;
            this.mTextResId = 0;
            this.mTextResName = null;
        } else {
            int i2 = shortcutInfo.mTextResId;
            if (i2 != 0) {
                this.mText = null;
                this.mTextResId = i2;
                this.mTextResName = null;
            }
        }
        CharSequence charSequence3 = shortcutInfo.mDisabledMessage;
        if (charSequence3 != null) {
            this.mDisabledMessage = charSequence3;
            this.mDisabledMessageResId = 0;
            this.mDisabledMessageResName = null;
        } else {
            int i3 = shortcutInfo.mDisabledMessageResId;
            if (i3 != 0) {
                this.mDisabledMessage = null;
                this.mDisabledMessageResId = i3;
                this.mDisabledMessageResName = null;
            }
        }
        ArraySet<String> arraySet = shortcutInfo.mCategories;
        if (arraySet != null) {
            this.mCategories = cloneCategories(arraySet);
        }
        Person[] personArr = shortcutInfo.mPersons;
        if (personArr != null) {
            this.mPersons = clonePersons(personArr);
        }
        Intent[] intentArr = shortcutInfo.mIntents;
        if (intentArr != null) {
            this.mIntents = cloneIntents(intentArr);
            this.mIntentPersistableExtrases = clonePersistableBundle(shortcutInfo.mIntentPersistableExtrases);
        }
        int i4 = shortcutInfo.mRank;
        if (i4 != Integer.MAX_VALUE) {
            this.mRank = i4;
        }
        PersistableBundle persistableBundle = shortcutInfo.mExtras;
        if (persistableBundle != null) {
            this.mExtras = persistableBundle;
        }
        LocusId locusId = shortcutInfo.mLocusId;
        if (locusId != null) {
            this.mLocusId = locusId;
        }
        String str = shortcutInfo.mStartingThemeResName;
        if (str != null && !str.isEmpty()) {
            this.mStartingThemeResName = shortcutInfo.mStartingThemeResName;
        }
        Map<String, Map<String, List<String>>> map = shortcutInfo.mCapabilityBindings;
        if (map != null) {
            this.mCapabilityBindings = cloneCapabilityBindings(map);
        }
    }

    public static Icon validateIcon(Icon icon) {
        int type = icon.getType();
        if (type != 1 && type != 2 && type != 4 && type != 5 && type != 6) {
            throw getInvalidIconException();
        }
        if (icon.hasTint()) {
            throw new IllegalArgumentException("Icons with tints are not supported");
        }
        return icon;
    }

    public static IllegalArgumentException getInvalidIconException() {
        return new IllegalArgumentException("Unsupported icon type: only the bitmap and resource types are supported");
    }

    public static class Builder {
        private ComponentName mActivity;
        private Map<String, Map<String, List<String>>> mCapabilityBindings;
        private Set<String> mCategories;
        private final Context mContext;
        private CharSequence mDisabledMessage;
        private int mDisabledMessageResId;
        private int mExcludedSurfaces;
        private PersistableBundle mExtras;
        private Icon mIcon;
        private String mId;
        private Intent[] mIntents;
        private boolean mIsLongLived;
        private LocusId mLocusId;
        private Person[] mPersons;
        private int mRank = Integer.MAX_VALUE;
        private int mStartingThemeResId;
        private CharSequence mText;
        private int mTextResId;
        private CharSequence mTitle;
        private int mTitleResId;

        @Deprecated
        public Builder(Context context) {
            this.mContext = context;
        }

        @Deprecated
        public Builder setId(String str) {
            this.mId = (String) Preconditions.checkStringNotEmpty(str, "id cannot be empty");
            return this;
        }

        public Builder(Context context, String str) {
            this.mContext = context;
            this.mId = (String) Preconditions.checkStringNotEmpty(str, "id cannot be empty");
        }

        public Builder setLocusId(LocusId locusId) {
            this.mLocusId = (LocusId) Objects.requireNonNull(locusId, "locusId cannot be null");
            return this;
        }

        public Builder setActivity(ComponentName componentName) {
            this.mActivity = (ComponentName) Objects.requireNonNull(componentName, "activity cannot be null");
            return this;
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = ShortcutInfo.validateIcon(icon);
            return this;
        }

        public Builder setStartingTheme(int i) {
            this.mStartingThemeResId = i;
            return this;
        }

        @Deprecated
        public Builder setShortLabelResId(int i) {
            Preconditions.checkState(this.mTitle == null, "shortLabel already set");
            this.mTitleResId = i;
            return this;
        }

        public Builder setShortLabel(CharSequence charSequence) {
            Preconditions.checkState(this.mTitleResId == 0, "shortLabelResId already set");
            this.mTitle = Preconditions.checkStringNotEmpty(charSequence, "shortLabel cannot be empty");
            return this;
        }

        @Deprecated
        public Builder setLongLabelResId(int i) {
            Preconditions.checkState(this.mText == null, "longLabel already set");
            this.mTextResId = i;
            return this;
        }

        public Builder setLongLabel(CharSequence charSequence) {
            Preconditions.checkState(this.mTextResId == 0, "longLabelResId already set");
            this.mText = Preconditions.checkStringNotEmpty(charSequence, "longLabel cannot be empty");
            return this;
        }

        @Deprecated
        public Builder setTitle(CharSequence charSequence) {
            return setShortLabel(charSequence);
        }

        @Deprecated
        public Builder setTitleResId(int i) {
            return setShortLabelResId(i);
        }

        @Deprecated
        public Builder setText(CharSequence charSequence) {
            return setLongLabel(charSequence);
        }

        @Deprecated
        public Builder setTextResId(int i) {
            return setLongLabelResId(i);
        }

        @Deprecated
        public Builder setDisabledMessageResId(int i) {
            Preconditions.checkState(this.mDisabledMessage == null, "disabledMessage already set");
            this.mDisabledMessageResId = i;
            return this;
        }

        public Builder setDisabledMessage(CharSequence charSequence) {
            Preconditions.checkState(this.mDisabledMessageResId == 0, "disabledMessageResId already set");
            this.mDisabledMessage = Preconditions.checkStringNotEmpty(charSequence, "disabledMessage cannot be empty");
            return this;
        }

        public Builder setCategories(Set<String> set) {
            this.mCategories = set;
            return this;
        }

        public Builder setIntent(Intent intent) {
            return setIntents(new Intent[]{intent});
        }

        public Builder setIntents(Intent[] intentArr) {
            Objects.requireNonNull(intentArr, "intents cannot be null");
            if (intentArr.length == 0) {
                throw new IllegalArgumentException("intents cannot be empty");
            }
            for (Intent intent : intentArr) {
                Objects.requireNonNull(intent, "intents cannot contain null");
                Objects.requireNonNull(intent.getAction(), "intent's action must be set");
            }
            this.mIntents = ShortcutInfo.cloneIntents(intentArr);
            return this;
        }

        public Builder setPerson(Person person) {
            return setPersons(new Person[]{person});
        }

        public Builder setPersons(Person[] personArr) {
            Objects.requireNonNull(personArr, "persons cannot be null");
            if (personArr.length == 0) {
                throw new IllegalArgumentException("persons cannot be empty");
            }
            for (Person person : personArr) {
                Objects.requireNonNull(person, "persons cannot contain null");
            }
            this.mPersons = ShortcutInfo.clonePersons(personArr);
            return this;
        }

        public Builder setLongLived(boolean z) {
            this.mIsLongLived = z;
            return this;
        }

        public Builder setRank(int i) {
            Preconditions.checkArgument(i >= 0, "Rank cannot be negative or bigger than MAX_RANK");
            this.mRank = i;
            return this;
        }

        public Builder setExtras(PersistableBundle persistableBundle) {
            this.mExtras = persistableBundle;
            return this;
        }

        public Builder addCapabilityBinding(Capability capability, CapabilityParams capabilityParams) {
            Objects.requireNonNull(capability);
            if (this.mCapabilityBindings == null) {
                this.mCapabilityBindings = new ArrayMap(1);
            }
            if (!this.mCapabilityBindings.containsKey(capability.getName())) {
                this.mCapabilityBindings.put(capability.getName(), new ArrayMap(0));
            }
            if (capabilityParams == null) {
                return this;
            }
            this.mCapabilityBindings.get(capability.getName()).put(capabilityParams.getName(), capabilityParams.getValues());
            return this;
        }

        public Builder setExcludedFromSurfaces(int i) {
            this.mExcludedSurfaces = i;
            return this;
        }

        public ShortcutInfo build() {
            return new ShortcutInfo(this);
        }
    }

    public String getId() {
        return this.mId;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public String getPackage() {
        return this.mPackageName;
    }

    public ComponentName getActivity() {
        return this.mActivity;
    }

    public void setActivity(ComponentName componentName) {
        this.mActivity = componentName;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public String getStartingThemeResName() {
        return this.mStartingThemeResName;
    }

    @Deprecated
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Deprecated
    public int getTitleResId() {
        return this.mTitleResId;
    }

    @Deprecated
    public CharSequence getText() {
        return this.mText;
    }

    @Deprecated
    public int getTextResId() {
        return this.mTextResId;
    }

    public CharSequence getShortLabel() {
        return this.mTitle;
    }

    public int getShortLabelResourceId() {
        return this.mTitleResId;
    }

    public CharSequence getLongLabel() {
        return this.mText;
    }

    public CharSequence getLabel() {
        CharSequence longLabel = getLongLabel();
        return TextUtils.isEmpty(longLabel) ? getShortLabel() : longLabel;
    }

    public int getLongLabelResourceId() {
        return this.mTextResId;
    }

    public CharSequence getDisabledMessage() {
        return this.mDisabledMessage;
    }

    public int getDisabledMessageResourceId() {
        return this.mDisabledMessageResId;
    }

    public void setDisabledReason(int i) {
        this.mDisabledReason = i;
    }

    public int getDisabledReason() {
        return this.mDisabledReason;
    }

    public Set<String> getCategories() {
        return this.mCategories;
    }

    public Intent getIntent() {
        Intent[] intentArr = this.mIntents;
        if (intentArr == null || intentArr.length == 0) {
            return null;
        }
        int length = intentArr.length - 1;
        return setIntentExtras(new Intent(this.mIntents[length]), this.mIntentPersistableExtrases[length]);
    }

    public Intent[] getIntents() {
        Intent[] intentArr = this.mIntents;
        if (intentArr == null) {
            return null;
        }
        int length = intentArr.length;
        Intent[] intentArr2 = new Intent[length];
        for (int i = 0; i < length; i++) {
            Intent intent = new Intent(this.mIntents[i]);
            intentArr2[i] = intent;
            setIntentExtras(intent, this.mIntentPersistableExtrases[i]);
        }
        return intentArr2;
    }

    public Intent[] getIntentsNoExtras() {
        return this.mIntents;
    }

    @SystemApi
    public Person[] getPersons() {
        return clonePersons(this.mPersons);
    }

    public PersistableBundle[] getIntentPersistableExtrases() {
        return this.mIntentPersistableExtrases;
    }

    public int getRank() {
        return this.mRank;
    }

    public boolean hasRank() {
        return this.mRank != Integer.MAX_VALUE;
    }

    public void setRank(int i) {
        this.mRank = i;
    }

    public void clearImplicitRankAndRankChangedFlag() {
        this.mImplicitRank = 0;
    }

    public void setImplicitRank(int i) {
        this.mImplicitRank = (i & Integer.MAX_VALUE) | (this.mImplicitRank & Integer.MIN_VALUE);
    }

    public int getImplicitRank() {
        return this.mImplicitRank & Integer.MAX_VALUE;
    }

    public void setRankChanged() {
        this.mImplicitRank |= Integer.MIN_VALUE;
    }

    public boolean isRankChanged() {
        return (this.mImplicitRank & Integer.MIN_VALUE) != 0;
    }

    public PersistableBundle getExtras() {
        return this.mExtras;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public UserHandle getUserHandle() {
        return UserHandle.of(this.mUserId);
    }

    public long getLastChangedTimestamp() {
        return this.mLastChangedTimestamp;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void replaceFlags(int i) {
        this.mFlags = i;
    }

    public void addFlags(int i) {
        this.mFlags = i | this.mFlags;
    }

    public void clearFlags(int i) {
        this.mFlags = (~i) & this.mFlags;
    }

    public boolean hasFlags(int i) {
        return (this.mFlags & i) == i;
    }

    public boolean isReturnedByServer() {
        return hasFlags(1024);
    }

    public void setReturnedByServer() {
        addFlags(1024);
    }

    public boolean isLongLived() {
        return hasFlags(8192);
    }

    public void setLongLived() {
        addFlags(8192);
    }

    public void setCached(int i) {
        addFlags(i);
    }

    public boolean isCached() {
        return (getFlags() & FLAG_CACHED_ALL) != 0;
    }

    public boolean isDynamic() {
        return hasFlags(1);
    }

    public boolean isPinned() {
        return hasFlags(2);
    }

    public boolean isDeclaredInManifest() {
        return hasFlags(32);
    }

    @Deprecated
    public boolean isManifestShortcut() {
        return isDeclaredInManifest();
    }

    public boolean isFloating() {
        return ((!isPinned() && !isCached()) || isDynamic() || isManifestShortcut()) ? false : true;
    }

    public boolean isOriginallyFromManifest() {
        return hasFlags(256);
    }

    public boolean isDynamicVisible() {
        return isDynamic() && isVisibleToPublisher();
    }

    public boolean isPinnedVisible() {
        return isPinned() && isVisibleToPublisher();
    }

    public boolean isManifestVisible() {
        return isDeclaredInManifest() && isVisibleToPublisher();
    }

    public boolean isNonManifestVisible() {
        if (isDeclaredInManifest() || !isVisibleToPublisher()) {
            return false;
        }
        return isPinned() || isCached() || isDynamic();
    }

    public boolean isImmutable() {
        return hasFlags(256);
    }

    public boolean isEnabled() {
        return !hasFlags(64);
    }

    public boolean isAlive() {
        return hasFlags(2) || hasFlags(1) || hasFlags(32) || isCached();
    }

    public boolean usesQuota() {
        return hasFlags(1) || hasFlags(32);
    }

    public boolean hasIconResource() {
        return hasFlags(4);
    }

    public boolean hasIconUri() {
        return hasFlags(32768);
    }

    public boolean hasStringResources() {
        return (this.mTitleResId == 0 && this.mTextResId == 0 && this.mDisabledMessageResId == 0) ? false : true;
    }

    public boolean hasAnyResources() {
        return hasIconResource() || hasStringResources();
    }

    public boolean hasIconFile() {
        return hasFlags(8);
    }

    public boolean hasAdaptiveBitmap() {
        return hasFlags(512);
    }

    public boolean isIconPendingSave() {
        return hasFlags(2048);
    }

    public void setIconPendingSave() {
        addFlags(2048);
    }

    public void clearIconPendingSave() {
        clearFlags(2048);
    }

    public boolean isVisibleToPublisher() {
        return !isDisabledForRestoreIssue(this.mDisabledReason);
    }

    public boolean hasKeyFieldsOnly() {
        return hasFlags(16);
    }

    public boolean hasStringResourcesResolved() {
        return hasFlags(128);
    }

    public void updateTimestamp() {
        this.mLastChangedTimestamp = System.currentTimeMillis();
    }

    public void setTimestamp(long j) {
        this.mLastChangedTimestamp = j;
    }

    public void clearIcon() {
        this.mIcon = null;
    }

    public void setIconResourceId(int i) {
        if (this.mIconResId != i) {
            this.mIconResName = null;
        }
        this.mIconResId = i;
    }

    public int getIconResourceId() {
        return this.mIconResId;
    }

    public void setIconUri(String str) {
        this.mIconUri = str;
    }

    public String getIconUri() {
        return this.mIconUri;
    }

    public String getBitmapPath() {
        return this.mBitmapPath;
    }

    public void setBitmapPath(String str) {
        this.mBitmapPath = str;
    }

    public void setDisabledMessageResId(int i) {
        if (this.mDisabledMessageResId != i) {
            this.mDisabledMessageResName = null;
        }
        this.mDisabledMessageResId = i;
        this.mDisabledMessage = null;
    }

    public void setDisabledMessage(String str) {
        this.mDisabledMessage = str;
        this.mDisabledMessageResId = 0;
        this.mDisabledMessageResName = null;
    }

    public String getTitleResName() {
        return this.mTitleResName;
    }

    public void setTitleResName(String str) {
        this.mTitleResName = str;
    }

    public String getTextResName() {
        return this.mTextResName;
    }

    public void setTextResName(String str) {
        this.mTextResName = str;
    }

    public String getDisabledMessageResName() {
        return this.mDisabledMessageResName;
    }

    public void setDisabledMessageResName(String str) {
        this.mDisabledMessageResName = str;
    }

    public String getIconResName() {
        return this.mIconResName;
    }

    public void setIconResName(String str) {
        this.mIconResName = str;
    }

    public void setIntents(Intent[] intentArr) throws IllegalArgumentException {
        Objects.requireNonNull(intentArr);
        Preconditions.checkArgument(intentArr.length > 0);
        this.mIntents = cloneIntents(intentArr);
        fixUpIntentExtras();
    }

    public static Intent setIntentExtras(Intent intent, PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            intent.replaceExtras((Bundle) null);
            return intent;
        }
        intent.replaceExtras(new Bundle(persistableBundle));
        return intent;
    }

    public void setCategories(Set<String> set) {
        this.mCategories = cloneCategories(set);
    }

    public boolean isExcludedFromSurfaces(int i) {
        return (this.mExcludedSurfaces & i) != 0;
    }

    public int getExcludedFromSurfaces() {
        return this.mExcludedSurfaces;
    }

    public Map<String, Map<String, List<String>>> getCapabilityBindingsInternal() {
        return cloneCapabilityBindings(this.mCapabilityBindings);
    }

    private static Map<String, Map<String, List<String>>> cloneCapabilityBindings(Map<String, Map<String, List<String>>> map) {
        ArrayMap arrayMap;
        if (map == null) {
            return null;
        }
        ArrayMap arrayMap2 = new ArrayMap();
        for (String str : map.keySet()) {
            Map<String, List<String>> map2 = map.get(str);
            if (map2 == null) {
                arrayMap = null;
            } else {
                arrayMap = new ArrayMap(map2.size());
                for (String str2 : map2.keySet()) {
                    arrayMap.put(str2, Collections.unmodifiableList(map2.get(str2)));
                }
            }
            arrayMap2.put(str, Collections.unmodifiableMap(arrayMap));
        }
        return Collections.unmodifiableMap(arrayMap2);
    }

    public List<Capability> getCapabilities() {
        Map<String, Map<String, List<String>>> map = this.mCapabilityBindings;
        if (map == null) {
            return new ArrayList(0);
        }
        return (List) map.keySet().stream().map(new Function() { // from class: android.content.pm.ShortcutInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new Capability((String) obj);
            }
        }).collect(Collectors.toList());
    }

    public List<CapabilityParams> getCapabilityParams(Capability capability) {
        Objects.requireNonNull(capability);
        Map<String, Map<String, List<String>>> map = this.mCapabilityBindings;
        if (map == null) {
            return new ArrayList(0);
        }
        Map<String, List<String>> map2 = map.get(capability.getName());
        if (map2 == null) {
            return new ArrayList(0);
        }
        ArrayList arrayList = new ArrayList(map2.size());
        for (String str : map2.keySet()) {
            List<String> list = map2.get(str);
            String str2 = list.get(0);
            List<String> listSubList = list.size() == 1 ? Collections.EMPTY_LIST : list.subList(1, list.size());
            CapabilityParams.Builder builder = new CapabilityParams.Builder(str, str2);
            Iterator<String> it = listSubList.iterator();
            while (it.hasNext()) {
                builder = builder.addAlias(it.next());
            }
            arrayList.add(builder.build());
        }
        return arrayList;
    }

    private ShortcutInfo(Parcel parcel) {
        ClassLoader classLoader = getClass().getClassLoader();
        this.mUserId = parcel.readInt();
        this.mId = getSafeId((String) Preconditions.checkStringNotEmpty(parcel.readString8(), "Shortcut ID must be provided"));
        this.mPackageName = parcel.readString8();
        this.mActivity = (ComponentName) parcel.readParcelable(classLoader, ComponentName.class);
        this.mFlags = parcel.readInt();
        this.mIconResId = parcel.readInt();
        this.mLastChangedTimestamp = parcel.readLong();
        this.mDisabledReason = parcel.readInt();
        if (parcel.readInt() == 0) {
            return;
        }
        this.mIcon = (Icon) parcel.readParcelable(classLoader, Icon.class);
        this.mTitle = parcel.readCharSequence();
        this.mTitleResId = parcel.readInt();
        this.mText = parcel.readCharSequence();
        this.mTextResId = parcel.readInt();
        this.mDisabledMessage = parcel.readCharSequence();
        this.mDisabledMessageResId = parcel.readInt();
        this.mIntents = (Intent[]) parcel.readParcelableArray(classLoader, Intent.class);
        this.mIntentPersistableExtrases = (PersistableBundle[]) parcel.readParcelableArray(classLoader, PersistableBundle.class);
        this.mRank = parcel.readInt();
        this.mExtras = (PersistableBundle) parcel.readParcelable(classLoader, PersistableBundle.class);
        this.mBitmapPath = parcel.readString8();
        this.mIconResName = parcel.readString8();
        this.mTitleResName = parcel.readString8();
        this.mTextResName = parcel.readString8();
        this.mDisabledMessageResName = parcel.readString8();
        int i = parcel.readInt();
        if (i == 0) {
            this.mCategories = null;
        } else {
            this.mCategories = new ArraySet<>(i);
            for (int i2 = 0; i2 < i; i2++) {
                this.mCategories.add(parcel.readString8().intern());
            }
        }
        this.mPersons = (Person[]) parcel.readParcelableArray(classLoader, Person.class);
        this.mLocusId = (LocusId) parcel.readParcelable(classLoader, LocusId.class);
        this.mIconUri = parcel.readString8();
        this.mStartingThemeResName = parcel.readString8();
        this.mExcludedSurfaces = parcel.readInt();
        HashMap hashMap = parcel.readHashMap(null, String.class, HashMap.class);
        if (hashMap == null || hashMap.isEmpty()) {
            return;
        }
        final ArrayMap arrayMap = new ArrayMap(hashMap.size());
        hashMap.forEach(new BiConsumer() { // from class: android.content.pm.ShortcutInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                arrayMap.put((String) obj, (Map) obj2);
            }
        });
        this.mCapabilityBindings = cloneCapabilityBindings(arrayMap);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mUserId);
        parcel.writeString8(this.mId);
        parcel.writeString8(this.mPackageName);
        parcel.writeParcelable(this.mActivity, i);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mIconResId);
        parcel.writeLong(this.mLastChangedTimestamp);
        parcel.writeInt(this.mDisabledReason);
        if (hasKeyFieldsOnly()) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcel.writeParcelable(this.mIcon, i);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeInt(this.mTitleResId);
        parcel.writeCharSequence(this.mText);
        parcel.writeInt(this.mTextResId);
        parcel.writeCharSequence(this.mDisabledMessage);
        parcel.writeInt(this.mDisabledMessageResId);
        parcel.writeParcelableArray(this.mIntents, i);
        parcel.writeParcelableArray(this.mIntentPersistableExtrases, i);
        parcel.writeInt(this.mRank);
        parcel.writeParcelable(this.mExtras, i);
        parcel.writeString8(this.mBitmapPath);
        parcel.writeString8(this.mIconResName);
        parcel.writeString8(this.mTitleResName);
        parcel.writeString8(this.mTextResName);
        parcel.writeString8(this.mDisabledMessageResName);
        ArraySet<String> arraySet = this.mCategories;
        if (arraySet != null) {
            int size = arraySet.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeString8(this.mCategories.valueAt(i2));
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeParcelableArray(this.mPersons, i);
        parcel.writeParcelable(this.mLocusId, i);
        parcel.writeString8(this.mIconUri);
        parcel.writeString8(this.mStartingThemeResName);
        parcel.writeInt(this.mExcludedSurfaces);
        parcel.writeMap(this.mCapabilityBindings);
    }

    public String toString() {
        return toStringInner(true, false, null);
    }

    public String toInsecureString() {
        return toStringInner(false, true, null);
    }

    public String toDumpString(String str) {
        return toStringInner(Build.IS_USER && SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x4f4c"), true, str);
    }

    public String toSimpleString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mId);
        addReadableFlags(sb);
        return sb.toString();
    }

    private void addReadableFlags(StringBuilder sb) {
        sb.append(" [");
        if ((this.mFlags & 4096) != 0) {
            sb.append("Sdw");
        }
        if (!isEnabled()) {
            sb.append(AppSearchShortcutInfo.IS_DISABLED);
        }
        if (isImmutable()) {
            sb.append(AppSearchShortcutInfo.IS_IMMUTABLE);
        }
        if (isManifestShortcut()) {
            sb.append(AppSearchShortcutInfo.IS_MANIFEST);
        }
        if (isDynamic()) {
            sb.append(AppSearchShortcutInfo.IS_DYNAMIC);
        }
        if (isPinned()) {
            sb.append("Pin");
        }
        if (hasIconFile()) {
            sb.append("Ic-f");
        }
        if (isIconPendingSave()) {
            sb.append("Pens");
        }
        if (hasIconResource()) {
            sb.append("Ic-r");
        }
        if (hasIconUri()) {
            sb.append("Ic-u");
        }
        if (hasAdaptiveBitmap()) {
            sb.append("Ic-a");
        }
        if (hasKeyFieldsOnly()) {
            sb.append("Key");
        }
        if (hasStringResourcesResolved()) {
            sb.append("Str");
        }
        if (isReturnedByServer()) {
            sb.append("Rets");
        }
        if (isLongLived()) {
            sb.append("Liv");
        }
        if (isExcludedFromSurfaces(1)) {
            sb.append("Hid-L");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
    }

    private void addIndentOrComma(StringBuilder sb, String str) {
        if (str != null) {
            sb.append("\n  ");
            sb.append(str);
        } else {
            sb.append(", ");
        }
    }

    private String toStringInner(boolean z, boolean z2, String str) {
        CharSequence charSequence;
        CharSequence charSequence2;
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
        }
        sb.append("ShortcutInfo {id=");
        CharSequence charSequence3 = "***";
        sb.append(z ? "***" : this.mId);
        sb.append(", flags=0x");
        sb.append(Integer.toHexString(this.mFlags));
        addReadableFlags(sb);
        addIndentOrComma(sb, str);
        sb.append("packageName=");
        sb.append(this.mPackageName);
        addIndentOrComma(sb, str);
        sb.append("activity=");
        sb.append(this.mActivity);
        addIndentOrComma(sb, str);
        sb.append("shortLabel=");
        if (z) {
            charSequence = "***";
        } else {
            charSequence = this.mTitle;
        }
        sb.append(charSequence);
        sb.append(", resId=");
        sb.append(this.mTitleResId);
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(this.mTitleResName);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        addIndentOrComma(sb, str);
        sb.append("longLabel=");
        if (z) {
            charSequence2 = "***";
        } else {
            charSequence2 = this.mText;
        }
        sb.append(charSequence2);
        sb.append(", resId=");
        sb.append(this.mTextResId);
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(this.mTextResName);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        addIndentOrComma(sb, str);
        sb.append("disabledMessage=");
        if (!z) {
            charSequence3 = this.mDisabledMessage;
        }
        sb.append(charSequence3);
        sb.append(", resId=");
        sb.append(this.mDisabledMessageResId);
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        sb.append(this.mDisabledMessageResName);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        addIndentOrComma(sb, str);
        sb.append("disabledReason=");
        sb.append(getDisabledReasonDebugString(this.mDisabledReason));
        String str2 = this.mStartingThemeResName;
        if (str2 != null && !str2.isEmpty()) {
            addIndentOrComma(sb, str);
            sb.append("SplashScreenThemeResName=");
            sb.append(this.mStartingThemeResName);
        }
        addIndentOrComma(sb, str);
        sb.append("categories=");
        sb.append(this.mCategories);
        addIndentOrComma(sb, str);
        sb.append("persons=");
        sb.append(Arrays.toString(this.mPersons));
        addIndentOrComma(sb, str);
        sb.append("icon=");
        sb.append(this.mIcon);
        addIndentOrComma(sb, str);
        sb.append("rank=");
        sb.append(this.mRank);
        sb.append(", timestamp=");
        sb.append(this.mLastChangedTimestamp);
        addIndentOrComma(sb, str);
        sb.append("intents=");
        Intent[] intentArr = this.mIntents;
        if (intentArr == null) {
            sb.append(PerfettoProtoLogImpl.NULL_STRING);
        } else if (z) {
            sb.append("size:");
            sb.append(this.mIntents.length);
        } else {
            int length = intentArr.length;
            sb.append(NavigationBarInflaterView.SIZE_MOD_START);
            String str3 = "";
            int i = 0;
            while (i < length) {
                sb.append(str3);
                sb.append(this.mIntents[i]);
                sb.append("/");
                sb.append(this.mIntentPersistableExtrases[i]);
                i++;
                str3 = ", ";
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        }
        addIndentOrComma(sb, str);
        sb.append("extras=");
        sb.append(this.mExtras);
        if (z2) {
            addIndentOrComma(sb, str);
            sb.append("iconRes=");
            sb.append(this.mIconResId);
            sb.append(NavigationBarInflaterView.SIZE_MOD_START);
            sb.append(this.mIconResName);
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            sb.append(", bitmapPath=");
            sb.append(this.mBitmapPath);
            sb.append(", iconUri=");
            sb.append(this.mIconUri);
        }
        if (this.mLocusId != null) {
            sb.append("locusId=");
            sb.append(this.mLocusId);
        }
        sb.append("}");
        return sb.toString();
    }

    public ShortcutInfo(int i, String str, String str2, ComponentName componentName, Icon icon, CharSequence charSequence, int i2, String str3, CharSequence charSequence2, int i3, String str4, CharSequence charSequence3, int i4, String str5, Set<String> set, Intent[] intentArr, int i5, PersistableBundle persistableBundle, long j, int i6, int i7, String str6, String str7, String str8, int i8, Person[] personArr, LocusId locusId, String str9, Map<String, Map<String, List<String>>> map) {
        this.mUserId = i;
        this.mId = str;
        this.mPackageName = str2;
        this.mActivity = componentName;
        this.mIcon = icon;
        this.mTitle = charSequence;
        this.mTitleResId = i2;
        this.mTitleResName = str3;
        this.mText = charSequence2;
        this.mTextResId = i3;
        this.mTextResName = str4;
        this.mDisabledMessage = charSequence3;
        this.mDisabledMessageResId = i4;
        this.mDisabledMessageResName = str5;
        this.mCategories = cloneCategories(set);
        this.mIntents = cloneIntents(intentArr);
        fixUpIntentExtras();
        this.mRank = i5;
        this.mExtras = persistableBundle;
        this.mLastChangedTimestamp = j;
        this.mFlags = i6;
        this.mIconResId = i7;
        this.mIconResName = str6;
        this.mBitmapPath = str7;
        this.mIconUri = str8;
        this.mDisabledReason = i8;
        this.mPersons = personArr;
        this.mLocusId = locusId;
        this.mStartingThemeResName = str9;
        this.mCapabilityBindings = cloneCapabilityBindings(map);
    }
}
