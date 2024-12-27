package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.graphics.Rect;

import java.io.PrintWriter;

@SystemApi
public abstract class PresentationParams {
    abstract void dump(String str, PrintWriter printWriter);

    PresentationParams() {}

    public Area getSuggestionArea() {
        return null;
    }

    @SystemApi
    public abstract static class Area {
        private final Rect mBounds;
        public final AugmentedAutofillService.AutofillProxy proxy;

        private Area(AugmentedAutofillService.AutofillProxy proxy, Rect bounds) {
            this.proxy = proxy;
            this.mBounds = bounds;
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

        public SystemPopupPresentationParams(
                AugmentedAutofillService.AutofillProxy proxy, Rect rect) {
            this.mSuggestionArea =
                    new Area(
                            proxy,
                            rect) { // from class:
                                    // android.service.autofill.augmented.PresentationParams.SystemPopupPresentationParams.1
                    };
        }

        @Override // android.service.autofill.augmented.PresentationParams
        public Area getSuggestionArea() {
            return this.mSuggestionArea;
        }

        @Override // android.service.autofill.augmented.PresentationParams
        void dump(String prefix, PrintWriter pw) {
            pw.print(prefix);
            pw.print("area: ");
            pw.println(this.mSuggestionArea);
        }
    }
}
