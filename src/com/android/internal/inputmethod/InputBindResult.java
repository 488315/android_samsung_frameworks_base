package com.android.internal.inputmethod;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.InputChannel;
import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.inputmethod.IInputMethodSession;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public final class InputBindResult implements Parcelable {
    public final SparseArray<IAccessibilityInputMethodSession> accessibilitySessions;
    public final InputChannel channel;
    public final String id;
    public final boolean isInputMethodSuppressingSpellChecker;
    public final IInputMethodSession method;
    public final int result;
    public final int sequence;
    public static final Parcelable.Creator<InputBindResult> CREATOR = new Parcelable.Creator<InputBindResult>() { // from class: com.android.internal.inputmethod.InputBindResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputBindResult createFromParcel(Parcel parcel) {
            return new InputBindResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputBindResult[] newArray(int i) {
            return new InputBindResult[i];
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

    public InputBindResult(int i, IInputMethodSession iInputMethodSession, SparseArray<IAccessibilityInputMethodSession> sparseArray, InputChannel inputChannel, String str, int i2, boolean z) {
        this.result = i;
        this.method = iInputMethodSession;
        this.accessibilitySessions = sparseArray;
        this.channel = inputChannel;
        this.id = str;
        this.sequence = i2;
        this.isInputMethodSuppressingSpellChecker = z;
    }

    private InputBindResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.method = IInputMethodSession.Stub.asInterface(parcel.readStrongBinder());
        int readInt = parcel.readInt();
        if (readInt < 0) {
            this.accessibilitySessions = null;
        } else {
            this.accessibilitySessions = new SparseArray<>(readInt);
            while (readInt > 0) {
                this.accessibilitySessions.append(parcel.readInt(), IAccessibilityInputMethodSession.Stub.asInterface(parcel.readStrongBinder()));
                readInt--;
            }
        }
        if (parcel.readInt() != 0) {
            this.channel = InputChannel.CREATOR.createFromParcel(parcel);
        } else {
            this.channel = null;
        }
        this.id = parcel.readString();
        this.sequence = parcel.readInt();
        this.isInputMethodSuppressingSpellChecker = parcel.readBoolean();
    }

    public String toString() {
        return "InputBindResult{result=" + getResultString() + " method=" + this.method + " id=" + this.id + " sequence=" + this.sequence + " result=" + this.result + " isInputMethodSuppressingSpellChecker=" + this.isInputMethodSuppressingSpellChecker + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeStrongInterface(this.method);
        SparseArray<IAccessibilityInputMethodSession> sparseArray = this.accessibilitySessions;
        if (sparseArray == null) {
            parcel.writeInt(-1);
        } else {
            int size = sparseArray.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeInt(this.accessibilitySessions.keyAt(i2));
                parcel.writeStrongInterface(this.accessibilitySessions.valueAt(i2));
            }
        }
        if (this.channel != null) {
            parcel.writeInt(1);
            this.channel.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.id);
        parcel.writeInt(this.sequence);
        parcel.writeBoolean(this.isInputMethodSuppressingSpellChecker);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        InputChannel inputChannel = this.channel;
        if (inputChannel != null) {
            return inputChannel.describeContents();
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

    private static InputBindResult error(int i) {
        return new InputBindResult(i, null, null, null, null, -1, false);
    }
}
