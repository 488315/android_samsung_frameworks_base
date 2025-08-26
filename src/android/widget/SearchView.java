package android.widget;

import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.CollapsibleActionView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AdapterView;
import android.widget.TextView;
import com.android.internal.R;
import com.samsung.android.rune.ViewRune;
import java.io.IOException;
import java.util.WeakHashMap;

/* loaded from: classes5.dex */
public class SearchView extends LinearLayout implements CollapsibleActionView {
    private static final boolean DBG = false;
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private static final String LOG_TAG = "SearchView";
    private static final String SEM_AUTHORITY_SVI_APP = "com.samsung.android.honeyboard.provider.VoiceLanguageListProvider";
    private static final String SEM_KEY_SVI_APP_LOCALE = "is_honeyvoice_locale_supported";
    private static final String SEM_SVI_ACTION = "samsung.honeyboard.honeyvoice.action.RECOGNIZE_SPEECH";
    private static final String SEM_SVI_PACKAGE = "com.samsung.android.honeyboard";
    private static final int SEM_SVI_VERSION_SUPPORTING_SEARCH_QUERY = 220002001;
    private static final int SHOW_IME_WITH_HARDKEY = 1;
    private static final int TW_SEARCH_ICON_RES_ID = 17304888;
    private Bundle mAppSearchData;
    private final ImageView mBackButton;
    private boolean mClearingFocus;
    private final ImageView mCloseButton;
    private final ImageView mCollapsedIcon;
    private int mCollapsedImeOptions;
    private Context mContext;
    private final CharSequence mDefaultQueryHint;
    private final View mDropDownAnchor;
    private boolean mExpandedInActionView;
    private final ImageView mGoButton;
    private boolean mIconified;
    private boolean mIconifiedByDefault;
    private boolean mIsNight;
    private boolean mIsPenSupport;
    private int mMaxWidth;
    private final ImageView mMoreButton;
    private CharSequence mOldQueryText;
    private final View.OnClickListener mOnClickListener;
    private OnCloseListener mOnCloseListener;
    private final TextView.OnEditorActionListener mOnEditorActionListener;
    private final AdapterView.OnItemClickListener mOnItemClickListener;
    private final AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private OnQueryTextListener mOnQueryChangeListener;
    private View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private View.OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private Runnable mReleaseCursorRunnable;
    private Intent mSVISearchIntent;
    private final ImageView mSearchButton;
    private final View mSearchEditFrame;
    private final Drawable mSearchHintIcon;
    private final int mSearchIconResId;
    private final View mSearchPlate;
    private final SearchAutoComplete mSearchSrcTextView;
    private Rect mSearchSrcTextViewBounds;
    private Rect mSearchSrtTextViewBoundsExpanded;
    private SearchableInfo mSearchable;
    private final View mSubmitArea;
    private boolean mSubmitButtonEnabled;
    private final int mSuggestionCommitIconResId;
    private final int mSuggestionRowLayout;
    private CursorAdapter mSuggestionsAdapter;
    private int[] mTemp;
    private int[] mTemp2;
    View.OnKeyListener mTextKeyListener;
    private TextWatcher mTextWatcher;
    private boolean mThemeIsDeviceDefault;
    private UpdatableTouchDelegate mTouchDelegate;
    private Runnable mUpdateDrawableStateRunnable;
    private boolean mUseSVI;
    private CharSequence mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    private final ImageView mVoiceButton;
    private boolean mVoiceButtonEnabled;
    private final Intent mVoiceWebSearchIntent;

    public interface OnCloseListener {
        boolean onClose();
    }

    public interface OnQueryTextListener {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    public interface OnSuggestionListener {
        boolean onSuggestionClick(int i);

        boolean onSuggestionSelect(int i);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<SearchView> {
        private int mIconifiedByDefaultId;
        private int mIconifiedId;
        private int mMaxWidthId;
        private boolean mPropertiesMapped = false;
        private int mQueryHintId;
        private int mQueryId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mIconifiedId = propertyMapper.mapBoolean("iconified", 0);
            this.mIconifiedByDefaultId = propertyMapper.mapBoolean("iconifiedByDefault", 16843514);
            this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
            this.mQueryId = propertyMapper.mapObject("query", 0);
            this.mQueryHintId = propertyMapper.mapObject("queryHint", 16843608);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(SearchView searchView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mIconifiedId, searchView.isIconified());
            propertyReader.readBoolean(this.mIconifiedByDefaultId, searchView.isIconifiedByDefault());
            propertyReader.readInt(this.mMaxWidthId, searchView.getMaxWidth());
            propertyReader.readObject(this.mQueryId, searchView.getQuery());
            propertyReader.readObject(this.mQueryHintId, searchView.getQueryHint());
        }
    }

    public AutoCompleteTextView semGetAutoCompleteView() {
        return this.mSearchSrcTextView;
    }

    public void semSetUpButtonIcon(Drawable drawable) {
        ImageView imageView = this.mBackButton;
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$0(drawable);
        }
    }

