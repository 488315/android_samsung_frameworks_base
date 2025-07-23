package com.android.settingslib.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.util.Slog;
import com.android.systemui.media.dialog.MediaSwitchingController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class InputRouteManager {
    static final AudioAttributes INPUT_ATTRIBUTES = new AudioAttributes.Builder().setCapturePreset(1).build();
    static final int[] PRESETS = {1, 5, 6, 7, 9, 10};
    final AudioDeviceCallback mAudioDeviceCallback;
    public final AudioManager mAudioManager;
    public final Context mContext;
    public int mSelectedInputDeviceType;
    final List<MediaDevice> mInputMediaDevices = new CopyOnWriteArrayList();
    public final Collection mCallbacks = new CopyOnWriteArrayList();
    public final Object mCallbackLock = new Object();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface InputDeviceCallback {
    }

    public InputRouteManager(Context context, AudioManager audioManager) {
        AudioDeviceCallback audioDeviceCallback = new AudioDeviceCallback() { // from class: com.android.settingslib.media.InputRouteManager.1
            @Override // android.media.AudioDeviceCallback
            public final void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
                MediaDevice mediaDevice;
                InputRouteManager inputRouteManager = InputRouteManager.this;
                AudioAttributes audioAttributes = InputRouteManager.INPUT_ATTRIBUTES;
                inputRouteManager.applyDefaultSelectedTypeToAllPresets();
                int i = InputRouteManager.this.mSelectedInputDeviceType;
                for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                    int type = audioDeviceInfo.getType();
                    if (InputMediaDevice.isSupportedInputDevice(type)) {
                        Iterator<MediaDevice> it = InputRouteManager.this.mInputMediaDevices.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                mediaDevice = it.next();
                                if (((InputMediaDevice) mediaDevice).mAudioDeviceInfoType == type) {
                                    break;
                                }
                            } else {
                                mediaDevice = null;
                                break;
                            }
                        }
                        if (mediaDevice == null) {
                            i = type;
                        }
                    }
                }
                InputRouteManager inputRouteManager2 = InputRouteManager.this;
                if (inputRouteManager2.mSelectedInputDeviceType != i) {
                    inputRouteManager2.mSelectedInputDeviceType = i;
                    InputRouteManager.this.setPreferredDeviceForAllPresets(new AudioDeviceAttributes(1, i, ""));
                }
            }

            @Override // android.media.AudioDeviceCallback
            public final void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
                InputRouteManager inputRouteManager = InputRouteManager.this;
                AudioAttributes audioAttributes = InputRouteManager.INPUT_ATTRIBUTES;
                inputRouteManager.applyDefaultSelectedTypeToAllPresets();
            }
        };
        this.mAudioDeviceCallback = audioDeviceCallback;
        this.mContext = context;
        this.mAudioManager = audioManager;
        Handler handler = new Handler(context.getMainLooper());
        audioManager.registerAudioDeviceCallback(audioDeviceCallback, handler);
        audioManager.addOnPreferredDevicesForCapturePresetChangedListener(new HandlerExecutor(handler), new AudioManager.OnPreferredDevicesForCapturePresetChangedListener() { // from class: com.android.settingslib.media.InputRouteManager$$ExternalSyntheticLambda0
            public final void onPreferredDevicesForCapturePresetChanged(int i, List list) {
                InputRouteManager.this.onPreferredDevicesForCapturePresetChangedListener(i, list);
            }
        });
        applyDefaultSelectedTypeToAllPresets();
    }

    public final void applyDefaultSelectedTypeToAllPresets() {
        int type;
        List devicesForAttributes = this.mAudioManager.getDevicesForAttributes(INPUT_ATTRIBUTES);
        if (devicesForAttributes.isEmpty()) {
            Slog.e("InputRouteManager", "Unexpected empty list of input devices. Using built-in mic.");
            type = 15;
        } else {
            if (devicesForAttributes.size() > 1) {
                Slog.w("InputRouteManager", "AudioManager.getDevicesForAttributes returned more than one element. Using the first one.");
            }
            type = ((AudioDeviceAttributes) devicesForAttributes.get(0)).getType();
        }
        this.mSelectedInputDeviceType = type;
        setPreferredDeviceForAllPresets(new AudioDeviceAttributes(1, type, ""));
    }

    public void onPreferredDevicesForCapturePresetChangedListener(int i, List<AudioDeviceAttributes> list) {
        if (i == 1) {
            AudioDeviceInfo[] devices = this.mAudioManager.getDevices(1);
            this.mInputMediaDevices.clear();
            for (AudioDeviceInfo audioDeviceInfo : devices) {
                Context context = this.mContext;
                String valueOf = String.valueOf(audioDeviceInfo.getId());
                int type = audioDeviceInfo.getType();
                CharSequence productName = audioDeviceInfo.getProductName();
                String str = null;
                if (productName != null) {
                    String charSequence = productName.toString();
                    if (!charSequence.isBlank()) {
                        str = charSequence;
                    }
                }
                InputMediaDevice create = InputMediaDevice.create(context, type, valueOf, str);
                if (create != null) {
                    if (audioDeviceInfo.getType() == this.mSelectedInputDeviceType) {
                        create.mState = 4;
                    }
                    this.mInputMediaDevices.add(create);
                }
            }
            ArrayList arrayList = new ArrayList(this.mInputMediaDevices);
            synchronized (this.mCallbackLock) {
                try {
                    Iterator it = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
                    while (it.hasNext()) {
                        ((MediaSwitchingController.AnonymousClass1) ((InputDeviceCallback) it.next())).onInputDeviceListUpdated(arrayList);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void setPreferredDeviceForAllPresets(AudioDeviceAttributes audioDeviceAttributes) {
        for (int i : PRESETS) {
            this.mAudioManager.setPreferredDeviceForCapturePreset(i, audioDeviceAttributes);
        }
    }
}
