package androidx.emoji2.viewsintegration;

import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.SparseArray;
import android.widget.TextView;
import androidx.appcompat.text.AllCapsTransformationMethod;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;

/* loaded from: classes.dex */
public final class EmojiTextViewHelper {
    private final HelperInternal mHelper;

    static class HelperInternal {
        InputFilter[] getFilters(InputFilter[] inputFilterArr) {
            throw null;
        }

        public boolean isEnabled() {
            throw null;
        }

        void setAllCaps(boolean z) {
            throw null;
        }

        void setEnabled(boolean z) {
            throw null;
        }

        TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
            throw null;
        }
    }

    private static class HelperInternal19 extends HelperInternal {
        private final EmojiInputFilter mEmojiInputFilter;
        private boolean mEnabled = true;
        private final TextView mTextView;

        HelperInternal19(TextView textView) {
            this.mTextView = textView;
            this.mEmojiInputFilter = new EmojiInputFilter(textView);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        final InputFilter[] getFilters(InputFilter[] inputFilterArr) {
            if (!this.mEnabled) {
                SparseArray sparseArray = new SparseArray(1);
                for (int i = 0; i < inputFilterArr.length; i++) {
                    InputFilter inputFilter = inputFilterArr[i];
                    if (inputFilter instanceof EmojiInputFilter) {
                        sparseArray.put(i, inputFilter);
                    }
                }
                if (sparseArray.size() == 0) {
                    return inputFilterArr;
                }
                int length = inputFilterArr.length;
                InputFilter[] inputFilterArr2 = new InputFilter[inputFilterArr.length - sparseArray.size()];
                int i2 = 0;
                for (int i3 = 0; i3 < length; i3++) {
                    if (sparseArray.indexOfKey(i3) < 0) {
                        inputFilterArr2[i2] = inputFilterArr[i3];
                        i2++;
                    }
                }
                return inputFilterArr2;
            }
            int length2 = inputFilterArr.length;
            int i4 = 0;
            while (true) {
                EmojiInputFilter emojiInputFilter = this.mEmojiInputFilter;
                if (i4 >= length2) {
                    InputFilter[] inputFilterArr3 = new InputFilter[inputFilterArr.length + 1];
                    System.arraycopy(inputFilterArr, 0, inputFilterArr3, 0, length2);
                    inputFilterArr3[length2] = emojiInputFilter;
                    return inputFilterArr3;
                }
                if (inputFilterArr[i4] == emojiInputFilter) {
                    return inputFilterArr;
                }
                i4++;
            }
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final boolean isEnabled() {
            return this.mEnabled;
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        final void setAllCaps(boolean z) {
            if (z) {
                TextView textView = this.mTextView;
                textView.setTransformationMethod(wrapTransformationMethod(textView.getTransformationMethod()));
            }
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        final void setEnabled(boolean z) {
            this.mEnabled = z;
            TextView textView = this.mTextView;
            textView.setTransformationMethod(wrapTransformationMethod(textView.getTransformationMethod()));
            textView.setFilters(getFilters(textView.getFilters()));
        }

        final void setEnabledUnsafe(boolean z) {
            this.mEnabled = z;
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        final TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
            return this.mEnabled ? ((transformationMethod instanceof EmojiTransformationMethod) || (transformationMethod instanceof PasswordTransformationMethod)) ? transformationMethod : new EmojiTransformationMethod(transformationMethod) : transformationMethod instanceof EmojiTransformationMethod ? ((EmojiTransformationMethod) transformationMethod).getOriginalTransformationMethod() : transformationMethod;
        }
    }

    private static class SkippingHelper19 extends HelperInternal {
        private final HelperInternal19 mHelperDelegate;

        SkippingHelper19(TextView textView) {
            this.mHelperDelegate = new HelperInternal19(textView);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        final InputFilter[] getFilters(InputFilter[] inputFilterArr) {
            return EmojiCompat.isConfigured() ^ true ? inputFilterArr : this.mHelperDelegate.getFilters(inputFilterArr);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final boolean isEnabled() {
            return this.mHelperDelegate.isEnabled();
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        final void setAllCaps(boolean z) {
            if (!EmojiCompat.isConfigured()) {
                return;
            }
            this.mHelperDelegate.setAllCaps(z);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        final void setEnabled(boolean z) {
            boolean z2 = !EmojiCompat.isConfigured();
            HelperInternal19 helperInternal19 = this.mHelperDelegate;
            if (z2) {
                helperInternal19.setEnabledUnsafe(z);
            } else {
                helperInternal19.setEnabled(z);
            }
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        final TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
            return EmojiCompat.isConfigured() ^ true ? transformationMethod : this.mHelperDelegate.wrapTransformationMethod(transformationMethod);
        }
    }

    public EmojiTextViewHelper(TextView textView) {
        Preconditions.checkNotNull(textView, "textView cannot be null");
        this.mHelper = new SkippingHelper19(textView);
    }

    public final InputFilter[] getFilters(InputFilter[] inputFilterArr) {
        return this.mHelper.getFilters(inputFilterArr);
    }

    public final boolean isEnabled() {
        return this.mHelper.isEnabled();
    }

    public final void setAllCaps(boolean z) {
        this.mHelper.setAllCaps(z);
    }

    public final void setEnabled(boolean z) {
        this.mHelper.setEnabled(z);
    }

    public final TransformationMethod wrapTransformationMethod(AllCapsTransformationMethod allCapsTransformationMethod) {
        return this.mHelper.wrapTransformationMethod(allCapsTransformationMethod);
    }
}
