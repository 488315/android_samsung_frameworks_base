package com.android.server.speech;

import android.os.IBinder;
import android.speech.IRecognitionService;

import java.util.function.Function;

public final /* synthetic */ class RemoteSpeechRecognitionService$$ExternalSyntheticLambda8
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IRecognitionService.Stub.asInterface((IBinder) obj);
    }
}
