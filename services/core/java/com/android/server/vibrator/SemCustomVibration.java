package com.android.server.vibrator;

import android.content.Context;
import android.os.vibrator.SemHapticSegment;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemCustomVibration extends SemVibration {
    public final Context mContext;
    public final SemHapticSegment mSemHapticSegment;
    public final VibrationSettings mSettings;

    public SemCustomVibration(
            Context context,
            SemVibrationBundle semVibrationBundle,
            SemHapticSegment semHapticSegment,
            VibrationSettings vibrationSettings) {
        super(semVibrationBundle);
        this.mContext = context;
        this.mSemHapticSegment = semHapticSegment;
        this.mSettings = vibrationSettings;
    }

    public final int[] getCustomAmplitudeData(int i, String str) {
        String[] split = str.split("#")[1].split(" ");
        int[] iArr = new int[split.length];
        double d = this.mSettings.mCustomSettings.mMaxMagnitude / i;
        for (int i2 = 0; i2 < split.length; i2++) {
            try {
                iArr[i2] = (int) (Integer.parseInt(split[i2]) / d);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return iArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0093 A[Catch: all -> 0x0053, Exception -> 0x0056, TRY_ENTER, TRY_LEAVE, TryCatch #3 {Exception -> 0x0056, blocks: (B:7:0x0016, B:26:0x004c, B:12:0x0093, B:42:0x00a4, B:47:0x00a1), top: B:6:0x0016, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00ab  */
    @Override // com.android.server.vibrator.SemVibration
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.vibrator.HalVibration getVibration() {
        /*
            r13 = this;
            int r0 = r13.mMagnitude
            int r1 = r13.mIndex
            boolean r2 = r13.commonValidation()
            r3 = 0
            if (r2 != 0) goto Lc
            return r3
        Lc:
            long r4 = android.os.Binder.clearCallingIdentity()
            android.content.Context r2 = r13.mContext
            android.content.ContentResolver r6 = r2.getContentResolver()
            android.os.vibrator.SemHapticSegment r2 = r13.mSemHapticSegment     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
            java.lang.String r2 = r2.getCategoryPath()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
            android.net.Uri r7 = android.net.Uri.parse(r2)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
            java.lang.String r9 = "vibration_pattern=?"
            java.lang.String r2 = java.lang.Integer.toString(r1)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
            java.lang.String[] r10 = new java.lang.String[]{r2}     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
            r11 = 0
            r8 = 0
            android.database.Cursor r2 = r6.query(r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
            int r6 = r13.mRepeat
            if (r2 == 0) goto L87
            boolean r7 = r2.moveToFirst()     // Catch: java.lang.Throwable -> L58
            if (r7 == 0) goto L87
            java.lang.String r7 = "custom_data"
            int r7 = r2.getColumnIndex(r7)     // Catch: java.lang.Throwable -> L58
            if (r7 < 0) goto L91
            java.lang.String r7 = r2.getString(r7)     // Catch: java.lang.Throwable -> L58
            if (r7 != 0) goto L5a
            r2.close()     // Catch: java.lang.Throwable -> L58
            r2.close()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
            android.os.Binder.restoreCallingIdentity(r4)
            return r3
        L53:
            r13 = move-exception
            goto Lca
        L56:
            r2 = move-exception
            goto La5
        L58:
            r6 = move-exception
            goto L9a
        L5a:
            java.lang.String r8 = "#"
            java.lang.String[] r8 = r7.split(r8)     // Catch: java.lang.Throwable -> L58
            r9 = 0
            r8 = r8[r9]     // Catch: java.lang.Throwable -> L58
            java.lang.String r10 = " "
            java.lang.String[] r8 = r8.split(r10)     // Catch: java.lang.Throwable -> L58
            int r10 = r8.length     // Catch: java.lang.Throwable -> L58
            long[] r10 = new long[r10]     // Catch: java.lang.Throwable -> L58
        L6c:
            int r11 = r8.length     // Catch: java.lang.Throwable -> L58 java.lang.NumberFormatException -> L7a
            if (r9 >= r11) goto L7e
            r11 = r8[r9]     // Catch: java.lang.Throwable -> L58 java.lang.NumberFormatException -> L7a
            long r11 = java.lang.Long.parseLong(r11)     // Catch: java.lang.Throwable -> L58 java.lang.NumberFormatException -> L7a
            r10[r9] = r11     // Catch: java.lang.Throwable -> L58 java.lang.NumberFormatException -> L7a
            int r9 = r9 + 1
            goto L6c
        L7a:
            r8 = move-exception
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L58
        L7e:
            int[] r7 = r13.getCustomAmplitudeData(r0, r7)     // Catch: java.lang.Throwable -> L58
            android.os.VibrationEffect r3 = android.os.VibrationEffect.createWaveform(r10, r7, r6)     // Catch: java.lang.Throwable -> L58
            goto L91
        L87:
            android.os.vibrator.SemHapticSegment r7 = r13.mSemHapticSegment     // Catch: java.lang.Throwable -> L58
            int r7 = r7.getDefaultSepIndex()     // Catch: java.lang.Throwable -> L58
            android.os.VibrationEffect r3 = android.os.VibrationEffect.semCreateHaptic(r7, r6)     // Catch: java.lang.Throwable -> L58
        L91:
            if (r2 == 0) goto L96
            r2.close()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
        L96:
            android.os.Binder.restoreCallingIdentity(r4)
            goto La9
        L9a:
            if (r2 == 0) goto La4
            r2.close()     // Catch: java.lang.Throwable -> La0
            goto La4
        La0:
            r2 = move-exception
            r6.addSuppressed(r2)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
        La4:
            throw r6     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L56
        La5:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L53
            goto L96
        La9:
            if (r3 == 0) goto Lae
            r3.semSetMagnitude(r0)
        Lae:
            android.os.CombinedVibration r6 = android.os.CombinedVibration.createParallel(r3)
            com.android.server.vibrator.HalVibration r0 = new com.android.server.vibrator.HalVibration
            android.os.IBinder r5 = r13.mToken
            com.samsung.android.server.vibrator.VibratorHelper r2 = r13.mVibratorHelper
            r2.getPatternFrequencyByIndex(r1)
            com.android.server.vibrator.Vibration$CallerInfo r12 = r13.getCallerInfo()
            int r9 = r13.mMagnitude
            r10 = 0
            r11 = 0
            r7 = -1
            r4 = r0
            r4.<init>(r5, r6, r7, r9, r10, r11, r12)
            return r0
        Lca:
            android.os.Binder.restoreCallingIdentity(r4)
            throw r13
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.vibrator.SemCustomVibration.getVibration():com.android.server.vibrator.HalVibration");
    }

    public final String toString() {
        return "SemCustomVibration : " + getCommonLog();
    }
}
