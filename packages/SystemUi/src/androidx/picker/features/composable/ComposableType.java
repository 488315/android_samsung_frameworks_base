package androidx.picker.features.composable;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ComposableType {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public static boolean isSame(ComposableType composableType, ComposableTypeSet composableTypeSet) {
            if (composableTypeSet == composableType) {
                return true;
            }
            return Intrinsics.areEqual(composableType.getLeftFrame(), composableTypeSet.getLeftFrame()) && Intrinsics.areEqual(composableType.getIconFrame(), composableTypeSet.getIconFrame()) && Intrinsics.areEqual(composableType.getTitleFrame(), composableTypeSet.getTitleFrame()) && Intrinsics.areEqual(composableType.getWidgetFrame(), composableTypeSet.getWidgetFrame());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ComposableTypeImpl implements ComposableType {
        public final ComposableFrame iconFrame;
        public final ComposableFrame leftFrame;
        public final ComposableFrame titleFrame;
        public final ComposableFrame widgetFrame;

        public ComposableTypeImpl(ComposableFrame composableFrame, ComposableFrame composableFrame2, ComposableFrame composableFrame3, ComposableFrame composableFrame4) {
            this.leftFrame = composableFrame;
            this.iconFrame = composableFrame2;
            this.titleFrame = composableFrame3;
            this.widgetFrame = composableFrame4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ComposableTypeImpl)) {
                return false;
            }
            ComposableTypeImpl composableTypeImpl = (ComposableTypeImpl) obj;
            return Intrinsics.areEqual(this.leftFrame, composableTypeImpl.leftFrame) && Intrinsics.areEqual(this.iconFrame, composableTypeImpl.iconFrame) && Intrinsics.areEqual(this.titleFrame, composableTypeImpl.titleFrame) && Intrinsics.areEqual(this.widgetFrame, composableTypeImpl.widgetFrame);
        }

        @Override // androidx.picker.features.composable.ComposableType
        public final ComposableFrame getIconFrame() {
            return this.iconFrame;
        }

        @Override // androidx.picker.features.composable.ComposableType
        public final ComposableFrame getLeftFrame() {
            return this.leftFrame;
        }

        @Override // androidx.picker.features.composable.ComposableType
        public final ComposableFrame getTitleFrame() {
            return this.titleFrame;
        }

        @Override // androidx.picker.features.composable.ComposableType
        public final ComposableFrame getWidgetFrame() {
            return this.widgetFrame;
        }

        public final int hashCode() {
            ComposableFrame composableFrame = this.leftFrame;
            int hashCode = (composableFrame == null ? 0 : composableFrame.hashCode()) * 31;
            ComposableFrame composableFrame2 = this.iconFrame;
            int hashCode2 = (hashCode + (composableFrame2 == null ? 0 : composableFrame2.hashCode())) * 31;
            ComposableFrame composableFrame3 = this.titleFrame;
            int hashCode3 = (hashCode2 + (composableFrame3 == null ? 0 : composableFrame3.hashCode())) * 31;
            ComposableFrame composableFrame4 = this.widgetFrame;
            return hashCode3 + (composableFrame4 != null ? composableFrame4.hashCode() : 0);
        }

        public final String toString() {
            return "ComposableTypeImpl(leftFrame=" + this.leftFrame + ", iconFrame=" + this.iconFrame + ", titleFrame=" + this.titleFrame + ", widgetFrame=" + this.widgetFrame + ')';
        }
    }

    ComposableFrame getIconFrame();

    ComposableFrame getLeftFrame();

    ComposableFrame getTitleFrame();

    ComposableFrame getWidgetFrame();
}
