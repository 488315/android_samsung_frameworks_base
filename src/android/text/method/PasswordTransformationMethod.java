package android.text.method;

import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.GetChars;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UpdateLayout;
import android.view.View;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class PasswordTransformationMethod implements TransformationMethod, TextWatcher {
    private static char DOT = 8226;
    private static PasswordTransformationMethod sInstance;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.method.TransformationMethod
    public CharSequence getTransformation(CharSequence charSequence, View view) {
        if (charSequence instanceof Spannable) {
            Spannable spannable = (Spannable) charSequence;
            for (ViewReference viewReference : (ViewReference[]) spannable.getSpans(0, spannable.length(), ViewReference.class)) {
                spannable.removeSpan(viewReference);
            }
            removeVisibleSpans(spannable);
            spannable.setSpan(new ViewReference(view), 0, 0, 34);
        }
        return new PasswordCharSequence(charSequence);
    }

    public static PasswordTransformationMethod getInstance() {
        PasswordTransformationMethod passwordTransformationMethod = sInstance;
        if (passwordTransformationMethod != null) {
            return passwordTransformationMethod;
        }
        PasswordTransformationMethod passwordTransformationMethod2 = new PasswordTransformationMethod();
        sInstance = passwordTransformationMethod2;
        return passwordTransformationMethod2;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence instanceof Spannable) {
            Spannable spannable = (Spannable) charSequence;
            ViewReference[] viewReferenceArr = (ViewReference[]) spannable.getSpans(0, charSequence.length(), ViewReference.class);
            if (viewReferenceArr.length == 0) {
                return;
            }
            View view = null;
            for (int i4 = 0; view == null && i4 < viewReferenceArr.length; i4++) {
                view = (View) viewReferenceArr[i4].get();
            }
            if (view == null || (TextKeyListener.getInstance().getPrefs(view.getContext()) & 8) == 0 || i3 <= 0) {
                return;
            }
            removeVisibleSpans(spannable);
            if (i3 == 1) {
                spannable.setSpan(new Visible(spannable, this), i, i3 + i, 33);
            }
        }
    }

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
        if (z || !(charSequence instanceof Spannable)) {
            return;
        }
        removeVisibleSpans((Spannable) charSequence);
    }

    private static void removeVisibleSpans(Spannable spannable) {
        for (Visible visible : (Visible[]) spannable.getSpans(0, spannable.length(), Visible.class)) {
            spannable.removeSpan(visible);
        }
    }

    private static class PasswordCharSequence implements CharSequence, GetChars {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence charSequence) {
            this.mSource = charSequence;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mSource.length();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            CharSequence charSequence = this.mSource;
            if (charSequence instanceof Spanned) {
                Spanned spanned = (Spanned) charSequence;
                int spanStart = spanned.getSpanStart(TextKeyListener.ACTIVE);
                int spanEnd = spanned.getSpanEnd(TextKeyListener.ACTIVE);
                if (i >= spanStart && i < spanEnd) {
                    return this.mSource.charAt(i);
                }
                Visible[] visibleArr = (Visible[]) spanned.getSpans(0, spanned.length(), Visible.class);
                for (int i2 = 0; i2 < visibleArr.length; i2++) {
                    if (spanned.getSpanStart(visibleArr[i2].mTransformer) >= 0) {
                        int spanStart2 = spanned.getSpanStart(visibleArr[i2]);
                        int spanEnd2 = spanned.getSpanEnd(visibleArr[i2]);
                        if (i >= spanStart2 && i < spanEnd2) {
                            return this.mSource.charAt(i);
                        }
                    }
                }
            }
            return PasswordTransformationMethod.DOT;
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            char[] cArr = new char[i2 - i];
            getChars(i, i2, cArr, 0);
            return new String(cArr);
        }

        @Override // java.lang.CharSequence
        public String toString() {
            return subSequence(0, length()).toString();
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            int i4;
            int[] iArr;
            int i5;
            int i6;
            int[] iArr2;
            int i7;
            TextUtils.getChars(this.mSource, i, i2, cArr, i3);
            CharSequence charSequence = this.mSource;
            if (charSequence instanceof Spanned) {
                Spanned spanned = (Spanned) charSequence;
                i4 = spanned.getSpanStart(TextKeyListener.ACTIVE);
                i5 = spanned.getSpanEnd(TextKeyListener.ACTIVE);
                Visible[] visibleArr = (Visible[]) spanned.getSpans(0, spanned.length(), Visible.class);
                i6 = visibleArr.length;
                iArr = new int[i6];
                iArr2 = new int[i6];
                for (int i8 = 0; i8 < i6; i8++) {
                    if (spanned.getSpanStart(visibleArr[i8].mTransformer) >= 0) {
                        iArr[i8] = spanned.getSpanStart(visibleArr[i8]);
                        iArr2[i8] = spanned.getSpanEnd(visibleArr[i8]);
                    }
                }
            } else {
                i4 = -1;
                iArr = null;
                i5 = -1;
                i6 = 0;
                iArr2 = null;
            }
            for (int i9 = i; i9 < i2; i9++) {
                if (i9 < i4 || i9 >= i5) {
                    while (true) {
                        if (i7 < i6) {
                            i7 = (i9 < iArr[i7] || i9 >= iArr2[i7]) ? i7 + 1 : 0;
                        } else {
                            cArr[(i9 - i) + i3] = PasswordTransformationMethod.DOT;
                            break;
                        }
                    }
                }
            }
        }
    }

    private static class Visible extends Handler implements UpdateLayout, Runnable {
        private Spannable mText;
        private PasswordTransformationMethod mTransformer;

        public Visible(Spannable spannable, PasswordTransformationMethod passwordTransformationMethod) {
            this.mText = spannable;
            this.mTransformer = passwordTransformationMethod;
            postAtTime(this, SystemClock.uptimeMillis() + 1500);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mText.removeSpan(this);
        }
    }

    private static class ViewReference extends WeakReference<View> implements NoCopySpan {
        public ViewReference(View view) {
            super(view);
        }
    }
}
