package com.android.systemui.statusbar.pipeline.mobile.data.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.Intrinsics;

public interface NetworkNameModel extends Diffable {

    public final class Default implements NetworkNameModel {
        public final String name;

        public Default(String str) {
            this.name = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Default) && Intrinsics.areEqual(this.name, ((Default) obj).name);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.name.hashCode();
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            NetworkNameModel networkNameModel = (NetworkNameModel) obj;
            boolean z = networkNameModel instanceof Default;
            String str = this.name;
            if (z && Intrinsics.areEqual(((Default) networkNameModel).getName(), str)) {
                return;
            }
            tableRowLoggerImpl.logChange("networkName", "Default(" + str + ")");
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("networkName", "Default(" + this.name + ")");
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Default(name="), this.name, ")");
        }
    }

    public final class IntentDerived implements NetworkNameModel {
        public final String name;

        public IntentDerived(String str) {
            this.name = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof IntentDerived) && Intrinsics.areEqual(this.name, ((IntentDerived) obj).name);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.name.hashCode();
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            NetworkNameModel networkNameModel = (NetworkNameModel) obj;
            boolean z = networkNameModel instanceof IntentDerived;
            String str = this.name;
            if (z && Intrinsics.areEqual(((IntentDerived) networkNameModel).getName(), str)) {
                return;
            }
            tableRowLoggerImpl.logChange("networkName", "IntentDerived(" + str + ")");
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("networkName", "IntentDerived(" + this.name + ")");
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("IntentDerived(name="), this.name, ")");
        }
    }

    public final class SimDerived implements NetworkNameModel {
        public final String name;

        public SimDerived(String str) {
            this.name = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SimDerived) && Intrinsics.areEqual(this.name, ((SimDerived) obj).name);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.name.hashCode();
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            NetworkNameModel networkNameModel = (NetworkNameModel) obj;
            boolean z = networkNameModel instanceof SimDerived;
            String str = this.name;
            if (z && Intrinsics.areEqual(((SimDerived) networkNameModel).getName(), str)) {
                return;
            }
            tableRowLoggerImpl.logChange("networkName", "SimDerived(" + str + ")");
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("networkName", "SimDerived(" + this.name + ")");
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("SimDerived(name="), this.name, ")");
        }
    }

    public final class SubscriptionDerived implements NetworkNameModel {
        public final String name;

        public SubscriptionDerived(String str) {
            this.name = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof SubscriptionDerived) && Intrinsics.areEqual(this.name, ((SubscriptionDerived) obj).name);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            return this.name.hashCode();
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            NetworkNameModel networkNameModel = (NetworkNameModel) obj;
            boolean z = networkNameModel instanceof SubscriptionDerived;
            String str = this.name;
            if (z && Intrinsics.areEqual(((SubscriptionDerived) networkNameModel).getName(), str)) {
                return;
            }
            tableRowLoggerImpl.logChange("networkName", "SubscriptionDerived(" + str + ")");
        }

        @Override // com.android.systemui.log.table.Diffable
        public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
            tableRowLoggerImpl.logChange("networkName", "SubscriptionDerived(" + this.name + ")");
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("SubscriptionDerived(name="), this.name, ")");
        }
    }

    String getName();
}
