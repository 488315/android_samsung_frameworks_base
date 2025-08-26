package com.android.systemui.complication;

import android.text.format.DateFormat;
import android.widget.TextClock;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.complication.dagger.DreamClockTimeComplicationComponent;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda1;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.condition.ConditionalCoreStartable;

/* loaded from: classes2.dex */
public class DreamClockTimeComplication implements Complication {
    public final DreamClockTimeComplicationComponent.Factory mComponentFactory;

    public class DreamClockTimeViewHolder {
        public final ComplicationLayoutParams mLayoutParams;
        public final TextClock mView;

        public DreamClockTimeViewHolder(TextClock textClock, ComplicationLayoutParams complicationLayoutParams, DreamClockTimeViewController dreamClockTimeViewController) {
            this.mView = textClock;
            this.mLayoutParams = complicationLayoutParams;
            dreamClockTimeViewController.init();
            String bestDateTimePattern = DateFormat.getBestDateTimePattern(textClock.getTextLocale(), textClock.is24HourModeEnabled() ? "Hm" : "hm");
            textClock.setContentDescriptionFormat12Hour(bestDateTimePattern);
            textClock.setContentDescriptionFormat24Hour(bestDateTimePattern);
        }
    }

    public class Registrant extends ConditionalCoreStartable {
        public final DreamClockTimeComplication mComplication;
        public final DreamOverlayStateController mDreamOverlayStateController;

        public Registrant(DreamOverlayStateController dreamOverlayStateController, DreamClockTimeComplication dreamClockTimeComplication, Monitor monitor) {
            super(monitor);
            this.mDreamOverlayStateController = dreamOverlayStateController;
            this.mComplication = dreamClockTimeComplication;
        }

        @Override // com.android.systemui.util.condition.ConditionalCoreStartable
        public final void onStart() {
            DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
            dreamOverlayStateController.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda1(dreamOverlayStateController, this.mComplication, 0));
        }
    }

    public DreamClockTimeComplication(DreamClockTimeComplicationComponent.Factory factory) {
        this.mComponentFactory = factory;
    }

    @Override // com.android.systemui.complication.Complication
    public final DreamClockTimeViewHolder createView() {
        return ((DaggerReferenceGlobalRootComponent.DreamClockTimeComplicationComponentImpl) this.mComponentFactory.create()).getViewHolder();
    }

    @Override // com.android.systemui.complication.Complication
    public final int getRequiredTypeAvailability() {
        return 1;
    }

    public class DreamClockTimeViewController extends ViewController {
        public DreamClockTimeViewController(TextClock textClock, UiEventLogger uiEventLogger) {
            super(textClock);
        }

        @Override // com.android.systemui.util.ViewController
        public final void onViewAttached() {
        }

        @Override // com.android.systemui.util.ViewController
        public final void onViewDetached() {
        }
    }
}
