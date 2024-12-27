package com.android.server.speech;

import android.content.AttributionSource;
import android.content.Intent;
import android.speech.IModelDownloadListener;
import android.speech.IRecognitionService;
import android.speech.IRecognitionSupportCallback;

import com.android.internal.infra.ServiceConnector;

public final /* synthetic */ class RemoteSpeechRecognitionService$$ExternalSyntheticLambda0
        implements ServiceConnector.VoidJob {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ Intent f$0;
    public final /* synthetic */ AttributionSource f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ RemoteSpeechRecognitionService$$ExternalSyntheticLambda0(
            Intent intent,
            AttributionSource attributionSource,
            IModelDownloadListener iModelDownloadListener) {
        this.f$0 = intent;
        this.f$1 = attributionSource;
        this.f$2 = iModelDownloadListener;
    }

    public /* synthetic */ RemoteSpeechRecognitionService$$ExternalSyntheticLambda0(
            Intent intent,
            AttributionSource attributionSource,
            IRecognitionSupportCallback iRecognitionSupportCallback) {
        this.f$0 = intent;
        this.f$1 = attributionSource;
        this.f$2 = iRecognitionSupportCallback;
    }

    public /* synthetic */ RemoteSpeechRecognitionService$$ExternalSyntheticLambda0(
            Intent intent,
            RemoteSpeechRecognitionService.DelegatingListener delegatingListener,
            AttributionSource attributionSource) {
        this.f$0 = intent;
        this.f$2 = delegatingListener;
        this.f$1 = attributionSource;
    }

    public final void runNoResult(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Intent intent = this.f$0;
                AttributionSource attributionSource = this.f$1;
                IRecognitionSupportCallback iRecognitionSupportCallback =
                        (IRecognitionSupportCallback) this.f$2;
                int i = RemoteSpeechRecognitionService.$r8$clinit;
                ((IRecognitionService) obj)
                        .checkRecognitionSupport(
                                intent, attributionSource, iRecognitionSupportCallback);
                break;
            case 1:
                Intent intent2 = this.f$0;
                RemoteSpeechRecognitionService.DelegatingListener delegatingListener =
                        (RemoteSpeechRecognitionService.DelegatingListener) this.f$2;
                AttributionSource attributionSource2 = this.f$1;
                int i2 = RemoteSpeechRecognitionService.$r8$clinit;
                ((IRecognitionService) obj)
                        .startListening(intent2, delegatingListener, attributionSource2);
                break;
            default:
                Intent intent3 = this.f$0;
                AttributionSource attributionSource3 = this.f$1;
                IModelDownloadListener iModelDownloadListener = (IModelDownloadListener) this.f$2;
                int i3 = RemoteSpeechRecognitionService.$r8$clinit;
                ((IRecognitionService) obj)
                        .triggerModelDownload(intent3, attributionSource3, iModelDownloadListener);
                break;
        }
    }
}
