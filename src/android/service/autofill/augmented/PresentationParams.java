package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.graphics.Rect;
import android.service.autofill.augmented.AugmentedAutofillService;
import java.io.PrintWriter;

@SystemApi
/* loaded from: classes3.dex */
public abstract class PresentationParams {
    abstract void dump(String str, PrintWriter printWriter);

    public Area getSuggestionArea() {
        return null;
    }

    PresentationParams() {
    }

    @SystemApi
    public static abstract class Area {
        private final Rect mBounds;
        public final AugmentedAutofillService.AutofillProxy proxy;

        private Area(AugmentedAutofillService.AutofillProxy autofillProxy, Rect rect) {
            this.proxy = autofillProxy;
            this.mBounds = rect;
        }

        public Rect getBounds() {
            return this.mBounds;
        }

        public String toString() {
            return this.mBounds.toString();
        }
    }

    public static final class SystemPopupPresentationParams extends PresentationParams {
        private final Area mSuggestionArea;

        public SystemPopupPresentationParams(AugmentedAutofillService.AutofillProxy autofillProxy, Rect rect) {
            this.mSuggestionArea = new Area(this, autofillProxy, rect) { // from class: android.service.autofill.augmented.PresentationParams.SystemPopupPresentationParams.1
            };
        }

        @Override // android.service.autofill.augmented.PresentationParams
        public Area getSuggestionArea() {
            return this.mSuggestionArea;
        }

        @Override // android.service.autofill.augmented.PresentationParams
        void dump(String str, PrintWriter printWriter) {
            printWriter.print(str);
            printWriter.print("area: ");
            printWriter.println(this.mSuggestionArea);
        }
    }
}
