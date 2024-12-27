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

public class EditorInfo implements InputType, Parcelable {
    public static final Parcelable.Creator<EditorInfo> CREATOR =
            new Parcelable.Creator<
                    EditorInfo>() { // from class: android.view.inputmethod.EditorInfo.1
                @Override // android.os.Parcelable.Creator
                public EditorInfo createFromParcel(Parcel source) {
                    EditorInfo res = new EditorInfo();
                    res.inputType = source.readInt();
                    res.imeOptions = source.readInt();
                    res.privateImeOptions = source.readString();
                    res.internalImeOptions = source.readInt();
                    res.actionLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
                    res.actionId = source.readInt();
                    res.initialSelStart = source.readInt();
                    res.initialSelEnd = source.readInt();
                    res.initialCapsMode = source.readInt();
                    res.mInitialToolType = source.readInt();
                    res.hintText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
                    res.label = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
                    res.packageName = source.readString();
                    res.autofillId =
                            (AutofillId)
                                    source.readParcelable(
                                            AutofillId.class.getClassLoader(), AutofillId.class);
                    res.fieldId = source.readInt();
                    res.fieldName = source.readString();
                    res.extras = source.readBundle();
                    res.mSupportedHandwritingGestureTypes = source.readInt();
                    res.mSupportedHandwritingGesturePreviewTypes = source.readInt();
                    if (Flags.editorinfoHandwritingEnabled()) {
                        res.mIsStylusHandwritingEnabled = source.readBoolean();
                    }
                    boolean hasInitialSurroundingText = source.readBoolean();
                    if (hasInitialSurroundingText) {
                        res.mInitialSurroundingText =
                                SurroundingText.CREATOR.createFromParcel(source);
                    }
                    LocaleList hintLocales = LocaleList.CREATOR.createFromParcel(source);
                    res.hintLocales = hintLocales.isEmpty() ? null : hintLocales;
                    res.contentMimeTypes = source.readStringArray();
                    res.targetInputMethodUser = UserHandle.readFromParcel(source);
                    return res;
                }

                @Override // android.os.Parcelable.Creator
                public EditorInfo[] newArray(int size) {
                    return new EditorInfo[size];
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
    public static final String STYLUS_HANDWRITING_ENABLED_ANDROIDX_EXTRAS_KEY =
            "androidx.core.view.inputmethod.EditorInfoCompat.STYLUS_HANDWRITING_ENABLED";
    public AutofillId autofillId;
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
    public UserHandle targetInputMethodUser = null;
    private SurroundingText mInitialSurroundingText = null;
    private int mInitialToolType = 0;

    @Retention(RetentionPolicy.SOURCE)
    @interface TrimPolicy {
        public static final int HEAD = 0;
        public static final int TAIL = 1;
    }

    public void setSupportedHandwritingGestures(
            List<Class<? extends HandwritingGesture>> gestures) {
        Objects.requireNonNull(gestures);
        if (gestures.isEmpty()) {
            this.mSupportedHandwritingGestureTypes = 0;
            return;
        }
        int supportedTypes = 0;
        for (Class<? extends HandwritingGesture> gesture : gestures) {
            Objects.requireNonNull(gesture);
            if (gesture.equals(SelectGesture.class)) {
                supportedTypes |= 1;
            } else if (gesture.equals(SelectRangeGesture.class)) {
                supportedTypes |= 32;
            } else if (gesture.equals(InsertGesture.class)) {
                supportedTypes |= 2;
            } else if (gesture.equals(InsertModeGesture.class)) {
                supportedTypes |= 128;
            } else if (gesture.equals(DeleteGesture.class)) {
                supportedTypes |= 4;
            } else if (gesture.equals(DeleteRangeGesture.class)) {
                supportedTypes |= 64;
            } else if (gesture.equals(RemoveSpaceGesture.class)) {
                supportedTypes |= 8;
            } else if (gesture.equals(JoinOrSplitGesture.class)) {
                supportedTypes |= 16;
            } else {
                throw new IllegalArgumentException("Unknown gesture type: " + gesture);
            }
        }
        this.mSupportedHandwritingGestureTypes = supportedTypes;
    }

    public List<Class<? extends HandwritingGesture>> getSupportedHandwritingGestures() {
        List<Class<? extends HandwritingGesture>> list = new ArrayList<>();
        if (this.mSupportedHandwritingGestureTypes == 0) {
            return list;
        }
        if ((this.mSupportedHandwritingGestureTypes & 1) == 1) {
            list.add(SelectGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 32) == 32) {
            list.add(SelectRangeGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 2) == 2) {
            list.add(InsertGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 128) == 128) {
            list.add(InsertModeGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 4) == 4) {
            list.add(DeleteGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 64) == 64) {
            list.add(DeleteRangeGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 8) == 8) {
            list.add(RemoveSpaceGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 16) == 16) {
            list.add(JoinOrSplitGesture.class);
        }
        return list;
    }

    public void setSupportedHandwritingGesturePreviews(
            Set<Class<? extends PreviewableHandwritingGesture>> gestures) {
        Objects.requireNonNull(gestures);
        if (gestures.isEmpty()) {
            this.mSupportedHandwritingGesturePreviewTypes = 0;
            return;
        }
        int supportedTypes = 0;
        for (Class<? extends PreviewableHandwritingGesture> gesture : gestures) {
            Objects.requireNonNull(gesture);
            if (gesture.equals(SelectGesture.class)) {
                supportedTypes |= 1;
            } else if (gesture.equals(SelectRangeGesture.class)) {
                supportedTypes |= 32;
            } else if (gesture.equals(DeleteGesture.class)) {
                supportedTypes |= 4;
            } else if (gesture.equals(DeleteRangeGesture.class)) {
                supportedTypes |= 64;
            } else {
                throw new IllegalArgumentException(
                        "Unsupported gesture type for preview: " + gesture);
            }
        }
        this.mSupportedHandwritingGesturePreviewTypes = supportedTypes;
    }

    public Set<Class<? extends PreviewableHandwritingGesture>>
            getSupportedHandwritingGesturePreviews() {
        Set<Class<? extends PreviewableHandwritingGesture>> set = new HashSet<>();
        if (this.mSupportedHandwritingGesturePreviewTypes == 0) {
            return set;
        }
        if ((this.mSupportedHandwritingGesturePreviewTypes & 1) == 1) {
            set.add(SelectGesture.class);
        }
        if ((this.mSupportedHandwritingGesturePreviewTypes & 32) == 32) {
            set.add(SelectRangeGesture.class);
        }
        if ((this.mSupportedHandwritingGesturePreviewTypes & 4) == 4) {
            set.add(DeleteGesture.class);
        }
        if ((this.mSupportedHandwritingGesturePreviewTypes & 64) == 64) {
            set.add(DeleteRangeGesture.class);
        }
        return set;
    }

    public void setStylusHandwritingEnabled(boolean enabled) {
        this.mIsStylusHandwritingEnabled = enabled;
    }

    public boolean isStylusHandwritingEnabled() {
        return this.mIsStylusHandwritingEnabled;
    }

    public void setInitialSurroundingText(CharSequence sourceText) {
        setInitialSurroundingSubText(sourceText, 0);
    }

    public final void setInitialSurroundingTextInternal(SurroundingText surroundingText) {
        this.mInitialSurroundingText = surroundingText;
    }

    public void setInitialSurroundingSubText(CharSequence subText, int subTextStart) {
        Objects.requireNonNull(subText);
        if (isPasswordInputType(this.inputType)) {
            this.mInitialSurroundingText = null;
            return;
        }
        int subTextSelStart =
                (this.initialSelStart > this.initialSelEnd
                                ? this.initialSelEnd
                                : this.initialSelStart)
                        - subTextStart;
        int subTextSelEnd =
                (this.initialSelStart > this.initialSelEnd
                                ? this.initialSelStart
                                : this.initialSelEnd)
                        - subTextStart;
        int subTextLength = subText.length();
        if (subTextStart < 0 || subTextSelStart < 0 || subTextSelEnd > subTextLength) {
            this.mInitialSurroundingText = null;
        } else if (subTextLength <= 2048) {
            this.mInitialSurroundingText =
                    new SurroundingText(subText, subTextSelStart, subTextSelEnd, subTextStart);
        } else {
            trimLongSurroundingText(subText, subTextSelStart, subTextSelEnd, subTextStart);
        }
    }

    private void trimLongSurroundingText(
            CharSequence subText, int selStart, int selEnd, int subTextStart) {
        CharSequence newInitialSurroundingText;
        int sourceSelLength = selEnd - selStart;
        int newSelLength = sourceSelLength > 1024 ? 0 : sourceSelLength;
        int subTextAfterCursorLength = subText.length() - selEnd;
        int maxLengthMinusSelection = 2048 - newSelLength;
        int possibleMaxBeforeCursorLength =
                Math.min(selStart, (int) (maxLengthMinusSelection * 0.8d));
        int newAfterCursorLength =
                Math.min(
                        subTextAfterCursorLength,
                        maxLengthMinusSelection - possibleMaxBeforeCursorLength);
        int newBeforeCursorLength =
                Math.min(selStart, maxLengthMinusSelection - newAfterCursorLength);
        int newBeforeCursorHead = selStart - newBeforeCursorLength;
        if (isCutOnSurrogate(subText, selStart - newBeforeCursorLength, 0)) {
            newBeforeCursorHead++;
            newBeforeCursorLength--;
        }
        if (isCutOnSurrogate(subText, (selEnd + newAfterCursorLength) - 1, 1)) {
            newAfterCursorLength--;
        }
        int newTextLength = newBeforeCursorLength + newSelLength + newAfterCursorLength;
        if (newSelLength != sourceSelLength) {
            CharSequence beforeCursor =
                    subText.subSequence(
                            newBeforeCursorHead, newBeforeCursorHead + newBeforeCursorLength);
            CharSequence afterCursor = subText.subSequence(selEnd, selEnd + newAfterCursorLength);
            newInitialSurroundingText = TextUtils.concat(beforeCursor, afterCursor);
        } else {
            newInitialSurroundingText =
                    subText.subSequence(newBeforeCursorHead, newBeforeCursorHead + newTextLength);
        }
        int newSelHead = 0 + newBeforeCursorLength;
        int newOffset = (subTextStart + selStart) - newSelHead;
        this.mInitialSurroundingText =
                new SurroundingText(
                        newInitialSurroundingText,
                        newSelHead,
                        newSelHead + newSelLength,
                        newOffset);
    }

    public CharSequence getInitialTextBeforeCursor(int length, int flags) {
        if (this.mInitialSurroundingText == null) {
            return null;
        }
        int selStart =
                Math.min(
                        this.mInitialSurroundingText.getSelectionStart(),
                        this.mInitialSurroundingText.getSelectionEnd());
        int n = Math.min(length, selStart);
        if ((flags & 1) != 0) {
            return this.mInitialSurroundingText.getText().subSequence(selStart - n, selStart);
        }
        return TextUtils.substring(this.mInitialSurroundingText.getText(), selStart - n, selStart);
    }

    public CharSequence getInitialSelectedText(int flags) {
        if (this.mInitialSurroundingText == null) {
            return null;
        }
        int correctedTextSelStart =
                this.initialSelStart > this.initialSelEnd
                        ? this.initialSelEnd
                        : this.initialSelStart;
        int correctedTextSelEnd =
                this.initialSelStart > this.initialSelEnd
                        ? this.initialSelStart
                        : this.initialSelEnd;
        int sourceSelLength = correctedTextSelEnd - correctedTextSelStart;
        int selStart = this.mInitialSurroundingText.getSelectionStart();
        int selEnd = this.mInitialSurroundingText.getSelectionEnd();
        if (selStart > selEnd) {
            selStart = selEnd;
            selEnd = selStart;
        }
        int tmp = selEnd - selStart;
        if (this.initialSelStart < 0 || this.initialSelEnd < 0 || tmp != sourceSelLength) {
            return null;
        }
        if ((flags & 1) != 0) {
            return this.mInitialSurroundingText.getText().subSequence(selStart, selEnd);
        }
        return TextUtils.substring(this.mInitialSurroundingText.getText(), selStart, selEnd);
    }

    public CharSequence getInitialTextAfterCursor(int length, int flags) {
        if (this.mInitialSurroundingText == null) {
            return null;
        }
        int surroundingTextLength = this.mInitialSurroundingText.getText().length();
        int selEnd =
                Math.max(
                        this.mInitialSurroundingText.getSelectionStart(),
                        this.mInitialSurroundingText.getSelectionEnd());
        int n = Math.min(length, surroundingTextLength - selEnd);
        if ((flags & 1) != 0) {
            return this.mInitialSurroundingText.getText().subSequence(selEnd, selEnd + n);
        }
        return TextUtils.substring(this.mInitialSurroundingText.getText(), selEnd, selEnd + n);
    }

    public SurroundingText getInitialSurroundingText(int beforeLength, int afterLength, int flags) {
        CharSequence newText;
        Preconditions.checkArgumentNonnegative(beforeLength);
        Preconditions.checkArgumentNonnegative(afterLength);
        if (this.mInitialSurroundingText == null) {
            return null;
        }
        int length = this.mInitialSurroundingText.getText().length();
        int selStart = this.mInitialSurroundingText.getSelectionStart();
        int selEnd = this.mInitialSurroundingText.getSelectionEnd();
        if (selStart > selEnd) {
            selStart = selEnd;
            selEnd = selStart;
        }
        int before = Math.min(beforeLength, selStart);
        int after = Math.min(selEnd + afterLength, length);
        int offset = selStart - before;
        if ((flags & 1) != 0) {
            newText = this.mInitialSurroundingText.getText().subSequence(offset, after);
        } else {
            newText = TextUtils.substring(this.mInitialSurroundingText.getText(), offset, after);
        }
        int newSelEnd = Math.min(selEnd - offset, length);
        return new SurroundingText(
                newText, before, newSelEnd, this.mInitialSurroundingText.getOffset() + offset);
    }

    private static boolean isCutOnSurrogate(CharSequence sourceText, int cutPosition, int policy) {
        switch (policy) {
            case 0:
                return Character.isLowSurrogate(sourceText.charAt(cutPosition));
            case 1:
                return Character.isHighSurrogate(sourceText.charAt(cutPosition));
            default:
                return false;
        }
    }

    private static boolean isPasswordInputType(int inputType) {
        int variation = inputType & 4095;
        return variation == 129 || variation == 225 || variation == 18;
    }

    public final void makeCompatible(int targetSdkVersion) {
        if (targetSdkVersion < 11) {
            switch (this.inputType & 4095) {
                case 2:
                case 18:
                    this.inputType = (this.inputType & InputType.TYPE_MASK_FLAGS) | 2;
                    break;
                case 209:
                    this.inputType = (this.inputType & InputType.TYPE_MASK_FLAGS) | 33;
                    break;
                case 225:
                    this.inputType = (this.inputType & InputType.TYPE_MASK_FLAGS) | 129;
                    break;
            }
        }
    }

    public int getInitialToolType() {
        return this.mInitialToolType;
    }

    public void setInitialToolType(int toolType) {
        this.mInitialToolType = toolType;
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1120986464257L, this.inputType);
        proto.write(1120986464258L, this.imeOptions);
        proto.write(1138166333443L, this.privateImeOptions);
        proto.write(1138166333444L, this.packageName);
        proto.write(1120986464261L, this.fieldId);
        if (this.targetInputMethodUser != null) {
            proto.write(1120986464262L, this.targetInputMethodUser.getIdentifier());
        }
        proto.end(token);
    }

    public void dump(Printer pw, String prefix) {
        dump(pw, prefix, true);
    }

    public void dump(Printer pw, String prefix, boolean dumpExtras) {
        pw.println(
                prefix
                        + "inputType=0x"
                        + Integer.toHexString(this.inputType)
                        + " imeOptions=0x"
                        + Integer.toHexString(this.imeOptions)
                        + " privateImeOptions="
                        + this.privateImeOptions);
        pw.println(
                prefix
                        + "actionLabel="
                        + ((Object) this.actionLabel)
                        + " actionId="
                        + this.actionId);
        pw.println(
                prefix
                        + "initialSelStart="
                        + this.initialSelStart
                        + " initialSelEnd="
                        + this.initialSelEnd
                        + " initialToolType="
                        + this.mInitialToolType
                        + " initialCapsMode=0x"
                        + Integer.toHexString(this.initialCapsMode));
        pw.println(
                prefix
                        + "hintText="
                        + ((Object) this.hintText)
                        + " label="
                        + ((Object) this.label));
        pw.println(
                prefix
                        + "packageName="
                        + this.packageName
                        + " autofillId="
                        + this.autofillId
                        + " fieldId="
                        + this.fieldId
                        + " fieldName="
                        + this.fieldName);
        if (dumpExtras) {
            pw.println(prefix + "extras=" + this.extras);
        }
        pw.println(prefix + "hintLocales=" + this.hintLocales);
        pw.println(
                prefix
                        + "supportedHandwritingGestureTypes="
                        + InputMethodDebug.handwritingGestureTypeFlagsToString(
                                this.mSupportedHandwritingGestureTypes));
        pw.println(
                prefix
                        + "supportedHandwritingGesturePreviewTypes="
                        + InputMethodDebug.handwritingGestureTypeFlagsToString(
                                this.mSupportedHandwritingGesturePreviewTypes));
        pw.println(prefix + "isStylusHandwritingEnabled=" + this.mIsStylusHandwritingEnabled);
        pw.println(prefix + "contentMimeTypes=" + Arrays.toString(this.contentMimeTypes));
        if (this.targetInputMethodUser != null) {
            pw.println(
                    prefix
                            + "targetInputMethodUserId="
                            + this.targetInputMethodUser.getIdentifier());
        }
    }

    public final EditorInfo createCopyInternal() {
        EditorInfo newEditorInfo = new EditorInfo();
        newEditorInfo.inputType = this.inputType;
        newEditorInfo.imeOptions = this.imeOptions;
        newEditorInfo.privateImeOptions = this.privateImeOptions;
        newEditorInfo.internalImeOptions = this.internalImeOptions;
        newEditorInfo.actionLabel = TextUtils.stringOrSpannedString(this.actionLabel);
        newEditorInfo.actionId = this.actionId;
        newEditorInfo.initialSelStart = this.initialSelStart;
        newEditorInfo.initialSelEnd = this.initialSelEnd;
        newEditorInfo.initialCapsMode = this.initialCapsMode;
        newEditorInfo.mInitialToolType = this.mInitialToolType;
        newEditorInfo.hintText = TextUtils.stringOrSpannedString(this.hintText);
        newEditorInfo.label = TextUtils.stringOrSpannedString(this.label);
        newEditorInfo.packageName = this.packageName;
        newEditorInfo.autofillId = this.autofillId;
        newEditorInfo.fieldId = this.fieldId;
        newEditorInfo.fieldName = this.fieldName;
        newEditorInfo.extras = this.extras != null ? this.extras.deepCopy() : null;
        newEditorInfo.mInitialSurroundingText = this.mInitialSurroundingText;
        newEditorInfo.hintLocales = this.hintLocales;
        newEditorInfo.contentMimeTypes = (String[]) ArrayUtils.cloneOrNull(this.contentMimeTypes);
        newEditorInfo.targetInputMethodUser = this.targetInputMethodUser;
        newEditorInfo.mSupportedHandwritingGestureTypes = this.mSupportedHandwritingGestureTypes;
        newEditorInfo.mSupportedHandwritingGesturePreviewTypes =
                this.mSupportedHandwritingGesturePreviewTypes;
        return newEditorInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.inputType);
        dest.writeInt(this.imeOptions);
        dest.writeString(this.privateImeOptions);
        dest.writeInt(this.internalImeOptions);
        TextUtils.writeToParcel(this.actionLabel, dest, flags);
        dest.writeInt(this.actionId);
        dest.writeInt(this.initialSelStart);
        dest.writeInt(this.initialSelEnd);
        dest.writeInt(this.initialCapsMode);
        dest.writeInt(this.mInitialToolType);
        TextUtils.writeToParcel(this.hintText, dest, flags);
        TextUtils.writeToParcel(this.label, dest, flags);
        dest.writeString(this.packageName);
        dest.writeParcelable(this.autofillId, flags);
        dest.writeInt(this.fieldId);
        dest.writeString(this.fieldName);
        dest.writeBundle(this.extras);
        dest.writeInt(this.mSupportedHandwritingGestureTypes);
        dest.writeInt(this.mSupportedHandwritingGesturePreviewTypes);
        if (Flags.editorinfoHandwritingEnabled()) {
            dest.writeBoolean(this.mIsStylusHandwritingEnabled);
        }
        dest.writeBoolean(this.mInitialSurroundingText != null);
        if (this.mInitialSurroundingText != null) {
            this.mInitialSurroundingText.writeToParcel(dest, flags);
        }
        if (this.hintLocales != null) {
            this.hintLocales.writeToParcel(dest, flags);
        } else {
            LocaleList.getEmptyLocaleList().writeToParcel(dest, flags);
        }
        dest.writeStringArray(this.contentMimeTypes);
        UserHandle.writeToParcel(this.targetInputMethodUser, dest);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean kindofEquals(EditorInfo that) {
        if (that == null) {
            return false;
        }
        if (this == that) {
            return true;
        }
        if (this.inputType != that.inputType
                || this.imeOptions != that.imeOptions
                || this.internalImeOptions != that.internalImeOptions
                || this.actionId != that.actionId
                || this.initialSelStart != that.initialSelStart
                || this.initialSelEnd != that.initialSelEnd
                || this.initialCapsMode != that.initialCapsMode
                || this.fieldId != that.fieldId
                || this.mSupportedHandwritingGestureTypes != that.mSupportedHandwritingGestureTypes
                || this.mSupportedHandwritingGesturePreviewTypes
                        != that.mSupportedHandwritingGesturePreviewTypes
                || !Objects.equals(this.autofillId, that.autofillId)
                || !Objects.equals(this.privateImeOptions, that.privateImeOptions)
                || !Objects.equals(this.packageName, that.packageName)
                || !Objects.equals(this.fieldName, that.fieldName)
                || !Objects.equals(this.hintLocales, that.hintLocales)
                || !Objects.equals(this.targetInputMethodUser, that.targetInputMethodUser)
                || !Arrays.equals(this.contentMimeTypes, that.contentMimeTypes)
                || !TextUtils.equals(this.actionLabel, that.actionLabel)
                || !TextUtils.equals(this.hintText, that.hintText)
                || !TextUtils.equals(this.label, that.label)) {
            return false;
        }
        if (this.extras != that.extras
                && (this.extras == null || !this.extras.kindofEquals(that.extras))) {
            return false;
        }
        if (this.mInitialSurroundingText != that.mInitialSurroundingText
                && (this.mInitialSurroundingText == null
                        || !this.mInitialSurroundingText.isEqualTo(that.mInitialSurroundingText))) {
            return false;
        }
        return true;
    }
}
