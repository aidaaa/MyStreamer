// Generated by Dagger (https://google.github.io/dagger).
package com.example.mystreamer.dagger;

import android.content.Context;
import com.example.mystreamer.MainActivity;
import com.example.mystreamer.MainActivity_MembersInjector;
import com.google.android.exoplayer2.upstream.DataSource;
import dagger.internal.Preconditions;

public final class DaggerPlayerComponent implements PlayerComponent {
  private PlayerModule playerModule;

  private Context context;

  private DaggerPlayerComponent(Builder builder) {
    initialize(builder);
  }

  public static PlayerComponent.Builder builder() {
    return new Builder();
  }

  private ContextClass getContextClass() {
    return new ContextClass(context);
  }

  private DataSource.Factory getFactory() {
    return PlayerModule_GetgetDataSourceFactoryFactory.proxyGetgetDataSourceFactory(
        playerModule, getContextClass());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.playerModule = builder.playerModule;
    this.context = builder.context;
  }

  @Override
  public void getPlayer(MainActivity mainActivity) {
    injectMainActivity(mainActivity);
  }

  private MainActivity injectMainActivity(MainActivity instance) {
    MainActivity_MembersInjector.injectTrackSelector(
        instance,
        PlayerModule_GetDefaultTrackSelectorFactory.proxyGetDefaultTrackSelector(playerModule));
    MainActivity_MembersInjector.injectDaFactory(instance, getFactory());
    return instance;
  }

  private static final class Builder implements PlayerComponent.Builder {
    private PlayerModule playerModule;

    private Context context;

    @Override
    public PlayerComponent build() {
      if (playerModule == null) {
        this.playerModule = new PlayerModule();
      }
      if (context == null) {
        throw new IllegalStateException(Context.class.getCanonicalName() + " must be set");
      }
      return new DaggerPlayerComponent(this);
    }

    @Override
    public Builder context(Context context) {
      this.context = Preconditions.checkNotNull(context);
      return this;
    }
  }
}
