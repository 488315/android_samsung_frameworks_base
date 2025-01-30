package com.android.systemui.biometrics.ui.viewmodel;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface PromptMessage {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DefaultImpls {
        public static String getMessage(PromptMessage promptMessage) {
            return promptMessage instanceof Error ? ((Error) promptMessage).errorMessage : promptMessage instanceof Help ? ((Help) promptMessage).helpMessage : "";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Empty implements PromptMessage {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        @Override // com.android.systemui.biometrics.ui.viewmodel.PromptMessage
        public final String getMessage() {
            return DefaultImpls.getMessage(this);
        }

        @Override // com.android.systemui.biometrics.ui.viewmodel.PromptMessage
        public final boolean isErrorOrHelp() {
            return (this instanceof Error) || (this instanceof Help);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Error implements PromptMessage {
        public final String errorMessage;

        public Error(String str) {
            this.errorMessage = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Error) && Intrinsics.areEqual(this.errorMessage, ((Error) obj).errorMessage);
        }

        @Override // com.android.systemui.biometrics.ui.viewmodel.PromptMessage
        public final String getMessage() {
            return DefaultImpls.getMessage(this);
        }

        public final int hashCode() {
            return this.errorMessage.hashCode();
        }

        @Override // com.android.systemui.biometrics.ui.viewmodel.PromptMessage
        public final boolean isErrorOrHelp() {
            return true;
        }

        public final String toString() {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("Error(errorMessage="), this.errorMessage, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Help implements PromptMessage {
        public final String helpMessage;

        public Help(String str) {
            this.helpMessage = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Help) && Intrinsics.areEqual(this.helpMessage, ((Help) obj).helpMessage);
        }

        @Override // com.android.systemui.biometrics.ui.viewmodel.PromptMessage
        public final String getMessage() {
            return DefaultImpls.getMessage(this);
        }

        public final int hashCode() {
            return this.helpMessage.hashCode();
        }

        @Override // com.android.systemui.biometrics.ui.viewmodel.PromptMessage
        public final boolean isErrorOrHelp() {
            boolean z = this instanceof Error;
            return true;
        }

        public final String toString() {
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("Help(helpMessage="), this.helpMessage, ")");
        }
    }

    String getMessage();

    boolean isErrorOrHelp();
}
