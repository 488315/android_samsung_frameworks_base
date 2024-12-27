package com.android.internal.inputmethod;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.InputChannel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class InputBindResult implements Parcelable {
    public final SparseArray<IAccessibilityInputMethodSession> accessibilitySessions;
    public final InputChannel channel;
    public final String id;
    public final boolean isInputMethodSuppressingSpellChecker;
    public final IInputMethodSession method;
    public final int result;
    public final int sequence;
    public static final Parcelable.Creator<InputBindResult> CREATOR =
            new Parcelable.Creator<
                    InputBindResult>() { // from class:
                                         // com.android.internal.inputmethod.InputBindResult.1
                @Override // android.os.Parcelable.Creator
                public InputBindResult createFromParcel(Parcel source) {
                    return new InputBindResult(source);
                }

                @Override // android.os.Parcelable.Creator
                public InputBindResult[] newArray(int size) {
                    return new InputBindResult[size];
                }
            };
    public static final InputBindResult NULL = error(5);
    public static final InputBindResult NO_IME = error(6);
    public static final InputBindResult NO_EDITOR = error(13);
    public static final InputBindResult INVALID_PACKAGE_NAME = error(7);
    public static final InputBindResult NULL_EDITOR_INFO = error(11);
    public static final InputBindResult NOT_IME_TARGET_WINDOW = error(12);
    public static final InputBindResult IME_NOT_CONNECTED = error(9);
    public static final InputBindResult INVALID_USER = error(10);
    public static final InputBindResult DISPLAY_ID_MISMATCH = error(14);
    public static final InputBindResult INVALID_DISPLAY_ID = error(15);
    public static final InputBindResult USER_SWITCHING = error(3);

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultCode {
        public static final int ERROR_DISPLAY_ID_MISMATCH = 14;
        public static final int ERROR_IME_NOT_CONNECTED = 9;
        public static final int ERROR_INVALID_DISPLAY_ID = 15;
        public static final int ERROR_INVALID_PACKAGE_NAME = 7;
        public static final int ERROR_INVALID_USER = 10;
        public static final int ERROR_NOT_IME_TARGET_WINDOW = 12;
        public static final int ERROR_NO_EDITOR = 13;
        public static final int ERROR_NO_IME = 6;
        public static final int ERROR_NULL = 5;
        public static final int ERROR_NULL_EDITOR_INFO = 11;
        public static final int ERROR_SYSTEM_NOT_READY = 8;
        public static final int SUCCESS_REPORT_WINDOW_FOCUS_ONLY = 4;
        public static final int SUCCESS_WAITING_IME_BINDING = 2;
        public static final int SUCCESS_WAITING_IME_SESSION = 1;
        public static final int SUCCESS_WAITING_USER_SWITCHING = 3;
        public static final int SUCCESS_WITH_ACCESSIBILITY_SESSION = 16;
        public static final int SUCCESS_WITH_IME_SESSION = 0;
    }

    public InputBindResult(
            int result,
            IInputMethodSession method,
            SparseArray<IAccessibilityInputMethodSession> accessibilitySessions,
            InputChannel channel,
            String id,
            int sequence,
            boolean isInputMethodSuppressingSpellChecker) {
        this.result = result;
        this.method = method;
        this.accessibilitySessions = accessibilitySessions;
        this.channel = channel;
        this.id = id;
        this.sequence = sequence;
        this.isInputMethodSuppressingSpellChecker = isInputMethodSuppressingSpellChecker;
    }

    private InputBindResult(Parcel source) {
        this.result = source.readInt();
        this.method = IInputMethodSession.Stub.asInterface(source.readStrongBinder());
        int n = source.readInt();
        if (n < 0) {
            this.accessibilitySessions = null;
        } else {
            this.accessibilitySessions = new SparseArray<>(n);
            while (n > 0) {
                int key = source.readInt();
                IAccessibilityInputMethodSession value =
                        IAccessibilityInputMethodSession.Stub.asInterface(
                                source.readStrongBinder());
                this.accessibilitySessions.append(key, value);
                n--;
            }
        }
        if (source.readInt() != 0) {
            this.channel = InputChannel.CREATOR.createFromParcel(source);
        } else {
            this.channel = null;
        }
        this.id = source.readString();
        this.sequence = source.readInt();
        this.isInputMethodSuppressingSpellChecker = source.readBoolean();
    }

    public String toString() {
        return "InputBindResult{result="
                + getResultString()
                + " method="
                + this.method
                + " id="
                + this.id
                + " sequence="
                + this.sequence
                + " result="
                + this.result
                + " isInputMethodSuppressingSpellChecker="
                + this.isInputMethodSuppressingSpellChecker
                + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.result);
        dest.writeStrongInterface(this.method);
        if (this.accessibilitySessions == null) {
            dest.writeInt(-1);
        } else {
            int n = this.accessibilitySessions.size();
            dest.writeInt(n);
            for (int i = 0; i < n; i++) {
                dest.writeInt(this.accessibilitySessions.keyAt(i));
                dest.writeStrongInterface(this.accessibilitySessions.valueAt(i));
            }
        }
        if (this.channel != null) {
            dest.writeInt(1);
            this.channel.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
        dest.writeString(this.id);
        dest.writeInt(this.sequence);
        dest.writeBoolean(this.isInputMethodSuppressingSpellChecker);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (this.channel != null) {
            return this.channel.describeContents();
        }
        return 0;
    }

    private String getResultString() {
        switch (this.result) {
            case 0:
                return "SUCCESS_WITH_IME_SESSION";
            case 1:
                return "SUCCESS_WAITING_IME_SESSION";
            case 2:
                return "SUCCESS_WAITING_IME_BINDING";
            case 3:
                return "SUCCESS_WAITING_USER_SWITCHING";
            case 4:
                return "SUCCESS_REPORT_WINDOW_FOCUS_ONLY";
            case 5:
                return "ERROR_NULL";
            case 6:
                return "ERROR_NO_IME";
            case 7:
                return "ERROR_INVALID_PACKAGE_NAME";
            case 8:
                return "ERROR_SYSTEM_NOT_READY";
            case 9:
                return "ERROR_IME_NOT_CONNECTED";
            case 10:
                return "ERROR_INVALID_USER";
            case 11:
                return "ERROR_NULL_EDITOR_INFO";
            case 12:
                return "ERROR_NOT_IME_TARGET_WINDOW";
            case 13:
                return "ERROR_NO_EDITOR";
            case 14:
                return "ERROR_DISPLAY_ID_MISMATCH";
            case 15:
                return "ERROR_INVALID_DISPLAY_ID";
            case 16:
                return "SUCCESS_WITH_ACCESSIBILITY_SESSION";
            default:
                return "Unknown(" + this.result + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    private static InputBindResult error(int result) {
        return new InputBindResult(result, null, null, null, null, -1, false);
    }
}
