package android.text.method;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.CallLog;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.InsertModeTransformationMethod;
import android.text.method.OffsetMapping;
import android.text.style.ReplacementSpan;
import android.util.MathUtils;
import android.util.TypedValue;
import android.view.View;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.text.flags.Flags;
import java.lang.reflect.Array;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class InsertModeTransformationMethod implements TransformationMethod, TextWatcher {
    private int mEnd;
    private final TransformationMethod mOldTransformationMethod;
    private final boolean mSingleLine;
    private int mStart;

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean intersect(int i, int i2, int i3, int i4) {
        if (i > i4 || i2 < i3) {
            return false;
        }
        if (i == i2 || i3 == i4) {
            return true;
        }
        return (i == i4 || i2 == i3) ? false : true;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public InsertModeTransformationMethod(int i, boolean z, TransformationMethod transformationMethod) {
        this(i, i, z, transformationMethod);
    }

    private InsertModeTransformationMethod(int i, int i2, boolean z, TransformationMethod transformationMethod) {
        this.mStart = i;
        this.mEnd = i2;
        this.mSingleLine = z;
        this.mOldTransformationMethod = transformationMethod;
    }

    public InsertModeTransformationMethod update(TransformationMethod transformationMethod, boolean z) {
        return new InsertModeTransformationMethod(this.mStart, this.mEnd, z, transformationMethod);
    }

    public TransformationMethod getOldTransformationMethod() {
        return this.mOldTransformationMethod;
    }

    private CharSequence getPlaceholderText(View view) {
        if (!this.mSingleLine) {
            return "\n\n";
        }
        SpannableString spannableString = new SpannableString("�");
        spannableString.setSpan(new SingleLinePlaceholderSpan((int) Math.ceil(TypedValue.applyDimension(1, 108.0f, view.getResources().getDisplayMetrics()))), 0, 1, 33);
        return spannableString;
    }

    @Override // android.text.method.TransformationMethod
    public CharSequence getTransformation(CharSequence charSequence, View view) {
        TransformationMethod transformationMethod = this.mOldTransformationMethod;
        if (transformationMethod != null) {
            CharSequence transformation = transformationMethod.getTransformation(charSequence, view);
            if (charSequence instanceof Spannable) {
                Spannable spannable = (Spannable) charSequence;
                spannable.setSpan(this.mOldTransformationMethod, 0, spannable.length(), 18);
            }
            charSequence = transformation;
        }
        return new TransformedText(charSequence, getPlaceholderText(view));
    }

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
        TransformationMethod transformationMethod = this.mOldTransformationMethod;
        if (transformationMethod != null) {
            transformationMethod.onFocusChanged(view, charSequence, z, i, rect);
        }
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (i > this.mEnd) {
            return;
        }
        int i4 = i3 - i2;
        int i5 = this.mStart;
        if (i < i5) {
            if (i + i2 <= i5) {
                this.mStart = i5 + i4;
            } else if (Flags.insertModeHighlightRange()) {
                this.mStart = Math.min(this.mStart, charSequence.length());
            } else {
                this.mStart = i;
            }
        }
        int i6 = i2 + i;
        int i7 = this.mEnd;
        if (i6 <= i7) {
            this.mEnd = i7 + i4;
        } else if (i < i7) {
            if (Flags.insertModeHighlightRange()) {
                this.mEnd = Math.min(this.mEnd, charSequence.length());
            } else {
                this.mEnd = i + i3;
            }
        }
    }

    public class TransformedText implements OffsetMapping, Spanned {
        private final CharSequence mOriginal;
        private final CharSequence mPlaceholder;
        private final Spanned mSpannedOriginal;
        private final Spanned mSpannedPlaceholder;

        TransformedText(CharSequence charSequence, CharSequence charSequence2) {
            this.mOriginal = charSequence;
            if (charSequence instanceof Spanned) {
                this.mSpannedOriginal = (Spanned) charSequence;
            } else {
                this.mSpannedOriginal = null;
            }
            this.mPlaceholder = charSequence2;
            if (charSequence2 instanceof Spanned) {
                this.mSpannedPlaceholder = (Spanned) charSequence2;
            } else {
                this.mSpannedPlaceholder = null;
            }
        }

        @Override // android.text.method.OffsetMapping
        public int originalToTransformed(int i, int i2) {
            if (i >= 0) {
                Preconditions.checkArgumentInRange(i, 0, this.mOriginal.length(), CallLog.Calls.OFFSET_PARAM_KEY);
                if ((i != InsertModeTransformationMethod.this.mEnd || i2 != 1) && i >= InsertModeTransformationMethod.this.mEnd) {
                    return i + this.mPlaceholder.length();
                }
            }
            return i;
        }

        @Override // android.text.method.OffsetMapping
        public int transformedToOriginal(int i, int i2) {
            if (i >= 0) {
                Preconditions.checkArgumentInRange(i, 0, length(), CallLog.Calls.OFFSET_PARAM_KEY);
                if (i >= InsertModeTransformationMethod.this.mEnd) {
                    if (i < InsertModeTransformationMethod.this.mEnd + this.mPlaceholder.length()) {
                        return InsertModeTransformationMethod.this.mEnd;
                    }
                    return i - this.mPlaceholder.length();
                }
            }
            return i;
        }

        @Override // android.text.method.OffsetMapping
        public void originalToTransformed(OffsetMapping.TextUpdate textUpdate) {
            if (textUpdate.where > InsertModeTransformationMethod.this.mEnd) {
                textUpdate.where += this.mPlaceholder.length();
            } else if (textUpdate.where + textUpdate.before > InsertModeTransformationMethod.this.mEnd) {
                textUpdate.before += this.mPlaceholder.length();
                textUpdate.after += this.mPlaceholder.length();
            }
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mOriginal.length() + this.mPlaceholder.length();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            Preconditions.checkArgumentInRange(i, 0, length() - 1, "index");
            if (i < InsertModeTransformationMethod.this.mEnd) {
                return this.mOriginal.charAt(i);
            }
            if (i < InsertModeTransformationMethod.this.mEnd + this.mPlaceholder.length()) {
                return this.mPlaceholder.charAt(i - InsertModeTransformationMethod.this.mEnd);
            }
            return this.mOriginal.charAt(i - this.mPlaceholder.length());
        }

        @Override // java.lang.CharSequence
        public CharSequence subSequence(int i, int i2) {
            if (i2 < i || i < 0 || i2 > length()) {
                throw new IndexOutOfBoundsException();
            }
            if (i == i2) {
                return "";
            }
            int length = this.mPlaceholder.length();
            return TextUtils.concat(this.mOriginal.subSequence(Math.min(i, InsertModeTransformationMethod.this.mEnd), Math.min(i2, InsertModeTransformationMethod.this.mEnd)), this.mPlaceholder.subSequence(MathUtils.constrain(i - InsertModeTransformationMethod.this.mEnd, 0, length), MathUtils.constrain(i2 - InsertModeTransformationMethod.this.mEnd, 0, length)), this.mOriginal.subSequence(Math.max(i - length, InsertModeTransformationMethod.this.mEnd), Math.max(i2 - length, InsertModeTransformationMethod.this.mEnd)));
        }

        @Override // java.lang.CharSequence
        public String toString() {
            return String.valueOf(this.mOriginal.subSequence(0, InsertModeTransformationMethod.this.mEnd)) + ((Object) this.mPlaceholder) + ((Object) this.mOriginal.subSequence(InsertModeTransformationMethod.this.mEnd, this.mOriginal.length()));
        }

        @Override // android.text.Spanned
        public <T> T[] getSpans(final int i, final int i2, final Class<T> cls) {
            Object[] objArrFilter;
            if (i2 < i) {
                return (T[]) ArrayUtils.emptyArray(cls);
            }
            Object[] spans = null;
            if (this.mSpannedOriginal != null) {
                objArrFilter = ArrayUtils.filter(this.mSpannedOriginal.getSpans(transformedToOriginal(i, 1), transformedToOriginal(i2, 1), cls), new IntFunction() { // from class: android.text.method.InsertModeTransformationMethod$TransformedText$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i3) {
                        return InsertModeTransformationMethod.TransformedText.lambda$getSpans$0(cls, i3);
                    }
                }, new Predicate() { // from class: android.text.method.InsertModeTransformationMethod$TransformedText$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.lambda$getSpans$1(i, i2, obj);
                    }
                });
            } else {
                objArrFilter = null;
            }
            if (this.mSpannedPlaceholder != null && InsertModeTransformationMethod.intersect(i, i2, InsertModeTransformationMethod.this.mEnd, InsertModeTransformationMethod.this.mEnd + this.mPlaceholder.length())) {
                spans = this.mSpannedPlaceholder.getSpans(Math.max(i - InsertModeTransformationMethod.this.mEnd, 0), Math.min(i2 - InsertModeTransformationMethod.this.mEnd, this.mPlaceholder.length()), cls);
            }
            return (T[]) ArrayUtils.concat(cls, objArrFilter, spans);
        }

        static /* synthetic */ Object[] lambda$getSpans$0(Class cls, int i) {
            return (Object[]) Array.newInstance((Class<?>) cls, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$getSpans$1(int i, int i2, Object obj) {
            return InsertModeTransformationMethod.intersect(getSpanStart(obj), getSpanEnd(obj), i, i2);
        }

        @Override // android.text.Spanned
        public int getSpanStart(Object obj) {
            int spanStart;
            int spanStart2;
            Spanned spanned = this.mSpannedOriginal;
            if (spanned != null && (spanStart2 = spanned.getSpanStart(obj)) >= 0) {
                return spanStart2 >= InsertModeTransformationMethod.this.mEnd ? (spanStart2 == InsertModeTransformationMethod.this.mEnd && this.mSpannedOriginal.getSpanEnd(obj) == spanStart2) ? spanStart2 : spanStart2 + this.mPlaceholder.length() : spanStart2;
            }
            Spanned spanned2 = this.mSpannedPlaceholder;
            if (spanned2 == null || (spanStart = spanned2.getSpanStart(obj)) < 0) {
                return -1;
            }
            return spanStart + InsertModeTransformationMethod.this.mEnd;
        }

        @Override // android.text.Spanned
        public int getSpanEnd(Object obj) {
            int spanEnd;
            int spanEnd2;
            Spanned spanned = this.mSpannedOriginal;
            if (spanned != null && (spanEnd2 = spanned.getSpanEnd(obj)) >= 0) {
                return spanEnd2 <= InsertModeTransformationMethod.this.mEnd ? spanEnd2 : spanEnd2 + this.mPlaceholder.length();
            }
            Spanned spanned2 = this.mSpannedPlaceholder;
            if (spanned2 == null || (spanEnd = spanned2.getSpanEnd(obj)) < 0) {
                return -1;
            }
            return spanEnd + InsertModeTransformationMethod.this.mEnd;
        }

        @Override // android.text.Spanned
        public int getSpanFlags(Object obj) {
            int spanFlags;
            Spanned spanned = this.mSpannedOriginal;
            if (spanned != null && (spanFlags = spanned.getSpanFlags(obj)) != 0) {
                return spanFlags;
            }
            Spanned spanned2 = this.mSpannedPlaceholder;
            if (spanned2 != null) {
                return spanned2.getSpanFlags(obj);
            }
            return 0;
        }

        @Override // android.text.Spanned
        public int nextSpanTransition(int i, int i2, Class cls) {
            if (i2 <= i) {
                return i2;
            }
            Object[] spans = getSpans(i, i2, cls);
            for (int i3 = 0; i3 < spans.length; i3++) {
                int spanStart = getSpanStart(spans[i3]);
                int spanEnd = getSpanEnd(spans[i3]);
                if (i < spanStart && spanStart < i2) {
                    i2 = spanStart;
                }
                if (i < spanEnd && spanEnd < i2) {
                    i2 = spanEnd;
                }
            }
            return i2;
        }

        public int getHighlightStart() {
            return InsertModeTransformationMethod.this.mStart;
        }

        public int getHighlightEnd() {
            return InsertModeTransformationMethod.this.mEnd + this.mPlaceholder.length();
        }
    }

    public static class SingleLinePlaceholderSpan extends ReplacementSpan {
        private final int mWidth;

        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        }

        SingleLinePlaceholderSpan(int i) {
            this.mWidth = i;
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            return this.mWidth;
        }
    }
}
