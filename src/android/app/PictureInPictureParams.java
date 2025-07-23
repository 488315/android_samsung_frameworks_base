package android.app;

import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Rational;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class PictureInPictureParams implements Parcelable {
    public static final Parcelable.Creator<PictureInPictureParams> CREATOR = new Parcelable.Creator<PictureInPictureParams>() { // from class: android.app.PictureInPictureParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureInPictureParams createFromParcel(Parcel parcel) {
            return new PictureInPictureParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureInPictureParams[] newArray(int i) {
            return new PictureInPictureParams[i];
        }
    };
    private Rational mAspectRatio;
    private Boolean mAutoEnterEnabled;
    private RemoteAction mCloseAction;
    private Rational mExpandedAspectRatio;
    private Boolean mIsLaunchIntoPip;
    private Boolean mSeamlessResizeEnabled;
    private Rect mSourceRectHint;
    private CharSequence mSubtitle;
    private CharSequence mTitle;
    private List<RemoteAction> mUserActions;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private Rational mAspectRatio;
        private Boolean mAutoEnterEnabled;
        private RemoteAction mCloseAction;
        private Rational mExpandedAspectRatio;
        private Boolean mIsLaunchIntoPip;
        private Boolean mSeamlessResizeEnabled;
        private Rect mSourceRectHint;
        private CharSequence mSubtitle;
        private CharSequence mTitle;
        private List<RemoteAction> mUserActions;

        public Builder() {
        }

        public Builder(PictureInPictureParams pictureInPictureParams) {
            this.mAspectRatio = pictureInPictureParams.mAspectRatio;
            this.mUserActions = pictureInPictureParams.mUserActions;
            this.mCloseAction = pictureInPictureParams.mCloseAction;
            this.mSourceRectHint = pictureInPictureParams.mSourceRectHint;
            this.mAutoEnterEnabled = pictureInPictureParams.mAutoEnterEnabled;
            this.mSeamlessResizeEnabled = pictureInPictureParams.mSeamlessResizeEnabled;
            this.mTitle = pictureInPictureParams.mTitle;
            this.mSubtitle = pictureInPictureParams.mSubtitle;
            this.mIsLaunchIntoPip = pictureInPictureParams.mIsLaunchIntoPip;
        }

        public Builder setAspectRatio(Rational rational) {
            this.mAspectRatio = rational;
            return this;
        }

        public Builder setExpandedAspectRatio(Rational rational) {
            this.mExpandedAspectRatio = rational;
            return this;
        }

        public Builder setActions(List<RemoteAction> list) {
            if (this.mUserActions != null) {
                this.mUserActions = null;
            }
            if (list != null) {
                this.mUserActions = new ArrayList(list);
            }
            return this;
        }

        public Builder setCloseAction(RemoteAction remoteAction) {
            this.mCloseAction = remoteAction;
            return this;
        }

        public Builder setSourceRectHint(Rect rect) {
            if (rect == null) {
                this.mSourceRectHint = null;
                return this;
            }
            this.mSourceRectHint = new Rect(rect);
            return this;
        }

        public Builder setAutoEnterEnabled(boolean z) {
            this.mAutoEnterEnabled = Boolean.valueOf(z);
            return this;
        }

        public Builder setSeamlessResizeEnabled(boolean z) {
            this.mSeamlessResizeEnabled = Boolean.valueOf(z);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public Builder setSubtitle(CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        Builder setIsLaunchIntoPip(boolean z) {
            this.mIsLaunchIntoPip = Boolean.valueOf(z);
            return this;
        }

        public PictureInPictureParams build() {
            return new PictureInPictureParams(this.mAspectRatio, this.mExpandedAspectRatio, this.mUserActions, this.mCloseAction, this.mSourceRectHint, this.mAutoEnterEnabled, this.mSeamlessResizeEnabled, this.mTitle, this.mSubtitle, this.mIsLaunchIntoPip);
        }
    }

    PictureInPictureParams() {
    }

    PictureInPictureParams(Parcel parcel) {
        this.mAspectRatio = readRationalFromParcel(parcel);
        this.mExpandedAspectRatio = readRationalFromParcel(parcel);
        if (parcel.readInt() != 0) {
            ArrayList arrayList = new ArrayList();
            this.mUserActions = arrayList;
            parcel.readTypedList(arrayList, RemoteAction.CREATOR);
        }
        this.mCloseAction = (RemoteAction) parcel.readTypedObject(RemoteAction.CREATOR);
        if (parcel.readInt() != 0) {
            this.mSourceRectHint = Rect.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mAutoEnterEnabled = Boolean.valueOf(parcel.readBoolean());
        }
        if (parcel.readInt() != 0) {
            this.mSeamlessResizeEnabled = Boolean.valueOf(parcel.readBoolean());
        }
        if (parcel.readInt() != 0) {
            this.mTitle = parcel.readCharSequence();
        }
        if (parcel.readInt() != 0) {
            this.mSubtitle = parcel.readCharSequence();
        }
        if (parcel.readInt() != 0) {
            this.mIsLaunchIntoPip = Boolean.valueOf(parcel.readBoolean());
        }
    }

    PictureInPictureParams(Rational rational, Rational rational2, List<RemoteAction> list, RemoteAction remoteAction, Rect rect, Boolean bool, Boolean bool2, CharSequence charSequence, CharSequence charSequence2, Boolean bool3) {
        this.mAspectRatio = rational;
        this.mExpandedAspectRatio = rational2;
        this.mUserActions = list;
        this.mCloseAction = remoteAction;
        this.mSourceRectHint = rect;
        this.mAutoEnterEnabled = bool;
        this.mSeamlessResizeEnabled = bool2;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mIsLaunchIntoPip = bool3;
    }

    public PictureInPictureParams(PictureInPictureParams pictureInPictureParams) {
        this(pictureInPictureParams.mAspectRatio, pictureInPictureParams.mExpandedAspectRatio, pictureInPictureParams.mUserActions, pictureInPictureParams.mCloseAction, pictureInPictureParams.hasSourceBoundsHint() ? new Rect(pictureInPictureParams.getSourceRectHint()) : null, pictureInPictureParams.mAutoEnterEnabled, pictureInPictureParams.mSeamlessResizeEnabled, pictureInPictureParams.mTitle, pictureInPictureParams.mSubtitle, pictureInPictureParams.mIsLaunchIntoPip);
    }

    public void copyOnlySet(PictureInPictureParams pictureInPictureParams) {
        if (pictureInPictureParams.hasSetAspectRatio()) {
            this.mAspectRatio = pictureInPictureParams.mAspectRatio;
        }
        this.mExpandedAspectRatio = pictureInPictureParams.mExpandedAspectRatio;
        if (pictureInPictureParams.hasSetActions()) {
            this.mUserActions = pictureInPictureParams.mUserActions;
        }
        if (pictureInPictureParams.hasSetCloseAction()) {
            this.mCloseAction = pictureInPictureParams.mCloseAction;
        }
        if (pictureInPictureParams.hasSourceBoundsHint()) {
            this.mSourceRectHint = new Rect(pictureInPictureParams.getSourceRectHint());
        }
        Boolean bool = pictureInPictureParams.mAutoEnterEnabled;
        if (bool != null) {
            this.mAutoEnterEnabled = bool;
        }
        Boolean bool2 = pictureInPictureParams.mSeamlessResizeEnabled;
        if (bool2 != null) {
            this.mSeamlessResizeEnabled = bool2;
        }
        if (pictureInPictureParams.hasSetTitle()) {
            this.mTitle = pictureInPictureParams.mTitle;
        }
        if (pictureInPictureParams.hasSetSubtitle()) {
            this.mSubtitle = pictureInPictureParams.mSubtitle;
        }
        Boolean bool3 = pictureInPictureParams.mIsLaunchIntoPip;
        if (bool3 != null) {
            this.mIsLaunchIntoPip = bool3;
        }
    }

    public float getAspectRatioFloat() {
        Rational rational = this.mAspectRatio;
        if (rational != null) {
            return rational.floatValue();
        }
        return 0.0f;
    }

    public Rational getAspectRatio() {
        return this.mAspectRatio;
    }

    public boolean hasSetAspectRatio() {
        return this.mAspectRatio != null;
    }

    public float getExpandedAspectRatioFloat() {
        Rational rational = this.mExpandedAspectRatio;
        if (rational != null) {
            return rational.floatValue();
        }
        return 0.0f;
    }

    public Rational getExpandedAspectRatio() {
        return this.mExpandedAspectRatio;
    }

    public boolean hasSetExpandedAspectRatio() {
        return this.mExpandedAspectRatio != null;
    }

    public List<RemoteAction> getActions() {
        List<RemoteAction> list = this.mUserActions;
        return list == null ? new ArrayList() : list;
    }

    public boolean hasSetActions() {
        return this.mUserActions != null;
    }

    public RemoteAction getCloseAction() {
        return this.mCloseAction;
    }

    public boolean hasSetCloseAction() {
        return this.mCloseAction != null;
    }

    public void truncateActions(int i) {
        if (hasSetActions()) {
            List<RemoteAction> list = this.mUserActions;
            this.mUserActions = list.subList(0, Math.min(list.size(), i));
        }
    }

    public Rect getSourceRectHint() {
        return this.mSourceRectHint;
    }

    public boolean hasSourceBoundsHint() {
        Rect rect = this.mSourceRectHint;
        return (rect == null || rect.isEmpty()) ? false : true;
    }

    public boolean isAutoEnterEnabled() {
        Boolean bool = this.mAutoEnterEnabled;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isSeamlessResizeEnabled() {
        Boolean bool = this.mSeamlessResizeEnabled;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean hasSetTitle() {
        return this.mTitle != null;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public boolean hasSetSubtitle() {
        return this.mSubtitle != null;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public boolean isLaunchIntoPip() {
        Boolean bool = this.mIsLaunchIntoPip;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean empty() {
        return (hasSourceBoundsHint() || hasSetActions() || hasSetCloseAction() || hasSetAspectRatio() || hasSetExpandedAspectRatio() || this.mAutoEnterEnabled != null || this.mSeamlessResizeEnabled != null || hasSetTitle() || hasSetSubtitle() || this.mIsLaunchIntoPip != null) ? false : true;
    }

    public static boolean isSameAspectRatio(Rect rect, Rational rational) {
        return !rect.isEmpty() && rational.floatValue() > 0.0f && (Math.abs(((rational.getNumerator() * rect.height()) / rational.getDenominator()) - rect.width()) <= 1 || Math.abs(((rational.getDenominator() * rect.width()) / rational.getNumerator()) - rect.height()) <= 1);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PictureInPictureParams)) {
            return false;
        }
        PictureInPictureParams pictureInPictureParams = (PictureInPictureParams) obj;
        return Objects.equals(this.mAutoEnterEnabled, pictureInPictureParams.mAutoEnterEnabled) && Objects.equals(this.mSeamlessResizeEnabled, pictureInPictureParams.mSeamlessResizeEnabled) && Objects.equals(this.mAspectRatio, pictureInPictureParams.mAspectRatio) && Objects.equals(this.mExpandedAspectRatio, pictureInPictureParams.mExpandedAspectRatio) && Objects.equals(this.mUserActions, pictureInPictureParams.mUserActions) && Objects.equals(this.mCloseAction, pictureInPictureParams.mCloseAction) && Objects.equals(this.mSourceRectHint, pictureInPictureParams.mSourceRectHint) && Objects.equals(this.mTitle, pictureInPictureParams.mTitle) && Objects.equals(this.mSubtitle, pictureInPictureParams.mSubtitle) && Objects.equals(this.mIsLaunchIntoPip, pictureInPictureParams.mIsLaunchIntoPip);
    }

    public int hashCode() {
        return Objects.hash(this.mAspectRatio, this.mExpandedAspectRatio, this.mUserActions, this.mCloseAction, this.mSourceRectHint, this.mAutoEnterEnabled, this.mSeamlessResizeEnabled, this.mTitle, this.mSubtitle, this.mIsLaunchIntoPip);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeRationalToParcel(this.mAspectRatio, parcel);
        writeRationalToParcel(this.mExpandedAspectRatio, parcel);
        if (this.mUserActions != null) {
            parcel.writeInt(1);
            parcel.writeTypedList(this.mUserActions, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeTypedObject(this.mCloseAction, 0);
        if (this.mSourceRectHint != null) {
            parcel.writeInt(1);
            this.mSourceRectHint.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.mAutoEnterEnabled != null) {
            parcel.writeInt(1);
            parcel.writeBoolean(this.mAutoEnterEnabled.booleanValue());
        } else {
            parcel.writeInt(0);
        }
        if (this.mSeamlessResizeEnabled != null) {
            parcel.writeInt(1);
            parcel.writeBoolean(this.mSeamlessResizeEnabled.booleanValue());
        } else {
            parcel.writeInt(0);
        }
        if (this.mTitle != null) {
            parcel.writeInt(1);
            parcel.writeCharSequence(this.mTitle);
        } else {
            parcel.writeInt(0);
        }
        if (this.mSubtitle != null) {
            parcel.writeInt(1);
            parcel.writeCharSequence(this.mSubtitle);
        } else {
            parcel.writeInt(0);
        }
        if (this.mIsLaunchIntoPip != null) {
            parcel.writeInt(1);
            parcel.writeBoolean(this.mIsLaunchIntoPip.booleanValue());
        } else {
            parcel.writeInt(0);
        }
    }

    private void writeRationalToParcel(Rational rational, Parcel parcel) {
        if (rational != null) {
            parcel.writeInt(1);
            parcel.writeInt(rational.getNumerator());
            parcel.writeInt(rational.getDenominator());
            return;
        }
        parcel.writeInt(0);
    }

    private Rational readRationalFromParcel(Parcel parcel) {
        if (parcel.readInt() != 0) {
            return new Rational(parcel.readInt(), parcel.readInt());
        }
        return null;
    }

    public String toString() {
        return "PictureInPictureParams( aspectRatio=" + getAspectRatio() + " expandedAspectRatio=" + this.mExpandedAspectRatio + " sourceRectHint=" + getSourceRectHint() + " hasSetActions=" + hasSetActions() + " hasSetCloseAction=" + hasSetCloseAction() + " isAutoPipEnabled=" + isAutoEnterEnabled() + " isSeamlessResizeEnabled=" + isSeamlessResizeEnabled() + " title=" + ((Object) getTitle()) + " subtitle=" + ((Object) getSubtitle()) + " isLaunchIntoPip=" + isLaunchIntoPip() + NavigationBarInflaterView.KEY_CODE_END;
    }
}
