package android.telecom;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes4.dex */
public final class StreamingCall implements Parcelable {
    public static final Parcelable.Creator<StreamingCall> CREATOR = new Parcelable.Creator<StreamingCall>() { // from class: android.telecom.StreamingCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamingCall createFromParcel(Parcel parcel) {
            return new StreamingCall(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamingCall[] newArray(int i) {
            return new StreamingCall[i];
        }
    };
    public static final String EXTRA_CALL_ID = "android.telecom.extra.CALL_ID";
    public static final int STATE_DISCONNECTED = 3;
    public static final int STATE_HOLDING = 2;
    public static final int STATE_STREAMING = 1;
    private StreamingCallAdapter mAdapter;
    private final Uri mAddress;
    private final ComponentName mComponentName;
    private final CharSequence mDisplayName;
    private final Bundle mExtras;
    private int mState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StreamingCallState {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private StreamingCall(Parcel parcel) {
        this.mAdapter = null;
        this.mComponentName = (ComponentName) parcel.readParcelable(ComponentName.class.getClassLoader());
        this.mDisplayName = parcel.readCharSequence();
        this.mAddress = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.mExtras = parcel.readBundle();
        this.mState = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mComponentName, i);
        parcel.writeCharSequence(this.mDisplayName);
        parcel.writeParcelable(this.mAddress, i);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mState);
    }

    public StreamingCall(ComponentName componentName, CharSequence charSequence, Uri uri, Bundle bundle) {
        this.mAdapter = null;
        this.mComponentName = componentName;
        this.mDisplayName = charSequence;
        this.mAddress = uri;
        this.mExtras = bundle;
        this.mState = 1;
    }

    public void setAdapter(StreamingCallAdapter streamingCallAdapter) {
        this.mAdapter = streamingCallAdapter;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public Uri getAddress() {
        return this.mAddress;
    }

    public int getState() {
        return this.mState;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public void requestStreamingState(int i) {
        this.mAdapter.setStreamingState(i);
    }
}
