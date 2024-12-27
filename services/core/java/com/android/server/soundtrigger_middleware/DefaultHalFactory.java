package com.android.server.soundtrigger_middleware;

import android.hardware.soundtrigger3.ISoundTriggerHw;
import android.os.HwBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DefaultHalFactory implements HalFactory {
    public static final ICaptureStateNotifier mCaptureStateNotifier =
            new ExternalCaptureStateTracker();

    @Override // com.android.server.soundtrigger_middleware.HalFactory
    public final ISoundTriggerHal create() {
        try {
            int i = SystemProperties.getInt("debug.soundtrigger_middleware.use_mock_hal", 0);
            ICaptureStateNotifier iCaptureStateNotifier = mCaptureStateNotifier;
            if (i == 0) {
                String str = ISoundTriggerHw.class.getCanonicalName() + "/default";
                if (ServiceManager.isDeclared(str)) {
                    Slog.i(
                            "SoundTriggerMiddlewareDefaultHalFactory",
                            "Connecting to default soundtrigger3.ISoundTriggerHw");
                    final int i2 = 0;
                    return new SoundTriggerHw3Compat(
                            ServiceManager.waitForService(str),
                            new Runnable() { // from class:
                                             // com.android.server.soundtrigger_middleware.DefaultHalFactory$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i2) {
                                        case 0:
                                            SystemProperties.set("sys.audio.restart.hal", "1");
                                            break;
                                        default:
                                            SystemProperties.set("sys.audio.restart.hal", "1");
                                            break;
                                    }
                                }
                            });
                }
                Slog.i(
                        "SoundTriggerMiddlewareDefaultHalFactory",
                        "Connecting to default soundtrigger-V2.x.ISoundTriggerHw");
                final int i3 = 1;
                return SoundTriggerHw2Compat.create(
                        android.hardware.soundtrigger.V2_0.ISoundTriggerHw.asInterface(
                                HwBinder.getService(
                                        "android.hardware.soundtrigger@2.0::ISoundTriggerHw",
                                        "default",
                                        true)),
                        new Runnable() { // from class:
                                         // com.android.server.soundtrigger_middleware.DefaultHalFactory$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i3) {
                                    case 0:
                                        SystemProperties.set("sys.audio.restart.hal", "1");
                                        break;
                                    default:
                                        SystemProperties.set("sys.audio.restart.hal", "1");
                                        break;
                                }
                            }
                        },
                        iCaptureStateNotifier);
            }
            if (i == 2) {
                Slog.i(
                        "SoundTriggerMiddlewareDefaultHalFactory",
                        "Connecting to mock soundtrigger-V2.x.ISoundTriggerHw");
                HwBinder.setTrebleTestingOverride(true);
                try {
                    final android.hardware.soundtrigger.V2_0.ISoundTriggerHw asInterface =
                            android.hardware.soundtrigger.V2_0.ISoundTriggerHw.asInterface(
                                    HwBinder.getService(
                                            "android.hardware.soundtrigger@2.0::ISoundTriggerHw",
                                            "mock",
                                            true));
                    final int i4 = 0;
                    return SoundTriggerHw2Compat.create(
                            asInterface,
                            new Runnable() { // from class:
                                             // com.android.server.soundtrigger_middleware.DefaultHalFactory$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i5 = i4;
                                    Object obj = asInterface;
                                    switch (i5) {
                                        case 0:
                                            try {
                                                ((android.hardware.soundtrigger.V2_0
                                                                        .ISoundTriggerHw)
                                                                obj)
                                                        .debug(
                                                                null,
                                                                new ArrayList(
                                                                        Arrays.asList("reboot")));
                                                break;
                                            } catch (Exception e) {
                                                Slog.e(
                                                        "SoundTriggerMiddlewareDefaultHalFactory",
                                                        "Failed to reboot mock HAL",
                                                        e);
                                                return;
                                            }
                                        default:
                                            try {
                                                ServiceManager.waitForService((String) obj)
                                                        .shellCommand(
                                                                null,
                                                                null,
                                                                null,
                                                                new String[] {"reboot"},
                                                                null,
                                                                null);
                                                break;
                                            } catch (Exception e2) {
                                                Slog.e(
                                                        "SoundTriggerMiddlewareDefaultHalFactory",
                                                        "Failed to reboot mock HAL",
                                                        e2);
                                            }
                                    }
                                }
                            },
                            iCaptureStateNotifier);
                } finally {
                    HwBinder.setTrebleTestingOverride(false);
                }
            }
            if (i != 3) {
                throw new RuntimeException("Unknown HAL mock version: " + i);
            }
            final String str2 = ISoundTriggerHw.class.getCanonicalName() + "/mock";
            Slog.i(
                    "SoundTriggerMiddlewareDefaultHalFactory",
                    "Connecting to mock soundtrigger3.ISoundTriggerHw");
            final int i5 = 1;
            return new SoundTriggerHw3Compat(
                    ServiceManager.waitForService(str2),
                    new Runnable() { // from class:
                                     // com.android.server.soundtrigger_middleware.DefaultHalFactory$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i52 = i5;
                            Object obj = str2;
                            switch (i52) {
                                case 0:
                                    try {
                                        ((android.hardware.soundtrigger.V2_0.ISoundTriggerHw) obj)
                                                .debug(
                                                        null,
                                                        new ArrayList(Arrays.asList("reboot")));
                                        break;
                                    } catch (Exception e) {
                                        Slog.e(
                                                "SoundTriggerMiddlewareDefaultHalFactory",
                                                "Failed to reboot mock HAL",
                                                e);
                                        return;
                                    }
                                default:
                                    try {
                                        ServiceManager.waitForService((String) obj)
                                                .shellCommand(
                                                        null,
                                                        null,
                                                        null,
                                                        new String[] {"reboot"},
                                                        null,
                                                        null);
                                        break;
                                    } catch (Exception e2) {
                                        Slog.e(
                                                "SoundTriggerMiddlewareDefaultHalFactory",
                                                "Failed to reboot mock HAL",
                                                e2);
                                    }
                            }
                        }
                    });
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }
}
