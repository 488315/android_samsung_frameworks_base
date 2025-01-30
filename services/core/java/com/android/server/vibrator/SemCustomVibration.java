package com.android.server.vibrator;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.CombinedVibration;
import android.os.VibrationEffect;
import android.os.vibrator.SemHapticSegment;

/* loaded from: classes3.dex */
public class SemCustomVibration extends SemVibration {
    public final Context mContext;
    public final SemHapticSegment mSemHapticSegment;
    public final VibrationSettings mSettings;

    public SemCustomVibration(Context context, SemVibrationBundle semVibrationBundle, SemHapticSegment semHapticSegment, VibrationSettings vibrationSettings) {
        super(semVibrationBundle);
        this.mContext = context;
        this.mSemHapticSegment = semHapticSegment;
        this.mSettings = vibrationSettings;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0070 A[Catch: all -> 0x0080, Exception -> 0x0082, TRY_ENTER, TRY_LEAVE, TryCatch #3 {Exception -> 0x0082, blocks: (B:7:0x0012, B:25:0x004a, B:11:0x0070, B:31:0x007f, B:36:0x007c), top: B:6:0x0012, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x008b  */
    @Override // com.android.server.vibrator.SemVibration
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public HalVibration getVibration() {
        Cursor query;
        VibrationEffect vibrationEffect = null;
        if (!commonValidation()) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                query = this.mContext.getContentResolver().query(Uri.parse(this.mSemHapticSegment.getCategoryPath()), null, "vibration_pattern=?", new String[]{Integer.toString(this.mIndex)}, null);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (query != null) {
            try {
                if (query.moveToFirst()) {
                    int columnIndex = query.getColumnIndex("custom_data");
                    if (columnIndex >= 0) {
                        String string = query.getString(columnIndex);
                        if (string == null) {
                            query.close();
                            query.close();
                            return null;
                        }
                        vibrationEffect = VibrationEffect.createWaveform(getCustomPatternData(string), getCustomAmplitudeData(string, this.mMagnitude), this.mRepeat);
                    }
                    if (query != null) {
                        query.close();
                    }
                    if (vibrationEffect != null) {
                        vibrationEffect.semSetMagnitude(this.mMagnitude);
                    }
                    return new HalVibration(this.mToken, CombinedVibration.createParallel(vibrationEffect), -1L, null, this.mMagnitude, this.mVibratorHelper.getPatternFrequencyByIndex(this.mIndex), null, null, getCallerInfo());
                }
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        vibrationEffect = VibrationEffect.semCreateHaptic(this.mSemHapticSegment.getDefaultSepIndex(), this.mRepeat);
        if (query != null) {
        }
        if (vibrationEffect != null) {
        }
        return new HalVibration(this.mToken, CombinedVibration.createParallel(vibrationEffect), -1L, null, this.mMagnitude, this.mVibratorHelper.getPatternFrequencyByIndex(this.mIndex), null, null, getCallerInfo());
    }

    public final long[] getCustomPatternData(String str) {
        String[] split = str.split("#");
        String[] split2 = split[0].split(" ");
        long[] jArr = new long[split2.length];
        for (int i = 0; i < split2.length; i++) {
            try {
                jArr[i] = Long.parseLong(split2[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return jArr;
    }

    public final int[] getCustomAmplitudeData(String str, int i) {
        String[] split = str.split("#")[1].split(" ");
        int[] iArr = new int[split.length];
        double maxMagnitude = this.mSettings.getMaxMagnitude() / i;
        for (int i2 = 0; i2 < split.length; i2++) {
            try {
                iArr[i2] = (int) (Integer.parseInt(split[i2]) / maxMagnitude);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return iArr;
    }

    public String toString() {
        return "SemCustomVibration : " + getCommonLog();
    }
}
