package com.android.server.usb;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.usb.UsbDevice;
import android.media.AudioManager;
import android.media.IAudioService;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.sysfwutil.Slog;
import com.android.internal.alsa.AlsaCardsParser;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.server.usb.descriptors.UsbDescriptorParser;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public final class UsbAlsaManager {
  public static final String TAG = "UsbAlsaManager";
  public IAudioService mAudioService;
  public final Context mContext;
  public final boolean mHasMidiFeature;
  public static final boolean IS_MULTI_MODE =
      SystemProperties.getBoolean("ro.audio.multi_usb_mode", false);
  public static final List sDeviceDenylist =
      Arrays.asList(
          new DenyListEntry(1356, 1476, 1),
          new DenyListEntry(1356, 2508, 1),
          new DenyListEntry(1356, 3302, 1));
  public final AlsaCardsParser mCardsParser = new AlsaCardsParser();
  public final ArrayList mAlsaDevices = new ArrayList();
  public HashMap mAttachedDevices = new HashMap();
  public UsbAlsaDevice mSelectedBundleDevice = null;
  public boolean isBundleRemovedDone = true;
  public final HashMap mMidiDevices = new HashMap();
  public UsbAlsaMidiDevice mPeripheralMidiDevice = null;
  public final HashSet mAlsaCards = new HashSet();
  public final FileObserver mAlsaObserver =
      new FileObserver(
          new File("/dev/snd/"),
          FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE) { // from class:
        // com.android.server.usb.UsbAlsaManager.1
        @Override // android.os.FileObserver
        public void onEvent(int i, String str) {
          if (i == 256) {
            UsbAlsaManager.this.alsaFileAdded(str);
          } else {
            if (i != 512) {
              return;
            }
            UsbAlsaManager.this.alsaFileRemoved(str);
          }
        }
      };

  public class DenyListEntry {
    public final int mFlags;
    public final int mProductId;
    public final int mVendorId;

    public DenyListEntry(int i, int i2, int i3) {
      this.mVendorId = i;
      this.mProductId = i2;
      this.mFlags = i3;
    }
  }

  public static boolean isDeviceDenylisted(int i, int i2, int i3) {
    for (DenyListEntry denyListEntry : sDeviceDenylist) {
      if (denyListEntry.mVendorId == i && denyListEntry.mProductId == i2) {
        return (denyListEntry.mFlags & i3) != 0;
      }
    }
    return false;
  }

  public UsbAlsaManager(Context context) {
    this.mContext = context;
    this.mHasMidiFeature = context.getPackageManager().hasSystemFeature("android.software.midi");
  }

  public void systemReady() {
    this.mAudioService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
    this.mAlsaObserver.startWatching();
  }

  public final synchronized void selectAlsaDevice(UsbAlsaDevice usbAlsaDevice) {
    String str = TAG;
    Slog.d(str, "selectAlsaDevice() " + usbAlsaDevice);
    if (Settings.Secure.getInt(
            this.mContext.getContentResolver(), "usb_audio_automatic_routing_disabled", 0)
        != 0) {
      Slog.d(str, "USB_AUDIO_AUTOMATIC_ROUTING_DISABLED");
    } else {
      usbAlsaDevice.start();
      Slog.d(str, "selectAlsaDevice() - done.");
    }
  }

  public final synchronized void deselectAlsaDevice(UsbAlsaDevice usbAlsaDevice) {
    Slog.d(TAG, "deselectAlsaDevice() selectedDevice " + usbAlsaDevice);
    usbAlsaDevice.stop();
  }

  public final int getAlsaDeviceListIndexFor(String str) {
    for (int i = 0; i < this.mAlsaDevices.size(); i++) {
      if (((UsbAlsaDevice) this.mAlsaDevices.get(i)).getDeviceAddress().equals(str)) {
        return i;
      }
    }
    return -1;
  }

  public final void addDeviceToAttachedDevicesMap(int i, UsbAlsaDevice usbAlsaDevice) {
    if (i == 0) {
      Slog.i(TAG, "Ignore caching device as the type is NONE, device=" + usbAlsaDevice);
      return;
    }
    Stack stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i));
    if (stack == null) {
      this.mAttachedDevices.put(Integer.valueOf(i), new Stack());
      stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i));
    }
    stack.push(usbAlsaDevice);
  }

  public final void addAlsaDevice(UsbAlsaDevice usbAlsaDevice) {
    this.mAlsaDevices.add(0, usbAlsaDevice);
    addDeviceToAttachedDevicesMap(usbAlsaDevice.getInputDeviceType(), usbAlsaDevice);
    addDeviceToAttachedDevicesMap(usbAlsaDevice.getOutputDeviceType(), usbAlsaDevice);
  }

  public final void removeDeviceFromAttachedDevicesMap(int i, UsbAlsaDevice usbAlsaDevice) {
    Stack stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i));
    if (stack == null) {
      return;
    }
    stack.remove(usbAlsaDevice);
    if (stack.isEmpty()) {
      this.mAttachedDevices.remove(Integer.valueOf(i));
    }
  }

  public final UsbAlsaDevice removeAlsaDevice(String str) {
    int alsaDeviceListIndexFor = getAlsaDeviceListIndexFor(str);
    if (alsaDeviceListIndexFor <= -1) {
      return null;
    }
    UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) this.mAlsaDevices.remove(alsaDeviceListIndexFor);
    removeDeviceFromAttachedDevicesMap(usbAlsaDevice.getOutputDeviceType(), usbAlsaDevice);
    removeDeviceFromAttachedDevicesMap(usbAlsaDevice.getInputDeviceType(), usbAlsaDevice);
    return usbAlsaDevice;
  }

  public final UsbAlsaDevice selectDefaultDevice(int i) {
    String str = TAG;
    Slog.d(str, "selectDefaultDevice():" + i);
    Stack stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i));
    if (stack == null || stack.isEmpty()) {
      return null;
    }
    UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) stack.peek();
    Slog.d(str, "select default device:" + usbAlsaDevice);
    if (AudioManager.isInputDevice(i)) {
      usbAlsaDevice.startInput();
    } else {
      usbAlsaDevice.startOutput();
    }
    return usbAlsaDevice;
  }

  public final void deselectCurrentDevice(int i) {
    Stack stack;
    String str = TAG;
    Slog.d(str, "deselectCurrentDevice():" + i);
    if (i == 0
        || (stack = (Stack) this.mAttachedDevices.get(Integer.valueOf(i))) == null
        || stack.isEmpty()) {
      return;
    }
    UsbAlsaDevice usbAlsaDevice = (UsbAlsaDevice) stack.peek();
    Slog.d(str, "deselect current device:" + usbAlsaDevice);
    if (AudioManager.isInputDevice(i)) {
      usbAlsaDevice.stopInput();
    } else {
      usbAlsaDevice.stopOutput();
    }
  }

  public void usbDeviceAdded(
      String str, UsbDevice usbDevice, UsbDescriptorParser usbDescriptorParser) {
    String str2 = TAG;
    Slog.d(
        str2,
        "usbDeviceAdded(): "
            + usbDevice.getManufacturerName()
            + " nm:"
            + usbDevice.getProductName());
    this.mCardsParser.scan();
    AlsaCardsParser.AlsaCardRecord findCardNumFor = this.mCardsParser.findCardNumFor(str);
    if (findCardNumFor == null) {
      Slog.e(str2, "usbDeviceAdded(): cannot find sound card for " + str);
      return;
    }
    waitForAlsaDevice(findCardNumFor.getCardNum(), true);
    boolean z =
        usbDescriptorParser.hasInput()
            && !isDeviceDenylisted(usbDevice.getVendorId(), usbDevice.getProductId(), 2);
    boolean z2 =
        usbDescriptorParser.hasOutput()
            && !isDeviceDenylisted(usbDevice.getVendorId(), usbDevice.getProductId(), 1);
    Slog.d(str2, "hasInput: " + z + " hasOutput:" + z2);
    if (z || z2) {
      boolean isInputHeadset = usbDescriptorParser.isInputHeadset();
      boolean isOutputHeadset = usbDescriptorParser.isOutputHeadset();
      boolean isDock = usbDescriptorParser.isDock();
      if (this.mAudioService == null) {
        Slog.e(str2, "no AudioService");
        return;
      }
      UsbAlsaDevice usbAlsaDevice =
          new UsbAlsaDevice(
              this.mAudioService,
              findCardNumFor.getCardNum(),
              0,
              str,
              z2,
              z,
              isInputHeadset,
              isOutputHeadset,
              isDock);
      usbAlsaDevice.setDeviceNameAndDescription(
          findCardNumFor.getCardName(), findCardNumFor.getCardDescription());
      if (IS_MULTI_MODE) {
        deselectCurrentDevice(usbAlsaDevice.getInputDeviceType());
        deselectCurrentDevice(usbAlsaDevice.getOutputDeviceType());
      } else if (!this.mAlsaDevices.isEmpty()) {
        deselectAlsaDevice((UsbAlsaDevice) this.mAlsaDevices.get(0));
      }
      addAlsaDevice(usbAlsaDevice);
      selectAlsaDevice(usbAlsaDevice);
    }
    addMidiDevice(str, usbDevice, usbDescriptorParser, findCardNumFor);
    logDevices("deviceAdded()");
    Slog.d(str2, "deviceAdded() - done");
  }

  public final void addMidiDevice(
      String str,
      UsbDevice usbDevice,
      UsbDescriptorParser usbDescriptorParser,
      AlsaCardsParser.AlsaCardRecord alsaCardRecord) {
    String str2;
    boolean hasMIDIInterface = usbDescriptorParser.hasMIDIInterface();
    boolean containsUniversalMidiDeviceEndpoint =
        usbDescriptorParser.containsUniversalMidiDeviceEndpoint();
    String str3 = TAG;
    Slog.d(str3, "hasMidi: " + hasMIDIInterface + " mHasMidiFeature:" + this.mHasMidiFeature);
    StringBuilder sb = new StringBuilder();
    sb.append("hasMidi2: ");
    sb.append(containsUniversalMidiDeviceEndpoint);
    Slog.d(str3, sb.toString());
    if (this.mHasMidiFeature && hasMIDIInterface && !containsUniversalMidiDeviceEndpoint) {
      Bundle bundle = new Bundle();
      String manufacturerName = usbDevice.getManufacturerName();
      String productName = usbDevice.getProductName();
      String version = usbDevice.getVersion();
      if (manufacturerName == null || manufacturerName.isEmpty()) {
        str2 = productName;
      } else if (productName == null || productName.isEmpty()) {
        str2 = manufacturerName;
      } else {
        str2 = manufacturerName + " " + productName;
      }
      bundle.putString("name", str2);
      bundle.putString("manufacturer", manufacturerName);
      bundle.putString("product", productName);
      bundle.putString("version", version);
      bundle.putString("serial_number", usbDevice.getSerialNumber());
      bundle.putInt("alsa_card", alsaCardRecord.getCardNum());
      bundle.putInt("alsa_device", 0);
      bundle.putParcelable("usb_device", usbDevice);
      int calculateNumLegacyMidiInputs = usbDescriptorParser.calculateNumLegacyMidiInputs();
      int calculateNumLegacyMidiOutputs = usbDescriptorParser.calculateNumLegacyMidiOutputs();
      Slog.d(str3, "numLegacyMidiInputs: " + calculateNumLegacyMidiInputs);
      Slog.d(str3, "numLegacyMidiOutputs:" + calculateNumLegacyMidiOutputs);
      UsbAlsaMidiDevice create =
          UsbAlsaMidiDevice.create(
              this.mContext,
              bundle,
              alsaCardRecord.getCardNum(),
              0,
              calculateNumLegacyMidiInputs,
              calculateNumLegacyMidiOutputs);
      if (create != null) {
        this.mMidiDevices.put(str, create);
      }
    }
  }

  public synchronized void usbDeviceRemoved(String str) {
    String str2 = TAG;
    Slog.d(str2, "deviceRemoved(" + str + ")");
    UsbAlsaDevice removeAlsaDevice = removeAlsaDevice(str);
    Slog.i(str2, "USB Audio Device Removed: " + removeAlsaDevice);
    if (this.mSelectedBundleDevice != null && this.isBundleRemovedDone) {
      Slog.d(
          str2,
          "usbDeviceRemoved set mSelectedBundleDevice to null, as it's not set even Bundle Remove"
              + " was done");
      this.mSelectedBundleDevice = null;
      this.isBundleRemovedDone = false;
    }
    if (removeAlsaDevice != null) {
      waitForAlsaDevice(removeAlsaDevice.getCardNum(), false);
      deselectAlsaDevice(removeAlsaDevice);
      if (IS_MULTI_MODE) {
        selectDefaultDevice(removeAlsaDevice.getOutputDeviceType());
        selectDefaultDevice(removeAlsaDevice.getInputDeviceType());
      } else if (!this.mAlsaDevices.isEmpty() && this.mAlsaDevices.get(0) != null) {
        selectAlsaDevice((UsbAlsaDevice) this.mAlsaDevices.get(0));
      }
    }
    UsbAlsaMidiDevice usbAlsaMidiDevice = (UsbAlsaMidiDevice) this.mMidiDevices.remove(str);
    if (usbAlsaMidiDevice != null) {
      Slog.i(str2, "USB MIDI Device Removed: " + str);
      IoUtils.closeQuietly(usbAlsaMidiDevice);
    }
    logDevices("usbDeviceRemoved()");
  }

  public void usbDeviceAddedBundle(String str, int i) {
    String str2 = TAG;
    Slog.d(str2, "usbDeviceAddedBundle(): deviceAddress=" + str + " cardNum=" + i);
    if (this.mAudioService == null) {
      Slog.e(str2, "no AudioService");
      return;
    }
    if (Settings.Secure.getInt(
            this.mContext.getContentResolver(), "usb_audio_automatic_routing_disabled", 0)
        != 0) {
      Slog.e(str2, "Disable USB audio routing is ON at usbDeviceAddedBundle");
      return;
    }
    UsbAlsaDevice usbAlsaDevice =
        new UsbAlsaDevice(this.mAudioService, i, 0, str, true, true, true, true, false);
    this.mSelectedBundleDevice = usbAlsaDevice;
    this.isBundleRemovedDone = false;
    usbAlsaDevice.setDeviceNameAndDescription("USB-Audio - Samsung USB C Earphone", "");
    usbAlsaDevice.updateWiredDeviceConnectionStateByBundle(true);
  }

  public void usbDeviceRemovedBundle(String str, int i) {
    String str2 = TAG;
    Slog.d(str2, "usbDeviceRemovedBundle(): deviceAddress=" + str + " cardNum=" + i);
    if (this.mAudioService == null) {
      Slog.e(str2, "no AudioService");
      return;
    }
    if (Settings.Secure.getInt(
            this.mContext.getContentResolver(), "usb_audio_automatic_routing_disabled", 0)
        != 0) {
      Slog.e(str2, "Disable USB audio routing is ON at usbDeviceRemovedBundle");
      return;
    }
    if (this.mSelectedBundleDevice == null) {
      Slog.e(str2, "No Selected Bundel Device at usbDeviceRemovedBundle");
      return;
    }
    UsbAlsaDevice usbAlsaDevice =
        new UsbAlsaDevice(this.mAudioService, i, 0, str, true, true, true, true, false);
    this.mSelectedBundleDevice = null;
    this.isBundleRemovedDone = true;
    usbAlsaDevice.setDeviceNameAndDescription("USB-Audio - Samsung USB C Earphone", "");
    usbAlsaDevice.updateWiredDeviceConnectionStateByBundle(false);
  }

  public void setPeripheralMidiState(boolean z, int i, int i2) {
    UsbAlsaMidiDevice usbAlsaMidiDevice;
    if (this.mHasMidiFeature) {
      if (!z || this.mPeripheralMidiDevice != null) {
        if (z || (usbAlsaMidiDevice = this.mPeripheralMidiDevice) == null) {
          return;
        }
        IoUtils.closeQuietly(usbAlsaMidiDevice);
        this.mPeripheralMidiDevice = null;
        return;
      }
      Bundle bundle = new Bundle();
      Resources resources = this.mContext.getResources();
      bundle.putString("name", resources.getString(17043138));
      bundle.putString("manufacturer", resources.getString(17043137));
      bundle.putString("product", resources.getString(17043139));
      bundle.putInt("alsa_card", i);
      bundle.putInt("alsa_device", i2);
      this.mPeripheralMidiDevice = UsbAlsaMidiDevice.create(this.mContext, bundle, i, i2, 1, 1);
    }
  }

  public final boolean waitForAlsaDevice(int i, boolean z) {
    boolean contains;
    Slog.e(TAG, "waitForAlsaDevice(c:" + i + ")");
    synchronized (this.mAlsaCards) {
      long elapsedRealtime = SystemClock.elapsedRealtime() + 2500;
      while ((this.mAlsaCards.contains(Integer.valueOf(i)) ^ z)
          && elapsedRealtime > SystemClock.elapsedRealtime()) {
        long elapsedRealtime2 = elapsedRealtime - SystemClock.elapsedRealtime();
        if (elapsedRealtime2 > 0) {
          try {
            this.mAlsaCards.wait(elapsedRealtime2);
          } catch (InterruptedException unused) {
            Slog.d(TAG, "usb: InterruptedException while waiting for ALSA file.");
          }
        }
      }
      contains = this.mAlsaCards.contains(Integer.valueOf(i));
      if ((z ^ contains) && elapsedRealtime > SystemClock.elapsedRealtime()) {
        Slog.e(TAG, "waitForAlsaDevice(" + i + ") timeout");
      } else {
        Slog.i(
            TAG,
            "waitForAlsaDevice for device card=" + i + ", isAdded=" + z + ", found=" + contains);
      }
    }
    return contains;
  }

  public final int getCardNumberFromAlsaFilePath(String str) {
    char c;
    if (str.startsWith("pcmC")) {
      if (str.endsWith(KnoxAnalyticsDataConverter.PAYLOAD)) {
        c = 1;
      } else {
        if (str.endsWith("c")) {
          c = 2;
        }
        c = 0;
      }
    } else {
      if (str.startsWith("midiC")) {
        c = 3;
      }
      c = 0;
    }
    if (c == 0) {
      Slog.i(TAG, "Unknown type file(" + str + ") added.");
      return -1;
    }
    try {
      return Integer.parseInt(str.substring(str.indexOf(67) + 1, str.indexOf(68)));
    } catch (Exception e) {
      Slog.e(TAG, "Could not parse ALSA file name " + str, e);
      return -1;
    }
  }

  public final void alsaFileAdded(String str) {
    String str2 = TAG;
    Slog.i(str2, "alsaFileAdded(" + str + ")");
    int cardNumberFromAlsaFilePath = getCardNumberFromAlsaFilePath(str);
    if (cardNumberFromAlsaFilePath == -1) {
      return;
    }
    synchronized (this.mAlsaCards) {
      if (!this.mAlsaCards.contains(Integer.valueOf(cardNumberFromAlsaFilePath))) {
        Slog.d(str2, "Adding ALSA device card=" + cardNumberFromAlsaFilePath);
        this.mAlsaCards.add(Integer.valueOf(cardNumberFromAlsaFilePath));
        this.mAlsaCards.notifyAll();
      }
    }
  }

  public final void alsaFileRemoved(String str) {
    int cardNumberFromAlsaFilePath = getCardNumberFromAlsaFilePath(str);
    if (cardNumberFromAlsaFilePath == -1) {
      return;
    }
    synchronized (this.mAlsaCards) {
      this.mAlsaCards.remove(Integer.valueOf(cardNumberFromAlsaFilePath));
    }
  }

  public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
    long start = dualDumpOutputStream.start(str, j);
    dualDumpOutputStream.write("cards_parser", 1120986464257L, this.mCardsParser.getScanStatus());
    Iterator it = this.mAlsaDevices.iterator();
    while (it.hasNext()) {
      ((UsbAlsaDevice) it.next()).dump(dualDumpOutputStream, "alsa_devices", 2246267895810L);
    }
    for (String str2 : this.mMidiDevices.keySet()) {
      ((UsbAlsaMidiDevice) this.mMidiDevices.get(str2))
          .dump(str2, dualDumpOutputStream, "alsa_midi_devices", 2246267895812L);
    }
    dualDumpOutputStream.end(start);
  }

  public void logDevices(String str) {
    Slog.i(TAG, str + "----------------");
    Iterator it = this.mAlsaDevices.iterator();
    while (it.hasNext()) {
      Slog.i(TAG, ((UsbAlsaDevice) it.next()).toShortString());
    }
    Slog.i(TAG, "----------------");
  }
}
