package com.samsung.android.desktopsystemui.sharedlib.system;

import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Choreographer;
import android.view.InputMonitor;
import com.samsung.android.desktopsystemui.sharedlib.system.InputChannelCompat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class InputMonitorCompat implements Parcelable {
    public static final Parcelable.Creator<InputMonitorCompat> CREATOR = new Parcelable.Creator<InputMonitorCompat>() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.InputMonitorCompat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMonitorCompat createFromParcel(Parcel parcel) {
            return new InputMonitorCompat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputMonitorCompat[] newArray(int i) {
            return new InputMonitorCompat[i];
        }
    };
    private boolean mForReturn;
    private final InputMonitor mInputMonitor;

    public static InputMonitorCompat fromBundle(Bundle bundle, String str) {
        bundle.setClassLoader(InputMonitorCompat.class.getClassLoader());
        return (InputMonitorCompat) bundle.getParcelable(str);
    }

    public static InputMonitorCompat obtainReturnValue(InputMonitor inputMonitor) {
        InputMonitorCompat inputMonitorCompat = new InputMonitorCompat(inputMonitor);
        inputMonitorCompat.mForReturn = true;
        return inputMonitorCompat;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void dispose() {
        this.mInputMonitor.dispose();
    }

    public InputChannelCompat.InputEventReceiver getInputReceiver(Looper looper, Choreographer choreographer, InputChannelCompat.InputEventListener inputEventListener) {
        return new InputChannelCompat.InputEventReceiver(this.mInputMonitor.getInputChannel(), looper, choreographer, inputEventListener);
    }

    public void pilferPointers() {
        this.mInputMonitor.pilferPointers();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        InputMonitor inputMonitor = this.mInputMonitor;
        if (this.mForReturn) {
            i = 1;
        }
        inputMonitor.writeToParcel(parcel, i);
    }

    public InputMonitorCompat(String str, int i) {
        this.mForReturn = false;
        this.mInputMonitor = InputManager.getInstance().monitorGestureInput(str, i);
    }

    private InputMonitorCompat(InputMonitor inputMonitor) {
        this.mForReturn = false;
        this.mInputMonitor = inputMonitor;
    }

    private InputMonitorCompat(Parcel parcel) {
        this.mForReturn = false;
        this.mInputMonitor = (InputMonitor) InputMonitor.CREATOR.createFromParcel(parcel);
    }
}
