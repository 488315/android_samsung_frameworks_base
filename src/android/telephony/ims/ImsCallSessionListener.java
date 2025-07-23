package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ServiceState;
import android.telephony.ims.aidl.IImsCallSessionListener;
import android.telephony.ims.stub.ImsCallSessionImplBase;
import android.util.Log;
import com.android.ims.internal.IImsCallSession;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes4.dex */
public class ImsCallSessionListener {
    private static final String TAG = "ImsCallSessionListener";
    private Executor mExecutor = null;
    private final IImsCallSessionListener mListener;

    public ImsCallSessionListener(IImsCallSessionListener iImsCallSessionListener) {
        this.mListener = iImsCallSessionListener;
    }

    public void callSessionInitiating(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionInitiating(imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionInitiatingFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInitiatingFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionProgressing(ImsStreamMediaProfile imsStreamMediaProfile) {
        try {
            this.mListener.callSessionProgressing(imsStreamMediaProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionInitiated(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionInitiated(imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void callSessionInitiatedFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInitiatedFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionTerminated(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionTerminated(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionHeld(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionHeld(imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionHoldFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionHoldFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionHoldReceived(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionHoldReceived(imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionResumed(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionResumed(imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionResumeFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionResumeFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionResumeReceived(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionResumeReceived(imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionMergeStarted(ImsCallSessionImplBase imsCallSessionImplBase, ImsCallProfile imsCallProfile) {
        if (imsCallSessionImplBase != null) {
            try {
                Executor executor = this.mExecutor;
                if (executor != null) {
                    imsCallSessionImplBase.setDefaultExecutor(executor);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mListener.callSessionMergeStarted(imsCallSessionImplBase != null ? imsCallSessionImplBase.getServiceImpl() : null, imsCallProfile);
    }

    public void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionMergeStarted(iImsCallSession, imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionMergeComplete(ImsCallSessionImplBase imsCallSessionImplBase) {
        if (imsCallSessionImplBase != null) {
            try {
                Executor executor = this.mExecutor;
                if (executor != null) {
                    imsCallSessionImplBase.setDefaultExecutor(executor);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mListener.callSessionMergeComplete(imsCallSessionImplBase != null ? imsCallSessionImplBase.getServiceImpl() : null);
    }

    public void callSessionMergeComplete(IImsCallSession iImsCallSession) {
        try {
            this.mListener.callSessionMergeComplete(iImsCallSession);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionMergeFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionMergeFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionUpdated(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionUpdated(imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionUpdateFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionUpdateFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionUpdateReceived(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionUpdateReceived(imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionConferenceExtended(ImsCallSessionImplBase imsCallSessionImplBase, ImsCallProfile imsCallProfile) {
        if (imsCallSessionImplBase != null) {
            try {
                Executor executor = this.mExecutor;
                if (executor != null) {
                    imsCallSessionImplBase.setDefaultExecutor(executor);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mListener.callSessionConferenceExtended(imsCallSessionImplBase != null ? imsCallSessionImplBase.getServiceImpl() : null, imsCallProfile);
    }

    public void callSessionConferenceExtended(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionConferenceExtended(iImsCallSession, imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionConferenceExtendFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionConferenceExtendFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionConferenceExtendReceived(ImsCallSessionImplBase imsCallSessionImplBase, ImsCallProfile imsCallProfile) {
        if (imsCallSessionImplBase != null) {
            try {
                Executor executor = this.mExecutor;
                if (executor != null) {
                    imsCallSessionImplBase.setDefaultExecutor(executor);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mListener.callSessionConferenceExtendReceived(imsCallSessionImplBase != null ? imsCallSessionImplBase.getServiceImpl() : null, imsCallProfile);
    }

    public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionConferenceExtendReceived(iImsCallSession, imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionInviteParticipantsRequestDelivered() {
        try {
            this.mListener.callSessionInviteParticipantsRequestDelivered();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInviteParticipantsRequestFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRemoveParticipantsRequestDelivered() {
        try {
            this.mListener.callSessionRemoveParticipantsRequestDelivered();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInviteParticipantsRequestFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionConferenceStateUpdated(ImsConferenceState imsConferenceState) {
        try {
            this.mListener.callSessionConferenceStateUpdated(imsConferenceState);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionUssdMessageReceived(int i, String str) {
        try {
            this.mListener.callSessionUssdMessageReceived(i, str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void callSessionMayHandover(int i, int i2) {
        onMayHandover(ServiceState.rilRadioTechnologyToNetworkType(i), ServiceState.rilRadioTechnologyToNetworkType(i2));
    }

    public void onMayHandover(int i, int i2) {
        try {
            this.mListener.callSessionMayHandover(i, i2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void callSessionHandover(int i, int i2, ImsReasonInfo imsReasonInfo) {
        onHandover(ServiceState.rilRadioTechnologyToNetworkType(i), ServiceState.rilRadioTechnologyToNetworkType(i2), imsReasonInfo);
    }

    public void onHandover(int i, int i2, ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionHandover(i, i2, imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void callSessionHandoverFailed(int i, int i2, ImsReasonInfo imsReasonInfo) {
        onHandoverFailed(ServiceState.rilRadioTechnologyToNetworkType(i), ServiceState.rilRadioTechnologyToNetworkType(i2), imsReasonInfo);
    }

    public void onHandoverFailed(int i, int i2, ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionHandoverFailed(i, i2, imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionTtyModeReceived(int i) {
        try {
            this.mListener.callSessionTtyModeReceived(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionMultipartyStateChanged(boolean z) {
        try {
            this.mListener.callSessionMultipartyStateChanged(z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionSuppServiceReceived(ImsSuppServiceNotification imsSuppServiceNotification) {
        try {
            this.mListener.callSessionSuppServiceReceived(imsSuppServiceNotification);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRttModifyRequestReceived(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionRttModifyRequestReceived(imsCallProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRttModifyResponseReceived(int i) {
        try {
            this.mListener.callSessionRttModifyResponseReceived(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRttMessageReceived(String str) {
        try {
            this.mListener.callSessionRttMessageReceived(str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) {
        try {
            this.mListener.callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callQualityChanged(CallQuality callQuality) {
        try {
            this.mListener.callQualityChanged(callQuality);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionDtmfReceived(char c) {
        if ((c < '0' || c > '9') && ((c < 'A' || c > 'D') && ((c < 'a' || c > 'd') && c != '*' && c != '#'))) {
            throw new IllegalArgumentException("DTMF digit must be 0-9, *, #, A, B, C, D");
        }
        try {
            this.mListener.callSessionDtmfReceived(Character.toUpperCase(c));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRtpHeaderExtensionsReceived(Set<RtpHeaderExtension> set) {
        Objects.requireNonNull(set, "extensions are required.");
        try {
            this.mListener.callSessionRtpHeaderExtensionsReceived(new ArrayList(set));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionTransferred() {
        try {
            this.mListener.callSessionTransferred();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionTransferFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionTransferFailed(imsReasonInfo);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void callSessionSendAnbrQuery(int i, int i2, int i3) {
        Log.d(TAG, "callSessionSendAnbrQuery in imscallsessonListener");
        try {
            this.mListener.callSessionSendAnbrQuery(i, i2, i3);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void setDefaultExecutor(Executor executor) {
        if (this.mExecutor == null) {
            this.mExecutor = executor;
        }
    }
}
