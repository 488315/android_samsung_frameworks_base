package android.media;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;

/* loaded from: classes2.dex */
public final class AudioFocusRequest {
    private static final AudioAttributes FOCUS_DEFAULT_ATTR = new AudioAttributes.Builder().setUsage(1).build();
    public static final String KEY_ACCESSIBILITY_FORCE_FOCUS_DUCKING = "a11y_force_ducking";
    private final AudioAttributes mAttr;
    private final int mFlags;
    private final int mFocusGain;
    private final AudioManager.OnAudioFocusChangeListener mFocusListener;
    private final Handler mListenerHandler;

    static final boolean isValidFocusGain(int i) {
        return i == 1 || i == 2 || i == 3 || i == 4;
    }

    private AudioFocusRequest(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, Handler handler, AudioAttributes audioAttributes, int i, int i2) {
        this.mFocusListener = onAudioFocusChangeListener;
        this.mListenerHandler = handler;
        this.mFocusGain = i;
        this.mAttr = audioAttributes;
        this.mFlags = i2;
    }

    public AudioManager.OnAudioFocusChangeListener getOnAudioFocusChangeListener() {
        return this.mFocusListener;
    }

    public Handler getOnAudioFocusChangeListenerHandler() {
        return this.mListenerHandler;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAttr;
    }

    public int getFocusGain() {
        return this.mFocusGain;
    }

    public boolean willPauseWhenDucked() {
        return (this.mFlags & 2) == 2;
    }

    public boolean acceptsDelayedFocusGain() {
        return (this.mFlags & 1) == 1;
    }

    @SystemApi
    public boolean locksFocus() {
        return (this.mFlags & 4) == 4;
    }

    int getFlags() {
        return this.mFlags;
    }

    public static final class Builder {
        private boolean mA11yForceDucking;
        private AudioAttributes mAttr;
        private boolean mDelayedFocus;
        private int mFocusGain;
        private AudioManager.OnAudioFocusChangeListener mFocusListener;
        private boolean mFocusLocked;
        private Handler mListenerHandler;
        private boolean mPausesOnDuck;

        public Builder(int i) {
            this.mAttr = AudioFocusRequest.FOCUS_DEFAULT_ATTR;
            this.mPausesOnDuck = false;
            this.mDelayedFocus = false;
            this.mFocusLocked = false;
            this.mA11yForceDucking = false;
            setFocusGain(i);
        }

        public Builder(AudioFocusRequest audioFocusRequest) {
            this.mAttr = AudioFocusRequest.FOCUS_DEFAULT_ATTR;
            this.mPausesOnDuck = false;
            this.mDelayedFocus = false;
            this.mFocusLocked = false;
            this.mA11yForceDucking = false;
            if (audioFocusRequest == null) {
                throw new IllegalArgumentException("Illegal null AudioFocusRequest");
            }
            this.mAttr = audioFocusRequest.mAttr;
            this.mFocusListener = audioFocusRequest.mFocusListener;
            this.mListenerHandler = audioFocusRequest.mListenerHandler;
            this.mFocusGain = audioFocusRequest.mFocusGain;
            this.mPausesOnDuck = audioFocusRequest.willPauseWhenDucked();
            this.mDelayedFocus = audioFocusRequest.acceptsDelayedFocusGain();
        }

        public Builder setFocusGain(int i) {
            if (!AudioFocusRequest.isValidFocusGain(i)) {
                throw new IllegalArgumentException("Illegal audio focus gain type " + i);
            }
            this.mFocusGain = i;
            return this;
        }

        public Builder setOnAudioFocusChangeListener(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
            if (onAudioFocusChangeListener == null) {
                throw new NullPointerException("Illegal null focus listener");
            }
            this.mFocusListener = onAudioFocusChangeListener;
            this.mListenerHandler = null;
            return this;
        }

        Builder setOnAudioFocusChangeListenerInt(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, Handler handler) {
            this.mFocusListener = onAudioFocusChangeListener;
            this.mListenerHandler = handler;
            return this;
        }

        public Builder setOnAudioFocusChangeListener(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, Handler handler) {
            if (onAudioFocusChangeListener == null || handler == null) {
                throw new NullPointerException("Illegal null focus listener or handler");
            }
            this.mFocusListener = onAudioFocusChangeListener;
            this.mListenerHandler = handler;
            return this;
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes) {
            if (audioAttributes == null) {
                throw new NullPointerException("Illegal null AudioAttributes");
            }
            this.mAttr = audioAttributes;
            return this;
        }

        public Builder setWillPauseWhenDucked(boolean z) {
            this.mPausesOnDuck = z;
            return this;
        }

        public Builder setAcceptsDelayedFocusGain(boolean z) {
            this.mDelayedFocus = z;
            return this;
        }

        @SystemApi
        public Builder setLocksFocus(boolean z) {
            this.mFocusLocked = z;
            return this;
        }

        public Builder setForceDucking(boolean z) {
            this.mA11yForceDucking = z;
            return this;
        }

        public AudioFocusRequest build() {
            Bundle bundle;
            if ((this.mDelayedFocus || this.mPausesOnDuck) && this.mFocusListener == null) {
                throw new IllegalStateException("Can't use delayed focus or pause on duck without a listener");
            }
            if (this.mA11yForceDucking) {
                if (this.mAttr.getBundle() == null) {
                    bundle = new Bundle();
                } else {
                    bundle = this.mAttr.getBundle();
                }
                bundle.putBoolean(AudioFocusRequest.KEY_ACCESSIBILITY_FORCE_FOCUS_DUCKING, true);
                this.mAttr = new AudioAttributes.Builder(this.mAttr).addBundle(bundle).build();
            }
            return new AudioFocusRequest(this.mFocusListener, this.mListenerHandler, this.mAttr, this.mFocusGain, (this.mDelayedFocus ? 1 : 0) | (this.mPausesOnDuck ? 2 : 0) | (this.mFocusLocked ? 4 : 0));
        }
    }
}
