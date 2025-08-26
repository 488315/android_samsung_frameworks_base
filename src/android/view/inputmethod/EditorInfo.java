package android.view.inputmethod;

import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Printer;
import android.util.proto.ProtoOutputStream;
import android.view.autofill.AutofillId;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public class EditorInfo implements InputType, Parcelable {
    public static final Parcelable.Creator<EditorInfo> CREATOR = new Parcelable.Creator<EditorInfo>() { // from class: android.view.inputmethod.EditorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EditorInfo createFromParcel(Parcel parcel) {
            EditorInfo editorInfo = new EditorInfo();
            editorInfo.inputType = parcel.readInt();
            editorInfo.imeOptions = parcel.readInt();
            editorInfo.privateImeOptions = parcel.readString();
            editorInfo.internalImeOptions = parcel.readInt();
            editorInfo.actionLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            editorInfo.actionId = parcel.readInt();
            editorInfo.initialSelStart = parcel.readInt();
            editorInfo.initialSelEnd = parcel.readInt();
            editorInfo.initialCapsMode = parcel.readInt();
            editorInfo.mInitialToolType = parcel.readInt();
            editorInfo.hintText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            editorInfo.label = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            editorInfo.packageName = parcel.readString();
            editorInfo.autofillId = (AutofillId) parcel.readParcelable(AutofillId.class.getClassLoader(), AutofillId.class);
            editorInfo.fieldId = parcel.readInt();
            editorInfo.fieldName = parcel.readString();
            editorInfo.extras = parcel.readBundle();
            editorInfo.mSupportedHandwritingGestureTypes = parcel.readInt();
            editorInfo.mSupportedHandwritingGesturePreviewTypes = parcel.readInt();
            if (Flags.editorinfoHandwritingEnabled()) {
                editorInfo.mIsStylusHandwritingEnabled = parcel.readBoolean();
            }
            if (parcel.readBoolean()) {
                editorInfo.mInitialSurroundingText = SurroundingText.CREATOR.createFromParcel(parcel);
            }
            LocaleList localeListCreateFromParcel = LocaleList.CREATOR.createFromParcel(parcel);
            if (localeListCreateFromParcel.isEmpty()) {
                localeListCreateFromParcel = null;
            }
            editorInfo.hintLocales = localeListCreateFromParcel;
            editorInfo.contentMimeTypes = parcel.readStringArray();
            editorInfo.targetInputMethodUser = UserHandle.readFromParcel(parcel);
            editorInfo.mWritingToolsEnabled = parcel.readBoolean();
            return editorInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EditorInfo[] newArray(int i) {
            return new EditorInfo[i];
        }
    };
    public static final int IME_ACTION_DONE = 6;
    public static final int IME_ACTION_GO = 2;
    public static final int IME_ACTION_NEXT = 5;
    public static final int IME_ACTION_NONE = 1;
    public static final int IME_ACTION_PREVIOUS = 7;
    public static final int IME_ACTION_SEARCH = 3;
    public static final int IME_ACTION_SEND = 4;
    public static final int IME_ACTION_UNSPECIFIED = 0;
    public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
    public static final int IME_FLAG_NAVIGATE_NEXT = 134217728;
    public static final int IME_FLAG_NAVIGATE_PREVIOUS = 67108864;
    public static final int IME_FLAG_NO_ACCESSORY_ACTION = 536870912;
    public static final int IME_FLAG_NO_ENTER_ACTION = 1073741824;
    public static final int IME_FLAG_NO_EXTRACT_UI = 268435456;
    public static final int IME_FLAG_NO_FULLSCREEN = 33554432;
    public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
    public static final int IME_INTERNAL_FLAG_APP_WINDOW_PORTRAIT = 1;
    public static final int IME_MASK_ACTION = 255;
    public static final int IME_NULL = 0;
    static final int MAX_INITIAL_SELECTION_LENGTH = 1024;
    static final int MEMORY_EFFICIENT_TEXT_LENGTH = 2048;
    public static final String STYLUS_HANDWRITING_ENABLED_ANDROIDX_EXTRAS_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.STYLUS_HANDWRITING_ENABLED";
    private AutofillId autofillId;
    public Bundle extras;
    public int fieldId;
    public String fieldName;
    public CharSequence hintText;
    public CharSequence label;
    private boolean mIsStylusHandwritingEnabled;
    private int mSupportedHandwritingGesturePreviewTypes;
    private int mSupportedHandwritingGestureTypes;
    public String packageName;
    public int inputType = 0;
    public int imeOptions = 0;
    public String privateImeOptions = null;
    public int internalImeOptions = 0;
    public CharSequence actionLabel = null;
    public int actionId = 0;
    public int initialSelStart = -1;
    public int initialSelEnd = -1;
    public int initialCapsMode = 0;
    public LocaleList hintLocales = null;
    public String[] contentMimeTypes = null;
    private boolean mWritingToolsEnabled = true;
    public UserHandle targetInputMethodUser = null;
    private SurroundingText mInitialSurroundingText = null;
    private int mInitialToolType = 0;

    @Retention(RetentionPolicy.SOURCE)
    @interface TrimPolicy {
        public static final int HEAD = 0;
        public static final int TAIL = 1;
    }

    private static boolean isPasswordInputType(int i) {
        int i2 = i & 4095;
        return i2 == 129 || i2 == 225 || i2 == 18;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setSupportedHandwritingGestures(List<Class<? extends HandwritingGesture>> list) {
        int i;
        Objects.requireNonNull(list);
        int i2 = 0;
        if (list.isEmpty()) {
            this.mSupportedHandwritingGestureTypes = 0;
            return;
        }
        for (Class<? extends HandwritingGesture> cls : list) {
            Objects.requireNonNull(cls);
            if (cls.equals(SelectGesture.class)) {
                i = i2 | 1;
            } else if (cls.equals(SelectRangeGesture.class)) {
                i = i2 | 32;
            } else if (cls.equals(InsertGesture.class)) {
                i = i2 | 2;
            } else if (cls.equals(InsertModeGesture.class)) {
                i = i2 | 128;
            } else if (cls.equals(DeleteGesture.class)) {
                i = i2 | 4;
            } else if (cls.equals(DeleteRangeGesture.class)) {
                i = i2 | 64;
            } else if (cls.equals(RemoveSpaceGesture.class)) {
                i = i2 | 8;
            } else {
                if (!cls.equals(JoinOrSplitGesture.class)) {
                    throw new IllegalArgumentException("Unknown gesture type: " + cls);
                }
                i = i2 | 16;
            }
            i2 = i;
        }
        this.mSupportedHandwritingGestureTypes = i2;
    }

    public List<Class<? extends HandwritingGesture>> getSupportedHandwritingGestures() {
        ArrayList arrayList = new ArrayList();
        int i = this.mSupportedHandwritingGestureTypes;
        if (i != 0) {
            if ((i & 1) == 1) {
                arrayList.add(SelectGesture.class);
            }
            if ((this.mSupportedHandwritingGestureTypes & 32) == 32) {
                arrayList.add(SelectRangeGesture.class);
            }
            if ((this.mSupportedHandwritingGestureTypes & 2) == 2) {
                arrayList.add(InsertGesture.class);
            }
            if ((this.mSupportedHandwritingGestureTypes & 128) == 128) {
                arrayList.add(InsertModeGesture.class);
            }
            if ((this.mSupportedHandwritingGestureTypes & 4) == 4) {
                arrayList.add(DeleteGesture.class);
            }
            if ((this.mSupportedHandwritingGestureTypes & 64) == 64) {
                arrayList.add(DeleteRangeGesture.class);
            }
            if ((this.mSupportedHandwritingGestureTypes & 8) == 8) {
                arrayList.add(RemoveSpaceGesture.class);
            }
            if ((this.mSupportedHandwritingGestureTypes & 16) == 16) {
                arrayList.add(JoinOrSplitGesture.class);
            }
        }
        return arrayList;
    }

    public void setSupportedHandwritingGesturePreviews(Set<Class<? extends PreviewableHandwritingGesture>> set) {
        int i;
        Objects.requireNonNull(set);
        int i2 = 0;
        if (set.isEmpty()) {
            this.mSupportedHandwritingGesturePreviewTypes = 0;
            return;
        }
        for (Class<? extends PreviewableHandwritingGesture> cls : set) {
            Objects.requireNonNull(cls);
            if (cls.equals(SelectGesture.class)) {
                i = i2 | 1;
            } else if (cls.equals(SelectRangeGesture.class)) {
                i = i2 | 32;
            } else if (cls.equals(DeleteGesture.class)) {
                i = i2 | 4;
            } else {
                if (!cls.equals(DeleteRangeGesture.class)) {
                    throw new IllegalArgumentException("Unsupported gesture type for preview: " + cls);
                }
                i = i2 | 64;
            }
            i2 = i;
        }
        this.mSupportedHandwritingGesturePreviewTypes = i2;
    }

    public Set<Class<? extends PreviewableHandwritingGesture>> getSupportedHandwritingGesturePreviews() {
        HashSet hashSet = new HashSet();
        int i = this.mSupportedHandwritingGesturePreviewTypes;
        if (i != 0) {
            if ((i & 1) == 1) {
                hashSet.add(SelectGesture.class);
            }
            if ((this.mSupportedHandwritingGesturePreviewTypes & 32) == 32) {
                hashSet.add(SelectRangeGesture.class);
            }
            if ((this.mSupportedHandwritingGesturePreviewTypes & 4) == 4) {
                hashSet.add(DeleteGesture.class);
            }
            if ((this.mSupportedHandwritingGesturePreviewTypes & 64) == 64) {
                hashSet.add(DeleteRangeGesture.class);
            }
        }
        return hashSet;
    }

    public void setStylusHandwritingEnabled(boolean z) {
        this.mIsStylusHandwritingEnabled = z;
    }

    public boolean isStylusHandwritingEnabled() {
        return this.mIsStylusHandwritingEnabled;
    }

    public boolean isWritingToolsEnabled() {
        return this.mWritingToolsEnabled;
    }

    public void setWritingToolsEnabled(boolean z) {
        this.mWritingToolsEnabled = z;
    }

    public void setInitialSurroundingText(CharSequence charSequence) {
        setInitialSurroundingSubText(charSequence, 0);
    }

    public final void setInitialSurroundingTextInternal(SurroundingText surroundingText) {
        this.mInitialSurroundingText = surroundingText;
    }

    public void setInitialSurroundingSubText(CharSequence charSequence, int i) {
        Objects.requireNonNull(charSequence);
        if (isPasswordInputType(this.inputType)) {
            this.mInitialSurroundingText = null;
            return;
        }
        int i2 = this.initialSelStart;
        int i3 = this.initialSelEnd;
        int i4 = i2 > i3 ? i3 - i : i2 - i;
        int i5 = i2 > i3 ? i2 - i : i3 - i;
        int length = charSequence.length();
        if (i < 0 || i4 < 0 || i5 > length) {
            this.mInitialSurroundingText = null;
        } else if (length <= 2048) {
            this.mInitialSurroundingText = new SurroundingText(charSequence, i4, i5, i);
        } else {
            trimLongSurroundingText(charSequence, i4, i5, i);
        }
    }

    private void trimLongSurroundingText(CharSequence charSequence, int i, int i2, int i3) {
        CharSequence charSequenceSubSequence;
        int i4 = i2 - i;
        int i5 = i4 > 1024 ? 0 : i4;
        int i6 = 2048 - i5;
        int iMin = Math.min(charSequence.length() - i2, i6 - Math.min(i, (int) (i6 * 0.8d)));
        int iMin2 = Math.min(i, i6 - iMin);
        int i7 = i - iMin2;
        if (isCutOnSurrogate(charSequence, i7, 0)) {
            i7++;
            iMin2--;
        }
        if (isCutOnSurrogate(charSequence, (i2 + iMin) - 1, 1)) {
            iMin--;
        }
        int i8 = iMin2 + i5 + iMin;
        if (i5 != i4) {
            charSequenceSubSequence = TextUtils.concat(charSequence.subSequence(i7, i7 + iMin2), charSequence.subSequence(i2, iMin + i2));
        } else {
            charSequenceSubSequence = charSequence.subSequence(i7, i8 + i7);
        }
        this.mInitialSurroundingText = new SurroundingText(charSequenceSubSequence, iMin2, i5 + iMin2, (i3 + i) - iMin2);
    }

    public CharSequence getInitialTextBeforeCursor(int i, int i2) {
        SurroundingText surroundingText = this.mInitialSurroundingText;
        if (surroundingText == null) {
            return null;
        }
        int iMin = Math.min(surroundingText.getSelectionStart(), this.mInitialSurroundingText.getSelectionEnd());
        int iMin2 = Math.min(i, iMin);
        if ((i2 & 1) != 0) {
            return this.mInitialSurroundingText.getText().subSequence(iMin - iMin2, iMin);
        }
        return TextUtils.substring(this.mInitialSurroundingText.getText(), iMin - iMin2, iMin);
    }

    public CharSequence getInitialSelectedText(int i) {
        SurroundingText surroundingText = this.mInitialSurroundingText;
        if (surroundingText == null) {
            return null;
        }
        int i2 = this.initialSelStart;
        int i3 = this.initialSelEnd;
        int i4 = i2 > i3 ? i3 : i2;
        if (i2 <= i3) {
            i2 = i3;
        }
        int i5 = i2 - i4;
        int selectionStart = surroundingText.getSelectionStart();
        int selectionEnd = this.mInitialSurroundingText.getSelectionEnd();
        if (selectionStart <= selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        int i6 = selectionStart - selectionEnd;
        if (this.initialSelStart < 0 || this.initialSelEnd < 0 || i6 != i5) {
            return null;
        }
        if ((i & 1) != 0) {
            return this.mInitialSurroundingText.getText().subSequence(selectionEnd, selectionStart);
        }
        return TextUtils.substring(this.mInitialSurroundingText.getText(), selectionEnd, selectionStart);
    }

    public CharSequence getInitialTextAfterCursor(int i, int i2) {
        SurroundingText surroundingText = this.mInitialSurroundingText;
        if (surroundingText == null) {
            return null;
        }
        int length = surroundingText.getText().length();
        int iMax = Math.max(this.mInitialSurroundingText.getSelectionStart(), this.mInitialSurroundingText.getSelectionEnd());
        int iMin = Math.min(i, length - iMax);
        if ((i2 & 1) != 0) {
            return this.mInitialSurroundingText.getText().subSequence(iMax, iMin + iMax);
        }
        return TextUtils.substring(this.mInitialSurroundingText.getText(), iMax, iMin + iMax);
    }

    public SurroundingText getInitialSurroundingText(int i, int i2, int i3) {
        CharSequence charSequenceSubstring;
        Preconditions.checkArgumentNonnegative(i);
        Preconditions.checkArgumentNonnegative(i2);
        SurroundingText surroundingText = this.mInitialSurroundingText;
        if (surroundingText == null) {
            return null;
        }
        int length = surroundingText.getText().length();
        int selectionStart = this.mInitialSurroundingText.getSelectionStart();
        int selectionEnd = this.mInitialSurroundingText.getSelectionEnd();
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        int iMin = Math.min(i, selectionStart);
        int iMin2 = Math.min(i2 + selectionEnd, length);
        int i4 = selectionStart - iMin;
        if ((i3 & 1) != 0) {
            charSequenceSubstring = this.mInitialSurroundingText.getText().subSequence(i4, iMin2);
        } else {
            charSequenceSubstring = TextUtils.substring(this.mInitialSurroundingText.getText(), i4, iMin2);
        }
        return new SurroundingText(charSequenceSubstring, iMin, Math.min(selectionEnd - i4, length), this.mInitialSurroundingText.getOffset() + i4);
    }

    private static boolean isCutOnSurrogate(CharSequence charSequence, int i, int i2) {
        if (i2 == 0) {
            return Character.isLowSurrogate(charSequence.charAt(i));
        }
        if (i2 != 1) {
            return false;
        }
        return Character.isHighSurrogate(charSequence.charAt(i));
    }

    public final void makeCompatible(int i) {
        if (i < 11) {
            int i2 = this.inputType;
            int i3 = i2 & 4095;
            if (i3 == 2 || i3 == 18) {
                this.inputType = (i2 & InputType.TYPE_MASK_FLAGS) | 2;
            } else if (i3 == 209) {
                this.inputType = (i2 & InputType.TYPE_MASK_FLAGS) | 33;
            } else {
                if (i3 != 225) {
                    return;
                }
                this.inputType = (i2 & InputType.TYPE_MASK_FLAGS) | 129;
            }
        }
    }

    public int getInitialToolType() {
        return this.mInitialToolType;
    }

    public void setInitialToolType(int i) {
        this.mInitialToolType = i;
    }

    public AutofillId getAutofillId() {
        return this.autofillId;
    }

    public void setAutofillId(AutofillId autofillId) {
        this.autofillId = autofillId;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.inputType);
        protoOutputStream.write(1120986464258L, this.imeOptions);
        protoOutputStream.write(1138166333443L, this.privateImeOptions);
        protoOutputStream.write(1138166333444L, this.packageName);
        protoOutputStream.write(1120986464261L, this.fieldId);
        UserHandle userHandle = this.targetInputMethodUser;
        if (userHandle != null) {
            protoOutputStream.write(1120986464262L, userHandle.getIdentifier());
        }
        protoOutputStream.end(jStart);
    }

    public void dump(Printer printer, String str) {
        dump(printer, str, true);
    }

    public void dump(Printer printer, String str, boolean z) {
        printer.println(str + "inputType=0x" + Integer.toHexString(this.inputType) + " imeOptions=0x" + Integer.toHexString(this.imeOptions) + " privateImeOptions=" + this.privateImeOptions);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("actionLabel=");
        sb.append((Object) this.actionLabel);
        sb.append(" actionId=");
        sb.append(this.actionId);
        printer.println(sb.toString());
        printer.println(str + "initialSelStart=" + this.initialSelStart + " initialSelEnd=" + this.initialSelEnd + " initialToolType=" + this.mInitialToolType + " initialCapsMode=0x" + Integer.toHexString(this.initialCapsMode));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("hintText=");
        sb2.append((Object) this.hintText);
        sb2.append(" label=");
        sb2.append((Object) this.label);
        printer.println(sb2.toString());
        printer.println(str + "packageName=" + this.packageName + " autofillId=" + this.autofillId + " fieldId=" + this.fieldId + " fieldName=" + this.fieldName);
        if (z) {
            printer.println(str + "extras=" + this.extras);
        }
        printer.println(str + "hintLocales=" + this.hintLocales);
        printer.println(str + "supportedHandwritingGestureTypes=" + InputMethodDebug.handwritingGestureTypeFlagsToString(this.mSupportedHandwritingGestureTypes));
        printer.println(str + "supportedHandwritingGesturePreviewTypes=" + InputMethodDebug.handwritingGestureTypeFlagsToString(this.mSupportedHandwritingGesturePreviewTypes));
        printer.println(str + "isStylusHandwritingEnabled=" + this.mIsStylusHandwritingEnabled);
        printer.println(str + "writingToolsEnabled=" + this.mWritingToolsEnabled);
        printer.println(str + "contentMimeTypes=" + Arrays.toString(this.contentMimeTypes));
        if (this.targetInputMethodUser != null) {
            printer.println(str + "targetInputMethodUserId=" + this.targetInputMethodUser.getIdentifier());
        }
    }

    public final EditorInfo createCopyInternal() {
        EditorInfo editorInfo = new EditorInfo();
        editorInfo.inputType = this.inputType;
        editorInfo.imeOptions = this.imeOptions;
        editorInfo.privateImeOptions = this.privateImeOptions;
        editorInfo.internalImeOptions = this.internalImeOptions;
        editorInfo.actionLabel = TextUtils.stringOrSpannedString(this.actionLabel);
        editorInfo.actionId = this.actionId;
        editorInfo.initialSelStart = this.initialSelStart;
        editorInfo.initialSelEnd = this.initialSelEnd;
        editorInfo.initialCapsMode = this.initialCapsMode;
        editorInfo.mInitialToolType = this.mInitialToolType;
        editorInfo.hintText = TextUtils.stringOrSpannedString(this.hintText);
        editorInfo.label = TextUtils.stringOrSpannedString(this.label);
        editorInfo.packageName = this.packageName;
        editorInfo.autofillId = this.autofillId;
        editorInfo.fieldId = this.fieldId;
        editorInfo.fieldName = this.fieldName;
        Bundle bundle = this.extras;
        editorInfo.extras = bundle != null ? bundle.deepCopy() : null;
        editorInfo.mInitialSurroundingText = this.mInitialSurroundingText;
        editorInfo.hintLocales = this.hintLocales;
        editorInfo.contentMimeTypes = (String[]) ArrayUtils.cloneOrNull(this.contentMimeTypes);
        editorInfo.targetInputMethodUser = this.targetInputMethodUser;
        editorInfo.mSupportedHandwritingGestureTypes = this.mSupportedHandwritingGestureTypes;
        editorInfo.mSupportedHandwritingGesturePreviewTypes = this.mSupportedHandwritingGesturePreviewTypes;
        return editorInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.inputType);
        parcel.writeInt(this.imeOptions);
        parcel.writeString(this.privateImeOptions);
        parcel.writeInt(this.internalImeOptions);
        TextUtils.writeToParcel(this.actionLabel, parcel, i);
        parcel.writeInt(this.actionId);
        parcel.writeInt(this.initialSelStart);
        parcel.writeInt(this.initialSelEnd);
        parcel.writeInt(this.initialCapsMode);
        parcel.writeInt(this.mInitialToolType);
        TextUtils.writeToParcel(this.hintText, parcel, i);
        TextUtils.writeToParcel(this.label, parcel, i);
        parcel.writeString(this.packageName);
        parcel.writeParcelable(this.autofillId, i);
        parcel.writeInt(this.fieldId);
        parcel.writeString(this.fieldName);
        parcel.writeBundle(this.extras);
        parcel.writeInt(this.mSupportedHandwritingGestureTypes);
        parcel.writeInt(this.mSupportedHandwritingGesturePreviewTypes);
        if (Flags.editorinfoHandwritingEnabled()) {
            parcel.writeBoolean(this.mIsStylusHandwritingEnabled);
        }
        parcel.writeBoolean(this.mInitialSurroundingText != null);
        SurroundingText surroundingText = this.mInitialSurroundingText;
        if (surroundingText != null) {
            surroundingText.writeToParcel(parcel, i);
        }
        LocaleList localeList = this.hintLocales;
        if (localeList != null) {
            localeList.writeToParcel(parcel, i);
        } else {
            LocaleList.getEmptyLocaleList().writeToParcel(parcel, i);
        }
        parcel.writeStringArray(this.contentMimeTypes);
        UserHandle.writeToParcel(this.targetInputMethodUser, parcel);
        parcel.writeBoolean(this.mWritingToolsEnabled);
    }

    public boolean kindofEquals(EditorInfo editorInfo) {
        Bundle bundle;
        Bundle bundle2;
        SurroundingText surroundingText;
        SurroundingText surroundingText2;
        if (editorInfo == null) {
            return false;
        }
        if (this == editorInfo) {
            return true;
        }
        return this.inputType == editorInfo.inputType && this.imeOptions == editorInfo.imeOptions && this.internalImeOptions == editorInfo.internalImeOptions && this.actionId == editorInfo.actionId && this.initialSelStart == editorInfo.initialSelStart && this.initialSelEnd == editorInfo.initialSelEnd && this.initialCapsMode == editorInfo.initialCapsMode && this.fieldId == editorInfo.fieldId && this.mSupportedHandwritingGestureTypes == editorInfo.mSupportedHandwritingGestureTypes && this.mSupportedHandwritingGesturePreviewTypes == editorInfo.mSupportedHandwritingGesturePreviewTypes && Objects.equals(this.autofillId, editorInfo.autofillId) && Objects.equals(this.privateImeOptions, editorInfo.privateImeOptions) && Objects.equals(this.packageName, editorInfo.packageName) && Objects.equals(this.fieldName, editorInfo.fieldName) && Objects.equals(this.hintLocales, editorInfo.hintLocales) && Objects.equals(this.targetInputMethodUser, editorInfo.targetInputMethodUser) && Arrays.equals(this.contentMimeTypes, editorInfo.contentMimeTypes) && TextUtils.equals(this.actionLabel, editorInfo.actionLabel) && TextUtils.equals(this.hintText, editorInfo.hintText) && TextUtils.equals(this.label, editorInfo.label) && ((bundle = this.extras) == (bundle2 = editorInfo.extras) || (bundle != null && bundle.kindofEquals(bundle2))) && ((surroundingText = this.mInitialSurroundingText) == (surroundingText2 = editorInfo.mInitialSurroundingText) || (surroundingText != null && surroundingText.isEqualTo(surroundingText2)));
    }
}
