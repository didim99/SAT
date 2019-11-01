package com.didim99.sat.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.didim99.sat.R;
import com.didim99.sat.SAT;
import com.didim99.sat.settings.Settings;
import com.didim99.sat.ui.sbxeditor.DialogManager;
import com.didim99.sat.utils.MyLog;

/**
 * Basic Activity wrapper for this application
 * Created by didim99 on 26.07.18.
 */
public abstract class BaseActivity extends AppCompatActivity
  implements SAT.GlobalEventListener {
  private static final String LOG_TAG = MyLog.LOG_TAG_BASE + "_BaseAct";

  protected DialogManager dialogManager;

  @Override
  @CallSuper
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    updateUITheme();
    super.onCreate(savedInstanceState);
    dialogManager = DialogManager.getInstance();
  }

  @Override
  @CallSuper
  protected void onResume() {
    super.onResume();
    ((SAT) getApplication()).registerEventListener(this);
  }

  @Override
  @CallSuper
  protected void onPause() {
    ((SAT) getApplication()).unregisterEventListener();
    super.onPause();
  }

  @Override
  @CallSuper
  public void onGlobalEvent(SAT.GlobalEvent event) {
    MyLog.d(LOG_TAG, "Global event received: " + event);
    switch (event) {
      case DB_DAMAGED:
        dialogManager.dbDamaged((SAT) getApplication());
        break;
      case UI_RELOAD:
        this.recreate();
        break;
    }
  }

  private void updateUITheme() {
    switch (Settings.getTheme()) {
      case Settings.THEME_DARK: setTheme(R.style.AppThemeDark); break;
      case Settings.THEME_LIGHT: setTheme(R.style.AppThemeLight); break;
    }
  }
}
