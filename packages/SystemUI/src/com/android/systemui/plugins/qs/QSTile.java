package com.android.systemui.plugins.qs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.text.TextUtils;
import com.android.internal.logging.InstanceId;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Dependencies({@DependsOn(target = QSIconView.class), @DependsOn(target = Callback.class), @DependsOn(target = Icon.class), @DependsOn(target = State.class)})
@ProvidesInterface(version = 5)
/* loaded from: classes2.dex */
public interface QSTile {
    public static final int VERSION = 5;

    @ProvidesInterface(version = 1)
    public class AdapterState extends State {
        public static final int VERSION = 1;
        public boolean forceExpandIcon;
        public boolean value;

        @Override // com.android.systemui.plugins.qs.QSTile.State
        public State copy() {
            AdapterState adapterState = new AdapterState();
            copyTo(adapterState);
            return adapterState;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.State
        public boolean copyTo(State state) {
            AdapterState adapterState = (AdapterState) state;
            boolean z = (!super.copyTo(state) && adapterState.value == this.value && adapterState.forceExpandIcon == this.forceExpandIcon) ? false : true;
            adapterState.value = this.value;
            adapterState.forceExpandIcon = this.forceExpandIcon;
            return z;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.State
        public StringBuilder toStringBuilder() {
            StringBuilder stringBuilder = super.toStringBuilder();
            stringBuilder.insert(stringBuilder.length() - 1, ",value=" + this.value);
            stringBuilder.insert(stringBuilder.length() + (-1), ",forceExpandIcon=" + this.forceExpandIcon);
            return stringBuilder;
        }
    }

    @ProvidesInterface(version = 1)
    public class BooleanState extends AdapterState {
        public static final int VERSION = 1;

        @Override // com.android.systemui.plugins.qs.QSTile.AdapterState, com.android.systemui.plugins.qs.QSTile.State
        public State copy() {
            BooleanState booleanState = new BooleanState();
            copyTo(booleanState);
            return booleanState;
        }
    }

    @ProvidesInterface(version = 2)
    public interface Callback {
        public static final int VERSION = 2;

        void onStateChanged(State state);
    }

    @ProvidesInterface(version = 1)
    public abstract class Icon {
        public static final int VERSION = 1;

        public abstract Drawable getDrawable(Context context);

        public Drawable getInvisibleDrawable(Context context) {
            return getDrawable(context);
        }

        public int getPadding() {
            return 0;
        }

        public int hashCode() {
            return Icon.class.hashCode();
        }

        public String toString() {
            return "Icon";
        }
    }

    @ProvidesInterface(version = 1)
    public class State {
        public static final int DEFAULT_STATE = 2;
        public static final int VERSION = 1;
        public CharSequence contentDescription;
        public boolean disabledByPolicy;
        public CharSequence dualLabelContentDescription;
        public String expandedAccessibilityClassName;
        public Icon icon;
        public Supplier<Icon> iconSupplier;
        public boolean isCustomTile;
        public CharSequence label;
        public Icon nextIcon;
        public CharSequence secondaryLabel;
        public Drawable sideViewCustomDrawable;
        public String spec;
        public CharSequence stateDescription;
        public String tileClassName;
        public int state = 2;
        public boolean dualTarget = false;
        public boolean isTransient = false;
        public boolean handlesLongClick = true;
        public boolean handlesSecondaryClick = false;

        public State copy() {
            State state = new State();
            copyTo(state);
            return state;
        }

        public boolean copyTo(State state) {
            if (state == null) {
                throw new IllegalArgumentException();
            }
            if (!state.getClass().equals(getClass())) {
                throw new IllegalArgumentException();
            }
            boolean z = (Objects.equals(state.spec, this.spec) && Objects.equals(state.icon, this.icon) && Objects.equals(state.iconSupplier, this.iconSupplier) && Objects.equals(state.label, this.label) && Objects.equals(state.secondaryLabel, this.secondaryLabel) && Objects.equals(state.contentDescription, this.contentDescription) && Objects.equals(state.stateDescription, this.stateDescription) && Objects.equals(state.dualLabelContentDescription, this.dualLabelContentDescription) && Objects.equals(state.expandedAccessibilityClassName, this.expandedAccessibilityClassName) && Boolean.valueOf(state.disabledByPolicy).equals(Boolean.valueOf(this.disabledByPolicy)) && Integer.valueOf(state.state).equals(Integer.valueOf(this.state)) && Boolean.valueOf(state.isTransient).equals(Boolean.valueOf(this.isTransient)) && Boolean.valueOf(state.dualTarget).equals(Boolean.valueOf(this.dualTarget)) && Boolean.valueOf(state.handlesLongClick).equals(Boolean.valueOf(this.handlesLongClick)) && Boolean.valueOf(state.handlesSecondaryClick).equals(Boolean.valueOf(this.handlesSecondaryClick)) && Objects.equals(state.sideViewCustomDrawable, this.sideViewCustomDrawable)) ? false : true;
            state.spec = this.spec;
            state.icon = this.icon;
            state.nextIcon = this.nextIcon;
            state.iconSupplier = this.iconSupplier;
            state.label = this.label;
            state.secondaryLabel = this.secondaryLabel;
            state.contentDescription = this.contentDescription;
            state.stateDescription = this.stateDescription;
            state.dualLabelContentDescription = this.dualLabelContentDescription;
            state.expandedAccessibilityClassName = this.expandedAccessibilityClassName;
            state.disabledByPolicy = this.disabledByPolicy;
            state.state = this.state;
            state.dualTarget = this.dualTarget;
            state.isTransient = this.isTransient;
            state.handlesLongClick = this.handlesLongClick;
            state.handlesSecondaryClick = this.handlesSecondaryClick;
            state.sideViewCustomDrawable = this.sideViewCustomDrawable;
            state.isCustomTile = this.isCustomTile;
            state.tileClassName = this.tileClassName;
            return z;
        }

        public CharSequence getSecondaryLabel(CharSequence charSequence) {
            CharSequence charSequence2 = this.secondaryLabel;
            return TextUtils.isEmpty(charSequence2) ? charSequence : charSequence2;
        }

        public CharSequence getStateText(int i, Resources resources) {
            return (this.state == 0 || (this instanceof BooleanState)) ? resources.getStringArray(i)[this.state] : "";
        }

        public String toString() {
            return toStringBuilder().toString();
        }

        public StringBuilder toStringBuilder() {
            StringBuilder sb = new StringBuilder(getClass().getSimpleName());
            sb.append("[spec=");
            sb.append(this.spec);
            sb.append(",icon=");
            sb.append(this.icon);
            sb.append(",nextIcon=");
            sb.append(this.nextIcon);
            sb.append(",iconSupplier=");
            sb.append(this.iconSupplier);
            sb.append(",label=");
            sb.append(this.label);
            sb.append(",secondaryLabel=");
            sb.append(this.secondaryLabel);
            sb.append(",contentDescription=");
            sb.append(this.contentDescription);
            sb.append(",stateDescription=");
            sb.append(this.stateDescription);
            sb.append(",dualLabelContentDescription=");
            sb.append(this.dualLabelContentDescription);
            sb.append(",expandedAccessibilityClassName=");
            sb.append(this.expandedAccessibilityClassName);
            sb.append(",disabledByPolicy=");
            sb.append(this.disabledByPolicy);
            sb.append(",dualTarget=");
            sb.append(this.dualTarget);
            sb.append(",isTransient=");
            sb.append(this.isTransient);
            sb.append(",handlesSecondaryClick=");
            sb.append(this.handlesSecondaryClick);
            sb.append(",state=");
            sb.append(this.state);
            sb.append(",sideViewCustomDrawable=");
            sb.append(this.sideViewCustomDrawable);
            sb.append(",tileClassName=");
            sb.append(this.tileClassName);
            sb.append(']');
            return sb;
        }
    }

    void addCallback(Callback callback);

    void click(Expandable expandable);

    void destroy();

    int getCurrentTileUser();

    default DetailAdapter getDetailAdapter() {
        return null;
    }

    default TileDetailsViewModel getDetailsViewModel() {
        return null;
    }

    InstanceId getInstanceId();

    @Deprecated
    int getMetricsCategory();

    default String getMetricsSpec() {
        return getClass().getSimpleName();
    }

    State getState();

    CharSequence getTileLabel();

    String getTileSpec();

    boolean isAvailable();

    boolean isDestroyed();

    boolean isListening();

    default boolean isTileReady() {
        return false;
    }

    void longClick(Expandable expandable);

    void refreshState();

    void removeCallback(Callback callback);

    void removeCallbacks();

    void secondaryClick(Expandable expandable);

    void setDetailListening(boolean z);

    void setListening(Object obj, boolean z);

    void setTileSpec(String str);

    void userSwitch(int i);

    default boolean getDetailsViewModel(Consumer<TileDetailsViewModel> consumer) {
        TileDetailsViewModel detailsViewModel = getDetailsViewModel();
        consumer.accept(detailsViewModel);
        return detailsViewModel != null;
    }

    default LogMaker populate(LogMaker logMaker) {
        return logMaker;
    }

    @Deprecated
    default void clearState() {
    }
}