    public void semSetOverflowMenuButtonIcon(Drawable drawable) {
        ImageView imageView = this.mMoreButton;
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$0(drawable);
        }
    }

    public void semSetUpButtonVisibility(int i) {
        ImageView imageView = this.mBackButton;
        if (imageView != null) {
            imageView.setVisibility(i);
        }
    }

    public void semSetOverflowMenuButtonVisibility(int i) {
        ImageView imageView = this.mMoreButton;
        if (imageView != null) {
            imageView.setVisibility(i);
        }
    }

    public void semSetOnUpButtonClickListener(View.OnClickListener onClickListener) {
        ImageView imageView = this.mBackButton;
        if (imageView != null) {
            imageView.setOnClickListener(onClickListener);
        }
    }

    public void semSetOnOverflowMenuButtonClickListener(View.OnClickListener onClickListener) {
        ImageView imageView = this.mMoreButton;
        if (imageView != null) {
            imageView.setOnClickListener(onClickListener);
        }
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843904);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        ImageView imageView;
        ImageView imageView2;
        super(context, attributeSet, i, i2);
        this.mSearchSrcTextViewBounds = new Rect();
        this.mSearchSrtTextViewBoundsExpanded = new Rect();
        this.mTemp = new int[2];
        this.mTemp2 = new int[2];
        this.mThemeIsDeviceDefault = false;
        this.mIsPenSupport = false;
        this.mUseSVI = false;
        this.mSVISearchIntent = null;
        this.mUpdateDrawableStateRunnable = new Runnable() { // from class: android.widget.SearchView.1
            @Override // java.lang.Runnable
            public void run() {
                SearchView.this.updateFocusedState();
            }
        };
        this.mReleaseCursorRunnable = new Runnable() { // from class: android.widget.SearchView.2
            @Override // java.lang.Runnable
            public void run() {
                if (SearchView.this.mSuggestionsAdapter == null || !(SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter)) {
                    return;
                }
                SearchView.this.mSuggestionsAdapter.changeCursor(null);
            }
        };
        this.mOutsideDrawablesCache = new WeakHashMap<>();
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: android.widget.SearchView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws Resources.NotFoundException {
                if (view == SearchView.this.mSearchButton) {
                    SearchView.this.onSearchClicked();
                    return;
                }
                if (view == SearchView.this.mCloseButton) {
                    SearchView.this.onCloseClicked();
                    return;
                }
                if (view == SearchView.this.mGoButton) {
                    SearchView.this.onSubmitQuery();
                } else if (view == SearchView.this.mVoiceButton) {
                    SearchView.this.onVoiceClicked();
                } else if (view == SearchView.this.mSearchSrcTextView) {
                    SearchView.this.forceSuggestionQuery();
                }
            }
        };
        this.mOnClickListener = onClickListener;
        this.mTextKeyListener = new View.OnKeyListener() { // from class: android.widget.SearchView.6
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i3, KeyEvent keyEvent) {
                SearchableInfo.ActionKeyInfo actionKeyInfoFindActionKey;
                if (SearchView.this.getContext().getPackageManager().hasSystemFeature(PackageManager.SEM_FEATURE_FOLDER_TYPE)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) SearchView.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (i3 == 23) {
                        inputMethodManager.viewClicked(view);
                        inputMethodManager.showSoftInput(view, 1);
                    }
                }
                if (SearchView.this.mSearchable == null) {
                    return false;
                }
                if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                    return SearchView.this.onSuggestionsKey(view, i3, keyEvent);
                }
                if (!SearchView.this.mSearchSrcTextView.isEmpty() && keyEvent.hasNoModifiers()) {
                    if (keyEvent.getAction() == 1 && (i3 == 66 || i3 == 160)) {
                        view.cancelLongPress();
                        SearchView searchView = SearchView.this;
                        searchView.launchQuerySearch(0, null, searchView.mSearchSrcTextView.getText().toString());
                        return true;
                    }
                    if (keyEvent.getAction() == 0 && (actionKeyInfoFindActionKey = SearchView.this.mSearchable.findActionKey(i3)) != null && actionKeyInfoFindActionKey.getQueryActionMsg() != null) {
                        SearchView.this.launchQuerySearch(i3, actionKeyInfoFindActionKey.getQueryActionMsg(), SearchView.this.mSearchSrcTextView.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        };
        TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() { // from class: android.widget.SearchView.7
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i3, KeyEvent keyEvent) {
                SearchView.this.onSubmitQuery();
                return true;
            }
        };
        this.mOnEditorActionListener = onEditorActionListener;
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() { // from class: android.widget.SearchView.8
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i3, long j) {
                SearchView.this.onItemClicked(i3, 0, null);
            }
        };
        this.mOnItemClickListener = onItemClickListener;
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() { // from class: android.widget.SearchView.9
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i3, long j) {
                SearchView.this.onItemSelected(i3);
            }
        };
        this.mOnItemSelectedListener = onItemSelectedListener;
        this.mTextWatcher = new TextWatcher() { // from class: android.widget.SearchView.10
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                SearchView.this.onTextChanged(charSequence);
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SearchView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.SearchView, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, R.layout.search_view);
        layoutInflater.inflate(resourceId, (ViewGroup) this, true);
        TypedValue typedValue = new TypedValue();
        this.mContext = context;
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, false);
        this.mThemeIsDeviceDefault = typedValue.data != 0;
        this.mIsNight = (getResources().getConfiguration().uiMode & 48) == 32;
        SearchAutoComplete searchAutoComplete = (SearchAutoComplete) findViewById(R.id.search_src_text);
        this.mSearchSrcTextView = searchAutoComplete;
        searchAutoComplete.setSearchView(this);
        this.mSearchEditFrame = findViewById(R.id.search_edit_frame);
        View viewFindViewById = findViewById(R.id.search_plate);
        this.mSearchPlate = viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.submit_area);
        this.mSubmitArea = viewFindViewById2;
        ImageView imageView3 = (ImageView) findViewById(R.id.search_button);
        this.mSearchButton = imageView3;
        ImageView imageView4 = (ImageView) findViewById(R.id.search_go_btn);
        this.mGoButton = imageView4;
        ImageView imageView5 = (ImageView) findViewById(R.id.search_close_btn);
        this.mCloseButton = imageView5;
        ImageView imageView6 = (ImageView) findViewById(R.id.search_voice_btn);
        this.mVoiceButton = imageView6;
        ImageView imageView7 = (ImageView) findViewById(R.id.search_mag_icon);
        this.mCollapsedIcon = imageView7;
        ImageView imageView8 = (ImageView) findViewById(R.id.search_more_btn);
        this.mMoreButton = imageView8;
        ImageView imageView9 = (ImageView) findViewById(R.id.search_back_btn);
        this.mBackButton = imageView9;
        viewFindViewById.setBackground(typedArrayObtainStyledAttributes.getDrawable(12));
        viewFindViewById2.setBackground(typedArrayObtainStyledAttributes.getDrawable(13));
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(8, 0);
        this.mSearchIconResId = resourceId2;
        imageView3.setImageResource(resourceId2);
        imageView4.lambda$setImageURIAsync$0(typedArrayObtainStyledAttributes.getDrawable(7));
        imageView5.lambda$setImageURIAsync$0(typedArrayObtainStyledAttributes.getDrawable(6));
        imageView6.lambda$setImageURIAsync$0(typedArrayObtainStyledAttributes.getDrawable(9));
        imageView7.lambda$setImageURIAsync$0(typedArrayObtainStyledAttributes.getDrawable(8));
        if (typedArrayObtainStyledAttributes.hasValueOrEmpty(14)) {
            this.mSearchHintIcon = typedArrayObtainStyledAttributes.getDrawable(14);
        } else {
            this.mSearchHintIcon = typedArrayObtainStyledAttributes.getDrawable(8);
        }
        this.mSuggestionRowLayout = typedArrayObtainStyledAttributes.getResourceId(11, R.layout.search_dropdown_item_icons_2line);
        this.mSuggestionCommitIconResId = typedArrayObtainStyledAttributes.getResourceId(10, 0);
        imageView3.setOnClickListener(onClickListener);
        imageView5.setOnClickListener(onClickListener);
        imageView4.setOnClickListener(onClickListener);
        imageView6.setOnClickListener(onClickListener);
        searchAutoComplete.setOnClickListener(onClickListener);
        searchAutoComplete.addTextChangedListener(this.mTextWatcher);
        searchAutoComplete.setOnEditorActionListener(onEditorActionListener);
        searchAutoComplete.setOnItemClickListener(onItemClickListener);
        searchAutoComplete.setOnItemSelectedListener(onItemSelectedListener);
        searchAutoComplete.setOnKeyListener(this.mTextKeyListener);
        imageView3.setTooltipText(imageView3.getContentDescription());
        imageView5.setTooltipText(imageView5.getContentDescription());
        imageView4.setTooltipText(imageView4.getContentDescription());
        imageView6.setTooltipText(imageView6.getContentDescription());
        if (resourceId == 17367542) {
            CharSequence contentDescription = imageView8.getContentDescription();
            imageView = imageView8;
            imageView.setTooltipText(contentDescription);
            imageView2 = imageView9;
            imageView2.setTooltipText(imageView9.getContentDescription());
        } else {
            imageView = imageView8;
            imageView2 = imageView9;
        }
        searchAutoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: android.widget.SearchView.3
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (SearchView.this.mOnQueryTextFocusChangeListener != null) {
                    SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange(SearchView.this, z);
                }
            }
        });
        setIconifiedByDefault(typedArrayObtainStyledAttributes.getBoolean(4, true));
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, -1);
        if (dimensionPixelSize != -1) {
            setMaxWidth(dimensionPixelSize);
        }
        this.mDefaultQueryHint = typedArrayObtainStyledAttributes.getText(15);
        this.mQueryHint = typedArrayObtainStyledAttributes.getText(5);
        int i3 = typedArrayObtainStyledAttributes.getInt(3, -1);
        if (i3 != -1) {
            setImeOptions(i3);
        }
        int i4 = typedArrayObtainStyledAttributes.getInt(2, -1);
        if (i4 != -1) {
            setInputType(i4);
        }
        if (getFocusable() == 16) {
            setFocusable(1);
        }
        if (this.mThemeIsDeviceDefault) {
            if (resourceId2 == 0) {
                imageView7.lambda$setImageURIAsync$0(getContext().getResources().getDrawable(17304888));
                imageView3.lambda$setImageURIAsync$0(getContext().getResources().getDrawable(17304888));
            }
            Resources resources = this.mContext.getResources();
            if (!this.mIsNight) {
                if (viewFindViewById.getBackground() == null) {
                    searchAutoComplete.setTextColor(resources.getColor(R.color.sem_search_view_no_background_text_color_light));
                    searchAutoComplete.setHintTextColor(resources.getColor(R.color.sem_search_view_no_background_hint_text_color_light));
                    imageView4.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView5.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView6.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView2.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                    imageView3.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_light));
                } else {
                    searchAutoComplete.setTextColor(resources.getColor(R.color.tw_searchview_text_material));
                    searchAutoComplete.setHintTextColor(resources.getColor(R.color.tw_searchview_hint_text_material));
                    imageView4.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView5.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView6.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView2.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                    imageView3.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material));
                }
            } else if (viewFindViewById.getBackground() == null) {
                searchAutoComplete.setTextColor(resources.getColor(R.color.sem_search_view_no_background_text_color_dark));
                searchAutoComplete.setHintTextColor(resources.getColor(R.color.sem_search_view_no_background_hint_text_color_dark));
                imageView4.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView5.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView6.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView2.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
                imageView3.setColorFilter(resources.getColor(R.color.sem_search_view_no_background_icon_color_dark));
            } else {
                searchAutoComplete.setTextColor(resources.getColor(R.color.tw_searchview_text_material_dark));
                searchAutoComplete.setHintTextColor(resources.getColor(R.color.tw_searchview_hint_text_material_dark));
                imageView4.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView5.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView6.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView2.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
                imageView3.setColorFilter(resources.getColor(R.color.tw_searchview_search_icon_color_material_dark));
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        this.mVoiceWebSearchIntent = intent;
        intent.addFlags(268435456);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        this.mVoiceAppSearchIntent = intent2;
        intent2.addFlags(268435456);
        View viewFindViewById3 = findViewById(searchAutoComplete.getDropDownAnchor());
        this.mDropDownAnchor = viewFindViewById3;
        if (viewFindViewById3 != null) {
            viewFindViewById3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: android.widget.SearchView.4
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
                    SearchView.this.adjustDropDownSizeAndPosition();
                }
            });
        }
        updateViewsVisibility(this.mIconifiedByDefault);
        updateQueryHint();
        if (ViewRune.WIDGET_SEARCHVIEW_USE_SVI) {
            Intent intent3 = new Intent(SEM_SVI_ACTION);
            this.mSVISearchIntent = intent3;
            intent3.addFlags(268435456);
        }
        if (this.mThemeIsDeviceDefault) {
            semCheckMaxFontSize();
        }
    }

    int getSuggestionRowLayout() {
        return this.mSuggestionRowLayout;
    }

    int getSuggestionCommitIconResId() {
        return this.mSuggestionCommitIconResId;
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.mSearchable = searchableInfo;
        if (searchableInfo != null) {
            updateSearchAutoComplete();
            updateQueryHint();
        }
        boolean zHasVoiceSearch = hasVoiceSearch();
        this.mVoiceButtonEnabled = zHasVoiceSearch;
        if (zHasVoiceSearch && !this.mThemeIsDeviceDefault) {
            this.mSearchSrcTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
        }
        updateViewsVisibility(isIconified());
    }

    public void setAppSearchData(Bundle bundle) {
        this.mAppSearchData = bundle;
    }

    public void setImeOptions(int i) {
        this.mSearchSrcTextView.setImeOptions(i);
    }

    public int getImeOptions() {
        return this.mSearchSrcTextView.getImeOptions();
    }

    public void setInputType(int i) {
        this.mSearchSrcTextView.setInputType(i);
    }

    public int getInputType() {
        return this.mSearchSrcTextView.getInputType();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean requestFocus(int i, Rect rect) {
        if (this.mClearingFocus || !isFocusable()) {
            return false;
        }
        if (isIconified()) {
            return super.requestFocus(i, rect);
        }
        if (i == 1) {
            View viewFocusSearch = focusSearch(1);
            if (viewFocusSearch != null) {
                return viewFocusSearch.requestFocus(1, rect);
            }
            return false;
        }
        boolean zRequestFocus = this.mSearchSrcTextView.requestFocus(i, rect);
        if (zRequestFocus) {
            updateViewsVisibility(false);
        }
        return zRequestFocus;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void clearFocus() {
        this.mClearingFocus = true;
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mSearchSrcTextView.setImeVisibility(false);
        this.mClearingFocus = false;
    }

    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        this.mOnQueryChangeListener = onQueryTextListener;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mOnQueryTextFocusChangeListener = onFocusChangeListener;
    }

    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        this.mOnSuggestionListener = onSuggestionListener;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.mOnSearchClickListener = onClickListener;
    }

    public CharSequence getQuery() {
        return this.mSearchSrcTextView.getText();
    }

    public void setQuery(CharSequence charSequence, boolean z) {
        this.mSearchSrcTextView.lambda$setTextAsync$0(charSequence);
        if (charSequence != null) {
            SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
            searchAutoComplete.setSelection(searchAutoComplete.length());
            this.mUserQuery = charSequence;
        }
        if (!z || TextUtils.isEmpty(charSequence)) {
            return;
        }
        onSubmitQuery();
    }

    public void setQueryHint(CharSequence charSequence) {
        this.mQueryHint = charSequence;
        updateQueryHint();
    }

    public CharSequence getQueryHint() {
        CharSequence charSequence = this.mQueryHint;
        if (charSequence != null) {
            return charSequence;
        }
        SearchableInfo searchableInfo = this.mSearchable;
        if (searchableInfo != null && searchableInfo.getHintId() != 0) {
            return getContext().getText(this.mSearchable.getHintId());
        }
        return this.mDefaultQueryHint;
    }

    public void setIconifiedByDefault(boolean z) {
        if (this.mIconifiedByDefault == z) {
            return;
        }
        this.mIconifiedByDefault = z;
        updateViewsVisibility(z);
        updateQueryHint();
    }

    @Deprecated
    public boolean isIconfiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public boolean isIconifiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public void setIconified(boolean z) {
        if (z) {
            onCloseClicked();
        } else {
            onSearchClicked();
        }
    }

    public boolean isIconified() {
        return this.mIconified;
    }

    public void setSubmitButtonEnabled(boolean z) {
        this.mSubmitButtonEnabled = z;
        updateViewsVisibility(isIconified());
    }

    public boolean isSubmitButtonEnabled() {
        return this.mSubmitButtonEnabled;
    }

    public void setQueryRefinementEnabled(boolean z) {
        this.mQueryRefinement = z;
        CursorAdapter cursorAdapter = this.mSuggestionsAdapter;
        if (cursorAdapter instanceof SuggestionsAdapter) {
            ((SuggestionsAdapter) cursorAdapter).setQueryRefinement(z ? 2 : 1);
        }
    }

    public boolean isQueryRefinementEnabled() {
        return this.mQueryRefinement;
    }

    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        this.mSuggestionsAdapter = cursorAdapter;
        this.mSearchSrcTextView.setAdapter(cursorAdapter);
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.mSuggestionsAdapter;
    }

    public void setMaxWidth(int i) {
        this.mMaxWidth = i;
        requestLayout();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        if (isIconified()) {
            super.onMeasure(i, i2);
            return;
        }
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == Integer.MIN_VALUE) {
            int i4 = this.mMaxWidth;
            if (i4 > 0) {
                size = Math.min(i4, size);
            } else if (!this.mThemeIsDeviceDefault) {
                size = Math.min(getPreferredWidth(), size);
            }
        } else if (mode == 0) {
            size = this.mMaxWidth;
            if (size <= 0) {
                size = getPreferredWidth();
            }
        } else if (mode == 1073741824 && (i3 = this.mMaxWidth) > 0) {
            size = Math.min(i3, size);
        }
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(getPreferredHeight(), size2);
        } else if (mode2 == 0) {
            size2 = getPreferredHeight();
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            getChildBoundsWithinSearchView(this.mSearchSrcTextView, this.mSearchSrcTextViewBounds);
            this.mSearchSrtTextViewBoundsExpanded.set(this.mSearchSrcTextViewBounds.left, 0, this.mSearchSrcTextViewBounds.right, i4 - i2);
            UpdatableTouchDelegate updatableTouchDelegate = this.mTouchDelegate;
            if (updatableTouchDelegate == null) {
                UpdatableTouchDelegate updatableTouchDelegate2 = new UpdatableTouchDelegate(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds, this.mSearchSrcTextView);
                this.mTouchDelegate = updatableTouchDelegate2;
                setTouchDelegate(updatableTouchDelegate2);
                return;
            }
            updatableTouchDelegate.setBounds(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds);
        }
    }

    private void getChildBoundsWithinSearchView(View view, Rect rect) {
        view.getLocationInWindow(this.mTemp);
        getLocationInWindow(this.mTemp2);
        int[] iArr = this.mTemp;
        int i = iArr[1];
        int[] iArr2 = this.mTemp2;
        int i2 = i - iArr2[1];
        int i3 = iArr[0] - iArr2[0];
        rect.set(i3, i2, view.getWidth() + i3, view.getHeight() + i2);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.search_view_preferred_width);
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.search_view_preferred_height);
    }

    private void updateViewsVisibility(boolean z) {
        this.mIconified = z;
        int i = z ? 0 : 8;
        boolean zIsEmpty = TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        this.mSearchButton.setVisibility(i);
        updateSubmitButton(!zIsEmpty);
        this.mSearchEditFrame.setVisibility(z ? 8 : 0);
        this.mCollapsedIcon.setVisibility((this.mCollapsedIcon.getDrawable() == null || this.mIconifiedByDefault || this.mThemeIsDeviceDefault) ? 8 : 0);
        updateCloseButton();
        updateVoiceButton(zIsEmpty);
        updateSubmitArea();
    }

    private boolean hasVoiceSearch() {
        Intent intent;
        SearchableInfo searchableInfo = this.mSearchable;
        if (searchableInfo != null && searchableInfo.getVoiceSearchEnabled()) {
            if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
                intent = this.mVoiceWebSearchIntent;
            } else if (!this.mSearchable.getVoiceSearchLaunchRecognizer()) {
                intent = null;
            } else if (ViewRune.WIDGET_SEARCHVIEW_USE_SVI && this.mUseSVI) {
                intent = this.mSVISearchIntent;
            } else {
                intent = this.mVoiceAppSearchIntent;
            }
            if (intent != null && getContext().getPackageManager().resolveActivity(intent, 65536) != null) {
                return true;
            }
        }
        return false;
    }

    private boolean isSubmitAreaEnabled() {
        return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !isIconified();
    }

    private void updateSubmitButton(boolean z) {
        this.mGoButton.setVisibility((this.mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus() && (z || !this.mVoiceButtonEnabled)) ? 0 : 8);
    }

    private void updateSubmitArea() {
        this.mSubmitArea.setVisibility((isSubmitAreaEnabled() && (this.mGoButton.getVisibility() == 0 || this.mVoiceButton.getVisibility() == 0)) ? 0 : 8);
    }

    private void updateCloseButton() {
        boolean zIsEmpty = TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        boolean z = !zIsEmpty || (this.mIconifiedByDefault && !this.mExpandedInActionView);
        if (this.mThemeIsDeviceDefault) {
            this.mCloseButton.setVisibility(zIsEmpty ? 8 : 0);
        } else {
            this.mCloseButton.setVisibility(z ? 0 : 8);
        }
        Drawable drawable = this.mCloseButton.getDrawable();
        if (drawable != null) {
            drawable.setState(!zIsEmpty ? ENABLED_STATE_SET : EMPTY_STATE_SET);
        }
    }

    private void postUpdateFocusedState() {
        post(this.mUpdateDrawableStateRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFocusedState() {
        int[] iArr = this.mSearchSrcTextView.hasFocus() ? FOCUSED_STATE_SET : EMPTY_STATE_SET;
        Drawable background = this.mSearchPlate.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.mSubmitArea.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.mUpdateDrawableStateRunnable);
        post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    void onQueryRefine(CharSequence charSequence) {
        setQuery(charSequence);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        SearchableInfo searchableInfo = this.mSearchable;
        if (searchableInfo == null) {
            return false;
        }
        SearchableInfo.ActionKeyInfo actionKeyInfoFindActionKey = searchableInfo.findActionKey(i);
        if (actionKeyInfoFindActionKey != null && actionKeyInfoFindActionKey.getQueryActionMsg() != null) {
            launchQuerySearch(i, actionKeyInfoFindActionKey.getQueryActionMsg(), this.mSearchSrcTextView.getText().toString());
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onSuggestionsKey(View view, int i, KeyEvent keyEvent) {
        SearchableInfo.ActionKeyInfo actionKeyInfoFindActionKey;
        int listSelection;
        String actionKeyMessage;
        if (this.mSearchable != null && this.mSuggestionsAdapter != null && keyEvent.getAction() == 0 && keyEvent.hasNoModifiers()) {
            if (i == 66 || i == 84 || i == 61 || i == 160) {
                return onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
            }
            if (i == 21 || i == 22) {
                this.mSearchSrcTextView.setSelection(i == 21 ? 0 : this.mSearchSrcTextView.length());
                this.mSearchSrcTextView.setListSelection(0);
                this.mSearchSrcTextView.clearListSelection();
                this.mSearchSrcTextView.ensureImeVisible(true);
                return true;
            }
            if ((i != 19 || this.mSearchSrcTextView.getListSelection() != 0) && (actionKeyInfoFindActionKey = this.mSearchable.findActionKey(i)) != null && ((actionKeyInfoFindActionKey.getSuggestActionMsg() != null || actionKeyInfoFindActionKey.getSuggestActionMsgColumn() != null) && (listSelection = this.mSearchSrcTextView.getListSelection()) != -1)) {
                Cursor cursor = this.mSuggestionsAdapter.getCursor();
                if (cursor.moveToPosition(listSelection) && (actionKeyMessage = getActionKeyMessage(cursor, actionKeyInfoFindActionKey)) != null && actionKeyMessage.length() > 0) {
                    return onItemClicked(listSelection, i, actionKeyMessage);
                }
            }
        }
        return false;
    }

    private static String getActionKeyMessage(Cursor cursor, SearchableInfo.ActionKeyInfo actionKeyInfo) {
        String suggestActionMsgColumn = actionKeyInfo.getSuggestActionMsgColumn();
        String columnString = suggestActionMsgColumn != null ? SuggestionsAdapter.getColumnString(cursor, suggestActionMsgColumn) : null;
        return columnString == null ? actionKeyInfo.getSuggestActionMsg() : columnString;
    }

    private CharSequence getDecoratedHint(CharSequence charSequence) {
        int i = this.mSearchIconResId;
        if (this.mThemeIsDeviceDefault || !this.mIconifiedByDefault || this.mSearchHintIcon == null) {
            return charSequence;
        }
        int textSize = (int) (this.mSearchSrcTextView.getTextSize() * 1.25d);
        if (this.mThemeIsDeviceDefault) {
            Drawable drawable = getContext().getDrawable(i);
            drawable.setBounds(1, -15, textSize + 1, textSize - 15);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("  ");
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.setSpan(new ImageSpan(drawable), 0, 1, 33);
            return spannableStringBuilder;
        }
        this.mSearchHintIcon.setBounds(0, 0, textSize, textSize);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder("   ");
        spannableStringBuilder2.setSpan(new ImageSpan(this.mSearchHintIcon), 1, 2, 33);
        spannableStringBuilder2.append(charSequence);
        return spannableStringBuilder2;
    }

    private void updateQueryHint() {
        CharSequence queryHint = getQueryHint();
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        if (queryHint == null) {
            queryHint = "";
        }
        searchAutoComplete.setHint(getDecoratedHint(queryHint));
    }

    private void updateSearchAutoComplete() {
        this.mSearchSrcTextView.setDropDownAnimationStyle(0);
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
        int inputType = this.mSearchable.getInputType();
        if ((inputType & 15) == 1) {
            inputType &= -65537;
            if (this.mSearchable.getSuggestAuthority() != null) {
                inputType = !this.mThemeIsDeviceDefault ? inputType | 589824 : 65536 | inputType;
            }
        }
        this.mSearchSrcTextView.setInputType(inputType);
        CursorAdapter cursorAdapter = this.mSuggestionsAdapter;
        if (cursorAdapter != null) {
            cursorAdapter.changeCursor(null);
        }
        if (this.mSearchable.getSuggestAuthority() != null) {
            SuggestionsAdapter suggestionsAdapter = new SuggestionsAdapter(getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
            this.mSuggestionsAdapter = suggestionsAdapter;
            this.mSearchSrcTextView.setAdapter(suggestionsAdapter);
            ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement(this.mQueryRefinement ? 2 : 1);
        }
    }

    private void updateVoiceButton(boolean z) {
        int i = 8;
        if (this.mVoiceButtonEnabled && !isIconified() && z) {
            this.mGoButton.setVisibility(8);
            i = 0;
        }
        this.mVoiceButton.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTextChanged(CharSequence charSequence) {
        Editable text = this.mSearchSrcTextView.getText();
        this.mUserQuery = text;
        boolean zIsEmpty = TextUtils.isEmpty(text);
        updateSubmitButton(!zIsEmpty);
        updateVoiceButton(zIsEmpty);
        updateCloseButton();
        updateSubmitArea();
        if (TextUtils.equals(charSequence, this.mOldQueryText)) {
            return;
        }
        this.mOldQueryText = charSequence.toString();
        OnQueryTextListener onQueryTextListener = this.mOnQueryChangeListener;
        if (onQueryTextListener != null) {
            onQueryTextListener.onQueryTextChange(charSequence.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubmitQuery() {
        Editable text = this.mSearchSrcTextView.getText();
        if (text == null || TextUtils.getTrimmedLength(text) <= 0) {
            return;
        }
        OnQueryTextListener onQueryTextListener = this.mOnQueryChangeListener;
        if (onQueryTextListener == null || !onQueryTextListener.onQueryTextSubmit(text.toString())) {
            if (this.mSearchable != null) {
                launchQuerySearch(0, null, text.toString());
            }
            this.mSearchSrcTextView.setImeVisibility(false);
            dismissSuggestions();
        }
    }

    private void dismissSuggestions() {
        this.mSearchSrcTextView.dismissDropDown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCloseClicked() {
        if (TextUtils.isEmpty(this.mSearchSrcTextView.getText())) {
            if (this.mIconifiedByDefault) {
                OnCloseListener onCloseListener = this.mOnCloseListener;
                if (onCloseListener == null || !onCloseListener.onClose()) {
                    clearFocus();
                    updateViewsVisibility(true);
                    return;
                }
                return;
            }
            return;
        }
        this.mSearchSrcTextView.lambda$setTextAsync$0("");
        this.mSearchSrcTextView.requestFocus();
        if (semIsForceHideSoftInput()) {
            this.mSearchSrcTextView.setImeVisibility(false);
        } else {
            this.mSearchSrcTextView.setImeVisibility(true);
        }
    }

    boolean semIsForceHideSoftInput() {
        int iIsAccessoryKeyboardState = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).isAccessoryKeyboardState();
        Resources resources = getContext().getResources();
        if (getViewRootImpl() == null || resources.getConfiguration().semDesktopModeEnabled == 1) {
            return (iIsAccessoryKeyboardState == 0 || Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD, 0) == 1) ? false : true;
        }
        return iIsAccessoryKeyboardState != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSearchClicked() {
        updateViewsVisibility(false);
        this.mSearchSrcTextView.requestFocus();
        if (semIsForceHideSoftInput()) {
            this.mSearchSrcTextView.setImeVisibility(false);
        } else {
            this.mSearchSrcTextView.setImeVisibility(true);
        }
        View.OnClickListener onClickListener = this.mOnSearchClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVoiceClicked() throws Resources.NotFoundException {
        SearchableInfo searchableInfo = this.mSearchable;
        if (searchableInfo == null) {
            return;
        }
        try {
            if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                getContext().startActivity(createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchableInfo));
            } else if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                if (this.mUseSVI) {
                    getContext().startActivity(createSVoiceSearchIntent(this.mSVISearchIntent, searchableInfo));
                } else {
                    getContext().startActivity(createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchableInfo));
                }
            }
        } catch (ActivityNotFoundException unused) {
            Log.w(LOG_TAG, "Could not find voice search activity");
        }
    }

    void onTextFocusChanged() {
        updateViewsVisibility(isIconified());
        postUpdateFocusedState();
        if (this.mSearchSrcTextView.hasFocus()) {
            forceSuggestionQuery();
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        postUpdateFocusedState();
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        if (this.mThemeIsDeviceDefault) {
            this.mSearchPlate.setBackground(drawable);
        } else {
            super.setBackground(drawable);
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        if (this.mThemeIsDeviceDefault) {
            this.mSearchPlate.setBackground(getContext().getResources().getDrawable(i));
        } else {
            super.setBackgroundResource(i);
        }
    }

    @Override // android.view.View
    public void setElevation(float f) {
        if (this.mThemeIsDeviceDefault) {
            this.mSearchPlate.setElevation(f);
        } else {
            super.setElevation(f);
        }
    }

    @Override // android.view.CollapsibleActionView
    public void onActionViewCollapsed() {
        setQuery("", false);
        clearFocus();
        updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    @Override // android.view.CollapsibleActionView
    public void onActionViewExpanded() {
        if (this.mExpandedInActionView) {
            return;
        }
        this.mExpandedInActionView = true;
        int imeOptions = this.mSearchSrcTextView.getImeOptions();
        this.mCollapsedImeOptions = imeOptions;
        this.mSearchSrcTextView.setImeOptions(imeOptions | 33554432);
        this.mSearchSrcTextView.lambda$setTextAsync$0("");
        setIconified(false);
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.SearchView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean isIconified;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.isIconified = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) throws IOException {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.isIconified));
        }

        public String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.isIconified + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isIconified = isIconified();
        return savedState;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        updateViewsVisibility(savedState.isIconified);
        requestLayout();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return SearchView.class.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustDropDownSizeAndPosition() {
        int i;
        if (this.mDropDownAnchor.getWidth() > 1) {
            Resources resources = getContext().getResources();
            int paddingLeft = this.mSearchPlate.getPaddingLeft();
            Rect rect = new Rect();
            boolean zIsLayoutRtl = isLayoutRtl();
            int i2 = 0;
            int dimensionPixelSize = this.mIconifiedByDefault ? resources.getDimensionPixelSize(R.dimen.dropdownitem_icon_width) + resources.getDimensionPixelSize(R.dimen.dropdownitem_text_padding_left) : 0;
            if (this.mThemeIsDeviceDefault) {
                paddingLeft = 0;
            } else {
                i2 = dimensionPixelSize;
            }
            this.mSearchSrcTextView.getDropDownBackground().getPadding(rect);
            if (zIsLayoutRtl) {
                i = -rect.left;
            } else {
                i = paddingLeft - (rect.left + i2);
            }
            this.mSearchSrcTextView.setDropDownHorizontalOffset(i);
            this.mSearchSrcTextView.setDropDownWidth((((this.mDropDownAnchor.getWidth() + rect.left) + rect.right) + i2) - paddingLeft);
            if (this.mSearchSrcTextView.isPopupShowing()) {
                this.mSearchSrcTextView.showDropDown();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onItemClicked(int i, int i2, String str) {
        OnSuggestionListener onSuggestionListener = this.mOnSuggestionListener;
        if (onSuggestionListener != null && onSuggestionListener.onSuggestionClick(i)) {
            return false;
        }
        launchSuggestion(i, 0, null);
        this.mSearchSrcTextView.setImeVisibility(false);
        dismissSuggestions();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onItemSelected(int i) {
        OnSuggestionListener onSuggestionListener = this.mOnSuggestionListener;
        if (onSuggestionListener != null && onSuggestionListener.onSuggestionSelect(i)) {
            return false;
        }
        rewriteQueryFromSuggestion(i);
        return true;
    }

    private void rewriteQueryFromSuggestion(int i) {
        Editable text = this.mSearchSrcTextView.getText();
        Cursor cursor = this.mSuggestionsAdapter.getCursor();
        if (cursor == null) {
            return;
        }
        if (cursor.moveToPosition(i)) {
            CharSequence charSequenceConvertToString = this.mSuggestionsAdapter.convertToString(cursor);
            if (charSequenceConvertToString != null) {
                setQuery(charSequenceConvertToString);
                return;
            } else {
                setQuery(text);
                return;
            }
        }
        setQuery(text);
    }

    private boolean launchSuggestion(int i, int i2, String str) {
        Cursor cursor = this.mSuggestionsAdapter.getCursor();
        if (cursor == null || !cursor.moveToPosition(i)) {
            return false;
        }
        launchIntent(createIntentFromSuggestion(cursor, i2, str));
        return true;
    }

    private void launchIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            getContext().startActivity(intent);
        } catch (RuntimeException e) {
            Log.e(LOG_TAG, "Failed launch activity: " + intent, e);
        }
    }

    private void setQuery(CharSequence charSequence) {
        this.mSearchSrcTextView.setText(charSequence, true);
        this.mSearchSrcTextView.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchQuerySearch(int i, String str, String str2) {
        getContext().startActivity(createIntent(Intent.ACTION_SEARCH, null, null, str2, i, str));
    }

    private Intent createIntent(String str, Uri uri, String str2, String str3, int i, String str4) {
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra(SearchManager.USER_QUERY, this.mUserQuery);
        if (str3 != null) {
            intent.putExtra("query", str3);
        }
        if (str2 != null) {
            intent.putExtra(SearchManager.EXTRA_DATA_KEY, str2);
        }
        Bundle bundle = this.mAppSearchData;
        if (bundle != null) {
            intent.putExtra(SearchManager.APP_DATA, bundle);
        }
        if (i != 0) {
            intent.putExtra(SearchManager.ACTION_KEY, i);
            intent.putExtra(SearchManager.ACTION_MSG, str4);
        }
        intent.setComponent(this.mSearchable.getSearchActivity());
        return intent;
    }

    private Intent createVoiceWebSearchIntent(Intent intent, SearchableInfo searchableInfo) {
        Intent intent2 = new Intent(intent);
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        intent2.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
        return intent2;
    }

    private Intent createVoiceAppSearchIntent(Intent intent, SearchableInfo searchableInfo) throws Resources.NotFoundException {
        String string;
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        Intent intent2 = new Intent(Intent.ACTION_SEARCH);
        intent2.setComponent(searchActivity);
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1107296256);
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.mAppSearchData;
        if (bundle2 != null) {
            bundle.putParcelable(SearchManager.APP_DATA, bundle2);
        }
        Intent intent3 = new Intent(intent);
        Resources resources = getResources();
        if (searchableInfo.getVoiceLanguageModeId() == 0) {
            string = RecognizerIntent.LANGUAGE_MODEL_FREE_FORM;
        } else {
            string = resources.getString(searchableInfo.getVoiceLanguageModeId());
        }
        String string2 = searchableInfo.getVoicePromptTextId() != 0 ? resources.getString(searchableInfo.getVoicePromptTextId()) : null;
        String string3 = searchableInfo.getVoiceLanguageId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageId()) : null;
        int voiceMaxResults = searchableInfo.getVoiceMaxResults() != 0 ? searchableInfo.getVoiceMaxResults() : 1;
        intent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, string);
        intent3.putExtra(RecognizerIntent.EXTRA_PROMPT, string2);
        intent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE, string3);
        intent3.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, voiceMaxResults);
        intent3.putExtra("calling_package", searchActivity != null ? searchActivity.flattenToShortString() : null);
        intent3.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT, activity);
        intent3.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT_BUNDLE, bundle);
        return intent3;
    }

    private Intent createIntentFromSuggestion(Cursor cursor, int i, String str) {
        int position;
        String columnString;
        try {
            String columnString2 = SuggestionsAdapter.getColumnString(cursor, SearchManager.SUGGEST_COLUMN_INTENT_ACTION);
            if (columnString2 == null) {
                columnString2 = this.mSearchable.getSuggestIntentAction();
            }
            if (columnString2 == null) {
                columnString2 = Intent.ACTION_SEARCH;
            }
            String str2 = columnString2;
            String columnString3 = SuggestionsAdapter.getColumnString(cursor, SearchManager.SUGGEST_COLUMN_INTENT_DATA);
            if (columnString3 == null) {
                columnString3 = this.mSearchable.getSuggestIntentData();
            }
            if (columnString3 != null && (columnString = SuggestionsAdapter.getColumnString(cursor, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID)) != null) {
                columnString3 = columnString3 + "/" + Uri.encode(columnString);
            }
            return createIntent(str2, columnString3 == null ? null : Uri.parse(columnString3), SuggestionsAdapter.getColumnString(cursor, SearchManager.SUGGEST_COLUMN_INTENT_EXTRA_DATA), SuggestionsAdapter.getColumnString(cursor, SearchManager.SUGGEST_COLUMN_QUERY), i, str);
        } catch (RuntimeException e) {
            try {
                position = cursor.getPosition();
            } catch (RuntimeException unused) {
                position = -1;
            }
            Log.w(LOG_TAG, "Search suggestions cursor at row " + position + " returned exception.", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceSuggestionQuery() {
        this.mSearchSrcTextView.doBeforeTextChanged();
        this.mSearchSrcTextView.doAfterTextChanged();
    }

    static boolean isLandscapeMode(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private static class UpdatableTouchDelegate extends TouchDelegate {
        private final Rect mActualBounds;
        private boolean mDelegateTargeted;
        private final View mDelegateView;
        private final int mSlop;
        private final Rect mSlopBounds;
        private final Rect mTargetBounds;

        public UpdatableTouchDelegate(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.mSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            this.mTargetBounds = new Rect();
            this.mSlopBounds = new Rect();
            this.mActualBounds = new Rect();
            setBounds(rect, rect2);
            this.mDelegateView = view;
        }

        public void setBounds(Rect rect, Rect rect2) {
            this.mTargetBounds.set(rect);
            this.mSlopBounds.set(rect);
            Rect rect3 = this.mSlopBounds;
            int i = this.mSlop;
            rect3.inset(-i, -i);
            this.mActualBounds.set(rect2);
        }

        @Override // android.view.TouchDelegate
        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z;
            boolean z2;
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int action = motionEvent.getAction();
            boolean z3 = true;
            if (action != 0) {
                if (action == 1 || action == 2) {
                    z2 = this.mDelegateTargeted;
                    if (z2 && !this.mSlopBounds.contains(x, y)) {
                        z3 = z2;
                        z = false;
                    }
                } else {
                    if (action == 3) {
                        z2 = this.mDelegateTargeted;
                        this.mDelegateTargeted = false;
                    }
                    z = true;
                    z3 = false;
                }
                z3 = z2;
                z = true;
            } else if (this.mTargetBounds.contains(x, y)) {
                this.mDelegateTargeted = true;
                z = true;
            } else {
                z = true;
                z3 = false;
            }
            if (!z3) {
                return false;
            }
            if (z && !this.mActualBounds.contains(x, y)) {
                motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
            } else {
                motionEvent.setLocation(x - this.mActualBounds.left, y - this.mActualBounds.top);
            }
            return this.mDelegateView.dispatchTouchEvent(motionEvent);
        }
    }

    public static class SearchAutoComplete extends AutoCompleteTextView {
        private boolean mHasPendingShowSoftInputRequest;
        final Runnable mRunShowSoftInputIfNecessary;
        private SearchView mSearchView;
        private int mThreshold;

        @Override // android.widget.AutoCompleteTextView
        public void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView
        protected void replaceText(CharSequence charSequence) {
        }

        public SearchAutoComplete(Context context) {
            super(context);
            this.mRunShowSoftInputIfNecessary = new Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mRunShowSoftInputIfNecessary = new Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.mRunShowSoftInputIfNecessary = new Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mRunShowSoftInputIfNecessary = new Runnable() { // from class: android.widget.SearchView$SearchAutoComplete$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            };
            this.mThreshold = getThreshold();
        }

        @Override // android.view.View
        protected void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        void setSearchView(SearchView searchView) {
            this.mSearchView = searchView;
        }

        @Override // android.widget.AutoCompleteTextView
        public void setThreshold(int i) {
            super.setThreshold(i);
            this.mThreshold = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (!this.mSearchView.semIsForceHideSoftInput() && z && this.mSearchView.hasFocus() && getVisibility() == 0) {
                this.mHasPendingShowSoftInputRequest = true;
                if (SearchView.isLandscapeMode(getContext())) {
                    ensureImeVisible(true);
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        protected void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            this.mSearchView.onTextFocusChanged();
        }

        @Override // android.widget.AutoCompleteTextView
        public boolean enoughToFilter() {
            return this.mThreshold <= 0 || super.enoughToFilter();
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            boolean zOnKeyPreIme = super.onKeyPreIme(i, keyEvent);
            if (!this.mSearchView.mThemeIsDeviceDefault && zOnKeyPreIme && i == 4 && keyEvent.getAction() == 1) {
                setImeVisibility(false);
            }
            return zOnKeyPreIme;
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int i = configuration.screenWidthDp;
            int i2 = configuration.screenHeightDp;
            int i3 = configuration.orientation;
            if (i >= 960 && i2 >= 720 && i3 == 2) {
                return 256;
            }
            if (i < 600) {
                return (i < 640 || i2 < 480) ? 160 : 192;
            }
            return 192;
        }

        @Override // android.widget.TextView, android.view.View
        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.mHasPendingShowSoftInputRequest) {
                removeCallbacks(this.mRunShowSoftInputIfNecessary);
                post(this.mRunShowSoftInputIfNecessary);
            }
            return inputConnectionOnCreateInputConnection;
        }

        @Override // android.view.View
        public boolean checkInputConnectionProxy(View view) {
            return view == this.mSearchView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: showSoftInputIfNecessary, reason: merged with bridge method [inline-methods] */
        public void lambda$new$0() {
            if (this.mHasPendingShowSoftInputRequest) {
                ((InputMethodManager) getContext().getSystemService(InputMethodManager.class)).showSoftInput(this, 0);
                this.mHasPendingShowSoftInputRequest = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setImeVisibility(boolean z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
            if (!z) {
                this.mHasPendingShowSoftInputRequest = false;
                removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else {
                if (inputMethodManager.hasActiveInputConnection(this)) {
                    this.mHasPendingShowSoftInputRequest = false;
                    removeCallbacks(this.mRunShowSoftInputIfNecessary);
                    inputMethodManager.showSoftInput(this, 0);
                    return;
                }
                this.mHasPendingShowSoftInputRequest = true;
            }
        }
    }

    private Intent createSVoiceSearchIntent(Intent intent, SearchableInfo searchableInfo) {
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        Intent intent2 = new Intent(Intent.ACTION_SEARCH);
        intent2.setComponent(searchActivity);
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1073741824);
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.mAppSearchData;
        if (bundle2 != null) {
            bundle.putParcelable(SearchManager.APP_DATA, bundle2);
        }
        Intent intent3 = new Intent(intent);
        intent3.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
        intent3.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT, activity);
        intent3.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT_BUNDLE, bundle);
        return intent3;
    }

    public boolean semSetSviEnabled(boolean z) {
        if (!ViewRune.WIDGET_SEARCHVIEW_USE_SVI) {
            Log.w(LOG_TAG, "semSetSviEnabled: WIDGET_SEARCHVIEW_USE_SVI is false");
            this.mUseSVI = false;
            return false;
        }
        this.mUseSVI = z;
        if (z) {
            try {
                PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo("com.samsung.android.honeyboard", 0);
                if ((packageInfo != null ? packageInfo.getLongVersionCode() : -1L) < 220002001) {
                    Log.w(LOG_TAG, "semSetSviEnabled: not supported SVI version");
                    this.mUseSVI = false;
                }
                if (!isSystemLocaleSupported()) {
                    Log.w(LOG_TAG, "semSetSviEnabled: not supported system locale");
                    this.mUseSVI = false;
                }
            } catch (Exception e) {
                Log.w(LOG_TAG, "Exception " + e);
                this.mUseSVI = false;
            }
        }
        return this.mUseSVI;
    }

    public boolean semIsSviEnabled() {
        return this.mUseSVI;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean isSystemLocaleSupported() {
        Exception exc;
        int i;
        Cursor cursorQuery;
        try {
            cursorQuery = this.mContext.getContentResolver().query(Uri.parse("content://com.samsung.android.honeyboard.provider.VoiceLanguageListProvider/is_honeyvoice_locale_supported"), null, null, null, null);
        } catch (Exception e) {
            exc = e;
            i = 0;
        }
        if (cursorQuery == null) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return false;
        }
        i = 0;
        while (cursorQuery.moveToNext()) {
            try {
                try {
                    i = cursorQuery.getInt(cursorQuery.getColumnIndex(SEM_KEY_SVI_APP_LOCALE));
                } finally {
                }
            } catch (Exception e2) {
                exc = e2;
                Log.w(LOG_TAG, "isSystemLocaleSupported: exception!!" + exc);
                if (i != 1) {
                }
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return i != 1;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        if (this.mThemeIsDeviceDefault) {
            semCheckMaxFontSize();
        }
    }

    private void semCheckMaxFontSize() throws Resources.NotFoundException {
        float f = getContext().getResources().getConfiguration().fontScale;
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.tw_searchview_search_text_size);
        if (f > 1.3f) {
            this.mSearchSrcTextView.setTextSize(0, (dimensionPixelSize / f) * 1.3f);
        } else {
            this.mSearchSrcTextView.setTextSize(0, dimensionPixelSize);
        }
    }
}
