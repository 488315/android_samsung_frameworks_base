package android.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.SeekBarVolumizer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import com.android.internal.R;

@Deprecated
/* loaded from: classes3.dex */
public class VolumePreference extends SeekBarDialogPreference implements PreferenceManager.OnActivityStopListener, View.OnKeyListener, SeekBarVolumizer.Callback {
    private SeekBarVolumizer mSeekBarVolumizer;
    private int mStreamType;

    public static class VolumeStore {
        public int volume = -1;
        public int originalVolume = -1;
    }

    @Override // android.preference.SeekBarVolumizer.Callback
    public void onMuted(boolean z, boolean z2) {
    }

    @Override // android.preference.SeekBarVolumizer.Callback
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
    }

    @Override // android.preference.SeekBarVolumizer.Callback
    public void onStartTrackingTouch(SeekBarVolumizer seekBarVolumizer) {
    }

    public VolumePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.VolumePreference, i, i2);
        this.mStreamType = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
    }

    public VolumePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public VolumePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarDialogPreferenceStyle);
    }

    public VolumePreference(Context context) {
        this(context, null);
    }

    public void setStreamType(int i) {
        this.mStreamType = i;
    }

    @Override // android.preference.SeekBarDialogPreference, android.preference.DialogPreference
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        SeekBarVolumizer seekBarVolumizer = new SeekBarVolumizer(getContext(), this.mStreamType, null, this);
        this.mSeekBarVolumizer = seekBarVolumizer;
        seekBarVolumizer.start();
        this.mSeekBarVolumizer.setSeekBar(seekBar);
        getPreferenceManager().registerOnActivityStopListener(this);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override // android.preference.Preference
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (this.mSeekBarVolumizer == null) {
            return true;
        }
        boolean z = keyEvent.getAction() == 0;
        if (i == 24) {
            if (z) {
                this.mSeekBarVolumizer.changeVolumeBy(1);
            }
            return true;
        }
        if (i == 25) {
            if (z) {
                this.mSeekBarVolumizer.changeVolumeBy(-1);
            }
            return true;
        }
        if (i != 164) {
            return false;
        }
        if (z) {
            this.mSeekBarVolumizer.muteVolume();
        }
        return true;
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        SeekBarVolumizer seekBarVolumizer;
        super.onDialogClosed(z);
        if (!z && (seekBarVolumizer = this.mSeekBarVolumizer) != null) {
            seekBarVolumizer.revertVolume();
        }
        cleanup();
    }

    @Override // android.preference.PreferenceManager.OnActivityStopListener
    public void onActivityStop() {
        SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
        if (seekBarVolumizer != null) {
            seekBarVolumizer.stopSample();
        }
    }

    private void cleanup() {
        getPreferenceManager().unregisterOnActivityStopListener(this);
        if (this.mSeekBarVolumizer != null) {
            Dialog dialog = getDialog();
            if (dialog != null && dialog.isShowing()) {
                View findViewById = dialog.getWindow().getDecorView().findViewById(R.id.seekbar);
                if (findViewById != null) {
                    findViewById.setOnKeyListener(null);
                }
                this.mSeekBarVolumizer.revertVolume();
            }
            this.mSeekBarVolumizer.stop();
            this.mSeekBarVolumizer = null;
        }
    }

    @Override // android.preference.SeekBarVolumizer.Callback
    public void onSampleStarting(SeekBarVolumizer seekBarVolumizer) {
        SeekBarVolumizer seekBarVolumizer2 = this.mSeekBarVolumizer;
        if (seekBarVolumizer2 == null || seekBarVolumizer == seekBarVolumizer2) {
            return;
        }
        seekBarVolumizer2.stopSample();
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
        if (seekBarVolumizer != null) {
            seekBarVolumizer.onSaveInstanceState(savedState.getVolumeStore());
        }
        return savedState;
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
        if (seekBarVolumizer != null) {
            seekBarVolumizer.onRestoreInstanceState(savedState.getVolumeStore());
        }
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.preference.VolumePreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        VolumeStore mVolumeStore;

        public SavedState(Parcel parcel) {
            super(parcel);
            VolumeStore volumeStore = new VolumeStore();
            this.mVolumeStore = volumeStore;
            volumeStore.volume = parcel.readInt();
            this.mVolumeStore.originalVolume = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mVolumeStore.volume);
            parcel.writeInt(this.mVolumeStore.originalVolume);
        }

        VolumeStore getVolumeStore() {
            return this.mVolumeStore;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
            this.mVolumeStore = new VolumeStore();
        }
    }
}
