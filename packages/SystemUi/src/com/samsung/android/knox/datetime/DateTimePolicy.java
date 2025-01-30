package com.samsung.android.knox.datetime;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.datetime.IDateTimePolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.Calendar;
import java.util.Date;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DateTimePolicy {
    public static final String ACTION_EVENT_NTP_SERVER_UNREACHABLE = "com.samsung.android.knox.intent.action.EVENT_NTP_SERVER_UNREACHABLE";
    public static final String ACTION_UPDATE_NTP_PARAMETERS_INTERNAL = "com.samsung.android.knox.intent.action.UPDATE_NTP_PARAMETERS_INTERNAL";
    public static String TAG = "DateTimePolicy";
    public ContextInfo mContextInfo;
    public IDateTimePolicy mService;

    public DateTimePolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean getAutomaticTime() {
        try {
            if (getService() != null) {
                return this.mService.getAutomaticTime(this.mContextInfo);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API getAutomaticTime", e);
            return false;
        }
    }

    public final String getDateFormat() {
        try {
            if (getService() != null) {
                return this.mService.getDateFormat(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API getDateFormat", e);
            return null;
        }
    }

    public final Date getDateTime() {
        try {
            if (getService() == null) {
                return null;
            }
            long dateTime = this.mService.getDateTime(this.mContextInfo);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateTime);
            return calendar.getTime();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API getDateTime", e);
            return null;
        }
    }

    public final boolean getDaylightSavingTime() {
        try {
            if (getService() != null) {
                return this.mService.getDaylightSavingTime(this.mContextInfo);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API getDaylightSavingTime", e);
            return false;
        }
    }

    public final NtpInfo getNtpInfo() {
        try {
            if (getService() == null) {
                return null;
            }
            if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 14) {
                return this.mService.getNtpInfo(this.mContextInfo);
            }
            Log.i(TAG, "setNtpInfo() : This device doesn't support this API.");
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API getNtpInfo", e);
            return null;
        }
    }

    public final IDateTimePolicy getService() {
        if (this.mService == null) {
            this.mService = IDateTimePolicy.Stub.asInterface(ServiceManager.getService("date_time_policy"));
        }
        return this.mService;
    }

    public final String getTimeFormat() {
        try {
            if (getService() != null) {
                return this.mService.getTimeFormat(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API getTimeFormat", e);
            return null;
        }
    }

    public final String getTimeZone() {
        try {
            if (getService() != null) {
                return this.mService.getTimeZone(this.mContextInfo);
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API getTimeZone", e);
            return null;
        }
    }

    public final boolean isDateTimeChangeEnabled() {
        try {
            if (getService() != null) {
                return this.mService.isDateTimeChangeEnabled(this.mContextInfo);
            }
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API setTimeFormat", e);
            return true;
        }
    }

    public final boolean setAutomaticTime(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DateTimePolicy.setAutomaticTime");
        try {
            if (getService() != null) {
                return this.mService.setAutomaticTime(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API setAutomaticTime", e);
            return false;
        }
    }

    public final boolean setDateTime(int i, int i2, int i3, int i4, int i5, int i6) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DateTimePolicy.setDateTime");
        try {
            if (getService() == null) {
                return false;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.set(5, i);
            calendar.set(2, i2);
            calendar.set(1, i3);
            calendar.set(11, i4);
            calendar.set(12, i5);
            calendar.set(13, i6);
            return this.mService.setDateTime(this.mContextInfo, calendar.getTimeInMillis());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API setDateTime", e);
            return false;
        }
    }

    public final boolean setDateTimeChangeEnabled(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DateTimePolicy.setDateTimeChangeEnabled");
        try {
            if (getService() != null) {
                return this.mService.setDateTimeChangeEnabled(this.mContextInfo, z);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API setTimeFormat", e);
            return false;
        }
    }

    public final boolean setNtpInfo(NtpInfo ntpInfo) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DateTimePolicy.setNtpInfo");
        try {
            if (getService() == null) {
                return false;
            }
            if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 14) {
                return this.mService.setNtpInfo(this.mContextInfo, ntpInfo);
            }
            Log.i(TAG, "setNtpInfo() : This device doesn't support this API.");
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API setNtpInfo", e);
            return false;
        }
    }

    public final boolean setTimeFormat(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DateTimePolicy.setTimeFormat");
        try {
            if (getService() != null) {
                return this.mService.setTimeFormat(this.mContextInfo, str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API setTimeFormat", e);
            return false;
        }
    }

    public final boolean setTimeZone(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DateTimePolicy.setTimeZone");
        try {
            if (getService() != null) {
                return this.mService.setTimeZone(this.mContextInfo, str);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed at DateTime policy API setTimeZone", e);
            return false;
        }
    }
}
