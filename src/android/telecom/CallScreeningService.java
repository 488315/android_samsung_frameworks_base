package android.telecom;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.Call;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.ICallScreeningAdapter;
import com.android.internal.telecom.ICallScreeningService;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public abstract class CallScreeningService extends Service {
    private static final int MSG_SCREEN_CALL = 1;
    public static final String SERVICE_INTERFACE = "android.telecom.CallScreeningService";
    private ICallScreeningAdapter mCallScreeningAdapter;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: android.telecom.CallScreeningService.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            SomeArgs someArgs = (SomeArgs) message.obj;
            try {
                CallScreeningService.this.mCallScreeningAdapter = (ICallScreeningAdapter) someArgs.arg1;
                Call.Details detailsCreateFromParcelableCall = Call.Details.createFromParcelableCall((ParcelableCall) someArgs.arg2);
                CallScreeningService.this.onScreenCall(detailsCreateFromParcelableCall);
                if (detailsCreateFromParcelableCall.getCallDirection() == 1) {
                    CallScreeningService.this.mCallScreeningAdapter.onScreeningResponse(detailsCreateFromParcelableCall.getTelecomCallId(), new ComponentName(CallScreeningService.this.getPackageName(), getClass().getName()), null);
                }
            } catch (RemoteException e) {
                Log.w(this, "Exception when screening call: " + e, new Object[0]);
            } finally {
                someArgs.recycle();
            }
        }
    };

    public abstract void onScreenCall(Call.Details details);

    private final class CallScreeningBinder extends ICallScreeningService.Stub {
        private CallScreeningBinder() {
        }

        @Override // com.android.internal.telecom.ICallScreeningService
        public void screenCall(ICallScreeningAdapter iCallScreeningAdapter, ParcelableCall parcelableCall) {
            Log.v(this, "screenCall", new Object[0]);
            SomeArgs someArgsObtain = SomeArgs.obtain();
            someArgsObtain.arg1 = iCallScreeningAdapter;
            someArgsObtain.arg2 = parcelableCall;
            CallScreeningService.this.mHandler.obtainMessage(1, someArgsObtain).sendToTarget();
        }
    }

    public static class ParcelableCallResponse implements Parcelable {
        public static final Parcelable.Creator<ParcelableCallResponse> CREATOR = new Parcelable.Creator<ParcelableCallResponse>() { // from class: android.telecom.CallScreeningService.ParcelableCallResponse.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ParcelableCallResponse createFromParcel(Parcel parcel) {
                return new ParcelableCallResponse(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ParcelableCallResponse[] newArray(int i) {
                return new ParcelableCallResponse[i];
            }
        };
        private final int mCallComposerAttachmentsToShow;
        private final boolean mShouldDisallowCall;
        private final boolean mShouldRejectCall;
        private final boolean mShouldScreenCallViaAudioProcessing;
        private final boolean mShouldSilenceCall;
        private final boolean mShouldSkipCallLog;
        private final boolean mShouldSkipNotification;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private ParcelableCallResponse(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i) {
            this.mShouldDisallowCall = z;
            this.mShouldRejectCall = z2;
            this.mShouldSilenceCall = z3;
            this.mShouldSkipCallLog = z4;
            this.mShouldSkipNotification = z5;
            this.mShouldScreenCallViaAudioProcessing = z6;
            this.mCallComposerAttachmentsToShow = i;
        }

        protected ParcelableCallResponse(Parcel parcel) {
            this.mShouldDisallowCall = parcel.readBoolean();
            this.mShouldRejectCall = parcel.readBoolean();
            this.mShouldSilenceCall = parcel.readBoolean();
            this.mShouldSkipCallLog = parcel.readBoolean();
            this.mShouldSkipNotification = parcel.readBoolean();
            this.mShouldScreenCallViaAudioProcessing = parcel.readBoolean();
            this.mCallComposerAttachmentsToShow = parcel.readInt();
        }

        public CallResponse toCallResponse() {
            return new CallResponse.Builder().setDisallowCall(this.mShouldDisallowCall).setRejectCall(this.mShouldRejectCall).setSilenceCall(this.mShouldSilenceCall).setSkipCallLog(this.mShouldSkipCallLog).setSkipNotification(this.mShouldSkipNotification).setShouldScreenCallViaAudioProcessing(this.mShouldScreenCallViaAudioProcessing).setCallComposerAttachmentsToShow(this.mCallComposerAttachmentsToShow).build();
        }

        public boolean shouldDisallowCall() {
            return this.mShouldDisallowCall;
        }

        public boolean shouldRejectCall() {
            return this.mShouldRejectCall;
        }

        public boolean shouldSilenceCall() {
            return this.mShouldSilenceCall;
        }

        public boolean shouldSkipCallLog() {
            return this.mShouldSkipCallLog;
        }

        public boolean shouldSkipNotification() {
            return this.mShouldSkipNotification;
        }

        public boolean shouldScreenCallViaAudioProcessing() {
            return this.mShouldScreenCallViaAudioProcessing;
        }

        public int getCallComposerAttachmentsToShow() {
            return this.mCallComposerAttachmentsToShow;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBoolean(this.mShouldDisallowCall);
            parcel.writeBoolean(this.mShouldRejectCall);
            parcel.writeBoolean(this.mShouldSilenceCall);
            parcel.writeBoolean(this.mShouldSkipCallLog);
            parcel.writeBoolean(this.mShouldSkipNotification);
            parcel.writeBoolean(this.mShouldScreenCallViaAudioProcessing);
            parcel.writeInt(this.mCallComposerAttachmentsToShow);
        }
    }

    public static class CallResponse {
        public static final int CALL_COMPOSER_ATTACHMENT_LOCATION = 2;
        public static final int CALL_COMPOSER_ATTACHMENT_PICTURE = 1;
        public static final int CALL_COMPOSER_ATTACHMENT_PRIORITY = 8;
        public static final int CALL_COMPOSER_ATTACHMENT_SUBJECT = 4;
        private static final int NUM_CALL_COMPOSER_ATTACHMENT_TYPES = 4;
        private final int mCallComposerAttachmentsToShow;
        private final boolean mShouldDisallowCall;
        private final boolean mShouldRejectCall;
        private final boolean mShouldScreenCallViaAudioProcessing;
        private final boolean mShouldSilenceCall;
        private final boolean mShouldSkipCallLog;
        private final boolean mShouldSkipNotification;

        @Retention(RetentionPolicy.SOURCE)
        public @interface CallComposerAttachmentType {
        }

        private CallResponse(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i) {
            if (!z && (z2 || z4 || z5)) {
                throw new IllegalStateException("Invalid response state for allowed call.");
            }
            if (z && z6) {
                throw new IllegalStateException("Invalid response state for allowed call.");
            }
            this.mShouldDisallowCall = z;
            this.mShouldRejectCall = z2;
            this.mShouldSkipCallLog = z4;
            this.mShouldSkipNotification = z5;
            this.mShouldSilenceCall = z3;
            this.mShouldScreenCallViaAudioProcessing = z6;
            this.mCallComposerAttachmentsToShow = i;
        }

        public boolean getDisallowCall() {
            return this.mShouldDisallowCall;
        }

        public boolean getRejectCall() {
            return this.mShouldRejectCall;
        }

        public boolean getSilenceCall() {
            return this.mShouldSilenceCall;
        }

        public boolean getSkipCallLog() {
            return this.mShouldSkipCallLog;
        }

        public boolean getSkipNotification() {
            return this.mShouldSkipNotification;
        }

        public boolean getShouldScreenCallViaAudioProcessing() {
            return this.mShouldScreenCallViaAudioProcessing;
        }

        public int getCallComposerAttachmentsToShow() {
            return this.mCallComposerAttachmentsToShow;
        }

        public ParcelableCallResponse toParcelable() {
            return new ParcelableCallResponse(this.mShouldDisallowCall, this.mShouldRejectCall, this.mShouldSilenceCall, this.mShouldSkipCallLog, this.mShouldSkipNotification, this.mShouldScreenCallViaAudioProcessing, this.mCallComposerAttachmentsToShow);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                CallResponse callResponse = (CallResponse) obj;
                if (this.mShouldDisallowCall == callResponse.mShouldDisallowCall && this.mShouldRejectCall == callResponse.mShouldRejectCall && this.mShouldSilenceCall == callResponse.mShouldSilenceCall && this.mShouldSkipCallLog == callResponse.mShouldSkipCallLog && this.mShouldSkipNotification == callResponse.mShouldSkipNotification && this.mShouldScreenCallViaAudioProcessing == callResponse.mShouldScreenCallViaAudioProcessing && this.mCallComposerAttachmentsToShow == callResponse.mCallComposerAttachmentsToShow) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Boolean.valueOf(this.mShouldDisallowCall), Boolean.valueOf(this.mShouldRejectCall), Boolean.valueOf(this.mShouldSilenceCall), Boolean.valueOf(this.mShouldSkipCallLog), Boolean.valueOf(this.mShouldSkipNotification), Boolean.valueOf(this.mShouldScreenCallViaAudioProcessing), Integer.valueOf(this.mCallComposerAttachmentsToShow));
        }

        public static class Builder {
            private int mCallComposerAttachmentsToShow = -1;
            private boolean mShouldDisallowCall;
            private boolean mShouldRejectCall;
            private boolean mShouldScreenCallViaAudioProcessing;
            private boolean mShouldSilenceCall;
            private boolean mShouldSkipCallLog;
            private boolean mShouldSkipNotification;

            public Builder setDisallowCall(boolean z) {
                this.mShouldDisallowCall = z;
                return this;
            }

            public Builder setRejectCall(boolean z) {
                this.mShouldRejectCall = z;
                return this;
            }

            public Builder setSilenceCall(boolean z) {
                this.mShouldSilenceCall = z;
                return this;
            }

            public Builder setSkipCallLog(boolean z) {
                this.mShouldSkipCallLog = z;
                return this;
            }

            public Builder setSkipNotification(boolean z) {
                this.mShouldSkipNotification = z;
                return this;
            }

            @SystemApi
            public Builder setShouldScreenCallViaAudioProcessing(boolean z) {
                this.mShouldScreenCallViaAudioProcessing = z;
                return this;
            }

            public Builder setCallComposerAttachmentsToShow(int i) {
                if (i < 0) {
                    return this;
                }
                if ((i & 16) != 0) {
                    throw new IllegalArgumentException("Attachment types must match the ones defined in CallResponse");
                }
                this.mCallComposerAttachmentsToShow = i;
                return this;
            }

            public CallResponse build() {
                return new CallResponse(this.mShouldDisallowCall, this.mShouldRejectCall, this.mShouldSilenceCall, this.mShouldSkipCallLog, this.mShouldSkipNotification, this.mShouldScreenCallViaAudioProcessing, this.mCallComposerAttachmentsToShow);
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.v(this, "onBind", new Object[0]);
        return new CallScreeningBinder();
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.v(this, "onUnbind", new Object[0]);
        return false;
    }

    public final void respondToCall(Call.Details details, CallResponse callResponse) {
        try {
            this.mCallScreeningAdapter.onScreeningResponse(details.getTelecomCallId(), new ComponentName(getPackageName(), getClass().getName()), callResponse.toParcelable());
        } catch (RemoteException e) {
            Log.e(this, e, "Got remote exception when returning response", new Object[0]);
        }
    }
}
