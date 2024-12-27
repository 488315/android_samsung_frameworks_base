package com.android.server.locksettings;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PasswordSlotManager {
    public Set mActiveSlots;
    public Map mSlotMap;

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void ensureSlotMapLoaded() {
        /*
            r3 = this;
            java.util.Map r0 = r3.mSlotMap
            if (r0 != 0) goto L4e
            java.lang.String r0 = r3.getSlotMapDir()
            java.lang.String r1 = "slot_map"
            java.lang.String[] r1 = new java.lang.String[]{r1}
            java.nio.file.Path r0 = java.nio.file.Paths.get(r0, r1)
            java.io.File r0 = r0.toFile()
            boolean r1 = r0.exists()
            if (r1 == 0) goto L3d
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L2a
            r1.<init>(r0)     // Catch: java.lang.Exception -> L2a
            java.util.Map r0 = r3.loadSlotMap(r1)     // Catch: java.lang.Throwable -> L2c
            r1.close()     // Catch: java.lang.Exception -> L2a
            goto L42
        L2a:
            r0 = move-exception
            goto L36
        L2c:
            r0 = move-exception
            r1.close()     // Catch: java.lang.Throwable -> L31
            goto L35
        L31:
            r1 = move-exception
            r0.addSuppressed(r1)     // Catch: java.lang.Exception -> L2a
        L35:
            throw r0     // Catch: java.lang.Exception -> L2a
        L36:
            java.lang.String r1 = "PasswordSlotManager"
            java.lang.String r2 = "Could not load slot map file"
            android.util.Slog.e(r1, r2, r0)
        L3d:
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
        L42:
            r3.mSlotMap = r0
            java.util.Set r0 = r3.mActiveSlots
            if (r0 == 0) goto L4e
            r3.refreshActiveSlots(r0)
            r0 = 0
            r3.mActiveSlots = r0
        L4e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.locksettings.PasswordSlotManager.ensureSlotMapLoaded():void");
    }

    public int getGsiImageNumber() {
        return SystemProperties.getInt("ro.gsid.image_running", 0);
    }

    public final String getMode() {
        int gsiImageNumber = getGsiImageNumber();
        return gsiImageNumber > 0 ? VibrationParam$1$$ExternalSyntheticOutline0.m(gsiImageNumber, "gsi") : "host";
    }

    public String getSlotMapDir() {
        return "/metadata/password_slots";
    }

    public Map loadSlotMap(InputStream inputStream) throws IOException {
        HashMap hashMap = new HashMap();
        Properties properties = new Properties();
        properties.load(inputStream);
        for (String str : properties.stringPropertyNames()) {
            int parseInt = Integer.parseInt(str);
            hashMap.put(Integer.valueOf(parseInt), properties.getProperty(str));
        }
        return hashMap;
    }

    public final void markSlotInUse(int i) {
        ensureSlotMapLoaded();
        if (this.mSlotMap.containsKey(Integer.valueOf(i)) && !((String) this.mSlotMap.get(Integer.valueOf(i))).equals(getMode())) {
            throw new IllegalStateException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "password slot ", " is not available"));
        }
        this.mSlotMap.put(Integer.valueOf(i), getMode());
        saveSlotMap();
    }

    public final void refreshActiveSlots(Set set) {
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

    public final void saveSlotMap() {
        if (this.mSlotMap == null) {
            return;
        }
        if (!Paths.get(getSlotMapDir(), "slot_map").toFile().getParentFile().exists()) {
            Slog.w("PasswordSlotManager", "Not saving slot map, " + getSlotMapDir() + " does not exist");
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(getSlotMapDir(), "slot_map").toFile());
            try {
                saveSlotMap(fileOutputStream);
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e("PasswordSlotManager", "failed to save password slot map", e);
        }
    }

    public void saveSlotMap(OutputStream outputStream) throws IOException {
        if (this.mSlotMap == null) {
            return;
        }
        Properties properties = new Properties();
        for (Map.Entry entry : this.mSlotMap.entrySet()) {
            properties.setProperty(((Integer) entry.getKey()).toString(), (String) entry.getValue());
        }
        properties.store(outputStream, "");
    }
}
