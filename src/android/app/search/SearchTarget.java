package android.app.search;

import android.annotation.SystemApi;
import android.appwidget.AppWidgetProviderInfo;
import android.content.pm.ShortcutInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class SearchTarget implements Parcelable {
    public static final Parcelable.Creator<SearchTarget> CREATOR = new Parcelable.Creator<SearchTarget>() { // from class: android.app.search.SearchTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchTarget createFromParcel(Parcel parcel) {
            return new SearchTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchTarget[] newArray(int i) {
            return new SearchTarget[i];
        }
    };
    public static final String LAYOUT_TYPE_ICON = "icon";
    public static final String LAYOUT_TYPE_ICON_ROW = "icon_row";
    public static final String LAYOUT_TYPE_SHORT_ICON_ROW = "short_icon_row";
    public static final int RESULT_TYPE_APPLICATION = 1;
    public static final int RESULT_TYPE_SHORTCUT = 2;
    public static final int RESULT_TYPE_SLICE = 4;
    public static final int RESULT_TYPE_WIDGETS = 8;
    private final AppWidgetProviderInfo mAppWidgetProviderInfo;
    private final Bundle mExtras;
    private final boolean mHidden;
    private final String mId;
    private final String mLayoutType;
    private final String mPackageName;
    private String mParentId;
    private final int mResultType;
    private final float mScore;
    private final SearchAction mSearchAction;
    private final ShortcutInfo mShortcutInfo;
    private final Uri mSliceUri;
    private final UserHandle mUserHandle;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SearchLayoutType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SearchResultType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SearchTarget(Parcel parcel) {
        this.mResultType = parcel.readInt();
        this.mLayoutType = parcel.readString();
        this.mId = parcel.readString();
        this.mParentId = parcel.readString();
        this.mScore = parcel.readFloat();
        this.mHidden = parcel.readBoolean();
        this.mPackageName = parcel.readString();
        this.mUserHandle = UserHandle.of(parcel.readInt());
        this.mSearchAction = (SearchAction) parcel.readTypedObject(SearchAction.CREATOR);
        this.mShortcutInfo = (ShortcutInfo) parcel.readTypedObject(ShortcutInfo.CREATOR);
        this.mAppWidgetProviderInfo = (AppWidgetProviderInfo) parcel.readTypedObject(AppWidgetProviderInfo.CREATOR);
        this.mSliceUri = (Uri) parcel.readTypedObject(Uri.CREATOR);
        this.mExtras = parcel.readBundle(getClass().getClassLoader());
    }

    private SearchTarget(int i, String str, String str2, String str3, float f, boolean z, String str4, UserHandle userHandle, SearchAction searchAction, ShortcutInfo shortcutInfo, Uri uri, AppWidgetProviderInfo appWidgetProviderInfo, Bundle bundle) {
        this.mResultType = i;
        this.mLayoutType = (String) Objects.requireNonNull(str);
        this.mId = (String) Objects.requireNonNull(str2);
        this.mParentId = str3;
        this.mScore = f;
        this.mHidden = z;
        this.mPackageName = (String) Objects.requireNonNull(str4);
        this.mUserHandle = (UserHandle) Objects.requireNonNull(userHandle);
        this.mSearchAction = searchAction;
        this.mShortcutInfo = shortcutInfo;
        this.mAppWidgetProviderInfo = appWidgetProviderInfo;
        this.mSliceUri = uri;
        this.mExtras = bundle == null ? new Bundle() : bundle;
    }

    public int getResultType() {
        return this.mResultType;
    }

    public String getLayoutType() {
        return this.mLayoutType;
    }

    public String getId() {
        return this.mId;
    }

    public String getParentId() {
        return this.mParentId;
    }

    public float getScore() {
        return this.mScore;
    }

    @Deprecated
    public boolean shouldHide() {
        return this.mHidden;
    }

    public boolean isHidden() {
        return this.mHidden;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public AppWidgetProviderInfo getAppWidgetProviderInfo() {
        return this.mAppWidgetProviderInfo;
    }

    public Uri getSliceUri() {
        return this.mSliceUri;
    }

    public SearchAction getSearchAction() {
        return this.mSearchAction;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResultType);
        parcel.writeString(this.mLayoutType);
        parcel.writeString(this.mId);
        parcel.writeString(this.mParentId);
        parcel.writeFloat(this.mScore);
        parcel.writeBoolean(this.mHidden);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mUserHandle.getIdentifier());
        parcel.writeTypedObject(this.mSearchAction, i);
        parcel.writeTypedObject(this.mShortcutInfo, i);
        parcel.writeTypedObject(this.mAppWidgetProviderInfo, i);
        parcel.writeTypedObject(this.mSliceUri, i);
        parcel.writeBundle(this.mExtras);
    }

    @SystemApi
    public static final class Builder {
        private AppWidgetProviderInfo mAppWidgetProviderInfo;
        private Bundle mExtras;
        private String mId;
        private String mLayoutType;
        private String mPackageName;
        private String mParentId;
        private int mResultType;
        private SearchAction mSearchAction;
        private ShortcutInfo mShortcutInfo;
        private Uri mSliceUri;
        private UserHandle mUserHandle;
        private float mScore = 1.0f;
        private boolean mHidden = false;

        public Builder(int i, String str, String str2) {
            this.mId = str2;
            this.mLayoutType = (String) Objects.requireNonNull(str);
            this.mResultType = i;
        }

        public Builder setParentId(String str) {
            this.mParentId = (String) Objects.requireNonNull(str);
            return this;
        }

        public Builder setPackageName(String str) {
            this.mPackageName = (String) Objects.requireNonNull(str);
            return this;
        }

        public Builder setUserHandle(UserHandle userHandle) {
            this.mUserHandle = (UserHandle) Objects.requireNonNull(userHandle);
            return this;
        }

        public Builder setShortcutInfo(ShortcutInfo shortcutInfo) {
            this.mShortcutInfo = (ShortcutInfo) Objects.requireNonNull(shortcutInfo);
            String str = this.mPackageName;
            if (str != null && !str.equals(shortcutInfo.getPackage())) {
                throw new IllegalStateException("SearchTarget packageName is different from shortcut's packageName");
            }
            this.mPackageName = shortcutInfo.getPackage();
            return this;
        }

        public Builder setAppWidgetProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo) {
            this.mAppWidgetProviderInfo = (AppWidgetProviderInfo) Objects.requireNonNull(appWidgetProviderInfo);
            String str = this.mPackageName;
            if (str == null || str.equals(appWidgetProviderInfo.provider.getPackageName())) {
                return this;
            }
            throw new IllegalStateException("SearchTarget packageName is different from appWidgetProviderInfo's packageName");
        }

        public Builder setSliceUri(Uri uri) {
            this.mSliceUri = uri;
            return this;
        }

        public Builder setSearchAction(SearchAction searchAction) {
            this.mSearchAction = searchAction;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = (Bundle) Objects.requireNonNull(bundle);
            return this;
        }

        public Builder setScore(float f) {
            this.mScore = f;
            return this;
        }

        public Builder setHidden(boolean z) {
            this.mHidden = z;
            return this;
        }

        @Deprecated
        public Builder setShouldHide(boolean z) {
            this.mHidden = z;
            return this;
        }

        public SearchTarget build() {
            return new SearchTarget(this.mResultType, this.mLayoutType, this.mId, this.mParentId, this.mScore, this.mHidden, this.mPackageName, this.mUserHandle, this.mSearchAction, this.mShortcutInfo, this.mSliceUri, this.mAppWidgetProviderInfo, this.mExtras);
        }
    }
}
