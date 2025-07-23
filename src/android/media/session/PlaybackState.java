package android.media.session;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.security.keystore.KeyProperties;
import android.service.timezone.TimeZoneProviderService;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class PlaybackState implements Parcelable {
    public static final long ACTION_FAST_FORWARD = 64;
    public static final long ACTION_PAUSE = 2;
    public static final long ACTION_PLAY = 4;
    public static final long ACTION_PLAY_FROM_MEDIA_ID = 1024;
    public static final long ACTION_PLAY_FROM_SEARCH = 2048;
    public static final long ACTION_PLAY_FROM_URI = 8192;
    public static final long ACTION_PLAY_PAUSE = 512;
    public static final long ACTION_PREPARE = 16384;
    public static final long ACTION_PREPARE_FROM_MEDIA_ID = 32768;
    public static final long ACTION_PREPARE_FROM_SEARCH = 65536;
    public static final long ACTION_PREPARE_FROM_URI = 131072;
    public static final long ACTION_REWIND = 8;
    public static final long ACTION_SEEK_TO = 256;
    public static final long ACTION_SET_PLAYBACK_SPEED = 4194304;
    public static final long ACTION_SET_RATING = 128;
    public static final long ACTION_SKIP_TO_NEXT = 32;
    public static final long ACTION_SKIP_TO_PREVIOUS = 16;
    public static final long ACTION_SKIP_TO_QUEUE_ITEM = 4096;
    public static final long ACTION_STOP = 1;
    public static final Parcelable.Creator<PlaybackState> CREATOR = new Parcelable.Creator<PlaybackState>() { // from class: android.media.session.PlaybackState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackState createFromParcel(Parcel parcel) {
            return new PlaybackState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PlaybackState[] newArray(int i) {
            return new PlaybackState[i];
        }
    };
    public static final long PLAYBACK_POSITION_UNKNOWN = -1;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_CONNECTING = 8;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FAST_FORWARDING = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_REWINDING = 5;
    public static final int STATE_SKIPPING_TO_NEXT = 10;
    public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    public static final int STATE_STOPPED = 1;
    private static final String TAG = "PlaybackState";
    private final long mActions;
    private final long mActiveItemId;
    private final long mBufferedPosition;
    private List<CustomAction> mCustomActions;
    private final CharSequence mErrorMessage;
    private final Bundle mExtras;
    private final long mPosition;
    private final float mSpeed;
    private final int mState;
    private final long mUpdateTime;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Actions {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PlaybackState(int i, long j, long j2, float f, long j3, long j4, List<CustomAction> list, long j5, CharSequence charSequence, Bundle bundle) {
        this.mState = i;
        this.mPosition = j;
        this.mSpeed = f;
        this.mUpdateTime = j2;
        this.mBufferedPosition = j3;
        this.mActions = j4;
        this.mCustomActions = new ArrayList(list);
        this.mActiveItemId = j5;
        this.mErrorMessage = charSequence;
        this.mExtras = bundle;
    }

    private PlaybackState(Parcel parcel) {
        this.mState = parcel.readInt();
        this.mPosition = parcel.readLong();
        this.mSpeed = parcel.readFloat();
        this.mUpdateTime = parcel.readLong();
        this.mBufferedPosition = parcel.readLong();
        this.mActions = parcel.readLong();
        this.mCustomActions = parcel.createTypedArrayList(CustomAction.CREATOR);
        this.mActiveItemId = parcel.readLong();
        this.mErrorMessage = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mExtras = parcel.readBundle();
    }

    public String toString() {
        return "PlaybackState {state=" + getStringForStateInt(this.mState) + NavigationBarInflaterView.KEY_CODE_START + this.mState + "), position=" + this.mPosition + ", buffered position=" + this.mBufferedPosition + ", speed=" + this.mSpeed + ", updated=" + this.mUpdateTime + ", actions=" + this.mActions + ", custom actions=" + this.mCustomActions + ", active item id=" + this.mActiveItemId + ", error=" + this.mErrorMessage + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mPosition);
        parcel.writeFloat(this.mSpeed);
        parcel.writeLong(this.mUpdateTime);
        parcel.writeLong(this.mBufferedPosition);
        parcel.writeLong(this.mActions);
        parcel.writeTypedList(this.mCustomActions);
        parcel.writeLong(this.mActiveItemId);
        TextUtils.writeToParcel(this.mErrorMessage, parcel, 0);
        parcel.writeBundle(this.mExtras);
    }

    public int getState() {
        return this.mState;
    }

    public long getPosition() {
        return this.mPosition;
    }

    public long getBufferedPosition() {
        return this.mBufferedPosition;
    }

    public float getPlaybackSpeed() {
        return this.mSpeed;
    }

    public long getActions() {
        return this.mActions;
    }

    public List<CustomAction> getCustomActions() {
        return this.mCustomActions;
    }

    public CharSequence getErrorMessage() {
        return this.mErrorMessage;
    }

    public long getLastPositionUpdateTime() {
        return this.mUpdateTime;
    }

    public long getActiveQueueItemId() {
        return this.mActiveItemId;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean isActive() {
        switch (this.mState) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
                return true;
            case 7:
            default:
                return false;
        }
    }

    private static String getStringForStateInt(int i) {
        switch (i) {
            case 0:
                return KeyProperties.DIGEST_NONE;
            case 1:
                return "STOPPED";
            case 2:
                return "PAUSED";
            case 3:
                return "PLAYING";
            case 4:
                return "FAST_FORWARDING";
            case 5:
                return "REWINDING";
            case 6:
                return "BUFFERING";
            case 7:
                return TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
            case 8:
                return "CONNECTING";
            case 9:
                return "SKIPPING_TO_PREVIOUS";
            case 10:
                return "SKIPPING_TO_NEXT";
            case 11:
                return "SKIPPING_TO_QUEUE_ITEM";
            default:
                return "UNKNOWN";
        }
    }

    public static final class CustomAction implements Parcelable {
        public static final Parcelable.Creator<CustomAction> CREATOR = new Parcelable.Creator<CustomAction>() { // from class: android.media.session.PlaybackState.CustomAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomAction createFromParcel(Parcel parcel) {
                return new CustomAction(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public CustomAction[] newArray(int i) {
                return new CustomAction[i];
            }
        };
        private final String mAction;
        private final Bundle mExtras;
        private final int mIcon;
        private final CharSequence mName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private CustomAction(String str, CharSequence charSequence, int i, Bundle bundle) {
            this.mAction = str;
            this.mName = charSequence;
            this.mIcon = i;
            this.mExtras = bundle;
        }

        private CustomAction(Parcel parcel) {
            this.mAction = parcel.readString();
            this.mName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mIcon = parcel.readInt();
            this.mExtras = parcel.readBundle();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mAction);
            TextUtils.writeToParcel(this.mName, parcel, i);
            parcel.writeInt(this.mIcon);
            parcel.writeBundle(this.mExtras);
        }

        public String getAction() {
            return this.mAction;
        }

        public CharSequence getName() {
            return this.mName;
        }

        public int getIcon() {
            return this.mIcon;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String toString() {
            return "Action:mName='" + ((Object) this.mName) + ", mIcon=" + this.mIcon + ", mExtras=" + this.mExtras;
        }

        public static final class Builder {
            private final String mAction;
            private Bundle mExtras;
            private final int mIcon;
            private final CharSequence mName;

            public Builder(String str, CharSequence charSequence, int i) {
                if (TextUtils.isEmpty(str)) {
                    throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
                }
                if (TextUtils.isEmpty(charSequence)) {
                    throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
                }
                if (i == 0) {
                    throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
                }
                this.mAction = str;
                this.mName = charSequence;
                this.mIcon = i;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public CustomAction build() {
                return new CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
            }
        }
    }

    public static final class Builder {
        private long mActions;
        private long mActiveItemId;
        private long mBufferedPosition;
        private final List<CustomAction> mCustomActions;
        private CharSequence mErrorMessage;
        private Bundle mExtras;
        private long mPosition;
        private float mSpeed;
        private int mState;
        private long mUpdateTime;

        public Builder() {
            this.mCustomActions = new ArrayList();
            this.mActiveItemId = -1L;
        }

        public Builder(PlaybackState playbackState) {
            ArrayList arrayList = new ArrayList();
            this.mCustomActions = arrayList;
            this.mActiveItemId = -1L;
            if (playbackState == null) {
                return;
            }
            this.mState = playbackState.mState;
            this.mPosition = playbackState.mPosition;
            this.mBufferedPosition = playbackState.mBufferedPosition;
            this.mSpeed = playbackState.mSpeed;
            this.mActions = playbackState.mActions;
            if (playbackState.mCustomActions != null) {
                arrayList.addAll(playbackState.mCustomActions);
            }
            this.mErrorMessage = playbackState.mErrorMessage;
            this.mUpdateTime = playbackState.mUpdateTime;
            this.mActiveItemId = playbackState.mActiveItemId;
            this.mExtras = playbackState.mExtras;
        }

        public Builder setState(int i, long j, float f, long j2) {
            this.mState = i;
            this.mPosition = j;
            this.mUpdateTime = j2;
            this.mSpeed = f;
            return this;
        }

        public Builder setState(int i, long j, float f) {
            return setState(i, j, f, SystemClock.elapsedRealtime());
        }

        public Builder setActions(long j) {
            this.mActions = j;
            return this;
        }

        public Builder addCustomAction(String str, String str2, int i) {
            return addCustomAction(new CustomAction(str, str2, i, null));
        }

        public Builder addCustomAction(CustomAction customAction) {
            if (customAction == null) {
                throw new IllegalArgumentException("You may not add a null CustomAction to PlaybackState.");
            }
            this.mCustomActions.add(customAction);
            return this;
        }

        public Builder setBufferedPosition(long j) {
            this.mBufferedPosition = j;
            return this;
        }

        public Builder setActiveQueueItemId(long j) {
            this.mActiveItemId = j;
            return this;
        }

        public Builder setErrorMessage(CharSequence charSequence) {
            this.mErrorMessage = charSequence;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public PlaybackState build() {
            return new PlaybackState(this.mState, this.mPosition, this.mUpdateTime, this.mSpeed, this.mBufferedPosition, this.mActions, this.mCustomActions, this.mActiveItemId, this.mErrorMessage, this.mExtras);
        }
    }
}
