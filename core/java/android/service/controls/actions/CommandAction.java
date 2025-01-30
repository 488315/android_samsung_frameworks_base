package android.service.controls.actions;

import android.os.Bundle;

/* loaded from: classes3.dex */
public final class CommandAction extends ControlAction {
  private static final int TYPE = 5;

  public CommandAction(String templateId, String challengeValue) {
    super(templateId, challengeValue);
  }

  public CommandAction(String templateId) {
    this(templateId, null);
  }

  CommandAction(Bundle b) {
    super(b);
  }

  @Override // android.service.controls.actions.ControlAction
  public int getActionType() {
    return 5;
  }
}
