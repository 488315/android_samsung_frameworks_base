package com.android.server.bgslotmanager;

import android.os.CountDownTimer;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.am.DynamicHiddenApp;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CustomEFKManager {
    public int addBonusEFK;
    public boolean isBlockDecEFK;
    public boolean isStillDecEFK;
    public int last_Watermark_EFK;
    public int origin_EFK;
    public int vDecreaseEFK;
    public int vDecreaseEFKTime;
    public int vWatermarkScaleGetPerTickTime;
    public int vWatermarkScaleGetTime;
    public int v_BonusEFK;
    public int v_bonusEFKWhileBoot;
    public int v_watermark_scale;
    public static final long mTotalMemMb = Process.getTotalMemory() / 1048576;
    public static final boolean APP_EFK_DECREASE_BOOST_ENABLE = BgAppPropManager.getSlmkPropertyBool("dec_EFK_enable", "false");
    public static final boolean BOOTING_EFK_BOOST_ENABLE = BgAppPropManager.getSlmkPropertyBool("bEFKb_enable", "false");
    public static final String[] DECEFK_EXCEPT_PROC_ARRAY = {DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5zYnJvd3Nlcg=="), DynamicHiddenApp.decodeToStr("Y29tLnNlYy5hbmRyb2lkLmFwcC5jYW1lcmE=")};

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.bgslotmanager.CustomEFKManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends CountDownTimer {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ CustomEFKManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CustomEFKManager customEFKManager) {
            super(180000L, 15000L);
            this.$r8$classId = 0;
            this.this$0 = customEFKManager;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass1(CustomEFKManager customEFKManager, long j, long j2, int i) {
            super(j, j2);
            this.$r8$classId = i;
            this.this$0 = customEFKManager;
        }

        private final void onTick$com$android$server$bgslotmanager$CustomEFKManager$2(long j) {
        }

        private final void onTick$com$android$server$bgslotmanager$CustomEFKManager$3(long j) {
        }

        @Override // android.os.CountDownTimer
        public final void onFinish() {
            switch (this.$r8$classId) {
                case 0:
                    Slog.i("DynamicHiddenApp_CustomEFKManager", "setEFKBoostRestoreTimer onfinish");
                    CustomEFKManager.m309$$Nest$mrestoreEFKAfterBoot(this.this$0);
                    break;
                case 1:
                    int i = this.this$0.v_watermark_scale;
                    if (i > 10) {
                        String num = Integer.toString(i);
                        int i2 = BgAppPropManager.TOTAL_MEMORY_2ND;
                        SystemProperties.set("sys.sysctl.watermark_scale_factor", num);
                    }
                    this.this$0.isStillDecEFK = false;
                    break;
                default:
                    CustomEFKManager.m309$$Nest$mrestoreEFKAfterBoot(this.this$0);
                    break;
            }
        }

        @Override // android.os.CountDownTimer
        public final void onTick(long j) {
            switch (this.$r8$classId) {
                case 0:
                    if (SystemClock.uptimeMillis() < 180000) {
                        Slog.i("DynamicHiddenApp_CustomEFKManager", "setEFKBoostRestoreTimer onTick");
                        break;
                    } else {
                        Slog.i("DynamicHiddenApp_CustomEFKManager", "setEFKBoostRestoreTimer onTick after 3minutes");
                        CustomEFKManager.m309$$Nest$mrestoreEFKAfterBoot(this.this$0);
                        cancel();
                        break;
                    }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WatermarkScaleTimer extends CountDownTimer {
        public int count;
        public final int reserve;

        public WatermarkScaleTimer(long j, long j2, int i) {
            super(j, j2);
            this.count = 0;
            this.reserve = i;
        }

        @Override // android.os.CountDownTimer
        public final void onFinish() {
            CustomEFKManager.this.isBlockDecEFK = false;
        }

        @Override // android.os.CountDownTimer
        public final void onTick(long j) {
            int i;
            CustomEFKManager customEFKManager = CustomEFKManager.this;
            int i2 = customEFKManager.v_watermark_scale;
            int i3 = this.count + 1;
            this.count = i3;
            if (i3 > 2 && !customEFKManager.isStillDecEFK) {
                long[] jArr = {0};
                Process.readProcFile("/proc/sys/vm/watermark_scale_factor", new int[]{8224}, null, jArr, null);
                int i4 = (int) jArr[0];
                if (i2 == 0 || (i = CustomEFKManager.this.last_Watermark_EFK) == -1 || ((i2 > i4 && i > this.reserve) || (i2 < i4 && i < this.reserve))) {
                    CustomEFKManager.this.v_watermark_scale = i4;
                    SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("curWatermarkScale : "), CustomEFKManager.this.v_watermark_scale, "DynamicHiddenApp_CustomEFKManager");
                    CustomEFKManager customEFKManager2 = CustomEFKManager.this;
                    customEFKManager2.last_Watermark_EFK = this.reserve;
                    customEFKManager2.isBlockDecEFK = false;
                    cancel();
                }
            }
        }
    }

    /* renamed from: -$$Nest$mrestoreEFKAfterBoot, reason: not valid java name */
    public static void m309$$Nest$mrestoreEFKAfterBoot(CustomEFKManager customEFKManager) {
        int i = customEFKManager.origin_EFK;
        if (i != -1) {
            String num = Integer.toString(i);
            int i2 = BgAppPropManager.TOTAL_MEMORY_2ND;
            SystemProperties.set("sys.sysctl.extra_free_kbytes", num);
        }
    }
}
