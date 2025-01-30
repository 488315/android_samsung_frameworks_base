package androidx.leanback.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SearchBar extends RelativeLayout {
    public boolean mAutoStartRecognition;
    public final int mBackgroundAlpha;
    public final int mBackgroundSpeechAlpha;
    public Drawable mBarBackground;
    public final Context mContext;
    public final Handler mHandler;
    public String mHint;
    public final InputMethodManager mInputMethodManager;
    public String mSearchQuery;
    public SearchEditText mSearchTextEditor;
    public final SparseIntArray mSoundMap;
    public SoundPool mSoundPool;
    public SpeechOrbView mSpeechOrbView;
    public final int mTextColor;
    public final int mTextColorSpeechMode;
    public final int mTextHintColor;
    public final int mTextHintColorSpeechMode;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.leanback.widget.SearchBar$4 */
    public final class C02874 {
        public C02874() {
        }
    }

    public SearchBar(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSoundPool = new SoundPool(2, 1, 0);
        Context context = this.mContext;
        int[] iArr = {R.raw.lb_voice_failure, R.raw.lb_voice_open, R.raw.lb_voice_no_input, R.raw.lb_voice_success};
        for (int i = 0; i < 4; i++) {
            int i2 = iArr[i];
            this.mSoundMap.put(i2, this.mSoundPool.load(context, i2, 1));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        stopRecognition();
        this.mSoundPool.release();
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBarBackground = ((RelativeLayout) findViewById(R.id.lb_search_bar_items)).getBackground();
        this.mSearchTextEditor = (SearchEditText) findViewById(R.id.lb_search_text_editor);
        this.mSearchTextEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.leanback.widget.SearchBar.1
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                if (z) {
                    final SearchBar searchBar = SearchBar.this;
                    searchBar.mHandler.post(new Runnable() { // from class: androidx.leanback.widget.SearchBar.8
                        @Override // java.lang.Runnable
                        public final void run() {
                            SearchBar.this.mSearchTextEditor.requestFocusFromTouch();
                            SearchBar.this.mSearchTextEditor.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, SearchBar.this.mSearchTextEditor.getWidth(), SearchBar.this.mSearchTextEditor.getHeight(), 0));
                            SearchBar.this.mSearchTextEditor.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, SearchBar.this.mSearchTextEditor.getWidth(), SearchBar.this.mSearchTextEditor.getHeight(), 0));
                        }
                    });
                } else {
                    SearchBar searchBar2 = SearchBar.this;
                    searchBar2.mInputMethodManager.hideSoftInputFromWindow(searchBar2.mSearchTextEditor.getWindowToken(), 0);
                }
                SearchBar.this.updateUi(z);
            }
        });
        final Runnable runnable = new Runnable() { // from class: androidx.leanback.widget.SearchBar.2
            @Override // java.lang.Runnable
            public final void run() {
                SearchBar searchBar = SearchBar.this;
                String obj = searchBar.mSearchTextEditor.getText().toString();
                if (TextUtils.equals(searchBar.mSearchQuery, obj)) {
                    return;
                }
                searchBar.mSearchQuery = obj;
            }
        };
        this.mSearchTextEditor.addTextChangedListener(new TextWatcher() { // from class: androidx.leanback.widget.SearchBar.3
            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                SearchBar searchBar = SearchBar.this;
                searchBar.getClass();
                searchBar.mHandler.removeCallbacks(runnable);
                SearchBar.this.mHandler.post(runnable);
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.mSearchTextEditor.mKeyboardDismissListener = new C02874();
        this.mSearchTextEditor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: androidx.leanback.widget.SearchBar.5
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (3 == i || i == 0) {
                    SearchBar.this.getClass();
                }
                if (1 == i) {
                    SearchBar.this.getClass();
                }
                if (2 != i) {
                    return false;
                }
                SearchBar searchBar = SearchBar.this;
                searchBar.mInputMethodManager.hideSoftInputFromWindow(searchBar.mSearchTextEditor.getWindowToken(), 0);
                SearchBar.this.mHandler.postDelayed(new Runnable() { // from class: androidx.leanback.widget.SearchBar.5.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SearchBar searchBar2 = SearchBar.this;
                        searchBar2.mAutoStartRecognition = true;
                        searchBar2.mSpeechOrbView.requestFocus();
                    }
                }, 500L);
                return true;
            }
        });
        this.mSearchTextEditor.setPrivateImeOptions("escapeNorth,voiceDismiss");
        SpeechOrbView speechOrbView = (SpeechOrbView) findViewById(R.id.lb_search_bar_speech_orb);
        this.mSpeechOrbView = speechOrbView;
        speechOrbView.mListener = new View.OnClickListener() { // from class: androidx.leanback.widget.SearchBar.6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchBar searchBar = SearchBar.this;
                if (searchBar.hasFocus()) {
                    return;
                }
                searchBar.requestFocus();
            }
        };
        this.mSpeechOrbView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.leanback.widget.SearchBar.7
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                if (z) {
                    SearchBar searchBar = SearchBar.this;
                    searchBar.mInputMethodManager.hideSoftInputFromWindow(searchBar.mSearchTextEditor.getWindowToken(), 0);
                    SearchBar searchBar2 = SearchBar.this;
                    if (searchBar2.mAutoStartRecognition) {
                        if (!searchBar2.hasFocus()) {
                            searchBar2.requestFocus();
                        }
                        SearchBar.this.mAutoStartRecognition = false;
                    }
                } else {
                    SearchBar.this.getClass();
                }
                SearchBar.this.updateUi(z);
            }
        });
        updateUi(hasFocus());
        updateHint();
    }

    @Override // android.view.View
    public final void setNextFocusDownId(int i) {
        this.mSpeechOrbView.setNextFocusDownId(i);
        this.mSearchTextEditor.setNextFocusDownId(i);
    }

    public final void updateHint() {
        String string = getResources().getString(R.string.lb_search_bar_hint);
        if (!TextUtils.isEmpty(null)) {
            string = this.mSpeechOrbView.isFocused() ? getResources().getString(R.string.lb_search_bar_hint_with_title_speech, null) : getResources().getString(R.string.lb_search_bar_hint_with_title, null);
        } else if (this.mSpeechOrbView.isFocused()) {
            string = getResources().getString(R.string.lb_search_bar_hint_speech);
        }
        this.mHint = string;
        SearchEditText searchEditText = this.mSearchTextEditor;
        if (searchEditText != null) {
            searchEditText.setHint(string);
        }
    }

    public final void updateUi(boolean z) {
        if (z) {
            this.mBarBackground.setAlpha(this.mBackgroundSpeechAlpha);
            if (this.mSpeechOrbView.isFocused()) {
                this.mSearchTextEditor.setTextColor(this.mTextHintColorSpeechMode);
                this.mSearchTextEditor.setHintTextColor(this.mTextHintColorSpeechMode);
            } else {
                this.mSearchTextEditor.setTextColor(this.mTextColorSpeechMode);
                this.mSearchTextEditor.setHintTextColor(this.mTextHintColorSpeechMode);
            }
        } else {
            this.mBarBackground.setAlpha(this.mBackgroundAlpha);
            this.mSearchTextEditor.setTextColor(this.mTextColor);
            this.mSearchTextEditor.setHintTextColor(this.mTextHintColor);
        }
        updateHint();
    }

    public SearchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SearchBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandler = new Handler();
        this.mAutoStartRecognition = false;
        this.mSoundMap = new SparseIntArray();
        this.mContext = context;
        Resources resources = getResources();
        LayoutInflater.from(getContext()).inflate(R.layout.lb_search_bar, (ViewGroup) this, true);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.lb_search_bar_height));
        layoutParams.addRule(10, -1);
        setLayoutParams(layoutParams);
        setBackgroundColor(0);
        setClipChildren(false);
        this.mSearchQuery = "";
        this.mInputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        this.mTextColorSpeechMode = resources.getColor(R.color.lb_search_bar_text_speech_mode);
        this.mTextColor = resources.getColor(R.color.lb_search_bar_text);
        this.mBackgroundSpeechAlpha = resources.getInteger(R.integer.lb_search_bar_speech_mode_background_alpha);
        this.mBackgroundAlpha = resources.getInteger(R.integer.lb_search_bar_text_mode_background_alpha);
        this.mTextHintColorSpeechMode = resources.getColor(R.color.lb_search_bar_hint_speech_mode);
        this.mTextHintColor = resources.getColor(R.color.lb_search_bar_hint);
    }

    public final void stopRecognition() {
    }
}
