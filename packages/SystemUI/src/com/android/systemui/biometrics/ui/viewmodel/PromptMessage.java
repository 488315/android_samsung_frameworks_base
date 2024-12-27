package com.android.systemui.biometrics.ui.viewmodel;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public interface PromptMessage {

    public final class Empty implements PromptMessage {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }
    }

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

        public final int hashCode() {
            return this.errorMessage.hashCode();
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Error(errorMessage="), this.errorMessage, ")");
        }
    }

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

        public final int hashCode() {
            return this.helpMessage.hashCode();
        }

        public final String toString() {
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("Help(helpMessage="), this.helpMessage, ")");
        }
    }
}
