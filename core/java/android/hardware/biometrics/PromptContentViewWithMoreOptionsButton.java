package android.hardware.biometrics;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.Executor;

public final class PromptContentViewWithMoreOptionsButton implements PromptContentViewParcelable {
    public static final Parcelable.Creator<PromptContentViewWithMoreOptionsButton> CREATOR =
            new Parcelable.Creator<PromptContentViewWithMoreOptionsButton>() { // from class:
                // android.hardware.biometrics.PromptContentViewWithMoreOptionsButton.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PromptContentViewWithMoreOptionsButton createFromParcel(Parcel in) {
                    return new PromptContentViewWithMoreOptionsButton(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public PromptContentViewWithMoreOptionsButton[] newArray(int size) {
                    return new PromptContentViewWithMoreOptionsButton[size];
                }
            };
    static final int MAX_DESCRIPTION_CHARACTER_NUMBER = 225;
    private BiometricPrompt.ButtonInfo mButtonInfo;
    private final String mDescription;
    private DialogInterface.OnClickListener mListener;

    private PromptContentViewWithMoreOptionsButton(
            String description, Executor executor, DialogInterface.OnClickListener listener) {
        this.mDescription = description;
        this.mListener = listener;
        this.mButtonInfo = new BiometricPrompt.ButtonInfo(executor, listener);
    }

    private PromptContentViewWithMoreOptionsButton(Parcel in) {
        this.mDescription = in.readString();
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mDescription);
    }

    public static final class Builder {
        private String mDescription;
        private Executor mExecutor;
        private DialogInterface.OnClickListener mListener;

        public Builder setDescription(String description) {
            if (description.length() > 225) {
                throw new IllegalArgumentException(
                        "The character number of description exceeds 225");
            }
            this.mDescription = description;
            return this;
        }

        public Builder setMoreOptionsButtonListener(
                Executor executor, DialogInterface.OnClickListener listener) {
            this.mExecutor = executor;
            this.mListener = listener;
            return this;
        }

        public PromptContentViewWithMoreOptionsButton build() {
            if (this.mExecutor == null) {
                throw new IllegalArgumentException(
                        "The executor for the listener of more options button on prompt content"
                            + " must be set and non-null if PromptContentViewWithMoreOptionsButton"
                            + " is used.");
            }
            if (this.mListener == null) {
                throw new IllegalArgumentException(
                        "The listener of more options button on prompt content must be set and"
                                + " non-null if PromptContentViewWithMoreOptionsButton is used.");
            }
            return new PromptContentViewWithMoreOptionsButton(
                    this.mDescription, this.mExecutor, this.mListener);
        }
    }
}
