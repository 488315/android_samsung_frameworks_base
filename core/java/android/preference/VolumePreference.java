package android.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import com.android.internal.C4337R;

@Deprecated
/* loaded from: classes3.dex */
public class VolumePreference extends SeekBarDialogPreference
    implements PreferenceManager.OnActivityStopListener,
        View.OnKeyListener,
        SeekBarVolumizer.Callback {
  private SeekBarVolumizer mSeekBarVolumizer;
  private int mStreamType;

  public static class VolumeStore {
    public int volume = -1;
    public int originalVolume = -1;
  }

  public VolumePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    TypedArray a =
        context.obtainStyledAttributes(
            attrs, C4337R.styleable.VolumePreference, defStyleAttr, defStyleRes);
    this.mStreamType = a.getInt(0, 0);
    a.recycle();
  }

  public VolumePreference(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public VolumePreference(Context context, AttributeSet attrs) {
    this(context, attrs, C4337R.attr.seekBarDialogPreferenceStyle);
  }

  public VolumePreference(Context context) {
    this(context, null);
  }

  public void setStreamType(int streamType) {
    this.mStreamType = streamType;
  }

  @Override // android.preference.SeekBarDialogPreference, android.preference.DialogPreference
  protected void onBindDialogView(View view) {
    super.onBindDialogView(view);
    SeekBar seekBar = (SeekBar) view.findViewById(C4337R.id.seekbar);
    SeekBarVolumizer seekBarVolumizer =
        new SeekBarVolumizer(getContext(), this.mStreamType, null, this);
    this.mSeekBarVolumizer = seekBarVolumizer;
    seekBarVolumizer.start();
    this.mSeekBarVolumizer.setSeekBar(seekBar);
    getPreferenceManager().registerOnActivityStopListener(this);
    view.setOnKeyListener(this);
    view.setFocusableInTouchMode(true);
    view.requestFocus();
  }

  @Override // android.preference.Preference
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if (this.mSeekBarVolumizer == null) {
      return true;
    }
    boolean isdown = event.getAction() == 0;
    switch (keyCode) {
      case 24:
        if (isdown) {
          this.mSeekBarVolumizer.changeVolumeBy(1);
        }
        return true;
      case 25:
        if (isdown) {
          this.mSeekBarVolumizer.changeVolumeBy(-1);
        }
        return true;
      case 164:
        if (isdown) {
          this.mSeekBarVolumizer.muteVolume();
        }
        return true;
      default:
        return false;
    }
  }

  @Override // android.preference.DialogPreference
  protected void onDialogClosed(boolean positiveResult) {
    SeekBarVolumizer seekBarVolumizer;
    super.onDialogClosed(positiveResult);
    if (!positiveResult && (seekBarVolumizer = this.mSeekBarVolumizer) != null) {
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
        View view = dialog.getWindow().getDecorView().findViewById(C4337R.id.seekbar);
        if (view != null) {
          view.setOnKeyListener(null);
        }
        this.mSeekBarVolumizer.revertVolume();
      }
      this.mSeekBarVolumizer.stop();
      this.mSeekBarVolumizer = null;
    }
  }

  @Override // android.preference.SeekBarVolumizer.Callback
  public void onSampleStarting(SeekBarVolumizer volumizer) {
    SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
    if (seekBarVolumizer != null && volumizer != seekBarVolumizer) {
      seekBarVolumizer.stopSample();
    }
  }

  @Override // android.preference.SeekBarVolumizer.Callback
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {}

  @Override // android.preference.SeekBarVolumizer.Callback
  public void onMuted(boolean muted, boolean zenMuted) {}

  @Override // android.preference.SeekBarVolumizer.Callback
  public void onStartTrackingTouch(SeekBarVolumizer sbv) {}

  @Override // android.preference.DialogPreference, android.preference.Preference
  protected Parcelable onSaveInstanceState() {
    Parcelable superState = super.onSaveInstanceState();
    if (isPersistent()) {
      return superState;
    }
    SavedState myState = new SavedState(superState);
    SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
    if (seekBarVolumizer != null) {
      seekBarVolumizer.onSaveInstanceState(myState.getVolumeStore());
    }
    return myState;
  }

  @Override // android.preference.DialogPreference, android.preference.Preference
  protected void onRestoreInstanceState(Parcelable state) {
    if (state == null || !state.getClass().equals(SavedState.class)) {
      super.onRestoreInstanceState(state);
      return;
    }
    SavedState myState = (SavedState) state;
    super.onRestoreInstanceState(myState.getSuperState());
    SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
    if (seekBarVolumizer != null) {
      seekBarVolumizer.onRestoreInstanceState(myState.getVolumeStore());
    }
  }

  private static class SavedState extends Preference.BaseSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR =
        new Parcelable.Creator<
            SavedState>() { // from class: android.preference.VolumePreference.SavedState.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SavedState[] newArray(int size) {
            return new SavedState[size];
          }
        };
    VolumeStore mVolumeStore;

    public SavedState(Parcel source) {
      super(source);
      VolumeStore volumeStore = new VolumeStore();
      this.mVolumeStore = volumeStore;
      volumeStore.volume = source.readInt();
      this.mVolumeStore.originalVolume = source.readInt();
    }

    @Override // android.view.AbsSavedState, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      super.writeToParcel(dest, flags);
      dest.writeInt(this.mVolumeStore.volume);
      dest.writeInt(this.mVolumeStore.originalVolume);
    }

    VolumeStore getVolumeStore() {
      return this.mVolumeStore;
    }

    public SavedState(Parcelable superState) {
      super(superState);
      this.mVolumeStore = new VolumeStore();
    }
  }
}
