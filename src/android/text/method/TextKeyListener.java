package android.text.method;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.QwertyKeyListener;
import android.view.KeyEvent;
import android.view.View;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class TextKeyListener extends BaseKeyListener implements SpanWatcher {
    static final int AUTO_CAP = 1;
    static final int AUTO_PERIOD = 4;
    static final int AUTO_TEXT = 2;
    static final int SHOW_PASSWORD = 8;
    private Capitalize mAutoCap;
    private boolean mAutoText;
    private SettingsObserver mObserver;
    private int mPrefs;
    private boolean mPrefsInited;
    private WeakReference<ContentResolver> mResolver;
    private static TextKeyListener[] sInstance = new TextKeyListener[Capitalize.values().length * 2];
    static final Object ACTIVE = new NoCopySpan.Concrete();
    static final Object CAPPED = new NoCopySpan.Concrete();
    static final Object INHIBIT_REPLACEMENT = new NoCopySpan.Concrete();
    static final Object LAST_TYPED = new NoCopySpan.Concrete();

    public enum Capitalize {
        NONE,
        SENTENCES,
        WORDS,
        CHARACTERS
    }

    @Override // android.text.SpanWatcher
    public void onSpanAdded(Spannable spannable, Object obj, int i, int i2) {
    }

    @Override // android.text.SpanWatcher
    public void onSpanRemoved(Spannable spannable, Object obj, int i, int i2) {
    }

    public TextKeyListener(Capitalize capitalize, boolean z) {
        this.mAutoCap = capitalize;
        this.mAutoText = z;
    }

    public static TextKeyListener getInstance(boolean z, Capitalize capitalize) {
        int ordinal = (capitalize.ordinal() * 2) + (z ? 1 : 0);
        TextKeyListener[] textKeyListenerArr = sInstance;
        if (textKeyListenerArr[ordinal] == null) {
            textKeyListenerArr[ordinal] = new TextKeyListener(capitalize, z);
        }
        return sInstance[ordinal];
    }

    public static TextKeyListener getInstance() {
        return getInstance(false, Capitalize.NONE);
    }

    public static boolean shouldCap(Capitalize capitalize, CharSequence charSequence, int i) {
        if (capitalize == Capitalize.NONE) {
            return false;
        }
        if (capitalize == Capitalize.CHARACTERS) {
            return true;
        }
        return TextUtils.getCapsMode(charSequence, i, capitalize == Capitalize.WORDS ? 8192 : 16384) != 0;
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return makeTextContentType(this.mAutoCap, this.mAutoText);
    }

    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
        return getKeyListener(keyEvent).onKeyDown(view, editable, i, keyEvent);
    }

    @Override // android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyUp(View view, Editable editable, int i, KeyEvent keyEvent) {
        return getKeyListener(keyEvent).onKeyUp(view, editable, i, keyEvent);
    }

    @Override // android.text.method.BaseKeyListener, android.text.method.KeyListener
    public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
        return getKeyListener(keyEvent).onKeyOther(view, editable, keyEvent);
    }

    public static void clear(Editable editable) {
        editable.clear();
        editable.removeSpan(ACTIVE);
        editable.removeSpan(CAPPED);
        editable.removeSpan(INHIBIT_REPLACEMENT);
        editable.removeSpan(LAST_TYPED);
        for (QwertyKeyListener.Replaced replaced : (QwertyKeyListener.Replaced[]) editable.getSpans(0, editable.length(), QwertyKeyListener.Replaced.class)) {
            editable.removeSpan(replaced);
        }
    }

    @Override // android.text.SpanWatcher
    public void onSpanChanged(Spannable spannable, Object obj, int i, int i2, int i3, int i4) {
        if (obj == Selection.SELECTION_END) {
            spannable.removeSpan(ACTIVE);
        }
    }

    private KeyListener getKeyListener(KeyEvent keyEvent) {
        int keyboardType = keyEvent.getKeyCharacterMap().getKeyboardType();
        if (keyboardType == 3) {
            return QwertyKeyListener.getInstance(this.mAutoText, this.mAutoCap);
        }
        if (keyboardType == 1) {
            return MultiTapKeyListener.getInstance(this.mAutoText, this.mAutoCap);
        }
        if (keyboardType == 4 || keyboardType == 5) {
            return QwertyKeyListener.getInstanceForFullKeyboard();
        }
        return NullKeyListener.getInstance();
    }

    private static class NullKeyListener implements KeyListener {
        private static NullKeyListener sInstance;

        @Override // android.text.method.KeyListener
        public void clearMetaKeyState(View view, Editable editable, int i) {
        }

        @Override // android.text.method.KeyListener
        public int getInputType() {
            return 0;
        }

        @Override // android.text.method.KeyListener
        public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
            return false;
        }

        @Override // android.text.method.KeyListener
        public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
            return false;
        }

        @Override // android.text.method.KeyListener
        public boolean onKeyUp(View view, Editable editable, int i, KeyEvent keyEvent) {
            return false;
        }

        private NullKeyListener() {
        }

        public static NullKeyListener getInstance() {
            NullKeyListener nullKeyListener = sInstance;
            if (nullKeyListener != null) {
                return nullKeyListener;
            }
            NullKeyListener nullKeyListener2 = new NullKeyListener();
            sInstance = nullKeyListener2;
            return nullKeyListener2;
        }
    }

    public void release() {
        WeakReference<ContentResolver> weakReference = this.mResolver;
        if (weakReference != null) {
            ContentResolver contentResolver = weakReference.get();
            if (contentResolver != null) {
                contentResolver.unregisterContentObserver(this.mObserver);
                this.mResolver.clear();
            }
            this.mObserver = null;
            this.mResolver = null;
            this.mPrefsInited = false;
        }
    }

    private void initPrefs(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        this.mResolver = new WeakReference<>(contentResolver);
        if (this.mObserver == null) {
            this.mObserver = new SettingsObserver();
            contentResolver.registerContentObserver(Settings.System.CONTENT_URI, true, this.mObserver);
        }
        updatePrefs(contentResolver);
        this.mPrefsInited = true;
    }

    private class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (TextKeyListener.this.mResolver != null) {
                ContentResolver contentResolver = (ContentResolver) TextKeyListener.this.mResolver.get();
                if (contentResolver == null) {
                    TextKeyListener.this.mPrefsInited = false;
                    return;
                } else {
                    TextKeyListener.this.updatePrefs(contentResolver);
                    return;
                }
            }
            TextKeyListener.this.mPrefsInited = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePrefs(ContentResolver contentResolver) {
        this.mPrefs = (Settings.System.getInt(contentResolver, Settings.System.TEXT_AUTO_REPLACE, 1) > 0 ? 2 : 0) | (Settings.System.getInt(contentResolver, Settings.System.TEXT_AUTO_CAPS, 1) > 0 ? 1 : 0) | (Settings.System.getInt(contentResolver, Settings.System.TEXT_AUTO_PUNCTUATE, 1) > 0 ? 4 : 0) | (Settings.System.getInt(contentResolver, Settings.System.TEXT_SHOW_PASSWORD, 1) > 0 ? 8 : 0);
    }

    int getPrefs(Context context) {
        synchronized (this) {
            if (!this.mPrefsInited || this.mResolver.refersTo(null)) {
                initPrefs(context);
            }
        }
        return this.mPrefs;
    }
}
