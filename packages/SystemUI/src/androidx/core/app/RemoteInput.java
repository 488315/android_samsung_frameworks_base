package androidx.core.app;

import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RemoteInput {
    public final boolean mAllowFreeFormTextInput;
    public final Set mAllowedDataTypes;
    public final CharSequence[] mChoices;
    public final int mEditChoicesBeforeSending;
    public final Bundle mExtras;
    public final CharSequence mLabel;
    public final String mResultKey;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public CharSequence[] mChoices;
        public CharSequence mLabel;
        public final String mResultKey;
        public final Set mAllowedDataTypes = new HashSet();
        public final Bundle mExtras = new Bundle();
        public boolean mAllowFreeFormTextInput = true;

        public Builder(String str) {
            if (str == null) {
                throw new IllegalArgumentException("Result key can't be null");
            }
            this.mResultKey = str;
        }
    }

    public RemoteInput(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, int i, Bundle bundle, Set<String> set) {
        this.mResultKey = str;
        this.mLabel = charSequence;
        this.mChoices = charSequenceArr;
        this.mAllowFreeFormTextInput = z;
        this.mEditChoicesBeforeSending = i;
        this.mExtras = bundle;
        this.mAllowedDataTypes = set;
        if (i == 2 && !z) {
            throw new IllegalArgumentException("setEditChoicesBeforeSending requires setAllowFreeFormInput");
        }
    }
}
