package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.plugins.qs.QSTile;
import java.util.function.Supplier;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface IconProvider {

    public final class ConstantIcon implements IconProvider {
        public final QSTile.Icon icon;

        public ConstantIcon(QSTile.Icon icon) {
            this.icon = icon;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ConstantIcon) && Intrinsics.areEqual(this.icon, ((ConstantIcon) obj).icon);
        }

        @Override // com.android.systemui.qs.panels.ui.viewmodel.IconProvider
        public final QSTile.Icon getIcon() {
            return this.icon;
        }

        public final int hashCode() {
            return this.icon.hashCode();
        }

        public final String toString() {
            return "ConstantIcon(icon=" + this.icon + ")";
        }
    }

    public final class Empty implements IconProvider {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Empty);
        }

        @Override // com.android.systemui.qs.panels.ui.viewmodel.IconProvider
        public final QSTile.Icon getIcon() {
            return null;
        }

        public final int hashCode() {
            return 501808411;
        }

        public final String toString() {
            return "Empty";
        }
    }

    public final class IconSupplier implements IconProvider {
        public final Supplier supplier;

        public IconSupplier(Supplier<QSTile.Icon> supplier) {
            this.supplier = supplier;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof IconSupplier) && Intrinsics.areEqual(this.supplier, ((IconSupplier) obj).supplier);
        }

        @Override // com.android.systemui.qs.panels.ui.viewmodel.IconProvider
        public final QSTile.Icon getIcon() {
            return (QSTile.Icon) this.supplier.get();
        }

        public final int hashCode() {
            return this.supplier.hashCode();
        }

        public final String toString() {
            return "IconSupplier(supplier=" + this.supplier + ")";
        }
    }

    QSTile.Icon getIcon();
}
