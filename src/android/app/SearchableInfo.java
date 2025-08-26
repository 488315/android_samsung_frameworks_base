package android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class SearchableInfo implements Parcelable {
    public static final Parcelable.Creator<SearchableInfo> CREATOR = new Parcelable.Creator<SearchableInfo>() { // from class: android.app.SearchableInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchableInfo createFromParcel(Parcel parcel) {
            return new SearchableInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SearchableInfo[] newArray(int i) {
            return new SearchableInfo[i];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "SearchableInfo";
    private static final String MD_LABEL_SEARCHABLE = "android.app.searchable";
    private static final String MD_XML_ELEMENT_SEARCHABLE = "searchable";
    private static final String MD_XML_ELEMENT_SEARCHABLE_ACTION_KEY = "actionkey";
    private static final int SEARCH_MODE_BADGE_ICON = 8;
    private static final int SEARCH_MODE_BADGE_LABEL = 4;
    private static final int SEARCH_MODE_QUERY_REWRITE_FROM_DATA = 16;
    private static final int SEARCH_MODE_QUERY_REWRITE_FROM_TEXT = 32;
    private static final int VOICE_SEARCH_LAUNCH_RECOGNIZER = 4;
    private static final int VOICE_SEARCH_LAUNCH_WEB_SEARCH = 2;
    private static final int VOICE_SEARCH_SHOW_BUTTON = 1;
    private HashMap<Integer, ActionKeyInfo> mActionKeys = null;
    private final boolean mAutoUrlDetect;
    private final int mHintId;
    private final int mIconId;
    private final boolean mIncludeInGlobalSearch;
    private final int mLabelId;
    private final boolean mQueryAfterZeroResults;
    private final ComponentName mSearchActivity;
    private final int mSearchButtonText;
    private final int mSearchImeOptions;
    private final int mSearchInputType;
    private final int mSearchMode;
    private final int mSettingsDescriptionId;
    private final String mSuggestAuthority;
    private final String mSuggestIntentAction;
    private final String mSuggestIntentData;
    private final String mSuggestPath;
    private final String mSuggestProviderPackage;
    private final String mSuggestSelection;
    private final int mSuggestThreshold;
    private final int mVoiceLanguageId;
    private final int mVoiceLanguageModeId;
    private final int mVoiceMaxResults;
    private final int mVoicePromptTextId;
    private final int mVoiceSearchMode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getSuggestAuthority() {
        return this.mSuggestAuthority;
    }

    public String getSuggestPackage() {
        return this.mSuggestProviderPackage;
    }

    public ComponentName getSearchActivity() {
        return this.mSearchActivity;
    }

    public boolean useBadgeLabel() {
        return (this.mSearchMode & 4) != 0;
    }

    public boolean useBadgeIcon() {
        return ((this.mSearchMode & 8) == 0 || this.mIconId == 0) ? false : true;
    }

    public boolean shouldRewriteQueryFromData() {
        return (this.mSearchMode & 16) != 0;
    }

    public boolean shouldRewriteQueryFromText() {
        return (this.mSearchMode & 32) != 0;
    }

    public int getSettingsDescriptionId() {
        return this.mSettingsDescriptionId;
    }

    public String getSuggestPath() {
        return this.mSuggestPath;
    }

    public String getSuggestSelection() {
        return this.mSuggestSelection;
    }

    public String getSuggestIntentAction() {
        return this.mSuggestIntentAction;
    }

    public String getSuggestIntentData() {
        return this.mSuggestIntentData;
    }

    public int getSuggestThreshold() {
        return this.mSuggestThreshold;
    }

    public Context getActivityContext(Context context) {
        return createActivityContext(context, this.mSearchActivity);
    }

    private static Context createActivityContext(Context context, ComponentName componentName) {
        try {
            return context.createPackageContext(componentName.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(LOG_TAG, "Package not found " + componentName.getPackageName());
            return null;
        } catch (SecurityException e) {
            Log.e(LOG_TAG, "Can't make context for " + componentName.getPackageName(), e);
            return null;
        }
    }

    public Context getProviderContext(Context context, Context context2) {
        if (this.mSearchActivity.getPackageName().equals(this.mSuggestProviderPackage)) {
            return context2;
        }
        String str = this.mSuggestProviderPackage;
        if (str == null) {
            return null;
        }
        try {
            return context.createPackageContext(str, 0);
        } catch (PackageManager.NameNotFoundException | SecurityException unused) {
            return null;
        }
    }

    private SearchableInfo(Context context, AttributeSet attributeSet, ComponentName componentName) {
        ProviderInfo providerInfoResolveContentProvider;
        String str = null;
        this.mSearchActivity = componentName;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Searchable);
        this.mSearchMode = typedArrayObtainStyledAttributes.getInt(3, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        this.mLabelId = resourceId;
        this.mHintId = typedArrayObtainStyledAttributes.getResourceId(2, 0);
        this.mIconId = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        this.mSearchButtonText = typedArrayObtainStyledAttributes.getResourceId(9, 0);
        this.mSearchInputType = typedArrayObtainStyledAttributes.getInt(10, 1);
        if (new TypedValue().data != 0) {
            this.mSearchImeOptions = typedArrayObtainStyledAttributes.getInt(16, 33554435);
        } else {
            this.mSearchImeOptions = typedArrayObtainStyledAttributes.getInt(16, 2);
        }
        this.mIncludeInGlobalSearch = typedArrayObtainStyledAttributes.getBoolean(18, false);
        this.mQueryAfterZeroResults = typedArrayObtainStyledAttributes.getBoolean(19, false);
        this.mAutoUrlDetect = typedArrayObtainStyledAttributes.getBoolean(21, false);
        this.mSettingsDescriptionId = typedArrayObtainStyledAttributes.getResourceId(20, 0);
        String string = typedArrayObtainStyledAttributes.getString(4);
        this.mSuggestAuthority = string;
        this.mSuggestPath = typedArrayObtainStyledAttributes.getString(5);
        this.mSuggestSelection = typedArrayObtainStyledAttributes.getString(6);
        this.mSuggestIntentAction = typedArrayObtainStyledAttributes.getString(7);
        this.mSuggestIntentData = typedArrayObtainStyledAttributes.getString(8);
        this.mSuggestThreshold = typedArrayObtainStyledAttributes.getInt(17, 0);
        this.mVoiceSearchMode = typedArrayObtainStyledAttributes.getInt(11, 0);
        this.mVoiceLanguageModeId = typedArrayObtainStyledAttributes.getResourceId(12, 0);
        this.mVoicePromptTextId = typedArrayObtainStyledAttributes.getResourceId(13, 0);
        this.mVoiceLanguageId = typedArrayObtainStyledAttributes.getResourceId(14, 0);
        this.mVoiceMaxResults = typedArrayObtainStyledAttributes.getInt(15, 0);
        typedArrayObtainStyledAttributes.recycle();
        if (string != null && (providerInfoResolveContentProvider = context.getPackageManager().resolveContentProvider(string, 268435456)) != null) {
            str = providerInfoResolveContentProvider.packageName;
        }
        this.mSuggestProviderPackage = str;
        if (resourceId == 0) {
            throw new IllegalArgumentException("Search label must be a resource reference.");
        }
    }

    public static class ActionKeyInfo implements Parcelable {
        public static final Parcelable.Creator<ActionKeyInfo> CREATOR = new Parcelable.Creator<ActionKeyInfo>() { // from class: android.app.SearchableInfo.ActionKeyInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ActionKeyInfo createFromParcel(Parcel parcel) {
                return new ActionKeyInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ActionKeyInfo[] newArray(int i) {
                return new ActionKeyInfo[i];
            }
        };
        private final int mKeyCode;
        private final String mQueryActionMsg;
        private final String mSuggestActionMsg;
        private final String mSuggestActionMsgColumn;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        ActionKeyInfo(Context context, AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SearchableActionKey);
            int i = typedArrayObtainStyledAttributes.getInt(0, 0);
            this.mKeyCode = i;
            String string = typedArrayObtainStyledAttributes.getString(1);
            this.mQueryActionMsg = string;
            String string2 = typedArrayObtainStyledAttributes.getString(2);
            this.mSuggestActionMsg = string2;
            String string3 = typedArrayObtainStyledAttributes.getString(3);
            this.mSuggestActionMsgColumn = string3;
            typedArrayObtainStyledAttributes.recycle();
            if (i == 0) {
                throw new IllegalArgumentException("No keycode.");
            }
            if (string == null && string2 == null && string3 == null) {
                throw new IllegalArgumentException("No message information.");
            }
        }

        private ActionKeyInfo(Parcel parcel) {
            this.mKeyCode = parcel.readInt();
            this.mQueryActionMsg = parcel.readString();
            this.mSuggestActionMsg = parcel.readString();
            this.mSuggestActionMsgColumn = parcel.readString();
        }

        public int getKeyCode() {
            return this.mKeyCode;
        }

        public String getQueryActionMsg() {
            return this.mQueryActionMsg;
        }

        public String getSuggestActionMsg() {
            return this.mSuggestActionMsg;
        }

        public String getSuggestActionMsgColumn() {
            return this.mSuggestActionMsgColumn;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mKeyCode);
            parcel.writeString(this.mQueryActionMsg);
            parcel.writeString(this.mSuggestActionMsg);
            parcel.writeString(this.mSuggestActionMsgColumn);
        }
    }

    public ActionKeyInfo findActionKey(int i) {
        HashMap<Integer, ActionKeyInfo> map = this.mActionKeys;
        if (map == null) {
            return null;
        }
        return map.get(Integer.valueOf(i));
    }

    private void addActionKey(ActionKeyInfo actionKeyInfo) {
        if (this.mActionKeys == null) {
            this.mActionKeys = new HashMap<>();
        }
        this.mActionKeys.put(Integer.valueOf(actionKeyInfo.getKeyCode()), actionKeyInfo);
    }

    public static SearchableInfo getActivityMetaData(Context context, ActivityInfo activityInfo, int i) throws XmlPullParserException, IOException {
        try {
            Context contextCreatePackageContextAsUser = context.createPackageContextAsUser("system", 0, new UserHandle(i));
            XmlResourceParser xmlResourceParserLoadXmlMetaData = activityInfo.loadXmlMetaData(contextCreatePackageContextAsUser.getPackageManager(), MD_LABEL_SEARCHABLE);
            if (xmlResourceParserLoadXmlMetaData == null) {
                return null;
            }
            SearchableInfo activityMetaData = getActivityMetaData(contextCreatePackageContextAsUser, xmlResourceParserLoadXmlMetaData, new ComponentName(activityInfo.packageName, activityInfo.name));
            xmlResourceParserLoadXmlMetaData.close();
            return activityMetaData;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(LOG_TAG, "Couldn't create package context for user " + i);
            return null;
        }
    }

    private static SearchableInfo getActivityMetaData(Context context, XmlPullParser xmlPullParser, ComponentName componentName) throws XmlPullParserException, IOException {
        Context contextCreateActivityContext = createActivityContext(context, componentName);
        if (contextCreateActivityContext == null) {
            return null;
        }
        try {
            int next = xmlPullParser.next();
            SearchableInfo searchableInfo = null;
            while (next != 1) {
                if (next == 2) {
                    if (xmlPullParser.getName().equals("searchable")) {
                        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
                        if (attributeSetAsAttributeSet != null) {
                            try {
                                searchableInfo = new SearchableInfo(contextCreateActivityContext, attributeSetAsAttributeSet, componentName);
                            } catch (IllegalArgumentException e) {
                                Log.w(LOG_TAG, "Invalid searchable metadata for " + componentName.flattenToShortString() + ": " + e.getMessage());
                                return null;
                            }
                        } else {
                            continue;
                        }
                    } else if (!xmlPullParser.getName().equals(MD_XML_ELEMENT_SEARCHABLE_ACTION_KEY)) {
                        continue;
                    } else {
                        if (searchableInfo == null) {
                            return null;
                        }
                        AttributeSet attributeSetAsAttributeSet2 = Xml.asAttributeSet(xmlPullParser);
                        if (attributeSetAsAttributeSet2 != null) {
                            try {
                                searchableInfo.addActionKey(new ActionKeyInfo(contextCreateActivityContext, attributeSetAsAttributeSet2));
                            } catch (IllegalArgumentException e2) {
                                Log.w(LOG_TAG, "Invalid action key for " + componentName.flattenToShortString() + ": " + e2.getMessage());
                                return null;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                next = xmlPullParser.next();
            }
            return searchableInfo;
        } catch (IOException e3) {
            Log.w(LOG_TAG, "Reading searchable metadata for " + componentName.flattenToShortString(), e3);
            return null;
        } catch (XmlPullParserException e4) {
            Log.w(LOG_TAG, "Reading searchable metadata for " + componentName.flattenToShortString(), e4);
            return null;
        }
    }

    public int getLabelId() {
        return this.mLabelId;
    }

    public int getHintId() {
        return this.mHintId;
    }

    public int getIconId() {
        return this.mIconId;
    }

    public boolean getVoiceSearchEnabled() {
        return (this.mVoiceSearchMode & 1) != 0;
    }

    public boolean getVoiceSearchLaunchWebSearch() {
        return (this.mVoiceSearchMode & 2) != 0;
    }

    public boolean getVoiceSearchLaunchRecognizer() {
        return (this.mVoiceSearchMode & 4) != 0;
    }

    public int getVoiceLanguageModeId() {
        return this.mVoiceLanguageModeId;
    }

    public int getVoicePromptTextId() {
        return this.mVoicePromptTextId;
    }

    public int getVoiceLanguageId() {
        return this.mVoiceLanguageId;
    }

    public int getVoiceMaxResults() {
        return this.mVoiceMaxResults;
    }

    public int getSearchButtonText() {
        return this.mSearchButtonText;
    }

    public int getInputType() {
        return this.mSearchInputType;
    }

    public int getImeOptions() {
        return this.mSearchImeOptions;
    }

    public boolean shouldIncludeInGlobalSearch() {
        return this.mIncludeInGlobalSearch;
    }

    public boolean queryAfterZeroResults() {
        return this.mQueryAfterZeroResults;
    }

    public boolean autoUrlDetect() {
        return this.mAutoUrlDetect;
    }

    SearchableInfo(Parcel parcel) {
        this.mLabelId = parcel.readInt();
        this.mSearchActivity = ComponentName.readFromParcel(parcel);
        this.mHintId = parcel.readInt();
        this.mSearchMode = parcel.readInt();
        this.mIconId = parcel.readInt();
        this.mSearchButtonText = parcel.readInt();
        this.mSearchInputType = parcel.readInt();
        this.mSearchImeOptions = parcel.readInt();
        this.mIncludeInGlobalSearch = parcel.readInt() != 0;
        this.mQueryAfterZeroResults = parcel.readInt() != 0;
        this.mAutoUrlDetect = parcel.readInt() != 0;
        this.mSettingsDescriptionId = parcel.readInt();
        this.mSuggestAuthority = parcel.readString();
        this.mSuggestPath = parcel.readString();
        this.mSuggestSelection = parcel.readString();
        this.mSuggestIntentAction = parcel.readString();
        this.mSuggestIntentData = parcel.readString();
        this.mSuggestThreshold = parcel.readInt();
        for (int i = parcel.readInt(); i > 0; i--) {
            addActionKey(new ActionKeyInfo(parcel));
        }
        this.mSuggestProviderPackage = parcel.readString();
        this.mVoiceSearchMode = parcel.readInt();
        this.mVoiceLanguageModeId = parcel.readInt();
        this.mVoicePromptTextId = parcel.readInt();
        this.mVoiceLanguageId = parcel.readInt();
        this.mVoiceMaxResults = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLabelId);
        this.mSearchActivity.writeToParcel(parcel, i);
        parcel.writeInt(this.mHintId);
        parcel.writeInt(this.mSearchMode);
        parcel.writeInt(this.mIconId);
        parcel.writeInt(this.mSearchButtonText);
        parcel.writeInt(this.mSearchInputType);
        parcel.writeInt(this.mSearchImeOptions);
        parcel.writeInt(this.mIncludeInGlobalSearch ? 1 : 0);
        parcel.writeInt(this.mQueryAfterZeroResults ? 1 : 0);
        parcel.writeInt(this.mAutoUrlDetect ? 1 : 0);
        parcel.writeInt(this.mSettingsDescriptionId);
        parcel.writeString(this.mSuggestAuthority);
        parcel.writeString(this.mSuggestPath);
        parcel.writeString(this.mSuggestSelection);
        parcel.writeString(this.mSuggestIntentAction);
        parcel.writeString(this.mSuggestIntentData);
        parcel.writeInt(this.mSuggestThreshold);
        HashMap<Integer, ActionKeyInfo> map = this.mActionKeys;
        if (map == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(map.size());
            Iterator<ActionKeyInfo> it = this.mActionKeys.values().iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(parcel, i);
            }
        }
        parcel.writeString(this.mSuggestProviderPackage);
        parcel.writeInt(this.mVoiceSearchMode);
        parcel.writeInt(this.mVoiceLanguageModeId);
        parcel.writeInt(this.mVoicePromptTextId);
        parcel.writeInt(this.mVoiceLanguageId);
        parcel.writeInt(this.mVoiceMaxResults);
    }
}
