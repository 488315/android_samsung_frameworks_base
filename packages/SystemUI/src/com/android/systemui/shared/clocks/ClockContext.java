package com.android.systemui.shared.clocks;

import android.content.Context;
import android.content.res.Resources;
import android.os.Vibrator;
import com.android.systemui.log.core.MessageBuffer;
import com.android.systemui.plugins.clocks.ClockMessageBuffers;
import com.android.systemui.plugins.clocks.ClockSettings;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ClockContext {
    public final Context context;
    public final MessageBuffer messageBuffer;
    public final ClockMessageBuffers messageBuffers;
    public final Resources resources;
    public final ClockSettings settings;
    public final TypefaceCache typefaceCache;
    public final Vibrator vibrator;

    public ClockContext(Context context, Resources resources, ClockSettings clockSettings, TypefaceCache typefaceCache, ClockMessageBuffers clockMessageBuffers, MessageBuffer messageBuffer, Vibrator vibrator) {
        this.context = context;
        this.resources = resources;
        this.settings = clockSettings;
        this.typefaceCache = typefaceCache;
        this.messageBuffers = clockMessageBuffers;
        this.messageBuffer = messageBuffer;
        this.vibrator = vibrator;
    }

    public static ClockContext copy$default(ClockContext clockContext, MessageBuffer messageBuffer) {
        Context context = clockContext.context;
        Resources resources = clockContext.resources;
        ClockSettings clockSettings = clockContext.settings;
        TypefaceCache typefaceCache = clockContext.typefaceCache;
        ClockMessageBuffers clockMessageBuffers = clockContext.messageBuffers;
        Vibrator vibrator = clockContext.vibrator;
        clockContext.getClass();
        return new ClockContext(context, resources, clockSettings, typefaceCache, clockMessageBuffers, messageBuffer, vibrator);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockContext)) {
            return false;
        }
        ClockContext clockContext = (ClockContext) obj;
        return Intrinsics.areEqual(this.context, clockContext.context) && Intrinsics.areEqual(this.resources, clockContext.resources) && Intrinsics.areEqual(this.settings, clockContext.settings) && Intrinsics.areEqual(this.typefaceCache, clockContext.typefaceCache) && Intrinsics.areEqual(this.messageBuffers, clockContext.messageBuffers) && Intrinsics.areEqual(this.messageBuffer, clockContext.messageBuffer) && Intrinsics.areEqual(this.vibrator, clockContext.vibrator);
    }

    public final int hashCode() {
        int hashCode = (this.messageBuffer.hashCode() + ((this.messageBuffers.hashCode() + ((this.typefaceCache.hashCode() + ((this.settings.hashCode() + ((this.resources.hashCode() + (this.context.hashCode() * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        Vibrator vibrator = this.vibrator;
        return hashCode + (vibrator == null ? 0 : vibrator.hashCode());
    }

    public final String toString() {
        return "ClockContext(context=" + this.context + ", resources=" + this.resources + ", settings=" + this.settings + ", typefaceCache=" + this.typefaceCache + ", messageBuffers=" + this.messageBuffers + ", messageBuffer=" + this.messageBuffer + ", vibrator=" + this.vibrator + ")";
    }
}
