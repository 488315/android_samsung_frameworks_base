package androidx.emoji2.viewsintegration;

import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.SparseArray;
import android.widget.TextView;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EmojiTextViewHelper {
    public final HelperInternal mHelper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HelperInternal19 extends HelperInternal {
        public final EmojiInputFilter mEmojiInputFilter;
        public boolean mEnabled = true;
        public final TextView mTextView;

        public HelperInternal19(TextView textView) {
            this.mTextView = textView;
            this.mEmojiInputFilter = new EmojiInputFilter(textView);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final InputFilter[] getFilters(InputFilter[] inputFilterArr) {
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
        public final void setAllCaps(boolean z) {
            if (z) {
                TextView textView = this.mTextView;
                textView.setTransformationMethod(wrapTransformationMethod(textView.getTransformationMethod()));
            }
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final void setEnabled(boolean z) {
            this.mEnabled = z;
            TextView textView = this.mTextView;
            textView.setTransformationMethod(wrapTransformationMethod(textView.getTransformationMethod()));
            textView.setFilters(getFilters(textView.getFilters()));
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
            return this.mEnabled ? ((transformationMethod instanceof EmojiTransformationMethod) || (transformationMethod instanceof PasswordTransformationMethod)) ? transformationMethod : new EmojiTransformationMethod(transformationMethod) : transformationMethod instanceof EmojiTransformationMethod ? ((EmojiTransformationMethod) transformationMethod).mTransformationMethod : transformationMethod;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SkippingHelper19 extends HelperInternal {
        public final HelperInternal19 mHelperDelegate;

        public SkippingHelper19(TextView textView) {
            this.mHelperDelegate = new HelperInternal19(textView);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final InputFilter[] getFilters(InputFilter[] inputFilterArr) {
            return (EmojiCompat.sInstance != null) ^ true ? inputFilterArr : this.mHelperDelegate.getFilters(inputFilterArr);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final boolean isEnabled() {
            return this.mHelperDelegate.mEnabled;
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final void setAllCaps(boolean z) {
            if (!(EmojiCompat.sInstance != null)) {
                return;
            }
            this.mHelperDelegate.setAllCaps(z);
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final void setEnabled(boolean z) {
            boolean z2 = !(EmojiCompat.sInstance != null);
            HelperInternal19 helperInternal19 = this.mHelperDelegate;
            if (z2) {
                helperInternal19.mEnabled = z;
            } else {
                helperInternal19.setEnabled(z);
            }
        }

        @Override // androidx.emoji2.viewsintegration.EmojiTextViewHelper.HelperInternal
        public final TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
            return (EmojiCompat.sInstance != null) ^ true ? transformationMethod : this.mHelperDelegate.wrapTransformationMethod(transformationMethod);
        }
    }

    public EmojiTextViewHelper(TextView textView) {
        this(textView, true);
    }

    public EmojiTextViewHelper(TextView textView, boolean z) {
        Preconditions.checkNotNull(textView, "textView cannot be null");
        if (z) {
            this.mHelper = new HelperInternal19(textView);
        } else {
            this.mHelper = new SkippingHelper19(textView);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class HelperInternal {
        public boolean isEnabled() {
            return false;
        }

        public InputFilter[] getFilters(InputFilter[] inputFilterArr) {
            return inputFilterArr;
        }

        public void setAllCaps(boolean z) {
        }

        public void setEnabled(boolean z) {
        }

        public TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
            return transformationMethod;
        }
    }
}
