package com.samsung.android.server.continuity.autoswitch;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public abstract class BluetoothDeviceDb {
    public static ArrayList retrieveBackupDevices(Context context) {
        ArrayList arrayList = new ArrayList(5);
        Uri parse = Uri.parse("content://com.samsung.bt.btservice.btsettingsprovider/bonddevice");
        if (context.getContentResolver() == null) {
            return arrayList;
        }
        try {
            Cursor query = context.getContentResolver().query(parse, null, "bond_state == 2 OR bond_state == 1 OR bond_state == 4", null, "timestamp DESC");
            if (query != null) {
                try {
                    query.moveToFirst();
                    while (!query.isAfterLast()) {
                        String string = query.getString(query.getColumnIndex("address"));
                        if (string != null) {
                            arrayList.add(new DeviceProperty(string, query.getString(query.getColumnIndex("name"))));
                            query.moveToNext();
                        }
                    }
                } catch (Throwable th) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            Log.e("[MCF_DS_SYS]_BluetoothDeviceDb", "retrieveBackupDevices - Exception : " + e);
        }
        return arrayList;
    }

    public class DeviceProperty {
        public final String mAddress;
        public final String mName;

        public DeviceProperty(String str, String str2) {
            this.mAddress = str;
            this.mName = str2;
        }

        public String getAddress() {
            return this.mAddress;
        }

        public String getName() {
            return this.mName;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.mAddress.equals(((DeviceProperty) obj).mAddress);
        }

        public int hashCode() {
            return this.mAddress.hashCode();
        }
    }
}
