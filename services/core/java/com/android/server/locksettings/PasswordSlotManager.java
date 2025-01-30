package com.android.server.locksettings;

import android.os.SystemProperties;
import android.util.Slog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/* loaded from: classes2.dex */
public class PasswordSlotManager {
  public Set mActiveSlots;
  public Map mSlotMap;

  public String getSlotMapDir() {
    return "/metadata/password_slots";
  }

  public int getGsiImageNumber() {
    return SystemProperties.getInt("ro.gsid.image_running", 0);
  }

  public void refreshActiveSlots(Set set) {
    if (this.mSlotMap == null) {
      this.mActiveSlots = new HashSet(set);
      return;
    }
    HashSet hashSet = new HashSet();
    for (Map.Entry entry : this.mSlotMap.entrySet()) {
      if (((String) entry.getValue()).equals(getMode())) {
        hashSet.add((Integer) entry.getKey());
      }
    }
    Iterator it = hashSet.iterator();
    while (it.hasNext()) {
      this.mSlotMap.remove((Integer) it.next());
    }
    Iterator it2 = set.iterator();
    while (it2.hasNext()) {
      this.mSlotMap.put((Integer) it2.next(), getMode());
    }
    saveSlotMap();
  }

  public void markSlotInUse(int i) {
    ensureSlotMapLoaded();
    if (this.mSlotMap.containsKey(Integer.valueOf(i))
        && !((String) this.mSlotMap.get(Integer.valueOf(i))).equals(getMode())) {
      throw new IllegalStateException("password slot " + i + " is not available");
    }
    this.mSlotMap.put(Integer.valueOf(i), getMode());
    saveSlotMap();
  }

  public void markSlotDeleted(int i) {
    ensureSlotMapLoaded();
    if (this.mSlotMap.containsKey(Integer.valueOf(i))
        && !((String) this.mSlotMap.get(Integer.valueOf(i))).equals(getMode())) {
      throw new IllegalStateException("password slot " + i + " cannot be deleted");
    }
    this.mSlotMap.remove(Integer.valueOf(i));
    saveSlotMap();
  }

  public Set getUsedSlots() {
    ensureSlotMapLoaded();
    return Collections.unmodifiableSet(this.mSlotMap.keySet());
  }

  public final File getSlotMapFile() {
    return Paths.get(getSlotMapDir(), "slot_map").toFile();
  }

  public final String getMode() {
    int gsiImageNumber = getGsiImageNumber();
    if (gsiImageNumber <= 0) {
      return "host";
    }
    return "gsi" + gsiImageNumber;
  }

  public Map loadSlotMap(InputStream inputStream) {
    HashMap hashMap = new HashMap();
    Properties properties = new Properties();
    properties.load(inputStream);
    for (String str : properties.stringPropertyNames()) {
      int parseInt = Integer.parseInt(str);
      hashMap.put(Integer.valueOf(parseInt), properties.getProperty(str));
    }
    return hashMap;
  }

  public final Map loadSlotMap() {
    File slotMapFile = getSlotMapFile();
    if (slotMapFile.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(slotMapFile);
        try {
          Map loadSlotMap = loadSlotMap(fileInputStream);
          fileInputStream.close();
          return loadSlotMap;
        } finally {
        }
      } catch (Exception e) {
        Slog.e("PasswordSlotManager", "Could not load slot map file", e);
      }
    }
    return new HashMap();
  }

  public final void ensureSlotMapLoaded() {
    if (this.mSlotMap == null) {
      this.mSlotMap = loadSlotMap();
      Set set = this.mActiveSlots;
      if (set != null) {
        refreshActiveSlots(set);
        this.mActiveSlots = null;
      }
    }
  }

  public void saveSlotMap(OutputStream outputStream) {
    if (this.mSlotMap == null) {
      return;
    }
    Properties properties = new Properties();
    for (Map.Entry entry : this.mSlotMap.entrySet()) {
      properties.setProperty(((Integer) entry.getKey()).toString(), (String) entry.getValue());
    }
    properties.store(outputStream, "");
  }

  public final void saveSlotMap() {
    if (this.mSlotMap == null) {
      return;
    }
    if (!getSlotMapFile().getParentFile().exists()) {
      Slog.w("PasswordSlotManager", "Not saving slot map, " + getSlotMapDir() + " does not exist");
      return;
    }
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(getSlotMapFile());
      try {
        saveSlotMap(fileOutputStream);
        fileOutputStream.close();
      } finally {
      }
    } catch (IOException e) {
      Slog.e("PasswordSlotManager", "failed to save password slot map", e);
    }
  }
}
