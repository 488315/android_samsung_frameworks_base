package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger_middleware.ISoundTriggerCallback;
import android.media.soundtrigger_middleware.ISoundTriggerModule;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;

/* loaded from: classes3.dex */
public interface ISoundTriggerMiddlewareInternal {
  ISoundTriggerModule attach(int i, ISoundTriggerCallback iSoundTriggerCallback, boolean z);

  SoundTriggerModuleDescriptor[] listModules();
}
