package com.android.server.display;

import android.hardware.display.BrightnessInfo;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Temperature;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.util.Slog;
import com.android.server.display.BrightnessThrottler;
import com.android.server.display.DisplayDeviceConfig;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class BrightnessThrottler {
    public float mBrightnessCap;
    public int mBrightnessMaxReason;
    public HashMap mDdcThermalThrottlingDataMap;
    public final DeviceConfigInterface mDeviceConfig;
    public final Handler mDeviceConfigHandler;
    public final DeviceConfigListener mDeviceConfigListener;
    public final Handler mHandler;
    public final Injector mInjector;
    public final SkinThermalStatusObserver mSkinThermalStatusObserver;
    public String mThermalBrightnessThrottlingDataId;
    public final HashMap mThermalBrightnessThrottlingDataOverride;
    public String mThermalBrightnessThrottlingDataString;
    public DisplayDeviceConfig.ThermalBrightnessThrottlingData mThermalThrottlingData;
    public final Runnable mThrottlingChangeCallback;
    public int mThrottlingStatus;
    public String mUniqueDisplayId;

    public BrightnessThrottler(Handler handler, Runnable runnable, String str, String str2, HashMap hashMap) {
        this(new Injector(), handler, handler, runnable, str, str2, hashMap);
    }

    public BrightnessThrottler(Injector injector, Handler handler, Handler handler2, Runnable runnable, String str, String str2, HashMap hashMap) {
        this.mBrightnessCap = 1.0f;
        this.mBrightnessMaxReason = 0;
        this.mThermalBrightnessThrottlingDataOverride = new HashMap(1);
        this.mInjector = injector;
        this.mHandler = handler;
        this.mDeviceConfigHandler = handler2;
        this.mDdcThermalThrottlingDataMap = hashMap;
        this.mThrottlingChangeCallback = runnable;
        this.mSkinThermalStatusObserver = new SkinThermalStatusObserver(injector, handler);
        this.mUniqueDisplayId = str;
        this.mDeviceConfig = injector.getDeviceConfig();
        this.mDeviceConfigListener = new DeviceConfigListener();
        this.mThermalBrightnessThrottlingDataId = str2;
        this.mDdcThermalThrottlingDataMap = hashMap;
        loadThermalBrightnessThrottlingDataFromDeviceConfig();
        loadThermalBrightnessThrottlingDataFromDisplayDeviceConfig(this.mDdcThermalThrottlingDataMap, this.mThermalBrightnessThrottlingDataId, this.mUniqueDisplayId);
    }

    public boolean deviceSupportsThrottling() {
        return this.mThermalThrottlingData != null;
    }

    public float getBrightnessCap() {
        return this.mBrightnessCap;
    }

    public int getBrightnessMaxReason() {
        return this.mBrightnessMaxReason;
    }

    public boolean isThrottled() {
        return this.mBrightnessMaxReason != 0;
    }

    public void stop() {
        this.mSkinThermalStatusObserver.stopObserving();
        this.mDeviceConfig.removeOnPropertiesChangedListener(this.mDeviceConfigListener);
        this.mBrightnessCap = 1.0f;
        this.mBrightnessMaxReason = 0;
        this.mThrottlingStatus = -1;
    }

    public void loadThermalBrightnessThrottlingDataFromDisplayDeviceConfig(HashMap hashMap, String str, String str2) {
        this.mDdcThermalThrottlingDataMap = hashMap;
        this.mThermalBrightnessThrottlingDataId = str;
        this.mUniqueDisplayId = str2;
        resetThermalThrottlingData();
    }

    public final float verifyAndConstrainBrightnessCap(float f) {
        if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            Slog.e("BrightnessThrottler", "brightness " + f + " is lower than the minimum possible brightness " + DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            f = 0.0f;
        }
        if (f <= 1.0f) {
            return f;
        }
        Slog.e("BrightnessThrottler", "brightness " + f + " is higher than the maximum possible brightness 1.0");
        return 1.0f;
    }

    public final void thermalStatusChanged(int i) {
        if (this.mThrottlingStatus != i) {
            this.mThrottlingStatus = i;
            updateThermalThrottling();
        }
    }

    public final void updateThermalThrottling() {
        DisplayDeviceConfig.ThermalBrightnessThrottlingData thermalBrightnessThrottlingData;
        if (deviceSupportsThrottling()) {
            float f = 1.0f;
            int i = 0;
            if (this.mThrottlingStatus != -1 && (thermalBrightnessThrottlingData = this.mThermalThrottlingData) != null) {
                for (DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel throttlingLevel : thermalBrightnessThrottlingData.throttlingLevels) {
                    if (throttlingLevel.thermalStatus > this.mThrottlingStatus) {
                        break;
                    }
                    f = throttlingLevel.brightness;
                    i = 1;
                }
            }
            if (this.mBrightnessCap == f && this.mBrightnessMaxReason == i) {
                return;
            }
            this.mBrightnessCap = verifyAndConstrainBrightnessCap(f);
            this.mBrightnessMaxReason = i;
            Runnable runnable = this.mThrottlingChangeCallback;
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public void dump(final PrintWriter printWriter) {
        this.mHandler.runWithScissors(new Runnable() { // from class: com.android.server.display.BrightnessThrottler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessThrottler.this.lambda$dump$0(printWriter);
            }
        }, 1000L);
    }

    /* renamed from: dumpLocal, reason: merged with bridge method [inline-methods] */
    public final void lambda$dump$0(PrintWriter printWriter) {
        printWriter.println("BrightnessThrottler:");
        printWriter.println("  mThermalBrightnessThrottlingDataId=" + this.mThermalBrightnessThrottlingDataId);
        printWriter.println("  mThermalThrottlingData=" + this.mThermalThrottlingData);
        printWriter.println("  mUniqueDisplayId=" + this.mUniqueDisplayId);
        printWriter.println("  mThrottlingStatus=" + this.mThrottlingStatus);
        printWriter.println("  mBrightnessCap=" + this.mBrightnessCap);
        printWriter.println("  mBrightnessMaxReason=" + BrightnessInfo.briMaxReasonToString(this.mBrightnessMaxReason));
        printWriter.println("  mDdcThermalThrottlingDataMap=" + this.mDdcThermalThrottlingDataMap);
        printWriter.println("  mThermalBrightnessThrottlingDataOverride=" + this.mThermalBrightnessThrottlingDataOverride);
        printWriter.println("  mThermalBrightnessThrottlingDataString=" + this.mThermalBrightnessThrottlingDataString);
        this.mSkinThermalStatusObserver.dump(printWriter);
    }

    public final String getThermalBrightnessThrottlingDataString() {
        return this.mDeviceConfig.getString("display_manager", "brightness_throttling_data", (String) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean parseAndAddData(String str, HashMap hashMap) {
        int i;
        String str2;
        int i2;
        String[] split = str.split(",");
        int i3 = 1;
        try {
            String str3 = split[0];
            i = 2;
            try {
                int parseInt = Integer.parseInt(split[1]);
                ArrayList arrayList = new ArrayList(parseInt);
                int i4 = 0;
                while (i4 < parseInt) {
                    int i5 = i + 1;
                    try {
                        i2 = i5 + 1;
                    } catch (UnknownThermalStatusException | IndexOutOfBoundsException | NumberFormatException e) {
                        e = e;
                        i3 = i5;
                    }
                    try {
                        arrayList.add(new DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel(parseThermalStatus(split[i]), parseBrightness(split[i5])));
                        i4++;
                        i = i2;
                    } catch (UnknownThermalStatusException | IndexOutOfBoundsException | NumberFormatException e2) {
                        e = e2;
                        i3 = i2;
                        Slog.e("BrightnessThrottler", "Throttling data is invalid array: '" + str + "'", e);
                        i = i3;
                        i3 = 0;
                        if (i != split.length) {
                        }
                    }
                }
                if (i < split.length) {
                    int i6 = i + 1;
                    try {
                        str2 = split[i];
                        i = i6;
                    } catch (UnknownThermalStatusException | IndexOutOfBoundsException | NumberFormatException e3) {
                        e = e3;
                        i3 = i6;
                        Slog.e("BrightnessThrottler", "Throttling data is invalid array: '" + str + "'", e);
                        i = i3;
                        i3 = 0;
                        if (i != split.length) {
                        }
                    }
                } else {
                    str2 = "default";
                }
                DisplayDeviceConfig.ThermalBrightnessThrottlingData create = DisplayDeviceConfig.ThermalBrightnessThrottlingData.create(arrayList);
                HashMap hashMap2 = (HashMap) hashMap.get(str3);
                if (hashMap2 == null) {
                    HashMap hashMap3 = new HashMap();
                    hashMap3.put(str2, create);
                    hashMap.put(str3, hashMap3);
                } else {
                    if (hashMap2.containsKey(str2)) {
                        Slog.e("BrightnessThrottler", "Throttling data for display " + str3 + "contains duplicate throttling ids: '" + str2 + "'");
                        return false;
                    }
                    hashMap2.put(str2, create);
                }
            } catch (UnknownThermalStatusException | IndexOutOfBoundsException | NumberFormatException e4) {
                e = e4;
                i3 = 2;
            }
        } catch (UnknownThermalStatusException | IndexOutOfBoundsException | NumberFormatException e5) {
            e = e5;
        }
        if (i != split.length) {
            return false;
        }
        return i3;
    }

    public final void loadThermalBrightnessThrottlingDataFromDeviceConfig() {
        boolean z = true;
        HashMap hashMap = new HashMap(1);
        this.mThermalBrightnessThrottlingDataString = getThermalBrightnessThrottlingDataString();
        this.mThermalBrightnessThrottlingDataOverride.clear();
        String str = this.mThermalBrightnessThrottlingDataString;
        if (str != null) {
            String[] split = str.split(KnoxVpnFirewallHelper.DELIMITER);
            int length = split.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (!parseAndAddData(split[i], hashMap)) {
                    z = false;
                    break;
                }
                i++;
            }
            if (z) {
                this.mThermalBrightnessThrottlingDataOverride.putAll(hashMap);
                hashMap.clear();
                return;
            }
            return;
        }
        Slog.w("BrightnessThrottler", "DeviceConfig ThermalBrightnessThrottlingData is null");
    }

    public final void resetThermalThrottlingData() {
        stop();
        this.mDeviceConfigListener.startListening();
        this.mThermalThrottlingData = getConfigFromId(this.mThermalBrightnessThrottlingDataId);
        if (!"default".equals(this.mThermalBrightnessThrottlingDataId) && this.mThermalThrottlingData == null) {
            this.mThermalThrottlingData = getConfigFromId("default");
            Slog.d("BrightnessThrottler", "Falling back to default throttling Id");
        }
        if (deviceSupportsThrottling()) {
            this.mSkinThermalStatusObserver.startObserving();
        }
    }

    public final DisplayDeviceConfig.ThermalBrightnessThrottlingData getConfigFromId(String str) {
        DisplayDeviceConfig.ThermalBrightnessThrottlingData thermalBrightnessThrottlingData = this.mThermalBrightnessThrottlingDataOverride.get(this.mUniqueDisplayId) == null ? null : (DisplayDeviceConfig.ThermalBrightnessThrottlingData) ((HashMap) this.mThermalBrightnessThrottlingDataOverride.get(this.mUniqueDisplayId)).get(str);
        return thermalBrightnessThrottlingData == null ? (DisplayDeviceConfig.ThermalBrightnessThrottlingData) this.mDdcThermalThrottlingDataMap.get(str) : thermalBrightnessThrottlingData;
    }

    public class DeviceConfigListener implements DeviceConfig.OnPropertiesChangedListener {
        public Executor mExecutor;

        public DeviceConfigListener() {
            this.mExecutor = new HandlerExecutor(BrightnessThrottler.this.mDeviceConfigHandler);
        }

        public void startListening() {
            BrightnessThrottler.this.mDeviceConfig.addOnPropertiesChangedListener("display_manager", this.mExecutor, this);
        }

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            BrightnessThrottler.this.loadThermalBrightnessThrottlingDataFromDeviceConfig();
            BrightnessThrottler.this.resetThermalThrottlingData();
        }
    }

    public final float parseBrightness(String str) {
        float parseFloat = Float.parseFloat(str);
        if (parseFloat < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || parseFloat > 1.0f) {
            throw new NumberFormatException("Brightness constraint value out of bounds.");
        }
        return parseFloat;
    }

    public final int parseThermalStatus(String str) {
        str.hashCode();
        switch (str) {
            case "severe":
                return 3;
            case "moderate":
                return 2;
            case "shutdown":
                return 6;
            case "none":
                return 0;
            case "light":
                return 1;
            case "emergency":
                return 5;
            case "critical":
                return 4;
            default:
                throw new UnknownThermalStatusException("Invalid Thermal Status: " + str);
        }
    }

    class UnknownThermalStatusException extends Exception {
        public UnknownThermalStatusException(String str) {
            super(str);
        }
    }

    public final class SkinThermalStatusObserver extends IThermalEventListener.Stub {
        public final Handler mHandler;
        public final Injector mInjector;
        public boolean mStarted;
        public IThermalService mThermalService;

        public SkinThermalStatusObserver(Injector injector, Handler handler) {
            this.mInjector = injector;
            this.mHandler = handler;
        }

        public void notifyThrottling(final Temperature temperature) {
            this.mHandler.post(new Runnable() { // from class: com.android.server.display.BrightnessThrottler$SkinThermalStatusObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BrightnessThrottler.SkinThermalStatusObserver.this.lambda$notifyThrottling$0(temperature);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyThrottling$0(Temperature temperature) {
            BrightnessThrottler.this.thermalStatusChanged(temperature.getStatus());
        }

        public void startObserving() {
            if (this.mStarted) {
                return;
            }
            IThermalService thermalService = this.mInjector.getThermalService();
            this.mThermalService = thermalService;
            if (thermalService == null) {
                Slog.e("BrightnessThrottler", "Could not observe thermal status. Service not available");
                return;
            }
            try {
                thermalService.registerThermalEventListenerWithType(this, 3);
                this.mStarted = true;
            } catch (RemoteException e) {
                Slog.e("BrightnessThrottler", "Failed to register thermal status listener", e);
            }
        }

        public void stopObserving() {
            if (this.mStarted) {
                try {
                    this.mThermalService.unregisterThermalEventListener(this);
                    this.mStarted = false;
                } catch (RemoteException e) {
                    Slog.e("BrightnessThrottler", "Failed to unregister thermal status listener", e);
                }
                this.mThermalService = null;
            }
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println("  SkinThermalStatusObserver:");
            printWriter.println("    mStarted: " + this.mStarted);
            if (this.mThermalService != null) {
                printWriter.println("    ThermalService available");
            } else {
                printWriter.println("    ThermalService not available");
            }
        }
    }

    public class Injector {
        public IThermalService getThermalService() {
            return IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
        }

        public DeviceConfigInterface getDeviceConfig() {
            return DeviceConfigInterface.REAL;
        }
    }
}
