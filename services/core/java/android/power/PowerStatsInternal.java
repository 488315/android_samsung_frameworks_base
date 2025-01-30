package android.power;

import android.hardware.power.stats.Channel;
import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.PowerEntity;
import java.util.concurrent.CompletableFuture;

/* loaded from: classes.dex */
public abstract class PowerStatsInternal {
    public abstract CompletableFuture getEnergyConsumedAsync(int[] iArr);

    public abstract EnergyConsumer[] getEnergyConsumerInfo();

    public abstract Channel[] getEnergyMeterInfo();

    public abstract PowerEntity[] getPowerEntityInfo();

    public abstract CompletableFuture getStateResidencyAsync(int[] iArr);

    public abstract CompletableFuture readEnergyMeterAsync(int[] iArr);
}
