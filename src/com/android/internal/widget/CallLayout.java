package com.android.internal.widget;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.flags.Flags;
import com.android.internal.R;
import java.util.function.Consumer;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class CallLayout extends FrameLayout {
    private CachingIconView mConversationIconBadgeBg;
    private CachingIconView mConversationIconView;
    private CachingIconView mIcon;
    private Icon mLargeIcon;
    private int mLayoutColor;
    private final PeopleHelper mPeopleHelper;
    private Person mUser;

    static /* synthetic */ void lambda$setLargeIconAsync$4() {
    }

    static /* synthetic */ void lambda$setLayoutColorAsync$2() {
    }

    public CallLayout(Context context) {
        super(context);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CallLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CallLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CallLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPeopleHelper = new PeopleHelper();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPeopleHelper.init(getContext());
        this.mConversationIconView = (CachingIconView) findViewById(R.id.conversation_icon);
        this.mIcon = (CachingIconView) findViewById(16908294);
        this.mConversationIconBadgeBg = (CachingIconView) findViewById(R.id.conversation_icon_badge_bg);
        this.mIcon.setOnForceHiddenChangedListener(new Consumer() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CallLayout.this.lambda$onFinishInflate$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(Boolean bool) {
        this.mPeopleHelper.animateViewForceHidden(this.mConversationIconBadgeBg, bool.booleanValue());
    }

    private Icon getConversationIcon() {
        Icon icon;
        String str;
        Person person = this.mUser;
        CharSequence charSequence = "";
        if (person != null) {
            icon = person.getIcon();
            CharSequence name = this.mUser.getName();
            str = this.mPeopleHelper.findNamePrefix(name, "");
            charSequence = name;
        } else {
            icon = null;
            str = "";
        }
        if (icon == null) {
            icon = this.mLargeIcon;
        }
        return icon == null ? this.mPeopleHelper.createAvatarSymbol(charSequence, str, this.mLayoutColor) : icon;
    }

    public Runnable setLayoutColorAsync(final int i) {
        if (!Flags.callStyleSetDataAsync()) {
            return new Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    CallLayout.this.lambda$setLayoutColorAsync$1(i);
                }
            };
        }
        this.mLayoutColor = i;
        return new Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CallLayout.lambda$setLayoutColorAsync$2();
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setLayoutColorAsync")
    /* renamed from: setLayoutColor, reason: merged with bridge method [inline-methods] */
    public void lambda$setLayoutColorAsync$1(int i) {
        this.mLayoutColor = i;
    }

    @RemotableViewMethod
    public void setNotificationBackgroundColor(int i) {
        this.mConversationIconBadgeBg.setImageTintList(ColorStateList.valueOf(i));
    }

    public Runnable setLargeIconAsync(final Icon icon) {
        if (!Flags.callStyleSetDataAsync()) {
            return new Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    CallLayout.this.lambda$setLargeIconAsync$3(icon);
                }
            };
        }
        this.mLargeIcon = icon;
        return new Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                CallLayout.lambda$setLargeIconAsync$4();
            }
        };
    }

    @RemotableViewMethod(asyncImpl = "setLargeIconAsync")
    /* renamed from: setLargeIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$setLargeIconAsync$3(Icon icon) {
        this.mLargeIcon = icon;
    }

    @RemotableViewMethod(asyncImpl = "setDataAsync")
    /* renamed from: setData, reason: merged with bridge method [inline-methods] */
    public void lambda$setDataAsync$5(Bundle bundle) {
        setUser(getPerson(bundle));
        this.mConversationIconView.setImageIcon(getConversationIcon());
    }

    public Runnable setDataAsync(final Bundle bundle) {
        if (!Flags.callStyleSetDataAsync()) {
            return new Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CallLayout.this.lambda$setDataAsync$5(bundle);
                }
            };
        }
        setUser(getPerson(bundle));
        return this.mConversationIconView.setImageIconAsync(getConversationIcon());
    }

    private Person getPerson(Bundle bundle) {
        return (Person) bundle.getParcelable(Notification.EXTRA_CALL_PERSON, Person.class);
    }

    private void setUser(Person person) {
        this.mUser = person;
    }
}
