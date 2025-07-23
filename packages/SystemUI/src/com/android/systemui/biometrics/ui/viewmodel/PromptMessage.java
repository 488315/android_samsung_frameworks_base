package com.android.systemui.biometrics.ui.viewmodel;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface PromptMessage {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Empty implements PromptMessage {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Error(errorMessage="), this.errorMessage, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Help(helpMessage="), this.helpMessage, ")");
        }
    }
}
