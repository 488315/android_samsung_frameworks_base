package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import androidx.core.app.NotificationCompat$Action;
import androidx.core.graphics.drawable.IconCompat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NotificationCompat$CallStyle extends NotificationCompat$Style {
    public Integer mAnswerButtonColor;
    public PendingIntent mAnswerIntent;
    public int mCallType;
    public Integer mDeclineButtonColor;
    public PendingIntent mDeclineIntent;
    public PendingIntent mHangUpIntent;
    public boolean mIsVideo;
    public Person mPerson;
    public IconCompat mVerificationIcon;
    public CharSequence mVerificationText;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api31Impl {
        private Api31Impl() {
        }

        public static Notification.CallStyle forIncomingCall(android.app.Person person, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
            return Notification.CallStyle.forIncomingCall(person, pendingIntent, pendingIntent2);
        }

        public static Notification.CallStyle forOngoingCall(android.app.Person person, PendingIntent pendingIntent) {
            return Notification.CallStyle.forOngoingCall(person, pendingIntent);
        }

        public static Notification.CallStyle forScreeningCall(android.app.Person person, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
            return Notification.CallStyle.forScreeningCall(person, pendingIntent, pendingIntent2);
        }

        public static Notification.CallStyle setAnswerButtonColorHint(Notification.CallStyle callStyle, int i) {
            return callStyle.setAnswerButtonColorHint(i);
        }

        public static Notification.CallStyle setDeclineButtonColorHint(Notification.CallStyle callStyle, int i) {
            return callStyle.setDeclineButtonColorHint(i);
        }

        public static Notification.CallStyle setIsVideo(Notification.CallStyle callStyle, boolean z) {
            return callStyle.setIsVideo(z);
        }

        public static Notification.CallStyle setVerificationIcon(Notification.CallStyle callStyle, Icon icon) {
            return callStyle.setVerificationIcon(icon);
        }

        public static Notification.CallStyle setVerificationText(Notification.CallStyle callStyle, CharSequence charSequence) {
            return callStyle.setVerificationText(charSequence);
        }
    }

    public NotificationCompat$CallStyle() {
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void addCompatExtras(Bundle bundle) {
        super.addCompatExtras(bundle);
        bundle.putInt("android.callType", this.mCallType);
        bundle.putBoolean("android.callIsVideo", this.mIsVideo);
        Person person = this.mPerson;
        if (person != null) {
            bundle.putParcelable("android.callPerson", Api28Impl.castToParcelable(person.toAndroidPerson()));
        }
        IconCompat iconCompat = this.mVerificationIcon;
        if (iconCompat != null) {
            Context context = this.mBuilder.mContext;
            bundle.putParcelable("android.verificationIcon", Api23Impl.castToParcelable(iconCompat.toIcon$1()));
        }
        bundle.putCharSequence("android.verificationText", this.mVerificationText);
        bundle.putParcelable("android.answerIntent", this.mAnswerIntent);
        bundle.putParcelable("android.declineIntent", this.mDeclineIntent);
        bundle.putParcelable("android.hangUpIntent", this.mHangUpIntent);
        Integer num = this.mAnswerButtonColor;
        if (num != null) {
            bundle.putInt("android.answerColor", num.intValue());
        }
        Integer num2 = this.mDeclineButtonColor;
        if (num2 != null) {
            bundle.putInt("android.declineColor", num2.intValue());
        }
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void apply(NotificationCompatBuilder notificationCompatBuilder) {
        Notification.CallStyle forIncomingCall;
        int i = this.mCallType;
        if (i == 1) {
            forIncomingCall = Api31Impl.forIncomingCall(this.mPerson.toAndroidPerson(), this.mDeclineIntent, this.mAnswerIntent);
        } else if (i == 2) {
            forIncomingCall = Api31Impl.forOngoingCall(this.mPerson.toAndroidPerson(), this.mHangUpIntent);
        } else if (i != 3) {
            if (Log.isLoggable("NotifCompat", 3)) {
                Log.d("NotifCompat", "Unrecognized call type in CallStyle: " + String.valueOf(this.mCallType));
            }
            forIncomingCall = null;
        } else {
            forIncomingCall = Api31Impl.forScreeningCall(this.mPerson.toAndroidPerson(), this.mHangUpIntent, this.mAnswerIntent);
        }
        if (forIncomingCall != null) {
            forIncomingCall.setBuilder(notificationCompatBuilder.mBuilder);
            Integer num = this.mAnswerButtonColor;
            if (num != null) {
                Api31Impl.setAnswerButtonColorHint(forIncomingCall, num.intValue());
            }
            Integer num2 = this.mDeclineButtonColor;
            if (num2 != null) {
                Api31Impl.setDeclineButtonColorHint(forIncomingCall, num2.intValue());
            }
            Api31Impl.setVerificationText(forIncomingCall, this.mVerificationText);
            IconCompat iconCompat = this.mVerificationIcon;
            if (iconCompat != null) {
                Context context = this.mBuilder.mContext;
                Api31Impl.setVerificationIcon(forIncomingCall, iconCompat.toIcon$1());
            }
            Api31Impl.setIsVideo(forIncomingCall, this.mIsVideo);
        }
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final String getClassName() {
        return "androidx.core.app.NotificationCompat$CallStyle";
    }

    public final NotificationCompat$Action makeAction(int i, int i2, Integer num, int i3, PendingIntent pendingIntent) {
        if (num == null) {
            num = Integer.valueOf(this.mBuilder.mContext.getColor(i3));
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) this.mBuilder.mContext.getResources().getString(i2));
        spannableStringBuilder.setSpan(new ForegroundColorSpan(num.intValue()), 0, spannableStringBuilder.length(), 18);
        NotificationCompat$Action build = new NotificationCompat$Action.Builder(IconCompat.createWithResource(i, this.mBuilder.mContext), spannableStringBuilder, pendingIntent).build();
        build.mExtras.putBoolean("key_action_priority", true);
        return build;
    }

    @Override // androidx.core.app.NotificationCompat$Style
    public final void restoreFromCompatExtras(Bundle bundle) {
        super.restoreFromCompatExtras(bundle);
        this.mCallType = bundle.getInt("android.callType");
        this.mIsVideo = bundle.getBoolean("android.callIsVideo");
        if (bundle.containsKey("android.callPerson")) {
            this.mPerson = Person.fromAndroidPerson((android.app.Person) bundle.getParcelable("android.callPerson"));
        } else if (bundle.containsKey("android.callPersonCompat")) {
            this.mPerson = Person.fromBundle(bundle.getBundle("android.callPersonCompat"));
        }
        if (bundle.containsKey("android.verificationIcon")) {
            Icon icon = (Icon) bundle.getParcelable("android.verificationIcon");
            PorterDuff.Mode mode = IconCompat.DEFAULT_TINT_MODE;
            this.mVerificationIcon = IconCompat.Api23Impl.createFromIconInner(icon);
        } else if (bundle.containsKey("android.verificationIconCompat")) {
            this.mVerificationIcon = IconCompat.createFromBundle(bundle.getBundle("android.verificationIconCompat"));
        }
        this.mVerificationText = bundle.getCharSequence("android.verificationText");
        this.mAnswerIntent = (PendingIntent) bundle.getParcelable("android.answerIntent");
        this.mDeclineIntent = (PendingIntent) bundle.getParcelable("android.declineIntent");
        this.mHangUpIntent = (PendingIntent) bundle.getParcelable("android.hangUpIntent");
        this.mAnswerButtonColor = bundle.containsKey("android.answerColor") ? Integer.valueOf(bundle.getInt("android.answerColor")) : null;
        this.mDeclineButtonColor = bundle.containsKey("android.declineColor") ? Integer.valueOf(bundle.getInt("android.declineColor")) : null;
    }

    public NotificationCompat$CallStyle(NotificationCompat$Builder notificationCompat$Builder) {
        setBuilder(notificationCompat$Builder);
    }

    private NotificationCompat$CallStyle(int i, Person person, PendingIntent pendingIntent, PendingIntent pendingIntent2, PendingIntent pendingIntent3) {
        if (person != null && !TextUtils.isEmpty(person.mName)) {
            this.mCallType = i;
            this.mPerson = person;
            this.mAnswerIntent = pendingIntent3;
            this.mDeclineIntent = pendingIntent2;
            this.mHangUpIntent = pendingIntent;
            return;
        }
        throw new IllegalArgumentException("person must have a non-empty a name");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api23Impl {
        private Api23Impl() {
        }

        public static Parcelable castToParcelable(Icon icon) {
            return icon;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api28Impl {
        private Api28Impl() {
        }

        public static Parcelable castToParcelable(android.app.Person person) {
            return person;
        }
    }
}
