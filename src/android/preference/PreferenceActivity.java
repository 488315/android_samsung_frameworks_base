package android.preference;

import android.animation.LayoutTransition;
import android.app.Fragment;
import android.app.FragmentBreadCrumbs;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.Downloads;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.WindowOnBackInvokedDispatcher;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import com.samsung.android.share.SemShareConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
/* loaded from: classes3.dex */
public abstract class PreferenceActivity extends ListActivity implements PreferenceManager.OnPreferenceTreeClickListener, PreferenceFragment.OnPreferenceStartFragmentCallback {
    private static final String BACK_STACK_PREFS = ":android:prefs";
    private static final String CUR_HEADER_TAG = ":android:cur_header";
    public static final String EXTRA_NO_HEADERS = ":android:no_headers";
    private static final String EXTRA_PREFS_SET_BACK_TEXT = "extra_prefs_set_back_text";
    private static final String EXTRA_PREFS_SET_NEXT_TEXT = "extra_prefs_set_next_text";
    private static final String EXTRA_PREFS_SHOW_BUTTON_BAR = "extra_prefs_show_button_bar";
    private static final String EXTRA_PREFS_SHOW_SKIP = "extra_prefs_show_skip";
    public static final String EXTRA_SHOW_FRAGMENT = ":android:show_fragment";
    public static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":android:show_fragment_args";
    public static final String EXTRA_SHOW_FRAGMENT_SHORT_TITLE = ":android:show_fragment_short_title";
    public static final String EXTRA_SHOW_FRAGMENT_TITLE = ":android:show_fragment_title";
    private static final int FIRST_REQUEST_CODE = 100;
    private static final String HEADERS_TAG = ":android:headers";
    public static final long HEADER_ID_UNDEFINED = -1;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final int MSG_BUILD_HEADERS = 2;
    private static final String PREFERENCES_TAG = ":android:preferences";
    private static final float SPLIT_BAR_MOVEABLE_AREA_MAX = 0.66f;
    private static final float SPLIT_BAR_MOVEABLE_AREA_MIN = 0.2f;
    private static final float SPLIT_BAR_SPLIT_X_IN_FULLVIEW = 20.0f;
    private static final String TAG = "PreferenceActivity";
    private static float mSplitBarMovedLeftWeight = -1.0f;
    private static boolean mUserUpdateSplit = false;
    private CharSequence mActivityTitle;
    private Header mCurHeader;
    private FragmentBreadCrumbs mFragmentBreadCrumbs;
    private ViewGroup mHeadersContainer;
    private boolean mIsDeviceDefault;
    private FrameLayout mListFooter;
    private Button mNextButton;
    private PreferenceManager mPreferenceManager;
    private ViewGroup mPrefsContainer;
    private Bundle mSavedInstanceState;
    private boolean mSinglePane;
    private final ArrayList<Header> mHeaders = new ArrayList<>();
    private int mPreferenceHeaderItemResId = 0;
    private boolean mPreferenceHeaderRemoveEmptyIcon = false;
    private boolean mIsBackCallbackRegistered = false;
    private final OnBackInvokedCallback mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: android.preference.PreferenceActivity$$ExternalSyntheticLambda0
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() throws Resources.NotFoundException {
            this.f$0.onBackInvoked();
        }
    };
    private final FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() { // from class: android.preference.PreferenceActivity$$ExternalSyntheticLambda1
        @Override // android.app.FragmentManager.OnBackStackChangedListener
        public final void onBackStackChanged() {
            this.f$0.updateBackCallbackRegistrationState();
        }
    };
    private boolean mInsideOnCreate = false;
    private boolean mIsRTL = false;
    private View mSplitBarView = null;
    private boolean mUpdateLayoutBySplitChange = false;
    private View.OnLayoutChangeListener mSplitBarLayoutChangeListner = null;
    private Handler mHandler = new Handler() { // from class: android.preference.PreferenceActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message message) throws Resources.NotFoundException, ClassNotFoundException {
            PreferenceActivity preferenceActivity;
            Header headerFindBestMatchingHeader;
            int i = message.what;
            if (i == 1) {
                PreferenceActivity.this.bindPreferences();
                return;
            }
            if (i != 2) {
                return;
            }
            ArrayList<Header> arrayList = new ArrayList<>(PreferenceActivity.this.mHeaders);
            PreferenceActivity.this.mHeaders.clear();
            PreferenceActivity preferenceActivity2 = PreferenceActivity.this;
            preferenceActivity2.onBuildHeaders(preferenceActivity2.mHeaders);
            if (PreferenceActivity.this.mAdapter instanceof BaseAdapter) {
                ((BaseAdapter) PreferenceActivity.this.mAdapter).notifyDataSetChanged();
            }
            Header headerOnGetNewHeader = PreferenceActivity.this.onGetNewHeader();
            if (headerOnGetNewHeader != null && headerOnGetNewHeader.fragment != null) {
                Header headerFindBestMatchingHeader2 = PreferenceActivity.this.findBestMatchingHeader(headerOnGetNewHeader, arrayList);
                if (headerFindBestMatchingHeader2 == null || PreferenceActivity.this.mCurHeader != headerFindBestMatchingHeader2) {
                    PreferenceActivity.this.switchToHeader(headerOnGetNewHeader);
                    return;
                }
                return;
            }
            if (PreferenceActivity.this.mCurHeader == null || (headerFindBestMatchingHeader = (preferenceActivity = PreferenceActivity.this).findBestMatchingHeader(preferenceActivity.mCurHeader, PreferenceActivity.this.mHeaders)) == null) {
                return;
            }
            PreferenceActivity.this.setSelectedHeader(headerFindBestMatchingHeader);
        }
    };
    private boolean mEnableSplitBar = true;
    private boolean mIsMultiPane = false;

    public void onBuildHeaders(List<Header> list) {
    }

    public Header onGetNewHeader() {
        return null;
    }

    @Override // android.preference.PreferenceManager.OnPreferenceTreeClickListener
    @Deprecated
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return false;
    }

    private static class HeaderAdapter extends ArrayAdapter<Header> {
        private LayoutInflater mInflater;
        private int mLayoutResId;
        private boolean mRemoveIconIfEmpty;

        private static class HeaderViewHolder {
            ImageView icon;
            TextView summary;
            TextView title;

            private HeaderViewHolder() {
            }
        }

        public HeaderAdapter(Context context, List<Header> list, int i, boolean z) {
            super(context, 0, list);
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mLayoutResId = i;
            this.mRemoveIconIfEmpty = z;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
            HeaderViewHolder headerViewHolder;
            if (view == null) {
                view = this.mInflater.inflate(this.mLayoutResId, viewGroup, false);
                headerViewHolder = new HeaderViewHolder();
                headerViewHolder.icon = (ImageView) view.findViewById(16908294);
                headerViewHolder.title = (TextView) view.findViewById(16908310);
                headerViewHolder.summary = (TextView) view.findViewById(16908304);
                view.setTag(headerViewHolder);
            } else {
                headerViewHolder = (HeaderViewHolder) view.getTag();
            }
            Header item = getItem(i);
            if (this.mRemoveIconIfEmpty) {
                if (item.iconRes == 0) {
                    headerViewHolder.icon.setVisibility(8);
                } else {
                    headerViewHolder.icon.setVisibility(0);
                    headerViewHolder.icon.setImageResource(item.iconRes);
                }
            } else {
                headerViewHolder.icon.setImageResource(item.iconRes);
            }
            headerViewHolder.title.lambda$setTextAsync$0(item.getTitle(getContext().getResources()));
            CharSequence summary = item.getSummary(getContext().getResources());
            if (!TextUtils.isEmpty(summary)) {
                headerViewHolder.summary.setVisibility(0);
                headerViewHolder.summary.lambda$setTextAsync$0(summary);
                return view;
            }
            headerViewHolder.summary.setVisibility(8);
            return view;
        }
    }

    @Deprecated
    public static final class Header implements Parcelable {
        public static final Parcelable.Creator<Header> CREATOR = new Parcelable.Creator<Header>() { // from class: android.preference.PreferenceActivity.Header.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Header createFromParcel(Parcel parcel) {
                return new Header(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Header[] newArray(int i) {
                return new Header[i];
            }
        };
        public CharSequence breadCrumbShortTitle;
        public int breadCrumbShortTitleRes;
        public CharSequence breadCrumbTitle;
        public int breadCrumbTitleRes;
        public Bundle extras;
        public String fragment;
        public Bundle fragmentArguments;
        public int iconRes;
        public long id = -1;
        public Intent intent;
        public CharSequence summary;
        public int summaryRes;
        public CharSequence title;
        public int titleRes;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Header() {
        }

        public CharSequence getTitle(Resources resources) {
            int i = this.titleRes;
            if (i != 0) {
                return resources.getText(i);
            }
            return this.title;
        }

        public CharSequence getSummary(Resources resources) {
            int i = this.summaryRes;
            if (i != 0) {
                return resources.getText(i);
            }
            return this.summary;
        }

        public CharSequence getBreadCrumbTitle(Resources resources) {
            int i = this.breadCrumbTitleRes;
            if (i != 0) {
                return resources.getText(i);
            }
            return this.breadCrumbTitle;
        }

        public CharSequence getBreadCrumbShortTitle(Resources resources) {
            int i = this.breadCrumbShortTitleRes;
            if (i != 0) {
                return resources.getText(i);
            }
            return this.breadCrumbShortTitle;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.id);
            parcel.writeInt(this.titleRes);
            TextUtils.writeToParcel(this.title, parcel, i);
            parcel.writeInt(this.summaryRes);
            TextUtils.writeToParcel(this.summary, parcel, i);
            parcel.writeInt(this.breadCrumbTitleRes);
            TextUtils.writeToParcel(this.breadCrumbTitle, parcel, i);
            parcel.writeInt(this.breadCrumbShortTitleRes);
            TextUtils.writeToParcel(this.breadCrumbShortTitle, parcel, i);
            parcel.writeInt(this.iconRes);
            parcel.writeString(this.fragment);
            parcel.writeBundle(this.fragmentArguments);
            if (this.intent != null) {
                parcel.writeInt(1);
                this.intent.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeBundle(this.extras);
        }

        public void readFromParcel(Parcel parcel) {
            this.id = parcel.readLong();
            this.titleRes = parcel.readInt();
            this.title = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.summaryRes = parcel.readInt();
            this.summary = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.breadCrumbTitleRes = parcel.readInt();
            this.breadCrumbTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.breadCrumbShortTitleRes = parcel.readInt();
            this.breadCrumbShortTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.iconRes = parcel.readInt();
            this.fragment = parcel.readString();
            this.fragmentArguments = parcel.readBundle();
            if (parcel.readInt() != 0) {
                this.intent = Intent.CREATOR.createFromParcel(parcel);
            }
            this.extras = parcel.readBundle();
        }

        Header(Parcel parcel) {
            readFromParcel(parcel);
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) throws Resources.NotFoundException {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws Resources.NotFoundException, ClassNotFoundException {
        Header header;
        super.onCreate(bundle);
        this.mInsideOnCreate = true;
        TypedArray typedArrayObtainStyledAttributes = obtainStyledAttributes(null, R.styleable.PreferenceActivity, R.attr.preferenceActivityStyle, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, R.layout.preference_list_content);
        this.mPreferenceHeaderItemResId = typedArrayObtainStyledAttributes.getResourceId(1, R.layout.preference_header_item);
        this.mPreferenceHeaderRemoveEmptyIcon = typedArrayObtainStyledAttributes.getBoolean(2, false);
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        this.mIsDeviceDefault = typedValue.data != 0;
        typedArrayObtainStyledAttributes.recycle();
        setContentView(resourceId);
        this.mListFooter = (FrameLayout) findViewById(R.id.list_footer);
        this.mPrefsContainer = (ViewGroup) findViewById(R.id.prefs_frame);
        this.mHeadersContainer = (ViewGroup) findViewById(R.id.headers);
        this.mSinglePane = onIsHidingHeaders() || !onIsMultiPane();
        String stringExtra = getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT);
        Bundle bundleExtra = getIntent().getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS);
        int intExtra = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_TITLE, 0);
        int intExtra2 = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, 0);
        this.mActivityTitle = getTitle();
        if (this.mIsDeviceDefault && !this.mSinglePane) {
            View viewFindViewById = findViewById(R.id.prefs_split_bar);
            this.mSplitBarView = viewFindViewById;
            if (viewFindViewById != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mHeadersContainer.getLayoutParams();
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mPrefsContainer.getLayoutParams();
                float f = layoutParams.weight + layoutParams2.weight;
                float f2 = mSplitBarMovedLeftWeight;
                if (f2 > 0.0f) {
                    layoutParams.weight = f2;
                    layoutParams2.weight = f - mSplitBarMovedLeftWeight;
                    this.mHeadersContainer.setLayoutParams(layoutParams);
                    this.mPrefsContainer.setLayoutParams(layoutParams2);
                }
            }
        } else {
            View viewFindViewById2 = findViewById(R.id.prefs_split_bar);
            this.mSplitBarView = viewFindViewById2;
            if (viewFindViewById2 != null) {
                viewFindViewById2.setVisibility(8);
                this.mSplitBarView = null;
            }
        }
        if (bundle != null) {
            ArrayList parcelableArrayList = bundle.getParcelableArrayList(HEADERS_TAG, Header.class);
            if (parcelableArrayList != null) {
                this.mHeaders.addAll(parcelableArrayList);
                int i = bundle.getInt(CUR_HEADER_TAG, -1);
                if (i >= 0 && i < this.mHeaders.size()) {
                    setSelectedHeader(this.mHeaders.get(i));
                } else if (!this.mSinglePane && stringExtra == null) {
                    switchToHeader(onGetInitialHeader());
                }
            } else {
                showBreadCrumbs(getTitle(), null);
            }
        } else {
            if (!onIsHidingHeaders()) {
                onBuildHeaders(this.mHeaders);
            }
            if (stringExtra != null) {
                switchToHeader(stringExtra, bundleExtra);
            } else if (!this.mSinglePane && this.mHeaders.size() > 0) {
                switchToHeader(onGetInitialHeader());
            }
        }
        if (this.mHeaders.size() > 0) {
            setListAdapter(new HeaderAdapter(this, this.mHeaders, this.mPreferenceHeaderItemResId, this.mPreferenceHeaderRemoveEmptyIcon));
            if (!this.mSinglePane) {
                getListView().setChoiceMode(1);
            }
        }
        if (this.mSinglePane && stringExtra != null) {
            if (this.mIsDeviceDefault) {
                ViewGroup viewGroup = (ViewGroup) this.mPrefsContainer.getChildAt(0);
                viewGroup.removeAllViews();
                viewGroup.setVisibility(8);
                this.mFragmentBreadCrumbs = null;
            }
            if (intExtra != 0) {
                showBreadCrumbs(getText(intExtra), intExtra2 != 0 ? getText(intExtra2) : null);
            }
        }
        if (this.mHeaders.size() == 0 && stringExtra == null) {
            setContentView(this.mIsDeviceDefault ? R.layout.tw_preference_list_content_single : R.layout.preference_list_content_single);
            this.mListFooter = (FrameLayout) findViewById(R.id.list_footer);
            this.mPrefsContainer = (ViewGroup) findViewById(R.id.prefs);
            PreferenceManager preferenceManager = new PreferenceManager(this, 100);
            this.mPreferenceManager = preferenceManager;
            preferenceManager.setOnPreferenceTreeClickListener(this);
            this.mHeadersContainer = null;
        } else if (this.mSinglePane) {
            if (stringExtra != null || this.mCurHeader != null) {
                this.mHeadersContainer.setVisibility(8);
            } else {
                this.mPrefsContainer.setVisibility(8);
            }
            ((ViewGroup) findViewById(R.id.prefs_container)).setLayoutTransition(new LayoutTransition());
        } else if (this.mHeaders.size() > 0 && (header = this.mCurHeader) != null) {
            setSelectedHeader(header);
        }
        Intent intent = getIntent();
        if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false)) {
            findViewById(R.id.button_bar).setVisibility(0);
            Button button = (Button) findViewById(R.id.back_button);
            button.setOnClickListener(new View.OnClickListener() { // from class: android.preference.PreferenceActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PreferenceActivity.this.setResult(0);
                    PreferenceActivity.this.finish();
                }
            });
            Button button2 = (Button) findViewById(R.id.skip_button);
            button2.setOnClickListener(new View.OnClickListener() { // from class: android.preference.PreferenceActivity.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            Button button3 = (Button) findViewById(R.id.next_button);
            this.mNextButton = button3;
            button3.setOnClickListener(new View.OnClickListener() { // from class: android.preference.PreferenceActivity.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            if (intent.hasExtra(EXTRA_PREFS_SET_NEXT_TEXT)) {
                String stringExtra2 = intent.getStringExtra(EXTRA_PREFS_SET_NEXT_TEXT);
                if (TextUtils.isEmpty(stringExtra2)) {
                    this.mNextButton.setVisibility(8);
                } else {
                    this.mNextButton.lambda$setTextAsync$0(stringExtra2);
                }
            }
            if (intent.hasExtra(EXTRA_PREFS_SET_BACK_TEXT)) {
                String stringExtra3 = intent.getStringExtra(EXTRA_PREFS_SET_BACK_TEXT);
                if (TextUtils.isEmpty(stringExtra3)) {
                    button.setVisibility(8);
                } else {
                    button.lambda$setTextAsync$0(stringExtra3);
                }
            }
            if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_SKIP, false)) {
                button2.setVisibility(0);
            }
        }
        updateBackCallbackRegistrationState();
        getFragmentManager().addOnBackStackChangedListener(this.mOnBackStackChangedListener);
        Preference preference = new Preference(this);
        this.mIsRTL = preference.isRTL() && preference.hasRTL();
        if (this.mIsDeviceDefault && !this.mSinglePane && this.mSplitBarView != null) {
            if (this.mSplitBarLayoutChangeListner == null) {
                this.mSplitBarLayoutChangeListner = new View.OnLayoutChangeListener() { // from class: android.preference.PreferenceActivity.5
                    @Override // android.view.View.OnLayoutChangeListener
                    public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                        float x;
                        if (PreferenceActivity.this.mEnableSplitBar) {
                            if (PreferenceActivity.this.mIsRTL) {
                                x = PreferenceActivity.this.mHeadersContainer.getX();
                            } else {
                                x = PreferenceActivity.this.mPrefsContainer.getX();
                            }
                            if (!PreferenceActivity.this.mIsDeviceDefault || PreferenceActivity.this.mSplitBarView == null) {
                                return;
                            }
                            float width = x - (PreferenceActivity.this.mSplitBarView.getWidth() / 2.0f);
                            if (width < 0.0f) {
                                width = 0.0f;
                            }
                            if (PreferenceActivity.this.mSplitBarView.getX() != width) {
                                PreferenceActivity.this.mSplitBarView.setX(width);
                            }
                        }
                    }
                };
            }
            this.mSplitBarView.addOnLayoutChangeListener(this.mSplitBarLayoutChangeListner);
            this.mSplitBarView.setOnTouchListener(new View.OnTouchListener() { // from class: android.preference.PreferenceActivity.6
                /* JADX WARN: Removed duplicated region for block: B:28:0x00a6  */
                /* JADX WARN: Removed duplicated region for block: B:50:0x0120  */
                @Override // android.view.View.OnTouchListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (!PreferenceActivity.this.mEnableSplitBar) {
                        return false;
                    }
                    int action = motionEvent.getAction();
                    View childAt = view instanceof ViewGroup ? ((ViewGroup) view).getChildAt(0) : null;
                    if (childAt == null) {
                        return false;
                    }
                    if (action == 0) {
                        childAt.setVisibility(0);
                        PreferenceActivity.this.mUpdateLayoutBySplitChange = false;
                    } else {
                        if (action == 2) {
                            int width = PreferenceActivity.this.mSplitBarView.getWidth();
                            int width2 = ((View) PreferenceActivity.this.mSplitBarView.getParent()).getWidth();
                            float x = motionEvent.getX();
                            float x2 = PreferenceActivity.this.mSplitBarView.getX();
                            float f3 = width;
                            float f4 = f3 / 2.0f;
                            float fApplyDimension = x2 + f4;
                            float f5 = x2 + x;
                            if (PreferenceActivity.this.mIsDeviceDefault && PreferenceActivity.this.mIsRTL) {
                                if (x > f3) {
                                    float f6 = width2;
                                    if (f5 <= f6) {
                                        fApplyDimension += x - f3;
                                        if (fApplyDimension / f6 > 0.8f) {
                                            fApplyDimension = f6 - TypedValue.applyDimension(1, PreferenceActivity.SPLIT_BAR_SPLIT_X_IN_FULLVIEW, PreferenceActivity.this.getResources().getDisplayMetrics());
                                        }
                                        PreferenceActivity.this.mSplitBarView.setX(fApplyDimension - f4);
                                        PreferenceActivity.this.mUpdateLayoutBySplitChange = true;
                                    } else if (x < 0.0f && f5 >= 0.0f) {
                                        fApplyDimension += x;
                                        float f7 = width2;
                                        float f8 = fApplyDimension / f7;
                                        if (f8 < 0.33999997f) {
                                            fApplyDimension = f7 * 0.33999997f;
                                        } else if (f8 > 0.8f) {
                                            fApplyDimension = f7 - TypedValue.applyDimension(1, PreferenceActivity.SPLIT_BAR_SPLIT_X_IN_FULLVIEW, PreferenceActivity.this.getResources().getDisplayMetrics());
                                        }
                                        PreferenceActivity.this.mSplitBarView.setX(fApplyDimension - f4);
                                        PreferenceActivity.this.mUpdateLayoutBySplitChange = true;
                                    }
                                }
                            } else if (x > f3) {
                                float f9 = width2;
                                if (f5 <= f9) {
                                    fApplyDimension += x - f3;
                                    float f10 = fApplyDimension / f9;
                                    if (f10 > PreferenceActivity.SPLIT_BAR_MOVEABLE_AREA_MAX) {
                                        fApplyDimension = f9 * PreferenceActivity.SPLIT_BAR_MOVEABLE_AREA_MAX;
                                    } else if (f10 < 0.2f) {
                                        fApplyDimension = TypedValue.applyDimension(1, PreferenceActivity.SPLIT_BAR_SPLIT_X_IN_FULLVIEW, PreferenceActivity.this.getResources().getDisplayMetrics());
                                    }
                                    PreferenceActivity.this.mSplitBarView.setX(fApplyDimension - f4);
                                    PreferenceActivity.this.mUpdateLayoutBySplitChange = true;
                                } else if (x < 0.0f && f5 >= 0.0f) {
                                    fApplyDimension += x;
                                    if (fApplyDimension / width2 < 0.2f) {
                                        fApplyDimension = TypedValue.applyDimension(1, PreferenceActivity.SPLIT_BAR_SPLIT_X_IN_FULLVIEW, PreferenceActivity.this.getResources().getDisplayMetrics());
                                    }
                                    PreferenceActivity.this.mSplitBarView.setX(fApplyDimension - f4);
                                    PreferenceActivity.this.mUpdateLayoutBySplitChange = true;
                                }
                            }
                            if (PreferenceActivity.this.mUpdateLayoutBySplitChange) {
                                PreferenceActivity.mUserUpdateSplit = true;
                                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) PreferenceActivity.this.mHeadersContainer.getLayoutParams();
                                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) PreferenceActivity.this.mPrefsContainer.getLayoutParams();
                                float f11 = layoutParams3.weight + layoutParams4.weight;
                                float f12 = (fApplyDimension / width2) * f11;
                                layoutParams3.weight = f12;
                                layoutParams4.weight = f11 - f12;
                                if (PreferenceActivity.this.mIsDeviceDefault) {
                                    if (PreferenceActivity.this.mIsRTL) {
                                        PreferenceActivity.this.mHeadersContainer.setLayoutParams(layoutParams4);
                                        PreferenceActivity.this.mPrefsContainer.setLayoutParams(layoutParams3);
                                    } else {
                                        PreferenceActivity.this.mHeadersContainer.setLayoutParams(layoutParams3);
                                        PreferenceActivity.this.mPrefsContainer.setLayoutParams(layoutParams4);
                                    }
                                }
                            }
                            PreferenceActivity.this.mUpdateLayoutBySplitChange = false;
                        } else if (action == 1) {
                            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) PreferenceActivity.this.mHeadersContainer.getLayoutParams();
                            if (PreferenceActivity.mSplitBarMovedLeftWeight != layoutParams5.weight) {
                                PreferenceActivity.mSplitBarMovedLeftWeight = layoutParams5.weight;
                            }
                            childAt.setVisibility(4);
                            childAt.requestLayout();
                        } else {
                            float x3 = PreferenceActivity.this.mPrefsContainer.getX() - (PreferenceActivity.this.mSplitBarView.getWidth() / 2.0f);
                            float f13 = x3 >= 0.0f ? x3 : 0.0f;
                            if (action != 3 || !PreferenceActivity.this.mIsDeviceDefault) {
                                PreferenceActivity.this.mSplitBarView.setX(f13);
                            }
                            PreferenceActivity.this.mUpdateLayoutBySplitChange = false;
                            childAt.setVisibility(4);
                        }
                    }
                    return true;
                }
            });
        }
        this.mInsideOnCreate = false;
    }

    @Override // android.app.Activity
    public void onBackPressed() throws Resources.NotFoundException {
        onBackInvoked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBackCallbackRegistrationState() {
        if (WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this)) {
            if ((this.mCurHeader != null && getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT) == null && this.mSinglePane) || getFragmentManager().getBackStackEntryCount() != 0) {
                if (this.mIsBackCallbackRegistered) {
                    return;
                }
                getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
                this.mIsBackCallbackRegistered = true;
                return;
            }
            if (this.mIsBackCallbackRegistered) {
                getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
                this.mIsBackCallbackRegistered = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackInvoked() throws Resources.NotFoundException {
        if (WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this) && getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStackImmediate();
        } else if (this.mCurHeader != null && this.mSinglePane && getFragmentManager().getBackStackEntryCount() == 0 && getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT) == null) {
            this.mCurHeader = null;
            this.mPrefsContainer.setVisibility(8);
            this.mHeadersContainer.setVisibility(0);
            CharSequence charSequence = this.mActivityTitle;
            if (charSequence != null) {
                showBreadCrumbs(charSequence, null);
            }
            getListView().clearChoices();
        } else if (!WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this)) {
            super.onBackPressed();
        } else if (!this.mIsBackCallbackRegistered) {
            finish();
        }
        updateBackCallbackRegistrationState();
    }

    public boolean hasHeaders() {
        ViewGroup viewGroup = this.mHeadersContainer;
        return viewGroup != null && viewGroup.getVisibility() == 0;
    }

    public List<Header> getHeaders() {
        return this.mHeaders;
    }

    public boolean isMultiPane() {
        return !this.mSinglePane;
    }

    public boolean onIsMultiPane() {
        return getResources().getBoolean(R.bool.preferences_prefer_dual_pane) || this.mIsMultiPane;
    }

    protected void semSetMultiPane(boolean z) {
        this.mIsMultiPane = z;
    }

    public void setEnableSplitBar(boolean z) {
        this.mEnableSplitBar = z;
    }

    public boolean onIsHidingHeaders() {
        return getIntent().getBooleanExtra(EXTRA_NO_HEADERS, false);
    }

    public Header onGetInitialHeader() {
        for (int i = 0; i < this.mHeaders.size(); i++) {
            Header header = this.mHeaders.get(i);
            if (header.fragment != null) {
                return header;
            }
        }
        throw new IllegalStateException("Must have at least one header with a fragment");
    }

    public void invalidateHeaders() {
        if (this.mHandler.hasMessages(2)) {
            return;
        }
        this.mHandler.sendEmptyMessage(2);
    }

    public void loadHeadersFromResource(int i, List<Header> list) throws Throwable {
        XmlResourceParser xml;
        int next;
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xml = getResources().getXml(i);
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        } catch (XmlPullParserException e2) {
            e = e2;
        }
        try {
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
            do {
                next = xml.next();
                if (next == 1) {
                    break;
                }
            } while (next != 2);
            String name = xml.getName();
            if (!"preference-headers".equals(name)) {
                throw new RuntimeException("XML document must start with <preference-headers> tag; found" + name + " at " + xml.getPositionDescription());
            }
            int depth = xml.getDepth();
            Bundle bundle = null;
            while (true) {
                int next2 = xml.next();
                if (next2 == 1 || (next2 == 3 && xml.getDepth() <= depth)) {
                    break;
                }
                if (next2 != 3 && next2 != 4) {
                    if (Downloads.Impl.RequestHeaders.COLUMN_HEADER.equals(xml.getName())) {
                        Header header = new Header();
                        TypedArray typedArrayObtainStyledAttributes = obtainStyledAttributes(attributeSetAsAttributeSet, R.styleable.PreferenceHeader);
                        header.id = typedArrayObtainStyledAttributes.getResourceId(1, -1);
                        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(2);
                        if (typedValuePeekValue != null && typedValuePeekValue.type == 3) {
                            if (typedValuePeekValue.resourceId != 0) {
                                header.titleRes = typedValuePeekValue.resourceId;
                            } else {
                                header.title = typedValuePeekValue.string;
                            }
                        }
                        TypedValue typedValuePeekValue2 = typedArrayObtainStyledAttributes.peekValue(3);
                        if (typedValuePeekValue2 != null && typedValuePeekValue2.type == 3) {
                            if (typedValuePeekValue2.resourceId != 0) {
                                header.summaryRes = typedValuePeekValue2.resourceId;
                            } else {
                                header.summary = typedValuePeekValue2.string;
                            }
                        }
                        TypedValue typedValuePeekValue3 = typedArrayObtainStyledAttributes.peekValue(5);
                        if (typedValuePeekValue3 != null && typedValuePeekValue3.type == 3) {
                            if (typedValuePeekValue3.resourceId != 0) {
                                header.breadCrumbTitleRes = typedValuePeekValue3.resourceId;
                            } else {
                                header.breadCrumbTitle = typedValuePeekValue3.string;
                            }
                        }
                        TypedValue typedValuePeekValue4 = typedArrayObtainStyledAttributes.peekValue(6);
                        if (typedValuePeekValue4 != null && typedValuePeekValue4.type == 3) {
                            if (typedValuePeekValue4.resourceId != 0) {
                                header.breadCrumbShortTitleRes = typedValuePeekValue4.resourceId;
                            } else {
                                header.breadCrumbShortTitle = typedValuePeekValue4.string;
                            }
                        }
                        header.iconRes = typedArrayObtainStyledAttributes.getResourceId(0, 0);
                        header.fragment = typedArrayObtainStyledAttributes.getString(4);
                        typedArrayObtainStyledAttributes.recycle();
                        if (bundle == null) {
                            bundle = new Bundle();
                        }
                        int depth2 = xml.getDepth();
                        while (true) {
                            int next3 = xml.next();
                            if (next3 == 1 || (next3 == 3 && xml.getDepth() <= depth2)) {
                                break;
                            }
                            if (next3 != 3 && next3 != 4) {
                                String name2 = xml.getName();
                                if (name2.equals(SemShareConstants.SURVEY_CONTENT_EXTRA)) {
                                    getResources().parseBundleExtra(SemShareConstants.SURVEY_CONTENT_EXTRA, attributeSetAsAttributeSet, bundle);
                                    XmlUtils.skipCurrentTag(xml);
                                } else if (name2.equals("intent")) {
                                    header.intent = Intent.parseIntent(getResources(), xml, attributeSetAsAttributeSet);
                                } else {
                                    XmlUtils.skipCurrentTag(xml);
                                }
                            }
                        }
                        if (bundle.size() > 0) {
                            header.fragmentArguments = bundle;
                            bundle = null;
                        }
                        list.add(header);
                    } else {
                        XmlUtils.skipCurrentTag(xml);
                    }
                }
            }
            if (xml != null) {
                xml.close();
            }
        } catch (IOException e3) {
            e = e3;
            throw new RuntimeException("Error parsing headers", e);
        } catch (XmlPullParserException e4) {
            e = e4;
            throw new RuntimeException("Error parsing headers", e);
        } catch (Throwable th2) {
            th = th2;
            xmlResourceParser = xml;
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    protected boolean isValidFragment(String str) {
        if (getApplicationInfo().targetSdkVersion < 19) {
            return true;
        }
        throw new RuntimeException("Subclasses of PreferenceActivity must override isValidFragment(String) to verify that the Fragment class is valid! " + getClass().getName() + " has not checked if fragment " + str + " is valid.");
    }

    public void setListFooter(View view) {
        this.mListFooter.removeAllViews();
        this.mListFooter.addView(view, new FrameLayout.LayoutParams(-1, -2));
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            preferenceManager.dispatchActivityStop();
        }
    }

    @Override // android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        getFragmentManager().removeOnBackStackChangedListener(this.mOnBackStackChangedListener);
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        super.onDestroy();
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            preferenceManager.dispatchActivityDestroy();
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        PreferenceScreen preferenceScreen;
        int iIndexOf;
        super.onSaveInstanceState(bundle);
        if (this.mHeaders.size() > 0) {
            bundle.putParcelableArrayList(HEADERS_TAG, this.mHeaders);
            Header header = this.mCurHeader;
            if (header != null && (iIndexOf = this.mHeaders.indexOf(header)) >= 0) {
                bundle.putInt(CUR_HEADER_TAG, iIndexOf);
            }
        }
        if (this.mPreferenceManager == null || (preferenceScreen = getPreferenceScreen()) == null) {
            return;
        }
        Bundle bundle2 = new Bundle();
        preferenceScreen.saveHierarchyState(bundle2);
        bundle.putBundle(PREFERENCES_TAG, bundle2);
    }

    @Override // android.app.ListActivity, android.app.Activity
    protected void onRestoreInstanceState(Bundle bundle) throws Resources.NotFoundException {
        Header header;
        Bundle bundle2;
        PreferenceScreen preferenceScreen;
        if (this.mPreferenceManager != null && (bundle2 = bundle.getBundle(PREFERENCES_TAG)) != null && (preferenceScreen = getPreferenceScreen()) != null) {
            preferenceScreen.restoreHierarchyState(bundle2);
            this.mSavedInstanceState = bundle;
            return;
        }
        super.onRestoreInstanceState(bundle);
        if (this.mSinglePane || (header = this.mCurHeader) == null) {
            return;
        }
        setSelectedHeader(header);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            preferenceManager.dispatchActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.ListActivity, android.app.Activity, android.view.Window.Callback
    public void onContentChanged() {
        super.onContentChanged();
        if (this.mPreferenceManager != null) {
            postBindPreferences();
        }
    }

    @Override // android.app.ListActivity
    protected void onListItemClick(ListView listView, View view, int i, long j) throws Resources.NotFoundException, ClassNotFoundException {
        if (isResumed()) {
            super.onListItemClick(listView, view, i, j);
            if (this.mAdapter != null) {
                Object item = this.mAdapter.getItem(i);
                if (item instanceof Header) {
                    onHeaderClick((Header) item, i);
                }
            }
        }
    }

    public void onHeaderClick(Header header, int i) throws Resources.NotFoundException, ClassNotFoundException {
        if (header.fragment != null) {
            switchToHeader(header);
        } else if (header.intent != null) {
            startActivity(header.intent);
        }
    }

    public Intent onBuildStartFragmentIntent(String str, Bundle bundle, int i, int i2) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(this, getClass());
        intent.putExtra(EXTRA_SHOW_FRAGMENT, str);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, bundle);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, i);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, i2);
        intent.putExtra(EXTRA_NO_HEADERS, true);
        return intent;
    }

    public void startWithFragment(String str, Bundle bundle, Fragment fragment, int i) {
        startWithFragment(str, bundle, fragment, i, 0, 0);
    }

    public void startWithFragment(String str, Bundle bundle, Fragment fragment, int i, int i2, int i3) {
        Intent intentOnBuildStartFragmentIntent = onBuildStartFragmentIntent(str, bundle, i2, i3);
        if (fragment == null) {
            startActivity(intentOnBuildStartFragmentIntent);
        } else {
            fragment.startActivityForResult(intentOnBuildStartFragmentIntent, i);
        }
    }

    public void showBreadCrumbs(CharSequence charSequence, CharSequence charSequence2) throws Resources.NotFoundException {
        if (this.mFragmentBreadCrumbs == null) {
            try {
                FragmentBreadCrumbs fragmentBreadCrumbs = (FragmentBreadCrumbs) findViewById(16908310);
                this.mFragmentBreadCrumbs = fragmentBreadCrumbs;
                if (fragmentBreadCrumbs == null) {
                    if (charSequence != null) {
                        setTitle(charSequence);
                        return;
                    }
                    return;
                }
                if (this.mSinglePane) {
                    fragmentBreadCrumbs.setVisibility(8);
                    View viewFindViewById = findViewById(R.id.breadcrumb_section);
                    if (viewFindViewById != null) {
                        viewFindViewById.setVisibility(8);
                    }
                    setTitle(charSequence);
                }
                this.mFragmentBreadCrumbs.setMaxVisible(2);
                this.mFragmentBreadCrumbs.setActivity(this);
            } catch (ClassCastException unused) {
                setTitle(charSequence);
                return;
            }
        }
        if (this.mFragmentBreadCrumbs.getVisibility() != 0) {
            setTitle(charSequence);
        } else {
            this.mFragmentBreadCrumbs.setTitle(charSequence, charSequence2);
            this.mFragmentBreadCrumbs.setParentTitle(null, null, null);
        }
    }

    public void setParentTitle(CharSequence charSequence, CharSequence charSequence2, View.OnClickListener onClickListener) throws Resources.NotFoundException {
        FragmentBreadCrumbs fragmentBreadCrumbs = this.mFragmentBreadCrumbs;
        if (fragmentBreadCrumbs != null) {
            fragmentBreadCrumbs.setParentTitle(charSequence, charSequence2, onClickListener);
        }
    }

    void setSelectedHeader(Header header) throws Resources.NotFoundException {
        this.mCurHeader = header;
        int iIndexOf = this.mHeaders.indexOf(header);
        if (iIndexOf >= 0) {
            getListView().setItemChecked(iIndexOf, true);
        } else {
            getListView().clearChoices();
        }
        showBreadCrumbs(header);
        updateBackCallbackRegistrationState();
    }

    void showBreadCrumbs(Header header) throws Resources.NotFoundException {
        if (header != null) {
            CharSequence breadCrumbTitle = header.getBreadCrumbTitle(getResources());
            if (breadCrumbTitle == null) {
                breadCrumbTitle = header.getTitle(getResources());
            }
            if (breadCrumbTitle == null) {
                breadCrumbTitle = getTitle();
            }
            showBreadCrumbs(breadCrumbTitle, header.getBreadCrumbShortTitle(getResources()));
            return;
        }
        showBreadCrumbs(getTitle(), null);
    }

    private void switchToHeaderInner(String str, Bundle bundle) throws ClassNotFoundException {
        getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        if (!isValidFragment(str)) {
            throw new IllegalArgumentException("Invalid fragment for this activity: " + str);
        }
        Fragment fragmentInstantiate = Fragment.instantiate(this, str, bundle);
        FragmentTransaction fragmentTransactionBeginTransaction = getFragmentManager().beginTransaction();
        if (!this.mInsideOnCreate) {
            fragmentTransactionBeginTransaction.setTransition(this.mSinglePane ? 0 : 4099);
        }
        fragmentTransactionBeginTransaction.replace(R.id.prefs, fragmentInstantiate);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        if (this.mSinglePane && this.mPrefsContainer.getVisibility() == 8) {
            this.mPrefsContainer.setVisibility(0);
            this.mHeadersContainer.setVisibility(8);
        }
    }

    public void switchToHeader(String str, Bundle bundle) throws Resources.NotFoundException, ClassNotFoundException {
        Header header;
        int i = 0;
        while (true) {
            if (i >= this.mHeaders.size()) {
                header = null;
                break;
            } else {
                if (str.equals(this.mHeaders.get(i).fragment)) {
                    header = this.mHeaders.get(i);
                    break;
                }
                i++;
            }
        }
        setSelectedHeader(header);
        switchToHeaderInner(str, bundle);
    }

    public void switchToHeader(Header header) throws Resources.NotFoundException, ClassNotFoundException {
        if (this.mCurHeader == header) {
            getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        } else {
            if (header.fragment == null) {
                throw new IllegalStateException("can't switch to header that has no fragment");
            }
            switchToHeaderInner(header.fragment, header.fragmentArguments);
            setSelectedHeader(header);
        }
    }

    Header findBestMatchingHeader(Header header, ArrayList<Header> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            Header header2 = arrayList.get(i);
            if (header == header2 || (header.id != -1 && header.id == header2.id)) {
                arrayList2.clear();
                arrayList2.add(header2);
                break;
            }
            if (header.fragment != null) {
                if (header.fragment.equals(header2.fragment)) {
                    arrayList2.add(header2);
                }
            } else if (header.intent != null) {
                if (header.intent.equals(header2.intent)) {
                    arrayList2.add(header2);
                }
            } else if (header.title != null && header.title.equals(header2.title)) {
                arrayList2.add(header2);
            }
        }
        int size = arrayList2.size();
        if (size == 1) {
            return (Header) arrayList2.get(0);
        }
        if (size <= 1) {
            return null;
        }
        for (int i2 = 0; i2 < size; i2++) {
            Header header3 = (Header) arrayList2.get(i2);
            if ((header.fragmentArguments != null && header.fragmentArguments.equals(header3.fragmentArguments)) || ((header.extras != null && header.extras.equals(header3.extras)) || (header.title != null && header.title.equals(header3.title)))) {
                return header3;
            }
        }
        return null;
    }

    public void startPreferenceFragment(Fragment fragment, boolean z) {
        FragmentTransaction fragmentTransactionBeginTransaction = getFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.prefs, fragment);
        if (z) {
            fragmentTransactionBeginTransaction.setTransition(4097);
            fragmentTransactionBeginTransaction.addToBackStack(BACK_STACK_PREFS);
        } else {
            fragmentTransactionBeginTransaction.setTransition(4099);
        }
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    public void startPreferencePanel(String str, Bundle bundle, int i, CharSequence charSequence, Fragment fragment, int i2) throws ClassNotFoundException {
        Fragment fragmentInstantiate = Fragment.instantiate(this, str, bundle);
        if (fragment != null) {
            fragmentInstantiate.setTargetFragment(fragment, i2);
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.prefs, fragmentInstantiate);
        if (i != 0) {
            fragmentTransactionBeginTransaction.setBreadCrumbTitle(i);
        } else if (charSequence != null) {
            fragmentTransactionBeginTransaction.setBreadCrumbTitle(charSequence);
        }
        fragmentTransactionBeginTransaction.setTransition(4097);
        fragmentTransactionBeginTransaction.addToBackStack(BACK_STACK_PREFS);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    public void finishPreferencePanel(Fragment fragment, int i, Intent intent) throws Resources.NotFoundException {
        onBackPressed();
        if (fragment == null || fragment.getTargetFragment() == null) {
            return;
        }
        fragment.getTargetFragment().onActivityResult(fragment.getTargetRequestCode(), i, intent);
    }

    @Override // android.preference.PreferenceFragment.OnPreferenceStartFragmentCallback
    public boolean onPreferenceStartFragment(PreferenceFragment preferenceFragment, Preference preference) throws ClassNotFoundException {
        startPreferencePanel(preference.getFragment(), preference.getExtras(), preference.getTitleRes(), preference.getTitle(), null, 0);
        return true;
    }

    private void postBindPreferences() {
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindPreferences() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.bind(getListView());
            Bundle bundle = this.mSavedInstanceState;
            if (bundle != null) {
                super.onRestoreInstanceState(bundle);
                this.mSavedInstanceState = null;
            }
        }
    }

    @Deprecated
    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    private void requirePreferenceManager() {
        if (this.mPreferenceManager == null) {
            if (this.mAdapter == null) {
                throw new RuntimeException("This should be called after super.onCreate.");
            }
            throw new RuntimeException("Modern two-pane PreferenceActivity requires use of a PreferenceFragment");
        }
    }

    @Deprecated
    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        requirePreferenceManager();
        if (!this.mPreferenceManager.setPreferences(preferenceScreen) || preferenceScreen == null) {
            return;
        }
        postBindPreferences();
        CharSequence title = getPreferenceScreen().getTitle();
        if (title != null) {
            setTitle(title);
        }
    }

    @Deprecated
    public PreferenceScreen getPreferenceScreen() {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            return preferenceManager.getPreferenceScreen();
        }
        return null;
    }

    @Deprecated
    public void addPreferencesFromIntent(Intent intent) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, getPreferenceScreen()));
    }

    @Deprecated
    public void addPreferencesFromResource(int i) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromResource(this, i, getPreferenceScreen()));
    }

    @Deprecated
    public Preference findPreference(CharSequence charSequence) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            return null;
        }
        return preferenceManager.findPreference(charSequence);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            preferenceManager.dispatchNewIntent(intent);
        }
    }

    protected boolean hasNextButton() {
        return this.mNextButton != null;
    }

    protected Button getNextButton() {
        return this.mNextButton;
    }
}
