package kotlin.collections.builders;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.io.Externalizable;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class SerializedMap implements Externalizable {
    private static final long serialVersionUID = 0;
    private Map<?, ?> map;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SerializedMap(Map<?, ?> map) {
        this.map = map;
    }

    private final Object readResolve() {
        return this.map;
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) {
        byte readByte = objectInput.readByte();
        if (readByte != 0) {
            throw new InvalidObjectException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(readByte, "Unsupported flags value: "));
        }
        int readInt = objectInput.readInt();
        if (readInt < 0) {
            throw new InvalidObjectException("Illegal size value: " + readInt + '.');
        }
        MapBuilder mapBuilder = new MapBuilder(readInt);
        for (int i = 0; i < readInt; i++) {
            mapBuilder.put(objectInput.readObject(), objectInput.readObject());
        }
        this.map = mapBuilder.build();
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this.map.size());
        for (Map.Entry<?, ?> entry : this.map.entrySet()) {
            objectOutput.writeObject(entry.getKey());
            objectOutput.writeObject(entry.getValue());
        }
    }

    public SerializedMap() {
        this(MapsKt__MapsKt.emptyMap());
    }
}
