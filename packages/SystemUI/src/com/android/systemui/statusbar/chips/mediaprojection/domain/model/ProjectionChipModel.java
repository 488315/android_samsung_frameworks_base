package com.android.systemui.statusbar.chips.mediaprojection.domain.model;

import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ProjectionChipModel {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ContentType {
        public static final /* synthetic */ ContentType[] $VALUES;
        public static final ContentType Audio;
        public static final ContentType Screen;

        static {
            ContentType contentType = new ContentType("Screen", 0);
            Screen = contentType;
            ContentType contentType2 = new ContentType("Audio", 1);
            Audio = contentType2;
            ContentType[] contentTypeArr = {contentType, contentType2};
            $VALUES = contentTypeArr;
            EnumEntriesKt.enumEntries(contentTypeArr);
        }

        private ContentType(String str, int i) {
        }

        public static ContentType valueOf(String str) {
            return (ContentType) Enum.valueOf(ContentType.class, str);
        }

        public static ContentType[] values() {
            return (ContentType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NotProjecting extends ProjectionChipModel {
        public static final NotProjecting INSTANCE = new NotProjecting();

        private NotProjecting() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NotProjecting);
        }

        public final int hashCode() {
            return 1648514747;
        }

        public final String toString() {
            return "NotProjecting";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Projecting extends ProjectionChipModel {
        public final ContentType contentType;
        public final MediaProjectionState.Projecting projectionState;
        public final Receiver receiver;

        public Projecting(Receiver receiver, ContentType contentType, MediaProjectionState.Projecting projecting) {
            super(null);
            this.receiver = receiver;
            this.contentType = contentType;
            this.projectionState = projecting;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Projecting)) {
                return false;
            }
            Projecting projecting = (Projecting) obj;
            return this.receiver == projecting.receiver && this.contentType == projecting.contentType && Intrinsics.areEqual(this.projectionState, projecting.projectionState);
        }

        public final int hashCode() {
            return this.projectionState.hashCode() + ((this.contentType.hashCode() + (this.receiver.hashCode() * 31)) * 31);
        }

        public final String toString() {
            return "Projecting(receiver=" + this.receiver + ", contentType=" + this.contentType + ", projectionState=" + this.projectionState + ")";
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Receiver {
        public static final /* synthetic */ Receiver[] $VALUES;
        public static final Receiver CastToOtherDevice;
        public static final Receiver ShareToApp;

        static {
            Receiver receiver = new Receiver("ShareToApp", 0);
            ShareToApp = receiver;
            Receiver receiver2 = new Receiver("CastToOtherDevice", 1);
            CastToOtherDevice = receiver2;
            Receiver[] receiverArr = {receiver, receiver2};
            $VALUES = receiverArr;
            EnumEntriesKt.enumEntries(receiverArr);
        }

        private Receiver(String str, int i) {
        }

        public static Receiver valueOf(String str) {
            return (Receiver) Enum.valueOf(Receiver.class, str);
        }

        public static Receiver[] values() {
            return (Receiver[]) $VALUES.clone();
        }
    }

    public /* synthetic */ ProjectionChipModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ProjectionChipModel() {
    }
}
