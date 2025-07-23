package android.hardware.biometrics;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class PromptContentViewWithMoreOptionsButton implements PromptContentViewParcelable {
    public static final Parcelable.Creator<PromptContentViewWithMoreOptionsButton> CREATOR = new Parcelable.Creator<PromptContentViewWithMoreOptionsButton>() { // from class: android.hardware.biometrics.PromptContentViewWithMoreOptionsButton.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptContentViewWithMoreOptionsButton createFromParcel(Parcel parcel) {
            return new PromptContentViewWithMoreOptionsButton(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PromptContentViewWithMoreOptionsButton[] newArray(int i) {
            return new PromptContentViewWithMoreOptionsButton[i];
        }
    };
    static final int MAX_DESCRIPTION_CHARACTER_NUMBER = 225;
    private static final String TAG = "PromptContentViewWithMoreOptionsButton";
    private BiometricPrompt.ButtonInfo mButtonInfo;
    private final String mDescription;
    private DialogInterface.OnClickListener mListener;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PromptContentViewWithMoreOptionsButton(String str, Executor executor, DialogInterface.OnClickListener onClickListener) {
        this.mDescription = str;
        this.mListener = onClickListener;
        this.mButtonInfo = new BiometricPrompt.ButtonInfo(executor, onClickListener);
    }

    private PromptContentViewWithMoreOptionsButton(Parcel parcel) {
        this.mDescription = parcel.readString();
    }

    public String getDescription() {
        return this.mDescription;
    }

    public DialogInterface.OnClickListener getMoreOptionsButtonListener() {
        return this.mListener;
    }

    BiometricPrompt.ButtonInfo getButtonInfo() {
        return this.mButtonInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mDescription);
    }

    public static final class Builder {
        private String mDescription;
        private Executor mExecutor;
        private DialogInterface.OnClickListener mListener;

        public Builder setDescription(String str) {
            if (str.length() > 225) {
                Log.w(PromptContentViewWithMoreOptionsButton.TAG, "The character number of description exceeds 225");
            }
            this.mDescription = str;
            return this;
        }

        public Builder setMoreOptionsButtonListener(Executor executor, DialogInterface.OnClickListener onClickListener) {
            this.mExecutor = executor;
            this.mListener = onClickListener;
            return this;
        }

        public PromptContentViewWithMoreOptionsButton build() {
            if (this.mExecutor == null) {
                throw new IllegalArgumentException("The executor for the listener of more options button on prompt content must be set and non-null if PromptContentViewWithMoreOptionsButton is used.");
            }
            if (this.mListener == null) {
                throw new IllegalArgumentException("The listener of more options button on prompt content must be set and non-null if PromptContentViewWithMoreOptionsButton is used.");
            }
            return new PromptContentViewWithMoreOptionsButton(this.mDescription, this.mExecutor, this.mListener);
        }
    }
}
