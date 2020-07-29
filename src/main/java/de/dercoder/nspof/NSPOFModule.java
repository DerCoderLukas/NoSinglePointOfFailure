package de.dercoder.nspof;

import com.google.inject.*;

import javax.inject.Singleton;

public final class NSPOFModule<T> extends AbstractModule {
  private NSPOFModule() {

  }

  @Provides
  @Singleton
  NSPOFDatabaseRegistry provideNSPOFDatabaseRegistry() {
    return NSPOFDatabaseRegistry.<T>empty();
  }

  @Provides
  @Singleton
  NSPOFNodeRegistry provideNSPOFNodeRegistry() {
    return NSPOFNodeRegistry.<T>empty();
  }

  public static <T> NSPOFModule<T> create() {
    return new NSPOFModule<T>();
  }
}
