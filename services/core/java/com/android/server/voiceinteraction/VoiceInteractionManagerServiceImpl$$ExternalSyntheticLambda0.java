package com.android.server.voiceinteraction;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.util.function.HexConsumer;
import com.android.server.LocalServices;
import com.android.server.wm.ActivityTaskManagerInternal;

public final /* synthetic */ class VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda0
        implements HexConsumer {
    public final void accept(
            Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl =
                (VoiceInteractionManagerServiceImpl) obj;
        IBinder iBinder = (IBinder) obj2;
        int intValue = ((Integer) obj3).intValue();
        IBinder iBinder2 = (IBinder) obj4;
        RemoteCallback remoteCallback = (RemoteCallback) obj5;
        RemoteCallback remoteCallback2 = (RemoteCallback) obj6;
        synchronized (voiceInteractionManagerServiceImpl.mServiceStub) {
            VoiceInteractionSessionConnection voiceInteractionSessionConnection =
                    voiceInteractionManagerServiceImpl.mActiveSession;
            if (voiceInteractionSessionConnection != null
                    && iBinder == voiceInteractionSessionConnection.mToken) {
                ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask =
                        ((ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class))
                                .getAttachedNonFinishingActivityForTask(intValue, null);
                if (attachedNonFinishingActivityForTask == null
                        || attachedNonFinishingActivityForTask.mAssistToken != iBinder2) {
                    Slog.w(
                            "VoiceInteractionServiceManager",
                            "Unknown activity to query for direct actions during retrying");
                    remoteCallback2.sendResult((Bundle) null);
                } else {
                    try {
                        attachedNonFinishingActivityForTask.mAppThread.requestDirectActions(
                                attachedNonFinishingActivityForTask.mActivityToken,
                                voiceInteractionManagerServiceImpl.mActiveSession.mInteractor,
                                remoteCallback,
                                remoteCallback2);
                    } catch (RemoteException e) {
                        Slog.w("Unexpected remote error", e);
                        remoteCallback2.sendResult((Bundle) null);
                    }
                }
                return;
            }
            Slog.w(
                    "VoiceInteractionServiceManager",
                    "retryRequestDirectActions does not match active session");
            remoteCallback2.sendResult((Bundle) null);
        }
    }
}
