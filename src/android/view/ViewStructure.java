package android.view;

import android.credentials.GetCredentialException;
import android.credentials.GetCredentialRequest;
import android.credentials.GetCredentialResponse;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.OutcomeReceiver;
import android.util.Pair;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import com.android.internal.util.Preconditions;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class ViewStructure {
    public static final String EXTRA_ACTIVE_CHILDREN_IDS = "android.view.ViewStructure.extra.ACTIVE_CHILDREN_IDS";
    public static final String EXTRA_CONTAINS_SECURE_LAYERS = "android.view.ViewStructure.extra.CONTAINS_SECURE_LAYERS";
    public static final String EXTRA_FIRST_ACTIVE_POSITION = "android.view.ViewStructure.extra.FIRST_ACTIVE_POSITION";
    public static final String EXTRA_VIRTUAL_STRUCTURE_TYPE = "android.view.extra.VIRTUAL_STRUCTURE_TYPE";
    public static final String EXTRA_VIRTUAL_STRUCTURE_VERSION_NUMBER = "android.view.extra.VIRTUAL_STRUCTURE_VERSION_NUMBER";

    public static abstract class HtmlInfo {

        public static abstract class Builder {
            public abstract Builder addAttribute(String str, String str2);

            public abstract HtmlInfo build();
        }

        public abstract List<Pair<String, String>> getAttributes();

        public abstract String getTag();
    }

    public abstract int addChildCount(int i);

    public abstract void asyncCommit();

    public abstract ViewStructure asyncNewChild(int i);

    public void clearCredentialManagerRequest() {
    }

    public abstract AutofillId getAutofillId();

    public abstract int getChildCount();

    public abstract Bundle getExtras();

    public abstract CharSequence getHint();

    public OutcomeReceiver<GetCredentialResponse, GetCredentialException> getPendingCredentialCallback() {
        return null;
    }

    public GetCredentialRequest getPendingCredentialRequest() {
        return null;
    }

    public abstract Rect getTempRect();

    public abstract CharSequence getText();

    public abstract int getTextSelectionEnd();

    public abstract int getTextSelectionStart();

    public abstract boolean hasExtras();

    public abstract ViewStructure newChild(int i);

    public abstract HtmlInfo.Builder newHtmlInfoBuilder(String str);

    public abstract void setAccessibilityFocused(boolean z);

    public abstract void setActivated(boolean z);

    public abstract void setAlpha(float f);

    public abstract void setAssistBlocked(boolean z);

    public abstract void setAutofillHints(String[] strArr);

    public abstract void setAutofillId(AutofillId autofillId);

    public abstract void setAutofillId(AutofillId autofillId, int i);

    public abstract void setAutofillOptions(CharSequence[] charSequenceArr);

    public abstract void setAutofillType(int i);

    public abstract void setAutofillValue(AutofillValue autofillValue);

    public abstract void setCheckable(boolean z);

    public abstract void setChecked(boolean z);

    public abstract void setChildCount(int i);

    public abstract void setClassName(String str);

    public abstract void setClickable(boolean z);

    public abstract void setContentDescription(CharSequence charSequence);

    public abstract void setContextClickable(boolean z);

    public abstract void setDataIsSensitive(boolean z);

    public abstract void setDimens(int i, int i2, int i3, int i4, int i5, int i6);

    public abstract void setElevation(float f);

    public abstract void setEnabled(boolean z);

    public abstract void setFocusable(boolean z);

    public abstract void setFocused(boolean z);

    public abstract void setHint(CharSequence charSequence);

    public abstract void setHtmlInfo(HtmlInfo htmlInfo);

    public abstract void setId(int i, String str, String str2, String str3);

    public void setImportantForAutofill(int i) {
    }

    public abstract void setInputType(int i);

    public void setIsCredential(boolean z) {
    }

    public abstract void setLocaleList(LocaleList localeList);

    public abstract void setLongClickable(boolean z);

    public void setMaxTextEms(int i) {
    }

    public void setMaxTextLength(int i) {
    }

    public void setMinTextEms(int i) {
    }

    public abstract void setOpaque(boolean z);

    public void setPendingCredentialRequest(GetCredentialRequest getCredentialRequest, OutcomeReceiver<GetCredentialResponse, GetCredentialException> outcomeReceiver) {
    }

    public void setReceiveContentMimeTypes(String[] strArr) {
    }

    public abstract void setSelected(boolean z);

    public abstract void setText(CharSequence charSequence);

    public abstract void setText(CharSequence charSequence, int i, int i2);

    public abstract void setTextLines(int[] iArr, int[] iArr2);

    public abstract void setTextStyle(float f, int i, int i2, int i3);

    public abstract void setTransformation(Matrix matrix);

    public abstract void setVisibility(int i);

    public abstract void setWebDomain(String str);

    public void setTextIdEntry(String str) {
        Preconditions.checkNotNull(str);
    }

    public void setHintIdEntry(String str) {
        Preconditions.checkNotNull(str);
    }
}
