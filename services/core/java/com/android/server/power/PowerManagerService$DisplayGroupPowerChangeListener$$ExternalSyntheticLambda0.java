package com.android.server.power;


public final /* synthetic */
class PowerManagerService$DisplayGroupPowerChangeListener$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PowerManagerService.DisplayGroupPowerChangeListener f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */
    PowerManagerService$DisplayGroupPowerChangeListener$$ExternalSyntheticLambda0(
            PowerManagerService.DisplayGroupPowerChangeListener displayGroupPowerChangeListener,
            int i,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = displayGroupPowerChangeListener;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PowerManagerService.DisplayGroupPowerChangeListener
                        displayGroupPowerChangeListener = this.f$0;
                int i = this.f$1;
                PowerHistorian powerHistorian =
                        displayGroupPowerChangeListener.this$0.mPowerHistorian;
                powerHistorian.getClass();
                powerHistorian.addRecord(4, new PowerHistorian.DisplayGroupRecord(i, false));
                break;
            default:
                PowerManagerService.DisplayGroupPowerChangeListener
                        displayGroupPowerChangeListener2 = this.f$0;
                int i2 = this.f$1;
                PowerHistorian powerHistorian2 =
                        displayGroupPowerChangeListener2.this$0.mPowerHistorian;
                powerHistorian2.getClass();
                powerHistorian2.addRecord(4, new PowerHistorian.DisplayGroupRecord(i2, true));
                break;
        }
    }
}
